package gui;

import dao.ChucNangDAO;
import model.ChucNang;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class ChucNangForm extends JInternalFrame {
    private JTextField txtMa, txtTen;
    private JButton btnThem, btnSua, btnXoa, btnLuu, btnHuy, btnThoat;
    private JTable table;
    private DefaultTableModel model;
    private ChucNangDAO chucNangDAO = new ChucNangDAO();
    private boolean isThemMoi = false; // Cờ kiểm tra đang Thêm hay không

    public ChucNangForm() {
        setTitle("Quản lý Chức năng");
        setClosable(true);
        setSize(700, 500);
        setLayout(new BorderLayout());

        // Top Panel
        JPanel panelTop = new JPanel(new GridLayout(2, 2, 10, 10));
        panelTop.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panelTop.add(new JLabel("Mã chức năng:"));
        txtMa = new JTextField();
        panelTop.add(txtMa);
        panelTop.add(new JLabel("Tên chức năng:"));
        txtTen = new JTextField();
        panelTop.add(txtTen);
        add(panelTop, BorderLayout.NORTH);

        // Center - Table
        model = new DefaultTableModel(new String[] { "Mã chức năng", "Tên chức năng" }, 0);
        table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        // Bottom - Buttons
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

        add(panelBottom, BorderLayout.SOUTH);

        // Load data
        loadTable();
        setButtonState("default");

        // Events
        table.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (isThemMoi) {
                    JOptionPane.showMessageDialog(ChucNangForm.this, "Bạn đang ở chế độ Thêm mới!");
                    return;
                }
                int row = table.getSelectedRow();
                if (row >= 0) {
                    txtMa.setText(model.getValueAt(row, 0).toString());
                    txtTen.setText(model.getValueAt(row, 1).toString());
                    txtMa.setEditable(false);
                    setButtonState("clicked");
                }
            }
        });

        btnThem.addActionListener(e -> {
            isThemMoi = true;
            clearForm();
            setButtonState("add");
        });

        btnHuy.addActionListener(e -> {
            isThemMoi = false;
            clearForm();
            setButtonState("default");
        });

        btnLuu.addActionListener(e -> {
            if (isThemMoi) {
                them();
            }
        });

        btnSua.addActionListener(e -> {
            sua();
        });

        btnXoa.addActionListener(e -> {
            xoa();
        });

        btnThoat.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(this, "Bạn có chắc chắn muốn thoát?", "Xác nhận",
                    JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                dispose(); // Đóng form
            }
        });
    }

    private void loadTable() {
        model.setRowCount(0);
        ArrayList<ChucNang> list = chucNangDAO.getAllChucNang();
        for (ChucNang cn : list) {
            model.addRow(new Object[] { cn.getMachucnang(), cn.getTenchucnang() });
        }
    }

    private void them() {
        String ma = txtMa.getText().trim();
        String ten = txtTen.getText().trim();
        if (ma.isEmpty() || ten.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin.");
            return;
        }
        ChucNang cn = new ChucNang(ma, ten);
        if (chucNangDAO.addChucNang(cn)) {
            JOptionPane.showMessageDialog(this, "Thêm thành công!");
            isThemMoi = false;
            loadTable();
            clearForm();
            setButtonState("default");
        } else {
            JOptionPane.showMessageDialog(this, "Thêm thất bại!");
        }
    }

    private void sua() {
        String ma = txtMa.getText().trim();
        String ten = txtTen.getText().trim();
        if (ma.isEmpty() || ten.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin.");
            return;
        }
        ChucNang cn = new ChucNang(ma, ten);
        if (chucNangDAO.updateChucNang(cn)) {
            JOptionPane.showMessageDialog(this, "Cập nhật thành công!");
            loadTable();
            clearForm();
            setButtonState("default");
        } else {
            JOptionPane.showMessageDialog(this, "Cập nhật thất bại!");
        }
    }

    private void xoa() {
        String ma = txtMa.getText();
        if (ma.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn chức năng cần xóa.");
            return;
        }
        int confirm = JOptionPane.showConfirmDialog(this, "Bạn có chắc chắn muốn xóa?", "Xác nhận",
                JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            if (chucNangDAO.deleteChucNang(ma)) {
                JOptionPane.showMessageDialog(this, "Xóa thành công!");
                loadTable();
                clearForm();
                setButtonState("default");
            } else {
                JOptionPane.showMessageDialog(this, "Xóa thất bại!");
            }
        }
    }

    private void clearForm() {
        txtMa.setText("");
        txtTen.setText("");
        txtMa.setEditable(true);
    }

    // Hàm điều khiển trạng thái nút
    private void setButtonState(String state) {
        switch (state) {
            case "default": // Load lần đầu hoặc sau Hủy
                btnThem.setEnabled(true);
                btnHuy.setEnabled(true);
                btnThoat.setEnabled(true);
                btnLuu.setEnabled(false);
                btnSua.setEnabled(false);
                btnXoa.setEnabled(false);
                break;
            case "add": // Khi Thêm mới
                btnThem.setEnabled(false);
                btnHuy.setEnabled(true);
                btnThoat.setEnabled(true);
                btnLuu.setEnabled(true);
                btnSua.setEnabled(false);
                btnXoa.setEnabled(false);
                break;
            case "clicked": // Khi click vào bảng
                btnThem.setEnabled(true);
                btnHuy.setEnabled(true);
                btnThoat.setEnabled(true);
                btnLuu.setEnabled(false);
                btnSua.setEnabled(true);
                btnXoa.setEnabled(true);
                break;
        }
    }
}
