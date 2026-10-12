// Dáma spája pohyb veže a strelca.
public class Dama extends Figurka {
    public Dama(Farba farba) {
        super(farba, vyberNazovObrazka(farba, "white-queen.png", "black-queen.png"));
    }

    @Override
    public boolean jePlatnyTah(Sachovnica sachovnica, int zaciatokX, int zaciatokY, int cielX, int cielY) {
        if (!jeNaSachovnici(cielX, cielY)) {
            return false;
        }
        if (!cielNieJeVlastnaFigurka(sachovnica, cielX, cielY)) {
            return false;
        }
        return jePohybDamy(sachovnica, zaciatokX, zaciatokY, cielX, cielY);
    }

    @Override
    public boolean ohrozujePolicko(Sachovnica sachovnica, int zaciatokX, int zaciatokY, int cielX, int cielY) {
        return jePohybDamy(sachovnica, zaciatokX, zaciatokY, cielX, cielY);
    }
}
