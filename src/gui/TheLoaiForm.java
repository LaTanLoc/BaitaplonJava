package gui;

import dao.TheLoaiDAO;
import model.TheLoai;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class TheLoaiForm extends JInternalFrame {
    private JTextField txtMa, txtTen;
    private JButton btnThem, btnSua, btnXoa, btnLuu, btnHuy, btnThoat;
    private JTable table;
    private DefaultTableModel model;
    private TheLoaiDAO theLoaiDAO = new TheLoaiDAO();
    private boolean isThemMoi = false;

    public TheLoaiForm() {
        setTitle("Quản lý Thể loại");
        setClosable(true);
        setSize(700, 500);
        setLayout(new BorderLayout());

        // Top - Input fields
        JPanel panelTop = new JPanel(new GridLayout(2, 2, 10, 10));
        panelTop.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panelTop.add(new JLabel("Mã thể loại:"));
        txtMa = new JTextField();
        panelTop.add(txtMa);
        panelTop.add(new JLabel("Tên thể loại:"));
        txtTen = new JTextField();
        panelTop.add(txtTen);
        add(panelTop, BorderLayout.NORTH);

        // Center - Table
        model = new DefaultTableModel(new String[] { "Mã thể loại", "Tên thể loại" }, 0);
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

        loadTable();
        setButtonState("default");

        // Events
        table.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (isThemMoi) {
                    JOptionPane.showMessageDialog(TheLoaiForm.this, "Bạn đang ở chế độ Thêm mới!");
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

        btnSua.addActionListener(e -> sua());
        btnXoa.addActionListener(e -> xoa());
        btnThoat.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(this, "Bạn có chắc chắn muốn thoát?", "Xác nhận",
                    JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                dispose();
            }
        });
    }

    private void loadTable() {
        model.setRowCount(0);
        ArrayList<TheLoai> list = theLoaiDAO.getAllTheLoai();
        for (TheLoai tl : list) {
            model.addRow(new Object[] { tl.getMaTheLoai(), tl.getTenTheLoai() });
        }
    }

    private void them() {
        String ma = txtMa.getText().trim();
        String ten = txtTen.getText().trim();
        if (ma.isEmpty() || ten.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin.");
            return;
        }
        TheLoai tl = new TheLoai(ma, ten);
        if (theLoaiDAO.insertTheLoai(tl)) {
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
        TheLoai tl = new TheLoai(ma, ten);
        if (theLoaiDAO.updateTheLoai(tl)) {
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
            JOptionPane.showMessageDialog(this, "Vui lòng chọn thể loại cần xóa.");
            return;
        }
        int confirm = JOptionPane.showConfirmDialog(this, "Bạn có chắc chắn muốn xóa?", "Xác nhận",
                JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            if (theLoaiDAO.deleteTheLoai(ma)) {
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

    private void setButtonState(String state) {
        switch (state) {
            case "default":
                btnThem.setEnabled(true);
                btnHuy.setEnabled(true);
                btnThoat.setEnabled(true);
                btnLuu.setEnabled(false);
                btnSua.setEnabled(false);
                btnXoa.setEnabled(false);
                break;
            case "add":
                btnThem.setEnabled(false);
                btnHuy.setEnabled(true);
                btnThoat.setEnabled(true);
                btnLuu.setEnabled(true);
                btnSua.setEnabled(false);
                btnXoa.setEnabled(false);
                break;
            case "clicked":
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
