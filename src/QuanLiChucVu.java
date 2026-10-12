package DoAn;

import java.io.*;
import java.util.*;

public class QuanLiChucVu implements ChucNang<ChucVu> {
    private ArrayList<ChucVu> danhSachChucVu = new ArrayList<>();
    private final String filePath = "DanhSachChucVu.txt";

    public QuanLiChucVu() {
    }

    public void docFile() {
        danhSachChucVu.clear();
        File f = new File(filePath);
        if (!f.exists()) return;

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            int lineNumber = 0;
            while ((line = br.readLine()) != null) {
                lineNumber++;
                if (line.trim().isEmpty()) continue;
                ChucVu cv = new ChucVu();
                try {
                    cv.parseChucVu(line);
                    if (TimTheoMa(cv.getMaCV()) != null) {
                        System.out.println("⚠️ Cảnh báo dòng " + lineNumber + ": Mã chức vụ '" + cv.getMaCV() + "' bị trùng lặp.");
                    } else {
                        danhSachChucVu.add(cv);
                    }
                } catch (Exception e) {
                    System.out.println("⚠️ Lỗi xử lý dòng " + lineNumber + ": " + line + " -> " + e.getMessage());
                }
            }
        } catch (IOException e) {
            System.out.println("❌ Lỗi đọc file '" + filePath + "': " + e.getMessage());
        }
    }

    public void ghiFile() {
        try (FileWriter fw = new FileWriter(filePath)) {
            for (ChucVu cv : danhSachChucVu) {
                fw.write(cv.dinhDangGhiFile().replaceAll(",$", "") + "\n");
            }
        } catch (IOException e) {
            System.out.println("❌ Lỗi ghi file '" + filePath + "': " + e.getMessage());
        }
    }

    public ArrayList<ChucVu> getDanhSach() {
        return danhSachChucVu;
    }

    public ChucVu TimTheoMa(String maCV) {
        if (maCV == null || maCV.trim().isEmpty()) return null;
        String maTimKiem = maCV.trim().toUpperCase();
        for (ChucVu cv : danhSachChucVu) {
            if (cv.getMaCV().equalsIgnoreCase(maTimKiem)) {
                return cv;
            }
        }
        return null;
    }

    @Override
    public void them(ChucVu cv) {
        if (cv == null || TimTheoMa(cv.getMaCV()) != null) {
            System.out.println("❌ Không thể thêm chức vụ null hoặc mã bị trùng.");
            return;
        }
        danhSachChucVu.add(cv);
    }

    @Override
    public void them() {
        ChucVu cv = new ChucVu();
        cv.nhap();
        if (TimTheoMa(cv.getMaCV()) != null) {
            System.out.println("❌ Mã chức vụ '" + cv.getMaCV() + "' đã tồn tại!");
            return;
        }
        danhSachChucVu.add(cv);
        ghiFile();
        System.out.println("✅ Đã thêm chức vụ '" + cv.getTenCV() + "'!");
    }

    public void xoa(QuanLiNhanVien qlnv_param) {
        if (qlnv_param == null) {
            System.out.println("❌ Lỗi: Hàm xoa() cần QuanLiNhanVien.");
            return;
        }

        String ma = KiemTra.nhapMa("Nhập mã chức vụ cần xóa: ", 2, 10);
        ChucVu cvCanXoa = TimTheoMa(ma);

        if (cvCanXoa == null) {
            System.out.println("❌ Không tìm thấy chức vụ có mã '" + ma + "'!");
            return;
        }

        String confirm = KiemTra.nhapChuoi("Nhập 'yes' để xác nhận xóa: ");
        if (!confirm.equalsIgnoreCase("yes")) {
            System.out.println("🚫 Thao tác xóa đã bị hủy.");
            return;
        }

        int demNVBiAnhHuong = 0;
        boolean canGhiFileNV = false;

        for (NhanVien nv : qlnv_param.getQLNV()) {
            if (nv.getMaChucVu() != null && nv.getMaChucVu().getMaCV().equalsIgnoreCase(ma)) {
                if (nv.isTrangThaiLamViec()) {
                    nv.setTrangThaiLamViec(false);
                    demNVBiAnhHuong++;
                }
                nv.setMaChucVu(null);
                canGhiFileNV = true;
            }
        }

        if (canGhiFileNV) {
            qlnv_param.ghiFile();
            if (demNVBiAnhHuong > 0) {
                System.out.println("✅ Đã cập nhật trạng thái 'Đã nghỉ' cho " + demNVBiAnhHuong + " nhân viên.");
            } else {
                System.out.println("ℹ️ Không có nhân viên 'Đang làm việc' nào bị ảnh hưởng trạng thái.");
            }
            System.out.println("✅ Đã gỡ bỏ chức vụ này khỏi hồ sơ của tất cả nhân viên liên quan.");
        } else {
            System.out.println("ℹ️ Chức vụ này không được gán cho bất kỳ nhân viên nào.");
        }

        danhSachChucVu.remove(cvCanXoa);
        ghiFile();
        System.out.println("✅ Đã xóa chức vụ '" + cvCanXoa.getTenCV() + "' khỏi hệ thống thành công!");
    }

    @Override
    public void xoa() {
        System.out.println("❌ Lỗi: Hàm xoa() cần QuanLiNhanVien. Vui lòng gọi từ main.");
    }

    @Override
    public void sua() {
        String ma = KiemTra.nhapMa("Nhập mã chức vụ cần sửa: ", 2, 10);
        ChucVu cvCanSua = TimTheoMa(ma);

        if (cvCanSua == null) {
            System.out.println("❌ Không tìm thấy chức vụ '" + ma + "'!");
            return;
        }

        System.out.println("\n--- Thông tin hiện tại ---");
        cvCanSua.xuat();

        int chon;
        boolean daThayDoi = false;

        do {
            System.out.println("\n=== MENU SỬA [" + cvCanSua.getTenCV() + "] ===");
            System.out.println("1. Sửa Tên Chức Vụ");
            System.out.println("2. Sửa Hệ Số Lương");
            System.out.println("0. Hoàn tất");

            chon = KiemTra.nhapSoNguyenDuong("Chọn (0-2): ");
            while (chon < 0 || chon > 2) {
                System.out.print("❌ Lựa chọn không hợp lệ (0-2): ");
                chon = KiemTra.nhapSoNguyenDuong("");
            }

            switch (chon) {
                case 1:
                    String tenMoi = KiemTra.nhapTen("Nhập Tên CV mới: ", 30);
                    if (!tenMoi.equals(cvCanSua.getTenCV())) {
                        cvCanSua.setTenCV(tenMoi);
                        daThayDoi = true;
                        System.out.println("✅ Tên cập nhật.");
                    } else {
                        System.out.println("ℹ️ Tên không đổi.");
                    }
                    break;

                case 2:
                    double hslMoi = KiemTra.nhapHeSoLuong("Nhập Hệ Số Lương mới (1-10): ");
                    if (hslMoi != cvCanSua.getHeSoLuong()) {
                        cvCanSua.setHeSoLuong(hslMoi);
                        daThayDoi = true;
                        System.out.println("✅ HSL cập nhật.");
                    } else {
                        System.out.println("ℹ️ HSL không đổi.");
                    }
                    break;

                case 0:
                    if (daThayDoi) {
                        ghiFile();
                        System.out.println("✅ Đã lưu thay đổi!");
                    } else {
                        System.out.println("✅ Không có thay đổi nào.");
                    }
                    break;
            }
        } while (chon != 0);
    }

    @Override
    public void timKiem() {
        if (danhSachChucVu.isEmpty()) {
            System.out.println("❌ Danh sách chức vụ trống.");
            return;
        }

        String keyword = KiemTra.nhapChuoi("Nhập từ khóa tìm kiếm (Mã, Tên, HSL): ").trim();
        while (keyword.isEmpty() || !keyword.matches("^[\\p{L}0-9\\s.]+$")) {
            if (keyword.isEmpty()) System.out.println("❌ Từ khóa không được trống.");
            else System.out.println("❌ Từ khóa chứa ký tự không hợp lệ.");
            keyword = KiemTra.nhapChuoi("Nhập lại từ khóa: ").trim();
        }

        keyword = keyword.toLowerCase();
        boolean found = false;

        System.out.println("\n====== KẾT QUẢ TÌM KIẾM CHO '" + keyword + "' ======");
        System.out.printf("| %-10s | %-20s | %-5s      |\n", "Mã CV", "Tên CV", "HSL");
        System.out.println("--------------------------------------------------");

        for (ChucVu cv : danhSachChucVu) {
            String data = (cv.getMaCV() + " " + cv.getTenCV() + " " + cv.getHeSoLuong()).toLowerCase();
            if (data.contains(keyword)) {
                cv.xuat();
                found = true;
            }
        }

        if (!found) {
            System.out.println("       Không tìm thấy chức vụ nào phù hợp.");
        }

        System.out.println("--------------------------------------------------");
    }

    @Override
    public void xem() {
        System.out.println("\n====== DANH SÁCH CHỨC VỤ ======");
        System.out.printf("| %-10s | %-20s | %-5s      |\n", "Mã CV", "Tên CV", "HSL");
        System.out.println("--------------------------------------------------");

        if (danhSachChucVu.isEmpty()) {
            System.out.println("           (Danh sách trống)");
        } else {
            for (ChucVu cv : danhSachChucVu) {
                cv.xuat();
            }
        }

        System.out.println("--------------------------------------------------");
    }

    public void HienThi() {
        xem();
    }
}
