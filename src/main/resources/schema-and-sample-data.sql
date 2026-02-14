-- ============================================================
-- MTS Database: username, account, transaction_log
-- Run this in MySQL Workbench (database: mts). Adjust username/password as needed.
-- ============================================================

-- Create database if needed (optional; you may already have mts)
-- CREATE DATABASE IF NOT EXISTS mts;
-- USE mts;

-- ------------------------------------------------------------
-- 1) username table (3 columns: id, username, password)
-- Only these users can log in. id must match account.id (1-to-1).
-- ------------------------------------------------------------
CREATE TABLE IF NOT EXISTS username (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL
);

-- Sample entries (add your own username/password here)
INSERT INTO username (id, username, password) VALUES
(1, 'alice', 'alice123'),
(2, 'bob', 'bob456'),
(3, 'carol', 'carol789')
ON DUPLICATE KEY UPDATE username = VALUES(username), password = VALUES(password);

-- ------------------------------------------------------------
-- 2) account table
-- id must align with username.id for the user who owns this account.
-- ------------------------------------------------------------
CREATE TABLE IF NOT EXISTS account (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    holder_name VARCHAR(225) NOT NULL,
    balance DECIMAL(18,2) NOT NULL,
    status VARCHAR(20) NOT NULL,
    version INT DEFAULT 0,
    last_updated TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- Sample entries (id 1 = alice, id 2 = bob, id 3 = carol)
INSERT INTO account (id, holder_name, balance, status, version) VALUES
(1, 'Alice Smith', 1500.00, 'ACTIVE', 0),
(2, 'Bob Jones', 3200.50, 'ACTIVE', 0),
(3, 'Carol White', 875.25, 'ACTIVE', 0)
ON DUPLICATE KEY UPDATE holder_name = VALUES(holder_name), balance = VALUES(balance), status = VALUES(status);

-- ------------------------------------------------------------
-- 3) transaction_log table - DO NOT CREATE MANUALLY
-- The application creates this table via JPA (spring.jpa.hibernate.ddl-auto=update).
-- Rows are inserted automatically when you perform transfers via the API:
--   - Account balances are updated in the account table
--   - Each transfer is recorded in transaction_log
--   - Idempotency key is generated automatically by the server (unique per transfer)
-- Dashboard balance and History list reflect these API-driven updates.
-- ------------------------------------------------------------

-- ============================================================
-- Summary
-- ============================================================
-- Login: use username/password from username table (e.g. alice / alice123).
-- User id 1 -> account id 1 -> dashboard shows that account's balance; history shows its transactions.
-- Run only the username and account sections above. transaction_log is created and filled by the app when you transfer via the UI/API.
-- ============================================================
