package org.project.barberwolf;

import android.graphics.Bitmap;
import android.graphics.Canvas;

public class Obtacle extends GameObject {
    private Bitmap bitmap;

    private int movingVectorX = -20;
    private int movingVectorY = 0;

    private int floor = 1;

    private GameSurface gameSurface;

    public Obtacle(GameSurface gameSurface, Bitmap image, int x, int y) {
        super(image, 1, 1, x, y);

        this.gameSurface= gameSurface;

        this.bitmap = this.createSubImageAt(0, 0);
    }


    public Bitmap getBitmap()  {
        return this.bitmap;
    }

    public void update()  {
        this.x = x + movingVectorX;
        this.y = y + movingVectorY;

        if (this.x <= -gameSurface.getObtacleWidth())  {
            for (Obtacle obtacle : this.gameSurface.obtaclesList) {
                if (obtacle.getX() <= -gameSurface.getObtacleWidth()) {
                    int randomNumber = (int)(Math.random() * 10);
                    if (randomNumber >= 0 && randomNumber <= 2) {
                        obtacle.bitmap = this.gameSurface.sheepBitmap;
                    } else {
                        obtacle.bitmap = this.gameSurface.obtacleBitmap;
                    }

                    randomNumber = (int)(Math.random() * 100) % 3 + 1;

                        if (obtacle.floor < randomNumber) {
                            obtacle.floor++;
                            obtacle.y -= obtacle.getHeight();
                        }

                        if (obtacle.floor < randomNumber) {
                            obtacle.floor++;
                            obtacle.y -= obtacle.getHeight();
                        }

                        if (obtacle.floor > randomNumber) {
                            obtacle.floor--;
                            obtacle.y += obtacle.getHeight();
                        }

                        if (obtacle.floor > randomNumber) {
                            obtacle.floor--;
                            obtacle.y += obtacle.getHeight();
                        }
                }
            }
            this.x = 5*gameSurface.getObtacleWidth();

        }
    }

    public void draw(Canvas canvas)  {
        Bitmap bitmap = this.getBitmap();
        canvas.drawBitmap(bitmap, x, y, null);
    }
}
