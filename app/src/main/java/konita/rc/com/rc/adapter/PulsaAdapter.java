package konita.rc.com.rc.adapter;

/*
 * Created by ozi on 14/04/2018.
 */

import android.annotation.SuppressLint;
import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import konita.rc.com.rc.R;
import konita.rc.com.rc.database.PulsaDB;
import konita.rc.com.rc.model.modTransaksi;

public class PulsaAdapter extends RecyclerView.Adapter {

    @SuppressLint("StaticFieldLeak")
    public static Activity activity;
    public static List<modTransaksi> items;

    private final int VIEW_ITEM = 1;
    private int lastPosition = -1;
    private PulsaDB pulsaDB;

    public PulsaAdapter(Activity act, List<modTransaksi> data) {
        activity = act;
        items = data;
        pulsaDB = new PulsaDB(act);
    }

    @Override
    public int getItemViewType(int position) {
        int VIEW_PROG = 0;
        return items.get(position) != null ? VIEW_ITEM : VIEW_PROG;
    }

    public static class BrandViewHolder extends RecyclerView.ViewHolder {

        TextView tTglTrx,tNoHp,tProvider,tNominal;
        ImageView imgLogo;
        CardView cardView;

        public BrandViewHolder(View v) {
            super(v);
            tTglTrx = v.findViewById(R.id.tglTransaksi);
            tNoHp = v.findViewById(R.id.teksNomor);
            tProvider = v.findViewById(R.id.teksProvider);
            tNominal = v.findViewById(R.id.teksNominal);
            imgLogo = v.findViewById(R.id.imgLogo);
            cardView = v.findViewById(R.id.cardView);
        }
    }

    @SuppressWarnings("ConstantConditions")
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        RecyclerView.ViewHolder vh = null;
        if (viewType == VIEW_ITEM) {
            View v = LayoutInflater.from(parent.getContext()).inflate(
                    R.layout.item_riwayat, parent, false);

            vh = new PulsaAdapter.BrandViewHolder(v);
        }

        return vh;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        if (holder instanceof PulsaAdapter.BrandViewHolder) {
            try {
                modTransaksi mrt = items.get(position);
                ((PulsaAdapter.BrandViewHolder) holder).tNominal.setText(mrt.getNominal());
                ((PulsaAdapter.BrandViewHolder) holder).tTglTrx.setText(mrt.getTglTrx());
                ((PulsaAdapter.BrandViewHolder) holder).tNoHp.setText(mrt.getNoHp());
                ((PulsaAdapter.BrandViewHolder) holder).tProvider.setText(mrt.getProvider());
                if (mrt.getProvider().equals("TELKOMSEL")) {
                    ((BrandViewHolder) holder).imgLogo.setImageResource(R.drawable.ic_telkomsel);
                } else if (mrt.getProvider().equals("INDOSAT")) {
                    ((BrandViewHolder) holder).imgLogo.setImageResource(R.drawable.ic_indosat);
                } else if (mrt.getProvider().equals("XL")) {
                    ((BrandViewHolder) holder).imgLogo.setImageResource(R.drawable.ic_xl);
                } else if (mrt.getProvider().equals("AXIS")) {
                    ((BrandViewHolder) holder).imgLogo.setImageResource(R.drawable.ic_axis);
                } else if (mrt.getProvider().equals("TRI")) {
                    ((BrandViewHolder) holder).imgLogo.setImageResource(R.drawable.ic_tri);
                }
                ((PulsaAdapter.BrandViewHolder) holder).cardView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                    }
                });
            } catch (ArrayIndexOutOfBoundsException exp) {
                exp.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
