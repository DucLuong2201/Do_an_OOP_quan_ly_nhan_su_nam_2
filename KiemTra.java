package DoAn;

import java.text.ParseException;
import java.util.ArrayList;
import java.text.SimpleDateFormat;
import java.util.Scanner;
import java.util.regex.Pattern;
import java.util.List;

public class KiemTra {
    private static final Scanner sc = new Scanner(System.in);
    private static final SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");

    static {
        df.setLenient(false);
    }

    // ===================== KIỂM TRA DỮ LIỆU CHUNG =====================

    public static String nhapChuoi(String thongBao) {
        while (true) {
            System.out.print(thongBao);
            String s = sc.nextLine().trim();
            if (s.isEmpty()) {
                System.out.println("❌ Không được để trống.");
                continue;
            }
            return s;
        }
    }

    public static String nhapMa(String thongBao, int min, int max) {
        while (true) {
            System.out.print(thongBao);
            String s = sc.nextLine().trim();
            if (s.length() < min || s.length() > max) {
                System.out.printf("❌ Độ dài phải từ %d đến %d ký tự.\n", min, max);
                continue;
            }
            if (!s.matches("^[A-Za-z0-9]+$")) {
                System.out.println("❌ Mã chỉ được chứa chữ và số.");
                continue;
            }
            return s;
        }
    }

    public static String nhapTen(String thongBao, int max) {
        while (true) {
            System.out.print(thongBao);
            String s = sc.nextLine().trim();
            if (s.isEmpty()) {
                System.out.println("❌ Không được để trống.");
                continue;
            }
            if (s.length() > max) {
                System.out.println("❌ Tên quá dài, nhập ngắn hơn.");
                continue;
            }
            if (!s.matches("^[\\p{L}0-9\\s]+$")) {
                System.out.println("❌ Tên không được chứa ký tự đặc biệt.");
                continue;
            }
            return s;
        }
    }

    public static String nhapNgay(String thongBao) {
        while (true) {
            System.out.print(thongBao);
            String s = sc.nextLine().trim();
            try {
                df.parse(s);
                return s;
            } catch (ParseException e) {
                System.out.println("❌ Ngày không hợp lệ (định dạng dd/MM/yyyy).");
            }
        }
    }

    public static String nhapEmail(String thongBao) {
        while (true) {
            System.out.print(thongBao);
            String s = sc.nextLine().trim();
            if (!EMAIL_PATTERN.matcher(s).matches()) {
                System.out.println("❌ Email không hợp lệ (ví dụ: abc@cty.com).");
                continue;
            }
            return s;
        }
    }

    public static int nhapSoNguyenDuong(String thongBao) {
        while (true) {
            System.out.print(thongBao);
            String s = sc.nextLine().trim();
            try {
                int val = Integer.parseInt(s);
                if (val < 0) {
                    System.out.println("❌ Không được nhập số âm.");
                    continue;
                }
                return val;
            } catch (NumberFormatException e) {
                System.out.println("❌ Vui lòng nhập số nguyên hợp lệ.");
            }
        }
    }

    public static double nhapSoThucDuong(String thongBao) {
        while (true) {
            System.out.print(thongBao);
            String s = sc.nextLine().trim();
            try {
                double val = Double.parseDouble(s);
                if (val <= 0) {
                    System.out.println("❌ Phải nhập số dương lớn hơn 0.");
                    continue;
                }
                return val;
            } catch (NumberFormatException e) {
                System.out.println("❌ Vui lòng nhập số hợp lệ.");
            }
        }
    }
 // ===================== KIỂM TRA THÔNG TIN NGƯỜI =====================
    public static String nhapHoTenNguoi(String thongBao) {
        while (true) {
            System.out.print(thongBao);
            String s = sc.nextLine().trim();
            if (s.isEmpty()) {
                System.out.println("❌ Không được để trống!");
                continue;
            }
            if (s.matches("[\\p{L}\\s]+")) {
                return s;
            }
            System.out.println("❌ Họ tên chỉ được chứa chữ cái và khoảng trắng!");
        }
    }

    public static int nhapNamSinhNguoi(String thongBao) {
        while (true) {
            try {
                System.out.print(thongBao);
                int nam = Integer.parseInt(sc.nextLine().trim());
                if (nam >= 1900 && nam <= 2025)
                    return nam;
                System.out.println("❌ Năm sinh không hợp lệ!");
            } catch (NumberFormatException e) {
                System.out.println("❌ Nhập sai, nhập lại!");
            }
        }
    }

    public static NoiSinh nhapNoiSinhNguoi(String thongBao) {
        return NoiSinh.parse(thongBao);
    }

    public static String nhapDiaChiNguoi(String thongBao) {
        while (true) {
            System.out.print(thongBao);
            String diachi = sc.nextLine().trim();
            if (diachi.isEmpty()) {
                System.out.println("❌ Địa chỉ không được để trống!");
                continue;
            }
            if (diachi.length() > 50) {
                System.out.println("❌ Địa chỉ quá dài! Tối đa 50 ký tự.");
                continue;
            }
            if (!diachi.matches("[\\p{L}\\p{N}\\s,.-]+")) {
                System.out.println("❌ Địa chỉ chỉ được chứa chữ, số, dấu phẩy, dấu chấm, dấu gạch ngang!");
                continue;
            }
            return diachi;
        }
    }
    
 // ===================== KIỂM TRA NHÂN VIÊN ====================
    public static String nhapMaNhanVien(String thongBao) {
        while (true) {
            System.out.print(thongBao);
            String s = sc.nextLine().trim().toUpperCase();

            if (!s.matches("^NV\\d{3}$")) {
                System.out.println("❌ Mã nhân viên phải có dạng NV + 3 chữ số (VD: NV001)!");
                continue;
            }
            return s;
        }
    }

    public static double nhapHeSoLuong(String thongBao) {
        while (true) {
            try {
                System.out.print(thongBao);
                double val = Double.parseDouble(sc.nextLine().trim());
                if (val < 1 || val > 10) {
                    System.out.println("❌ Hệ số lương phải trong khoảng 1–10!");
                    continue;
                }
                return val;
            } catch (NumberFormatException e) {
                System.out.println("❌ Nhập sai, nhập lại!");
            }
        }
    }

    public static String nhapPhongBan(List<String> danhSachPB) {
        while (true) {
            System.out.println("Chọn phòng ban hợp lệ: " + danhSachPB);
            System.out.print("Nhập phòng ban: ");
            String pb = sc.nextLine().trim();
            for (String s : danhSachPB)
                if (s.equalsIgnoreCase(pb))
                    return s;
            System.out.println("❌ Phòng ban không hợp lệ! Vui lòng nhập lại.");
        }
    }

    public static String nhapChucVu(List<String> danhSachCV) {
        while (true) {
            System.out.println("Chọn chức vụ hợp lệ: " + danhSachCV);
            System.out.print("Nhập chức vụ: ");
            String cv = sc.nextLine().trim();
            for (String s : danhSachCV)
                if (s.equalsIgnoreCase(cv))
                    return s;
            System.out.println("❌ Chức vụ không hợp lệ! Vui lòng nhập lại.");
        }
    }



    // ===================== KIỂM TRA ĐẶC THÙ =====================
    public static String nhapMaDeAn(String thongBao) {
        while (true) {
            System.out.print(thongBao);
            String s = sc.nextLine().trim();
            if (s.matches("^[A-Z]{2}\\d+$")) {
                return s;
            }
            System.out.println("❌ Mã đề án không hợp lệ. (VD: DA1, DA01)");
        }
    }

    public static String nhapTenDeAn(String thongBao, int max) {
        while (true) {
            System.out.print(thongBao);
            String s = sc.nextLine().trim();
            if (s.isEmpty()) {
                System.out.println("❌ Không được để trống.");
                continue;
            }
            if (s.length() > max) {
                System.out.println("❌ Tên quá dài, nhập ngắn hơn.");
                continue;
            }
            if (!Character.isUpperCase(s.charAt(0))) {
                System.out.println("❌ Tên đề án phải viết hoa chữ cái đầu.");
                continue;
            }
            if (!s.matches("^[\\p{L}0-9\\s]+$")) {
                System.out.println("❌ Tên không được chứa ký tự đặc biệt.");
                continue;
            }
            return s;
        }
    }

    public static String nhapTrangThai() {
        System.out.println("Chọn trạng thái mới:");
        System.out.println("1. Mới");
        System.out.println("2. Đang thực hiện");
        System.out.println("3. Hoàn thành");
        System.out.println("4. Đã hủy");
        int chon = nhapSoNguyenDuong("👉 Nhập lựa chọn: ");

        return switch (chon) {
            case 1 -> "Moi";
            case 2 -> "DangThucHien";
            case 3 -> "HoanThanh";
            case 4 -> "DaHuy";
            default -> {
                System.out.println("❌ Lựa chọn không hợp lệ.");
                yield null;
            }
        };
    }
    
    public static String nhapChuoiChoPhepRong(String thongBao) {
        System.out.print(thongBao);
        return sc.nextLine().trim();
    }

    // ===================== HÀM LIÊN KẾT ĐỐI TƯỢNG =====================
    public static String nhapNgayKetThuc(String thongBao, String ngayBatDauStr) {
        java.util.Date ngayBatDau = null;
        try {
            ngayBatDau = df.parse(ngayBatDauStr);
        } catch (ParseException e) {
            System.out.println("⚠️ Lỗi hệ thống: Không thể so sánh với ngày bắt đầu '" + ngayBatDauStr + "'. Vui lòng kiểm tra định dạng.");
            return null;
        }

        while (true) {
            System.out.print(thongBao);
            String s = sc.nextLine().trim();
            try {
                java.util.Date ngayKetThuc = df.parse(s);
                if (ngayKetThuc.before(ngayBatDau)) {
                    System.out.println("❌ Ngày kết thúc không được nhỏ hơn ngày bắt đầu (" + ngayBatDauStr + ").");
                    continue;
                }
                return s;
            } catch (ParseException e) {
                System.out.println("❌ Ngày không hợp lệ (định dạng dd/MM/yyyy).");
            }
        }
    }
    
    public static boolean isNgayBDHopLe(String ngayBatDauMoiStr, String ngayKetThucHienTaiStr) {
        try {
           java.util.Date ngayBDMoi = df.parse(ngayBatDauMoiStr);
           java.util.Date ngayKTHienTai = df.parse(ngayKetThucHienTaiStr);
           if (ngayBDMoi.after(ngayKTHienTai)) {
               System.out.println("❌ Ngày bắt đầu mới ("+ngayBatDauMoiStr+") không được lớn hơn ngày kết thúc hiện tại (" + ngayKetThucHienTaiStr + ").");
               return false;
           }
           return true;
       } catch (ParseException e) {
            System.out.println("⚠️ Lỗi định dạng ngày khi so sánh.");
           return false;
       }
   }
    
 // === HÀM MỚI ĐỂ NHẬP MÃ VIẾT TẮT ===
    public static String nhapMaVietTat(String thongBao, int min, int max, boolean chiChuCai) {
        while (true) {
            System.out.print(thongBao);
            String s = sc.nextLine().trim().toUpperCase(); 
            if (s.length() < min || s.length() > max) {
                System.out.printf("❌ Mã viết tắt phải có độ dài từ %d đến %d ký tự.\n", min, max);
                continue;
            }
            if (chiChuCai && !s.matches("^[A-Z]+$")) { 
                 System.out.println("❌ Mã viết tắt chỉ được chứa chữ cái.");
                 continue;
            }
            if (!chiChuCai && !s.matches("^[A-Z0-9]+$")){
                 System.out.println("❌ Mã viết tắt chỉ được chứa chữ cái và số.");
                 continue;
            }
            return s; 
        }
    }

 // === SỬA LẠI nhapPhongBan ĐỂ DÙNG MÃ VIẾT TẮT ===
    public static PhongBan nhapPhongBan(QuanLiPhongBan qlPB) {
        ArrayList<PhongBan> list = qlPB.getDanhSach();
        if (list == null || list.isEmpty()) {
            System.out.println("❌ Danh sách phòng ban trống. Không thể tiếp tục.");
            return null;
        }

        System.out.println("--- Danh sách Phòng ban ---");
        qlPB.HienThi(); 
        while (true) {
            String maVietTat = nhapMaVietTat("Nhập mã phòng ban viết tắt để chọn: ", 2, 3, true);
            PhongBan pb = qlPB.TimTheoMa(maVietTat);
            if (pb != null) {
                return pb; 
            }
            System.out.println("❌ Mã phòng ban viết tắt không tồn tại. Nhập lại.");
        }
    }

    // === SỬA LẠI nhapChucVu ĐỂ DÙNG MÃ VIẾT TẮT ===
     public static ChucVu nhapChucVu(QuanLiChucVu qlCV) {
         ArrayList<ChucVu> list = qlCV.getDanhSach();
        if (list == null || list.isEmpty()) {
            System.out.println("❌ Danh sách chức vụ trống. Không thể tiếp tục.");
            return null;
        }

        System.out.println("--- Danh sách Chức vụ ---");
        qlCV.HienThi(); 
         while (true) {
            String maVietTat = nhapMaVietTat("Nhập mã chức vụ viết tắt để chọn: ", 2, 3, true);
            ChucVu cv = qlCV.TimTheoMa(maVietTat);
            if (cv != null) {
                return cv; 
            }
            System.out.println("❌ Mã chức vụ viết tắt không tồn tại. Nhập lại.");
        }
    }
     
     public static DeAn nhapDeAn(QuanLiDeAn qlDA) {
         ArrayList<DeAn> list = qlDA.getDanhSach();
        if (list == null || list.isEmpty()) {
            System.out.println("❌ Danh sách đề án trống. Không thể tiếp tục.");
            return null;
        }

        System.out.println("--- Danh sách Đề án (Chỉ hiển thị các đề án đang hoạt động) ---");
        qlDA.xem(); 
         while (true) {
            String maDA = nhapMaDeAn("Nhập mã đề án để chọn: ");
            DeAn da = qlDA.TimTheoMa(maDA);
            if (da == null) {
                 System.out.println("❌ Mã đề án không tồn tại. Nhập lại.");
                 continue;
            }
            if (da.getTrangThai().equalsIgnoreCase("DaHuy")) {
                System.out.println("❌ Không thể chọn đề án đã bị hủy. Nhập lại.");
                continue;
            }
            return da;
        }
    }
     
     // === HÀM BỊ THIẾU MÀ QUANLIPHANCONG CẦN DÙNG ===
     public static NhanVien nhapNhanVien(QuanLiNhanVien qlNV) {
         ArrayList<NhanVien> list = qlNV.getQLNV();
         if (list == null || list.isEmpty()) {
             System.out.println("❌ Danh sách nhân viên trống. Không thể tiếp tục.");
             return null;
         }

         System.out.println("--- Danh sách Nhân viên ---");
         qlNV.xem(); 
         while (true) {
             String maNV = nhapMaNhanVien("Nhập mã nhân viên để chọn (NVxxx): ");

             NhanVien nv = qlNV.TimTheoMa(maNV);
             if (nv != null) {
                 return nv; 
             }
             System.out.println("❌ Mã nhân viên không tồn tại. Nhập lại.");
         }
     }
     // ============================================
    
    

    
    
}


