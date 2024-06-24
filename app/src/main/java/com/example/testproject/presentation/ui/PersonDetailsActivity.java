package com.example.testproject.presentation.ui;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.testproject.R;
import com.example.testproject.data.model.Person;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class PersonDetailsActivity extends AppCompatActivity {

    private TextView firstNameTextView;
    private TextView lastNameTextView;
    private TextView birthdayTextView;
    private TextView ageTextView;
    private TextView emailTextView;
    private TextView mobileTextView;
    private TextView addressTextView;
    private TextView contactPersonTextView;
    private TextView contactPersonPhoneTextView;

    private Button backButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_person_details);



        firstNameTextView = findViewById(R.id.first_name_text_view);
        lastNameTextView = findViewById(R.id.last_name_text_view);
        birthdayTextView = findViewById(R.id.birthday_text_view);
        ageTextView = findViewById(R.id.age_text_view);
        emailTextView = findViewById(R.id.email_text_view);
        mobileTextView = findViewById(R.id.mobile_text_view);
        addressTextView = findViewById(R.id.address_text_view);
        contactPersonTextView = findViewById(R.id.contact_person_text_view);
        contactPersonPhoneTextView = findViewById(R.id.contact_person_phone_text_view);
        backButton = findViewById(R.id.back_button);

        backButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                finish();
            }
        });

        Person person = getIntent().getParcelableExtra("person");
        if (person != null) {
            Log.d("populate person", "populate person");
            populatePersonDetails(person);
        } else {
            Log.d("no populate", "no populate");
        }
    }

    private void populatePersonDetails(Person person) {
        firstNameTextView.setText(person.getFirstName());
        lastNameTextView.setText(person.getLastName());
        birthdayTextView.setText(formatDate(person.getBirthday()));
        ageTextView.setText(String.valueOf(person.getAge()));
        emailTextView.setText(person.getEmail());
        mobileTextView.setText(person.getPhone());
        addressTextView.setText(person.getAddress());
        contactPersonTextView.setText(person.getContactPerson());
        contactPersonPhoneTextView.setText(person.getContactPhone());
    }

    private String formatDate(String inputDate) {
        SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault());
        SimpleDateFormat outputFormat = new SimpleDateFormat("MMMM d, yyyy", Locale.getDefault());
        try {
            Date date = inputFormat.parse(inputDate);
            return outputFormat.format(date);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }
}
