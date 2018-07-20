package konita.rc.com.rc.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import konita.rc.com.rc.R;

public class TentangKami extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tentang_kami);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Tentang Kami");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }
}
