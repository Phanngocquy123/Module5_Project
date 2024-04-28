CREATE DATABASE Project_Module5;
USE Project_Module5;

CREATE TABLE users (
    user_id BIGINT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(100) UNIQUE NOT NULL CHECK (LENGTH(username) >= 6),
    email VARCHAR(255) UNIQUE NOT NULL CHECK (email LIKE '%@%.%'),
    fullName VARCHAR(100) NOT NULL,
    status BIT NOT NULL DEFAULT 1, -- true:Active, false:Block
    password VARCHAR(255) NOT NULL,
    avatar VARCHAR(255),
    phone VARCHAR(15) UNIQUE CHECK (phone REGEXP '^[0-9]{10,15}$'),
    address VARCHAR(255) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT NULL
);
SELECT * FROM users;

CREATE TABLE roles (
    role_id BIGINT PRIMARY KEY AUTO_INCREMENT,
    role_name ENUM('ROLE_ADMIN', 'ROLE_USER') NOT NULL
);
INSERT INTO roles (role_name) VALUES 
('ROLE_ADMIN'),
('ROLE_USER')
;
SELECT * FROM roles;

CREATE TABLE user_role(	
	user_id BIGINT NOT NULL,    
	role_id BIGINT NOT NULL,    
	PRIMARY KEY (user_id, role_id),    
	FOREIGN KEY (user_id) REFERENCES users(user_id),    
	FOREIGN KEY (role_id) REFERENCES roles(role_id)
);
SELECT * FROM user_role;

CREATE TABLE categories (
    category_id BIGINT PRIMARY KEY AUTO_INCREMENT,
    category_name VARCHAR(100) NOT NULL,
    description TEXT,
    status BIT NOT NULL DEFAULT 1  -- true:Active, false:Inactive
);
INSERT INTO categories (category_name, description, status) VALUES
    ('Điện tử', 'Các loại hàng điện tử', 1),
    ('Sách', 'Các loại sách', 1),
    ('Áo quần', 'Các loại áo quần', 1)
;


CREATE TABLE products (
    product_id BIGINT PRIMARY KEY AUTO_INCREMENT,
    sku VARCHAR(100) UNIQUE,           -- code sản phẩm
    product_name VARCHAR(100) NOT NULL UNIQUE,
    description TEXT,
    unit_price DECIMAL(10,2),
    stock_quantity INT CHECK (stock_quantity >= 0),
    image VARCHAR(255),
    category_id BIGINT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT NULL,
    FOREIGN KEY (category_id) REFERENCES categories(category_id)
);
SELECT * FROM products;
INSERT INTO products (sku, product_name, description, unit_price, stock_quantity, image, category_id) VALUES
    (SUBSTRING(REPLACE(UUID(), '-', ''), 1, 100), 'Máy tính', 'các loại máy tính', 10.99, 100, 'image1.jpg', 1),
    (SUBSTRING(REPLACE(UUID(), '-', ''), 1, 100), 'Điện thoại', 'các loại điện thoại', 15.99, 150, 'image2.jpg', 1),
    (SUBSTRING(REPLACE(UUID(), '-', ''), 1, 100), 'Nhân văn', 'các loại sách nhân văn', 20.99, 200, 'image3.jpg', 2),
    (SUBSTRING(REPLACE(UUID(), '-', ''), 1, 100), 'Khoa học', 'các loại sách khoa học', 20.99, 200, 'image4.jpg', 2),
    (SUBSTRING(REPLACE(UUID(), '-', ''), 1, 100), 'Thể thao', 'các loại áo quần thể thao', 20.99, 200, 'image5.jpg', 3)
;
   

CREATE TABLE orders (
    order_id BIGINT PRIMARY KEY AUTO_INCREMENT,
    serial_number VARCHAR(100) UNIQUE,
    user_id BIGINT NOT NULL,
    total_price DECIMAL(10,2),
    status ENUM('WAITING', 'CONFIRM', 'DELIVERY', 'SUCCESS', 'CANCEL', 'DENIED') NOT NULL,
    note VARCHAR(100),
    receive_name VARCHAR(100),
    receive_address VARCHAR(255),
    receive_phone VARCHAR(15),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    received_at DATE
);
-- WAITING : Đơn hàng mới chờ xác nhận
-- CONFIRM : Đã xác nhận
-- DELIVERY : Đang giao hàng
-- SUCCESS : Đã giao hàng
-- CANCEL : Đã hủy đơn
-- DENIED: Bị từ chối

CREATE TABLE order_details (
    order_id BIGINT,
    product_id BIGINT,
    name VARCHAR(100),
    unit_price DECIMAL(10,2),
    order_quantity INT CHECK (order_quantity > 0),
    PRIMARY KEY (order_id, product_id),
    FOREIGN KEY (order_id) REFERENCES orders(order_id),
    FOREIGN KEY (product_id) REFERENCES products(product_id)
);

CREATE TABLE shopping_cart (
    shopping_cart_id INT PRIMARY KEY AUTO_INCREMENT,
    product_id BIGINT,
    user_id BIGINT,
    order_quantity INT CHECK (order_quantity > 0),
    FOREIGN KEY (product_id) REFERENCES products(product_id),
    FOREIGN KEY (user_id) REFERENCES users(user_id)
);
SELECT * FROM shopping_cart;

CREATE TABLE addresses (
    address_id BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_id BIGINT,
    full_address VARCHAR(255),
    phone VARCHAR(15),
    receive_name VARCHAR(50),
    FOREIGN KEY (user_id) REFERENCES users(user_id)
);

CREATE TABLE wish_lists (
    wish_list_id BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_id BIGINT,
    product_id BIGINT,
    FOREIGN KEY (user_id) REFERENCES users(user_id),
    FOREIGN KEY (product_id) REFERENCES products(product_id)
);