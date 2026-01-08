CREATE DATABASE cab_db;
CREATE DATABASE user_db;
CREATE DATABASE booking_db;
CREATE DATABASE admin_db;
USE cab_db;
USE user_db;
USE booking_db;
use admin_db;
show tables;
select * from cab;
select * from admin;
Select * from users;
Describe cab;

Select * from admin;
Select * from booking;
Select * from cab;
Select * from users;
INSERT INTO admin (id, username, password) VALUES (1, 'rohit', 'rohit@123');
-- Insert a user
INSERT INTO users (id, username, email, password, role)
VALUES (1, 'vishal', 'vishal@gmail.com', '1234', 'USER');

-- Insert a cab
INSERT INTO cab (id, source, destination, type, kms, fare_per_km)
VALUES (1, 'Pune', 'Mumbai', 'SUV', 175, 18);

INSERT INTO cab (id, source, destination, type, kms, fare_per_km)
VALUES (2, 'Pune', 'Mumbai', 'Sedan', 175, 15);
