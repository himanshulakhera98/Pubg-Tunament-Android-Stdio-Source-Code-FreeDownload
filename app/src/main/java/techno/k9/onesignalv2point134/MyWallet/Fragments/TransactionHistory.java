package techno.k9.onesignalv2point134.MyWallet.Fragments;


import android.app.Dialog;
import android.content.Context;
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
import techno.k9.onesignalv2point134.Beans.BeanTHistory;
import techno.k9.onesignalv2point134.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class TransactionHistory extends Fragment {

    RecyclerView recyclerView;
    ArrayList<BeanTHistory> arrayList;
    public Dialog dialog;


    public TransactionHistory() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view=inflater.inflate(R.layout.fragment_transaction_history, container, false);
        dialog=new SpotsDialog.Builder().setContext(getActivity())
                .setCancelable(false)
                .setMessage("Getting Data From Server...")
                .build();


        dialog.show();
        recyclerView=view.findViewById(R.id.recyclerView);
        arrayList=new ArrayList<>();
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext().getApplicationContext()));

        Button button=view.findViewById(R.id.button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "Hii", Toast.LENGTH_SHORT).show();
            }
        });

        DatabaseReference reference= FirebaseDatabase.getInstance().getReference(getResources().getString(R.string.database_name)).child("PaymentsReq").child(getActivity().getSharedPreferences(getActivity().getResources().getString(R.string.database_name), Context.MODE_PRIVATE).getString("username", ""));

        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                arrayList.clear();
                if(dataSnapshot.exists())
                {

                    for(DataSnapshot dataSnapshot1:dataSnapshot.getChildren())
                    {
                        BeanTHistory beanTHistory=dataSnapshot1.getValue(BeanTHistory.class);
                        arrayList.add(beanTHistory);
                    }

                    Collections.reverse(arrayList);

                    dialog.dismiss();
                    recyclerView.setAdapter(new MyTAdapter(getActivity(),arrayList));

                }
                dialog.dismiss();



            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

                dialog.dismiss();
            }
        });


        return view;

    }


}


class MyTAdapter extends RecyclerView.Adapter<MyTAdapter.MyTViewHolder>
{
    Context context;
    ArrayList<BeanTHistory> arrayList;

    public MyTAdapter(Context context, ArrayList<BeanTHistory> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }


    @NonNull
    @Override
    public MyTViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view=LayoutInflater.from(context).inflate(R.layout.trasaction_item,viewGroup,false);
        return new MyTViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull MyTViewHolder myTViewHolder, int i) {

        myTViewHolder.mobile.setText(arrayList.get(i).getPaytmNumber());
        myTViewHolder.amount.setText("₹ "+arrayList.get(i).getAmountToPay());
        myTViewHolder.date.setText(arrayList.get(i).getDate());
        myTViewHolder.status.setText(arrayList.get(i).getStatus());

        if(arrayList.get(i).getStatus().equals("Pending"))
        {
            myTViewHolder.status.setTextColor(context.getResources().getColor(android.R.color.holo_red_light));
        }
        else if(arrayList.get(i).getStatus().equals("Success"))
        {
            myTViewHolder.status.setTextColor(context.getResources().getColor(android.R.color.holo_green_light
            ));
        }


    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    class MyTViewHolder extends RecyclerView.ViewHolder
    {
        TextView mobile,amount,date,status;

        public MyTViewHolder(@NonNull View itemView) {
            super(itemView);

            mobile=itemView.findViewById(R.id.mobilenumber);
            amount=itemView.findViewById(R.id.amount);
            date=itemView.findViewById(R.id.date);
            status=itemView.findViewById(R.id.status);



        }
    }
}
