package org.project.barberwolf;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class GameOverIndicator extends Indicator {
    final static int strokeWidth = 7;
    final static int textSize = 150;
    final static int textColor = Color.BLUE;
    private static final String message = "Game over!";
    private GameSurface gameSurface;

    public GameOverIndicator(GameSurface gameSurface, int x, int y) {
        super(-1, gameSurface.getWidth() / 2 - (int)(textSize * 2.5), gameSurface.getHeight() / 2);
        this.gameSurface = gameSurface;
    }

    public void draw(Canvas canvas) {
        Paint fontPaint = generateFontPaint(strokeWidth, textSize, textColor);
        canvas.drawText(message, x, y, fontPaint);
    }
}
