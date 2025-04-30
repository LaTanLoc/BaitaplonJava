package dao;

import model.KhachHang;
import util.DatabaseConnection;
import java.sql.*;
import java.util.ArrayList;

public class KhachHangDAO {
    Connection conn = DatabaseConnection.getConnection();

    public ArrayList<KhachHang> getAllKhachHang() {
        ArrayList<KhachHang> list = new ArrayList<>();
        String sql = "SELECT * FROM KhachHang";
        try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                list.add(new KhachHang(
                        rs.getString("MaKH"),
                        rs.getString("TenKH"),
                        rs.getString("DiaChi"),
                        rs.getString("DienThoai"),
                        rs.getString("Email"),
                        rs.getString("MaLVHD")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public boolean insertKhachHang(KhachHang kh) {
        String sql = "INSERT INTO KhachHang VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, kh.getMaKH());
            stmt.setString(2, kh.getTenKH());
            stmt.setString(3, kh.getDiaChi());
            stmt.setString(4, kh.getDienThoai());
            stmt.setString(5, kh.getEmail());
            stmt.setString(6, kh.getMaLVHD());
            return stmt.executeUpdate() > 0;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean updateKhachHang(KhachHang kh) {
        String sql = "UPDATE KhachHang SET TenKH=?, DiaChi=?, DienThoai=?, Email=?, MaLVHD=? WHERE MaKH=?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, kh.getTenKH());
            stmt.setString(2, kh.getDiaChi());
            stmt.setString(3, kh.getDienThoai());
            stmt.setString(4, kh.getEmail());
            stmt.setString(5, kh.getMaLVHD());
            stmt.setString(6, kh.getMaKH());
            return stmt.executeUpdate() > 0;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean deleteKhachHang(String maKH) {
        String sql = "DELETE FROM KhachHang WHERE MaKH=?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, maKH);
            return stmt.executeUpdate() > 0;
        } catch (Exception e) {
            return false;
        }
    }
}
