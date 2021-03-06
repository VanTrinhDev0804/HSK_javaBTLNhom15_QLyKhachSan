package app;

import java.awt.Color;
import java.awt.Cursor;

import java.awt.Font;
import java.awt.Image;
import java.awt.Panel;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import java.sql.Date;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.ArrayList;

import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.LineBorder;

import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

import com.mindfusion.drawing.Colors;

import connection.ConnectDB;
import dao.DAOLoaiMH;
import dao.DAOMatHang;
import dao.DAOPhatSinhMa;
import dao.Regex;
import entity.LoaiMatHang;
import entity.MatHang;



import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JComboBox;
import javax.swing.JFrame;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;

public class FrmMatHang extends JFrame implements ActionListener, MouseListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Panel pMain;
	private JTextField txtSoLuong;
	private JTextField txtTenMH;
	private JTextField txtDonGia;
	private JTable tblMH;
	private DefaultTableModel modelMatHang;
	private FixButton btnTim;
	private JTextField txtTim;
	private FixButton btnThemMH;
	private FixButton btnSuaMH;
	private FixButton btnXoaMH;
	private FixButton btnReset;
	private DAOMatHang daoMH;
	private DAOLoaiMH daoLMH;
	private JComboBox<String> cboLoaiMH;
	private DAOPhatSinhMa daoPhatSinhMa;
	private ArrayList<LoaiMatHang> loaiMH;
	private DecimalFormat dfVND;

	private Regex regex;
	private JPanel pNhapThongTin;
	private JLabel lblNhapThongTin;

	public Panel getFrmMatHang() {
		return this.pMain;
	}

	public FrmMatHang(String sHeaderTenNV, String sHeaderMaNV, Date dNgayHienTai) {
		/**
		 * pMain
		 */
		getContentPane().setLayout(null);
		pMain = new Panel();
		pMain.setBackground(Color.WHITE);
		pMain.setBounds(0, 0, 1281, 629);
		getContentPane().add(pMain);
		pMain.setLayout(null);

		/**
		 * DAO
		 */
		regex = new Regex();
		daoMH = new DAOMatHang();
		daoLMH = new DAOLoaiMH();
		daoPhatSinhMa = new DAOPhatSinhMa();

		/**
		 * K???t n???i SQL
		 */
		try {
			ConnectDB.getinstance().connect();
		} catch (Exception e) {
			e.printStackTrace();
		}

		/**
		 * panel Nh???p th??ng tin
		 */
		pNhapThongTin = new JPanel();
		pNhapThongTin.setBorder(new LineBorder(new Color(114, 23, 153)));
		pNhapThongTin.setBackground(Color.WHITE);
		pNhapThongTin.setBounds(10, 72, 1249, 165);
		pMain.add(pNhapThongTin);
		pNhapThongTin.setLayout(null);
		pNhapThongTin.setToolTipText("Th??ng tin m???t h??ng");

		JLabel lblTenMH = new JLabel("T??n m???t h??ng: ");
		lblTenMH.setBounds(10, 49, 102, 36);
		pNhapThongTin.add(lblTenMH);
		lblTenMH.setFont(new Font("SansSerif", Font.PLAIN, 15));

		txtTenMH = new JTextField();
		txtTenMH.setBounds(122, 50, 201, 37);
		pNhapThongTin.add(txtTenMH);
		txtTenMH.setBackground(new Color(255, 255, 255));
		txtTenMH.setFont(new Font("arial", Font.PLAIN, 14));
		txtTenMH.setBorder(new LineBorder(new Color(114, 23, 153), 1, true));
		txtTenMH.setColumns(30);

		JLabel lblDonGia = new JLabel("Gi?? b??n:");
		lblDonGia.setBounds(356, 49, 102, 36);
		pNhapThongTin.add(lblDonGia);
		lblDonGia.setFont(new Font("SansSerif", Font.PLAIN, 15));

		txtDonGia = new JTextField();
		txtDonGia.setBounds(425, 49, 201, 37);
		pNhapThongTin.add(txtDonGia);
		txtDonGia.setBackground(new Color(255, 255, 255));
		txtDonGia.setFont(new Font("SansSerif", Font.PLAIN, 14));
		txtDonGia.setBorder(new LineBorder(new Color(114, 23, 153), 1, true));
		txtDonGia.setColumns(20);

		lblNhapThongTin = new JLabel("Nh???p th??ng tin m???t h??ng");
		lblNhapThongTin.setHorizontalAlignment(SwingConstants.CENTER);
		lblNhapThongTin.setFont(new Font("SansSerif", Font.BOLD, 18));
		lblNhapThongTin.setBounds(-14, 10, 262, 29);
		pNhapThongTin.add(lblNhapThongTin);

		JLabel lblSoluongMH = new JLabel("S??? l?????ng:");
		lblSoluongMH.setBounds(646, 49, 84, 36);
		pNhapThongTin.add(lblSoluongMH);
		lblSoluongMH.setFont(new Font("SansSerif", Font.PLAIN, 15));

		txtSoLuong = new JTextField();
		txtSoLuong.setBounds(720, 49, 201, 37);
		pNhapThongTin.add(txtSoLuong);
		txtSoLuong.setBackground(new Color(255, 255, 255));
		txtSoLuong.setFont(new Font("SansSerif", Font.PLAIN, 14));
		txtSoLuong.setBorder(new LineBorder(new Color(114, 23, 153), 1, true));
		txtSoLuong.setColumns(20);

		JLabel lblSubLMH = new JLabel("Lo???i m???t h??ng: ");
		lblSubLMH.setBounds(938, 50, 102, 35);
		pNhapThongTin.add(lblSubLMH);
		lblSubLMH.setFont(new Font("SansSerif", Font.PLAIN, 15));

		cboLoaiMH = new JComboBox<String>();
		cboLoaiMH.setBounds(1038, 49, 201, 37);
		cboLoaiMH.setBorder(new LineBorder(new Color(114, 23, 153), 1, true));
		pNhapThongTin.add(cboLoaiMH);
		cboLoaiMH.setFont(new Font("SansSerif", Font.PLAIN, 15));
		cboLoaiMH.setBackground(Color.WHITE);

		/**
		 * T??m ki???m
		 */
		JLabel lblTim = new JLabel("T??m ki???m:");
		lblTim.setFont(new Font("SansSerif", Font.BOLD, 14));
		lblTim.setBounds(350, 11, 90, 35);
		pMain.add(lblTim);

		txtTim = new JTextField();
		txtTim.setToolTipText("Th??ng tin t??m ki???m");
		txtTim.setText("T??m m???t h??ng theo t??n m???t h??ng, lo???i m???t h??ng");
		txtTim.setFont(new Font("SansSerif", Font.ITALIC, 15));
		txtTim.setForeground(Colors.LightGray);
		txtTim.setBorder(new LineBorder(new Color(114, 23, 153), 2, true));
		txtTim.setBounds(425, 11, 529, 33);
		txtTim.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				if (txtTim.getText().equals("T??m m???t h??ng theo t??n m???t h??ng, lo???i m???t h??ng")) {
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
					txtTim.setText("T??m m???t h??ng theo t??n m???t h??ng, lo???i m???t h??ng");
				}
			}
		});
		pMain.add(txtTim);

		btnTim = new FixButton("T??m");
		btnTim.setToolTipText("T??m ki???m m???t h??ng");
		btnTim.setForeground(Color.WHITE);
		btnTim.setFont(new Font("SansSerif", Font.BOLD, 14));
		btnTim.setBorder(new LineBorder(new Color(0, 146, 182), 2, true));
		btnTim.setBackground(new Color(114, 23, 153));
		btnTim.setBounds(964, 12, 127, 33);

		pMain.add(btnTim);

		/**
		 * C??c buttons
		 */
		btnThemMH = new FixButton("Th??m");
		btnThemMH.setForeground(Color.WHITE);
		btnThemMH.setFont(new Font("SansSerif", Font.BOLD, 14));
		btnThemMH.setBackground(new Color(57, 210, 247));
		btnThemMH.setBounds(200, 112, 145, 38);

		btnThemMH.setToolTipText("Th??m m???t m???t h??ng");
		pNhapThongTin.add(btnThemMH);

		btnSuaMH = new FixButton("S???a");
		btnSuaMH.setForeground(Color.WHITE);
		btnSuaMH.setFont(new Font("SansSerif", Font.BOLD, 14));
		btnSuaMH.setBackground(new Color(133, 217, 191));
		btnSuaMH.setBounds(400, 112, 145, 38);
		btnSuaMH.setToolTipText("S???a th??ng th??ng m???t m???t h??ng");

		pNhapThongTin.add(btnSuaMH);

		btnXoaMH = new FixButton("X??a");
		btnXoaMH.setForeground(Color.WHITE);
		btnXoaMH.setFont(new Font("SansSerif", Font.BOLD, 14));
		btnXoaMH.setBackground(new Color(0xE91940));
		btnXoaMH.setBounds(600, 112, 145, 38);

		btnXoaMH.setToolTipText("X??a m???t m???t h??ng");
		pNhapThongTin.add(btnXoaMH);

		btnReset = new FixButton("L??m m???i");
		btnReset.setForeground(Color.WHITE);
		btnReset.setFont(new Font("SansSerif", Font.BOLD, 14));
		btnReset.setBackground(new Color(114, 23, 153));
		btnReset.setBounds(800, 112, 145, 38);

		btnReset.setToolTipText("L??m m???i to??n b??? ch????ng tr??nh");
		pNhapThongTin.add(btnReset);

		/**
		 * B???ng ch??nh
		 */
		String mh[] = { "M?? MH", "T??n m???t h??ng", "Lo???i MH", "S??? l?????ng", "Gi?? b??n" };
		modelMatHang = new DefaultTableModel(mh, 0);

		tblMH = new JTable(modelMatHang);
		tblMH.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		tblMH.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		tblMH.setShowHorizontalLines(true);
		tblMH.setShowGrid(true);
		tblMH.setBackground(Color.WHITE);
		tblMH.setFont(new Font("SansSerif", Font.PLAIN, 13));
		tblMH.setSelectionBackground(new Color(164, 44, 167, 30));
		tblMH.setSelectionForeground(new Color(114, 23, 153));
		tblMH.setRowHeight(30);
		tblMH.setSelectionBackground(new Color(164, 44, 167, 30));
		tblMH.setToolTipText("B???ng m???t h??ng");

		JTableHeader tbHeader = tblMH.getTableHeader();
		tbHeader.setBackground(new Color(164, 44, 167));
		tbHeader.setForeground(Color.white);
		tbHeader.setFont(new Font("SansSerif", Font.BOLD, 14));

		JScrollPane spMatHang = new JScrollPane(tblMH, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		spMatHang.setBounds(10, 247, 1249, 361);
		spMatHang.setBorder(new LineBorder(new Color(164, 44, 167), 1, true));
		spMatHang.setBackground(new Color(164, 44, 167));
		spMatHang.getHorizontalScrollBar();
		pMain.add(spMatHang);

		tblMH.getColumnModel().getColumn(0).setPreferredWidth(240);
		tblMH.getColumnModel().getColumn(1).setPreferredWidth(240);
		tblMH.getColumnModel().getColumn(2).setPreferredWidth(240);
		tblMH.getColumnModel().getColumn(3).setPreferredWidth(240);
		tblMH.getColumnModel().getColumn(4).setPreferredWidth(230);

		DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
		rightRenderer.setHorizontalAlignment(JLabel.RIGHT);
		DefaultTableCellRenderer leftRenderer = new DefaultTableCellRenderer();
		leftRenderer.setHorizontalAlignment(JLabel.LEFT);
		tblMH.getColumnModel().getColumn(0).setCellRenderer(leftRenderer);
		tblMH.getColumnModel().getColumn(1).setCellRenderer(leftRenderer);
		tblMH.getColumnModel().getColumn(2).setCellRenderer(leftRenderer);
		tblMH.getColumnModel().getColumn(3).setCellRenderer(rightRenderer);
		tblMH.getColumnModel().getColumn(4).setCellRenderer(rightRenderer);
		spMatHang.setViewportView(tblMH);

		/**
		 * Ph??ng n???n - background
		 */
		JLabel lblBackGround = new JLabel("");
		lblBackGround.setIcon(new ImageIcon("data\\img\\background.png"));
		lblBackGround.setBounds(0, 0, 1281, 629);
		Image imgBackGround = Toolkit.getDefaultToolkit().getImage("data\\img\\background.png");
		Image resizeBG = imgBackGround.getScaledInstance(lblBackGround.getWidth(), lblBackGround.getHeight(), 0);
		lblBackGround.setIcon(new ImageIcon(resizeBG));
		pMain.add(lblBackGround);

		/**
		 * Load lo???i m???t h??ng v??o ComboBox lo???i m???t h??ng
		 */
		loaiMH = daoLMH.getAllLoaiMatHang();
		for (LoaiMatHang lmh : loaiMH) {
			cboLoaiMH.addItem(lmh.getTenLoaiMatHang());
		}
		/**
		 * S??? ki???n buttons, chu???t, key
		 */
		btnThemMH.addActionListener(this);
		btnXoaMH.addActionListener(this);
		btnTim.addActionListener(this);
		btnSuaMH.addActionListener(this);
		btnReset.addActionListener(this);

		tblMH.addMouseListener(this);

		/**
		 * ?????nh d???ng gi?? tr???
		 */
		dfVND = new DecimalFormat("###,### VND");
		/**
		 * D??? li???u m???u
		 */
		txtTenMH.setText("BBQ");
		txtSoLuong.setText("100");
		txtDonGia.setText("50000");
		cboLoaiMH.setSelectedIndex(1);

		/**
		 * Load d??? li???u l??n b???ng
		 */
		loadTableMH();
	}

	/**
	 * L???y d??? li???u t??? SQL Server n???p v??o b???ng th??ng qua v??ng l???p for, kh??ng n???p v??o
	 * b???ng v???i lo???i m???t h??ng ng???ng kinh doanh
	 */
	public void loadTableMH() {
		ArrayList<MatHang> lsMH = daoMH.getDSMatHang();
		for (MatHang mh : lsMH) {
			LoaiMatHang lMH = daoLMH.getLoaiMHTheoMaLoai(mh.getLoaiMatHang().getMaLoaiMatHang());
			String check = lMH.getMaLoaiMatHang();
			if (!"LMH004".equals(check)) {
				modelMatHang.addRow(new Object[] { mh.getMaMatHang(), mh.getTenMatHang(), lMH.getTenLoaiMatHang(),
						mh.getSoLuongMatHang(), dfVND.format(Math.round(mh.getGiaMatHang())) });
			}
		}
	}

	/**
	 * X??a to??n b??? b???ng
	 */
	public void clearTable() {
		while (tblMH.getRowCount() > 0) {
			modelMatHang.removeRow(0);
		}
	}

	/**
	 * L??m m???i to??n b??? ch????ng tr??nh, ?????t t???t c??? v??? gi?? tr??? m???c ?????nh
	 */
	public void LamMoi() {
		txtTim.setText("T??m m???t h??ng theo t??n m???t h??ng, lo???i m???t h??ng");
		txtTim.setFont(new Font("SansSerif", Font.ITALIC, 15));
		txtTim.setForeground(Colors.LightGray);
		clearTable();
		txtTenMH.setText("");
		txtDonGia.setText("");
		txtSoLuong.setText("");
		txtTenMH.requestFocus();

		loadTableMH();
	}

	/**
	 * S??? ki???n ch??nh
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		Object o = e.getSource();
		if (o.equals(btnReset)) {
			LamMoi();
		}
		if (o.equals(btnThemMH)) {
			ThemMH();
		}
		if (o.equals(btnXoaMH)) {
			updateMHNgungKD();
		}
		if (o.equals(btnSuaMH)) {
			SuaMH();
		}
		if (o.equals(btnTim)) {
			timMH();
		}

	}

	/**
	 * Th??m m???t h??ng v??o table v?? SQL Server
	 */
	@SuppressWarnings("unlikely-arg-type")
	public void ThemMH() {
		String maMH = daoPhatSinhMa.getMaMH();
		String tenMH = txtTenMH.getText().trim();
		String loaiMH = cboLoaiMH.getSelectedItem().toString();
		String maLMH = daoLMH.getMaLoaiMHTheoTen(loaiMH);
		if (tenMH.equals("") || txtSoLuong.equals("") || txtDonGia.equals("")) {
			JOptionPane.showMessageDialog(this, "Vui l??ng nh???p th??ng tin ?????y ?????!", "Th??ng b??o",
					JOptionPane.WARNING_MESSAGE);
			txtTenMH.requestFocus();
		} else if (regex.regexTenMH(txtTenMH)) {
			if (regex.regexSoLuong(txtSoLuong)) {
				if (regex.regexGiaMH(txtDonGia)) {
					int soluong = Integer.parseInt(txtSoLuong.getText());
					double dongia = Double.parseDouble(txtDonGia.getText());
					MatHang mh = new MatHang(maMH, tenMH, soluong, dongia, new LoaiMatHang(maLMH));
					try {
						daoMH.ThemMH(mh);
					} catch (Exception e) {
						e.printStackTrace();
						JOptionPane.showMessageDialog(this, "Th??m m???t h??ng th???t b???i!");
					}
					clearTable();
					JOptionPane.showMessageDialog(this, "Th??m m???t h??ng th??nh c??ng!");
					loadTableMH();
					LoaiMatHang lMH = daoLMH.getLoaiMHTheoMaLoai(mh.getLoaiMatHang().getMaLoaiMatHang());
					modelMatHang.addRow(new Object[] { mh.getMaMatHang(), mh.getTenMatHang(), lMH.getTenLoaiMatHang(),
							mh.getSoLuongMatHang(), dfVND.format(Math.round(mh.getGiaMatHang())) });
				}
			}
		}
	}

	/**
	 * X??a m???t h??ng kh???i table, update m???t h??ng th??nh ng???ng kinh doanh tr??n SQL
	 * Server
	 */
	public void updateMHNgungKD() {
		if (tblMH.getSelectedRow() == -1) {
			JOptionPane.showMessageDialog(this, "Vui l??ng ch???n m???t h??ng c???n x??a");
		} else {
			int r = tblMH.getSelectedRow();
			if (r >= 0) {
				int del = JOptionPane.showConfirmDialog(null, "B???n ch???c ch???n mu???n x??a? ", "Th??ng b??o",
						JOptionPane.YES_NO_OPTION);
				if (del == JOptionPane.YES_OPTION) {
					String maMH1 = tblMH.getValueAt(r, 0).toString();
					String tenMH = txtTenMH.getText();
					String maLMH = "LMH004";
					int soluong = Integer.parseInt(txtSoLuong.getText());
					double dongia = Double.parseDouble(txtDonGia.getText());
					MatHang mh = new MatHang(maMH1, tenMH, soluong, dongia, new LoaiMatHang(maLMH));
					daoMH.updateMH(mh);
					clearTable();
					loadTableMH();
				}
			}
		}
	}

	/**
	 * S???a th??ng tin m???t h??ng
	 */
	public void SuaMH() {
		int row = tblMH.getSelectedRow();
		if (row >= 0) {
			int update = JOptionPane.showConfirmDialog(this, "B???n mu???n s???a th??ng tin m???t h??ng  n??y kh??ng?", "Th??ng b??o",
					JOptionPane.YES_NO_OPTION);
			if (update == JOptionPane.YES_OPTION) {
				if (regex.regexTenMH(txtTenMH) && regex.regexSoLuong(txtSoLuong) && regex.regexGiaMH(txtDonGia)) {
					String maMH = (String) tblMH.getValueAt(row, 0);
					String tenMH = txtTenMH.getText();
					String loaiMH = cboLoaiMH.getSelectedItem().toString();
					String maLMH = daoLMH.getMaLoaiMHTheoTen(loaiMH);
					int soluong = Integer.parseInt(txtSoLuong.getText());
					double dongia = Double.parseDouble(txtDonGia.getText());

					try {

						MatHang mh = new MatHang(maMH, tenMH, soluong, dongia, new LoaiMatHang(maLMH));
						daoMH.updateMH(mh);
						clearTable();
						LoaiMatHang lMH = daoLMH.getLoaiMHTheoMaLoai(mh.getLoaiMatHang().getMaLoaiMatHang());
						modelMatHang
								.addRow(new Object[] { mh.getMaMatHang(), mh.getTenMatHang(), lMH.getTenLoaiMatHang(),
										mh.getSoLuongMatHang(), dfVND.format(Math.round(mh.getGiaMatHang())) });
						clearTable();
						loadTableMH();
						JOptionPane.showMessageDialog(this, "C???p  nh???t m???t h??ng th??nh c??ng! ");
					} catch (Exception e) {
						e.printStackTrace();
						JOptionPane.showMessageDialog(this, "M?? m???t h??ng kh??ng t???n t???i.");
					}

				}
			}
		} else {
			JOptionPane.showMessageDialog(null, "Vui l??ng ch???n th??ng tin m???t h??ng c???n s???a!", "Th??ng b??o",
					JOptionPane.WARNING_MESSAGE);
		}
	}

	/**
	 * T??m m???t h??ng theo t??n m???t h??ng, lo???i m???t h??ng
	 */
	@SuppressWarnings("unlikely-arg-type")
	public void timMH() {
		ArrayList<MatHang> lstMH = null;
		String input = txtTim.getText().trim();
		String regexTenMH = "^[ A-Za-za-zA-Z??????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????]+$";
		if (!txtTim.equals("") && !txtTim.getText().equals("T??m m???t h??ng theo t??n m???t h??ng, lo???i m???t h??ng")) {
			if (input.matches(regexTenMH)) {
				lstMH = daoMH.getTenMH(input);
				loadTenMH(lstMH);
			}
			if (regex.regexTimKiemLoaiMatHang(txtTim)) {
				lstMH = daoMH.getLMH(input);
				loadLoaiMH(lstMH);
			} else if (lstMH.size() == 0)
				JOptionPane.showMessageDialog(null,
						"Th??ng tin t??m ki???m kh??ng h???p l???!\nTh??ng tin c?? th??? t??m ki???m:\n - T??n m???t h??ng. V?? d???: Bia Heiniken,..."
								+ "\n - Lo???i m???t h??ng. V?? d???: ????? u???ng, ????? ??n.",
						"Th??ng b??o", JOptionPane.ERROR_MESSAGE);
		} else {
			clearTable();
			JOptionPane.showMessageDialog(this, "Vui l??ng nh???p th??ng tin t??m ki???m!", "Th??ng b??o",
					JOptionPane.WARNING_MESSAGE);
		}
	}

	/**
	 * N???p d??? li???u t??n m???t h??ng v??o b???ng
	 */
	public void loadTenMH(ArrayList<MatHang> mh1) {
		clearTable();
		ArrayList<MatHang> lsMH = daoMH.getTenMH(txtTim.getText());
		for (MatHang mh : lsMH) {
			LoaiMatHang lMH = daoLMH.getLoaiMHTheoMaLoai(mh.getLoaiMatHang().getMaLoaiMatHang());
			modelMatHang.addRow(new Object[] { mh.getMaMatHang(), mh.getTenMatHang(), lMH.getTenLoaiMatHang(),
					mh.getSoLuongMatHang(), dfVND.format(Math.round(mh.getGiaMatHang())) });
		}
	}

	/**
	 * N???p d??? li???u lo???i m???t h??ng v??o b???ng
	 */
	public void loadLoaiMH(ArrayList<MatHang> mh1) {
		clearTable();
		String maLoai = daoLMH.getMaLoaiMHTheoTen(txtTim.getText());
		ArrayList<MatHang> lsMH = daoMH.getLMH(maLoai);
		for (MatHang mh : lsMH) {
			LoaiMatHang lMH = daoLMH.getLoaiMHTheoMaLoai(mh.getLoaiMatHang().getMaLoaiMatHang());
			modelMatHang.addRow(new Object[] { mh.getMaMatHang(), mh.getTenMatHang(), lMH.getTenLoaiMatHang(),
					mh.getSoLuongMatHang(), dfVND.format(Math.round(mh.getGiaMatHang())) });
		}
	}

	/**
	 * S??? ki???n click chu???t
	 */
	@Override
	public void mouseClicked(MouseEvent e) {
		Object o = e.getSource();
		if (o.equals(tblMH)) {
			int row = tblMH.getSelectedRow();
			txtTenMH.setText(modelMatHang.getValueAt(row, 1).toString());
			txtSoLuong.setText(modelMatHang.getValueAt(row, 3).toString());
			try {
				txtDonGia.setText(dfVND.parse(modelMatHang.getValueAt(row, 4).toString()) + "");
			} catch (ParseException e1) {
				e1.printStackTrace();
			}
			cboLoaiMH.setSelectedItem(modelMatHang.getValueAt(row, 2).toString());

		}
	}

	@Override
	public void mousePressed(MouseEvent e) {

	}

	@Override
	public void mouseReleased(MouseEvent e) {

	}

	@Override
	public void mouseEntered(MouseEvent e) {

	}

	@Override
	public void mouseExited(MouseEvent e) {

	}

}
