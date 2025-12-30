package blackjack.view;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import javax.imageio.ImageIO;
import java.io.IOException;

public class VueAccueil extends JPanel {

    private JButton boutonJouer;
    private Image imageFond;
    private Image imageLogo;

    public VueAccueil() {
        // --- 1. CHARGEMENT DES IMAGES ---
        try {
            imageFond = ImageIO.read(new File("src/blackjack/images/fond_casino.png")); 
            // N'oublie pas de vérifier le nom de ton image logo
        } catch (IOException e) {
            System.out.println("Erreur chargement images : " + e.getMessage());
            setBackground(new Color(20, 80, 20)); 
        }

        setLayout(new GridBagLayout()); 
        GridBagConstraints gbc = new GridBagConstraints();

        // --- 2. CONFIGURATION DU LOGO ---
        JLabel labelLogo = new JLabel();
        if (imageLogo != null) {
            labelLogo.setIcon(new ImageIcon(imageLogo));
        }

        // --- 3. CONFIGURATION DU BOUTON ---
        boutonJouer = new JButton("JOUER");
        boutonJouer.setPreferredSize(new Dimension(200, 50));
        boutonJouer.setBackground(new Color(0, 153, 204)); 
        boutonJouer.setForeground(Color.WHITE);
        boutonJouer.setFont(new Font("SansSerif", Font.BOLD, 20));
        boutonJouer.setFocusPainted(false);
        boutonJouer.setBorder(BorderFactory.createLineBorder(Color.WHITE, 1));
        boutonJouer.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // --- 4. PLACEMENT ---
        
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0; 
        gbc.anchor = GridBagConstraints.CENTER; 
        gbc.fill = GridBagConstraints.NONE;
        add(labelLogo, gbc);

        // >> LE BOUTON (Tout en bas)
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weighty = 0.0; 
        gbc.anchor = GridBagConstraints.PAGE_END; 
        gbc.insets = new Insets(0, 0, 50, 0); 
        add(boutonJouer, gbc);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (imageFond != null) {
            g.drawImage(imageFond, 0, 0, getWidth(), getHeight(), this);
        }
    }

    public JButton getBoutonJouer() {
        return boutonJouer;
    }
}