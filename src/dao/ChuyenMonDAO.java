package dao;

import model.ChuyenMon;
import util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;

public class ChuyenMonDAO {
    Connection conn = DatabaseConnection.getConnection();

    public ArrayList<ChuyenMon> getAllChuyenMon() {
        ArrayList<ChuyenMon> list = new ArrayList<>();
        String sql = "SELECT * FROM ChuyenMon";
        try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                ChuyenMon cm = new ChuyenMon(rs.getString("MaCM"), rs.getString("TenCM"));
                list.add(cm);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public boolean insertChuyenMon(ChuyenMon cm) {
        String sql = "INSERT INTO ChuyenMon (MaCM, TenCM) VALUES (?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, cm.getMaCM());
            ps.setString(2, cm.getTenCM());
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateChuyenMon(ChuyenMon cm) {
        String sql = "UPDATE ChuyenMon SET TenCM=? WHERE MaCM=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, cm.getTenCM());
            ps.setString(2, cm.getMaCM());
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteChuyenMon(String maCM) {
        String sql = "DELETE FROM ChuyenMon WHERE MaCM=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, maCM);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
