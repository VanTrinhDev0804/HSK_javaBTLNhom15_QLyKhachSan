USE [master]
GO
/****** Object:  Database [QLyDatPhong]    Script Date: 05/14/2022 11:29:31 AM ******/
CREATE DATABASE [QLyDatPhong]
 CONTAINMENT = NONE
 ON  PRIMARY 
( NAME = N'QLyDatPhong', FILENAME = N'C:\Program Files\Microsoft SQL Server\MSSQL15.SQLEXPRESS\MSSQL\DATA\dbQLyDatPhong.mdf' , SIZE = 8192KB , MAXSIZE = UNLIMITED, FILEGROWTH = 65536KB )
 LOG ON 
( NAME = N'QLyDatPhong_log', FILENAME = N'C:\Program Files\Microsoft SQL Server\MSSQL15.SQLEXPRESS\MSSQL\DATA\dbQLyDatPhong_log.ldf' , SIZE = 8192KB , MAXSIZE = 2048GB , FILEGROWTH = 65536KB )
 WITH CATALOG_COLLATION = DATABASE_DEFAULT
GO
ALTER DATABASE [QLyDatPhong] SET COMPATIBILITY_LEVEL = 150
GO
IF (1 = FULLTEXTSERVICEPROPERTY('IsFullTextInstalled'))
begin
EXEC [QLyDatPhong].[dbo].[sp_fulltext_database] @action = 'enable'
end
GO
ALTER DATABASE [QLyDatPhong] SET ANSI_NULL_DEFAULT OFF 
GO
ALTER DATABASE [QLyDatPhong] SET ANSI_NULLS OFF 
GO
ALTER DATABASE [QLyDatPhong] SET ANSI_PADDING OFF 
GO
ALTER DATABASE [QLyDatPhong] SET ANSI_WARNINGS OFF 
GO
ALTER DATABASE [QLyDatPhong] SET ARITHABORT OFF 
GO
ALTER DATABASE [QLyDatPhong] SET AUTO_CLOSE OFF 
GO
ALTER DATABASE [QLyDatPhong] SET AUTO_SHRINK OFF 
GO
ALTER DATABASE [QLyDatPhong] SET AUTO_UPDATE_STATISTICS ON 
GO
ALTER DATABASE [QLyDatPhong] SET CURSOR_CLOSE_ON_COMMIT OFF 
GO
ALTER DATABASE [QLyDatPhong] SET CURSOR_DEFAULT  GLOBAL 
GO
ALTER DATABASE [QLyDatPhong] SET CONCAT_NULL_YIELDS_NULL OFF 
GO
ALTER DATABASE [QLyDatPhong] SET NUMERIC_ROUNDABORT OFF 
GO
ALTER DATABASE [QLyDatPhong] SET QUOTED_IDENTIFIER OFF 
GO
ALTER DATABASE [QLyDatPhong] SET RECURSIVE_TRIGGERS OFF 
GO
ALTER DATABASE [QLyDatPhong] SET  DISABLE_BROKER 
GO
ALTER DATABASE [QLyDatPhong] SET AUTO_UPDATE_STATISTICS_ASYNC OFF 
GO
ALTER DATABASE [QLyDatPhong] SET DATE_CORRELATION_OPTIMIZATION OFF 
GO
ALTER DATABASE [QLyDatPhong] SET TRUSTWORTHY OFF 
GO
ALTER DATABASE [QLyDatPhong] SET ALLOW_SNAPSHOT_ISOLATION OFF 
GO
ALTER DATABASE [QLyDatPhong] SET PARAMETERIZATION SIMPLE 
GO
ALTER DATABASE [QLyDatPhong] SET READ_COMMITTED_SNAPSHOT OFF 
GO
ALTER DATABASE [QLyDatPhong] SET HONOR_BROKER_PRIORITY OFF 
GO
ALTER DATABASE [QLyDatPhong] SET RECOVERY FULL 
GO
ALTER DATABASE [QLyDatPhong] SET  MULTI_USER 
GO
ALTER DATABASE [QLyDatPhong] SET PAGE_VERIFY CHECKSUM  
GO
ALTER DATABASE [QLyDatPhong] SET DB_CHAINING OFF 
GO
ALTER DATABASE [QLyDatPhong] SET FILESTREAM( NON_TRANSACTED_ACCESS = OFF ) 
GO
ALTER DATABASE [QLyDatPhong] SET TARGET_RECOVERY_TIME = 60 SECONDS 
GO
ALTER DATABASE [QLyDatPhong] SET DELAYED_DURABILITY = DISABLED 
GO
ALTER DATABASE [QLyDatPhong] SET ACCELERATED_DATABASE_RECOVERY = OFF  
GO
ALTER DATABASE [QLyDatPhong] SET QUERY_STORE = OFF
GO
USE [QLyDatPhong]
GO
/****** Object:  Table [dbo].[CTDDP]    Script Date: 05/14/2022 11:29:32 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[CTDDP](
	[maDDP] [nvarchar](50) NOT NULL,
	[maMH] [nvarchar](50) NOT NULL,
	[soLuong] [int] NULL,
 CONSTRAINT [PK_CTDDP] PRIMARY KEY CLUSTERED 
(
	[maDDP] ASC,
	[maMH] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[CTHD]    Script Date: 05/14/2022 11:29:32 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[CTHD](
	[maHD] [nvarchar](50) NOT NULL,
	[maMH] [nvarchar](50) NOT NULL,
	[soLuong] [int] NULL,
 CONSTRAINT [PK_CTHD] PRIMARY KEY CLUSTERED 
(
	[maHD] ASC,
	[maMH] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[DonDatPhong]    Script Date: 05/14/2022 11:29:32 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[DonDatPhong](
	[maDDP] [nvarchar](50) NOT NULL,
	[maPhong] [nvarchar](50) NOT NULL,
	[maKH] [nvarchar](50) NOT NULL,
	[maNhanVien] [nvarchar](50) NOT NULL,
	[ngayLap] [date] NULL,
	[gioDen] [time](7) NULL,
	[ngayDen] [date] NULL,
	[TrangThaiDDP] [nvarchar](255) NULL,
 CONSTRAINT [PK_DonDatPhong] PRIMARY KEY CLUSTERED 
(
	[maDDP] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[HoaDon]    Script Date: 05/14/2022 11:29:32 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[HoaDon](
	[maHD] [nvarchar](50) NOT NULL,
	[maPhong] [nvarchar](50) NOT NULL,
	[maKH] [nvarchar](50) NOT NULL,
	[maNhanVien] [nvarchar](50) NOT NULL,
	[ngayLap] [date] NULL,
	[gioVao] [time](7) NULL,
	[gioRa] [time](7) NULL,
	[phuThu] [nvarchar](255) NULL,
	[trangThai] [nvarchar](255) NULL,
	[giamGia] [float] NULL,
 CONSTRAINT [PK_HoaDon] PRIMARY KEY CLUSTERED 
(
	[maHD] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[KhachHang]    Script Date: 05/14/2022 11:29:32 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[KhachHang](
	[maKhachHang] [nvarchar](50) NOT NULL,
	[maLoaiKH] [nvarchar](50) NOT NULL,
	[tenKH] [nvarchar](255) NULL,
	[sdt] [nvarchar](255) NULL,
	[cccd] [nvarchar](255) NULL,
	[diaChi] [nvarchar](255) NULL,
	[ngaySinh] [date] NULL,
	[gioiTinh] [nvarchar](255) NULL,
	[diemTichLuy] [int] NULL,
	[ngayDangKy] [date] NULL,
 CONSTRAINT [PK_KhachHang] PRIMARY KEY CLUSTERED 
(
	[maKhachHang] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[LoaiKH]    Script Date: 05/14/2022 11:29:32 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[LoaiKH](
	[maLoaiKH] [nvarchar](50) NOT NULL,
	[tenLoaiKH] [nvarchar](255) NULL,
 CONSTRAINT [PK_LoaiKH] PRIMARY KEY CLUSTERED 
(
	[maLoaiKH] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[LoaiMatHang]    Script Date: 05/14/2022 11:29:32 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[LoaiMatHang](
	[maLoaiMH] [nvarchar](50) NOT NULL,
	[tenLoaiMH] [nvarchar](255) NULL,
 CONSTRAINT [PK_LoaiMatHang] PRIMARY KEY CLUSTERED 
(
	[maLoaiMH] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[LoaiPhong]    Script Date: 05/14/2022 11:29:32 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[LoaiPhong](
	[maLoaiPhong] [nvarchar](50) NOT NULL,
	[tenLoaiPhong] [nvarchar](255) NULL,
 CONSTRAINT [PK_LoaiPhong] PRIMARY KEY CLUSTERED 
(
	[maLoaiPhong] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[MatHang]    Script Date: 05/14/2022 11:29:32 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[MatHang](
	[maMH] [nvarchar](50) NOT NULL,
	[maLoaiMH] [nvarchar](50) NOT NULL,
	[tenMH] [nvarchar](255) NULL,
	[soLuongMH] [int] NULL,
	[giaMH] [float] NULL,
 CONSTRAINT [PK_MatHang] PRIMARY KEY CLUSTERED 
(
	[maMH] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[NhanVien]    Script Date: 05/14/2022 11:29:32 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[NhanVien](
	[maNhanVien] [nvarchar](50) NOT NULL,
	[maTK] [nvarchar](50) NOT NULL,
	[tenNhanVien] [nvarchar](255) NULL,
	[chucVu] [nvarchar](255) NULL,
	[gioiTinh] [nvarchar](255) NULL,
	[ngaySinh] [date] NULL,
	[diaChi] [nvarchar](255) NULL,
	[sdt] [nvarchar](255) NULL,
	[cccd] [nvarchar](255) NULL,
	[luong] [float] NULL,
 CONSTRAINT [PK_NhanVien] PRIMARY KEY CLUSTERED 
(
	[maNhanVien] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Phong]    Script Date: 05/14/2022 11:29:32 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Phong](
	[maPhong] [nvarchar](50) NOT NULL,
	[maLoaiPhong] [nvarchar](50) NOT NULL,
	[tinhTrangPhong] [nvarchar](255) NULL,
	[giaPhong] [float] NULL,
 CONSTRAINT [PK_Phong] PRIMARY KEY CLUSTERED 
(
	[maPhong] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[TaiKhoan]    Script Date: 05/14/2022 11:29:32 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[TaiKhoan](
	[maTK] [nvarchar](50) NOT NULL,
	[matKhau] [nvarchar](255) NOT NULL,
 CONSTRAINT [PK_TaiKhoan] PRIMARY KEY CLUSTERED 
(
	[maTK] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
INSERT [dbo].[DonDatPhong] ([maDDP], [maPhong], [maKH], [maNhanVien], [ngayLap], [gioDen], [ngayDen], [TrangThaiDDP]) VALUES (N'DDP001', N'P023', N'KH001', N'NV002', CAST(N'2022-05-09' AS Date), CAST(N'23:59:00' AS Time), CAST(N'2022-05-09' AS Date), N'Đã xác nhận')
GO
INSERT [dbo].[KhachHang] ([maKhachHang], [maLoaiKH], [tenKH], [sdt], [cccd], [diaChi], [ngaySinh], [gioiTinh], [diemTichLuy], [ngayDangKy]) VALUES (N'KH001', N'LKH001', N'Đinh Quang Tuấn', N'0903142210', NULL, N'118 Hoàng Văn Thụ, P9, Q.Phú Nhuận', NULL, NULL, 0, NULL)
GO
INSERT [dbo].[LoaiKH] ([maLoaiKH], [tenLoaiKH]) VALUES (N'LKH001', N'Khách hàng thường')
INSERT [dbo].[LoaiKH] ([maLoaiKH], [tenLoaiKH]) VALUES (N'LKH002', N'Thành viên thường')
INSERT [dbo].[LoaiKH] ([maLoaiKH], [tenLoaiKH]) VALUES (N'LKH003', N'Thành viên VIP')
INSERT [dbo].[LoaiKH] ([maLoaiKH], [tenLoaiKH]) VALUES (N'LKH004', N'Không còn là khách hàng')
GO
INSERT [dbo].[LoaiMatHang] ([maLoaiMH], [tenLoaiMH]) VALUES (N'LMH001', N'Đồ uống')
INSERT [dbo].[LoaiMatHang] ([maLoaiMH], [tenLoaiMH]) VALUES (N'LMH002', N'Đồ ăn')
INSERT [dbo].[LoaiMatHang] ([maLoaiMH], [tenLoaiMH]) VALUES (N'LMH003', N'Khác')
INSERT [dbo].[LoaiMatHang] ([maLoaiMH], [tenLoaiMH]) VALUES (N'LMH004', N'Ngừng kinh doanh')
GO
INSERT [dbo].[LoaiPhong] ([maLoaiPhong], [tenLoaiPhong]) VALUES (N'LP001', N'Phòng 2 người')
INSERT [dbo].[LoaiPhong] ([maLoaiPhong], [tenLoaiPhong]) VALUES (N'LP002', N'Phòng 4 người')
INSERT [dbo].[LoaiPhong] ([maLoaiPhong], [tenLoaiPhong]) VALUES (N'LP003', N'Phòng VIP')
INSERT [dbo].[LoaiPhong] ([maLoaiPhong], [tenLoaiPhong]) VALUES (N'LP004', N'Ngừng hoạt động')
GO
INSERT [dbo].[MatHang] ([maMH], [maLoaiMH], [tenMH], [soLuongMH], [giaMH]) VALUES (N'MH001', N'LMH003', N'Khăn giấy', 4980, 5000)
INSERT [dbo].[MatHang] ([maMH], [maLoaiMH], [tenMH], [soLuongMH], [giaMH]) VALUES (N'MH002', N'LMH001', N'Bia Heniken Silver', 600, 45000)
INSERT [dbo].[MatHang] ([maMH], [maLoaiMH], [tenMH], [soLuongMH], [giaMH]) VALUES (N'MH003', N'LMH001', N'Bia Heniken', 1190, 40000)
INSERT [dbo].[MatHang] ([maMH], [maLoaiMH], [tenMH], [soLuongMH], [giaMH]) VALUES (N'MH004', N'LMH001', N'Bia Tiger Silver', 623, 35000)
INSERT [dbo].[MatHang] ([maMH], [maLoaiMH], [tenMH], [soLuongMH], [giaMH]) VALUES (N'MH005', N'LMH001', N'Bia Tiger', 2400, 30000)
INSERT [dbo].[MatHang] ([maMH], [maLoaiMH], [tenMH], [soLuongMH], [giaMH]) VALUES (N'MH006', N'LMH001', N'Bia Sài Gòn', 2400, 28000)
INSERT [dbo].[MatHang] ([maMH], [maLoaiMH], [tenMH], [soLuongMH], [giaMH]) VALUES (N'MH007', N'LMH001', N'Bia Ruby', 960, 25000)
INSERT [dbo].[MatHang] ([maMH], [maLoaiMH], [tenMH], [soLuongMH], [giaMH]) VALUES (N'MH008', N'LMH001', N'Coca Cola', 1196, 20000)
INSERT [dbo].[MatHang] ([maMH], [maLoaiMH], [tenMH], [soLuongMH], [giaMH]) VALUES (N'MH009', N'LMH001', N'Pepsi', 1165, 20000)
INSERT [dbo].[MatHang] ([maMH], [maLoaiMH], [tenMH], [soLuongMH], [giaMH]) VALUES (N'MH010', N'LMH001', N'StrongBow táo', 480, 35000)
INSERT [dbo].[MatHang] ([maMH], [maLoaiMH], [tenMH], [soLuongMH], [giaMH]) VALUES (N'MH011', N'LMH001', N'StrongBow mật ong', 465, 35000)
INSERT [dbo].[MatHang] ([maMH], [maLoaiMH], [tenMH], [soLuongMH], [giaMH]) VALUES (N'MH012', N'LMH001', N'StrongBow dâu', 100493, 35000)
INSERT [dbo].[MatHang] ([maMH], [maLoaiMH], [tenMH], [soLuongMH], [giaMH]) VALUES (N'MH013', N'LMH001', N'StrongBow dâu đen', 475, 35000)
INSERT [dbo].[MatHang] ([maMH], [maLoaiMH], [tenMH], [soLuongMH], [giaMH]) VALUES (N'MH014', N'LMH002', N'Trái cây tổng hợp', 33, 80000)
INSERT [dbo].[MatHang] ([maMH], [maLoaiMH], [tenMH], [soLuongMH], [giaMH]) VALUES (N'MH016', N'LMH002', N'Bánh Snack', 185, 20000)
INSERT [dbo].[MatHang] ([maMH], [maLoaiMH], [tenMH], [soLuongMH], [giaMH]) VALUES (N'MH017', N'LMH001', N'Không độ', 478, 20000)
INSERT [dbo].[MatHang] ([maMH], [maLoaiMH], [tenMH], [soLuongMH], [giaMH]) VALUES (N'MH018', N'LMH001', N'Trà ô long', 1200, 25000)
INSERT [dbo].[MatHang] ([maMH], [maLoaiMH], [tenMH], [soLuongMH], [giaMH]) VALUES (N'MH019', N'LMH001', N'Wake Up 247', 1200, 25000)
INSERT [dbo].[MatHang] ([maMH], [maLoaiMH], [tenMH], [soLuongMH], [giaMH]) VALUES (N'MH020', N'LMH001', N'Bò húc ', 1200, 30000)
INSERT [dbo].[MatHang] ([maMH], [maLoaiMH], [tenMH], [soLuongMH], [giaMH]) VALUES (N'MH021', N'LMH004', N'a', 5, 1)
INSERT [dbo].[MatHang] ([maMH], [maLoaiMH], [tenMH], [soLuongMH], [giaMH]) VALUES (N'MH022', N'LMH004', N'Snack', 500, 5000)
INSERT [dbo].[MatHang] ([maMH], [maLoaiMH], [tenMH], [soLuongMH], [giaMH]) VALUES (N'MH023', N'LMH002', N'BBQ', 100, 100000)
GO
INSERT [dbo].[NhanVien] ([maNhanVien], [maTK], [tenNhanVien], [chucVu], [gioiTinh], [ngaySinh], [diaChi], [sdt], [cccd], [luong]) VALUES (N'NV002', N'NV002', N'Nguyễn Văn Trinh', N'Quản lý', N'Nam', CAST(N'2001-04-08' AS Date), N'168 đường số 17 phường 11 gò vâp', N'0386200961', N'215557688', 50000)
GO
INSERT [dbo].[Phong] ([maPhong], [maLoaiPhong], [tinhTrangPhong], [giaPhong]) VALUES (N'P001', N'LP001', N'Trống', 100000)
INSERT [dbo].[Phong] ([maPhong], [maLoaiPhong], [tinhTrangPhong], [giaPhong]) VALUES (N'P002', N'LP001', N'Trống', 100000)
INSERT [dbo].[Phong] ([maPhong], [maLoaiPhong], [tinhTrangPhong], [giaPhong]) VALUES (N'P003', N'LP002', N'Trống', 150000)
INSERT [dbo].[Phong] ([maPhong], [maLoaiPhong], [tinhTrangPhong], [giaPhong]) VALUES (N'P004', N'LP003', N'Trống', 200000)
INSERT [dbo].[Phong] ([maPhong], [maLoaiPhong], [tinhTrangPhong], [giaPhong]) VALUES (N'P005', N'LP002', N'Đã đặt', 150000)
INSERT [dbo].[Phong] ([maPhong], [maLoaiPhong], [tinhTrangPhong], [giaPhong]) VALUES (N'P006', N'LP002', N'Trống', 150000)
INSERT [dbo].[Phong] ([maPhong], [maLoaiPhong], [tinhTrangPhong], [giaPhong]) VALUES (N'P007', N'LP003', N'Trống', 200000)
INSERT [dbo].[Phong] ([maPhong], [maLoaiPhong], [tinhTrangPhong], [giaPhong]) VALUES (N'P008', N'LP001', N'Trống', 100000)
INSERT [dbo].[Phong] ([maPhong], [maLoaiPhong], [tinhTrangPhong], [giaPhong]) VALUES (N'P009', N'LP002', N'Trống', 150000)
INSERT [dbo].[Phong] ([maPhong], [maLoaiPhong], [tinhTrangPhong], [giaPhong]) VALUES (N'P010', N'LP003', N'Đang hoạt động', 200000)
INSERT [dbo].[Phong] ([maPhong], [maLoaiPhong], [tinhTrangPhong], [giaPhong]) VALUES (N'P011', N'LP003', N'Trống', 200000)
INSERT [dbo].[Phong] ([maPhong], [maLoaiPhong], [tinhTrangPhong], [giaPhong]) VALUES (N'P012', N'LP002', N'Trống', 150000)
INSERT [dbo].[Phong] ([maPhong], [maLoaiPhong], [tinhTrangPhong], [giaPhong]) VALUES (N'P013', N'LP001', N'Trống', 100000)
INSERT [dbo].[Phong] ([maPhong], [maLoaiPhong], [tinhTrangPhong], [giaPhong]) VALUES (N'P014', N'LP002', N'Trống', 150000)
INSERT [dbo].[Phong] ([maPhong], [maLoaiPhong], [tinhTrangPhong], [giaPhong]) VALUES (N'P015', N'LP004', N'Trống', 100000)
INSERT [dbo].[Phong] ([maPhong], [maLoaiPhong], [tinhTrangPhong], [giaPhong]) VALUES (N'P016', N'LP003', N'Trống', 200000)
INSERT [dbo].[Phong] ([maPhong], [maLoaiPhong], [tinhTrangPhong], [giaPhong]) VALUES (N'P017', N'LP001', N'Trống', 100000)
INSERT [dbo].[Phong] ([maPhong], [maLoaiPhong], [tinhTrangPhong], [giaPhong]) VALUES (N'P018', N'LP001', N'Trống', 100000)
INSERT [dbo].[Phong] ([maPhong], [maLoaiPhong], [tinhTrangPhong], [giaPhong]) VALUES (N'P019', N'LP002', N'Trống', 150000)
INSERT [dbo].[Phong] ([maPhong], [maLoaiPhong], [tinhTrangPhong], [giaPhong]) VALUES (N'P020', N'LP002', N'Trống', 150000)
INSERT [dbo].[Phong] ([maPhong], [maLoaiPhong], [tinhTrangPhong], [giaPhong]) VALUES (N'P021', N'LP002', N'Trống', 150000)
INSERT [dbo].[Phong] ([maPhong], [maLoaiPhong], [tinhTrangPhong], [giaPhong]) VALUES (N'P022', N'LP002', N'Trống', 150000)
INSERT [dbo].[Phong] ([maPhong], [maLoaiPhong], [tinhTrangPhong], [giaPhong]) VALUES (N'P023', N'LP003', N'Đang hoạt động', 200000)
INSERT [dbo].[Phong] ([maPhong], [maLoaiPhong], [tinhTrangPhong], [giaPhong]) VALUES (N'P024', N'LP003', N'Trống', 200000)
INSERT [dbo].[Phong] ([maPhong], [maLoaiPhong], [tinhTrangPhong], [giaPhong]) VALUES (N'P025', N'LP003', N'Trống', 200000)
INSERT [dbo].[Phong] ([maPhong], [maLoaiPhong], [tinhTrangPhong], [giaPhong]) VALUES (N'P026', N'LP001', N'Trống', 100000)
INSERT [dbo].[Phong] ([maPhong], [maLoaiPhong], [tinhTrangPhong], [giaPhong]) VALUES (N'P027', N'LP001', N'Trống', 100000)
GO
INSERT [dbo].[TaiKhoan] ([maTK], [matKhau]) VALUES (N'NV002', N'NV002')
INSERT [dbo].[TaiKhoan] ([maTK], [matKhau]) VALUES (N'NV003', N'NV0030944302210')
GO
SET ANSI_PADDING ON
GO
/****** Object:  Index [IX_NhanVien]    Script Date: 05/14/2022 11:29:32 AM ******/
ALTER TABLE [dbo].[NhanVien] ADD  CONSTRAINT [IX_NhanVien] UNIQUE NONCLUSTERED 
(
	[maTK] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, IGNORE_DUP_KEY = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
GO
ALTER TABLE [dbo].[CTDDP]  WITH CHECK ADD  CONSTRAINT [FK_CTDDP_DonDatPhong] FOREIGN KEY([maDDP])
REFERENCES [dbo].[DonDatPhong] ([maDDP])
GO
ALTER TABLE [dbo].[CTDDP] CHECK CONSTRAINT [FK_CTDDP_DonDatPhong]
GO
ALTER TABLE [dbo].[CTDDP]  WITH CHECK ADD  CONSTRAINT [FK_CTDDP_MatHang] FOREIGN KEY([maMH])
REFERENCES [dbo].[MatHang] ([maMH])
GO
ALTER TABLE [dbo].[CTDDP] CHECK CONSTRAINT [FK_CTDDP_MatHang]
GO
ALTER TABLE [dbo].[CTHD]  WITH CHECK ADD  CONSTRAINT [FK_CTHD_HoaDon] FOREIGN KEY([maHD])
REFERENCES [dbo].[HoaDon] ([maHD])
GO
ALTER TABLE [dbo].[CTHD] CHECK CONSTRAINT [FK_CTHD_HoaDon]
GO
ALTER TABLE [dbo].[CTHD]  WITH CHECK ADD  CONSTRAINT [FK_CTHD_MatHang] FOREIGN KEY([maMH])
REFERENCES [dbo].[MatHang] ([maMH])
GO
ALTER TABLE [dbo].[CTHD] CHECK CONSTRAINT [FK_CTHD_MatHang]
GO
ALTER TABLE [dbo].[DonDatPhong]  WITH CHECK ADD  CONSTRAINT [FK_DonDatPhong_KhachHang] FOREIGN KEY([maKH])
REFERENCES [dbo].[KhachHang] ([maKhachHang])
ON UPDATE CASCADE
ON DELETE CASCADE
GO
ALTER TABLE [dbo].[DonDatPhong] CHECK CONSTRAINT [FK_DonDatPhong_KhachHang]
GO
ALTER TABLE [dbo].[DonDatPhong]  WITH CHECK ADD  CONSTRAINT [FK_DonDatPhong_NhanVien] FOREIGN KEY([maNhanVien])
REFERENCES [dbo].[NhanVien] ([maNhanVien])
ON UPDATE CASCADE
ON DELETE CASCADE
GO
ALTER TABLE [dbo].[DonDatPhong] CHECK CONSTRAINT [FK_DonDatPhong_NhanVien]
GO
ALTER TABLE [dbo].[DonDatPhong]  WITH CHECK ADD  CONSTRAINT [FK_DonDatPhong_Phong] FOREIGN KEY([maPhong])
REFERENCES [dbo].[Phong] ([maPhong])
ON UPDATE CASCADE
ON DELETE CASCADE
GO
ALTER TABLE [dbo].[DonDatPhong] CHECK CONSTRAINT [FK_DonDatPhong_Phong]
GO
ALTER TABLE [dbo].[HoaDon]  WITH CHECK ADD  CONSTRAINT [FK_HoaDon_KhachHang] FOREIGN KEY([maKH])
REFERENCES [dbo].[KhachHang] ([maKhachHang])
ON UPDATE CASCADE
ON DELETE CASCADE
GO
ALTER TABLE [dbo].[HoaDon] CHECK CONSTRAINT [FK_HoaDon_KhachHang]
GO
ALTER TABLE [dbo].[HoaDon]  WITH CHECK ADD  CONSTRAINT [FK_HoaDon_NhanVien] FOREIGN KEY([maNhanVien])
REFERENCES [dbo].[NhanVien] ([maNhanVien])
ON UPDATE CASCADE
ON DELETE CASCADE
GO
ALTER TABLE [dbo].[HoaDon] CHECK CONSTRAINT [FK_HoaDon_NhanVien]
GO
ALTER TABLE [dbo].[HoaDon]  WITH CHECK ADD  CONSTRAINT [FK_HoaDon_Phong] FOREIGN KEY([maPhong])
REFERENCES [dbo].[Phong] ([maPhong])
ON UPDATE CASCADE
ON DELETE CASCADE
GO
ALTER TABLE [dbo].[HoaDon] CHECK CONSTRAINT [FK_HoaDon_Phong]
GO
ALTER TABLE [dbo].[KhachHang]  WITH CHECK ADD  CONSTRAINT [FK_KhachHang_LoaiKH] FOREIGN KEY([maLoaiKH])
REFERENCES [dbo].[LoaiKH] ([maLoaiKH])
ON UPDATE CASCADE
ON DELETE CASCADE
GO
ALTER TABLE [dbo].[KhachHang] CHECK CONSTRAINT [FK_KhachHang_LoaiKH]
GO
ALTER TABLE [dbo].[MatHang]  WITH CHECK ADD  CONSTRAINT [FK_MatHang_LoaiMatHang] FOREIGN KEY([maLoaiMH])
REFERENCES [dbo].[LoaiMatHang] ([maLoaiMH])
ON UPDATE CASCADE
ON DELETE CASCADE
GO
ALTER TABLE [dbo].[MatHang] CHECK CONSTRAINT [FK_MatHang_LoaiMatHang]
GO
ALTER TABLE [dbo].[NhanVien]  WITH CHECK ADD  CONSTRAINT [FK_NhanVien_TaiKhoan1] FOREIGN KEY([maTK])
REFERENCES [dbo].[TaiKhoan] ([maTK])
GO
ALTER TABLE [dbo].[NhanVien] CHECK CONSTRAINT [FK_NhanVien_TaiKhoan1]
GO
ALTER TABLE [dbo].[Phong]  WITH CHECK ADD  CONSTRAINT [FK_Phong_LoaiPhong] FOREIGN KEY([maLoaiPhong])
REFERENCES [dbo].[LoaiPhong] ([maLoaiPhong])
ON UPDATE CASCADE
ON DELETE CASCADE
GO
ALTER TABLE [dbo].[Phong] CHECK CONSTRAINT [FK_Phong_LoaiPhong]
GO
USE [master]
GO
ALTER DATABASE [QLyDatPhong] SET  READ_WRITE 
GO
