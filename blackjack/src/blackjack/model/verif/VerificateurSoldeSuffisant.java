package blackjack.model.verif;

import blackjack.model.joueur.IJoueur;

public class VerificateurSoldeSuffisant extends Verificateur {

    public VerificateurSoldeSuffisant(Verificateur suivant) {
        super(suivant);
    }

    @Override
    protected boolean verifLocale(int mise, IJoueur joueur) {
        return joueur.getSolde() >= mise;  // Vérifie si le joueur a assez d'argent
    }
}
