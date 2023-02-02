# Obligatorisk oppgave 3 i Algoritmer og Datastrukturer

Denne oppgaven er en innlevering i Algoritmer og Datastrukturer. 
Oppgaven er levert av følgende student:
* Kevin Amundsen, S364530, s364530@oslomet.no

# Oppgavebeskrivelse

I oppgave 1 så gikk jeg frem ved å endre litt i programkoden 5.2.3 a) fra kompendiet.
Der hvor den nye noden blir opprettet, altså p = new Node<>(verdi), la jeg inn en referanse til foreldrenoden
i parameteren slik at noden blir opprettet på riktig måte med en foreldrereferanse: p = new Node<>(verdi, **q**);

I oppgave 2 så brukte jeg en hjelpevariabel til å holde på antall forekomster av den gitte verdien. Deretter brukte
jeg sammenligningen comp.compare(verdi,p.verdi) som enten gir -1, 0 eller 1 til å sammenligne
den gitte verdien med verdiene i treet. Og avhengig om den gitte verdien er større (eller lik) eller mindre, 
traverserer man treet til høyre eller venstre henholdsvis. Dersom den gitte verdien er den samme (compare gir 0),
økes antallet med 1.

I oppgave 3a brukte jeg en while-løkke som fortsetter helt til noden p ikke lenger har barn, altså at p.venstre
og p.høyre er _null_. Inne i while-løkken blir p satt til sitt venstrebarn dersom den finnes.
Hvis ikke, settes p til høyrebarnet. Til slutt blir første node i postorden returnert. 

I oppgave 3b er det tre tilfeller knyttet til det å finne neste node i postorden. I det første tilfellet, brukte jeg en 
if-test til å returnere _null_ dersom p ikke har en forelder, altså at p er rotnoden. I det andre tilfellet sjekkes
det om p er et enebarn på venstresiden eller på høyresiden. Hvis det er tilfellet, er forelderen
f den neste i postorden. I det tredje tilfellet har foreldrenoden to barn. Her bruker jeg metoden førstePostorden()
med f sin høyre som parameter for å finne neste postorden i det nye subtreet (f.høyre som rot).

I oppgave 4a satte jeg en node p til å være den første i postorden. Deretter bruker jeg en while-løkke til å traverse
treet helt til p blir _null_ (ute av treet). Inne i løkken blir en oppgave utført for hver iterasjon og p
blir satt til den neste i postorden ved hjelp av nestePostorden(p).

I oppgave 4b sjekkes venstre subtre og metoden fortsetter å kalle på seg selv til p ikke har noen venstrebarn igjen.
Når p ikke har noen venstrebarn igjen, sjekkes høyresiden. Dersom det heller ikke er noen barn der, er begge if-testene
usanne og oppgaven blir utført- noden skrives ut. Deretter går programmet tilbake i programstakken og fortsetter
med høyre subtre. Metoden kaller seg selv til den finner noe på venstresiden av dette høyre subtreet. Når den har
gjort seg ferdig der, fortsetter den på høyresiden. Dersom det ikke er noe mer på høyresiden, går den videre oppover
i treet og sånn fortsetter det. Til slutt får man treet skrevet ut i postorden.
