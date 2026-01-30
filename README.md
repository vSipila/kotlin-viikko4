 mvvm-arkkitehtuuri

Model: Task-data-luokka (id, title, description, priority, dueDate)

ViewModel: Käyttää StateFlow/ViewModelState<List<Task>> tilan hallintaan

View: Jetpack Compose -käyttöliittymä

mvvm malli on hyvä koska se erottaa ui:n ja logiigan sekä helpottaa testattavuutta.

stateflow pitää tilaa tilaa "stateflow" jos ui:n tulee muutoksia niin se päivittyy automaattisesti.

reaktiiviset toiminnot:

addTask() - Lisää uusi tehtävä

toggleDone() - Merkitse tehtävä tehdyksi/kesken

removeTask() - Poista tehtävä

updateTask() - Päivitä tehtävän tiedot

käyttöliittymä:

HomeScreen - Näyttää tehtävälistan

DetailScreen - Dialogi tehtävän muokkaamiseen ja poistamiseen
