package techno.k9.onesignalv2point134.MyWallet.Fragments;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import techno.k9.onesignalv2point134.Payment.PaytmPay;
import techno.k9.onesignalv2point134.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddMoney extends Fragment {

    Button addMoney;
    TextView balance;

    public AddMoney() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_add_money, container, false);
addMoney=view.findViewById(R.id.addMoney);
balance=view.findViewById(R.id.balance);

        DatabaseReference reference=FirebaseDatabase.getInstance().getReference(getResources().getString(R.string.database_name)).child("Balance")
                .child(getActivity().getSharedPreferences(getResources().getString(R.string.database_name),Context.MODE_PRIVATE).getString("username",""));


        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if(dataSnapshot.exists())
                {
                    Long coins= Long.valueOf(dataSnapshot.getValue(String.class));

                    Double rupee=(coins*1.00)/100.00;

                    balance.setText("₹ "+rupee.toString());

                }else
                {
                    balance.setText("₹ 0.0");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        addMoney.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(getActivity(), PaytmPay.class));
            }
        });


        return view;
    }

}
