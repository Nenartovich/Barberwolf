package org.project.barberwolf;

import android.graphics.Bitmap;

public class GameObject {
    protected Bitmap image;
    protected String value;

    protected final int rowCount;
    protected final int colCount;

    protected final int screenWidth;
    protected final int screenHeidth;

    protected final int width;


    protected final int height;
    protected int x;
    protected int y;

    public GameObject(Bitmap image, int rowCount, int colCount, int x, int y) {

        this.image = image;
        this.rowCount = rowCount;
        this.colCount = colCount;

        this.x = x;
        this.y = y;

        this.screenWidth = image.getWidth();
        this.screenHeidth = image.getHeight();

        this.width = this.screenWidth / colCount;
        this.height = this.screenHeidth / rowCount;
    }

    protected Bitmap createSubImageAt(int row, int col) {
        return Bitmap.createBitmap(image, col * width, row * height, width, height);
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }
}
