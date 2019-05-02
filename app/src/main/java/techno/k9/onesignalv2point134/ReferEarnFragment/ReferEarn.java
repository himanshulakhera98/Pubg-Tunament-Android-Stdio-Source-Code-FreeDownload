package techno.k9.onesignalv2point134.ReferEarnFragment;


import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import techno.k9.onesignalv2point134.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class ReferEarn extends Fragment {

    Button referId;
    Button copy;

    public ReferEarn() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_refer_earn, container, false);

        getActivity().setTitle("Refer n Earn");





        referId=view.findViewById(R.id.referId);
        copy=view.findViewById(R.id.copy);

        copy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ClipboardManager manager= (ClipboardManager) getActivity().getSystemService(Context.CLIPBOARD_SERVICE);



                manager.setText("Install "+getResources().getString(R.string.app_name)+ "  " +
                        ": http://bit.ly/IGS-App "+"\n \n Enter Refer Code - "+referId.getText().toString());

                Toast.makeText(getActivity(), "Refer Code Coppied to Sucessfully !", Toast.LENGTH_SHORT).show();
            }
        });



        DatabaseReference userAdd=FirebaseDatabase.getInstance().getReference(getResources().getString(R.string.database_name)).child("Users")
                .child(getActivity().getSharedPreferences(getResources().getString(R.string.database_name),Context.MODE_PRIVATE).getString("username",""));

        userAdd.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if(dataSnapshot.exists())
                {
                    referId.setText(getActivity().getSharedPreferences(getResources().getString(R.string.database_name),Context.MODE_PRIVATE).getString("username",""));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

                Toast.makeText(getActivity(), "Hii", Toast.LENGTH_SHORT).show();
            }
        });


        return view;
    }

}
