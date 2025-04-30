package model;

public class TrinhDo {
    private String maTD;
    private String tenTD;

    public TrinhDo(String maTD, String tenTD) {
        this.maTD = maTD;
        this.tenTD = tenTD;
    }

    public String getMaTD() {
        return maTD;
    }

    public void setMaTD(String maTD) {
        this.maTD = maTD;
    }

    public String getTenTD() {
        return tenTD;
    }

    public void setTenTD(String tenTD) {
        this.tenTD = tenTD;
    }
}
