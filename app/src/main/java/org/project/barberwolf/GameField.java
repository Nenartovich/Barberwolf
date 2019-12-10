package org.project.barberwolf;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class GameField extends AppCompatActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.setContentView(R.layout.game_field);

        ImageButton pauseButton = findViewById(R.id.pauseButton);
        ImageButton playButton = findViewById(R.id.playButton);
        ImageButton restartButton = findViewById(R.id.restartButton);

        pauseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GameField.this.getGameSurface().setPause();
            }
        });

        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GameField.this.getGameSurface().setResume();
            }
        });

        restartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GameField.this.getGameSurface().initSurface();
            }
        });

    }

    GameSurface getGameSurface() {
        return findViewById(R.id.gameSurface);
    }

    TextView getHealthView() {
        return findViewById(R.id.health);
    }

    TextView getScoreView() {
        return findViewById(R.id.score);
    }

    TextView getMessageView() {
        return findViewById(R.id.message);
    }
}
