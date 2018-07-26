package konita.rc.com.rc.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import konita.rc.com.rc.R;
import konita.rc.com.rc.database.PulsaDB;

public class Splash extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
//        PulsaDB pulsaDB = new PulsaDB(this);
//        pulsaDB.dropTable();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                SharedPreferences spLogin = getSharedPreferences("SPLOGIN",MODE_PRIVATE);
                if (spLogin.getString("username","").equals(Login.username)) {
                    startActivity(new Intent(Splash.this,MenuUtama.class));
                } else {
                    startActivity(new Intent(Splash.this,Login.class));
                }
                finish();
            }
        },2000);
    }
}
