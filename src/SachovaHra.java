import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

// Grafické rozhranie šachovej hry.
public class SachovaHra extends JFrame implements MouseListener {
    private Sachovnica sachovnica;
    private int vybratyX = -1;
    private int vybratyY = -1;
    private Farba naTahu = Farba.BIELA;
    private JPanel[][] panely;
    private JLabel stavHry;
    private JLabel casBieleho;
    private JLabel casCierneho;
    private Timer casovac;
    private int zostavajuciCasBieleho = 600;
    private int zostavajuciCasCierneho = 600;
    private boolean hraSkoncila = false;
    private String textKoniecHry = "";

    public SachovaHra() {
        sachovnica = new Sachovnica();
        panely = new JPanel[8][8];
        nastavOkno();
        obnovSachovnicu();

        casovac = new Timer(1000, udalost -> odpocitajCas());
        casovac.start();
    }

    private void nastavOkno() {
        setTitle("Jednoduché šachy");
        setSize(640, 690);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel hornaLista = new JPanel(new GridLayout(1, 3));
        stavHry = new JLabel("Na ťahu: biely hráč", SwingConstants.CENTER);
        casBieleho = new JLabel("Biely: 10:00", SwingConstants.CENTER);
        casCierneho = new JLabel("Čierny: 10:00", SwingConstants.CENTER);
        hornaLista.add(casBieleho);
        hornaLista.add(stavHry);
        hornaLista.add(casCierneho);
        add(hornaLista, BorderLayout.NORTH);

        JPanel mriezka = new JPanel(new GridLayout(8, 8));
        for (int riadok = 0; riadok < 8; riadok++) {
            for (int stlpec = 0; stlpec < 8; stlpec++) {
                JPanel panel = new JPanel(new BorderLayout());
                panel.addMouseListener(this);
                panely[riadok][stlpec] = panel;
                mriezka.add(panel);
            }
        }
        add(mriezka, BorderLayout.CENTER);
    }

    // Každú sekundu odpočíta jednu sekundu hráčovi, ktorý je na ťahu.
    private void odpocitajCas() {
        if (hraSkoncila) {
            return;
        }

        if (naTahu == Farba.BIELA) {
            zostavajuciCasBieleho = zostavajuciCasBieleho - 1;
            if (zostavajuciCasBieleho <= 0) {
                zostavajuciCasBieleho = 0;
                ukonciHruPreCas();
            }
        }
        else {
            zostavajuciCasCierneho = zostavajuciCasCierneho - 1;
            if (zostavajuciCasCierneho <= 0) {
                zostavajuciCasCierneho = 0;
                ukonciHruPreCas();
            }
        }

        obnovCasomieru();
        obnovStavHry();
    }

    private void ukonciHruPreCas() {
        hraSkoncila = true;
        if (naTahu == Farba.BIELA) {
            textKoniecHry = "Čas vypršal – vyhráva čierny";
        }
        else {
            textKoniecHry = "Čas vypršal – vyhráva biely";
        }
        casovac.stop();
        obnovStavHry();
        zobrazKoniecHry();
    }

    // Po každom ťahu skontroluje mat alebo pat.
    private void skontrolujKoniecHry() {
        boolean kralVSachu = sachovnica.jeKralVSachu(naTahu);
        boolean maTah = sachovnica.maPlatnyTah(naTahu);

        if (!maTah) {
            hraSkoncila = true;
            casovac.stop();

            if (kralVSachu) {
                if (naTahu == Farba.BIELA) {
                    textKoniecHry = "Šachmat – vyhráva čierny";
                }
                else {
                    textKoniecHry = "Šachmat – vyhráva biely";
                }
            }
            else {
                textKoniecHry = "Pat – hra sa skončila remízou";
            }

            obnovStavHry();
            zobrazKoniecHry();
        }
    }

    // Zobrazí výsledok uprostred okna a ponúkne novú hru.
    private void zobrazKoniecHry() {
        JLabel sprava = new JLabel(textKoniecHry, SwingConstants.CENTER);
        sprava.setFont(new Font("Arial", Font.BOLD, 18));
        sprava.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));

        JButton tlacidloNovaHra = new JButton("Nová hra");
        JPanel obsah = new JPanel(new BorderLayout(10, 10));
        obsah.add(sprava, BorderLayout.CENTER);
        obsah.add(tlacidloNovaHra, BorderLayout.SOUTH);

        JDialog okno = new JDialog(this, "Koniec hry", true);
        okno.setContentPane(obsah);
        okno.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
        tlacidloNovaHra.addActionListener(udalost -> {
            okno.dispose();
            novaHra();
        });
        okno.pack();
        okno.setLocationRelativeTo(this);
        okno.setVisible(true);
    }

    // Vynuluje šachovnicu aj čas a spustí novú hru.
    private void novaHra() {
        sachovnica = new Sachovnica();
        vybratyX = -1;
        vybratyY = -1;
        naTahu = Farba.BIELA;
        zostavajuciCasBieleho = 600;
        zostavajuciCasCierneho = 600;
        hraSkoncila = false;
        textKoniecHry = "";
        obnovSachovnicu();
        casovac.restart();
    }

    private void obnovCasomieru() {
        casBieleho.setText("Biely: " + prevedCasNaText(zostavajuciCasBieleho));
        casCierneho.setText("Čierny: " + prevedCasNaText(zostavajuciCasCierneho));
    }

    private String prevedCasNaText(int sekundy) {
        int minuty = sekundy / 60;
        int zvysneSekundy = sekundy % 60;
        String textSekund = String.valueOf(zvysneSekundy);
        if (zvysneSekundy < 10) {
            textSekund = "0" + textSekund;
        }
        return minuty + ":" + textSekund;
    }

    // Znovu vykreslí farby políčok a obrázky figúrok.
    private void obnovSachovnicu() {
        for (int riadok = 0; riadok < 8; riadok++) {
            for (int stlpec = 0; stlpec < 8; stlpec++) {
                JPanel panel = panely[riadok][stlpec];
                panel.removeAll();

                if ((riadok + stlpec) % 2 == 0) {
                    panel.setBackground(new java.awt.Color(240, 217, 181));
                }
                else {
                    panel.setBackground(new java.awt.Color(181, 136, 99));
                }

                if (riadok == vybratyY && stlpec == vybratyX) {
                    panel.setBackground(java.awt.Color.GREEN);
                }

                Figurka figurka = sachovnica.policka[riadok][stlpec];
                if (figurka != null) {
                    String cesta = "images/" + figurka.nazovObrazka;
                    ImageIcon ikona = new ImageIcon(cesta);
                    if (ikona.getIconWidth() > 0) {
                        Image obrazok = ikona.getImage().getScaledInstance(60, 60, Image.SCALE_SMOOTH);
                        panel.add(new JLabel(new ImageIcon(obrazok), SwingConstants.CENTER), BorderLayout.CENTER);
                    }
                    else {
                        // Ak obrázok chýba, zobrazí sa aspoň písmeno figúrky.
                        panel.add(new JLabel(skratkaFigurky(figurka), SwingConstants.CENTER), BorderLayout.CENTER);
                    }
                }

                panel.revalidate();
                panel.repaint();
            }
        }

        obnovStavHry();
        obnovCasomieru();
    }

    private void obnovStavHry() {
        if (hraSkoncila) {
            stavHry.setText(textKoniecHry);
            return;
        }

        if (sachovnica.jeKralVSachu(naTahu)) {
            stavHry.setText("Na ťahu: " + nazovFarby(naTahu) + " – šach");
        }
        else {
            stavHry.setText("Na ťahu: " + nazovFarby(naTahu));
        }
    }

    private String nazovFarby(Farba farba) {
        if (farba == Farba.BIELA) {
            return "biely hráč";
        }
        return "čierny hráč";
    }

    private String skratkaFigurky(Figurka figurka) {
        String znak = "?";
        if (figurka instanceof Kral) {
            znak = "K";
        }
        else if (figurka instanceof Dama) {
            znak = "D";
        }
        else if (figurka instanceof Veza) {
            znak = "V";
        }
        else if (figurka instanceof Strelec) {
            znak = "S";
        }
        else if (figurka instanceof Jazdec) {
            znak = "J";
        }
        else if (figurka instanceof Pesec) {
            znak = "P";
        }

        if (figurka.farba == Farba.CIERNA) {
            znak = znak.toLowerCase();
        }
        return znak;
    }

    @Override
    public void mousePressed(MouseEvent udalost) {
        if (hraSkoncila) {
            return;
        }

        JPanel kliknutyPanel = (JPanel) udalost.getSource();
        int klikX = -1;
        int klikY = -1;

        for (int riadok = 0; riadok < 8; riadok++) {
            for (int stlpec = 0; stlpec < 8; stlpec++) {
                if (panely[riadok][stlpec] == kliknutyPanel) {
                    klikX = stlpec;
                    klikY = riadok;
                }
            }
        }

        if (klikX == -1 || klikY == -1) {
            return;
        }

        if (vybratyX == -1) {
            Figurka figurka = sachovnica.policka[klikY][klikX];
            if (figurka != null && figurka.farba == naTahu) {
                vybratyX = klikX;
                vybratyY = klikY;
                obnovSachovnicu();
            }
        }
        else {
            if (vybratyX == klikX && vybratyY == klikY) {
                vybratyX = -1;
                vybratyY = -1;
                obnovSachovnicu();
                return;
            }

            Figurka figurka = sachovnica.policka[vybratyY][vybratyX];
            if (figurka != null && figurka.jePlatnyTah(sachovnica, vybratyX, vybratyY, klikX, klikY)) {
                if (!sachovnica.tahOhroziKrala(vybratyX, vybratyY, klikX, klikY)) {
                    vykonajTah(vybratyX, vybratyY, klikX, klikY, figurka);
                    if (naTahu == Farba.BIELA) {
                        naTahu = Farba.CIERNA;
                    }
                    else {
                        naTahu = Farba.BIELA;
                    }
                    skontrolujKoniecHry();
                }
            }

            vybratyX = -1;
            vybratyY = -1;
            obnovSachovnicu();
        }
    }

    // Vyberie figúrku, na ktorú sa zmení pešiak na poslednom riadku.
    private Figurka vyberFigurkuNaPremenu(Farba farba) {
        String[] moznosti = {"Dáma", "Veža", "Strelec", "Jazdec"};
        int vyber = JOptionPane.showOptionDialog(
                this,
                "Na ktorú figúrku chceš premeniť pešiaka?",
                "Premena pešiaka",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                moznosti,
                moznosti[0]
        );

        if (vyber == 1) {
            return new Veza(farba);
        }
        if (vyber == 2) {
            return new Strelec(farba);
        }
        if (vyber == 3) {
            return new Jazdec(farba);
        }

        // Ak hráč zavrie okno alebo nič nevyberie, pešiak sa zmení na dámu.
        return new Dama(farba);
    }

    // Vykoná ťah, vrátane špeciálnych pravidiel.
    private void vykonajTah(int zaciatokX, int zaciatokY, int cielX, int cielY, Figurka figurka) {
        Figurka ciel = sachovnica.policka[cielY][cielX];

        // Branie mimochodom odstráni pešiaka zo susedného políčka.
        if (figurka instanceof Pesec && zaciatokX != cielX && ciel == null) {
            sachovnica.policka[zaciatokY][cielX] = null;
        }

        // Pri rošáde sa presunie aj veža.
        if (figurka instanceof Kral && cielX - zaciatokX == 2) {
            sachovnica.policka[zaciatokY][5] = sachovnica.policka[zaciatokY][7];
            sachovnica.policka[zaciatokY][7] = null;
            sachovnica.policka[zaciatokY][5].uzSaPohla = true;
        }
        else if (figurka instanceof Kral && cielX - zaciatokX == -2) {
            sachovnica.policka[zaciatokY][3] = sachovnica.policka[zaciatokY][0];
            sachovnica.policka[zaciatokY][0] = null;
            sachovnica.policka[zaciatokY][3].uzSaPohla = true;
        }

        sachovnica.policka[cielY][cielX] = figurka;
        sachovnica.policka[zaciatokY][zaciatokX] = null;
        figurka.uzSaPohla = true;

        // Pri dosiahnutí posledného riadku si hráč vyberie novú figúrku.
        if (figurka instanceof Pesec && (cielY == 0 || cielY == 7)) {
            sachovnica.policka[cielY][cielX] = vyberFigurkuNaPremenu(figurka.farba);
        }

        sachovnica.pridajTah(new Tah(figurka, zaciatokX, zaciatokY, cielX, cielY));
    }

    @Override
    public void mouseClicked(MouseEvent udalost) {
    }

    @Override
    public void mouseReleased(MouseEvent udalost) {
    }

    @Override
    public void mouseEntered(MouseEvent udalost) {
    }

    @Override
    public void mouseExited(MouseEvent udalost) {
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            SachovaHra hra = new SachovaHra();
            hra.setLocationRelativeTo(null);
            hra.setVisible(true);
        });
    }
}
