package gr.uom.android.helpinghand;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class IntroActivity extends Activity {

    private static final int MY_PERMISSIONS_REQUEST_CAMERA = 0;
    private static final int MY_PERMISSIONS_REQUEST_INTERNET = 1;
    private boolean flag = false;



    private Runnable task = new Runnable() {
        public void run() {

            if (ContextCompat.checkSelfPermission(IntroActivity.this,
                    Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                flag = true;
                ActivityCompat.requestPermissions(IntroActivity.this,
                        new String[]{Manifest.permission.CAMERA},
                        MY_PERMISSIONS_REQUEST_CAMERA);
            }
            if (ContextCompat.checkSelfPermission(IntroActivity.this,
                    Manifest.permission.INTERNET) != PackageManager.PERMISSION_GRANTED) {
                flag = true;
                ActivityCompat.requestPermissions(IntroActivity.this,
                        new String[]{Manifest.permission.INTERNET},
                        MY_PERMISSIONS_REQUEST_INTERNET);
            }
            if (!flag){
                FirebaseAuth mAuth = FirebaseAuth.getInstance();
                if (mAuth.getCurrentUser() == null) {
                    finish();
                    startActivity(new Intent(IntroActivity.this, SignIn.class));
                } else {
                    Toast.makeText(getApplicationContext(), "Login Complete!", Toast.LENGTH_SHORT).show();
                    finish();
                    startActivity(new Intent(IntroActivity.this, MainMenu.class));
                }
            }



        }
    };


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.intro);


        Handler handler = new Handler();
        handler.postDelayed(task, 1000);


    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_CAMERA: {

                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    FirebaseAuth mAuth = FirebaseAuth.getInstance();
                    if (mAuth.getCurrentUser() == null) {
                        finish();
                        startActivity(new Intent(IntroActivity.this, SignIn.class));
                    } else {
                        Toast.makeText(getApplicationContext(), "Login Complete!", Toast.LENGTH_SHORT).show();
                        finish();
                        startActivity(new Intent(IntroActivity.this, MainMenu.class));
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Grant Permissions to continue.", Toast.LENGTH_LONG).show();
                    ActivityCompat.requestPermissions(IntroActivity.this,
                            new String[]{Manifest.permission.CAMERA},
                            MY_PERMISSIONS_REQUEST_CAMERA);
                }
            }
            case MY_PERMISSIONS_REQUEST_INTERNET: {

                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    FirebaseAuth mAuth = FirebaseAuth.getInstance();
                    if (mAuth.getCurrentUser() == null) {
                        finish();
                        startActivity(new Intent(IntroActivity.this, SignIn.class));
                    } else {
                        Toast.makeText(getApplicationContext(), "Login Complete!", Toast.LENGTH_SHORT).show();
                        finish();
                        startActivity(new Intent(IntroActivity.this, MainMenu.class));
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Grant Permissions to continue.", Toast.LENGTH_LONG).show();
                    if (ContextCompat.checkSelfPermission(IntroActivity.this,
                            Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                        flag = true;
                        ActivityCompat.requestPermissions(IntroActivity.this,
                                new String[]{Manifest.permission.CAMERA},
                                MY_PERMISSIONS_REQUEST_CAMERA);
                    }
                }
            }
        }
    }
}






