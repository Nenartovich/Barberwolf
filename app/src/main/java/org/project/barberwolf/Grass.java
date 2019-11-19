package org.project.barberwolf;

import android.graphics.Bitmap;
import android.graphics.Canvas;

public class Grass extends GameObject {

    private Bitmap bitmap;

    // Velocity of game character (pixel/millisecond)
    private int movingVectorX = -30;
    private int movingVectorY = 0;

    private GameSurface gameSurface;

    public Grass(GameSurface gameSurface, Bitmap image, int x, int y) {
        super(image, 1, 1, x, y);

        this.gameSurface= gameSurface;

        this.bitmap = this.createSubImageAt(0, 0);
    }

    public Bitmap getBitmap()  {
        return this.bitmap;

    }


    public void update()  {
        // Calculate the new position of the game character.
        this.x = x + movingVectorX;
        this.y = y + movingVectorY;

        // When the game's character touches the edge of the screen, then change direction

        if(this.x <= -this.getWidth() )  {
            this.x = this.getWidth()*5;
        }
    }

    public void draw(Canvas canvas)  {
        Bitmap bitmap = this.getBitmap();
        canvas.drawBitmap(bitmap, x, y, null);
    }
}
