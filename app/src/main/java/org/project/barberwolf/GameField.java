package org.project.barberwolf;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.SurfaceHolder;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class GameField extends AppCompatActivity implements SurfaceHolder.Callback {
    public static final int PRINT_MESSAGE = 1;
    public static final int HIDE_MESSAGE = 2;
    public static final int PRINT_HEALTH = 3;
    public static final int PRINT_SCORE = 4;

    private GameSurface gameSurface;
    private GameThread gameThread;
    private TextView messageView;
    private TextView healthView;
    private TextView scoreView;
    private Handler fieldHandler;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.setContentView(R.layout.game_field);

        gameSurface = this.findViewById(R.id.gameSurface);
        gameSurface.getHolder().addCallback(this);

        messageView = this.findViewById(R.id.message);
        healthView = this.findViewById(R.id.health);
        scoreView = this.findViewById(R.id.score);

        fieldHandler = new Handler(Looper.getMainLooper()) {
            @SuppressLint("SetTextI18n")
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case PRINT_MESSAGE:
                        messageView.setText((String) msg.obj);
                        messageView.setVisibility(View.VISIBLE);
                        break;
                    case HIDE_MESSAGE:
                        messageView.setVisibility(View.GONE);
                        break;
                    case PRINT_HEALTH:
                        healthView.setText("Health: " + msg.obj);
                        break;
                    case PRINT_SCORE:
                        scoreView.setText("Score: " + msg.obj);
                }
            }
        };
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        gameThread = new GameThread(gameSurface, holder, fieldHandler);
        gameThread.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        gameSurface.destroy();
        gameThread.quit();
        gameSurface = null;
        gameThread = null;
    }

    public void onPauseClick(View view) {
        gameThread.pauseGame();
    }

    public void onResumeClick(View view) {
        gameThread.resumeGame();
    }

    public void onRestartClick(View view) {
        gameThread.restartGame();
    }
}
