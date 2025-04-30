package gui;

import dao.NhanVienDAO;
import model.NhanVien;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;

public class NhanVienForm extends JInternalFrame {
    private JTextField txtMaNV, txtTenNV, txtMaPhong, txtMaChucVu, txtMaTD, txtMaCM, txtDiaChi, txtNgaySinh,
            txtGioiTinh, txtDienThoai, txtEmail;
    private JTable table;
    private DefaultTableModel model;
    private JButton btnThem, btnSua, btnXoa, btnLuu, btnHuy, btnThoat;
    private boolean isAdding = false;
    private boolean isBlockingSelection = false;

    private NhanVienDAO nhanVienDAO = new NhanVienDAO();

    public NhanVienForm() {
        setTitle("Quản lý Nhân viên");
        setClosable(true);
        setBounds(100, 100, 1000, 600);
        getContentPane().setLayout(new BorderLayout(0, 0));

        JPanel panelTop = new JPanel(new GridLayout(6, 4, 5, 5));
        txtMaNV = new JTextField();
        txtTenNV = new JTextField();
        txtMaPhong = new JTextField();
        txtMaChucVu = new JTextField();
        txtMaTD = new JTextField();
        txtMaCM = new JTextField();
        txtDiaChi = new JTextField();
        txtNgaySinh = new JTextField();
        txtGioiTinh = new JTextField();
        txtDienThoai = new JTextField();
        txtEmail = new JTextField();

        panelTop.add(new JLabel("Mã NV"));
        panelTop.add(txtMaNV);
        panelTop.add(new JLabel("Tên NV"));
        panelTop.add(txtTenNV);
        panelTop.add(new JLabel("Mã phòng"));
        panelTop.add(txtMaPhong);
        panelTop.add(new JLabel("Mã chức vụ"));
        panelTop.add(txtMaChucVu);
        panelTop.add(new JLabel("Mã trình độ"));
        panelTop.add(txtMaTD);
        panelTop.add(new JLabel("Mã chuyên môn"));
        panelTop.add(txtMaCM);
        panelTop.add(new JLabel("Địa chỉ"));
        panelTop.add(txtDiaChi);
        panelTop.add(new JLabel("Ngày sinh"));
        panelTop.add(txtNgaySinh);
        panelTop.add(new JLabel("Giới tính"));
        panelTop.add(txtGioiTinh);
        panelTop.add(new JLabel("Điện thoại"));
        panelTop.add(txtDienThoai);
        panelTop.add(new JLabel("Email"));
        panelTop.add(txtEmail);

        getContentPane().add(panelTop, BorderLayout.NORTH);

        model = new DefaultTableModel();
        model.setColumnIdentifiers(new String[] { "Mã NV", "Tên NV", "Mã phòng", "Mã chức vụ", "Mã trình độ",
                "Mã chuyên môn", "Địa chỉ", "Ngày sinh", "Giới tính", "Điện thoại", "Email" });
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
        setInitialButtonState();
        txtMaNV.setEditable(false);

        btnThem.addActionListener(e -> {
            clearFields();
            isAdding = true;
            btnThem.setEnabled(false);
            btnSua.setEnabled(false);
            btnXoa.setEnabled(false);
            btnLuu.setEnabled(true);
            btnHuy.setEnabled(true);
            btnThoat.setEnabled(true);
            txtMaNV.setEditable(true);
        });

        btnSua.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow < 0) {
                JOptionPane.showMessageDialog(null, "Vui lòng chọn một dòng để sửa!");
                return;
            }
            NhanVien nv = getNhanVienFromFields();
            if (nhanVienDAO.updateNhanVien(nv)) {
                JOptionPane.showMessageDialog(null, "Cập nhật thành công!");
                loadData();
                showData();
                isAdding = false;
            } else {
                JOptionPane.showMessageDialog(null, "Cập nhật thất bại!");
            }
        });

        btnXoa.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow >= 0) {
                String maNV = (String) model.getValueAt(selectedRow, 0);
                int confirm = JOptionPane.showConfirmDialog(null, "Bạn có chắc muốn xóa nhân viên này?", "Xác nhận",
                        JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    if (nhanVienDAO.deleteNhanVien(maNV)) {
                        JOptionPane.showMessageDialog(null, "Xóa thành công!");
                        loadData();
                        clearFields();
                        setInitialButtonState();
                    } else {
                        JOptionPane.showMessageDialog(null, "Xóa thất bại!");
                    }
                }
            } else {
                JOptionPane.showMessageDialog(null, "Vui lòng chọn một dòng để xóa!");
            }
        });

        btnLuu.addActionListener(e -> {
            if (isAdding) {
                NhanVien nv = getNhanVienFromFields();
                if (nhanVienDAO.insertNhanVien(nv)) {
                    JOptionPane.showMessageDialog(null, "Thêm mới thành công!");
                    loadData();
                    isAdding = false;
                    setInitialButtonState();
                    txtMaNV.setEditable(false);
                    showData();
                } else {
                    JOptionPane.showMessageDialog(null, "Thêm mới thất bại! Có thể Mã NV đã tồn tại.");
                }
            }
        });

        btnHuy.addActionListener(e -> {
            clearFields();
            isAdding = false;
            setInitialButtonState();
            txtMaNV.setEditable(false);
        });

        btnThoat.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(null, "Bạn có chắc muốn thoát?", "Xác nhận thoát",
                    JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                dispose();
            }
        });

        table.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && !isBlockingSelection) {
                if (isAdding) {
                    isBlockingSelection = true; // chặn lần gọi thứ hai
                    JOptionPane.showMessageDialog(null, "Bạn đang ở chế độ Thêm mới!");
                    table.clearSelection();
                    isBlockingSelection = false; // mở lại
                } else {
                    showData();
                    btnThem.setEnabled(true);
                    btnSua.setEnabled(true);
                    btnXoa.setEnabled(true);
                    btnLuu.setEnabled(false);
                    btnHuy.setEnabled(true);
                    btnThoat.setEnabled(true);
                    txtMaNV.setEditable(false);
                }
            }
        });

    }

    private void setInitialButtonState() {
        btnThem.setEnabled(true);
        btnSua.setEnabled(false);
        btnXoa.setEnabled(false);
        btnLuu.setEnabled(false);
        btnHuy.setEnabled(true);
        btnThoat.setEnabled(true);
    }

    private void loadData() {
        model.setRowCount(0);
        ArrayList<NhanVien> list = nhanVienDAO.getAllNhanVien();
        for (NhanVien nv : list) {
            model.addRow(new Object[] {
                    nv.getMaNV(), nv.getTenNV(), nv.getMaPhong(), nv.getMaChucVu(), nv.getMaTD(), nv.getMaCM(),
                    nv.getDiaChi(), nv.getNgaySinh(), nv.getGioiTinh(), nv.getDienThoai(), nv.getEmail()
            });
        }
    }

    private void clearFields() {
        txtMaNV.setText("");
        txtTenNV.setText("");
        txtMaPhong.setText("");
        txtMaChucVu.setText("");
        txtMaTD.setText("");
        txtMaCM.setText("");
        txtDiaChi.setText("");
        txtNgaySinh.setText("");
        txtGioiTinh.setText("");
        txtDienThoai.setText("");
        txtEmail.setText("");
    }

    private void showData() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow >= 0) {
            txtMaNV.setText(model.getValueAt(selectedRow, 0).toString());
            txtTenNV.setText(model.getValueAt(selectedRow, 1).toString());
            txtMaPhong.setText(model.getValueAt(selectedRow, 2).toString());
            txtMaChucVu.setText(model.getValueAt(selectedRow, 3).toString());
            txtMaTD.setText(model.getValueAt(selectedRow, 4).toString());
            txtMaCM.setText(model.getValueAt(selectedRow, 5).toString());
            txtDiaChi.setText(model.getValueAt(selectedRow, 6).toString());
            txtNgaySinh.setText(model.getValueAt(selectedRow, 7).toString());
            txtGioiTinh.setText(model.getValueAt(selectedRow, 8).toString());
            txtDienThoai.setText(model.getValueAt(selectedRow, 9).toString());
            txtEmail.setText(model.getValueAt(selectedRow, 10).toString());
        }
    }

    private NhanVien getNhanVienFromFields() {
        return new NhanVien(
                txtMaNV.getText(), txtTenNV.getText(), txtMaPhong.getText(), txtMaChucVu.getText(),
                txtMaTD.getText(), txtMaCM.getText(), txtDiaChi.getText(), txtNgaySinh.getText(),
                txtGioiTinh.getText(), txtDienThoai.getText(), txtEmail.getText());
    }
}
