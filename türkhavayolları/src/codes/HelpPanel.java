package codes;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class HelpPanel extends JPanel {
    private TurkishAirlinesGUI parent;
    private JFrame parentFrame;

    public HelpPanel(TurkishAirlinesGUI parent, JFrame parentFrame) {
        this.parent = parent;
        this.parentFrame = parentFrame;

        // Panel düzeni
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        // Üst gezinti menüsü ve başlık paneli
        JPanel menuPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
        menuPanel.setBackground(Color.DARK_GRAY);
        JButton homeButton = new JButton("Anasayfa");

        // Buton stilini ayarlama
        setButtonStyle(homeButton);

        // Butonları menü paneline ekleme
        menuPanel.add(homeButton);

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
        JLabel titleLabel = new JLabel("Türk Hava Yolları - Geri Bildirim");
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

        // Anasayfa butonu için eylem dinleyicisi
        homeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                parentFrame.getContentPane().removeAll();
                parentFrame.getContentPane().add(parent.getMainPanel());
                parentFrame.revalidate();
                parentFrame.repaint();
            }
        });

        // İletişim teşekkür mesajı
        JLabel thankYouLabel = new JLabel("Bizimle iletişime geçtiğiniz için teşekkürler!");
        thankYouLabel.setFont(new Font("Arial", Font.PLAIN, 16));

        // Bilgi paragrafı
        JTextArea infoTextArea = new JTextArea("Konforlu ve güvenli bir seyahat deneyimi yaşamanız öncelikli amacımız. "
                + "Sunduğumuz hizmetleri geliştirebilmek için seyahat sırasında yaşadığınız tecrübeleriniz ve ayrıca görüşleriniz "
                + "hakkında bilgi sahibi olmaktan mutluluk duyarız. "
                + "Sayfanın altında yer alan 'Yeni geri bildirim' bölümünden tüm görüş ve beklentilerinizi bize ulaştırabilirsiniz. "
                + "Daha önce geri bildirimde bulunduğunuz konuları sayfanın altında yer alan 'Önceki geri bildirim' bölümünden "
                + "geri bildirim kayıt numarasıyla takip edebilirsiniz. "
                + "Geri bildirim değerlendirme sürecini buradan inceleyebilirsiniz.");
        infoTextArea.setLineWrap(true);
        infoTextArea.setWrapStyleWord(true);
        infoTextArea.setEditable(false);

        // Notlar paragrafı
        JTextArea notesTextArea = new JTextArea(
                "- Herhangi bir sorunla karşılaşmanız durumunda sık sorulan sorular sayfamızdan bilgi alabilirsiniz.\n"
                        + "- Diğer iletişim kanallarımızı görmek için bize ulaşın sayfamızı ziyaret edebilirsiniz.\n"
                        + "- AnadoluJet ile gerçekleştirdiğiniz uçuşlar için geri bildirimlerinizi buradan iletebilirsiniz.\n"
                        + "- Kayıp bagaj, hasarlı bagaj, geç teslim edilen bagaj, eksik eşya ve unutulan eşya konulu bildirimlerinizi, "
                        + "bagaj aksaklık bildirimi ve takibi sayfamızdan kontrol edebilirsiniz.\n"
                        + "- Miles&Smiles üyesiyseniz giriş yaparak işleminize devam edebilirsiniz. "
                        + "Üye değilseniz giriş yapmadan devam edebilirsiniz.\n"
                        + "- Uçuşunuza 24 saatten daha az kaldıysa lütfen geri bildirim formunu doldurmak yerine doğrudan çağrı merkezimizle iletişime geçiniz.");
        notesTextArea.setLineWrap(true);
        notesTextArea.setWrapStyleWord(true);
        notesTextArea.setEditable(false);

        // Scroll panelleri oluştur
        JScrollPane infoScrollPane = new JScrollPane(infoTextArea);
        JScrollPane notesScrollPane = new JScrollPane(notesTextArea);

        // Mail adresi alanı
        JLabel mailLabel = new JLabel("Mail adres:");
        JTextField mailField = new JTextField(20);

        // Geri bildirim text alanı
        JLabel feedbackLabel = new JLabel("Geri Bildirim:");
        JTextArea feedbackTextArea = new JTextArea(5, 20);
        JScrollPane feedbackScrollPane = new JScrollPane(feedbackTextArea);

        // Gönder butonu
        JButton sendButton = new JButton("Gönder");
        setButtonStyle(sendButton);
        sendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String mail = mailField.getText();
                String feedback = feedbackTextArea.getText();

                if (mail.isEmpty() || feedback.isEmpty()) {
                    JOptionPane.showMessageDialog(parentFrame, "Lütfen tüm alanları doldurunuz.", "Hata", JOptionPane.ERROR_MESSAGE);
                } else {
                    // Veritabanına kayıt
                    String sql = "INSERT INTO Feedback (email, feedback) VALUES (?, ?)";

                    try (Connection conn = DatabaseConnection.getConnection();
                         PreparedStatement pstmt = conn.prepareStatement(sql)) {
                        pstmt.setString(1, mail);
                        pstmt.setString(2, feedback);
                        pstmt.executeUpdate();
                        JOptionPane.showMessageDialog(parentFrame, "Geri bildiriminiz alınmıştır. Teşekkür ederiz!", "Başarılı", JOptionPane.INFORMATION_MESSAGE);
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(parentFrame, "Geri bildirim kaydedilemedi!", "Hata", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });

        // Panel düzeni
        JPanel contentPanel = new JPanel(new GridBagLayout());
        contentPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        contentPanel.add(thankYouLabel, gbc);

        gbc.gridy = 1;
        gbc.gridwidth = 2;
        contentPanel.add(infoScrollPane, gbc);

        gbc.gridy = 2;
        gbc.gridwidth = 2;
        contentPanel.add(notesScrollPane, gbc);

        gbc.gridy = 3;
        gbc.gridwidth = 1;
        contentPanel.add(mailLabel, gbc);

        gbc.gridx = 1;
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        contentPanel.add(mailField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        contentPanel.add(feedbackLabel, gbc);

        gbc.gridx = 1;
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        contentPanel.add(feedbackScrollPane, gbc);

        gbc.gridx = 1;
        gbc.gridy = 5;
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.anchor = GridBagConstraints.CENTER;
        contentPanel.add(sendButton, gbc);

        // Paneli ekrana ekleme
        add(headerPanel, BorderLayout.NORTH);
        add(contentPanel, BorderLayout.CENTER);
    }

    private void setButtonStyle(JButton button) {
        button.setBackground(Color.DARK_GRAY);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setOpaque(true);
    }
}
