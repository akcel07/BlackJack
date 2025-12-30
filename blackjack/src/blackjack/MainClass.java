package blackjack;

import javax.swing.SwingUtilities;
import blackjack.controller.ControleurJeu;

/**
 * Point d'entrée de l'application Blackjack.
 */
public class MainClass {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ControleurJeu());
    }
}
