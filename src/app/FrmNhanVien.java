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
	 * K?????? th??????a t????n v???? m???? nh????n vi????n, ng????y hi???????n t??????i c??????a FrmQuanLy
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
		 * k???t n???i database
		 */
		try {
			ConnectDB.getinstance().connect();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}

		/**
		 * Khai b??o DAO
		 */
		daoNhanVien = new DAONhanVien();
		daoPhatSinhMa = new DAOPhatSinhMa();
		daoTaiKhoan = new DAOTaiKhoan();
		regex = new Regex();

		/**
		 * Khai b??o ng??y hi???n tai
		 */
		dfNgaySinh = new SimpleDateFormat("dd/MM/yyyy");
		dfLuong = new DecimalFormat("##,###,###");

		/**
		 * Khai b????o entity NhanVien
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
		 * Nh???p th??ng tin nh??n vi??n Panel pNhapThongTin
		 */
		pNhapThongTin = new JPanel();
		pNhapThongTin.setBorder(new LineBorder(new Color(114, 23, 153)));
		pNhapThongTin.setBackground(Color.WHITE);
		pNhapThongTin.setBounds(2, 56, 1258, 238);
		pNhapThongTin.setLayout(null);
		pNhapThongTin.setToolTipText("C??c th??ng tin nh??n vi??n c???n nh???p");
		pMain.add(pNhapThongTin);

		lblNhapThongTin = new JLabel("Nh???p th??ng tin nh??n vi??n");
		lblNhapThongTin.setHorizontalAlignment(SwingConstants.CENTER);
		lblNhapThongTin.setFont(new Font("SansSerif", Font.BOLD, 18));
		lblNhapThongTin.setBounds(10, 10, 239, 29);
		pNhapThongTin.add(lblNhapThongTin);

		JLabel lblHoTen = new JLabel("H??? v?? t??n:");
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
		txtHoTen.setText("Nguy???n V??n Trinh");

		JLabel lblSDT = new JLabel("S??T:");
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

		JLabel lblDiaChi = new JLabel("?????a ch???:");
		lblDiaChi.setBounds(10, 112, 72, 20);
		lblDiaChi.setFont(new Font("SansSerif", Font.BOLD, 15));
		pNhapThongTin.add(lblDiaChi);
		txtDiaChi = new JTextField();
		txtDiaChi.setBounds(110, 108, 454, 28);
		txtDiaChi.setFont(new Font("SansSerif", Font.PLAIN, 15));
		txtDiaChi.setBorder(new LineBorder(new Color(114, 23, 153), 1, true));
		pNhapThongTin.add(txtDiaChi);
		txtDiaChi.setText("168 ???????ng s??? 17 ph?????ng 11 g?? v???p");

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

		JLabel lblGioiTinh = new JLabel("Gi???i t??nh:");
		lblGioiTinh.setBounds(596, 154, 88, 23);
		lblGioiTinh.setFont(new Font("SansSerif", Font.BOLD, 15));
		pNhapThongTin.add(lblGioiTinh);
		cboGioiTinh = new JComboBox<Object>(new Object[] { "Nam", "N???" });
		cboGioiTinh.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		cboGioiTinh.setBounds(675, 153, 191, 25);
		cboGioiTinh.setFont(new Font("SansSerif", Font.PLAIN, 15));
		cboGioiTinh.setBackground(Color.white);
		cboGioiTinh.setBorder(new LineBorder(new Color(114, 23, 153), 1, true));
		cboGioiTinh.setToolTipText("Ch???n gi???i t??nh");
		pNhapThongTin.add(cboGioiTinh);

		now = LocalDate.now();
		ngay = now.getDayOfMonth();
		thang = now.getMonthValue() - 1;
		nam = now.getYear() - 1900;
		dNow = new Date(nam, thang, ngay);

		JLabel lblNgaySinh = new JLabel("Ng??y sinh:");
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
		chooserNgaySinh.getCalendarButton().setToolTipText("Ch???n ng??y sinh");
		Icon iconCalendar = IconFontSwing.buildIcon(FontAwesome.CALENDAR, 17, Color.white);
		chooserNgaySinh.setIcon((ImageIcon) iconCalendar);
		pNhapThongTin.add(chooserNgaySinh);

		JLabel lblChucVu = new JLabel("Ch???c v???:");
		lblChucVu.setBounds(10, 159, 98, 19);
		lblChucVu.setFont(new Font("SansSerif", Font.BOLD, 15));
		pNhapThongTin.add(lblChucVu);
		cboChucVu = new JComboBox<Object>(new Object[] { "Qu???n l??", "Nh??n vi??n" });
		cboChucVu.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		cboChucVu.setBounds(110, 156, 191, 25);
		cboChucVu.setFont(new Font("SansSerif", Font.PLAIN, 15));
		cboChucVu.setBackground(Color.white);
		cboChucVu.setBorder(new LineBorder(new Color(114, 23, 153), 1, true));
		cboChucVu.setToolTipText("Ch???n Ch???c v??? nh??n vi??n");
		pNhapThongTin.add(cboChucVu);

		JLabel lblTim = new JLabel("T??m ki???m:");
		lblTim.setFont(new Font("SansSerif", Font.BOLD, 14));
		lblTim.setBounds(332, 11, 90, 35);
		pMain.add(lblTim);
		//
		txtTim = new JTextField();
		txtTim.setText("T??m nh??n vi??n theo m?? nh??n vi??n");
		txtTim.setFont(new Font("SansSerif", Font.ITALIC, 15));
		txtTim.setForeground(Colors.LightGray);
		txtTim.setBorder(new LineBorder(new Color(114, 23, 153), 2, true));
		txtTim.setBounds(414, 11, 540, 33);
		txtTim.setToolTipText("T??m ki???m th??ng tin nh??n vi??n");
		pMain.add(txtTim);
		//
		btnTim = new FixButton("T??m");
		btnTim.setForeground(Color.WHITE);
		btnTim.setFont(new Font("SansSerif", Font.BOLD, 14));
		btnTim.setBorder(new LineBorder(new Color(0, 146, 182), 2, true));
		btnTim.setBackground(new Color(114, 23, 153));
		btnTim.setBounds(964, 12, 127, 33);

		pMain.add(btnTim);

		/**
		 * Th??m 1 nh??n vi??n v??o danh s??ch b???ng Nh??n Vi??n N??t Th??m nh??n vi??n JButton
		 * btnThemNV Icon iconThemNV
		 */
		btnThemNV = new FixButton("Th??m");
		btnThemNV.setForeground(Color.black);
		btnThemNV.setFont(new Font("SansSerif", Font.BOLD, 14));
		btnThemNV.setBorder(new LineBorder(new Color(0, 146, 182), 2, true));
		btnThemNV.setBackground(new Color(57, 210, 247));
		btnThemNV.setBounds(234, 191, 145, 38);

		pNhapThongTin.add(btnThemNV);

		/**
		 * S???a th??ng tin nh??n vi??n N??t s???a th??ng tin nh??n vi??n JButton btnSuaNV Icon
		 * iconSuaNV
		 */
		btnSuaNV = new FixButton("S???a");
		btnSuaNV.setForeground(Color.black);
		btnSuaNV.setFont(new Font("SansSerif", Font.BOLD, 14));
		btnSuaNV.setBorder(new LineBorder(new Color(0, 146, 182), 2, true));
		btnSuaNV.setBackground(new Color(133, 217, 191));
		btnSuaNV.setBounds(442, 189, 145, 40);

		pNhapThongTin.add(btnSuaNV);

		/**
		 * L??m m???i: xo??? tr???ng c??c text, x??a t???t c??? n???i dung trong b???ng, ????t m???c ?????nh c??c
		 * combobox, b??? ch???n checkbox v?? c??c radiobutton N??t l??m m???i JButton btnH???yNV
		 * Icon iconThemNV
		 */
		btnLamMoiNV = new FixButton("L??m m???i");
		btnLamMoiNV.setText("L??m m???i");
		btnLamMoiNV.setForeground(Color.WHITE);
		btnLamMoiNV.setFont(new Font("SansSerif", Font.BOLD, 14));
		btnLamMoiNV.setBorder(new LineBorder(new Color(0, 146, 182), 2, true));
		btnLamMoiNV.setBackground(new Color(114, 23, 153));
		btnLamMoiNV.setBounds(675, 192, 145, 37);
		pNhapThongTin.add(btnLamMoiNV);
		Icon iconHuyNV = IconFontSwing.buildIcon(FontAwesome.TIMES_CIRCLE, 20, Color.white);
		/**
		 * Nh??m c??c radiobutton ButtonGroup bg
		 */
		bg = new ButtonGroup();
		bg.add(btnThemNV);
		bg.add(btnSuaNV);
		bg.add(btnLamMoiNV);

		JLabel lblMaNV = new JLabel("M?? NV:");
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

		btnHuy = new FixButton("H???y s???a");
		btnHuy.setText("H???y s???a");
		btnHuy.setForeground(Color.BLACK);
		btnHuy.setFont(new Font("SansSerif", Font.BOLD, 14));
		btnHuy.setBorder(new LineBorder(new Color(0, 146, 182), 2, true));
		btnHuy.setBackground(Color.RED);
		btnHuy.setBounds(868, 190, 145, 40);
		pNhapThongTin.add(btnHuy);

		btnXoa = new FixButton("X??a");
		btnXoa.setText("X??a");
		btnXoa.setForeground(Color.BLACK);
		btnXoa.setFont(new Font("SansSerif", Font.BOLD, 14));
		btnXoa.setBorder(new LineBorder(new Color(0, 146, 182), 2, true));
		btnXoa.setBackground(new Color(210, 180, 140));
		btnXoa.setBounds(675, 190, 145, 40);
		pNhapThongTin.add(btnXoa);

		checkEdit();

		/**
		 * B???ng ch???a c??c th??ng tin nh??n vi??n JScrollPane scrollPaneNV String col[]: t??n
		 * c??c c???t JTable tblNV: n???i dung c???a b???ng JTableHeader tbHeader: ?????nh d???ng
		 * khung c??c t??n c???t
		 */
		JScrollPane scrollPaneNV = new JScrollPane(tblNV, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		scrollPaneNV.setBorder(new LineBorder(new Color(164, 44, 167), 1, true));
		scrollPaneNV.setBackground(new Color(164, 44, 167));
		scrollPaneNV.setBounds(2, 304, 1258, 295);
		scrollPaneNV.getHorizontalScrollBar();
		scrollPaneNV.setToolTipText("Danh s??ch th??ng tin nh??n vi??n");
		pMain.add(scrollPaneNV);

		String col[] = { "M?? NV", "H??? v?? t??n Nh??n vi??n", "Ch???c v???", "Gi???i t??nh", "Ng??y sinh", "?????a ch???", "S??T", "CCCD",
				"L????ng", "M???t kh???u" };
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
		tblNV.setToolTipText("Ch???n nh??n vi??n ????? th???c hi???n ch???c n??ng");

		JTableHeader tbHeader = tblNV.getTableHeader();
		tbHeader.setBackground(new Color(164, 44, 167));
		tbHeader.setForeground(Color.white);
		tbHeader.setFont(new Font("SansSerif", Font.BOLD, 14));
		tbHeader.setToolTipText("Danh s??ch th??ng tin nh??n vi??n");

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
		 * C????c s?????? ki???????n c??????a giao di???????n qu??????n l???? nh????n vi????n
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
	 * X??a h???t d??? li???u trong b???ng danh s??ch th??ng tin nh??n vi??n
	 * 
	 * @param defaultTableModel tr??? v??? modelNV
	 */
	private void removeDanhSachNV(DefaultTableModel defaultTableModel) {
		while (tblNV.getRowCount() > 0) {
			modelNV.removeRow(0);
		}
	}

	/**
	 * X??a tr???ng textfield v?? textarea, ?????t l???i m???c ?????nh c??c combobox v?? c??c button
	 */
	private void resetAll() {
		txtTim.setText("T??m nh??n vi??n theo m?? nh??n vi??n");
		txtTim.setFont(new Font("SansSerif", Font.ITALIC, 15));
		txtTim.setForeground(Colors.LightGray);

		txtHoTen.setText("");
		txtMaNV.setText("");
		txtSDT.setText("");
		txtDiaChi.setText("");
		txtCccd.setText("");

		cboChucVu.setSelectedItem("Qu???n l??");
		cboGioiTinh.setSelectedItem("Nam");
		chooserNgaySinh.setDate(dNow);

//		rdoTheoMaNV.setSelected(false);
//		rdoTheoTenNV.setSelected(false);
//		rdoTheoChucVuNV.setSelected(false);

		txtHoTen.requestFocus();
	}

	/**
	 * Hi???n danh s??ch th??ng tin nh??n vi??n ??ang l??m vi???c t??? m?? NV g???m: m?? NV, t??n NV,
	 * ch???c v???, gi???i t??nh, ng??y sinh, ?????a ch???, s??t, cccd, l????ng, ca l??m vi???c, tr???ng
	 * th??i l??m vi???c, m???t kh???u
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
	 * Hi???n danh s??ch th??ng tin NV theo t??n nh??n vi??n * @param lstNV
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
	 * Hi???????n danh s????ch th????ng tin NV theo t????n nh????n vi????n
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
	 * S?????? ki???????n t????m ki??????m th????ng tin nh????n vi????n
	 */
	private void findNV() {
		String input = txtTim.getText().trim();
		input = input.toUpperCase();
		ArrayList<NhanVien> lstNV = null;

		String regexMaNV = "^((NV|nv)[0-9]{3})$";
		String regexTenNV = "^[ A-Za-za-zA-Z??????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????]+$";
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
				JOptionPane.showMessageDialog(this, "Kh??ng t??m th???y th??ng tin nh??n vi??n", "Th??ng b??o",
						JOptionPane.ERROR_MESSAGE);
				txtTim.requestFocus();
				txtTim.selectAll();
			}
		}
	}

	/**
	 * T???o t??i kho???n nh??n vi??n m???i
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
					JOptionPane.showMessageDialog(this, "Vui l??ng nh???p th??ng tin ?????y ?????!", "Th??ng b??o",
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
									if (cboChucVu.getSelectedItem() == "Qu???n l??")
										nv.setLuong(10000000);
									if (cboChucVu.getSelectedItem() == "Nh??n Vi??n")
										nv.setLuong(6000000);
									try {
										daoNhanVien.themNV(nv);
									} catch (SQLException e) {
										e.printStackTrace();
										JOptionPane.showMessageDialog(this, "Th??m nh??n vi??n th???t b???i!", "Th??ng b??o",
												JOptionPane.ERROR_MESSAGE);
									}

									// them vao table
									resetAll();
									modelNV.addRow(new Object[] { maNV, hoTen, chucVu, gioiTinh,
											dfNgaySinh.format(chooserNgaySinh.getDate()), diaChi, sdt, cccd,
											dfLuong.format(Math.round(nv.getLuong())), matKhau });
									String mkTK = "\nM???t kh???u: " + matKhau;
									JOptionPane.showMessageDialog(this,
											"Th??m th??nh c??ng!\nM?? t??i kho???n: " + maNV + mkTK, "Th????ng b????o",
											JOptionPane.INFORMATION_MESSAGE);
								} else
									JOptionPane.showMessageDialog(this, "Nh??n vi??n l??m vi???c ph???i tr??n 18 tu???i!",
											"Th??ng b??o", JOptionPane.WARNING_MESSAGE);
							} else
								JOptionPane.showMessageDialog(this, "C??n c?????c c??ng d??n ???? ????ng k??", "Th??ng b??o",
										JOptionPane.ERROR_MESSAGE);
						} else
							JOptionPane.showMessageDialog(this, "S??? ??i???n tho???i ???? ????ng k??", "Th??ng b??o",
									JOptionPane.ERROR_MESSAGE);
					}
				}
			} else {
				JOptionPane.showMessageDialog(this, "M?? nh??n vi??n ???? t???n t???i!", "Th??ng b??o", JOptionPane.ERROR_MESSAGE);
			}

		} else {
			JOptionPane.showMessageDialog(this, "M?? nh??n vi??n c?? ?????nh d???ng NV00x!", "Th??ng b??o",
					JOptionPane.ERROR_MESSAGE);
		}

	}

	@SuppressWarnings("deprecation")
	private void XoaNV() {
		int row = tblNV.getSelectedRow();
		if (row >= 0) {
			int delete = JOptionPane.showConfirmDialog(this, "B???n mu???n x??a nh??n vi??n n??y?", "Th??ng b??o",
					JOptionPane.YES_NO_OPTION);
			if (delete == JOptionPane.YES_OPTION) {
				String maNV = (String) tblNV.getValueAt(row, 0);
				try {

					daoNhanVien.xoaNV(maNV);
//						daoTaiKhoan.xoaTK(maNV);
					removeDanhSachNV(modelNV);
					JOptionPane.showMessageDialog(this, "X??a nh??n vi??n th??nh c??ng!", "Th??ng b??o",
							JOptionPane.INFORMATION_MESSAGE);

				} catch (SQLException e) {
					e.printStackTrace();
					JOptionPane.showMessageDialog(null, "X??a nh??n vi??n th???t b???i!", "Th??ng b??o",
							JOptionPane.ERROR_MESSAGE);
				}
			}
		} else
			JOptionPane.showMessageDialog(null, "Vui l??ng ch???n th??ng tin nh??n vi??n c???n s???a!", "Th??ng b??o",
					JOptionPane.WARNING_MESSAGE);

		loadDanhSachNV();

	}

	@SuppressWarnings("deprecation")
	private void updateNV() {
		int row = tblNV.getSelectedRow();
		if (row >= 0) {
			int update = JOptionPane.showConfirmDialog(this, "B???n mu???n s???a th??ng tin nh??n vi??n n??y?", "Th??ng b??o",
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
						if (cboChucVu.getSelectedItem() == "Qu???n l??")
							nv.setLuong(10000000);
						if (cboChucVu.getSelectedItem() == "Thu ng??n")
							nv.setLuong(6000000);

						new DAONhanVien().capNhatNV(nv, maNV);
						TaiKhoan tk = daoTaiKhoan.getMatKhauTheoMaNV(maNV);

						removeDanhSachNV(modelNV);
						modelNV.setRowCount(0);
						modelNV.addRow(new Object[] { maNV, nv.getTenNhanVien(), nv.getChucVu(), nv.getGioiTinh(),
								dfNgaySinh.format(nv.getNgaySinh()), nv.getDiaChi(), nv.getSdt(), nv.getCccd(),
								dfLuong.format(Math.round(nv.getLuong())), tk.getMatKhau() });
						JOptionPane.showMessageDialog(this, "Th??ng tin nh??n vi??n ???? ???????c s???a!", "Th??ng b??o",
								JOptionPane.INFORMATION_MESSAGE);
					}
				} catch (SQLException e) {
					e.printStackTrace();
					JOptionPane.showMessageDialog(null, "Ch???nh s???a th??ng tin th???t b???i!", "Th??ng b??o",
							JOptionPane.ERROR_MESSAGE);
				}
			}
		} else
			JOptionPane.showMessageDialog(null, "Vui l??ng ch???n th??ng tin nh??n vi??n c???n s???a!", "Th??ng b??o",
					JOptionPane.WARNING_MESSAGE);

		loadDanhSachNV();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object o = e.getSource();

		// t??m NV
		if (o.equals(btnTim)) {
			if (txtTim.getText().equals("")
					|| txtTim.getText().equals("T??m nh??n vi??n theo m?? nh??n vi??n, t??n nh??n vi??n, s??t.")) {
				removeDanhSachNV(modelNV);
				JOptionPane.showMessageDialog(this, "Vui l??ng nh???p th??ng tin t??m ki???m!", "Th??ng b??o",
						JOptionPane.WARNING_MESSAGE);
				txtTim.requestFocus();
			} else {
				findNV();
				txtTim.selectAll();
			}
		}

		// th??m NV
		if (o.equals(btnThemNV)) {
			addNV();
		}

//s???a nh??n vien
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
	 * S?????? ki???????n c??????a click chu???????t
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
	 * S?????? ki???????n placeholder c??????a txtTim
	 */
	@Override
	public void focusGained(FocusEvent e) {
		if (txtTim.getText().equals("T??m nh??n vi??n theo m?? nh??n vi??n, t??n nh??n vi??n, s??t.")) {
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
			txtTim.setText("T??m nh??n vi??n theo m?? nh??n vi??n, t??n nh??n vi??n, s??t.");
		}
	}
}
