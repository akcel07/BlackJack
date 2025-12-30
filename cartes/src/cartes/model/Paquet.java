package cartes.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import cartes.model.observer.*;


public class Paquet extends AbstractModeleEcoutable {

    private List<Carte> cartes = new ArrayList<>();
    private Random random = new Random();

    public void ajouter(Carte c) {
        cartes.add(c);
        fireChangement();
    }

    public void ajouter(int position, Carte c) {
        cartes.add(position, c);
        fireChangement();
    }

    public Carte retirerPremiere() {
        if (cartes.isEmpty()) return null;
        Carte c = cartes.remove(0);
        fireChangement();
        return c;
    }

    public Carte retirer(int index) {
        if (index < 0 || index >= cartes.size()) return null;
        Carte c = cartes.remove(index);
        fireChangement();
        return c;
    }

    public void melanger() {
        Collections.shuffle(cartes);
        fireChangement();
    }

    public void couper() {
        if (cartes.size() < 7) return; // pas possible
        int pos = random.nextInt(cartes.size() - 6) + 3; // exclure 3 premières et 3 dernières

        List<Carte> haut = cartes.subList(0, pos);
        List<Carte> bas = cartes.subList(pos, cartes.size());

        List<Carte> nouveau = new ArrayList<>(bas);
        nouveau.addAll(haut);

        cartes = nouveau;
        fireChangement();
    }

    public int taille() {
        return cartes.size();
    }

    public Carte get(int i) {
        return cartes.get(i);
    }

    public List<Carte> getCartes() {
        return new ArrayList<>(cartes); // copie défensive
    }
}
