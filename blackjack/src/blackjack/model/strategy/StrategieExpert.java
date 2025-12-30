package blackjack.model.strategy;

import blackjack.model.jeu.BlackjackUtils;
import blackjack.model.jeu.ReglesBlackjack;
import blackjack.model.joueur.Action;
import cartes.model.Carte;
import cartes.model.Paquet;

/**
 * Stratégie "Expert" pour un joueur IA.
 * Combine plusieurs logiques pour prendre la meilleure décision possible :
 * - Décide de doubler sur 10 ou 11.
 * - Applique la règle "Hit on Soft 17".
 * - Devient agressive si le croupier a une meilleure main.
 * - Devient prudente si le joueur est devant.
 */
public class StrategieExpert implements StrategieJeu {

    @Override
    public Action choisirAction(Paquet main, Paquet mainCroupier, boolean peutDoubler, boolean peutSplitter) {
        int scoreJoueur = BlackjackUtils.calculerScore(main);
        int scoreVisibleCroupier = BlackjackUtils.calculerScore(mainCroupier);

        // Règle 1 : Décision de DOUBLER (prioritaire)
        if (peutDoubler && (scoreJoueur == 10 || scoreJoueur == 11)) {
            System.out.println("Joueur IA (Expert): Score de " + scoreJoueur + ", je double !");
            return Action.DOUBLER;
        }

        // Règle 2 : Toujours tirer si le score est inférieur à 17.
        if (scoreJoueur < ReglesBlackjack.SCORE_CROUPIER_MIN) {
            return Action.TIRER;
        }

        // Règle 3 : Stratégie "Hit on Soft 17".
        if (scoreJoueur == ReglesBlackjack.SCORE_CROUPIER_MIN) {
            for (Carte carte : main.getCartes()) {
                if (BlackjackUtils.estAs(carte)) {
                    System.out.println("Joueur IA (Expert): Soft 17, je tire !");
                    return Action.TIRER; // C'est un "soft 17", on tire.
                }
            }
        }

        // Règle 4 : Logique adaptative contre le croupier.
        // Le joueur ne joue que si le croupier n'a pas déjà "busté".
        if (scoreVisibleCroupier <= ReglesBlackjack.VALEUR_MAX) {
            // Cas AGRESSIF : Le croupier est devant, il faut tenter de le battre.
            if (scoreJoueur < scoreVisibleCroupier) {
                if (scoreJoueur < 18) { // Seuil de risque : ne tire pas sur un 18 "dur" ou plus.
                    System.out.println("Joueur IA (Expert): Croupier a " + scoreVisibleCroupier + ", je suis à " + scoreJoueur + ". Je dois tirer !");
                    return Action.TIRER;
                } else {
                    System.out.println("Joueur IA (Expert): Croupier a " + scoreVisibleCroupier + ", mais mon score de " + scoreJoueur + " est trop risqué pour tirer.");
                    // Laisser passer vers la décision de RESTER par défaut.
                }
            }
            // Cas PRUDENT : Le joueur est déjà devant, pas besoin de risquer de "buster".
            // Cette condition est implicite : si scoreJoueur >= scoreVisibleCroupier, on ne fait rien.
        }

        // Règle par défaut : si aucune des conditions ci-dessus n'a mené à une action, on s'arrête.
        return Action.RESTER;
    }

    }