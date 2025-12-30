package blackjack.model.strategy;

import blackjack.model.jeu.BlackjackUtils;
import blackjack.model.jeu.ReglesBlackjack;
import blackjack.model.joueur.Action;
import cartes.model.Paquet;

/**
 * Stratégie "prudente" pour un joueur IA.
 * Cette stratégie vise à minimiser les risques de "buster".
 * Elle évite de prendre des risques inutiles.
 */
public class StrategiePrudente implements StrategieJeu {

    @Override
    public Action choisirAction(Paquet main, Paquet mainCroupier, boolean peutDoubler, boolean peutSplitter) {
        int scoreJoueur = BlackjackUtils.calculerScore(main);
        int scoreVisibleCroupier = BlackjackUtils.calculerScore(mainCroupier);

        // Règle 1 : Décision de DOUBLER (très prudente)
        // On ne double que sur 11, la meilleure situation possible.
        if (peutDoubler && scoreJoueur == 11) {
            System.out.println("Joueur IA (Prudent): Score de 11, je tente de doubler.");
            return Action.DOUBLER;
        }

        // Règle 2 : Logique PRUDENTE. Si le croupier semble faible (2-6), on ne prend aucun risque.
        boolean croupierEstFaible = scoreVisibleCroupier >= 2 && scoreVisibleCroupier <= 6;
        if (croupierEstFaible && scoreJoueur >= 14) {
            System.out.println("Joueur IA (Prudent): Croupier à " + scoreVisibleCroupier + ", je préfère rester sur " + scoreJoueur + ".");
            return Action.RESTER;
        }

        // Règle 3 : Règle de base, on s'arrête toujours à 17 ou plus.
        if (scoreJoueur >= ReglesBlackjack.SCORE_CROUPIER_MIN) {
            return Action.RESTER;
        }
        
        // Règle 4 : Si le score est bas, on peut tirer.
        if (scoreJoueur < 14) {
             return Action.TIRER;
        }

        // Règle par défaut : Dans le doute, la prudence dicte de s'arrêter.
        return Action.RESTER;
    }

    
}