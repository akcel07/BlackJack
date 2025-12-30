package cartes.controller;

import cartes.model.*;
import cartes.view.*;

import java.awt.event.*;

public class ControleurPiocheVersPaquet implements MouseListener {

    private VuePaquet vue;
    private Paquet destination;

    public ControleurPiocheVersPaquet(VuePaquet vueSource, Paquet dest) {
        this.vue = vueSource;
        this.destination = dest;
        vueSource.addMouseListener(this);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        Carte c = vue.getModele().retirerPremiere();
        if (c != null) {
            destination.ajouter(c);
        }
    }

    @Override public void mousePressed(MouseEvent e) {}
    @Override public void mouseReleased(MouseEvent e) {}
    @Override public void mouseEntered(MouseEvent e) {}
    @Override public void mouseExited(MouseEvent e) {}
}
