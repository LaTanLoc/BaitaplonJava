package gui;

import javax.swing.*;
import java.awt.*;

public class KhachQuangCaoForm extends JInternalFrame {

    public KhachQuangCaoForm() {
        super("Quản lý Khách - Quảng cáo", true, true, true, true);
        setSize(600, 400);
        setLocation(190, 190);

        JPanel panel = new JPanel(new BorderLayout());
        JLabel lblTitle = new JLabel("QUẢN LÝ KHÁCH - QUẢNG CÁO", JLabel.CENTER);
        lblTitle.setFont(new Font("Arial", Font.BOLD, 22));
        panel.add(lblTitle, BorderLayout.NORTH);

        add(panel);
    }
}
