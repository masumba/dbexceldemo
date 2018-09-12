package com.example.excel.db.dbexceldemo.Modal;

public class Database {
    private String database;

    public Database() {
    }

    public String getDatabase() {
        return database;
    }

    public void setDatabase(String database) {
        this.database = database;
    }

    @Override
    public String toString() {
        return "Database{" +
                "database='" + database + '\'' +
                '}';
    }
}
