import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class LoginForm extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;

    public LoginForm() {
        setTitle("Đăng nhập");
        setSize(400, 250);
        setLocationRelativeTo(null); // Hiển thị giữa màn hình
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Tạo panel chính
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50));

        // Tên đăng nhập
        JLabel userLabel = new JLabel("Tên đăng nhập:");
        userLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        usernameField = new JTextField(20);
        usernameField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));

        // Mật khẩu
        JLabel passLabel = new JLabel("Mật khẩu:");
        passLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        passwordField = new JPasswordField(20);
        passwordField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));

        // Nút đăng nhập
        loginButton = new JButton("ĐĂNG NHẬP");
        loginButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        loginButton.setPreferredSize(new Dimension(200, 40));
        loginButton.setFont(new Font("Arial", Font.BOLD, 16));
        loginButton.setBackground(new Color(66, 135, 245));
        loginButton.setForeground(Color.WHITE);

        // Thêm khoảng cách giữa các thành phần
        mainPanel.add(userLabel);
        mainPanel.add(Box.createVerticalStrut(5));
        mainPanel.add(usernameField);
        mainPanel.add(Box.createVerticalStrut(15));
        mainPanel.add(passLabel);
        mainPanel.add(Box.createVerticalStrut(5));
        mainPanel.add(passwordField);
        mainPanel.add(Box.createVerticalStrut(20));
        mainPanel.add(loginButton);

        add(mainPanel, BorderLayout.CENTER);

        // Sự kiện đăng nhập
        loginButton.addActionListener(e -> handleLogin());
    }

    private void handleLogin() {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());

        if (username.equals("admin") && password.equals("123456")) {
            JOptionPane.showMessageDialog(this, "Đăng nhập thành công!");
            // Chuyển sang giao diện chính tại đây nếu cần
        } else {
            JOptionPane.showMessageDialog(this, "Sai tên đăng nhập hoặc mật khẩu!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new LoginForm().setVisible(true));
    }
}
