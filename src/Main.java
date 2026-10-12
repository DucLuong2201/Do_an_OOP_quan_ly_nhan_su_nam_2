package DoAn;

public class Main {

   
    private static final String FILE_CV = "DanhSachChucVu.txt";
    private static final String FILE_PB = "DanhSachPhongBan.txt";
    private static final String FILE_NV = "DanhSachNhanVien.txt";
    private static final String FILE_DA = "DanhSachDeAn.txt";
    private static final String FILE_PC = "DanhSachPhanCong.txt";

    private static QuanLiChucVu qlCV = new QuanLiChucVu();
    private static QuanLiPhongBan qlPB = new QuanLiPhongBan();
    
   
    private static QuanLiNhanVien qlNV = new QuanLiNhanVien(qlPB, qlCV); 
    
    private static QuanLiDeAn qlDA = new QuanLiDeAn(qlPB);
    
    private static QuanLiPhanCong qlPC = new QuanLiPhanCong(qlNV, qlDA);
    
    static {
        qlNV.setQuanLiPhanCong(qlPC);
    }

    
    public static void main(String[] args) {
        
       
    	qlPB.setDependencies(qlNV, qlCV);
        System.out.println("--- 💾 Đang tải dữ liệu từ file... ---");
        qlCV.docFile();
        qlPB.docFile();      
        qlNV.docFile(); 
        qlDA.DocFile(FILE_DA);
        
        qlDA.setQuanLiPhanCong(qlPC); 
        

        qlPC.DocFile(FILE_PC);
        System.out.println("--- ✅ Tải dữ liệu hoàn tất ---");

        boolean chayChinh = true;
        while (chayChinh) {
            System.out.println("\n╔======================================╗");
            System.out.println("║    MENU CHÍNH - QUẢN LÝ NHÂN SỰ    ║");
            System.out.println("╠======================================╣");
            System.out.println("║ 1. Quản Lí Nhân Viên               ║");
            System.out.println("║ 2. Quản Lí Chức Vụ                 ║");
            System.out.println("║ 3. Quản Lí Phòng Ban               ║");
            System.out.println("║ 4. Quản Lí Phân Công               ║");
            System.out.println("║ 5. Quản Lí Đề Án                   ║");
            System.out.println("║ 0. Thoát chương trình              ║"); 
            System.out.println("╚======================================╝");
            int chonChinh = KiemTra.nhapSoNguyenDuong("👉 Nhập lựa chọn của bạn (0-5): "); 

            switch (chonChinh) {
                case 1:
                    menuNhanVien();
                    break;
                case 2:
                    menuChucVu();
                    break;
                case 3:
                    menuPhongBan();
                    break;
                case 4:
                    menuPhanCong();
                    break;
                case 5:
                    menuDeAn();
                    break;
                case 0: 
                    chayChinh = false;
                    break;
                default:
System.out.println("❌ Lựa chọn không hợp lệ. Vui lòng chọn lại.");
            }
        }

        
        System.out.println("\n--- 💾 Đang lưu dữ liệu ra file... ---");
        qlCV.ghiFile();
        qlPB.ghiFile();
        qlNV.ghiFile();
        qlDA.GhiFile(FILE_DA);
        qlPC.GhiFile(FILE_PC);
        System.out.println("--- ✅ Lưu dữ liệu hoàn tất. Tạm biệt! ---");
    }

    

  
    public static void menuNhanVien() {
        boolean chaySub = true;
        while (chaySub) {
            System.out.println("\n--- 🧑‍💼 MENU QUẢN LÍ NHÂN VIÊN ---");
            System.out.println("1. Thêm Nhân Viên");
            System.out.println("2. Xóa Nhân Viên");
            System.out.println("3. Sửa Nhân Viên");
            System.out.println("4. Tìm kiếm Nhân Viên");
            System.out.println("5. Xem danh sách Nhân Viên");
            System.out.println("6. Xem danh sach luong");
            System.out.println("7. Xem luong tung nhan vien");
            System.out.println("0. Quay lại Menu Chính"); 
            int chonSub = KiemTra.nhapSoNguyenDuong("👉 Nhập lựa chọn (0-7): "); 

            switch (chonSub) {
                case 1: qlNV.them(); break;
                case 2: qlNV.xoa(); break;
                case 3: qlNV.sua(); break;
                case 4: qlNV.timKiem(); break;
                case 5: qlNV.xem(); break;
                case 6: qlNV.tinhLuongToanBo(); break;
                case 7: qlNV.tinhluong(); break;
                case 0: chaySub = false; break;
                default: System.out.println("❌ Lựa chọn không hợp lệ.");
            }
        }
    }

   
    public static void menuChucVu() {
        boolean chaySub = true;
        while (chaySub) {
            System.out.println("\n--- 🎖️ MENU QUẢN LÍ CHỨC VỤ ---");
            System.out.println("1. Thêm Chức Vụ");
            System.out.println("2. Xóa Chức Vụ");
            System.out.println("3. Sửa Chức Vụ");
            System.out.println("4. Tìm kiếm Chức Vụ");
            System.out.println("5. Xem danh sách Chức Vụ");
            System.out.println("0. Quay lại Menu Chính"); 
            int chonSub = KiemTra.nhapSoNguyenDuong("👉 Nhập lựa chọn (0-5): "); 

            switch (chonSub) {
                case 1: qlCV.them(); break;
                case 2: qlCV.xoa(qlNV); break;
                case 3: qlCV.sua(); break;
                case 4: qlCV.timKiem(); break;
                case 5: qlCV.xem(); break;
                case 0: chaySub = false; break; 
                default: System.out.println("❌ Lựa chọn không hợp lệ.");
            }
        }
    }

    public static void menuPhongBan() {
        boolean chaySub = true;
        while (chaySub) {
            System.out.println("\n--- 🏢 MENU QUẢN LÍ PHÒNG BAN ---");
System.out.println("1. Thêm Phòng Ban");
            System.out.println("2. Xóa Phòng Ban"); 
            System.out.println("3. Sửa Phòng Ban");
            System.out.println("4. Tìm kiếm Phòng Ban");
            System.out.println("5. Xem danh sách Phòng Ban");
            System.out.println("0. Quay lại Menu Chính"); 
            int chonSub = KiemTra.nhapSoNguyenDuong("👉 Nhập lựa chọn (0-5): "); 

            switch (chonSub) {
                case 1: qlPB.them(); break;
                case 2: qlPB.xoa(qlNV); break;               
                case 3: qlPB.sua(); break;
                case 4: qlPB.timKiem(); break;
                case 5: qlPB.xem(); break; 
                case 0: chaySub = false; break; 
                default: System.out.println("❌ Lựa chọn không hợp lệ.");
            }
        }
    }

    public static void menuPhanCong() {
        boolean chaySub = true;
        while (chaySub) {
            System.out.println("\n--- 🔗 MENU QUẢN LÍ PHÂN CÔNG ---");
            System.out.println("1. Thêm Phân Công (Phân NV vào Đề Án)");
            System.out.println("2. Xóa Phân Công");
            System.out.println("3. Sửa Phân Công (Tiền đề án, đổi NV,...)");
            System.out.println("4. Tìm kiếm Phân Công (theo NV hoặc ĐA)");
            System.out.println("5. Xem danh sách Phân Công");
            System.out.println("0. Quay lại Menu Chính"); 
            int chonSub = KiemTra.nhapSoNguyenDuong("👉 Nhập lựa chọn (0-5): "); 

            switch (chonSub) {
                case 1: qlPC.Them(); break;
                case 2: qlPC.Xoa(); break;
                case 3: qlPC.Sua(); break;
                case 4: qlPC.TimKiem(); break;
                case 5: qlPC.HienThi(); break;
                case 0: chaySub = false; break; 
                default: System.out.println("❌ Lựa chọn không hợp lệ.");
            }
        }
    }

   
    public static void menuDeAn() {
        boolean chaySub = true;
        while (chaySub) {
            System.out.println("\n--- 📋 MENU QUẢN LÍ ĐỀ ÁN ---");
            System.out.println("1. Thêm Đề Án");
            System.out.println("2. Hủy Đề Án (Chuyển trạng thái 'DaHuy')");
            System.out.println("3. Sửa Đề Án");
            System.out.println("4. Tìm kiếm Đề Án");
            System.out.println("5. Xem danh sách Đề Án (đang hoạt động)");
            System.out.println("0. Quay lại Menu Chính"); 
            int chonSub = KiemTra.nhapSoNguyenDuong("👉 Nhập lựa chọn (0-5): "); 

            switch (chonSub) {
                case 1: qlDA.them(); break;
                case 2: qlDA.xoa(); break; 
                case 3: qlDA.sua(); break;
                case 4: qlDA.timKiem(); break;
                case 5: qlDA.xem(); break;
                case 0: chaySub = false; break;
default: System.out.println("❌ Lựa chọn không hợp lệ.");
            }
        }
    }
}
