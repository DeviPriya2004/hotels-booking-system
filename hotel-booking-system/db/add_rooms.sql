USE hotel_db;

INSERT INTO rooms (room_number, type, price, description, image_url) VALUES 
('103', 'Single', 5200.00, 'Cozy single room with city view', 'single_room.jpg'),
('104', 'Double', 8500.00, 'Spacious double room with garden view', 'double_room.jpg'),
('105', 'Single', 4800.00, 'Standard single room', 'single_room.jpg'),
('202', 'Suite', 16000.00, 'Luxury suite with panoramic view', 'suite.jpg'),
('203', 'Double', 9000.00, 'Premium double room', 'double_room.jpg'),
('204', 'Single', 5100.00, 'Modern single room near elevator', 'single_room.jpg'),
('301', 'Suite', 18000.00, 'Presidential suite', 'suite.jpg'),
('302', 'Double', 8200.00, 'Double room with extra bed', 'double_room.jpg'),
('303', 'Single', 5500.00, 'Deluxe single room', 'single_room.jpg'),
('304', 'Double', 8800.00, 'Double room with balcony', 'double_room.jpg')
ON DUPLICATE KEY UPDATE id=id;
