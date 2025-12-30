package blackjack.model.joueur;

/**
 * Représente les actions possibles pour un joueur de Blackjack.
 */
public enum Action {
    TIRER,    // Hit
    RESTER,   // Stand
    DOUBLER,  // Double Down
    SPLITTER, // Split
    AUCUNE    // Aucune action possible (ex: partie terminée)
}