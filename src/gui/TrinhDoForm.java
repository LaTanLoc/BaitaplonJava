package gui;

import dao.TrinhDoDAO;
import model.TrinhDo;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class TrinhDoForm extends JInternalFrame {
    private JTextField txtMaTD, txtTenTD;
    private JTable table;
    private DefaultTableModel model;
    private JButton btnThem, btnSua, btnXoa, btnLuu, btnHuy, btnThoat;
    private boolean isAdding = false;

    private TrinhDoDAO dao = new TrinhDoDAO();

    public TrinhDoForm() {
        setTitle("Quản lý Trình độ");
        setClosable(true);
        setBounds(100, 100, 600, 400);
        getContentPane().setLayout(new BorderLayout());

        // Panel nhập liệu
        JPanel panelTop = new JPanel(new GridLayout(2, 2, 10, 10));
        panelTop.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panelTop.add(new JLabel("Mã TD:"));
        txtMaTD = new JTextField();
        panelTop.add(txtMaTD);
        panelTop.add(new JLabel("Tên TD:"));
        txtTenTD = new JTextField();
        panelTop.add(txtTenTD);
        getContentPane().add(panelTop, BorderLayout.NORTH);

        // Bảng
        model = new DefaultTableModel();
        model.setColumnIdentifiers(new String[] { "Mã trình độ", "Tên trình độ" });
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
            txtMaTD.setEditable(true);
            setButtonState("add");
        });

        btnLuu.addActionListener(e -> {
            TrinhDo td = new TrinhDo(txtMaTD.getText(), txtTenTD.getText());
            if (isAdding) {
                if (dao.insertTrinhDo(td)) {
                    JOptionPane.showMessageDialog(null, "Thêm thành công!");
                    loadData();
                } else {
                    JOptionPane.showMessageDialog(null, "Thêm thất bại!");
                }
                isAdding = false;
            } else {
                if (dao.updateTrinhDo(td)) {
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
            txtMaTD.setEditable(false);
            setButtonState("edit");
        });

        btnXoa.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row >= 0) {
                String ma = model.getValueAt(row, 0).toString();
                if (dao.deleteTrinhDo(ma)) {
                    JOptionPane.showMessageDialog(null, "Xóa thành công!");
                    loadData();
                } else {
                    JOptionPane.showMessageDialog(null, "Xóa thất bại!");
                }
            }
        });

        btnHuy.addActionListener(e -> {
            clearFields();
            isAdding = false;
            setButtonState("init");
        });

        btnThoat.addActionListener(e -> dispose());

        table.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (isAdding) {
                    JOptionPane.showMessageDialog(TrinhDoForm.this, "Bạn đang ở chế độ thêm mới. Không chọn dữ liệu!");
                    return;
                }
                int row = table.getSelectedRow();
                if (row >= 0) {
                    txtMaTD.setText(model.getValueAt(row, 0).toString());
                    txtTenTD.setText(model.getValueAt(row, 1).toString());
                    txtMaTD.setEditable(false);
                    setButtonState("selected");
                }
            }
        });
    }

    private void loadData() {
        model.setRowCount(0);
        ArrayList<TrinhDo> list = dao.getAllTrinhDo();
        for (TrinhDo td : list) {
            model.addRow(new Object[] { td.getMaTD(), td.getTenTD() });
        }
    }

    private void clearFields() {
        txtMaTD.setText("");
        txtTenTD.setText("");
        txtMaTD.setEditable(true);
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
