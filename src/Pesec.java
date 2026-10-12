// Pešiak sa pohybuje dopredu a súperove figúrky berie šikmo.
public class Pesec extends Figurka {
    public Pesec(Farba farba) {
        super(farba, vyberNazovObrazka(farba, "white-pawn.png", "black-pawn.png"));
    }

    @Override
    public boolean jePlatnyTah(Sachovnica sachovnica, int zaciatokX, int zaciatokY, int cielX, int cielY) {
        if (!jeNaSachovnici(cielX, cielY) || (zaciatokX == cielX && zaciatokY == cielY)) {
            return false;
        }
        int smer = -1;
        int zaciatocnyRiadok = 6;
        if (farba == Farba.CIERNA) {
            smer = 1;
            zaciatocnyRiadok = 1;
        }

        int rozdielX = cielX - zaciatokX;
        int rozdielY = cielY - zaciatokY;
        Figurka ciel = sachovnica.policka[cielY][cielX];

        // Bežný pohyb o jedno políčko dopredu.
        if (rozdielX == 0 && rozdielY == smer && ciel == null) {
            return true;
        }

        // Prvý pohyb môže byť dlhý dve políčka, ak je cesta voľná.
        if (rozdielX == 0 && rozdielY == smer * 2 && zaciatokY == zaciatocnyRiadok && ciel == null) {
            if (sachovnica.policka[zaciatokY + smer][zaciatokX] == null) {
                return true;
            }
        }

        // Bežné branie súperovej figúrky šikmo.
        int absolutnyRozdielX = rozdielX;
        if (absolutnyRozdielX < 0) {
            absolutnyRozdielX = -absolutnyRozdielX;
        }
        if (absolutnyRozdielX == 1 && rozdielY == smer && ciel != null && ciel.farba != farba) {
            return true;
        }

        // Branie mimochodom: súperov pešiak sa musel práve posunúť o dve políčka.
        if (absolutnyRozdielX == 1 && rozdielY == smer && ciel == null) {
            Tah poslednyTah = sachovnica.vratPoslednyTah();
            if (poslednyTah != null && poslednyTah.figurka instanceof Pesec) {
                int dlzka = poslednyTah.koniecY - poslednyTah.zaciatokY;
                if (dlzka < 0) {
                    dlzka = -dlzka;
                }
                Figurka sused = sachovnica.policka[zaciatokY][cielX];
                if (dlzka == 2 && poslednyTah.koniecX == cielX && poslednyTah.koniecY == zaciatokY
                        && sused instanceof Pesec && sused.farba != farba) return true;
            }
        }
        return false;
    }

    @Override
    public boolean ohrozujePolicko(Sachovnica sachovnica, int zaciatokX, int zaciatokY, int cielX, int cielY) {
        int smer = -1;
        if (farba == Farba.CIERNA) {
            smer = 1;
        }
        int rozdielX = cielX - zaciatokX;
        if (rozdielX < 0) {
            rozdielX = -rozdielX;
        }
        return rozdielX == 1 && cielY - zaciatokY == smer;
    }
}
