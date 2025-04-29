package gui;

import javax.swing.*;
import java.awt.*;

public class BangGiaForm extends JInternalFrame {

    public BangGiaForm() {
        super("Quản lý Bảng giá", true, true, true, true);
        setSize(600, 400);
        setLocation(150, 150);

        JPanel panel = new JPanel(new BorderLayout());
        JLabel lblTitle = new JLabel("QUẢN LÝ BẢNG GIÁ", JLabel.CENTER);
        lblTitle.setFont(new Font("Arial", Font.BOLD, 24));
        panel.add(lblTitle, BorderLayout.NORTH);

        add(panel);
    }
}
