package org.project.barberwolf;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class HealthIndicator extends Indicator {
    final static int strokeWidth = 5;
    final static int textSize = 75;
    final static int textColor = Color.BLACK;

    public HealthIndicator(int value, int x, int y) {
        super(value, x, y);
    }

    public void draw(Canvas canvas) {
        Paint fontPaint = generateFontPaint(strokeWidth, textSize, textColor);
        canvas.drawText("Health: " + this.value, x, y, fontPaint);
    }
}
