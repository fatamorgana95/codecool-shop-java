DROP TABLE IF EXISTS product CASCADE;
DROP TABLE IF EXISTS product_category CASCADE;


CREATE TABLE Product(
                        id SERIAL,
                        name VARCHAR(255),
                        description VARCHAR (255),
                        default_price FLOAT,
                        default_currency VARCHAR (7),
                        product_category_id INT,
                        supplier VARCHAR (255),
                        image VARCHAR(255),
                        PRIMARY KEY (id)
);

CREATE TABLE Product_Category(
                                 id SERIAL,
                                 name VARCHAR(255),
                                 description VARCHAR(255),
                                 department VARCHAR (255),
                                 PRIMARY KEY (id)
);


ALTER TABLE Product
    ADD CONSTRAINT fk_product_product_category_id FOREIGN KEY (product_category_id) REFERENCES product_category(id);


INSERT INTO product_category (name, department, description) VALUES ('Red', 'Colour', '');
INSERT INTO product_category (name, department, description) VALUES ('Brown', 'Colour', '');
INSERT INTO product_category (name, department, description) VALUES ('Green', 'Colour', '');
INSERT INTO product_category (name, department, description) VALUES ('Black', 'Colour', '');
INSERT INTO product_category (name, department, description) VALUES ('White', 'Colour', '');


INSERT INTO product (name, default_price,default_currency,description,product_category_id,supplier, image) VALUES ('7 Headed Dragon', 1500, 'HUF', 'They say seven is better than one! Grab this excellent dragon if you know how to handle more than one.', 3, 'magyarNepMesek', 'sevenheadeddragon.png');
INSERT INTO product (name, default_price,default_currency,description,product_category_id,supplier, image) VALUES ('Susu', 3250, 'HUF', 'Hungarian one-headed dragon, for an excellent price. It is kind and would probably never bite you.', 3, 'susu', 'susu.png');
INSERT INTO product (name, default_price,default_currency,description,product_category_id,supplier, image) VALUES ('Toothless', 2323, 'HUF', 'He is nice, friendly but most importantly dangerous! If you want to train your dragon, Toothles is the best partner for you.', 4, 'howToTrainYourDragon', 'toothless.jpg');
INSERT INTO product (name, default_price,default_currency,description,product_category_id,supplier, image) VALUES ('Mushu', 1, 'HUF', 'This chinese dragon, may talk a lot but he is kind and friendly. It certainly helps you with fighting the enemy.', 1, 'mulan', 'mushu.png');
INSERT INTO product (name, default_price,default_currency,description,product_category_id,supplier, image) VALUES ('Drogon', 15777, 'HUF', 'It is one of the most fearful dragon out there. I double dare you to say "DRACARYS" close to it.', 2, 'gameOfThrones', 'drogon.png');
INSERT INTO product (name, default_price,default_currency,description,product_category_id,supplier, image) VALUES ('Hungarian Horntail', 131234, 'HUF', 'Native to Hungary and is considered to be one of the most dangerous dragon breeds, if not the most dangerous.', 2, 'harryPotter', 'hungarianHorntail.jpg');
INSERT INTO product (name, default_price,default_currency,description,product_category_id,supplier, image) VALUES ('Dragon (Shrek)', 88888, 'HUF', 'The most dangerous female dragon out there. She might bite or kiss you depends on the situation!', 1, 'shrek', 'dragonShrek.png');
INSERT INTO product (name, default_price,default_currency,description,product_category_id,supplier, image) VALUES ('Light Furry', 6668, 'HUF', 'Can walk through the middle of a village without any humans or most dragons sensing her around.', 5, 'howToTrainYourDragon', 'lightFurry.jpg');
INSERT INTO product (name, default_price,default_currency,description,product_category_id,supplier, image) VALUES ('Dronkey (Shrek)', 44444, 'HUF', 'Childish energy and the flying and fire breathing abilities of a dragon, there is never a dull moment with the dronkeys!', 2, 'shrek', 'dronkey.png');
INSERT INTO product (name, default_price,default_currency,description,product_category_id,supplier, image) VALUES ('Norwegian Ridgeback', 10000, 'HUF', 'A species of dragon, its typical habitat is the Northern mountains. It is said to physically resemble a Hungarian Horntail.', 3, 'harryPotter', 'norwegianRidgeback.png');
