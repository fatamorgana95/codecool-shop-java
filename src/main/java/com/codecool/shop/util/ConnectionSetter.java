package com.codecool.shop.util;

import java.io.InputStream;
import java.util.Properties;
import org.postgresql.ds.PGSimpleDataSource;

import javax.sql.DataSource;

public class ConnectionSetter {

    public static DataSource connect() {
        try {
            ClassLoader classloader = Thread.currentThread().getContextClassLoader();
            InputStream inputStream = classloader.getResourceAsStream("connection.properties");
            Properties properties = new Properties();
            properties.load(inputStream);

            String database = (String) properties.get("database");
            String url = properties.get("url") + "/" + database;
            String user = (String) properties.get("user");
            String password = (String) properties.get("password");

            PGSimpleDataSource dataSource = new PGSimpleDataSource();
            dataSource.setURL(url);
            dataSource.setDatabaseName(database);
            dataSource.setUser(user);
            dataSource.setPassword(password);

            System.out.println("Trying to connect...");
            dataSource.getConnection().close();
            System.out.println("Connection OK");

            return dataSource;
        }
        catch(Exception e){
            System.err.println("Connection error.");
            return null;
        }
    }

   /* public static Optional<Connection> getConnection() {
        try {
            FileInputStream inputStream = new FileInputStream("resources/connection.properties");
            Properties properties = new Properties();
            properties.load(inputStream);

            String url = (String) properties.get("url");
            String database = (String) properties.get("database");
            String user = (String) properties.get("user");
            String password = (String) properties.get("password");

            Class.forName(database);

            return Optional.of(DriverManager.getConnection(
                    url, user, password));
        }
        catch (Exception e) {
            System.err.println("Connection error.");
            return Optional.empty();
        }*/
}
