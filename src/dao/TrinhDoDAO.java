package dao;

import model.TrinhDo;
import util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;

public class TrinhDoDAO {
    Connection conn = DatabaseConnection.getConnection();

    public ArrayList<TrinhDo> getAllTrinhDo() {
        ArrayList<TrinhDo> list = new ArrayList<>();
        String sql = "SELECT * FROM TrinhDo";
        try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                TrinhDo td = new TrinhDo(rs.getString("MaTD"), rs.getString("TenTD"));
                list.add(td);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public boolean insertTrinhDo(TrinhDo td) {
        String sql = "INSERT INTO TrinhDo (MaTD, TenTD) VALUES (?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, td.getMaTD());
            ps.setString(2, td.getTenTD());
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateTrinhDo(TrinhDo td) {
        String sql = "UPDATE TrinhDo SET TenTD=? WHERE MaTD=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, td.getTenTD());
            ps.setString(2, td.getMaTD());
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteTrinhDo(String maTD) {
        String sql = "DELETE FROM TrinhDo WHERE MaTD=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, maTD);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
