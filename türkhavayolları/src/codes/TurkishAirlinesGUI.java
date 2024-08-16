package codes;

import javax.swing.*;
import java.awt.*;

public class TurkishAirlinesGUI {
    private static JFrame frame;
    private JPanel mainPanel;

    public static void main(String[] args) {
        // Ana pencere oluşturma
        frame = new JFrame("Türk Hava Yolları");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600); // Pencere boyutunu biraz büyütelim

        TurkishAirlinesGUI gui = new TurkishAirlinesGUI();
        frame.getContentPane().add(gui.getMainPanel());

        frame.setVisible(true);
    }

    public TurkishAirlinesGUI() {
        // Üst gezinti menüsü
        JPanel menuPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 3, 3));
        JButton loginButton = new JButton("GİRİŞ YAP");
        JButton signupButton = new JButton("ÜYE OL");
        JButton planAndFlyButton = new JButton("PLANLA&UÇ");
        JButton helpButton = new JButton("YARDIM");

        // Buton stilini ayarlama
        setButtonStyle(loginButton);
        setButtonStyle(signupButton);
        setButtonStyle(planAndFlyButton);
        setButtonStyle(helpButton);

        // Butonları menü paneline ekleme
        menuPanel.add(planAndFlyButton);
        menuPanel.add(helpButton);
        menuPanel.add(signupButton);
        menuPanel.add(loginButton);

        // Menü paneli arka plan rengini ayarlama
        menuPanel.setBackground(Color.DARK_GRAY);

        // Başlık ve logo paneli
        JPanel titlePanel = new JPanel();
        titlePanel.setLayout(new BoxLayout(titlePanel, BoxLayout.X_AXIS));

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
        titleLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        // Logo ve başlığı titlePanel'e ekleme
        titlePanel.add(logoLabel);
        titlePanel.add(Box.createRigidArea(new Dimension(10, 0))); // Boşluk
        titlePanel.add(titleLabel);

        // Başlık paneli arka plan rengini ayarlama
        titlePanel.setBackground(Color.DARK_GRAY);

        // Başlık ve menü panelini birleştirme
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.add(titlePanel, BorderLayout.WEST);
        headerPanel.add(menuPanel, BorderLayout.EAST);

        // Header paneli arka plan rengini ayarlama
        headerPanel.setBackground(Color.DARK_GRAY);

        // İçerik paneli
        JPanel contentPanel = new JPanel(new GridBagLayout());
        contentPanel.setBorder(BorderFactory.createEmptyBorder(3, 3, 3, 3)); // Sağdan, alttan ve soldan 3-4 piksellik boşluk
        JLabel contentLabel = new JLabel("Hoş geldiniz!");
        contentLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        contentPanel.add(contentLabel, new GridBagConstraints());

        // Ana düzen
        mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.add(headerPanel, BorderLayout.NORTH);
        mainPanel.add(contentPanel, BorderLayout.CENTER);

        // Giriş yap butonunun eylem dinleyicisi
        loginButton.addActionListener(e -> {
            // Giriş yap butonuna tıklandığında LoginPanel'a geçiş
            frame.getContentPane().removeAll();
            frame.getContentPane().add(new LoginPanel(frame));
            frame.revalidate();
            frame.repaint();
        });

        // Kayıt ol butonunun eylem dinleyicisi
        signupButton.addActionListener(e -> {
            // Kayıt ol butonuna tıklandığında SignupPanel'a geçiş
            frame.getContentPane().removeAll();
            frame.getContentPane().add(new SignupPanel(frame));
            frame.revalidate();
            frame.repaint();
        });

        planAndFlyButton.addActionListener(e -> {
            // Planla ve Uç butonuna tıklandığında PlanAndFlyPanel ekranına geçiş
            frame.getContentPane().removeAll();
            frame.getContentPane().add(new PlanAndFlyPanel(frame));
            frame.revalidate();
            frame.repaint();
        });

        helpButton.addActionListener(e -> {
            // Yardım butonuna tıklandığında HelpPanel ekranına geçiş
            frame.getContentPane().removeAll();
            frame.getContentPane().add(new HelpPanel(this, frame));
            frame.revalidate();
            frame.repaint();
        });
    }

    private void setButtonStyle(JButton button) {
        button.setBackground(Color.DARK_GRAY);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setOpaque(true);
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }
}
