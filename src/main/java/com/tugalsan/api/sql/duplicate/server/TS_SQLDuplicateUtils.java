package com.tugalsan.api.sql.duplicate.server;

import com.tugalsan.api.sql.conn.server.*;
import com.tugalsan.api.union.client.TGS_UnionExcuse;

public class TS_SQLDuplicateUtils {

    public static  TGS_UnionExcuse<TS_SQLDuplicate> duplicateWhereFirstColAsId(TS_SQLConnAnchor anchor, CharSequence tableName, long id) {
        return TS_SQLDuplicate.of(anchor, tableName, id);
    }

//    public static void test() {
//        TS_SQLDuplicateUtils.duplicateWhereFirstColAsId(null, "tn", 0).genIdNext();
//    }
}
