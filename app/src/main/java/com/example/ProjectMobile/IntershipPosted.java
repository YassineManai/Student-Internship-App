package com.example.ProjectMobile;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ProjectMobile.Model.Data;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class IntershipPosted extends AppCompatActivity {

    private FloatingActionButton fabBtn;
    private Toolbar toolbar;

    //Recycler View

    private RecyclerView recyclerView;

    //Firebase

    private FirebaseAuth mAuth;
    private DatabaseReference JobPostDatabase;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intership_posted);

        toolbar = findViewById(R.id.toolbar_post_job);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("InterShip Posted");


        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        fabBtn = findViewById(R.id.fab_add);


        mAuth = FirebaseAuth.getInstance();

        FirebaseUser mUser = mAuth.getCurrentUser();
        String uId = mUser.getUid();

        JobPostDatabase = FirebaseDatabase.getInstance().getReference().child("InterShop Post").child(uId);


        recyclerView = findViewById(R.id.recycler_job_post_id);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setStackFromEnd(true);
        layoutManager.setReverseLayout(true);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);

        fabBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(getApplicationContext(), InsertIntershipPost.class));

            }
        });

    }


    @Override
    protected void onStart(){
        super.onStart();

        FirebaseRecyclerOptions<Data> firebaseRecyclerOptions =
                new FirebaseRecyclerOptions.Builder<Data>()
                .setQuery(JobPostDatabase, Data.class)
                .build();


        FirebaseRecyclerAdapter<Data, MyViewHolder> adapter = new FirebaseRecyclerAdapter<Data, MyViewHolder>(firebaseRecyclerOptions) {

            @NonNull
            @Override
            public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.intership_post_item,parent,false);
                return new MyViewHolder(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull MyViewHolder viewHolder, int i, @NonNull Data model) {

                viewHolder.setDomain(model.getDomain());
                viewHolder.setJobDate(model.getDate());
                viewHolder.setLevel(model.getLevel());
                viewHolder.setlocation(model.getLocation());
                viewHolder.setDuration(model.getDuration());
                viewHolder.setContact(model.getContact());
            }

        };
        adapter.startListening();
        recyclerView.setAdapter(adapter);


    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.mainmenu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){
            case R.id.logout:
                mAuth.signOut();
                startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                break;


        }

        return super.onOptionsItemSelected(item);
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        View myview;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            myview = itemView;
        }

        public void setDomain(String Domain){

            TextView mDomain = myview.findViewById(R.id.i_Domain);
            mDomain.setText("   "+Domain);

        }

        public void setJobDate(String date){
            TextView mDate = myview.findViewById(R.id.i_Date);
            mDate.setText("   "+date);
        }

        public void setLevel(String Level){
            TextView mLevel = myview.findViewById(R.id.i_levelneeded);
            mLevel.setText("   "+Level);
        }

        public void setlocation(String Location){
            TextView mLocation = myview.findViewById(R.id.i_location);
            mLocation.setText("   "+Location);
        }

        public void setDuration(String Duration){
            TextView mDuration = myview.findViewById(R.id.i_Duration);
            mDuration.setText("   "+Duration);
        }
        public void setContact(String Contact){
            TextView mSalary = myview.findViewById(R.id.i_Contact);
            mSalary.setText("   "+Contact);
        }



    }



}