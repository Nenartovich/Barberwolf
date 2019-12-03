package org.project.barberwolf;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;

public class HealthIndicator extends Indicator {

    private GameSurface gameSurface;

    public HealthIndicator(GameSurface gameSurface, int value, int x, int y) {
        super(value, x, y);

        this.gameSurface = gameSurface;

    }

    public void draw(Canvas canvas) {
        Paint fontPaint;
        fontPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        //fontPaint.setColor(Color.WHITE);
        fontPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        fontPaint.setTextSize(100);
        fontPaint.setStyle(Paint.Style.STROKE);
        canvas.drawText("Health: " + this.value, 100, 100, fontPaint);
    }
}
