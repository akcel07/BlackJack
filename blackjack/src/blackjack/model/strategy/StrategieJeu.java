package blackjack.model.strategy;

import blackjack.model.joueur.Action;
import cartes.model.Paquet;

/**
 * Strategy : définit la décision "tirer ou rester"
 * en fonction de la main et éventuellement d'informations
 * sur l'adversaire.
 * Interface pour une stratégie de jeu au Blackjack (Pattern Strategy).
 * Définit l'algorithme de décision pour savoir si un joueur doit tirer une carte.
 */
public interface StrategieJeu {

    /**
     * Choisit la meilleure action à effectuer en fonction de la situation de jeu.
     * @param main La main du joueur.
     * @param mainCroupier La main du croupier.
     * @param peutDoubler Indique si l'action "Doubler" est autorisée.
     * @param peutSplitter Indique si l'action "Splitter" est autorisée.
     * @return L'action à effectuer (TIRER, RESTER, DOUBLER, SPLITTER).
     */
    Action choisirAction(Paquet main, Paquet mainCroupier, boolean peutDoubler, boolean peutSplitter);
    

}
