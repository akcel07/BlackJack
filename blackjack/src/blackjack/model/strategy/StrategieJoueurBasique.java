package blackjack.model.strategy;

import blackjack.model.jeu.BlackjackUtils;
import blackjack.model.joueur.Action;
import cartes.model.Paquet;

/**
 * Une stratégie de base pour un joueur IA.
 * - Double si le score est 10 ou 11.
 * - Tire si le score est inférieur à 17.
 * - Reste sinon.
 */
public class StrategieJoueurBasique implements StrategieJeu {

    @Override
    public Action choisirAction(Paquet main, Paquet mainCroupier, boolean peutDoubler, boolean peutSplitter) {
        int scoreJoueur = BlackjackUtils.calculerScore(main);

        // Règle pour doubler : si le score est 10 ou 11 et que c'est possible.
        if (peutDoubler && (scoreJoueur == 10 || scoreJoueur == 11)) {
            return Action.DOUBLER;
        }

        // Règle de base : tirer en dessous de 17.
        if (scoreJoueur < 17) {
            return Action.TIRER;
        } else {
            return Action.RESTER;
        }
    }


}