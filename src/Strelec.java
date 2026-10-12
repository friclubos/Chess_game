// Strelec sa pohybuje po uhlopriečkach.
public class Strelec extends Figurka {
    public Strelec(Farba farba) {
        super(farba, vyberNazovObrazka(farba, "white-bishop.png", "black-bishop.png"));
    }

    @Override
    public boolean jePlatnyTah(Sachovnica sachovnica, int zaciatokX, int zaciatokY, int cielX, int cielY) {
        if (!jeNaSachovnici(cielX, cielY)) {
            return false;
        }
        if (!cielNieJeVlastnaFigurka(sachovnica, cielX, cielY)) {
            return false;
        }
        return jePohybStrelca(sachovnica, zaciatokX, zaciatokY, cielX, cielY);
    }

    @Override
    public boolean ohrozujePolicko(Sachovnica sachovnica, int zaciatokX, int zaciatokY, int cielX, int cielY) {
        return jePohybStrelca(sachovnica, zaciatokX, zaciatokY, cielX, cielY);
    }
}
