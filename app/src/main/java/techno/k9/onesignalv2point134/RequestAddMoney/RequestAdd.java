package techno.k9.onesignalv2point134.RequestAddMoney;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.UUID;

import dmax.dialog.SpotsDialog;
import techno.k9.onesignalv2point134.Beans.BeanScreesnhot;
import techno.k9.onesignalv2point134.R;

public class RequestAdd extends AppCompatActivity {

    Uri uri;
    public Dialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_add);

        dialog=new SpotsDialog.Builder().setContext(RequestAdd.this)
                .setCancelable(false)
                .setMessage("Uploading Image.. Please Wait")
                .build();


    }

    public void upload(View view) {


       if(uri!=null)
       {
           dialog.show();
           final StorageReference reference=FirebaseStorage.getInstance().getReference("Pics").child(UUID.randomUUID().toString());

           reference.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
               @Override
               public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                  reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                      @Override
                      public void onSuccess(Uri uri) {

                          Log.d("TAG",String.valueOf(uri.toString()));

                          DatabaseReference reference1=FirebaseDatabase.getInstance().getReference(getResources().getString(R.string.database_name)).child("PaymentScreenshots");

                          String id=reference1.push().getKey();

                          reference1.child(id).setValue(new BeanScreesnhot(getSharedPreferences("FACEBOOKLv4",Context.MODE_PRIVATE).getString("username",""),String.valueOf(uri),id))
                          .addOnCompleteListener(new OnCompleteListener<Void>() {
                              @Override
                              public void onComplete(@NonNull Task<Void> task) {

                                  if(task.isSuccessful())
                                  {
                                      findViewById(R.id.upload).setVisibility(View.GONE);

                                      dialog.dismiss();
                                      Toast.makeText(RequestAdd.this, "Upload Successfull !! Money will be added in wallet in 1 hour ", Toast.LENGTH_SHORT).show();

                                  }else
                                  {
                                      findViewById(R.id.upload).setVisibility(View.GONE);
                                      findViewById(R.id.addScreesnshot).setVisibility(View.VISIBLE);

                                      dialog.dismiss();
                                      Toast.makeText(RequestAdd.this, "ERROR Upload Failed !!  ", Toast.LENGTH_SHORT).show();

                                  }
                              }
                          });

                          }
                  });
               }
           });

       }else
       {
           Toast.makeText(this, "Image not Selected", Toast.LENGTH_SHORT).show();
       }

    }

    public void addScreenshot(View view) {

        Intent intent=new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");

        startActivityForResult(intent,1230);

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode==1230)
        {
            if(resultCode== RESULT_OK)
            {
                if(data.getData()!=null)
                {

                     uri=data.getData();

                    findViewById(R.id.upload).setVisibility(View.VISIBLE);
                    findViewById(R.id.addScreesnshot).setVisibility(View.GONE);

                }
            }
        }
    }
}
