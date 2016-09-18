package com.longtrinh.memorygame;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{


    private Button playButton;
    private Button rulesButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        playButton = (Button) findViewById(R.id.playButton);
        rulesButton = (Button) findViewById(R.id.rulesButton);

        playButton.setOnClickListener(this);
        rulesButton.setOnClickListener(this);

    }

    @Override
    public void onClick(View v){
        if(v.getId() == R.id.playButton){
            Intent playIntent = new Intent(this, PlayActivity.class);
            startActivity(playIntent);
        }else if(v.getId() == R.id.rulesButton){
            Intent rulesIntent = new Intent(this, RulesActivity.class);
            startActivity(rulesIntent);
        }
    }
}
