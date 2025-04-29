package gui;

import dao.NhanVienDAO;
import model.NhanVien;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class NhanVienForm extends JInternalFrame {
    private JTextField txtMaNV, txtTenNV, txtMaPhong, txtMaChucVu, txtMaTD, txtMaCM, txtDiaChi, txtNgaySinh,
            txtGioiTinh, txtDienThoai, txtMobile, txtEmail;
    private JTable table;
    private DefaultTableModel model;
    private JButton btnThem, btnSua, btnXoa, btnLuu, btnHuy, btnThoat;
    private boolean isAdding = false;

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
        txtMobile = new JTextField();
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
        panelTop.add(new JLabel("Mobile"));
        panelTop.add(txtMobile);
        panelTop.add(new JLabel("Email"));
        panelTop.add(txtEmail);

        getContentPane().add(panelTop, BorderLayout.NORTH);

        model = new DefaultTableModel();
        model.setColumnIdentifiers(new String[] { "Mã NV", "Tên NV", "Mã phòng", "Mã chức vụ", "Mã trình độ",
                "Mã chuyên môn", "Địa chỉ", "Ngày sinh", "Giới tính", "Điện thoại", "Mobile", "Email" });
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
        lockButtons(true);

        btnThem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                clearFields();
                isAdding = true;
                lockButtons(false);
            }
        });

        btnHuy.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                clearFields();
                isAdding = false;
                lockButtons(true);
            }
        });

        btnLuu.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (isAdding) {
                    NhanVien nv = getNhanVienFromFields();
                    if (nhanVienDAO.insertNhanVien(nv)) {
                        JOptionPane.showMessageDialog(null, "Thêm mới thành công!");
                        loadData();
                        lockButtons(true);
                        isAdding = false;
                    } else {
                        JOptionPane.showMessageDialog(null, "Thêm mới thất bại!");
                    }
                }
            }
        });

        btnSua.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                NhanVien nv = getNhanVienFromFields();
                if (nhanVienDAO.updateNhanVien(nv)) {
                    JOptionPane.showMessageDialog(null, "Cập nhật thành công!");
                    loadData();
                } else {
                    JOptionPane.showMessageDialog(null, "Cập nhật thất bại!");
                }
            }
        });

        btnXoa.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int selectedRow = table.getSelectedRow();
                if (selectedRow >= 0) {
                    String maNV = (String) model.getValueAt(selectedRow, 0);
                    if (nhanVienDAO.deleteNhanVien(maNV)) {
                        JOptionPane.showMessageDialog(null, "Xóa thành công!");
                        loadData();
                    } else {
                        JOptionPane.showMessageDialog(null, "Xóa thất bại!");
                    }
                }
            }
        });

        btnThoat.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    if (isAdding) {
                        JOptionPane.showMessageDialog(null, "Đang ở chế độ thêm mới, không chọn dữ liệu!");
                    } else {
                        showData();
                        lockButtons(true);
                    }
                }
            }
        });
    }

    private void loadData() {
        model.setRowCount(0);
        ArrayList<NhanVien> list = nhanVienDAO.getAllNhanVien();
        for (NhanVien nv : list) {
            model.addRow(new Object[] {
                    nv.getMaNV(), nv.getTenNV(), nv.getMaPhong(), nv.getMaChucVu(), nv.getMaTD(), nv.getMaCM(),
                    nv.getDiaChi(), nv.getNgaySinh(), nv.getGioiTinh(), nv.getDienThoai(), nv.getMobile(), nv.getEmail()
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
        txtMobile.setText("");
        txtEmail.setText("");
    }

    private void lockButtons(boolean normalMode) {
        btnThem.setEnabled(normalMode);
        btnSua.setEnabled(normalMode);
        btnXoa.setEnabled(normalMode);
        btnThoat.setEnabled(true);
        btnLuu.setEnabled(!normalMode);
        btnHuy.setEnabled(true);
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
            txtMobile.setText(model.getValueAt(selectedRow, 10).toString());
            txtEmail.setText(model.getValueAt(selectedRow, 11).toString());
        }
    }

    private NhanVien getNhanVienFromFields() {
        return new NhanVien(
                txtMaNV.getText(), txtTenNV.getText(), txtMaPhong.getText(), txtMaChucVu.getText(),
                txtMaTD.getText(), txtMaCM.getText(), txtDiaChi.getText(), txtNgaySinh.getText(),
                txtGioiTinh.getText(), txtDienThoai.getText(), txtMobile.getText(), txtEmail.getText());
    }
}
