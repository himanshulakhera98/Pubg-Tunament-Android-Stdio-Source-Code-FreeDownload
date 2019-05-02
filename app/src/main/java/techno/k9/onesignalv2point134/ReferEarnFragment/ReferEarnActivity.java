package techno.k9.onesignalv2point134.ReferEarnFragment;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import techno.k9.onesignalv2point134.R;


public class ReferEarnActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_refer_earn);

        Toolbar toolbar=findViewById(R.id.toolbar);

        toolbar.setNavigationIcon(R.drawable.back);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Refer n Earn");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        getSupportFragmentManager().beginTransaction().replace(R.id.layout,new ReferEarn()).commit();


    }

    @Override
    public boolean onSupportNavigateUp() {
        super.onBackPressed();
        return true;
    }
}
