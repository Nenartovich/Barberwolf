package org.project.barberwolf;

import android.graphics.Bitmap;
import android.graphics.Canvas;

public class Grass extends GameObject {

    private Bitmap bitmap;

    private int movingVectorX = -20;
    private int movingVectorY = 0;

    private GameSurface gameSurface;

    final static int grassMaxCoef = 9;

    public Grass(GameSurface gameSurface, Bitmap image, int x, int y) {
        super(image, 1, 1, x, y);

        this.gameSurface = gameSurface;

        this.bitmap = this.createSubImageAt(0, 0);
    }

    public Bitmap getBitmap() {
        return this.bitmap;

    }


    public void update() {
        this.x = x + movingVectorX;
        this.y = y + movingVectorY;

        if (this.x <= -this.getWidth()) {
            this.x = this.getWidth() * grassMaxCoef;
        }
    }

    public void draw(Canvas canvas) {
        Bitmap bitmap = this.getBitmap();
        canvas.drawBitmap(bitmap, x, y, null);
    }
}
