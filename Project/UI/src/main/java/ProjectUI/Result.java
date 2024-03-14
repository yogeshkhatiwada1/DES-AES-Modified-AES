package ProjectUI;
import static ProjectUI.Home.*;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DatasetUtilities;
import java.awt.*;
/**
 *
 * @author acer
 */

public class Result extends javax.swing.JFrame {

    /**
     * Creates new form Result
     */
    public Result() {
        initComponents();
    }

    private Home previousHome;
    private String DET;
    private String DDT;
    private String AET;
    private String ADT;
    private String MAET;
    private String MADT;
    public Result(Home previousHome) {
        initComponents();
        this.previousHome = previousHome;
        if(DesEncryptionTime!=null&&DesEncryptionTime!=null) {
            jTextField1.setText(DesEncryptionTime + " ms");
            jTextField2.setText(DesDecryptionTime + " ms");
            DET=DesEncryptionTime;
            DDT=DesDecryptionTime;
            ChiDes.setText(DesEncryptedText);

        }
        if(AesEncryptionTime!=null&&AesDecryptionTime!=null){
            jTextField3.setText(AesEncryptionTime+ " ms");
            jTextField4.setText(AesDecryptionTime+ " ms");
            AET=AesEncryptionTime;
            ADT=AesDecryptionTime;
            ChiAes.setText(AesEncryptedText);
        }
        if(MAesEncryptionTime!=null&&MAesDecryptionTime!=null) {
            jTextField5.setText(MAesEncryptionTime + " ms");
            jTextField6.setText(MAesDecryptionTime + " ms");
            MAET=MAesEncryptionTime;
            MADT=MAesDecryptionTime;
            ChiMAes.setText(MAesEncryptedText);
        }
        Result.MyChart myChart = new Result.MyChart();
        JFreeChart chart = myChart.createChart();
        ChartPanel chartPanel = new ChartPanel(chart);
        jPanel10.add(chartPanel);
    }
    public class MyChart {
        public JFreeChart createChart() {
            if(DesEncryptionTime==null){
                DET= String.valueOf(0);
                DDT= String.valueOf(0);
            }
            if(AesEncryptionTime==null){
                AET= String.valueOf(0);
                ADT= String.valueOf(0);
            }
            if(MAesEncryptionTime==null){
                MAET= String.valueOf(0);
                MADT= String.valueOf(0);
            }
            DefaultCategoryDataset dataset = new DefaultCategoryDataset();
            dataset.addValue(Double.parseDouble(DET), "DES", "Encryption");
            dataset.addValue(Double.parseDouble(DDT), "DES", "Decryption");
            dataset.addValue(Double.parseDouble(AET), "AES", "Encryption");
            dataset.addValue(Double.parseDouble(ADT), "AES", "Decryption");
            dataset.addValue(Double.parseDouble(MAET), "Modified-AES", "Encryption");
            dataset.addValue(Double.parseDouble(MADT), "Modified-AES", "Decryption");

            JFreeChart chart = ChartFactory.createBarChart(
                    "",
                    "Algorithms",
                    "Time (ms)",
                    dataset,
                    PlotOrientation.VERTICAL,
                    true,
                    true,
                    false);

            CategoryPlot plot = chart.getCategoryPlot();
            NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
            double range = DatasetUtilities.findRangeBounds(dataset).getLength();
            rangeAxis.setRange(0, range + (range * 0.9)); // set range of y-axis with 90% buffer

            BarRenderer renderer = (BarRenderer) plot.getRenderer();
            renderer.setSeriesPaint(0, Color.decode("#222222")); // set color of first bar
            renderer.setSeriesPaint(1, Color.decode("#434242")); // set color of second bar
            renderer.setSeriesPaint(2, Color.decode("#22A39F")); // set color of third bar
            return chart;
        }
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        jPanel7 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jPanel9 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        ChiDes = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        ChiAes = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        ChiMAes = new javax.swing.JTextField();
        jPanel8 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jPanel10 = new javax.swing.JPanel();
        jPanel11 = new javax.swing.JPanel();
        jPanel12 = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jPanel14 = new javax.swing.JPanel();
        jPanel15 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jPanel17 = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jPanel13 = new javax.swing.JPanel();
        jPanel16 = new javax.swing.JPanel();
        jLabel17 = new javax.swing.JLabel();
        jPanel18 = new javax.swing.JPanel();
        jLabel18 = new javax.swing.JLabel();
        jPanel19 = new javax.swing.JPanel();
        jTextField1 = new javax.swing.JTextField();
        jTextField2 = new javax.swing.JTextField();
        jTextField3 = new javax.swing.JTextField();
        jTextField4 = new javax.swing.JTextField();
        jTextField5 = new javax.swing.JTextField();
        jTextField6 = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setPreferredSize(new java.awt.Dimension(1420, 700));
        getContentPane().setLayout(new javax.swing.BoxLayout(getContentPane(), javax.swing.BoxLayout.LINE_AXIS));

        jPanel1.setMinimumSize(new java.awt.Dimension(0, 0));
        jPanel1.setPreferredSize(new java.awt.Dimension(1400, 700));
        jPanel1.setLayout(new java.awt.BorderLayout());

        jPanel2.setBackground(new java.awt.Color(20, 20, 20));
        jPanel2.setForeground(new java.awt.Color(20, 20, 20));
        jPanel2.setMinimumSize(new java.awt.Dimension(0, 0));
        jPanel2.setPreferredSize(new java.awt.Dimension(995, 100));
        jPanel2.setLayout(new java.awt.BorderLayout());

        jLabel1.setFont(new java.awt.Font("Ink Free", 1, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Results");
        jLabel1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jLabel1.setMinimumSize(new java.awt.Dimension(0, 0));
        jPanel2.add(jLabel1, java.awt.BorderLayout.CENTER);

        jPanel3.setBackground(new java.awt.Color(20, 20, 20));
        jPanel3.setMinimumSize(new java.awt.Dimension(0, 0));
        jPanel3.setPreferredSize(new java.awt.Dimension(80, 100));

        jButton1.setBackground(new java.awt.Color(20, 20, 20));
        jButton1.setForeground(new java.awt.Color(20, 20, 20));
        jButton1.setIcon(new javax.swing.ImageIcon("C:\\Users\\acer\\Desktop\\Project\\UI\\src\\main\\java\\Images\\BackButton.jpg")); // NOI18N
        jButton1.setBorder(null);
        jButton1.setBorderPainted(false);
        jButton1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton1.setIconTextGap(0);
        jButton1.setOpaque(true);
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(22, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(47, Short.MAX_VALUE))
        );

        jPanel2.add(jPanel3, java.awt.BorderLayout.LINE_START);

        jPanel1.add(jPanel2, java.awt.BorderLayout.PAGE_START);

        jPanel4.setBackground(new java.awt.Color(20, 20, 20));
        jPanel4.setMinimumSize(new java.awt.Dimension(0, 0));

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1129, Short.MAX_VALUE)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        jPanel1.add(jPanel4, java.awt.BorderLayout.PAGE_END);

        jPanel5.setMinimumSize(new java.awt.Dimension(100, 22));
        jPanel5.setLayout(new java.awt.BorderLayout());

        jPanel7.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel7.setMinimumSize(new java.awt.Dimension(0, 0));
        jPanel7.setPreferredSize(new java.awt.Dimension(995, 110));
        jPanel7.setLayout(new java.awt.BorderLayout());

        jLabel5.setFont(new java.awt.Font("Rockwell", 1, 24)); // NOI18N
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.setText("Chi-Square Randomness Test");
        jLabel5.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jPanel7.add(jLabel5, java.awt.BorderLayout.PAGE_START);

        jPanel9.setLayout(new java.awt.GridBagLayout());

        jLabel7.setBackground(new java.awt.Color(20, 20, 20));
        jLabel7.setFont(new java.awt.Font("Verdana", 1, 14)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel7.setText("DES Chi-Square result :");
        jLabel7.setToolTipText("");
        jLabel7.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jLabel7.setMaximumSize(new java.awt.Dimension(2147483647, 2147483647));
        jLabel7.setMinimumSize(new java.awt.Dimension(100, 20));
        jLabel7.setName(""); // NOI18N
        jLabel7.setOpaque(true);
        jLabel7.setPreferredSize(new java.awt.Dimension(310, 30));
        jPanel9.add(jLabel7, new java.awt.GridBagConstraints());

        ChiDes.setEditable(false);
        ChiDes.setFont(new java.awt.Font("Verdana", 1, 14)); // NOI18N
        ChiDes.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        ChiDes.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        ChiDes.setMinimumSize(new java.awt.Dimension(120, 20));
        ChiDes.setPreferredSize(new java.awt.Dimension(165, 30));
        jPanel9.add(ChiDes, new java.awt.GridBagConstraints());

        jLabel8.setBackground(new java.awt.Color(20, 20, 20));
        jLabel8.setFont(new java.awt.Font("Verdana", 1, 14)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel8.setText("AES Chi-Square result :");
        jLabel8.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jLabel8.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jLabel8.setMaximumSize(new java.awt.Dimension(2147483647, 2147483647));
        jLabel8.setMinimumSize(new java.awt.Dimension(120, 20));
        jLabel8.setOpaque(true);
        jLabel8.setPreferredSize(new java.awt.Dimension(310, 30));
        jPanel9.add(jLabel8, new java.awt.GridBagConstraints());

        ChiAes.setEditable(false);
        ChiAes.setFont(new java.awt.Font("Verdana", 1, 14)); // NOI18N
        ChiAes.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        ChiAes.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        ChiAes.setMinimumSize(new java.awt.Dimension(120, 20));
        ChiAes.setPreferredSize(new java.awt.Dimension(165, 30));
        jPanel9.add(ChiAes, new java.awt.GridBagConstraints());

        jLabel9.setBackground(new java.awt.Color(20, 20, 20));
        jLabel9.setFont(new java.awt.Font("Verdana", 1, 14)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel9.setText("Modified-Aes Chi-Square result :");
        jLabel9.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jLabel9.setMaximumSize(new java.awt.Dimension(2147483647, 2147483647));
        jLabel9.setMinimumSize(new java.awt.Dimension(120, 20));
        jLabel9.setOpaque(true);
        jLabel9.setPreferredSize(new java.awt.Dimension(280, 30));
        jPanel9.add(jLabel9, new java.awt.GridBagConstraints());

        ChiMAes.setEditable(false);
        ChiMAes.setFont(new java.awt.Font("Verdana", 1, 14)); // NOI18N
        ChiMAes.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        ChiMAes.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        ChiMAes.setMinimumSize(new java.awt.Dimension(120, 20));
        ChiMAes.setPreferredSize(new java.awt.Dimension(165, 30));
        jPanel9.add(ChiMAes, new java.awt.GridBagConstraints());

        jPanel7.add(jPanel9, java.awt.BorderLayout.CENTER);

        jPanel5.add(jPanel7, java.awt.BorderLayout.PAGE_END);

        jPanel8.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel8.setMinimumSize(new java.awt.Dimension(80, 22));
        jPanel8.setPreferredSize(new java.awt.Dimension(350, 234));
        jPanel8.setLayout(new java.awt.BorderLayout());

        jLabel6.setFont(new java.awt.Font("Sitka Text", 2, 14)); // NOI18N
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel6.setText("Fig:- Comparative Bar Chart");
        jLabel6.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jLabel6.setPreferredSize(new java.awt.Dimension(189, 50));
        jPanel8.add(jLabel6, java.awt.BorderLayout.PAGE_END);

        jPanel10.setMinimumSize(new java.awt.Dimension(80, 22));
        jPanel10.setPreferredSize(new java.awt.Dimension(350, 234));
        jPanel10.setLayout(new java.awt.BorderLayout());
        jPanel8.add(jPanel10, java.awt.BorderLayout.CENTER);

        jPanel5.add(jPanel8, java.awt.BorderLayout.LINE_END);

        jPanel11.setLayout(new java.awt.BorderLayout());

        jPanel12.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel12.setLayout(new java.awt.BorderLayout());

        jPanel6.setBackground(new java.awt.Color(39, 36, 46));
        jPanel6.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        jPanel6.setLayout(new javax.swing.BoxLayout(jPanel6, javax.swing.BoxLayout.LINE_AXIS));

        jLabel2.setFont(new java.awt.Font("Rockwell", 1, 24)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Algorithms");
        jPanel6.add(jLabel2);

        jPanel12.add(jPanel6, java.awt.BorderLayout.LINE_START);

        jPanel14.setPreferredSize(new java.awt.Dimension(1000, 100));
        jPanel14.setLayout(new java.awt.BorderLayout());

        jPanel15.setBackground(new java.awt.Color(39, 36, 46));
        jPanel15.setLayout(new java.awt.GridLayout(1, 0));

        jLabel3.setFont(new java.awt.Font("Rockwell", 1, 24)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("DES");
        jLabel3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        jLabel3.setPreferredSize(new java.awt.Dimension(300, 20));
        jPanel15.add(jLabel3);

        jLabel4.setFont(new java.awt.Font("Rockwell", 1, 24)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setText("AES");
        jLabel4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        jLabel4.setPreferredSize(new java.awt.Dimension(300, 20));
        jPanel15.add(jLabel4);

        jLabel10.setFont(new java.awt.Font("Rockwell", 1, 24)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(255, 255, 255));
        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel10.setText("Modified-AES");
        jLabel10.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        jLabel10.setPreferredSize(new java.awt.Dimension(300, 20));
        jPanel15.add(jLabel10);

        jPanel14.add(jPanel15, java.awt.BorderLayout.CENTER);

        jPanel17.setBackground(new java.awt.Color(190, 200, 200));
        jPanel17.setLayout(new java.awt.GridLayout(1, 0));

        jLabel11.setFont(new java.awt.Font("Rockwell", 1, 18)); // NOI18N
        jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel11.setText("Encryption");
        jLabel11.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel17.add(jLabel11);

        jLabel12.setFont(new java.awt.Font("Rockwell", 1, 18)); // NOI18N
        jLabel12.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel12.setText("Decryption");
        jLabel12.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel17.add(jLabel12);

        jLabel13.setFont(new java.awt.Font("Rockwell", 1, 18)); // NOI18N
        jLabel13.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel13.setText("Decryption");
        jLabel13.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel17.add(jLabel13);

        jLabel14.setFont(new java.awt.Font("Rockwell", 1, 18)); // NOI18N
        jLabel14.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel14.setText("Encryption");
        jLabel14.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel17.add(jLabel14);

        jLabel15.setFont(new java.awt.Font("Rockwell", 1, 18)); // NOI18N
        jLabel15.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel15.setText("Decryption");
        jLabel15.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel17.add(jLabel15);

        jLabel16.setFont(new java.awt.Font("Rockwell", 1, 18)); // NOI18N
        jLabel16.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel16.setText("Encryption");
        jLabel16.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel17.add(jLabel16);

        jPanel14.add(jPanel17, java.awt.BorderLayout.PAGE_END);

        jPanel12.add(jPanel14, java.awt.BorderLayout.CENTER);

        jPanel11.add(jPanel12, java.awt.BorderLayout.PAGE_START);

        jPanel13.setLayout(new java.awt.BorderLayout());

        jPanel16.setBackground(new java.awt.Color(39, 36, 46));
        jPanel16.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        jPanel16.setPreferredSize(new java.awt.Dimension(138, 233));
        jPanel16.setLayout(new javax.swing.BoxLayout(jPanel16, javax.swing.BoxLayout.LINE_AXIS));

        jLabel17.setFont(new java.awt.Font("Rockwell", 1, 22)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(255, 255, 255));
        jLabel17.setText("Time Taken");
        jPanel16.add(jLabel17);

        jPanel13.add(jPanel16, java.awt.BorderLayout.LINE_START);

        jPanel18.setPreferredSize(new java.awt.Dimension(1050, 150));

        jLabel18.setFont(new java.awt.Font("Sitka Text", 2, 14)); // NOI18N
        jLabel18.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel18.setText("Table:- Time Taken by Algorithm");
        jLabel18.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jLabel18.setPreferredSize(new java.awt.Dimension(222, 50));
        jPanel18.add(jLabel18);

        jPanel13.add(jPanel18, java.awt.BorderLayout.PAGE_END);

        jPanel19.setPreferredSize(new java.awt.Dimension(944, 200));
        jPanel19.setLayout(new java.awt.GridLayout(1, 0));

        jTextField1.setEditable(false);
        jTextField1.setFont(new java.awt.Font("Segoe UI", 2, 18)); // NOI18N
        jTextField1.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTextField1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jTextField1.setMaximumSize(new java.awt.Dimension(64, 27));
        jTextField1.setOpaque(true);
        jPanel19.add(jTextField1);

        jTextField2.setEditable(false);
        jTextField2.setFont(new java.awt.Font("Segoe UI", 2, 18)); // NOI18N
        jTextField2.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTextField2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jTextField2.setMaximumSize(new java.awt.Dimension(64, 27));
        jTextField2.setOpaque(true);
        jPanel19.add(jTextField2);

        jTextField3.setEditable(false);
        jTextField3.setFont(new java.awt.Font("Segoe UI", 2, 18)); // NOI18N
        jTextField3.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTextField3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jTextField3.setMaximumSize(new java.awt.Dimension(64, 27));
        jTextField3.setOpaque(true);
        jPanel19.add(jTextField3);

        jTextField4.setEditable(false);
        jTextField4.setFont(new java.awt.Font("Segoe UI", 2, 18)); // NOI18N
        jTextField4.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTextField4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jTextField4.setMaximumSize(new java.awt.Dimension(64, 27));
        jTextField4.setOpaque(true);
        jPanel19.add(jTextField4);

        jTextField5.setEditable(false);
        jTextField5.setFont(new java.awt.Font("Segoe UI", 2, 18)); // NOI18N
        jTextField5.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTextField5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jTextField5.setMaximumSize(new java.awt.Dimension(64, 27));
        jTextField5.setOpaque(true);
        jPanel19.add(jTextField5);

        jTextField6.setEditable(false);
        jTextField6.setFont(new java.awt.Font("Segoe UI", 2, 18)); // NOI18N
        jTextField6.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTextField6.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jTextField6.setMaximumSize(new java.awt.Dimension(64, 27));
        jTextField6.setOpaque(true);
        jPanel19.add(jTextField6);

        jPanel13.add(jPanel19, java.awt.BorderLayout.CENTER);

        jPanel11.add(jPanel13, java.awt.BorderLayout.CENTER);

        jPanel5.add(jPanel11, java.awt.BorderLayout.CENTER);

        jPanel1.add(jPanel5, java.awt.BorderLayout.CENTER);

        getContentPane().add(jPanel1);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        previousHome.setVisible(true);
        previousHome.getJButton2().setEnabled(false);
        dispose();

    }//GEN-LAST:event_jButton1ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Result.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Result.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Result.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Result.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Result().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField ChiAes;
    private javax.swing.JTextField ChiDes;
    private javax.swing.JTextField ChiMAes;
    private javax.swing.JButton jButton1;
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
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel16;
    private javax.swing.JPanel jPanel17;
    private javax.swing.JPanel jPanel18;
    private javax.swing.JPanel jPanel19;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    public static javax.swing.JTextField jTextField1;
    public static javax.swing.JTextField jTextField2;
    public static javax.swing.JTextField jTextField3;
    public static javax.swing.JTextField jTextField4;
    public static javax.swing.JTextField jTextField5;
    public static javax.swing.JTextField jTextField6;
    // End of variables declaration//GEN-END:variables
}
