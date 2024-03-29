package no.oslomet.cs.algdat.Oblig3;


import java.util.ArrayList;
import java.util.Comparator;
import java.util.Objects;
import java.util.StringJoiner;

public class SBinTre<T> {

    private static final class Node<T>   // en indre nodeklasse
    {
        private T verdi;                   // nodens verdi
        private Node<T> venstre, høyre;    // venstre og høyre barn
        private Node<T> forelder;          // forelder

        // konstruktør
        private Node(T verdi, Node<T> v, Node<T> h, Node<T> forelder) {
            this.verdi = verdi;
            venstre = v;
            høyre = h;
            this.forelder = forelder;
        }

        private Node(T verdi, Node<T> forelder)  // konstruktør
        {
            this(verdi, null, null, forelder);
        }

        @Override
        public String toString() {
            return "" + verdi;
        }

    } // class Node

    private Node<T> rot;                            // peker til rotnoden
    private int antall;                             // antall noder
    private int endringer;                          // antall endringer

    private final Comparator<? super T> comp;       // komparator

    public SBinTre(Comparator<? super T> c)    // konstruktør
    {
        rot = null;
        antall = 0;
        comp = c;
    }

    public boolean inneholder(T verdi) {
        if (verdi == null) return false;

        Node<T> p = rot;

        while (p != null) {
            int cmp = comp.compare(verdi, p.verdi);
            if (cmp < 0) p = p.venstre;
            else if (cmp > 0) p = p.høyre;
            else return true;
        }

        return false;
    }

    public int antall() {
        return antall;
    }

    public String toStringPostOrder() {
        if (tom()) return "[]";

        StringJoiner s = new StringJoiner(", ", "[", "]");

        Node<T> p = førstePostorden(rot); // går til den første i postorden
        while (p != null) {
            s.add(p.verdi.toString());
            p = nestePostorden(p);
        }

        return s.toString();
    }

    public boolean tom() {
        return antall == 0;
    }

    // Modifisert metode som legger inn en verdi i treet - original er 5.2.3 a) fra kompendiet
    public boolean leggInn(T verdi) {
        Objects.requireNonNull(verdi, "Ulovlig med nullverdier!");

        Node<T> p = rot, q = null;               // p starter i roten
        int cmp = 0;                             // hjelpevariabel

        while (p != null)       // fortsetter til p er ute av treet
        {
            q = p;                                 // q er forelder til p
            cmp = comp.compare(verdi,p.verdi);     // bruker komparatoren
            p = cmp < 0 ? p.venstre : p.høyre;     // flytter p
        }

        // p er nå null, dvs. ute av treet, q er den siste vi passerte
        p = new Node<>(verdi, q);                // oppretter en ny node med foreldrereferanse

        if (q == null) rot = p;                  // p blir rotnode
        else if (cmp < 0) q.venstre = p;         // venstre barn til q
        else q.høyre = p;                        // høyre barn til q

        antall++;                                // én verdi mer i treet
        return true;                             // vellykket innlegging
    }

    public boolean fjern(T verdi) {
        throw new UnsupportedOperationException("Ikke kodet ennå!");
    }

    public int fjernAlle(T verdi) {
        throw new UnsupportedOperationException("Ikke kodet ennå!");
    }

    // Metode som returnerer antall forekomster av gitt verdi i treet
    public int antall(T verdi) {
        Node<T> p = rot; // Starter i roten
        int antall = 0; // Hjelpevariabel

        // Fortsetter sammenligningen til p er ute av treet (null)
        while(p != null){
            // Sammenligner gitt verdi med nåværende p sin verdi
            int cmp = comp.compare(verdi,p.verdi);

            /* Hvis gitt verdi er mindre enn p sin verdi, går vi til venstre
             * Ellers er verdien større eller lik og vi går vi til høyre */
            p = cmp < 0 ? p.venstre : p.høyre;

            // Ekstra sjekk om den faktisk er lik, øker isåfall antallet med 1
            if(cmp == 0) antall++;
        }
        return antall; // Returnerer antall forekomster av gitt verdi
    }

    public void nullstill() {
        throw new UnsupportedOperationException("Ikke kodet ennå!");
    }

    // Metode som finner første node i postorden
    private static <T> Node<T> førstePostorden(Node<T> p) {
        // Fortsetter så lenge p har barn
        while(p.venstre != null || p.høyre != null) {
            // Setter p til sitt venstrebarn
            // Ellers settes p til sitt høyrebarn
            p = p.venstre != null ? p.venstre : p.høyre;
        }
        return p; // Returnerer første node i postorden
    }

    // Metode som returnerer den neste i postorden
    private static <T> Node<T> nestePostorden(Node<T> p) {
        // Referanse til foreldernoden f
        Node<T> f = p.forelder;

        // Tilfelle 1:
        // Hvis p ikke har en forelder (p er rotnoden), returneres null
        if (f == null) return null;

        // Tilfelle 2:
        /* Hvis foreldrenoden f ikke har et høyrebarn || p er et høyrebarn
         * så er forelderen f neste i postorden */
        if(f.høyre == null || f.høyre == p) return f;

        // Tilfelle 3:
        /* Ellers har foreldrenoden f to barn, og den neste i postorden er
         * den noden som kommer først i subtreet med f.høyre som rot */
        else {
            // Kaller derfor på førstePostorden på det nye subtreet
            return førstePostorden(f.høyre);
        }
    }

    // Metode som utfører en "oppgave" og skriver ut treet i postorden
    public void postorden(Oppgave<? super T> oppgave) {
        // Setter p til å være den første i postorden
        Node<T> p = førstePostorden(rot);

        // Fortsetter til p er ute av treet (null)
        while(p != null){
            oppgave.utførOppgave(p.verdi); // Utfører oppgave (skrive p sin verdi)
            p = nestePostorden(p); // Setter p til den neste noden i postorden
        }
    }

    public void postordenRecursive(Oppgave<? super T> oppgave) {
        postordenRecursive(rot, oppgave);
    }

    // Metode som rekursivt utfører en "oppgave" og skriver ut treet i postorden
    private void postordenRecursive(Node<T> p, Oppgave<? super T> oppgave) {
        // Sjekker venstre subtre
        if(p.venstre != null){
            postordenRecursive(p.venstre,oppgave);
        }
        // Sjekker høyre subtre
        if (p.høyre != null){
            postordenRecursive(p.høyre,oppgave);
        }
        // Utfører oppgaven med å skrive ut når p ikke har flere barn (begge if-testene gir false)
        oppgave.utførOppgave(p.verdi); // Skriver ut etter if-testene for å få det i postorden
    }

    public ArrayList<T> serialize() {
        throw new UnsupportedOperationException("Ikke kodet ennå!");
    }

    static <K> SBinTre<K> deserialize(ArrayList<K> data, Comparator<? super K> c) {
        throw new UnsupportedOperationException("Ikke kodet ennå!");
    }


} // ObligSBinTre
