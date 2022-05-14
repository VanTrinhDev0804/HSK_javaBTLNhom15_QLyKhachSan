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
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.Format;
import java.text.ParseException;
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
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

import com.mindfusion.drawing.Colors;
import com.toedter.calendar.JDateChooser;

import connection.ConnectDB;
import dao.DAOKhachHang;
import dao.DAOLoaiKH;
import dao.DAOPhatSinhMa;
import dao.Regex;
import entity.KhachHang;
import entity.LoaiKH;
import entity.NhanVien;
import jiconfont.icons.FontAwesome;
import jiconfont.swing.IconFontSwing;

public class FrmKhachHang extends JFrame
		implements ActionListener, MouseListener, ItemListener, FocusListener, KeyListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@SuppressWarnings("unused")
	private String sHeaderMaNV;
	@SuppressWarnings("unused")
	private String sHeaderTenNV;
	@SuppressWarnings("unused")
	private Date dNgayHienTai;
	private LocalDate now;
	private int ngay;
	private int thang;
	private int nam;
	private Date dNow;
	private Panel pMain;
	private JTextField txtTK;
	private JButton btnTim;
	private JButton btnThemKH;
	private JButton btnSuaKH;
	private JButton btnXoaKH;
	private JButton btnReset;
	private JTextField txtCccd;
	private JTextField txtPoint;
	private JTable tableKH;
	private DAOLoaiKH daoLoaiKH;
	private DAOKhachHang daoKhachHang;
	private DAOPhatSinhMa daoMaKH;
	private DefaultTableModel modelKhachHang;
	private JComboBox<String> cboloaiKH;
	private JComboBox<String> cbogioiTinh;
	private JDateChooser dateChooserNgaySinh;
	private JDateChooser dateChooserNgayDangKy;
	private Format dfNgaySinh;
	private Regex regex;
	private KhachHang kh;
	private ButtonGroup bg;
	private JPanel pNhapThongTin;
	private JLabel lblNhapThongTin;
	private JTextField txtHoTen;
	private JTextField txtSDT;
	private JTextField txtDiaChi;

	public Panel getFrmKH() {
		return this.pMain;
	}

	@SuppressWarnings("deprecation")
	public FrmKhachHang(String sHeaderTenNV, String sHeaderMaNV, Date dNgayHienTai) {

		this.sHeaderMaNV = sHeaderMaNV;
		this.sHeaderTenNV = sHeaderTenNV;
		this.dNgayHienTai = dNgayHienTai;

		// connect database
		try {
			ConnectDB.getinstance().connect();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		// khai bao dao
		daoLoaiKH = new DAOLoaiKH();
		daoKhachHang = new DAOKhachHang();
		daoMaKH = new DAOPhatSinhMa();
		regex = new Regex();

		// dfNgaySinh
		// dfNgayDangKy = new SimpleDateFormat("dd/MM/yyyy");

		// Giao dien
		getContentPane().setLayout(null);
		pMain = new Panel();
		pMain.setBackground(Color.WHITE);
		pMain.setBounds(0, 0, 1281, 629);
		getContentPane().add(pMain);
		pMain.setLayout(null);

		pNhapThongTin = new JPanel();
		pNhapThongTin.setBorder(new LineBorder(new Color(114, 23, 153)));
		pNhapThongTin.setBackground(Color.WHITE);
		pNhapThongTin.setBounds(10, 11, 1247, 237);
		pMain.add(pNhapThongTin);
		pNhapThongTin.setLayout(null);

		lblNhapThongTin = new JLabel("Nhập thông tin khách hàng");
		lblNhapThongTin.setHorizontalAlignment(SwingConstants.CENTER);
		lblNhapThongTin.setFont(new Font("SansSerif", Font.BOLD, 18));
		lblNhapThongTin.setBounds(10, 11, 313, 29);
		pNhapThongTin.add(lblNhapThongTin);

		// Nhap ho ten
		txtHoTen = new JTextField();
		txtHoTen.setFont(new Font("SansSerif", Font.PLAIN, 14));
		txtHoTen.setColumns(10);
		txtHoTen.setBorder(new LineBorder(new Color(114, 23, 153), 1, true));
		txtHoTen.setBounds(110, 64, 170, 27);
		pNhapThongTin.add(txtHoTen);
		txtHoTen.setText("Võ Kim Bản");

		JLabel lblHoTen = new JLabel("Họ và tên:");
		lblHoTen.setBounds(10, 64, 90, 27);
		pNhapThongTin.add(lblHoTen);
		lblHoTen.setFont(new Font("SansSerif", Font.BOLD, 15));

		// Nhap so dien thoai
		JLabel lblSDT = new JLabel("SĐT:");
		lblSDT.setBounds(331, 66, 59, 23);
		pNhapThongTin.add(lblSDT);
		lblSDT.setFont(new Font("SansSerif", Font.BOLD, 15));

		txtSDT = new JTextField();
		txtSDT.setFont(new Font("SansSerif", Font.PLAIN, 14));
		txtSDT.setColumns(10);
		txtSDT.setBorder(new LineBorder(new Color(114, 23, 153), 1, true));
		txtSDT.setBounds(433, 64, 170, 27);
		pNhapThongTin.add(txtSDT);
		txtSDT.setText("02822292211");

		// Nhap dia chi
		JLabel lblAddress = new JLabel("Địa chỉ:");
		lblAddress.setBounds(10, 101, 84, 27);
		pNhapThongTin.add(lblAddress);
		lblAddress.setFont(new Font("SansSerif", Font.BOLD, 15));

		txtDiaChi = new JTextField();
		txtDiaChi.setFont(new Font("SansSerif", Font.PLAIN, 14));
		txtDiaChi.setColumns(10);
		txtDiaChi.setBorder(new LineBorder(new Color(114, 23, 153), 1, true));
		txtDiaChi.setBounds(110, 101, 493, 27);
		pNhapThongTin.add(txtDiaChi);
		txtDiaChi.setText("23 Trần Não, quận 2");

		// Nhap cac cuoc cong dan
		txtCccd = new JTextField();
		txtCccd.setBounds(733, 64, 170, 27);
		pNhapThongTin.add(txtCccd);
		txtCccd.setFont(new Font("SansSerif", Font.PLAIN, 14));
		txtCccd.setColumns(10);
		txtCccd.setBorder(new LineBorder(new Color(114, 23, 153), 1, true));
		txtCccd.setText("032144572012");

		JLabel lblCccd = new JLabel("CCCD:");
		lblCccd.setBounds(658, 66, 65, 23);
		pNhapThongTin.add(lblCccd);
		lblCccd.setFont(new Font("SansSerif", Font.BOLD, 15));

		now = LocalDate.now();
		ngay = now.getDayOfMonth();
		thang = now.getMonthValue() - 1;
		nam = now.getYear() - 1900;

		dNow = new Date(nam, thang, ngay);
		// Chon ngay sinh cua khach hang
		dateChooserNgaySinh = new JDateChooser();
		dateChooserNgaySinh.setBounds(733, 101, 170, 28);
		pNhapThongTin.add(dateChooserNgaySinh);

		dateChooserNgaySinh.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		dateChooserNgaySinh.setDateFormatString("dd/MM/yyyy");
		dateChooserNgaySinh.setBorder(new LineBorder(new Color(114, 23, 153), 1, true));
		dateChooserNgaySinh.setFont(new Font("SansSerif", Font.PLAIN, 15));
		dateChooserNgaySinh.getCalendarButton().setPreferredSize(new Dimension(30, 24));
		dateChooserNgaySinh.getCalendarButton().setBackground(new Color(102, 0, 153));
		Icon iconCalendar = IconFontSwing.buildIcon(FontAwesome.CALENDAR, 20, Color.white);
		dateChooserNgaySinh.setIcon((ImageIcon) iconCalendar);
		dateChooserNgaySinh.setDate(dNow);

		JLabel lblNgaySinh = new JLabel("Ngày sinh:");
		lblNgaySinh.setBounds(658, 100, 102, 28);
		pNhapThongTin.add(lblNgaySinh);
		lblNgaySinh.setFont(new Font("SansSerif", Font.BOLD, 15));

		// chon ngay dang ki
		JLabel lblNgayDangKy = new JLabel("Ngày đăng ký:");
		lblNgayDangKy.setBounds(950, 100, 113, 29);
		pNhapThongTin.add(lblNgayDangKy);
		lblNgayDangKy.setFont(new Font("SansSerif", Font.BOLD, 15));

		dateChooserNgayDangKy = new JDateChooser();
		dateChooserNgayDangKy.setBounds(1054, 101, 170, 28);
		pNhapThongTin.add(dateChooserNgayDangKy);

		dateChooserNgayDangKy.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		dateChooserNgayDangKy.setDateFormatString("dd/MM/yyyy");
		dateChooserNgayDangKy.setBorder(new LineBorder(new Color(114, 23, 153), 1, true));
		dateChooserNgayDangKy.setFont(new Font("SansSerif", Font.PLAIN, 15));
		dateChooserNgayDangKy.getCalendarButton().setPreferredSize(new Dimension(30, 24));
		dateChooserNgayDangKy.getCalendarButton().setBackground(new Color(102, 0, 153));
		Icon iconCalendar1 = IconFontSwing.buildIcon(FontAwesome.CALENDAR, 20, Color.white);
		dateChooserNgayDangKy.setIcon((ImageIcon) iconCalendar1);
		dateChooserNgayDangKy.setDate(dNow);

		// Loai khach hang
		JLabel lblLoaiKH = new JLabel("Loại khách hàng:");
		lblLoaiKH.setBounds(10, 149, 135, 23);
		pNhapThongTin.add(lblLoaiKH);
		lblLoaiKH.setFont(new Font("SansSerif", Font.BOLD, 15));

		cboloaiKH = new JComboBox<String>();
		cboloaiKH.setToolTipText("Chọn loại khách hàng hàng");
		cboloaiKH.setBounds(133, 147, 170, 27);
		pNhapThongTin.add(cboloaiKH);
		cboloaiKH.setFont(new Font("SansSerif", Font.PLAIN, 15));
		cboloaiKH.setBorder(new LineBorder(new Color(114, 23, 153), 1, true));
		cboloaiKH.setBackground(new Color(255, 255, 255));

		// Chon gioi tinh
		JLabel lblGioiTinh = new JLabel("Giới tính:");
		lblGioiTinh.setBounds(950, 64, 72, 26);
		pNhapThongTin.add(lblGioiTinh);
		lblGioiTinh.setFont(new Font("SansSerif", Font.BOLD, 15));

		cbogioiTinh = new JComboBox<String>();

		cbogioiTinh.setToolTipText("Chọn giới tính");
		cbogioiTinh.setBounds(1054, 63, 170, 28);
		pNhapThongTin.add(cbogioiTinh);
		cbogioiTinh.setFont(new Font("SansSerif", Font.PLAIN, 15));
		cbogioiTinh.setBackground(Color.WHITE);
		cbogioiTinh.setBorder(new LineBorder(new Color(114, 23, 153), 1, true));

		// diem tich cua khach hang
		JLabel lblDiem = new JLabel("Điểm tích lũy:");
		lblDiem.setBounds(331, 146, 102, 28);
		pNhapThongTin.add(lblDiem);
		lblDiem.setFont(new Font("SansSerif", Font.BOLD, 15));

		txtPoint = new JTextField();
		txtPoint.setBounds(433, 147, 170, 28);
		pNhapThongTin.add(txtPoint);
		txtPoint.setFont(new Font("SansSerif", Font.PLAIN, 14));
		txtPoint.setBorder(new LineBorder(new Color(114, 23, 153), 2, true));
		txtPoint.setBorder(new LineBorder(new Color(114, 23, 153), 1, true));
		txtPoint.setEditable(isDisplayable());
		txtPoint.setColumns(10);

		// lblTim
		JLabel lblTim = new JLabel("Tìm kiếm:");
		lblTim.setFont(new Font("SansSerif", Font.BOLD, 14));
		lblTim.setBounds(353, 11, 90, 35);
		pMain.add(lblTim);

		// txtTK
		txtTK = new JTextField();
		txtTK.setToolTipText("Tìm khách hàng theo mã, tên, sđt và loại khách hàng");
		txtTK.setText("Tìm khách hàng theo mã, tên, sđt và loại khách hàng.");
		txtTK.setFont(new Font("SansSerif", Font.ITALIC, 15));
		txtTK.setForeground(Colors.LightGray);
		txtTK.setBorder(new LineBorder(new Color(114, 23, 153), 2, true));
		txtTK.setBounds(421, 11, 526, 33);
		pMain.add(txtTK);

		// btnTim
		btnTim = new FixButton("Tìm");
		btnTim.setForeground(Color.WHITE);

		btnTim.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnTim.setFont(new Font("SansSerif", Font.BOLD, 14));
		btnTim.setBorder(new LineBorder(new Color(0, 146, 182), 2, true));
		btnTim.setBackground(new Color(114, 23, 153));
		btnTim.setBounds(957, 12, 132, 33);

		pMain.add(btnTim);
		Icon iconExcel = IconFontSwing.buildIcon(FontAwesome.FILE_EXCEL_O, 20, Color.white);

		String cbbGioiTinh[] = { "Nam", "Nữ" };
		for (int i = 0; i < cbbGioiTinh.length; i++) {
			cbogioiTinh.addItem(cbbGioiTinh[i]);
		}

		// btnThem
		btnThemKH = new FixButton("Thêm");

		btnThemKH.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnThemKH.setForeground(Color.WHITE);
		btnThemKH.setFont(new Font("SansSerif", Font.BOLD, 14));
		btnThemKH.setBorder(new LineBorder(new Color(0, 146, 182), 2, true));
		btnThemKH.setBackground(new Color(57, 210, 247));
		btnThemKH.setBounds(178, 184, 145, 38);

		pNhapThongTin.add(btnThemKH);

		// btn sua thong tin
		btnSuaKH = new FixButton("Sửa");
		btnSuaKH.setForeground(Color.WHITE);
		btnSuaKH.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnSuaKH.setFont(new Font("SansSerif", Font.BOLD, 14));
		btnSuaKH.setBorder(new LineBorder(new Color(0, 146, 182), 2, true));
		btnSuaKH.setBackground(new Color(133, 217, 191));
		btnSuaKH.setBounds(377, 184, 145, 38);

		pNhapThongTin.add(btnSuaKH);

		// btn set loaiKH khong con la khach hang
		btnXoaKH = new FixButton("Xóa");
		btnXoaKH.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnXoaKH.setForeground(Color.WHITE);
		btnXoaKH.setFont(new Font("SansSerif", Font.BOLD, 14));
		btnXoaKH.setBorder(new LineBorder(new Color(0, 146, 182), 2, true));
		btnXoaKH.setBackground(new Color(0xE91940));
		btnXoaKH.setBounds(786, 184, 145, 38);

		pNhapThongTin.add(btnXoaKH);

		// dua cac txt, rdo, cbo ve mac dinh
		btnReset = new FixButton("Làm mới");
		btnReset.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnReset.setToolTipText("Làm mới tất cả thông tin");
		btnReset.setForeground(Color.WHITE);
		btnReset.setFont(new Font("SansSerif", Font.BOLD, 14));
		btnReset.setBorder(new LineBorder(new Color(0, 146, 182), 2, true));
		btnReset.setBackground(new Color(114, 23, 153));
		btnReset.setBounds(579, 185, 145, 38);

		pNhapThongTin.add(btnReset);

		JScrollPane scrollPaneKH = new JScrollPane();
		scrollPaneKH.setToolTipText("Chọn khách hàng cần hiển thị thông tin");
		scrollPaneKH.setBorder(new LineBorder(new Color(164, 44, 167), 1, true));
		scrollPaneKH.setBackground(new Color(164, 44, 167));
		scrollPaneKH.setBounds(10, 258, 1247, 371);
		pMain.add(scrollPaneKH);

		String col[] = { "Mã KH", "Họ và tên KH", "Loại KH", "Giới tính", "Ngày sinh", "Địa chỉ", "SĐT", "CCCD",
				"Ngày đăng ký", "Điểm tích lũy" };
		modelKhachHang = new DefaultTableModel(col, 0);
		tableKH = new JTable(modelKhachHang);

		// header cua table
		JTableHeader tbHeader = tableKH.getTableHeader();
		tbHeader.setBackground(new Color(164, 44, 167));
		tbHeader.setForeground(Color.white);
		tbHeader.setFont(new Font("SansSerif", Font.BOLD, 14));

		// do dai cua tung cot trong table
		tableKH.getColumnModel().getColumn(0).setPreferredWidth(80);
		tableKH.getColumnModel().getColumn(1).setPreferredWidth(200);
		tableKH.getColumnModel().getColumn(2).setPreferredWidth(130);
		tableKH.getColumnModel().getColumn(3).setPreferredWidth(80);
		tableKH.getColumnModel().getColumn(4).setPreferredWidth(80);
		tableKH.getColumnModel().getColumn(5).setPreferredWidth(400);
		tableKH.getColumnModel().getColumn(6).setPreferredWidth(80);
		tableKH.getColumnModel().getColumn(7).setPreferredWidth(100);
		tableKH.getColumnModel().getColumn(8).setPreferredWidth(100);
		tableKH.getColumnModel().getColumn(9).setPreferredWidth(100);
		tableKH.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

		// Chữ canh trái, số canh phải
		DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
		rightRenderer.setHorizontalAlignment(JLabel.RIGHT);
		tableKH.getColumnModel().getColumn(6).setCellRenderer(rightRenderer);

		tableKH.getColumnModel().getColumn(8).setCellRenderer(rightRenderer);
		tableKH.getColumnModel().getColumn(7).setCellRenderer(rightRenderer);
		tableKH.getColumnModel().getColumn(9).setCellRenderer(rightRenderer);

		tableKH.setShowGrid(true);
		tableKH.setShowHorizontalLines(true);
		tableKH.setBackground(Color.WHITE);
		tableKH.setSelectionBackground(new Color(164, 44, 167, 30));
		tableKH.setSelectionForeground(new Color(114, 23, 153));
		tableKH.setFont(new Font("SansSerif", Font.PLAIN, 13));
		tableKH.setRowHeight(30);
		tableKH.setGridColor(getBackground());
		// tableKH.setOpaque(false);
		tableKH.setSelectionBackground(new Color(164, 44, 167, 30));
		scrollPaneKH.setViewportView(tableKH);

		// chèn background
		JLabel lblBackground = new JLabel("");
		lblBackground.setIcon(new ImageIcon("data\\img\\background.png"));
		lblBackground.setBounds(0, 0, 1281, 629);
		Image imgBackground = Toolkit.getDefaultToolkit().getImage("data\\img\\background.png");
		Image resizeBG = imgBackground.getScaledInstance(lblBackground.getWidth(), lblBackground.getHeight(), 0);
		lblBackground.setIcon(new ImageIcon(resizeBG));
		pMain.add(lblBackground);

		// end giao dien
		// Load tên loại KH
		ArrayList<LoaiKH> lsLoaiKH = daoLoaiKH.getAllLoaiKH();
		for (LoaiKH lkh : lsLoaiKH) {
			cboloaiKH.addItem(lkh.getTenLoaiKH());
		}

		// add actions
		dfNgaySinh = new SimpleDateFormat("dd/MM/yyyy");
		loadDanhSachKH();
		btnThemKH.addActionListener(this);
		btnXoaKH.addActionListener(this);
		btnSuaKH.addActionListener(this);
		tableKH.addMouseListener(this);
		btnReset.addActionListener(this);
		btnTim.addActionListener(this);

		txtTK.addFocusListener(this);
		txtTK.addKeyListener(this);
		txtHoTen.addKeyListener(this);
		txtSDT.addKeyListener(this);
		txtDiaChi.addKeyListener(this);
		txtCccd.addKeyListener(this);
	}

	// end giao diện

	/**
	 * Load danh sách thông tin khách hàng
	 */

	// Load danh sach tu du lieu
	public void loadDanhSachKH() {
		clearTable();
		ArrayList<KhachHang> lsKH = daoKhachHang.getDanhSachKH();
		for (KhachHang kh : lsKH) {
			LoaiKH loaiKH = daoLoaiKH.getLoaiKHTheoMaLoai(kh.getLoaiKH().getMaLoaiKH());
			String ngaySinh = "";
			String ngayDK = "";
			if (kh.getNgaySinh() != null)
				ngaySinh = dfNgaySinh.format(kh.getNgaySinh());
			if (kh.getNgayDangKy() != null)
				ngayDK = dfNgaySinh.format(kh.getNgayDangKy());

			modelKhachHang
					.addRow(new Object[] { kh.getMaKhangHang(), kh.getTenKH(), loaiKH.getTenLoaiKH(), kh.getGioiTinh(),
							ngaySinh, kh.getDiaChi(), kh.getSdt(), kh.getCccd(), ngayDK, kh.getDiemTichLuy() });
		}
	}

	// load thong tin 1 nguoi
	public void loadThongTin(KhachHang kh) {
		LoaiKH loaiKH = daoLoaiKH.getLoaiKHTheoMaLoai(kh.getLoaiKH().getMaLoaiKH());
		String ngaySinh = "";
		String ngayDK = "";
		if (kh.getNgaySinh() != null)
			ngaySinh = dfNgaySinh.format(kh.getNgaySinh());
		if (kh.getNgayDangKy() != null)
			ngayDK = dfNgaySinh.format(kh.getNgayDangKy());

		modelKhachHang.addRow(new Object[] { kh.getMaKhangHang(), kh.getTenKH(), loaiKH.getTenLoaiKH(),
				kh.getGioiTinh(), ngaySinh, kh.getDiaChi(), kh.getSdt(), kh.getCccd(), ngayDK, kh.getDiemTichLuy() });
	}

	// load theo ten kh
	private void loadDanhSachMaVaSoKH(ArrayList<KhachHang> kh1) {
		clearTable();
		ArrayList<KhachHang> lstName = daoKhachHang.getMaVaSDTKH(txtTK.getText().toLowerCase().trim());
		for (KhachHang lskh : lstName) {
			LoaiKH loaiKH = daoLoaiKH.getLoaiKHTheoMaLoai(lskh.getLoaiKH().getMaLoaiKH());
			String ngaySinh = "";
			String ngayDK = "";
			if (lskh.getNgaySinh() != null)
				ngaySinh = dfNgaySinh.format(lskh.getNgaySinh());
			if (lskh.getNgayDangKy() != null)
				ngayDK = dfNgaySinh.format(lskh.getNgayDangKy());

			modelKhachHang.addRow(
					new Object[] { lskh.getMaKhangHang(), lskh.getTenKH(), loaiKH.getTenLoaiKH(), lskh.getGioiTinh(),
							ngaySinh, lskh.getDiaChi(), lskh.getSdt(), lskh.getCccd(), ngayDK, lskh.getDiemTichLuy() });
		}
	}

	private void loadDanhSachTenKH(ArrayList<KhachHang> kh1) {
		clearTable();
		ArrayList<KhachHang> lstName = daoKhachHang.getTenKH(txtTK.getText().toLowerCase().trim());
		for (KhachHang lskh : lstName) {
			LoaiKH loaiKH = daoLoaiKH.getLoaiKHTheoMaLoai(lskh.getLoaiKH().getMaLoaiKH());
			String ngaySinh = "";
			String ngayDK = "";
			if (lskh.getNgaySinh() != null)
				ngaySinh = dfNgaySinh.format(lskh.getNgaySinh());
			if (lskh.getNgayDangKy() != null)
				ngayDK = dfNgaySinh.format(lskh.getNgayDangKy());

			modelKhachHang.addRow(
					new Object[] { lskh.getMaKhangHang(), lskh.getTenKH(), loaiKH.getTenLoaiKH(), lskh.getGioiTinh(),
							ngaySinh, lskh.getDiaChi(), lskh.getSdt(), lskh.getCccd(), ngayDK, lskh.getDiemTichLuy() });
		}
	}

	private void loadDanhSachTenKHTheoLoai(ArrayList<KhachHang> kh2) {
		clearTable();
		String maLoai = daoLoaiKH.getMaLoaiKHTheoTen(txtTK.getText());

		ArrayList<KhachHang> lstName = daoKhachHang.getKHTheoLoai(maLoai);
		for (KhachHang lskh : lstName) {
			LoaiKH loaiKH = daoLoaiKH.getLoaiKHTheoMaLoai(lskh.getLoaiKH().getMaLoaiKH());
			String ngaySinh = "";
			String ngayDK = "";
			if (lskh.getNgaySinh() != null)
				ngaySinh = dfNgaySinh.format(lskh.getNgaySinh());
			if (lskh.getNgayDangKy() != null)
				ngayDK = dfNgaySinh.format(lskh.getNgayDangKy());

			modelKhachHang.addRow(
					new Object[] { lskh.getMaKhangHang(), lskh.getTenKH(), loaiKH.getTenLoaiKH(), lskh.getGioiTinh(),
							ngaySinh, lskh.getDiaChi(), lskh.getSdt(), lskh.getCccd(), ngayDK, lskh.getDiemTichLuy() });
		}
	}

	// Lam moi danh sach
	/**
	 * @return danh sách trống
	 */
	public void clearTable() {
		while (tableKH.getRowCount() > 0) {
			modelKhachHang.removeRow(0);
		}
	}

	// Nut them

	/**
	 * Thêm khách hàng vào danh sách
	 * 
	 */
	@SuppressWarnings("deprecation")
	public void themKHVaoDanhSach() {
		// int optThem = JOptionPane.showConfirmDialog(this, "Bạn có chắn chắn muốn thêm
		// khách hàng không?", "Thông báo", JOptionPane.YES_NO_OPTION );

		try {
			String maKH = daoMaKH.getMaKH();
			String tenKH = txtHoTen.getText().toString();
			String sdt = txtSDT.getText().toString();
			String diaChi = txtDiaChi.getText().toString();
			String cccd = txtCccd.getText().toString();
			String gioiTinh = cbogioiTinh.getSelectedItem().toString();
			LoaiKH loaiKH = new LoaiKH(daoLoaiKH.getMaLoaiKHTheoTen(cboloaiKH.getSelectedItem().toString()));

			int ngaySinh = dateChooserNgaySinh.getDate().getDate();
			int thangSinh = dateChooserNgaySinh.getDate().getMonth();
			int namSinh = dateChooserNgaySinh.getDate().getYear();

			int ngayDangKy = dateChooserNgayDangKy.getDate().getDate();
			int thangDangKy = dateChooserNgayDangKy.getDate().getMonth();
			int namDangKy = dateChooserNgayDangKy.getDate().getYear();
			int diemTichLuy = 0;
			int tuoi = nam - namSinh;

			if (regex.regexTen(txtHoTen) && regex.regexSDT(txtSDT) && regex.regexCCCD(txtCccd)
					&& regex.regexDiaChi(txtDiaChi)) {
				if (tuoi >= 13) {
					if (daoKhachHang.checkSdtKH(sdt) == false) {
						KhachHang kh = new KhachHang(maKH, tenKH, diaChi, sdt, cccd,
								new Date(namSinh, thangSinh, ngaySinh), gioiTinh, diemTichLuy,
								new Date(ngayDangKy, thangDangKy, namDangKy), loaiKH);
						daoKhachHang.themDanhSachKH(kh);
						loadThongTin(kh);
						resetAll();
						JOptionPane.showMessageDialog(this, "Thêm khách hàng thành công");
					} else {
						JOptionPane.showMessageDialog(this, "Số điện thoại đã đăng kí", "Thông báo",
								JOptionPane.ERROR_MESSAGE);
					}
				} else {
					JOptionPane.showMessageDialog(this, "Khách hàng chưa đủ 13 tuổi", "Thông báo",
							JOptionPane.ERROR_MESSAGE);
				}
			}

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin", "Thông báo",
					JOptionPane.WARNING_MESSAGE);
		}

	}

	// Nút sửa
	/**
	 * Sửa thông tin khách hàng đã được chọn trong danh sách hoặc tìm kiểm
	 */

	@SuppressWarnings("deprecation")
	public void suaThongTin() {
		int row = tableKH.getSelectedRow();
		if (row >= 0) {
			int update = JOptionPane.showConfirmDialog(this, "Bạn muốn sửa thông tin khách hàng  này?", "Thông báo",
					JOptionPane.YES_NO_OPTION);
			if (update == JOptionPane.YES_OPTION) {
				java.util.Date date = dateChooserNgaySinh.getDate();
				Date ngaySinh = new Date(date.getYear(), date.getMonth(), date.getDate());
				java.util.Date date1 = dateChooserNgayDangKy.getDate();
				Date ngayDangKy = new Date(date1.getYear(), date1.getMonth(), date1.getDate());
				int tuoi = nam - ngaySinh.getYear();
				// System.out.println(tuoi);
				String maKH = modelKhachHang.getValueAt(row, 0).toString();
				String tenKH = txtHoTen.getText().toString();
				String sdt = txtSDT.getText().toString();
				String diaChi = txtDiaChi.getText().toString();
				String cccd = txtCccd.getText().toString();
				String gioiTinh = cbogioiTinh.getSelectedItem().toString();
				LoaiKH loaiKH = new LoaiKH(daoLoaiKH.getMaLoaiKHTheoTen(cboloaiKH.getSelectedItem().toString()));

				int diemTichLuy = Integer.parseInt(txtPoint.getText().toString());
				if (regex.regexTen(txtHoTen) && regex.regexSDT(txtSDT) && regex.regexCCCD(txtCccd)
						&& regex.regexDiaChi(txtDiaChi)) {
					if (tuoi >= 13) {
						try {
							KhachHang kh = new KhachHang(maKH, tenKH, diaChi, sdt, cccd, ngaySinh, gioiTinh,
									diemTichLuy, ngayDangKy, loaiKH);
							daoKhachHang.suaThongTinKhachHang(kh);
							resetAll();
//								loadThongTin(kh);
							JOptionPane.showMessageDialog(this, "Thông tin khách hàng đã được sửa!", "Thông báo",
									JOptionPane.OK_OPTION);
						} catch (Exception e) {
							e.printStackTrace();
							JOptionPane.showMessageDialog(null, "Chỉnh sửa thông tin thất bại!", "Thông báo",
									JOptionPane.ERROR_MESSAGE);
						}

					} else {
						JOptionPane.showMessageDialog(null, "Khách hàng chưa đủ 13 tuổi!", "Thông báo",
								JOptionPane.ERROR_MESSAGE);
					}

				}
			}
		} else {
			JOptionPane.showMessageDialog(null, "Vui lòng chọn thông tin khách hàng cần sửa!", "Thông báo",
					JOptionPane.WARNING_MESSAGE);
		}
	}

	// Làm mới
	/**
	 * Làm mới lại tất cả txt và cbo trong frm
	 */

	public void resetAll() {
		txtTK.setText("Tìm khách hàng theo mã, tên, sđt và loại khách hàng.");
		txtTK.setFont(new Font("SansSerif", Font.ITALIC, 15));
		txtTK.setForeground(Colors.LightGray);

		txtHoTen.setText("");
		txtSDT.setText("");
		txtCccd.setText("");
		txtDiaChi.setText("");
		dateChooserNgaySinh.setDate(dNow);
		dateChooserNgayDangKy.setDate(dNow);
		txtPoint.setText("");
		loadDanhSachKH();
//		bg.clearSelection();
	}

	/**
	 * Xóa 1 khách hàng đã được chon trong danh sách hoặc tìm kiếm
	 * 
	 * @return
	 */

	// Xoa khach hang
	private boolean cancelKH() {
		int row = tableKH.getSelectedRow();
		if (row >= 0) {
			int cancel = JOptionPane.showConfirmDialog(null, "Bạn muốn xóa khách hàng này?", "Thông báo",
					JOptionPane.YES_NO_OPTION);
			if (cancel == JOptionPane.YES_OPTION) {
				String maKH = tableKH.getValueAt(row, 0).toString();
				try {
					modelKhachHang.removeRow(row);
					clearTable();
					daoKhachHang.huyKH(maKH);
					resetAll();
					JOptionPane.showMessageDialog(null, "Đã xóa khách hàng!", "Thông báo", JOptionPane.OK_OPTION);
				} catch (Exception e1) {
					e1.printStackTrace();
					JOptionPane.showMessageDialog(null, "xóa khách hàng thất bại!", "Thông báo",
							JOptionPane.ERROR_MESSAGE);
				}
			}
		} else {
			JOptionPane.showMessageDialog(null, "Bạn chưa chọn thông tin khách hàng cần hủy!", "Thông báo",
					JOptionPane.WARNING_MESSAGE);
		}
		return false;
	}

	// Tìm kiếm
	/**
	 * Giúp tìm kiếm khách hàng dựa vào mã, tên, số điện thoại, loại khách hàng
	 */
	private void findKH() {

		String input = txtTK.getText().trim();
		ArrayList<KhachHang> lstKH = null;
		String regexMaKH = "^((KH|kh)[0-9]{3})$";
		String regexTenKH = "^[ A-Za-za-zA-ZÀÁÂÃÈÉÊÌÍÒÓÔÕÙÚĂĐĨŨƠàáâãèéêìíòóôõùúăđĩũơƯĂẠẢẤẦẨẪẬẮẰẲẴẶẸẺẼỀỀỂẾưăạảấầẩẫậắằẳẵặẹẻẽềềểếỄỆỈỊỌỎỐỒỔỖỘỚỜỞỠỢỤỦỨỪễệỉịọỏốồổỗộớờởỡợụủứừỬỮỰỲỴÝỶỸửữựỳỵỷỹ]+$";
		String regexSDT = "^(0[0-9]{9})$";

		if (!txtTK.getText().equals("")
				&& !txtTK.getText().equals("Tìm khách hàng theo mã, tên, sđt và loại khách hàng.")) {
			if (regex.regexTimKH(txtTK)) {
				if (input.matches(regexMaKH)) {
					lstKH = daoKhachHang.getMaVaSDTKH(input);
					loadDanhSachMaVaSoKH(lstKH);
				}
				if (input.matches(regexSDT)) {
					lstKH = daoKhachHang.getMaVaSDTKH(input);
					loadDanhSachMaVaSoKH(lstKH);
				}
				if (input.matches(regexTenKH)) {
					lstKH = daoKhachHang.getTenKH(input);
					loadDanhSachTenKH(lstKH);
				}
				if (regex.regexTimKiemLoaiKH(txtTK)) {
					lstKH = daoKhachHang.getKHTheoLoai(daoLoaiKH.getMaLoaiKHTheoTen(input));
					loadDanhSachTenKHTheoLoai(lstKH);
				}
				if (lstKH.size() == 0) {
					JOptionPane.showMessageDialog(this, "Không tìm thấy thông tin tìm kiếm phù hợp!");
				}
			}
		} else {
			clearTable();
			JOptionPane.showMessageDialog(this, "Vui lòng nhập thông tin tìm kiếm!", "Thông báo",
					JOptionPane.WARNING_MESSAGE);
		}

	}

	@Override
	public void itemStateChanged(ItemEvent e) {
		// TODO Auto-generated method stub

	}

	@SuppressWarnings("unused")
	@Override
	// Hiển thị thông tin khi chọn vào bảng
	public void mouseClicked(MouseEvent e) {
		DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		// TODO Auto-generated method stub
		Object o = e.getSource();
		if (o.equals(tableKH)) {
			int row = tableKH.getSelectedRow();
			txtHoTen.setText(modelKhachHang.getValueAt(row, 1).toString());
			cboloaiKH.setSelectedItem(modelKhachHang.getValueAt(row, 2).toString());
			txtSDT.setText(modelKhachHang.getValueAt(row, 6).toString());
			if (modelKhachHang.getValueAt(row, 7) == null) {
				txtCccd.setText("");
			} else
				txtCccd.setText(modelKhachHang.getValueAt(row, 7).toString());
			txtDiaChi.setText(modelKhachHang.getValueAt(row, 5).toString());
			if (modelKhachHang.getValueAt(row, 3) == null) {
				cbogioiTinh.setSelectedIndex(-1);
			} else
				cbogioiTinh.setSelectedItem(modelKhachHang.getValueAt(row, 3).toString());
			txtPoint.setText(modelKhachHang.getValueAt(row, 9).toString());

			java.util.Date d = dNow;
			java.util.Date d1 = dNow;

			//

//			try {
//				if(!modelKhachHang.getValueAt(row, 4).equals("")) {
//					d = df.parse(modelKhachHang.getValueAt(row, 4).toString());
//				}
//				if(!modelKhachHang.getValueAt(row, 8).equals("")) {
//					d = df.parse(modelKhachHang.getValueAt(row, 8).toString());
//				}
//				
//				System.out.println(d);
//				dateChooserNgaySinh.setDate(d);
//				dateChooserNgayDangKy.setDate(d1);
//			} catch (ParseException e1) {
//				e1.printStackTrace();
//			}
			String maKH = (String) modelKhachHang.getValueAt(row, 0);
			ArrayList<KhachHang> lstKH = daoKhachHang.getDanhSachKH();
			for (KhachHang kh : lstKH) {
				if (maKH.equals(kh.getMaKhangHang())) {
//					txt.setText(maNV);
//					txtHoTen.setText(nv.getTenNhanVien());
//					txtSDT.setText(nv.getSdt());
//					txtDiaChi.setText(nv.getDiaChi());
//					cboChucVu.setSelectedItem(nv.getChucVu());
//					txtCccd.setText(nv.getCccd());
//					cboGioiTinh.setSelectedItem(nv.getGioiTinh());
//					chooserNgaySinh.setDate(nv.getNgaySinh());
//					break;
//				}
					dateChooserNgaySinh.setDate(kh.getNgaySinh());
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

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		Object o = e.getSource();
		if (o.equals(btnThemKH)) {
			themKHVaoDanhSach();
		}
		if (o.equals(btnSuaKH)) {
			suaThongTin();
		}
		if (o.equals(btnReset)) {
			clearTable();
			resetAll();
		}
		if (o.equals(btnXoaKH)) {
			cancelKH();
		}
		if (o.equals(btnTim)) {
			findKH();
		}

	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyPressed(KeyEvent e) {
		Object o = e.getSource();
		int key = e.getKeyCode();
		if (o.equals(txtHoTen) && key == KeyEvent.VK_ENTER) {
			txtSDT.requestFocus();
		} else if (o.equals(txtSDT) && key == KeyEvent.VK_ENTER) {
			txtDiaChi.requestFocus();
		} else if (o.equals(txtDiaChi) && key == KeyEvent.VK_ENTER) {
			txtCccd.requestFocus();
		} else if (o.equals(txtCccd) && key == KeyEvent.VK_ENTER) {
			btnThemKH.requestFocus();
		} else if (o.equals(txtTK) && key == KeyEvent.VK_ENTER) {
			btnTim.doClick();
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void focusGained(FocusEvent e) {
		if (txtTK.getText().equals("Tìm khách hàng theo mã, tên, sđt và loại khách hàng.")) {
			txtTK.setText("");
			txtTK.setFont(new Font("SansSerif", Font.PLAIN, 15));
			txtTK.setForeground(Color.BLACK);
		}
	}

	@Override
	public void focusLost(FocusEvent e) {
		if (txtTK.getText().equals("")) {
			txtTK.setFont(new Font("SansSerif", Font.ITALIC, 15));
			txtTK.setText("Tìm khách hàng theo mã, tên, sđt và loại khách hàng.");
			txtTK.setForeground(Colors.LightGray);
		}
	}
}
