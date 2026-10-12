package DoAn;

import java.util.Scanner;

public abstract class Nguoi {
    // ---------------------- Thuộc tính ----------------------
    private String hoten;
    private int namsinh;
    private String diachi;
    private NoiSinh ns;

    Scanner sc = new Scanner(System.in);

    // ---------------------- Constructor ----------------------
    public Nguoi() {
        this.hoten = "";
        this.namsinh = 0;
        this.diachi = "";
        this.ns = new NoiSinh();
    }

    public Nguoi(String hoten, int namsinh, String diachi, NoiSinh ns) {
        this.hoten = hoten;
        this.namsinh = namsinh;
        this.diachi = diachi;
        this.ns = ns;
    }

    public Nguoi(Nguoi n) {
        this.hoten = n.hoten;
        this.namsinh = n.namsinh;
        this.diachi = n.diachi;
        this.ns = n.ns;
    }

    // ---------------------- Getter & Setter ----------------------
    public String getHoten() {
        return hoten;
    }

    public void setHoten(String hoten) {
        this.hoten = hoten;
    }

    public int getNamsinh() {
        return namsinh;
    }

    public void setNamsinh(int namsinh) {
        this.namsinh = namsinh;
    }

    public String getDiachi() {
        return diachi;
    }

    public void setDiachi(String diachi) {
        this.diachi = diachi;
    }

    public NoiSinh getNs() {
        return ns;
    }

    public void setNs(NoiSinh ns) {
        this.ns = ns;
    }

    // ---------------------- Phương thức trừu tượng ----------------------
    public abstract double tinhLuong();

    // ---------------------- Nhập & Xuất ----------------------
    public void nhap() {
        hoten = KiemTra.nhapHoTenNguoi("Họ tên: ");
        namsinh = KiemTra.nhapNamSinhNguoi("Năm sinh: ");
        diachi = KiemTra.nhapDiaChiNguoi("Địa chỉ: ");
        ns = KiemTra.nhapNoiSinhNguoi("Nơi sinh: ");
    }

    public void xuat() {
        System.out.println("Họ tên: " + getHoten());
        System.out.println("Năm sinh: " + getNamsinh());
        System.out.println("Địa chỉ: " + getDiachi());
        System.out.println("Nơi sinh: " + getNs());
    }
}

