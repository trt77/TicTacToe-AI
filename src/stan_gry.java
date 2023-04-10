public class stan_gry {
    public static boolean czy_koniec_gry() {
        /*
            sprawdź kolumny
         */
        for(int y = 0; y < 4; y++) {
            if (Gra.tab[0][y] == 'X' && Gra.tab[1][y] == 'X' && Gra.tab[2][y] == 'X' && Gra.tab[3][y] == 'X')
                return true;
        }
        /*
            sprawdź wiersze
         */
        for(int x = 0; x < 4; x++) {
            if (Gra.tab[x][0] == 'X' && Gra.tab[x][1] == 'X' && Gra.tab[x][2] == 'X' && Gra.tab[x][3] == 'X')
                return true;
        }
        /*
            sprawdź przekątne (2)
         */
        if(Gra.tab[0][0] == 'X' && Gra.tab[1][1] == 'X' && Gra.tab[2][2] == 'X' && Gra.tab[3][3] == 'X')
            return true;
        if(Gra.tab[0][3] == 'X' && Gra.tab[1][2] == 'X' && Gra.tab[2][1] == 'X' && Gra.tab[3][0] == 'X')
            return true;

        return false;
    }

    public static boolean mozliwy_ruch(int x, int y) {
        return (Gra.tab[x][y] == '-');
    }
}
