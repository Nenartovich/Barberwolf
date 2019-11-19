package org.project.barberwolf;


import android.graphics.Canvas;
import android.view.SurfaceHolder;

public class GameThread extends Thread {

    private boolean running;
    private GameSurface gameSurface;
    private SurfaceHolder surfaceHolder;

    public GameThread(GameSurface gameSurface, SurfaceHolder surfaceHolder)  {
        this.gameSurface= gameSurface;
        this.surfaceHolder= surfaceHolder;
    }

    @Override
    public void run()  {

        while(running)  {
            Canvas canvas= null;
            try {
                // Get Canvas from Holder and lock it.
                canvas = this.surfaceHolder.lockCanvas();

                // Synchronized
                synchronized (canvas)  {
                    this.gameSurface.update();
                    this.gameSurface.draw(canvas);
                }
            }catch(Exception e)  {
                // Do nothing.
            } finally {
                if(canvas!= null)  {
                    // Unlock Canvas.
                    this.surfaceHolder.unlockCanvasAndPost(canvas);
                }
            }
            try {
                // Sleep.
                this.sleep(10);
            } catch(InterruptedException e)  {

            }

        }
    }

    public void setRunning(boolean running)  {
        this.running= running;
    }
}