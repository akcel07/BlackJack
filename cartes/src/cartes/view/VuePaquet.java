package cartes.view;

import javax.swing.JPanel;
import cartes.model.Paquet;
import cartes.model.observer.EcouteurModele;

public abstract class VuePaquet extends JPanel implements EcouteurModele {

    protected Paquet modele;

    public VuePaquet(Paquet p) {
        this.modele = p;
        p.ajoutEcouteur(this);
    }

    public Paquet getModele() {
        return modele;
    }

    @Override
    public void modeleMisAJour(Object source) {
        repaint();
    }
}