package org.project.barberwolf;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import java.util.List;

public class Obstacle extends GameObject {
    private Bitmap bitmap;

    private int movingVectorX = -20;
    private int movingVectorY = 0;

    private int floor = 1;

    private boolean good = false;
    private boolean caught = false;

    private GameSurface gameSurface;

    public Obstacle(GameSurface gameSurface, Bitmap image, int x, int y) {
        super(image, 1, 1, x, y);

        this.gameSurface = gameSurface;

        this.bitmap = this.createSubImageAt(0, 0);
    }

    public boolean isGood() {
        return good;
    }

    public boolean isCaught() {
        return caught;
    }

    public void setCaughtFlag(boolean flag) {
        caught = flag;
    }

    public void setMovingVector(int newMovingVectorX, int newMovingVectorY) {
        movingVectorX = newMovingVectorX;
        movingVectorY = newMovingVectorY;
    }

    public Bitmap getBitmap() {
        return this.bitmap;
    }

    public void update() {
        this.x = x + movingVectorX;
        this.y = y + movingVectorY;

        if (this.x <= -gameSurface.getObstacleWidth()) {
            List<Obstacle> obtaclesList = gameSurface.getObstaclesList();
            for (Obstacle obstacle : obtaclesList) {
                obstacle.setCaughtFlag(false);
                if (obstacle.getX() <= -gameSurface.getObstacleWidth()) {
                    int randomNumber = (int) (Math.random() * 10);
                    if (randomNumber >= 0 && randomNumber <= 2) {
                        obstacle.bitmap = this.gameSurface.sheepBitmap;
                        if (!obstacle.good) {
                            obstacle.y -= this.gameSurface.sheepBitmap.getHeight() / 3;
                        }
                        obstacle.good = true;

                    } else {
                        obstacle.bitmap = this.gameSurface.obstacleBitmap;
                        if (obstacle.good) {
                            obstacle.y += this.gameSurface.sheepBitmap.getHeight() / 3;
                        }
                        obstacle.good = false;
                    }

                    randomNumber = (int) (Math.random() * 100) % 3 + 1;

                    if (obstacle.floor < randomNumber) {
                        ++obstacle.floor;
                        obstacle.y -= obstacle.getHeight();
                    }

                    if (obstacle.floor < randomNumber) {
                        ++obstacle.floor;
                        obstacle.y -= obstacle.getHeight();
                    }

                    if (obstacle.floor > randomNumber) {
                        --obstacle.floor;
                        obstacle.y += obstacle.getHeight();
                    }

                    if (obstacle.floor > randomNumber) {
                        --obstacle.floor;
                        obstacle.y += obstacle.getHeight();
                    }
                }
            }

            this.x = 5 * gameSurface.getObstacleWidth();
        }
    }

    public void draw(Canvas canvas) {
        Bitmap bitmap = this.getBitmap();
        canvas.drawBitmap(bitmap, x, y, null);
    }
}
