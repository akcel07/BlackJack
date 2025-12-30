# 🃏 Blackjack - Simulation en Java

Une implémentation complète et modulaire du célèbre jeu de casino, développée dans le cadre d'un projet de conception logicielle.

## 🎯 Fonctionnalités

*   **Moteur de jeu complet** : Respect des règles officielles du Blackjack.
*   **Architecture modulaire** : Séparation claire entre une bibliothèque générique de gestion de cartes et le moteur spécifique au Blackjack.
*   **Interface graphique** : Interface utilisateur intuitive développée avec Java Swing.
*   **Système d'IA** : Joueurs et croupier contrôlés par l'ordinateur avec différentes stratégies.
*   **Design Patterns** : Utilisation de patrons de conception (Factory, Strategy, Observer, Proxy, Chain of Responsibility) pour une codebase robuste et extensible.
*   **Build automatisé** : Compilation et exécution simplifiées via Apache Ant.

## 🏗️ Architecture

Le projet est structuré en deux packages principaux :

1.  **`cartes`** : Bibliothèque réutilisable pour la manipulation d'un jeu de cartes standard (classe `Carte`, `Paquet`, `Main`).
2.  **`blackjack`** : Implémente la logique métier du jeu (classe `Joueur`, `Croupier`, `JeuBlackjack`, stratégies, gestion des mises).

## ▶️ Installation et Exécution

**Prérequis** : Java JDK 11+ et Apache Ant installés.

1.  **Cloner le dépôt**
    ```bash
    git clone https://github.com/votre-utilisateur/blackjack-java.git
    cd blackjack-java
    ```

2.  **Compiler le projet**
    ```bash
    ant compile
    ```

3.  **Lancer l'application**
    ```bash
    ant run
    ```



## 📁 Structure du projet

.
├── build.xml # Fichier de configuration Apache Ant
├── src/
│ ├── cartes/ # Module bibliothèque de cartes
│ └── blackjack/ # Module moteur du jeu Blackjack
├── lib/ # Dépendances externes (si existantes)
└── README.md
