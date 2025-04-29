package gui;

import javax.swing.*;
import java.awt.*;

public class TTQuangCaoForm extends JInternalFrame {

    public TTQuangCaoForm() {
        super("Quản lý TT Quảng cáo", true, true, true, true);
        setSize(600, 400);
        setLocation(130, 130);

        JPanel panel = new JPanel(new BorderLayout());
        JLabel lblTitle = new JLabel("QUẢN LÝ TT QUẢNG CÁO", JLabel.CENTER);
        lblTitle.setFont(new Font("Arial", Font.BOLD, 22));
        panel.add(lblTitle, BorderLayout.NORTH);

        add(panel);
    }
}
