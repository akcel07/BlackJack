package blackjack.model.jeu;

import java.util.List;
import cartes.model.Carte;
import cartes.model.Paquet;

/**
 * Méthodes utilitaires pour le calcul des scores en Blackjack.
 */
public final class BlackjackUtils {

    private BlackjackUtils() {}

    /**
     * Calcule le score d'une main de Blackjack.
     * Un As peut valoir 1 ou 11 selon la situation.
     */
    public static int calculerScore(Paquet main) {
        List<Carte> cartes = main.getCartes();
        int total = 0;
        int nbAs = 0;

        // Calcul de la somme des cartes et comptabilisation des As
        for (Carte c : cartes) {
            int v = valeurBlackjack(c);
            total += v;
            if (estAs(c)) {
                nbAs++;
            }
        }

        // Ajustement de la valeur des As : si le total dépasse 21, chaque As vaut 1 au lieu de 11
        while (total > ReglesBlackjack.VALEUR_MAX && nbAs > 0) {
            total -= 10; // Un As passe de 11 à 1
            nbAs--;
        }

        return total;
    }

    /**
     * Retourne la valeur d'une carte en Blackjack.
     * Les cartes "valet", "dame" et "roi" valent 10,
     * un "as" vaut 11 sauf en cas de dépassement de 21, auquel cas il vaut 1.
     */
    public static int valeurBlackjack(Carte c) {
        String h = c.getHauteur(); // "2".."10","valet","dame","roi","as"

        if (h == null) {
            throw new IllegalArgumentException("Carte invalide avec hauteur null");
        }

        switch (h) {
            case "valet":
            case "dame":
            case "roi":
                return 10;
            case "as":
                return 11;
            default:
                try {
                    return Integer.parseInt(h);
                } catch (NumberFormatException e) {
                    throw new IllegalArgumentException("Carte invalide avec hauteur: " + h);
                }
        }
    }

    /**
     * Vérifie si la carte est un As.
     */
    public static boolean estAs(Carte c) {
        return "as".equals(c.getHauteur());
    }

    /**
     * Vérifie si une main dépasse la valeur maximale de 21, ce qui signifie que la main est "bustée".
     */
    public static boolean estBuste(Paquet main) {
        return calculerScore(main) > ReglesBlackjack.VALEUR_MAX;
    }
}
