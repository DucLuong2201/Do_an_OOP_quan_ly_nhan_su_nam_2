package DoAn;

import java.util.*;
import java.io.*;
import java.text.DecimalFormat;

public class QuanLiNhanVien implements ChucNang<NhanVien> {
    private final ArrayList<NhanVien> qlnv = new ArrayList<>();
    
    QuanLiPhanCong qlpc;
    private final QuanLiPhongBan qlpb;
    private final QuanLiChucVu qlcv;

    public QuanLiNhanVien(QuanLiPhongBan qlpb, QuanLiChucVu qlcv) {
        this.qlpb = qlpb;
        this.qlcv = qlcv;
    }
    
    public void setQuanLiPhanCong(QuanLiPhanCong qlpc) {
        this.qlpc = qlpc;
    }
    
    

    @Override
    public void xem() {
        if (qlnv.isEmpty()) {
            System.out.println("⚠ Danh sách nhân viên rỗng!");
            return;
        }

        System.out.println("\n=== DANH SÁCH NHÂN VIÊN ===");
        
        System.out.printf("| %-8s | %-20s | %-7s | %-7s | %-12s | %-9s | %-9s | %-12s | %-9s | %-15s | %-45s | %-18s |\n",
                "Mã NV", "Họ Tên", "Phòng", "Chức vụ", "Lương CB", "HSL Thực", "Thâm niên", "Phụ cấp", "Năm sinh", "Nơi sinh", "Địa chỉ", "Trạng thái");
        
        System.out.println("|----------|----------------------|---------|---------|--------------|-----------|-----------|--------------|-----------|-----------------|-----------------------------------------------|------------------|");

        for (NhanVien nv : qlnv) {
            
            System.out.printf("| %-8s | %-20s | %-7s | %-7s | %-12.0f | %-9.1f | %-9d | %-12.0f | %-9d | %-15s | %-45s | %-18s |\n",
                    nv.getMaNhanVien(),
                    nv.getHoten(),
                    (nv.getMaPhongBan() != null ? nv.getMaPhongBan().getMaPB() : "N/A"),
                    (nv.getMaChucVu() != null ? nv.getMaChucVu().getMaCV() : "N/A"),
                    nv.getLuongCoBan(),
                    nv.getHeSoLuongThucTe(),
                    nv.getThamNien(),
                    nv.getPhuCap(),
                    nv.getNamsinh(),
                    (nv.getNs() != null ? nv.getNs().getTinh() : "N/A"),
                    nv.getDiachi(),
                    (nv.isTrangThaiLamViec() ? "Đang làm việc" : "Đã nghỉ") 
            );
        }
        
        System.out.println("|----------|----------------------|---------|---------|--------------|-----------|-----------|--------------|-----------|-----------------|-----------------------------------------------|------------------|");
        System.out.println("Tổng số nhân viên: " + qlnv.size());
    }

    @Override
    public void them(NhanVien nv) {
        qlnv.add(nv);
        System.out.println("Da them nhan vien!");
    }
    
    @Override
    public void them() {
        System.out.println("=== THÊM NHÂN VIÊN MỚI ===");
        NhanVien nv = new NhanVien();

        String maNV = KiemTra.nhapMaNhanVien("Nhập mã nhân viên (NVxxx): ");
        while (tonTaiMa(maNV)) {
            System.out.print("⚠ Mã nhân viên đã tồn tại, nhập lại: ");
            maNV = KiemTra.nhapMaNhanVien("");
        }
        nv.setMaNhanVien(maNV);
        nv.setHoten(KiemTra.nhapHoTenNguoi("Nhập họ tên: "));
        
        this.qlpb.xem(); 
        String maPB = KiemTra.nhapMa("Nhập Mã Phòng Ban từ danh sách trên: ", 2, 10);
        PhongBan pbChon = this.qlpb.TimTheoMa(maPB);
        while (pbChon == null) {
            System.out.print("❌ Mã phòng ban không tồn tại. Nhập lại: ");
            maPB = KiemTra.nhapMa("", 2, 10);
            pbChon = this.qlpb.TimTheoMa(maPB);
        }
        System.out.println("✅ Đã chọn phòng: " + pbChon.getTenPB());

        this.qlcv.xem();
        String maCV = KiemTra.nhapMa("Nhập Mã Chức Vụ từ danh sách trên: ", 2, 10);
        ChucVu cvChon = this.qlcv.TimTheoMa(maCV);
        while (cvChon == null) {
            System.out.print("❌ Mã chức vụ không tồn tại. Nhập lại: ");
            maCV = KiemTra.nhapMa("", 2, 10);
            cvChon = this.qlcv.TimTheoMa(maCV);
        }
        System.out.println("✅ Đã chọn chức vụ: " + cvChon.getTenCV() + " (HSL: " + cvChon.getHeSoLuong() + ")");
        
        boolean daThayDoiFileNV = false;
        boolean daThayDoiFilePB = false;

        if (cvChon.getMaCV().equals("TP")) {
            String maTpExisting = pbChon.getMaTP();
            
            if (maTpExisting != null && !maTpExisting.equals("N/A") && !maTpExisting.equalsIgnoreCase(maNV)) {
                 NhanVien nvTpExisting = this.TimTheoMa(maTpExisting);
                 if (nvTpExisting != null) {
                     ChucVu chucVuNV = this.qlcv.TimTheoMa("NV");
                     if (chucVuNV != null) {
                         System.out.println("  -> Phòng [" + pbChon.getTenPB() + "] đã có TP là " + nvTpExisting.getHoten() + " (" + maTpExisting + ").");
                         System.out.println("  -> Tự động giáng chức TP cũ xuống làm Nhân viên.");
                         nvTpExisting.setMaChucVu(chucVuNV);
                         daThayDoiFileNV = true;
                     } else {
                         System.out.println("  ⚠️ Lỗi: Không tìm thấy chức vụ 'NV' để giáng chức TP cũ.");
                     }
                 } else {
                      System.out.println("  ⚠️ Không tìm thấy thông tin TP hiện tại ("+maTpExisting+") để giáng chức.");
                 }
            }
            System.out.println("  -> Bổ nhiệm " + nv.getHoten() + " làm TP phòng [" + pbChon.getTenPB() + "].");
            pbChon.setMaTP(maNV);
            daThayDoiFilePB = true;
        }
        
        nv.setMaPhongBan(pbChon);
        nv.setMaChucVu(cvChon);
        
        nv.setLuongCoBan(KiemTra.nhapSoThucDuong("Nhập lương cơ bản (>0): "));
        nv.setThamNien(KiemTra.nhapSoNguyenDuong("Nhập thâm niên (số năm): "));
        nv.setPhuCap(0);
        nv.setNamsinh(KiemTra.nhapNamSinhNguoi("Nhập năm sinh (1900-2025): "));
        nv.setDiachi(KiemTra.nhapDiaChiNguoi("Nhập địa chỉ: "));
        String noiSinhStr = KiemTra.nhapChuoi("Nhập nơi sinh (Tỉnh/Thành phố): ");
        nv.setNs(NoiSinh.parse(noiSinhStr));
        nv.setTrangThaiLamViec(true);
        
        qlnv.add(nv);
        System.out.println("✅ Thêm nhân viên thành công!");

        if (daThayDoiFileNV) {
            this.ghiFile();
            System.out.println("  -> Đã cập nhật file nhân viên do có thay đổi chức vụ TP cũ.");
        }
        if (daThayDoiFilePB) {
            this.qlpb.ghiFile();
             System.out.println("  -> Đã cập nhật file phòng ban do có thay đổi Trưởng phòng.");
        }
    }


    @Override
    public void xoa() {
        if (qlnv.isEmpty()) {
            System.out.println("⚠ Danh sách rỗng!");
            return;
        }
        String ma = KiemTra.nhapMaNhanVien("Nhập mã nhân viên cần xóa (NVxxx): ");
        if (!tonTaiMa(ma)) {
            System.out.println("❌ Không tìm thấy nhân viên có mã " + ma);
            return;
        }

        Iterator<NhanVien> itNV = qlnv.iterator();
        boolean daXoaNV = false;
        while (itNV.hasNext()) {
            NhanVien nv = itNV.next();
            if (nv.getMaNhanVien().equalsIgnoreCase(ma)) {
                itNV.remove();
                daXoaNV = true;
                System.out.println("✅ Đã xóa nhân viên " + nv.getHoten() + " (" + ma + ")");
                break;
            }
        }

        if (!daXoaNV) {
            System.out.println("❌ Không thể xóa nhân viên " + ma);
            return;
        }

        if (qlpc != null) {
            Iterator<PhanCong> itPC = qlpc.getDanhSach().iterator();
            boolean daXoaPC = false;
            while (itPC.hasNext()) {
                PhanCong pc = itPC.next();
                if (pc.getNhanVien() != null &&
                    pc.getNhanVien().getMaNhanVien().equalsIgnoreCase(ma)) {
                    itPC.remove();
                    daXoaPC = true;
                }
            }

            if (daXoaPC) {
                System.out.println("✅ Đã xóa các phân công của nhân viên " + ma);
            } else {
                System.out.println("ℹ️ Không có phân công nào của nhân viên " + ma);
            }
       } else {
           System.out.println("⚠ Chưa liên kết với QuanLiPhanCong (qlpc == null)");
       }
    }
    
    @Override
    public void sua() {
        if (qlnv.isEmpty()) {
            System.out.println("⚠ Danh sách rỗng!"); return;
        }
        String ma = KiemTra.nhapMaNhanVien("Nhập mã NV cần sửa (NVxxx): ");
        NhanVien nvCanSua = TimTheoMa(ma);

        if (nvCanSua == null) {
            System.out.println("❌ Không tìm thấy NV '" + ma + "'."); return;
        }

        ChucVu chucVuTP = this.qlcv.TimTheoMa("TP");
        ChucVu chucVuNV = this.qlcv.TimTheoMa("NV");
        if (chucVuTP == null) {
            System.out.println("❌ Lỗi hệ thống: Không tìm thấy chức vụ 'TP'. Không thể sửa.");
            return;
        }
         if (chucVuNV == null) {
            System.out.println("❌ Lỗi hệ thống: Không tìm thấy chức vụ 'NV'. Không thể sửa.");
            return;
        }

        System.out.println("Tìm thấy:");
        nvCanSua.xuat();

        int chon;
        boolean daThayDoiFileNV = false;
        boolean daThayDoiFilePB = false;

        do {
            System.out.println("\n--- MENU SỬA [" + nvCanSua.getHoten() + "] ---");
            System.out.println(" 1. Họ Tên");
            System.out.println(" 2. Phòng ban");
            System.out.println(" 3. Chức vụ");
            System.out.println(" 4. Lương cơ bản");
            System.out.println(" 5. Thâm niên");
            System.out.println(" 6. Phụ cấp");
            System.out.println(" 7. Năm sinh");
            System.out.println(" 8. Nơi sinh (Tỉnh/TP)");
            System.out.println(" 9. Địa chỉ");
            System.out.println("10. Trạng thái làm việc");
            System.out.println("11. Sửa Hệ số lương (của Chức vụ hiện tại)");
            System.out.println(" 0. Hoàn tất");

            chon = KiemTra.nhapSoNguyenDuong("Chọn (0-11): ");
            while (chon < 0 || chon > 11) {
                System.out.print("❌ Lựa chọn không hợp lệ (0-11): ");
                chon = KiemTra.nhapSoNguyenDuong("");
            }

            switch (chon) {
                case 1:
                    nvCanSua.setHoten(KiemTra.nhapHoTenNguoi("Nhập họ tên mới: "));
                    daThayDoiFileNV = true;
                    System.out.println("✅ Họ tên cập nhật.");
                    break;

                case 2:
                    PhongBan pbCu = nvCanSua.getMaPhongBan();
                    ChucVu cvHienTai = nvCanSua.getMaChucVu();
                    boolean dangLaTpCu = false;

                    if (pbCu != null && cvHienTai != null && cvHienTai.getMaCV().equals("TP") &&
                        pbCu.getMaTP() != null && pbCu.getMaTP().equalsIgnoreCase(nvCanSua.getMaNhanVien()))
                    {
                        dangLaTpCu = true;
                    }

                    this.qlpb.xem();
                    String maPBMoi = KiemTra.nhapMa("Chọn Mã PB mới: ", 2, 10);
                    PhongBan pbMoi = this.qlpb.TimTheoMa(maPBMoi);
                    while (pbMoi == null) {
                         System.out.print("❌ Mã PB không tồn tại. Chọn lại: ");
                         maPBMoi = KiemTra.nhapMa("", 2, 10);
                         pbMoi = this.qlpb.TimTheoMa(maPBMoi);
                    }

                    if (pbCu == null || !pbCu.getMaPB().equals(pbMoi.getMaPB())) {
                        
                        if (dangLaTpCu) {
                            System.out.println("  -> Do chuyển phòng, gỡ bỏ vai trò TP của " + nvCanSua.getMaNhanVien() + " tại phòng [" + pbCu.getTenPB() + "].");
                            pbCu.setMaTP("N/A");
                            daThayDoiFilePB = true;
                        }

                        nvCanSua.setMaPhongBan(pbMoi);
                        daThayDoiFileNV = true;
                        System.out.println("✅ Đã chuyển NV sang phòng [" + pbMoi.getTenPB() + "].");

                        if (dangLaTpCu) {
                            System.out.println("  -> Do chuyển phòng, chức vụ Trưởng phòng bị hạ xuống Nhân viên.");
                            nvCanSua.setMaChucVu(chucVuNV);
                        }
                    } else {
                        System.out.println("ℹ️ PB không đổi.");
                    }
                    break;

                case 3:
                    ChucVu cvCu = nvCanSua.getMaChucVu();
                    PhongBan pbHienTai = nvCanSua.getMaPhongBan();

                    this.qlcv.xem();
                    String maCVMoi = KiemTra.nhapMa("Chọn Mã CV mới: ", 2, 10);
                    ChucVu cvMoi = this.qlcv.TimTheoMa(maCVMoi);
                    while (cvMoi == null) {
                         System.out.print("❌ Mã CV không tồn tại. Chọn lại: ");
                         maCVMoi = KiemTra.nhapMa("", 2, 10);
                         cvMoi = this.qlcv.TimTheoMa(maCVMoi);
                    }

                    if (cvCu == null || !cvCu.getMaCV().equals(cvMoi.getMaCV())) {
                        boolean choPhepThayDoi = true;

                        if (cvCu != null && cvCu.getMaCV().equals("TP") && !cvMoi.getMaCV().equals("TP")) {
                            if (pbHienTai != null && pbHienTai.getMaTP() != null && pbHienTai.getMaTP().equalsIgnoreCase(nvCanSua.getMaNhanVien())) {
                                System.out.println("  -> Do thay đổi chức vụ, gỡ bỏ vai trò TP của " + nvCanSua.getMaNhanVien() + " tại phòng [" + pbHienTai.getTenPB() + "].");
                                pbHienTai.setMaTP("N/A");
                                daThayDoiFilePB = true;
                            }
                        }
                        else if (cvMoi.getMaCV().equals("TP") && (cvCu == null || !cvCu.getMaCV().equals("TP"))) {
                            if (pbHienTai == null) {
                                System.out.println("❌ Không thể bổ nhiệm TP cho NV chưa thuộc phòng ban nào. Vui lòng sửa phòng ban trước.");
                                choPhepThayDoi = false;
                            } else {
                                String maTpExisting = pbHienTai.getMaTP();
                                if (maTpExisting != null && !maTpExisting.equals("N/A") && !maTpExisting.equalsIgnoreCase(nvCanSua.getMaNhanVien())) {
                                    NhanVien nvTpExisting = this.TimTheoMa(maTpExisting);
                                    if (nvTpExisting != null) {
                                        System.out.println("  -> Phòng [" + pbHienTai.getTenPB() + "] đã có TP là " + nvTpExisting.getHoten() + " (" + maTpExisting + ").");
                                        System.out.println("  -> Tự động giáng chức TP cũ xuống làm Nhân viên.");
                                        nvTpExisting.setMaChucVu(chucVuNV);
                                        daThayDoiFileNV = true;
                                    } else {
                                         System.out.println("  ⚠️ Không tìm thấy thông tin TP hiện tại ("+maTpExisting+") để giáng chức.");
                                    }
                                }
                                System.out.println("  -> Bổ nhiệm " + nvCanSua.getHoten() + " làm TP phòng [" + pbHienTai.getTenPB() + "].");
                                pbHienTai.setMaTP(nvCanSua.getMaNhanVien());
                                daThayDoiFilePB = true;
                            }
                        }

                        if (choPhepThayDoi) {
                            nvCanSua.setMaChucVu(cvMoi);
                            daThayDoiFileNV = true;
                            System.out.println("✅ Chức vụ cập nhật thành [" + cvMoi.getTenCV() + "].");
                        }

                    } else {
                         System.out.println("ℹ️ CV không đổi.");
                    }
                    break;

                case 4:
                    nvCanSua.setLuongCoBan(KiemTra.nhapSoThucDuong("Nhập Lương CB mới (>0): "));
                    daThayDoiFileNV = true;
                    System.out.println("✅ Lương CB cập nhật.");
                    break;
                case 5:
                    nvCanSua.setThamNien(KiemTra.nhapSoNguyenDuong("Nhập thâm niên mới (số năm): "));
                    daThayDoiFileNV = true;
                    System.out.println("✅ Thâm niên cập nhật.");
                    break;
                case 6:
                    nvCanSua.setPhuCap(KiemTra.nhapSoNguyenDuong("Nhập phụ cấp mới (>=0): "));
                    daThayDoiFileNV = true;
                    System.out.println("✅ Phụ cấp cập nhật.");
                    break;
                case 7:
                    nvCanSua.setNamsinh(KiemTra.nhapNamSinhNguoi("Nhập năm sinh mới (1900-2025): "));
                    daThayDoiFileNV = true;
                    System.out.println("✅ Năm sinh cập nhật.");
                    break;
                case 8:
                    String noiSinhMoiStr = KiemTra.nhapChuoi("Nhập nơi sinh mới (Tỉnh/TP): ");
                    nvCanSua.setNs(NoiSinh.parse(noiSinhMoiStr));
                    daThayDoiFileNV = true;
                    System.out.println("✅ Nơi sinh cập nhật.");
                    break;
                case 9:
                    nvCanSua.setDiachi(KiemTra.nhapDiaChiNguoi("Nhập địa chỉ mới: "));
                    daThayDoiFileNV = true;
                    System.out.println("✅ Địa chỉ cập nhật.");
                    break;
                case 10:
                    System.out.println("Trạng thái hiện tại: " + (nvCanSua.isTrangThaiLamViec() ? "Đang làm việc" : "Đã nghỉ"));
                    int ttChon = KiemTra.nhapSoNguyenDuong("Chọn trạng thái mới (1: Đang làm việc, 2: Đã nghỉ): ");
                    while(ttChon < 1 || ttChon > 2) {
                          System.out.print("❌ Chọn 1 hoặc 2: ");
                          ttChon = KiemTra.nhapSoNguyenDuong("");
                     }
                     boolean trangThaiMoi = (ttChon == 1);
                     if (nvCanSua.isTrangThaiLamViec() != trangThaiMoi) {
                         nvCanSua.setTrangThaiLamViec(trangThaiMoi);
                         daThayDoiFileNV=true;
                         this.qlpb.capNhatSoNhanVien();
                         daThayDoiFilePB = true;
                         System.out.println("✅ Trạng thái cập nhật.");
                     } else System.out.println("ℹ️ Trạng thái không đổi.");
                    break;
                case 11:
                    if(nvCanSua.getMaChucVu() == null) {
                         System.out.println("❌ Nhân viên chưa có chức vụ để sửa HSL.");
                         break;
                    }
                    System.out.println("CV hiện tại: " + nvCanSua.getMaChucVu().getTenCV() + " (" + nvCanSua.getMaChucVu().getMaCV() + ")");
                    double hslMoi = KiemTra.nhapHeSoLuong("Nhập HSL mới cho chức vụ này (1–10): ");
                    nvCanSua.getMaChucVu().setHeSoLuong(hslMoi);
                    System.out.println("✅ HSL cập nhật (Lưu ý: cần ghi lại file Chức vụ từ menu chính).");
                    nvCanSua.setMaChucVu(nvCanSua.getMaChucVu());
                    daThayDoiFileNV = true;
                    break;
                case 0:
                     if (daThayDoiFileNV) {
                         System.out.println("✅ Hoàn tất sửa (Nhớ ghi file NV từ menu chính nếu chưa tự động).");
                     }
                     if (daThayDoiFilePB) {
                         System.out.println("✅ Thay đổi liên quan đến Trưởng phòng/Số NV đã được cập nhật (Nhớ ghi file PB từ menu chính nếu chưa tự động).");
                     }
                     if (!daThayDoiFileNV && !daThayDoiFilePB) {
                         System.out.println("✅ Hoàn tất sửa (Không có thay đổi).");
                     }
                     break;
            }
             if (daThayDoiFileNV) this.ghiFile();
             if (daThayDoiFilePB) this.qlpb.ghiFile();

        } while (chon != 0);
    }
        
    
    @Override
    public void timKiem() {
        if (qlnv.isEmpty()) {
            System.out.println("⚠ Danh sách nhân viên rỗng!");
            return;
        }

        System.out.println("\n=== TÌM KIẾM NHÂN VIÊN THEO TỪ KHÓA ===");
        String keyword = KiemTra.nhapChuoi("Nhập từ khóa cần tìm (Mã NV, Tên, Phòng, Chức vụ,...): ").trim();

        while (keyword.isEmpty() || !keyword.matches("^[\\p{L}0-9\\s@./-]+$")) {
            if (keyword.isEmpty()) {
                 System.out.println("❌ Từ khóa không được để trống.");
            } else {
                 System.out.println("❌ Từ khóa chứa ký tự không hợp lệ.");
            }
            keyword = KiemTra.nhapChuoi("Nhập lại từ khóa: ").trim();
        }
        String keywordLower = keyword.toLowerCase(); 

        ArrayList<NhanVien> ketQua = new ArrayList<>();
        for (NhanVien nv : qlnv) {
            String data = (
                nv.getMaNhanVien() + " " +
                nv.getHoten() + " " +
                (nv.getMaPhongBan() != null ? nv.getMaPhongBan().getMaPB() : "") + " " + 
                (nv.getMaPhongBan() != null ? nv.getMaPhongBan().getTenPB() : "") + " " +
                (nv.getMaChucVu() != null ? nv.getMaChucVu().getMaCV() : "") + " " +  
                (nv.getMaChucVu() != null ? nv.getMaChucVu().getTenCV() : "") + " " +  
                nv.getLuongCoBan() + " " +
                nv.getHeSoLuongThucTe() + " " +
                nv.getThamNien() + " " +
                nv.getPhuCap() + " " +
                nv.getNamsinh() + " " +
                (nv.getNs() != null ? nv.getNs().getTinh() : "") + " " + 
                nv.getDiachi() + " " +
                (nv.isTrangThaiLamViec() ? "dang lam viec" : "da nghi") 
            ).toLowerCase(); 

            if (data.contains(keywordLower)) {
                ketQua.add(nv);
            }
        }

        if (ketQua.isEmpty()) {
            System.out.println("\n❌ Không tìm thấy nhân viên nào khớp với từ khóa \"" + keyword + "\"!");
        } else {
            System.out.println("\n✅ Tìm thấy " + ketQua.size() + " nhân viên khớp với từ khóa \"" + keyword + "\":");
            
            System.out.printf("| %-8s | %-20s | %-7s | %-7s | %-12s | %-9s | %-9s | %-12s | %-9s | %-15s | %-45s | %-18s |\n", "Mã NV", "Họ Tên", "Phòng", "Chức vụ", "Lương CB", "HSL Thực", "Thâm niên", "Phụ cấp", "Năm sinh", "Nơi sinh", "Địa chỉ", "Trạng thái");
            System.out.println("|----------|----------------------|---------|---------|--------------|-----------|-----------|--------------|-----------|-----------------|-----------------------------------------------|------------------|");
            
            for (NhanVien nv : ketQua) {
                System.out.printf("| %-8s | %-20s | %-7s | %-7s | %-12.0f | %-9.1f | %-9d | %-12.0f | %-9d | %-15s | %-45s | %-18s |\n",
                        nv.getMaNhanVien(), nv.getHoten(),
                        (nv.getMaPhongBan()!=null?nv.getMaPhongBan().getMaPB():"N/A"),
                        (nv.getMaChucVu()!=null?nv.getMaChucVu().getMaCV():"N/A"),
                        nv.getLuongCoBan(), nv.getHeSoLuongThucTe(), nv.getThamNien(), nv.getPhuCap(),
                        nv.getNamsinh(),
                        (nv.getNs() != null ? nv.getNs().getTinh() : "N/A"),
                        nv.getDiachi(),
                        nv.isTrangThaiLamViec() ? "Đang làm việc" : "Đã nghỉ");
            }
            System.out.println("|----------|----------------------|---------|---------|--------------|-----------|-----------|--------------|-----------|-----------------|-----------------------------------------------|------------------|");
        }
    }

    public void ghiFile() {
        if (qlnv.isEmpty()) {
            System.out.println("ℹ️ Danh sách NV rỗng, không ghi file.");
            return;
        }

        try (FileWriter fw = new FileWriter("DanhSachNhanVien.txt")) {
            for (NhanVien nv : qlnv) {
                String maPB = (nv.getMaPhongBan() != null) ? nv.getMaPhongBan().getMaPB() : "N/A";
                String maCV = (nv.getMaChucVu() != null) ? nv.getMaChucVu().getMaCV() : "N/A";
                double hslCV = (nv.getMaChucVu() != null) ? nv.getMaChucVu().getHeSoLuong() : 0.0;
                String tinh = (nv.getNs() != null) ? nv.getNs().getTinh() : "Chưa rõ";

                fw.write(String.join(",",
                        nv.getMaNhanVien(),
                        nv.getHoten(),
                        maPB,
                        maCV,
                        String.valueOf(nv.getLuongCoBan()),
                        String.valueOf(hslCV),
                        String.valueOf(nv.getThamNien()),
                        String.valueOf(nv.getHeSoLuongThucTe()),
                        String.valueOf(nv.getPhuCap()),
                        String.valueOf(nv.tinhLuong()),
                        
                        tinh,
                        String.valueOf(nv.getNamsinh()),
                        nv.getDiachi(),
                        String.valueOf(nv.isTrangThaiLamViec())
                        ) + "\n");
            }
            System.out.println("✅ Ghi file DanhSachNhanVien.txt thành công (" + qlnv.size() + " NV).");
        } catch (IOException | NullPointerException e ) {
            System.out.println("❌ Lỗi khi ghi file NV: " + e.getMessage());
             if (e instanceof NullPointerException) e.printStackTrace();
        }
    }
    
    public void docFile() {
        File f = new File("DanhSachNhanVien.txt");
        if (!f.exists()) {
            System.out.println("⚠ File NV không tồn tại!");
            return;
        }
        if (this.qlpb == null || this.qlcv == null) {
            System.out.println("❌ Lỗi: Cần inject qlpb, qlcv.");
            return;
        }
        try (BufferedReader br = new BufferedReader(new FileReader(f))) {
            qlnv.clear();
            String line;
            int ln = 0;
            while ((line = br.readLine()) != null) {
                ln++;
                if (line.trim().isEmpty()) continue;
                
                String[] arr = line.split(",");
                
                if (arr.length >= 14) { 
                    try {
                        String maNV = arr[0].trim();
                        String hoTen = arr[1].trim();
                        String maPB = arr[2].trim();
                        String maCV = arr[3].trim();
                        double lcb = Double.parseDouble(arr[4].trim());
                        double hslFile = 0;
                        try {
                            hslFile = Double.parseDouble(arr[5].trim());
                        } catch (Exception e) {}
                        int tn = Integer.parseInt(arr[6].trim());
                        double pc = Double.parseDouble(arr[8].trim());
                        
                        String tinh = arr[10].trim();
                        int nsInt = Integer.parseInt(arr[11].trim());
                        
                        boolean tt = Boolean.parseBoolean(arr[arr.length - 1].trim()); 

                        StringBuilder diaChiBuilder = new StringBuilder();
                        for (int i = 12; i < arr.length - 1; i++) {
                            diaChiBuilder.append(arr[i].trim());
                            if (i < arr.length - 2) {
                                diaChiBuilder.append(", ");
                            }
                        }
                        String dc = diaChiBuilder.toString();

                        PhongBan pb = this.qlpb.TimTheoMa(maPB);
                        if (pb == null) {
                            System.out.println("⚠️ Dòng " + ln + ": Mã PB '" + maPB + "' ko tồn tại.");
                            pb = new PhongBan(maPB);
                        }
                        ChucVu cv = this.qlcv.TimTheoMa(maCV);
                        if (cv == null) {
                            System.out.println("⚠️ Dòng " + ln + ": Mã CV '" + maCV + "' ko tồn tại.");
                            cv = new ChucVu(maCV, "Lỗi", hslFile);
                        }
                        NoiSinh nsObj = NoiSinh.parse(tinh);

                        NhanVien nv = new NhanVien(maNV, hoTen, pb, cv, lcb, tn, nsObj, nsInt, dc, pc);
                        nv.setTrangThaiLamViec(tt);
                        qlnv.add(nv);

                    } catch (Exception e) {
                        System.out.println("⚠️ Lỗi xử lý dòng " + ln + ": " + line + " -> " + e.getMessage());
                    }
                } else {
                    System.out.println("⚠️ Dòng " + ln + " không đủ 14 cột: " + line);
                }
            }
            System.out.println("✅ Đọc file NV thành công (" + qlnv.size() + " NV).");
        } catch (IOException e) {
            System.out.println("❌ Lỗi đọc file NV: " + e.getMessage());
        }
    }

    public ArrayList<NhanVien> getQLNV() { return qlnv; }
    private boolean tonTaiMa(String ma) { return TimTheoMa(ma) != null; }
    public NhanVien TimTheoMa(String maNV) { if(maNV==null||maNV.trim().isEmpty())return null; String mt=maNV.trim().toUpperCase(); for(NhanVien n:qlnv)if(n.getMaNhanVien().equalsIgnoreCase(mt))return n; return null; }
    public QuanLiChucVu getQuanLiChucVu() { return this.qlcv; 
    }
    
    public void tinhluong() {
        KiemTra kt = new KiemTra();
        String maNV = kt.nhapMaNhanVien("Nhập mã nhân viên cần tra cứu lương (NVxxx): ");

        NhanVien nvCanTim = null;
        for (NhanVien nv : qlnv) {
            if (nv.getMaNhanVien().equalsIgnoreCase(maNV)) {
                nvCanTim = nv;
                break;
            }
        }

        if (nvCanTim == null) {
            System.out.println("❌ Không tìm thấy nhân viên có mã: " + maNV);
            return; 
        }

        System.out.println("\n✅ Đã tìm thấy! Chi tiết lương cho nhân viên: " + nvCanTim.getHoten());
        DecimalFormat df = new DecimalFormat("#,###.## VND");

        double luongCung = nvCanTim.tinhLuong(); 
        double phuCap = nvCanTim.getPhuCap();
        double luongNhanDuoc = luongCung + phuCap;
        
        System.out.println("|----------|----------------------|---------|---------|-----------------|-----------|-----------|-----------------|--------------------|");
        System.out.printf("| %-8s | %-20s | %-7s | %-7s | %-15s | %-9s | %-9s | %-15s | %-18s |\n",
                "Mã NV", "Họ Tên", "Mã CV", "Mã PB", "Lương CB", "HSL Thực", "Thâm niên", "Phụ cấp", "Lương Nhận Được");
        System.out.println("|----------|----------------------|---------|---------|-----------------|-----------|-----------|-----------------|--------------------|");

        System.out.printf("| %-8s | %-20s | %-7s | %-7s | %-15s | %-9.1f | %-9d | %-15s | %-18s |\n",
                nvCanTim.getMaNhanVien(),
                nvCanTim.getHoten(),
                (nvCanTim.getMaChucVu() != null ? nvCanTim.getMaChucVu().getMaCV() : "N/A"),
                (nvCanTim.getMaPhongBan() != null ? nvCanTim.getMaPhongBan().getMaPB() : "N/A"),
                df.format(nvCanTim.getLuongCoBan()),
                nvCanTim.getHeSoLuongThucTe(),
                nvCanTim.getThamNien(),
                df.format(phuCap),
                df.format(luongNhanDuoc)
        );
        
        System.out.println("|----------|----------------------|---------|---------|-----------------|-----------|-----------|-----------------|--------------------|");
    }
    
    public void tinhLuongToanBo() {
        if (qlnv.isEmpty()) {
            System.out.println("⚠ Danh sách nhân viên rỗng, không thể tính lương.");
            return;
        }

        DecimalFormat df = new DecimalFormat("#,###.## VND");
        
        System.out.println("\n================================ BẢNG LƯƠNG TOÀN BỘ NHÂN VIÊN ==================================");
        
        System.out.println("|----------|----------------------|---------|---------|-----------------|-----------|-----------|-----------------|--------------------|");
        System.out.printf("| %-8s | %-20s | %-7s | %-7s | %-15s | %-9s | %-9s | %-15s | %-18s |\n",
                "Mã NV", "Họ Tên", "Mã CV", "Mã PB", "Lương CB", "HSL Thực", "Thâm niên", "Phụ cấp", "Lương Nhận Được");
        System.out.println("|----------|----------------------|---------|---------|-----------------|-----------|-----------|-----------------|--------------------|");

        for (NhanVien nv : qlnv) {
            
            if (nv.isTrangThaiLamViec()) {
                
                double luongCung = nv.tinhLuong(); 
                double phuCap = nv.getPhuCap();
                double luongNhanDuoc = luongCung + phuCap;

                System.out.printf("| %-8s | %-20s | %-7s | %-7s | %-15s | %-9.1f | %-9d | %-15s | %-18s |\n",
                        nv.getMaNhanVien(),
                        nv.getHoten(),
                        (nv.getMaChucVu() != null ? nv.getMaChucVu().getMaCV() : "N/A"),
                        (nv.getMaPhongBan() != null ? nv.getMaPhongBan().getMaPB() : "N/A"),
                        df.format(nv.getLuongCoBan()),
                        nv.getHeSoLuongThucTe(),
                        nv.getThamNien(),
                        df.format(phuCap),
                        df.format(luongNhanDuoc)
                );
            }
        }
        System.out.println("|----------|----------------------|---------|---------|-----------------|-----------|-----------|-----------------|--------------------|");
    }
    

}