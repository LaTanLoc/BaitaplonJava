package gui;

import javax.swing.*;
import java.awt.*;

public class ChucVuForm extends JInternalFrame {

    public ChucVuForm() {
        super("Quản lý Chức vụ", true, true, true, true);
        setSize(600, 400);
        setLocation(270, 270);

        JPanel panel = new JPanel(new BorderLayout());
        JLabel lblTitle = new JLabel("QUẢN LÝ CHỨC VỤ", JLabel.CENTER);
        lblTitle.setFont(new Font("Arial", Font.BOLD, 24));
        panel.add(lblTitle, BorderLayout.NORTH);

        add(panel);
    }
}
