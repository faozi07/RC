package konita.rc.com.rc.database;

/*
 * Created by ozi on 07/04/2018.
 */

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.telephony.SmsManager;
import android.util.Log;
import android.widget.Toast;

import konita.rc.com.rc.activity.Pln;
import konita.rc.com.rc.activity.Pulsa;
import konita.rc.com.rc.activity.Riwayat;
import konita.rc.com.rc.activity.RiwayatPln;
import konita.rc.com.rc.activity.RiwayatPulsa;
import konita.rc.com.rc.model.modTransaksi;

public class PulsaDB extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "roficell.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "transaksiPulsaPln";
    private static final String ID = "id";
    private static final String NOMOR = "nomor";
    private static final String PROVIDER = "provider";
    private static final String NOMINAL = "nominal";
    private static final String IS_PULSA = "isPulsa";
    private static final String TGL_TRANSAKSI = "tgl_transaksi";

    private Context context;
    modTransaksi modTransaksi;

    public PulsaDB(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            String sql = "CREATE TABLE " + TABLE_NAME + " (" + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + NOMOR + " TEXT null, " + PROVIDER + " TEXT null, " +
                    NOMINAL + " TEXT null, " + TGL_TRANSAKSI + " TEXT null, " + IS_PULSA + " TEXT null);";
            db.execSQL(sql);
        } catch (Exception exp) {
            exp.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public void dropTable() {
        SQLiteDatabase database = this.getWritableDatabase();
        String updateQuery = "DROP TABLE IF EXISTS " + TABLE_NAME;
        database.execSQL(updateQuery);
        database.close();
    }

    public void insertTransaksi(String nomor, String provider, String nominal, String tgl, boolean isPulsa) {
        try {
            SQLiteDatabase db = getWritableDatabase();
            String sql = "INSERT INTO " + TABLE_NAME + " (" + NOMOR + ", " + PROVIDER + ", " + NOMINAL + ", " + TGL_TRANSAKSI + ", " + IS_PULSA + ") " +
                    "VALUES ('" + nomor + "', '" + provider + "', '" + nominal + "', '" + tgl + "', '" + isPulsa + "');";
            db.execSQL(sql);
            Pulsa.isBerhasil = true;
            Pln.isBerhasil = true;
        } catch (SQLException exp) {
            exp.printStackTrace();
        } catch (Exception exp) {
            exp.printStackTrace();
        }
    }

    @SuppressWarnings("MismatchedQueryAndUpdateOfCollection")
    public void showTransaksiPulsa() {
        SQLiteDatabase db = getWritableDatabase();
        @SuppressLint("Recycle")
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
        if (cursor.getCount() > 0) {
            for (int i = 0; i < cursor.getCount(); i++) {
                cursor.moveToNext();
                modTransaksi = new modTransaksi();
                modTransaksi.setId(cursor.getInt(0));
                modTransaksi.setNoHp(cursor.getString(1));
                modTransaksi.setProvider(cursor.getString(2));
                modTransaksi.setNominal(cursor.getString(3));
                modTransaksi.setTglTrx(cursor.getString(4));
                if (cursor.getString(5).equals("true")) {
                    RiwayatPulsa.arrayRiwayat.add(modTransaksi);
                }
                Log.i("arrayRiwayat ", RiwayatPulsa.arrayRiwayat.toString());
            }
        }
        db.close();
    }
    @SuppressWarnings("MismatchedQueryAndUpdateOfCollection")
    public void showTransaksiPln() {
        SQLiteDatabase db = getWritableDatabase();
        @SuppressLint("Recycle")
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
        if (cursor.getCount() > 0) {
            for (int i = 0; i < cursor.getCount(); i++) {
                cursor.moveToNext();
                modTransaksi = new modTransaksi();
                modTransaksi.setId(cursor.getInt(0));
                modTransaksi.setNoHp(cursor.getString(1));
                modTransaksi.setProvider(cursor.getString(2));
                modTransaksi.setNominal(cursor.getString(3));
                modTransaksi.setTglTrx(cursor.getString(4));
                if (cursor.getString(5).equals("false")) {
                    RiwayatPln.arrayRiwayat.add(modTransaksi);
                }
                Log.i("arrayRiwayat ", RiwayatPln.arrayRiwayat.toString());
            }
        }
        db.close();
    }
}
