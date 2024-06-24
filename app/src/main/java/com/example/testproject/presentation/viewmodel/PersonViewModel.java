package com.example.testproject.presentation.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.testproject.data.model.Person;
import com.example.testproject.domain.usecase.GetPersonsUseCase;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class PersonViewModel extends ViewModel {
    private final GetPersonsUseCase getPersonsUseCase;
    private final MutableLiveData<List<Person>> persons = new MutableLiveData<>();

    @Inject
    public PersonViewModel(GetPersonsUseCase getPersonsUseCase) {
        this.getPersonsUseCase = getPersonsUseCase;
    }

    // This method fetches the list of persons from the repository
    public LiveData<List<Person>> getPersons() {
        return persons;
    }

    //this method is called initial to show cached data or fetch from api calls
    public void fetchPersons() {
        getPersonsUseCase.invoke().observeForever(persons::setValue);
    }

    //This method force update the repository
    public void refreshPersons() {
        getPersonsUseCase.refreshPersons();
    }

    //this method insert more person list to the repository
    public void addMorePersons() {
        getPersonsUseCase.addMorePersons();
    }


}
