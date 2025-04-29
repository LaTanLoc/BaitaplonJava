package dao;

import model.NhanVien;
import util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;

public class NhanVienDAO {

    public ArrayList<NhanVien> getAllNhanVien() {
        ArrayList<NhanVien> list = new ArrayList<>();
        String sql = "SELECT * FROM NhanVien";
        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                NhanVien nv = new NhanVien(
                        rs.getString("MaNV"),
                        rs.getString("TenNV"),
                        rs.getString("MaPhong"),
                        rs.getString("MaChucVu"),
                        rs.getString("MaTD"),
                        rs.getString("MaCM"),
                        rs.getString("DiaChi"),
                        rs.getString("NgaySinh"),
                        rs.getString("GioiTinh"),
                        rs.getString("DienThoai"),
                        rs.getString("Mobile"),
                        rs.getString("Email"));
                list.add(nv);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public boolean insertNhanVien(NhanVien nv) {
        String sql = "INSERT INTO NhanVien (MaNV, TenNV, MaPhong, MaChucVu, MaTD, MaCM, DiaChi, NgaySinh, GioiTinh, DienThoai, Mobile, Email) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, nv.getMaNV());
            ps.setString(2, nv.getTenNV());
            ps.setString(3, nv.getMaPhong());
            ps.setString(4, nv.getMaChucVu());
            ps.setString(5, nv.getMaTD());
            ps.setString(6, nv.getMaCM());
            ps.setString(7, nv.getDiaChi());
            ps.setString(8, nv.getNgaySinh());
            ps.setString(9, nv.getGioiTinh());
            ps.setString(10, nv.getDienThoai());
            ps.setString(11, nv.getMobile());
            ps.setString(12, nv.getEmail());

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean updateNhanVien(NhanVien nv) {
        String sql = "UPDATE NhanVien SET TenNV=?, MaPhong=?, MaChucVu=?, MaTD=?, MaCM=?, DiaChi=?, NgaySinh=?, GioiTinh=?, DienThoai=?, Mobile=?, Email=? WHERE MaNV=?";
        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, nv.getTenNV());
            ps.setString(2, nv.getMaPhong());
            ps.setString(3, nv.getMaChucVu());
            ps.setString(4, nv.getMaTD());
            ps.setString(5, nv.getMaCM());
            ps.setString(6, nv.getDiaChi());
            ps.setString(7, nv.getNgaySinh());
            ps.setString(8, nv.getGioiTinh());
            ps.setString(9, nv.getDienThoai());
            ps.setString(10, nv.getMobile());
            ps.setString(11, nv.getEmail());
            ps.setString(12, nv.getMaNV());

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean deleteNhanVien(String maNV) {
        String sql = "DELETE FROM NhanVien WHERE MaNV=?";
        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, maNV);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
