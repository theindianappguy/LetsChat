package com.theindianappguy.letschat.LoginAndRegister;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import com.theindianappguy.letschat.R;

public class LoginOrRegisterActivity extends AppCompatActivity implements Animation.AnimationListener{

    /*UI Components*/
    RelativeLayout facebookLogin, googleLogin, emailLogin;
    ProgressBar progressBar;
    TextView register;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_or_register);

        facebookLogin = findViewById(R.id.facebookLogin);
        googleLogin = findViewById(R.id.googleLogin);
        progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.GONE);
        register = findViewById(R.id.register);

        emailLogin = findViewById(R.id.emailLogin);
        emailLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(LoginOrRegisterActivity.this,LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginOrRegisterActivity.this,RegisterActivity.class);
                startActivity(intent);
                finish();
            }
        });


    }

    @Override
    public void onAnimationStart(Animation animation) {

    }

    @Override
    public void onAnimationEnd(Animation animation) {

    }

    @Override
    public void onAnimationRepeat(Animation animation) {

    }

}
