package com.example.myproject;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MyuserAdapter extends RecyclerView.Adapter<MyuserAdapter.myViewHolder> {

        private Context context;
        private List<Users> list;
        private DatabaseReference UdatabaseReference;
        private FirebaseDatabase firebaseDatabase;

        public MyuserAdapter(Context context, List<Users> list, DatabaseReference udatabaseReference) {
            this.context = context;
            this.list = list;
            UdatabaseReference = udatabaseReference;
        }

        @NonNull
        @Override
        public MyuserAdapter.myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.useritem,parent,false);
            return new MyuserAdapter.myViewHolder(v);
        }

        @SuppressLint("SetTextI18n")
        @Override
        public void onBindViewHolder(@NonNull MyuserAdapter.myViewHolder holder, int position) {
            Users users = list.get(position);

            holder.Contact.setText("Contact: "+users.getContact_no());
            holder.Email.setText("Email: "+users.getEmail());
            holder.Name.setText("Name: "+ users.getName());

        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        public static class myViewHolder extends RecyclerView.ViewHolder{

            TextView Name,Email,Contact,Block;


            public myViewHolder(@NonNull View itemView) {
                super(itemView);
                Name = itemView.findViewById(R.id.user_name);
                Email = itemView.findViewById(R.id.user_email);
                Contact = itemView.findViewById(R.id.user_contact);
                //Block = itemView.findViewById(R.id.Delete_button);

            }
        }

}
