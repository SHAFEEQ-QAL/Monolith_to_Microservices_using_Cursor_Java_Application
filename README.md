# Employee Management Monolith

A complete practice monolithic REST API built with Java 21, Spring Boot and MySQL. It is intentionally deployed as one application with one shared database so it can later be migrated gradually to microservices.

## Business modules

- Departments
- Employees
- Payroll
- Leave requests
- Notifications

Although the code is grouped by business feature, all modules run in the same Java process, share one Maven project, are deployed together and use the same database. It is therefore a monolith.

## Requirements

- Java 21
- Maven 3.9+ (or run it from Cursor's Maven integration)
- MySQL 8

## Set up MySQL

Create a database and user that match the app defaults:

```sql
CREATE DATABASE employee_management;
CREATE USER 'employee_user'@'localhost' IDENTIFIED BY 'employee_password';
GRANT ALL PRIVILEGES ON employee_management.* TO 'employee_user'@'localhost';
FLUSH PRIVILEGES;
```

To use different credentials, set `DB_URL`, `DB_USERNAME` and `DB_PASSWORD` environment variables.

## Test and run

```bash
mvn test
mvn spring-boot:run
```

The tests use an isolated H2 in-memory database and do not require MySQL.

Health check: `GET http://localhost:8080/actuator/health`

## Suggested API testing order

### 1. Create a department

`POST /api/departments`

```json
{"name":"Engineering","location":"Manchester"}
```

### 2. Create an employee

`POST /api/employees`

```json
{"firstName":"Aisha","lastName":"Khan","email":"aisha@example.com","annualSalary":45000,"departmentId":1}
```

### 3. Create payroll

`POST /api/payroll`

```json
{"employeeId":1,"payDate":"2026-08-31","grossAmount":3750,"deductions":750}
```

### 4. Request leave

`POST /api/leaves`

```json
{"employeeId":1,"startDate":"2026-09-10","endDate":"2026-09-12","reason":"Annual leave"}
```

Approve it with `PATCH /api/leaves/1/decision?status=APPROVED`.

### 5. Create a notification

`POST /api/notifications`

```json
{"employeeId":1,"subject":"Leave approved","message":"Your leave request has been approved."}
```

Mark it as sent with `PATCH /api/notifications/1/send`.

## Main endpoints

| Method | Endpoint | Purpose |
|---|---|---|
| POST/GET | `/api/departments` | Create/list departments |
| GET/PUT/DELETE | `/api/departments/{id}` | Manage one department |
| POST/GET | `/api/employees` | Create/list employees |
| GET/PUT | `/api/employees/{id}` | Read/update an employee |
| PATCH | `/api/employees/{id}/status?value=INACTIVE` | Change employment status |
| PATCH | `/api/employees/{id}/transfer?departmentId=2` | Transfer an employee |
| POST/GET | `/api/payroll` | Create/list payroll records |
| POST/GET | `/api/leaves` | Create/list leave requests |
| PATCH | `/api/leaves/{id}/decision?status=APPROVED` | Decide a leave request |
| POST/GET | `/api/notifications` | Create/list notifications |
| PATCH | `/api/notifications/{id}/send` | Mark a notification sent |

## Learning rule

Do not start splitting this project yet. First run all automated tests and manually test every API. This establishes the known working behaviour we must preserve during the microservices migration.
