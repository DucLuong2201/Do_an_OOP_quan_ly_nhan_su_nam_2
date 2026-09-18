package DoAn;

import java.util.Scanner;

public class DeAn {
	
    private String MaDA;
    private String TenDA;
    private String NgayBD;
    private String NgayKT;
    private int SoNVThamGia;
    private String TrangThai;
    private PhongBan PhongBan; 

    
    public DeAn() {
        MaDA = "";
        TenDA = "";
        PhongBan = null; 
        NgayBD = "";
        NgayKT = "";
        SoNVThamGia = 0;
        TrangThai = "Moi"; 
    }

    public DeAn(String maDA, String tenDA, PhongBan phongBan, String ngayBD, String ngayKT, int soNVThamGia, String trangThai) {
        MaDA = maDA;
        TenDA = tenDA;
        this.PhongBan = phongBan; 
        NgayBD = ngayBD;
        NgayKT = ngayKT;
        SoNVThamGia = soNVThamGia;
        this.TrangThai = trangThai;
    }

    
    public DeAn(DeAn da) {
        MaDA = da.MaDA;
        TenDA = da.TenDA;
        PhongBan = da.PhongBan; 
        NgayBD = da.NgayBD;
        NgayKT = da.NgayKT;
        SoNVThamGia = da.SoNVThamGia;
        this.TrangThai = da.TrangThai;
    }

    
    public String parseDeAn(String line) {
        String[] txt = line.split(",");
        if (txt.length == 7) {
            MaDA = txt[0].trim();
            TenDA = txt[1].trim();
            String maPB_vietTat_from_file = txt[2].trim(); 
            NgayBD = txt[3].trim();
            NgayKT = txt[4].trim();
            try { 
                SoNVThamGia = Integer.parseInt(txt[5].trim());
                if (SoNVThamGia < 0) SoNVThamGia = 0;
            } catch (NumberFormatException e) {
                 System.out.println("⚠️ Lỗi định dạng Số NV tham gia trong dòng: " + line);
                 SoNVThamGia = 0; 
            }
            TrangThai = txt[6].trim();
            return maPB_vietTat_from_file; 
        } else {
             System.out.println("⚠️ Bỏ qua dòng Đề án không đúng định dạng 7 cột: " + line);
             return null;
        }
    }

    
    public String dinhDangGhiFile() {
        
        String maPB_vietTat = (PhongBan != null) ? PhongBan.getMaPB() : "";
        return MaDA + "," + TenDA + "," + maPB_vietTat + "," + NgayBD + "," + NgayKT + "," + SoNVThamGia + "," + TrangThai;
    }

    
    public void xuat() {
        String maPB_vietTat = (PhongBan != null) ? PhongBan.getMaPB() : "N/A";
        System.out.printf("| %-5s | %-35s | %-8s | %-12s | %-12s | %-5d | %-15s |\n",
                MaDA, TenDA, maPB_vietTat, NgayBD, NgayKT, SoNVThamGia, TrangThai);
    }

    
    public String getMaDA() { return MaDA; }
    public void setMaDA(String maDA) { MaDA = maDA; }
    public String getTenDA() { return TenDA; }
    public void setTenDA(String tenDA) { TenDA = tenDA; }

    public PhongBan getPhongBan() { return PhongBan; }
    public void setPhongBan(PhongBan phongBan) { this.PhongBan = phongBan; }

    public String getNgayBD() { return NgayBD; }
    public void setNgayBD(String ngayBD) { NgayBD = ngayBD; }
    public String getNgayKT() { return NgayKT; }
    public void setNgayKT(String ngayKT) { NgayKT = ngayKT; }
    public int getSoNVThamGia() { return SoNVThamGia; }
    public void setSoNVThamGia(int soNVThamGia) { SoNVThamGia = soNVThamGia; }
    public String getTrangThai() { return TrangThai; }
    public void setTrangThai(String trangThai) { this.TrangThai = trangThai; }

}