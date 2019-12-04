package org.project.barberwolf;

import android.graphics.Paint;
import android.graphics.Typeface;

public class Indicator {
    protected int value;

    protected int x;
    protected int y;

    Indicator(int value, int x, int y) {
        this.value = value;
        this.x = x;
        this.y = y;
    }

    public Paint generateFontPaint(int strokeWidth, int textSize, int textColor) {
        Paint fontPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        fontPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        fontPaint.setTextSize(textSize);
        fontPaint.setStyle(Paint.Style.STROKE);
        fontPaint.setStrokeWidth(strokeWidth);
        fontPaint.setColor(textColor);

        return fontPaint;
    }
}