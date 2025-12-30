package blackjack.controller;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import blackjack.model.jeu.JeuBlackjack;
import blackjack.view.VueAccueil;
import blackjack.view.VueJeu;
import blackjack.view.VueMise;
import blackjack.model.verif.Verificateur;
import blackjack.model.verif.VerificateurSoldeSuffisant;
import blackjack.model.verif.VerifMiseMin;
import cartes.model.observer.EcouteurModele;

/**
 * Contrôleur principal :
 * - Gère la navigation (CardLayout)
 * - Gère le cycle de vie du jeu (Nouvelle partie, Timer de fin)
 */
public class ControleurJeu implements EcouteurModele { 

    private final JeuBlackjack modele;
    private final VueJeu vueJeu;
    private final VueAccueil vueAccueil;
    private final VueMise vueMise;
    
    private final JFrame frame;
    private final JPanel panelPrincipal;
    private final CardLayout cardLayout;

    // On crée la chaîne de validation une seule fois pour la réutiliser.
    private final Verificateur chaineValidation = new VerificateurSoldeSuffisant(
        new VerifMiseMin(1, null) // Mise minimum de 1, puis fin de la chaîne.
    );

    // On crée les stratégies une seule fois pour les réutiliser.
    private final blackjack.model.strategy.StrategieJeu strategieAgressive = new blackjack.model.strategy.StrategieAgressive();
    private final blackjack.model.strategy.StrategieJeu strategiePrudente = new blackjack.model.strategy.StrategiePrudente();
    private final blackjack.model.strategy.StrategieJeu strategieExpert = new blackjack.model.strategy.StrategieExpert();
    private final blackjack.model.strategy.StrategieJeu strategieBasique = new blackjack.model.strategy.StrategieJoueurBasique();


    // Pour éviter de lancer le timer plusieurs fois si le modèle notifie trop
    private boolean timerDejaLance = false; 

    public ControleurJeu() {
        // 1. Init Modèle
        this.modele = new JeuBlackjack();
        // Le contrôleur s'abonne aux changements du modèle (pour le Timer)
        this.modele.ajoutEcouteur(this); 

        // 2. Init Vues
        this.vueJeu = new VueJeu(modele);
        this.vueAccueil = new VueAccueil();
        this.vueMise = new VueMise();

        // 3. Fenêtre principale
        frame = new JFrame("Blackjack Casino");

        // 4. Contrôleur Actions (Branche les boutons du tapis)
        new ControleurAction(vueJeu);

        // 5. Navigation (Système de piles de cartes)
        cardLayout = new CardLayout();
        panelPrincipal = new JPanel(cardLayout);

        panelPrincipal.add(vueAccueil, "ACCUEIL");
        panelPrincipal.add(vueMise, "MISE");
        panelPrincipal.add(vueJeu, "JEU");

        // --- NAVIGATION : ACCUEIL -> MISE ---
        vueAccueil.getBoutonJouer().addActionListener(e -> {
            afficherVueMise(); 
        });

        // --- NAVIGATION : MISE -> JEU (DISTRIBUER) ---
        vueMise.getBoutonDistribuer().addActionListener(e -> {
            try {
                String texteMise = vueMise.getChampMise().getText();
                int montant = Integer.parseInt(texteMise);
                
                if (!chaineValidation.verifier(montant, modele.getJoueur())) {
                    JOptionPane.showMessageDialog(frame, "Mise invalide ! (Solde insuffisant ou mise < 1€)");
                    return;
                }

                // --- RÉCUPÉRATION DE LA STRATÉGIE DE L'IA DEPUIS LA VUE ---
                String choix = (String) vueMise.getComboStrategie().getSelectedItem();
                
                blackjack.model.strategy.StrategieJeu strategieChoisie;
                switch (choix) {
                    case "Agressive":
                        strategieChoisie = strategieAgressive;
                        break;
                    case "Prudente":
                        strategieChoisie = strategiePrudente;
                        break;
                    case "Experte":
                        strategieChoisie = strategieExpert;
                        break;
                    default:
                        strategieChoisie = strategieBasique;
                        break;
                }

                // On cherche le joueur IA dans le modèle pour lui appliquer la stratégie
                for (blackjack.model.joueur.JoueurConcret joueur : modele.getJoueurs()) {
                    if (joueur instanceof blackjack.model.joueur.JoueurIA) {
                        ((blackjack.model.joueur.JoueurIA) joueur).setStrategie(strategieChoisie);
                    }
                }


                // 1. D'ABORD : Nettoyage de la table
                modele.nouvellePartie(); 
                
                // 2. ENSUITE : Application de la mise
                modele.appliquerMise(montant);

                // 3. IMPORTANT : On force la carte du croupier à être cachée
                // (Même si le proxy est resté ouvert de la partie d'avant)
                vueJeu.getVueMainCroupier().cacherPremiereCarte();
                
                // Reset du flag pour le prochain timer de fin de partie
                timerDejaLance = false;

                // Affichage du tapis de jeu
                cardLayout.show(panelPrincipal, "JEU");
                vueJeu.mettreAJourLabels(); 

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(frame, "Mise invalide !");
            }
        });

        // --- FINITION FENETRE ---
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setContentPane(panelPrincipal);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH); // Met en plein écran
        frame.setLocationRelativeTo(null); // Centrer à l'écran
        frame.setVisible(true);
        
        // Démarrage sur l'accueil
        cardLayout.show(panelPrincipal, "ACCUEIL");
    }

    /**
     * Méthode appelée automatiquement quand le modèle change.
     * Sert principalement à détecter la fin de partie pour lancer le Timer.
     */
    @Override
    public void modeleMisAJour(Object source) {
        
        // Si la partie est terminée ET qu'on n'a pas encore lancé le chrono
        if (modele.isPartieTerminee() && !timerDejaLance) {
            
            timerDejaLance = true; // On verrouille
            
            // On crée un Timer qui se déclenchera dans 3000 ms (3 secondes)
            Timer timerFinPartie = new Timer(10000, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    // Retour automatique à l'écran de mise
                    afficherVueMise();
                }
            });
            
            timerFinPartie.setRepeats(false); // Une seule fois
            timerFinPartie.start(); // Top chrono
        }
    }

    /**
     * Helper pour afficher la vue Mise et mettre à jour les infos (Solde, Cartes).
     */
    private void afficherVueMise() {
        // On force la lecture du solde ACTUEL du modèle
        int soldeActuel = modele.getJoueur().getSolde();
        
        vueMise.getLabelSolde().setText("Solde : " + soldeActuel + " €");
        
        // Affiche le nombre de cartes restantes (+4 pour inclure la main précédente si voulu)
        vueMise.getLabelNbCartes().setText("Cartes : " + (modele.getPioche().taille() + 4));
        
        cardLayout.show(panelPrincipal, "MISE");
    }
}