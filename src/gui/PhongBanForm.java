package gui;

import javax.swing.*;
import java.awt.*;

public class PhongBanForm extends JInternalFrame {

    public PhongBanForm() {
        super("Quản lý Phòng ban", true, true, true, true);
        setSize(600, 400);
        setLocation(210, 210);

        JPanel panel = new JPanel(new BorderLayout());
        JLabel lblTitle = new JLabel("QUẢN LÝ PHÒNG BAN", JLabel.CENTER);
        lblTitle.setFont(new Font("Arial", Font.BOLD, 24));
        panel.add(lblTitle, BorderLayout.NORTH);

        add(panel);
    }
}
