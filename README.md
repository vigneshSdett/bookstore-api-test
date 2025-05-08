# 📚 Bookstore API Automation Project

This project automates the testing of a FastAPI-based Bookstore backend using **Java, RestAssured, Cucumber**, and **Extent Reports**.

## 🚀 Tech Stack

| Layer             | Tech Used                  |
|------------------|----------------------------|
| Backend API      | FastAPI (Python)           |
| API Testing      | Java + RestAssured         |
| Test Framework   | Cucumber (BDD)             |
| Reporting        | Extent Reports (HTML)      |
| Build Tool       | Maven                      |
| CI/CD            | GitHub Actions (Optional)  |

## 📁 Project Structure

```
bookstore-api-test/
├── bookstore-main/             # FastAPI backend
│   ├── main.py                 # FastAPI app entry
│   └── requirements.txt        # Python dependencies
├── src/
│   └── main/java/com/bookStore
│       ├── base/               # POJOs for request payloads
│       ├── config/             # Constants and configs
│       ├── service/            # API service layers
│       ├── utils/              # Reporting & REST utilities
│   └── test/java/com/bookstore
│       ├── stepdefs/           # Cucumber step definitions
│       ├── hooks/              # Cucumber hooks (if any)
├── features/
│   └── BookManagement.feature  # API test scenarios
├── pom.xml                     # Maven config
└── README.md                   # You are here!
```

## ⚙️ Setup & Execution

### 1. Start the FastAPI Backend

```bash
cd bookstore-main
pip install -r requirements.txt
uvicorn main:app --reload
```

📌 The server runs at `http://127.0.0.1:8000`.

### 2. Run the API Tests

```bash
mvn clean verify
```

🧪 This runs all scenarios from your Cucumber `.feature` files and generates Extent HTML reports.

## ✅ Test Coverage

| Feature           | Method | Endpoint         | Status |
|------------------|--------|------------------|--------|
| User Signup       | POST   | /signup          | ✅     |
| User Login        | POST   | /login           | ✅     |
| Create Book       | POST   | /books/          | ✅     |
| Fetch All Books   | GET    | /books/          | ✅     |
| Fetch Book by ID  | GET    | /books/{id}      | ✅     |
| Update Book       | PUT    | /books/{id}      | ✅     |
| Delete Book       | DELETE | /books/{id}      | ✅     |
| Validation Errors | ALL    | All Endpoints    | ✅     |

## 📊 Reporting

- After execution, view the report at:
  ```
  target/cucumber-reports/ExtentReport.html
  ```
- Report includes:
  - Scenario-wise status
  - Expected vs Actual response
  - Response payloads

## 🔁 CI/CD (Optional)

You can configure **GitHub Actions** or any CI tool to:
- Install dependencies
- Run tests
- Publish reports

Example GitHub Actions steps:
```yaml
- name: Set up JDK 17
  uses: actions/setup-java@v3
  with:
    java-version: '17'

- name: Run Maven tests
  run: mvn clean test
```

## 👨‍💻 Author

**Vignesh Arun Kumar**  
Senior Test Engineer | API Automation | FastAPI | Java + Cucumber + RestAssured
