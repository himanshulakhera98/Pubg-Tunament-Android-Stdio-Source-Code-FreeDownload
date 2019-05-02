package techno.k9.onesignalv2point134;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SuccessJoin extends AppCompatActivity {

    double coins;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_success_join);

        String id=getIntent().getExtras().getString("id");
        DatabaseReference reference1 = FirebaseDatabase.getInstance().getReference(getResources().getString(R.string.database_name)).child("ReferMatchJoinerVerify").child(getSharedPreferences(getResources().getString(R.string.database_name), Context.MODE_PRIVATE).getString("username", "").concat(getSharedPreferences("refercode",MODE_PRIVATE).getString("refercode","")));
        reference1.setValue("done");






        DatabaseReference reference=FirebaseDatabase.getInstance().getReference(getResources().getString(R.string.database_name)).child("Joined").child(id);

        reference.push().setValue(getSharedPreferences(getResources().getString(R.string.database_name),Context.MODE_PRIVATE).getString("username","")).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                if(task.isSuccessful())
                {
                    findViewById(R.id.back).setVisibility(View.VISIBLE);
                }
            }
        });


    }

    public void back(View view) {

        onBackPressed();

    }


    @Override
    public void onBackPressed() {

        myMethod();


    }



    private void myMethod()
    {


        DatabaseReference userAdd = FirebaseDatabase.getInstance().getReference(getResources().getString(R.string.database_name)).child("Users")
                .child(getSharedPreferences(getResources().getString(R.string.database_name), Context.MODE_PRIVATE).getString("username", ""));

        Log.d("TAG",getSharedPreferences(getResources().getString(R.string.database_name), Context.MODE_PRIVATE).getString("username", ""));

        userAdd.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull final DataSnapshot dataSnapshot1) {

                if (dataSnapshot1.exists()) {



                    Log.d("TAG","HII"+dataSnapshot1.child("username").getValue(String.class));

                    final DatabaseReference reference0 = FirebaseDatabase.getInstance().getReference(getResources().getString(R.string.database_name)).child("ReferMoneyAddDone").child(dataSnapshot1.child("username").getValue(String.class).concat(getSharedPreferences("refercode",MODE_PRIVATE).getString("refercode","")));

                    reference0.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if (!dataSnapshot.exists()) {

                                DatabaseReference reference1 = FirebaseDatabase.getInstance().getReference(getResources().getString(R.string.database_name)).child("ReferMatchJoinerVerify").child(dataSnapshot1.child("username").getValue(String.class).concat(getSharedPreferences("refercode",MODE_PRIVATE).getString("refercode","")));
                                reference1.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                        if (dataSnapshot.exists()) {
                                            final DatabaseReference reference = FirebaseDatabase.getInstance().getReference(getResources().getString(R.string.database_name)).child("Balance")
                                                    .child(dataSnapshot1.child("username").getValue(String.class));

                                            reference.addListenerForSingleValueEvent(new ValueEventListener() {
                                                @Override
                                                public void onDataChange(@NonNull final DataSnapshot dataSnapshot) {

                                                    if (dataSnapshot.exists()) {
                                                        coins = Double.parseDouble(dataSnapshot.getValue(String.class));
                                                    } else {
                                                        coins = 0.0;

                                                    }



                                                    long newCoins1 = Math.round(Double.valueOf(500))+Math.round(coins);
                                                    reference.setValue(String.valueOf(newCoins1)).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                        @Override
                                                        public void onComplete(@NonNull Task<Void> task) {

                                                            if (task.isSuccessful()) {
                                                                final DatabaseReference reference = FirebaseDatabase.getInstance().getReference(getResources().getString(R.string.database_name)).child("Balance")
                                                                        .child(dataSnapshot1.child("referral").getValue(String.class));

                                                                reference.addListenerForSingleValueEvent(new ValueEventListener() {
                                                                    @Override
                                                                    public void onDataChange(@NonNull final DataSnapshot dataSnapshot) {

                                                                        if (dataSnapshot.exists()) {
                                                                            coins = Double.parseDouble(dataSnapshot.getValue(String.class));
                                                                        } else {
                                                                            coins = 0.0;

                                                                        }

                                                                        long newCoins1 = Math.round(Double.valueOf(500))+Math.round(coins);
                                                                        reference.setValue(String.valueOf(newCoins1)).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                            @Override
                                                                            public void onComplete(@NonNull Task<Void> task) {

                                                                                if (task.isSuccessful()) {

                                                                                    final DatabaseReference reference0 = FirebaseDatabase.getInstance().getReference(getResources().getString(R.string.database_name)).child("ReferMoneyAddDone").child(dataSnapshot1.child("username").getValue(String.class).concat(dataSnapshot1.child("referral").getValue(String.class)));
                                                                                    reference0.setValue("done");


                                                                                }
                                                                            }
                                                                        });
                                                                    }

                                                                    @Override
                                                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                                                    }
                                                                });
                                                            }
                                                        }
                                                    });
                                                }

                                                @Override
                                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                                }
                                            });

                                        }else
                                        {

                                        }
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                    }
                                });

                            }else
                            {

                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        startActivity(new Intent(SuccessJoin.this,MainActivity.class));
        finish();


    }
}
