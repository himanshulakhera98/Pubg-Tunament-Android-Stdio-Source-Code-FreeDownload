package techno.k9.onesignalv2point134.MyWallet;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;

import techno.k9.onesignalv2point134.MyWallet.Fragments.AddMoney;
import techno.k9.onesignalv2point134.MyWallet.Fragments.TransactionHistory;
import techno.k9.onesignalv2point134.MyWallet.Fragments.WithDrawMoney;
import techno.k9.onesignalv2point134.R;

public class MyWalletClass extends AppCompatActivity {

    Toolbar toolbar;
    TabLayout tabLayout;
    ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_wallet);

        if (ContextCompat.checkSelfPermission(MyWalletClass.this, Manifest.permission.READ_SMS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MyWalletClass.this, new String[]{Manifest.permission.READ_SMS, Manifest.permission.RECEIVE_SMS}, 101);
        }

        toolbar=findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.back);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        tabLayout=findViewById(R.id.tabLayout);
        viewPager=findViewById(R.id.viewPager);

        viewPager.setAdapter(new ViewPagerAdapter(getSupportFragmentManager()));


        viewPager.setOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });


        tabLayout.addTab(tabLayout.newTab().setText("Add Money"));
        tabLayout.addTab(tabLayout.newTab().setText("Withdraw Money"));
        tabLayout.addTab(tabLayout.newTab().setText("Transactions"));
        tabLayout.setTabGravity(Gravity.CENTER);

    }

    @Override
    public boolean onSupportNavigateUp() {
        super.onBackPressed();
        return true;
    }
}


class ViewPagerAdapter extends FragmentPagerAdapter
{

    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int i) {
        switch (i)
        {
            case 0:
                AddMoney adMoney=new AddMoney();
                return adMoney;
            case 1:
                WithDrawMoney withDrawMoney=new WithDrawMoney();
                return withDrawMoney;

            case 2:
                TransactionHistory transactionHistory=new TransactionHistory();
                return transactionHistory;

            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return 3;
    }

}
