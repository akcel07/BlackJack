package blackjack.model.verif;

import blackjack.model.joueur.IJoueur;

/**
 * Vérifie que la mise est supérieure à une mise minimale.
 */
public class VerifMiseMin extends Verificateur {

    private final int miseMin;

    public VerifMiseMin(int miseMin, Verificateur suivant) {
        super(suivant);
        this.miseMin = miseMin;
    }

    @Override
    protected boolean verifLocale(int mise, IJoueur joueur) {
        return mise >= miseMin;
    }
}
