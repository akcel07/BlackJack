package blackjack.model.verif;

import blackjack.model.joueur.IJoueur;

/**
 * Chain of Responsibility :
 * maillon abstrait de vérification d'une mise.
 */
public abstract class Verificateur {

    private Verificateur suivant;

    public Verificateur(Verificateur suivant) {
        this.suivant = suivant;
    }

    /**
     * Vérifie la mise. Si la vérification locale passe,
     * délègue au maillon suivant s'il existe.
     */
    public final boolean verifier(int mise, IJoueur joueur) {
        if (!verifLocale(mise, joueur)) {
            return false;
        }
        if (suivant != null) {
            return suivant.verifier(mise, joueur);
        }
        return true;
    }

    protected abstract boolean verifLocale(int mise, IJoueur joueur);
}
