package com.example.testproject.domain.repository;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.testproject.data.api.RandomPersonApi;
import com.example.testproject.data.dao.RandomPersonDao;
import com.example.testproject.data.model.Person;
import com.example.testproject.data.model.RandomPersonResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.List;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

import javax.inject.Inject;

public class PersonRepository {
    private final RandomPersonApi randomUserApi;
    private final RandomPersonDao personDao;

    @Inject
    public PersonRepository(RandomPersonApi randomUserApi, RandomPersonDao randomPersonDao) {
        this.randomUserApi = randomUserApi;
        this.personDao = randomPersonDao;
    }

    //this method do http call when cached data is empty or else get data from local database
    public LiveData<List<Person>> getPersons() {
        MutableLiveData<List<Person>> personsLiveData = new MutableLiveData<>();
        personDao.getAllPersons().observeForever(cachedPersons -> {
            if (cachedPersons != null && !cachedPersons.isEmpty()) {
                Log.d("db fetching", "db fetching");
                personsLiveData.postValue(cachedPersons);
            } else {
                Log.d("http fetching", "http fetching");
                randomUserApi.getPersons().enqueue(new Callback<RandomPersonResponse>() {

                    @Override
                    public void onResponse(Call<RandomPersonResponse> call, Response<RandomPersonResponse> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            Log.d("success call", response.body().toString());
                            List<Person> persons = response.body().getResults().stream().map(result -> {
                                Person person = new Person();
                                person.setFirstName(result.getName().getFirst());
                                person.setLastName(result.getName().getLast());
                                person.setBirthday(result.getDob().getDate());
                                person.setAge(result.getDob().getAge());
                                person.setEmail(result.getEmail());
                                person.setPhone(result.getPhone());
                                person.setAddress(result.getLocation().getStreet().getName() + ", " + result.getLocation().getCity());
                                person.setContactPerson(result.getName().getFirst() + " " + result.getName().getLast());
                                person.setContactPhone(result.getCell());
                                return person;
                            }).collect(Collectors.toList());
                            Log.d("saving data", "saving data");
                            Executors.newSingleThreadExecutor().execute(() -> {
                                personDao.insertPersons(persons);
                                personsLiveData.postValue(persons);
                            });
                        } else {
                            Log.d("fail call", "fail call");
                        }
                    }

                    @Override
                    public void onFailure(Call<RandomPersonResponse> call, Throwable t) {
                        Log.d("fail call", "fail call");
                    }
                });
            }
        });
        return personsLiveData;
    }

    //this method delete the cached data and update the repository
    public void refreshPersons() {
        MutableLiveData<List<Person>> personsLiveData = new MutableLiveData<>();
        randomUserApi.getPersons().enqueue(new Callback<RandomPersonResponse>() {
            @Override
            public void onResponse(Call<RandomPersonResponse> call, Response<RandomPersonResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Person> persons = response.body().getResults().stream().map(result -> {
                        Person person = new Person();
                        person.setFirstName(result.getName().getFirst());
                        person.setLastName(result.getName().getLast());
                        person.setBirthday(result.getDob().getDate());
                        person.setAge(result.getDob().getAge());
                        person.setEmail(result.getEmail());
                        person.setPhone(result.getPhone());
                        person.setAddress(result.getLocation().getStreet().getName() + ", " + result.getLocation().getCity());
                        person.setContactPerson(result.getName().getFirst() + " " + result.getName().getLast());
                        person.setContactPhone(result.getCell());
                        return person;
                    }).collect(Collectors.toList());
                    Executors.newSingleThreadExecutor().execute(() -> {
                        personDao.deleteAllPersons();
                        personDao.insertPersons(persons);
                    });
                }
            }

            @Override
            public void onFailure(Call<RandomPersonResponse> call, Throwable t) {
                // Handle error
            }
        });
    }

    //this method update the repository after a successful api call
    public void addMorePerson() {
        MutableLiveData<List<Person>> personsLiveData = new MutableLiveData<>();
        randomUserApi.getPersons().enqueue(new Callback<RandomPersonResponse>() {
            @Override
            public void onResponse(Call<RandomPersonResponse> call, Response<RandomPersonResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Person> persons = response.body().getResults().stream().map(result -> {
                        Person person = new Person();
                        person.setFirstName(result.getName().getFirst());
                        person.setLastName(result.getName().getLast());
                        person.setBirthday(result.getDob().getDate());
                        person.setAge(result.getDob().getAge());
                        person.setEmail(result.getEmail());
                        person.setPhone(result.getPhone());
                        person.setAddress(result.getLocation().getStreet().getName() + ", " + result.getLocation().getCity());
                        person.setContactPerson(result.getName().getFirst() + " " + result.getName().getLast());
                        person.setContactPhone(result.getCell());
                        return person;
                    }).collect(Collectors.toList());
                    Executors.newSingleThreadExecutor().execute(() -> {
                        Log.d("update data", "update data");
                        personDao.insertPersons(persons);
                    });
                }
            }

            @Override
            public void onFailure(Call<RandomPersonResponse> call, Throwable t) {
               Log.d("error occured", t.toString());
            }
        });
    }
}
