package blackjack.model.jeu;

import cartes.model.Paquet;

/**
 * Constantes et règles du Blackjack.
 * Cette classe regroupe les règles et les constantes associées au jeu de Blackjack.
 */
public final class ReglesBlackjack {

    // Empêche l'instanciation de la classe.
    private ReglesBlackjack() {}

    // Constantes des règles du jeu
    public static final int VALEUR_MAX = 21;
    public static final int SCORE_CROUPIER_MIN = 17;

    /**
     * Vérifie si la main d'un joueur est un Blackjack.
     * Un Blackjack est une main de 21 points avec exactement 2 cartes.
     * 
     * @param main La main à vérifier.
     * @return true si la main est un Blackjack, false sinon.
     */
    public static boolean estBlackjack(Paquet main) {
        return BlackjackUtils.calculerScore(main) == VALEUR_MAX && main.taille() == 2;
    }

    /**
     * Vérifie si un joueur a gagné par rapport au croupier selon les règles du Blackjack.
     * Le joueur gagne si son score est plus élevé que celui du croupier, sans dépasser 21.
     * 
     * @param scoreJoueur Le score du joueur.
     * @param scoreCroupier Le score du croupier.
     * @return true si le joueur gagne, false sinon.
     */
    public static boolean joueurGagne(int scoreJoueur, int scoreCroupier) {
        // Le joueur gagne si son score est supérieur à celui du croupier et qu'il ne dépasse pas 21
        return scoreJoueur > scoreCroupier && scoreJoueur <= VALEUR_MAX;
    }

    /**
     * Vérifie si le croupier doit tirer une carte en fonction de son score.
     * Le croupier doit tirer tant que son score est inférieur à 17.
     * 
     * @param scoreCroupier Le score actuel du croupier.
     * @return true si le croupier doit tirer, false sinon.
     */
    public static boolean croupierDoitTirer(int scoreCroupier) {
        return scoreCroupier < SCORE_CROUPIER_MIN;
    }

    /**
     * Vérifie si un joueur ou le croupier est "busté", c'est-à-dire si son score dépasse 21.
     * 
     * @param score Le score à vérifier.
     * @return true si le score est supérieur à 21, false sinon.
     */
    public static boolean estBuste(int score) {
        return score > VALEUR_MAX;
    }
}
