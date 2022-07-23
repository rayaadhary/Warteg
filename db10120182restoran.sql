-- phpMyAdmin SQL Dump
-- version 5.1.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Jul 23, 2022 at 07:28 AM
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
('admin', 'admin', '*4ACFE3202A5FF5CF467898FC58AAB1D615029441');

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
('M0005', 'Sayur Sop', 3000);

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
('A0001', 'Es Tolol', 3000);

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
(1, ''),
(2, 'Raja'),
(3, 'Hala Jembut'),
(4, 'Hala Jembut'),
(5, 'Joko'),
(6, 'Memen'),
(7, 'Memen'),
(8, 'Memen'),
(9, 'Jaka'),
(10, 'Jaka'),
(11, 'Jaka'),
(12, 'Jaka'),
(13, 'Jaka'),
(14, 'Jaka'),
(15, '2'),
(16, '2'),
(17, ''),
(18, 'Raya'),
(19, ''),
(20, 'Jaka Sembung'),
(21, 'Jaka Sembung'),
(22, 'juno'),
(23, ''),
(24, 'maman gendeng'),
(25, ''),
(26, 'Wayan Wibu'),
(27, 'bobo'),
(28, 'heh'),
(29, 'wibu'),
(30, 'komeng');

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
