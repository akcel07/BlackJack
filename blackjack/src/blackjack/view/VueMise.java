package blackjack.view;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class VueMise extends JPanel {

    private JTextField champMise;
    private JButton boutonDistribuer; 
    private JLabel labelSolde;
    private JLabel labelNbCartes;
    private Image imageFond;
    private JComboBox<String> comboStrategie;

    public VueMise() {
     
        try {
            imageFond = ImageIO.read(new File("src/blackjack/images/fond_pieces.png"));
        } catch (IOException e) {
            setBackground(new Color(20, 40, 20)); 
        }

        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        JPanel panelInfos = new JPanel(new FlowLayout(FlowLayout.CENTER, 50, 0)); 
        panelInfos.setOpaque(false);

       
        labelSolde = new JLabel("Solde : 1000 €");
        labelSolde.setFont(new Font("Segoe UI", Font.BOLD, 18));
        labelSolde.setForeground(Color.WHITE);
        
        
        JPanel panelSolde = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                g.setColor(new Color(0, 0, 0, 180));
                g.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);
                super.paintComponent(g);
            }
        };
        panelSolde.setOpaque(false);
        panelSolde.setBorder(new EmptyBorder(10, 20, 10, 20));
        panelSolde.add(labelSolde);

       
        labelNbCartes = new JLabel("52");
        
        labelNbCartes.setForeground(new Color(0, 0, 0, 0)); 
        labelNbCartes.setFont(new Font("Segoe UI", Font.BOLD, 1)); 
        
        
        labelNbCartes.setIcon(new Icon() {
            @Override
            public void paintIcon(Component c, Graphics g, int x, int y) {
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
                
                
                int w = getIconWidth();
                int h = getIconHeight();

                
                g2.setColor(new Color(220, 220, 220));
                g2.fillRoundRect(x, y-6, w, h, 6, 6);
                g2.setColor(Color.GRAY);
                g2.drawRoundRect(x, y-6, w, h, 6, 6);

                
                g2.setColor(new Color(235, 235, 235));
                g2.fillRoundRect(x+3, y-3, w, h, 6, 6);
                g2.setColor(Color.GRAY);
                g2.drawRoundRect(x+3, y-3, w, h, 6, 6);

                
                int topX = x + 6;
                int topY = y;
                g2.setColor(Color.WHITE);
                g2.fillRoundRect(topX, topY, w, h, 6, 6);
                g2.setColor(new Color(180, 50, 50)); 
                g2.setStroke(new BasicStroke(2));
                g2.drawRoundRect(topX, topY, w, h, 6, 6);

                
                String texte = ((JLabel)c).getText();
                
                texte = texte.replaceAll("[^0-9]", ""); 
                
                g2.setColor(Color.BLACK);
                g2.setFont(new Font("Segoe UI", Font.BOLD, 20)); 
                FontMetrics fm = g2.getFontMetrics();
                
                
                int textX = topX + (w - fm.stringWidth(texte)) / 2;
                int textY = topY + (h - fm.getHeight()) / 2 + fm.getAscent();
                
                g2.drawString(texte, textX, textY);
            }

            @Override
            public int getIconWidth() { return 45; }
            @Override
            public int getIconHeight() { return 65; }
        });

        panelInfos.add(panelSolde);
        panelInfos.add(labelNbCartes);

        
        gbc.gridx = 0; gbc.gridy = 0;
        gbc.weightx = 1.0; gbc.weighty = 0.3;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(20, 0, 0, 0);
        add(panelInfos, gbc);



        JPanel panelCentral = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
            
                g.setColor(new Color(0, 0, 0, 150));
                g.fillRoundRect(0, 0, getWidth(), getHeight(), 30, 30);
                super.paintComponent(g);
            }
        };
        panelCentral.setOpaque(false);
        panelCentral.setLayout(new BoxLayout(panelCentral, BoxLayout.Y_AXIS));
        panelCentral.setBorder(new EmptyBorder(40, 60, 40, 60));


        JLabel labelTitre = new JLabel("VOTRE MISE");
        labelTitre.setFont(new Font("Segoe UI", Font.BOLD, 22));
        labelTitre.setForeground(Color.LIGHT_GRAY);
        labelTitre.setAlignmentX(Component.CENTER_ALIGNMENT);

    
        champMise = new JTextField("100");
        champMise.setFont(new Font("Consolas", Font.BOLD, 36));
        champMise.setForeground(new Color(255, 215, 0)); 
        champMise.setCaretColor(Color.WHITE); 
        champMise.setHorizontalAlignment(JTextField.CENTER);
        champMise.setOpaque(false);
        champMise.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 0, 2, 0, new Color(255, 215, 0)),
                new EmptyBorder(10, 10, 10, 10)
        ));
        champMise.setMaximumSize(new Dimension(200, 60));
        champMise.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        
        JLabel labelStrategie = new JLabel("Stratégie de l'IA");
        labelStrategie.setFont(new Font("Segoe UI", Font.BOLD, 16));
        labelStrategie.setForeground(Color.LIGHT_GRAY);
        labelStrategie.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        String[] strategies = { "Basique", "Agressive", "Prudente", "Experte" };
        comboStrategie = new JComboBox<>(strategies);
        comboStrategie.setMaximumSize(new Dimension(200, 30));
        comboStrategie.setAlignmentX(Component.CENTER_ALIGNMENT);

        
        boutonDistribuer = new JButton("DISTRIBUER");
        boutonDistribuer.setPreferredSize(new Dimension(200, 50));
        boutonDistribuer.setMaximumSize(new Dimension(200, 50));
        boutonDistribuer.setBackground(new Color(39, 174, 96)); 
        boutonDistribuer.setForeground(Color.WHITE);
        boutonDistribuer.setFont(new Font("Segoe UI", Font.BOLD, 18));
        boutonDistribuer.setFocusPainted(false);
        boutonDistribuer.setBorder(BorderFactory.createRaisedBevelBorder());
        boutonDistribuer.setCursor(new Cursor(Cursor.HAND_CURSOR));
        boutonDistribuer.setAlignmentX(Component.CENTER_ALIGNMENT);

        panelCentral.add(labelTitre);
        panelCentral.add(Box.createVerticalStrut(20));
        panelCentral.add(champMise);
        panelCentral.add(Box.createVerticalStrut(20));
        panelCentral.add(labelStrategie);
        panelCentral.add(Box.createVerticalStrut(5));
        panelCentral.add(comboStrategie);
        panelCentral.add(Box.createVerticalStrut(30));
        panelCentral.add(boutonDistribuer);

        gbc.gridx = 0; gbc.gridy = 1;
        gbc.weighty = 0.7;
        gbc.anchor = GridBagConstraints.NORTH;
        add(panelCentral, gbc);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (imageFond != null) {
            g.drawImage(imageFond, 0, 0, getWidth(), getHeight(), this);
        }
    }

    public JButton getBoutonDistribuer() { return boutonDistribuer; }
    public JTextField getChampMise() { return champMise; }
    public JLabel getLabelSolde() { return labelSolde; }
    public JLabel getLabelNbCartes() { return labelNbCartes; }
    public JComboBox<String> getComboStrategie() { return comboStrategie; }
}