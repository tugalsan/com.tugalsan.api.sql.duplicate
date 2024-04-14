package com.tugalsan.api.sql.duplicate.server;

import com.tugalsan.api.sql.cellgen.server.*;
import com.tugalsan.api.sql.conn.server.*;
import com.tugalsan.api.union.client.TGS_UnionExcuse;

public class TS_SQLDuplicate {

    protected TS_SQLDuplicate(TS_SQLDuplicateExecutor executor) {
        this.executor = executor;
    }
    private final TS_SQLDuplicateExecutor executor;

    public static TGS_UnionExcuse<TS_SQLDuplicate> of(TS_SQLConnAnchor anchor, CharSequence tableName, long id) {
        var u = TS_SQLDuplicateExecutor.of(anchor, tableName, id);
        if (u.isExcuse()) {
            return u.toExcuse();
        }
        return TGS_UnionExcuse.of(new TS_SQLDuplicate(u.value()));
    }

    public TGS_UnionExcuse<TS_SQLConnStmtUpdateResult> genIdNext() {
        executor.genId = new TS_SQLCellGenLngNext(executor, 0, executor.anchor, executor.tableName, executor.colNames);
        return executor.run();
    }

    public TGS_UnionExcuse<TS_SQLConnStmtUpdateResult> genIdNextDated() {
        executor.genId = new TS_SQLCellGenLngNextDated(executor, 0, executor.anchor, executor.tableName, executor.colNames);
        return executor.run();
    }
}
