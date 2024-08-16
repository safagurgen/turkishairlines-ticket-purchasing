package codes;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class PaymentPanel extends JPanel {
    private JFrame parentFrame;
    private List<Integer> seatIds;
    private BigDecimal totalPrice;
    private int passengerCount;
    private int flightId;

    public PaymentPanel(JFrame parentFrame, List<Integer> seatIds, BigDecimal totalPrice, int passengerCount, int flightId) {
        this.parentFrame = parentFrame;
        this.seatIds = seatIds;
        this.totalPrice = totalPrice;
        this.passengerCount = passengerCount;
        this.flightId = flightId;

        // Panel düzeni
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(800, 600));
        setBackground(Color.WHITE);

        // Üst gezinti menüsü ve başlık paneli
        JPanel menuPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
        menuPanel.setBackground(Color.DARK_GRAY);
        JButton homeButton = new JButton("Anasayfa");
        JButton backButton = new JButton("Geri Dön");

        // Buton stilini ayarlama
        setButtonStyle(homeButton);
        setButtonStyle(backButton);

        // Butonları menü paneline ekleme
        menuPanel.add(backButton);
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

        // Başlık
        JLabel paymentLabel = new JLabel("Ödeme Yap");
        paymentLabel.setFont(new Font("Arial", Font.BOLD, 24));
        paymentLabel.setHorizontalAlignment(JLabel.CENTER);

        // Kart bilgileri paneli
        JPanel cardInfoPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Kart Numarası
        JLabel cardNumberLabel = new JLabel("Kart Numarası:");
        JTextField cardNumberField = new JTextField(20);
        gbc.gridx = 0;
        gbc.gridy = 0;
        cardInfoPanel.add(cardNumberLabel, gbc);
        gbc.gridx = 1;
        cardInfoPanel.add(cardNumberField, gbc);

        // CVV
        JLabel cvvLabel = new JLabel("CVV:");
        JTextField cvvField = new JTextField(4);
        gbc.gridx = 0;
        gbc.gridy = 1;
        cardInfoPanel.add(cvvLabel, gbc);
        gbc.gridx = 1;
        cardInfoPanel.add(cvvField, gbc);

        // Son Kullanma Tarihi
        JLabel expiryDateLabel = new JLabel("Son Kullanma Tarihi (MM/YY):");
        JTextField expiryDateField = new JTextField(5);
        gbc.gridx = 0;
        gbc.gridy = 2;
        cardInfoPanel.add(expiryDateLabel, gbc);
        gbc.gridx = 1;
        cardInfoPanel.add(expiryDateField, gbc);

        // Ödeme Tutarı
        JLabel paymentAmountLabel = new JLabel("Ödeme Tutarı:");
        JTextField paymentAmountField = new JTextField(totalPrice.toString());
        paymentAmountField.setEditable(false); // Kullanıcı bu alanı değiştiremez
        gbc.gridx = 0;
        gbc.gridy = 3;
        cardInfoPanel.add(paymentAmountLabel, gbc);
        gbc.gridx = 1;
        cardInfoPanel.add(paymentAmountField, gbc);

        // Ödeme Yap butonu
        JButton payButton = new JButton("Ödeme Yap");
        setButtonStyle(payButton);
        gbc.gridx = 1;
        gbc.gridy = 4;
        gbc.anchor = GridBagConstraints.EAST;
        cardInfoPanel.add(payButton, gbc);

        // İçerik paneli
        JPanel contentPanel = new JPanel(new BorderLayout());
        contentPanel.add(paymentLabel, BorderLayout.NORTH);
        contentPanel.add(cardInfoPanel, BorderLayout.CENTER);
        contentPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Paneli düzenleme
        add(headerPanel, BorderLayout.NORTH);
        add(contentPanel, BorderLayout.CENTER);

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

        // Geri Dön butonuna listener ekleme
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                parentFrame.getContentPane().removeAll();
                parentFrame.getContentPane().add(new SeatSelectionPanel(parentFrame, passengerCount, flightId));
                parentFrame.revalidate();
                parentFrame.repaint();
            }
        });

        // Ödeme Yap butonuna listener ekleme
        payButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String cardNumber = cardNumberField.getText();
                String cvv = cvvField.getText();
                String expiryDate = expiryDateField.getText();
                String paymentAmount = paymentAmountField.getText();

                if (cardNumber.isEmpty() || cvv.isEmpty() || expiryDate.isEmpty() || paymentAmount.isEmpty()) {
                    JOptionPane.showMessageDialog(parentFrame, "Lütfen tüm alanları doldurunuz.", "Hata", JOptionPane.ERROR_MESSAGE);
                } else {
                    // Veritabanına kayıt
                    String sql = "INSERT INTO payments (seat_id, card_number, cvv, expiry_date, payment_amount) VALUES (?, ?, ?, ?, ?)";

                    try (Connection conn = DatabaseConnection.getConnection()) {
                        for (int seatId : seatIds) {
                            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                                pstmt.setInt(1, seatId);
                                pstmt.setString(2, cardNumber);
                                pstmt.setString(3, cvv);
                                pstmt.setString(4, expiryDate);
                                pstmt.setBigDecimal(5, new BigDecimal(paymentAmount));
                                pstmt.executeUpdate();
                            }
                        }
                        JOptionPane.showMessageDialog(parentFrame, "Ödeme işleminiz gerçekleştirilmiştir.", "Başarılı", JOptionPane.INFORMATION_MESSAGE);
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(parentFrame, "Ödeme işlemi başarısız oldu!", "Hata", JOptionPane.ERROR_MESSAGE);
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
}
