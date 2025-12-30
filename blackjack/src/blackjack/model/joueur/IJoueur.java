package blackjack.model.joueur;

import cartes.model.Carte;
import cartes.model.Paquet;

/**
 * Interface représentant un joueur dans le jeu de Blackjack.
 * Définit les actions possibles et les informations accessibles.
 */
public interface IJoueur {

    /** @return Le nom du joueur. */
    String getNom();

    /** @return La main active du joueur. */
    Paquet getMain();

    /** Vide les mains pour une nouvelle manche. */
    void reinitialiserMain();

    /** Ajoute une carte à la main active. */
    void recevoirCarte(Carte c);

    // --- GESTION BANQUE ---

    /** @return Le solde du joueur. */
    int getSolde();

    void debiter(int montant);
    void crediter(int montant);

    /** @return La mise actuelle. */
    int getMise();
    void setMise(int a);
    void ajouterMise(int montant);

    /** @return Le gain de la dernière manche. */
    int getGain();
    void setGain(int a);
    void ajouterGain(int montant);

    // --- INFO JOUEUR ---

    boolean estIA();

    // --- GESTION DU SPLIT (MULTI-MAINS) ---

    /** @return Le nombre total de mains du joueur (1 par défaut, 2 si Split). */
    int getNombreDeMains();

    /** @return L'index de la main en cours de jeu (0 ou 1). */
    int getIndexMainCourante();
}