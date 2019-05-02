package techno.k9.onesignalv2point134;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import dmax.dialog.SpotsDialog;
import techno.k9.onesignalv2point134.Beans.BeanRegister;

public class Register extends AppCompatActivity {

    EditText username,password,email,phone;
    EditText referCode;

    public Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        username=findViewById(R.id.username);
        password=findViewById(R.id.password);
        email=findViewById(R.id.email);
        phone=findViewById(R.id.mobile);
        referCode=findViewById(R.id.refer);




        dialog=new SpotsDialog.Builder()
                .setContext(Register.this)
                .setMessage("Checking Data")
                .setCancelable(false)
                .build();



    }

    public void signinGo(View view) {
        startActivity(new Intent(this,Login.class));
        finish();
    }

    public void register(View view) {

        dialog.show();
        if(!TextUtils.isEmpty(username.getText().toString()) && username.length()>=4)
        {
            if(!TextUtils.isEmpty(email.getText().toString()) && Patterns.EMAIL_ADDRESS.matcher(email.getText().toString()).matches())
            {
                if(!TextUtils.isEmpty(phone.getText().toString()))
                {
                    if(phone.length()<10)
                    {
                        dialog.dismiss();
                        phone.setError("Please Enter Valid Mobile Number");
                        phone.requestFocus();
                    }else
                    {
                        if(!TextUtils.isEmpty(password.getText().toString()) && password.length()>=4)
                        {

                            DatabaseReference checkAlreadAccount=FirebaseDatabase.getInstance().getReference(getResources()
                                    .getString(R.string.database_name)).child("DeviceIdList").child(Settings.Secure.getString(getContentResolver(),Settings.Secure.ANDROID_ID));

                            checkAlreadAccount.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                    if(dataSnapshot.exists())
                                    {
                                        dialog.dismiss();
                                        Toast.makeText(Register.this, "This Device is already registered with another account ! ", Toast.LENGTH_SHORT).show();

                                    }
                                    else
                                    {
                                        DatabaseReference usernameCheck=FirebaseDatabase.getInstance().getReference(getResources().getString(R.string.database_name)).child("Users")
                                                .child(username.getText().toString());

                                        usernameCheck.addListenerForSingleValueEvent(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                                if(dataSnapshot.exists())
                                                {
                                                    dialog.dismiss();
                                                    username.setError("This username is already used.");
                                                    username.requestFocus();
                                                }else
                                                {
                                                   if(!TextUtils.isEmpty(referCode.getText().toString().trim()))
                                                   {

                                                       DatabaseReference checkRefer=FirebaseDatabase.getInstance().getReference(getResources().getString(R.string.database_name)).child("Users")
                                                               .child(referCode.getText().toString());

                                                       checkRefer.addListenerForSingleValueEvent(new ValueEventListener() {
                                                           @Override
                                                           public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                                               if(dataSnapshot.exists())
                                                               {

                                                                   getSharedPreferences("refercode",MODE_PRIVATE).edit().putString("refercode",referCode.getText().toString()).commit();

                                                                   DatabaseReference userAdd=FirebaseDatabase.getInstance().getReference(getResources().getString(R.string.database_name)).child("Users")
                                                                           .child(username.getText().toString());

                                                                   userAdd.setValue(new BeanRegister(username.getText().toString(),email.getText().toString(),
                                                                           phone.getText().toString(),password.getText().toString(),referCode.getText().toString())).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                       @Override
                                                                       public void onComplete(@NonNull Task<Void> task) {

                                                                           if(task.isSuccessful())
                                                                           {

                                                                               DatabaseReference checkAlreadAccount=FirebaseDatabase.getInstance().getReference(getResources()
                                                                                       .getString(R.string.database_name)).child("DeviceIdList").child(Settings.Secure.getString
                                                                                       (getContentResolver(),Settings.Secure.ANDROID_ID));
                                                                               checkAlreadAccount.setValue("");


                                                                               final DatabaseReference reference=FirebaseDatabase.getInstance().getReference(getResources().getString(R.string.database_name)).child("Balance")
                                                                                       .child(username.getText().toString());


                                                                               reference.addListenerForSingleValueEvent(new ValueEventListener() {
                                                                                   @Override
                                                                                   public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                                                                       long coins;
                                                                                       if(dataSnapshot.exists())
                                                                                       {
                                                                                           coins= Long.parseLong(dataSnapshot.getValue(String.class));
                                                                                       }else
                                                                                       {
                                                                                           coins=0;
                                                                                       }
                                                                                       reference.setValue(String.valueOf(coins)).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                                           @Override
                                                                                           public void onComplete(@NonNull Task<Void> task) {

                                                                                               final DatabaseReference reference=FirebaseDatabase.getInstance().getReference(getResources().getString(R.string.database_name)).child("Balance")
                                                                                                       .child(referCode.getText().toString());


                                                                                               reference.addListenerForSingleValueEvent(new ValueEventListener() {
                                                                                                   @Override
                                                                                                   public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                                                                                       long coins;
                                                                                                       if(dataSnapshot.exists())
                                                                                                       {
                                                                                                           coins= Long.parseLong(dataSnapshot.getValue(String.class));
                                                                                                       }else
                                                                                                       {
                                                                                                           coins=0;
                                                                                                       }
                                                                                                       reference.setValue(String.valueOf(coins)).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                                                           @Override
                                                                                                           public void onComplete(@NonNull Task<Void> task) {


                                                                                                               dialog.dismiss();
                                                                                                               Toast.makeText(Register.this, "Registeration Sucessfull!", Toast.LENGTH_SHORT).show();

                                                                                                               startActivity(new Intent(Register.this,Login.class));
                                                                                                               finish();

                                                                                                           }
                                                                                                       });
                                                                                                   }

                                                                                                   @Override
                                                                                                   public void onCancelled(@NonNull DatabaseError databaseError) {

                                                                                                       dialog.dismiss();
                                                                                                       Toast.makeText(Register.this, ""+databaseError.getMessage(), Toast.LENGTH_SHORT).show();

                                                                                                   }
                                                                                               });


                                                                                           }
                                                                                       });
                                                                                   }

                                                                                   @Override
                                                                                   public void onCancelled(@NonNull DatabaseError databaseError) {
                                                                                       dialog.dismiss();
                                                                                       Toast.makeText(Register.this, ""+databaseError.getMessage(), Toast.LENGTH_SHORT).show();

                                                                                   }
                                                                               });



                                                                           }else
                                                                           {
                                                                               dialog.dismiss();
                                                                               Toast.makeText(Register.this, "ERROR !! Try Again !", Toast.LENGTH_SHORT).show();
                                                                           }
                                                                       }
                                                                   });

                                                               }else
                                                               {
                                                                   dialog.dismiss();
                                                                   referCode.setError("Refer Code is incorrect");
                                                                   referCode.requestFocus();
                                                               }

                                                           }

                                                           @Override
                                                           public void onCancelled(@NonNull DatabaseError databaseError) {

                                                               dialog.dismiss();
                                                               Toast.makeText(Register.this, ""+databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                                                           }
                                                       });




                                                   }else
                                                   {
//                                                       dialog.dismiss();
//                                                       referCode.setError("Enter Refer Code");
//                                                       referCode.requestFocus();


                                                       DatabaseReference checkRefer=FirebaseDatabase.getInstance().getReference(getResources().getString(R.string.database_name)).child("Users")
                                                               .child(getResources().getString(R.string.default_register_number));

                                                       checkRefer.addListenerForSingleValueEvent(new ValueEventListener() {
                                                           @Override
                                                           public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                                               if(dataSnapshot.exists())
                                                               {

                                                                   DatabaseReference userAdd=FirebaseDatabase.getInstance().getReference(getResources().getString(R.string.database_name)).child("Users")
                                                                           .child(username.getText().toString());

                                                                   userAdd.setValue(new BeanRegister(username.getText().toString(),email.getText().toString(),
                                                                           phone.getText().toString(),password.getText().toString(),getResources().getString(R.string.default_register_number))).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                       @Override
                                                                       public void onComplete(@NonNull Task<Void> task) {

                                                                           if(task.isSuccessful())
                                                                           {

                                                                               DatabaseReference checkAlreadAccount=FirebaseDatabase.getInstance().getReference(getResources()
                                                                                       .getString(R.string.database_name)).child("DeviceIdList").child(Settings.Secure.getString
                                                                                       (getContentResolver(),Settings.Secure.ANDROID_ID));
                                                                               checkAlreadAccount.setValue("");


                                                                               final DatabaseReference reference=FirebaseDatabase.getInstance().getReference(getResources().getString(R.string.database_name)).child("Balance")
                                                                                       .child(username.getText().toString());


                                                                               reference.addListenerForSingleValueEvent(new ValueEventListener() {
                                                                                   @Override
                                                                                   public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                                                                       long coins;
                                                                                       if(dataSnapshot.exists())
                                                                                       {
                                                                                           coins= Long.parseLong(dataSnapshot.getValue(String.class));
                                                                                       }else
                                                                                       {
                                                                                           coins=0;
                                                                                       }

                                                                                       reference.setValue(String.valueOf(coins)).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                                           @Override
                                                                                           public void onComplete(@NonNull Task<Void> task) {

                                                                                               final DatabaseReference reference=FirebaseDatabase.getInstance().getReference(getResources().getString(R.string.database_name)).child("Balance")
                                                                                                       .child(getResources().getString(R.string.default_register_number));


                                                                                               reference.addListenerForSingleValueEvent(new ValueEventListener() {
                                                                                                   @Override
                                                                                                   public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                                                                                       long coins;
                                                                                                       if(dataSnapshot.exists())
                                                                                                       {
                                                                                                           coins= Long.parseLong(dataSnapshot.getValue(String.class));
                                                                                                       }else
                                                                                                       {
                                                                                                           coins=0;
                                                                                                       }

                                                                                                       reference.setValue(String.valueOf(coins)).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                                                           @Override
                                                                                                           public void onComplete(@NonNull Task<Void> task) {

                                                                                                               getSharedPreferences("refercode",MODE_PRIVATE).edit().putString("refercode","8302137134").commit();


                                                                                                               dialog.dismiss();
                                                                                                               Toast.makeText(Register.this, "Registeration Sucessfull!", Toast.LENGTH_SHORT).show();

                                                                                                               startActivity(new Intent(Register.this,Login.class));
                                                                                                               finish();

                                                                                                           }
                                                                                                       });
                                                                                                   }

                                                                                                   @Override
                                                                                                   public void onCancelled(@NonNull DatabaseError databaseError) {

                                                                                                       dialog.dismiss();
                                                                                                       Toast.makeText(Register.this, ""+databaseError.getMessage(), Toast.LENGTH_SHORT).show();

                                                                                                   }
                                                                                               });


                                                                                           }
                                                                                       });
                                                                                   }

                                                                                   @Override
                                                                                   public void onCancelled(@NonNull DatabaseError databaseError) {
                                                                                       dialog.dismiss();
                                                                                       Toast.makeText(Register.this, ""+databaseError.getMessage(), Toast.LENGTH_SHORT).show();

                                                                                   }
                                                                               });



                                                                           }else
                                                                           {
                                                                               dialog.dismiss();
                                                                               Toast.makeText(Register.this, "ERROR !! Try Again !", Toast.LENGTH_SHORT).show();
                                                                           }
                                                                       }
                                                                   });

                                                               }else
                                                               {
                                                                   dialog.dismiss();
                                                                   referCode.setError("Refer Code is incorrect");
                                                                   referCode.requestFocus();
                                                               }

                                                           }

                                                           @Override
                                                           public void onCancelled(@NonNull DatabaseError databaseError) {

                                                               dialog.dismiss();
                                                               Toast.makeText(Register.this, ""+databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                                                           }
                                                       });



                                                   }

                                                }
                                            }

                                            @Override
                                            public void onCancelled(@NonNull DatabaseError databaseError) {

                                                dialog.dismiss();
                                                Toast.makeText(Register.this, ""+databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                                            }
                                        });
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                    dialog.dismiss();
                                    Toast.makeText(Register.this, ""+databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            });

                        }else
                        {
                            dialog.dismiss();
                            password.setError("Please Enter Valid Password");
                            password.requestFocus();
                        }
                    }

                }else
                {
                    dialog.dismiss();
                    phone.setError("Please Enter Valid Mobile Number");
                    phone.requestFocus();
                }
            }else
            {
                dialog.dismiss();
                email.setError("Please Enter Valid Email");
                email.requestFocus();
            }

        }else
        {
            dialog.dismiss();
            username.setError("Please Enter Valid Username");
            username.requestFocus();
        }
    }
}
