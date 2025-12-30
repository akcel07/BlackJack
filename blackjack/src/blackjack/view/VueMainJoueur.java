package blackjack.view;

import blackjack.model.jeu.JeuBlackjack;
import cartes.model.Paquet;
import cartes.model.observer.EcouteurModele;
import cartes.view.VuePaquetVisible;
import java.awt.Color;

public class VueMainJoueur extends VuePaquetVisible implements EcouteurModele {

    private final JeuBlackjack jeu;

    public VueMainJoueur(JeuBlackjack jeu) {
        
        super(jeu.getJoueur().getMain());
        this.jeu = jeu;
        
        
        this.jeu.ajoutEcouteur(this);

        
        setOpaque(false);
        setBackground(new Color(0,0,0,0));
    }


    @Override
    public void modeleMisAJour(Object source) {

        Paquet mainActive = jeu.getJoueur().getMain();
        setPaquet(mainActive);
        repaint();
    }

    @Override
    public void setPaquet(Paquet paquet) {
        super.setPaquet(paquet); 
        repaint();
    }
    
    public JeuBlackjack getJeu() {
        return jeu;
    }
}