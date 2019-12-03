package org.project.barberwolf;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.ArrayList;
import java.util.List;

public class GameSurface extends SurfaceView implements SurfaceHolder.Callback {
    private GameThread gameThread;

    private Background background = null;
    private HealthIndicator healthIndicator = null;
    private ScoreIndicator scoreIndicator = null;
    private final List<Grass> grassList = new ArrayList<>();
    private Wolf wolf = null;
    private final List<Obstacle> obstaclesList = new ArrayList<>();

    public Bitmap grassBitmap = BitmapFactory.decodeResource(this.getResources(), R.drawable.grass);
    public Bitmap obstacleBitmap = BitmapFactory.decodeResource(this.getResources(), R.drawable.obtacle);
    public Bitmap sheepBitmap = BitmapFactory.decodeResource(this.getResources(), R.drawable.sheep);
    public Bitmap backgroundBitmap = BitmapFactory.decodeResource(this.getResources(), R.drawable.background);

    private int obstacleWidth = 0;

    final static int wolfPositionX = 250;
    final static int obstacleNormalCoef = 240;
    final static int obstacleOffset = 20;
    
    public GameSurface(Context context) {
        super(context);
        this.setFocusable(true);
        this.getHolder().addCallback(this);
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
        healthIndicator.value = healthIndicator.value - 1;
    }

    public void increaseScore() {
        scoreIndicator.value = scoreIndicator.value + 1;
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

        this.healthIndicator = new HealthIndicator(this, 5, 100, 100);
        this.scoreIndicator = new ScoreIndicator(this, 5, 1000, 100);

        this.grassList.add(new Grass(this, grassBitmap, 0, this.getHeight()
                - grassBitmap.getHeight()));
        this.grassList.add(new Grass(this, grassBitmap, grassBitmap.getWidth(),
                this.getHeight() - grassBitmap.getHeight()));
        this.grassList.add(new Grass(this, grassBitmap, 2 * grassBitmap.getWidth(),
                this.getHeight() - grassBitmap.getHeight()));
        this.grassList.add(new Grass(this, grassBitmap, 3 * grassBitmap.getWidth(),
                this.getHeight() - grassBitmap.getHeight()));
        this.grassList.add(new Grass(this, grassBitmap, 4 * grassBitmap.getWidth(),
                this.getHeight() - grassBitmap.getHeight()));
        this.grassList.add(new Grass(this, grassBitmap, 5 * grassBitmap.getWidth(),
                this.getHeight() - grassBitmap.getHeight()));
        this.grassList.add(new Grass(this, grassBitmap, 6 * grassBitmap.getWidth(),
                this.getHeight() - grassBitmap.getHeight()));
        this.grassList.add(new Grass(this, grassBitmap, 7 * grassBitmap.getWidth(),
                this.getHeight() - grassBitmap.getHeight()));
        this.grassList.add(new Grass(this, grassBitmap, 8 * grassBitmap.getWidth(),
                this.getHeight() - grassBitmap.getHeight()));
        this.grassList.add(new Grass(this, grassBitmap, 9 * grassBitmap.getWidth(),
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
        while (true) {
            try {
                this.gameThread.setRunning(false);
                this.gameThread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
