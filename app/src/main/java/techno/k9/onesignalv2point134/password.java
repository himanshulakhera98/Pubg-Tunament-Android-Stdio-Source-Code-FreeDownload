package techno.k9.onesignalv2point134;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
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

public class password extends AppCompatActivity {

    EditText oldPass,newPass;
    Button back,procced;
    public Dialog dialog2;

    private static final String TAG="password.java";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password);


        oldPass=findViewById(R.id.enteroldPassword);
        newPass=findViewById(R.id.enterPassword);

        back=findViewById(R.id.back);
        procced=findViewById(R.id.procced);
        dialog2=new SpotsDialog.Builder().setContext(password.this)
                .setCancelable(false)
                .setMessage("Getting Data From Server...")
                .build();





        procced.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog2.show();

                if(!TextUtils.isEmpty(oldPass.getText().toString()))
                {
                    Log.d(TAG,"1st");
                    if(!TextUtils.isEmpty(newPass.getText().toString()))
                    {
                        Log.d(TAG,"2st");
                        if(newPass.getText().length()>=6)
                        {
                            Log.d(TAG,"3st");
                            DatabaseReference reference=FirebaseDatabase.getInstance().getReference(getResources().getString(R.string.database_name)).child("Users").child(getSharedPreferences(getResources().getString(R.string.database_name),Context.MODE_PRIVATE).getString("username",""));
                            reference.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                    Log.d(TAG,"4st");
                                    if(dataSnapshot.exists())
                                    {

                                        String pass=dataSnapshot.child("password").getValue(String.class);
                                        Log.d(TAG,"5st"+pass);
                                        if(oldPass.getText().toString().equals(pass))
                                        {
                                            Log.d(TAG,"6st");
                                            DatabaseReference reference=FirebaseDatabase.getInstance().getReference(getResources().getString(R.string.database_name)).child("Users").child(getSharedPreferences(getResources().getString(R.string.database_name),Context.MODE_PRIVATE).getString("username",""));
                                            reference=reference.child("password");
                                            reference.setValue(newPass.getText().toString());

                                            Log.d(TAG,"7st");
                                            dialog2.dismiss();
                                            Toast.makeText(password.this, "Password Updated", Toast.LENGTH_SHORT).show();
                                            startActivity(new Intent(password.this,MainActivity.class));
                                            finish();

                                        }else
                                        {
                                            dialog2.dismiss();
                                            oldPass.setError("Old Password Incorrect");
                                            oldPass.requestFocus();
                                        }
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
                            newPass.setError("Please Enter a Secure Password");
                            newPass.requestFocus();
                        }

                    }else
                    {
                        dialog2.dismiss();
                        newPass.setError("Enter New Pass");
                        newPass.requestFocus();
                    }
                }else
                {
                    dialog2.dismiss();
                    oldPass.setError("Enter Old Password");
                    oldPass.requestFocus();
                }

            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                password.super.onBackPressed();
            }
        });
    }
}
