package com.tugalsan.api.sql.duplicate.server;

import java.sql.*;
import java.util.*;
import com.tugalsan.api.sql.cellgen.server.*;
import com.tugalsan.api.sql.conn.server.*;
import com.tugalsan.api.sql.sanitize.server.*;
import com.tugalsan.api.sql.update.server.*;
import com.tugalsan.api.sql.where.server.*;
import com.tugalsan.api.string.client.*;
import com.tugalsan.api.tuple.client.TGS_Tuple1;
import com.tugalsan.api.union.client.TGS_UnionExcuse;

public class TS_SQLDuplicateExecutor {

    protected TS_SQLDuplicateExecutor(TS_SQLConnAnchor anchor, CharSequence tableName, long id, List<String> colNames) {
        this.anchor = anchor;
        this.tableName = tableName.toString();
        this.colNames = colNames;
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

    public static TGS_UnionExcuse<TS_SQLDuplicateExecutor> of(TS_SQLConnAnchor anchor, CharSequence tableName, long id) {
        var u_colNames = TS_SQLConnColUtils.names(anchor, tableName);
        if (u_colNames.isExcuse()) {
            return u_colNames.toExcuse();
        }
        return TGS_UnionExcuse.of(new TS_SQLDuplicateExecutor(anchor, tableName, id, u_colNames.value()));
    }

    public TS_SQLCellGenAbstract genId = null;

    @Override
    public String toString() {
        TS_SQLSanitizeUtils.sanitize(tableName);
        var colNamesLine = TGS_StringUtils.toString(colNames, ",");
        var colNamesLineExceptId = TGS_StringUtils.toString(colNames, ",", 1);
        return TGS_StringUtils.concat(
                "INSERT INTO ", tableName, " (", colNamesLine, ")",
                " SELECT ?, ", colNamesLineExceptId,
                " FROM ", tableName,
                " WHERE ", where.toString()
        );
    }

    private TGS_UnionExcuse<Integer> set_fill(PreparedStatement fillStmt, int offset) {
        TGS_Tuple1<Long> res = new TGS_Tuple1(-1);
        genId.run(res);
        return TS_SQLConnStmtUtils.fill(fillStmt, colNames.get(0), res.value0, offset);
    }

    public TGS_UnionExcuse<TS_SQLConnStmtUpdateResult> run() {
        var wrap = new Object() {
            TGS_UnionExcuse<Integer> u_set_fill;
            TGS_UnionExcuse<Integer> u_where_fill;
        };
        var u_update = TS_SQLUpdateStmtUtils.update(anchor, toString(), fillStmt -> {
            wrap.u_set_fill = set_fill(fillStmt, 0);
            if (wrap.u_set_fill.isExcuse()) {
                return;
            }
            wrap.u_where_fill = where.fill(fillStmt, wrap.u_set_fill.value());
        });
        if (wrap.u_set_fill != null && wrap.u_set_fill.isExcuse()) {
            return wrap.u_set_fill.toExcuse();
        }
        if (wrap.u_where_fill != null && wrap.u_where_fill.isExcuse()) {
            return wrap.u_where_fill.toExcuse();
        }
        return u_update;
    }
}
