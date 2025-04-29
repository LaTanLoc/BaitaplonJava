package dao;

import java.sql.*;
import java.util.ArrayList;
import model.ChucNang;
import util.DatabaseConnection;

public class ChucNangDAO {

    public ArrayList<ChucNang> getAllChucNang() {
        ArrayList<ChucNang> list = new ArrayList<>();
        Connection conn = DatabaseConnection.getConnection();
        String sql = "SELECT * FROM ChucNang";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                ChucNang cn = new ChucNang();
                cn.setMachucnang(rs.getString("Machucnang"));
                cn.setTenchucnang(rs.getString("Tenchucnang"));
                list.add(cn);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public boolean addChucNang(ChucNang cn) {
        Connection conn = DatabaseConnection.getConnection();
        String sql = "INSERT INTO ChucNang (Machucnang, Tenchucnang) VALUES (?, ?)";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, cn.getMachucnang());
            ps.setString(2, cn.getTenchucnang());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean updateChucNang(ChucNang cn) {
        Connection conn = DatabaseConnection.getConnection();
        String sql = "UPDATE ChucNang SET Tenchucnang = ? WHERE Machucnang = ?";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, cn.getTenchucnang());
            ps.setString(2, cn.getMachucnang());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean deleteChucNang(String machucnang) {
        Connection conn = DatabaseConnection.getConnection();
        String sql = "DELETE FROM ChucNang WHERE Machucnang = ?";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, machucnang);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
