# Car Dealer App 🚗

A Java desktop application for managing a car dealership system.

## 🔧 Tech Stack
- **Java 17**
- **JavaFX** (for GUI)
- **MySQL** (as the database)
- **Maven** (as the build tool)

---

## 📁 Project Structure

```
car-dealer-app/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/shamseddin/
│   │   │       ├── dao/                 # AdminDAO, VehicleDAO + JDBC impls
│   │   │       ├── db/                  # DatabaseConnection
│   │   │       ├── model/               # Domain models and enums
│   │   │       └── utils/               # PasswordHasher
│   │   └── resources/
│   │       ├── db.properties            # JDBC URL, user, password
│   │       └── schema.sql               # DB + tables DDL
│   └── test/
│       └── java/                        # JUnit tests and TestDatabase utility
├── pom.xml                              # Maven configuration
└── README.md                            # Project documentation
```

---

## 🛠 Requirements
- Java 17+
- Maven
- MySQL server

---

## 🚀 Getting Started

1. **Clone the repository**
  ```bash
   git clone https://github.com/AmmarAtGitHub/car-dealer-app.git
   cd car-dealer-app
  ````

2. **Configure the database**

   * Add your DB credentials to `src/main/resources/db.properties`

3. **Compile the project**

   ```bash
   mvn clean compile
   ```

4. **Run the app** (once UI is added)

   ```bash
   mvn exec:java
   ```

---

## 📚 Roadmap / To-Do
- [x] Data model design & implementation
- [x] JDBC connection and configuration via `db.properties`
- [x] Admin and Vehicle DAOs (JDBC)
- [x] Database schema and basic tests
- [x] Service layer (business logic)
- [x] Additional DAOs (Customer, Documents, Photos, Transactions)
- [ ] JavaFX UI (desktop app)
- [ ] Integration tests and sample data seeds


---

## 🤝 Contributing

This project is part of my Java learning journey. Suggestions and pull requests are welcome as I continue to improve it!
