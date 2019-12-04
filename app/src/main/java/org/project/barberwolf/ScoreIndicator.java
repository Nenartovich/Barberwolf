package org.project.barberwolf;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;

public class ScoreIndicator extends Indicator {
    final static int strokeWidth = 5;
    final static int textSize = 100;
    final static int textColor = Color.BLACK;

    private GameSurface gameSurface;

    public ScoreIndicator(GameSurface gameSurface, int value, int x, int y) {
        super(value, x, y);

        this.gameSurface = gameSurface;

    }

    public void draw(Canvas canvas) {
        Paint fontPaint;
        fontPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        fontPaint.setColor(textColor);
        fontPaint.setStyle(Paint.Style.STROKE);
        fontPaint.setStrokeWidth(strokeWidth);
        fontPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        fontPaint.setTextSize(textSize);
        fontPaint.setStyle(Paint.Style.STROKE);

        canvas.drawText("Score: " + this.value, x, y, fontPaint);

    }
}
