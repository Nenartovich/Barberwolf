package org.project.barberwolf;

import android.graphics.Bitmap;
import android.graphics.Canvas;

public class Background extends GameObject {
    private Bitmap bitmap;
    private GameSurface gameSurface;

    public Background(GameSurface gameSurface, Bitmap image, int x, int y) {
        super(image, 1, 1, x, y);
        this.gameSurface = gameSurface;
        this.bitmap = this.createSubImageAt(0, 0);
    }

    public void draw(Canvas canvas) {
        canvas.drawBitmap(gameSurface.backgroundBitmap, x, y, null);
        canvas.drawBitmap(gameSurface.restartButtonBitmap,
                gameSurface.getWidth() - gameSurface.restartButtonBitmap.getWidth() - 10,
                10, null);
        canvas.drawBitmap(gameSurface.playButtonBitmap,
                gameSurface.getWidth() - gameSurface.restartButtonBitmap.getWidth() - 10
                -gameSurface.playButtonBitmap.getWidth() - 10,
                10, null);
        canvas.drawBitmap(gameSurface.pauseButtonBitmap,
                gameSurface.getWidth() - gameSurface.restartButtonBitmap.getWidth() - 10
                        -gameSurface.playButtonBitmap.getWidth() - 10
                - gameSurface.pauseButtonBitmap.getWidth() - 15,
                10, null);



    }
}
