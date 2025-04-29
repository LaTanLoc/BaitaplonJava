package gui;

import javax.swing.*;
import java.awt.*;

public class TheLoaiForm extends JInternalFrame {

    public TheLoaiForm() {
        super("Quản lý Thể loại", true, true, true, true);
        setSize(600, 400);
        setLocation(90, 90);

        JPanel panel = new JPanel(new BorderLayout());
        JLabel lblTitle = new JLabel("QUẢN LÝ THỂ LOẠI", JLabel.CENTER);
        lblTitle.setFont(new Font("Arial", Font.BOLD, 24));
        panel.add(lblTitle, BorderLayout.NORTH);

        add(panel);
    }
}
