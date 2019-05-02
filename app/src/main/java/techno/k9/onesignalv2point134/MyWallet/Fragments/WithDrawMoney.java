package techno.k9.onesignalv2point134.MyWallet.Fragments;


import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Date;

import techno.k9.onesignalv2point134.Beans.BeanTHistory;
import techno.k9.onesignalv2point134.Beans.BeanWithDraw;
import techno.k9.onesignalv2point134.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class WithDrawMoney extends Fragment {


    EditText number,amount;
    Button withdraw;

    ProgressDialog dialog;
    public WithDrawMoney() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_with_draw_money, container, false);
        number=view.findViewById(R.id.paytm);
        amount=view.findViewById(R.id.amount);
        withdraw=view.findViewById(R.id.withdraw);

        dialog=new ProgressDialog(getActivity());
        dialog.setTitle("Sending Request");
        dialog.setMessage("Please Wait");
        withdraw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if(!TextUtils.isEmpty(number.getText().toString()))
                {
                    if(!TextUtils.isEmpty(amount.getText().toString()))
                    {
                        if(Integer.parseInt(amount.getText().toString())<50)
                        {
                            amount.setError("Minimum Withdraw Amount is 50 rs ");
                            amount.requestFocus();
                        }else {
                            dialog.show();
                            final DatabaseReference reference = FirebaseDatabase.getInstance().getReference(getResources().getString(R.string.database_name)).child("Balance")
                                    .child(getActivity().getSharedPreferences(getResources().getString(R.string.database_name), Context.MODE_PRIVATE).getString("username", ""));


                            reference.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                    if (dataSnapshot.exists()) {


                                        if (Integer.parseInt(amount.getText().toString()) <= (Integer.parseInt(dataSnapshot.getValue(String.class))) / 100) {
                                            double coins = Double.parseDouble(dataSnapshot.getValue(String.class));

                                            double newCoins = Double.valueOf(amount.getText().toString()) * 100.00;
                                            long newCoins1 = Math.round(coins) - Math.round(newCoins);
                                            reference.setValue(String.valueOf(newCoins1));

                                            DatabaseReference reference1 = FirebaseDatabase.getInstance().getReference(getResources().getString(R.string.database_name)).child("PaymentsReq").child(getActivity().getSharedPreferences(getResources().getString(R.string.database_name), Context.MODE_PRIVATE).getString("username", ""));

                                            String id = reference1.push().getKey();

                                            String currentDateandTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());

                                            reference1.child(id).setValue(new BeanTHistory(getActivity().getSharedPreferences(getResources().getString(R.string.database_name), Context.MODE_PRIVATE).getString("username", ""),number.getText().toString(),amount.getText().toString(),currentDateandTime,"Pending",id));

                                            DatabaseReference reference2 = FirebaseDatabase.getInstance().getReference(getResources().getString(R.string.database_name)).child("PaymentRequest");

                                            reference2.child(id).setValue(new BeanWithDraw(number.getText().toString(),amount.getText().toString(),getActivity().getSharedPreferences(getResources().getString(R.string.database_name), Context.MODE_PRIVATE).getString("username", ""),id));

                                            dialog.dismiss();
                                            Toast.makeText(getActivity(), "You Will Receive your Money in Within 1 Day.  ", Toast.LENGTH_SHORT).show();
                                            amount.setText("");
                                            number.setText("");

                                        } else {
                                            dialog.dismiss();
                                            amount.setError("You Dont have Enough Balance to Withdraw");
                                            amount.requestFocus();
                                        }


                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {
                                    dialog.dismiss();
                                    Toast.makeText(getActivity(), "" + databaseError.getMessage(), Toast.LENGTH_SHORT).show();

                                }
                            });
                        }

                    }else
                    {
                        amount.setError("Amount Cant be Empty");
                        amount.requestFocus();
                    }

                }else
                {
                    number.setError("Number Cant be Empty");
                    number.requestFocus();
                }
            }
        });
        return view;
    }

}
