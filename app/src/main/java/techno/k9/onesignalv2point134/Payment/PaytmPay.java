package techno.k9.onesignalv2point134.Payment;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.paytm.pgsdk.PaytmOrder;
import com.paytm.pgsdk.PaytmPGService;
import com.paytm.pgsdk.PaytmPaymentTransactionCallback;

import org.json.JSONObject;

import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.ConnectionSpec;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import techno.k9.onesignalv2point134.MyWallet.MyWalletClass;
import techno.k9.onesignalv2point134.R;


public class PaytmPay extends AppCompatActivity {
    PaytmPGService Service;
    Button btnSubmit;
    EditText etAmount;
    String amount = "";
    ProgressDialog dialog;
    private int random;
    private String customerId = "";

    String amount1;

    android.support.v7.widget.Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.paytm_pay);

        toolbar=findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.back);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        if (ContextCompat.checkSelfPermission(PaytmPay.this, Manifest.permission.READ_SMS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(PaytmPay.this, new String[]{Manifest.permission.READ_SMS, Manifest.permission.RECEIVE_SMS}, 101);
        }
        btnSubmit = findViewById(R.id.btnSubmit);
        etAmount = findViewById(R.id.etAmount);
        btnSubmit.setOnClickListener(new View.OnClickListener() {
                                         @Override
                                         public void onClick(View v) {
                                             amount = etAmount.getText().toString().trim();
                                             //Toast.makeText(PaytmPay.this, ""+getSharedPreferences("FACEBOOKL",Context.MODE_PRIVATE).getString("username",""), Toast.LENGTH_SHORT).show();
                                             customerId = "igsdeveloper";
                                             if (amount.isEmpty()) {
                                                 etAmount.setError("Enter Valid Amount");
                                                 etAmount.requestFocus();
                                             } else if (customerId.isEmpty()) {
                                                 Toast.makeText(PaytmPay.this, "Invalid customer id", Toast.LENGTH_LONG).show();
                                             } else {
                                                 dialog = new ProgressDialog(PaytmPay.this);
                                                 dialog.setTitle("Paytm payment");
                                                 dialog.setMessage("Please wait");
                                                 dialog.show();
                                                 random = (int) (new Date()).getTime();
                                                 getCheckSum();
                                             }
                                         }
                                     }
        );


    }

    private void getCheckSum() {
        OkHttpClient client = new OkHttpClient.Builder().connectionSpecs(Arrays.asList(ConnectionSpec.MODERN_TLS, ConnectionSpec.CLEARTEXT)).build();
        FormBody.Builder builder = new FormBody.Builder();
        builder.add("MID", "GiZGaH94157927540426");
        builder.add("ORDER_ID", "" + random);
        builder.add("CUST_ID", "cust123");
        builder.add("MERC_UNQ_REF", customerId);
        builder.add("MOBILE_NO", getSharedPreferences(getResources().getString(R.string.database_name),Context.MODE_PRIVATE).getString("mobile",""));
        builder.add("EMAIL", getSharedPreferences(getResources().getString(R.string.database_name),Context.MODE_PRIVATE).getString("email",""));
        builder.add("CHANNEL_ID", "WAP");
        builder.add("TXN_AMOUNT", amount);
        builder.add("WEBSITE", "DEFAULT");
// This is the staging value. Production value is available in your dashboard
        builder.add("INDUSTRY_TYPE_ID", "Retail");
// This is the staging value. Production value is available in your dashboard
        builder.add("CALLBACK_URL", "http://playpubg.xyz/paytmMiravali/paytmMiravali/verifyChecksum.php");
        //builder.add("CALLBACK_URL", "https://securegw-stage.paytm.in/theia/paytmCallback?ORDER_ID="+random);

        Request request = new Request.Builder().post(builder.build()).url("http://playpubg.xyz/paytmMiravali/paytmMiravali/generateChecksum.php").build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call1, final IOException e) {
                call1.cancel();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        dialog.dismiss();
                        showDialog(e.toString());
                    }
                });
            }

            @Override
            public void onResponse(Call call1, Response response) throws IOException {
                call1.cancel();

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        dialog.dismiss();
                    }
                });
                String str = response.body().string();
                Log.e("response", str);
                try {
                    JSONObject object = new JSONObject(str);

                    String checksum = object.getString("CHECKSUMHASH");
                    paytmPay(checksum);
                } catch (final Exception e) {
                    e.printStackTrace();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            showDialog(e.toString());
                        }
                    });
                }
            }
        });
    }

    private void paytmPay(String checksumhash) {
        HashMap<String, String> paramMap = new HashMap<String, String>();
        paramMap.put("MID", "GiZGaH94157927540426");
        paramMap.put("ORDER_ID", "" + random);
        paramMap.put("CUST_ID", "cust123");
        paramMap.put("MERC_UNQ_REF", customerId);
        paramMap.put("MOBILE_NO", getSharedPreferences(getResources().getString(R.string.database_name),Context.MODE_PRIVATE).getString("mobile",""));
        paramMap.put("EMAIL", getSharedPreferences(getResources().getString(R.string.database_name),Context.MODE_PRIVATE).getString("email",""));
        paramMap.put("CHANNEL_ID", "WAP");
        paramMap.put("TXN_AMOUNT", amount);
        paramMap.put("WEBSITE", "DEFAULT");
// This is the staging value. Production value is available in your dashboard
        paramMap.put("INDUSTRY_TYPE_ID", "Retail");
// This is the staging value. Production value is available in your dashboard
        paramMap.put("CALLBACK_URL", "http://playpubg.xyz/paytmMiravali/paytmMiravali/verifyChecksum.php");
       // paramMap.put("CALLBACK_URL", "https://securegw-stage.paytm.in/theia/paytmCallback?ORDER_ID="+random);

        paramMap.put("CHECKSUMHASH", checksumhash);
        PaytmOrder Order = new PaytmOrder(paramMap);
        Service = PaytmPGService.getProductionService();
        Service.initialize(Order, null);
        Service.startPaymentTransaction(this, true, true, new PaytmPaymentTransactionCallback() {
            /*Call Backs*/
            public void someUIErrorOccurred(String inErrorMessage) {
                Toast.makeText(getApplicationContext(), "UI Error " + inErrorMessage, Toast.LENGTH_LONG).show();
            }

            public void onTransactionResponse(Bundle inResponse) {
                String status = inResponse.getString("STATUS");
                String orderid = inResponse.getString("ORDERID");
                amount1 = inResponse.getString("TXNAMOUNT");
                String msg = inResponse.getString("RESPMSG");
                String custId = inResponse.getString("MERC_UNQ_REF");
                if (status.equalsIgnoreCase("TXN_SUCCESS"))
                    showDialog("Order id: " + orderid + "\n \nAmount: " + amount1 + "\n \n Username : " + custId);
                else {
                    Toast.makeText(PaytmPay.this, msg, Toast.LENGTH_SHORT).show();
                }

            }

            public void networkNotAvailable() {
                Toast.makeText(getApplicationContext(), "Network connection error: Check your internet connectivity", Toast.LENGTH_LONG).show();

            }

            public void clientAuthenticationFailed(String inErrorMessage) {
                Toast.makeText(getApplicationContext(), "Authentication failed: Server error" + inErrorMessage.toString(), Toast.LENGTH_LONG).show();
            }

            public void onErrorLoadingWebPage(int iniErrorCode, String inErrorMessage, String inFailingUrl) {
                Toast.makeText(getApplicationContext(), "Unable to load webpage " + inFailingUrl.toString(), Toast.LENGTH_LONG).show();

            }

            public void onBackPressedCancelTransaction() {
                Toast.makeText(getApplicationContext(), "Transaction cancelled", Toast.LENGTH_LONG).show();

            }

            public void onTransactionCancel(String inErrorMessage, Bundle inResponse) {
                Toast.makeText(getApplicationContext(), "Transaction Cancelled" + inResponse.toString(), Toast.LENGTH_LONG).show();

            }
        });
    }

    private void showDialog(String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(message);

        final DatabaseReference reference=FirebaseDatabase.getInstance().getReference(getResources().getString(R.string.database_name)).child("Balance")
                .child(getSharedPreferences(getResources().getString(R.string.database_name),Context.MODE_PRIVATE).getString("username",""));


        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if(dataSnapshot.exists())
                {
                    double coins= Double.parseDouble(dataSnapshot.getValue(String.class));

                    double newCoins= Double.valueOf(amount1)*100.00;
                    long newCoins1 = Math.round(newCoins)+Math.round(coins);
                    reference.setValue(String.valueOf(newCoins1));



                }else
                {
                    double newCoins= Double.valueOf(amount1)*100.00;
                    long newCoins1 = Math.round(newCoins);
                    reference.setValue(String.valueOf(newCoins1));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(PaytmPay.this, ""+databaseError.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
        builder.setTitle("Payment Success !!");
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                startActivity(new Intent(PaytmPay.this,MyWalletClass.class));
                finish();
            }
        });

        builder.setCancelable(false);

        builder.create().show();
    }

    @Override
    public boolean onSupportNavigateUp() {
        super.onBackPressed();
        return true;
    }
}
