package gui;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.swing.*;
import util.DatabaseConnection;
import java.awt.*;
import java.awt.event.*;

public class RegisterForm extends JFrame {
    DatabaseConnection databaseConnection = new DatabaseConnection();
    Connection conn = DatabaseConnection.getConnection();
    private JTextField txtHoTen, txtEmail, txtDiaChi, txtTenDangNhap;
    private JPasswordField txtMatKhau;
    private JComboBox<String> cboQuyen;
    private JButton btnDangKy, btnQuayLai;

    public RegisterForm() {
        setTitle("Đăng ký tài khoản");
        setSize(400, 500);
        setLocationRelativeTo(null); // Center the frame
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panel.setLayout(new GridLayout(8, 2, 10, 10));

        // Components
        panel.add(new JLabel("Họ tên:"));
        txtHoTen = new JTextField();
        panel.add(txtHoTen);

        panel.add(new JLabel("Email:"));
        txtEmail = new JTextField();
        panel.add(txtEmail);

        panel.add(new JLabel("Địa chỉ:"));
        txtDiaChi = new JTextField();
        panel.add(txtDiaChi);

        panel.add(new JLabel("Tên đăng nhập:"));
        txtTenDangNhap = new JTextField();
        panel.add(txtTenDangNhap);

        panel.add(new JLabel("Mật khẩu:"));
        txtMatKhau = new JPasswordField();
        panel.add(txtMatKhau);

        panel.add(new JLabel("Quyền:"));
        cboQuyen = new JComboBox<>(new String[] { "1", "2", "3" });
        panel.add(cboQuyen);

        btnDangKy = new JButton("Đăng ký");
        btnQuayLai = new JButton("Quay lại");

        panel.add(btnDangKy);
        panel.add(btnQuayLai);

        add(panel);

        // Sự kiện nút Quay lại
        btnQuayLai.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Mở lại form đăng nhập
                new LoginForm().setVisible(true);
                dispose(); // Đóng form hiện tại
            }
        });

        btnDangKy.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String hoTen = txtHoTen.getText().trim();
                String email = txtEmail.getText().trim();
                String diaChi = txtDiaChi.getText().trim();
                String tenDangNhap = txtTenDangNhap.getText().trim();
                String matKhau = new String(txtMatKhau.getPassword()).trim();
                String quyenText = (String) cboQuyen.getSelectedItem();
                int quyen = 3; // mặc định là Người dùng thường

                if (quyenText.equals("Admin")) {
                    quyen = 1;
                } else if (quyenText.equals("Nhân viên quản lý")) {
                    quyen = 2;
                } else if (quyenText.equals("Người dùng thường")) {
                    quyen = 3;
                }

                // Kiểm tra dữ liệu có rỗng không
                if (hoTen.isEmpty() || email.isEmpty() || diaChi.isEmpty() || tenDangNhap.isEmpty()
                        || matKhau.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Vui lòng nhập đầy đủ thông tin!", "Thông báo",
                            JOptionPane.WARNING_MESSAGE);
                    return;
                }

                // Bắt đầu kết nối và thêm vào database
                Connection conn = null;
                PreparedStatement stmt = null;

                try {
                    conn = DatabaseConnection.getConnection();
                    if (conn != null) {
                        String sql = "INSERT INTO TaiKhoan (HoTen, Email, DiaChi, Tendangnhap, MatKhau, Quyen) VALUES (?, ?, ?, ?, ?, ?)";
                        stmt = conn.prepareStatement(sql);
                        stmt.setString(1, hoTen);
                        stmt.setString(2, email);
                        stmt.setString(3, diaChi);
                        stmt.setString(4, tenDangNhap);
                        stmt.setString(5, matKhau);
                        stmt.setInt(6, quyen);

                        int rowsInserted = stmt.executeUpdate();
                        if (rowsInserted > 0) {
                            JOptionPane.showMessageDialog(null, "Đăng ký thành công!", "Thông báo",
                                    JOptionPane.INFORMATION_MESSAGE);
                            dispose(); // Đóng form đăng ký
                            new LoginForm(); // Mở lại form đăng nhập
                        } else {
                            JOptionPane.showMessageDialog(null, "Đăng ký thất bại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "Không kết nối được đến database!", "Lỗi",
                                JOptionPane.ERROR_MESSAGE);
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Lỗi SQL: " + ex.getMessage(), "Lỗi",
                            JOptionPane.ERROR_MESSAGE);
                } finally {
                    try {
                        if (stmt != null)
                            stmt.close();
                        if (conn != null)
                            conn.close();
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });

    }
}
