package techno.k9.onesignalv2point134;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.onesignal.OneSignal;

import techno.k9.onesignalv2point134.Beans.BeanRegister;
import techno.k9.onesignalv2point134.Notification.Notification;
import techno.k9.onesignalv2point134.OnGoingFragments.OnGoing;
import techno.k9.onesignalv2point134.PlayFragments.Play;
import techno.k9.onesignalv2point134.ProfilFragment.Profile;
import techno.k9.onesignalv2point134.ResultsFragment.Result;

public class MainActivity extends AppCompatActivity {

    private LinearLayout linearLayout;
    private Toolbar toolbar;

    TextView balance;
    BeanRegister register;
    double coins;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.play:
                    //mTextMessage.setText(R.string.title_home);
                    getSupportActionBar().setTitle("Upcoming Matches");
                    setText();
                    getSupportFragmentManager().beginTransaction().replace(R.id.addFragmentHere, new Play()).addToBackStack("").commit();


                    return true;
                case R.id.result:
                    //mTextMessage.setText(R.string.title_dashboard);
                    getSupportActionBar().setTitle("Match Results");
                    setText();
                    getSupportFragmentManager().beginTransaction().replace(R.id.addFragmentHere, new Result()).addToBackStack("").commit();

                    return true;
                case R.id.me:
                    getSupportActionBar().setTitle(" My Profile ");
                    setText();
                    getSupportFragmentManager().beginTransaction().replace(R.id.addFragmentHere, new Profile()).addToBackStack("").commit();
                    //mTextMessage.setText(R.string.title_notifications);
                    return true;

                case R.id.notification:
                    getSupportActionBar().setTitle("Room Notifier");
                    setText();
                    getSupportFragmentManager().beginTransaction().replace(R.id.addFragmentHere, new Notification()).addToBackStack("").commit();
                    return true;

                case R.id.ongoing:
                    getSupportActionBar().setTitle("Ongoing Matches");
                    setText();
                    getSupportFragmentManager().beginTransaction().replace(R.id.addFragmentHere, new OnGoing()).addToBackStack("").commit();
                    return true;


            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = findViewById(R.id.toolbar);
        balance = findViewById(R.id.balance);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Upcoming Matches");

        setText();


        OneSignal.startInit(this)
                .inFocusDisplaying(OneSignal.OSInFocusDisplayOption.Notification)
                .unsubscribeWhenNotificationsAreDisabled(true)
                .init();

        linearLayout = findViewById(R.id.addFragmentHere);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);



       // startService(new Intent(MainActivity.this, BackgroundSoundService.class));


        getSupportFragmentManager().beginTransaction().replace(R.id.addFragmentHere, new Play()).addToBackStack("").commit();

    }

    private void setText() {

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference(getResources().getString(R.string.database_name)).child("Balance")
                .child(getSharedPreferences(getResources().getString(R.string.database_name), Context.MODE_PRIVATE).getString("username", ""));


        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if (dataSnapshot.exists()) {
                    Long coins = Long.valueOf(dataSnapshot.getValue(String.class));

                    Double rupee = Double.parseDouble(String.valueOf((coins * 1.00) / 100.00));

                    Double rupee1= Double.valueOf(Math.round(rupee));

                    balance.setText("  ₹ " +String.valueOf(rupee1) + "  ");

                } else {
                    balance.setText("  ₹ 0.0  ");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        DatabaseReference reference1=FirebaseDatabase.getInstance().getReference(getResources().getString(R.string.database_name)).child("Version");

        reference1.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                String value=dataSnapshot.getValue(String.class);


               if(dataSnapshot.exists())
               {
                   if(!value.equals(getResources().getString(R.string.Version)))
                   {
                       AlertDialog.Builder builder=new AlertDialog.Builder(MainActivity.this);

                       builder.setCancelable(false);
                       builder.setTitle("New Update Found !!");
                       builder.setMessage("\n Please Update to New Version to Enjoy More Features \n");
                       builder.setPositiveButton("Update", new DialogInterface.OnClickListener() {
                           @Override
                           public void onClick(DialogInterface dialog, int which) {

                               startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://bit.ly/IGS-App")));
                           }
                       });

                       builder.create().show();
                   }
               }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }


}