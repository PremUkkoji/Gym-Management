package com.premukkoji.fitnessarena;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class CustomerListAdapter extends RecyclerView.Adapter<CustomerListAdapter.CustomerListViewHolder> {

    private ArrayList<User> users;
    private Context context;

    public CustomerListAdapter(Context context, ArrayList<User> users)
    {
        this.context = context;
        this.users = users;
    }

    @Override
    public CustomerListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.customer_list_item, parent, false);
        return new CustomerListViewHolder(view);
    }

    public void onBindViewHolder(CustomerListViewHolder holder, final int position) {
        String customerName = users.get(position).getName();
        holder.name.setText(customerName);
        Glide.with(context).load(users.get(position).getImage_url()).into(holder.picture);

        holder.remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseDatabase.getInstance().getReference().child("users").child(users.get(position).getId()).removeValue();
                Snackbar.make(view, "User removed successfully !!!", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ViewEdit.class);
                String name = users.get(position).getName();
                int age = users.get(position).getAge();
                String dob = users.get(position).getDob();
                int height = users.get(position).getHeight();
                int weight = users.get(position).getWeight();
                String gender = users.get(position).getGender();
                int chest = users.get(position).getChest();
                int biceps = users.get(position).getBiceps();
                int arms = users.get(position).getArms();
                int legs = users.get(position).getLegs();
                int stomach = users.get(position).getStomach();

                intent.putExtra("id", users.get(position).getId());
                intent.putExtra("name", name);
                intent.putExtra("age", age);
                intent.putExtra("dob", dob);
                intent.putExtra("height", height);
                intent.putExtra("weight", weight);
                intent.putExtra("gender", gender);
                intent.putExtra("chest", chest);
                intent.putExtra("biceps", biceps);
                intent.putExtra("arms", arms);
                intent.putExtra("legs", legs);
                intent.putExtra("stomach", stomach);

                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    public class CustomerListViewHolder extends RecyclerView.ViewHolder {

        TextView name;
        Button remove;
        ImageView picture;

        public CustomerListViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.customer_name);
            remove = itemView.findViewById(R.id.customer_remove);
            picture = itemView.findViewById(R.id.customer_pic);
        }
    }
}
