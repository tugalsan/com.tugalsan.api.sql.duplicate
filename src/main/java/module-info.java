module com.tugalsan.api.sql.duplicate {
    requires java.sql;
    requires com.tugalsan.api.executable;
    requires com.tugalsan.api.string;
    requires com.tugalsan.api.pack;
    requires com.tugalsan.api.sql.cellgen;
    requires com.tugalsan.api.sql.conn;
    requires com.tugalsan.api.sql.update;
    requires com.tugalsan.api.sql.where;
    requires com.tugalsan.api.sql.sanitize;
    exports com.tugalsan.api.sql.duplicate.server;
}
