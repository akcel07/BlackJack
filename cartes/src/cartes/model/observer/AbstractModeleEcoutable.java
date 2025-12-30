package cartes.model.observer;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractModeleEcoutable implements ModeleEcoutable {

    private List<EcouteurModele> ecouteurs = new ArrayList<>();

    @Override
    public void ajoutEcouteur(EcouteurModele e) {
        ecouteurs.add(e);
    }

    @Override
    public void retraitEcouteur(EcouteurModele e) {
        ecouteurs.remove(e);
    }

    protected void fireChangement() {
        for (EcouteurModele e : ecouteurs) {
            e.modeleMisAJour(this);
        }
    }
}