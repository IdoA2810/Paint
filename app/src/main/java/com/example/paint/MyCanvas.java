package com.example.paint;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class MyCanvas extends View
{

    Paint paint;
    List<Path> paths;
    List<Integer> colors;
    List<Float> brushes;
    int currentColor;
    float currentBrush;
    float xPos;
    float yPos;
    public MyCanvas(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        paint = new Paint();
        paths = new ArrayList<Path>();
        colors = new ArrayList<Integer>();
        brushes = new ArrayList<Float>();
        paint.setAntiAlias(true);
        paint.setColor(Color.BLACK);
        currentColor = Color.BLACK;
        paint.setStrokeJoin(Paint.Join.ROUND);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(10f);
        currentBrush = 10f;

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        for (int i=0; i<paths.size(); i++)
        {
            this.paint.setColor(colors.get(i));
            this.paint.setStrokeWidth(brushes.get(i));
            canvas.drawPath(paths.get(i), paint);
        }



    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Path path = new Path();
        path.moveTo(xPos, yPos);

        xPos = event.getX();
        yPos = event.getY();


        switch(event.getAction())
        {
            case MotionEvent.ACTION_DOWN:
                path.moveTo(xPos, yPos);
                return true;
            case MotionEvent.ACTION_MOVE:
                path.lineTo(xPos, yPos);
                break;
            case MotionEvent.ACTION_UP:
                break;

            default:
                return false;
        }
        paths.add(path);
        colors.add(currentColor);
        brushes.add(currentBrush);
        invalidate();
        return true;
    }

    public void changeColor(String color)
    {
        if (color.equals("red"))
            currentColor = Color.RED;
        else if (color.equals("black"))
            currentColor = Color.BLACK;
        else if (color.equals("green"))
            currentColor = Color.GREEN;
        else if (color.equals("yellow"))
            currentColor = Color.YELLOW;
        else if (color.equals("blue"))
            currentColor = Color.BLUE;
        else if (color.equals("white"))
            currentColor = Color.WHITE;

    }

    public void changeBrush(int level)
    {
        currentBrush = level * 5f;
    }


}
