package com.tugalsan.api.sql.duplicate.server;

import module com.tugalsan.api.sql.conn;

public class TS_SQLDuplicateUtils {

    public static TS_SQLDuplicate duplicateWhereFirstColAsId(TS_SQLConnAnchor anchor, CharSequence tableName, long id) {
        return new TS_SQLDuplicate(anchor, tableName, id);
    }

    public static void test() {
        TS_SQLDuplicateUtils.duplicateWhereFirstColAsId(null, "tn", 0).genIdNext();
    }
}
