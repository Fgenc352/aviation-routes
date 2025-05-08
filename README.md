# Aviation Routes

A full-stack application to manage Locations, Transportations and compute valid travel Routes between them.  
Built with:
- **Backend:** Spring Boot, Spring Data JPA, PostgreSQL, validation & global exception handling, CORS, Swagger/OpenAPI
- **Frontend:** React, React Router, Axios

---

## 🚀 Features

- **Locations CRUD**  
  Create, Read, Update, Delete airports/cities with name, country, city, unique code  
  — Filter by Name/Country/City/Code dropdowns

- **Transportations CRUD**  
  Define connections between locations: origin → destination, type (FLIGHT, BUS, SUBWAY, UBER), operating days (1-7)  
  — Client & server validation: no empty fields, no same origin/destination, no duplicates

- **Route Finder**  
  Compute all valid routes (max 3 legs, exactly one FLIGHT, at most one pre-flight and one post-flight transfer)  
  — Filter by origin, destination, date (matches operating days)  
  — “Available Routes” list & side-panel step-by-step detail

- **API Documentation**  
  Swagger UI available at `http://localhost:8080/swagger-ui.html`

---

## 🔧 Prerequisites

- Java 17
- Maven 3.6+
- Node.js 16+ & npm
- PostgreSQL

---

## 🛠️ Backend Setup

1. **Configure Database**  
   Edit `src/main/resources/application.properties`:
   ```properties
   spring.datasource.url=jdbc:postgresql://localhost:5432/aviation
   spring.datasource.username=…
   spring.datasource.password=…
   spring.jpa.hibernate.ddl-auto=update
   ```

2. **Build & Run**  
   ```bash
   cd backend
   mvn clean package
   mvn spring-boot:run
   ```
   The API will listen on `http://localhost:8080/api`

3. **Swagger / OpenAPI**  
   Visit: `http://localhost:8080/swagger-ui.html`

---

## 🛠️ Frontend Setup

1. **Install & Run**  
   ```bash
   cd frontend
   npm install
   npm start
   ```
   Opens at `http://localhost:3000`

2. **API Base URL**  
   By default, Axios uses `http://localhost:8080/api`. Adjust in `src/services/api.js` if needed.

---

## 📚 API Endpoints

### Locations
| Method | Path                   | Description                   |
| ------ | ---------------------- | ----------------------------- |
| GET    | `/locations`           | List all Locations            |
| POST   | `/locations`           | Create a Location             |
| PUT    | `/locations/{id}`      | Update a Location by ID       |
| DELETE | `/locations/{id}`      | Delete a Location (cascade)   |

### Transportations
| Method | Path                            | Description                     |
| ------ | ------------------------------- | ------------------------------- |
| GET    | `/transportations`              | List all Transportations        |
| POST   | `/transportations`              | Create a Transportation         |
| PUT    | `/transportations/{id}`         | Update by ID                    |
| DELETE | `/transportations/{id}`         | Delete by ID                    |

### Routes
| Method | Path                            | Query Params                              | Description                                    |
| ------ | ------------------------------- | ------------------------------------------ | ---------------------------------------------- |
| GET    | `/routes`                       | `origin` (optional), `destination` (opt.), `date` (opt., `YYYY-MM-DD`) | Find all valid routes according to rules      |

---

## 🧩 Business Rules for Routes

1. **Max 3 legs**  
2. **Exactly one FLIGHT**  
3. **At most one “pre-flight” transfer** (before the flight)  
4. **At most one “post-flight” transfer** (after the flight)  

Valid examples:
- UBER ➡ FLIGHT ➡ BUS  
- FLIGHT ➡ BUS  
- UBER ➡ FLIGHT  

Invalid examples:
- UBER ➡ BUS (no flight)  
- FLIGHT ➡ FLIGHT (two flights)  
- UBER ➡ BUS ➡ FLIGHT (two pre-flight transfers)  

---

## 🎨 Project Structure

```
aviation-routes/
├── backend/           # Spring Boot app
│   ├── src/main/java/com/aviation/routes
│   │   ├── controller
│   │   ├── dto
│   │   ├── entity
│   │   ├── exception
│   │   ├── mapper
│   │   ├── repository
│   │   ├── service
│   │   └── config
│   └── resources
│       ├── application.properties
│       └── data.sql
└── frontend/          # React app
    ├── src/
    │   ├── components  # Header, Sidebar
    │   ├── pages       # Locations.jsx, Transportations.jsx, Routes.jsx
    │   ├── services    # Axios API wrappers
    │   ├── App.js
    │   └── App.css
    └── public/
```

---

## 📦 Postman Collection

See `postman_collection.json` (included) for example requests to all endpoints:
- Locations: 5 examples (create/update/delete/list)
- Transportations: 5 examples
- Routes: 1 search queries

---

## 🤝 Contributing

1. Fork & clone  
2. Create feature branch  
3. Commit, push & open PR  

---

## 📝 License

This project is open-source under the MIT License.  
