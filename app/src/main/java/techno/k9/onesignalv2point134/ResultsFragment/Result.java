package techno.k9.onesignalv2point134.ResultsFragment;


import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;

import dmax.dialog.SpotsDialog;
import techno.k9.onesignalv2point134.Beans.BeanPlay;
import techno.k9.onesignalv2point134.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class Result extends Fragment {

    RecyclerView recyclerView;

    ArrayList<BeanPlay> arrayList;
    public Dialog dialog;


    public Result() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_result, container, false);

        dialog=new SpotsDialog.Builder().setContext(getActivity())
                .setCancelable(false)
                .setMessage("Getting Data From Server...")
                .build();


        dialog.show();

        recyclerView=view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        arrayList=new ArrayList<>();

        DatabaseReference reference=FirebaseDatabase.getInstance().getReference(getResources().getString(R.string.database_name)).child("MatchResults");

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
                    Collections.reverse(arrayList);

                    dialog.dismiss();
                    recyclerView.setAdapter(new MyRecyclerResultAdapter(getActivity(),arrayList));
                } else
                {

                    dialog.dismiss();
                    recyclerView.setAdapter(new MyRecyclerResultAdapter(getActivity(),arrayList));
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


class MyRecyclerResultAdapter extends RecyclerView.Adapter<MyRecyclerResultAdapter.MyResultViewHolder>
{
    Context context;
    ArrayList<BeanPlay> arrayList;

    public MyRecyclerResultAdapter(Context context, ArrayList<BeanPlay> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public MyRecyclerResultAdapter.MyResultViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view=LayoutInflater.from(context).inflate(R.layout.result_item_layout,viewGroup,false);
        return new MyResultViewHolder(view);
    }

    @Override
    public void onBindViewHolder( MyResultViewHolder myPlayViewHolder, int i) {

        myPlayViewHolder.title.setText(arrayList.get(i).getTitle());
        myPlayViewHolder.time.setText("Time: "+arrayList.get(i).getTime());
        myPlayViewHolder.win.setText("₹ "+arrayList.get(i).getWinPrize());
        myPlayViewHolder.kill.setText("₹ "+arrayList.get(i).getPerKill());
        myPlayViewHolder.entry.setText("₹ "+arrayList.get(i).getEntryFee());
        myPlayViewHolder.map.setText(arrayList.get(i).getMap());
        myPlayViewHolder.version.setText(arrayList.get(i).getVersion());
        myPlayViewHolder.type.setText(arrayList.get(i).getType());




    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    class MyResultViewHolder extends RecyclerView.ViewHolder
    {

        TextView title,time,win,kill,entry,map,version,type,joined;

            Button result,watchvideo;

        public MyResultViewHolder(@NonNull View itemView) {
            super(itemView);

            title=itemView.findViewById(R.id.title);
            time=itemView.findViewById(R.id.time);
            win=itemView.findViewById(R.id.winprize);
            kill=itemView.findViewById(R.id.perkill);
            entry=itemView.findViewById(R.id.entryfee);
            map=itemView.findViewById(R.id.map);
            version=itemView.findViewById(R.id.version);
            type=itemView.findViewById(R.id.type);
            watchvideo=itemView.findViewById(R.id.watchLive);

            watchvideo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/channel/UCAVHNIhZ3DifWTbZTK8w-9Q")));
                }
            });

            result=itemView.findViewById(R.id.joinnow);

            result.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    context.startActivity(new Intent(context,ShowResult.class)
                            .putExtra("id",arrayList.get(getAdapterPosition()).getId())
                    .putExtra("title",arrayList.get(getAdapterPosition()).getTitle())
                            .putExtra("time",arrayList.get(getAdapterPosition()).getTime())
                            .putExtra("winprize",arrayList.get(getAdapterPosition()).getWinPrize())
                            .putExtra("perkill",arrayList.get(getAdapterPosition()).getPerKill())
                            .putExtra("entryfee",arrayList.get(getAdapterPosition()).getEntryFee()));
                }
            });






        }
    }
}
