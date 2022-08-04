package restoran.utama;

import java.awt.Desktop;
import static java.awt.SystemColor.desktop;
import java.io.File;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JRDesignQuery;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;
import restoran.koneksi.Koneksi;

/**
 *
 * @author adhary
 */
public class formUtama extends javax.swing.JFrame {
    private Connection con;
    private Statement stt;
    private ResultSet rss;
    private PreparedStatement psmt;
    private DefaultTableModel model;
    private String sql;
    private String tanggal;
    
    
    /**
     * Creates new form formUtama
     */
    public formUtama() {
        initComponents();
        
        jumlahKasir();
        pendapatan();
        TampilKasir();
        TampilDaftarTransaksi();
         tampilPelanggan();
        tampilDetailTransaksi();
        TampilMakanan();
        TampilMinuman();
        //TampilTransaksi();
        tampildata2();
        idMakanan();
        tanggalSekarang();
        noTransaksi();
        idPelanggan();
        idMinuman();
    }
    
    private void jumlahKasir() {
        try {
            con = Koneksi.getConnection();
            sql = "select count(id_kasir) from kasir";
            stt = con.createStatement();
            rss = stt.executeQuery(sql);
            
            if(rss.next()) {
                txtKasir.setText(rss.getString("count(id_kasir)"));
            } else {
                txtKasir.setText("0");
            }
        } 
        catch(Exception e)
        {
            e.printStackTrace();
        }
    txtKasir.setEnabled(false);
    }
    
    private void pendapatan() {
        try {
            con = Koneksi.getConnection();
            sql = "select sum(total) from transaksi";
            stt = con.createStatement();
            rss = stt.executeQuery(sql);
            
            if(rss.next()) {
                String total = rss.getString("sum(total)");
                txtPendapatan.setText("Rp "+total);
            } else {
                txtPendapatan.setText("Rp 0");
            }
        } 
        catch(Exception e)
        {
            e.printStackTrace();
        }
    txtPendapatan.setEditable(false);
    }
    
    private void TampilDaftarTransaksi() 
    {
        model = new DefaultTableModel();
        model.addColumn("No. Transaksi");
        model.addColumn("Tanggal");
        model.addColumn("Pelanggan");
        model.addColumn("Kasir");
        model.addColumn("Total");
        
        try {
            con = Koneksi.getConnection();
            sql = "select transaksi.no_transaksi, tgl_transaksi, nm_pelanggan, nm_kasir, total "
                    +"from transaksi, pelanggan, kasir "
                    +"where transaksi.id_pelanggan = pelanggan.id_pelanggan "
                    +"and transaksi.id_kasir = kasir.id_kasir";
            stt = con.createStatement();
            rss = stt.executeQuery(sql);            
            while(rss.next()) {
                model.addRow(new Object[] {
                    rss.getString(1),
                    rss.getString(2),
                    rss.getString(3),
                    rss.getString(4),                    
                    rss.getString(5)
                });
            }
        t_DaftarTransaksi.setModel(model);
        } 
        catch(Exception e)
        {
            JOptionPane.showMessageDialog(null, "error karena : "+e);
        }
    }
    
    public JTextField id_kasir(){
        return txtIdKasir;
    }
    
      private void tampilPelanggan() 
    {
        model = new DefaultTableModel();
        model.addColumn("ID Pelanggan");
        model.addColumn("Nama Pelanggan");
        
        try {
            con = Koneksi.getConnection();
            sql = "select * from pelanggan";
            stt = con.createStatement();
            rss = stt.executeQuery(sql);            
            while(rss.next()) {                
                model.addRow(new Object[] {                   
                    rss.getString(1),
                    rss.getString(2)
                });
            }
        tabelPelanggan.setModel(model);
        } 
        catch(Exception e)
        {
            JOptionPane.showMessageDialog(null, "error karena : "+e);
        }
    }
    
    private void tampilDetailTransaksi() 
    {
        model = new DefaultTableModel();
        model.addColumn("No. Transaksi");
        model.addColumn("Makanan");
        model.addColumn("Jumlah Makanan");
        model.addColumn("Minuman");
        model.addColumn("Jumlah Minuman");
        
        try {
            con = Koneksi.getConnection();
            sql = "select detail_transaksi.no_transaksi, nm_makanan, jmlh_makanan, nm_minuman, jmlh_minuman from detail_transaksi "
                    + "join menu_makanan on detail_transaksi.id_makanan = menu_makanan.id_makanan "
                    + "join menu_minuman on detail_transaksi.id_minuman = menu_minuman.id_minuman";
            stt = con.createStatement();
            rss = stt.executeQuery(sql);            
            while(rss.next()) {                
                model.addRow(new Object[] {                   
                    rss.getString(1),
                    rss.getString(2),
                    rss.getString(3),
                    rss.getString(4),
                    rss.getString(5)
                });
            }
        tabelDetailTransaksi.setModel(model);
        } 
        catch(Exception e)
        {
            JOptionPane.showMessageDialog(null, "error karena : "+e);
        }
    }
    
    private void TampilMakanan()
    {
        model = new DefaultTableModel();
        model.addColumn("id_makanan");
        model.addColumn("Nama Makanan");
        model.addColumn("harga");
        
        try {
                con = Koneksi.getConnection();
                sql = "select * from menu_makanan";
                stt = con.createStatement();
                rss = stt.executeQuery(sql);
                while(rss.next()){
                    model.addRow(new Object[]
                    {
                        rss.getString(1),
                        rss.getString(2),
                        rss.getString(3)
                    });
                }
            tableMakanan.setModel(model);
        } 
        catch(Exception e)
        {
            JOptionPane.showMessageDialog(null, "error karena : "+e);
        }
//        disable button
            btnTambahMakanan.setEnabled(true);
            btnUbahMakanan.setEnabled(false);
            btnHapusMakanan.setEnabled(false);
    }
    
    private void TampilMinuman()
    {
        model = new DefaultTableModel();
        model.addColumn("id_minuman");
        model.addColumn("Nama Minuman");
        model.addColumn("harga");
        
        try {
                con = Koneksi.getConnection();
                 sql = "select * from menu_minuman";
                 stt = con.createStatement();
                   rss = stt.executeQuery(sql);
                while(rss.next()){
                model.addRow(new Object[]
                {
                    rss.getString(1),
                    rss.getString(2),
                    rss.getString(3)
                });
                }
            tableMinuman.setModel(model);
        } 
        catch(Exception e)
        {
            JOptionPane.showMessageDialog(null, "error karena : "+e);
        }
        btnTambahMinuman.setEnabled(true);
        btnUbahMakanan.setEnabled(false);
        btnHapusMakanan.setEnabled(false);
    }
    
        private void TampilTransaksi()
    {
        model = new DefaultTableModel();
        model.addColumn("no_transaksi");
        model.addColumn("Tanggal");
        model.addColumn("nama makanan");
        model.addColumn("nama minuman");
        model.addColumn("harga makanan");
        model.addColumn("harga minuman");
        model.addColumn("Sub Total Makanan");
        model.addColumn("Sub Total Minuman");
        
        try {
                con = Koneksi.getConnection();
                sql = "SELECT transaksi.no_transaksi, tgl_transaksi, nm_makanan, nm_minuman, harga_makanan, harga_minuman, subtotal_makanan, subtotal_minuman "
                        + "FROM transaksi, detail_transaksi, menu_makanan, menu_minuman "
                        + "WHERE transaksi.no_transaksi = detail_transaksi.no_transaksi"
                        + " AND detail_transaksi.id_makanan = menu_makanan.id_makanan "
                        + "AND detail_transaksi.id_minuman = menu_minuman.id_minuman";                
                stt = con.createStatement();
                rss = stt.executeQuery(sql);
                while(rss.next()){
                    model.addRow(new Object[]
                    {
                        rss.getString(1),
                        rss.getString(2),
                        rss.getString(3),
                        rss.getString(4),
                        rss.getString(5),
                        rss.getString(6),
                        rss.getString(7),
                        rss.getString(8),
                    });
                }
            tabelTransaksi.setModel(model);
        } 
        catch(Exception e)
        {
            JOptionPane.showMessageDialog(null, "error karena : "+e);
        }
//        disable button
            btnTambahMakanan.setEnabled(true);
            btnUbahMakanan.setEnabled(false);
            btnHapusMakanan.setEnabled(false);
    }
        
        private void tampildata2() {
            try {
                DefaultTableModel TM = (DefaultTableModel) tabelTransaksi.getModel();
            TM.setRowCount(0);
            con = Koneksi.getConnection();
            sql = "SELECT transaksi.no_transaksi, tgl_transaksi, nm_makanan, nm_minuman, harga_makanan, harga_minuman, subtotal_makanan, subtotal_minuman " 
                      + "FROM transaksi, detail_transaksi, menu_makanan, menu_minuman " 
                        + "WHERE transaksi.no_transaksi = detail_transaksi.no_transaksi " 
                        + " AND detail_transaksi.id_makanan = menu_makanan.id_makanan " 
                        + "AND detail_transaksi.id_minuman = menu_minuman.id_minuman";                
                stt = con.createStatement();
                rss = stt.executeQuery(sql);
            while (rss.next()) {
                String no = rss.getString("no_transaksi");
                String tgl = rss.getString("tgl_transaksi");
                String nm_makan = rss.getString("nm_makanan");
                String nm_minum = rss.getString("nm_minuman");
                String hrg_makan = rss.getString("harga_makanan");
                String hrg_minum = rss.getString("harga_minuman");
                String sub_makan = rss.getString("subtotal_makanan");
                String sub_minum = rss.getString("subtotal_minuman");
                TM.addRow(new Object[]{no, tgl, nm_makan, nm_minum, hrg_makan, hrg_minum, sub_makan, sub_minum});
            }
            } catch (Exception e) {
            }
            

    }
      
    public void bersihkanInput()
    {
        txtIdMakanan.setText("");
        txtNamaMakanan.setText("");
        txtHargaMakanan.setText("");
        txtIdMinuman.setText("");
        txtNamaMinuman.setText("");
        txtHargaMinuman.setText("");
        txtIdMakanan.setEnabled(true);
        txtIdMinuman.setEnabled(true);
    }
    
    private void TampilKasir()
    {
        model = new DefaultTableModel();
        model.addColumn("id_kasir");
        model.addColumn("nm_kasir");
        model.addColumn("password");
        
        try {
                con = Koneksi.getConnection();
                 sql = "select * from kasir";
                 stt = con.createStatement();
                   rss = stt.executeQuery(sql);
                while(rss.next()){
                model.addRow(new Object[]
                {
                    rss.getString(1),
                    rss.getString(2),
                    rss.getString(3),
                });
                }
            tabelKasir.setModel(model);
        } 
        catch(Exception e)
        {
            JOptionPane.showMessageDialog(null, "error karena : "+e);
        }
        btnTambahKasir.setEnabled(true);
        btnEditKasir.setEnabled(false);
        btnHapusKasir.setEnabled(false);
    }
    
    public void bersihKasir()
    {
        txtId.setText("");
        txtNamaKasir.setText("");
        txtPass.setText("");
        txtIdKasir.setEnabled(true);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane5 = new javax.swing.JScrollPane();
        jTable5 = new javax.swing.JTable();
        jScrollPane6 = new javax.swing.JScrollPane();
        jTable6 = new javax.swing.JTable();
        jDialog1 = new javax.swing.JDialog();
        jPanel11 = new javax.swing.JPanel();
        jLabel31 = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();
        jDialogKasir = new javax.swing.JDialog();
        jPanel3 = new javax.swing.JPanel();
        jLabel32 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jDialogKasirUbah = new javax.swing.JDialog();
        jPanel10 = new javax.swing.JPanel();
        jLabel33 = new javax.swing.JLabel();
        jButton3 = new javax.swing.JButton();
        dashboard = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        txtKasir = new javax.swing.JTextField();
        txtPendapatan = new javax.swing.JTextField();
        jLabel28 = new javax.swing.JLabel();
        jLabel29 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        t_DaftarTransaksi = new javax.swing.JTable();
        jLabel5 = new javax.swing.JLabel();
        makanan = new javax.swing.JPanel();
        txtIdMakanan = new javax.swing.JTextField();
        txtNamaMakanan = new javax.swing.JTextField();
        txtHargaMakanan = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        btnTambahMakanan = new javax.swing.JButton();
        btnUbahMakanan = new javax.swing.JButton();
        btnHapusMakanan = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tableMakanan = new javax.swing.JTable();
        jPanel8 = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        transaksi = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        txtTransaksi = new javax.swing.JTextField();
        txtTanggal = new javax.swing.JTextField();
        txtIdKasir = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        txtNamaMakananTransaksi = new javax.swing.JTextField();
        txtHargaMakananTransaksi = new javax.swing.JTextField();
        txtJumlahMakanan = new javax.swing.JTextField();
        cmbIdMakanan = new javax.swing.JComboBox<>();
        jPanel9 = new javax.swing.JPanel();
        jLabel23 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        txtNamaMinumanTransaksi = new javax.swing.JTextField();
        txtHargaMinumanTransaksi = new javax.swing.JTextField();
        txtJumlahMinuman = new javax.swing.JTextField();
        cmbIdMinuman = new javax.swing.JComboBox<>();
        btnBeli = new javax.swing.JButton();
        btnTambahTransaksi = new javax.swing.JButton();
        cetakStruk = new javax.swing.JButton();
        jLabel27 = new javax.swing.JLabel();
        txtTotal = new javax.swing.JTextField();
        jLabel30 = new javax.swing.JLabel();
        txtNmPelanggan = new javax.swing.JTextField();
        txtIdPelanggan = new javax.swing.JTextField();
        jScrollPane7 = new javax.swing.JScrollPane();
        minuman = new javax.swing.JPanel();
        jPanel7 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        txtIdMinuman = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        txtNamaMinuman = new javax.swing.JTextField();
        txtHargaMinuman = new javax.swing.JTextField();
        btnTambahMinuman = new javax.swing.JButton();
        btnUbahMinuman = new javax.swing.JButton();
        btnHapusMinuman = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        tableMinuman = new javax.swing.JTable();
        kasir = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        txtId = new javax.swing.JTextField();
        txtNamaKasir = new javax.swing.JTextField();
        txtPass = new javax.swing.JTextField();
        btnTambahKasir = new javax.swing.JButton();
        btnEditKasir = new javax.swing.JButton();
        btnHapusKasir = new javax.swing.JButton();
        jScrollPane4 = new javax.swing.JScrollPane();
        tabelKasir = new javax.swing.JTable();
        detailTransaksi = new javax.swing.JPanel();
        jPanel14 = new javax.swing.JPanel();
        jLabel36 = new javax.swing.JLabel();
        jLabel37 = new javax.swing.JLabel();
        jScrollPane9 = new javax.swing.JScrollPane();
        tabelDetailTransaksi = new javax.swing.JTable();
        pelanggan = new javax.swing.JPanel();
        jPanel13 = new javax.swing.JPanel();
        jLabel34 = new javax.swing.JLabel();
        jScrollPane8 = new javax.swing.JScrollPane();
        tabelPelanggan = new javax.swing.JTable();
        jLabel35 = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu5 = new javax.swing.JMenu();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem8 = new javax.swing.JMenuItem();
        jMenuItem2 = new javax.swing.JMenuItem();
        cetakLaporan = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenuItem4 = new javax.swing.JMenuItem();
        jMenu3 = new javax.swing.JMenu();
        jMenuItem6 = new javax.swing.JMenuItem();
        jMenuItem7 = new javax.swing.JMenuItem();
        jMenu4 = new javax.swing.JMenu();

        jTable5.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane5.setViewportView(jTable5);

        jTable6.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane6.setViewportView(jTable6);

        jPanel11.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel11.setPreferredSize(new java.awt.Dimension(300, 150));

        jLabel31.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        jLabel31.setText("Data berhasil dihapus !");

        jButton2.setText("Ok");
        jButton2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton2MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addGap(71, 71, 71)
                        .addComponent(jLabel31))
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addGap(101, 101, 101)
                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(76, Short.MAX_VALUE))
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addComponent(jLabel31)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 47, Short.MAX_VALUE)
                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(21, 21, 21))
        );

        javax.swing.GroupLayout jDialog1Layout = new javax.swing.GroupLayout(jDialog1.getContentPane());
        jDialog1.getContentPane().setLayout(jDialog1Layout);
        jDialog1Layout.setHorizontalGroup(
            jDialog1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        jDialog1Layout.setVerticalGroup(
            jDialog1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        jPanel3.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel3.setPreferredSize(new java.awt.Dimension(300, 150));

        jLabel32.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        jLabel32.setText("Data Kasir berhasil ditambahkan");

        jButton1.setText("Ok");
        jButton1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton1MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(40, 40, 40)
                        .addComponent(jLabel32))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(102, 102, 102)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(47, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addComponent(jLabel32)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 49, Short.MAX_VALUE)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(23, 23, 23))
        );

        javax.swing.GroupLayout jDialogKasirLayout = new javax.swing.GroupLayout(jDialogKasir.getContentPane());
        jDialogKasir.getContentPane().setLayout(jDialogKasirLayout);
        jDialogKasirLayout.setHorizontalGroup(
            jDialogKasirLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        jDialogKasirLayout.setVerticalGroup(
            jDialogKasirLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        jPanel10.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel10.setPreferredSize(new java.awt.Dimension(300, 150));

        jLabel33.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        jLabel33.setText("Data Kasir berhasil diubah");

        jButton3.setText("Ok");
        jButton3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton3MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addGap(63, 63, 63)
                        .addComponent(jLabel33))
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addGap(104, 104, 104)
                        .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(64, Short.MAX_VALUE))
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addComponent(jLabel33)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 38, Short.MAX_VALUE)
                .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(23, 23, 23))
        );

        javax.swing.GroupLayout jDialogKasirUbahLayout = new javax.swing.GroupLayout(jDialogKasirUbah.getContentPane());
        jDialogKasirUbah.getContentPane().setLayout(jDialogKasirUbahLayout);
        jDialogKasirUbahLayout.setHorizontalGroup(
            jDialogKasirUbahLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        jDialogKasirUbahLayout.setVerticalGroup(
            jDialogKasirUbahLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("GoWarteg");
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        getContentPane().setLayout(new java.awt.CardLayout());

        dashboard.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        txtKasir.setHorizontalAlignment(javax.swing.JTextField.RIGHT);

        jLabel28.setFont(new java.awt.Font("sansserif", 1, 14)); // NOI18N
        jLabel28.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/kasir.png"))); // NOI18N
        jLabel28.setText("Kasir");

        jLabel29.setFont(new java.awt.Font("sansserif", 1, 14)); // NOI18N
        jLabel29.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/pendapatan.png"))); // NOI18N
        jLabel29.setText("Pendapatan");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtKasir, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel28, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtPendapatan, javax.swing.GroupLayout.DEFAULT_SIZE, 123, Short.MAX_VALUE)
                    .addComponent(jLabel29, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(330, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel28)
                    .addComponent(jLabel29))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtKasir, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtPendapatan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(10, Short.MAX_VALUE))
        );

        dashboard.add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 50, 560, 90));

        jPanel4.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel6.setFont(new java.awt.Font("sansserif", 1, 14)); // NOI18N
        jLabel6.setText("DASHBOARD");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel6)
                .addContainerGap(462, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, 26, Short.MAX_VALUE)
        );

        dashboard.add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 560, 30));

        t_DaftarTransaksi.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane2.setViewportView(t_DaftarTransaksi);

        dashboard.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 200, 560, 270));

        jLabel5.setFont(new java.awt.Font("sansserif", 1, 14)); // NOI18N
        jLabel5.setText("Daftar Transaksi");
        dashboard.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 180, 130, -1));

        getContentPane().add(dashboard, "card2");

        jLabel1.setFont(new java.awt.Font("sansserif", 1, 12)); // NOI18N
        jLabel1.setText("ID Makanan");

        jLabel3.setFont(new java.awt.Font("sansserif", 1, 12)); // NOI18N
        jLabel3.setText("Nama Makanan");

        jLabel4.setFont(new java.awt.Font("sansserif", 1, 12)); // NOI18N
        jLabel4.setText("Harga");

        btnTambahMakanan.setFont(new java.awt.Font("sansserif", 1, 12)); // NOI18N
        btnTambahMakanan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/add.png"))); // NOI18N
        btnTambahMakanan.setText("Tambah");
        btnTambahMakanan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTambahMakananActionPerformed(evt);
            }
        });

        btnUbahMakanan.setFont(new java.awt.Font("sansserif", 1, 12)); // NOI18N
        btnUbahMakanan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/edit.png"))); // NOI18N
        btnUbahMakanan.setText("Ubah");
        btnUbahMakanan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUbahMakananActionPerformed(evt);
            }
        });

        btnHapusMakanan.setFont(new java.awt.Font("sansserif", 1, 12)); // NOI18N
        btnHapusMakanan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/delete.png"))); // NOI18N
        btnHapusMakanan.setText("Hapus");
        btnHapusMakanan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHapusMakananActionPerformed(evt);
            }
        });

        tableMakanan.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tableMakanan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tableMakananMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tableMakanan);

        jPanel8.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel10.setFont(new java.awt.Font("sansserif", 1, 14)); // NOI18N
        jLabel10.setText("Daftar Menu Makanan");

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel10)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel10, javax.swing.GroupLayout.DEFAULT_SIZE, 26, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout makananLayout = new javax.swing.GroupLayout(makanan);
        makanan.setLayout(makananLayout);
        makananLayout.setHorizontalGroup(
            makananLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(makananLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(makananLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 571, Short.MAX_VALUE))
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, makananLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(makananLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(makananLayout.createSequentialGroup()
                        .addGroup(makananLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3)
                            .addComponent(jLabel4)
                            .addComponent(jLabel1))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(makananLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtHargaMakanan, javax.swing.GroupLayout.PREFERRED_SIZE, 193, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtNamaMakanan, javax.swing.GroupLayout.PREFERRED_SIZE, 193, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtIdMakanan, javax.swing.GroupLayout.PREFERRED_SIZE, 193, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(52, 52, 52))
                    .addGroup(makananLayout.createSequentialGroup()
                        .addComponent(btnTambahMakanan, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(36, 36, 36)
                        .addComponent(btnUbahMakanan)
                        .addGap(30, 30, 30)
                        .addComponent(btnHapusMakanan)))
                .addGap(107, 107, 107))
        );
        makananLayout.setVerticalGroup(
            makananLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(makananLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30)
                .addGroup(makananLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(makananLayout.createSequentialGroup()
                        .addGap(31, 31, 31)
                        .addGroup(makananLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(txtNamaMakanan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(makananLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtIdMakanan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel1)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(makananLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4)
                    .addComponent(txtHargaMakanan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(makananLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnTambahMakanan)
                    .addGroup(makananLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnHapusMakanan)
                        .addComponent(btnUbahMakanan)))
                .addGap(39, 39, 39)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 272, Short.MAX_VALUE)
                .addGap(67, 67, 67))
        );

        getContentPane().add(makanan, "card3");

        jPanel6.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel8.setFont(new java.awt.Font("sansserif", 1, 14)); // NOI18N
        jLabel8.setText("TRANSAKSI");

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel8)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, 26, Short.MAX_VALUE)
        );

        jLabel16.setFont(new java.awt.Font("sansserif", 1, 12)); // NOI18N
        jLabel16.setText("No. Transaksi");

        jLabel17.setFont(new java.awt.Font("sansserif", 1, 12)); // NOI18N
        jLabel17.setText("Tanggal");

        jLabel18.setFont(new java.awt.Font("sansserif", 1, 12)); // NOI18N
        jLabel18.setText("ID Kasir");

        txtTransaksi.setEditable(false);

        txtTanggal.setEditable(false);

        txtIdKasir.setEditable(false);

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Menu Makanan"));

        jLabel19.setText("Id Makanan");

        jLabel20.setText("Nama Makanan");

        jLabel21.setText("Harga");

        jLabel22.setText("Jumlah");

        txtNamaMakananTransaksi.setEditable(false);

        txtHargaMakananTransaksi.setEditable(false);

        cmbIdMakanan.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] {"Pilih Makanan"}));
        cmbIdMakanan.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cmbIdMakananItemStateChanged(evt);
            }
        });
        cmbIdMakanan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbIdMakananActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(1, 1, 1)
                                .addComponent(jLabel20))
                            .addComponent(jLabel21)
                            .addComponent(jLabel22))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(txtHargaMakananTransaksi, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtNamaMakananTransaksi)
                            .addComponent(txtJumlahMakanan, javax.swing.GroupLayout.DEFAULT_SIZE, 139, Short.MAX_VALUE))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel19)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(cmbIdMakanan, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel19)
                    .addComponent(cmbIdMakanan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtNamaMakananTransaksi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel20))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtHargaMakananTransaksi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel21))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtJumlahMakanan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel22)))
        );

        jPanel9.setBorder(javax.swing.BorderFactory.createTitledBorder("Menu Minuman"));

        jLabel23.setText("Id Minuman");

        jLabel24.setText("Nama Minuman");

        jLabel25.setText("Harga");

        jLabel26.setText("Jumlah");

        txtNamaMinumanTransaksi.setEditable(false);

        txtHargaMakananTransaksi.setEditable(false);

        cmbIdMinuman.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Pilih Minuman"}));
        cmbIdMinuman.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cmbIdMinumanItemStateChanged(evt);
            }
        });
        cmbIdMinuman.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbIdMinumanActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel9Layout.createSequentialGroup()
                                .addGap(1, 1, 1)
                                .addComponent(jLabel24))
                            .addComponent(jLabel25)
                            .addComponent(jLabel26))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(txtHargaMinumanTransaksi, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtNamaMinumanTransaksi)
                            .addComponent(txtJumlahMinuman, javax.swing.GroupLayout.DEFAULT_SIZE, 139, Short.MAX_VALUE))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addComponent(jLabel23)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(cmbIdMinuman, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel23)
                    .addComponent(cmbIdMinuman, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtNamaMinumanTransaksi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel24))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtHargaMinumanTransaksi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel25))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtJumlahMinuman, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel26)))
        );

        btnBeli.setText("Beli");
        btnBeli.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBeliActionPerformed(evt);
            }
        });

        btnTambahTransaksi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/add.png"))); // NOI18N
        btnTambahTransaksi.setText("Tambah Transaksi");
        btnTambahTransaksi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTambahTransaksiActionPerformed(evt);
            }
        });

        cetakStruk.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/cetak.png"))); // NOI18N
        cetakStruk.setText("Cetak");
        cetakStruk.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cetakStrukActionPerformed(evt);
            }
        });

        jLabel27.setText("Total");

        txtTotal.setText("Rp 0");

        jLabel30.setFont(new java.awt.Font("sansserif", 1, 12)); // NOI18N
        jLabel30.setText("Pelanggan");

        txtIdPelanggan.setEditable(false);

        tabelTransaksi.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "No Transaksi", "Tanggal", "Nama Makanan", "Nama Minuman", "Harga Makanan", "Harga Minuman", "Subtotal Makanan", "Subtotal Minuman", "Hapus"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tabelTransaksi.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabelTransaksiMouseClicked(evt);
            }
        });
        jScrollPane7.setViewportView(tabelTransaksi);

        javax.swing.GroupLayout transaksiLayout = new javax.swing.GroupLayout(transaksi);
        transaksi.setLayout(transaksiLayout);
        transaksiLayout.setHorizontalGroup(
            transaksiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(transaksiLayout.createSequentialGroup()
                .addGap(258, 258, 258)
                .addComponent(btnBeli, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(transaksiLayout.createSequentialGroup()
                .addGroup(transaksiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(transaksiLayout.createSequentialGroup()
                        .addGap(12, 12, 12)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 32, Short.MAX_VALUE))
                    .addGroup(transaksiLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(transaksiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(transaksiLayout.createSequentialGroup()
                                .addGap(11, 11, 11)
                                .addGroup(transaksiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel18)
                                    .addComponent(jLabel17)
                                    .addComponent(jLabel16)
                                    .addComponent(jLabel30))
                                .addGap(36, 36, 36)
                                .addGroup(transaksiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(transaksiLayout.createSequentialGroup()
                                        .addComponent(txtIdPelanggan, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(txtNmPelanggan))
                                    .addComponent(txtTransaksi, javax.swing.GroupLayout.PREFERRED_SIZE, 216, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtTanggal)
                                    .addComponent(txtIdKasir))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btnTambahTransaksi))
                            .addComponent(jSeparator1)))
                    .addGroup(transaksiLayout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(jLabel27)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(cetakStruk))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, transaksiLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane7)))
                .addContainerGap())
        );
        transaksiLayout.setVerticalGroup(
            transaksiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(transaksiLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(transaksiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel16)
                    .addComponent(txtTransaksi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnTambahTransaksi))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(transaksiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtTanggal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel17))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(transaksiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtIdKasir, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel18))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(transaksiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtIdPelanggan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(transaksiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel30)
                        .addComponent(txtNmPelanggan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(8, 8, 8)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(transaksiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnBeli)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane7, javax.swing.GroupLayout.DEFAULT_SIZE, 157, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(transaksiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cetakStruk)
                    .addComponent(jLabel27)
                    .addComponent(txtTotal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        getContentPane().add(transaksi, "card4");

        jPanel7.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel9.setFont(new java.awt.Font("sansserif", 1, 14)); // NOI18N
        jLabel9.setText("Daftar Menu Minuman");

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel9)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, 26, Short.MAX_VALUE)
        );

        jLabel2.setFont(new java.awt.Font("sansserif", 1, 12)); // NOI18N
        jLabel2.setText("ID Minuman");

        jLabel11.setFont(new java.awt.Font("sansserif", 1, 12)); // NOI18N
        jLabel11.setText("Nama Minuman");

        jLabel12.setFont(new java.awt.Font("sansserif", 1, 12)); // NOI18N
        jLabel12.setText("Harga");

        btnTambahMinuman.setFont(new java.awt.Font("sansserif", 1, 12)); // NOI18N
        btnTambahMinuman.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/add.png"))); // NOI18N
        btnTambahMinuman.setText("Tambah");
        btnTambahMinuman.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTambahMinumanActionPerformed(evt);
            }
        });

        btnUbahMinuman.setFont(new java.awt.Font("sansserif", 1, 12)); // NOI18N
        btnUbahMinuman.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/edit.png"))); // NOI18N
        btnUbahMinuman.setText("Ubah");
        btnUbahMinuman.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUbahMinumanActionPerformed(evt);
            }
        });

        btnHapusMinuman.setFont(new java.awt.Font("sansserif", 1, 12)); // NOI18N
        btnHapusMinuman.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/delete.png"))); // NOI18N
        btnHapusMinuman.setText("Hapus");
        btnHapusMinuman.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHapusMinumanActionPerformed(evt);
            }
        });

        tableMinuman.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tableMinuman.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tableMinumanMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(tableMinuman);

        javax.swing.GroupLayout minumanLayout = new javax.swing.GroupLayout(minuman);
        minuman.setLayout(minumanLayout);
        minumanLayout.setHorizontalGroup(
            minumanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(minumanLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(minumanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane3)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, minumanLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(minumanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(minumanLayout.createSequentialGroup()
                                .addGap(8, 8, 8)
                                .addGroup(minumanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel11)
                                    .addComponent(jLabel12)
                                    .addComponent(jLabel2))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(minumanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtHargaMinuman, javax.swing.GroupLayout.PREFERRED_SIZE, 193, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtNamaMinuman, javax.swing.GroupLayout.PREFERRED_SIZE, 193, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtIdMinuman, javax.swing.GroupLayout.PREFERRED_SIZE, 193, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(minumanLayout.createSequentialGroup()
                                .addComponent(btnTambahMinuman, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(36, 36, 36)
                                .addComponent(btnUbahMinuman)
                                .addGap(30, 30, 30)
                                .addComponent(btnHapusMinuman)))
                        .addGap(109, 109, 109)))
                .addContainerGap())
        );
        minumanLayout.setVerticalGroup(
            minumanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(minumanLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(26, 26, 26)
                .addGroup(minumanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(minumanLayout.createSequentialGroup()
                        .addGap(31, 31, 31)
                        .addGroup(minumanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel11)
                            .addComponent(txtNamaMinuman, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(minumanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtIdMinuman, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel2)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(minumanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel12)
                    .addComponent(txtHargaMinuman, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(minumanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnTambahMinuman)
                    .addGroup(minumanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnHapusMinuman)
                        .addComponent(btnUbahMinuman)))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 187, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(171, Short.MAX_VALUE))
        );

        getContentPane().add(minuman, "card6");

        jPanel5.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel7.setFont(new java.awt.Font("sansserif", 1, 14)); // NOI18N
        jLabel7.setText("KASIR");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel7)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, 26, Short.MAX_VALUE)
        );

        jLabel13.setFont(new java.awt.Font("sansserif", 1, 12)); // NOI18N
        jLabel13.setText("ID Kasir");

        jLabel14.setFont(new java.awt.Font("sansserif", 1, 12)); // NOI18N
        jLabel14.setText("Nama Kasir");

        jLabel15.setFont(new java.awt.Font("sansserif", 1, 12)); // NOI18N
        jLabel15.setText("password");

        btnTambahKasir.setFont(new java.awt.Font("sansserif", 1, 12)); // NOI18N
        btnTambahKasir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/add.png"))); // NOI18N
        btnTambahKasir.setText("Tambah");
        btnTambahKasir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTambahKasirActionPerformed(evt);
            }
        });

        btnEditKasir.setFont(new java.awt.Font("sansserif", 1, 12)); // NOI18N
        btnEditKasir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/edit.png"))); // NOI18N
        btnEditKasir.setText("Ubah");
        btnEditKasir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditKasirActionPerformed(evt);
            }
        });

        btnHapusKasir.setFont(new java.awt.Font("sansserif", 1, 12)); // NOI18N
        btnHapusKasir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/delete.png"))); // NOI18N
        btnHapusKasir.setText("Hapus");
        btnHapusKasir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHapusKasirActionPerformed(evt);
            }
        });

        tabelKasir.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tabelKasir.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabelKasirMouseClicked(evt);
            }
        });
        jScrollPane4.setViewportView(tabelKasir);

        javax.swing.GroupLayout kasirLayout = new javax.swing.GroupLayout(kasir);
        kasir.setLayout(kasirLayout);
        kasirLayout.setHorizontalGroup(
            kasirLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(kasirLayout.createSequentialGroup()
                .addGap(75, 75, 75)
                .addGroup(kasirLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(kasirLayout.createSequentialGroup()
                        .addGap(8, 8, 8)
                        .addGroup(kasirLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel14)
                            .addComponent(jLabel15)
                            .addComponent(jLabel13))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(kasirLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtPass, javax.swing.GroupLayout.PREFERRED_SIZE, 193, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtNamaKasir, javax.swing.GroupLayout.PREFERRED_SIZE, 193, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtId, javax.swing.GroupLayout.PREFERRED_SIZE, 193, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(kasirLayout.createSequentialGroup()
                        .addComponent(btnTambahKasir, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(36, 36, 36)
                        .addComponent(btnEditKasir)
                        .addGap(30, 30, 30)
                        .addComponent(btnHapusKasir)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(kasirLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(kasirLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 571, Short.MAX_VALUE))
                .addContainerGap())
        );
        kasirLayout.setVerticalGroup(
            kasirLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(kasirLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(39, 39, 39)
                .addGroup(kasirLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(kasirLayout.createSequentialGroup()
                        .addGap(31, 31, 31)
                        .addGroup(kasirLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel14)
                            .addComponent(txtNamaKasir, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(kasirLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel13)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(kasirLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel15)
                    .addComponent(txtPass, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(kasirLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnTambahKasir)
                    .addGroup(kasirLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnHapusKasir)
                        .addComponent(btnEditKasir)))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 187, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(158, Short.MAX_VALUE))
        );

        getContentPane().add(kasir, "card5");

        jPanel14.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel36.setFont(new java.awt.Font("sansserif", 1, 14)); // NOI18N
        jLabel36.setText("DETAIL TRANSAKSI");

        javax.swing.GroupLayout jPanel14Layout = new javax.swing.GroupLayout(jPanel14);
        jPanel14.setLayout(jPanel14Layout);
        jPanel14Layout.setHorizontalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel36)
                .addContainerGap(431, Short.MAX_VALUE))
        );
        jPanel14Layout.setVerticalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel36, javax.swing.GroupLayout.DEFAULT_SIZE, 26, Short.MAX_VALUE)
        );

        jLabel37.setFont(new java.awt.Font("sansserif", 1, 14)); // NOI18N
        jLabel37.setText("Daftar Detail Transaksi");

        tabelDetailTransaksi.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane9.setViewportView(tabelDetailTransaksi);

        javax.swing.GroupLayout detailTransaksiLayout = new javax.swing.GroupLayout(detailTransaksi);
        detailTransaksi.setLayout(detailTransaksiLayout);
        detailTransaksiLayout.setHorizontalGroup(
            detailTransaksiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(detailTransaksiLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(detailTransaksiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane9)
                    .addGroup(detailTransaksiLayout.createSequentialGroup()
                        .addComponent(jLabel37, javax.swing.GroupLayout.PREFERRED_SIZE, 173, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        detailTransaksiLayout.setVerticalGroup(
            detailTransaksiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(detailTransaksiLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel37)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane9, javax.swing.GroupLayout.PREFERRED_SIZE, 296, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(198, Short.MAX_VALUE))
        );

        getContentPane().add(detailTransaksi, "card8");

        jPanel13.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel34.setFont(new java.awt.Font("sansserif", 1, 14)); // NOI18N
        jLabel34.setText("PELANGGAN");

        javax.swing.GroupLayout jPanel13Layout = new javax.swing.GroupLayout(jPanel13);
        jPanel13.setLayout(jPanel13Layout);
        jPanel13Layout.setHorizontalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel34)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel13Layout.setVerticalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel34, javax.swing.GroupLayout.DEFAULT_SIZE, 26, Short.MAX_VALUE)
        );

        tabelPelanggan.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane8.setViewportView(tabelPelanggan);

        jLabel35.setFont(new java.awt.Font("sansserif", 1, 14)); // NOI18N
        jLabel35.setText("Daftar Pelanggan");

        javax.swing.GroupLayout pelangganLayout = new javax.swing.GroupLayout(pelanggan);
        pelanggan.setLayout(pelangganLayout);
        pelangganLayout.setHorizontalGroup(
            pelangganLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pelangganLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pelangganLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane8, javax.swing.GroupLayout.DEFAULT_SIZE, 571, Short.MAX_VALUE)
                    .addGroup(pelangganLayout.createSequentialGroup()
                        .addComponent(jLabel35, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        pelangganLayout.setVerticalGroup(
            pelangganLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pelangganLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel35)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane8, javax.swing.GroupLayout.PREFERRED_SIZE, 336, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(158, Short.MAX_VALUE))
        );

        getContentPane().add(pelanggan, "card7");

        jMenu5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/home.png"))); // NOI18N
        jMenu5.setText("Home");
        jMenu5.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jMenu5MouseClicked(evt);
            }
        });
        jMenuBar1.add(jMenu5);

        jMenu1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/data.png"))); // NOI18N
        jMenu1.setText("Data");

        jMenuItem8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/kasir.png"))); // NOI18N
        jMenuItem8.setText("Kasir");
        jMenuItem8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem8ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem8);

        jMenuItem2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/kasir.png"))); // NOI18N
        jMenuItem2.setText("Pelanggan");
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem2ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem2);

        cetakLaporan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/penjualan.png"))); // NOI18N
        cetakLaporan.setText("Laporan Penjualan");
        cetakLaporan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cetakLaporanActionPerformed(evt);
            }
        });
        jMenu1.add(cetakLaporan);

        jMenuBar1.add(jMenu1);

        jMenu2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/menu.png"))); // NOI18N
        jMenu2.setText("Menu");

        jMenuItem1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/makanan.png"))); // NOI18N
        jMenuItem1.setText("Makanan");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItem1);

        jMenuItem4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/minuman.png"))); // NOI18N
        jMenuItem4.setText("Minuman");
        jMenuItem4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem4ActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItem4);

        jMenuBar1.add(jMenu2);

        jMenu3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/transaksi.png"))); // NOI18N
        jMenu3.setText("Transaksi");
        jMenu3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jMenu3MouseClicked(evt);
            }
        });

        jMenuItem6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/transaksi.png"))); // NOI18N
        jMenuItem6.setText("Transaksi");
        jMenuItem6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem6ActionPerformed(evt);
            }
        });
        jMenu3.add(jMenuItem6);

        jMenuItem7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/data.png"))); // NOI18N
        jMenuItem7.setText("Detail Transaksi");
        jMenuItem7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem7ActionPerformed(evt);
            }
        });
        jMenu3.add(jMenuItem7);

        jMenuBar1.add(jMenu3);

        jMenu4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/logout.png"))); // NOI18N
        jMenu4.setText("Keluar");
        jMenu4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jMenu4MouseClicked(evt);
            }
        });
        jMenuBar1.add(jMenu4);

        setJMenuBar(jMenuBar1);

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        // Makanan
        dashboard.setVisible(false);
        kasir.setVisible(false);
        makanan.setVisible(true);
        minuman.setVisible(false);
        transaksi.setVisible(false);
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void btnHapusMakananActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHapusMakananActionPerformed
        // TODO add your handling code here:
        con = Koneksi.getConnection();
        int konfirmasiHapus = JOptionPane.showConfirmDialog(null, "Apakah anda yakin ingin menghapus data ini?",
                "Konfirmasi", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        if (konfirmasiHapus == 0)
        {
            try {
                sql = "DELETE FROM menu_makanan WHERE id_makanan = '" +txtIdMakanan.getText()+"'"; 
                 psmt = con.prepareStatement(sql);
                 psmt.executeUpdate();
                 bersihkanInput();
                 JOptionPane.showMessageDialog(null, "Data berhasil dihapus", "Pesan", JOptionPane.INFORMATION_MESSAGE);
                 TampilMakanan();
            }
            catch (Exception e)
            {
                JOptionPane.showMessageDialog(null, "Data gagal dihapus karena : "+e);
            }
        }
    }//GEN-LAST:event_btnHapusMakananActionPerformed

    private void btnHapusMinumanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHapusMinumanActionPerformed
        // TODO add your handling code here:
        con = Koneksi.getConnection();
        int konfirmasiHapus = JOptionPane.showConfirmDialog(null, "Apakah anda yakin ingin menghapus data ini?",
                "Konfirmasi", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        if (konfirmasiHapus == 0)
        {
            try {
               sql = "DELETE FROM menu_minuman WHERE id_minuman = '" +txtIdMinuman.getText()+"'"; 
                psmt = con.prepareStatement(sql);
                psmt.executeUpdate();
                bersihkanInput();
                JOptionPane.showMessageDialog(null, "Data berhasil dihapus", "Pesan", JOptionPane.INFORMATION_MESSAGE);
                TampilMinuman();
            }
            catch (Exception e)
            {
               JOptionPane.showMessageDialog(null, "Data gagal dihapus karena : "+e);
            }
        }
    }//GEN-LAST:event_btnHapusMinumanActionPerformed

    private void btnHapusKasirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHapusKasirActionPerformed
        // TODO add your handling code here:
        con = Koneksi.getConnection();
        int konfirmasiHapus = JOptionPane.showConfirmDialog(null, "Apakah anda yakin ingin menghapus data ini?",
                "Konfirmasi", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
       if (konfirmasiHapus == 0)
       {
           try {
               sql = "DELETE FROM kasir WHERE id_kasir = '" +txtIdKasir.getText()+"'"; 
                psmt = con.prepareStatement(sql);
                psmt.executeUpdate();
                bersihkanInput();
                jDialog1.setSize(300, 150);
                jDialog1.setLocationRelativeTo(this);
                jDialog1.setVisible(true);
                //JOptionPane.showMessageDialog(null, "Data berhasil dihapus", "Pesan", JOptionPane.INFORMATION_MESSAGE);
                TampilKasir();
                bersihKasir();
           }
           catch (Exception e)
           {
               JOptionPane.showMessageDialog(null, "Data gagal dihapus karena : "+e);
           }
       }
    }//GEN-LAST:event_btnHapusKasirActionPerformed

    private void jMenu4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jMenu4MouseClicked
        // Keluar
        int conf = JOptionPane.showConfirmDialog(rootPane,"Apakah anda yakin ingin keluar dari Aplikasi ini?","Konfirmasi",JOptionPane.YES_NO_OPTION);
        if (conf == 0) {
//        System.exit(0);
        dispose();
        login l = new login();
        l.setVisible(true);
        JOptionPane.showMessageDialog(null, "Anda berhasil keluar!");
        }
    }//GEN-LAST:event_jMenu4MouseClicked

    private void jMenuItem4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem4ActionPerformed
        // Minuman
        dashboard.setVisible(false);
        kasir.setVisible(false);
        makanan.setVisible(false);
        minuman.setVisible(true);
        transaksi.setVisible(false);

    }//GEN-LAST:event_jMenuItem4ActionPerformed

    private void jMenu3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jMenu3MouseClicked
        // Transaksi
        dashboard.setVisible(false);
        kasir.setVisible(false);
        makanan.setVisible(false);
        minuman.setVisible(false);
        transaksi.setVisible(true);
    }//GEN-LAST:event_jMenu3MouseClicked

    private void jMenu5MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jMenu5MouseClicked
        // Home
        dashboard.setVisible(true);
        kasir.setVisible(false);
        makanan.setVisible(false);
        minuman.setVisible(false);
        transaksi.setVisible(false);
    }//GEN-LAST:event_jMenu5MouseClicked

    private void btnTambahMakananActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTambahMakananActionPerformed
        // TODO add your handling code here:
        
        con = Koneksi.getConnection();   
        try {
            if(btnTambahMakanan.getText().equals("Batal")){
                bersihkanInput();
                btnTambahMakanan.setText("Simpan");
            }
            else
            {
            sql = "INSERT INTO menu_makanan VALUES ('"+txtIdMakanan.getText()+"','"+txtNamaMakanan.getText()+"','"+txtHargaMakanan.getText()+"') ";
            psmt = con.prepareStatement(sql);
            psmt.execute();
            bersihkanInput();
            JOptionPane.showMessageDialog(null, "Makanan berhasil ditambahkan!");
            TampilMakanan();
            }
            }
        catch(Exception e){
            JOptionPane.showMessageDialog(null, "error karena : "+e);
        }
    }//GEN-LAST:event_btnTambahMakananActionPerformed

    private void btnUbahMinumanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUbahMinumanActionPerformed
        // TODO add your handling code here:
          try {
        con = Koneksi.getConnection();
        sql = "UPDATE menu_minuman SET  nm_minuman=?, harga_minuman=? WHERE id_minuman=?";
        psmt = con.prepareStatement(sql);
        psmt.setString(1, txtNamaMinuman.getText());
        psmt.setString(2, txtHargaMinuman.getText());
        psmt.setString(3, txtIdMinuman.getText());
        psmt.executeUpdate();
        bersihkanInput();
        JOptionPane.showMessageDialog(null, "Data berhasil diubah!");
        TampilMinuman();
     }
      catch(Exception e)
      {
          JOptionPane.showMessageDialog(null, "Data gagal ditambahkan karena : "+e);
      }
    }//GEN-LAST:event_btnUbahMinumanActionPerformed

    private void tableMinumanMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableMinumanMouseClicked
        txtIdMinuman.setText(tableMinuman.getValueAt(tableMinuman.getSelectedRow(), 0).toString());
        txtNamaMinuman.setText(tableMinuman.getValueAt(tableMinuman.getSelectedRow(), 1).toString());
        txtHargaMinuman.setText(tableMinuman.getValueAt(tableMinuman.getSelectedRow(), 2).toString());
        txtIdMinuman.setEnabled(false);
        btnTambahMinuman.setEnabled(true);
        btnUbahMinuman.setEnabled(true);
        btnHapusMakanan.setEnabled(true);
        btnTambahMinuman.setText("Batal");
    }//GEN-LAST:event_tableMinumanMouseClicked

    private void tableMakananMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableMakananMouseClicked
        // TODO add your handling code here:
        txtIdMakanan.setText(tableMakanan.getValueAt(tableMakanan.getSelectedRow(), 0).toString());
        txtNamaMakanan.setText(tableMakanan.getValueAt(tableMakanan.getSelectedRow(), 1).toString());
        txtHargaMakanan.setText(tableMakanan.getValueAt(tableMakanan.getSelectedRow(), 2).toString());
        txtIdMakanan.setEnabled(false);
        btnHapusMakanan.setEnabled(true);
        btnUbahMakanan.setEnabled(true);
        btnTambahMakanan.setEnabled(true);
        btnTambahMakanan.setText("Batal");
    }//GEN-LAST:event_tableMakananMouseClicked
        
    private void btnUbahMakananActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUbahMakananActionPerformed
        // TODO add your handling code here:
            try {
        con = Koneksi.getConnection();
        sql = "UPDATE menu_makanan SET  nm_makanan=?, harga_makanan=? WHERE id_makanan=?";
        psmt = con.prepareStatement(sql);
        psmt.setString(1, txtNamaMakanan.getText());
        psmt.setString(2, txtHargaMakanan.getText());
        psmt.setString(3, txtIdMakanan.getText());
        psmt.executeUpdate();
        bersihkanInput();
        JOptionPane.showMessageDialog(null, "Data berhasil diubah!");
        TampilMinuman();
     }
      catch(Exception e)
      {
          JOptionPane.showMessageDialog(null, "Data gagal ditambahkan karena : "+e);
      }
    }//GEN-LAST:event_btnUbahMakananActionPerformed

    private void btnTambahMinumanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTambahMinumanActionPerformed
        // TODO add your handling code here:
        con = Koneksi.getConnection();
        try {   
            if(btnTambahMinuman.getText().equals("Batal")){
                bersihkanInput();
                btnTambahMakanan.setText("Tambah");
            }
            else
            {
            sql = "INSERT INTO menu_minuman VALUES ('"+txtIdMinuman.getText()+"','"+txtNamaMinuman.getText()+"','"+txtHargaMinuman.getText()+"') ";
            psmt = con.prepareStatement(sql);
            psmt.execute();
            bersihkanInput();
            JOptionPane.showMessageDialog(null, "Minuman berhasil ditambahkan!");
            TampilMinuman();
        }
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(null, "error karena : "+e);
        }
    }//GEN-LAST:event_btnTambahMinumanActionPerformed

    private void cmbIdMakananActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbIdMakananActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cmbIdMakananActionPerformed

    private void cmbIdMakananItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cmbIdMakananItemStateChanged
        // TODO add your handling code here:
         con = Koneksi.getConnection();
        ResultSet rss2;
        sql = "SELECT * FROM menu_makanan WHERE id_makanan = '"+cmbIdMakanan.getSelectedItem().toString()+"'";
        try {
            stt = con.createStatement();
            rss2 = stt.executeQuery(sql);
            if(rss2.next()){
                txtNamaMakananTransaksi.setText(rss2.getString("nm_makanan"));
                txtHargaMakananTransaksi.setText(rss2.getString("harga_makanan"));
            }
            rss2.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error karena : "+e);
        }
    }//GEN-LAST:event_cmbIdMakananItemStateChanged

     //FORM TRANSAKSI
    public void tanggalSekarang(){
        Date skrg= new Date();
        SimpleDateFormat format= new SimpleDateFormat("dd MMMM yyyy HH:mm:ss");
        SimpleDateFormat format2= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        tanggal = format2.format(skrg);
        txtTanggal.setText(format.format(skrg));
    }
    
    public void noTransaksi(){
        con = Koneksi.getConnection();
        sql = "SELECT no_transaksi FROM transaksi";
        try {
            stt = con.createStatement();
            rss = stt.executeQuery(sql);
            if(rss.last()) {
                txtTransaksi.setText(String.valueOf(rss.getInt(1)+1));
            } else
                txtTransaksi.setText("1");            
        } catch (Exception e) {
           e.printStackTrace();
        }
    }
    
    public void idPelanggan() {
        con = Koneksi.getConnection();
        sql = "SELECT id_pelanggan FROM pelanggan";
        try {
            stt = con.createStatement();
            rss = stt.executeQuery(sql);
            if(rss.last()) {
                txtIdPelanggan.setText(String.valueOf(rss.getInt(1)+1));
            }
            else {
                txtTransaksi.setText("1");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    
    
    private void btnBeliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBeliActionPerformed
        // TODO add your handling code here:
        con = Koneksi.getConnection();
        
        txtTotal.setText(txtTransaksi.getText());
        
        if(cmbIdMakanan.getSelectedItem().toString().equals("Pilih Makanan"))
        {
            JOptionPane.showMessageDialog(null, "Harap Pilih Makanan");
        }
         
        if(cmbIdMinuman.getSelectedItem().toString().equals("Pilih Minuman"))
        {
            JOptionPane.showMessageDialog(null, "Harap Pilih Minuman");
        }
            
        if (txtJumlahMakanan.getText().equals("") && txtJumlahMinuman.getText().equals(""))
        {
            JOptionPane.showMessageDialog(null, "Harap Masukkan Jumlah!");
        }
        else {        
            int total2 = 0;
            if(txtTotal.getText().equals("")){
                total2 = 0;
            }
            else
                total2 = Integer.valueOf(txtTotal.getText());

        int jumlahMakanan = Integer.parseInt(txtJumlahMakanan.getText());
        int jumlahMinuman = Integer.parseInt(txtJumlahMinuman.getText());
        int hargaMakanan = Integer.parseInt(txtHargaMakananTransaksi.getText());
        int hargaMinuman = Integer.parseInt(txtHargaMinumanTransaksi.getText());
        
        int subTotalMakanan = jumlahMakanan * hargaMakanan;
        int subTotalMinuman = jumlahMinuman * hargaMinuman;
        int total = subTotalMakanan + subTotalMinuman;
        
        try
        {
            String sql0 = "INSERT INTO pelanggan (nm_pelanggan) VALUES ('"+txtNmPelanggan.getText()+"')";
            PreparedStatement psmt0 = con.prepareStatement(sql0);
            psmt0.execute();
            
            String sql_idPelanggan = "SELECT * FROM pelanggan WHERE id_pelanggan='"+txtIdPelanggan.getText()+"' ";
            rss = stt.executeQuery(sql_idPelanggan);
            if(rss.next()) {
                String idPelanggan = rss.getString("id_pelanggan"); 
                
                String  sql1 = "INSERT INTO transaksi (tgl_transaksi, id_pelanggan, id_kasir, subtotal_makanan, subtotal_minuman, total) "
                                + "VALUES ('"+tanggal+"','"+idPelanggan+"','"+txtIdKasir.getText()+"','"+subTotalMakanan+"','"+subTotalMinuman+"','"+total+"')";
                PreparedStatement psmt1 = con.prepareStatement(sql1);
                psmt1.execute();                                                                                
            }                                                
            sql = "INSERT INTO detail_transaksi VALUES ('"+txtTransaksi.getText()+"','"+cmbIdMakanan.getSelectedItem().toString()+"','"+jumlahMakanan+"',"
                    + "'"+cmbIdMinuman.getSelectedItem().toString()+"','"+jumlahMinuman+"')";
            psmt = con.prepareStatement(sql);
            psmt.execute();
                
            txtTotal.setText("Rp. "+total);
            bersihkanInput();
            JOptionPane.showMessageDialog(null, "Transaksi berhasil!");
            tanggalSekarang();
            //TampilTransaksi();
            tampildata2();
            TampilDaftarTransaksi();
        }
        catch (Exception e)
        {
            JOptionPane.showMessageDialog(null, "transaksi gagal karena :"+e);
        }
        }
    }//GEN-LAST:event_btnBeliActionPerformed

    private void cmbIdMinumanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbIdMinumanActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cmbIdMinumanActionPerformed

    private void cmbIdMinumanItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cmbIdMinumanItemStateChanged
        // TODO add your handling code here:
          // TODO add your handling code here:
        con = Koneksi.getConnection();
        ResultSet rss2;
        sql = "SELECT * FROM menu_minuman WHERE id_minuman = '"+cmbIdMinuman.getSelectedItem().toString()+"'";
        try {
            stt = con.createStatement();
            rss2 = stt.executeQuery(sql);
            if(rss2.next()){
                txtNamaMinumanTransaksi.setText(rss2.getString("nm_minuman"));
                txtHargaMinumanTransaksi.setText(rss2.getString("harga_minuman"));
            }
            rss2.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error karena : "+e);
        }
    }//GEN-LAST:event_cmbIdMinumanItemStateChanged

    private void btnTambahTransaksiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTambahTransaksiActionPerformed
        // TODO add your handling code here:
         noTransaksi();
        tanggalSekarang();
        //TampilTransaksi();
        tampildata2();
        txtTotal.setText("Rp. 0");
    }//GEN-LAST:event_btnTambahTransaksiActionPerformed

    private void cetakStrukActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cetakStrukActionPerformed
          con = Koneksi.getConnection();
            String NamaFile = "/restoran/report/struk.jasper";
            HashMap hash = new HashMap();
        try {
            hash.put("no", txtTransaksi.getText());
//           runReportDefault(NamaFile,hash);
//            JasperReport Jr = JasperCompileManager.compileReport(NamaFile);
//            JasperPrint Jp = JasperFillManager.fillReport(Jr, hash, con);
//            JasperViewer.viewReport(Jp, false);
            InputStream Report;
            Report = getClass().getResourceAsStream(NamaFile);
            JasperPrint print = JasperFillManager.fillReport(Report, hash, con);
            JasperViewer view = new JasperViewer(print, false);
            view.setZoomRatio(CENTER_ALIGNMENT);
            view.setVisible(true);
            view.setExtendedState(view.MAXIMIZED_BOTH);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error karena :"+e);
        }
        
//           Connection con = koneksi.getConnection();
//            String NamaFile = "/com/restoran/report/Struk_Pembayaran.jasper";
//            HashMap hash = new HashMap();
//        try {
//            hash.put("idtransaksi", txt_no_transaksi.getText());
//            runReportDefault(NamaFile,hash);
//        } catch (Exception e) {
//        }
        
//        try {
            // TODO add your handling code here:
//        try {
//            File struk = new File("src\\restoran\\report\\struk.jasper");
//            JasperPrint jp = JasperFillManager.fillReport(struk.getPath(), null, Koneksi.getConnection());
//            JasperViewer.viewReport(jp, false);
//        } catch (Exception e) {
//            JOptionPane.showMessageDialog(rootPane, e);
//        }
//Koneksi.getConnection();
//JasperDesign JD = JRXmlLoader.load("src\\restoran\\report\\struk.jasper");
//JRDesignQuery JDQ = new JRDesignQuery();
//JDQ.setText("SELECT transaksi.no_transaksi, tgl_transaksi, nm_makanan, nm_minuman, harga_makanan, harga_minuman, detail_transaksi.jmlh_makanan, detail_transaksi.jmlh_minuman, subtotal_makanan, subtotal_minuman, transaksi.total\n" +
//"FROM transaksi, detail_transaksi, menu_makanan, menu_minuman\n" +
//"WHERE transaksi.no_transaksi = detail_transaksi.no_transaksi\n" +
//"AND detail_transaksi.id_makanan = menu_makanan.id_makanan \n" +
//"AND detail_transaksi.id_minuman = menu_minuman.id_minuman");
//JD.setQuery(JDQ);
//JasperReport JR = JasperCompileManager.compileReport(JD);
//JasperPrint JP = JasperFillManager.fillReport(JR, null, Koneksi.con);
//JasperViewer JV = new JasperViewer(JP, false);
//JV.setVisible(true);
//        } catch (JRException ex) {
//            Logger.getLogger(formUtama.class.getName()).log(Level.SEVERE, null, ex);
//        }
    }//GEN-LAST:event_cetakStrukActionPerformed

    private void btnTambahKasirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTambahKasirActionPerformed
        // TODO add your handling code here:
        con = Koneksi.getConnection();
        try {   
            if(btnTambahKasir.getText().equals("Batal")){
                bersihKasir();
                btnTambahKasir.setText("Tambah");
            }
            else
            {
            sql = "INSERT INTO kasir VALUES ('"+txtId.getText()+"','"+txtNamaKasir.getText()+"',PASSWORD('"+txtPass.getText()+"')) ";
            psmt = con.prepareStatement(sql);
            psmt.execute();
            bersihKasir();
            jDialogKasir.setSize(300, 150);
            jDialogKasir.setLocationRelativeTo(this);
            jDialogKasir.setVisible(true);
            //JOptionPane.showMessageDialog(null, "Data Kasir berhasil ditambahkan!");
            TampilKasir();
        }
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(null, "error karena : "+e);
        }
    }//GEN-LAST:event_btnTambahKasirActionPerformed

    private void jButton1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton1MouseClicked
        // TODO add your handling code here:
        jDialogKasir.dispose();
    }//GEN-LAST:event_jButton1MouseClicked

    private void jButton2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton2MouseClicked
        // TODO add your handling code here:
        jDialog1.dispose();
    }//GEN-LAST:event_jButton2MouseClicked

    private void btnEditKasirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditKasirActionPerformed
        // TODO add your handling code here:
        try {
        con = Koneksi.getConnection();
        sql = "UPDATE kasir SET  nm_kasir=?, password=? WHERE id_kasir=?";
        psmt = con.prepareStatement(sql);
        psmt.setString(1, txtNamaKasir.getText());
        psmt.setString(2, txtPass.getText());
        psmt.setString(3, txtId.getText());
        psmt.executeUpdate();
        bersihKasir();
            jDialogKasirUbah.setSize(300, 150);
            jDialogKasirUbah.setLocationRelativeTo(this);
            jDialogKasirUbah.setVisible(true);
        //JOptionPane.showMessageDialog(null, "Data berhasil diubah!");
        TampilKasir();
     }
      catch(Exception e)
      {
          JOptionPane.showMessageDialog(null, "Data gagal ditambahkan karena : "+e);
      }
    }//GEN-LAST:event_btnEditKasirActionPerformed

    private void jButton3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton3MouseClicked
        // TODO add your handling code here:
        jDialogKasirUbah.dispose();
    }//GEN-LAST:event_jButton3MouseClicked

    private void tabelKasirMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabelKasirMouseClicked
        // TODO add your handling code here:
        txtId.setText(tabelKasir.getValueAt(tabelKasir.getSelectedRow(), 0).toString());
        txtNamaKasir.setText(tabelKasir.getValueAt(tabelKasir.getSelectedRow(), 1).toString());
        txtPass.setText(tabelKasir.getValueAt(tabelKasir.getSelectedRow(), 2).toString());
       
        txtId.setEnabled(false);
        txtPass.setEnabled(false);
        btnHapusKasir.setEnabled(true);
        btnEditKasir.setEnabled(true);
        btnTambahKasir.setEnabled(true);
        btnTambahKasir.setText("Batal");
    }//GEN-LAST:event_tabelKasirMouseClicked
 String not;
    private void jMenuItem6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem6ActionPerformed
        // Transaksi
        dashboard.setVisible(false);
        kasir.setVisible(false);
        makanan.setVisible(false);
        minuman.setVisible(false);
        transaksi.setVisible(true);
        pelanggan.setVisible(false);
        detailTransaksi.setVisible(false);
    }//GEN-LAST:event_jMenuItem6ActionPerformed

    private void jMenuItem7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem7ActionPerformed
        // Detail Transaksi
        dashboard.setVisible(false);
        kasir.setVisible(false);
        makanan.setVisible(false);
        minuman.setVisible(false);
        transaksi.setVisible(false);
        pelanggan.setVisible(false);
        detailTransaksi.setVisible(true);
    }//GEN-LAST:event_jMenuItem7ActionPerformed

    private void jMenuItem8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem8ActionPerformed
        // Kasir
        dashboard.setVisible(false);
        kasir.setVisible(true);
        makanan.setVisible(false);
        minuman.setVisible(false);
        transaksi.setVisible(false);
        pelanggan.setVisible(false);
        detailTransaksi.setVisible(false);
    }//GEN-LAST:event_jMenuItem8ActionPerformed

    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem2ActionPerformed
        // Pelanggan
        dashboard.setVisible(false);
        kasir.setVisible(false);
        makanan.setVisible(false);
        minuman.setVisible(false);
        transaksi.setVisible(false);
        pelanggan.setVisible(true);
        detailTransaksi.setVisible(false);
    }//GEN-LAST:event_jMenuItem2ActionPerformed

    private void cetakLaporanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cetakLaporanActionPerformed
        // TODO add your handling code here:

        con = Koneksi.getConnection();
        String NamaFile = "/restoran/report/laporanPenjualan.jasper";
        try {
            InputStream Report;
            Report = getClass().getResourceAsStream(NamaFile);
            JasperPrint print = JasperFillManager.fillReport(Report, null, con);
            JasperViewer view = new JasperViewer(print, false);
            view.setZoomRatio(CENTER_ALIGNMENT);
            view.setVisible(true);
            view.setExtendedState(view.MAXIMIZED_BOTH);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error karena :"+e);
        }
    }//GEN-LAST:event_cetakLaporanActionPerformed

    private void tabelTransaksiMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabelTransaksiMouseClicked
        // TODO add your handling code here:
        try {
            String kodedaritable = tabelTransaksi.getValueAt(tabelTransaksi.getSelectedRow(), 0).toString();
            int lokasikolom = tabelTransaksi.getSelectedColumn();
            if (lokasikolom == 8) {
                con = Koneksi.getConnection();
                int konfirmasiHapus = JOptionPane.showConfirmDialog(null, "Apakah anda yakin ingin menghapus data ini?",
                    "Konfirmasi", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                if (konfirmasiHapus == 0)
                {
                    try {
                        not = tabelTransaksi.getValueAt(tabelTransaksi.getSelectedRow(), 0).toString();
                        sql = "DELETE FROM detail_transaksi WHERE no_transaksi = '" +not+"'";
                        psmt = con.prepareStatement(sql);
                        psmt.executeUpdate();
                        sql = "DELETE FROM transaksi WHERE no_transaksi = '" +not+"'";
                        psmt = con.prepareStatement(sql);
                        psmt.executeUpdate();
                        JOptionPane.showMessageDialog(null, "Data berhasil dihapus", "Pesan", JOptionPane.INFORMATION_MESSAGE);
                        tampildata2();
                    }
                    catch (Exception e)
                    {
                        JOptionPane.showMessageDialog(null, "Data gagal dihapus karena : "+e);
                    }
                }
            }
        } catch (Exception e) {
        }
    }//GEN-LAST:event_tabelTransaksiMouseClicked

     public void idMakanan(){
        con = Koneksi.getConnection();
        sql = "SELECT * FROM menu_makanan";
        try {
         stt = con.createStatement();
         rss= stt.executeQuery(sql);
         while(rss.next()){
             cmbIdMakanan.addItem(rss.getString("id_makanan"));
         }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Pilihan tidak ada"+e);
        }
    }
     
     public void idMinuman(){
        con = Koneksi.getConnection();
        sql = "SELECT * FROM menu_minuman";
        try {
         stt = con.createStatement();
         rss= stt.executeQuery(sql);
         while(rss.next()){
             cmbIdMinuman.addItem(rss.getString("id_minuman"));
         }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Pilihan tidak ada"+e);
        }
    }
    

    /**
     * @param args the command line arguments
     */
//    public static void main(String args[]) {
//        /* Set the Nimbus look and feel */
//        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
//        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
//         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
//         */
//        try {
//            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
//                if ("Nimbus".equals(info.getName())) {
//                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
//                    break;
//                }
//            }
//        } catch (ClassNotFoundException ex) {
//            java.util.logging.Logger.getLogger(formUtama.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (InstantiationException ex) {
//            java.util.logging.Logger.getLogger(formUtama.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (IllegalAccessException ex) {
//            java.util.logging.Logger.getLogger(formUtama.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
//            java.util.logging.Logger.getLogger(formUtama.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        }
//        //</editor-fold>
//
//        /* Create and display the form */
//        java.awt.EventQueue.invokeLater(new Runnable() {
//            public void run() {
//                new formUtama().setVisible(true);
//            }
//        });
//    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBeli;
    private javax.swing.JButton btnEditKasir;
    private javax.swing.JButton btnHapusKasir;
    private javax.swing.JButton btnHapusMakanan;
    private javax.swing.JButton btnHapusMinuman;
    private javax.swing.JButton btnTambahKasir;
    private javax.swing.JButton btnTambahMakanan;
    private javax.swing.JButton btnTambahMinuman;
    private javax.swing.JButton btnTambahTransaksi;
    private javax.swing.JButton btnUbahMakanan;
    private javax.swing.JButton btnUbahMinuman;
    private javax.swing.JMenuItem cetakLaporan;
    private javax.swing.JButton cetakStruk;
    private javax.swing.JComboBox<String> cmbIdMakanan;
    private javax.swing.JComboBox<String> cmbIdMinuman;
    private javax.swing.JPanel dashboard;
    private javax.swing.JPanel detailTransaksi;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JDialog jDialog1;
    private javax.swing.JDialog jDialogKasir;
    private javax.swing.JDialog jDialogKasirUbah;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenu jMenu4;
    private javax.swing.JMenu jMenu5;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem4;
    private javax.swing.JMenuItem jMenuItem6;
    private javax.swing.JMenuItem jMenuItem7;
    private javax.swing.JMenuItem jMenuItem8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JScrollPane jScrollPane9;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JTable jTable5;
    private javax.swing.JTable jTable6;
    private javax.swing.JPanel kasir;
    private javax.swing.JPanel makanan;
    private javax.swing.JPanel minuman;
    private javax.swing.JPanel pelanggan;
    private javax.swing.JTable t_DaftarTransaksi;
    private javax.swing.JTable tabelDetailTransaksi;
    private javax.swing.JTable tabelKasir;
    private javax.swing.JTable tabelPelanggan;
    public static final javax.swing.JTable tabelTransaksi = new javax.swing.JTable();
    private javax.swing.JTable tableMakanan;
    private javax.swing.JTable tableMinuman;
    private javax.swing.JPanel transaksi;
    private javax.swing.JTextField txtHargaMakanan;
    private javax.swing.JTextField txtHargaMakananTransaksi;
    private javax.swing.JTextField txtHargaMinuman;
    private javax.swing.JTextField txtHargaMinumanTransaksi;
    private javax.swing.JTextField txtId;
    private javax.swing.JTextField txtIdKasir;
    private javax.swing.JTextField txtIdMakanan;
    private javax.swing.JTextField txtIdMinuman;
    private javax.swing.JTextField txtIdPelanggan;
    private javax.swing.JTextField txtJumlahMakanan;
    private javax.swing.JTextField txtJumlahMinuman;
    private javax.swing.JTextField txtKasir;
    private javax.swing.JTextField txtNamaKasir;
    private javax.swing.JTextField txtNamaMakanan;
    private javax.swing.JTextField txtNamaMakananTransaksi;
    private javax.swing.JTextField txtNamaMinuman;
    private javax.swing.JTextField txtNamaMinumanTransaksi;
    private javax.swing.JTextField txtNmPelanggan;
    private javax.swing.JTextField txtPass;
    private javax.swing.JTextField txtPendapatan;
    private javax.swing.JTextField txtTanggal;
    private javax.swing.JTextField txtTotal;
    private javax.swing.JTextField txtTransaksi;
    // End of variables declaration//GEN-END:variables

    private void runReportDefault(String NamaFile, HashMap hash) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
