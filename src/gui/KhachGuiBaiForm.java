package gui;

import javax.swing.*;
import java.awt.*;

public class KhachGuiBaiForm extends JInternalFrame {

    public KhachGuiBaiForm() {
        super("Quản lý Khách gửi bài", true, true, true, true);
        setSize(600, 400);
        setLocation(170, 170);

        JPanel panel = new JPanel(new BorderLayout());
        JLabel lblTitle = new JLabel("QUẢN LÝ KHÁCH GỬI BÀI", JLabel.CENTER);
        lblTitle.setFont(new Font("Arial", Font.BOLD, 22));
        panel.add(lblTitle, BorderLayout.NORTH);

        add(panel);
    }
}
