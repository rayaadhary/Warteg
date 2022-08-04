-- phpMyAdmin SQL Dump
-- version 5.1.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Aug 04, 2022 at 05:28 AM
-- Server version: 10.4.22-MariaDB
-- PHP Version: 8.1.1

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `db10120182restoran`
--

-- --------------------------------------------------------

--
-- Table structure for table `detail_transaksi`
--

CREATE TABLE `detail_transaksi` (
  `no_transaksi` int(11) DEFAULT NULL,
  `id_makanan` varchar(5) DEFAULT NULL,
  `jmlh_makanan` int(11) DEFAULT NULL,
  `id_minuman` varchar(5) DEFAULT NULL,
  `jmlh_minuman` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `detail_transaksi`
--

INSERT INTO `detail_transaksi` (`no_transaksi`, `id_makanan`, `jmlh_makanan`, `id_minuman`, `jmlh_minuman`) VALUES
(7, 'M0002', 2, 'A0001', 2),
(8, 'M0001', 2, 'A0001', 1),
(8, 'M0001', 2, 'A0001', 1),
(22, 'M0002', 2, 'A0001', 2),
(23, 'M0002', 2, 'A0001', 1),
(24, 'M0003', 10, 'A0001', 2),
(25, 'M0003', 3, 'A0001', 10),
(25, 'M0003', 3, 'A0001', 10),
(27, 'M0005', 2, 'A0001', 2),
(28, 'M0005', 11, 'A0001', 11),
(29, 'M0005', 5, 'A0001', 3),
(30, 'M0002', 2, 'A0001', 2),
(31, 'M0003', 3, 'A0001', 1),
(32, 'M0002', 2, 'A0001', 1),
(33, 'M0001', 2, 'A0001', 1),
(34, 'M0001', 2, 'A0001', 1),
(35, 'M0003', 2, 'A0001', 1);

-- --------------------------------------------------------

--
-- Table structure for table `kasir`
--

CREATE TABLE `kasir` (
  `id_kasir` varchar(5) NOT NULL,
  `nm_kasir` varchar(20) DEFAULT NULL,
  `password` varchar(50) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `kasir`
--

INSERT INTO `kasir` (`id_kasir`, `nm_kasir`, `password`) VALUES
('admin', 'admin', '*4ACFE3202A5FF5CF467898FC58AAB1D615029441'),
('K0001', 'Tina Martil', '*3F6651AB66B3E6F65EDBE47C37FB58DCCEB741E5'),
('K0002', 'Titin Martini', '*B756764DFF9EE0EE8EC7E04978C27DDAEDC2A0E2'),
('K0003', 'Zahra', '*7AE5676ABB6CD6492641D48F205ADF92C7034C3C'),
('K0004', 'Ramli', '*67BE9BEC0691EE20F815971B76A4134B7DD1FA1A'),
('K0005', 'Romlan', '*0E29605103C71EBA90E601F4104CC1C7CBAB7985'),
('K0006', 'Malik', '*F6D162211962D6C07BEA52681F963B4F047F7912'),
('K0007', 'Indri', '*CC66D38EA0CC34C8104C5A3978580798F06AE431'),
('K0008', 'Deni', '*105CBFDFDCA7B125757CEBE8DB9D38ECA2475C42'),
('K0009', 'Dami', '*0FEBD21AA031D656B74EE050A83ADEDBCC3D8D72'),
('K0010', 'Ganda', '*6D9652377F9EB8C5EDF7F567F1052CC920D63A09'),
('K0011', 'Titin', '*7181EF9CEDAB18E5B544CD1677E66B3BF7AEFAA5'),
('K0012', 'Martin', '*B1211CA74707D9B9885D41CE3AEC952FC808A73C'),
('K0013', 'Ruslan', '*96D8CA8E0D5CF0DF6A6750FA6461D9BEFFED4A3D'),
('K0014', 'Otto', '*1DF1A3A98C13FDEA54CA311F6A941A466F7C3344'),
('K0015', 'Novi', '*7FBFA397B4728893EB5075CED1D03BE3E1453490'),
('K0016', 'Devi', '*67665A9625B397C0B5869C9386E43FC74764C098'),
('K0017', 'Krisna', '*6803ABE90C0BAEFE29508969507B9B2B8654CC03'),
('K0018', 'Utami', '*AF8929B56AE3613F1CBE43E8186E47ABD0B176BA'),
('K0019', 'Fadli', '*826942873060B35A6AF07553E09E3B3AB0A3FE8D'),
('K0020', 'Yaya', '*8B549322F276FF39BE39C65AB25C129AD444B32F'),
('K0021', 'Xavier', '*42EE1440B257C9F4A29B9C224960131099A2B8B1'),
('K0022', 'Zeta', '*834BE959B0451AA9E1FD946D50EB3EF4502C2C26'),
('K0023', 'Kaka', '*8FB8022523173EA01D112A7B7B9AA504D14F3B53'),
('K0024', 'Oman', '*F2A2D7C52E9646EAAEE8601095F18BE4080E2462'),
('K0025', 'Jajang', '*0C088FF91682FD87706C5FD095E13241C5C93945');

-- --------------------------------------------------------

--
-- Table structure for table `menu_makanan`
--

CREATE TABLE `menu_makanan` (
  `id_makanan` varchar(5) NOT NULL,
  `nm_makanan` varchar(15) DEFAULT NULL,
  `harga_makanan` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `menu_makanan`
--

INSERT INTO `menu_makanan` (`id_makanan`, `nm_makanan`, `harga_makanan`) VALUES
('M0001', 'Telur', 3000),
('M0002', 'Ayam Goreng', 6000),
('M0003', 'Rolade', 2500),
('M0004', 'Ayam Geprek', 10000),
('M0005', 'Sayur Sop', 3000),
('M0006', 'Sop Iga', 15000),
('M0007', 'Kentang Balado', 2000),
('M0008', 'Ati Ampela', 4000),
('M0009', 'Perkedel Kentan', 2000),
('M0010', 'Perkedel Jagung', 2000),
('M0011', 'Terong', 2000),
('M0012', 'Mustofa', 3000),
('M0013', 'Orek Tempe', 3000),
('M0014', 'Asin', 2000),
('M0015', 'Tempe Goreng', 1500),
('M0016', 'Tahu Goreng', 1500),
('M0017', 'Babi Guling', 20000),
('M0018', 'Gulai sapi', 15000),
('M0019', 'Gepuk', 5000),
('M0020', 'Kere', 3000),
('M0021', 'Usus Goreng', 2000),
('M0022', 'Mie Goreng', 3000),
('M0023', 'Bihun Goreng', 3000),
('M0024', 'Capcay', 3000),
('M0025', 'Tempe Bacem', 2500);

-- --------------------------------------------------------

--
-- Table structure for table `menu_minuman`
--

CREATE TABLE `menu_minuman` (
  `id_minuman` varchar(5) NOT NULL,
  `nm_minuman` varchar(15) DEFAULT NULL,
  `harga_minuman` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `menu_minuman`
--

INSERT INTO `menu_minuman` (`id_minuman`, `nm_minuman`, `harga_minuman`) VALUES
('', NULL, NULL),
('A0001', 'Es Teh Manis', 3000),
('A0003', 'Kopi susu', 4000),
('A0004', 'Susu putih', 3000),
('A0005', 'Susu coklat', 3000),
('A0006', 'STMJ', 3000),
('A0007', 'Teh tarik', 4000),
('A0008', 'Susu jahe', 4000),
('A0009', 'Bandrek', 4000),
('A0010', 'Bajigur', 4000),
('A0011', 'Wedang jahe', 4000),
('A0012', 'Nutrisari jeruk', 3000),
('A0013', 'Energen', 4000),
('A0014', 'Ekstrajoss', 4000),
('A0015', 'Es kelapa', 5000),
('A0016', 'Teh lemon', 4000),
('A0017', 'Soda', 3000),
('A0018', 'Susu soda', 4000),
('A0019', 'Jus alpuket', 6000),
('A0020', 'Jus mangga', 6000),
('A0021', 'Jus stroberi', 6000),
('A0022', 'Jus jambu', 6000),
('A0023', 'Es cendol', 5000),
('A0024', 'Cincau', 5000),
('A0025', 'Coklat panas', 5000);

-- --------------------------------------------------------

--
-- Table structure for table `pelanggan`
--

CREATE TABLE `pelanggan` (
  `id_pelanggan` int(11) NOT NULL,
  `nm_pelanggan` varchar(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `pelanggan`
--

INSERT INTO `pelanggan` (`id_pelanggan`, `nm_pelanggan`) VALUES
(1, 'Raya'),
(2, 'Adhary'),
(3, 'Wayan'),
(4, 'Widi'),
(5, 'Pastika'),
(6, 'Daffa'),
(7, 'Surya'),
(8, 'Mahardhika'),
(9, 'Diaz'),
(10, 'Yasir'),
(11, 'Almarogi'),
(12, 'Rifa'),
(13, 'Said'),
(14, 'Aqil'),
(15, 'Hammam'),
(16, 'Jakabin'),
(17, 'Remi'),
(18, 'Agus'),
(19, 'Lemper'),
(20, 'Asep'),
(21, 'Acep'),
(22, 'Jini'),
(23, 'Mandi'),
(24, 'Maman'),
(25, 'Mika'),
(26, 'Rere'),
(27, 'Amir'),
(28, 'Ahmad'),
(29, 'Mujadih'),
(30, 'Dara');

-- --------------------------------------------------------

--
-- Table structure for table `transaksi`
--

CREATE TABLE `transaksi` (
  `no_transaksi` int(11) NOT NULL,
  `tgl_transaksi` datetime DEFAULT NULL,
  `id_pelanggan` int(11) DEFAULT NULL,
  `id_kasir` varchar(5) DEFAULT NULL,
  `subtotal_makanan` int(11) DEFAULT NULL,
  `subtotal_minuman` int(11) DEFAULT NULL,
  `total` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `transaksi`
--

INSERT INTO `transaksi` (`no_transaksi`, `tgl_transaksi`, `id_pelanggan`, `id_kasir`, `subtotal_makanan`, `subtotal_minuman`, `total`) VALUES
(7, '2022-07-19 17:47:59', 2, 'admin', 12000, 6000, 18000),
(8, '2022-07-19 17:48:30', 3, 'admin', 6000, 3000, 9000),
(20, '2022-07-23 10:56:45', 15, 'admin', 3000, 3000, 6000),
(21, '2022-07-23 10:56:45', 15, 'admin', 3000, 3000, 6000),
(22, '2022-07-23 10:58:45', 17, 'admin', 12000, 6000, 18000),
(23, '2022-07-23 11:04:59', 18, 'admin', 12000, 3000, 15000),
(24, '2022-07-23 12:04:49', 19, 'admin', 25000, 6000, 31000),
(25, '2022-07-23 12:05:41', 20, 'admin', 7500, 30000, 37500),
(26, '2022-07-23 12:05:58', 20, 'admin', 7500, 30000, 37500),
(27, '2022-07-23 12:06:24', 22, 'admin', 6000, 6000, 12000),
(28, '2022-07-23 12:10:40', 23, 'admin', 33000, 33000, 66000),
(29, '2022-07-23 12:10:54', 23, 'admin', 15000, 9000, 24000),
(30, '2022-07-23 12:13:11', 25, 'admin', 12000, 6000, 18000),
(31, '2022-07-23 12:17:18', 26, 'admin', 7500, 3000, 10500),
(32, '2022-07-23 12:18:44', 27, 'admin', 12000, 3000, 15000),
(33, '2022-07-23 12:21:54', 28, 'admin', 6000, 3000, 9000),
(34, '2022-07-23 12:22:47', 29, 'admin', 6000, 3000, 9000),
(35, '2022-07-23 12:24:33', 30, 'admin', 5000, 3000, 8000);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `detail_transaksi`
--
ALTER TABLE `detail_transaksi`
  ADD KEY `id_makanan` (`id_makanan`),
  ADD KEY `id_minuman` (`id_minuman`),
  ADD KEY `no_transaksi` (`no_transaksi`);

--
-- Indexes for table `kasir`
--
ALTER TABLE `kasir`
  ADD PRIMARY KEY (`id_kasir`);

--
-- Indexes for table `menu_makanan`
--
ALTER TABLE `menu_makanan`
  ADD PRIMARY KEY (`id_makanan`);

--
-- Indexes for table `menu_minuman`
--
ALTER TABLE `menu_minuman`
  ADD PRIMARY KEY (`id_minuman`);

--
-- Indexes for table `pelanggan`
--
ALTER TABLE `pelanggan`
  ADD PRIMARY KEY (`id_pelanggan`);

--
-- Indexes for table `transaksi`
--
ALTER TABLE `transaksi`
  ADD PRIMARY KEY (`no_transaksi`),
  ADD KEY `id_kasir` (`id_kasir`),
  ADD KEY `id_pelanggan` (`id_pelanggan`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `pelanggan`
--
ALTER TABLE `pelanggan`
  MODIFY `id_pelanggan` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=31;

--
-- AUTO_INCREMENT for table `transaksi`
--
ALTER TABLE `transaksi`
  MODIFY `no_transaksi` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=36;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `detail_transaksi`
--
ALTER TABLE `detail_transaksi`
  ADD CONSTRAINT `detail_transaksi_ibfk_2` FOREIGN KEY (`id_minuman`) REFERENCES `menu_minuman` (`id_minuman`),
  ADD CONSTRAINT `detail_transaksi_ibfk_4` FOREIGN KEY (`id_makanan`) REFERENCES `menu_makanan` (`id_makanan`),
  ADD CONSTRAINT `detail_transaksi_ibfk_6` FOREIGN KEY (`no_transaksi`) REFERENCES `transaksi` (`no_transaksi`) ON DELETE CASCADE;

--
-- Constraints for table `transaksi`
--
ALTER TABLE `transaksi`
  ADD CONSTRAINT `transaksi_ibfk_1` FOREIGN KEY (`id_kasir`) REFERENCES `kasir` (`id_kasir`),
  ADD CONSTRAINT `transaksi_ibfk_2` FOREIGN KEY (`id_pelanggan`) REFERENCES `pelanggan` (`id_pelanggan`) ON DELETE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
