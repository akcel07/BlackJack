package blackjack.model.strategy;

import blackjack.model.jeu.BlackjackUtils;
import blackjack.model.jeu.ReglesBlackjack;
import blackjack.model.joueur.Action;
import cartes.model.Carte;
import cartes.model.Paquet;

/**
 * Stratégie agressive pour un joueur IA : "Hit on Soft 17" et doublement fréquent.
 * Le joueur tire une carte s'il a 17 avec un As (soft 17).
 * Le joueur double sa mise dès que son score est de 9, 10 ou 11.
 */
public class StrategieAgressive implements StrategieJeu {

    @Override
    public Action choisirAction(Paquet main, Paquet mainCroupier, boolean peutDoubler, boolean peutSplitter) {
        int scoreJoueur = BlackjackUtils.calculerScore(main);

        // Règle 1 : Décision de DOUBLER (agressive)
        // On double sur 9, 10 ou 11 si possible.
        if (peutDoubler && (scoreJoueur >= 9 && scoreJoueur <= 11)) {
            System.out.println("Joueur IA (Agressif): Score de " + scoreJoueur + ", je double !");
            return Action.DOUBLER;
        }

        // Règle 2 : Toujours tirer si le score est inférieur à 17
        if (scoreJoueur < ReglesBlackjack.SCORE_CROUPIER_MIN) {
            return Action.TIRER;
        }

        // Règle 3 : Stratégie "Hit on Soft 17"
        if (scoreJoueur == ReglesBlackjack.SCORE_CROUPIER_MIN) {
            // Un "soft 17" est un score de 17 obtenu avec un As comptant pour 11.
            for (Carte carte : main.getCartes()) {
                if (BlackjackUtils.estAs(carte)) {
                    System.out.println("Joueur IA (Agressif): Soft 17, je tire !");
                    return Action.TIRER;
                }
            }
        }

        // Règle par défaut : Ne pas tirer si le score est supérieur à 17 (et que ce n'est pas un soft 17)
        return Action.RESTER;
    }


}
