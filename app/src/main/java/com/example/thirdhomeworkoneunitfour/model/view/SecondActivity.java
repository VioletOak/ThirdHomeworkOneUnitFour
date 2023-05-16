package com.example.thirdhomeworkoneunitfour.model.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.thirdhomeworkoneunitfour.R;
import com.example.thirdhomeworkoneunitfour.model.model.Notebook;
import com.example.thirdhomeworkoneunitfour.model.viewmodel.Adapter;
import com.example.thirdhomeworkoneunitfour.model.viewmodel.DatabaseHelper;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class SecondActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private FloatingActionButton fabAdd;
    private View deleteAllNotes;
    private List<Notebook> notesList;
    private DatabaseHelper database;
    private RecyclerView.Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        recyclerView = findViewById(R.id.recycler_list);
        fabAdd = findViewById(R.id.fabAdd);
        notesList = new ArrayList<>();
        database = new DatabaseHelper(this);

        fetchAllNotes();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new Adapter(this, SecondActivity.this, notesList);
        recyclerView.setAdapter(adapter);

        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SecondActivity.this, AddNotesActivity.class));
            }
        });

        deleteAllNotes = findViewById(R.id.deleteAllNotes);
        deleteAllNotes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseHelper deleteAllNotes = new DatabaseHelper(SecondActivity.this);
                deleteAllNotes.deleteAllNotes();
                Toast.makeText(SecondActivity.this, "Все записи удалены", Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void fetchAllNotes(){
        Cursor cursor = database.readNotes();
        if (cursor.getCount() == 0) {
            Toast.makeText(this, "Заметок нет", Toast.LENGTH_SHORT).show();
        } else {
            while (cursor.moveToNext()){
                notesList.add(new Notebook(cursor.getString(0), cursor.getString(1), cursor.getString(2)));
            }
        }
    }
}