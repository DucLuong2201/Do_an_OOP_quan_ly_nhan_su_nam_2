package DoAn;

import java.io.*;
import java.util.*;

public class QuanLiPhongBan implements ChucNang<PhongBan> {
    private ArrayList<PhongBan> danhSachPhongBan = new ArrayList<>();
    private final String filePath = "DanhSachPhongBan.txt";

    private QuanLiNhanVien qlnv;
    private QuanLiChucVu qlcv;

    public QuanLiPhongBan() {
        this.qlnv = null;
        this.qlcv = null;
    }

    public void setDependencies(QuanLiNhanVien qlnv, QuanLiChucVu qlcv) {
        this.qlnv = qlnv;
        this.qlcv = qlcv;
        capNhatSoNhanVien();
    }

    public void docFile() {
        danhSachPhongBan.clear();
        File f = new File(filePath);
        if (!f.exists()) {
            return;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            int lineNumber = 0;
            while ((line = br.readLine()) != null) {
                lineNumber++;
                if (line.trim().isEmpty()) continue;

                PhongBan pb = new PhongBan();
                try {
                    pb.parsePhongBan(line);
                    if (TimTheoMa(pb.getMaPB()) != null) {
                        System.out.println("⚠️ Cảnh báo dòng " + lineNumber + ": Mã phòng ban '" + pb.getMaPB() + "' bị trùng lặp.");
                    } else {
                        danhSachPhongBan.add(pb);
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
        capNhatSoNhanVien();
        try (FileWriter fw = new FileWriter(filePath)) {
            for (PhongBan pb : danhSachPhongBan) {
                fw.write(pb.dinhDangGhiFile() + "\n");
            }
        } catch (IOException e) {
            System.out.println("❌ Lỗi ghi file '" + filePath + "': " + e.getMessage());
        }
    }

    public void capNhatSoNhanVien() {
        if (this.qlnv == null) {
            return;
        }

        for (PhongBan pb : danhSachPhongBan) {
            pb.setSoNV(0);
        }

        for (NhanVien nv : this.qlnv.getQLNV()) {
            if (nv.isTrangThaiLamViec() && nv.getMaPhongBan() != null) {
                String maPB_NV = nv.getMaPhongBan().getMaPB();
                PhongBan pb_timThay = TimTheoMa(maPB_NV);
                if (pb_timThay != null) {
                    pb_timThay.setSoNV(pb_timThay.getSoNV() + 1);
                }
            }
        }
    }

    public ArrayList<PhongBan> getDanhSach() {
        return danhSachPhongBan;
    }

    public PhongBan TimTheoMa(String maPB) {
        if (maPB == null || maPB.trim().isEmpty()) return null;
        String maTimKiem = maPB.trim().toUpperCase();
        for (PhongBan pb : danhSachPhongBan) {
            if (pb.getMaPB().equalsIgnoreCase(maTimKiem)) {
                return pb;
            }
        }
        return null;
    }

    @Override
    public void them(PhongBan pb) {
        if (pb == null || TimTheoMa(pb.getMaPB()) != null) {
            System.out.println("❌ Không thể thêm phòng ban null hoặc mã bị trùng.");
            return;
        }
        danhSachPhongBan.add(pb);
    }

    @Override
    public void them() {
        if (this.qlnv == null || this.qlcv == null) {
            System.out.println("❌ Lỗi: Cần gọi setDependencies() từ main.");
            return;
        }

        PhongBan pb = new PhongBan();
        pb.nhap();

        if (TimTheoMa(pb.getMaPB()) != null) {
            System.out.println("❌ Mã phòng ban '" + pb.getMaPB() + "' đã tồn tại!");
            return;
        }

        chonTruongPhong(pb);

        danhSachPhongBan.add(pb);
        ghiFile();
        System.out.println("✅ Đã thêm phòng ban '" + pb.getTenPB() + "'!");
    }

    public void xoa(QuanLiNhanVien qlnv_param) {
        if (qlnv_param == null) {
            System.out.println("❌ Lỗi: Hàm xoa() cần QuanLiNhanVien.");
            return;
        }

        String ma = KiemTra.nhapMa("Nhập mã phòng cần xóa: ", 2, 10);
        PhongBan pbCanXoa = TimTheoMa(ma);

        if (pbCanXoa == null) {
            System.out.println("❌ Không tìm thấy phòng ban '" + ma + "'!");
            return;
        }

        System.out.println("Bạn chắc chắn muốn xóa phòng ban '" + pbCanXoa.getTenPB() + "'?");
        System.out.println("NV đang làm việc thuộc phòng này sẽ bị cập nhật trạng thái 'Đã nghỉ'.");
        String confirm = KiemTra.nhapChuoi("Nhập 'yes' để xác nhận: ");

        if (!confirm.equalsIgnoreCase("yes")) {
            System.out.println("🚫 Thao tác xóa đã bị hủy.");
            return;
        }

        int demNhanVienBiAnhHuong = 0;
        boolean canGhiFileNV = false;

        for (NhanVien nv : qlnv_param.getQLNV()) {
            if (nv.getMaPhongBan() != null && nv.getMaPhongBan().getMaPB().equalsIgnoreCase(ma)) {
                if (nv.isTrangThaiLamViec()) {
                    nv.setTrangThaiLamViec(false);
                    demNhanVienBiAnhHuong++;
                    canGhiFileNV = true;
                }
                nv.setMaPhongBan(null);
                canGhiFileNV = true;
            }
        }

        if (canGhiFileNV) {
            qlnv_param.ghiFile();
            if(demNhanVienBiAnhHuong > 0) {
                 System.out.println("✅ Đã cập nhật trạng thái 'Đã nghỉ' cho " + demNhanVienBiAnhHuong + " nhân viên.");
            }
            System.out.println("✅ Đã gỡ bỏ phòng ban này khỏi hồ sơ nhân viên liên quan.");
        } else {
            System.out.println("ℹ️ Phòng ban này không có nhân viên nào liên kết.");
        }

        danhSachPhongBan.remove(pbCanXoa);
        ghiFile();
        System.out.println("✅ Đã xóa phòng ban '" + pbCanXoa.getTenPB() + "'!");
    }

    @Override
    public void xoa() {
        System.out.println("❌ Lỗi: Hàm xoa() cần QuanLiNhanVien. Vui lòng gọi từ main.");
    }

    @Override
    public void sua() {
        if (this.qlnv == null || this.qlcv == null) {
            System.out.println("❌ Lỗi: Cần gọi setDependencies() từ main.");
            return;
        }

        String ma = KiemTra.nhapMa("Nhập mã phòng cần sửa: ", 2, 10);
        PhongBan pbCanSua = TimTheoMa(ma);

        if (pbCanSua == null) {
            System.out.println("❌ Không tìm thấy phòng ban '" + ma + "'!");
            return;
        }

        System.out.println("\n--- Thông tin hiện tại ---");
        pbCanSua.xuat();

        int chon;
        boolean daThayDoi = false;
        do {
            System.out.println("\n=== MENU SỬA [" + pbCanSua.getTenPB() + "] ===");
            System.out.println("1. Sửa Tên PB");
            System.out.println("2. Sửa/Bổ nhiệm Trưởng Phòng");
            System.out.println("3. Sửa Ngày Thành Lập");
            System.out.println("4. Sửa Email");
            System.out.println("0. Hoàn tất");

            chon = KiemTra.nhapSoNguyenDuong("Chọn (0-4): ");
            while (chon < 0 || chon > 4) {
                System.out.print("❌ Lựa chọn không hợp lệ (0-4): ");
                chon = KiemTra.nhapSoNguyenDuong("");
            }

            switch (chon) {
                case 1:
                    String tenMoi = KiemTra.nhapTen("Nhập Tên PB mới: ", 30);
                    if (!tenMoi.equals(pbCanSua.getTenPB())) {
                        pbCanSua.setTenPB(tenMoi); daThayDoi = true;
                        System.out.println("✅ Tên cập nhật.");
                    } else System.out.println("ℹ️ Tên không đổi.");
                    break;
                case 2:
                    String maTpCu = pbCanSua.getMaTP();
                    chonTruongPhong(pbCanSua);
                    if (!pbCanSua.getMaTP().equals(maTpCu)) daThayDoi = true;
                    break;
                case 3:
                    String ngayMoi = KiemTra.nhapNgay("Nhập Ngày TL mới (dd/MM/yyyy): ");
                    if (!ngayMoi.equals(pbCanSua.getNgayThanhLap())) {
                        pbCanSua.setNgayThanhLap(ngayMoi); daThayDoi = true;
                        System.out.println("✅ Ngày TL cập nhật.");
                    } else System.out.println("ℹ️ Ngày TL không đổi.");
                    break;
                case 4:
                    String emailMoi = KiemTra.nhapEmail("Nhập Email mới: ");
                    if (!emailMoi.equals(pbCanSua.getEmail())) {
                        pbCanSua.setEmail(emailMoi); daThayDoi = true;
                        System.out.println("✅ Email cập nhật.");
                    } else System.out.println("ℹ️ Email không đổi.");
                    break;
                case 0:
                    if (daThayDoi) {
                        ghiFile();
                        System.out.println("✅ Đã lưu thay đổi!");
                    } else System.out.println("✅ Hoàn tất sửa (Không có thay đổi).");
                    break;
            }
        } while (chon != 0);
    }

    @Override
    public void timKiem() {
        if (danhSachPhongBan.isEmpty()) {
            System.out.println("❌ Danh sách phòng ban trống.");
            return;
        }
        String keyword = KiemTra.nhapChuoi("Nhập từ khóa tìm kiếm (Mã, Tên, Mã TP,...): ").trim();
        while (keyword.isEmpty() || !keyword.matches("^[\\p{L}0-9\\s@./-]+$")) {
            if (keyword.isEmpty()) {
                System.out.println("❌ Từ khóa không được để trống.");
            } else {
                System.out.println("❌ Từ khóa chứa ký tự không hợp lệ.");
            }
            keyword = KiemTra.nhapChuoi("Nhập từ khóa cần tìm: ").trim();
        }
        keyword = keyword.toLowerCase();

        boolean found = false;
        System.out.println("\n====== KẾT QUẢ TÌM KIẾM CHO '" + keyword + "' ======");
        System.out.printf("| %-5s | %-25s | %-8s | %-12s | %-20s | %-5s |\n", "Ma PB", "Ten PB", "Ma TP", "Ngay TL", "Email", "So NV");
        System.out.println("------------------------------------------------------------------------------------------------");
        for (PhongBan pb : danhSachPhongBan) {
            String data = (pb.getMaPB() + " " + pb.getTenPB() + " " + pb.getMaTP() + " " + pb.getNgayThanhLap() + " " + pb.getEmail() + " " + pb.getSoNV()).toLowerCase();
            if (data.contains(keyword)) {
                pb.xuat();
                found = true;
            }
        }
        if (!found) System.out.println("       Không tìm thấy phòng ban nào phù hợp.");
        System.out.println("------------------------------------------------------------------------------------------------");
    }

    @Override
    public void xem() {
        capNhatSoNhanVien();
        System.out.println("\n====== DANH SÁCH PHÒNG BAN ======");
        System.out.printf("| %-5s | %-25s | %-8s | %-12s | %-20s | %-5s |\n", "Ma PB", "Ten PB", "Ma TP", "Ngay TL", "Email", "So NV");
        System.out.println("------------------------------------------------------------------------------------------------");
        if (danhSachPhongBan.isEmpty()) System.out.println("                       (Danh sách trống)");
        else {
            for (PhongBan pb : danhSachPhongBan) pb.xuat();
        }
        System.out.println("------------------------------------------------------------------------------------------------");
    }

    public void HienThi() {
        xem();
    }

    private void chonTruongPhong(PhongBan pb) {
        if (this.qlnv == null || this.qlcv == null) {
            System.out.println("❌ Lỗi hệ thống: Chưa liên kết QLNV/QLCV.");
            pb.setMaTP("N/A");
            return;
        }
        ChucVu chucVuTP = this.qlcv.TimTheoMa("TP");
        if (chucVuTP == null) {
            System.out.println("❌ Lỗi hệ thống: Không tìm thấy chức vụ 'TP'.");
            pb.setMaTP("N/A");
            return;
        }

        while (true) {
            String inputMaTP = KiemTra.nhapChuoiChoPhepRong(
                "Nhập Mã Trưởng phòng (NVxxx) cho [" + pb.getTenPB() + "] (bỏ trống hoặc '0' nếu không có): "
            ).toUpperCase();

            if (inputMaTP.isEmpty() || inputMaTP.equals("0")) {
                handleNoManager(pb);
                return;
            }

            if (!inputMaTP.matches("^NV\\d{3}$")) {
                System.out.println("❌ Mã NV không đúng định dạng (NVxxx).");
                continue;
            }

            NhanVien nvDuocChon = this.qlnv.TimTheoMa(inputMaTP);

            if (nvDuocChon != null) {
                if (!nvDuocChon.isTrangThaiLamViec()) {
                    System.out.println("❌ NV '" + inputMaTP + "' (" + nvDuocChon.getHoten() + ") đã nghỉ việc.");
                    continue;
                }

                System.out.println("▶ Tìm thấy: " + nvDuocChon.getHoten() + " (CV: " + (nvDuocChon.getMaChucVu()!=null?nvDuocChon.getMaChucVu().getMaCV():"N/A") + ", PB: " + (nvDuocChon.getMaPhongBan() != null ? nvDuocChon.getMaPhongBan().getMaPB() : "N/A") + ")");
                String confirm = KiemTra.nhapChuoi("Xác nhận bổ nhiệm làm TP? (yes/no): ");

                if (confirm.equalsIgnoreCase("yes")) {
                    performAppointment(pb, nvDuocChon, chucVuTP);
                    return;
                } else {
                    System.out.println("🚫 Đã hủy bổ nhiệm. Nhập lại hoặc bỏ trống/'0'.");
                }
            } else {
                System.out.println("❌ Không tìm thấy NV có mã '" + inputMaTP + "'.");
            }
        }
    }

    private void handleNoManager(PhongBan pb) {
        String maTpCu = pb.getMaTP();

        if (maTpCu != null && !maTpCu.equals("N/A")) {
             NhanVien nvTpCu = this.qlnv.TimTheoMa(maTpCu);
             if (nvTpCu != null) {
                 ChucVu chucVuNV = this.qlcv.TimTheoMa("NV");
                 if(chucVuNV != null && nvTpCu.getMaChucVu() != null && nvTpCu.getMaChucVu().getMaCV().equals("TP")) {
                      System.out.println("  -> Giáng chức TP cũ (" + nvTpCu.getHoten() + " - " + maTpCu + ") xuống làm Nhân viên.");
                      nvTpCu.setMaChucVu(chucVuNV);
                      this.qlnv.ghiFile();
                 }
             } else {
                  System.out.println("  ⚠️ Không tìm thấy thông tin của TP cũ ("+maTpCu+") để giáng chức.");
             }
             pb.setMaTP("N/A");
             System.out.println("✅ Đã gỡ bỏ TP cũ (" + maTpCu + "). Phòng ban này sẽ không có TP.");
        } else {
             pb.setMaTP("N/A");
             System.out.println("✅ Phòng ban này sẽ không có TP.");
        }
    }

    private void performAppointment(PhongBan pbMoi, NhanVien nvDuocChon, ChucVu chucVuTP) {
        System.out.println("🔄 Đang bổ nhiệm " + nvDuocChon.getHoten() + " làm TP phòng [" + pbMoi.getTenPB() + "]...");

        String maNVMoi = nvDuocChon.getMaNhanVien();
        String maTpCu_pbMoi = pbMoi.getMaTP();
        boolean daThayDoiNV = false;
        boolean daThayDoiPB = false;

        if (maTpCu_pbMoi != null && !maTpCu_pbMoi.equals("N/A") && !maTpCu_pbMoi.equalsIgnoreCase(maNVMoi)) {
            NhanVien nvTpCu = this.qlnv.TimTheoMa(maTpCu_pbMoi);
            if (nvTpCu != null) {
                ChucVu chucVuNV = this.qlcv.TimTheoMa("NV");
                if (chucVuNV != null) {
                    if (nvTpCu.getMaChucVu() != null && nvTpCu.getMaChucVu().getMaCV().equals("TP") &&
                        nvTpCu.getMaPhongBan() != null && nvTpCu.getMaPhongBan().getMaPB().equals(pbMoi.getMaPB())) {
                        System.out.println("  -> Giáng chức TP cũ (" + nvTpCu.getHoten() + " - " + maTpCu_pbMoi + ") xuống làm Nhân viên.");
                        nvTpCu.setMaChucVu(chucVuNV);
                        daThayDoiNV = true;
                    }
                } else {
                    System.out.println("  ⚠️ Lỗi: Không tìm thấy chức vụ 'NV' để giáng chức TP cũ.");
                }
            } else {
                System.out.println("  ⚠️ Không tìm thấy thông tin của TP cũ (" + maTpCu_pbMoi + ") để giáng chức.");
            }
        }

        PhongBan pbCuCuaNVMoi = null;
        for (PhongBan pbTim : danhSachPhongBan) {
            if (!pbTim.getMaPB().equals(pbMoi.getMaPB()) &&
                pbTim.getMaTP() != null &&
                pbTim.getMaTP().equalsIgnoreCase(maNVMoi)) {
                pbCuCuaNVMoi = pbTim;
                break;
            }
        }
        if (pbCuCuaNVMoi != null) {
            System.out.println("  -> Gỡ bỏ vai trò TP của " + maNVMoi + " tại phòng cũ [" + pbCuCuaNVMoi.getTenPB() + "].");
            pbCuCuaNVMoi.setMaTP("N/A");
            daThayDoiPB = true;
        }

        pbMoi.setMaTP(maNVMoi);
        daThayDoiPB = true;

        if (nvDuocChon.getMaPhongBan() == null || !nvDuocChon.getMaPhongBan().getMaPB().equals(pbMoi.getMaPB())) {
            nvDuocChon.setMaPhongBan(pbMoi);
            daThayDoiNV = true;
            System.out.println("  -> Đã luân chuyển NV sang phòng [" + pbMoi.getTenPB() + "].");
        }

        if (nvDuocChon.getMaChucVu() == null || !nvDuocChon.getMaChucVu().getMaCV().equals(chucVuTP.getMaCV())) {
            nvDuocChon.setMaChucVu(chucVuTP);
            daThayDoiNV = true;
            System.out.println("  -> Đã cập nhật chức vụ thành [" + chucVuTP.getTenCV() + "].");
        }

        if (daThayDoiNV) {
            this.qlnv.ghiFile();
            System.out.println("  -> Đã lưu thay đổi thông tin NV.");
        }

        System.out.println("✅ Hoàn tất bổ nhiệm!");
    }
}