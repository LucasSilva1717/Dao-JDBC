# 🏦 Transaction Management - Java & JDBC

This project demonstrates the implementation of a financial transaction management system using **Java SE** and **JDBC**. The primary focus is to ensure data consistency in complex operations by using manual transaction controls.

## ✨ Features

### 1. Transactional Integrity
*   Implementation of **ACID properties** (Atomicity, Consistency, Isolation, and Durability).
*   Manual **commit and rollback** control to ensure transfers are only completed if all steps succeed.

### 2. JDBC Core Implementation
*   Direct database connection via Driver Manager.
*   Use of `PreparedStatement` to prevent **SQL Injection** attacks.
*   Efficient resource management (proper handling of Connection and Statement closures).

### 3. Error Handling
*   SQL exception handling designed to trigger automatic rollbacks in case of failures during a transaction.
*   Informative logs regarding the operation status.

### 4. Database Integration
*   Utilizes **PostgreSQL** for data persistence.
*   Table modeling focused on accounts and transaction logging.

---

## 🛠 Tech Stack

*   **Java SE**: Core programming language.
*   **JDBC (Java Database Connectivity)**: API for database interaction.
*   **PostgreSQL**: Relational database management system.
*   **Maven**: Dependency management and build tool.

---

## 🚀 How to Run the Project

1.  **Clone the Repository:**
    ```bash
    git clone https://github.com
    ```

2.  **Configure the Database:**
    Create a database in your PostgreSQL instance and update your connection class (e.g., `DB.java`) with your credentials:
    - URL: `jdbc:postgresql://localhost:5432/your_database`
    - User: `your_username`
    - Password: `your_password`

3.  **Database Schema:**
    Ensure you have the necessary tables created. Basic example:
    ```sql
    CREATE TABLE accounts (
        id SERIAL PRIMARY KEY,
        name VARCHAR(100),
        balance DECIMAL(10, 2)
    );
    ```

4.  **Run the Application:**
    Execute the main class through your IDE or via terminal:
    ```bash
    mvn compile exec:java -Dexec.mainClass="your.package.name.Main"
    ```

---

## 🤝 Contributing

Feel free to fork this project, open issues, or submit pull requests with improvements, such as implementing different transaction isolation levels.

---
Developed by https://github.com/LucasSilva1717
