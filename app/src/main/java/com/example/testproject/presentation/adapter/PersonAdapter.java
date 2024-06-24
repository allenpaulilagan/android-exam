package com.example.testproject.presentation.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.testproject.R;
import com.example.testproject.data.model.Person;

import java.util.List;

public class PersonAdapter extends RecyclerView.Adapter<PersonAdapter.PersonViewHolder>{

    private List<Person> persons;
    private final OnItemClickListener listener;

    private final int VISIBLE_THRESHOLD = 10;
    private boolean isLoading;

    public interface OnItemClickListener {
        void onItemClick(Person person);
    }

    public PersonAdapter(List<Person> persons, OnItemClickListener listener) {
        this.persons = persons;
        this.listener = listener;
    }

    //this update the data in recycler view when the viewmodel notify of changes
    public void updateData(List<Person> persons) {
        this.persons = persons;
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public PersonViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_person, parent, false);
        return new PersonViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PersonViewHolder holder, int position) {
        Person personViewHolder = persons.get(position);
        holder.firstName.setText(personViewHolder.getFirstName());
        holder.lastName.setText(personViewHolder.getLastName());
        holder.itemView.setOnClickListener(v -> listener.onItemClick(persons.get(position)));
    }

    @Override
    public int getItemCount() {
        return persons.size();
    }


    public static class PersonViewHolder extends RecyclerView.ViewHolder {
        TextView firstName, lastName;

        public PersonViewHolder(@NonNull View itemView) {
            super(itemView);
            firstName = itemView.findViewById(R.id.text_view_first_name);
            lastName = itemView.findViewById(R.id.text_view_last_name);
        }
    }
}
