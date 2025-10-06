package org.arkn37.util;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Database {
    private static final String PROPS = "/org/arkn37/db.properties";
    private static final String URL;
    private static final String USER;
    private static final String PASSWORD;

    static {
        try (InputStream in = Database.class.getResourceAsStream(PROPS)) {
            if (in == null) {
                throw new IllegalStateException("Database properties file not found: " + PROPS);
            }
            Properties p = new Properties();
            p.load(in);
            URL = p.getProperty("db.url");
            USER = p.getProperty("db.user");
            PASSWORD = p.getProperty("db.password");
        } catch (IOException e) {
            throw new ExceptionInInitializerError(e);
        }
    }

    private Database() {
        throw new IllegalStateException("Utility class");
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
