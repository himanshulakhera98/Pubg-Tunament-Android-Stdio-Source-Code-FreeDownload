package techno.k9.onesignalv2point134;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

public class howToJoin extends AppCompatActivity {

    ImageView a1,a2,a3,a4;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_how_to_join);

        a1=findViewById(R.id.homeIcon);
        a2=findViewById(R.id.room_id);
        a3=findViewById(R.id.room_password);
        a4=findViewById(R.id.room1);


        Picasso.get().load("https://playerzon.com/blog/img/home_icon.png").placeholder(R.drawable.loading).into(a1);
        Picasso.get().load("https://playerzon.com/blog/img/room_id.png").placeholder(R.drawable.loading).into(a2);
        Picasso.get().load("https://playerzon.com/blog/img/room_password.png").placeholder(R.drawable.loading).into(a3);
        Picasso.get().load("http://playpubg.xyz/pic.jpg").placeholder(R.drawable.loading).into(a4);
    }
}
