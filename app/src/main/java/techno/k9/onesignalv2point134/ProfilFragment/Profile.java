package techno.k9.onesignalv2point134.ProfilFragment;


import android.app.Dialog;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import techno.k9.onesignalv2point134.MyWallet.MyWalletClass;
import techno.k9.onesignalv2point134.R;
import techno.k9.onesignalv2point134.ReferEarnFragment.ReferEarnActivity;
import techno.k9.onesignalv2point134.howToJoin;
import techno.k9.onesignalv2point134.username_change;


/**
 * A simple {@link Fragment} subclass.
 */
public class Profile extends android.support.v4.app.Fragment {

RecyclerView recyclerView;
ArrayList<Integer> images;
ArrayList<String> names;


    public Profile() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_profile, container, false);

        images=new ArrayList<>();
        names=new ArrayList<>();
        images.clear();
        names.clear();

        names.add("My Wallet");
        names.add("Refer n Earn");
        names.add("Buy This App");
        names.add("Watch n Earn");
        names.add("How to Join Match");
        names.add("Account Setting");
        names.add("Customer Support");
        names.add("Terms & Conditions");
        names.add("Share");

        images.add(R.drawable.wallet);
        images.add(R.drawable.refer);
        images.add(R.drawable.icon);
        images.add(R.drawable.help);
        images.add(R.drawable.topuser);
        images.add(R.drawable.support);
        images.add(R.drawable.term);
        images.add(R.drawable.aboutus);
        images.add(R.drawable.share);

        recyclerView=view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        recyclerView.setAdapter(new MyRecyclerAdapter(getActivity(),names,images));
        return view;
    }


}

class MyRecyclerAdapter extends RecyclerView.Adapter<MyRecyclerAdapter.MyViewHolder>
{
    Dialog dialog1,dialog2;

    Context context;
    ArrayList<String> names;
    ArrayList<Integer> images;

    public MyRecyclerAdapter(Context context, ArrayList<String> names, ArrayList<Integer> images) {
        this.context = context;
        this.names = names;
        this.images = images;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view=LayoutInflater.from(context).inflate(R.layout.profile_item,viewGroup,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int position) {

        myViewHolder.imageView.setImageResource(images.get(position));
        myViewHolder.textView.setText(names.get(position));



    }

    @Override
    public int getItemCount() {
        return images.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder
    {
        ImageView imageView;
        TextView textView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView=itemView.findViewById(R.id.profile_image);
            textView=itemView.findViewById(R.id.profile_text);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if(getAdapterPosition()==0)
                    {
                        context.startActivity(new Intent(context, MyWalletClass.class));
                    }
                    if(getAdapterPosition()==6)
                    {
                       // composeEmail(new String[]{"simranenterprisesworld@gmail.com"},"Hello");


                        context.startActivity(  new Intent(Intent.ACTION_VIEW,
                            Uri.parse("https://api.whatsapp.com/send?phone=918209524527")));
                    }

                    if(getAdapterPosition()==5)
                    {

                        AlertDialog.Builder builder=new AlertDialog.Builder(context);
                        View view=LayoutInflater.from(context).inflate(R.layout.account_details_view,null);
                        builder.setView(view);
                        final Button username=view.findViewById(R.id.newUsername);
                        final Button password=view.findViewById(R.id.newPassword);
                        final Button back=view.findViewById(R.id.back);

                        username.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                context.startActivity(new Intent(context, username_change.class));



                            }
                        });

                        password.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                context.startActivity(new Intent(context, techno.k9.onesignalv2point134.password.class));
                            }
                        });


                        back.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                dialog1.dismiss();
                            }
                        });

                        dialog1=builder.create();
                        dialog1.show();
                    }
                    if(getAdapterPosition()==1)
                    {
                        context.startActivity(new Intent(context, ReferEarnActivity.class));
                    }
                    if(getAdapterPosition()==4)
                    {

                        context.startActivity(new Intent(context, howToJoin.class));

                    }
                    if(getAdapterPosition()==7)
                    {

                        context.startActivity(new Intent(context,Terms.class));


                    }
                    if(getAdapterPosition()==8)
                    {
                        Intent intent = new Intent();
                        intent.setAction(Intent.ACTION_SEND);
                        intent.putExtra(Intent.EXTRA_TEXT,"Install "+context.getResources().getString(R.string.app_name)+ "  : http://bit.ly/IGS-App" +"\n \n and " +
                                "Use My Refer Code "+context.getSharedPreferences(context.getResources().getString(R.string.database_name),Context.MODE_PRIVATE).getString("username",""));
                        intent.setType("text/plain");
                        context.startActivity(intent);
                    }
                    if(getAdapterPosition()==2)
                    {
                        context.startActivity(  new Intent(Intent.ACTION_VIEW,
                               Uri.parse("https://api.whatsapp.com/send?phone=918209524527")));
                    }
                    if(getAdapterPosition()==3)
                    {


                        AlertDialog.Builder builder=new AlertDialog.Builder(context);
                        builder.setTitle("Watch Video to Earn")
                                .setMessage("Watch Full Video to Earn 8 Coins !! \n \n 100 Coins=1 Rs \n ")
                                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                        context.startActivity(new Intent(context,WatchnEarn.class));
                                    }
                                }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        }).create().show();
                    }





                }
            });
        }


        public void composeEmail(String[] addresses, String subject) {
            Intent intent = new Intent(Intent.ACTION_SENDTO);
            intent.setData(Uri.parse("mailto:")); // only email apps should handle this
            intent.putExtra(Intent.EXTRA_EMAIL, addresses);
            intent.putExtra(Intent.EXTRA_SUBJECT, subject);
            if (intent.resolveActivity(context.getPackageManager()) != null) {
                context.startActivity(intent);
            }
        }
    }


}
