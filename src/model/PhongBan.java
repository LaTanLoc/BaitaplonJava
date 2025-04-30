package model;

public class PhongBan {
    private String maPhong;
    private String tenPhong;
    private String dienThoai;

    public PhongBan(String maPhong, String tenPhong, String dienThoai) {
        this.maPhong = maPhong;
        this.tenPhong = tenPhong;
        this.dienThoai = dienThoai;
    }

    public String getMaPhong() {
        return maPhong;
    }

    public void setMaPhong(String maPhong) {
        this.maPhong = maPhong;
    }

    public String getTenPhong() {
        return tenPhong;
    }

    public void setTenPhong(String tenPhong) {
        this.tenPhong = tenPhong;
    }

    public String getDienThoai() {
        return dienThoai;
    }

    public void setDienThoai(String dienThoai) {
        this.dienThoai = dienThoai;
    }
}
