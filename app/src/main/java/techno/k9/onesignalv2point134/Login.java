package techno.k9.onesignalv2point134;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.onesignal.OneSignal;

import dmax.dialog.SpotsDialog;

public class Login extends AppCompatActivity {

    EditText username,password;


    public Dialog dialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        OneSignal.startInit(this)
                .inFocusDisplaying(OneSignal.OSInFocusDisplayOption.Notification)
                .unsubscribeWhenNotificationsAreDisabled(true)
                .init();

        if(getSharedPreferences(getResources().getString(R.string.database_name),MODE_PRIVATE).getBoolean("login",false))
        {
            startActivity(new Intent(Login.this,MainActivity.class));
        }

        username=findViewById(R.id.username);
        password=findViewById(R.id.password);


        dialog=new SpotsDialog.Builder().setContext(Login.this)
                .setCancelable(false)
                .setMessage("Checking Login Data")
                .build();



    }

    public void createGo(View view) {

        startActivity(new Intent(this,Register.class));
    }

    public void login(View view) {

        dialog.show();
        if(!TextUtils.isEmpty(username.getText().toString()) && username.length()>=4)
        {
            if(!TextUtils.isEmpty(password.getText().toString()) && password.length()>=4)
            {

                DatabaseReference userAdd=FirebaseDatabase.getInstance().getReference(getResources().getString(R.string.database_name)).child("Users")
                        .child(username.getText().toString());

                userAdd.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        if(dataSnapshot.exists())
                        {

                               if(password.getText().toString().equals(dataSnapshot.child("password").getValue(String.class)))
                               {
                                   dialog.dismiss();
                                   getSharedPreferences(getResources().getString(R.string.database_name),MODE_PRIVATE).edit().putString("username",username.getText().toString()).commit();
                                   getSharedPreferences(getResources().getString(R.string.database_name),MODE_PRIVATE).edit().putString("email",dataSnapshot.child("emailAddress").getValue(String.class)).commit();
                                   getSharedPreferences(getResources().getString(R.string.database_name),MODE_PRIVATE).edit().putString("mobile",dataSnapshot.child("mobileNumber").getValue(String.class)).commit();
                                   getSharedPreferences("refercode",MODE_PRIVATE).edit().putString("refercode",dataSnapshot.child("referral").getValue(String.class)).commit();

                                   getSharedPreferences(getResources().getString(R.string.database_name),MODE_PRIVATE).edit().putBoolean("login",true).commit();
                                   Toast.makeText(Login.this, "Login Sucessfull !!", Toast.LENGTH_SHORT).show();

                                   startActivity(new Intent(Login.this,MainActivity.class));
                                   finish();
                               }else
                               {
                                   dialog.dismiss();
                                   password.setError("Please Enter Valid Password");
                                   password.requestFocus();
                               }

                        }else
                        {
                            dialog.dismiss();
                            username.setError("Account not exist with this username");
                            username.requestFocus();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        dialog.dismiss();
                        Toast.makeText(Login.this, ""+databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });


            }else
            {
                dialog.dismiss();
                password.setError("Enter Valid password");
                password.requestFocus();
            }

        }else
        {
            dialog.dismiss();
            username.setError("Enter Valid Username");
            username.requestFocus();
        }
    }

    public void reset(View view) {

        Toast.makeText(this, "Mail Us - simranenterprisesworld@gmail.com", Toast.LENGTH_LONG).show();
    }
}
