package codes;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SignupPanel extends JPanel {
    public SignupPanel(JFrame parentFrame) {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        // Üst gezinti menüsü
        JPanel menuPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
        menuPanel.setBackground(Color.DARK_GRAY);
        JButton homeButton = new JButton("Ana Sayfa");
        JButton loginButton = new JButton("Giriş Yap");

        // Buton stilini ayarlama
        setButtonStyle(homeButton);
        setButtonStyle(loginButton);

        // Butonları menü paneline ekleme
        menuPanel.add(homeButton);
        menuPanel.add(loginButton);

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

        // Kayıt ol paneli
        JPanel signupPanel = new JPanel(new GridBagLayout());
        signupPanel.setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50)); // Sağdan, alttan ve soldan 3-4 piksellik boşluk
        signupPanel.setBackground(Color.WHITE);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel nameLabel = new JLabel("Ad Soyad:");
        gbc.gridx = 0;
        gbc.gridy = 0;
        signupPanel.add(nameLabel, gbc);

        JTextField nameField = new JTextField(20);
        gbc.gridx = 1;
        gbc.gridy = 0;
        signupPanel.add(nameField, gbc);

        JLabel emailLabel = new JLabel("E-posta:");
        gbc.gridx = 0;
        gbc.gridy = 1;
        signupPanel.add(emailLabel, gbc);

        JTextField emailField = new JTextField(20);
        gbc.gridx = 1;
        gbc.gridy = 1;
        signupPanel.add(emailField, gbc);

        JLabel passwordLabel = new JLabel("Şifre:");
        gbc.gridx = 0;
        gbc.gridy = 2;
        signupPanel.add(passwordLabel, gbc);

        JPasswordField passwordField = new JPasswordField(20);
        gbc.gridx = 1;
        gbc.gridy = 2;
        signupPanel.add(passwordField, gbc);

        JLabel idLabel = new JLabel("T.C. Kimlik No:");
        gbc.gridx = 0;
        gbc.gridy = 3;
        signupPanel.add(idLabel, gbc);

        JTextField idField = new JTextField(20);
        gbc.gridx = 1;
        gbc.gridy = 3;
        signupPanel.add(idField, gbc);

        JButton signupButton = new JButton("Kayıt Ol");
        gbc.gridx = 1;
        gbc.gridy = 4;
        gbc.anchor = GridBagConstraints.CENTER;
        signupPanel.add(signupButton, gbc);

        // Kayıt ol butonu için eylem dinleyicisi
        signupButton.addActionListener(e -> {
            String name = nameField.getText();
            String email = emailField.getText();
            String password = new String(passwordField.getPassword());
            String nationalId = idField.getText();

            if (name.isEmpty() || email.isEmpty() || password.isEmpty() || nationalId.isEmpty()) {
                JOptionPane.showMessageDialog(parentFrame, "Lütfen tüm alanları doldurunuz.", "Hata", JOptionPane.ERROR_MESSAGE);
            } else {
                // Veritabanına kayıt
                String sql = "INSERT INTO Users (name, email, password, national_id) VALUES (?, ?, ?, ?)";

                try (Connection conn = DatabaseConnection.getConnection();
                     PreparedStatement pstmt = conn.prepareStatement(sql)) {
                    pstmt.setString(1, name);
                    pstmt.setString(2, email);
                    pstmt.setString(3, password);
                    pstmt.setString(4, nationalId);
                    pstmt.executeUpdate();
                    JOptionPane.showMessageDialog(parentFrame, "Kayıt başarılı!", "Başarılı", JOptionPane.INFORMATION_MESSAGE);
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(parentFrame, "Kayıt başarısız!", "Hata", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // Anasayfa ve Giriş Yap butonları için eylem dinleyicileri
        homeButton.addActionListener(e -> {
            parentFrame.getContentPane().removeAll();
            parentFrame.getContentPane().add(new TurkishAirlinesGUI().getMainPanel());
            parentFrame.revalidate();
            parentFrame.repaint();
        });

        loginButton.addActionListener(e -> {
            parentFrame.getContentPane().removeAll();
            parentFrame.getContentPane().add(new LoginPanel(parentFrame));
            parentFrame.revalidate();
            parentFrame.repaint();
        });

        // Paneli birleştirme
        add(headerPanel, BorderLayout.NORTH);
        add(signupPanel, BorderLayout.CENTER);
    }

    private void setButtonStyle(JButton button) {
        button.setBackground(Color.DARK_GRAY);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setOpaque(true);
    }
}
