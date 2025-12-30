package blackjack.model.joueur;

import blackjack.model.strategy.StrategieJeu;
import cartes.model.Paquet;
import blackjack.model.joueur.Action;

public class Croupier extends JoueurConcret {

    private StrategieJeu strategie;

    /**
     * Construit un Croupier en lui injectant sa stratégie de jeu.
     * @param strategie La stratégie que le croupier doit utiliser (Pattern Strategy).
     */
    public Croupier(StrategieJeu strategie) {
        super("Croupier", 1000, true);  // Solde initial de 1000 pour le croupier
        this.strategie = strategie;  // La dépendance (stratégie) est injectée.
    }

    /**
     * Demande à la stratégie de choisir la meilleure action à effectuer.
     * @param mainJoueur La main du joueur (pour des stratégies plus avancées).
     * @return L'action choisie par la stratégie.
     */
    public Action choisirAction(Paquet mainJoueur) {
        // Le croupier ne peut ni doubler ni splitter.
        return strategie.choisirAction(getMain(), mainJoueur, false, false);
    }

    /**
     * Permet de changer la stratégie du croupier.
     */
    public void setStrategie(StrategieJeu nouvelleStrategie) {
        this.strategie = nouvelleStrategie;
    }

    /**
     * Retourne la stratégie actuelle du croupier.
     */
    public StrategieJeu getStrategie() {
        return this.strategie;
    }

    // ------------ Gestion de la mise et du gain -------------

    /**
     * Le croupier place une mise. Il peut la définir en fonction de sa stratégie,
     * ou on peut la définir manuellement comme un montant fixe.
     * La mise du croupier sera égale à celle du joueur lorsqu'il tire.
     */
    @Override
    public void ajouterMise(int montant) {
        if (montant <= getSolde()) {
            setMise(montant);  // Applique la mise pour le croupier
            debiter(montant);  // Déduit la mise du solde du croupier
            System.out.println("Mise du croupier : " + getMise());
            System.out.println("Solde du croupier après mise : " + getSolde());
        } else {
            System.out.println("Fonds insuffisants pour la mise du croupier.");
        }
    }

    /**
     * Le croupier gagne un montant et l'ajoute à son solde.
     */
    public void ajouterGain(int montant) {
        setGain(montant);  // Définit le gain du croupier
        crediter(montant);  // Ajoute le gain au solde
    }

}
