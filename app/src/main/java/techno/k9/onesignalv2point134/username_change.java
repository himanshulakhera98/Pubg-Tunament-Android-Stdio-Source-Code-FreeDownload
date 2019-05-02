package techno.k9.onesignalv2point134;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import dmax.dialog.SpotsDialog;

public class username_change extends AppCompatActivity {

    Button back,proceed;
   EditText oldusername,newUsername;

   EditText passwordSave;

    public Dialog dialog2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_username_change);



        back=findViewById(R.id.back);

        oldusername=findViewById(R.id.enterOldUsername);
        newUsername=findViewById(R.id.enterNewUsername);
        passwordSave=findViewById(R.id.enterPassword);

        proceed=findViewById(R.id.procced);

        dialog2=new SpotsDialog.Builder().setContext(username_change.this)
                .setCancelable(false)
                .setMessage("Getting Data From Server...")
                .build();




        proceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog2.show();

                if(!TextUtils.isEmpty(oldusername.getText().toString()))
                {
                    if(!TextUtils.isEmpty(newUsername.getText().toString()))
                    {
                        if(!TextUtils.isEmpty(passwordSave.getText().toString()))
                        {
                            if(oldusername.getText().toString().equals(getSharedPreferences(getResources().getString(R.string.database_name),Context.MODE_PRIVATE).getString("username","")))
                            {
                                DatabaseReference reference=FirebaseDatabase.getInstance().getReference(getResources().getString(R.string.database_name)).child("Users").child(newUsername.getText().toString());

                                reference.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                        if(!dataSnapshot.exists())
                                        {
                                            DatabaseReference reference=FirebaseDatabase.getInstance().getReference(getResources().getString(R.string.database_name)).child("Users").child(oldusername.getText().toString());

                                            reference.addListenerForSingleValueEvent(new ValueEventListener() {
                                                @Override
                                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                                    if(dataSnapshot.exists())
                                                    {
                                                        String password1=dataSnapshot.child("password").getValue(String.class);

                                                        if(password1.equals(passwordSave.getText().toString()))
                                                        {
                                                            DatabaseReference reference1=FirebaseDatabase.getInstance().getReference(getResources().getString(R.string.database_name)).child("Users").child(newUsername.getText().toString());

                                                            reference1.child("emailAddress").setValue(dataSnapshot.child("emailAddress").getValue(String.class));

                                                            reference1.child("mobileNumber").setValue(dataSnapshot.child("mobileNumber").getValue(String.class));

                                                            reference1.child("password").setValue(dataSnapshot.child("password").getValue(String.class));

                                                            reference1.child("referral").setValue(dataSnapshot.child("referral").getValue(String.class));

                                                            reference1.child("username").setValue(dataSnapshot.child(newUsername.getText().toString()).getValue(String.class));

                                                            DatabaseReference reference2=FirebaseDatabase.getInstance().getReference(getResources().getString(R.string.database_name)).child("Users").child(oldusername.getText().toString());

                                                            reference2.removeValue();

                                                            DatabaseReference reference3=FirebaseDatabase.getInstance().getReference(getResources().getString(R.string.database_name)).child("Balance").child(oldusername.getText().toString());

                                                            reference3.addListenerForSingleValueEvent(new ValueEventListener() {
                                                                @Override
                                                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                                                    String money=dataSnapshot.getValue(String.class);

                                                                    DatabaseReference reference4=FirebaseDatabase.getInstance().getReference(getResources().getString(R.string.database_name)).child("Balance").child(newUsername.getText().toString());

                                                                    reference4.setValue(money);
                                                                    DatabaseReference reference3=FirebaseDatabase.getInstance().getReference(getResources().getString(R.string.database_name)).child("Balance").child(oldusername.getText().toString());
                                                                    reference3.removeValue();

                                                                    getSharedPreferences(getResources().getString(R.string.database_name),Context.MODE_PRIVATE).edit().putString("username",newUsername.getText().toString()).commit();


                                                                    Toast.makeText(username_change.this, "Username Changed ..  Login Again", Toast.LENGTH_SHORT).show();
                                                                    getSharedPreferences(getResources().getString(R.string.database_name),MODE_PRIVATE).edit().putBoolean("login",true).commit();

                                                                    startActivity(new Intent(username_change.this,Login.class));

                                                                    dialog2.dismiss();

                                                                    finish();


                                                                }

                                                                @Override
                                                                public void onCancelled(@NonNull DatabaseError databaseError) {
                                                                    dialog2.dismiss();
                                                                }
                                                            });




                                                        }else
                                                        {
                                                            dialog2.dismiss();
                                                            passwordSave.setError("Password is Wrong");
                                                            passwordSave.requestFocus();
                                                        }

                                                    }else
                                                    {
                                                        dialog2.dismiss();
                                                        Toast.makeText(username_change.this, "ERROR - Contact Support", Toast.LENGTH_SHORT).show();
                                                    }
                                                }

                                                @Override
                                                public void onCancelled(@NonNull DatabaseError databaseError) {
                                                    dialog2.dismiss();
                                                }
                                            });


                                        }else
                                        {
                                            dialog2.dismiss();
                                            newUsername.setError("This Username already Used.. Please Use Another");
                                            newUsername.requestFocus();

                                        }
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {
                                        dialog2.dismiss();
                                    }
                                });



                            }else
                            {
                                dialog2.dismiss();
                                oldusername.setError("Username is Wrong");
                                oldusername.requestFocus();

                            }


                        }else
                        {
                            dialog2.dismiss();
                            passwordSave.setError("Enter Your Password");
                            passwordSave.requestFocus();
                        }


                    }else
                    {
                        dialog2.dismiss();
                        newUsername.setError("Enter New Username");
                        newUsername.requestFocus();
                    }

                }else
                {
                    dialog2.dismiss();
                    oldusername.setError("Enter Old Username");
                    oldusername.requestFocus();
                }
            }
        });




        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                username_change.super.onBackPressed();
            }
        });


    }
}
