package org.project.barberwolf;

import android.graphics.Bitmap;
import android.graphics.Canvas;

public class Wolf extends GameObject {

    private int rowUsing = 0;
    private int colUsing = 0;

    private Bitmap[] runningBitmaps;
    private Bitmap[] squattingBitmaps;


    private int movingVectorX = 0;
    private int movingVectorY = 0;

    private GameSurface gameSurface;

    private double oldY;
    private double newY;

    private boolean moveFlag = false;

    private boolean jumpFlag = false;
    private boolean jumpPhase1 = false;
    private int jumpHeight = 0;
    private boolean downSwipeFlag = false;

    public Wolf(GameSurface gameSurface, Bitmap image, int x, int y) {
        super(image, 2, 6, x, y);

        this.gameSurface= gameSurface;

        this.runningBitmaps = new Bitmap[colCount];
        this.squattingBitmaps = new Bitmap[colCount];

        for(int col = 0; col < this.colCount; col++ ) {
            this.runningBitmaps[col] = this.createSubImageAt(0, col);
            this.squattingBitmaps[col] = this.createSubImageAt(1, col);
        }
    }

    public Bitmap[] getMoveBitmaps()  {
        if (rowUsing == 0) {
            return runningBitmaps;
        } else {
            return squattingBitmaps;
        }
    }

    public Bitmap getCurrentMoveBitmap()  {
        Bitmap[] bitmaps = this.getMoveBitmaps();
        return bitmaps[this.colUsing];
    }

    public void update()  {
        this.colUsing++;
        if(colUsing >= this.colCount)  {
            this.colUsing =0;
        }

        this.x = x + movingVectorX;
        this.y = y + movingVectorY;

        if (jumpFlag && jumpPhase1 && jumpHeight < 15) {
            this.y -= 15;
            this.jumpHeight++;
        } else if (jumpFlag && jumpPhase1 && jumpHeight == 15) {
            setJumpPhase1(false);
        } else if (jumpFlag && !jumpPhase1 && jumpHeight > 0) {
            this.y += 15;
            this.jumpHeight--;
        } else if (jumpFlag && !jumpPhase1 && jumpHeight == 0) {
            setJumpFlag(false);
        }
    }

    public void draw(Canvas canvas)  {
        Bitmap bitmap = this.getCurrentMoveBitmap();
        canvas.drawBitmap(bitmap,x, y, null);
    }

    public void setOldY(double currentY) {
        oldY = currentY;
    }

    public void setNewY(double currentY) {
        newY = currentY;
    }

    public double getOldY() {
        return oldY;
    }

    public double getNewY() {
        return newY;
    }

    public void setMoveFlag(boolean flag) {
        moveFlag = flag;
    }

    public boolean getMoveFlag() {
        return moveFlag;
    }

    public void setJumpPhase1(boolean flag) {
        jumpPhase1 = flag;
    }

    public void setJumpFlag(boolean flag) {
        jumpFlag = flag;
    }

    public void setDownSwipeFlag(boolean flag) {
        downSwipeFlag = flag;
    }

    public boolean getDownSwipeFlag() {
        return downSwipeFlag;
    }

    public void setRowUsing(int rowNumber) {
        rowUsing = rowNumber;
    }
}

