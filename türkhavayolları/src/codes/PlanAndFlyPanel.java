package codes;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PlanAndFlyPanel extends JPanel {
    private JFrame parentFrame;

    public PlanAndFlyPanel(JFrame parentFrame) {
        this.parentFrame = parentFrame;

        // Panel düzeni
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(800, 600));
        setBackground(Color.WHITE);

        // Üst gezinti menüsü ve başlık paneli
        JPanel menuPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
        menuPanel.setBackground(Color.DARK_GRAY);
        JButton homeButton = new JButton("Anasayfa");

        // Buton stilini ayarlama
        setButtonStyle(homeButton);

        // Butonları menü paneline ekleme
        menuPanel.add(homeButton);

        // Logo ve başlık paneli
        JPanel titlePanel = new JPanel();
        titlePanel.setLayout(new BoxLayout(titlePanel, BoxLayout.X_AXIS));
        titlePanel.setBackground(Color.DARK_GRAY);

        // Logo
        ImageIcon logoIcon = new ImageIcon(getClass().getClassLoader().getResource("images/thy1.png"));
        Image logoImage = logoIcon.getImage(); // Resmi Image olarak al
        Image resizedLogoImage = logoImage.getScaledInstance(50, 50, java.awt.Image.SCALE_SMOOTH); // Yeniden boyutlandır
        ImageIcon resizedLogoIcon = new ImageIcon(resizedLogoImage); // Yeniden boyutlandırılmış resmi ImageIcon olarak ayarla
        JLabel logoLabel = new JLabel(resizedLogoIcon);

        // Başlık
        JLabel titleLabel = new JLabel("Türk Hava Yolları");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(Color.WHITE); // Başlık yazı rengini beyaz yap

        // Logo ve başlığı titlePanel'e ekleme
        titlePanel.add(logoLabel);
        titlePanel.add(Box.createRigidArea(new Dimension(10, 0))); // Boşluk
        titlePanel.add(titleLabel);

        // Üst paneli oluşturma
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(Color.DARK_GRAY);
        headerPanel.add(titlePanel, BorderLayout.WEST);
        headerPanel.add(menuPanel, BorderLayout.EAST);

        // Bilet türü seçim butonları
        JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JRadioButton roundTripButton = new JRadioButton("Gidiş-Dönüş");
        JRadioButton oneWayButton = new JRadioButton("Tek Yön");
        JRadioButton stopoverButton = new JRadioButton("İstanbul'da Stopover");
        JRadioButton multiCityButton = new JRadioButton("Çoklu Uçuş");

        // Buton grubu oluşturma
        ButtonGroup group = new ButtonGroup();
        group.add(roundTripButton);
        group.add(oneWayButton);
        group.add(stopoverButton);
        group.add(multiCityButton);

        buttonsPanel.add(roundTripButton);
        buttonsPanel.add(oneWayButton);
        buttonsPanel.add(stopoverButton);
        buttonsPanel.add(multiCityButton);

        // Uçuş arama bileşenleri
        JPanel searchPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Nereden
        JLabel fromLabel = new JLabel("Nereden");
        JTextField fromField = new JTextField(10);

        // Nereye
        JLabel toLabel = new JLabel("Nereye");
        JTextField toField = new JTextField(10);

        // Tarih
        JLabel dateLabel = new JLabel("Tarih");
        JTextField dateField = new JTextField(10);

        // Yolcular
        JLabel passengersLabel = new JLabel("Yolcu Sayısı");
        JSpinner passengersSpinner = new JSpinner(new SpinnerNumberModel(1, 1, 10, 1));

        // Uçuş Ara butonu
        JButton searchButton = new JButton("Uçuş Ara");
        searchButton.setBackground(Color.RED);
        searchButton.setForeground(Color.WHITE);

        gbc.gridx = 0;
        gbc.gridy = 0;
        searchPanel.add(fromLabel, gbc);

        gbc.gridx = 1;
        searchPanel.add(toLabel, gbc);

        gbc.gridx = 2;
        searchPanel.add(dateLabel, gbc);

        gbc.gridx = 3;
        searchPanel.add(passengersLabel, gbc);

        gbc.gridx = 4;
        gbc.gridheight = 2;
        gbc.fill = GridBagConstraints.VERTICAL;
        searchPanel.add(searchButton, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridheight = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        searchPanel.add(fromField, gbc);

        gbc.gridx = 1;
        searchPanel.add(toField, gbc);

        gbc.gridx = 2;
        searchPanel.add(dateField, gbc);

        gbc.gridx = 3;
        searchPanel.add(passengersSpinner, gbc);

        // Paneli ortalamak için bir container panel
        JPanel centerPanel = new JPanel(new GridBagLayout());
        GridBagConstraints centerGbc = new GridBagConstraints();
        centerGbc.gridx = 0;
        centerGbc.gridy = 0;
        centerGbc.anchor = GridBagConstraints.CENTER;
        centerPanel.add(buttonsPanel, centerGbc);

        centerGbc.gridy = 1;
        centerPanel.add(searchPanel, centerGbc);

        // Paneli düzenleme
        add(headerPanel, BorderLayout.NORTH);
        add(centerPanel, BorderLayout.CENTER);

        // Anasayfa butonuna listener ekleme
        homeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                parentFrame.getContentPane().removeAll();
                parentFrame.getContentPane().add(new TurkishAirlinesGUI().getMainPanel());
                parentFrame.revalidate();
                parentFrame.repaint();
            }
        });

        // Uçuş Ara butonuna listener ekleme
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String from = fromField.getText();
                String to = toField.getText();
                String date = dateField.getText();
                int passengerCount = (int) passengersSpinner.getValue();

                if (from.isEmpty() || to.isEmpty() || date.isEmpty() || !isRadioButtonSelected(group)) {
                    JOptionPane.showMessageDialog(parentFrame, "Lütfen bilgileri doldurunuz.", "Hata", JOptionPane.ERROR_MESSAGE);
                } else {
                    String sql = "SELECT flight_id FROM flights WHERE from_location = ? AND to_location = ? AND flight_date = ?";

                    try (Connection conn = DatabaseConnection.getConnection();
                         PreparedStatement pstmt = conn.prepareStatement(sql)) {
                        pstmt.setString(1, from);
                        pstmt.setString(2, to);
                        pstmt.setString(3, date);
                        ResultSet rs = pstmt.executeQuery();

                        if (rs.next()) {
                            int flightId = rs.getInt("flight_id");

                            parentFrame.getContentPane().removeAll();
                            parentFrame.getContentPane().add(new SeatSelectionPanel(parentFrame, passengerCount, flightId));
                            parentFrame.revalidate();
                            parentFrame.repaint();
                        } else {
                            JOptionPane.showMessageDialog(parentFrame, "Uçuş bulunamadı.", "Hata", JOptionPane.ERROR_MESSAGE);
                        }
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(parentFrame, "Uçuş arama işlemi başarısız oldu!", "Hata", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });
    }

    private void setButtonStyle(JButton button) {
        button.setBackground(Color.DARK_GRAY);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setOpaque(true);
    }

    private boolean isRadioButtonSelected(ButtonGroup group) {
        return group.getSelection() != null;
    }
}
