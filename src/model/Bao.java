package model;

public class Bao {
    private String maBao;
    private String tenBao;
    private String diaChi;
    private String maChucNang;
    private String dienThoai;

    public Bao(String maBao, String tenBao, String diaChi, String maChucNang, String dienThoai) {
        this.maBao = maBao;
        this.tenBao = tenBao;
        this.diaChi = diaChi;
        this.maChucNang = maChucNang;
        this.dienThoai = dienThoai;
    }

    public String getMaBao() {
        return maBao;
    }

    public void setMaBao(String maBao) {
        this.maBao = maBao;
    }

    public String getTenBao() {
        return tenBao;
    }

    public void setTenBao(String tenBao) {
        this.tenBao = tenBao;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }

    public String getMaChucNang() {
        return maChucNang;
    }

    public void setMaChucNang(String maChucNang) {
        this.maChucNang = maChucNang;
    }

    public String getDienThoai() {
        return dienThoai;
    }

    public void setDienThoai(String dienThoai) {
        this.dienThoai = dienThoai;
    }
}
