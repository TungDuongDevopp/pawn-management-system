# Pawn Management System

## 1. Tổng quan dự án

**Pawn Management System** là hệ thống quản lý hoạt động của một cửa hàng cầm đồ, được xây dựng theo kiến trúc **RESTful API + Stateless Authentication**.

Hệ thống cho phép quản lý khách hàng, tài khoản người dùng, nhân viên, tài sản cầm cố, hợp đồng cầm đồ, khoản vay, thanh toán và trạng thái hợp đồng.

Backend được xây dựng bằng **Java + Spring Boot**, sử dụng **Spring Data JPA/Hibernate** để làm việc với cơ sở dữ liệu và **Spring Security + JWT** để xác thực, phân quyền.

Trong giai đoạn phát triển backend, hệ thống được kiểm thử chủ yếu thông qua **Postman**. Sau khi hoàn thiện API, hệ thống sẽ được tích hợp **Swagger/OpenAPI** để mô tả và kiểm thử API trực tiếp.

Frontend ReactJS sẽ được phát triển ở giai đoạn sau và sử dụng các REST API đã hoàn thiện.

---

# 2. Kiến trúc dự kiến

```text
                    ReactJS
                       │
                       │ HTTP / JSON
                       ▼
              ┌─────────────────┐
              │   Spring Boot   │
              │    REST API     │
              └─────────────────┘
                       │
        ┌──────────────┼──────────────┐
        ▼              ▼              ▼
   Controller       Service       Security
        │              │              │
        └──────────────┼──────────────┘
                       ▼
                  Repository
                       │
                       ▼
                    MySQL
```

Backend sử dụng mô hình phân tầng:

```text
Controller
    ↓
Service
    ↓
Repository
    ↓
Database
```

Authentication sử dụng mô hình **stateless**, server không lưu session đăng nhập của người dùng.

---

# 3. Các đối tượng chính

## Account

Quản lý tài khoản đăng nhập.

Thông tin dự kiến:

* ID
* Username
* Password
* Role
* Status
* CreatedAt
* UpdatedAt

Các role chính:

```text
ADMIN
STAFF
CUSTOMER
```

---

## Customer

Quản lý thông tin khách hàng.

Ví dụ:

* ID
* Họ tên
* Số CCCD
* Số điện thoại
* Địa chỉ
* Email
* Ngày sinh
* Thông tin liên hệ khác

Một Customer có thể có nhiều hợp đồng cầm đồ.

```text
Customer
    │
    └── 1 ─────── N ── PawnContract
```

---

## Staff

Quản lý nhân viên của cửa hàng.

Thông tin có thể gồm:

* ID
* Họ tên
* Số điện thoại
* Email
* Chức vụ
* Phòng ban
* Trạng thái

Staff có thể chịu trách nhiệm xử lý hoặc phê duyệt hợp đồng.

---

## Collateral

Quản lý tài sản được dùng để cầm cố.

Ví dụ loại tài sản:

```text
VEHICLE
ELECTRONICS
JEWELRY
PROPERTY
OTHER
```

Thông tin dự kiến:

* ID
* Tên tài sản
* Loại tài sản
* Mô tả
* Giá trị định giá
* Tình trạng
* Hình ảnh
* Thông tin giấy tờ liên quan

---

## PawnContract

Đây là **đối tượng nghiệp vụ trung tâm** của hệ thống.

Một hợp đồng liên kết:

```text
Customer
    │
    ▼
PawnContract
    │
    ├── Collateral
    ├── Loan
    ├── Payment
    └── Staff
```

Thông tin có thể gồm:

* ID hợp đồng
* Khách hàng
* Nhân viên xử lý
* Tài sản
* Số tiền cho vay
* Lãi suất
* Thời hạn
* Ngày bắt đầu
* Ngày đáo hạn
* Trạng thái

Trạng thái có thể gồm:

```text
PENDING
APPROVED
REJECTED
ACTIVE
OVERDUE
COMPLETED
LIQUIDATED
CANCELLED
```

---

## Loan

Quản lý khoản tiền mà khách hàng được vay dựa trên hợp đồng.

Có thể quản lý:

* Số tiền vay
* Lãi suất
* Thời hạn
* Tổng tiền phải trả
* Số tiền đã trả
* Số tiền còn lại

---

## Payment

Quản lý các lần thanh toán.

Ví dụ:

```text
PawnContract
      │
      └── 1 ─────── N ── Payment
```

Mỗi lần thanh toán có thể lưu:

* ID
* Hợp đồng
* Số tiền
* Thời gian
* Loại thanh toán
* Người thực hiện
* Ghi chú

---

# 4. Phân quyền

Hệ thống sử dụng **Role-Based Access Control**.

### ADMIN

Có quyền quản lý toàn hệ thống:

* Quản lý tài khoản
* Quản lý nhân viên
* Quản lý khách hàng
* Quản lý hợp đồng
* Xem báo cáo
* Quản lý cấu hình

### STAFF

Thực hiện nghiệp vụ cửa hàng:

* Quản lý khách hàng
* Tạo hợp đồng
* Định giá tài sản
* Xử lý hợp đồng
* Quản lý thanh toán
* Theo dõi hợp đồng

### CUSTOMER

Có quyền giới hạn:

* Xem thông tin cá nhân
* Xem hợp đồng của mình
* Xem khoản vay
* Xem lịch sử thanh toán
* Theo dõi trạng thái hợp đồng

---

# 5. Authentication

Backend sử dụng:

```text
Username + Password
        ↓
Authentication
        ↓
Access Token (JWT)
        +
Refresh Token
```

Các API yêu cầu đăng nhập sẽ sử dụng:

```http
Authorization: Bearer <access_token>
```

Spring Security chịu trách nhiệm:

```text
Request
   ↓
Security Filter
   ↓
JWT validation
   ↓
Authentication
   ↓
Authorization
   ↓
Controller
```

Mục tiêu là xây dựng hệ thống **stateless**, không sử dụng HTTP Session để duy trì trạng thái đăng nhập.

---

# 6. Các nhóm REST API

## Authentication API

```text
POST /api/auth/login
POST /api/auth/refresh
POST /api/auth/logout
```

## Account API

```text
GET    /api/accounts
GET    /api/accounts/{id}
POST   /api/accounts
PUT    /api/accounts/{id}
DELETE /api/accounts/{id}
```

## Customer API

```text
GET    /api/customers
GET    /api/customers/{id}
POST   /api/customers
PUT    /api/customers/{id}
DELETE /api/customers/{id}
```

## Staff API

```text
GET    /api/staff
GET    /api/staff/{id}
POST   /api/staff
PUT    /api/staff/{id}
DELETE /api/staff/{id}
```

## Collateral API

```text
GET    /api/collaterals
GET    /api/collaterals/{id}
POST   /api/collaterals
PUT    /api/collaterals/{id}
DELETE /api/collaterals/{id}
```

## Pawn Contract API

```text
GET    /api/contracts
GET    /api/contracts/{id}
POST   /api/contracts
PUT    /api/contracts/{id}
PATCH  /api/contracts/{id}/approve
PATCH  /api/contracts/{id}/reject
PATCH  /api/contracts/{id}/complete
```

## Payment API

```text
GET  /api/contracts/{id}/payments
POST /api/contracts/{id}/payments
```

Các endpoint trên chỉ là **định hướng ban đầu**, sẽ được điều chỉnh khi thiết kế database và nghiệp vụ cụ thể.

---

# 7. Những vấn đề backend cần xử lý

Dự án không chỉ dừng ở CRUD mà sẽ cố gắng mô phỏng một backend thực tế.

### Validation

```text
@NotBlank
@NotNull
@Size
@Pattern
...
```

### Exception Handling

Xây dựng Global Exception Handler:

```text
400 Bad Request
401 Unauthorized
403 Forbidden
404 Not Found
409 Conflict
500 Internal Server Error
```

Response lỗi thống nhất.

### Pagination

Ví dụ:

```text
GET /api/customers?page=0&size=10
```

### Sorting

```text
GET /api/customers?sort=createdAt,desc
```

### Filtering / Searching

Ví dụ:

```text
GET /api/contracts?status=ACTIVE
GET /api/customers?keyword=nguyen
```

### Transaction

Các nghiệp vụ liên quan đến nhiều thao tác database sẽ sử dụng transaction để đảm bảo tính nhất quán.

### CORS

Cho phép frontend ReactJS sau này gọi REST API.

### Security

Xử lý:

* Authentication
* Authorization
* Password hashing
* JWT
* Refresh Token
* Role-based access
* Endpoint protection

---

# 8. Quy trình phát triển dự án

## Phase 1 — Thiết kế

Trước khi code:

```text
Requirement
    ↓
Use Case
    ↓
Database Design
    ↓
ERD
    ↓
Entity / Relationship
    ↓
API Design
```

Không nên lao ngay vào viết Controller.

---

## Phase 2 — Database + JPA

Xây dựng:

* Database
* Tables
* Relationships
* Entity
* Enum
* Repository

Sau đó kiểm tra khả năng mapping giữa Java và Database.

---

## Phase 3 — Core CRUD

Làm từng module:

```text
Account
Customer
Staff
Collateral
Contract
Payment
```

Mỗi module đi theo:

```text
Entity
 ↓
Repository
 ↓
Service
 ↓
DTO
 ↓
Controller
 ↓
Postman
```

---

## Phase 4 — Authentication & Authorization

Sau khi CRUD ổn định:

```text
Spring Security
      ↓
Authentication
      ↓
JWT
      ↓
Refresh Token
      ↓
Role
      ↓
Authorization
```

Sau đó bảo vệ từng endpoint.

---

## Phase 5 — Business Logic

Đây là phần quan trọng nhất để project không biến thành **CRUD thuần túy**.

Ví dụ:

```text
Tạo hợp đồng
      ↓
Định giá tài sản
      ↓
Xác định khoản vay
      ↓
Staff xử lý
      ↓
Approve / Reject
      ↓
Hợp đồng Active
      ↓
Thanh toán
      ↓
Hoàn tất / Quá hạn
      ↓
Liquidation nếu cần
```

---

## Phase 6 — Advanced Backend

Sau khi nghiệp vụ chính chạy được:

* Pagination
* Sorting
* Filtering
* Search
* Transaction
* Optimistic/Pessimistic Locking nếu cần
* Audit fields
* Logging
* CORS
* Rate limiting
* Xử lý upload file
* Tối ưu query
* Index database

Không cần làm tất cả ngay từ đầu. Chỉ thêm khi phần core đã ổn.

---

# 9. Testing bằng Postman

Trong toàn bộ quá trình backend:

```text
Spring Boot
     ↕
  Postman
```

Test:

* Success case
* Validation error
* Authentication error
* Authorization error
* Not found
* Duplicate data
* Invalid request
* Business rule violation

Mục tiêu là **API chạy hoàn chỉnh trước khi đụng tới ReactJS**.

---

# 10. Swagger / OpenAPI

Sau khi API tương đối ổn định:

```text
REST API
   ↓
Swagger/OpenAPI
   ↓
API Documentation
```

Mỗi API cần mô tả:

* Endpoint
* HTTP Method
* Authentication
* Request parameters
* Request body
* Response
* HTTP status
* Error response
* Example request/response

Swagger vừa là documentation vừa giúp kiểm thử API.

---

# 11. Frontend — giai đoạn sau

Chỉ sau khi backend hoàn thiện mới bắt đầu ReactJS.

```text
ReactJS
   │
   │ HTTP
   ▼
REST API
```

React sẽ sử dụng API đã có sẵn thay vì backend và frontend phát triển song song.

Frontend tập trung vào:

* Login
* Dashboard
* Customer management
* Contract management
* Collateral management
* Payment
* Role-based UI
* JWT handling

Backend không cần thay đổi kiến trúc chỉ vì thêm React.

---

# 12. Mục tiêu cuối cùng

Project hoàn thiện sẽ có:

```text
                    ReactJS
                       │
                       ▼
              ┌─────────────────┐
              │ RESTful API      │
              │ Spring Boot      │
              ├─────────────────┤
              │ Spring Security  │
              │ JWT              │
              │ Validation       │
              │ Exception Handle │
              │ Transaction      │
              │ JPA / Hibernate  │
              └────────┬────────┘
                       │
                       ▼
                     MySQL
```

Kèm theo:

```text
Postman Collection
Swagger / OpenAPI
API Documentation
Database ERD
README
```

**Mục tiêu không phải chỉ là "làm được CRUD", mà là xây dựng một backend có cấu trúc, authentication, authorization, business logic và API documentation đủ hoàn chỉnh để sau này có thể dùng ReactJS làm client mà không phải viết lại backend.**
