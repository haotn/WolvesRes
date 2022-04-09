package com.wolvesres.helper;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author haotn
 */
public class XJdbc {

    static String driver = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
    static String dburl = "jdbc:sqlserver://localhost:1433;databaseName=WOLVESRES";
    static String user = "sa";
    static String pass = "123";

    static {
        try {
            Class.forName(driver);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static PreparedStatement getStmt(String sql, Object... args) throws SQLException {
        Connection conn = DriverManager.getConnection(dburl, user, pass);
        PreparedStatement stmt;
        if (sql.trim().startsWith("{")) {
            stmt = conn.prepareCall(sql);
        } else {
            stmt = conn.prepareStatement(sql);
        }
        for (int i = 0; i < args.length; i++) {
            stmt.setObject(i + 1, args[i]);
        }
        return stmt;
    }

    public static int update(String sql, Object... args) throws SQLException {
        try {
            PreparedStatement stmt = XJdbc.getStmt(sql, args);
            try {
                return stmt.executeUpdate();
            } finally {
                //  stmt.getConnection().close();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static ResultSet query(String sql, Object... args) throws SQLException {
        PreparedStatement stmt = XJdbc.getStmt(sql, args);
        return stmt.executeQuery();
    }

    public static Object value(String sql, Object... args) {
        try {
            ResultSet result = XJdbc.query(sql, args);
            if (result.next()) {
                return result.getObject(0);
            }
            // result.getStatement().getConnection().close();
            return null;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
