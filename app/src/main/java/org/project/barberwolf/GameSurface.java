package org.project.barberwolf;

import android.annotation.SuppressLint;
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
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameSurface extends SurfaceView implements SurfaceHolder.Callback {
    private GameThread gameThread;
    private GameField gameField;
    private final List<Grass> grassList = new ArrayList<>();
    private Wolf wolf = null;
    private final List<Obstacle> obstaclesList = new ArrayList<>();
    private PoofCloud poofCloud = null;

    public Bitmap grassBitmap = BitmapFactory
            .decodeResource(this.getResources(), R.drawable.grass);
    public Bitmap obstacleBitmap = BitmapFactory
            .decodeResource(this.getResources(), R.drawable.obtacle);
    public Bitmap sheepBitmap = BitmapFactory
            .decodeResource(this.getResources(), R.drawable.sheep);
    public Bitmap cutSheepBitmap = BitmapFactory
            .decodeResource(this.getResources(), R.drawable.cut_sheep);
    public Bitmap backgroundBitmap = BitmapFactory
            .decodeResource(this.getResources(), R.drawable.background);
    public Bitmap backgroundTreesBitmap = BitmapFactory
            .decodeResource(this.getResources(), R.drawable.background_trees);
    public Bitmap poofBitmap = BitmapFactory.decodeResource(this.getResources(), R.drawable.poof);
    private int obstacleWidth = 0;

    int health = this.getResources().getInteger(R.integer.maxHealth);
    int score = this.getResources().getInteger(R.integer.initScore);

    private SoundPool soundPool;
    private boolean soundPoolLoaded = false;
    private MediaPlayer backgroundSound;
    private ArrayList<Integer> sheepSoundIds = new ArrayList<>();
    private ArrayList<Integer> wolfSoundIds = new ArrayList<>();

    private boolean pause = false;
    private boolean gameOver = false;

    private boolean makePoof = false;

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
        this.gameField = (GameField) this.getContext();
    }

    public void initSurface() {
        grassList.clear();

        grassList.add(new Grass(grassBitmap, 0, getHeight()
                - grassBitmap.getHeight()));
        grassList.add(new Grass(grassBitmap, grassBitmap.getWidth(),
                getHeight() - grassBitmap.getHeight()));
        grassList.add(new Grass(grassBitmap, 2 * grassBitmap.getWidth(),
                getHeight() - grassBitmap.getHeight()));
        grassList.add(new Grass(grassBitmap, 3 * grassBitmap.getWidth(),
                getHeight() - grassBitmap.getHeight()));
        grassList.add(new Grass(grassBitmap, 4 * grassBitmap.getWidth(),
                getHeight() - grassBitmap.getHeight()));
        grassList.add(new Grass(grassBitmap, 5 * grassBitmap.getWidth(),
                getHeight() - grassBitmap.getHeight()));
        grassList.add(new Grass(grassBitmap, 6 * grassBitmap.getWidth(),
                getHeight() - grassBitmap.getHeight()));
        grassList.add(new Grass(grassBitmap, 7 * grassBitmap.getWidth(),
                getHeight() - grassBitmap.getHeight()));
        grassList.add(new Grass(grassBitmap, 8 * grassBitmap.getWidth(),
                getHeight() - grassBitmap.getHeight()));
        grassList.add(new Grass(grassBitmap, 9 * grassBitmap.getWidth(),
                getHeight() - grassBitmap.getHeight()));

        Bitmap wolfBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.wolf);

        this.wolf = new Wolf(this, wolfBitmap,
                getResources().getInteger(R.integer.wolfInitX),
                getHeight() - grassBitmap.getHeight() - wolfBitmap.getHeight() / 2);
        this.poofCloud = new PoofCloud(this, poofBitmap, -100, -100);

        int coefficient = getResources().getInteger(R.integer.obstacleNormalCoef);
        int offset = getResources().getInteger(R.integer.obstacleOffset);

        setObstacleWidth(wolfBitmap.getWidth() * 3 / coefficient * offset + offset);

        obstaclesList.clear();

        Obstacle newObstacle1 = new Obstacle(this, obstacleBitmap, 5 * obstacleWidth,
                this.getHeight() - grassBitmap.getHeight() - obstacleBitmap.getHeight());
        this.obstaclesList.add(newObstacle1);

        Obstacle newObstacle2 = new Obstacle(this, obstacleBitmap, 7 * obstacleWidth,
                this.getHeight() - grassBitmap.getHeight() - obstacleBitmap.getHeight());
        this.obstaclesList.add(newObstacle2);

        Obstacle newObstacle3 = new Obstacle(this, obstacleBitmap, 9 * obstacleWidth,
                this.getHeight() - grassBitmap.getHeight() - obstacleBitmap.getHeight());
        this.obstaclesList.add(newObstacle3);

        health = getResources().getInteger(R.integer.maxHealth);
        score = getResources().getInteger(R.integer.initScore);

        hideMessage();

        pause = false;
        gameOver = false;
    }

    public void initPoofCloud(int obstacleX, int obstacleY) {
        makePoof = true;
        poofCloud.x = obstacleX - (poofBitmap.getWidth() / 16 - sheepBitmap.getWidth()) / 2;
        poofCloud.y = obstacleY - (poofBitmap.getHeight() - sheepBitmap.getHeight()) / 2;
    }

    public void setMakePoof(boolean flag) {
        makePoof = flag;
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
            float leftVolume = 0.15f;
            float rightVolume = 0.15f;

            int index = getRandomNumber(sheepSoundIds.size());
            this.soundPool.play(this.sheepSoundIds
                    .get(index), leftVolume, rightVolume, 1, 0, 1f);
        }
    }

    public void playSoundCryingWolf() {
        if (this.soundPoolLoaded) {
            float leftVolume = 0.15f;
            float rightVolume = 0.15f;

            int index = getRandomNumber(wolfSoundIds.size());
            this.soundPool.play(this.wolfSoundIds
                    .get(index), leftVolume, rightVolume, 1, 0, 1f);
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {
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
        if (health > 0) {
            --health;
        }
        if (health <= 0) {
            setGameOver();
        }
    }

    public void increaseScore() {
        score += 10;
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
        if (pause || gameOver) {
            return;
        }

        for (Grass grassElement : grassList) {
            grassElement.update();
        }

        wolf.update();

        for (Obstacle obstacleElement : obstaclesList) {
            obstacleElement.update();
        }
        if (makePoof) {
            poofCloud.update();
        }

    }

    public void printMessage(String messageString) {
        TextView message = gameField.getMessageView();
        message.setText(messageString);
        message.setVisibility(View.VISIBLE);
    }

    public void hideMessage(){
        TextView message = gameField.getMessageView();
        message.setVisibility(View.GONE);
    }

    public void setPause() {
        if (!gameOver) {
            printMessage(getResources().getString(R.string.gamePause));
            pause = true;
        }
    }

    public void setResume() {
        if (pause) {
            hideMessage();
            pause = false;
        }
    }

    public void setGameOver() {
        printMessage(getResources().getString(R.string.gameOver));
        gameOver = true;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        canvas.drawBitmap(backgroundBitmap, 0, 0, null);
        canvas.drawBitmap(backgroundTreesBitmap, 0,
                this.getHeight() - backgroundTreesBitmap.getHeight(), null);
        GameField field = (GameField) this.getContext();

        field.getHealthView().setText("Health: " + this.health);
        field.getScoreView().setText("Score: " + this.score);

        for (Grass grassElement : grassList) {
            grassElement.draw(canvas);
        }

        wolf.draw(canvas);
        for (Obstacle obstacleElement : obstaclesList) {
            obstacleElement.draw(canvas);
        }

        if (makePoof) {
            poofCloud.draw(canvas);
        }
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        initSurface();
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
