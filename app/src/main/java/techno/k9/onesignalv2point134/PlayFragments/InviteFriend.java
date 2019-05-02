package techno.k9.onesignalv2point134.PlayFragments;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import dmax.dialog.SpotsDialog;
import techno.k9.onesignalv2point134.Beans.BeanImage;
import techno.k9.onesignalv2point134.MainActivity;
import techno.k9.onesignalv2point134.MyWallet.MyWalletClass;
import techno.k9.onesignalv2point134.R;

public class InviteFriend extends AppCompatActivity {


    Button pay;

    EditText PUBGusername;
    Dialog dialog1;

    ArrayList usersList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invite_friend);


        pay=findViewById(R.id.pay);
        PUBGusername=findViewById(R.id.username);
        usersList=new ArrayList();
        dialog1=new SpotsDialog.Builder().setContext(InviteFriend.this)
                .setCancelable(false)
                .setMessage("Getting Data From Server...")
                .build();


        pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog1.show();

                if(!TextUtils.isEmpty(PUBGusername.getText().toString()))
                {
                    BeanImage.friendName=PUBGusername.getText().toString();



                    DatabaseReference reference= FirebaseDatabase.getInstance().getReference(getResources().getString(R.string.database_name)).child("Joined").child(getIntent().getStringExtra("id"));

                    reference.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                            usersList.clear();
                            if(dataSnapshot.exists()) {
                                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                    usersList.add(snapshot.getValue(String.class));
                                }
                            }

                            if(usersList.contains(getSharedPreferences(getResources().getString(R.string.database_name), Context.MODE_PRIVATE).getString("username","").concat("+"+ BeanImage.friendName)))
                            {
                                dialog1.dismiss();
                                Toast.makeText(InviteFriend.this, "You Already Joined This Match !!", Toast.LENGTH_SHORT).show();
                            }
                            else
                            {

                                if(Integer.parseInt(getIntent().getStringExtra("joined"))<100)
                                {
                                    final Double fee= Double.valueOf(getIntent().getStringExtra("entry"));

                                    if(getIntent().getDoubleExtra("rupee",0.0)<fee)
                                    {

                                        android.support.v7.app.AlertDialog.Builder builder=new android.support.v7.app.AlertDialog.Builder(InviteFriend.this);

                                        builder.setTitle("Low Balance").setCancelable(false).setMessage("You Dont have Enough Money to Join This Match !! Please Add Money from Your Paytm Wallet ")
                                                .setPositiveButton("AddMoney", new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialog, int which) {

                                                        startActivity(new Intent(InviteFriend.this, MyWalletClass.class));
                                                    }
                                                }).setNegativeButton("Back", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {

                                                dialog.dismiss();
                                                dialog1.dismiss();
                                            }
                                        }).create().show();
                                    }
                                    else
                                    {
                                        android.support.v7.app.AlertDialog.Builder builder=new android.support.v7.app.AlertDialog.Builder(InviteFriend.this);

                                        builder.setTitle("Confirm to Join").setCancelable(false).setMessage("Are You Sure to Join This Match ?")
                                                .setPositiveButton("Join", new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(final DialogInterface dialog, int which) {


                                                        final DatabaseReference reference=FirebaseDatabase.getInstance().getReference(getResources().getString(R.string.database_name)).child("Balance")
                                                                .child(getSharedPreferences(getResources().getString(R.string.database_name),Context.MODE_PRIVATE).getString("username",""));


                                                        reference.addListenerForSingleValueEvent(new ValueEventListener() {
                                                            @Override
                                                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                                                if(dataSnapshot.exists())
                                                                {
                                                                    double coins= Double.parseDouble(dataSnapshot.getValue(String.class));

                                                                    double newCoins= Double.valueOf(getIntent().getStringExtra("entry"))*100.00;
                                                                    long newCoins1 = Math.round(coins)-Math.round(newCoins);
                                                                    reference.setValue(String.valueOf(newCoins1));

                                                                    DatabaseReference reference=FirebaseDatabase.getInstance().getReference(getResources().getString(R.string.database_name)).child("Joined").child(getIntent().getStringExtra("id"));

                                                                    reference.push().setValue(getSharedPreferences(getResources().getString(R.string.database_name),Context.MODE_PRIVATE).getString("username","").concat("+"+BeanImage.friendName)).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                        @Override
                                                                        public void onComplete(@NonNull Task<Void> task) {

                                                                            if (task.isSuccessful()) {

                                                                                dialog1.dismiss();
                                                                                Toast.makeText(InviteFriend.this, "Success !! Joined Successfully !", Toast.LENGTH_SHORT).show();

                                                                                startActivity(new Intent(InviteFriend.this, MainActivity.class));
                                                                                finish();

                                                                            }
                                                                        }
                                                                    });


                                                                }else
                                                                {
                                                                    dialog1.dismiss();
                                                                    Toast.makeText(InviteFriend.this, "ERROR - Try Again", Toast.LENGTH_SHORT).show();
                                                                }
                                                            }

                                                            @Override
                                                            public void onCancelled(@NonNull DatabaseError databaseError) {
                                                                dialog1.dismiss();
                                                                Toast.makeText(InviteFriend.this, ""+databaseError.getMessage(), Toast.LENGTH_SHORT).show();

                                                            }
                                                        });



                                                    }
                                                }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {

                                                dialog.dismiss();
                                            }
                                        }).create().show();

                                    }
                                }else
                                {
                                    dialog1.dismiss();
                                    Toast.makeText(InviteFriend.this, "Already 100 Players Joined This Match !! ", Toast.LENGTH_SHORT).show();
                                }

                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                            dialog1.dismiss();
                            Toast.makeText(InviteFriend.this, ""+databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });


                }else
                {
                    dialog1.dismiss();
                    PUBGusername.requestFocus();
                    Toast.makeText(InviteFriend.this, "PUBG Name Cant be Empty !", Toast.LENGTH_SHORT).show();
                }
            }
        });



    }
}
