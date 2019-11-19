package org.project.barberwolf;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.ArrayList;
import java.util.List;

public class GameSurface extends SurfaceView implements SurfaceHolder.Callback {
    private GameThread gameThread;

    private final List<Grass> grassList = new ArrayList<Grass>();

    public GameSurface(Context context)  {
        super(context);

        this.setFocusable(true);

        this.getHolder().addCallback(this);
    }

    public void update()  {
        for(Grass grassElement: grassList) {
            grassElement.update();
        }
    }

    @Override
    public void draw(Canvas canvas)  {
        super.draw(canvas);
        for(Grass grassElement: grassList)  {
            grassElement.draw(canvas);
        }
    }

    // Implements method of SurfaceHolder.Callback
    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        Bitmap grassBitmap = BitmapFactory.decodeResource(this.getResources(),R.drawable.grass);
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
        this.grassList.add(grass1);
        this.grassList.add(grass2);
        this.grassList.add(grass3);
        this.grassList.add(grass4);
        this.grassList.add(grass5);
        this.grassList.add(grass6);


        this.gameThread = new GameThread(this, holder);
        this.gameThread.setRunning(true);
        this.gameThread.start();
    }

    // Implements method of SurfaceHolder.Callback
    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    // Implements method of SurfaceHolder.Callback
    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        boolean retry= true;
        while(retry) {
            try {
                this.gameThread.setRunning(false);

                // Parent thread must wait until the end of GameThread.
                this.gameThread.join();
            }catch(InterruptedException e)  {
                e.printStackTrace();
            }
            retry= true;
        }
    }
}
