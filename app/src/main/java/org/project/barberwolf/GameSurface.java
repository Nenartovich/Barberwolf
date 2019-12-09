package org.project.barberwolf;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.media.AudioAttributes;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.nio.channels.NonReadableChannelException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameSurface extends SurfaceView implements SurfaceHolder.Callback {
    private GameThread gameThread;
    private Background background = null;
    private HealthIndicator healthIndicator = null;
    private ScoreIndicator scoreIndicator = null;
    private GameOverIndicator gameOverIndicator = null;
    private PauseIndicator pauseIndicator = null;
    private final List<Grass> grassList = new ArrayList<>();
    private Wolf wolf = null;
    private final List<Obstacle> obstaclesList = new ArrayList<>();

    public Bitmap grassBitmap = BitmapFactory.decodeResource(this.getResources(), R.drawable.grass);
    public Bitmap obstacleBitmap = BitmapFactory.decodeResource(this.getResources(), R.drawable.obtacle);
    public Bitmap sheepBitmap = BitmapFactory.decodeResource(this.getResources(), R.drawable.sheep);
    public Bitmap cutSheepBitmap = BitmapFactory.decodeResource(this.getResources(), R.drawable.cut_sheep);
    public Bitmap backgroundBitmap = BitmapFactory.decodeResource(this.getResources(), R.drawable.background);
    public Bitmap pauseButtonBitmap = BitmapFactory.decodeResource(this.getResources(), R.drawable.pause_button);
    public Bitmap playButtonBitmap = BitmapFactory.decodeResource(this.getResources(), R.drawable.play_button);
    public Bitmap restartButtonBitmap = BitmapFactory.decodeResource(this.getResources(), R.drawable.restart_button);

    private int obstacleWidth = 0;

    final static int wolfPositionX = 250;
    final static int obstacleNormalCoef = 240;
    final static int obstacleOffset = 20;

    final static int initialHealth = 100;
    final static int initialScore = 0;

    private SoundPool soundPool;
    private boolean soundPoolLoaded = false;
    private MediaPlayer backgroundSound;
    private ArrayList<Integer> sheepSoundIds = new ArrayList<>();
    private ArrayList<Integer> wolfSoundIds = new ArrayList<>();

    final static float pauseButtonBoundX = 1850;
    final static float pauseButtonBoundY = 150;

    private boolean pausePressed = false;
    final static float stopPauseButtonBoundX = 1850;
    final static float stopPauseButtonBoundYMin = 200;
    final static float stopPauseButtonBoundYMax = 350;

    float restartButtonBoundX = 1850;
    final static float restartButtonBoundYMin = 400;
    final static float restartButtonBoundYMax = 550;

    final static float backToMainMenuButtonBoundX = 1850;
    final static float backToMainMenuButtonBoundYMin = 600;
    final static float backToMainMenuButtonBoundYMax = 750;

    private boolean gameOver = false;

    public GameSurface(Context context) {
        this(context, null);
    }

    public GameSurface(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public GameSurface(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.setFocusable(true);
        this.getHolder().addCallback(this);
        this.initSoundPool();

    }

    private void initSoundPool() {
        AudioAttributes audioAttrib = new AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_GAME)
                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                .build();
        SoundPool.Builder builder = new SoundPool.Builder();
        builder.setAudioAttributes(audioAttrib).setMaxStreams(100);

        this.soundPool = builder.build();

        this.soundPool.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener() {
            @Override
            public void onLoadComplete(SoundPool soundPool, int sampleId, int status) {
                soundPoolLoaded = true;
            }
        });

        backgroundSound = MediaPlayer.create(this.getContext(), R.raw.eminem);
        backgroundSound.setLooping(true);
        backgroundSound.setVolume(0.15f, 0.15f);
        backgroundSound.start();

        sheepSoundIds.add(this.soundPool.load(this.getContext(), R.raw.sheep_caught_1, 1));
        sheepSoundIds.add(this.soundPool.load(this.getContext(), R.raw.sheep_caught_2, 1));
        sheepSoundIds.add(this.soundPool.load(this.getContext(), R.raw.sheep_caught_3, 1));
        sheepSoundIds.add(this.soundPool.load(this.getContext(), R.raw.sheep_caught_4, 1));
        sheepSoundIds.add(this.soundPool.load(this.getContext(), R.raw.sheep_caught_5, 1));
        wolfSoundIds.add(this.soundPool.load(this.getContext(), R.raw.crying_wolf_1, 1));
        wolfSoundIds.add(this.soundPool.load(this.getContext(), R.raw.crying_wolf_2, 1));
        wolfSoundIds.add(this.soundPool.load(this.getContext(), R.raw.crying_wolf_3, 1));
        wolfSoundIds.add(this.soundPool.load(this.getContext(), R.raw.crying_wolf_4, 1));
        wolfSoundIds.add(this.soundPool.load(this.getContext(), R.raw.crying_wolf_5, 1));
        wolfSoundIds.add(this.soundPool.load(this.getContext(), R.raw.crying_wolf_6, 1));
        wolfSoundIds.add(this.soundPool.load(this.getContext(), R.raw.crying_wolf_7, 1));
        wolfSoundIds.add(this.soundPool.load(this.getContext(), R.raw.crying_wolf_8, 1));
    }

    public int getRandomNumber(int bound) {
        Random random = new Random();
        return random.nextInt(bound);
    }

    public void playSoundSheepCaught() {
        if (this.soundPoolLoaded) {
            float leftVolume = 0.8f;
            float rightVolume = 0.8f;

            int index = getRandomNumber(sheepSoundIds.size());
            this.soundPool.play(this.sheepSoundIds.get(index), leftVolume, rightVolume, 1, 0, 1f);
        }
    }

    public void playSoundCryingWolf() {
        if (this.soundPoolLoaded) {
            float leftVolume = 0.8f;
            float rightVolume = 0.8f;

            int index = getRandomNumber(wolfSoundIds.size());
            this.soundPool.play(this.wolfSoundIds.get(index), leftVolume, rightVolume, 1, 0, 1f);
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getX() >= pauseButtonBoundX && event.getY() <= pauseButtonBoundY) {
            pausePressed = true;
            return true;
        }

        if (event.getX() >= stopPauseButtonBoundX && event.getY() >= stopPauseButtonBoundYMin &&
                event.getY() <= stopPauseButtonBoundYMax) {
            pausePressed = false;
            return true;
        }

        if (event.getX() >= restartButtonBoundX && event.getY() >= restartButtonBoundYMin &&
                event.getY() <= restartButtonBoundYMax) {
            ((Activity) getContext()).recreate();
            return true;
        }

        if (event.getX() >= backToMainMenuButtonBoundX && event.getY() >= backToMainMenuButtonBoundYMin &&
                event.getY() <= backToMainMenuButtonBoundYMax) {
            ((Activity) getContext()).finish();
            return true;
        }


        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            wolf.setOldY(event.getY());
        }
        if (event.getAction() == MotionEvent.ACTION_MOVE) {
            if (wolf.getOldY() < event.getY() && !wolf.getMoveFlag()) {
                wolf.setMoveFlag(true);
                wolf.setDownSwipeFlag(true);
                wolf.setRowUsing(1);
            }
        }
        if (event.getAction() == MotionEvent.ACTION_UP) {
            if (wolf.getDownSwipeFlag()) {
                wolf.setDownSwipeFlag(false);
                wolf.setMoveFlag(false);
                wolf.setRowUsing(0);
            } else {
                wolf.setNewY(event.getY());
                if (wolf.getOldY() > wolf.getNewY() && !wolf.getDownSwipeFlag()) {
                    wolf.setJumpPhase1(true);
                    wolf.setJumpFlag(true);
                }
            }
        }

        return true;
    }

    public void reduceHealth() {
        healthIndicator.value = healthIndicator.value - 1;
    }

    public void increaseScore() {
        scoreIndicator.value = scoreIndicator.value + 10;
    }

    public void setObstaclesList(List<Obstacle> list) {
        for (int i = 0; i < list.size(); i++) {
            obstaclesList.get(i).x = list.get(i).getX();
            obstaclesList.get(i).y = list.get(i).getY();
        }
    }

    public List<Obstacle> getObstaclesList() {
        return obstaclesList;
    }

    public void update() {
        if (pausePressed || gameOver) {
            return;
        }

        for (Grass grassElement : grassList) {
            grassElement.update();
        }

        wolf.update();

        for (Obstacle obstacleElement : obstaclesList) {
            obstacleElement.update();
        }
    }


    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        background.draw(canvas);

        if (gameOver) {
            gameOverIndicator.draw(canvas);
        } else if (pausePressed) {
            pauseIndicator.draw(canvas);
        }

        if (healthIndicator.value <= 0) {
            gameOver = true;
        }

        healthIndicator.draw(canvas);
        scoreIndicator.draw(canvas);
        for (Grass grassElement : grassList) {
            grassElement.draw(canvas);
        }
        wolf.draw(canvas);
        for (Obstacle obstacleElement : obstaclesList) {
            obstacleElement.draw(canvas);
        }
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        this.background = new Background(this, backgroundBitmap, 0, this.getHeight()
                - backgroundBitmap.getHeight());

        this.healthIndicator = new HealthIndicator(initialHealth, 50, 100);
        this.scoreIndicator = new ScoreIndicator(initialScore, 500, 100);
        this.gameOverIndicator = new GameOverIndicator(this,500, 600);
        this.pauseIndicator = new PauseIndicator(500, 600);

        this.grassList.add(new Grass(grassBitmap, 0, this.getHeight()
                - grassBitmap.getHeight()));
        this.grassList.add(new Grass(grassBitmap, grassBitmap.getWidth(),
                this.getHeight() - grassBitmap.getHeight()));
        this.grassList.add(new Grass(grassBitmap, 2 * grassBitmap.getWidth(),
                this.getHeight() - grassBitmap.getHeight()));
        this.grassList.add(new Grass(grassBitmap, 3 * grassBitmap.getWidth(),
                this.getHeight() - grassBitmap.getHeight()));
        this.grassList.add(new Grass(grassBitmap, 4 * grassBitmap.getWidth(),
                this.getHeight() - grassBitmap.getHeight()));
        this.grassList.add(new Grass(grassBitmap, 5 * grassBitmap.getWidth(),
                this.getHeight() - grassBitmap.getHeight()));
        this.grassList.add(new Grass(grassBitmap, 6 * grassBitmap.getWidth(),
                this.getHeight() - grassBitmap.getHeight()));
        this.grassList.add(new Grass(grassBitmap, 7 * grassBitmap.getWidth(),
                this.getHeight() - grassBitmap.getHeight()));
        this.grassList.add(new Grass(grassBitmap, 8 * grassBitmap.getWidth(),
                this.getHeight() - grassBitmap.getHeight()));
        this.grassList.add(new Grass(grassBitmap, 9 * grassBitmap.getWidth(),
                this.getHeight() - grassBitmap.getHeight()));

        Bitmap wolfBitmap = BitmapFactory.decodeResource(this.getResources(), R.drawable.wolf);
        this.wolf = new Wolf(this, wolfBitmap, wolfPositionX, this.getHeight() -
                grassBitmap.getHeight() - wolfBitmap.getHeight() / 2);


        setObstacleWidth(wolfBitmap.getWidth() * 3 / obstacleNormalCoef * obstacleOffset + obstacleOffset);
        Obstacle newObstacle1 = new Obstacle(this, obstacleBitmap, 5 * obstacleWidth,
                this.getHeight() - grassBitmap.getHeight() - obstacleBitmap.getHeight());
        this.obstaclesList.add(newObstacle1);

        Obstacle newObstacle2 = new Obstacle(this, obstacleBitmap, 7 * obstacleWidth,
                this.getHeight() - grassBitmap.getHeight() - obstacleBitmap.getHeight());
        this.obstaclesList.add(newObstacle2);

        Obstacle newObstacle3 = new Obstacle(this, obstacleBitmap, 9 * obstacleWidth,
                this.getHeight() - grassBitmap.getHeight() - obstacleBitmap.getHeight());
        this.obstaclesList.add(newObstacle3);

        this.gameThread = new GameThread(this, holder);
        this.gameThread.setRunning(true);
        this.gameThread.start();
    }


    public void setObstacleWidth(int width) {
        obstacleWidth = width;
    }

    public int getObstacleWidth() {
        return obstacleWidth;
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        // Empty method
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        try {
            backgroundSound.stop();
            for (int index : wolfSoundIds) {
                soundPool.stop(index);
            }
            for (int index : sheepSoundIds) {
                soundPool.stop(index);
            }
            this.gameThread.setRunning(false);
            this.gameThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
