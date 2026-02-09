
NavController on  hallintakomponentti. Se pitää kirjaa navigaatiohistoriasta ja mahdollistaa siirtymisen näkymien välillä. 

NavHost on container-komponentti, joka näyttää kulloinkin aktiivisen näkymän. Se määrittelee kaikki sovelluksen reitit ja yhdistää ne Composable-funktioihin.


Sovelluksessa on kolme reittiä:
- home → HomeScreen (tehtävälista)
- calendar  CalendarScreen (kalenterinäkymä)
- settings  SettingsScreen (asetukset)


Model: Task data class (id, title, description, dueDate, isDone)

ViewModel: TaskViewModel käyttää StateFlow-tilaa tehtävien hallintaan. ViewModel sisältää logiikan tehtävien lisäämiseen, päivittämiseen ja poistamiseen.

View: Jetpack Compose -käyttöliittymä (HomeScreen, CalendarScreen, SettingsScreen)


MVVM-malli on hyvä koska se:
- Erottaa UI:n ja logiikan toisistaan
- Helpottaa testattavuutta
- Mahdollistaa tilan jakamisen näkymien välillä

StateFlow pitää tilaa reaktiivisena. Jos tila muuttuu, UI päivittyy automaattisesti.
Näkymässä jokainen päivämäärä näytetään otsikkona ja sen alla kyseisen päivän tehtävät. Tehtävät järjestetään päivämäärän mukaan nousevaan järjestykseen.
Käyttäjä näkee selkeästi minkä päivän tehtävä on kyseessä ja voi muokata tehtäviä suoraan kalenterinäkymästä.

AlertDialog hoitaa sekä uuden tehtävän lisäämisen että olemassaolevan muokkaamisen. Dialog ei ole navigaatiokohta vaan UI-komponentti joka näytetään näkymän päällä.
## Reaktiiviset toiminnot

- addTask() - Lisää uusi tehtävä
- toggleTaskDone() - Merkitse tehtävä tehdyksi/kesken
- removeTask() - Poista tehtävä
- updateTask() - Päivitä tehtävän tiedot
- filterByDone() - Filtteröi tehtäviä
- sortByDueDate() - Järjestä päivämäärän mukaan


HomeScreen - Näyttää tehtävälistan filttereineen ja lajittelumahdollisuudella

CalendarScreen - Näyttää tehtävät kalenterimaisesti päivämäärän mukaan ryhmiteltynä

SettingsScreen - Teeman vaihto (tumma/vaalea)

DetailScreen - AlertDialog-dialogi tehtävän muokkaamiseen ja poistamiseen


