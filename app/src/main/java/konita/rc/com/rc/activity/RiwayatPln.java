package konita.rc.com.rc.activity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import konita.rc.com.rc.R;
import konita.rc.com.rc.adapter.TransaksiAdapter;
import konita.rc.com.rc.database.PulsaDB;
import konita.rc.com.rc.model.modTransaksi;

public class RiwayatPln extends Fragment {

    TransaksiAdapter plnAdapter;
    RecyclerView rvRiwayat;
    public static ArrayList<modTransaksi> arrayRiwayat = new ArrayList<>();
    PulsaDB pulsaDB;
    LinearLayoutManager llm;
    TextView teksNoData;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
        @SuppressLint("InflateParams")
        View view = inflater.inflate(R.layout.riwayat_pulsa, null);
        deklarasi(view);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Memuat data");
        progressDialog.show();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (arrayRiwayat != null && arrayRiwayat.size()>0) {
                    plnAdapter = new TransaksiAdapter(getActivity(), arrayRiwayat);
                    rvRiwayat.setAdapter(plnAdapter);
                    teksNoData.setVisibility(View.GONE);
                } else {
                    teksNoData.setVisibility(View.VISIBLE);
                }
                progressDialog.dismiss();
            }
        },2000);
    }

    @SuppressLint("SetTextI18n")
    private void deklarasi(View view) {
        arrayRiwayat.clear();
        pulsaDB = new PulsaDB(getActivity());
        pulsaDB.showTransaksiPln();
        rvRiwayat = view.findViewById(R.id.rcRiwayat);
        teksNoData = view.findViewById(R.id.teksNoData);

        teksNoData.setText("Belum ada riwayat pembelian Token PLN");
        teksNoData.setVisibility(View.GONE);
        llm = new LinearLayoutManager(getActivity());
        rvRiwayat.setLayoutManager(llm);
        rvRiwayat.setHasFixedSize(true);
    }
}
