import java.util.ArrayList;

// Uchováva rozloženie figúrok a históriu ťahov.
public class Sachovnica {
    public Figurka[][] policka;
    private ArrayList<Tah> historiaTahov;

    public Sachovnica() {
        policka = new Figurka[8][8];
        historiaTahov = new ArrayList<Tah>();
        nastavFigurky();
    }

    // Rozmiestni figúrky na začiatku hry.
    private void nastavFigurky() {
        policka[0][0] = new Veza(Farba.CIERNA);
        policka[0][1] = new Jazdec(Farba.CIERNA);
        policka[0][2] = new Strelec(Farba.CIERNA);
        policka[0][3] = new Dama(Farba.CIERNA);
        policka[0][4] = new Kral(Farba.CIERNA);
        policka[0][5] = new Strelec(Farba.CIERNA);
        policka[0][6] = new Jazdec(Farba.CIERNA);
        policka[0][7] = new Veza(Farba.CIERNA);

        for (int i = 0; i < 8; i++) {
            policka[1][i] = new Pesec(Farba.CIERNA);
        }

        policka[7][0] = new Veza(Farba.BIELA);
        policka[7][1] = new Jazdec(Farba.BIELA);
        policka[7][2] = new Strelec(Farba.BIELA);
        policka[7][3] = new Dama(Farba.BIELA);
        policka[7][4] = new Kral(Farba.BIELA);
        policka[7][5] = new Strelec(Farba.BIELA);
        policka[7][6] = new Jazdec(Farba.BIELA);
        policka[7][7] = new Veza(Farba.BIELA);

        for (int i = 0; i < 8; i++) {
            policka[6][i] = new Pesec(Farba.BIELA);
        }
    }

    public Kral najdiKrala(Farba farba) {
        for (int riadok = 0; riadok < 8; riadok++) {
            for (int stlpec = 0; stlpec < 8; stlpec++) {
                Figurka figurka = policka[riadok][stlpec];
                if (figurka instanceof Kral && figurka.farba == farba) {
                    return (Kral) figurka;
                }
            }
        }
        return null;
    }

    public Tah vratPoslednyTah() {
        if (historiaTahov.isEmpty()) {
            return null;
        }
        return historiaTahov.get(historiaTahov.size() - 1);
    }

    public void pridajTah(Tah tah) {
        historiaTahov.add(tah);
    }

    // Zistí, či je kráľ danej farby ohrozený.
    public boolean jeKralVSachu(Farba farba) {
        int kralX = -1;
        int kralY = -1;

        // Nájde polohu kráľa pri jednom prechode cez šachovnicu.
        for (int riadok = 0; riadok < 8; riadok++) {
            for (int stlpec = 0; stlpec < 8; stlpec++) {
                Figurka figurka = policka[riadok][stlpec];
                if (figurka instanceof Kral && figurka.farba == farba) {
                    kralX = stlpec;
                    kralY = riadok;
                }
            }
        }

        if (kralX == -1 || kralY == -1) {
            return false;
        }

        // Skontroluje, či na kráľa útočí niektorá súperova figúrka.
        for (int riadok = 0; riadok < 8; riadok++) {
            for (int stlpec = 0; stlpec < 8; stlpec++) {
                Figurka figurka = policka[riadok][stlpec];
                if (figurka != null && figurka.farba != farba) {
                    if (figurka.ohrozujePolicko(this, stlpec, riadok, kralX, kralY)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }


    // Zistí, či má hráč aspoň jeden ťah, po ktorom jeho kráľ nebude v šachu.
    public boolean maPlatnyTah(Farba farba) {
        for (int riadok = 0; riadok < 8; riadok++) {
            for (int stlpec = 0; stlpec < 8; stlpec++) {
                Figurka figurka = policka[riadok][stlpec];
                if (figurka != null && figurka.farba == farba) {
                    for (int cielRiadok = 0; cielRiadok < 8; cielRiadok++) {
                        for (int cielStlpec = 0; cielStlpec < 8; cielStlpec++) {
                            if (figurka.jePlatnyTah(this, stlpec, riadok, cielStlpec, cielRiadok)) {
                                if (!tahOhroziKrala(stlpec, riadok, cielStlpec, cielRiadok)) {
                                    return true;
                                }
                            }
                        }
                    }
                }
            }
        }
        return false;
    }

    // Zistí, či políčko ohrozuje niektorá figúrka zadanej farby.
    public boolean jePolickoOhrozene(int x, int y, Farba farbaUtociaceho) {
        for (int riadok = 0; riadok < 8; riadok++) {
            for (int stlpec = 0; stlpec < 8; stlpec++) {
                Figurka figurka = policka[riadok][stlpec];
                if (figurka != null && figurka.farba == farbaUtociaceho) {
                    if (figurka.ohrozujePolicko(this, stlpec, riadok, x, y)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    // Dočasne vykoná ťah, skontroluje kráľa a potom vráti figúrky späť.
    public boolean tahOhroziKrala(int zaciatokX, int zaciatokY, int cielX, int cielY) {
        Figurka figurka = policka[zaciatokY][zaciatokX];
        Figurka ciel = policka[cielY][cielX];
        if (figurka == null) {
            return true;
        }

        policka[cielY][cielX] = figurka;
        policka[zaciatokY][zaciatokX] = null;

        // Pri braní mimochodom sa odstráni aj preskakovaný pešiak.
        Figurka odstranenyPesec = null;
        boolean branieMimochodom = figurka instanceof Pesec && zaciatokX != cielX && ciel == null;
        if (branieMimochodom) {
            odstranenyPesec = policka[zaciatokY][cielX];
            policka[zaciatokY][cielX] = null;
        }

        // Pri rošáde sa dočasne presunie aj veža.
        Figurka vez = null;
        int povodnyStlpecVeze = -1;
        int novyStlpecVeze = -1;
        if (figurka instanceof Kral && cielX - zaciatokX == 2) {
            povodnyStlpecVeze = 7;
            novyStlpecVeze = 5;
        }
else if (figurka instanceof Kral && cielX - zaciatokX == -2) {
            povodnyStlpecVeze = 0;
            novyStlpecVeze = 3;
        }
        if (povodnyStlpecVeze != -1) {
            vez = policka[zaciatokY][povodnyStlpecVeze];
            policka[zaciatokY][novyStlpecVeze] = vez;
            policka[zaciatokY][povodnyStlpecVeze] = null;
        }

        boolean kralJeOhrozeny = jeKralVSachu(figurka.farba);

        policka[zaciatokY][zaciatokX] = figurka;
        policka[cielY][cielX] = ciel;
        if (branieMimochodom) {
            policka[zaciatokY][cielX] = odstranenyPesec;
        }
        if (povodnyStlpecVeze != -1) {
            policka[zaciatokY][povodnyStlpecVeze] = vez;
            policka[zaciatokY][novyStlpecVeze] = null;
        }
        return kralJeOhrozeny;
    }
}
