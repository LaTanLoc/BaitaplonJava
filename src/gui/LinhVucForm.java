package gui;

import javax.swing.*;
import java.awt.*;

public class LinhVucForm extends JInternalFrame {

    public LinhVucForm() {
        super("Quản lý Lĩnh vực hoạt động", true, true, true, true);
        setSize(600, 400);
        setLocation(110, 110);

        JPanel panel = new JPanel(new BorderLayout());
        JLabel lblTitle = new JLabel("QUẢN LÝ LĨNH VỰC HOẠT ĐỘNG", JLabel.CENTER);
        lblTitle.setFont(new Font("Arial", Font.BOLD, 22));
        panel.add(lblTitle, BorderLayout.NORTH);

        add(panel);
    }
}
