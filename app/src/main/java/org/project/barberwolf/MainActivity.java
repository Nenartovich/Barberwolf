package org.project.barberwolf;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        System.err.println(findViewById(R.id.newGameButton));
        final Button newGameButton = findViewById(R.id.newGameButton);
        Button settingsButton = findViewById(R.id.settingsButton);
        Button exitButton = findViewById(R.id.exitButton);


        newGameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!GameField.active) {
                    Intent intent = new Intent(MainActivity.this, GameField.class);
                    startActivity(intent);
                }
            }
        });

        settingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Go to the settings screen
            }
        });

        exitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.exit(1);
            }
        });
    }
}
