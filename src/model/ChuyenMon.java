package model;

public class ChuyenMon {
    private String maCM;
    private String tenCM;

    public ChuyenMon(String maCM, String tenCM) {
        this.maCM = maCM;
        this.tenCM = tenCM;
    }

    public String getMaCM() {
        return maCM;
    }

    public void setMaCM(String maCM) {
        this.maCM = maCM;
    }

    public String getTenCM() {
        return tenCM;
    }

    public void setTenCM(String tenCM) {
        this.tenCM = tenCM;
    }
}
