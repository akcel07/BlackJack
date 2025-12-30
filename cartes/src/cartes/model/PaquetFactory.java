package cartes.model;

public class PaquetFactory {

    private static final String[] COULEURS = {
        "trèfle", "carreau", "cœur", "pique"
    };

    private static final String[] HAUTEURS_32 = {
        "7", "8", "9", "10", "valet", "dame", "roi", "as"
    };

    private static final String[] HAUTEURS_52 = {
        "2", "3", "4", "5", "6", "7", "8", "9", "10",
        "valet", "dame", "roi", "as"
    };

    public static Paquet creerJeu32() {
        Paquet p = new Paquet();
        for (String c : COULEURS) {
            for (String h : HAUTEURS_32) {
                p.ajouter(new Carte(h, c));
            }
        }
        p.melanger();
        return p;
    }

    public static Paquet creerJeu52() {
        Paquet p = new Paquet();
        for (String c : COULEURS) {
            for (String h : HAUTEURS_52) {
                p.ajouter(new Carte(h, c));
            }
        }
        p.melanger();
        return p;
    }
}
