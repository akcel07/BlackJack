package blackjack.model.joueur;

import blackjack.model.strategy.StrategieJeu;
import cartes.model.Paquet;

/**
 * Joueur IA générique, utilisant une StrategieJeu.
 */
public class JoueurIA extends JoueurConcret {

    private StrategieJeu strategie;

    public JoueurIA(String nom, int soldeInitial, StrategieJeu strategie) {
        super(nom, soldeInitial, true);
        this.strategie = strategie;
    }

    /**
     * Demande à la stratégie de choisir la meilleure action à effectuer.
     * @param mainCroupier La main du croupier (pour des stratégies plus avancées).
     * @param peutDoubler Indique si l'action "Doubler" est autorisée.
     * @param peutSplitter Indique si l'action "Splitter" est autorisée.
     * @return L'action choisie par la stratégie.
     */
    public Action choisirAction(Paquet mainCroupier, boolean peutDoubler, boolean peutSplitter) {
        return strategie.choisirAction(getMain(), mainCroupier, peutDoubler, peutSplitter);
    }

    /**
     * Permet de changer la stratégie du joueur IA pendant le jeu.
     */
    public void setStrategie(StrategieJeu nouvelleStrategie) {
        this.strategie = nouvelleStrategie;
    }
}
