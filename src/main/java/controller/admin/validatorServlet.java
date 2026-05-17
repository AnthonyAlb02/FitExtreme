package controller.admin;

import java.math.BigDecimal;

public class validatorServlet {

    // Metodo che controlla se un campo stringa è vuoto o nullo
    public static boolean isVuoto(String s) {
        return s == null || s.trim().isEmpty();
    }

    // Controllo nome prodotto
    public static String validaNome(String nome) {
        if (isVuoto(nome)) {
            return "Il nome del prodotto non può essere vuoto.";
        }
        if (nome.length() < 3) {
            return "Il nome deve contenere almeno 3 caratteri.";
        }
        return null; // nessun errore
    }

    // Controllo descrizione
    public static String validaDescrizione(String descrizione) {
        if (isVuoto(descrizione)) {
            return "La descrizione non può essere vuota.";
        }
        if (descrizione.length() < 10) {
            return "La descrizione deve contenere almeno 10 caratteri.";
        }
        return null;
    }

    // Controllo prezzo
    public static String validaPrezzo(String prezzo) {
        if (isVuoto(prezzo)) {
            return "Il prezzo non può essere vuoto.";
        }

        try {
            BigDecimal p = new BigDecimal(prezzo);
            if (p.compareTo(BigDecimal.ZERO) <= 0) {
                return "Il prezzo deve essere maggiore di zero.";
            }
        } catch (NumberFormatException e) {
            return "Il prezzo deve essere un numero valido.";
        }

        return null;
    }

    // Controllo quantità
    public static String validaQuantita(String quantita) {
        if (isVuoto(quantita)) {
            return "La quantità non può essere vuota.";
        }

        try {
            int q = Integer.parseInt(quantita);
            if (q < 0) {
                return "La quantità non può essere negativa.";
            }
        } catch (NumberFormatException e) {
            return "La quantità deve essere un numero intero.";
        }

        return null;
    }

    // Controllo categoria
    public static String validaCategoria(String categoria) {
        if (isVuoto(categoria)) {
            return "La categoria non può essere vuota.";
        }

        try {
            int c = Integer.parseInt(categoria);
            if (c <= 0) {
                return "La categoria selezionata non è valida.";
            }
        } catch (NumberFormatException e) {
            return "La categoria deve essere un numero valido.";
        }

        return null;
    }

    // Metodo che esegue TUTTE le validazioni insieme
    public static String validaTutto(String nome, String descrizione, String prezzo, String quantita, String categoria) {

        String errore;

        errore = validaNome(nome);
        if (errore != null) return errore;

        errore = validaDescrizione(descrizione);
        if (errore != null) return errore;

        errore = validaPrezzo(prezzo);
        if (errore != null) return errore;

        errore = validaQuantita(quantita);
        if (errore != null) return errore;

        errore = validaCategoria(categoria);
        if (errore != null) return errore;

        return null; // tutto ok
    }
}
