package com.codecool.shop.config;

import com.codecool.shop.dao.ProductCategoryDao;
import com.codecool.shop.dao.implementation.ProductCategoryDaoJDBC;
import com.codecool.shop.dao.implementation.ProductCategoryDaoMem;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class DaoSelector {

    public static String select(){

    ClassLoader classloader = Thread.currentThread().getContextClassLoader();
    InputStream inputStream = classloader.getResourceAsStream("connection.properties");
    Properties properties = new Properties();
        try {
            properties.load(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }

        String database = (String) properties.get("dao");

    return database;
}
}
