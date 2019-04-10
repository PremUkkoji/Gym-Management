package com.premukkoji.fitnessarena;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

public class AddCustomer extends AppCompatActivity {

    private EditText name, dob, gender, height, weight;
    private Button add;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_customer);

        name = findViewById(R.id.name);
        dob = findViewById(R.id.dob);
        gender = findViewById(R.id.gender);
        height = findViewById(R.id.height);
        weight = findViewById(R.id.weight);
        add = findViewById(R.id.add_customer);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String cname, cdob, cgender, cheight, cweight;
                cname = name.getText().toString();
                cdob = dob.getText().toString();
                cgender = gender.getText().toString();
                cheight = height.getText().toString();
                cweight = weight.getText().toString();

                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference root = database.getReference();
                String key =  root.push().getKey();
                DatabaseReference reference = root.child("users").child(key);

                HashMap<String, Object> user = new HashMap<>();
                user.put("id", key);
                user.put("name", cname);
                user.put("dob", cdob);

                SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                Date d = null;
                try {
                    d = dateFormat.parse(cdob);
                    Calendar cal = Calendar.getInstance();
                    cal.setTime(d);
                    int year = cal.get(Calendar.YEAR);
                    int month = cal.get(Calendar.MONTH) + 1;
                    int date = cal.get(Calendar.DATE);
                    LocalDate l1 = null;
                    if(android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                        l1 = LocalDate.of(year, month, date);
                        LocalDate now1 = LocalDate.now();
                        Period diff1 = Period.between(l1, now1);
                        user.put("age", diff1.getYears());
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                    user.put("age", 0);
                }

                user.put("gender", cgender);

                Date date = new Date();
                String joinDate = dateFormat.format(date);
                user.put("joining date", joinDate);

                user.put("height", Integer.parseInt(cheight));
                user.put("weight", Integer.parseInt(cweight));
                user.put("chest", 0);
                user.put("arms", 0);
                user.put("biceps", 0);
                user.put("legs", 0);
                user.put("stomach", 0);
                if(cgender.equals("male"))
                    user.put("image_url", "https://firebasestorage.googleapis.com/v0/b/fitnessarena-6426c.appspot.com/o/male.png?alt=media&token=329fe800-b1ea-4413-aa98-64d500dc166b");
                else
                    user.put("image_url", "https://firebasestorage.googleapis.com/v0/b/fitnessarena-6426c.appspot.com/o/female.png?alt=media&token=df82b136-98d6-4871-a17c-6147f55187cc");
                reference.setValue(user);
                Snackbar.make(view, "User added successfully !!!", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

                Intent intent = new Intent(AddCustomer.this, CustomerList.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });
    }
}
