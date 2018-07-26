package konita.rc.com.rc.activity;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.Calendar;

import konita.rc.com.rc.R;
import konita.rc.com.rc.database.PulsaDB;

public class Pln extends AppCompatActivity {

    Spinner spinNominal;
    EditText editIdPelanggan;
    Button btnCancel, btnBayar;
    public static boolean isBerhasil = false, isFromMenu = false;
    public static String nominal = "", noIdPelanggan = "";
    PulsaDB pulsaDB;
    String namaHariIni = "";
    int tgl, bulan, tahun;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pln);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Pembayaran PLN");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        pulsaDB = new PulsaDB(Pln.this);
        spinNominal = (Spinner) findViewById(R.id.spinNominal);
        editIdPelanggan = (EditText) findViewById(R.id.editIdPelanggan);
        btnBayar = (Button) findViewById(R.id.btnBeli);
        btnCancel = (Button) findViewById(R.id.btnBatal);
        btnBayar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isCompleteBulanan()) {
                    permissionSms();
                } else {
                    Toast.makeText(Pln.this, "Inputan tidak boleh kosong", Toast.LENGTH_LONG).show();
                }
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        Calendar calendar = Calendar.getInstance();
        tgl = calendar.get(Calendar.DAY_OF_MONTH);
        int tglHari = calendar.get(Calendar.DAY_OF_WEEK);
        bulan = calendar.get(Calendar.MONTH) + 1;
        tahun = calendar.get(Calendar.YEAR);

        if (tglHari == Calendar.MONDAY) {
            namaHariIni = "Senin";
        } else if (tglHari == Calendar.TUESDAY) {
            namaHariIni = "Selasa";
        } else if (tglHari == Calendar.WEDNESDAY) {
            namaHariIni = "Rabu";
        } else if (tglHari == Calendar.THURSDAY) {
            namaHariIni = "Kamis";
        } else if (tglHari == Calendar.FRIDAY) {
            namaHariIni = "Jum'at";
        } else if (tglHari == Calendar.SATURDAY) {
            namaHariIni = "Sabtu";
        } else if (tglHari == Calendar.SUNDAY) {
            namaHariIni = "Minggu";
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!isFromMenu) {
            isiUlang();
        }
    }

    private void isiUlang() {
        editIdPelanggan.setText(noIdPelanggan);
        String[] arrayNominal = getResources().getStringArray(R.array.nomTokenPln);
        for (int i=0;i<arrayNominal.length;i++) {
            if (nominal.equals(arrayNominal[i])) {
                spinNominal.setSelection(i);
            }
        }
    }

    private void permissionSms() {
        if (Build.VERSION.SDK_INT > 22) {
            String permission = Manifest.permission.SEND_SMS;
            int grant = ContextCompat.checkSelfPermission(this, permission);
            if (grant != PackageManager.PERMISSION_GRANTED) {
                String[] permissionList = new String[1];
                permissionList[0] = permission;
                ActivityCompat.requestPermissions(this, permissionList, 1);
            } else {
                sendSmsByManager();
            }
        } else {
            sendSmsByManager();
        }
    }

    private boolean isCompleteBulanan() {
        return !editIdPelanggan.getText().toString().equals("") || spinNominal.getSelectedItemPosition() != 0;
    }

    public String filterDigit(String message) {
        int len = 0, i = 0;
        boolean isDigit;

        char[] data = message.toCharArray();
        while (i < data.length) {
            isDigit = Character.isDigit(data[i]);
            if (isDigit) {
                data[len] = data[i];
                len++;
            }
            i++;
        }

        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(data, 0, len);
        return stringBuffer.toString();
    }

    public void sendSmsByManager() {
        int nominals = Integer.parseInt(filterDigit(spinNominal.getSelectedItem().toString()));
        String nom = String.valueOf(nominals).substring(0,String.valueOf(nominals).length()-3);
        try {
            String nomor = editIdPelanggan.getText().toString();
            String nominal = spinNominal.getSelectedItem().toString();
            String tanggal = namaHariIni + ", " + tgl + "/" + bulan + "/" + tahun;
            // Mengambil default instance dari SmsManager
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage("085814198868", //085814198868
                    null,
                    "pln"+nom + "." + editIdPelanggan.getText().toString() + ".1234",
                    null,
                    null);
            Toast.makeText(Pln.this, "SMS Berhasil Dikirim!",
                    Toast.LENGTH_LONG).show();
            pulsaDB.insertTransaksi(nomor, "PLN PRABAYAR", nominal, tanggal, false);
            if (isBerhasil) {
                showDialog("Transaksi Berhasil", "Silahkan lihat laporan di riwayat transaksi");
            } else {
                showDialog("Transaksi Gagal", "Silahkan coba kembali");
            }
        } catch (Exception ex) {
            Toast.makeText(Pln.this, "Pengiriman SMS Gagal...",
                    Toast.LENGTH_LONG).show();
            ex.printStackTrace();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                sendSmsByManager();
            } else {
                Toast.makeText(Pln.this, "Aplikasi ini tidak diizinkan mengirim SMS", Toast.LENGTH_LONG).show();
            }
        }
    }

    private void showDialog(String title, String message) {
        AlertDialog.Builder builder;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder = new AlertDialog.Builder(Pln.this, android.R.style.Theme_Material_Dialog_Alert);
        } else {
            builder = new AlertDialog.Builder(Pln.this);
        }
        builder.setTitle(title)
                .setMessage(message)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        if (isBerhasil) {
                            isBerhasil = false;
                            finish();
                        }
                    }
                })
                .show();
    }
}
