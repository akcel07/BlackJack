package blackjack.model.proxy;

import java.util.ArrayList;
import java.util.List;
import blackjack.model.jeu.BlackjackUtils;
import blackjack.model.joueur.Croupier;
import cartes.model.Carte;

public class MainCroupierProxy {
    private final Croupier croupierReel;
    private boolean premiereCarteVisible = false;

    public MainCroupierProxy(Croupier croupierReel) { this.croupierReel = croupierReel; }

    public List<Carte> getCartesVisibles() {
        List<Carte> toutes = croupierReel.getMain().getCartes();
        List<Carte> visibles = new ArrayList<>();

        if (toutes.isEmpty()) {
            premiereCarteVisible = false; // Auto-reset
            return visibles;
        }

        if (premiereCarteVisible) {
            visibles.addAll(toutes);
        } else {
            if (toutes.size() > 1) visibles.addAll(toutes.subList(1, toutes.size()));
        }
        return visibles;
    }

    public void revelerCartes() { premiereCarteVisible = true; }
    public void cacherPremiereCarte() { premiereCarteVisible = false; }
    public Croupier getCroupierReel() { return croupierReel; }
    
    public int getScoreVisible() {
        int total = 0;
        for (Carte c : getCartesVisibles()) total += BlackjackUtils.valeurBlackjack(c);
        return total;
    }
}