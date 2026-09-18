package DoAn;

import java.util.Scanner;
import java.util.ArrayList;

public class PhongBan {
	private String MaPB, TenPB, MaTP, NgayThanhLap, Email;
	private int SoNV;
	
	public PhongBan() {
		MaPB = "";
		TenPB = "";
		MaTP = "N/A";
		NgayThanhLap = "";
		Email = "";
		SoNV = 0;
	}    
	
	public PhongBan(String maPB, String tenPB, String maTP, int soNV, String ngayThanhLap, String email) {
		MaPB = maPB;
		TenPB = tenPB;
		MaTP = (maTP == null || maTP.trim().isEmpty() || maTP.trim().equalsIgnoreCase("N/A")) ? "N/A" : maTP.trim().toUpperCase();
		SoNV = soNV;
		NgayThanhLap = ngayThanhLap;
		Email = email;
	}
	
	public PhongBan(PhongBan pb) {
		MaPB = pb.MaPB;
		TenPB = pb.TenPB;
		MaTP = pb.MaTP;
		SoNV = pb.SoNV;
		NgayThanhLap = pb.NgayThanhLap;
		Email = pb.Email;
	}
	
	public PhongBan(String maPhongBan) {
	    MaPB = maPhongBan;
	}
	
	public void parsePhongBan(String line) {
		String[] txt = line.split(",");
		if (txt.length >= 6) {
			MaPB = txt[0].trim();
			TenPB = txt[1].trim();
			String maTpFromFile = txt[2].trim();
            MaTP = (maTpFromFile.isEmpty() || maTpFromFile.equalsIgnoreCase("N/A")) ? "N/A" : maTpFromFile.toUpperCase();
			NgayThanhLap = txt[3].trim();
			Email = txt[4].trim();
			SoNV = Integer.parseInt(txt[5].trim());
		}
	}
	
	public String dinhDangGhiFile() {
		String maTruongPhong = (MaTP == null || MaTP.trim().isEmpty() || MaTP.equalsIgnoreCase("N/A")) ? "N/A" : MaTP;
		return MaPB + "," + TenPB + "," + MaTP + "," + NgayThanhLap + "," + Email + "," + SoNV;
	}
	
	public void nhap() {
	    System.out.println("\n=== NHẬP THÔNG TIN PHÒNG BAN ===");

	    MaPB = KiemTra.nhapMa("Nhập mã phòng ban: ", 2, 10);
	    TenPB = KiemTra.nhapTen("Nhập tên phòng ban: ", 30);
	    NgayThanhLap = KiemTra.nhapNgay("Nhập ngày thành lập (dd/MM/yyyy): ");
	    Email = KiemTra.nhapEmail("Nhập email phòng ban: ");
	    SoNV = 0; 
	    MaTP = "N/A";

	    System.out.println("✅ Thêm phòng ban thành công!\n");
	}


	
	public void xuat() {
	    System.out.printf("| %-5s | %-25s | %-8s | %-12s | %-20s | %-5d |\n",MaPB, TenPB, MaTP, NgayThanhLap, Email, SoNV);
	}

	public String getMaPB() {
		return MaPB;
	}
	public void setMaPB(String maPB) {
		MaPB = maPB;
	}
	
	public String getTenPB() {
		return TenPB;
	}
	public void setTenPB(String tenPB) {
		TenPB = tenPB;
	}
	
	public String getMaTP() {
		return MaTP;
	}
	public void setMaTP(String maTP) {
        if (maTP == null || maTP.trim().isEmpty() || maTP.trim().equalsIgnoreCase("N/A")) {
            this.MaTP = "N/A";
        } else {
            this.MaTP = maTP.trim().toUpperCase();
        }
    }
	
	public String getNgayThanhLap() {
		return NgayThanhLap;
	}
	public void setNgayThanhLap(String ngayThanhLap) {
		NgayThanhLap = ngayThanhLap;
	}
	
	public String getEmail() {
		return Email;
	}
	public void setEmail(String email) {
		Email = email;
	}
	
	public int getSoNV() {
		return SoNV;
	}
	public void setSoNV(int soNV) {
		SoNV = (soNV < 0) ? 0 : soNV;
	}
}
