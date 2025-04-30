package dao;

import model.ChucVu;
import util.DatabaseConnection;
import java.sql.*;
import java.util.ArrayList;

public class ChucVuDAO {
    Connection conn = DatabaseConnection.getConnection();

    public ArrayList<ChucVu> getAllChucVu() {
        ArrayList<ChucVu> list = new ArrayList<>();
        String sql = "SELECT * FROM ChucVu";
        try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                ChucVu cv = new ChucVu(rs.getString("MaChucVu"), rs.getString("TenChucVu"));
                list.add(cv);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public boolean insertChucVu(ChucVu cv) {
        String sql = "INSERT INTO ChucVu(MaChucVu, TenChucVu) VALUES (?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, cv.getMaChucVu());
            ps.setString(2, cv.getTenChucVu());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean updateChucVu(ChucVu cv) {
        String sql = "UPDATE ChucVu SET TenChucVu = ? WHERE MaChucVu = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, cv.getTenChucVu());
            ps.setString(2, cv.getMaChucVu());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean deleteChucVu(String maChucVu) {
        String sql = "DELETE FROM ChucVu WHERE MaChucVu = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, maChucVu);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
