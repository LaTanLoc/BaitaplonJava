package gui;

import dao.KhachHangDAO;
import model.KhachHang;

import javax.swing.*;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class KhachHangForm extends JInternalFrame {
    private JTextField txtMaKH, txtTenKH, txtDiaChi, txtDienThoai, txtEmail, txtMaLVHD;
    private JTable table;
    private DefaultTableModel model;
    private JButton btnThem, btnSua, btnXoa, btnLuu, btnHuy, btnThoat;
    private boolean isAdding = false;

    private KhachHangDAO khachHangDAO = new KhachHangDAO();

    public KhachHangForm() {
        setTitle("Quản lý Khách hàng");
        setClosable(true);
        setBounds(100, 100, 800, 500);
        getContentPane().setLayout(new BorderLayout());

        JPanel panelTop = new JPanel(new GridLayout(6, 2, 10, 10));
        panelTop.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        panelTop.add(new JLabel("Mã KH:"));
        txtMaKH = new JTextField();
        panelTop.add(txtMaKH);
        panelTop.add(new JLabel("Tên KH:"));
        txtTenKH = new JTextField();
        panelTop.add(txtTenKH);
        panelTop.add(new JLabel("Địa chỉ:"));
        txtDiaChi = new JTextField();
        panelTop.add(txtDiaChi);
        panelTop.add(new JLabel("Điện thoại:"));
        txtDienThoai = new JTextField();
        panelTop.add(txtDienThoai);
        panelTop.add(new JLabel("Email:"));
        txtEmail = new JTextField();
        panelTop.add(txtEmail);
        panelTop.add(new JLabel("Mã LVHD:"));
        txtMaLVHD = new JTextField();
        panelTop.add(txtMaLVHD);

        getContentPane().add(panelTop, BorderLayout.NORTH);

        model = new DefaultTableModel();
        model.setColumnIdentifiers(new String[] { "Mã KH", "Tên KH", "Địa chỉ", "Điện thoại", "Email", "Mã LVHD" });
        table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);
        getContentPane().add(scrollPane, BorderLayout.CENTER);

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
            KhachHang kh = getKhachHangFromFields();
            if (isAdding) {
                if (khachHangDAO.insertKhachHang(kh)) {
                    JOptionPane.showMessageDialog(null, "Thêm thành công!");
                    loadData();
                } else {
                    JOptionPane.showMessageDialog(null, "Thêm thất bại!");
                }
                isAdding = false;
            } else {
                if (khachHangDAO.updateKhachHang(kh)) {
                    JOptionPane.showMessageDialog(null, "Cập nhật thành công!");
                    loadData();
                } else {
                    JOptionPane.showMessageDialog(null, "Cập nhật thất bại!");
                }
            }
            setButtonState("init");
        });

        btnSua.addActionListener(e -> {
            isAdding = false;
            setButtonState("edit");
        });

        btnXoa.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row >= 0) {
                String maKH = model.getValueAt(row, 0).toString();
                if (khachHangDAO.deleteKhachHang(maKH)) {
                    JOptionPane.showMessageDialog(null, "Xóa thành công!");
                    loadData();
                } else {
                    JOptionPane.showMessageDialog(null, "Xóa thất bại!");
                }
            }
        });

        btnHuy.addActionListener(e -> {
            clearFields();
            setButtonState("init");
        });

        btnThoat.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(this, "Bạn có chắc chắn muốn thoát?", "Xác nhận",
                    JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                dispose();
            }
        });

        table.getSelectionModel().addListSelectionListener((ListSelectionListener) e -> {
            if (!e.getValueIsAdjusting()) {
                if (isAdding) {
                    JOptionPane.showMessageDialog(null, "Bạn đang ở chế độ thêm mới. Không chọn dữ liệu!");
                    table.clearSelection();
                    return;
                }
                showSelectedRow();
                setButtonState("selected");
            }
        });
    }

    private void loadData() {
        model.setRowCount(0);
        for (KhachHang kh : khachHangDAO.getAllKhachHang()) {
            model.addRow(new Object[] {
                    kh.getMaKH(), kh.getTenKH(), kh.getDiaChi(), kh.getDienThoai(), kh.getEmail(), kh.getMaLVHD()
            });
        }
    }

    private void clearFields() {
        txtMaKH.setText("");
        txtTenKH.setText("");
        txtDiaChi.setText("");
        txtDienThoai.setText("");
        txtEmail.setText("");
        txtMaLVHD.setText("");
    }

    private void showSelectedRow() {
        int row = table.getSelectedRow();
        if (row >= 0) {
            txtMaKH.setText(model.getValueAt(row, 0).toString());
            txtTenKH.setText(model.getValueAt(row, 1).toString());
            txtDiaChi.setText(model.getValueAt(row, 2).toString());
            txtDienThoai.setText(model.getValueAt(row, 3).toString());
            txtEmail.setText(model.getValueAt(row, 4).toString());
            txtMaLVHD.setText(model.getValueAt(row, 5).toString());
            txtMaKH.setEditable(false);
        }
    }

    private KhachHang getKhachHangFromFields() {
        return new KhachHang(
                txtMaKH.getText(), txtTenKH.getText(), txtDiaChi.getText(),
                txtDienThoai.getText(), txtEmail.getText(), txtMaLVHD.getText());
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
                txtMaKH.setEditable(true);
                break;
            case "add":
                btnThem.setEnabled(false);
                btnSua.setEnabled(false);
                btnXoa.setEnabled(false);
                btnLuu.setEnabled(true);
                btnHuy.setEnabled(true);
                btnThoat.setEnabled(true);
                txtMaKH.setEditable(true);
                break;
            case "selected":
                btnThem.setEnabled(true);
                btnSua.setEnabled(true);
                btnXoa.setEnabled(true);
                btnLuu.setEnabled(false);
                btnHuy.setEnabled(true);
                btnThoat.setEnabled(true);
                txtMaKH.setEditable(false);
                break;
            case "edit":
                btnThem.setEnabled(false);
                btnSua.setEnabled(false);
                btnXoa.setEnabled(false);
                btnLuu.setEnabled(true);
                btnHuy.setEnabled(true);
                btnThoat.setEnabled(true);
                txtMaKH.setEditable(false);
                break;
        }
    }
}
