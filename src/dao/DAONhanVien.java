package dao;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import connection.ConnectDB;
import entity.NhanVien;
import entity.TaiKhoan;

public class DAONhanVien implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	//them NV
	public boolean themNV(NhanVien nv) throws SQLException {
		ConnectDB.getinstance();
		Connection con = ConnectDB.getConnection();
		String sql = "insert into NhanVien values (?,?,?,?,?,?,?,?,?,?)";
		try {
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, nv.getMaNhanVien());
			ps.setString(2, nv.getTaiKhoan().getMaTK());
			ps.setString(3, nv.getTenNhanVien());
			ps.setString(4, nv.getChucVu());
			ps.setString(5, nv.getGioiTinh());
			ps.setDate(6, nv.getNgaySinh());
			ps.setString(7, nv.getDiaChi());
			ps.setString(8, nv.getSdt());
			ps.setString(9, nv.getCccd());
			ps.setDouble(10, nv.getLuong());


			return ps.executeUpdate() > 0;
		}catch (SQLException e) {
			e.printStackTrace();
		}
		con.close();
		return false;
	}

	public boolean xoaNV( String ma) throws SQLException {
		Connection con = ConnectDB.getConnection();
		String sql = "delete from NhanVien where maNhanVien = ?";
		try {
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, ma);
	
			return ps.executeUpdate() > 0;
		}catch (Exception e) {
			e.printStackTrace();
		}
		con.close();
		return false;

	}
	public boolean capNhatNV(NhanVien nv, String ma) throws SQLException {
		Connection con = ConnectDB.getConnection();
		String sql = "update NhanVien set tenNhanVien = ?, chucVu = ?, gioiTinh = ?, ngaySinh = ?, diaChi = ?, sdt = ?, cccd = ?, luong = ?, caLamViec = ? where maNhanVien = '"+ma+"'";
		try {
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, nv.getTenNhanVien());
			ps.setString(2, nv.getChucVu());
			ps.setString(3, nv.getGioiTinh());
			ps.setDate(4, nv.getNgaySinh());
			ps.setString(5, nv.getDiaChi());
			ps.setString(6, nv.getSdt());
			ps.setString(7, nv.getCccd());
			ps.setDouble(8, nv.getLuong());


			return ps.executeUpdate() > 0;
		}catch (Exception e) {
			e.printStackTrace();
		}
		con.close();
		return false;

	}

	//Load tat ca sd NV
	public ArrayList<NhanVien> getAllDanhSachNV() {
		ArrayList<NhanVien> lstNV=new ArrayList<>();
		ConnectDB.getinstance();
		Connection con = ConnectDB.getConnection();
		try {
			PreparedStatement ps = con.prepareStatement("select * from [dbo].[NhanVien]");
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				NhanVien nv=new NhanVien();
				nv.setMaNhanVien(rs.getString(1));
				nv.setTaiKhoan(new TaiKhoan(rs.getString(2)));
				nv.setTenNhanVien(rs.getString(3));
				nv.setChucVu(rs.getString(4));
				nv.setGioiTinh(rs.getString(5));
				nv.setNgaySinh(rs.getDate(6));
				nv.setDiaChi(rs.getString(7));
				nv.setSdt(rs.getString(8));
				nv.setCccd(rs.getString(9));
				nv.setLuong(rs.getDouble(10));
				lstNV.add(nv);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return lstNV;
	}

	//Load ds NV dang lam viec

	//Load 1 NV  theo manv, sdt

	public ArrayList<NhanVien> getMaVaSDTNV(String info) { 
		ArrayList<NhanVien> lstNV=new ArrayList<NhanVien>();
		ConnectDB.getinstance();
		Connection con = ConnectDB.getConnection();
		String sqlMa = "select * from [dbo].[NhanVien] where maNhanVien = '"+info+"'";
		String sqlSDT = "select * from [dbo].[NhanVien] where sdt = '"+info+"'";
		try {
			Statement stm = con.createStatement();
			ResultSet rsMa = stm.executeQuery(sqlMa);
			while(rsMa.next()) {
				NhanVien nv=new NhanVien();
				nv.setMaNhanVien(rsMa.getString(1));
				nv.setTaiKhoan(new TaiKhoan(rsMa.getString(2)));
				nv.setTenNhanVien(rsMa.getString(3));
				nv.setChucVu(rsMa.getString(4));
				nv.setGioiTinh(rsMa.getString(5));
				nv.setNgaySinh(rsMa.getDate(6));
				nv.setDiaChi(rsMa.getString(7));
				nv.setSdt(rsMa.getString(8));
				nv.setCccd(rsMa.getString(9));
				nv.setLuong(rsMa.getDouble(10));
		
				lstNV.add(nv);
			}

			ResultSet rsSDT = stm.executeQuery(sqlSDT);
			while(rsSDT.next()) {
				NhanVien nv=new NhanVien();
				nv.setMaNhanVien(rsSDT.getString(1));
				nv.setTaiKhoan(new TaiKhoan(rsSDT.getString(2)));
				nv.setTenNhanVien(rsSDT.getString(3));
				nv.setChucVu(rsSDT.getString(4));
				nv.setGioiTinh(rsSDT.getString(5));
				nv.setNgaySinh(rsSDT.getDate(6));
				nv.setDiaChi(rsSDT.getString(7));
				nv.setSdt(rsSDT.getString(8));
				nv.setCccd(rsSDT.getString(9));
				nv.setLuong(rsSDT.getDouble(10));

				lstNV.add(nv);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return lstNV;
	}

	public NhanVien getNVTheoMa(String ma) { 
		NhanVien nv = new NhanVien();
		ConnectDB.getinstance();
		Connection con = ConnectDB.getConnection();
		String sql = "select * from [dbo].[NhanVien] where maNhanVien = '"+ma+"'";
		try {
			Statement stm = con.createStatement();
			ResultSet rs = stm.executeQuery(sql);
			while(rs.next()) {
				nv.setMaNhanVien(rs.getString(1));
				nv.setTaiKhoan(new TaiKhoan(rs.getString(2)));
				nv.setTenNhanVien(rs.getString(3));
				nv.setChucVu(rs.getString(4));
				nv.setGioiTinh(rs.getString(5));
				nv.setNgaySinh(rs.getDate(6));
				nv.setDiaChi(rs.getString(7));
				nv.setSdt(rs.getString(8));
				nv.setCccd(rs.getString(9));
				nv.setLuong(rs.getDouble(10));
			

			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return nv;
	}

	//Load 1 NV  theo tenNV
	public ArrayList<NhanVien> getTenNV(String info) { 
		ArrayList<NhanVien> list = new ArrayList<>();
		ConnectDB.getinstance();
		Connection con = ConnectDB.getConnection();
		String sql = "select * from [dbo].[NhanVien] where tenNhanVien like N'%"+info+"%'";
		try {
			Statement stm = con.createStatement();
			ResultSet rs = stm.executeQuery(sql);
			while(rs.next()) {
				NhanVien nv=new NhanVien();
				nv.setMaNhanVien(rs.getString(1));
				nv.setTaiKhoan(new TaiKhoan(rs.getString(2)));
				nv.setTenNhanVien(rs.getString(3));
				nv.setChucVu(rs.getString(4));
				nv.setGioiTinh(rs.getString(5));
				nv.setNgaySinh(rs.getDate(6));
				nv.setDiaChi(rs.getString(7));
				nv.setSdt(rs.getString(8));
				nv.setCccd(rs.getString(9));
				nv.setLuong(rs.getDouble(10));
	
				list.add(nv);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	//Load 1 NV  theo chucvu
	public ArrayList<NhanVien> getChucVuNV(String info) { 
		ArrayList<NhanVien> list = new ArrayList<>();
		ConnectDB.getinstance();
		Connection con = ConnectDB.getConnection();
		String sql = "select * from [dbo].[NhanVien] where chucVu like N'"+info+"'";
		try {
			Statement stm = con.createStatement();
			ResultSet rs = stm.executeQuery(sql);
			while(rs.next()) {
				NhanVien nv=new NhanVien();
				nv.setMaNhanVien(rs.getString(1));
				nv.setTaiKhoan(new TaiKhoan(rs.getString(2)));
				nv.setTenNhanVien(rs.getString(3));
				nv.setChucVu(rs.getString(4));
				nv.setGioiTinh(rs.getString(5));
				nv.setNgaySinh(rs.getDate(6));
				nv.setDiaChi(rs.getString(7));
				nv.setSdt(rs.getString(8));
				nv.setCccd(rs.getString(9));
				nv.setLuong(rs.getDouble(10));
	
				list.add(nv);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
	public NhanVien getMaVaSdtNVChoDDP(String info) { 
		NhanVien nv = new NhanVien();
		ConnectDB.getinstance();
		Connection con = ConnectDB.getConnection();
		String sqlMa = "select * from [dbo].[NhanVien] where maNhanVien = '"+info+"'";
		String sqlSDT = "select * from [dbo].[NhanVien] where sdt = '"+info+"'";
		try {
			Statement stm = con.createStatement();
			ResultSet rsMa = stm.executeQuery(sqlMa);
			while(rsMa.next()) {
				nv.setMaNhanVien(rsMa.getString(1));
				nv.setTaiKhoan(new TaiKhoan(rsMa.getString(2)));
				nv.setTenNhanVien(rsMa.getString(3));
				nv.setChucVu(rsMa.getString(4));
				nv.setGioiTinh(rsMa.getString(5));
				nv.setNgaySinh(rsMa.getDate(6));
				nv.setDiaChi(rsMa.getString(7));
				nv.setSdt(rsMa.getString(8));
				nv.setCccd(rsMa.getString(9));
				nv.setLuong(rsMa.getDouble(10));
			}

			ResultSet rsSDT = stm.executeQuery(sqlSDT);
			while(rsSDT.next()) {
				nv.setMaNhanVien(rsSDT.getString(1));
				nv.setTaiKhoan(new TaiKhoan(rsSDT.getString(2)));
				nv.setTenNhanVien(rsSDT.getString(3));
				nv.setChucVu(rsSDT.getString(4));
				nv.setGioiTinh(rsSDT.getString(5));
				nv.setNgaySinh(rsSDT.getDate(6));
				nv.setDiaChi(rsSDT.getString(7));
				nv.setSdt(rsSDT.getString(8));
				nv.setCccd(rsSDT.getString(9));
				nv.setLuong(rsSDT.getDouble(10));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return nv;
	}


	public NhanVien getNVTheoTK(String maTK) { 
		NhanVien nv = new NhanVien();
		ConnectDB.getinstance();
		Connection con = ConnectDB.getConnection();
		String sql = "select * from NhanVien where maTK = '"+maTK+"'";

		try {
			Statement stm = con.createStatement();
			ResultSet rs = stm.executeQuery(sql);
			while(rs.next()) {
				nv.setMaNhanVien(rs.getString(1));
				nv.setTaiKhoan(new TaiKhoan(rs.getNString(2)));
				nv.setTenNhanVien(rs.getString(3));
				nv.setChucVu(rs.getString(4));
				nv.setGioiTinh(rs.getString(5));
				nv.setNgaySinh(rs.getDate(6));
				nv.setDiaChi(rs.getString(7));
				nv.setSdt(rs.getString(8));
				nv.setCccd(rs.getString(9));
				nv.setLuong(rs.getDouble(10));
		
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return nv;
	}


	public boolean checkmaNV(String maNV) { 
		NhanVien nv = new NhanVien();
		ConnectDB.getinstance();
		Connection con = ConnectDB.getConnection();
		String sql = "select * from [dbo].[NhanVien] where maNhanVien = '"+maNV+"'";
		try {
			Statement stm = con.createStatement();
			ResultSet rs = stm.executeQuery(sql);
			while(rs.next()) {
				nv.setMaNhanVien(rs.getString(1));
				nv.setTaiKhoan(new TaiKhoan(rs.getString(2)));
				nv.setTenNhanVien(rs.getString(3));
				nv.setChucVu(rs.getString(4));
				nv.setGioiTinh(rs.getString(5));
				nv.setNgaySinh(rs.getDate(6));
				nv.setDiaChi(rs.getString(7));
				nv.setSdt(rs.getString(8));
				nv.setCccd(rs.getString(9));
				nv.setLuong(rs.getDouble(10));
		
				return false;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return true;
	}
	public boolean checkSdtNV(String sdt) { 
		NhanVien nv = new NhanVien();
		ConnectDB.getinstance();
		Connection con = ConnectDB.getConnection();
		String sql = "select * from [dbo].[NhanVien] where sdt = '"+sdt+"'";
		try {
			Statement stm = con.createStatement();
			ResultSet rs = stm.executeQuery(sql);
			while(rs.next()) {
				nv.setMaNhanVien(rs.getString(1));
				nv.setTaiKhoan(new TaiKhoan(rs.getString(2)));
				nv.setTenNhanVien(rs.getString(3));
				nv.setChucVu(rs.getString(4));
				nv.setGioiTinh(rs.getString(5));
				nv.setNgaySinh(rs.getDate(6));
				nv.setDiaChi(rs.getString(7));
				nv.setSdt(rs.getString(8));
				nv.setCccd(rs.getString(9));
				nv.setLuong(rs.getDouble(10));
		
				return false;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return true;
	}

	public boolean checkCccdNV(String cccd) { 
		NhanVien nv = new NhanVien();
		ConnectDB.getinstance();
		Connection con = ConnectDB.getConnection();
		String sql = "select * from [dbo].[NhanVien] where cccd = '"+cccd+"'";
		try {
			Statement stm = con.createStatement();
			ResultSet rs = stm.executeQuery(sql);
			while(rs.next()) {
				nv.setMaNhanVien(rs.getString(1));
				nv.setTaiKhoan(new TaiKhoan(rs.getString(2)));
				nv.setTenNhanVien(rs.getString(3));
				nv.setChucVu(rs.getString(4));
				nv.setGioiTinh(rs.getString(5));
				nv.setNgaySinh(rs.getDate(6));
				nv.setDiaChi(rs.getString(7));
				nv.setSdt(rs.getString(8));
				nv.setCccd(rs.getString(9));
				nv.setLuong(rs.getDouble(10));
			
				return false;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return true;
	}

}
