package org.project.barberwolf;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class ScoreIndicator extends Indicator {
    final static int strokeWidth = 5;
    final static int textSize = 100;
    final static int textColor = Color.BLACK;

    public ScoreIndicator(int value, int x, int y) {
        super(value, x, y);
    }

    public void draw(Canvas canvas) {
        Paint fontPaint = generateFontPaint(strokeWidth, textSize, textColor);
        canvas.drawText("Score: " + this.value, x, y, fontPaint);
    }
}
