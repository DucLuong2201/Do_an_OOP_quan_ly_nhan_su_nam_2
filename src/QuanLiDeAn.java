package DoAn;

import java.io.*;
import java.util.*;

public class QuanLiDeAn implements ChucNang<DeAn> {
	private ArrayList<DeAn> danhSach = new ArrayList<>();
    private QuanLiPhongBan dsPhongBan; 
    private QuanLiPhanCong qlPC; 

    
    public QuanLiDeAn(QuanLiPhongBan dsPhongBan) {
        this.dsPhongBan = dsPhongBan;
        this.qlPC = null; 
    }
    
 
    public void setQuanLiPhanCong(QuanLiPhanCong qlPC) {
        this.qlPC = qlPC;
    }

    
    public ArrayList<DeAn> getDanhSach() {
        return danhSach;
    }

    @Override
    public void them(DeAn da) {
        if (da == null || TimTheoMa(da.getMaDA()) != null) {
             System.out.println("❌ Không thể thêm de an null hoặc ma bị trùng.");
             return;
        }
        danhSach.add(da);
        
    }
    
    @Override
    public void them() {
        System.out.println("\n=== THÊM ĐỀ ÁN MỚI ===");
        
        String maDA = KiemTra.nhapMaDeAn("Nhập mã đề án (ví dụ DA001): ");
        if (TimTheoMa(maDA) != null) {
            System.out.println("❌ Mã đề án đã tồn tại!");
            return;
        }

        String tenDA = KiemTra.nhapTenDeAn("Nhập tên đề án: ", 50);

        
        System.out.println("Chọn phòng ban thực hiện:");
        PhongBan pb = KiemTra.nhapPhongBan(dsPhongBan); 
        if (pb == null) {
             System.out.println("❌ Thao tác thêm đề án đã bị hủy (do lỗi Phòng ban).");
            return; 
        }

        
        String ngayBD = KiemTra.nhapNgay("Nhập ngày bắt đầu (dd/MM/yyyy): ");
        String ngayKT = KiemTra.nhapNgayKetThuc("Nhập ngày kết thúc (dd/MM/yyyy): ", ngayBD);
        if (ngayKT == null) return; 

        
        int soNV = KiemTra.nhapSoNguyenDuong("Nhập số nhân viên tham gia (tối đa): ");

        
        DeAn da = new DeAn(maDA, tenDA, pb, ngayBD, ngayKT, soNV, "Moi");
        danhSach.add(da);
        System.out.println("✅ Thêm đề án thành công!");
    }


    @Override
    public void xoa() { 
        System.out.println("\n=== HỦY ĐỀ ÁN (Đánh dấu đã hủy) ===");
        String ma = KiemTra.nhapMaDeAn("Nhập mã đề án cần hủy: ");
        DeAn da = TimTheoMa(ma);
        if (da != null) {
            
            da.setTrangThai("DaHuy");
            System.out.println("✅ Đã hủy đề án '" + da.getTenDA() + "' thành công!");
            
            
            if (this.qlPC != null) {
                
                this.qlPC.capNhatPhuCapNhanVien(); 
            }
            

        } else {
            System.out.println("❌ Không tìm thấy đề án với mã '" + ma + "'!");
        }
    }
    
    @Override
    public void sua() {
        System.out.println("\n=== SỬA THÔNG TIN ĐỀ ÁN ===");
        String ma = KiemTra.nhapMaDeAn("Nhập mã đề án cần sửa: ");
        DeAn da = TimTheoMa(ma);
        if (da == null) {
            System.out.println("❌ Không tìm thấy đề án với mã '" + ma + "'!");
            return;
        }

        System.out.println("Đang sửa thông tin cho Đề án: " + da.getTenDA() + " (Mã: " + da.getMaDA() + ")");
        System.out.println("1. Tên đề án");
        System.out.println("2. Phòng ban");
        System.out.println("3. Ngày bắt đầu");
        System.out.println("4. Ngày kết thúc");
        System.out.println("5. Số nhân viên tham gia (tối đa)");
        System.out.println("6. Cập nhật trạng thái");
        System.out.println("0. Quay lại");
        int chon = KiemTra.nhapSoNguyenDuong("Chọn mục cần sửa: ");

        switch (chon) {
            case 1:
                da.setTenDA(KiemTra.nhapTenDeAn("Nhập tên mới: ", 50));
                 System.out.println("✅ Cập nhật tên đề án thành công!");
                break;
            case 2: {
                System.out.println("Chọn phòng ban mới:");
                PhongBan pbMoi = KiemTra.nhapPhongBan(dsPhongBan); 
                if (pbMoi != null) {
                    da.setPhongBan(pbMoi); 
                    System.out.println("✅ Cập nhật phòng ban thành công!");
                } else {
                     System.out.println(" Giữ nguyên phòng ban cũ.");
                }
                break;
            }
            case 3: {
                String ngayBDMoi = KiemTra.nhapNgay("Nhập ngày bắt đầu mới (dd/MM/yyyy): ");
                
                if (KiemTra.isNgayBDHopLe(ngayBDMoi, da.getNgayKT())) {
                    da.setNgayBD(ngayBDMoi);
                    System.out.println("✅ Cập nhật ngày bắt đầu thành công!");
                }
                break;
            }
            case 4: {
                
                String ngayKTMoi = KiemTra.nhapNgayKetThuc("Nhập ngày kết thúc mới (dd/MM/yyyy): ", da.getNgayBD());
                if (ngayKTMoi != null) {
                    da.setNgayKT(ngayKTMoi);
                    System.out.println("✅ Cập nhật ngày kết thúc thành công!");
                }
                break;
            }
            case 5:
                int soNVMoi = KiemTra.nhapSoNguyenDuong("Nhập số nhân viên tham gia mới (tối đa): ");
                 
                da.setSoNVThamGia(soNVMoi);
                 System.out.println("✅ Cập nhật số nhân viên tham gia thành công!");
                break;
             
            case 6: {
                String trangThaiMoi = KiemTra.nhapTrangThai();
                if (trangThaiMoi != null) {
                    da.setTrangThai(trangThaiMoi);
                    System.out.println("✅ Cập nhật trạng thái thành công!");

                    
                    if (this.qlPC != null) {
                        
                        this.qlPC.capNhatPhuCapNhanVien(); 
                        System.out.println("ℹ️ Đã tự động cập nhật lại phụ cấp toàn hệ thống.");
                    }
                    
                }
                break;
            }
             case 0:
                 System.out.println(" Hủy thao tác sửa.");
                 break;
            default:
                System.out.println("❌ Lựa chọn không hợp lệ!");
        }
    }

    @Override
    public void timKiem() {
        System.out.println("\n=== TÌM KIẾM ĐỀ ÁN ===");
        String ma = KiemTra.nhapMaDeAn("Nhập mã đề án cần tìm: ");
        DeAn da = TimTheoMa(ma);
        if (da != null) {
            System.out.println("✅ Tìm thấy đề án:");
            
            System.out.printf("| %-5s | %-35s | %-8s | %-12s | %-12s | %-5s | %-15s |\n",
                "MãDA", "Tên đề án", "MãPB", "Ngày BD", "Ngày KT", "SốNV", "Trạng thái");
            System.out.println("-----------------------------------------------------------------------------------------------------------------");
            
            da.xuat();
        } else {
            System.out.println("❌ Không tìm thấy đề án với mã '" + ma + "'!");
        }
    }

    @Override
    public void xem() {
        if (danhSach.isEmpty()) {
            System.out.println("⚠️ Danh sách đề án trống!");
            return;
        }

        boolean coDuLieu = false;
        System.out.println("\n=== DANH SÁCH ĐỀ ÁN (Trừ các đề án đã hủy) ===");
        System.out.printf("| %-5s | %-35s | %-8s | %-12s | %-12s | %-5s | %-15s |\n",
                "MãDA", "Tên đề án", "MãPB", "Ngày BD", "Ngày KT", "SốNV", "Trạng thái");
        System.out.println("-----------------------------------------------------------------------------------------------------------------");

        
        for (DeAn da : danhSach) {
            if (!da.getTrangThai().equalsIgnoreCase("DaHuy")) {
                da.xuat();
                coDuLieu = true;
            }
        }

        
        if (!coDuLieu) {
             System.out.println("... (Không có đề án nào đang hoạt động để hiển thị) ...");
        }
    }

    
    public DeAn TimTheoMa(String maDA) {
        for (DeAn da : danhSach) {
            if (da.getMaDA().equalsIgnoreCase(maDA))
                return da;
        }
        return null;
    }

    
    
    public void DocFile(String tenFile) {
        danhSach.clear();
        try (BufferedReader br = new BufferedReader(new FileReader(tenFile))) {
            String line;
             int lineNumber = 0;
            while ((line = br.readLine()) != null) {
                 lineNumber++;
                DeAn da = new DeAn();

                
                String maPB_vietTat_from_file = da.parseDeAn(line); 

                if (maPB_vietTat_from_file != null) {
                    
                    PhongBan pb = dsPhongBan.TimTheoMa(maPB_vietTat_from_file);

                    if (pb == null) {
                        System.out.println("⚠️ Cảnh báo dòng " + lineNumber + ": Đề án " + da.getMaDA() + " có mã PB '" + maPB_vietTat_from_file + "' không tồn tại. Bỏ qua...");
                        continue; 
                    }

                    
                    da.setPhongBan(pb);
                    danhSach.add(da); 
                }
                
            }
            System.out.println("✅ Đã tải dữ liệu Đề án (đã liên kết bằng mã viết tắt)!");
        } catch (FileNotFoundException e) {
             System.out.println("⚠️ Không tìm thấy file: " + tenFile + ". Tạo danh sách trống.");
        } catch (IOException e) {
            System.out.println("⚠️ Lỗi đọc file " + tenFile + ": " + e.getMessage());
        } catch (Exception e) { 
            System.out.println("⚠️ Lỗi không xác định khi đọc file Đề án: " + e.getMessage());
            e.printStackTrace();
        }
    }
    

    
    
    public void GhiFile(String tenFile) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(tenFile))) {
            for (DeAn da : danhSach) {
                
                bw.write(da.dinhDangGhiFile());
                bw.newLine();
            }
            System.out.println("✅ Đã lưu dữ liệu Đề án (theo định dạng mã viết tắt)!");
        } catch (IOException e) {
            System.out.println("⚠️ Lỗi ghi file " + tenFile + ": " + e.getMessage());
        }
    }

}