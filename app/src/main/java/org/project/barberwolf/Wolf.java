package org.project.barberwolf;

import android.graphics.Bitmap;
import android.graphics.Canvas;

public class Wolf extends GameObject {

    private static final int ROW_TOP_TO_BOTTOM = 0;
    private static final int ROW_RIGHT_TO_LEFT = 1;
    private static final int ROW_LEFT_TO_RIGHT = 2;
    private static final int ROW_BOTTOM_TO_TOP = 3;

    private int rowUsing = ROW_LEFT_TO_RIGHT;

    private int colUsing;

    private Bitmap[] leftToRights;
    private Bitmap[] rightToLefts;
    private Bitmap[] topToBottoms;
    private Bitmap[] bottomToTops;

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
        super(image, 4, 3, x, y);

        this.gameSurface= gameSurface;

        this.topToBottoms = new Bitmap[colCount];
        this.rightToLefts = new Bitmap[colCount];
        this.leftToRights = new Bitmap[colCount];
        this.bottomToTops = new Bitmap[colCount];

        for(int col = 0; col< this.colCount; col++ ) {
            this.topToBottoms[col] = this.createSubImageAt(ROW_TOP_TO_BOTTOM, col);
            this.rightToLefts[col]  = this.createSubImageAt(ROW_RIGHT_TO_LEFT, col);
            this.leftToRights[col] = this.createSubImageAt(ROW_LEFT_TO_RIGHT, col);
            this.bottomToTops[col]  = this.createSubImageAt(ROW_BOTTOM_TO_TOP, col);
        }
    }

    public Bitmap[] getMoveBitmaps()  {
        switch (rowUsing)  {
            case ROW_BOTTOM_TO_TOP:
                return  this.bottomToTops;
            case ROW_LEFT_TO_RIGHT:
                return this.leftToRights;
            case ROW_RIGHT_TO_LEFT:
                return this.rightToLefts;
            case ROW_TOP_TO_BOTTOM:
                return this.topToBottoms;
            default:
                return null;
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

        if (jumpFlag && jumpPhase1 && jumpHeight < 5) {
            this.y -= 15;
            this.jumpHeight++;
        } else if (jumpFlag && jumpPhase1 && jumpHeight == 5) {
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
}

