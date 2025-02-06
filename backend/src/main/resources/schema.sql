-- Users Table
CREATE TABLE users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    email VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(120) NOT NULL,
    first_name VARCHAR(50),
    last_name VARCHAR(50),
    phone VARCHAR(20),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- Products Table
CREATE TABLE products (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    description TEXT,
    price DECIMAL(10,2) NOT NULL,
    stock_quantity INT NOT NULL,
    image_url VARCHAR(255),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    INDEX idx_product_name (name),
    INDEX idx_product_stock (stock_quantity)
);

-- Product Categories Table
CREATE TABLE product_categories (
    product_id BIGINT NOT NULL,
    category VARCHAR(50) NOT NULL,
    PRIMARY KEY (product_id, category),
    FOREIGN KEY (product_id) REFERENCES products(id) ON DELETE CASCADE
);

-- Product Tags Table
CREATE TABLE product_tags (
    product_id BIGINT NOT NULL,
    tag VARCHAR(50) NOT NULL,
    PRIMARY KEY (product_id, tag),
    FOREIGN KEY (product_id) REFERENCES products(id) ON DELETE CASCADE
);

-- Orders Table
CREATE TABLE orders (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL,
    total_amount DECIMAL(10,2) NOT NULL,
    status VARCHAR(20) NOT NULL,
    shipping_address TEXT NOT NULL,
    tracking_number VARCHAR(100),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id),
    INDEX idx_order_user (user_id),
    INDEX idx_order_status (status),
    INDEX idx_order_created (created_at)
);

-- Order Items Table
CREATE TABLE order_items (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    order_id BIGINT NOT NULL,
    product_id BIGINT NOT NULL,
    quantity INT NOT NULL,
    price DECIMAL(10,2) NOT NULL,
    FOREIGN KEY (order_id) REFERENCES orders(id) ON DELETE CASCADE,
    FOREIGN KEY (product_id) REFERENCES products(id)
);

-- Contents Table
CREATE TABLE contents (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    content TEXT,
    type VARCHAR(20) NOT NULL,
    image_url VARCHAR(255),
    published BOOLEAN DEFAULT FALSE,
    publish_date TIMESTAMP,
    author_id BIGINT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (author_id) REFERENCES users(id)
);

-- Marketing Campaigns Table
CREATE TABLE marketing_campaigns (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    description TEXT,
    start_date TIMESTAMP NOT NULL,
    end_date TIMESTAMP NOT NULL,
    type VARCHAR(30) NOT NULL,
    discount_amount DECIMAL(10,2),
    discount_percentage INT,
    coupon_code VARCHAR(50),
    usage_limit INT,
    current_usage INT DEFAULT 0,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- Campaign Products Table
CREATE TABLE campaign_products (
    campaign_id BIGINT NOT NULL,
    product_id BIGINT NOT NULL,
    PRIMARY KEY (campaign_id, product_id),
    FOREIGN KEY (campaign_id) REFERENCES marketing_campaigns(id) ON DELETE CASCADE,
    FOREIGN KEY (product_id) REFERENCES products(id) ON DELETE CASCADE
);

-- Statistics Table
CREATE TABLE statistics (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    date TIMESTAMP NOT NULL,
    total_orders INT,
    total_revenue DECIMAL(15,2),
    total_users INT,
    active_users INT,
    total_products INT,
    low_stock_products INT,
    average_order_value DECIMAL(10,2),
    total_campaigns INT,
    active_campaigns INT
);

-- User Feedback Table
CREATE TABLE user_feedback (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT,
    feedback_type VARCHAR(50) NOT NULL,
    comment TEXT,
    rating INT CHECK (rating >= 1 AND rating <= 5),
    page_url VARCHAR(255),
    browser_info VARCHAR(255),
    device_info VARCHAR(255),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id)
);
