
Composessa tilanhallinta mahdollistaa UI:n automaattisen päivittymisen tilan muuttuessa. Kun sovelluksen tila muuttuu, Compose tunnistaa muutokset ja uudelleenpiirtää vain ne UI-komponentit, jotka riippuvat muuttuneesta tilasta.



remember-funktio
- Säilyttää tilan vain yhden Composable-funktion sisällä
- Tila katoaa kun komponentti poistuu näytöltä
- Sopii yksinkertaisiin, paikallisiin tiloihin

ViewModel
- Säilyttää tilan elinkaaren aikana (esim. konfiguraatiomuutokset)
- Sovelluslogiikka erillään UI:sta
- Mahdollistaa tilan jakamisen useiden komponenttien kesken
- Parempi arkkitehtuuri ja testattavuus

Tämän viikon tehtävässä tehtävälista on siirretty remember-muuttujasta TaskViewModel luokkaan, mikä mahdollistaa paremman skaalautuvuuden ja sovelluslogiikan eriyttämisen.



- Tehtävien listaaminen LazyColumn-komponentilla
- Uusien tehtävien lisääminen
- ehtävien merkitseminen suoritetuksi/tekemättömäksi
- Tehtävien poistaminen
- Suodatus suoritettujen/tekemättömien tehtävien mukaan
- Järjestäminen määräajan mukaan
- Kaikki toiminnot käyttävät ViewModelia tilanhallintaan
