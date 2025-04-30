package gui;

import dao.BaoDAO;
import model.Bao;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;

public class BaoForm extends JInternalFrame {
    private JTextField txtMaBao, txtTenBao, txtDiaChi, txtMaChucNang, txtDienThoai;
    private JTable table;
    private DefaultTableModel model;
    private JButton btnThem, btnSua, btnXoa, btnLuu, btnHuy, btnThoat;
    private boolean isAdding = false;
    private boolean isEditing = false;

    private BaoDAO baoDAO = new BaoDAO();

    public BaoForm() {
        setTitle("Quản lý Báo");
        setClosable(true);
        setBounds(100, 100, 800, 500);
        getContentPane().setLayout(new BorderLayout());

        JPanel panelTop = new JPanel(new GridLayout(3, 4, 5, 5));
        txtMaBao = new JTextField();
        txtTenBao = new JTextField();
        txtDiaChi = new JTextField();
        txtMaChucNang = new JTextField();
        txtDienThoai = new JTextField();

        panelTop.add(new JLabel("Mã Báo"));
        panelTop.add(txtMaBao);
        panelTop.add(new JLabel("Tên Báo"));
        panelTop.add(txtTenBao);
        panelTop.add(new JLabel("Địa chỉ"));
        panelTop.add(txtDiaChi);
        panelTop.add(new JLabel("Mã Chức Năng"));
        panelTop.add(txtMaChucNang);
        panelTop.add(new JLabel("Điện thoại"));
        panelTop.add(txtDienThoai);

        getContentPane().add(panelTop, BorderLayout.NORTH);

        model = new DefaultTableModel();
        model.setColumnIdentifiers(new String[] { "Mã Báo", "Tên Báo", "Địa chỉ", "Mã Chức Năng", "Điện thoại" });
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
        lockButtons("init");

        btnThem.addActionListener(e -> {
            clearFields();
            isAdding = true;
            isEditing = false;
            lockButtons("add");
        });

        btnHuy.addActionListener(e -> {
            clearFields();
            isAdding = false;
            isEditing = false;
            lockButtons("init");
        });

        btnLuu.addActionListener(e -> {
            if (isAdding) {
                Bao bao = getBaoFromFields();
                if (baoDAO.insertBao(bao)) {
                    JOptionPane.showMessageDialog(null, "Thêm mới thành công!");
                    loadData();
                    isAdding = false;
                    lockButtons("init");
                } else {
                    JOptionPane.showMessageDialog(null, "Thêm mới thất bại!");
                }
            }
        });

        btnSua.addActionListener(e -> {
            Bao bao = getBaoFromFields();
            if (baoDAO.updateBao(bao)) {
                JOptionPane.showMessageDialog(null, "Cập nhật thành công!");
                loadData();
            } else {
                JOptionPane.showMessageDialog(null, "Cập nhật thất bại!");
            }
        });

        btnXoa.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow >= 0) {
                String maBao = (String) model.getValueAt(selectedRow, 0);
                if (baoDAO.deleteBao(maBao)) {
                    JOptionPane.showMessageDialog(null, "Xóa thành công!");
                    loadData();
                } else {
                    JOptionPane.showMessageDialog(null, "Xóa thất bại!");
                }
            }
        });

        btnThoat.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(null, "Bạn có chắc muốn thoát?", "Xác nhận thoát",
                    JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                dispose();
            }
        });

        table.getSelectionModel().addListSelectionListener((ListSelectionEvent e) -> {
            if (!e.getValueIsAdjusting()) {
                if (isAdding) {
                    JOptionPane.showMessageDialog(null, "Đang ở chế độ thêm mới, không chọn dữ liệu!");
                } else {
                    showData();
                    isEditing = true;
                    lockButtons("select");
                }
            }
        });
    }

    private void loadData() {
        model.setRowCount(0);
        ArrayList<Bao> list = baoDAO.getAllBao();
        for (Bao b : list) {
            model.addRow(new Object[] {
                    b.getMaBao(), b.getTenBao(), b.getDiaChi(), b.getMaChucNang(), b.getDienThoai()
            });
        }
    }

    private void clearFields() {
        txtMaBao.setText("");
        txtTenBao.setText("");
        txtDiaChi.setText("");
        txtMaChucNang.setText("");
        txtDienThoai.setText("");
    }

    private void lockButtons(String state) {
        switch (state) {
            case "init":
                btnThem.setEnabled(true);
                btnSua.setEnabled(false);
                btnXoa.setEnabled(false);
                btnLuu.setEnabled(false);
                btnHuy.setEnabled(true);
                btnThoat.setEnabled(true);
                txtMaBao.setEditable(true);
                break;
            case "add":
                btnThem.setEnabled(false);
                btnSua.setEnabled(false);
                btnXoa.setEnabled(false);
                btnLuu.setEnabled(true);
                btnHuy.setEnabled(true);
                btnThoat.setEnabled(true);
                txtMaBao.setEditable(true);
                break;
            case "select":
                btnThem.setEnabled(true);
                btnSua.setEnabled(true);
                btnXoa.setEnabled(true);
                btnLuu.setEnabled(false);
                btnHuy.setEnabled(true);
                btnThoat.setEnabled(true);
                txtMaBao.setEditable(false);
                break;
        }
    }

    private void showData() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow >= 0) {
            txtMaBao.setText(model.getValueAt(selectedRow, 0).toString());
            txtTenBao.setText(model.getValueAt(selectedRow, 1).toString());
            txtDiaChi.setText(model.getValueAt(selectedRow, 2).toString());
            txtMaChucNang.setText(model.getValueAt(selectedRow, 3).toString());
            txtDienThoai.setText(model.getValueAt(selectedRow, 4).toString());
        }
    }

    private Bao getBaoFromFields() {
        return new Bao(
                txtMaBao.getText(), txtTenBao.getText(), txtDiaChi.getText(),
                txtMaChucNang.getText(), txtDienThoai.getText());
    }
}
