package gui;

import dao.TaiKhoanDAO;
import model.TaiKhoan;
import util.DatabaseConnection; // Đảm bảo bạn import đúng class DatabaseConnection
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.SQLException;

public class LoginForm extends JFrame {
    private JTextField txtUsername;
    private JPasswordField txtPassword;
    private JButton btnLogin, btnExit, btnRegister;

    public LoginForm() {
        setTitle("Đăng nhập hệ thống");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel panel = new JPanel();
        panel.setLayout(null);

        JLabel lblTitle = new JLabel("ĐĂNG NHẬP", SwingConstants.CENTER);
        lblTitle.setFont(new Font("Arial", Font.BOLD, 24));
        lblTitle.setBounds(100, 20, 200, 30);
        panel.add(lblTitle);

        JLabel lblUsername = new JLabel("Tên đăng nhập:");
        lblUsername.setBounds(50, 80, 100, 25);
        panel.add(lblUsername);

        txtUsername = new JTextField();
        txtUsername.setBounds(160, 80, 180, 25);
        panel.add(txtUsername);

        JLabel lblPassword = new JLabel("Mật khẩu:");
        lblPassword.setBounds(50, 120, 100, 25);
        panel.add(lblPassword);

        txtPassword = new JPasswordField();
        txtPassword.setBounds(160, 120, 180, 25);
        panel.add(txtPassword);

        btnLogin = new JButton("Đăng nhập");
        btnLogin.setBounds(50, 170, 130, 30);
        panel.add(btnLogin);

        btnRegister = new JButton("Đăng ký");
        btnRegister.setBounds(210, 170, 130, 30);
        panel.add(btnRegister);

        btnExit = new JButton("Thoát");
        btnExit.setBounds(130, 220, 130, 30);
        panel.add(btnExit);

        btnExit.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(this, "Bạn có chắc chắn muốn thoát không?", "Xác nhận thoát",
                    JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                System.exit(0); // Thoát ứng dụng
            }
        });
        add(panel);

        setVisible(true);

        // Action Events
        btnRegister.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                RegisterForm registerForm = new RegisterForm();
                registerForm.setVisible(true);
                dispose(); // đóng form Đăng nhập đi
            }
        });

        btnLogin.addActionListener(e -> {
            String tenDangNhap = txtUsername.getText();
            String matKhau = new String(txtPassword.getPassword());

            if (tenDangNhap.isEmpty() || matKhau.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin!");
                return;
            }

            // Kiểm tra kết nối CSDL
            if (!checkDatabaseConnection()) {
                JOptionPane.showMessageDialog(this, "Không thể kết nối tới cơ sở dữ liệu. Vui lòng kiểm tra kết nối.");
                return;
            }

            TaiKhoanDAO taiKhoanDAO = new TaiKhoanDAO();
            TaiKhoan tk = taiKhoanDAO.dangNhap(tenDangNhap, matKhau);

            if (tk != null) {
                JOptionPane.showMessageDialog(this, "Đăng nhập thành công!");

                // Mở MainForm sau khi đăng nhập thành công
                this.dispose(); // đóng LoginForm
                new gui.MainForm().setVisible(true); // Mở MainForm
            } else {
                JOptionPane.showMessageDialog(this, "Sai tên đăng nhập hoặc mật khẩu!");
            }
        });
    }

    // Phương thức kiểm tra kết nối cơ sở dữ liệu
    private boolean checkDatabaseConnection() {
        try (Connection conn = DatabaseConnection.getConnection()) {
            if (conn != null) {
                return true; // Nếu kết nối thành công
            } else {
                return false; // Nếu không thể kết nối
            }
        } catch (SQLException e) {
            return false; // Nếu có lỗi kết nối
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new LoginForm());
    }
}
