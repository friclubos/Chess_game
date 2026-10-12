// Uchováva údaje o jednom vykonanom ťahu.
public class Tah {
    public Figurka figurka;
    public int zaciatokX;
    public int zaciatokY;
    public int koniecX;
    public int koniecY;

    public Tah(Figurka figurka, int zaciatokX, int zaciatokY, int koniecX, int koniecY) {
        this.figurka = figurka;
        this.zaciatokX = zaciatokX;
        this.zaciatokY = zaciatokY;
        this.koniecX = koniecX;
        this.koniecY = koniecY;
    }
}
