package dao;

import model.PhongBan;
import util.DatabaseConnection;
import java.sql.*;
import java.util.ArrayList;

public class PhongBanDAO {
    Connection conn = DatabaseConnection.getConnection();

    public ArrayList<PhongBan> getAllPhongBan() {
        ArrayList<PhongBan> list = new ArrayList<>();
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM PhongBan");
            while (rs.next()) {
                list.add(new PhongBan(
                        rs.getString("MaPhong"),
                        rs.getString("TenPhong"),
                        rs.getString("DienThoai")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public boolean insertPhongBan(PhongBan pb) {
        String sql = "INSERT INTO PhongBan VALUES (?, ?, ?)";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, pb.getMaPhong());
            ps.setString(2, pb.getTenPhong());
            ps.setString(3, pb.getDienThoai());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            return false;
        }
    }

    public boolean updatePhongBan(PhongBan pb) {
        String sql = "UPDATE PhongBan SET TenPhong = ?, DienThoai = ? WHERE MaPhong = ?";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, pb.getTenPhong());
            ps.setString(2, pb.getDienThoai());
            ps.setString(3, pb.getMaPhong());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            return false;
        }
    }

    public boolean deletePhongBan(String maPhong) {
        String sql = "DELETE FROM PhongBan WHERE MaPhong = ?";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, maPhong);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            return false;
        }
    }
}
