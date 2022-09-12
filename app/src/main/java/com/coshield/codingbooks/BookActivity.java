package com.coshield.codingbooks;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class BookActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    DatabaseReference database;
    BookAdapter bookAdapter;
    ArrayList<Book> books;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book);



        recyclerView = findViewById(R.id.bookList);
        database = FirebaseDatabase.getInstance().getReference("Books");
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        books = new ArrayList<>();
        bookAdapter = new BookAdapter(this,books);
        recyclerView.setAdapter(bookAdapter);


        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){

                    Book book = dataSnapshot.getValue(Book.class);
                    books.add(book);
                }
                bookAdapter.notifyDataSetChanged();


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {


            }
        });




    }
}