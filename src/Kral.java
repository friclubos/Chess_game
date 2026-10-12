// Kráľ sa bežne pohybuje o jedno políčko ľubovoľným smerom.
public class Kral extends Figurka {
    public Kral(Farba farba) {
        super(farba, vyberNazovObrazka(farba, "white-king.png", "black-king.png"));
    }

    @Override
    public boolean jePlatnyTah(Sachovnica sachovnica, int zaciatokX, int zaciatokY, int cielX, int cielY) {
        if (!jeNaSachovnici(cielX, cielY) || (zaciatokX == cielX && zaciatokY == cielY)) {
            return false;
        }
        int rozdielX = cielX - zaciatokX;
        int rozdielY = cielY - zaciatokY;
        int absolutnyRozdielX = rozdielX;
        int absolutnyRozdielY = rozdielY;
        if (absolutnyRozdielX < 0) {
            absolutnyRozdielX = -absolutnyRozdielX;
        }
        if (absolutnyRozdielY < 0) {
            absolutnyRozdielY = -absolutnyRozdielY;
        }

        if (absolutnyRozdielX <= 1 && absolutnyRozdielY <= 1) {
            return cielNieJeVlastnaFigurka(sachovnica, cielX, cielY);
        }
        return jeMoznaRosada(sachovnica, zaciatokX, zaciatokY, cielX, cielY);
    }

    private boolean jeMoznaRosada(Sachovnica sachovnica, int zaciatokX, int zaciatokY, int cielX, int cielY) {
        int domovnyRiadok = 0;
        if (farba == Farba.BIELA) {
            domovnyRiadok = 7;
        }

        if (uzSaPohla || zaciatokY != cielY || cielY != domovnyRiadok) {
            return false;
        }
        if (cielX != 2 && cielX != 6) {
            return false;
        }
        if (sachovnica.jeKralVSachu(farba)) {
            return false;
        }

        int stlpecVeze = 0;
        if (cielX == 6) {
            stlpecVeze = 7;
        }
        Figurka figurkaVeze = sachovnica.policka[zaciatokY][stlpecVeze];
        if (!(figurkaVeze instanceof Veza) || figurkaVeze.farba != farba || figurkaVeze.uzSaPohla) {
            return false;
        }

        int smer = -1;
        if (cielX > zaciatokX) {
            smer = 1;
        }
        int x = zaciatokX + smer;
        while (x != stlpecVeze) {
            if (sachovnica.policka[zaciatokY][x] != null) {
                return false;
            }
            x = x + smer;
        }

        // Kráľ nesmie prejsť cez ohrozené políčko.
        x = zaciatokX + smer;
        if (sachovnica.jePolickoOhrozene(x, zaciatokY, opacnaFarba())) {
            return false;
        }
        if (sachovnica.jePolickoOhrozene(cielX, cielY, opacnaFarba())) {
            return false;
        }
        return true;
    }

    private Farba opacnaFarba() {
        if (farba == Farba.BIELA) {
            return Farba.CIERNA;
        }
        return Farba.BIELA;
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
        return (rozdielX <= 1 && rozdielY <= 1) && !(rozdielX == 0 && rozdielY == 0);
    }
}
