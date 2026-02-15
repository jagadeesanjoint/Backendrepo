-- Progressive Project 1 - Seed data (accounts; users are seeded via DataLoader with BCrypt)
INSERT INTO accounts (id, holder_name, balance, status, version) VALUES
(1, 'Alice Smith', 1500.00, 'ACTIVE', 0),
(2, 'Bob Jones', 3200.50, 'ACTIVE', 0),
(3, 'Carol White', 875.25, 'ACTIVE', 0)
ON DUPLICATE KEY UPDATE holder_name = VALUES(holder_name), balance = VALUES(balance), status = VALUES(status);
