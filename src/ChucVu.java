package DoAn;

import java.util.Scanner;

public class ChucVu {
	private String MaCV, TenCV;
	private Double HeSoLuong;

	public ChucVu() {
		MaCV = "";
		TenCV = "";
		HeSoLuong = 0.0;
	}
	
	public ChucVu(String maCV, String tenCV, double heSoluong) {
		MaCV = maCV;
		TenCV = tenCV;
		HeSoLuong = heSoluong;
	}
	
	public ChucVu(ChucVu cv) {
		MaCV = cv.MaCV;
		TenCV = cv.TenCV;
		HeSoLuong = cv.HeSoLuong;
	}
	
	public ChucVu(String maCV) {
	    MaCV = maCV;
	}
	
	public String getMaCV() {
		return MaCV;
	} 
	public void setMaCV(String maCV) {
		MaCV = maCV;
	}
	
	public String getTenCV() { 
		return TenCV;
	}
	public void setTenCV(String tenCV) {
		TenCV = tenCV;
	}
	
	public double getHeSoLuong() {
		return HeSoLuong;
	}
	public void setHeSoLuong(double heSoLuong) {
		HeSoLuong = heSoLuong;
	}
	
	
	public void nhap() {
		Scanner sc = new Scanner(System.in);
		MaCV = KiemTra.nhapMa("Nhap ma chuc vu: ", 2, 10);
		TenCV = KiemTra.nhapTen("Nhap ten chuc vu: ", 30);
		HeSoLuong = KiemTra.nhapHeSoLuong("Nhap he so luong: ");
	}
	
	public void xuat() {
		System.out.printf("| %-10s | %-20s | %-10.2f | \n", MaCV, TenCV, HeSoLuong);
	}
	
	public void parseChucVu(String line) {
		String[] txt = line.split(",");
		if (txt.length == 3) {
			MaCV = txt[0].trim();
			TenCV = txt[1].trim();
			HeSoLuong = Double.parseDouble(txt[2].trim());
		}
	}
	
	public String dinhDangGhiFile() {
		return MaCV + "," + TenCV + "," + HeSoLuong;
	}
}
