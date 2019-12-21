package com.theindianappguy.letschat;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class AddProfile extends AppCompatActivity {

    TextView continueBtn, choose_proofImg;
    ImageView proofImgView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_profile);

        continueBtn = findViewById(R.id.continueBtn);
        proofImgView = findViewById(R.id.proofImgView);

        continueBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }
}
