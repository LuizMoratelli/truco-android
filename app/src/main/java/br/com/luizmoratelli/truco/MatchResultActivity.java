package br.com.luizmoratelli.truco;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class MatchResultActivity extends AppCompatActivity {

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match_result);
        Bundle extras = getIntent().getExtras();

        boolean playerWon = extras.getBoolean("PLAYER_WON");
        Button button = findViewById(R.id.buttonReplay);
        button.setBackgroundTintList(ContextCompat.getColorStateList(this, android.R.color.darker_gray));
        button.setBackgroundTintList(ContextCompat.getColorStateList(this, playerWon ? R.color.green : R.color.purple));

        if (!playerWon) {
            ImageView imgView = findViewById(R.id.imageView2);
            imgView.setImageResource(R.drawable.loose);
            TextView txtView = findViewById(R.id.textView);
            txtView.setText(R.string.activity_lose);
        }

        Context context = this;

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.CreateNewGame();
                finish();
                //startActivity(intent);
            }
        });
    }
}
