package cartes.controller;

import cartes.view.*;
import cartes.model.*;

import java.awt.event.*;

public class ControleurChoixCarteVersPaquet implements MouseListener {

    private VuePaquetVisible vue;
    private Paquet destination;

    public ControleurChoixCarteVersPaquet(VuePaquetVisible vueSource, Paquet dest) {
        this.vue = vueSource;
        this.destination = dest;
        vueSource.addMouseListener(this);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        int x = e.getX();

        // Chaque carte fait 70px de large (60 + 10 de marge)
        int indexCarte = (x - 10) / 70;

        if (indexCarte >= 0 && indexCarte < vue.getModele().taille()) {
            Carte c = vue.getModele().retirer(indexCarte);
            destination.ajouter(c);
        }
    }

    @Override public void mousePressed(MouseEvent e) {}
    @Override public void mouseReleased(MouseEvent e) {}
    @Override public void mouseEntered(MouseEvent e) {}
    @Override public void mouseExited(MouseEvent e) {}
}
