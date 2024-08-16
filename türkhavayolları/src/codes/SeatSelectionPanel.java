package codes;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SeatSelectionPanel extends JPanel {
    private JFrame parentFrame;
    private int passengerCount;
    private List<JToggleButton> selectedSeats;
    private int flightId;

    public SeatSelectionPanel(JFrame parentFrame, int passengerCount, int flightId) {
        this.parentFrame = parentFrame;
        this.passengerCount = passengerCount;
        this.flightId = flightId;
        this.selectedSeats = new ArrayList<>();

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
        JLabel seatSelectionLabel = new JLabel("Koltuk Seçme Ekranı");
        seatSelectionLabel.setFont(new Font("Arial", Font.BOLD, 24));
        seatSelectionLabel.setHorizontalAlignment(JLabel.CENTER);

        // Koltuk seçim paneli
        JPanel seatSelectionPanel = new JPanel(new GridLayout(5, 5, 10, 10)); // 5x5'lik bir grid
        for (int i = 1; i <= 25; i++) {
            JToggleButton seatButton = new JToggleButton("Koltuk " + i);
            seatSelectionPanel.add(seatButton);

            int seatId = i; // Seat ID'yi burada tut
            seatButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (seatButton.isSelected()) {
                        selectedSeats.add(seatButton);
                    } else {
                        selectedSeats.remove(seatButton);
                    }

                    if (selectedSeats.size() > passengerCount) {
                        seatButton.setSelected(false);
                        selectedSeats.remove(seatButton);
                        JOptionPane.showMessageDialog(parentFrame, "Sadece " + passengerCount + " koltuk seçebilirsiniz.", "Hata", JOptionPane.ERROR_MESSAGE);
                    }
                }
            });
        }

        // İçerik paneli
        JPanel contentPanel = new JPanel(new BorderLayout());
        contentPanel.add(seatSelectionLabel, BorderLayout.NORTH);
        contentPanel.add(seatSelectionPanel, BorderLayout.CENTER);
        contentPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Ödeme Yap butonu
        JButton paymentButton = new JButton("Ödeme Yap");
        setButtonStyle(paymentButton);
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.add(paymentButton);

        // Paneli düzenleme
        add(headerPanel, BorderLayout.NORTH);
        add(contentPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

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
                parentFrame.getContentPane().add(new PlanAndFlyPanel(parentFrame));
                parentFrame.revalidate();
                parentFrame.repaint();
            }
        });

        // Ödeme Yap butonuna listener ekleme
        paymentButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (selectedSeats.size() != passengerCount) {
                    JOptionPane.showMessageDialog(parentFrame, "Lütfen " + passengerCount + " koltuk seçiniz.", "Hata", JOptionPane.ERROR_MESSAGE);
                } else {
                    BigDecimal totalPrice = BigDecimal.ZERO;
                    List<Integer> seatIds = new ArrayList<>();

                    // Seçilen koltukların bilgilerini veritabanına kaydet
                    String sql = "UPDATE seats SET is_booked = 1 WHERE seat_id = ? AND flight_id = ?";
                    String priceQuery = "SELECT price FROM seats WHERE seat_id = ? AND flight_id = ?";

                    try (Connection conn = DatabaseConnection.getConnection()) {
                        for (JToggleButton seatButton : selectedSeats) {
                            int seatId = Integer.parseInt(seatButton.getText().replace("Koltuk ", ""));
                            try (PreparedStatement pstmt = conn.prepareStatement(sql);
                                 PreparedStatement priceStmt = conn.prepareStatement(priceQuery)) {
                                pstmt.setInt(1, seatId);
                                pstmt.setInt(2, flightId);
                                pstmt.executeUpdate();

                                priceStmt.setInt(1, seatId);
                                priceStmt.setInt(2, flightId);
                                ResultSet rs = priceStmt.executeQuery();
                                if (rs.next()) {
                                    totalPrice = totalPrice.add(rs.getBigDecimal("price"));
                                }
                                seatIds.add(seatId);
                            }
                        }

                        // PaymentPanel ekranına geçiş
                        parentFrame.getContentPane().removeAll();
                        parentFrame.getContentPane().add(new PaymentPanel(parentFrame, seatIds, totalPrice, passengerCount, flightId));
                        parentFrame.revalidate();
                        parentFrame.repaint();
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(parentFrame, "Koltuk rezervasyonu başarısız oldu!", "Hata", JOptionPane.ERROR_MESSAGE);
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
