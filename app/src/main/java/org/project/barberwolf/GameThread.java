package org.project.barberwolf;

import android.graphics.Canvas;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.view.SurfaceHolder;

public class GameThread extends HandlerThread implements Handler.Callback {
    private static final int INIT = -1;
    private static final int TICK = 0;
    private static final int PAUSE = 1;
    private static final int RESUME = 2;
    private static final int RESTART = 3;
    private static final int SET_VOLUME = 4;

    private GameSurface gameSurface;
    private SurfaceHolder surfaceHolder;
    private Handler messageReceiver;
    private Handler fieldHandler;

    public GameThread(GameSurface gameSurface, SurfaceHolder surfaceHolder, Handler fieldHandler) {
        super("GameThread");
        this.gameSurface = gameSurface;
        this.surfaceHolder = surfaceHolder;
        this.fieldHandler = fieldHandler;
    }

    @Override
    protected void onLooperPrepared() {
        messageReceiver = new Handler(getLooper(), this);
        messageReceiver.sendEmptyMessage(INIT);
    }

    @Override
    public boolean handleMessage(Message msg) {
        switch (msg.what) {
            case INIT:
                gameSurface.setFieldHandler(fieldHandler);
                gameSurface.initSurface();
                messageReceiver.sendEmptyMessage(TICK);
                break;
            case TICK:
                Canvas canvas = this.surfaceHolder.lockCanvas();
                if (canvas == null) {
                    break;
                }
                this.gameSurface.update();
                this.gameSurface.draw(canvas);
                this.surfaceHolder.unlockCanvasAndPost(canvas);

                try {
                    sleep(2);  // TODO: affects fps
                } catch (InterruptedException ignored) {
                }

                messageReceiver.sendEmptyMessage(TICK);
                break;
            case PAUSE:
                gameSurface.setPause();
                break;
            case RESUME:
                gameSurface.setResume();
                break;
            case RESTART:
                gameSurface.initSurface();
                break;
            case SET_VOLUME:
                gameSurface.setVolume((int) msg.obj);
                break;
        }

        return true;
    }

    @Override
    public boolean quit() {
        messageReceiver.removeCallbacksAndMessages(null);
        return super.quit();
    }

    public void pauseGame() {
        messageReceiver.sendEmptyMessage(PAUSE);
    }

    public void resumeGame() {
        messageReceiver.sendEmptyMessage(RESUME);
    }

    public void restartGame() {
        messageReceiver.sendEmptyMessage(RESTART);
    }

    public void setVolume(int volume) {
        Message message = Message.obtain(messageReceiver, SET_VOLUME, volume);
        messageReceiver.sendMessage(message);
    }
}
