package techno.k9.onesignalv2point134.Notification;


import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
public class Notification extends Fragment {

    TextView textView;
    public Dialog dialog;
    ArrayList<BeanPlay> arrayList;


    public Notification() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =inflater.inflate(R.layout.fragment_notification, container, false);

        textView=view.findViewById(R.id.showRoomId);
        arrayList=new ArrayList<>();


        dialog=new SpotsDialog.Builder().setContext(getActivity())
                .setCancelable(false)
                .setMessage("Getting Data From Server...")
                .build();

        dialog.show();


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


                    DatabaseReference reference1=FirebaseDatabase.getInstance().getReference(getResources().getString(R.string.database_name)).child("Joined").child(arrayList.get(0).getId());

                    reference1.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                            if(dataSnapshot.exists())
                            {
                                for(DataSnapshot snapshot:dataSnapshot.getChildren())
                                {
                                    if(snapshot.getValue(String.class).equals(getActivity().getSharedPreferences(getResources().getString(R.string.database_name),Context.MODE_PRIVATE).getString("username","")))
                                    {
                                        DatabaseReference reference2=FirebaseDatabase.getInstance().getReference(getResources().getString(R.string.database_name))
                                                .child("RoomIdPass").child(arrayList.get(0).getId());

                                        reference2.addListenerForSingleValueEvent(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                                if(dataSnapshot.exists())
                                                {
                                                    String id=dataSnapshot.child("id").getValue(String.class);
                                                    String pass=dataSnapshot.child("pass").getValue(String.class);
                                                    String time=dataSnapshot.child("time").getValue(String.class);
                                                    String title=dataSnapshot.child("title").getValue(String.class);

                                                    textView.setText("Title : "+title+" \n \n"+" Time : "+time+" \n \n"+" Room ID - "+id+" \n \n Room Password - "+pass);
                                                    dialog.dismiss();
                                                }else
                                                {
                                                    dialog.dismiss();
                                                    textView.setText("Room ID and Password will be Displayed here before 15 Minutes of Starting the Match ..if you Have Joined any Match !!");
                                                }
                                            }

                                            @Override
                                            public void onCancelled(@NonNull DatabaseError databaseError) {

                                                dialog.dismiss();
                                                Toast.makeText(getActivity(), ""+databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                                            }
                                        });
                                    }else
                                    {
                                        dialog.dismiss();

                                        textView.setText("Room ID and Password will be Displayed here before 15 Minutes of Starting the Match ..if you Have Joined any Match !!");

                                    }
                                }
                            }else
                            {
                                dialog.dismiss();

                                textView.setText("Room ID and Password will be Displayed here before 15 Minutes of Starting the Match ..if you Have Joined any Match !!");

                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                            dialog.dismiss();
                            Toast.makeText(getActivity(), ""+databaseError.getMessage(), Toast.LENGTH_SHORT).show();

                        }
                    });
                }
                else
                {

                    dialog.dismiss();

                    textView.setText("Room ID and Password will be Displayed here before 15 Minutes of Starting the Match ..if you Have Joined any Match !!");
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
