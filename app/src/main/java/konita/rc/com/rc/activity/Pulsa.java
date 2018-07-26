package konita.rc.com.rc.activity;

import android.Manifest;
import android.app.Activity;
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
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

import konita.rc.com.rc.R;
import konita.rc.com.rc.database.PulsaDB;

public class Pulsa extends AppCompatActivity {

    Spinner spinNominal;
    EditText editNoHp;
    Button btnBatal,btnBeli;
    TextView teksOperator;
    ImageView imgOperator;
    ArrayAdapter<CharSequence> arrayAdapter;
    PulsaDB pulsaDB;
    String namaHariIni = "", operator = "";
    int tgl,bulan,tahun;
    public static boolean isBerhasil = false, isFromMenu = false;
    public static String nominal = "", noHp = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pulsa);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Pembelian Pulsa");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        deklarasi();
        action();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!isFromMenu) {
            isiUlang();
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

    private void deklarasi(){
        pulsaDB = new PulsaDB(Pulsa.this);
        spinNominal = (Spinner) findViewById(R.id.spinNomPulsa);
        teksOperator = (TextView) findViewById(R.id.teksOperator);
        editNoHp = (EditText) findViewById(R.id.editNomrHP);
        imgOperator = (ImageView) findViewById(R.id.imgOperator);
        btnBatal = (Button) findViewById(R.id.btnBatal);
        btnBeli = (Button) findViewById(R.id.btnBeli);
        Calendar calendar = Calendar.getInstance();
        tgl = calendar.get(Calendar.DAY_OF_MONTH);
        int tglHari = calendar.get(Calendar.DAY_OF_WEEK);
        bulan = calendar.get(Calendar.MONTH)+1;
        tahun = calendar.get(Calendar.YEAR);

        teksOperator.setVisibility(View.VISIBLE);
        imgOperator.setVisibility(View.GONE);

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

    private void isiUlang() {
        editNoHp.setText(noHp);
        listProvider();
        String[] arrayNominal = {};
        if (operator.equals("TELKOMSEL")) {
            arrayNominal = getResources().getStringArray(R.array.opTelkomsel);
        } else if (operator.equals("INDOSAT")) {
            arrayNominal = getResources().getStringArray(R.array.opIndosat);
        } else if (operator.equals("AXIS")) {
            arrayNominal = getResources().getStringArray(R.array.opAxis);
        } else if (operator.equals("XL")) {
            arrayNominal = getResources().getStringArray(R.array.opXL);
        } else if (operator.equals("TRI")) {
            arrayNominal = getResources().getStringArray(R.array.opTri);
        } else {
            arrayNominal = getResources().getStringArray(R.array.nominal);
        }
        for (int i=0;i<arrayNominal.length;i++) {
            if (nominal.equals(arrayNominal[i])) {
                spinNominal.setSelection(i);
            }
        }
    }

    private void action() {
        editNoHp.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.length()>=4) {
                    String number = editable.toString().substring(0, 4);
                    if (number.equals("0857") || number.equals("0856") || number.equals("0858") || number.equals("0815")
                            || number.equals("0816")) {
                        operator = "INDOSAT";
                        teksOperator.setVisibility(View.GONE);
                        imgOperator.setVisibility(View.VISIBLE);
                        imgOperator.setImageDrawable(getResources().getDrawable(R.drawable.ic_indosat));
                    } else if (number.equals("0859") || number.equals("0878") || number.equals("0819") || number.equals("0877")) {
                        operator = "XL";
                        teksOperator.setVisibility(View.GONE);
                        imgOperator.setVisibility(View.VISIBLE);
                        imgOperator.setImageDrawable(getResources().getDrawable(R.drawable.ic_xl));
                    } else if (number.equals("0812") || number.equals("0852") || number.equals("0853") || number.equals("0821")
                            || number.equals("0813") || number.equals("0822") || number.equals("0823")) {
                        operator = "TELKOMSEL";
                        teksOperator.setVisibility(View.GONE);
                        imgOperator.setVisibility(View.VISIBLE);
                        imgOperator.setImageDrawable(getResources().getDrawable(R.drawable.ic_telkomsel));
                    } else if (number.equals("0896") || number.equals("0895") || number.equals("0899") || number.equals("0897")) {
                        operator = "TRI";
                        teksOperator.setVisibility(View.GONE);
                        imgOperator.setVisibility(View.VISIBLE);
                        imgOperator.setImageDrawable(getResources().getDrawable(R.drawable.ic_tri));
                    } else if (number.equals("0838") || number.equals("0831") || number.equals("0832")) {
                        operator = "AXIS";
                        teksOperator.setVisibility(View.GONE);
                        imgOperator.setVisibility(View.VISIBLE);
                        imgOperator.setImageDrawable(getResources().getDrawable(R.drawable.ic_axis));
                    }
                } else {
                    operator = "";
                    teksOperator.setText("Masukkan nomor terlebih dulu");
                    imgOperator.setImageDrawable(null);
                    teksOperator.setVisibility(View.VISIBLE);
                    imgOperator.setVisibility(View.GONE);
                }
                listProvider();
            }
        });

        btnBeli.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isComplete()) {
                    permissionSms();
                } else {
                    Toast.makeText(Pulsa.this,"Inputan tidak boleh kosong",Toast.LENGTH_LONG).show();
                }
            }
        });

        btnBatal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    private boolean isComplete() {
        return !editNoHp.getText().toString().equals("") || !operator.equals("") ||
                !spinNominal.getSelectedItem().toString().equals("-- PILIH NOMINAL --");
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
        String nom = String.valueOf(nominals).substring(0,String.valueOf(nominals).length()-3);
        try {
            String nomor = editNoHp.getText().toString();
            String nominal = spinNominal.getSelectedItem().toString();
            String tanggal = namaHariIni+", "+tgl+"/"+bulan+"/"+tahun;
            // Mengambil default instance dari SmsManager
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage("085814198868", //085814198868
                    null,
                    nom+"."+nomor+".1234",
                    null,
                    null);
            Toast.makeText(Pulsa.this, "SMS Berhasil Dikirim!",
                    Toast.LENGTH_LONG).show();
            pulsaDB.insertTransaksi(nomor,operator,nominal,tanggal, true);
            if (isBerhasil) {
                showDialog("Transaksi Berhasil", "Silahkan lihat laporan di riwayat transaksi");
            } else {
                showDialog("Transaksi Gagal", "Silahkan coba kembali");
            }
        } catch (Exception ex) {
            Toast.makeText(Pulsa.this,"Pengiriman SMS Gagal...",
                    Toast.LENGTH_LONG).show();
            ex.printStackTrace();
        }
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

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                sendSmsByManager();
            } else {
                Toast.makeText(Pulsa.this,"Aplikasi ini tidak diizinkan mengirim SMS", Toast.LENGTH_LONG).show();
            }
        }
    }

    private void listProvider() {
        if (operator.equals("TELKOMSEL")) {
            arrayAdapter = ArrayAdapter.createFromResource(Pulsa.this, R.array.opTelkomsel, R.layout.spinner);
        } else if (operator.equals("INDOSAT")) {
            arrayAdapter = ArrayAdapter.createFromResource(Pulsa.this, R.array.opIndosat, R.layout.spinner);
        } else if (operator.equals("AXIS")) {
            arrayAdapter = ArrayAdapter.createFromResource(Pulsa.this, R.array.opAxis, R.layout.spinner);
        } else if (operator.equals("XL")) {
            arrayAdapter = ArrayAdapter.createFromResource(Pulsa.this, R.array.opXL, R.layout.spinner);
        } else if (operator.equals("TRI")) {
            arrayAdapter = ArrayAdapter.createFromResource(Pulsa.this, R.array.opTri, R.layout.spinner);
        } else {
            arrayAdapter = ArrayAdapter.createFromResource(Pulsa.this, R.array.nominal, R.layout.spinner);
        }
        spinNominal.setAdapter(arrayAdapter);
    }

    private void showDialog(String title, String message) {
        AlertDialog.Builder builder;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder = new AlertDialog.Builder(Pulsa.this, android.R.style.Theme_Material_Dialog_Alert);
        } else {
            builder = new AlertDialog.Builder(Pulsa.this);
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
