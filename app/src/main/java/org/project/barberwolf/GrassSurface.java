package org.project.barberwolf;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.ArrayList;
import java.util.List;


public class GrassSurface extends SurfaceView implements SurfaceHolder.Callback {
    private GrassThread gameThread;

    private final List<Grass> grassList = new ArrayList<Grass>();

    public GrassSurface(Context context)  {
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
        Bitmap chibiBitmap1 = BitmapFactory.decodeResource(this.getResources(),R.drawable.grass);
        Grass chibi1 = new Grass(this,chibiBitmap1,0,this.getHeight() - chibiBitmap1.getHeight());
        Grass chibi2 = new Grass(this,chibiBitmap1, chibiBitmap1.getWidth(),this.getHeight() - chibiBitmap1.getHeight());
        Grass chibi3 = new Grass(this,chibiBitmap1,2*chibiBitmap1.getWidth(),this.getHeight() - chibiBitmap1.getHeight());
        Grass chibi4 = new Grass(this,chibiBitmap1,3*chibiBitmap1.getWidth(),this.getHeight() - chibiBitmap1.getHeight());
        Grass chibi5 = new Grass(this,chibiBitmap1,4*chibiBitmap1.getWidth(),this.getHeight() - chibiBitmap1.getHeight());
        Grass chibi6 = new Grass(this,chibiBitmap1,5*chibiBitmap1.getWidth(),this.getHeight() - chibiBitmap1.getHeight());
        System.out.println(chibi1.getWidth());
        this.grassList.add(chibi1);
        this.grassList.add(chibi2);
        this.grassList.add(chibi3);
        this.grassList.add(chibi4);
        this.grassList.add(chibi5);
        this.grassList.add(chibi6);


        this.gameThread = new GrassThread(this, holder);
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