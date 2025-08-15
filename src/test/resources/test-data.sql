-- Clear existing data
DELETE FROM photos;
DELETE FROM documents;
DELETE FROM vehicles;
DELETE FROM customers;
DELETE FROM admins;

-- Insert test admin
INSERT INTO admins (username, password_hash) 
VALUES ('admin', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBpwTTiC7Fd3uO'); -- password: admin123

-- Insert test vehicles
INSERT INTO vehicles (vin, brand, model, year, mileage, color, asking_price, status)
VALUES 
    ('WBA4B3C50JF123456', 'BMW', '3 Series', 2018, 35000, 'Black', 28990.00, 'AVAILABLE'),
    ('5YJ3E1EA8KF123456', 'Tesla', 'Model 3', 2019, 22000, 'White', 42990.00, 'AVAILABLE'),
    ('WAUZZZ4GXFN123456', 'Audi', 'A4', 2020, 18000, 'Silver', 34990.00, 'RESERVED');

-- Insert test customers
INSERT INTO customers (first_name, last_name, email, phone, address)
VALUES
    ('John', 'Doe', 'john.doe@example.com', '+1234567890', '123 Main St, Anytown'),
    ('Jane', 'Smith', 'jane.smith@example.com', '+1987654321', '456 Oak Ave, Somewhere');

-- Insert test documents (example for one vehicle)
INSERT INTO documents (title, file_path, upload_date, document_type, file_size, vehicle_id)
VALUES 
    ('Registration', '/documents/WBA4B3C50JF123456/reg.pdf', NOW(), 'REGISTRATION', 1024, 1),
    ('Service Record', '/documents/WBA4B3C50JF123456/service.pdf', NOW(), 'MAINTENANCE_RECORD', 2048, 1);

-- Insert test photos (example for one vehicle)
INSERT INTO photos (photo_path, vehicle_id)
VALUES 
    ('/photos/WBA4B3C50JF123456/1.jpg', 1),
    ('/photos/WBA4B3C50JF123456/2.jpg', 1);
