# How to Run the Money Transfer System

Follow these steps **in order**. No foreign key between `username` and `account` is required — the app matches by **same id** (e.g. `username.id = 1` and `account.id = 1` for the same person).

---

## Step 1: MySQL

1. Start **MySQL** (e.g. MySQL Server / XAMPP / WAMP).
2. Open **MySQL Workbench** (or any client) and connect with your username/password.
3. Create the database and run the setup SQL below.

---

## Step 2: Database and tables (MySQL Workbench)

Run this in MySQL Workbench (use your MySQL user/password when connecting):

```sql
-- Create database
CREATE DATABASE IF NOT EXISTS mts;
USE mts;

-- 1) username table (id, username, password)
CREATE TABLE IF NOT EXISTS username (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL
);

INSERT INTO username (id, username, password) VALUES
(1, 'alice', 'alice123'),
(2, 'bob', 'bob456'),
(3, 'carol', 'carol789')
ON DUPLICATE KEY UPDATE username = VALUES(username), password = VALUES(password);

-- 2) account table — same id as username for each person (id 1 = alice, 2 = bob, 3 = carol)
CREATE TABLE IF NOT EXISTS account (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    holder_name VARCHAR(225) NOT NULL,
    balance DECIMAL(18,2) NOT NULL,
    status VARCHAR(20) NOT NULL,
    version INT DEFAULT 0,
    last_updated TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

INSERT INTO account (id, holder_name, balance, status, version) VALUES
(1, 'Alice Smith', 1500.00, 'ACTIVE', 0),
(2, 'Bob Jones', 3200.50, 'ACTIVE', 0),
(3, 'Carol White', 875.25, 'ACTIVE', 0)
ON DUPLICATE KEY UPDATE holder_name = VALUES(holder_name), balance = VALUES(balance), status = VALUES(status);

-- Optional: link account.id to username.id (foreign key)
-- ALTER TABLE account ADD CONSTRAINT fk_account_username FOREIGN KEY (id) REFERENCES username(id);
```

**Important:** `username.id` and `account.id` must match for the same person (1=alice, 2=bob, 3=carol). A foreign key is optional.

---

## Step 3: Backend (Spring Boot)

1. Set MySQL password in **`src/main/resources/application.properties`**:
   - `spring.datasource.password=YOUR_MYSQL_PASSWORD` (e.g. `Abhi@2005` or your own).

2. From project root run:
   ```bash
   cd C:\Users\kavya\IdeaProjects\moneytransfersystem
   mvn spring-boot:run
   ```
   Or run the main class from your IDE.

3. Wait until you see: **`Tomcat started on port(s): 8585`**.

4. Leave this terminal/run running.

---

## Step 4: Frontend (Angular)

In a **new** terminal:

```bash
cd C:\Users\kavya\IdeaProjects\mts-frontend-final
npm install
ng serve
```

5. Open browser: **http://localhost:4200**

---

## Step 5: Login and dashboard

1. On the login page enter:
   - **Username:** `bob`
   - **Password:** `bob456`
2. Click **Login**.
3. You should be redirected to **http://localhost:4200/dashboard** and see Bob’s details (name, balance, masked account).

---

## If dashboard stays on "Loading..."

1. **Backend running?**  
   Check the backend terminal for errors. You must see `Tomcat started on port(s): 8585`.

2. **MySQL and data**  
   - Database `mts` exists.  
   - Tables `username` and `account` exist.  
   - Same ids: e.g. `username` has id=2 for bob, `account` has id=2 for Bob Jones.

3. **Browser DevTools**  
   - F12 → **Network** tab.  
   - Login, then go to dashboard.  
   - Find the request to **`me`** or **`accounts/2`**.  
   - Check **Status** (200 = OK, 401 = auth problem, 0 = server not reached).

4. **application.properties**  
   - `spring.datasource.url=jdbc:mysql://localhost:3306/mts`  
   - `spring.datasource.username=root` (or your user)  
   - `spring.datasource.password=YOUR_PASSWORD`

---

## Summary

| Step | What to do |
|------|------------|
| 1 | Start MySQL, create DB `mts`, run the SQL above (username + account, same ids). |
| 2 | Set MySQL password in `application.properties`. |
| 3 | Run backend: `mvn spring-boot:run` → wait for port 8585. |
| 4 | Run frontend: `ng serve` → open http://localhost:4200. |
| 5 | Login with bob / bob456 → dashboard shows Bob’s details. |

No foreign key is required; matching `username.id` and `account.id` is enough.
