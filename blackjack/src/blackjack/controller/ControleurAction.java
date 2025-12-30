package blackjack.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import blackjack.view.VueJeu;

/**
 * Contrôleur des actions de jeu (boutons sur le tapis).
 */
public class ControleurAction {

    private final VueJeu vue;

    public ControleurAction(VueJeu vue) {
        this.vue = vue;

        // ---------------------------------------------------------
        // 1. ACTION : TIRER
        // ---------------------------------------------------------
        this.vue.getBoutonTirer().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                vue.getModel().joueurTire(); 
            }
        });

        // ---------------------------------------------------------
        // 2. ACTION : SPLIT
        // ---------------------------------------------------------
        this.vue.getBoutonSplit().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 1. On lance la séparation dans le modèle
                vue.getModel().joueurSplit(); // Le modèle notifiera la vue qui se mettra à jour
            }
        });

        // ---------------------------------------------------------
        // 3. ACTION : RESTER (Sécurisé pour le Split)
        // ---------------------------------------------------------
        this.vue.getBoutonRester().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // On dit simplement au modèle que le joueur veut rester.
                // Le modèle gérera la suite (passer à la main suivante ou au croupier).
                vue.getModel().joueurReste();
            }
        });

        // ---------------------------------------------------------
        // 4. ACTION : DOUBLER
        // ---------------------------------------------------------
        this.vue.getBoutonDoubler().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // On appelle la méthode du modèle qui gère tout (mise, tirage, rester auto)
                vue.getModel().joueurDouble();
            }
        });
    }
}