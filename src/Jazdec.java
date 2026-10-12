// Jazdec sa pohybuje v tvare písmena L a môže preskakovať figúrky.
public class Jazdec extends Figurka {
    public Jazdec(Farba farba) {
        super(farba, vyberNazovObrazka(farba, "white-knight.png", "black-knight.png"));
    }

    @Override
    public boolean jePlatnyTah(Sachovnica sachovnica, int zaciatokX, int zaciatokY, int cielX, int cielY) {
        if (!jeNaSachovnici(cielX, cielY) || (zaciatokX == cielX && zaciatokY == cielY)) {
            return false;
        }
        int rozdielX = cielX - zaciatokX;
        int rozdielY = cielY - zaciatokY;
        if (rozdielX < 0) {
            rozdielX = -rozdielX;
        }
        if (rozdielY < 0) {
            rozdielY = -rozdielY;
        }
        if (!((rozdielX == 2 && rozdielY == 1) || (rozdielX == 1 && rozdielY == 2))) {
            return false;
        }
        return cielNieJeVlastnaFigurka(sachovnica, cielX, cielY);
    }

    @Override
    public boolean ohrozujePolicko(Sachovnica sachovnica, int zaciatokX, int zaciatokY, int cielX, int cielY) {
        int rozdielX = cielX - zaciatokX;
        int rozdielY = cielY - zaciatokY;
        if (rozdielX < 0) {
            rozdielX = -rozdielX;
        }
        if (rozdielY < 0) {
            rozdielY = -rozdielY;
        }
        return (rozdielX == 2 && rozdielY == 1) || (rozdielX == 1 && rozdielY == 2);
    }
}
