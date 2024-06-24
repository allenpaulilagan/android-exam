The Project use this packages
* hilt - for dependency injection
* room - for local database
* retrofit - for http call
* okhttp - for http call
* androidx - for recyclerview and swipe refresh layout

The project;
* uses the Model-View-ViewModel architecture
* uses dependency injection via hilt package
* use the observable pattern for updating the data
* use the random user generator api for fetching data

The app initially fetches data from "https://randomuser.me". When the app is restarted, it retrieves the cached data from the local database. Pulling to refresh triggers the force update function. Additionally, whenever the user scrolls to the bottom of the page, the app fetches data from "https://randomuser.me" again and inserts the fetched data into the database, adding it to the list.
