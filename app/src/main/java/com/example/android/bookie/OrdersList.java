package com.example.android.bookie;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.android.bookie.Adapter.WordAdapter;
import com.example.android.bookie.Model.Word;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class OrdersList extends AppCompatActivity {


    DatabaseReference databaseCart;

    Button remove;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orders_list);


        databaseCart = FirebaseDatabase.getInstance().getReference("AddedToCart");

        ArrayList<Word> bookInCart = new ArrayList<>();


        remove = (Button)findViewById(R.id.list_item_remove);

        WordAdapter itemsAdapter = new WordAdapter(OrdersList.this,bookInCart);
        ListView listview = (ListView) findViewById(R.id.list);
        TextView empty = findViewById(R.id.emptyText);
        listview.setEmptyView(empty);
        listview.setAdapter(itemsAdapter);


        databaseCart.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot cartSnapshot : snapshot.getChildren()){



                    Word word = new Word();
                    word.setImage(cartSnapshot.child("BookDetails").child("image").getValue().toString());
                    word.setName(cartSnapshot.child("BookDetails").child("name").getValue().toString());
                    word.setAuthor(cartSnapshot.child("BookDetails").child("author").getValue().toString());
                    word.setPrice(Integer.parseInt(cartSnapshot.child("BookDetails").child("price").getValue().toString()));

                    bookInCart.add(word);
                    itemsAdapter.notifyDataSetChanged();
                }

                WordAdapter itemsAdapter = new WordAdapter(OrdersList.this,bookInCart);
                ListView listview = (ListView) findViewById(R.id.list);
                listview.setAdapter((ListAdapter) itemsAdapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



    }



    }
