package blackjack.model.jeu;

import cartes.model.Carte;
import cartes.model.Paquet;
import cartes.model.PaquetFactory;
import cartes.model.observer.*;
import java.util.ArrayList;
import java.util.List;
import blackjack.model.joueur.Croupier;
import blackjack.model.joueur.IJoueur;
import blackjack.model.joueur.JoueurIA;
import blackjack.model.joueur.JoueurConcret;
import blackjack.model.joueur.Action;
import blackjack.model.strategy.*;;

public class JeuBlackjack extends AbstractModeleEcoutable {

    public enum ResultatPartie { EN_COURS, JOUEUR_GAGNE, CROUPIER_GAGNE, EGALITE }

    private List<JoueurConcret> joueurs;
    private Paquet pioche;
    private Croupier croupier;
    private boolean partieTerminee;
    private int sommeMises;
    private int joueurCourantIndex;

    public JeuBlackjack() {
        joueurs = new ArrayList<>();
        
        // 1. Créer le joueur humain et le joueur IA
        JoueurConcret joueurHumain = new JoueurConcret("Vous", 1000);
        StrategieJeu strategieIA = new StrategieJoueurBasique(); // Utilise la stratégie qu'on a créée
        JoueurIA joueurIA = new JoueurIA("Joueur IA", 1000, strategieIA);

        joueurs.add(joueurHumain);
        joueurs.add(joueurIA);

        // 2. Le croupier utilise sa stratégie classique
        this.croupier = new Croupier(new StrategieCroupierClassique());
        this.pioche = PaquetFactory.creerJeu52(); // Créé une seule fois
        nouvellePartie();
    }

    public void nouvellePartie() {
        if (this.pioche.taille() < 20) this.pioche = PaquetFactory.creerJeu52(); // Augmenter la marge pour plus de joueurs
        
        for (JoueurConcret j : joueurs) {
            j.reinitialiserMain();
            j.setMise(0);
            j.setGain(0);
        }
        croupier.reinitialiserMain();
        croupier.setMise(0);
        croupier.setGain(0);
        sommeMises = 0;
        joueurCourantIndex = 0; // Le tour commence avec le premier joueur (l'humain)
        partieTerminee = false;
        distribuerCartesInitiales();
        fireChangement();
    }

    private void distribuerCartesInitiales() {
        // 1. Première carte pour les joueurs
        for (JoueurConcret j : joueurs) {
            tirerPour(j);
        }
        // 2. Première carte pour le croupier (sa carte cachée)
        tirerPour(croupier);
        // 3. Deuxième carte pour les joueurs
        for (JoueurConcret j : joueurs) {
            tirerPour(j);
        }
        // 4. Deuxième carte pour le croupier (sa carte visible)
        tirerPour(croupier);
    }

    private void tirerPour(IJoueur j) {
        if (pioche.taille() > 0) j.recevoirCarte(pioche.retirerPremiere());
    }

    public void joueurTire() {
        if (partieTerminee || getJoueurCourant().estIA()) return; // L'IA ne tire pas via cette méthode
        JoueurConcret joueur = getJoueurCourant();
        tirerPour(joueur);
        if (BlackjackUtils.estBuste(joueur.getMain())) {
            if (joueur.aEncoreUneMainAJouer()) {
                joueur.passerALaMainSuivante();
                tirerPour(joueur); // Each new hand gets a second card
                fireChangement();
            } else {
                passerAuJoueurSuivant();
            }
        } else {
            fireChangement();
        }
    }

    public void joueurReste() {
        if (partieTerminee || getJoueurCourant().estIA()) return;
        JoueurConcret joueur = getJoueurCourant();
        if (joueur.aEncoreUneMainAJouer()) {
            joueur.passerALaMainSuivante();
            tirerPour(joueur); // Each new hand gets a second card
            fireChangement();
        } else {
            passerAuJoueurSuivant();
        }
    }

    public void joueurDouble() {
        if (partieTerminee || getJoueurCourant().estIA()) return;
        JoueurConcret joueur = getJoueurCourant();
        int miseActuelle = joueur.getMise();
        if (joueur.getSolde() >= miseActuelle) {
            joueur.ajouterMise(miseActuelle);
            tirerPour(joueur);
            passerAuJoueurSuivant();
        }
    }

    private void passerAuJoueurSuivant() {
        if (joueurCourantIndex < joueurs.size() - 1) {
            joueurCourantIndex++;
            // Si le nouveau joueur est une IA, on la fait jouer automatiquement
            if (getJoueurCourant().estIA()) {
                jouerTourIA();
            }
        } else {
            // Tous les joueurs ont joué, c'est au tour du croupier
            joueurCourantIndex = -1; // Plus aucun joueur n'est actif
            croupierJoue();
            partieTerminee = true;
            terminerPartie();
        }
        fireChangement();
    }

    public void joueurSplit() {
        if (partieTerminee) return;
        JoueurConcret joueur = getJoueurCourant();
        if (joueur.getMain().taille() != 2) return;

        Carte c1 = joueur.getMain().getCartes().get(0);
        Carte c2 = joueur.getMain().getCartes().get(1);
        int v1 = BlackjackUtils.valeurBlackjack(c1);
        int v2 = BlackjackUtils.valeurBlackjack(c2);

        if (v1 != v2) return;

        int miseDeBase = joueur.getMise(); 
        if (joueur.getSolde() >= miseDeBase) {
            joueur.ajouterMise(miseDeBase);
            joueur.splitter();
            tirerPour(joueur);
            fireChangement();
        }
    }

    /**
     * Fait jouer un tour complet pour un joueur IA de manière automatique.
     * La méthode s'exécute en boucle jusqu'à ce que l'IA décide de rester ou qu'elle buste.
     */
    public void jouerTourIA() {
        if (partieTerminee || !(getJoueurCourant() instanceof JoueurIA)) return;

        JoueurIA joueurIA = (JoueurIA) getJoueurCourant();
        boolean tourIAFini = false;

        while (!tourIAFini) {
            boolean peutDoubler = joueurIA.getMain().taille() == 2 && joueurIA.getSolde() >= joueurIA.getMise();
            Action action = joueurIA.choisirAction(croupier.getMain(), peutDoubler, false); // Split non géré pour l'instant

            switch (action) {
                case TIRER:
                    System.out.println(joueurIA.getNom() + " décide de TIRER.");
                    tirerPour(joueurIA);
                    if (BlackjackUtils.estBuste(joueurIA.getMain())) tourIAFini = true;
                    break;
                case DOUBLER:
                    System.out.println(joueurIA.getNom() + " décide de DOUBLER.");
                    joueurIA.ajouterMise(joueurIA.getMise());
                    tirerPour(joueurIA);
                    tourIAFini = true; break;
                case RESTER:
                default:
                    System.out.println(joueurIA.getNom() + " décide de RESTER.");
                    tourIAFini = true; break;
            }
        }
        passerAuJoueurSuivant();
    }

    private void croupierJoue() {
        // Le croupier joue en utilisant sa stratégie.
    // La main du joueur n'est pas nécessaire pour la stratégie classique du croupier.
    while (croupier.choisirAction(null) == Action.TIRER) {
        tirerPour(croupier);
    }
    }

    public void terminerPartie() {
        int scoreCroupier = getScoreCroupier();
        boolean croupierBuste = ReglesBlackjack.estBuste(scoreCroupier);

        for (JoueurConcret joueur : joueurs) {
            // La logique de gain doit être revue pour gérer les mains splittées
            List<Paquet> mains = joueur.getToutesLesMains();
            int miseParMain = (mains.isEmpty() || joueur.getMise() == 0) ? 0 : joueur.getMise() / mains.size();
            int gainTotalSession = 0;

            for (Paquet mainJoueur : mains) {
                int scoreJoueur = BlackjackUtils.calculerScore(mainJoueur);
                boolean joueurBuste = ReglesBlackjack.estBuste(scoreJoueur);
                
                if (!joueurBuste) {
                    if (croupierBuste || scoreJoueur > scoreCroupier) gainTotalSession += (miseParMain * 2);
                    else if (scoreJoueur == scoreCroupier) gainTotalSession += miseParMain;
                }
            }

            if (gainTotalSession > 0) joueur.ajouterGain(gainTotalSession);
            else joueur.setGain(0);
        }
        fireChangement();
    }

    public ResultatPartie getResultat() {
        if (!partieTerminee) return ResultatPartie.EN_COURS;

        // On calcule le résultat pour le joueur humain (le premier de la liste)
        JoueurConcret joueurHumain = getJoueur();
        int scoreJoueur = BlackjackUtils.calculerScore(joueurHumain.getMain());
        int scoreCroupier = getScoreCroupier();

        if (ReglesBlackjack.estBuste(scoreJoueur)) return ResultatPartie.CROUPIER_GAGNE;
        if (ReglesBlackjack.estBuste(scoreCroupier)) return ResultatPartie.JOUEUR_GAGNE;
        if (scoreJoueur > scoreCroupier) return ResultatPartie.JOUEUR_GAGNE;
        if (scoreCroupier > scoreJoueur) return ResultatPartie.CROUPIER_GAGNE;
        return ResultatPartie.EGALITE;
    }

    /**
     * Retourne le joueur humain (le premier de la liste) pour la compatibilité avec l'interface graphique.
     * @return Le joueur humain.
     */
    public JoueurConcret getJoueur() {
        return joueurs.get(0);
    }

    public JoueurConcret getJoueurCourant() {
        if (joueurCourantIndex >= 0 && joueurCourantIndex < joueurs.size()) {
            return joueurs.get(joueurCourantIndex);
        }
        return null; // Aucun joueur n'est en train de jouer
    }

    public List<JoueurConcret> getJoueurs() { return joueurs; }
    public int getScoreCroupier() { return BlackjackUtils.calculerScore(croupier.getMain()); }
    public Paquet getPioche() { return pioche; }
    public Croupier getCroupier() { return croupier; }
    public boolean isTourDuJoueur() { return joueurCourantIndex != -1; }
    public boolean isPartieTerminee() { return partieTerminee; }
    
    public void appliquerMise(int mise) {
        // Applique la mise pour tous les joueurs pour simplifier
        for (JoueurConcret joueur : joueurs) {
            if (mise <= joueur.getSolde()) {
                joueur.ajouterMise(mise);
            }
        }
        fireChangement();
    }
}