package com.danieloloan.pbp_uts.Activity;

import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;
import com.danieloloan.pbp_uts.Booked;
import com.danieloloan.pbp_uts.BookedDatabaseClient;
import com.danieloloan.pbp_uts.R;
import com.google.android.material.textfield.TextInputEditText;

public class Tambah_pesanan extends Fragment {

    TextInputEditText addName, addAddress;
    String addMobil, addLamaSewa, addHarga;
    int HargaByMobil,hitung;
    Button addBtn, cancelBtn;
    Booked booked;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public Tambah_pesanan() {
        // Required empty public constructor
    }

    public static Tambah_pesanan newInstance(String param1, String param2) {
        Tambah_pesanan fragment = new Tambah_pesanan();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tambah_pesanan, container,false);
        booked = (Booked) getArguments().getSerializable("booked");
        addName = view.findViewById(R.id.addNama);
        addAddress = view.findViewById(R.id.addAddress);
        addBtn = view.findViewById(R.id.add_booking);
        cancelBtn = view.findViewById(R.id.cancel_addBooking);
        AutoCompleteTextView mobil,hari;
        mobil = view.findViewById(R.id.dropDownMobil);
        final String[] Mobil = new String[]{
                "Avanza", "Xpander", "Fortuner", "Ertiga", "HRV", "CRV", "Calya", "Pajero", "Outlander"
        };
        final ArrayAdapter<String> adapter_mobil = new ArrayAdapter<>(getActivity(), R.layout.dropdown, Mobil);
        mobil.setAdapter(adapter_mobil);
        mobil.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String mobil = adapterView.getItemAtPosition(i).toString();
                addMobil = mobil;
                if (addMobil.equals("Avanza")) {
                    HargaByMobil = 120000;
                }else if (addMobil.equals("Xpander")){
                    HargaByMobil = 280000;
                }else if (addMobil.equals("Fortuner")){
                    HargaByMobil = 250000;
                }else if (addMobil.equals("Ertiga")){
                    HargaByMobil = 150000;
                }else if (addMobil.equals("HRV")){
                    HargaByMobil = 200000;
                }else if (addMobil.equals("CRV")){
                    HargaByMobil = 220000;
                }else if (addMobil.equals("Calya")){
                    HargaByMobil = 150000;
                }else if (addMobil.equals("Pajero")){
                    HargaByMobil = 280000;
                }else if (addMobil.equals("Outlander")){
                    HargaByMobil = 275000;
                }
            }
        });

        hari = view.findViewById(R.id.dropDownHari);
        final Integer[] Hari = new Integer[]{
                1, 2, 3, 7, 14
        };
        final ArrayAdapter<Integer> adapter_hari = new ArrayAdapter<>(getActivity(), R.layout.dropdown, Hari);
        hari.setAdapter(adapter_hari);
        hari.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String hari = adapterView.getItemAtPosition(i).toString();
                addLamaSewa = hari;
                if (addLamaSewa.equals("1")) {
                    hitung = 1 *  HargaByMobil ;
                }else if (addLamaSewa.equals("2")){
                    hitung = 2 * HargaByMobil;
                }else if (addLamaSewa.equals("3")){
                    hitung = 3 * HargaByMobil;
                }else if (addLamaSewa.equals("7")){
                    hitung = 7 * HargaByMobil;
                }else if (addLamaSewa.equals("14")) {
                    hitung = 14 * HargaByMobil;
                }
                 addHarga = String.valueOf(hitung);
            }
        });

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    AddPesanan(booked);
//                }
            }
        });

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.hide(Tambah_pesanan.this).commit();
            }
        });
    }

    public void AddPesanan(final Booked booked){
        final String nama= addName.getText().toString();
        final String address = addAddress.getText().toString();
        final String namamobil = addMobil;
        final String lamasewa = addLamaSewa;
        final String harga = addHarga;

        class addBooked extends AsyncTask<Void, Void, Void> {

            @Override
            protected Void doInBackground(Void... voids) {
                Booked booked = new Booked();
                booked.setSimpan_nama(nama);
                booked.setSimpan_address(address);
                booked.setSimpan_namaMobil(namamobil);
                booked.setStringSimpan_lamaSewa(lamasewa);
                booked.setStringSimpan_harga(harga);
                BookedDatabaseClient.getInstance(getActivity().getApplicationContext()).getDatabase()
                        .bookedDao()
                        .insert(booked);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                Toast.makeText(getActivity().getApplicationContext(), "Booking Added, " + "Harga : " + addHarga, Toast.LENGTH_SHORT).show();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.hide(Tambah_pesanan.this).commit();
            }
        }

        addBooked add = new addBooked();
        add.execute();
    }
}