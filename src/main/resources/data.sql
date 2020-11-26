insert into ACCOUNT (USER_NAME, EMAIL, PASSWORD, PHONE, STATUS) values ('seller', 'seller@gmail.com', 'test', '7212345678', 'ACTIVE');
insert into ACCOUNT (USER_NAME, EMAIL, PASSWORD, PHONE, STATUS) values ('buyer', 'buyer@gmail.com', 'test', '9712345678', 'ACTIVE');

insert into PRODUCT_CATEGORY (NAME, DESCRIPTION) values ('MOBILE', 'Mobile description');
insert into PRODUCT_CATEGORY (NAME, DESCRIPTION) values ('TV', 'Tv description');
insert into PRODUCT_CATEGORY (NAME, DESCRIPTION) values ('LAPTOP', 'Laptop description');

insert into PRODUCT (PRODUCT_ID ,AVAILABLE_ITEM_COUNT ,DESCRIPTION ,NAME ,PRICE ,CATEGORY ,SELLER_ID )
values ('7cfb4719-888f-4244-8984-dc9d1ef83242', 50, 'Mobile Desc', 'Samsung', 50000, 'MOBILE', 'seller');
insert into PRODUCT (PRODUCT_ID ,AVAILABLE_ITEM_COUNT ,DESCRIPTION ,NAME ,PRICE ,CATEGORY ,SELLER_ID )
values ('7cfb4719-888f-4244-8984-dc9d1ef83243', 50, 'Mobile Desc', 'Motorola', 40000, 'MOBILE', 'seller');
insert into PRODUCT (PRODUCT_ID ,AVAILABLE_ITEM_COUNT ,DESCRIPTION ,NAME ,PRICE ,CATEGORY ,SELLER_ID )
values ('a0f7ddaf-871a-4817-9be4-7ca9096d7253', 50, 'TV Desc', 'Samsung', 70000, 'TV', 'seller');