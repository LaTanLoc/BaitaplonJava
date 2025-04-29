package DangNhap;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class LoginForm extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JLabel registerLabel;

    public LoginForm() {
        setTitle("Đăng nhập hệ thống");
        setSize(400, 450);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel mainPanel = new JPanel();
        mainPanel.setBackground(Color.WHITE);
        mainPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createEmptyBorder(20, 20, 20, 20),
                BorderFactory.createLineBorder(new Color(200, 200, 200))
        ));
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        JLabel titleLabel = new JLabel("HỆ THỐNG QUẢN LÝ");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 22));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        titleLabel.setForeground(new Color(0, 123, 255));

        JLabel subtitleLabel = new JLabel("Hợp đồng quảng cáo");
        subtitleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        subtitleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        mainPanel.add(titleLabel);
        mainPanel.add(subtitleLabel);
        mainPanel.add(Box.createVerticalStrut(30));

        usernameField = new JTextField(15);
        usernameField.setBorder(BorderFactory.createTitledBorder("Tên đăng nhập"));
        mainPanel.add(usernameField);
        mainPanel.add(Box.createVerticalStrut(15));

        passwordField = new JPasswordField(15);
        passwordField.setBorder(BorderFactory.createTitledBorder("Mật khẩu"));
        mainPanel.add(passwordField);
        mainPanel.add(Box.createVerticalStrut(15));

        loginButton = new JButton("Đăng nhập");
        loginButton.setBackground(new Color(0, 123, 255));
        loginButton.setForeground(Color.WHITE);
        loginButton.setFont(new Font("Segoe UI", Font.BOLD, 16));
        loginButton.setFocusPainted(false);
        loginButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        mainPanel.add(loginButton);
        mainPanel.add(Box.createVerticalStrut(20));

        registerLabel = new JLabel("Chưa có tài khoản? Đăng ký ngay");
        registerLabel.setForeground(Color.BLUE.darker());
        registerLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        registerLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        mainPanel.add(registerLabel);

        add(mainPanel, BorderLayout.CENTER);

        loginButton.addActionListener(e -> handleLogin());
        registerLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                new RegisterForm().setVisible(true);
                dispose();
            }
        });
    }

    private void handleLogin() {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());

        if (username.equals("admin") && password.equals("123456")) {
            JOptionPane.showMessageDialog(this, "Đăng nhập thành công!");
        } else {
            JOptionPane.showMessageDialog(this, "Sai tên đăng nhập hoặc mật khẩu!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }
}
