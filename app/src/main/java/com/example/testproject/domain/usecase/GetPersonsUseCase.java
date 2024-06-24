    package com.example.testproject.domain.usecase;

    import androidx.lifecycle.LiveData;

    import com.example.testproject.data.model.Person;
    import com.example.testproject.domain.repository.PersonRepository;

    import java.util.List;

    import javax.inject.Inject;

    public class GetPersonsUseCase {
        private final PersonRepository repository;

        @Inject
        public GetPersonsUseCase(PersonRepository repository) {
            this.repository = repository;
        }

        public LiveData<List<Person>> invoke() {
            return repository.getPersons();
        }

        public void refreshPersons() {
            repository.refreshPersons();
        }

        public void addMorePersons() {
            repository.addMorePerson();
        }


    }
