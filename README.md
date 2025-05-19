# Ứng dụng Quản lý Kho Hàng Trái Cây T13F

## Giới thiệu

Ứng dụng Quản lý Kho Hàng Trái Cây T13F là một hệ thống phần mềm được phát triển để quản lý các hoạt động liên quan đến kho hàng trái cây. Ứng dụng này cung cấp các chức năng cần thiết cho người quản lý và nhân viên để theo dõi, cập nhật và xử lý thông tin về nhà cung cấp, nhân viên, phiếu nhập/xuất, khu vực lưu trữ, khách hàng và thống kê kho hàng.

## Công nghệ sử dụng

* **Frontend:** JavaFX
* **Backend:** Java
* **ORM (Object-Relational Mapping):** JPA (Java Persistence API) với Hibernate implementation
* **Truyền thông:** RMI (Remote Method Invocation) cho giao tiếp giữa client và server (nếu có kiến trúc client-server)
* **Cơ sở dữ liệu:** MySQL

## Các chức năng chính

Ứng dụng cung cấp các use case sau:

1.  **Quản lý nhà cung cấp:**
    * Xem danh sách nhà cung cấp.
    * Thêm thông tin nhà cung cấp mới.
    * Sửa thông tin nhà cung cấp hiện có.

2.  **Quản lý nhân viên:**
    * Xem danh sách nhân viên.
    * Thêm mới thông tin nhân viên.
    * Chỉnh sửa thông tin nhân viên.
    * Ngừng quyền truy cập của nhân viên đã nghỉ việc.

3.  **Quản lý phiếu xuất:**
    * Xem thông tin chi tiết của phiếu xuất.

4.  **Quản lý khu vực lưu trữ trái cây:**
    * Xem khu vực lưu trữ theo loại trái cây.
    * Xem thông tin chi tiết về vị trí lưu trữ.

5.  **Quản lý khách hàng:**
    * Xem danh sách khách hàng.
    * Tìm kiếm khách hàng.

6.  **Quản lý phiếu nhập chưa được duyệt:**
    * Xem danh sách phiếu nhập chưa được duyệt.
    * Chỉnh sửa thông tin phiếu nhập chưa được duyệt.
    * Tạo phiếu nhập mới.

7.  **Xác nhận phiếu nhập:**
    * Cho phép nhân viên xác nhận phiếu nhập sau khi đã nhận hàng thành công.

8.  **Xuất hàng:**
    * Cho phép nhân viên lập phiếu xuất hàng.

9.  **Quản lý thống kê:**
    * Xem thống kê số lượng trái cây tồn kho.
    * Xem thống kê doanh thu và lợi nhuận trong 10 tháng gần nhất.

10. **Quản lý phiếu nhập đã được duyệt:**
    * Xem danh sách các phiếu nhập đã được duyệt.

11. **Quản lý tài khoản:**
    * Cho phép người dùng (nhân viên, người quản lý) đăng nhập vào hệ thống.
    * Cho phép người dùng đổi mật khẩu tài khoản.

12. **Xác định trái cây cần xử lý:**
    * Xem danh sách trái cây bị hỏng.
    * Xem danh sách trái cây quá chín.
    * Xem danh sách trái cây chín.
    * Cập nhật vị trí của trái cây bị hỏng thành trạng thái trống.

13. **Khôi phục tài khoản cho nhân viên:**
    * Cho phép người quản lý khôi phục tài khoản cho nhân viên bằng cách làm mới mật khẩu thông qua tên đăng nhập.

## Cài đặt và sử dụng

1.  **Yêu cầu hệ thống:**
    * Hệ điều hành: (Windows, Linux, macOS)
    * Java Development Kit (JDK): Phiên bản (11 trở lên)
    * MySQL Server: Đã được cài đặt và cấu hình
    * (Các yêu cầu khác nếu có)

2.  **Các bước cài đặt:**
    * Tải xuống và cài đặt JDK (nếu chưa có).
    * Cài đặt MySQL Server và tạo database cho ứng dụng.
    * Cấu hình kết nối cơ sở dữ liệu trong ứng dụng.
    * (Các bước cài đặt RMI server nếu có kiến trúc client-server).
    * Chạy ứng dụng JavaFX client (nếu có kiến trúc client-server) hoặc chạy ứng dụng standalone.

## Kiến trúc hệ thống

* **Kiến trúc Client-Server:**
    * **Client (JavaFX):** Giao diện người dùng được xây dựng bằng JavaFX, chịu trách nhiệm hiển thị dữ liệu và tương tác với người dùng.
    * **Server (Java, RMI):** Chứa logic nghiệp vụ và quản lý kết nối đến cơ sở dữ liệu MySQL thông qua JPA/Hibernate. Client giao tiếp với server thông qua RMI.

---
