package org.project.barberwolf;


import android.graphics.Bitmap;
import android.graphics.Canvas;

import java.util.List;

public class PoofCloud extends GameObject {

    private int colUsing = 0;

    private Bitmap[] poofBitmaps;


    private int movingVectorX = -20;
    private int movingVectorY = 0;

    private GameSurface gameSurface;

    public PoofCloud(GameSurface gameSurface, Bitmap image, int x, int y) {
        super(image, 1, 16, x, y);

        this.gameSurface = gameSurface;

        this.poofBitmaps = new Bitmap[colCount];

        for (int col = 0; col < this.colCount; ++col) {
            this.poofBitmaps[col] = this.createSubImageAt(0, col);
        }
    }

    public Bitmap getCurrentMoveBitmap() {
        return poofBitmaps[this.colUsing];
    }


    public void update() {
        ++this.colUsing;
        if (colUsing >= this.colCount) {
            colUsing = 0;
            gameSurface.setMakePoof(false);
        }

        this.x = x + movingVectorX;
        this.y = y + movingVectorY;
    }

    public void draw(Canvas canvas) {
        Bitmap bitmap = this.getCurrentMoveBitmap();
        canvas.drawBitmap(bitmap, x, y, null);
    }
}
