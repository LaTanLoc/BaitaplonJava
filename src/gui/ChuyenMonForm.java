package gui;

import javax.swing.*;
import java.awt.*;

public class ChuyenMonForm extends JInternalFrame {

    public ChuyenMonForm() {
        super("Quản lý Chuyên môn", true, true, true, true);
        setSize(600, 400);
        setLocation(230, 230);

        JPanel panel = new JPanel(new BorderLayout());
        JLabel lblTitle = new JLabel("QUẢN LÝ CHUYÊN MÔN", JLabel.CENTER);
        lblTitle.setFont(new Font("Arial", Font.BOLD, 24));
        panel.add(lblTitle, BorderLayout.NORTH);

        add(panel);
    }
}
