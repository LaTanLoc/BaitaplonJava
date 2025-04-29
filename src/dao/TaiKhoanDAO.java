package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import model.TaiKhoan;
import util.DatabaseConnection;

public class TaiKhoanDAO {

    public TaiKhoan dangNhap(String tenDangNhap, String matKhau) {
        TaiKhoan tk = null;
        try {
            Connection conn = DatabaseConnection.getConnection();
            String sql = "SELECT * FROM TaiKhoan WHERE TenDangNhap = ? AND MatKhau = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, tenDangNhap);
            stmt.setString(2, matKhau);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                tk = new TaiKhoan(
                        rs.getString("TenDangNhap"),
                        rs.getString("MatKhau"),
                        rs.getString("Quyen"),
                        rs.getString("HoTen"),
                        rs.getString("Email"),
                        rs.getString("DiaChi"));
            }
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return tk;
    }
}
