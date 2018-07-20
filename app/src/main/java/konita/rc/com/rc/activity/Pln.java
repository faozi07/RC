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

import konita.rc.com.rc.R;

public class Pln extends AppCompatActivity {

    Spinner spinNominal;
    EditText editIdPelanggan;
    Button btnCancel, btnBayar;
    boolean isBulanan = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pln);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Pembayaran PLN");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        spinNominal = (Spinner) findViewById(R.id.spinNominal);
        editIdPelanggan = (EditText) findViewById(R.id.editIdPelanggan);
        btnBayar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isBulanan) {
                    if (isCompleteBulanan()) {
                        permissionSms();
                    } else {
                        Toast.makeText(Pln.this, "Inputan tidak boleh kosong", Toast.LENGTH_LONG).show();
                    }
                } else {
//                    if (isCompleteTagihan()) {
//                        permissionSms();
//                    } else {
//                        Toast.makeText(Pln.this, "Inputan tidak boleh kosong", Toast.LENGTH_LONG).show();
//                    }
                }
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
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

    private void permissionSms() {
        if (Build.VERSION.SDK_INT > 22) {
            String permission = Manifest.permission.SEND_SMS;
            int grant = ContextCompat.checkSelfPermission(this, permission);
            if (grant != PackageManager.PERMISSION_GRANTED) {
                String[] permissionList = new String[1];
                permissionList[0] = permission;
                ActivityCompat.requestPermissions(this, permissionList,1);
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
        int len=0, i=0;
        boolean isDigit;

        char[] data = message.toCharArray();
        while (i<data.length) {
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
//        try {
//            // Mengambil default instance dari SmsManager
//            SmsManager smsManager = SmsManager.getDefault();
//            smsManager.sendTextMessage("085797511021",
//                    null,
//                    operator+"#"+nominals+"#"+editNoHp.getText().toString()+"#"+editPin.getText().toString(),
//                    null,
//                    null);
//            Toast.makeText(Pln.this, "SMS Berhasil Dikirim!",
//                    Toast.LENGTH_LONG).show();
//            String nomor = editNoHp.getText().toString();
//            String nominal = spinNominal.getSelectedItem().toString();
//            String tanggal = namaHariIni+", "+tgl+"/"+bulan+"/"+tahun;
////            pulsaDB.insertTransaksi(nomor,operator,nominal,tanggal);
//            if (isBerhasil) {
//                showDialog("Transaksi Berhasil", "Silahkan lihat laporan di riwayat transaksi");
//            } else {
//                showDialog("Transaksi Gagal", "Silahkan coba kembali");
//            }
//        } catch (Exception ex) {
//            Toast.makeText(Pln.this,"Pengiriman SMS Gagal...",
//                    Toast.LENGTH_LONG).show();
//            ex.printStackTrace();
//        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                sendSmsByManager();
            } else {
                Toast.makeText(Pln.this,"Aplikasi ini tidak diizinkan mengirim SMS", Toast.LENGTH_LONG).show();
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
//                        if (isBerhasil) {
//                            isBerhasil = false;
//                            finish();
//                        }
                    }
                })
                .show();
    }
}
