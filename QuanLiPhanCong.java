package DoAn;
import java.io.*;
import java.util.ArrayList;

public class QuanLiPhanCong {
	 ArrayList<PhanCong> danhSach = new ArrayList<>();
        private QuanLiNhanVien qlNhanVien;
        private QuanLiDeAn qlDeAn;
        public ArrayList<PhanCong> getDanhSach() {
            return danhSach;
}
    public QuanLiPhanCong(QuanLiNhanVien qlNhanVien, QuanLiDeAn qlDeAn) {
        this.qlNhanVien = qlNhanVien;
        this.qlDeAn = qlDeAn;
    }

    

    public PhanCong TimPhanCongTheoMa(String maNV, String maDA) {
        for (PhanCong pc : danhSach) {
            if (pc.getNhanVien() != null && pc.getDeAn() != null &&
                pc.getNhanVien().getMaNhanVien().equalsIgnoreCase(maNV) &&
                pc.getDeAn().getMaDA().equalsIgnoreCase(maDA)) {
                return pc;
            }
        }
        return null;
    }

    public int DemSoNVTheoDeAn(String maDA) {
        int count = 0;
        for (PhanCong pc : danhSach) {
            if (pc.getDeAn() != null && pc.getDeAn().getMaDA().equalsIgnoreCase(maDA)) {
                count++;
            }
        }
        return count;
    }

    public int DemSoDeAnTheoNV(String maNV) {
        int dem = 0;
        for (PhanCong pc : danhSach) {
            if (pc.getNhanVien() != null &&
                pc.getNhanVien().getMaNhanVien().equalsIgnoreCase(maNV)) {
                dem++;
            }
        }
        return dem;
    }

    
    public void Them() {
        System.out.println("\n=== THÊM PHÂN CÔNG MỚI ===");

        NhanVien nv = KiemTra.nhapNhanVien(qlNhanVien);
        if (nv == null) return;

        DeAn da = KiemTra.nhapDeAn(qlDeAn);
        if (da == null) return;

        if (TimPhanCongTheoMa(nv.getMaNhanVien(), da.getMaDA()) != null) {
            System.out.println("❌ Nhân viên đã được phân công cho đề án này!");
            return;
        }

        double tien = KiemTra.nhapSoThucDuong("💰 Nhập tiền đề án cho nhân viên: ");
        int soHienTai = DemSoNVTheoDeAn(da.getMaDA());
        int soToiDa = da.getSoNVThamGia();

        if (soToiDa > 0 && soHienTai >= soToiDa) {
            System.out.println("❌ Đề án đã đủ số lượng nhân viên!");
            return;
        }

        PhanCong pc = new PhanCong(nv, da, tien);
        danhSach.add(pc);

        System.out.println("✅ Thêm phân công thành công!");
        capNhatPhuCapNhanVien(); 
    }

    
    public void Xoa() {
        System.out.println("\n=== XÓA PHÂN CÔNG ===");

        NhanVien nv = KiemTra.nhapNhanVien(qlNhanVien);
        if (nv == null) return;

        DeAn da = KiemTra.nhapDeAn(qlDeAn);
        if (da == null) return;

        PhanCong pc = TimPhanCongTheoMa(nv.getMaNhanVien(), da.getMaDA());
        if (pc == null) {
            System.out.println("❌ Không tìm thấy phân công này!");
            return;
        }

        String xacNhan = KiemTra.nhapChuoi("❓ Nhập 'Y' để xác nhận xóa: ");
        if (xacNhan.equalsIgnoreCase("Y")) {
            danhSach.remove(pc);
            System.out.println("✅ Đã xóa phân công!");
            capNhatPhuCapNhanVien(); 
        } else {
            System.out.println("❌ Hủy thao tác xóa.");
        }
    }

    
    public void Sua() {
        System.out.println("\n=== SỬA PHÂN CÔNG ===");

        NhanVien nv = KiemTra.nhapNhanVien(qlNhanVien);
        if (nv == null) return;

        DeAn da = KiemTra.nhapDeAn(qlDeAn);
        if (da == null) return;

        PhanCong pc = TimPhanCongTheoMa(nv.getMaNhanVien(), da.getMaDA());
        if (pc == null) {
            System.out.println("❌ Không tìm thấy phân công!");
            return;
        }

        System.out.println("1. Đổi Nhân viên");
        System.out.println("2. Đổi Đề án");
        System.out.println("3. Sửa tiền đề án");
        System.out.println("0. Quay lại");
        int chon = KiemTra.nhapSoNguyenDuong("👉 Nhập lựa chọn: ");

        switch (chon) {
            case 1:
                NhanVien nvMoi = KiemTra.nhapNhanVien(qlNhanVien);
                if (nvMoi == null) return;
                pc.setNhanVien(nvMoi);
                System.out.println("✅ Đã đổi nhân viên!");
                break;
            case 2:
                DeAn daMoi = KiemTra.nhapDeAn(qlDeAn);
                if (daMoi == null) return;
                pc.setDeAn(daMoi);
                System.out.println("✅ Đã đổi đề án!");
                break;
            case 3:
                double tienMoi = KiemTra.nhapSoThucDuong("💰 Nhập tiền đề án mới: ");
                pc.setTienDeAn(tienMoi);
                System.out.println("✅ Đã sửa tiền đề án!");
                break;
            case 0:
                System.out.println("Hủy thao tác sửa.");
                return;
            default:
                System.out.println("❌ Lựa chọn không hợp lệ!");
        }

        capNhatPhuCapNhanVien(); 
    }

    
    public void TimKiem() {
        System.out.println("\n=== TÌM KIẾM PHÂN CÔNG ===");
        System.out.println("1. Tìm tất cả đề án của 1 nhân viên");
        System.out.println("2. Tìm tất cả nhân viên của 1 đề án");
        int chon = KiemTra.nhapSoNguyenDuong("👉 Nhập lựa chọn: ");

     
        System.out.printf("| %-9s | %-20s | %-5s | %-35s | %10s |\n",
                "Mã NV", "Tên Nhân Viên", "Mã DA", "Tên Đề Án", "Tiền DA");
        System.out.println("|-----------|----------------------|-------|-------------------------------------|------------|");

        boolean found = false;
        if (chon == 1) {
            NhanVien nv = KiemTra.nhapNhanVien(qlNhanVien);
            for (PhanCong pc : danhSach) {
                if (pc.getNhanVien().getMaNhanVien().equalsIgnoreCase(nv.getMaNhanVien())) {
                    pc.xuat();
                    found = true;
                }
            }
        } else if (chon == 2) {
            DeAn da = KiemTra.nhapDeAn(qlDeAn);
            for (PhanCong pc : danhSach) {
                if (pc.getDeAn().getMaDA().equalsIgnoreCase(da.getMaDA())) {
                    pc.xuat();
                    found = true;
                }
            }
        }

        if (!found) System.out.println("⚠️ Không tìm thấy kết quả nào!");
    }

 
    
    public void HienThi() {
        if (danhSach.isEmpty()) {
            System.out.println("⚠️ Danh sách phân công trống!");
            return;
        }

        System.out.println("\n=== DANH SÁCH PHÂN CÔNG (Trừ các đề án đã hủy) ===");
        
        
        System.out.printf("| %-9s | %-20s | %-5s | %-35s | %10s |\n",
                "Mã NV", "Tên Nhân Viên", "Mã DA", "Tên Đề Án", "Tiền DA");
        System.out.println("|-----------|----------------------|-------|-------------------------------------|------------|");

        boolean coDuLieu = false; 
        for (PhanCong pc : danhSach) {
            
            
            if (pc.getDeAn() != null && !pc.getDeAn().getTrangThai().equalsIgnoreCase("DaHuy")) {
                pc.xuat();
                coDuLieu = true;
            }
            
        }
        
        if (!coDuLieu) {
            System.out.println("... (Không có phân công nào cho các đề án đang hoạt động) ...");
        }
    }
    
    public void DocFile(String tenFile) {
        danhSach.clear();
        try (BufferedReader br = new BufferedReader(new FileReader(tenFile))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] txt = line.split(",");
                if (txt.length != 3) continue;

                String maNV = txt[0].trim();
                String maDA = txt[1].trim();
                double tien = Double.parseDouble(txt[2].trim());

                NhanVien nv = qlNhanVien.TimTheoMa(maNV);
                DeAn da = qlDeAn.TimTheoMa(maDA);
                if (nv != null && da != null) {
                    danhSach.add(new PhanCong(nv, da, tien));
                }
            }
            capNhatPhuCapNhanVien(); 
            System.out.println("✅ Đã đọc file phân công!");
        } catch (Exception e) {
            System.out.println("❌ Lỗi đọc file: " + e.getMessage());
        }
    }

    public void GhiFile(String tenFile) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(tenFile))) {
            for (PhanCong pc : danhSach) {
                bw.write(pc.dinhDangGhiFile());
                bw.newLine();
            }
            System.out.println("✅ Đã ghi file phân công!");
        } catch (IOException e) {
            System.out.println("❌ Lỗi ghi file: " + e.getMessage());
        }
    }

    
    public void capNhatPhuCapNhanVien() {
        if (qlNhanVien == null) return;
        ArrayList<NhanVien> dsNV = qlNhanVien.getQLNV();
        for (NhanVien nv : dsNV) {
            nv.tinhPhuCap(danhSach);
        }
    }
}