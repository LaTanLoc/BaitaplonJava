package model;

public class KhachHang {
    private String maKH;
    private String tenKH;
    private String diaChi;
    private String dienThoai;
    private String email;
    private String maLVHD;

    public KhachHang(String maKH, String tenKH, String diaChi, String dienThoai, String email, String maLVHD) {
        this.maKH = maKH;
        this.tenKH = tenKH;
        this.diaChi = diaChi;
        this.dienThoai = dienThoai;
        this.email = email;
        this.maLVHD = maLVHD;
    }

    public String getMaKH() {
        return maKH;
    }

    public void setMaKH(String maKH) {
        this.maKH = maKH;
    }

    public String getTenKH() {
        return tenKH;
    }

    public void setTenKH(String tenKH) {
        this.tenKH = tenKH;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }

    public String getDienThoai() {
        return dienThoai;
    }

    public void setDienThoai(String dienThoai) {
        this.dienThoai = dienThoai;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMaLVHD() {
        return maLVHD;
    }

    public void setMaLVHD(String maLVHD) {
        this.maLVHD = maLVHD;
    }
}
