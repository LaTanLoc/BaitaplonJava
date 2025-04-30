package dao;

import model.Bao;
import util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;

public class BaoDAO {
    private Connection conn;

    public BaoDAO() {
        conn = DatabaseConnection.getConnection();
    }

    public ArrayList<Bao> getAllBao() {
        ArrayList<Bao> list = new ArrayList<>();
        String sql = "SELECT * FROM Bao";
        try (Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Bao b = new Bao(
                        rs.getString("MaBao"),
                        rs.getString("TenBao"),
                        rs.getString("DiaChi"),
                        rs.getString("MaChucNang"),
                        rs.getString("DienThoai"));
                list.add(b);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public boolean insertBao(Bao bao) {
        String sql = "INSERT INTO Bao (MaBao, TenBao, DiaChi, MaChucNang, DienThoai) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, bao.getMaBao());
            stmt.setString(2, bao.getTenBao());
            stmt.setString(3, bao.getDiaChi());
            stmt.setString(4, bao.getMaChucNang());
            stmt.setString(5, bao.getDienThoai());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateBao(Bao bao) {
        String sql = "UPDATE Bao SET TenBao = ?, DiaChi = ?, MaChucNang = ?, DienThoai = ? WHERE MaBao = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, bao.getTenBao());
            stmt.setString(2, bao.getDiaChi());
            stmt.setString(3, bao.getMaChucNang());
            stmt.setString(4, bao.getDienThoai());
            stmt.setString(5, bao.getMaBao());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteBao(String maBao) {
        String sql = "DELETE FROM Bao WHERE MaBao = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, maBao);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
