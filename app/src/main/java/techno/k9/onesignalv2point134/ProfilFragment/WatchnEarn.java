package techno.k9.onesignalv2point134.ProfilFragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.reward.RewardItem;
import com.google.android.gms.ads.reward.RewardedVideoAd;
import com.google.android.gms.ads.reward.RewardedVideoAdListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import techno.k9.onesignalv2point134.R;


public class WatchnEarn extends AppCompatActivity implements RewardedVideoAdListener {
    private RewardedVideoAd mRewardedVideoAd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_watchn_earn);

        MobileAds.initialize(this, "ca-app-pub-2695341616855205~7225991706");

        // Use an activity context to get the rewarded video instance.
        mRewardedVideoAd = MobileAds.getRewardedVideoAdInstance(this);
        mRewardedVideoAd.setRewardedVideoAdListener(this);
        loadRewardedVideoAd();
    }
    private void loadRewardedVideoAd() {
        mRewardedVideoAd.loadAd("ca-app-pub-2695341616855205/8295051867",
                new AdRequest.Builder().build());
    }

    @Override
    public void onRewardedVideoAdLoaded() {
        if (mRewardedVideoAd.isLoaded()) {
            mRewardedVideoAd.show();
        }
    }

    @Override
    public void onRewardedVideoAdOpened() {

    }

    @Override
    public void onRewardedVideoStarted() {

    }

    @Override
    public void onRewardedVideoAdClosed() {

    }

    @Override
    public void onRewarded(RewardItem rewardItem) {

    }

    @Override
    public void onRewardedVideoAdLeftApplication() {

    }

    @Override
    public void onRewardedVideoAdFailedToLoad(int i) {

        Toast.makeText(this, "Video Failed to Load.. Try later", Toast.LENGTH_SHORT).show();
        finish();

    }

    @Override
    public void onRewardedVideoCompleted() {

        final DatabaseReference reference= FirebaseDatabase.getInstance().getReference(getResources().getString(R.string.database_name)).child("Balance")
                .child(getSharedPreferences(getResources().getString(R.string.database_name), Context.MODE_PRIVATE).getString("username",""));


        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if(dataSnapshot.exists())
                {
                    double coins= Double.parseDouble(dataSnapshot.getValue(String.class));

                    double newCoins= Double.valueOf(0.08)*100.00;
                    long newCoins1 = Math.round(newCoins)+Math.round(coins);
                    reference.setValue(String.valueOf(newCoins1));

                    Toast.makeText(WatchnEarn.this, "You Earned 8 Coins ", Toast.LENGTH_SHORT).show();

                    finish();



                }else
                {
                    double newCoins= Double.valueOf(0.08)*100.00;
                    long newCoins1 = Math.round(newCoins);
                    reference.setValue(String.valueOf(newCoins1));

                    Toast.makeText(WatchnEarn.this, "You Earned 8 Coins ", Toast.LENGTH_SHORT).show();

                    finish();

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(WatchnEarn.this, ""+databaseError.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });

    }
}
