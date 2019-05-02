package techno.k9.onesignalv2point134.PlayFragments;


import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import dmax.dialog.SpotsDialog;
import techno.k9.onesignalv2point134.Beans.BeanPlay;
import techno.k9.onesignalv2point134.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class Play extends Fragment {

    RecyclerView recyclerView;

    ArrayList<BeanPlay>arrayList;
    public Dialog dialog;



    public Play() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_play, container, false);



        dialog=new SpotsDialog.Builder().setContext(getActivity())
                .setCancelable(false)
                .setMessage("Getting Data From Server...")
                .build();


        dialog.show();

        recyclerView=view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        arrayList=new ArrayList<>();

        DatabaseReference reference=FirebaseDatabase.getInstance().getReference(getResources().getString(R.string.database_name)).child("AddedMatch");

        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                arrayList.clear();
               if(dataSnapshot.exists())
               {
                   for(DataSnapshot dataSnapshot1:dataSnapshot.getChildren())
                   {
                       BeanPlay beanPlay=dataSnapshot1.getValue(BeanPlay.class);

                       arrayList.add(beanPlay);
                   }

                   dialog.dismiss();
                   recyclerView.setAdapter(new MyRecyclerPlayAdapter(getActivity(),arrayList));
               }
               else
               {

                   dialog.dismiss();
               }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

                dialog.dismiss();
                Toast.makeText(getActivity(), ""+databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }

}


class MyRecyclerPlayAdapter extends RecyclerView.Adapter<MyRecyclerPlayAdapter.MyPlayViewHolder>
{
    Context context;
    ArrayList<BeanPlay> arrayList;
    Double rupee=0.0;
    ArrayList usersList=new ArrayList();
    public Dialog dialog;



    public MyRecyclerPlayAdapter(Context context, ArrayList<BeanPlay> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public MyPlayViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view=LayoutInflater.from(context).inflate(R.layout.play_item_layout,viewGroup,false);
        return new MyPlayViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyPlayViewHolder myPlayViewHolder, final int i) {

        myPlayViewHolder.title.setText(arrayList.get(i).getTitle());
        myPlayViewHolder.time.setText("Time: "+arrayList.get(i).getTime());
        myPlayViewHolder.win.setText("₹ "+arrayList.get(i).getWinPrize());
        myPlayViewHolder.kill.setText("₹ "+arrayList.get(i).getPerKill());
        myPlayViewHolder.entry.setText("₹ "+arrayList.get(i).getEntryFee());
        myPlayViewHolder.map.setText(arrayList.get(i).getMap());
        myPlayViewHolder.version.setText(arrayList.get(i).getVersion());
        myPlayViewHolder.type.setText(arrayList.get(i).getType());

        myPlayViewHolder.progressBar.setProgress(Integer.parseInt(arrayList.get(i).getJoined()));

        myPlayViewHolder.joined.setText(arrayList.get(i).getJoined()+"/100 Players Joined");

        Log.d("TAG",arrayList.get(i).getJoined());




    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    class MyPlayViewHolder extends RecyclerView.ViewHolder
    {

        TextView title,time,win,kill,entry,map,version,type,joined;

        ProgressBar progressBar;
        // Button join;

        public MyPlayViewHolder(@NonNull View itemView) {
            super(itemView);

            title=itemView.findViewById(R.id.title);
            time=itemView.findViewById(R.id.time);
            win=itemView.findViewById(R.id.winprize);
            kill=itemView.findViewById(R.id.perkill);
            entry=itemView.findViewById(R.id.entryfee);
            map=itemView.findViewById(R.id.map);
            version=itemView.findViewById(R.id.version);
            type=itemView.findViewById(R.id.type);
            joined=itemView.findViewById(R.id.joined);
            progressBar=itemView.findViewById(R.id.progress);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {



                    context.startActivity(new Intent(context,ViewPlayMatch.class).putExtra("time",arrayList.get(getAdapterPosition()).getTime())
                            .putExtra("type",arrayList.get(getAdapterPosition()).getType())
                            .putExtra("entry",arrayList.get(getAdapterPosition()).getEntryFee())
                            .putExtra("map",arrayList.get(getAdapterPosition()).getMap())
                            .putExtra("version",arrayList.get(getAdapterPosition()).getVersion())
                            .putExtra("win",arrayList.get(getAdapterPosition()).getWinPrize())
                            .putExtra("id",arrayList.get(getAdapterPosition()).getId())
                            .putExtra("joined",arrayList.get(getAdapterPosition()).getJoined())
                            .putExtra("title",arrayList.get(getAdapterPosition()).getTitle())
                            .putExtra("anouncement",arrayList.get(getAdapterPosition()).getAnouncement()));
                }
            });








        }
    }
}




