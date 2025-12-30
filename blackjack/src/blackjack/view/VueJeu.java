package blackjack.view;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.util.ArrayList;
import java.util.List;
import blackjack.model.jeu.BlackjackUtils;
import blackjack.model.jeu.JeuBlackjack;
import blackjack.model.jeu.JeuBlackjack.ResultatPartie;
import blackjack.model.joueur.JoueurConcret;
import blackjack.model.jeu.ReglesBlackjack;
import cartes.model.Carte;
import cartes.model.Paquet;
import cartes.model.observer.EcouteurModele;
import cartes.view.VuePaquetVisible;

public class VueJeu extends JPanel implements EcouteurModele {

    private final JeuBlackjack modele;
    private final VueMainCroupier vueMainCroupier;
    private JPanel panelJoueurs;
    private List<VueJoueur> vuesJoueurs;
    private Image imageFond;
    private JButton boutonTirer, boutonRester, boutonDoubler, boutonSplit;
    private JLabel labelMise, labelScoreCroupier, labelResultatGlobal;
    private final Border BORDURE_INACTIVE = BorderFactory.createEmptyBorder(2, 2, 2, 2);

    public VueJeu(JeuBlackjack modele) {
        this.modele = modele;
        this.modele.ajoutEcouteur(this);
        try {
            imageFond = ImageIO.read(new File("src/blackjack/images/tapis_casino.png"));
        } catch (IOException e) {
            setBackground(new Color(39, 119, 20));
        }
        setLayout(new GridBagLayout());

        vueMainCroupier = new VueMainCroupier(modele);
        vueMainCroupier.setOpaque(false);
        vueMainCroupier.setBorder(null);

        panelJoueurs = new JPanel(new GridLayout(1, 0, 10, 0));
        panelJoueurs.setOpaque(false);

        vuesJoueurs = new ArrayList<>();
        for (JoueurConcret joueur : modele.getJoueurs()) {
            VueJoueur vueJoueur = new VueJoueur(joueur);
            vuesJoueurs.add(vueJoueur);
            panelJoueurs.add(vueJoueur);
        }

        boutonTirer = creerBoutonCarre("TIRER");
        boutonRester = creerBoutonCarre("RESTER");
        boutonDoubler = creerBoutonAction("x2", new Color(255, 215, 0));
        boutonSplit = creerBoutonAction("SPLIT", new Color(142, 68, 173));

        labelMise = new JLabel("0 $", JLabel.CENTER);
        labelMise.setFont(new Font("Segoe UI", Font.BOLD, 20));
        labelMise.setForeground(new Color(255, 215, 0));
        labelMise.setBorder(BorderFactory.createLineBorder(new Color(255, 215, 0), 2, true));
        labelMise.setPreferredSize(new Dimension(80, 80));

        labelScoreCroupier = creerLabelScore("Croupier: 0");
        labelResultatGlobal = new JLabel("", JLabel.CENTER);
        labelResultatGlobal.setFont(new Font("Segoe UI", Font.BOLD, 30));
        labelResultatGlobal.setForeground(Color.WHITE);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 5; gbc.weighty = 0.2; gbc.anchor = GridBagConstraints.NORTH;
        gbc.insets = new Insets(20, 0, 0, 0);
        add(vueMainCroupier, gbc);

        gbc.gridy = 1; gbc.weighty = 0.0; gbc.insets = new Insets(0, 0, 10, 0);
        add(labelScoreCroupier, gbc);

        gbc.gridy = 2; gbc.gridwidth = 1; gbc.weighty = 0.0;
        gbc.gridx = 0; gbc.anchor = GridBagConstraints.EAST; gbc.insets = new Insets(0, 0, 0, 10);
        add(boutonTirer, gbc);
        gbc.gridx = 1; gbc.anchor = GridBagConstraints.CENTER; gbc.insets = new Insets(0, 5, 0, 5);
        add(boutonSplit, gbc);
        JPanel panelMiseGroup = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 0));
        panelMiseGroup.setOpaque(false);
        panelMiseGroup.add(boutonDoubler);
        panelMiseGroup.add(labelMise);
        gbc.gridx = 2;
        add(panelMiseGroup, gbc);
        gbc.gridx = 3; gbc.anchor = GridBagConstraints.WEST; gbc.insets = new Insets(0, 10, 0, 0);
        add(boutonRester, gbc);

        gbc.gridx = 0; gbc.gridy = 3; gbc.gridwidth = 5; gbc.anchor = GridBagConstraints.CENTER; gbc.insets = new Insets(10, 0, 10, 0);
        add(labelResultatGlobal, gbc);

        gbc.gridx = 0; gbc.gridy = 4; gbc.gridwidth = 5; gbc.weighty = 0.5; gbc.fill = GridBagConstraints.BOTH; gbc.insets = new Insets(0, 20, 20, 20);
        add(panelJoueurs, gbc);

        mettreAJourLabels();
    }

    private JButton creerBoutonCarre(String texte) {
        return creerBoutonAction(texte, new Color(255, 255, 255, 200));
    }

    private JButton creerBoutonAction(String texte, Color bg) {
        JButton btn = new JButton(texte);
        btn.setPreferredSize(new Dimension(100, 60));
        btn.setBackground(bg);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btn.setFocusPainted(false);
        return btn;
    }

    private JLabel creerLabelScore(String texte) {
        JLabel lbl = new JLabel(texte);
        lbl.setFont(new Font("SansSerif", Font.BOLD, 16));
        lbl.setForeground(Color.WHITE);
        return lbl;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (imageFond != null) {
            g.drawImage(imageFond, 0, 0, getWidth(), getHeight(), this);
        }
    }

    @Override
    public void modeleMisAJour(Object source) {
        if (modele.isPartieTerminee()) {
            vueMainCroupier.revelerCartes();
        }
        mettreAJourLabels(modele.isPartieTerminee());
    }

    public void mettreAJourLabels() {
        mettreAJourLabels(modele.isPartieTerminee());
    }

    public void mettreAJourLabels(boolean finDePartie) {
        JoueurConcret joueurCourant = modele.getJoueurCourant();

        for (VueJoueur vueJoueur : vuesJoueurs) {
            vueJoueur.mettreAJour(finDePartie, joueurCourant);
        }

        if (finDePartie) {
            labelScoreCroupier.setText("Score Croupier: " + modele.getScoreCroupier());
        } else {
            labelScoreCroupier.setText("Score Croupier: ?");
        }
        
        
        labelMise.setText(modele.getJoueur().getMise() + " $");

        ResultatPartie r = modele.getResultat();
        if (r == ResultatPartie.EN_COURS) {
            labelResultatGlobal.setText("");
            boolean tourHumain = joueurCourant != null && !joueurCourant.estIA();
            boutonTirer.setEnabled(tourHumain);
            boutonRester.setEnabled(tourHumain);

            if (tourHumain) {
                boutonDoubler.setEnabled(joueurCourant.getSolde() >= joueurCourant.getMise() && joueurCourant.getMain().taille() == 2);
                boolean peutSplitter = false;
                List<Paquet> mains = joueurCourant.getToutesLesMains();
                if (mains.size() == 1 && mains.get(0).taille() == 2) {
                    Carte c1 = mains.get(0).getCartes().get(0);
                    Carte c2 = mains.get(0).getCartes().get(1);
                    if (BlackjackUtils.valeurBlackjack(c1) == BlackjackUtils.valeurBlackjack(c2) && joueurCourant.getSolde() >= joueurCourant.getMise()) {
                        peutSplitter = true;
                    }
                }
                boutonSplit.setEnabled(peutSplitter);
            } else {
                boutonDoubler.setEnabled(false);
                boutonSplit.setEnabled(false);
            }
        } else {
            boutonTirer.setEnabled(false);
            boutonRester.setEnabled(false);
            boutonDoubler.setEnabled(false);
            boutonSplit.setEnabled(false);
            
            
            labelResultatGlobal.setText(r.toString().replace("_", " "));
        }
        repaint();
    }

    private TitledBorder createTitledBorder(String titre, Color couleur) {
        TitledBorder tb = BorderFactory.createTitledBorder(BorderFactory.createLineBorder(couleur, 2), titre);
        tb.setTitleColor(couleur);
        tb.setTitleFont(new Font("Segoe UI", Font.BOLD, 12));
        tb.setTitleJustification(TitledBorder.CENTER);
        return tb;
    }

    
    public JButton getBoutonTirer() { return boutonTirer; }
    public JButton getBoutonRester() { return boutonRester; }
    public JButton getBoutonDoubler() { return boutonDoubler; }
    public JButton getBoutonSplit() { return boutonSplit; }
    public VueMainCroupier getVueMainCroupier() { return vueMainCroupier; }
    public JeuBlackjack getModel() { return modele; }

  
    private class VueJoueur extends JPanel {
        private JoueurConcret joueur;
        private JPanel mainsPanel;
        private JLabel labelNom;
        private JLabel labelScore;
        private JLabel labelSolde; // NEW

        VueJoueur(JoueurConcret joueur) {
            this.joueur = joueur;
            setOpaque(false);
            setLayout(new BorderLayout(5, 5));

            JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
            topPanel.setOpaque(false);

            labelNom = new JLabel(joueur.getNom(), JLabel.CENTER);
            labelNom.setForeground(Color.WHITE);
            labelNom.setFont(new Font("Segoe UI", Font.BOLD, 16));
            topPanel.add(labelNom);

            labelSolde = new JLabel("Solde: " + joueur.getSolde() + " $", JLabel.CENTER); // NEW
            labelSolde.setForeground(Color.YELLOW);
            labelSolde.setFont(new Font("Segoe UI", Font.BOLD, 14));
            topPanel.add(labelSolde);
            
            mainsPanel = new JPanel(new GridLayout(1, 0, 10, 0));
            mainsPanel.setOpaque(false);

            labelScore = new JLabel("Score: 0", JLabel.CENTER);
            labelScore.setForeground(Color.WHITE);
            labelScore.setFont(new Font("SansSerif", Font.BOLD, 14));
            
            add(topPanel, BorderLayout.NORTH); // Modified
            add(mainsPanel, BorderLayout.CENTER);
            add(labelScore, BorderLayout.SOUTH);
            
            setBorder(BORDURE_INACTIVE);
        }

        void mettreAJour(boolean finDePartie, JoueurConcret joueurCourant) {
            mainsPanel.removeAll();
            List<Paquet> mains = joueur.getToutesLesMains();
            String scores = "Score: ";

            labelSolde.setText("Solde: " + joueur.getSolde() + " $");

            for (int i = 0; i < mains.size(); i++) {
                Paquet main = mains.get(i);
                VuePaquetVisible vueMain = new VuePaquetVisible(main);
                vueMain.setOpaque(false);
                
                JPanel handPanel = new JPanel(new BorderLayout());
                handPanel.setOpaque(false);
                handPanel.add(vueMain, BorderLayout.CENTER);

                if (!finDePartie && joueur == joueurCourant && i == joueur.getIndexMainCourante()) {
                    handPanel.setBorder(BorderFactory.createLineBorder(Color.YELLOW, 2));
                }

                if (finDePartie) {
                    int scoreJoueur = BlackjackUtils.calculerScore(main);
                    int scoreCroupier = modele.getScoreCroupier();
                    boolean joueurBuste = ReglesBlackjack.estBuste(scoreJoueur);
                    boolean croupierBuste = ReglesBlackjack.estBuste(scoreCroupier);
                    String resultat;
                    Color couleur;

                    if (joueurBuste) {
                        resultat = "Perdu (Bust)";
                        couleur = Color.RED;
                    } else if (croupierBuste || scoreJoueur > scoreCroupier) {
                        resultat = "Gagné !";
                        couleur = Color.GREEN;
                    } else if (scoreJoueur == scoreCroupier) {
                        resultat = "Égalité";
                        couleur = Color.WHITE;
                    } else {
                        resultat = "Perdu";
                        couleur = Color.RED;
                    }
                    JLabel resultatLabel = new JLabel(resultat, JLabel.CENTER);
                    resultatLabel.setForeground(couleur);
                    resultatLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
                    handPanel.add(resultatLabel, BorderLayout.SOUTH);
                }

                mainsPanel.add(handPanel);
                scores += BlackjackUtils.calculerScore(main);
                if (i < mains.size() - 1) {
                    scores += " / ";
                }
            }
            labelScore.setText(scores);

            if (finDePartie) {
                setBorder(createTitledBorder("Partie Terminée", Color.GRAY));
            } else {
                if (joueur == joueurCourant) {
                    setBorder(createTitledBorder("À vous de jouer !", Color.YELLOW));
                } else {
                    setBorder(BORDURE_INACTIVE);
                }
            }
            mainsPanel.revalidate();
            mainsPanel.repaint();
        }
    }
}