package com.codecool.shop.config;

import java.io.InputStream;
import java.util.Properties;

import org.postgresql.ds.PGSimpleDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;

public class ConnectionSetter {
    private static final Logger logger = LoggerFactory.getLogger(ConnectionSetter.class);

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

            logger.info("Trying to connect database...");
            dataSource.getConnection().close();
            logger.info("Connection OK");

            return dataSource;
        } catch (Exception e) {
            logger.error("Connection error.");
            return null;
        }
    }

}
