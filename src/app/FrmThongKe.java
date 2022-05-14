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
import java.awt.event.MouseListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.rmi.RemoteException;
import java.sql.Date;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;

import javax.swing.ButtonGroup;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
//import org.apache.poi.xssf.usermodel.XSSFWorkbook;
//import org.jfree.chart.ChartFactory;
//import org.jfree.chart.ChartPanel;
//import org.jfree.chart.JFreeChart;
//import org.jfree.chart.axis.CategoryAxis;
//import org.jfree.chart.axis.CategoryLabelPositions;
//import org.jfree.chart.plot.CategoryPlot;
//import org.jfree.chart.plot.PlotOrientation;
//import org.jfree.data.category.CategoryDataset;
//import org.jfree.data.category.DefaultCategoryDataset;
//import org.jfree.data.general.DefaultPieDataset;
//import org.jfree.data.general.PieDataset;

import com.toedter.calendar.JDateChooser;

import connection.ConnectDB;
import dao.DAOCTHD;
import dao.DAOHoaDon;
import dao.DAOMatHang;
import dao.DAOPhong;
import entity.CTHD;
import entity.HoaDon;
import entity.MatHang;
import entity.Phong;
import jiconfont.icons.FontAwesome;
import jiconfont.swing.IconFontSwing;



public class FrmThongKe extends JFrame implements ActionListener{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Panel pMain;
	private JButton btnTK;
	private JButton btnTongDoanhThu;
	private JButton btnTGHD;
	private LocalDate now;
	private int ngay;
	private int thang;
	private int nam;
	private Date dNow;
	private FixButton btnLamMoi;
	private JDateChooser dateChooserThongKeNgayBatDau;
	private JDateChooser dateChooserThongKeNgayKetThuc;
	private DAOCTHD daoCTHD;
	private DAOMatHang daoMatHang;
	private DAOHoaDon daoHoaDon;
	private DAOPhong daoPhong;
	private DecimalFormat df;
	private DecimalFormat dfs;
	private DecimalFormat dfh;
	private SimpleDateFormat sf;
	private JLabel lblNgayHienTai;
	private DefaultTableModel modelThongKe;
	private JTable tblTK;
	
	public Panel getFrmThongKe() {
		return this.pMain;
	}
	@SuppressWarnings("deprecation")
	public FrmThongKe(String sHeaderTenNV, String sHeaderMaNV, Date dNgayHienTai) {
		

		//connect database
		try {
			ConnectDB.getinstance().connect();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		daoCTHD = new DAOCTHD();
		daoMatHang = new DAOMatHang();
		daoHoaDon = new DAOHoaDon();
		daoPhong = new DAOPhong();

		getContentPane().setLayout(null);
		pMain = new Panel();
		pMain.setBackground(Color.WHITE);
		pMain.setBounds(0, 0, 1281, 629);
		getContentPane().add(pMain);
		pMain.setLayout(null);

		now = LocalDate.now();
		ngay = now.getDayOfMonth();
		thang = now.getMonthValue();
		nam = now.getYear();

		dNow = new Date(nam,thang,ngay);


		JLabel lblHeaderDate = new JLabel("   Thời gian thống kê:");
		lblHeaderDate.setForeground(Color.BLACK);
		lblHeaderDate.setFont(new Font("SansSerif", Font.BOLD, 18));
		lblHeaderDate.setBounds(334, 0, 221, 41);
		pMain.add(lblHeaderDate);

		lblNgayHienTai = new JLabel();
		lblNgayHienTai.setForeground(Color.BLACK);
		lblNgayHienTai.setFont(new Font("SansSerif", Font.ITALIC, 22));
		lblNgayHienTai.setBounds(521, -2, 267, 41);
		pMain.add(lblNgayHienTai);
		
		

		JPanel pThongKe = new JPanel();
		pThongKe.setBackground(new Color(238,239,243,90));
		pThongKe.setBorder(new TitledBorder(new LineBorder(new Color(114, 23 ,153), 1, true), "", TitledBorder.LEFT, TitledBorder.TOP, null, Color.BLACK));
		pThongKe.setBounds(10, 36, 328, 200);
		pThongKe.setBackground(Color.WHITE);
		pMain.add(pThongKe);
		pThongKe.setLayout(null);
		
		//Bang Tthong ke
		String thongKe[] = {"Mã hóa đơn","Mã phòng", "Khách hàng", "Ngày lập", "Thời gian thuê","Phụ thu" , "Giảm trừ", "Tổng tiền"};
		modelThongKe=new DefaultTableModel(thongKe, 0);
		
		tblTK = new JTable(modelThongKe);
		tblTK.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		tblTK.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		tblTK.setShowHorizontalLines(true);
		tblTK.setShowGrid(true);
		tblTK.setBackground(Color.WHITE);
		tblTK.setFont(new Font("SansSerif", Font.PLAIN, 14));
		tblTK.setSelectionBackground(new Color(164, 44, 167, 30));
		tblTK.setSelectionForeground(new Color(114, 23, 153));
		tblTK.setRowHeight(30);
		
		JTableHeader tbHeader = tblTK.getTableHeader();
		tbHeader.setBackground(new Color(164, 44, 167));
		tbHeader.setForeground(Color.white);
		tbHeader.setFont(new Font("SansSerif", Font.BOLD, 14));

		//thanh cuốn lên xuống
		JScrollPane spPhong = new JScrollPane(tblTK, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		spPhong.setBounds(10, 247, 1243, 371);
		spPhong.setBorder(new LineBorder(new Color(164, 44, 167), 1, true));
		spPhong.setBackground(new Color(164, 44, 167));
		spPhong.getHorizontalScrollBar();
		pMain.add(spPhong);
				
		
				
		//Chữ canh lề trái, số canh lề phải
		DefaultTableCellRenderer rightRenderer=new DefaultTableCellRenderer();
		rightRenderer.setHorizontalAlignment(JLabel.RIGHT);
		DefaultTableCellRenderer leftRenderer = new DefaultTableCellRenderer();
		leftRenderer.setHorizontalAlignment(JLabel.LEFT);
		tblTK.getColumnModel().getColumn(0).setCellRenderer(leftRenderer);
		tblTK.getColumnModel().getColumn(1).setCellRenderer(leftRenderer);
		tblTK.getColumnModel().getColumn(2).setCellRenderer(leftRenderer);
		tblTK.getColumnModel().getColumn(3).setCellRenderer(leftRenderer);
		
		//
		ButtonGroup bg = new ButtonGroup();
		bg.clearSelection();

		JLabel lblChonNgay = new JLabel("Ngày bắt đàu:");
		lblChonNgay.setFont(new Font("SansSerif", Font.PLAIN, 16));
		lblChonNgay.setBounds(25, 15, 100, 36);
		pThongKe.add(lblChonNgay);

		now = LocalDate.now();
		ngay = now.getDayOfMonth();
		thang = now.getMonthValue()-1;
		nam = now.getYear()-1900;
		
		dNow = new Date(nam,thang,ngay);
		
		dateChooserThongKeNgayBatDau = new JDateChooser();
		dateChooserThongKeNgayBatDau.setDateFormatString("dd/MM/yyyy");

		dateChooserThongKeNgayBatDau.setFont(new Font("SansSerif", Font.PLAIN, 15));
		dateChooserThongKeNgayBatDau.getCalendarButton().setPreferredSize(new Dimension(30, 24));
		dateChooserThongKeNgayBatDau.getCalendarButton().setBackground(new Color(102, 0, 153));
		Icon iconCalendar = IconFontSwing.buildIcon(FontAwesome.CALENDAR, 20, Color.white);
		dateChooserThongKeNgayBatDau.setIcon((ImageIcon) iconCalendar);

		dateChooserThongKeNgayBatDau.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		dateChooserThongKeNgayBatDau.setBounds(130, 15, 180, 38);
		dateChooserThongKeNgayBatDau.setDate(dNow);
		pThongKe.add(dateChooserThongKeNgayBatDau);


		
		btnTK = new FixButton("Thống kê");
		btnTK.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnTK.setForeground(Color.WHITE);
		btnTK.setFont(new Font("SansSerif", Font.BOLD, 20));
		btnTK.setBackground(new Color(114, 23, 153));
		btnTK.setBounds(10, 112, 300, 36);

		pThongKe.add(btnTK);

		btnLamMoi = new FixButton("Làm mới");

		btnLamMoi.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnLamMoi.setForeground(Color.WHITE);
		btnLamMoi.setFont(new Font("SansSerif", Font.BOLD, 20));
		btnLamMoi.setBackground(new Color(114, 23, 153));
		btnLamMoi.setBounds(10, 153, 300, 36);
		
		pThongKe.add(btnLamMoi);
		//Icon iconExcel = IconFontSwing.buildIcon(FontAwesome.FILE_EXCEL_O, 20, Color.white);
		
		
		dateChooserThongKeNgayKetThuc = new JDateChooser();
		dateChooserThongKeNgayKetThuc.getCalendarButton().setPreferredSize(new Dimension(30, 24));

		dateChooserThongKeNgayKetThuc.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		dateChooserThongKeNgayKetThuc.getCalendarButton().setBackground(new Color(102, 0, 153));
		dateChooserThongKeNgayKetThuc.setFont(new Font("SansSerif", Font.PLAIN, 15));
		Icon iconCalendar1 = IconFontSwing.buildIcon(FontAwesome.CALENDAR, 20, Color.white);
		dateChooserThongKeNgayKetThuc.setIcon((ImageIcon) iconCalendar1);
		dateChooserThongKeNgayKetThuc.setDateFormatString("dd/MM/yyyy");
		dateChooserThongKeNgayKetThuc.setBounds(130, 60, 180, 38);
		dateChooserThongKeNgayKetThuc.setDate(dNow);
		pThongKe.add(dateChooserThongKeNgayKetThuc);
		
		JLabel lblNgyKtThc = new JLabel("Đến ngày:");
		lblNgyKtThc.setFont(new Font("SansSerif", Font.PLAIN, 16));
		lblNgyKtThc.setBounds(25, 62, 100, 36);
		pThongKe.add(lblNgyKtThc);

		JPanel pTongDoanhThu = new JPanel();
		pTongDoanhThu.setBackground(new Color(238,239,243,90));
		pTongDoanhThu.setBorder(new TitledBorder(new LineBorder(new Color(114, 23 ,153), 1, true), "", TitledBorder.LEFT, TitledBorder.TOP, null, Color.BLACK));
		pTongDoanhThu.setBounds(348, 36, 440, 200);
		pTongDoanhThu.setBackground(Color.WHITE);
		pMain.add(pTongDoanhThu);
		pTongDoanhThu.setLayout(null);

		JLabel lblTDT= new JLabel("Tổng doanh thu ");
		lblTDT.setHorizontalAlignment(SwingConstants.CENTER);
		lblTDT.setFont(new Font("SansSerif", Font.ITALIC, 15));
		lblTDT.setForeground(new Color(148, 0, 211));
		lblTDT.setBounds(107, 172, 226, 17);
		pTongDoanhThu.add(lblTDT);

		btnTongDoanhThu = new FixButton("");

		btnTongDoanhThu.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnTongDoanhThu.setFont(new Font("SansSerif", Font.BOLD, 20));
		btnTongDoanhThu.setForeground(Color.BLACK);
		btnTongDoanhThu.setBackground(Color.WHITE);
		btnTongDoanhThu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnTongDoanhThu.setBounds(107, 109, 226, 52);
		pTongDoanhThu.add(btnTongDoanhThu);
		
		JLabel lblDollar = new JLabel("$");
		lblDollar.setFont(new Font("SansSerif", Font.BOLD, 60));
		lblDollar.setHorizontalAlignment(SwingConstants.CENTER);
		lblDollar.setForeground(Color.ORANGE);
		lblDollar.setBounds(107, 11, 226, 87);
		pTongDoanhThu.add(lblDollar);

		JPanel pTgPhongSD = new JPanel();
		pTgPhongSD.setBackground(new Color(238,239,243,90));
		pTgPhongSD.setBorder(new TitledBorder(new LineBorder(new Color(114, 23 ,153), 1, true), "", TitledBorder.LEFT, TitledBorder.TOP, null, Color.BLACK));
		pTgPhongSD.setBounds(798, 36, 455, 200);
		pTgPhongSD.setBackground(Color.WHITE);
		pMain.add(pTgPhongSD);
		pTgPhongSD.setLayout(null);

		JLabel lblTGSD= new JLabel("Thời gian thuê phòng");
		lblTGSD.setHorizontalAlignment(SwingConstants.CENTER);
		lblTGSD.setFont(new Font("SansSerif", Font.ITALIC, 15));
		lblTGSD.setForeground(new Color(153, 50, 204));
		lblTGSD.setBounds(109, 172, 237, 17);
		pTgPhongSD.add(lblTGSD);

		btnTGHD = new FixButton("");

		btnTGHD.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnTGHD.setFont(new Font("SansSerif", Font.BOLD, 20));
		btnTGHD.setForeground(Color.BLACK);
		btnTGHD.setBackground(Color.WHITE);
		btnTGHD.setBounds(109, 109, 237, 51);
		pTgPhongSD.add(btnTGHD);
		
		JLabel lblIconTGHoatDong = new JLabel();
		lblIconTGHoatDong.setHorizontalAlignment(SwingConstants.CENTER);
		lblIconTGHoatDong.setBounds(109, 11, 237, 87);
		Icon iconTGHoatDong = IconFontSwing.buildIcon(FontAwesome.HOSPITAL_O, 60, Color.red);
		lblIconTGHoatDong.setIcon(iconTGHoatDong);
		pTgPhongSD.add(lblIconTGHoatDong);


		JLabel lblBackGround=new JLabel("");
		lblBackGround.setIcon(new ImageIcon("data\\img\\background.png"));
		lblBackGround.setBounds(0, -23, 1281, 652);
		Image imgBackGround = Toolkit.getDefaultToolkit().getImage("data\\img\\background.png");
		Image resizeBG = imgBackGround.getScaledInstance(lblBackGround.getWidth(), lblBackGround.getHeight(), 0);
		lblBackGround.setIcon(new ImageIcon(resizeBG));
		pMain.add(lblBackGround);
		btnTK.addActionListener(this);
		btnLamMoi.addActionListener(this);
		btnTongDoanhThu.addActionListener(this);
		btnTGHD.addActionListener(this);
		//tblTK.addMouseListener(this);
		
		df = new DecimalFormat("###,### VNĐ");
		dfs = new DecimalFormat("### p");
		dfh = new DecimalFormat("### h");
		sf = new SimpleDateFormat("dd/MM/yyy");
		loadDataTable();
	}
	//so tien thue
	public double tinhTienThue(double giaPhong, HoaDon hd) {
		@SuppressWarnings("deprecation")
		int gioVao = hd.getGioVao().getHours(),
				phutVao = hd.getGioVao().getMinutes();
		@SuppressWarnings("deprecation")
		int gioRa = hd.getGioRa().getHours(),
				phutRa = hd.getGioRa().getMinutes();

		int tongThoiGian = (gioRa*60 + phutRa) - (gioVao*60 + phutVao);
		double tongTienThuePhong = 0;
		if(tongThoiGian > 0) {
			if(tongThoiGian <= 60) {
				tongTienThuePhong = giaPhong;
				return tongTienThuePhong;
			}
			else {
				tongTienThuePhong = (tongThoiGian * giaPhong)/60;
				return tongTienThuePhong;
			}
		}

		return -1;
	}
	//tong tien 1 hoa don
	public double tongTienCTHD(double tongTienThue,HoaDon hd) {
		double tong = tongTienThue;

		ArrayList<CTHD> lsCTHD = daoCTHD.getCTHDTheoMaHD(hd.getMaHoaDon());
		for(CTHD ct : lsCTHD) {
			MatHang mh = daoMatHang.getMHTheoMaMH(ct.getMatHang().getMaMatHang());
			tong += mh.getGiaMatHang() * ct.getSoLuong();
		}

		return tong;
	}
	public double tongDoanhThu(ArrayList<HoaDon> lstHD) {
		double doanhThu = 0;
		for(HoaDon hd : lstHD) {
			String phuThu = hd.getPhuThu();
			Phong p = daoPhong.getPhongTheoMa(hd.getPhong().getMaPhong());
			double giaPhong =p.getGiaPhong();
			double giaPhuThu = 0;
			if(phuThu.equalsIgnoreCase("Buổi tối")) {
				giaPhuThu = 10000;
			}
			if(phuThu.equalsIgnoreCase("Ngày làm")) {
				giaPhuThu = 30000;
			}
			if(phuThu.equalsIgnoreCase("Cuối tuần")) {
				giaPhuThu = 20000;
			}
			giaPhong = giaPhuThu + giaPhong;
			double tongTienThue = tinhTienThue(giaPhong, hd);

			@SuppressWarnings("unused")
			int tongGioThue = (int) ((tongTienThue)/giaPhong);
			@SuppressWarnings("unused")
			int tongPhutThue = (int) (((tongTienThue*60)/giaPhong) % 60);



			double thanhTien = tongTienCTHD(tongTienThue, hd);


			thanhTien = thanhTien - hd.getGiamGia();
			doanhThu+= thanhTien;
		}
		return doanhThu;
	}


	

	//load thong ke doanh thu
	public void loadThongKeDoanhThu() {
		
		java.util.Date utilngayBD = dateChooserThongKeNgayBatDau.getDate();
		java.util.Date utilngayKT = dateChooserThongKeNgayKetThuc.getDate();
		
		@SuppressWarnings("deprecation")
		Date ngayBatDau = new Date(utilngayBD.getYear(), utilngayBD.getMonth(), utilngayBD.getDate());
		@SuppressWarnings("deprecation")
		Date ngayKetThuc = new Date(utilngayKT.getYear(), utilngayKT.getMonth(), utilngayKT.getDate());
		if(ngayBatDau.before(ngayKetThuc)||ngayBatDau.equals(ngayKetThuc)) {
			
			ArrayList<HoaDon> lstHD = daoHoaDon.getHDTheoNgay(ngayBatDau, ngayKetThuc);
			double doanhThu = tongDoanhThu(lstHD);
			btnTongDoanhThu.setText(df.format(doanhThu));
				
		}
		else JOptionPane.showMessageDialog(this, "Ngày bắt đầu phải trước hoặc bằng ngày kết thúc!");
	}

	

//
//
//	//load thá»‘ng kÃª sá»‘ giá»� Ä‘Ã£ sá»­ dá»¥ng cho cÃ¡c phÃ²ng
//	@SuppressWarnings("deprecation")
	public void loadThongKeSoGio() {
		java.util.Date utilngayBD = dateChooserThongKeNgayBatDau.getDate();
		java.util.Date utilngayKT = dateChooserThongKeNgayKetThuc.getDate();
		

		Date ngayBatDau = new Date(utilngayBD.getYear(), utilngayBD.getMonth(), utilngayBD.getDate());
		Date ngayKetThuc = new Date(utilngayKT.getYear(), utilngayKT.getMonth(), utilngayKT.getDate());
		ArrayList<HoaDon> lstHD = daoHoaDon.getHDTheoNgay(ngayBatDau, ngayKetThuc);
		if(ngayBatDau.before(ngayKetThuc)||ngayBatDau.equals(ngayKetThuc)) {
			int phutThongke = 0;
			int gioThongke = 0;
			for(HoaDon hd: lstHD) {
				int gioVao = hd.getGioVao().getHours(),
						phutVao = hd.getGioVao().getMinutes();
				int gioRa = hd.getGioRa().getHours(),
						phutRa = hd.getGioRa().getMinutes();

				phutThongke += (gioRa*60 + phutRa) - (gioVao*60 + phutVao);
				gioThongke = phutThongke/60;
			}
			if(phutThongke < 60 ) {
				btnTGHD.setText(dfs.format(phutThongke)+"");
			}
			else {
				btnTGHD.setText(dfh.format(gioThongke)+"");
			}
			
		}
	}
	public void resetAll() {
		dateChooserThongKeNgayBatDau.setDate(dNow);
		dateChooserThongKeNgayKetThuc.setDate(dNow);
		btnTongDoanhThu.setText("");
		btnTGHD.setText("");
		
		
	}
	public double tinhTienThue(int gioVao, int gioRa,int phutVao, int phutRa, double giaPhong) {

			
			int tongThoiGian = (gioRa*60 + phutRa) - (gioVao*60 + phutVao);
			double tongTienThuePhong = 0;
			if(tongThoiGian > 0) {
				if(tongThoiGian <= 60) {
					tongTienThuePhong = giaPhong;
					return tongTienThuePhong;
				}
				else {
					tongTienThuePhong = (tongThoiGian * giaPhong)/60;
					return tongTienThuePhong;
				}
			}
			
			 return -1;
	}
	private void loadDataTable() {
		java.util.Date utilngayBD = dateChooserThongKeNgayBatDau.getDate();
		java.util.Date utilngayKT = dateChooserThongKeNgayKetThuc.getDate();
		

		Date ngayBatDau = new Date(utilngayBD.getYear(), utilngayBD.getMonth(), utilngayBD.getDate());
		Date ngayKetThuc = new Date(utilngayKT.getYear(), utilngayKT.getMonth(), utilngayKT.getDate());
		ArrayList<HoaDon> lstHD = daoHoaDon.getHDTheoNgay(ngayBatDau, ngayKetThuc);
		if(ngayBatDau.before(ngayKetThuc)||ngayBatDau.equals(ngayKetThuc)) {
			int thoiGianThongKe = 0;
			for(HoaDon hd: lstHD) {
				int gioVao = hd.getGioVao().getHours(),
						phutVao = hd.getGioVao().getMinutes();
				int gioRa = hd.getGioRa().getHours(),
						phutRa = hd.getGioRa().getMinutes();
				
				double giaPhong = daoPhong.getGiaPhongTheoMa(hd.getPhong().getMaPhong()).getGiaPhong();
				double tienThue = tinhTienThue(gioVao, gioRa, phutVao, phutRa,giaPhong );
				
				
				String dftienThue= df.format(tienThue);
				String tgThue = "";
				
				int phutThongke = (gioRa*60 + phutRa) - (gioVao*60 + phutVao);
				int gioThongke = phutThongke/60;
				
			if(phutThongke < 60 ) {
				tgThue =dfs.format(phutThongke)+"";
			}
			else {
				int phut = phutThongke - gioThongke*60;
				tgThue =(dfh.format(gioThongke)+"")+(dfs.format(phut)+"");
			}
				
				modelThongKe.addRow(new Object[] {
						hd.getMaHoaDon(), hd.getPhong().getMaPhong(), hd.getKhachHang().getTenKH(), hd.getNgayLap(),tgThue, hd.getPhuThu(),hd.getGiamGia(),dftienThue
				});
		}
		
	}
		}
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		Object o = e.getSource();

		if(o.equals(btnTK))
		{
			java.util.Date utilngayBD = dateChooserThongKeNgayBatDau.getDate();
			java.util.Date utilngayKT = dateChooserThongKeNgayKetThuc.getDate();
			@SuppressWarnings("deprecation")
			Date ngayden = new Date(utilngayBD.getYear(), utilngayBD.getMonth(), utilngayBD.getDate());
			@SuppressWarnings("deprecation")
			Date ngayKT = new Date(utilngayKT.getYear(), utilngayKT.getMonth(), utilngayKT.getDate());
			lblNgayHienTai.setText(sf.format(ngayden)+" - "+sf.format(ngayKT));
			
			long noDay = (ngayKT.getTime() - ngayden.getTime()) / (24 * 3600 * 1000);
				if(noDay < 32) {
					loadThongKeDoanhThu();
					loadThongKeSoGio();
					
				}
				else
			JOptionPane.showMessageDialog(null, "Chọn khoảng thời gian thống kê!");
			
		}
		if(o.equals(btnLamMoi))
			resetAll();

		}


			
		
	
}


