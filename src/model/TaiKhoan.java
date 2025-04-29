package model;

public class TaiKhoan {
    private String tenDangNhap;
    private String matKhau;
    private String quyen;
    private String hoTen;
    private String email;
    private String diaChi;

    public TaiKhoan() {
    }

    public TaiKhoan(String tenDangNhap, String matKhau, String quyen, String hoTen, String email, String diaChi) {
        this.tenDangNhap = tenDangNhap;
        this.matKhau = matKhau;
        this.quyen = quyen;
        this.hoTen = hoTen;
        this.email = email;
        this.diaChi = diaChi;
    }

    // Getter và Setter
    public String getTenDangNhap() {
        return tenDangNhap;
    }

    public void setTenDangNhap(String tenDangNhap) {
        this.tenDangNhap = tenDangNhap;
    }

    public String getMatKhau() {
        return matKhau;
    }

    public void setMatKhau(String matKhau) {
        this.matKhau = matKhau;
    }

    public String getQuyen() {
        return quyen;
    }

    public void setQuyen(String quyen) {
        this.quyen = quyen;
    }

    public String getHoTen() {
        return hoTen;
    }

    public void setHoTen(String hoTen) {
        this.hoTen = hoTen;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }
}
