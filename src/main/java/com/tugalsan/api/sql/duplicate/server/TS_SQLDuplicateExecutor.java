package com.tugalsan.api.sql.duplicate.server;

import module com.tugalsan.api.tuple;
import module com.tugalsan.api.sql.cellgen;
import module com.tugalsan.api.sql.conn;
import module com.tugalsan.api.sql.sanitize;
import module com.tugalsan.api.sql.update;
import module com.tugalsan.api.sql.where;
import module com.tugalsan.api.string;
import module java.sql;
import java.util.*;

public class TS_SQLDuplicateExecutor {

    public TS_SQLDuplicateExecutor(TS_SQLConnAnchor anchor, CharSequence tableName, long id) {
        this.anchor = anchor;
        this.tableName = tableName.toString();
        this.colNames = TS_SQLConnColUtils.names(anchor, tableName);
        this.where = TS_SQLWhereUtils.where();
        where.groupsAnd(groups -> {
            groups.conditionsAnd(conditions -> {
                conditions.lngEq(colNames.get(0), id);
            });
        });
    }
    final public TS_SQLConnAnchor anchor;
    final public String tableName;
    final public List<String> colNames;
    public TS_SQLWhere where = null;

    public TS_SQLCellGenAbstract genId = null;

    @Override
    public String toString() {
        TS_SQLSanitizeUtils.sanitize(tableName);
        var colNamesLine = TGS_StringUtils.cmn().toString(colNames, ",");
        var colNamesLineExceptId = TGS_StringUtils.cmn().toString(colNames, ",", 1);
        return TGS_StringUtils.cmn().concat(
                "INSERT INTO ", tableName, " (", colNamesLine, ")",
                " SELECT ?, ", colNamesLineExceptId,
                " FROM ", tableName,
                " WHERE ", where.toString()
        );
    }

    private int set_fill(PreparedStatement fillStmt, int offset) {
        TGS_Tuple1<Long> res = new TGS_Tuple1(-1);
        genId.run(res);
        return TS_SQLConnStmtUtils.fill(fillStmt, colNames.get(0), res.value0, offset);
    }

    public TS_SQLConnStmtUpdateResult run() {
        return TS_SQLUpdateStmtUtils.update(anchor, toString(), fillStmt -> {
            var idx = set_fill(fillStmt, 0);
            where.fill(fillStmt, idx);
        });
    }
}
