package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class MainForm extends JFrame {
    private JDesktopPane desktopPane;

    public MainForm() {
        setTitle("Quản lý hệ thống");
        setExtendedState(JFrame.NORMAL);
        setSize(1100, 1100);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        desktopPane = new JDesktopPane();
        setContentPane(desktopPane);

        JMenuBar menuBar = new JMenuBar();

        // --- Quản lý ---
        JMenu menuQuanLy = new JMenu("Quản lý");

        JMenuItem menuBao = new JMenuItem("Báo");
        menuBao.addActionListener(e -> openForm(new BaoForm()));
        menuQuanLy.add(menuBao);

        JMenuItem menuNhanVien = new JMenuItem("Nhân viên");
        menuNhanVien.addActionListener(e -> openForm(new NhanVienForm())); // <- gọi đúng NhanVienForm
        menuQuanLy.add(menuNhanVien);

        JMenuItem menuKhachHang = new JMenuItem("Khách hàng");
        menuKhachHang.addActionListener(e -> openForm(new KhachHangForm()));
        menuQuanLy.add(menuKhachHang);

        JMenuItem menuTheLoai = new JMenuItem("Thể loại");
        menuTheLoai.addActionListener(e -> openForm(new TheLoaiForm()));
        menuQuanLy.add(menuTheLoai);

        JMenuItem menuLinhVuc = new JMenuItem("Lĩnh vực hoạt động");
        menuLinhVuc.addActionListener(e -> openForm(new LinhVucForm()));
        menuQuanLy.add(menuLinhVuc);

        JMenuItem menuTTQuangCao = new JMenuItem("TT Quảng cáo");
        menuTTQuangCao.addActionListener(e -> openForm(new TTQuangCaoForm()));
        menuQuanLy.add(menuTTQuangCao);

        JMenuItem menuBangGia = new JMenuItem("Bảng giá");
        menuBangGia.addActionListener(e -> openForm(new BangGiaForm()));
        menuQuanLy.add(menuBangGia);

        JMenuItem menuKhachGuiBai = new JMenuItem("Khách gửi bài");
        menuKhachGuiBai.addActionListener(e -> openForm(new KhachGuiBaiForm()));
        menuQuanLy.add(menuKhachGuiBai);

        JMenuItem menuKhachQuangCao = new JMenuItem("Khách - Quảng cáo");
        menuKhachQuangCao.addActionListener(e -> openForm(new KhachQuangCaoForm()));
        menuQuanLy.add(menuKhachQuangCao);

        menuBar.add(menuQuanLy);

        // --- Danh mục ---
        JMenu menuDanhMuc = new JMenu("Danh mục");

        JMenuItem menuPhongBan = new JMenuItem("Phòng ban");
        menuPhongBan.addActionListener(e -> openForm(new PhongBanForm()));
        menuDanhMuc.add(menuPhongBan);

        JMenuItem menuChuyenMon = new JMenuItem("Chuyên môn");
        menuChuyenMon.addActionListener(e -> openForm(new ChuyenMonForm()));
        menuDanhMuc.add(menuChuyenMon);

        JMenuItem menuTrinhDo = new JMenuItem("Trình độ");
        menuTrinhDo.addActionListener(e -> openForm(new TrinhDoForm()));
        menuDanhMuc.add(menuTrinhDo);

        JMenuItem menuChucVu = new JMenuItem("Chức vụ");
        menuChucVu.addActionListener(e -> openForm(new ChucVuForm()));
        menuDanhMuc.add(menuChucVu);

        JMenuItem menuChucNang = new JMenuItem("Chức năng");
        menuChucNang.addActionListener(e -> openForm(new ChucNangForm()));
        menuDanhMuc.add(menuChucNang);

        menuBar.add(menuDanhMuc);

        // --- Tài khoản ---
        JMenu menuTaiKhoan = new JMenu("Tài khoản");

        JMenuItem menuDangXuat = new JMenuItem("Đăng xuất");
        menuDangXuat.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(this, "Bạn có chắc chắn muốn đăng xuất?", "Đăng xuất",
                    JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                new LoginForm().setVisible(true);
                dispose(); // đóng MainForm
            }
        });
        menuTaiKhoan.add(menuDangXuat);

        JMenuItem menuThoat = new JMenuItem("Thoát");
        menuThoat.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(this, "Bạn có chắc chắn muốn thoát?", "Thoát",
                    JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                System.exit(0);
            }
        });
        menuTaiKhoan.add(menuThoat);

        menuBar.add(menuTaiKhoan);

        setJMenuBar(menuBar);
    }

    private void openForm(JInternalFrame form) {
        // Kiểm tra xem form đã mở chưa
        for (JInternalFrame frame : desktopPane.getAllFrames()) {
            if (frame.getClass().equals(form.getClass())) {
                try {
                    frame.setSelected(true); // focus vào form cũ
                } catch (java.beans.PropertyVetoException ex) {
                    ex.printStackTrace();
                }
                return;
            }
        }
        // Nếu chưa mở thì add form mới
        desktopPane.add(form);
        form.setVisible(true);
    }
}
