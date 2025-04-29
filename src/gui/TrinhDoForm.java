package gui;

import javax.swing.*;
import java.awt.*;

public class TrinhDoForm extends JInternalFrame {

    public TrinhDoForm() {
        super("Quản lý Trình độ", true, true, true, true);
        setSize(600, 400);
        setLocation(250, 250);

        JPanel panel = new JPanel(new BorderLayout());
        JLabel lblTitle = new JLabel("QUẢN LÝ TRÌNH ĐỘ", JLabel.CENTER);
        lblTitle.setFont(new Font("Arial", Font.BOLD, 24));
        panel.add(lblTitle, BorderLayout.NORTH);

        add(panel);
    }
}
