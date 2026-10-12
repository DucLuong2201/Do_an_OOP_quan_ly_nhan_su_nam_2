package DoAn;

import java.util.*;

public class NhanVien extends Nguoi {
    // ---------------------- Thuộc tính ----------------------
    private String maNhanVien;
    private PhongBan maPhongBan;
    private ChucVu maChucVu;
    private double luongCoBan;
    private int thamNien;
    private double phuCap;
    private double heSoLuongThucTe;
    private boolean trangThaiLamViec = true; // mặc định là đang làm việc

    private List<PhanCong> dsPhanCong = new ArrayList<>();

    private Scanner sc = new Scanner(System.in);

    // ---------------------- Constructor ----------------------
    public NhanVien() {
        super();
        maNhanVien = "";
        maPhongBan = new PhongBan();
        maChucVu = new ChucVu();
        luongCoBan = 0.0;
        thamNien = 0;
        heSoLuongThucTe = 0.0;
        phuCap = 0.0;
        trangThaiLamViec = true;
    }

    public NhanVien(String maNV, String hoTen, PhongBan pb, ChucVu cv, double luongCB,
                    int tn, NoiSinh noiSinh, int namSinh,
                    String diaChi, double phuCap) {
        super(hoTen, namSinh, diaChi, noiSinh);
        this.maNhanVien = maNV;
        this.maPhongBan = pb;
        this.maChucVu = cv;
        this.luongCoBan = luongCB;
        this.thamNien = tn;
        this.phuCap = phuCap;
        this.heSoLuongThucTe = tinhHslThucTe();
        this.trangThaiLamViec = true;
    }

    public NhanVien(NhanVien nv) {
        super(nv.getHoten(), nv.getNamsinh(), nv.getDiachi(), nv.getNs());
        this.maNhanVien = nv.maNhanVien;
        this.maPhongBan = nv.maPhongBan;
        this.maChucVu = nv.maChucVu;
        this.luongCoBan = nv.luongCoBan;
        this.thamNien = nv.thamNien;
        this.heSoLuongThucTe = nv.tinhHslThucTe();
        this.phuCap = nv.phuCap;
        this.trangThaiLamViec = nv.trangThaiLamViec;
    }

    // ---------------------- Getter & Setter ----------------------
    public String getMaNhanVien() {
        return maNhanVien;
    }

    public void setMaNhanVien(String maNV) {
        this.maNhanVien = maNV;
    }

    public PhongBan getMaPhongBan() {
        return maPhongBan;
    }

    public void setMaPhongBan(PhongBan pb) {
        this.maPhongBan = pb;
    }

    public ChucVu getMaChucVu() {
        return maChucVu;
    }

    public void setMaChucVu(ChucVu cv) {
        this.maChucVu = cv;
        this.heSoLuongThucTe = tinhHslThucTe();
    }

    public double getLuongCoBan() {
        return luongCoBan;
    }

    public void setLuongCoBan(double luongCoBan) {
        this.luongCoBan = luongCoBan;
    }

    public int getThamNien() {
        return thamNien;
    }

    public void setThamNien(int thamNien) {
        this.thamNien = thamNien;
        this.heSoLuongThucTe = tinhHslThucTe();
    }

    public double getPhuCap() {
        return phuCap;
    }

    public void setPhuCap(double phuCap) {
        this.phuCap = phuCap;
    }

    public double getHeSoLuongThucTe() {
        return heSoLuongThucTe;
    }
    
    public boolean isTrangThaiLamViec() {
        return trangThaiLamViec;
    }

    public void setTrangThaiLamViec(boolean trangThaiLamViec) {
        this.trangThaiLamViec = trangThaiLamViec;
    }


    // ---------------------- Tính toán ----------------------
    private double tinhHslThucTe() {
    	double hslCoBan = (maChucVu != null) ? maChucVu.getHeSoLuong() : 0.0;
    	double hslThem;

        if (thamNien <= 2) hslThem = 0;
        else if (thamNien <= 5) hslThem = 1.0;
        else if (thamNien <= 10) hslThem = 2.0;
        else hslThem = 2.5;

        return hslCoBan + hslThem;
    }

    @Override
    public double tinhLuong() {
        return getHeSoLuongThucTe() * luongCoBan;
    }

    public double tinhPhuCap(List<PhanCong> dsPhanCong) {
        double tong = 0;
        for (PhanCong pc : dsPhanCong) {
            if (pc.getNhanVien() != null &&
                pc.getNhanVien().getMaNhanVien().equalsIgnoreCase(this.maNhanVien)) {
                tong += pc.getTienDeAn();
            }
        }
        this.phuCap = tong;
        return tong;
    }

    public void thangChuc(ChucVu chucVuMoi) {
        this.maChucVu = chucVuMoi;
        this.heSoLuongThucTe = tinhHslThucTe();
    }

    public void tangThamNien(int namTangThem) {
        this.thamNien += namTangThem;
        this.heSoLuongThucTe = tinhHslThucTe();
    }

    // ---------------------- Nhập & Xuất ----------------------
    public void nhap() {
    	super.nhap(); // nhập phần Nguoi (SDD, họ tên, ...)

        maNhanVien = KiemTra.nhapMaNhanVien("Nhập mã nhân viên: ");
        luongCoBan = KiemTra.nhapSoThucDuong("Nhập lương cơ bản: ");

        thamNien = KiemTra.nhapSoNguyenDuong("Nhập thâm niên: ");

        // Dùng luôn method nhập của object để tránh lỗi construct
        String maPhongBanStr = KiemTra.nhapMa("Nhập mã phòng ban: ", 2, 10);
        this.maPhongBan = new PhongBan(maPhongBanStr);
       
        String maChucVuStr = KiemTra.nhapMa("Nhap ma chuc vu: ", 2, 10);
        this.maChucVu = new ChucVu(maChucVuStr);
        double hsl = KiemTra.nhapHeSoLuong("Nhập hệ số lương cho chức vụ này: ");
        this.maChucVu.setHeSoLuong(hsl);
        // cập nhật hệ số thực tế sau khi nhập
        this.heSoLuongThucTe = tinhHslThucTe();
    }



    public void xuat() {
        super.xuat();
        System.out.println("Mã NV: " + getMaNhanVien());
        System.out.println("Phòng ban: " + (getMaPhongBan() != null ? getMaPhongBan().getMaPB() : "N/A"));
        System.out.println("Chức vụ: " + (getMaChucVu() != null ? getMaChucVu().getMaCV() : "N/A"));
        System.out.println("Lương cơ bản: " + getLuongCoBan());
        System.out.println("Hệ số lương thực tế: " + getHeSoLuongThucTe());
        System.out.println("Thâm niên: " + getThamNien() + " năm");
        System.out.println("Tổng lương: " + tinhLuong());
        System.out.println("Trạng thái: " + (trangThaiLamViec ? "Đang làm việc" : "Đã nghỉ"));

    }
}
