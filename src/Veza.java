// Veža sa pohybuje vodorovne alebo zvislo.
public class Veza extends Figurka {
    public Veza(Farba farba) {
        super(farba, vyberNazovObrazka(farba, "white-rook.png", "black-rook.png"));
    }

    @Override
    public boolean jePlatnyTah(Sachovnica sachovnica, int zaciatokX, int zaciatokY, int cielX, int cielY) {
        if (!jeNaSachovnici(cielX, cielY)) {
            return false;
        }
        if (!cielNieJeVlastnaFigurka(sachovnica, cielX, cielY)) {
            return false;
        }
        return jePohybVeze(sachovnica, zaciatokX, zaciatokY, cielX, cielY);
    }

    @Override
    public boolean ohrozujePolicko(Sachovnica sachovnica, int zaciatokX, int zaciatokY, int cielX, int cielY) {
        return jePohybVeze(sachovnica, zaciatokX, zaciatokY, cielX, cielY);
    }
}
