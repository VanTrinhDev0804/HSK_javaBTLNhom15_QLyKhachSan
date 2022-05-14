package app;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FileDialog;
import java.awt.Font;
import java.awt.Image;
import java.awt.Panel;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import javax.swing.ButtonGroup;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

import com.mindfusion.drawing.Colors;
import com.toedter.calendar.JDateChooser;

import connection.ConnectDB;
import dao.DAONhanVien;
import dao.DAOPhatSinhMa;
import dao.DAOTaiKhoan;
import dao.Regex;
import entity.NhanVien;
import entity.TaiKhoan;
import jiconfont.icons.FontAwesome;
import jiconfont.swing.IconFontSwing;

public class FrmNhanVien extends JFrame implements ActionListener, MouseListener, FocusListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JButton btnTim, btnThemNV, btnSuaNV, btnLamMoiNV, btnHuy, btnXoa;
	private Panel pMain;
	@SuppressWarnings("unused")
	private String sHeaderTenNV, sHeaderMaNV;
	@SuppressWarnings("unused")
	private Date dNgayHienTai;
	private LocalDate now;
	private Date dNow;
	private int nam, thang, ngay;
	private JLabel lblNVDaNghiViec, lblSubGioTheoCa;
	private JTextField txtTim, txtHoTen, txtSDT, txtCccd, txtDiaChi;
	private JComboBox<Object> cboChucVu, cboGioiTinh, cboCaLamViec;
	private ButtonGroup bg;
	private JTable tblNV;
	private DefaultTableModel modelNV;
	private SimpleDateFormat dfNgaySinh;
	private DecimalFormat dfLuong;
	private JDateChooser chooserNgaySinh;

	private DAONhanVien daoNhanVien;
	private DAOPhatSinhMa daoPhatSinhMa;
	private DAOTaiKhoan daoTaiKhoan;
	private Regex regex;

	private NhanVien nv;
	private JPanel pNhapThongTin;
	private JLabel lblNhapThongTin;
	private JTextField txtMaNV;

	private boolean statusEdit = false;

	/**
	 * @return pMain
	 */
	public Panel getPanel() {
		return pMain;
	}

	/**
	 * Káº¿ thá»«a tÃªn vÃ  mÃ£ nhÃ¢n viÃªn, ngÃ y hiá»‡n táº¡i cá»§a FrmQuanLy
	 * 
	 * @param sHeaderTenNV
	 * @param sHeaderMaNV
	 * @param dNgayHienTai
	 */
	@SuppressWarnings("deprecation")
	public FrmNhanVien(String sHeaderTenNV, String sHeaderMaNV, Date dNgayHienTai) {

		this.sHeaderMaNV = sHeaderMaNV;
		this.sHeaderTenNV = sHeaderTenNV;
		this.dNgayHienTai = dNgayHienTai;

		/**
		 * kết nối database
		 */
		try {
			ConnectDB.getinstance().connect();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}

		/**
		 * Khai báo DAO
		 */
		daoNhanVien = new DAONhanVien();
		daoPhatSinhMa = new DAOPhatSinhMa();
		daoTaiKhoan = new DAOTaiKhoan();
		regex = new Regex();

		/**
		 * Khai báo ngày hiện tai
		 */
		dfNgaySinh = new SimpleDateFormat("dd/MM/yyyy");
		dfLuong = new DecimalFormat("##,###,###");

		/**
		 * Khai bÃ¡o entity NhanVien
		 */
		NhanVien nv = new NhanVien();

		/**
		 * Frame NhanVien
		 */
		getContentPane().setLayout(null);
		pMain = new Panel();
		pMain.setBackground(Color.WHITE);
		pMain.setBounds(0, 0, 1278, 629);
		pMain.setLayout(null);
		getContentPane().add(pMain);

		/**
		 * Nhập thông tin nhân viên Panel pNhapThongTin
		 */
		pNhapThongTin = new JPanel();
		pNhapThongTin.setBorder(new LineBorder(new Color(114, 23, 153)));
		pNhapThongTin.setBackground(Color.WHITE);
		pNhapThongTin.setBounds(2, 56, 1258, 238);
		pNhapThongTin.setLayout(null);
		pNhapThongTin.setToolTipText("Các thông tin nhân viên cần nhập");
		pMain.add(pNhapThongTin);

		lblNhapThongTin = new JLabel("Nhập thông tin nhân viên");
		lblNhapThongTin.setHorizontalAlignment(SwingConstants.CENTER);
		lblNhapThongTin.setFont(new Font("SansSerif", Font.BOLD, 18));
		lblNhapThongTin.setBounds(10, 10, 239, 29);
		pNhapThongTin.add(lblNhapThongTin);

		JLabel lblHoTen = new JLabel("Họ và tên:");
		lblHoTen.setBounds(10, 61, 90, 29);
		lblHoTen.setFont(new Font("SansSerif", Font.BOLD, 15));
		pNhapThongTin.add(lblHoTen);
		txtHoTen = new JTextField();
		txtHoTen.setBounds(110, 62, 454, 28);
		txtHoTen.setFont(new Font("arial", Font.PLAIN, 15));
		txtHoTen.setColumns(10);
		txtHoTen.setBorder(new LineBorder(new Color(114, 23, 153), 1, true));
		pNhapThongTin.add(txtHoTen);

		// test data nhanh
		txtHoTen.setText("Nguyễn Văn Trinh");

		JLabel lblSDT = new JLabel("SĐT:");
		lblSDT.setBounds(881, 113, 46, 19);
		lblSDT.setFont(new Font("SansSerif", Font.BOLD, 15));
		pNhapThongTin.add(lblSDT);
		txtSDT = new JTextField();
		txtSDT.setBounds(953, 108, 283, 28);
		txtSDT.setFont(new Font("SansSerif", Font.PLAIN, 15));
		txtSDT.setColumns(10);
		txtSDT.setBorder(new LineBorder(new Color(114, 23, 153), 1, true));
		pNhapThongTin.add(txtSDT);
		txtSDT.setText("0944302210");

		JLabel lblDiaChi = new JLabel("Địa chỉ:");
		lblDiaChi.setBounds(10, 112, 72, 20);
		lblDiaChi.setFont(new Font("SansSerif", Font.BOLD, 15));
		pNhapThongTin.add(lblDiaChi);
		txtDiaChi = new JTextField();
		txtDiaChi.setBounds(110, 108, 454, 28);
		txtDiaChi.setFont(new Font("SansSerif", Font.PLAIN, 15));
		txtDiaChi.setBorder(new LineBorder(new Color(114, 23, 153), 1, true));
		pNhapThongTin.add(txtDiaChi);
		txtDiaChi.setText("168 đường số 17 phường 11 gò vấp");

		JLabel lblCccd = new JLabel("CCCD:");
		lblCccd.setBounds(881, 63, 72, 24);
		lblCccd.setFont(new Font("SansSerif", Font.BOLD, 15));
		pNhapThongTin.add(lblCccd);
		txtCccd = new JTextField();
		txtCccd.setBounds(953, 61, 283, 28);
		txtCccd.setFont(new Font("SansSerif", Font.PLAIN, 15));
		txtCccd.setColumns(10);
		txtCccd.setBorder(new LineBorder(new Color(114, 23, 153), 1, true));
		txtCccd.setText("123456789012");
		pNhapThongTin.add(txtCccd);

		JLabel lblGioiTinh = new JLabel("Giới tính:");
		lblGioiTinh.setBounds(596, 154, 88, 23);
		lblGioiTinh.setFont(new Font("SansSerif", Font.BOLD, 15));
		pNhapThongTin.add(lblGioiTinh);
		cboGioiTinh = new JComboBox<Object>(new Object[] { "Nam", "Nữ" });
		cboGioiTinh.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		cboGioiTinh.setBounds(675, 153, 191, 25);
		cboGioiTinh.setFont(new Font("SansSerif", Font.PLAIN, 15));
		cboGioiTinh.setBackground(Color.white);
		cboGioiTinh.setBorder(new LineBorder(new Color(114, 23, 153), 1, true));
		cboGioiTinh.setToolTipText("Chọn giới tính");
		pNhapThongTin.add(cboGioiTinh);

		now = LocalDate.now();
		ngay = now.getDayOfMonth();
		thang = now.getMonthValue() - 1;
		nam = now.getYear() - 1900;
		dNow = new Date(nam, thang, ngay);

		JLabel lblNgaySinh = new JLabel("Ngày sinh:");
		lblNgaySinh.setBounds(596, 111, 90, 23);
		lblNgaySinh.setFont(new Font("SansSerif", Font.BOLD, 15));
		pNhapThongTin.add(lblNgaySinh);
		chooserNgaySinh = new JDateChooser();
		chooserNgaySinh.getCalendarButton().setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		chooserNgaySinh.setBounds(675, 107, 191, 25);
		chooserNgaySinh.setDateFormatString("dd/MM/yyyy");
		chooserNgaySinh.setDate(dNgayHienTai);
		chooserNgaySinh.setBorder(new LineBorder(new Color(114, 23, 153), 1, true));
		chooserNgaySinh.setFont(new Font("SansSerif", Font.PLAIN, 15));
		chooserNgaySinh.getCalendarButton().setPreferredSize(new Dimension(30, 24));
		chooserNgaySinh.getCalendarButton().setBackground(new Color(102, 0, 153));
		chooserNgaySinh.getCalendarButton().setToolTipText("Chọn ngày sinh");
		Icon iconCalendar = IconFontSwing.buildIcon(FontAwesome.CALENDAR, 17, Color.white);
		chooserNgaySinh.setIcon((ImageIcon) iconCalendar);
		pNhapThongTin.add(chooserNgaySinh);

		JLabel lblChucVu = new JLabel("Chức vụ:");
		lblChucVu.setBounds(10, 159, 98, 19);
		lblChucVu.setFont(new Font("SansSerif", Font.BOLD, 15));
		pNhapThongTin.add(lblChucVu);
		cboChucVu = new JComboBox<Object>(new Object[] { "Quản lý", "Nhân viên" });
		cboChucVu.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		cboChucVu.setBounds(110, 156, 191, 25);
		cboChucVu.setFont(new Font("SansSerif", Font.PLAIN, 15));
		cboChucVu.setBackground(Color.white);
		cboChucVu.setBorder(new LineBorder(new Color(114, 23, 153), 1, true));
		cboChucVu.setToolTipText("Chọn Chức vụ nhân viên");
		pNhapThongTin.add(cboChucVu);

		JLabel lblTim = new JLabel("Tìm kiếm:");
		lblTim.setFont(new Font("SansSerif", Font.BOLD, 14));
		lblTim.setBounds(332, 11, 90, 35);
		pMain.add(lblTim);
		//
		txtTim = new JTextField();
		txtTim.setText("Tìm nhân viên theo mã nhân viên");
		txtTim.setFont(new Font("SansSerif", Font.ITALIC, 15));
		txtTim.setForeground(Colors.LightGray);
		txtTim.setBorder(new LineBorder(new Color(114, 23, 153), 2, true));
		txtTim.setBounds(414, 11, 540, 33);
		txtTim.setToolTipText("Tìm kiếm thông tin nhân viên");
		pMain.add(txtTim);
		//
		btnTim = new FixButton("Tìm");
		btnTim.setForeground(Color.WHITE);
		btnTim.setFont(new Font("SansSerif", Font.BOLD, 14));
		btnTim.setBorder(new LineBorder(new Color(0, 146, 182), 2, true));
		btnTim.setBackground(new Color(114, 23, 153));
		btnTim.setBounds(964, 12, 127, 33);

		pMain.add(btnTim);

		/**
		 * Thêm 1 nhân viên vào danh sách bảng Nhân Viên Nút Thêm nhân viên JButton
		 * btnThemNV Icon iconThemNV
		 */
		btnThemNV = new FixButton("Thêm");
		btnThemNV.setForeground(Color.black);
		btnThemNV.setFont(new Font("SansSerif", Font.BOLD, 14));
		btnThemNV.setBorder(new LineBorder(new Color(0, 146, 182), 2, true));
		btnThemNV.setBackground(new Color(57, 210, 247));
		btnThemNV.setBounds(234, 191, 145, 38);

		pNhapThongTin.add(btnThemNV);

		/**
		 * Sửa thông tin nhân viên Nút sửa thông tin nhân viên JButton btnSuaNV Icon
		 * iconSuaNV
		 */
		btnSuaNV = new FixButton("Sửa");
		btnSuaNV.setForeground(Color.black);
		btnSuaNV.setFont(new Font("SansSerif", Font.BOLD, 14));
		btnSuaNV.setBorder(new LineBorder(new Color(0, 146, 182), 2, true));
		btnSuaNV.setBackground(new Color(133, 217, 191));
		btnSuaNV.setBounds(442, 189, 145, 40);

		pNhapThongTin.add(btnSuaNV);

		/**
		 * Làm mới: xoắ trắng các text, xóa tất cả nội dung trong bảng, đât mặc định các
		 * combobox, bỏ chọn checkbox và các radiobutton Nút làm mới JButton btnHủyNV
		 * Icon iconThemNV
		 */
		btnLamMoiNV = new FixButton("Làm mới");
		btnLamMoiNV.setText("Làm mới");
		btnLamMoiNV.setForeground(Color.WHITE);
		btnLamMoiNV.setFont(new Font("SansSerif", Font.BOLD, 14));
		btnLamMoiNV.setBorder(new LineBorder(new Color(0, 146, 182), 2, true));
		btnLamMoiNV.setBackground(new Color(114, 23, 153));
		btnLamMoiNV.setBounds(675, 192, 145, 37);
		pNhapThongTin.add(btnLamMoiNV);
		Icon iconHuyNV = IconFontSwing.buildIcon(FontAwesome.TIMES_CIRCLE, 20, Color.white);
		/**
		 * Nhóm các radiobutton ButtonGroup bg
		 */
		bg = new ButtonGroup();
		bg.add(btnThemNV);
		bg.add(btnSuaNV);
		bg.add(btnLamMoiNV);

		JLabel lblMaNV = new JLabel("Mã NV:");
		lblMaNV.setFont(new Font("SansSerif", Font.BOLD, 15));
		lblMaNV.setBounds(596, 65, 72, 25);
		pNhapThongTin.add(lblMaNV);

		txtMaNV = new JTextField();
		txtMaNV.setText("NV002");
		txtMaNV.setFont(new Font("SansSerif", Font.PLAIN, 15));
		txtMaNV.setColumns(10);
		txtMaNV.setBorder(new LineBorder(new Color(114, 23, 153), 1, true));
		txtMaNV.setBounds(675, 61, 191, 29);
		pNhapThongTin.add(txtMaNV);

		btnHuy = new FixButton("Hủy sửa");
		btnHuy.setText("Hủy sửa");
		btnHuy.setForeground(Color.BLACK);
		btnHuy.setFont(new Font("SansSerif", Font.BOLD, 14));
		btnHuy.setBorder(new LineBorder(new Color(0, 146, 182), 2, true));
		btnHuy.setBackground(Color.RED);
		btnHuy.setBounds(868, 190, 145, 40);
		pNhapThongTin.add(btnHuy);

		btnXoa = new FixButton("Xóa");
		btnXoa.setText("Xóa");
		btnXoa.setForeground(Color.BLACK);
		btnXoa.setFont(new Font("SansSerif", Font.BOLD, 14));
		btnXoa.setBorder(new LineBorder(new Color(0, 146, 182), 2, true));
		btnXoa.setBackground(new Color(210, 180, 140));
		btnXoa.setBounds(675, 190, 145, 40);
		pNhapThongTin.add(btnXoa);

		checkEdit();

		/**
		 * Bảng chứa các thông tin nhân viên JScrollPane scrollPaneNV String col[]: tên
		 * các cột JTable tblNV: nội dung của bảng JTableHeader tbHeader: định dạng
		 * khung các tên cột
		 */
		JScrollPane scrollPaneNV = new JScrollPane(tblNV, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		scrollPaneNV.setBorder(new LineBorder(new Color(164, 44, 167), 1, true));
		scrollPaneNV.setBackground(new Color(164, 44, 167));
		scrollPaneNV.setBounds(2, 304, 1258, 295);
		scrollPaneNV.getHorizontalScrollBar();
		scrollPaneNV.setToolTipText("Danh sách thông tin nhân viên");
		pMain.add(scrollPaneNV);

		String col[] = { "Mã NV", "Họ và tên Nhân viên", "Chức vụ", "Giới tính", "Ngày sinh", "Địa chỉ", "SĐT", "CCCD",
				"Lương", "Mật khẩu" };
		modelNV = new DefaultTableModel(col, 0);

		tblNV = new JTable(modelNV);
		tblNV.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		tblNV.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		tblNV.setShowHorizontalLines(true);
		tblNV.setShowGrid(true);
		tblNV.setBackground(Color.white);
		tblNV.setFont(new Font("SansSerif", Font.PLAIN, 13));
		tblNV.setSelectionBackground(new Color(164, 44, 167, 30));
		tblNV.setSelectionForeground(new Color(114, 23, 153));
		tblNV.setRowHeight(30);
		tblNV.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		tblNV.setToolTipText("Chọn nhân viên để thực hiện chức năng");

		JTableHeader tbHeader = tblNV.getTableHeader();
		tbHeader.setBackground(new Color(164, 44, 167));
		tbHeader.setForeground(Color.white);
		tbHeader.setFont(new Font("SansSerif", Font.BOLD, 14));
		tbHeader.setToolTipText("Danh sách thông tin nhân viên");

		tblNV.getColumnModel().getColumn(0).setPreferredWidth(100);
		tblNV.getColumnModel().getColumn(1).setPreferredWidth(160);
		tblNV.getColumnModel().getColumn(2).setPreferredWidth(80);
		tblNV.getColumnModel().getColumn(3).setPreferredWidth(75);
		tblNV.getColumnModel().getColumn(4).setPreferredWidth(80);
		tblNV.getColumnModel().getColumn(5).setPreferredWidth(350);
		tblNV.getColumnModel().getColumn(6).setPreferredWidth(90);
		tblNV.getColumnModel().getColumn(7).setPreferredWidth(100);
		tblNV.getColumnModel().getColumn(8).setPreferredWidth(100);
		tblNV.getColumnModel().getColumn(9).setPreferredWidth(120);

		DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
		rightRenderer.setHorizontalAlignment(JLabel.RIGHT);
		tblNV.getColumnModel().getColumn(4).setCellRenderer(rightRenderer);
		tblNV.getColumnModel().getColumn(6).setCellRenderer(rightRenderer);
		tblNV.getColumnModel().getColumn(7).setCellRenderer(rightRenderer);
		tblNV.getColumnModel().getColumn(8).setCellRenderer(rightRenderer);
		tblNV.getColumnModel().getColumn(9).setCellRenderer(rightRenderer);

		// tableNV.setOpaque(false);
		scrollPaneNV.setViewportView(tblNV);
		Image imgBackGround = Toolkit.getDefaultToolkit().getImage("data\\img\\background.png");

		/**
		 * CÃ¡c sá»± kiá»‡n cá»§a giao diá»‡n quáº£n lÃ½ nhÃ¢n viÃªn
		 */
		loadDanhSachNV();

		txtTim.addFocusListener(this);

		btnTim.addActionListener(this);
		btnThemNV.addActionListener(this);
		btnSuaNV.addActionListener(this);
		btnHuy.addActionListener(this);
		btnLamMoiNV.addActionListener(this);
		btnXoa.addActionListener(this);

		tblNV.addMouseListener(this);

	}

	/**
	 * Xóa hết dữ liệu trong bảng danh sách thông tin nhân viên
	 * 
	 * @param defaultTableModel trả về modelNV
	 */
	private void removeDanhSachNV(DefaultTableModel defaultTableModel) {
		while (tblNV.getRowCount() > 0) {
			modelNV.removeRow(0);
		}
	}

	/**
	 * Xóa trắng textfield và textarea, đặt lại mặc định các combobox và các button
	 */
	private void resetAll() {
		txtTim.setText("Tìm nhân viên theo mã nhân viên");
		txtTim.setFont(new Font("SansSerif", Font.ITALIC, 15));
		txtTim.setForeground(Colors.LightGray);

		txtHoTen.setText("");
		txtMaNV.setText("");
		txtSDT.setText("");
		txtDiaChi.setText("");
		txtCccd.setText("");

		cboChucVu.setSelectedItem("Quản lý");
		cboGioiTinh.setSelectedItem("Nam");
		chooserNgaySinh.setDate(dNow);

//		rdoTheoMaNV.setSelected(false);
//		rdoTheoTenNV.setSelected(false);
//		rdoTheoChucVuNV.setSelected(false);

		txtHoTen.requestFocus();
	}

	/**
	 * Hiện danh sách thông tin nhân viên đang làm việc từ mã NV gồm: mã NV, tên NV,
	 * chức vụ, giới tính, ngày sinh, địa chỉ, sđt, cccd, lương, ca làm việc, trạng
	 * thái làm việc, mật khẩu
	 * 
	 * @param nv
	 */
	private void loadDanhSachNV() {
		// clearTable();
		removeDanhSachNV(modelNV);
		ArrayList<NhanVien> lstNV = daoNhanVien.getAllDanhSachNV();
		for (NhanVien infoNV : lstNV) {
			TaiKhoan tk = daoTaiKhoan.getMatKhauTheoMaNV(infoNV.getMaNhanVien());
			modelNV.addRow(new Object[] { infoNV.getMaNhanVien(), infoNV.getTenNhanVien(), infoNV.getChucVu(),
					infoNV.getGioiTinh(), dfNgaySinh.format(infoNV.getNgaySinh()), infoNV.getDiaChi(), infoNV.getSdt(),
					infoNV.getCccd(), dfLuong.format(Math.round(infoNV.getLuong())), tk.getMatKhau() });
		}
	}

	/**
	 * Hiện danh sách thông tin NV theo tên nhân viên * @param lstNV
	 */
	private void loadDanhSachMaVaSdtNV(ArrayList<NhanVien> lstNV) {
		removeDanhSachNV(modelNV);
		ArrayList<NhanVien> lstMa = daoNhanVien.getMaVaSDTNV(txtTim.getText().toLowerCase().trim());
		for (NhanVien infoNV : lstMa) {
			TaiKhoan tk = daoTaiKhoan.getMatKhauTheoMaNV(infoNV.getMaNhanVien());
			modelNV.addRow(new Object[] { infoNV.getMaNhanVien(), infoNV.getTenNhanVien(), infoNV.getChucVu(),
					infoNV.getGioiTinh(), dfNgaySinh.format(infoNV.getNgaySinh()), infoNV.getDiaChi(), infoNV.getSdt(),
					infoNV.getCccd(), dfLuong.format(Math.round(infoNV.getLuong())), tk.getMatKhau() });
		}
	}

	/**
	 * Hiá»‡n danh sÃ¡ch thÃ´ng tin NV theo tÃªn nhÃ¢n viÃªn
	 * 
	 * @param lstNV
	 */
	private void loadDanhSachTenNV(ArrayList<NhanVien> lstNV) {
		removeDanhSachNV(modelNV);
		ArrayList<NhanVien> lstName = daoNhanVien.getTenNV(txtTim.getText().trim());
		for (NhanVien infoNV : lstName) {
			TaiKhoan tk = daoTaiKhoan.getMatKhauTheoMaNV(infoNV.getMaNhanVien());
			modelNV.addRow(new Object[] { infoNV.getMaNhanVien(), infoNV.getTenNhanVien(), infoNV.getChucVu(),
					infoNV.getGioiTinh(), dfNgaySinh.format(infoNV.getNgaySinh()), infoNV.getDiaChi(), infoNV.getSdt(),
					infoNV.getCccd(), dfLuong.format(Math.round(infoNV.getLuong())), tk.getMatKhau() });
		}
	}

	/**
	 * Sá»± kiá»‡n tÃ¬m kiáº¿m thÃ´ng tin nhÃ¢n viÃªn
	 */
	private void findNV() {
		String input = txtTim.getText().trim();
		input = input.toUpperCase();
		ArrayList<NhanVien> lstNV = null;

		String regexMaNV = "^((NV|nv)[0-9]{3})$";
		String regexTenNV = "^[ A-Za-za-zA-ZÀÁÂÃÈÉÊÌÍÒÓÔÕÙÚĂĐĨŨƠàáâãèéêìíòóôõùúăđĩũơƯĂẠẢẤẦẨẪẬẮẰẲẴẶẸẺẼỀỀỂẾưăạảấầẩẫậắằẳẵặẹẻẽềềểếỄỆỈỊỌỎỐỒỔỖỘỚỜỞỠỢỤỦỨỪễệỉịọỏốồổỗộớờởỡợụủứừỬỮỰỲỴÝỶỸửữựỳỵỷỹ]+$";
		String regexSDT = "^(0[0-9]{9})$";

		if (regex.regexTimNV(txtTim)) {
			if (input.matches(regexMaNV)) {
				lstNV = daoNhanVien.getMaVaSDTNV(input);
				loadDanhSachMaVaSdtNV(lstNV);
			} else if (input.matches(regexTenNV)) {
				lstNV = daoNhanVien.getTenNV(input);
				loadDanhSachTenNV(lstNV);
			} else if (input.matches(regexSDT)) {
				lstNV = daoNhanVien.getMaVaSDTNV(input);
				loadDanhSachMaVaSdtNV(lstNV);
			}

			if (lstNV.size() == 0) {
				JOptionPane.showMessageDialog(this, "Không tìm thấy thông tin nhân viên", "Thông báo",
						JOptionPane.ERROR_MESSAGE);
				txtTim.requestFocus();
				txtTim.selectAll();
			}
		}
	}

	/**
	 * Tạo tài khoản nhân viên mới
	 */
	@SuppressWarnings("deprecation")
	private void addNV() {
		String maNV = txtMaNV.getText().trim();
		String hoTen = txtHoTen.getText().trim();
		String sdt = txtSDT.getText().trim();
		String diaChi = txtDiaChi.getText().trim();
		String chucVu = cboChucVu.getSelectedItem().toString();
		String cccd = txtCccd.getText().trim();
		String gioiTinh = cboGioiTinh.getSelectedItem().toString();

		java.util.Date date = chooserNgaySinh.getDate();
		Date ngaySinh = new Date(date.getYear(), date.getMonth(), date.getDate());
		int age = nam - ngaySinh.getYear();

//		kiem tra maNV
		ArrayList<NhanVien> lstNV = null;
		String regexMaNV = "^((NV|nv)[0-9]{3})$";
		if (maNV.matches(regexMaNV)) {

			if (daoNhanVien.checkmaNV(maNV)) {
				TaiKhoan tk = new TaiKhoan(maNV);
				String matKhau = maNV;

				if (hoTen.equals("") || sdt.equals("") || diaChi.equals("") || cccd.equals("")) {
					JOptionPane.showMessageDialog(this, "Vui lòng nhập thông tin đầy đủ!", "Thông báo",
							JOptionPane.WARNING_MESSAGE);
					txtHoTen.requestFocus();
				} else {
					if (regex.regexTen(txtHoTen) && regex.regexSDT(txtSDT) && regex.regexDiaChi(txtDiaChi)
							&& regex.regexCCCD(txtCccd)) {
						if (daoNhanVien.checkSdtNV(sdt)) {
							if (daoNhanVien.checkCccdNV(cccd)) {
								if (age >= 18 && ngaySinh.getDate() > 0 && ngaySinh.getDate() <= 31
										&& ngaySinh.getMonth() > 0 && ngaySinh.getMonth() <= 12
										&& ngaySinh.getYear() > 0 && ngaySinh.getYear() < nam) {
									TaiKhoan tk1 = new TaiKhoan();
									tk1.setMaTK(maNV);
									tk1.setMatKhau(matKhau);
									try {
										new DAOTaiKhoan().createTK(tk1);
									} catch (SQLException e2) {
										e2.printStackTrace();
									}

									// them vao data
									NhanVien nv = new NhanVien();
									nv.setMaNhanVien(maNV);
									nv.setTaiKhoan(tk);
									nv.setTenNhanVien(hoTen);
									nv.setChucVu(chucVu);
									nv.setGioiTinh(gioiTinh);
									nv.setNgaySinh(ngaySinh);
									nv.setDiaChi(diaChi);
									nv.setSdt(sdt);
									nv.setCccd(cccd);
									if (cboChucVu.getSelectedItem() == "Quản lý")
										nv.setLuong(10000000);
									if (cboChucVu.getSelectedItem() == "Nhân Viên")
										nv.setLuong(6000000);
									try {
										daoNhanVien.themNV(nv);
									} catch (SQLException e) {
										e.printStackTrace();
										JOptionPane.showMessageDialog(this, "Thêm nhân viên thất bại!", "Thông báo",
												JOptionPane.ERROR_MESSAGE);
									}

									// them vao table
									resetAll();
									modelNV.addRow(new Object[] { maNV, hoTen, chucVu, gioiTinh,
											dfNgaySinh.format(chooserNgaySinh.getDate()), diaChi, sdt, cccd,
											dfLuong.format(Math.round(nv.getLuong())), matKhau });
									String mkTK = "\nMật khẩu: " + matKhau;
									JOptionPane.showMessageDialog(this,
											"Thêm thành công!\nMã tài khoản: " + maNV + mkTK, "ThÃ´ng bÃ¡o",
											JOptionPane.INFORMATION_MESSAGE);
								} else
									JOptionPane.showMessageDialog(this, "Nhân viên làm việc phải trên 18 tuổi!",
											"Thông báo", JOptionPane.WARNING_MESSAGE);
							} else
								JOptionPane.showMessageDialog(this, "Căn cước công dân đã đăng ký", "Thông báo",
										JOptionPane.ERROR_MESSAGE);
						} else
							JOptionPane.showMessageDialog(this, "Số điện thoại đã đăng ký", "Thông báo",
									JOptionPane.ERROR_MESSAGE);
					}
				}
			} else {
				JOptionPane.showMessageDialog(this, "Mã nhân viên đã tồn tại!", "Thông báo", JOptionPane.ERROR_MESSAGE);
			}

		} else {
			JOptionPane.showMessageDialog(this, "Mã nhân viên có định dạng NV00x!", "Thông báo",
					JOptionPane.ERROR_MESSAGE);
		}

	}

	@SuppressWarnings("deprecation")
	private void XoaNV() {
		int row = tblNV.getSelectedRow();
		if (row >= 0) {
			int delete = JOptionPane.showConfirmDialog(this, "Bạn muốn xóa nhân viên này?", "Thông báo",
					JOptionPane.YES_NO_OPTION);
			if (delete == JOptionPane.YES_OPTION) {
				String maNV = (String) tblNV.getValueAt(row, 0);
				try {

					daoNhanVien.xoaNV(maNV);
//						daoTaiKhoan.xoaTK(maNV);
					removeDanhSachNV(modelNV);
					JOptionPane.showMessageDialog(this, "Xóa nhân viên thành công!", "Thông báo",
							JOptionPane.INFORMATION_MESSAGE);

				} catch (SQLException e) {
					e.printStackTrace();
					JOptionPane.showMessageDialog(null, "Xóa nhân viên thất bại!", "Thông báo",
							JOptionPane.ERROR_MESSAGE);
				}
			}
		} else
			JOptionPane.showMessageDialog(null, "Vui lòng chọn thông tin nhân viên cần sửa!", "Thông báo",
					JOptionPane.WARNING_MESSAGE);

		loadDanhSachNV();

	}

	@SuppressWarnings("deprecation")
	private void updateNV() {
		int row = tblNV.getSelectedRow();
		if (row >= 0) {
			int update = JOptionPane.showConfirmDialog(this, "Bạn muốn sửa thông tin nhân viên này?", "Thông báo",
					JOptionPane.YES_NO_OPTION);
			if (update == JOptionPane.YES_OPTION) {
				NhanVien nv = new NhanVien();
				String maNV = (String) tblNV.getValueAt(row, 0);
				java.util.Date date = chooserNgaySinh.getDate();
				Date ngaySinh = new Date(date.getYear(), date.getMonth(), date.getDate());
				try {
					if (regex.regexTen(txtHoTen) && regex.regexSDT(txtSDT) && regex.regexDiaChi(txtDiaChi)
							&& regex.regexCCCD(txtCccd)) {
						nv.setTenNhanVien(txtHoTen.getText());
						nv.setChucVu((String) cboChucVu.getSelectedItem());
						nv.setGioiTinh((String) cboGioiTinh.getSelectedItem());
						nv.setNgaySinh(ngaySinh);
						nv.setDiaChi(txtDiaChi.getText());
						nv.setSdt(txtSDT.getText());
						nv.setCccd(txtCccd.getText());
						if (cboChucVu.getSelectedItem() == "Quản lý")
							nv.setLuong(10000000);
						if (cboChucVu.getSelectedItem() == "Thu ngân")
							nv.setLuong(6000000);

						new DAONhanVien().capNhatNV(nv, maNV);
						TaiKhoan tk = daoTaiKhoan.getMatKhauTheoMaNV(maNV);

						removeDanhSachNV(modelNV);
						modelNV.setRowCount(0);
						modelNV.addRow(new Object[] { maNV, nv.getTenNhanVien(), nv.getChucVu(), nv.getGioiTinh(),
								dfNgaySinh.format(nv.getNgaySinh()), nv.getDiaChi(), nv.getSdt(), nv.getCccd(),
								dfLuong.format(Math.round(nv.getLuong())), tk.getMatKhau() });
						JOptionPane.showMessageDialog(this, "Thông tin nhân viên đã được sửa!", "Thông báo",
								JOptionPane.INFORMATION_MESSAGE);
					}
				} catch (SQLException e) {
					e.printStackTrace();
					JOptionPane.showMessageDialog(null, "Chỉnh sửa thông tin thất bại!", "Thông báo",
							JOptionPane.ERROR_MESSAGE);
				}
			}
		} else
			JOptionPane.showMessageDialog(null, "Vui lòng chọn thông tin nhân viên cần sửa!", "Thông báo",
					JOptionPane.WARNING_MESSAGE);

		loadDanhSachNV();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object o = e.getSource();

		// tìm NV
		if (o.equals(btnTim)) {
			if (txtTim.getText().equals("")
					|| txtTim.getText().equals("Tìm nhân viên theo mã nhân viên, tên nhân viên, sđt.")) {
				removeDanhSachNV(modelNV);
				JOptionPane.showMessageDialog(this, "Vui lòng nhập thông tin tìm kiếm!", "Thông báo",
						JOptionPane.WARNING_MESSAGE);
				txtTim.requestFocus();
			} else {
				findNV();
				txtTim.selectAll();
			}
		}

		// thêm NV
		if (o.equals(btnThemNV)) {
			addNV();
		}

//sửa nhân vien
		if (o.equals(btnSuaNV)) {
			updateNV();
		}

		if (o.equals(btnLamMoiNV)) {
			resetAll();
			bg.clearSelection();
			removeDanhSachNV(modelNV);
			loadDanhSachNV();
		}
		if (o.equals(btnXoa)) {
			XoaNV();
			tblNV.clearSelection();
			statusEdit = false;
			checkEdit();
		}
		if (o.equals(btnHuy)) {
			resetAll();
			tblNV.clearSelection();
			statusEdit = false;
			checkEdit();
		}

	}

	private void checkEdit() {
		if (!statusEdit) {
			btnXoa.setVisible(false);
			btnHuy.setVisible(false);
			btnLamMoiNV.setVisible(true);
			btnThemNV.setEnabled(true);
			txtMaNV.setEditable(true);
		} else {
			btnXoa.setVisible(true);
			btnHuy.setVisible(true);
			btnLamMoiNV.setVisible(false);
			btnThemNV.setEnabled(false);
		}

	}

	/**
	 * Sá»± kiá»‡n cá»§a click chuá»™t
	 */
	@Override
	public void mouseClicked(MouseEvent e) {
		int selectedRow = tblNV.getSelectedRow();

		if (selectedRow >= 0) {
			txtMaNV.setEditable(false);
			statusEdit = true;
			checkEdit();
			String maNV = (String) tblNV.getValueAt(selectedRow, 0);
			ArrayList<NhanVien> lstNV = daoNhanVien.getAllDanhSachNV();
			for (NhanVien nv : lstNV) {
				if (maNV.equals(nv.getMaNhanVien())) {
					txtMaNV.setText(maNV);
					txtHoTen.setText(nv.getTenNhanVien());
					txtSDT.setText(nv.getSdt());
					txtDiaChi.setText(nv.getDiaChi());
					cboChucVu.setSelectedItem(nv.getChucVu());
					txtCccd.setText(nv.getCccd());
					cboGioiTinh.setSelectedItem(nv.getGioiTinh());
					chooserNgaySinh.setDate(nv.getNgaySinh());
					break;
				}

			}
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	/**
	 * Sá»± kiá»‡n placeholder cá»§a txtTim
	 */
	@Override
	public void focusGained(FocusEvent e) {
		if (txtTim.getText().equals("Tìm nhân viên theo mã nhân viên, tên nhân viên, sđt.")) {
			txtTim.setFont(new Font("SansSerif", Font.PLAIN, 15));
			txtTim.setForeground(Color.BLACK);
			txtTim.setText("");
		}
	}

	@Override
	public void focusLost(FocusEvent e) {
		if (txtTim.getText().equals("")) {
			txtTim.setFont(new Font("SansSerif", Font.ITALIC, 15));
			txtTim.setForeground(Colors.LightGray);
			txtTim.setText("Tìm nhân viên theo mã nhân viên, tên nhân viên, sđt.");
		}
	}
}
