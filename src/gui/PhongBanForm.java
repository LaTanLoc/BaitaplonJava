package gui;

import dao.PhongBanDAO;
import model.PhongBan;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class PhongBanForm extends JInternalFrame {
    private JTextField txtMaPhong, txtTenPhong, txtDienThoai;
    private JTable table;
    private DefaultTableModel model;
    private JButton btnThem, btnSua, btnXoa, btnLuu, btnHuy, btnThoat;
    private boolean isAdding = false;
    private boolean ignoreSelection = false;

    private PhongBanDAO phongBanDAO = new PhongBanDAO();

    public PhongBanForm() {
        setTitle("Quản lý Phòng ban");
        setClosable(true);
        setBounds(100, 100, 700, 450);
        getContentPane().setLayout(new BorderLayout());

        // Top Form
        JPanel panelTop = new JPanel(new GridLayout(3, 2, 10, 10));
        JLabel lblMaPhong = new JLabel("Mã phòng:");
        txtMaPhong = new JTextField();
        JLabel lblTenPhong = new JLabel("Tên phòng:");
        txtTenPhong = new JTextField();
        JLabel lblDienThoai = new JLabel("Điện thoại:");
        txtDienThoai = new JTextField();

        panelTop.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panelTop.add(lblMaPhong);
        panelTop.add(txtMaPhong);
        panelTop.add(lblTenPhong);
        panelTop.add(txtTenPhong);
        panelTop.add(lblDienThoai);
        panelTop.add(txtDienThoai);

        getContentPane().add(panelTop, BorderLayout.NORTH);

        // Table
        model = new DefaultTableModel();
        model.setColumnIdentifiers(new String[] { "Mã Phòng", "Tên Phòng", "Điện Thoại" });
        table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);
        getContentPane().add(scrollPane, BorderLayout.CENTER);

        // Buttons
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

        // Sự kiện nút
        btnThem.addActionListener(e -> {
            clearFields();
            isAdding = true;
            setButtonState("add");
        });

        btnLuu.addActionListener(e -> {
            PhongBan pb = getPhongBanFromFields();
            if (pb.getMaPhong().isEmpty() || pb.getTenPhong().isEmpty() || pb.getDienThoai().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Vui lòng nhập đầy đủ thông tin!");
                return;
            }

            if (isAdding) {
                if (phongBanDAO.insertPhongBan(pb)) {
                    JOptionPane.showMessageDialog(null, "Thêm thành công!");
                    loadData();
                } else {
                    JOptionPane.showMessageDialog(null, "Thêm thất bại!");
                }
                isAdding = false;
            } else {
                if (phongBanDAO.updatePhongBan(pb)) {
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
            isAdding = false;
            setButtonState("edit");
        });

        btnXoa.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row >= 0) {
                String maPhong = model.getValueAt(row, 0).toString();
                int confirm = JOptionPane.showConfirmDialog(null, "Bạn có chắc muốn xóa?", "Xác nhận",
                        JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    if (phongBanDAO.deletePhongBan(maPhong)) {
                        JOptionPane.showMessageDialog(null, "Xóa thành công!");
                        loadData();
                        clearFields();
                        setButtonState("init");
                    } else {
                        JOptionPane.showMessageDialog(null, "Xóa thất bại!");
                    }
                }
            }
        });

        btnHuy.addActionListener(e -> {
            clearFields();
            isAdding = false;
            setButtonState("init");
        });

        btnThoat.addActionListener(e -> dispose());

        // Sự kiện chọn dòng trong bảng
        // Thêm biến cờ vào class

        // Trong constructor, sửa đoạn addListSelectionListener:
        table.getSelectionModel().addListSelectionListener((ListSelectionEvent e) -> {
            if (!e.getValueIsAdjusting() && !ignoreSelection) {
                if (isAdding) {
                    ignoreSelection = true; // Bỏ qua sự kiện kế tiếp
                    table.clearSelection();
                    JOptionPane.showMessageDialog(null, "Bạn đang ở chế độ thêm mới. Không chọn dữ liệu!");
                    ignoreSelection = false; // Reset lại cờ
                    return;
                }
                showSelectedRow();
                setButtonState("selected");
            }
        });

    }

    private void loadData() {
        model.setRowCount(0);
        for (PhongBan pb : phongBanDAO.getAllPhongBan()) {
            model.addRow(new Object[] {
                    pb.getMaPhong(), pb.getTenPhong(), pb.getDienThoai()
            });
        }
    }

    private void clearFields() {
        txtMaPhong.setText("");
        txtTenPhong.setText("");
        txtDienThoai.setText("");
        txtMaPhong.setEditable(true);
    }

    private void showSelectedRow() {
        int row = table.getSelectedRow();
        if (row >= 0) {
            txtMaPhong.setText(model.getValueAt(row, 0).toString());
            txtTenPhong.setText(model.getValueAt(row, 1).toString());
            txtDienThoai.setText(model.getValueAt(row, 2).toString());
            txtMaPhong.setEditable(false);
        }
    }

    private PhongBan getPhongBanFromFields() {
        return new PhongBan(
                txtMaPhong.getText().trim(),
                txtTenPhong.getText().trim(),
                txtDienThoai.getText().trim());
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
                txtMaPhong.setEditable(true);
                break;
            case "add":
                btnThem.setEnabled(false);
                btnSua.setEnabled(false);
                btnXoa.setEnabled(false);
                btnLuu.setEnabled(true);
                btnHuy.setEnabled(true);
                btnThoat.setEnabled(true);
                txtMaPhong.setEditable(true);
                break;
            case "selected":
                btnThem.setEnabled(true);
                btnSua.setEnabled(true);
                btnXoa.setEnabled(true);
                btnLuu.setEnabled(false);
                btnHuy.setEnabled(true);
                btnThoat.setEnabled(true);
                txtMaPhong.setEditable(false);
                break;
            case "edit":
                btnThem.setEnabled(false);
                btnSua.setEnabled(false);
                btnXoa.setEnabled(false);
                btnLuu.setEnabled(true);
                btnHuy.setEnabled(true);
                btnThoat.setEnabled(true);
                txtMaPhong.setEditable(false);
                break;
        }
    }
}
