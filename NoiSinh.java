package DoAn;

import java.util.Scanner;

public class NoiSinh {
    // ---------------------- Thuộc tính ----------------------
    private String tinh;

    // ---------------------- Constructor ----------------------
    public NoiSinh() {
        this.tinh = "";
    }

    public NoiSinh(String tinh) {
        this.tinh = tinh;
    }

    public NoiSinh(NoiSinh ns) {
        this.tinh = ns.tinh;
    }

    // ---------------------- Getter & Setter ----------------------
    public String getTinh() {
        return tinh;
    }

    public void setTinh(String tinh) {
        this.tinh = tinh;
    }

    // ---------------------- Nhập & Xuất ----------------------
    public void nhap(Scanner sc) {
        System.out.print("Nhập tỉnh/thành phố: ");
        tinh = sc.nextLine().trim();
    }

    public String xuatFile() {
        return tinh;
    }

    @Override
    public String toString() {
        return tinh;
    }

    // ---------------------- Hàm xử lý ----------------------
    public static NoiSinh parse(String tenTinh) {
        if (tenTinh == null || tenTinh.trim().isEmpty()) {
            return new NoiSinh("Chưa rõ"); // Hoặc return new NoiSinh("");
        }
        return new NoiSinh(tenTinh.trim());
    }
}
