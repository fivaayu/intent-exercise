package id.ac.polinema.intentexercise;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class ProfileActivity extends AppCompatActivity {
    private TextView Fullname;
    private TextView Email;
    private TextView Homepage;
    private TextView About;
    private ImageView avatarImage;
    private Bundle extra;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        Fullname = findViewById(R.id.label_fullname);
        Email = findViewById(R.id.label_email);
        Homepage = findViewById(R.id.label_homepage);
        About = findViewById(R.id.label_about);
        avatarImage = findViewById(R.id.image_profile);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String fname = getIntent().getExtras().getString( "fullname");
            String email = getIntent().getExtras().getString( "email");
            String hpage = getIntent().getExtras().getString( "homepage");
            String about = getIntent().getExtras().getString( "about");
            Bundle extra = getIntent().getExtras();
            Bitmap bmp = extra.getParcelable("image");

            Fullname.setText(fname);
            Email.setText(email);
            Homepage.setText(hpage);
            About.setText(about);
            avatarImage.setImageBitmap(bmp);

        }
    }

    public void handleHomePage(View view) {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null ) {
            String homepageText = bundle.getString( "homepage");
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://"+homepageText));
            startActivity(intent);
        }
    }

}
