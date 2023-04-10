import java.util.Scanner;

public class Gra {
    public static char[][] tab = new char[4][4];
    static Scanner scan = new Scanner(System.in);

    public static void main(String[] args) {

        /*
            pierwszy kontakt z graczem - wybór dokonującego pierwszy ruch
         */
        System.out.print("Kto ma zacząć? 1 - gracz  2 - SI: ");
        int wybranyGracz = scan.nextInt();

        /*
            na początek wszystkie pola na planszy oznaczane jako puste
         */
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                tab[i][j] = '-';
            }
        }

        /*
            gra odbywa się w tej pętli
         */
        while (!stan_gry.czy_koniec_gry()) {
            if (wybranyGracz == 1) {
                ruszaGracz();
                printTab();
                wybranyGracz = 2;
            }else{
                ruszaSI();
                printTab();
                wybranyGracz = 1;
            }
        }

        /*
            po zakończeniu gry wyświetlany jest komunikat o wyniku
         */
        if (wybranyGracz == 1) {
            System.out.println("Gratulacje, wygrałeś!");
        } else {
            System.out.println("Przegrałeś, spróbuj jeszcze raz.");
        }

    }

    static void printTab() {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                System.out.print(tab[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println();
    }

    static void ruszaGracz() {
        int x, y;
        System.out.print("Wprowadź wiersz i kolumnę: ");
        x = scan.nextInt() - 1;
        y = scan.nextInt() - 1;
        if (stan_gry.mozliwy_ruch(x, y)) {
            tab[x][y] = 'X';
        }
        else {
            System.out.print("Dane nieprawidłowe. Spróbuj jeszcze raz... \n");
            ruszaGracz();
        }
    }

    static void ruszaSI() {
        int topX = -1;
        int topY = -1;
        int topWynik = Integer.MAX_VALUE;
        for (int x = 0; x < 4; x++) {
            for (int y = 0; y < 4; y++) {
                if (stan_gry.mozliwy_ruch(x, y)) {
                    /*
                        dla każdego z kolejnych możliwych ruchów
                     */
                    tab[x][y] = 'X';
                    /*
                        wykonuje się ruch, i dokonuje ewaluacji
                        planszy algorytmem minimax
                     */
                    int wynik = minimax(true, Integer.MIN_VALUE, Integer.MAX_VALUE);
                    /*
                        SI jest graczem maksymalizującym,
                        w ramach algorytmu minimax, dla ruchu
                        wygrywającej sekwencji (gracz został
                        zmuszony do dopełnienia linii 'X'),
                        zwracany jest bestMin.
                     */
                    tab[x][y] = '-';
                    if (wynik < topWynik) {
                        /*
                            ...który nadpisze topWynik...
                         */
                        topWynik = wynik;
                        /*
                            ... a obecny ruch zapisany zostanie jako najlepszy...
                         */
                        topX = x;
                        topY = y;
                    }
                }
            }
        }
        /*
            ... i zostanie wpisany w planszę (statyczną tablicę).
         */
        tab[topX][topY] = 'X';
    }

    static int minimax(boolean maksymalizacja, int alfa, int beta) {
        if (stan_gry.czy_koniec_gry()) {
            return wynik(maksymalizacja);
        }

        if (maksymalizacja) {
            int bestMin = Integer.MAX_VALUE;
            /*  alfa-----beta-----bestMin */
            for (int x = 0; x < 4; x++) {
                for (int y = 0; y < 4; y++) {
                    if (stan_gry.mozliwy_ruch(x, y)) {
                        /*  dla wszystkich ruchów, które mogą zajść  */
                        tab[x][y] = 'X';
                        /*
                            dla potrzeb rekurencji kolejno wybrane
                            pola są oznaczane
                         */
                        int eval = minimax(!maksymalizacja, alfa, beta);
                        /*
                            funkcja rekurencyjnie generuje ruchy aż
                            stan_gry.czy_koniec_gry() == true
                            po czym w zależności od stanu gry:
                            eval = 1, gdy dla danej sekwencji ruchów,
                            SI skutecznie zmusza gracza minimalizującego
                            (gracza) do przegranej (cztery 'X' w linii)
                            eval = -1, w przeciwnym przypadku
                         */
                        tab[x][y] = '-';
                        /*
                            pola są odznaczane, by nie naruszać stanu planszy
                         */
                        bestMin = Math.min(bestMin, eval);
                        /*
                            jeśli SI kończy zwracany jest bestMin...
                         */
                        beta = Math.min(beta, bestMin);
                        /*
                            i ustawiany parametr beta
                         */
                        if (beta <= alfa) {
                            /*
                                cięcie alfa beta gdy niespełniony warunek alfa < beta,
                                algorytm minimax pomija dalsze stany gry "wgłąb" (y)
                                i przesuwa się "wszerz" (x), co usprawnia algorytm
                             */
                            break;
                        }
                    }
                }
            }
            return bestMin;
        } else {
            int bestMax = Integer.MIN_VALUE;
            for (int x = 0; x < 4; x++) {
                for (int y = 0; y < 4; y++) {
                    if (stan_gry.mozliwy_ruch(x, y)) {
                        tab[x][y] = 'X';
                        int eval = minimax(!maksymalizacja, alfa, beta);
                        tab[x][y] = '-';
                        bestMax = Math.max(bestMax, eval);
                        alfa = Math.max(alfa, bestMax);
                        if (beta <= alfa) {
                            break;
                        }
                    }
                }
            }
            return bestMax;
        }
    }

    static int wynik(boolean maksymalizacja) {
            return maksymalizacja ? 1 : -1;
    }
}
