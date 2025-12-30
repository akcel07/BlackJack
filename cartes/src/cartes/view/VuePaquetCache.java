
package cartes.view;

import javax.swing.*;
import java.awt.*;
import cartes.model.Paquet;

public class VuePaquetCache extends VuePaquet {

    public VuePaquetCache(Paquet p) {
        super(p);
        setPreferredSize(new Dimension(80, 120));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        int n = modele.taille();

        for (int i = 0; i < Math.min(n, 10); i++) {
            g.drawRect(10 + i, 20 + i, 60, 80);
        }
    }
}