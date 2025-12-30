package cartes.view;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import cartes.model.Carte;
import cartes.model.Paquet;

public class VuePaquetVisible extends VuePaquet {

    private static final int LARGEUR_CARTE = 80;
    private static final int HAUTEUR_CARTE = 120;
    private static final int ESPACEMENT = 90;

    public VuePaquetVisible(Paquet p) {
        super(p);
    }

    public void setPaquet(Paquet p) {
        this.modele = p; // Assurez-vous que 'modele' est protected dans VuePaquet
        repaint();
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(20 + modele.taille() * ESPACEMENT, HAUTEUR_CARTE + 40);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        List<Carte> cartes = modele.getCartes();

        int x = 10;
        for (Carte c : cartes) {
            g2.setColor(Color.WHITE);
            g2.fillRoundRect(x, 20, LARGEUR_CARTE, HAUTEUR_CARTE, 12, 12);

            g2.setColor(Color.BLACK);
            g2.drawRoundRect(x, 20, LARGEUR_CARTE, HAUTEUR_CARTE, 12, 12);

            String symbole = switch (c.getCouleur()) {
                case "pique" -> "♠";
                case "cœur" -> "♥";
                case "carreau" -> "♦";
                default -> "♣";
            };

            Color color = (c.getCouleur().equals("cœur") || c.getCouleur().equals("carreau")) ? Color.RED : Color.BLACK;
            g2.setColor(color);

            g2.setFont(new Font("SansSerif", Font.BOLD, 16));
            g2.drawString(c.getHauteur(), x + 10, 50);
            g2.drawString(symbole, x + 10, 90);

            x += ESPACEMENT;
        }
    }
}
