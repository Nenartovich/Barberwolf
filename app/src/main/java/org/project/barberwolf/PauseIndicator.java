package org.project.barberwolf;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class PauseIndicator extends Indicator {
    final static int strokeWidth = 7;
    final static int textSize = 150;
    final static int textColor = Color.BLUE;

    public PauseIndicator(int x, int y) {
        super(-2, x, y);
    }

    public void draw(Canvas canvas) {
        Paint fontPaint = generateFontPaint(strokeWidth, textSize, textColor);
        canvas.drawText("Pause Pressed", x, y, fontPaint);
    }
}
