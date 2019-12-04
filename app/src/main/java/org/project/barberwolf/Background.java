package org.project.barberwolf;

import android.graphics.Bitmap;
import android.graphics.Canvas;

public class Background extends GameObject {
    private Bitmap bitmap;

    public Background(Bitmap image, int x, int y) {
        super(image, 1, 1, x, y);

        this.bitmap = this.createSubImageAt(0, 0);
    }

    public Bitmap getBitmap() {
        return this.bitmap;
    }

    public void draw(Canvas canvas) {
        Bitmap bitmap = this.getBitmap();
        canvas.drawBitmap(bitmap, x, y, null);
    }
}
