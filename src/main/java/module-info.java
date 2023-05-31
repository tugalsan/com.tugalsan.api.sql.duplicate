module com.tugalsan.api.sql.duplicate {
    requires java.sql;
    requires com.tugalsan.api.runnable;
    requires com.tugalsan.api.string;
    requires com.tugalsan.api.tuple;
    requires com.tugalsan.api.sql.cellgen;
    requires com.tugalsan.api.sql.conn;
    requires com.tugalsan.api.sql.update;
    requires com.tugalsan.api.sql.where;
    requires com.tugalsan.api.sql.sanitize;
    exports com.tugalsan.api.sql.duplicate.server;
}
