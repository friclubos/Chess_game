// Spoločný základ pre všetky šachové figúrky.
public abstract class Figurka {
    public Farba farba;
    public String nazovObrazka;
    public boolean uzSaPohla;

    public Figurka(Farba farba, String nazovObrazka) {
        this.farba = farba;
        this.nazovObrazka = nazovObrazka;
        this.uzSaPohla = false;
    }

    // Vyberie názov obrázka podľa farby, aby sa rovnaký kód neopakoval pri každej figúrke.
    protected static String vyberNazovObrazka(Farba farba, String bielyObrazok, String ciernyObrazok) {
        if (farba == Farba.BIELA) {
            return bielyObrazok;
        }
        return ciernyObrazok;
    }

    // Overí, či sa figúrka môže presunúť na zadané políčko.
    public abstract boolean jePlatnyTah(Sachovnica sachovnica, int zaciatokX, int zaciatokY,
                                        int cielX, int cielY);

    // Overí, či figúrka ohrozuje zadané políčko.
    // Táto metóda je samostatná, pretože napríklad pešiak ohrozuje iné políčka,
    // než na ktoré sa môže bežne presúvať.
    public abstract boolean ohrozujePolicko(Sachovnica sachovnica, int zaciatokX, int zaciatokY,
                                            int cielX, int cielY);

    protected boolean jeNaSachovnici(int x, int y) {
        return x >= 0 && x < 8 && y >= 0 && y < 8;
    }

    // Zistí, či medzi začiatočným a cieľovým políčkom nie je iná figúrka.
    protected boolean jeCestaVolna(Sachovnica sachovnica, int zaciatokX, int zaciatokY,
                                   int cielX, int cielY) {
        int krokX = 0;
        int krokY = 0;

        if (cielX > zaciatokX) {
            krokX = 1;
        }
else if (cielX < zaciatokX) {
            krokX = -1;
        }

        if (cielY > zaciatokY) {
            krokY = 1;
        }
else if (cielY < zaciatokY) {
            krokY = -1;
        }

        int x = zaciatokX + krokX;
        int y = zaciatokY + krokY;

        while (x != cielX || y != cielY) {
            if (sachovnica.policka[y][x] != null) {
                return false;
            }
            x = x + krokX;
            y = y + krokY;
        }
        return true;
    }

    // Spoločná kontrola pohybu veže. Používa sa aj pri kontrole ohrozenia.
    protected boolean jePohybVeze(Sachovnica sachovnica, int zaciatokX, int zaciatokY,
                                  int cielX, int cielY) {
        if (zaciatokX == cielX && zaciatokY == cielY) {
            return false;
        }
        if (zaciatokX != cielX && zaciatokY != cielY) {
            return false;
        }
        return jeCestaVolna(sachovnica, zaciatokX, zaciatokY, cielX, cielY);
    }

    // Spoločná kontrola pohybu strelca. Používa sa aj pri kontrole ohrozenia.
    protected boolean jePohybStrelca(Sachovnica sachovnica, int zaciatokX, int zaciatokY,
                                     int cielX, int cielY) {
        if (zaciatokX == cielX && zaciatokY == cielY) {
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
        if (rozdielX != rozdielY) {
            return false;
        }
        return jeCestaVolna(sachovnica, zaciatokX, zaciatokY, cielX, cielY);
    }

    // Spoločná kontrola pohybu dámy. Dáma sa pohybuje ako veža alebo strelec.
    protected boolean jePohybDamy(Sachovnica sachovnica, int zaciatokX, int zaciatokY,
                                  int cielX, int cielY) {
        if (jePohybVeze(sachovnica, zaciatokX, zaciatokY, cielX, cielY)) {
            return true;
        }
        return jePohybStrelca(sachovnica, zaciatokX, zaciatokY, cielX, cielY);
    }

    protected boolean cielNieJeVlastnaFigurka(Sachovnica sachovnica, int cielX, int cielY) {
        Figurka ciel = sachovnica.policka[cielY][cielX];
        if (ciel == null) {
            return true;
        }
        return ciel.farba != this.farba;
    }
}
