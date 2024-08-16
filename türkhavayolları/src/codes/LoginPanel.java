package codes;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginPanel extends JPanel {
    public LoginPanel(JFrame parentFrame) {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        // Üst gezinti menüsü
        JPanel menuPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
        menuPanel.setBackground(Color.DARK_GRAY);
        JButton homeButton = new JButton("Ana Sayfa");
        JButton signupButton = new JButton("Kayıt Ol");

        // Buton stilini ayarlama
        setButtonStyle(homeButton);
        setButtonStyle(signupButton);

        // Butonları menü paneline ekleme
        menuPanel.add(homeButton);
        menuPanel.add(signupButton);

        // Başlık ve logo paneli
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

        // Giriş paneli
        JPanel loginPanel = new JPanel(new GridBagLayout());
        loginPanel.setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50)); // Sağdan, alttan ve soldan 3-4 piksellik boşluk
        loginPanel.setBackground(Color.WHITE);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel emailLabel = new JLabel("E-posta:");
        gbc.gridx = 0;
        gbc.gridy = 0;
        loginPanel.add(emailLabel, gbc);

        JTextField emailField = new JTextField(20);
        gbc.gridx = 1;
        gbc.gridy = 0;
        loginPanel.add(emailField, gbc);

        JLabel passwordLabel = new JLabel("Şifre:");
        gbc.gridx = 0;
        gbc.gridy = 1;
        loginPanel.add(passwordLabel, gbc);

        JPasswordField passwordField = new JPasswordField(20);
        gbc.gridx = 1;
        gbc.gridy = 1;
        loginPanel.add(passwordField, gbc);

        JButton loginButton = new JButton("Giriş Yap");
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        loginPanel.add(loginButton, gbc);

        // Anasayfa ve Kayıt Ol butonları için eylem dinleyicileri
        homeButton.addActionListener(e -> {
            parentFrame.getContentPane().removeAll();
            parentFrame.getContentPane().add(new TurkishAirlinesGUI().getMainPanel());
            parentFrame.revalidate();
            parentFrame.repaint();
        });

        signupButton.addActionListener(e -> {
            parentFrame.getContentPane().removeAll();
            parentFrame.getContentPane().add(new SignupPanel(parentFrame));
            parentFrame.revalidate();
            parentFrame.repaint();
        });

        // Giriş yap butonu için eylem dinleyicisi
        loginButton.addActionListener(e -> {
            String email = emailField.getText();
            String password = new String(passwordField.getPassword());

            if (email.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(parentFrame, "Lütfen tüm alanları doldurunuz.", "Hata", JOptionPane.ERROR_MESSAGE);
            } else {
                // Veritabanından kontrol
                String sql = "SELECT * FROM Users WHERE email = ? AND password = ?";

                try (Connection conn = DatabaseConnection.getConnection();
                     PreparedStatement pstmt = conn.prepareStatement(sql)) {
                    pstmt.setString(1, email);
                    pstmt.setString(2, password);
                    ResultSet rs = pstmt.executeQuery();

                    if (rs.next()) {
                        JOptionPane.showMessageDialog(parentFrame, "Giriş başarılı!", "Başarılı", JOptionPane.INFORMATION_MESSAGE);
                        // Giriş başarılı, ana panele geçiş
                        parentFrame.getContentPane().removeAll();
                        parentFrame.getContentPane().add(new TurkishAirlinesGUI().getMainPanel());
                        parentFrame.revalidate();
                        parentFrame.repaint();
                    } else {
                        JOptionPane.showMessageDialog(parentFrame, "Geçersiz e-posta veya şifre.", "Hata", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(parentFrame, "Veritabanı bağlantı hatası.", "Hata", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // Paneli birleştirme
        add(headerPanel, BorderLayout.NORTH);
        add(loginPanel, BorderLayout.CENTER);
    }

    private void setButtonStyle(JButton button) {
        button.setBackground(Color.DARK_GRAY);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setOpaque(true);
    }
}
