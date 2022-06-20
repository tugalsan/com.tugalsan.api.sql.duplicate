package com.tugalsan.api.sql.duplicate.server;

import com.tugalsan.api.sql.cellgen.server.*;
import com.tugalsan.api.sql.conn.server.*;

public class TS_SQLDuplicate {

    public TS_SQLDuplicate(TS_SQLConnAnchor anchor, CharSequence tableName, long id) {
        executor = new TS_SQLDuplicateExecutor(anchor, tableName, id);
    }
    private TS_SQLDuplicateExecutor executor;

    public int genIdNext() {
        executor.genId = new TS_SQLCellGenLngNext(executor, 0, executor.anchor, executor.tableName, executor.colNames);
        return executor.execute();
    }

    public int genIdNextDated() {
        executor.genId = new TS_SQLCellGenLngNextDated(executor, 0, executor.anchor, executor.tableName, executor.colNames);
        return executor.execute();
    }
}
