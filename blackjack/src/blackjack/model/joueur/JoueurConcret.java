package blackjack.model.joueur;

import cartes.model.Carte;
import cartes.model.Paquet;
import java.util.ArrayList;
import java.util.List;

public class JoueurConcret implements IJoueur {

    private String nom;
    private List<Paquet> mains;
    private int indexMainCourante; 
    private int solde;
    private boolean estIA;
    private int mise;
    private int gain;

    public JoueurConcret(String nom, int soldeInitial) {
        this(nom, soldeInitial, false);
    }

    public JoueurConcret(String nom, int soldeInitial, boolean estIA) {
        this.nom = nom;
        this.solde = soldeInitial;
        this.estIA = estIA;
        this.mains = new ArrayList<>();
        this.mains.add(new Paquet());
        this.indexMainCourante = 0;
        this.mise = 0;
        this.gain = 0;
    }

    public void splitter() {
        Paquet mainActuelle = getMain();
        if (mainActuelle.taille() < 2) return;

        Carte c1 = mainActuelle.getCartes().get(0);
        Carte c2 = mainActuelle.getCartes().get(1);

        Paquet nouvelleMain1 = new Paquet();
        nouvelleMain1.ajouter(c1);

        Paquet nouvelleMain2 = new Paquet();
        nouvelleMain2.ajouter(c2);

        this.mains.set(indexMainCourante, nouvelleMain1);
        this.mains.add(nouvelleMain2);
    }

    public void passerALaMainSuivante() {
        if (indexMainCourante < mains.size() - 1) indexMainCourante++;
    }
    
    public boolean aEncoreUneMainAJouer() {
        return indexMainCourante < mains.size() - 1;
    }

    @Override
    public Paquet getMain() { return mains.get(indexMainCourante); }
    
    public List<Paquet> getToutesLesMains() { return mains; }

    @Override
    public int getNombreDeMains() { return mains.size(); }

    @Override
    public int getIndexMainCourante() { return indexMainCourante; }

    @Override
    public void reinitialiserMain() {
        this.mains.clear();
        this.mains.add(new Paquet());
        this.indexMainCourante = 0;
    }

    @Override
    public void recevoirCarte(Carte c) { if (c != null) getMain().ajouter(c); }

    @Override
    public void ajouterMise(int montant) {
        if (montant <= solde) {
            this.mise += montant; 
            this.solde -= montant;
        }
    }

    @Override public void setMise(int a) { this.mise = a; }
    @Override public int getMise() { return mise; }
    @Override public String getNom() { return nom; }
    @Override public int getSolde() { return solde; }
    @Override public void debiter(int montant) { this.solde -= montant; }
    @Override public void crediter(int montant) { this.solde += montant; }
    @Override public boolean estIA() { return estIA; }
    public void setIA(boolean estIA) { this.estIA = estIA; }
    @Override public int getGain() { return gain; }
    @Override public void setGain(int a) { this.gain = a; }
    @Override public void ajouterGain(int montant) {
        this.gain = montant;
        this.solde += montant;
    }
}