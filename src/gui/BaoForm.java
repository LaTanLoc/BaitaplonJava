package gui;

import javax.swing.*;
import java.awt.*;

public class BaoForm extends JInternalFrame {

    public BaoForm() {
        super("Quản lý Báo", true, true, true, true);
        setSize(600, 400);
        setLocation(30, 30);

        JPanel panel = new JPanel(new BorderLayout());
        JLabel lblTitle = new JLabel("QUẢN LÝ BÁO", JLabel.CENTER);
        lblTitle.setFont(new Font("Arial", Font.BOLD, 24));
        panel.add(lblTitle, BorderLayout.NORTH);

        add(panel);
    }
}
