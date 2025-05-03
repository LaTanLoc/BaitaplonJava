package gui;

import dao.ChuyenMonDAO;
import model.ChuyenMon;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;

public class ChuyenMonForm extends JInternalFrame {
    private JTextField txtMaCM, txtTenCM;
    private JTable table;
    private DefaultTableModel model;
    private JButton btnThem, btnSua, btnXoa, btnLuu, btnHuy, btnThoat;
    private boolean isAdding = false;

    private ChuyenMonDAO dao = new ChuyenMonDAO();

    public ChuyenMonForm() {
        setTitle("Quản lý Chuyên môn");
        setClosable(true);
        setBounds(100, 100, 600, 400);
        getContentPane().setLayout(new BorderLayout());

        // Panel nhập liệu
        JPanel panelTop = new JPanel(new GridLayout(2, 2, 10, 10));
        panelTop.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panelTop.add(new JLabel("Mã CM:"));
        txtMaCM = new JTextField();
        panelTop.add(txtMaCM);
        panelTop.add(new JLabel("Tên CM:"));
        txtTenCM = new JTextField();
        panelTop.add(txtTenCM);
        getContentPane().add(panelTop, BorderLayout.NORTH);

        // Bảng
        model = new DefaultTableModel();
        model.setColumnIdentifiers(new String[] { "Mã CM", "Tên CM" });
        table = new JTable(model);
        JScrollPane scroll = new JScrollPane(table);
        getContentPane().add(scroll, BorderLayout.CENTER);

        // Panel nút
        JPanel panelBottom = new JPanel();
        btnThem = new JButton("Thêm");
        btnSua = new JButton("Sửa");
        btnXoa = new JButton("Xóa");
        btnLuu = new JButton("Lưu");
        btnHuy = new JButton("Hủy bỏ");
        btnThoat = new JButton("Thoát");
        panelBottom.add(btnThem);
        panelBottom.add(btnSua);
        panelBottom.add(btnXoa);
        panelBottom.add(btnLuu);
        panelBottom.add(btnHuy);
        panelBottom.add(btnThoat);
        getContentPane().add(panelBottom, BorderLayout.SOUTH);

        loadData();
        setButtonState("init");

        btnThem.addActionListener(e -> {
            clearFields();
            isAdding = true;
            setButtonState("add");
        });

        btnLuu.addActionListener(e -> {
            ChuyenMon cm = new ChuyenMon(txtMaCM.getText().trim(), txtTenCM.getText().trim());
            if (cm.getMaCM().isEmpty() || cm.getTenCM().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Vui lòng nhập đầy đủ thông tin!");
                return;
            }

            if (isAdding) {
                if (dao.insertChuyenMon(cm)) {
                    JOptionPane.showMessageDialog(null, "Thêm thành công!");
                    loadData();
                } else {
                    JOptionPane.showMessageDialog(null, "Thêm thất bại!");
                }
                isAdding = false;
            } else {
                if (dao.updateChuyenMon(cm)) {
                    JOptionPane.showMessageDialog(null, "Cập nhật thành công!");
                    loadData();
                } else {
                    JOptionPane.showMessageDialog(null, "Cập nhật thất bại!");
                }
            }
            clearFields();
            setButtonState("init");
        });

        btnSua.addActionListener(e -> {
            if (txtMaCM.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Vui lòng chọn chuyên môn để sửa.");
                return;
            }
            isAdding = false;
            txtMaCM.setEditable(false);
            setButtonState("edit");
        });

        btnXoa.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row >= 0) {
                String ma = model.getValueAt(row, 0).toString();
                int confirm = JOptionPane.showConfirmDialog(null, "Bạn có chắc muốn xóa?", "Xác nhận",
                        JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    if (dao.deleteChuyenMon(ma)) {
                        JOptionPane.showMessageDialog(null, "Xóa thành công!");
                        loadData();
                        clearFields();
                        setButtonState("init");
                    } else {
                        JOptionPane.showMessageDialog(null, "Xóa thất bại!");
                    }
                }
            } else {
                JOptionPane.showMessageDialog(null, "Vui lòng chọn chuyên môn cần xóa!");
            }
        });

        btnHuy.addActionListener(e -> {
            clearFields();
            isAdding = false;
            setButtonState("init");
        });

        btnThoat.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(this, "Bạn có chắc chắn muốn thoát?", "Xác nhận",
                    JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                dispose();
            }
        });

        // Sự kiện click bảng
        table.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                if (isAdding) {
                    JOptionPane.showMessageDialog(ChuyenMonForm.this,
                            "Bạn đang ở chế độ thêm mới. Không chọn dữ liệu!");
                    return;
                }

                int row = table.getSelectedRow();
                if (row >= 0) {
                    txtMaCM.setText(model.getValueAt(row, 0).toString());
                    txtTenCM.setText(model.getValueAt(row, 1).toString());
                    txtMaCM.setEditable(false);
                    setButtonState("selected");
                }
            }
        });
    }

    private void loadData() {
        model.setRowCount(0);
        ArrayList<ChuyenMon> list = dao.getAllChuyenMon();
        for (ChuyenMon cm : list) {
            model.addRow(new Object[] { cm.getMaCM(), cm.getTenCM() });
        }
    }

    private void clearFields() {
        txtMaCM.setText("");
        txtTenCM.setText("");
        txtMaCM.setEditable(true);
    }

    private void setButtonState(String state) {
        switch (state) {
            case "init":
                btnThem.setEnabled(true);
                btnSua.setEnabled(false);
                btnXoa.setEnabled(false);
                btnLuu.setEnabled(false);
                btnHuy.setEnabled(true);
                btnThoat.setEnabled(true);
                break;
            case "add":
                btnThem.setEnabled(false);
                btnSua.setEnabled(false);
                btnXoa.setEnabled(false);
                btnLuu.setEnabled(true);
                btnHuy.setEnabled(true);
                btnThoat.setEnabled(true);
                break;
            case "edit":
                btnThem.setEnabled(false);
                btnSua.setEnabled(false);
                btnXoa.setEnabled(false);
                btnLuu.setEnabled(true);
                btnHuy.setEnabled(true);
                btnThoat.setEnabled(true);
                break;
            case "selected":
                btnThem.setEnabled(true);
                btnSua.setEnabled(true);
                btnXoa.setEnabled(true);
                btnLuu.setEnabled(false);
                btnHuy.setEnabled(true);
                btnThoat.setEnabled(true);
                break;
        }
    }
}
