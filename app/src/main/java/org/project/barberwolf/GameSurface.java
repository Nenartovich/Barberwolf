package org.project.barberwolf;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameSurface extends SurfaceView implements SurfaceHolder.Callback {
    private GameThread gameThread;

    private final List<Grass> grassList = new ArrayList<Grass>();
    private Wolf wolf = null;
    private final List<Obtacle> obtaclesList = new ArrayList<Obtacle>();

    public Bitmap grassBitmap = BitmapFactory.decodeResource(this.getResources(),R.drawable.grass);
    public Bitmap obtacleBitmap = BitmapFactory.decodeResource(this.getResources(),R.drawable.obtacle);
    public Bitmap sheepBitmap = BitmapFactory.decodeResource(this.getResources(), R.drawable.sheep);

    public int screenHeight = 0;
    public int grassHeight = grassBitmap.getHeight();


    private int obtacleWidth = 0;
    public GameSurface(Context context)  {
        super(context);
        this.setFocusable(true);
        this.getHolder().addCallback(this);
    }

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

    public void setObtaclesList(List<Obtacle> list) {
        for (int i = 0; i < list.size(); i++) {
            obtaclesList.get(i).x = list.get(i).getX();
            obtaclesList.get(i).y = list.get(i).getY();
        }
    }

    public List<Obtacle> getObtaclesList() {
        return obtaclesList;
    }
    public void update()  {
        for (Grass grassElement: grassList) {
            grassElement.update();
        }

        wolf.update();

        for (Obtacle obtacleElement: obtaclesList) {
            obtacleElement.update();
        }
    }

    @Override
    public void draw(Canvas canvas)  {
        super.draw(canvas);
        for(Grass grassElement: grassList)  {
            grassElement.draw(canvas);
        }
        wolf.draw(canvas);
        for (Obtacle obtacleElement: obtaclesList) {
            obtacleElement.draw(canvas);
        }
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {

        Grass grass1 = new Grass(this, grassBitmap,0,this.getHeight()
                - grassBitmap.getHeight());
        Grass grass2 = new Grass(this, grassBitmap, grassBitmap.getWidth(),
                this.getHeight() - grassBitmap.getHeight());
        Grass grass3 = new Grass(this, grassBitmap,2*grassBitmap.getWidth(),
                this.getHeight() - grassBitmap.getHeight());
        Grass grass4 = new Grass(this, grassBitmap,3*grassBitmap.getWidth(),
                this.getHeight() - grassBitmap.getHeight());
        Grass grass5 = new Grass(this, grassBitmap,4*grassBitmap.getWidth(),
                this.getHeight() - grassBitmap.getHeight());
        Grass grass6 = new Grass(this, grassBitmap,5*grassBitmap.getWidth(),
                this.getHeight() - grassBitmap.getHeight());
        Grass grass7 = new Grass(this, grassBitmap,6*grassBitmap.getWidth(),
                this.getHeight() - grassBitmap.getHeight());
        Grass grass8 = new Grass(this, grassBitmap,7*grassBitmap.getWidth(),
                this.getHeight() - grassBitmap.getHeight());
        Grass grass9 = new Grass(this, grassBitmap,8*grassBitmap.getWidth(),
                this.getHeight() - grassBitmap.getHeight());
        Grass grass10 = new Grass(this, grassBitmap,9*grassBitmap.getWidth(),
                this.getHeight() - grassBitmap.getHeight());
        this.grassList.add(grass1);
        this.grassList.add(grass2);
        this.grassList.add(grass3);
        this.grassList.add(grass4);
        this.grassList.add(grass5);
        this.grassList.add(grass6);
        this.grassList.add(grass7);
        this.grassList.add(grass8);
        this.grassList.add(grass9);
        this.grassList.add(grass10);

        Bitmap wolfBitmap = BitmapFactory.decodeResource(this.getResources(),R.drawable.wolf);
        Wolf newWolf = new Wolf(this, wolfBitmap,250,this.getHeight() -
                grassBitmap.getHeight() - wolfBitmap.getHeight() / 2);
        this.wolf = newWolf;

        int tmp = wolfBitmap.getWidth() * 3;
        tmp /= 240;
        tmp ++;
        tmp *= 20;
        setObtacleWidth(tmp);
        Obtacle newObtacle1 = new Obtacle(this, obtacleBitmap,5*obtacleWidth,
                this.getHeight() - grassBitmap.getHeight() - obtacleBitmap.getHeight());
        this.obtaclesList.add(newObtacle1);

        Obtacle newObtacle2 = new Obtacle(this, obtacleBitmap,7*obtacleWidth,
                this.getHeight() - grassBitmap.getHeight() - obtacleBitmap.getHeight());
        this.obtaclesList.add(newObtacle2);

        Obtacle newObtacle3 = new Obtacle(this, obtacleBitmap,9*obtacleWidth,
                this.getHeight() - grassBitmap.getHeight() - obtacleBitmap.getHeight());
        this.obtaclesList.add(newObtacle3);



        this.gameThread = new GameThread(this, holder);
        this.gameThread.setRunning(true);
        this.gameThread.start();
    }


    public void setObtacleWidth(int width) {
        obtacleWidth = width;
    }

    public int getObtacleWidth() {
        return obtacleWidth;
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        // Empty method
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        boolean retry= true;
        while(retry) {
            try {
                this.gameThread.setRunning(false);
                this.gameThread.join();
            }catch(InterruptedException e)  {
                e.printStackTrace();
            }
            retry= true;
        }
    }
}
