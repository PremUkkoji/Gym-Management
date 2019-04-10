package com.premukkoji.fitnessarena;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class CustomerList extends AppCompatActivity {

    RecyclerView customerList;
    CustomerListAdapter customerListAdapter;
    FloatingActionButton fab;
    ArrayList<User> users;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_list);

        users = new ArrayList<>();
        customerList = findViewById(R.id.customerList);
        customerList.setLayoutManager(new LinearLayoutManager(this));
        customerListAdapter = new CustomerListAdapter(getApplicationContext(), users);
        customerList.setAdapter(customerListAdapter);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference rootRef = database.getReference();
        DatabaseReference usersRef = rootRef.child("users");

        ValueEventListener eventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                users.clear();
                for(DataSnapshot ds : dataSnapshot.getChildren()) {
                    User user = ds.getValue(User.class);
                    Log.d("name: ", user.getName());
                    users.add(user);
                }
                customerListAdapter.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d("The read failed: ", String.valueOf(databaseError.getCode()));
            }
        };
        usersRef.orderByChild("name").addValueEventListener(eventListener);

        fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CustomerList.this, AddCustomer.class);
                startActivity(intent);
            }
        });
    }
}
