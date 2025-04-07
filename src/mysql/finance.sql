CREATE DATABASE finance_db;
USE finance_db;


CREATE TABLE Users (
    user_id INT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(100) NOT NULL,
    password VARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL
);


CREATE TABLE ExpenseCategories (
    category_id INT PRIMARY KEY AUTO_INCREMENT,
    category_name VARCHAR(100) NOT NULL UNIQUE
);


CREATE TABLE Expenses (
    expense_id INT PRIMARY KEY AUTO_INCREMENT,
    user_id INT NOT NULL,
    amount DECIMAL(10, 2) NOT NULL,
    category_id INT NOT NULL,
    date DATE NOT NULL,
    description VARCHAR(255),

    FOREIGN KEY (user_id) REFERENCES Users(user_id) ON DELETE CASCADE,
    FOREIGN KEY (category_id) REFERENCES ExpenseCategories(category_id)
);
-- Insert sample users
INSERT INTO Users (username, password, email)
VALUES
('john_doe', 'pass123', 'john@example.com'),
('alice_wonder', 'alicepass', 'alice@example.com');

-- Insert sample categories
INSERT INTO ExpenseCategories (category_name)
VALUES
('Food'),
('Transport'),
('Entertainment'),
('Utilities');

-- Insert sample expenses
INSERT INTO Expenses (user_id, amount, category_id, date, description)
VALUES
(1, 150.00, 1, '2025-04-01', 'Groceries at supermarket'),
(1, 50.00, 2, '2025-04-02', 'Bus ticket'),
(2, 200.00, 3, '2025-04-03', 'Movie tickets'),
(2, 120.00, 4, '2025-04-04', 'Electricity bill');
