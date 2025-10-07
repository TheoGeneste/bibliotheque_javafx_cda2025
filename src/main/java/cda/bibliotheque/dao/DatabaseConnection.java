package cda.bibliotheque.dao;

import java.sql.Connection;

public class DatabaseConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/bibliotheque_cda2025";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    public static Connection connection;
}
