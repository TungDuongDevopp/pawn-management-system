# Pawn Management System (Hệ thống Quản lý Cầm đồ)

Dự án cá nhân sử dụng Spring Boot để phát triển hệ thống quản lý cầm đồ và cho vay.

---

## 📋 Quy trình Phát triển Dự án (Development Roadmap)

Quy trình phát triển của dự án được chia làm 4 giai đoạn chính nhằm đảm bảo tính liền mạch, dễ bảo trì và mở rộng:

### Giai đoạn 1: Xây dựng MVC cho Account (Quản lý tài khoản)
- **Model**: Định nghĩa thực thể `Account` với các thuộc tính (`id`, `username`, `password`, `role`, `status`). Sử dụng các Enum `Role` và `AccountStatus` để quản lý phân quyền và trạng thái.
- **Repository**: Thiết lập `AccountRepository` ban đầu sử dụng dữ liệu giả lập lưu trữ trong bộ nhớ (`ArrayList`).
- **Service**: Tạo lớp `AccountService` làm cầu nối xử lý các nghiệp vụ.
- **Controller**: Thiết lập `UserAccountsController` ánh xạ các API GET/POST/DELETE.
- **View (Thymeleaf)**: 
  - `Admin/Account/index.html`: Hiển thị danh sách tài khoản dưới dạng bảng.
  - `Admin/Account/create.html`: Form thêm mới tài khoản (chứa nút quay lại trang danh sách).
  - `Admin/Account/update.html`: Form cập nhật thông tin vai trò và trạng thái của tài khoản (chứa nút quay lại trang danh sách).

### Giai đoạn 2: Xây dựng MVC cho Customer (Quản lý khách hàng)
- **Model**: Định nghĩa thực thể `Customer` (`id`, `name`, ,`citizenId`,`email`, `phone`, `address`).
- **Repository**: Thiết lập `CustomerRepository` sử dụng `ArrayList` để giả lập lưu trữ dữ liệu khách hàng tương tự Account.
- **Service**: Tạo lớp `CustomerService` để cung cấp các phương thức CRUD khách hàng.
- **Controller**: Thiết lập `CustomerController` kết nối dữ liệu từ service và trả về view tương ứng.
- **View (Thymeleaf)**:
  - Tích hợp **Sidebar** chung thông qua Thymeleaf Fragment (`fragments/sidebar.html`) vào trang `index.html` của cả Account và Customer để tối ưu hóa trải nghiệm điều hướng.
  - Xây dựng giao diện tạo mới (`Admin/Customer/create.html`) và cập nhật (`Admin/Customer/update.html`) sạch sẽ, không chứa sidebar và bắt buộc có nút quay lại trang danh sách (`index`).

### Giai đoạn 3: Chuyển đổi lưu trữ từ List (In-memory) sang Spring Data JPA
- **Database Configuration**: Cấu hình kết nối cơ sở dữ liệu MySQL (hoặc H2) trong file `application.properties`.
- **Entity Mapping**: Cấu hình các annotations JPA (`@Entity`, `@Table`, `@Id`, `@GeneratedValue(strategy = GenerationType.IDENTITY)`) trên các lớp Model (`Account`, `Customer`).
- **JPA Repository**: Thay thế các class Repository giả lập bằng các interfaces kế thừa `JpaRepository<T, ID>` để tự động hóa các câu lệnh SQL cơ bản.
- **Service Refactoring**: Điều chỉnh `AccountService` và `CustomerService` chuyển sang gọi các hàm chuẩn của Spring Data JPA (`findAll()`, `findById()`, `save()`, `deleteById()`).

### Giai đoạn 4: Tích hợp Validation cơ bản (Xác thực dữ liệu)
- **Dependency**: Sử dụng thư viện `spring-boot-starter-validation`.
- **Annotation Validation**: Áp dụng các quy tắc kiểm tra ràng buộc trực tiếp trên các thuộc tính của Model:
  - `@NotBlank(message = "...")`: Bắt buộc không được để trống (Name, Username, Password).
  - `@Email(message = "...")`: Định dạng email hợp lệ.
  - `@Size(min = ..., max = ..., message = "...")`: Giới hạn độ dài chuỗi ký tự.
  - `@Pattern(regexp = "...", message = "...")`: Xác thực số điện thoại theo định dạng chuẩn Việt Nam.
- **Controller Validation**: Sử dụng `@Valid` và `@ModelAttribute` kết hợp với đối tượng `BindingResult` trong Controller để phát hiện lỗi đầu vào.
- **UI Error Display**: Sử dụng cú pháp Thymeleaf `th:errors` và `th:if="${#fields.hasErrors('...')}"` để hiển thị trực tiếp các câu thông báo lỗi chi tiết ra ngoài giao diện người dùng nếu dữ liệu nhập vào không hợp lệ.

### Giai đoạn 5: Xây dựng MVC cho Staff(Quản lý nhân viên) và áp dụng 4 giai đoạn đã học vào giai đoạn này và bỏ qua giai đoạn lưu trữ dữ liệu In-memory qua Spring Data JPA

