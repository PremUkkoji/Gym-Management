package com.premukkoji.fitnessarena;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class ViewEdit extends AppCompatActivity {

    private EditText name, age, dob, height, weight, gender, chest, biceps, arms, legs, stomach;
    private Button editView;
    private String id, cname, cdob, cgender;
    private int cage, cheight, cweight, cchest, cbiceps, carms, clegs, cstomach;
    boolean flag = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_edit);

        name = findViewById(R.id.name);
        age = findViewById(R.id.age);
        dob = findViewById(R.id.dob);
        height = findViewById(R.id.height);
        weight = findViewById(R.id.weight);
        gender = findViewById(R.id.gender);
        chest = findViewById(R.id.chest);
        biceps = findViewById(R.id.biceps);
        arms = findViewById(R.id.arms);
        legs = findViewById(R.id.legs);
        stomach = findViewById(R.id.stomach);
        editView = findViewById(R.id.edit_view);

        Intent intent = getIntent();
        id = intent.getStringExtra("id");
        cname = intent.getStringExtra("name");
        cdob = intent.getStringExtra("dob");
        cage = intent.getIntExtra("age", -1);
        cheight = intent.getIntExtra("height", -1);
        cweight = intent.getIntExtra("weight", -1);
        cgender = intent.getStringExtra("gender");
        cchest = intent.getIntExtra("chest", -1);
        cbiceps = intent.getIntExtra("biceps", -1);
        carms = intent.getIntExtra("arms", -1);
        clegs = intent.getIntExtra("legs", -1);
        cstomach = intent.getIntExtra("stomach", -1);

        name.setText(cname);
        age.setText(String.valueOf(cage));
        dob.setText(cdob);
        height.setText(String.valueOf(cheight));
        weight.setText(String.valueOf(cweight));
        gender.setText(cgender);
        chest.setText(String.valueOf(cchest));
        biceps.setText(String.valueOf(cbiceps));
        arms.setText(String.valueOf(carms));
        stomach.setText(String.valueOf(clegs));
        legs.setText(String.valueOf(cstomach));

        editView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                HashMap<String, Object> user = new HashMap<>();

                if(!cname.equals(name.getText().toString())){
                    user.put("name", name.getText().toString());
                    flag = true;
                }
                if(!cdob.equals(dob.getText().toString())){
                    user.put("dob", dob.getText().toString());
                    flag = true;
                }
                if(cage != Integer.parseInt(age.getText().toString())){
                    user.put("age", Integer.parseInt(age.getText().toString()));
                    flag = true;
                }
                if(cheight != Integer.parseInt(height.getText().toString())){
                    user.put("height", Integer.parseInt(height.getText().toString()));
                    flag = true;
                }
                if(cweight != Integer.parseInt(weight.getText().toString())){
                    user.put("weight", Integer.parseInt(weight.getText().toString()));
                    flag = true;
                }
                if(cchest != Integer.parseInt(chest.getText().toString())){
                    user.put("chest", Integer.parseInt(chest.getText().toString()));
                    flag = true;
                }
                if(cbiceps != Integer.parseInt(biceps.getText().toString())){
                    user.put("biceps", biceps.getText().toString());
                    flag = true;
                }
                if(carms != Integer.parseInt(arms.getText().toString())){
                    user.put("arms", Integer.parseInt(arms.getText().toString()));
                    flag = true;
                }
                if(clegs != Integer.parseInt(legs.getText().toString())){
                    user.put("legs", Integer.parseInt(legs.getText().toString()));
                    flag = true;
                }
                if(!cgender.equals(gender.getText().toString())){
                    if(gender.getText().toString().equals("male"))
                    {
                        user.put("gender", "male");
                        user.put("image_url", "https://firebasestorage.googleapis.com/v0/b/fitnessarena-6426c.appspot.com/o/male.png?alt=media&token=329fe800-b1ea-4413-aa98-64d500dc166b");
                    }
                    else
                    {
                        user.put("gender", "female");
                        user.put("image_url", "https://firebasestorage.googleapis.com/v0/b/fitnessarena-6426c.appspot.com/o/female.png?alt=media&token=df82b136-98d6-4871-a17c-6147f55187cc");
                    }
                    flag = true;
                }
                if(cstomach != Integer.parseInt(stomach.getText().toString())){
                    user.put("stomach", Integer.parseInt(stomach.getText().toString()));
                    flag = true;
                }

                if(flag) {
                    FirebaseDatabase.getInstance().getReference().child("users").child(id).updateChildren(user);
                    Snackbar.make(view, "User details updated !!!", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
                else
                {
                    Snackbar.make(view, "You didn't make any changes!!!", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }

                Intent intent = new Intent(ViewEdit.this, CustomerList.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });

    }
}
