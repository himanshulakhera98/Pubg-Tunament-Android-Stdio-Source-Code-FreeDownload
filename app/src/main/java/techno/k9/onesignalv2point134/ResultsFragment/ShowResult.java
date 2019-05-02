package techno.k9.onesignalv2point134.ResultsFragment;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
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

import dmax.dialog.SpotsDialog;
import techno.k9.onesignalv2point134.Beans.BeanPlay;
import techno.k9.onesignalv2point134.Beans.BeanResult;
import techno.k9.onesignalv2point134.R;


public class ShowResult extends AppCompatActivity {

    String id ;
    ArrayList<BeanPlay> arrayList;
    Dialog dialog;
    ArrayList<BeanResult> resultArrayList;

    TextView title,time,win,perkill,fee;

    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_result);


        id=getIntent().getExtras().getString("id");
        listView=findViewById(R.id.listView);

        dialog=new SpotsDialog.Builder().setContext(ShowResult.this)
                .setCancelable(false)
                .setMessage("Getting Data From Server...")
                .build();

        title=findViewById(R.id.title);
        time=findViewById(R.id.time);
        win=findViewById(R.id.winprize);
        perkill=findViewById(R.id.perkill);
        fee=findViewById(R.id.entryfee);





        dialog.show();

        arrayList=new ArrayList<>();
        resultArrayList=new ArrayList<>();


        title.setText(getIntent().getStringExtra("title"));
        time.setText("Organised on : "+getIntent().getStringExtra("time"));
        win.setText("₹ "+getIntent().getStringExtra("winprize"));
        perkill.setText("₹ "+getIntent().getStringExtra("perkill"));
        fee.setText("₹ "+getIntent().getStringExtra("entryfee"));



                    DatabaseReference reference1=FirebaseDatabase.getInstance().getReference(getResources().getString(R.string.database_name)).child("MatchResultsFULLRESULT").child(id);


                    reference1.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                            resultArrayList.clear();
                            if(dataSnapshot.exists())
                            {
                                for(DataSnapshot snapshot:dataSnapshot.getChildren()) {
                                    BeanResult result = snapshot.getValue(BeanResult.class);

                                    resultArrayList.add(result);
                                }

                                dialog.dismiss();
                                listView.setAdapter(new MyListViewResultAdapter(ShowResult.this,resultArrayList));

                            }else
                            {
                                dialog.dismiss();
                                Toast.makeText(ShowResult.this, "There is no Data Yet", Toast.LENGTH_SHORT).show();
                            }

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                            dialog.dismiss();
                            Toast.makeText(ShowResult.this, ""+databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });

    }
}


class MyListViewResultAdapter extends BaseAdapter
{
    Context context;
    ArrayList<BeanResult> resultArrayList;

    public MyListViewResultAdapter(Context context, ArrayList<BeanResult> resultArrayList) {
        this.context = context;
        this.resultArrayList = resultArrayList;
    }

    @Override
    public int getCount() {
        return resultArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return resultArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view=LayoutInflater.from(context).inflate(R.layout.result_list_item,parent,false);
        ImageView image1,image2,image3,image4,image5;



       image1=view.findViewById(R.id.image1);
        image2=view.findViewById(R.id.image2);
        image3=view.findViewById(R.id.image3);
        image4=view.findViewById(R.id.image4);
        image5=view.findViewById(R.id.image5);

        Picasso.get().load(resultArrayList.get(0).getUrl1()).into(image1);
        Picasso.get().load(resultArrayList.get(0).getUrl2()).into(image2);
        Picasso.get().load(resultArrayList.get(0).getUrl3()).into(image3);
        Picasso.get().load(resultArrayList.get(0).getUrl4()).into(image4);
        Picasso.get().load(resultArrayList.get(0).getUrl5()).into(image5);


        image1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder=new AlertDialog.Builder(context);
                LayoutInflater inflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View view1=inflater.inflate(R.layout.viewfullimage,null);
                builder.setView(view1);

                ImageView imageView=view1.findViewById(R.id.myZoomageView);
                Picasso.get().load(resultArrayList.get(0).getUrl1()).into(imageView);

                builder.create().show();
            }
        });

        image2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder=new AlertDialog.Builder(context);
                LayoutInflater inflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View view1=inflater.inflate(R.layout.viewfullimage,null);
                builder.setView(view1);

                ImageView imageView=view1.findViewById(R.id.myZoomageView);
                Picasso.get().load(resultArrayList.get(0).getUrl2()).into(imageView);

                builder.create().show();
            }
        });

        image3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder=new AlertDialog.Builder(context);
                LayoutInflater inflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View view1=inflater.inflate(R.layout.viewfullimage,null);
                builder.setView(view1);

                ImageView imageView=view1.findViewById(R.id.myZoomageView);
                Picasso.get().load(resultArrayList.get(0).getUrl3()).into(imageView);

                builder.create().show();
            }
        });

        image4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder=new AlertDialog.Builder(context);
                LayoutInflater inflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View view1=inflater.inflate(R.layout.viewfullimage,null);
                builder.setView(view1);

                ImageView imageView=view1.findViewById(R.id.myZoomageView);
                Picasso.get().load(resultArrayList.get(0).getUrl4()).into(imageView);

                builder.create().show();
            }
        });

        image5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder=new AlertDialog.Builder(context);
                LayoutInflater inflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View view1=inflater.inflate(R.layout.viewfullimage,null);
                builder.setView(view1);

                ImageView imageView=view1.findViewById(R.id.myZoomageView);
                Picasso.get().load(resultArrayList.get(0).getUrl5()).into(imageView);

                builder.create().show();
            }
        });

        return view;
    }


}
