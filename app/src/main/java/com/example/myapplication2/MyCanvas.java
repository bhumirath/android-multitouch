package com.example.myapplication2;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.Random;

public class MyCanvas extends View {
    Random ran = new Random();
    int r,g,b;
    ArrayList<ArrayList<Float>> coors = new ArrayList<ArrayList<Float>>();
    ArrayList<ArrayList<Integer>> rgbs = new ArrayList<ArrayList<Integer>>();

    private float xDown = 0, yDown = 0, xUp = 0, yUp = 0;
    boolean touched = false;

    public MyCanvas(Context context) {
        super(context);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawColor(Color.TRANSPARENT);

        if (touched) {
            Paint paint = new Paint();
            r = ran.nextInt(256);
            g = ran.nextInt(256);
            b = ran.nextInt(256);
            paint.setColor(Color.rgb(r,g,b));
            for (int i = 0; i <coors.size();i++){
                ArrayList<Float>newCoor = coors.get(i);
                ArrayList<Integer>newRGB = rgbs.get(i);
                paint.setColor(Color.rgb(newRGB.get(0),newRGB.get(1),newRGB.get(2)));
                canvas.drawRect(newCoor.get(0),newCoor.get(1),newCoor.get(2),newCoor.get(3),paint);
            }
            canvas.drawRect(xDown, yDown, xUp, yUp, paint);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int fingers = event.getPointerCount();
        switch (event.getAction()) {

            case MotionEvent.ACTION_DOWN:
                if (fingers == 1) {
                    xDown = event.getX(0);
                    yDown = event.getY(0);

                    xUp = 0;
                    yUp = 0;
                }
                if (fingers == 2) {
                    xUp = event.getX(1);
                    yUp = event.getY(1);
                    xDown = event.getX(0);
                    yDown = event.getY(0);
                    touched = true;
                }
                break;
            case MotionEvent.ACTION_MOVE:
                if (fingers == 1) {
                    xUp = event.getX();
                    yUp = event.getY();
                    touched = true;
                }
                if (fingers == 2) {
                    xUp = event.getX(1);
                    yUp = event.getY(1);
                    xDown = event.getX(0);
                    yDown = event.getY(0);
                    touched = true;
                }
                break;
            case MotionEvent.ACTION_UP:
                if (fingers == 1) {
                    xUp = event.getX();
                    yUp = event.getY();
                    touched = true;
                }

                if (fingers == 2) {
                    xUp = event.getX(1);
                    yUp = event.getY(1);
                    xDown = event.getX(0);
                    yDown = event.getY(0);
                    touched = true;
                }
                ArrayList<Float> coor = new ArrayList<Float>();
                coor.add(xUp);
                coor.add(xDown);
                coor.add(xDown);
                coor.add(yDown);
                coors.add(coor);

                r = ran.nextInt(256);
                g = ran.nextInt(256);
                b = ran.nextInt(256);
                ArrayList<Integer> rgb = new ArrayList<Integer>();
                rgb.add(r);
                rgb.add(g);
                rgb.add(b);
                rgbs.add(rgb);

                break;
        }
        invalidate();
        return true;
    }
}
