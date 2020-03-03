package id.ac.polinema.intentexercise;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import java.io.IOException;

import static android.webkit.URLUtil.isValidUrl;

public class RegisterActivity extends AppCompatActivity {

    public static final String FULLNAME_KEY = "fullname";
    public static final String EMAIL_KEY = "email";
    public static final String PASSWORD_KEY = "password";
    public static final String CPASSWORD_KEY = "cpassword";
    public static final String HOMEPAGE_KEY = "homepage";
    public static final String ABOUT_KEY = "about";
    public static final String AVATAR_KEY = "image";

    private TextInputEditText FullnameInput;
    private TextInputEditText EmailInput;
    private TextInputEditText PasswordInput;
    private TextInputEditText CPasswordInput;
    private TextInputEditText HomepageInput;
    private TextInputEditText AboutInput;

    private static final String TAG = RegisterActivity.class.getCanonicalName();
    private static final int GALLERY_REQUEST_CODE = 1;

    private ImageView avatarImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        avatarImage = findViewById(R.id.image_profile);

        FullnameInput = findViewById(R.id.text_fullname);
        EmailInput = findViewById(R.id.text_email);
        PasswordInput = findViewById(R.id.text_password);
        CPasswordInput = findViewById(R.id.text_confirm_password);
        HomepageInput = findViewById(R.id.text_homepage);
        AboutInput = findViewById(R.id.text_about);
        avatarImage = findViewById(R.id.image_profile);

    }

    public void handleProfil(View view) {
        String fullname = FullnameInput.getText().toString();
        String email = EmailInput.getText().toString();
        String password = PasswordInput.getText().toString();
        String cpassword = CPasswordInput.getText().toString();
        String homepage = HomepageInput.getText().toString();
        String about = AboutInput.getText().toString();

        if (fullname.length() == 0) {
            FullnameInput.setError("Fullname harus diisi");
        } else if (email.length() == 0) {
            EmailInput.setError("Email harus diisi");
        } else if (password.length() == 0) {
            PasswordInput.setError("Password harus diisi");
        } else if (cpassword.length() == 0) {
            CPasswordInput.setError("Confirm Password harus diisi");
        } else if (homepage.length() == 0) {
            HomepageInput.setError("Homepage harus diisi");
        } else if (about.length() == 0) {
            AboutInput.setError("About harus diisi");
        } else {
            if (isValidEmail(email)) {
                if (isValidUrl(homepage)) {
                    if (password.equals(cpassword)) {
                        Toast.makeText(getApplicationContext(), "Registrasi Berhasil", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(this, ProfileActivity.class);
                        avatarImage.buildDrawingCache();
                        Bitmap image = avatarImage.getDrawingCache();
                        Bundle extras = new Bundle();
                        extras.putParcelable(AVATAR_KEY, image);
                        intent.putExtras(extras);
                        intent.putExtra(FULLNAME_KEY, fullname);
                        intent.putExtra(EMAIL_KEY, email);
                        intent.putExtra(HOMEPAGE_KEY, homepage);
                        intent.putExtra(ABOUT_KEY, about);
                        startActivity(intent);
                    } else {
                        Toast.makeText(this, "Password tidak cocok", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(this, "Url tidal valid", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this, "Email tidak valid", Toast.LENGTH_SHORT).show();
            }
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_CANCELED) {
            return;
        }

        if (requestCode == GALLERY_REQUEST_CODE) {
            if (data != null) {
                try {
                    Uri imageUri = data.getData();
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
                    avatarImage.setImageBitmap(bitmap);
                } catch (IOException e) {
                    Toast.makeText(this, "Can't load image", Toast.LENGTH_SHORT).show();
                    Log.e(TAG, e.getMessage());
                }
            }
        }
    }

    public void handleChangeAvatar(View view) {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, GALLERY_REQUEST_CODE);
    }

    public  static boolean isValidEmail(String email) {
        boolean validasi;
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        String emailPattern2 = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+\\.+[a-z]+";

        if (email.matches(emailPattern)) {
            validasi = true;
        } else if (email.matches(emailPattern2)) {
            validasi = true;
        } else {
            validasi = false;
        }
        return validasi;
    }

    public static boolean valid(String url) {
        boolean validasi;
        String link = "[a-zA-Z0-9._-]+\\.+[com]+";

        if (url.matches(link)) {
            validasi = true;
        } else {
            validasi = false;
        }
        return validasi;
    }
}
