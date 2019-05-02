package techno.k9.onesignalv2point134.OnGoingFragments;


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
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;

import techno.k9.onesignalv2point134.Beans.BeanOngoing;
import techno.k9.onesignalv2point134.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class OnGoing extends Fragment {

    RecyclerView recyclerView;
    ArrayList<BeanOngoing> arrayList;

    TextView textView;

    public OnGoing() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_on_going, container, false);

        recyclerView=view.findViewById(R.id.recyclerView);
        textView=view.findViewById(R.id.textView_nothing);
        arrayList=new ArrayList<>();
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));


        DatabaseReference reference=FirebaseDatabase.getInstance().getReference(getActivity().getResources().getString(R.string.database_name)).child("Ongoing");


        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                arrayList.clear();
                if(dataSnapshot.exists())
                {
                    for(DataSnapshot snapshot:dataSnapshot.getChildren())
                    {
                        BeanOngoing beanOngoing=snapshot.getValue(BeanOngoing.class);
                        arrayList.add(beanOngoing);
                    }
                    textView.setVisibility(View.GONE);
                    recyclerView.setVisibility(View.VISIBLE);

                    Collections.reverse(arrayList);

                    recyclerView.setAdapter(new OngoingAdapter(getActivity(),arrayList));


                }
                else
                {
                    textView.setVisibility(View.VISIBLE);
                    recyclerView.setVisibility(View.GONE);

                       }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });





        return view;
    }

}


class OngoingAdapter extends RecyclerView.Adapter<OngoingAdapter.MyOngoingHolder>
{
    Context context;
    ArrayList<BeanOngoing> arrayList;

    public OngoingAdapter(Context context, ArrayList<BeanOngoing> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public MyOngoingHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view=LayoutInflater.from(context).inflate(R.layout.ongoing,viewGroup,false);
        return new MyOngoingHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyOngoingHolder myPlayViewHolder, final int i) {

        myPlayViewHolder.title.setText(arrayList.get(i).getTitle());
        myPlayViewHolder.time.setText("Started on: "+arrayList.get(i).getTime());
        myPlayViewHolder.win.setText("₹ "+arrayList.get(i).getWinPrize());
        myPlayViewHolder.kill.setText("₹ "+arrayList.get(i).getPerKill());
        myPlayViewHolder.entry.setText("₹ "+arrayList.get(i).getEntryFee());
        myPlayViewHolder.map.setText(arrayList.get(i).getMap());
        myPlayViewHolder.version.setText(arrayList.get(i).getVersion());
        myPlayViewHolder.type.setText(arrayList.get(i).getType());
        myPlayViewHolder.joined.setText(arrayList.get(i).getJoined()+"/100 Players Joined");
        myPlayViewHolder.progressBar.setProgress(Integer.parseInt(arrayList.get(i).getJoined()));

        myPlayViewHolder.watchLive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                context.startActivity(  new Intent(Intent.ACTION_VIEW,
                        Uri.parse(arrayList.get(i).getWatchlive())));
                    }
        });

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    class MyOngoingHolder extends RecyclerView.ViewHolder
    {
        TextView title,time,win,kill,entry,map,version,type,joined;

        ProgressBar progressBar;
        Button watchLive;

        public MyOngoingHolder(@NonNull View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.title);
            time = itemView.findViewById(R.id.time);
            win = itemView.findViewById(R.id.winprize);
            kill = itemView.findViewById(R.id.perkill);
            entry = itemView.findViewById(R.id.entryfee);
            map = itemView.findViewById(R.id.map);
            version = itemView.findViewById(R.id.version);
            type = itemView.findViewById(R.id.type);
            joined = itemView.findViewById(R.id.joined);
            progressBar = itemView.findViewById(R.id.progress);
            watchLive=itemView.findViewById(R.id.watchlive);





        }
    }
}
