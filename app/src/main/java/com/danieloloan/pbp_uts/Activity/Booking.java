package com.danieloloan.pbp_uts.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.danieloloan.pbp_uts.Booked;
import com.danieloloan.pbp_uts.BookedDatabaseClient;
import com.danieloloan.pbp_uts.BookedRecyclerViewAdapter;
import com.danieloloan.pbp_uts.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.List;

public class Booking extends AppCompatActivity {
    private Button addBtn;
    private RecyclerView recyclerView;
    private SwipeRefreshLayout refreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);
        addBtn = findViewById(R.id.add_button);
        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setSelectedItemId(R.id.navigation_booking);
        bottomNav.setOnNavigationItemSelectedListener(navListener);
        refreshLayout = findViewById(R.id.swipe_refresh);
        recyclerView = findViewById(R.id.list_pesanan_rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppCompatActivity activity = (AppCompatActivity) v.getContext();
                Bundle data = new Bundle();
                Tambah_pesanan addBooking = new Tambah_pesanan();
                addBooking.setArguments(data);
                activity.getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.layout_booking, addBooking )
                        .commit();
            }
        });
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getBooking();
                refreshLayout.setRefreshing(false);
            }
        });

        getBooking();
    }

    private void getBooking(){
        class GetPesanan extends AsyncTask<Void, Void, List<Booked>> {

            @Override
            protected List<Booked> doInBackground(Void... voids) {
                List<Booked> bookedList = BookedDatabaseClient
                        .getInstance(getApplicationContext())
                        .getDatabase()
                        .bookedDao()
                        .getAll();
                return bookedList;
            }

            @Override
            protected void onPostExecute(List<Booked> booked) {
                super.onPostExecute(booked);
                BookedRecyclerViewAdapter adapter = new BookedRecyclerViewAdapter(Booking.this, booked);
                recyclerView.setAdapter(adapter);
            }
        }

        GetPesanan get = new GetPesanan();
        get.execute();
    }
    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                    switch (menuItem.getItemId()){
                        case R.id.navigation_home:
                            startActivity(new Intent(getApplicationContext()
                                    ,MainActivity.class));
                            overridePendingTransition(0,0);
                            return true;
                        case R.id.navigation_search:
                            startActivity(new Intent(getApplicationContext()
                                    , DaftarMobil.class));
                            overridePendingTransition(0,0);
                            return true;
                        case R.id.navigation_booking:
                            startActivity(new Intent(getApplicationContext()
                                    ,Booking.class));
                            overridePendingTransition(0,0);
                            return true;
                        case R.id.navigation_profile:
                            startActivity(new Intent(getApplicationContext()
                                    , Profile.class));
                            overridePendingTransition(0,0);
                            return true;
                    }
                    return true;
                }
            };
}