package gui;

import javax.swing.*;
import java.awt.*;

public class KhachHangForm extends JInternalFrame {

    public KhachHangForm() {
        super("Quản lý Khách hàng", true, true, true, true);
        setSize(600, 400);
        setLocation(70, 70);

        JPanel panel = new JPanel(new BorderLayout());
        JLabel lblTitle = new JLabel("QUẢN LÝ KHÁCH HÀNG", JLabel.CENTER);
        lblTitle.setFont(new Font("Arial", Font.BOLD, 24));
        panel.add(lblTitle, BorderLayout.NORTH);

        add(panel);
    }
}
