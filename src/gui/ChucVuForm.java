package gui;

import dao.ChucVuDAO;
import model.ChucVu;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class ChucVuForm extends JInternalFrame {
    private JTextField txtMaChucVu, txtTenChucVu;
    private JButton btnThem, btnSua, btnXoa, btnLuu, btnHuy, btnThoat;
    private JTable table;
    private DefaultTableModel model;

    private boolean isAdding = false;

    private ChucVuDAO dao = new ChucVuDAO();

    public ChucVuForm() {
        setTitle("Quản lý Chức Vụ");
        setClosable(true);
        setSize(600, 400);
        setLayout(new BorderLayout());

        // Panel nhập liệu
        JPanel pnlInput = new JPanel(new GridLayout(2, 2, 10, 10));
        pnlInput.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        pnlInput.add(new JLabel("Mã chức vụ:"));
        txtMaChucVu = new JTextField();
        pnlInput.add(txtMaChucVu);

        pnlInput.add(new JLabel("Tên chức vụ:"));
        txtTenChucVu = new JTextField();
        pnlInput.add(txtTenChucVu);

        add(pnlInput, BorderLayout.NORTH);

        // Bảng
        model = new DefaultTableModel(new String[] { "Mã chức vụ", "Tên chức vụ" }, 0);
        table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        // Panel nút
        JPanel pnlButtons = new JPanel();

        btnThem = new JButton("Thêm");
        btnSua = new JButton("Sửa");
        btnXoa = new JButton("Xóa");
        btnLuu = new JButton("Lưu");
        btnHuy = new JButton("Hủy");
        btnThoat = new JButton("Thoát");

        pnlButtons.add(btnThem);
        pnlButtons.add(btnSua);
        pnlButtons.add(btnXoa);
        pnlButtons.add(btnLuu);
        pnlButtons.add(btnHuy);
        pnlButtons.add(btnThoat);

        add(pnlButtons, BorderLayout.SOUTH);

        // Load dữ liệu
        loadData();
        setButtonState("init");

        // Sự kiện
        btnThem.addActionListener(e -> {
            clearFields();
            txtMaChucVu.setEditable(true);
            isAdding = true;
            setButtonState("editing");
        });

        btnSua.addActionListener(e -> {
            if (txtMaChucVu.getText().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn chức vụ để sửa.");
                return;
            }
            txtMaChucVu.setEditable(false);
            isAdding = false;
            setButtonState("editing");
        });

        btnLuu.addActionListener(e -> {
            String ma = txtMaChucVu.getText().trim();
            String ten = txtTenChucVu.getText().trim();
            if (ma.isEmpty() || ten.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin!");
                return;
            }

            ChucVu cv = new ChucVu(ma, ten);
            boolean success;

            if (isAdding) {
                success = dao.insertChucVu(cv);
                if (success)
                    JOptionPane.showMessageDialog(this, "Thêm thành công!");
                else
                    JOptionPane.showMessageDialog(this, "Thêm thất bại!");
            } else {
                success = dao.updateChucVu(cv);
                if (success)
                    JOptionPane.showMessageDialog(this, "Cập nhật thành công!");
                else
                    JOptionPane.showMessageDialog(this, "Cập nhật thất bại!");
            }

            loadData();
            clearFields();
            setButtonState("init");
            isAdding = false;
        });

        btnXoa.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row >= 0) {
                String ma = table.getValueAt(row, 0).toString();
                int confirm = JOptionPane.showConfirmDialog(this, "Bạn có chắc muốn xóa?", "Xác nhận",
                        JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    boolean success = dao.deleteChucVu(ma);
                    if (success) {
                        JOptionPane.showMessageDialog(this, "Xóa thành công!");
                        loadData();
                        clearFields();
                        setButtonState("init");
                    } else {
                        JOptionPane.showMessageDialog(this, "Xóa thất bại!");
                    }
                }
            } else {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn chức vụ cần xóa!");
            }
        });

        btnHuy.addActionListener(e -> {
            clearFields();
            setButtonState("init");
            isAdding = false;
        });

        // Thoát
        btnThoat.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(this, "Bạn có chắc chắn muốn thoát?", "Xác nhận",
                    JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                dispose(); // Đóng form
            }
        });

        // Click bảng
        table.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (isAdding) {
                    JOptionPane.showMessageDialog(ChucVuForm.this, "Bạn đang ở chế độ thêm mới. Không chọn dữ liệu!");
                    return;
                }
                int row = table.getSelectedRow();
                if (row >= 0) {
                    txtMaChucVu.setText(model.getValueAt(row, 0).toString());
                    txtTenChucVu.setText(model.getValueAt(row, 1).toString());
                    txtMaChucVu.setEditable(false);
                    setButtonState("selected");
                }
            }
        });
    }

    private void loadData() {
        model.setRowCount(0);
        for (ChucVu cv : dao.getAllChucVu()) {
            model.addRow(new Object[] { cv.getMaChucVu(), cv.getTenChucVu() });
        }
    }

    private void clearFields() {
        txtMaChucVu.setText("");
        txtTenChucVu.setText("");
        txtMaChucVu.setEditable(true);
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
            case "selected":
                btnThem.setEnabled(true);
                btnSua.setEnabled(true);
                btnXoa.setEnabled(true);
                btnLuu.setEnabled(false);
                btnHuy.setEnabled(true);
                btnThoat.setEnabled(true);
                break;
            case "editing":
                btnThem.setEnabled(false);
                btnSua.setEnabled(false);
                btnXoa.setEnabled(false);
                btnLuu.setEnabled(true);
                btnHuy.setEnabled(true);
                btnThoat.setEnabled(true);
                break;
        }
    }
}
