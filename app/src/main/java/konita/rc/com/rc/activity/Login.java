package konita.rc.com.rc.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import konita.rc.com.rc.R;

public class Login extends AppCompatActivity {

    EditText editUsername, editPassword;
    Button btnLogin;
    public static String username = "roficell", password = "roficell123";
    SharedPreferences spLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Login");
        }
        spLogin = getSharedPreferences("SPLOGIN",MODE_PRIVATE);
        editUsername = (EditText) findViewById(R.id.edit_username);
        editPassword = (EditText) findViewById(R.id.edit_password);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (editUsername.getText().toString().equals(username) || editPassword.getText().toString().equals(password)) {
                    startActivity(new Intent(Login.this, MenuUtama.class));
                    SharedPreferences.Editor editSpLogin = spLogin.edit();
                    editSpLogin.putString("username",username);
                    editSpLogin.putString("password", password);
                    editSpLogin.apply();
                    Toast.makeText(Login.this,"Berhasil Login",Toast.LENGTH_LONG).show();
                    finish();
                } else {
                    Toast.makeText(Login.this,"Username atau password Anda salah",Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
