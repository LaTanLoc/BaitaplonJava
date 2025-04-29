package DangNhap;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class RegisterForm extends JFrame {
    private JTextField fullNameField, usernameField, phoneField, emailField;
    private JPasswordField passwordField, confirmPasswordField;
    private JButton registerButton;
    private JLabel loginLabel;

    public RegisterForm() {
        setTitle("Đăng ký tài khoản");
        setSize(400, 600);
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

        JLabel titleLabel = new JLabel("ĐĂNG KÝ TÀI KHOẢN");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 22));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        titleLabel.setForeground(new Color(0, 123, 255));

        mainPanel.add(titleLabel);
        mainPanel.add(Box.createVerticalStrut(30));

        fullNameField = new JTextField();
        fullNameField.setBorder(BorderFactory.createTitledBorder("Họ và tên"));
        mainPanel.add(fullNameField);
        mainPanel.add(Box.createVerticalStrut(10));

        usernameField = new JTextField();
        usernameField.setBorder(BorderFactory.createTitledBorder("Tên đăng nhập"));
        mainPanel.add(usernameField);
        mainPanel.add(Box.createVerticalStrut(10));

        passwordField = new JPasswordField();
        passwordField.setBorder(BorderFactory.createTitledBorder("Mật khẩu"));
        mainPanel.add(passwordField);
        mainPanel.add(Box.createVerticalStrut(10));

        confirmPasswordField = new JPasswordField();
        confirmPasswordField.setBorder(BorderFactory.createTitledBorder("Xác nhận mật khẩu"));
        mainPanel.add(confirmPasswordField);
        mainPanel.add(Box.createVerticalStrut(10));

        phoneField = new JTextField();
        phoneField.setBorder(BorderFactory.createTitledBorder("Số điện thoại"));
        mainPanel.add(phoneField);
        mainPanel.add(Box.createVerticalStrut(10));

        emailField = new JTextField();
        emailField.setBorder(BorderFactory.createTitledBorder("Email"));
        mainPanel.add(emailField);
        mainPanel.add(Box.createVerticalStrut(20));

        registerButton = new JButton("Đăng ký");
        registerButton.setBackground(new Color(0, 123, 255));
        registerButton.setForeground(Color.WHITE);
        registerButton.setFont(new Font("Segoe UI", Font.BOLD, 16));
        registerButton.setFocusPainted(false);
        registerButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        mainPanel.add(registerButton);
        mainPanel.add(Box.createVerticalStrut(20));

        loginLabel = new JLabel("Đã có tài khoản? Đăng nhập");
        loginLabel.setForeground(Color.BLUE.darker());
        loginLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        loginLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        mainPanel.add(loginLabel);

        add(mainPanel, BorderLayout.CENTER);

        registerButton.addActionListener(e -> handleRegister());
        loginLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                new LoginForm().setVisible(true);
                dispose();
            }
        });
    }

    private void handleRegister() {
        String password = new String(passwordField.getPassword());
        String confirm = new String(confirmPasswordField.getPassword());

        if (!password.equals(confirm)) {
            JOptionPane.showMessageDialog(this, "Mật khẩu không khớp!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        JOptionPane.showMessageDialog(this, "Đăng ký thành công!");
        new LoginForm().setVisible(true);
        dispose();
    }
}
