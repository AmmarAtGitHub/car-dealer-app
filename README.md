# Car Dealer App 🚗

A **Java backend system** for managing a car dealership. Designed with clean architecture principles emphasizing separation of concerns, modularity, and scalability.

---

## 🏗️ Project Architecture

This is a **backend-only** application intentionally designed without a desktop UI. The backend can be integrated with any frontend technology:
- 🌐 Web UI (React, Vue, Angular)
- 📱 Mobile app (Android, iOS)
- 💻 Desktop app (JavaFX, Swing, or any other framework)
- 🖥️ REST API for third-party clients

This modular approach ensures:
- **Separation of Concerns:** Business logic remains independent from UI implementation
- **Scalability:** Backend can serve multiple client types simultaneously
- **Maintainability:** Changes to UI don't affect core business logic
- **Extensibility:** Easy to add new clients or integrate with external systems

---

## 🔧 Tech Stack
- **Java 17**
- **MySQL** (database)
- **Maven** (build tool)
- **JUnit 5** (testing)
- **jBCrypt** (password hashing & security)
- **SLF4J + Logback** (logging)

---

## 📁 Project Structure

```
car-dealer-app/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/shamseddin/
│   │   │       ├── dao/                 # Data Access Objects (JDBC implementations)
│   │   │       ├── db/                  # DatabaseConnection (connection pooling)
│   │   │       ├── model/               # Domain models and enums
│   │   │       ├── service/             # Business logic layer
│   │   │       └── utils/               # PasswordHasher, utilities
│   │   └── resources/
│   │       ├── db.properties            # Database configuration (JDBC URL, user, password)
│   │       └── schema.sql               # Database schema and DDL
│   └── test/
│       └── java/                        # JUnit tests
├── pom.xml                              # Maven configuration
└── README.md                            # Project documentation
```

---

## 🛠️ Requirements
- **Java 17+**
- **Maven 3.6+**
- **MySQL 8.0+**

---

## 🚀 Getting Started

### 1. Clone the Repository
```bash
git clone https://github.com/AmmarAtGitHub/car-dealer-app.git
cd car-dealer-app
```

### 2. Configure the Database
- Open `src/main/resources/db.properties`
- Add your MySQL credentials:
  ```properties
  db.url=jdbc:mysql://localhost:3306/car_dealer_db
  db.user=your_mysql_user
  db.password=your_mysql_password
  ```

### 3. Create the Database Schema
- Run the SQL script in `src/main/resources/schema.sql` in your MySQL server to create tables and initial structure

### 4. Compile the Project
```bash
mvn clean compile
```

### 5. Run Tests
```bash
mvn test
```

---

## 📚 Core Components

### Data Access Layer (DAO)
- `AdminDAO` — Manage admin users and authentication
- `VehicleDAO` — Handle vehicle inventory operations
- `CustomerDAO` — Manage customer information
- `TransactionDAO` — Record purchase and sale transactions
- `DocumentDAO` — Manage vehicle documents (titles, registrations)
- `PhotoDAO` — Store and retrieve vehicle photos

### Business Logic Layer (Service)
- `AdminService` — Admin registration, login, and account management
- `VehicleService` — Vehicle CRUD operations and search
- `CustomerService` — Customer management
- `TransactionService` — Process vehicle purchases and sales
- `InventoryService` — Manage and analyze inventory

### Models
- `Admin`, `Customer`, `Vehicle`
- `Transaction`, `Inventory`, `Document`, `Photo`
- Enums: `VehicleStatus`, `TransactionType`, `DocumentType`

---

## 🔐 Security
- All passwords are hashed using **jBCrypt** (industry-standard secure hashing)
- Database credentials are externalized in `db.properties` (not committed to repo)
- JDBC prepared statements prevent SQL injection

---

## 📊 Roadmap / To-Do

### ✅ Completed
- [x] Data model design & implementation
- [x] JDBC connection and configuration via `db.properties`
- [x] Admin and Vehicle DAOs (JDBC implementations)
- [x] Database schema and basic tests
- [x] Service layer (business logic)
- [x] Additional DAOs (Customer, Documents, Photos, Transactions)
- [x] Backend-only architecture with professional separation of concerns

### 🚀 Future possible Enhancements
- [ ] REST API layer (Spring Boot or similar)
- [ ] Integration tests and sample data seeds
- [ ] Frontend integration examples (optional)

---

## 🤝 Contributing

This project is part of my Java learning journey. It demonstrates:
- Clean architecture principles
- JDBC and database design
- Service-oriented architecture
- Security best practices
- Professional Java development patterns

Suggestions and discussions are welcome!

---

## 📝 License

This project is open source and is part of my Java learning journey.
