package cartes;

import javax.swing.*;
import java.awt.*;

import cartes.model.*;
import cartes.view.*;
import cartes.controller.*;

public class MainTest {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {

            // === MODÈLES ===
            Paquet pioche = PaquetFactory.creerJeu52(); // 52 cartes
            Paquet mainJoueur = new Paquet();           // main vide
            Paquet defausse = new Paquet();             // défausse vide

            // === VUES ===
            VuePaquetCache vuePioche = new VuePaquetCache(pioche);
            VuePaquetVisible vueMain = new VuePaquetVisible(mainJoueur);
            VuePaquetVisible vueDefausse = new VuePaquetVisible(defausse);

            // === SCROLL PANELS pour gérer beaucoup de cartes ===
            JScrollPane scrollMain = new JScrollPane(vueMain);
            JScrollPane scrollDefausse = new JScrollPane(vueDefausse);
            JScrollPane scrollPioche = new JScrollPane(vuePioche);

            scrollMain.setPreferredSize(new Dimension(600, 150));
            scrollDefausse.setPreferredSize(new Dimension(600, 150));
            scrollPioche.setPreferredSize(new Dimension(100, 150)); // pioche verticale possible

            // === CONTRÔLEURS ===
            new ControleurPiocheVersPaquet(vuePioche, mainJoueur);         // pioche -> main
            new ControleurChoixCarteVersPaquet(vueMain, defausse);         // main -> défausse
            new ControleurPiocheVersPaquet(vueDefausse, pioche);           // défausse -> pioche

            // === FENÊTRE PRINCIPALE ===
            JFrame f = new JFrame("Test paquets : pioche / main / défausse");
            f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            f.setLayout(new FlowLayout());

            f.add(new JLabel("Pioche"));
            f.add(scrollPioche);

            f.add(new JLabel("Main"));
            f.add(scrollMain);

            f.add(new JLabel("Défausse"));
            f.add(scrollDefausse);

            f.pack();
            f.setLocationRelativeTo(null); // centre
            f.setVisible(true);

            // === FENÊTRE SUPPLÉMENTAIRE : Pioche visible (copie du modèle) ===
            Paquet piocheCopie = new Paquet();
            for (Carte c : pioche.getCartes()) {
                piocheCopie.ajouter(new Carte(c.getHauteur(), c.getCouleur()));
            }
            VuePaquetVisible vuePiocheVisible = new VuePaquetVisible(piocheCopie);
            JScrollPane scrollPiocheVisible = new JScrollPane(vuePiocheVisible);
            scrollPiocheVisible.setPreferredSize(new Dimension(600, 150));

            JFrame f2 = new JFrame("Pioche visible");
            f2.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            f2.add(scrollPiocheVisible);
            f2.pack();
            f2.setLocation(f.getX() + f.getWidth() + 20, f.getY());
            f2.setVisible(true);
        });
    }
}
