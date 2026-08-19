# Department Service

A separate Spring Boot application extracted from the employee-management monolith. This is a **partial** migration: Department REST APIs have moved; Employee still depends on the old in-process Department model. The split is **not** complete.

## Stack

- Java 21
- Spring Boot 3.5.4
- Maven
- Local MySQL database `department_db`
- **No Docker**

## Ports and data

| Process | Port | Database |
|---|---|---|
| Monolith | 8080 | `employee_management` |
| Department Service | 8081 | `department_db` |

Department Service owns Department API data in `department_db`.

The monolith still runs on port 8080. Its `/api/departments` endpoints are proxied over HTTP to Department Service (`http://localhost:8081`).

Employee still uses the old Department entity and the `employee_management.departments` table. Removing the Employee `@ManyToOne` Department relationship is a **later** migration phase.

## Architecture

```
Client
  |
  v
Monolith :8080
  |  /api/departments  ----HTTP---->  Department Service :8081
  |                                      |
  |                                      v
  |                                   department_db
  |
  |  /api/employees (unchanged)
  v
employee_management.departments   <-- Employee @ManyToOne Department (still here)
```

## Required environment

Set `DEPARTMENT_DB_PASSWORD` before running Department Service. There is no default password.

Optional overrides: `DEPARTMENT_DB_URL`, `DEPARTMENT_DB_USERNAME` (default `employee_user`).

Create the local database yourself (no Docker):

```sql
CREATE DATABASE department_db;
GRANT ALL PRIVILEGES ON department_db.* TO 'employee_user'@'localhost';
FLUSH PRIVILEGES;
```

## Commands (from the repository root)

Test:

```bash
mvn -f department-service/pom.xml test
```

Run:

```bash
mvn -f department-service/pom.xml spring-boot:run
```

Tests use H2 and do not need MySQL. Runtime uses local `department_db` on port 8081.

Start the monolith separately on 8080 if you need the HTTP proxy.

## Later phase (not done)

Do not remove the monolith Department entity, table, or `DepartmentService` yet. Employee still depends on them. The next phase is to replace the Employee `@ManyToOne` to Department with an HTTP lookup and a plain `departmentId`.
