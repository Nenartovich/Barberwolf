package org.project.barberwolf;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class Background extends GameObject {

    private Bitmap bitmap;

    private GameSurface gameSurface;

    public Background(GameSurface gameSurface, Bitmap image, int x, int y) {
        super(image, 1, 1, x, y);

        this.gameSurface = gameSurface;

        this.bitmap = this.createSubImageAt(0, 0);
    }

    public Bitmap getBitmap() {
        return this.bitmap;

    }


    public void draw(Canvas canvas) {
        Bitmap bitmap = this.getBitmap();
        canvas.drawBitmap(bitmap, x, y, null);
        /*Paint fontPaint;
        fontPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        fontPaint.setColor(Color.WHITE);
        fontPaint.setTextSize(100);
        fontPaint.setStyle(Paint.Style.STROKE);
        canvas.drawText("FDKJLKSJFLKSJDLFSJD", 100, 100, fontPaint);*/
    }
}
