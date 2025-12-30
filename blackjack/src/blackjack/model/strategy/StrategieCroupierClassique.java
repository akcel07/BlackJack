package blackjack.model.strategy;

import blackjack.model.jeu.BlackjackUtils;
import blackjack.model.jeu.ReglesBlackjack;
import blackjack.model.joueur.Action;
import cartes.model.Paquet;

/**
 * Stratégie classique du croupier : tirer jusqu'à 17.
 */
public class StrategieCroupierClassique implements StrategieJeu {

    @Override
    public Action choisirAction(Paquet main, Paquet mainCroupier, boolean peutDoubler, boolean peutSplitter) {
        // Le croupier ne double pas et ne splitte pas. Il tire ou il reste.
        // La logique est simple : tirer si le score est inférieur à la limite fixée par les règles.
        if (BlackjackUtils.calculerScore(main) < ReglesBlackjack.SCORE_CROUPIER_MIN) {
            return Action.TIRER;
        }
        return Action.RESTER;
    }
}
