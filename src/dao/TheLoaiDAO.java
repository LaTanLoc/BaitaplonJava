package dao;

import model.TheLoai;
import util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;

public class TheLoaiDAO {
    public ArrayList<TheLoai> getAllTheLoai() {
        ArrayList<TheLoai> list = new ArrayList<>();
        String sql = "SELECT * FROM TheLoai";
        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                TheLoai tl = new TheLoai(
                        rs.getString("MaTheLoai"),
                        rs.getString("TenTheLoai"));
                list.add(tl);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public boolean insertTheLoai(TheLoai tl) {
        String sql = "INSERT INTO TheLoai (MaTheLoai, TenTheLoai) VALUES (?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, tl.getMaTheLoai());
            ps.setString(2, tl.getTenTheLoai());

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean updateTheLoai(TheLoai tl) {
        String sql = "UPDATE TheLoai SET TenTheLoai=? WHERE MaTheLoai=?";
        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, tl.getTenTheLoai());
            ps.setString(2, tl.getMaTheLoai());

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean deleteTheLoai(String maTheLoai) {
        String sql = "DELETE FROM TheLoai WHERE MaTheLoai=?";
        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, maTheLoai);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
