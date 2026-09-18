package DoAn;

public class PhanCong {
	 private NhanVien nhanVien; 
	 private DeAn deAn;         
	 private double tienDeAn;   

	    
	    public PhanCong() {
	        this.nhanVien = null;
	        this.deAn = null;
	        this.tienDeAn = 0.0;
	    }

	    
	    public PhanCong(NhanVien nhanVien, DeAn deAn, double tienDeAn) {
	        this.nhanVien = nhanVien;
	        this.deAn = deAn;
	        this.tienDeAn = tienDeAn;
	    }

	    
	    public PhanCong(PhanCong pc) {
	        this.nhanVien = pc.nhanVien;
	        this.deAn = pc.deAn;
	        this.tienDeAn = pc.tienDeAn;
	    }

	    
	    public String dinhDangGhiFile() {
	        String maNV = (nhanVien != null) ? nhanVien.getMaNhanVien() : "";
	        String maDA = (deAn != null) ? deAn.getMaDA() : "";
	        double tienGhi = (tienDeAn < 0) ? 0 : tienDeAn;
	        return maNV + "," + maDA + "," + tienDeAn;
	    }

	    
	    public void xuat() {
	        String maNV = (nhanVien != null) ? nhanVien.getMaNhanVien() : "N/A";
	        String tenNV = (nhanVien != null) ? nhanVien.getHoten() : "N/A";
	        String maDA = (deAn != null) ? deAn.getMaDA() : "N/A";
	        String tenDA = (deAn != null) ? deAn.getTenDA() : "N/A";
	        double tienXuat = (tienDeAn < 0) ? 0 : tienDeAn; 
	        System.out.printf("| %-9s | %-20s | %-5s | %-35s | %10.0f |\n",
	                maNV, tenNV, maDA, tenDA, tienDeAn);
	    }

	    
	    public NhanVien getNhanVien() { return nhanVien; }
	    public void setNhanVien(NhanVien nhanVien) { this.nhanVien = nhanVien; }

	    public DeAn getDeAn() { return deAn; }
	    public void setDeAn(DeAn deAn) { this.deAn = deAn; }

	    public double getTienDeAn() { return tienDeAn; }
	    public void setTienDeAn(double tienDeAn) { this.tienDeAn = tienDeAn; }
	}