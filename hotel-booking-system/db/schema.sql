CREATE DATABASE IF NOT EXISTS hotel_db;
USE hotel_db;

CREATE TABLE IF NOT EXISTS users (
    id INT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    full_name VARCHAR(100) NOT NULL,
    role ENUM('ADMIN', 'CUSTOMER') NOT NULL,
    email VARCHAR(100) NOT NULL
);

CREATE TABLE IF NOT EXISTS rooms (
    id INT PRIMARY KEY AUTO_INCREMENT,
    room_number VARCHAR(10) NOT NULL UNIQUE,
    type VARCHAR(50) NOT NULL,
    price DECIMAL(10, 2) NOT NULL,
    status ENUM('AVAILABLE', 'MAINTENANCE') DEFAULT 'AVAILABLE',
    description TEXT,
    image_url VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS bookings (
    id INT PRIMARY KEY AUTO_INCREMENT,
    user_id INT NOT NULL,
    room_id INT NOT NULL,
    check_in DATE NOT NULL,
    check_out DATE NOT NULL,
    total_price DECIMAL(10, 2) NOT NULL,
    status ENUM('CONFIRMED', 'CANCELLED', 'CHECKED_IN', 'CHECKED_OUT') DEFAULT 'CONFIRMED',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id),
    FOREIGN KEY (room_id) REFERENCES rooms(id)
);

-- Insert a default admin user (password: admin123)
-- Note: In production passwords should be hashed. This is for initial setup.
INSERT INTO users (username, password, full_name, role, email) 
VALUES ('admin', 'admin123', 'System Administrator', 'ADMIN', 'admin@hotel.com')
ON DUPLICATE KEY UPDATE id=id;

-- Insert some dummy rooms
INSERT INTO rooms (room_number, type, price, description, image_url) VALUES 
('101', 'Single', 5000.00, 'Cozy single room with city view', 'single_room.jpg'),
('102', 'Double', 8000.00, 'Spacious double room', 'double_room.jpg'),
('201', 'Suite', 15000.00, 'Luxury suite with balcony', 'suite.jpg')
ON DUPLICATE KEY UPDATE id=id;
