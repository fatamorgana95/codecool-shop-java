package com.codecool.shop.config;

import com.codecool.shop.dao.ProductCategoryDao;
import com.codecool.shop.dao.ProductDao;
import com.codecool.shop.dao.SupplierDao;
import com.codecool.shop.dao.implementation.ProductCategoryDaoMem;
import com.codecool.shop.dao.implementation.ProductDaoMem;
import com.codecool.shop.dao.implementation.SupplierDaoMem;
import com.codecool.shop.model.Product;
import com.codecool.shop.model.ProductCategory;
import com.codecool.shop.model.Supplier;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.sql.SQLException;

@WebListener
public class Initializer implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ProductDao productDataStore = ProductDaoMem.getInstance();
        ProductCategoryDao productCategoryDataStore = ProductCategoryDaoMem.getInstance();
        SupplierDao supplierDataStore = SupplierDaoMem.getInstance();

        //setting up a new supplier
        Supplier harryPotter = new Supplier("Harry Potter", "Harry Potter is a series of seven fantasy novels written by British author J. K. Rowling. The novels chronicle the lives of a young wizard, Harry Potter, and his friends Hermione Granger and Ron Weasley, all of whom are students at Hogwarts School of Witchcraft and Wizardry.");
        supplierDataStore.add(harryPotter);

        Supplier gameOfThrones = new Supplier("Game Of Thrones", "Game of Thrones is an American fantasy drama television series created by David Benioff and D. B. Weiss for HBO. It is an adaptation of A Song of Ice and Fire, George R. R. Martin's series of fantasy novels, the first of which is A Game of Thrones (1996).");
        supplierDataStore.add(gameOfThrones);

        Supplier mulan = new Supplier("Mulan", "Mulan is a fictional folk heroine from the Northern and Southern dynasties era (4th to 6th century AD) of Chinese history. According to the legend, Mulan takes her aged father's place in the conscription for the army by disguising herself as a man. ");
        supplierDataStore.add(mulan);

        Supplier howToTrainYourDragon = new Supplier("How To Train Your Dragon", "How to Train Your Dragon (or HTTYD) is an American media franchise from DreamWorks Animation and loosely based on the eponymous series of children's books by British author Cressida Cowell.");
        supplierDataStore.add(howToTrainYourDragon);

        Supplier shrek = new Supplier("Shrek", "Shrek is a 2001 American computer-animated comedy film loosely based on the 1990 fairy tale picture book of the same name by William Steig.");
        supplierDataStore.add(shrek);

        Supplier susu = new Supplier("Susu, a sarkany", "A Süsü, a sárkány 1977-től 1984-ig futott magyar televíziós bábfilmsorozat, amely a Magyar Televízióban készült 1976-tól 1984-ig.");
        supplierDataStore.add(susu);

        Supplier magyarNepMesek = new Supplier("Magyar Nepmesek", "A Magyar népmesék 1980-tól 2012-ig futott magyar televíziós rajzfilmsorozat, amelynek ötlete Mikulás Ferenc stúdióvezető fejében fogant meg.i");
        supplierDataStore.add(magyarNepMesek);



        //setting up a new product category
        ProductCategory red = new ProductCategory("Red", "Colour", "A tablet computer, commonly shortened to tablet, is a thin, flat mobile computer with a touchscreen display.");
        productCategoryDataStore.add(red);

        ProductCategory brown = new ProductCategory("Brown", "Colour", "");
        productCategoryDataStore.add(brown);

        ProductCategory green = new ProductCategory("Green", "Colour", "");
        productCategoryDataStore.add(green);

        ProductCategory black = new ProductCategory("Black", "Colour", "");
        productCategoryDataStore.add(black);

        ProductCategory white = new ProductCategory("White", "Colour", "");
        productCategoryDataStore.add(white);


        //setting up products and printing it
        try {
            productDataStore.add(new Product("7 Headed Dragon", 1500, "HUF", "They say seven is better than one! Grab this excellent dragon if you know how to handle more than one.", green, magyarNepMesek, "sevenheadeddragon.png"));
            productDataStore.add(new Product("Susu", 3250, "HUF", "Hungarian one-headed dragon, for an excellent price. It is kind and would probably never bite you.", green, susu, "susu.png"));
            productDataStore.add(new Product("Toothless", 2323, "HUF", "He is nice, friendly but most importantly dangerous! If you want to train your dragon, Toothles is the best partner for you.", black, howToTrainYourDragon, "toothless.jpg"));
            productDataStore.add(new Product("Mushu", 1, "HUF", "This chinese dragon, may talk a lot but he is kind and friendly. It certainly helps you with fighting the enemy.", red, mulan, "mushu.png"));
            productDataStore.add(new Product("Drogon", 15777, "HUF", "It is one of the most fearful dragon out there. I double dare you to say 'DRACARYS' close to it.", brown, gameOfThrones, "drogon.png"));
            productDataStore.add(new Product("Hungarian Horntail", 131234, "HUF", "Native to Hungary and is considered to be one of the most dangerous dragon breeds, if not the most dangerous.", brown, harryPotter, "hungarianHorntail.jpg"));
            productDataStore.add(new Product("Dragon (Shrek)", 88888, "HUF", "The most dangerous female dragon out there. She might bite or kiss you depends on the situation!", red, shrek, "dragonShrek.png"));
            productDataStore.add(new Product("Light Furry", 6668, "HUF", "Can walk through the middle of a village without any humans or most dragons sensing her around. ", white, howToTrainYourDragon, "lightFurry.jpg"));
            productDataStore.add(new Product("Dronkey (Shrek)", 44444, "HUF", "Childish energy and the flying and fire breathing abilities of a dragon, there is never a dull moment with the dronkeys!", brown, shrek, "dronkey.png"));
            productDataStore.add(new Product("Norwegian Ridgeback", 10000, "HUF", "A species of dragon, its typical habitat is the Northern mountains. It is said to physically resemble a Hungarian Horntail.", green, harryPotter, "norwegianRidgeback.png"));
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}