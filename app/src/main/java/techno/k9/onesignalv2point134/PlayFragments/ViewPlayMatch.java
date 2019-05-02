package techno.k9.onesignalv2point134.PlayFragments;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Random;

import dmax.dialog.SpotsDialog;
import techno.k9.onesignalv2point134.Beans.BeanImage;
import techno.k9.onesignalv2point134.Beans.BeanPlay;
import techno.k9.onesignalv2point134.MyWallet.MyWalletClass;
import techno.k9.onesignalv2point134.R;
import techno.k9.onesignalv2point134.SuccessJoin;

public class ViewPlayMatch extends AppCompatActivity {
    TextView timer,typer,entryr,mapr,versionr,winPrizer,idr,anouncements;
    Button join;
    Double rupee=0.0;
    ArrayList<BeanPlay> arrayList;
    ArrayList usersList=new ArrayList();
    public Dialog dialog;

    Toolbar toolbar;

    Random random;
    ImageView imageView;


    ListView listView;

    ArrayList usersList1;

    Button loadParticipants,inviteFriend;


    String id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_play_match);

        toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setTitle(getIntent().getStringExtra("title"));
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        listView=findViewById(R.id.listView);
        usersList1=new ArrayList();
        loadParticipants=findViewById(R.id.lodParticipants);
        inviteFriend=findViewById(R.id.invite);


        String time=getIntent().getStringExtra("time");
        String type=getIntent().getStringExtra("type");
        final String entry=getIntent().getStringExtra("entry");
        String map=getIntent().getStringExtra("map");
        String version=getIntent().getStringExtra("version");
        String winPrize=getIntent().getStringExtra("win");
         id=getIntent().getStringExtra("id");
        final String joined=getIntent().getStringExtra("joined");
        String anouncement=getIntent().getStringExtra("anouncement");

        timer=findViewById(R.id.date);
        typer=findViewById(R.id.type);
        entryr=findViewById(R.id.entry);
        mapr=findViewById(R.id.map);
        versionr=findViewById(R.id.version);
        winPrizer=findViewById(R.id.prize);
        imageView=findViewById(R.id.imagedemo);
        anouncements=findViewById(R.id.anouncements);

        join=findViewById(R.id.joinnow);
        arrayList=new ArrayList<>();
        usersList=new ArrayList();

        random=new Random();


        final DatabaseReference reference1=FirebaseDatabase.getInstance().getReference(getResources().getString(R.string.database_name)).child("AddedMatch").child(id);

        DatabaseReference reference2=FirebaseDatabase.getInstance().getReference(getResources().getString(R.string.database_name)).child("Joined").child(id);

        reference2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull final DataSnapshot dataSnapshot1) {

              reference1.addListenerForSingleValueEvent(new ValueEventListener() {
                  @Override
                  public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                      if(dataSnapshot.exists())
                      {
                          reference1.child("joined").setValue(String.valueOf(dataSnapshot1.getChildrenCount()));
                      }
                  }

                  @Override
                  public void onCancelled(@NonNull DatabaseError databaseError) {

                  }
              });



            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



        DatabaseReference reference111= FirebaseDatabase.getInstance().getReference(getResources().getString(R.string.database_name)).child("Images");
        final ArrayList<BeanImage> arrayList1=new ArrayList();
        reference111.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                BeanImage image=dataSnapshot.getValue(BeanImage.class);

                arrayList1.add(image);


                if(random.nextInt(5)==1)
                {
                    Picasso.get().load(arrayList1.get(0).getUrl1()).placeholder(R.drawable.loading).into(imageView);
                }
                else  if(random.nextInt(5)==2)
                {
                    Picasso.get().load(arrayList1.get(0).getUrl2()).placeholder(R.drawable.loading).into(imageView);
                }
                else  if(random.nextInt(5)==3)
                {
                    Picasso.get().load(arrayList1.get(0).getUrl3()).placeholder(R.drawable.loading).into(imageView);
                }
                else  if(random.nextInt(5)==4)
                {
                    Picasso.get().load(arrayList1.get(0).getUrl4()).placeholder(R.drawable.loading).into(imageView);
                }
                else  if(random.nextInt(5)==5)
                {
                    Picasso.get().load(arrayList1.get(0).getUrl5()).placeholder(R.drawable.loading).into(imageView);
                }
                else
                {
                    Picasso.get().load(arrayList1.get(0).getUrl1()).placeholder(R.drawable.loading).into(imageView);
                }




            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        if(id!=null) {
            DatabaseReference reference00 = FirebaseDatabase.getInstance().getReference(getResources().getString(R.string.database_name)).child("Joined").child(id);

            reference00.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    if(dataSnapshot.exists())
                    {
                        usersList.clear();
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            usersList.add(snapshot.getValue(String.class));
                        }

                        listView.setAdapter(new ArrayAdapter<String>(ViewPlayMatch.this, android.R.layout.simple_expandable_list_item_1, usersList));

                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                    Toast.makeText(ViewPlayMatch.this, "" + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });

        }


       loadParticipants.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {

               listView.setVisibility(View.VISIBLE);
               loadParticipants.setVisibility(View.GONE);
           }
       });





        DatabaseReference reference=FirebaseDatabase.getInstance().getReference(getResources().getString(R.string.database_name)).child("Joined").child(id);

        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                usersList.clear();
                if(dataSnapshot.exists()) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        usersList.add(snapshot.getValue(String.class));
                    }
                }

                if(usersList.contains(getSharedPreferences(getResources().getString(R.string.database_name), Context.MODE_PRIVATE).getString("username","")))
                {

                    join.setText("Joined");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

                Toast.makeText(ViewPlayMatch.this, ""+databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });












        DatabaseReference reference11=FirebaseDatabase.getInstance().getReference(getString(R.string.database_name)).child("Balance")
                .child(getSharedPreferences(getResources().getString(R.string.database_name),Context.MODE_PRIVATE).getString("username",""));


        reference11.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if(dataSnapshot.exists())
                {
                    Long coins= Long.valueOf(dataSnapshot.getValue(String.class));

                    rupee=(coins*1.00)/100.00;


                }else
                {
                    DatabaseReference reference=FirebaseDatabase.getInstance().getReference(getString(R.string.database_name)).child("Balance")
                            .child(getSharedPreferences(getResources().getString(R.string.database_name),Context.MODE_PRIVATE).getString("username",""));
                    reference.setValue("0");

                    rupee=0.0;
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

                Toast.makeText(ViewPlayMatch.this, ""+databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


        inviteFriend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                startActivity(new Intent(ViewPlayMatch.this,InviteFriend.class).putExtra("id",id).putExtra("entry",entry)
                    .putExtra("joined",joined).putExtra("rupee",rupee));

            }
        });


        join.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog=new SpotsDialog.Builder().setContext(ViewPlayMatch.this)
                        .setCancelable(false)
                        .setMessage("Getting Data From Server...")
                        .build();

                dialog.show();

                DatabaseReference reference=FirebaseDatabase.getInstance().getReference(getResources().getString(R.string.database_name)).child("Joined").child(id);

                reference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        usersList.clear();
                        if(dataSnapshot.exists()) {
                            for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                usersList.add(snapshot.getValue(String.class));
                            }
                        }

                        if(usersList.contains(getSharedPreferences(getResources().getString(R.string.database_name),Context.MODE_PRIVATE).getString("username","")))
                        {
                            dialog.dismiss();
                            Toast.makeText(ViewPlayMatch.this, "You Already Joined This Match !!", Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            dialog.dismiss();
                            if(Integer.parseInt(joined)<100)
                            {
                                final Double fee= Double.valueOf(entry);

                                if(rupee<fee)
                                {

                                    android.support.v7.app.AlertDialog.Builder builder=new android.support.v7.app.AlertDialog.Builder(ViewPlayMatch.this);

                                    builder.setTitle("Low Balance").setCancelable(false).setMessage("You Dont have Enough Money to Join This Match !! Please Add Money from Your Paytm Wallet ")
                                            .setPositiveButton("AddMoney", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {

                                                    startActivity(new Intent(ViewPlayMatch.this, MyWalletClass.class));
                                                }
                                            }).setNegativeButton("Back", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {

                                            dialog.dismiss();
                                        }
                                    }).create().show();
                                }
                                else
                                {
                                    android.support.v7.app.AlertDialog.Builder builder=new android.support.v7.app.AlertDialog.Builder(ViewPlayMatch.this);

                                    builder.setTitle("Confirm to Join").setCancelable(false).setMessage("Are You Sure to Join This Match ?")
                                            .setPositiveButton("Join", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {


                                                    final DatabaseReference reference=FirebaseDatabase.getInstance().getReference(getResources().getString(R.string.database_name)).child("Balance")
                                                            .child(getSharedPreferences(getResources().getString(R.string.database_name),Context.MODE_PRIVATE).getString("username",""));


                                                    reference.addListenerForSingleValueEvent(new ValueEventListener() {
                                                        @Override
                                                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                                            if(dataSnapshot.exists())
                                                            {
                                                                double coins= Double.parseDouble(dataSnapshot.getValue(String.class));

                                                                double newCoins= Double.valueOf(entry)*100.00;
                                                                long newCoins1 = Math.round(coins)-Math.round(newCoins);
                                                                reference.setValue(String.valueOf(newCoins1));

                                                                startActivity(new Intent(ViewPlayMatch.this, SuccessJoin.class).putExtra("id",id));



                                                            }else
                                                            {
                                                                Toast.makeText(ViewPlayMatch.this, "ERROR - Try Again", Toast.LENGTH_SHORT).show();
                                                            }
                                                        }

                                                        @Override
                                                        public void onCancelled(@NonNull DatabaseError databaseError) {
                                                            Toast.makeText(ViewPlayMatch.this, ""+databaseError.getMessage(), Toast.LENGTH_SHORT).show();

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
                                Toast.makeText(ViewPlayMatch.this, "Already 100 Players Joined This Match !! ", Toast.LENGTH_SHORT).show();
                            }

                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                        Toast.makeText(ViewPlayMatch.this, ""+databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });




            }



        });



        timer.setText("Time : "+time);
        typer.setText("Type : "+type.toUpperCase());
        entryr.setText("Entry Fee : ₹ "+entry);
        mapr.setText("Map : "+map.toUpperCase());
        versionr.setText("Version : "+version.toUpperCase());
        winPrizer.setText("Prize : ₹ "+winPrize);
        anouncements.setText("Anouncements : "+anouncement);


    }

    @Override
    public boolean onSupportNavigateUp() {
        super.onBackPressed();
        return true;
    }

    public void discord(View view) {

        startActivity(  new Intent(Intent.ACTION_VIEW,
                Uri.parse("https://discord.gg/6XsNqTX")));
    }
}
