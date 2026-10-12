# 🏢 HỆ THỐNG QUẢN LÝ NHÂN SỰ (OOP - JAVA)

> **Repository:** `Do_an_OOP_quan_ly_nhan_su_nam_2`  
> **Học phần:** Lập trình Hướng đối tượng (OOP) - Năm 2  
> **Ngôn ngữ:** Java (JDK 17+ / JavaSE-24)  
> **Môi trường đề xuất:** Eclipse IDE, Visual Studio Code, IntelliJ IDEA  

---

## 📖 1. Giới thiệu tổng quan

Dự án **Quản lý Nhân sự** là đồ án kết thúc môn **Lập trình Hướng đối tượng (OOP)**, được xây dựng dưới dạng ứng dụng giao diện dòng lệnh (Console/CLI). Hệ thống hỗ trợ tin học hóa quy trình quản trị doanh nghiệp, bao gồm: hồ sơ nhân sự, danh mục phòng ban, chức vụ, quản lý tiến độ đề án và phân công nhiệm vụ.

### Ứng dụng 4 đặc trưng OOP:
* **Đóng gói (Encapsulation):** Bảo vệ toàn vẹn dữ liệu thuộc tính thông qua phạm vi truy cập `private`, cung cấp các phương thức Getter/Setter để truy xuất an toàn.
* **Kế thừa (Inheritance):** Lớp `NhanVien` kế thừa các thông tin cơ sở từ lớp cha trừu tượng `Nguoi`.
* **Trừu tượng (Abstraction):** Lớp trừu tượng `Nguoi` và interface nghiệp vụ (`ChucNang` / `IChucNang`) định hình bộ khung chuẩn cho hệ thống.
* **Đa hình (Polymorphism):** Ghi đè phương thức (Override) và xử lý linh hoạt giữa các nhóm đối tượng quản lý.

---

## 📂 2. Cấu trúc thư mục dự án

Toàn bộ các file mã nguồn `.java` được đặt trực tiếp bên trong thư mục `src/`. Các file dữ liệu `.txt` được đặt ở **thư mục gốc (root)** ngang hàng với thư mục `src/`.

```text
Do_an_OOP_quan_ly_nhan_su_nam_2/
├── src/                               # Thư mục chứa toàn bộ mã nguồn Java
│   ├── Main.java                      # Điểm khởi chạy (Entry Point) & điều hướng Menu chính
│   ├── KiemTra.java                   # Lớp tiện ích kiểm tra (validate) ràng buộc nhập/xuất
│   ├── ChucNang.java                  # Interface định nghĩa hành vi nghiệp vụ chuẩn
│   ├── Nguoi.java                     # Abstract class đại diện cho thông tin cơ bản con người
│   ├── NoiSinh.java                   # Lớp đối tượng lưu trữ thông tin nơi sinh
│   ├── NhanVien.java                  # Lớp đối tượng Nhân viên (kế thừa từ Nguoi)
│   ├── ChucVu.java                    # Lớp đối tượng Chức vụ
│   ├── PhongBan.java                  # Lớp đối tượng Phòng ban
│   ├── DeAn.java                      # Lớp đối tượng Đề án
│   ├── PhanCong.java                  # Lớp đối tượng Phân công nhân viên vào đề án
│   ├── QuanLiNhanVien.java            # Nghiệp vụ quản lý & tính toán bảng lương nhân sự
│   ├── QuanLiChucVu.java              # Nghiệp vụ quản lý danh mục chức vụ
│   ├── QuanLiPhongBan.java            # Nghiệp vụ quản lý phòng ban và cơ cấu nhân sự
│   ├── QuanLiDeAn.java                # Nghiệp vụ quản lý tiến độ & trạng thái đề án
│   └── QuanLiPhanCong.java            # Nghiệp vụ liên kết phân công nhân sự và đề án
│
├── DanhSachChucVu.txt                 # File dữ liệu lưu trữ Chức vụ
├── DanhSachDeAn.txt                   # File dữ liệu lưu trữ Đề án
├── DanhSachNhanVien.txt               # File dữ liệu lưu trữ Nhân viên
├── DanhSachPhanCong.txt               # File dữ liệu lưu trữ Phân công nhiệm vụ
├── DanhSachPhongBan.txt               # File dữ liệu lưu trữ Phòng ban
└── README.md                          # Tài liệu hướng dẫn đồ án
```

---

## ⚠️ 3. Quy tắc bắt buộc về các file dữ liệu (`.txt`)

* **Vai trò:** Toàn bộ dữ liệu của chương trình được đọc và lưu vào **5 file `.txt`**. Khi khởi động, hệ thống nạp dữ liệu từ các file này vào bộ nhớ; khi chọn `0. Thoát chương trình`, dữ liệu mới nhất sẽ được ghi đè và lưu lại vào file.
* **Vị trí bắt buộc:** Các file `.txt` **PHẢI ĐƯỢC ĐẶT Ở THƯ MỤC GỐC (ROOT)** của project, ngang hàng với `src/` (tuyệt đối không để bên trong thư mục `src/`).
* **Lý do kỹ thuật:** Chương trình sử dụng đường dẫn tương đối (Relative Path) dựa trên thư mục chạy hiện hành (Working Directory):
  ```java
  private static final String FILE_CV = "DanhSachChucVu.txt";
  private static final String FILE_PB = "DanhSachPhongBan.txt";
  private static final String FILE_NV = "DanhSachNhanVien.txt";
  private static final String FILE_DA = "DanhSachDeAn.txt";
  private static final String FILE_PC = "DanhSachPhanCong.txt";
  ```
  *Nếu các file `.txt` bị đặt sai vị trí hoặc nằm trong thư mục con, chương trình sẽ gặp lỗi `FileNotFoundException` hoặc không thể lưu/tải dữ liệu.*

---

## ✨ 4. Chức năng chính

Chương trình cung cấp bảng điều khiển menu số gồm 5 phân hệ lớn:

1. **Quản lí Nhân viên:**
   * Thêm mới, chỉnh sửa, xóa và tìm kiếm hồ sơ nhân viên.
   * Xem danh sách nhân sự toàn công ty.
   * Tính bảng lương tổng thể hoặc tra cứu chi tiết mức lương của từng nhân viên.
2. **Quản lí Chức vụ:**
   * Thêm mới, điều chỉnh, xóa và tra cứu chức vụ.
   * Kiểm tra ràng buộc an toàn (không cho xóa chức vụ đang có nhân viên nắm giữ).
3. **Quản lí Phòng ban:**
   * Thiết lập cơ cấu phòng ban, quản lý điều chuyển nhân sự.
   * Kiểm tra ràng buộc nhân sự trước khi xóa phòng ban.
4. **Quản lí Phân công:**
   * Gán nhân viên vào từng đề án cụ thể.
   * Điều chỉnh kinh phí, phụ cấp đề án cho nhân viên.
   * Tra cứu phân công theo mã nhân viên hoặc mã đề án.
5. **Quản lí Đề án:**
   * Thêm mới đề án, cập nhật tiến độ, hủy đề án (chuyển sang trạng thái `DaHuy`).
   * Xem danh sách các đề án đang hoạt động.
6. **Tiện ích kiểm tra (`KiemTra.java`):**
   * Bắt lỗi nhập liệu chặt chẽ (ép nhập số nguyên dương, chống nhập chuỗi rỗng,...), ngăn chặn chương trình bị crash do sai kiểu dữ liệu đầu vào.

---

## 🚀 5. Hướng dẫn chi tiết cách chạy chương trình

### Yêu cầu môi trường
* Đã cài đặt **Java Development Kit (JDK 17 trở lên)** (ví dụ: OpenJDK 17, JDK 21 hoặc JavaSE-24).

---

### Cách 1: Chạy bằng Eclipse IDE (Môi trường mặc định)
1. Khởi động Eclipse $\rightarrow$ Chọn **File** $\rightarrow$ **Open Projects from File System...**
2. Tại mục **Import source**, bấm **Directory...** $\rightarrow$ Trỏ đến thư mục dự án `Do_an_OOP_quan_ly_nhan_su_nam_2`.
3. Kiểm tra thanh **Package Explorer**:
   * Thư mục `src` chứa trực tiếp các file `.java`.
   * 5 file `.txt` nằm ở thư mục gốc (ngang hàng với `src`).
4. Mở thư mục `src` $\rightarrow$ Nhấp chuột phải vào file **`Main.java`** $\rightarrow$ Chọn **Run As** $\rightarrow$ **Java Application** (hoặc nhấn `Alt + Shift + X, J`).
5. Thao tác điều khiển chương trình trực tiếp trên cửa sổ **Console** bên dưới.

---

### Cách 2: Chạy bằng Visual Studio Code (VS Code)
1. Cài đặt tiện ích mở rộng: **Extension Pack for Java** (của Microsoft) trên VS Code.
2. Mở thư mục dự án: **File** $\rightarrow$ **Open Folder...** $\rightarrow$ Chọn thư mục gốc `Do_an_OOP_quan_ly_nhan_su_nam_2` *(Bắt buộc mở thư mục gốc để VS Code đặt đúng Working Directory tìm file `.txt`)*.
3. Mở file `src/Main.java`.
4. Bấm biểu tượng **Play (Run Java)** ở góc trên bên phải màn hình hoặc nhấn phím `F5`.

---

### Cách 3: Chạy bằng IntelliJ IDEA
1. Mở IntelliJ IDEA $\rightarrow$ Chọn **Open** $\rightarrow$ Trỏ đến thư mục `Do_an_OOP_quan_ly_nhan_su_nam_2`.
2. Đánh dấu thư mục mã nguồn: Nhấp chuột phải vào thư mục `src` $\rightarrow$ Chọn **Mark Directory as** $\rightarrow$ **Sources Root** (nếu IntelliJ chưa tự nhận diện).
3. Mở file `src/Main.java`.
4. Nhấn biểu tượng tam giác màu xanh (**Run**) cạnh hàm `main` để khởi chạy.

---

### Cách 4: Chạy từ Terminal / Command Prompt (CMD)

Mở Terminal tại thư mục gốc của dự án (nơi nhìn thấy thư mục `src` và các file `.txt`):

```bash
# Bật hiển thị UTF-8 trên Windows CMD (tránh lỗi font chữ tiếng Việt)
chcp 65001

# Bước 1: Biên dịch toàn bộ các file java trong src vào thư mục bin
javac -encoding UTF-8 -d bin src/*.java

# Bước 2: Chạy chương trình từ lớp Main
# (Trường hợp các file Java không khai báo package):
java -cp bin Main

# (Trường hợp các file Java có khai báo "package DoAn;" hoặc "package do_An;"):
# java -cp bin DoAn.Main
```

---

## 👥 Tác giả
* **Sinh viên thực hiện:** DucLuong2201  
* **Đề tài:** Hệ thống Quản lý Nhân sự - Đồ án môn Lập trình Hướng đối tượng (OOP)