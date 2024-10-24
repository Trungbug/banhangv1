create database banhang;
use banhang;

create table user (
                      id INT PRIMARY KEY auto_increment,
                      fullname varchar(100) default '',
                      phone_number varchar(10) not null ,
                      address varchar(200) default '',
                      password varchar(100) not null ,
                      creat_at datetime,
                      update_at datetime,
                      is_active tinyInt(1) default 1,
                      date_of_birth date,
                      facebook_account_id int default 0,
                      google_account_id int default 0
);
create table tokens(
                       id int primary key auto_increment,
                       token varchar(225) unique not null ,
                       token_type varchar(50 ) not null ,
                       expiration_date datetime,
                       revoked tinyint(1) not null ,
                       expired tinyint(1) not null ,
                       user_id int ,
                       foreign key (user_id) references user(id)
);
CREATE TABLE social_accounts (
                                 id INT PRIMARY KEY AUTO_INCREMENT,
                                 provider VARCHAR(20) NOT NULL COMMENT 'Tên nhà social network',
                                 provider_id VARCHAR(50) NOT NULL,
                                 email VARCHAR(150) NOT NULL COMMENT 'Email tài khoản',
                                 name VARCHAR(100) NOT NULL COMMENT 'Tên người dùng',
                                 user_id INT,
                                 FOREIGN KEY (user_id) REFERENCES user(id) -- Giả sử bảng "users" tồn tại
);
-- Bảng danh mục sản phẩm (Category)
CREATE TABLE categories (
                            id INT PRIMARY KEY AUTO_INCREMENT,
                            name VARCHAR(100) NOT NULL DEFAULT '' COMMENT 'Tên danh mục, vd: đồ điện tử'
);

-- Bảng chứa sản phẩm (Product)
CREATE TABLE products (
                          id INT PRIMARY KEY AUTO_INCREMENT,
                          name VARCHAR(350) NOT NULL COMMENT 'Tên sản phẩm',
                          price FLOAT NOT NULL CHECK(price >= 0),  -- Kiểm tra giá không âm
                          thumbnail VARCHAR(300) DEFAULT '',       -- Đường dẫn hình ảnh sản phẩm
                          description LONGTEXT DEFAULT '',         -- Mô tả sản phẩm
                          created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,  -- Thời gian tạo sản phẩm
                          updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP, -- Thời gian cập nhật sản phẩm
                          category_id INT,
                          FOREIGN KEY (category_id) REFERENCES categories(id) -- Khóa ngoại tham chiếu đến bảng categories
);

ALTER TABLE products
    MODIFY COLUMN created_at DATETIME NOT NULL DEFAULT NOW(),
    MODIFY COLUMN updated_at DATETIME NOT NULL DEFAULT NOW() ON UPDATE CURRENT_TIMESTAMP;


CREATE TABLE roles (
                       id INT PRIMARY KEY,
                       name VARCHAR(20) NOT NULL
);
ALTER TABLE user ADD COLUMN role_id INT;
ALTER TABLE user ADD FOREIGN KEY (role_id) REFERENCES roles(id);

CREATE TABLE orders (
                        id INT PRIMARY KEY AUTO_INCREMENT,
                        user_id INT,
                        FOREIGN KEY (user_id) REFERENCES user(id),
                        fullname VARCHAR(100) DEFAULT '',
                        email VARCHAR(100) DEFAULT '',
                        phone_number VARCHAR(20) NOT NULL,
                        address VARCHAR(200) NOT NULL,
                        note VARCHAR(100) DEFAULT '',
                        order_date DATETIME DEFAULT CURRENT_TIMESTAMP,
                        status VARCHAR(20),
                        total_money FLOAT CHECK(total_money >= 0)
);

ALTER TABLE orders ADD COLUMN `shipping_method` VARCHAR(100);
ALTER TABLE orders ADD COLUMN `shipping_address` VARCHAR(200);
ALTER TABLE orders ADD COLUMN `shipping_date` DATE;
ALTER TABLE orders ADD COLUMN `tracking_number` VARCHAR(100);
ALTER TABLE orders ADD COLUMN `payment_method` VARCHAR(100);
alter table orders add column active tinyint(1);
alter table orders modify column status enum('pending','processing','shipped','delivered','canceled');

CREATE TABLE order_details (
                               id INT PRIMARY KEY AUTO_INCREMENT,
                               order_id INT,
                               FOREIGN KEY (order_id) REFERENCES orders(id),
                               product_id INT,
                               FOREIGN KEY (product_id) REFERENCES products(id),
                               price FLOAT CHECK (price >= 0),
                               number_of_products INT CHECK (number_of_products > 0),
                               total_money FLOAT CHECK (total_money >= 0),
                               color VARCHAR(20) DEFAULT ''
);
CREATE TABLE product_images (
                                id INT PRIMARY KEY AUTO_INCREMENT,
                                product_id INT,
                                FOREIGN KEY (product_id) REFERENCES products(id),
                                CONSTRAINT fk_product_images_product_id
                                    FOREIGN KEY (product_id) REFERENCES products(id) ON DELETE CASCADE,
                                images_url varchar(300)
                            );

