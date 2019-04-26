package com.assesment.omnitrax.util;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.Transformation;

public class CircularDottedProgress extends View {

    private int dotRadius = 10;

    private int bounceDotRadius = 13;

    private int dotPosition = 1;

    private int dotAmount = 10;

    private int circleRadius = 50;


    public CircularDottedProgress(Context context) {
        super(context);
    }

    public CircularDottedProgress(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CircularDottedProgress(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        startAnimation();
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.translate(this.getWidth()/2,this.getHeight()/2);

        Paint progressPaint = new Paint();
        progressPaint.setColor(Color.parseColor("#ff014e"));

        createDotInCircle(canvas,progressPaint);
    }

    private void createDotInCircle(Canvas canvas,Paint progressPaint) {
        int angle = 36;

        for(int i = 1; i <= dotAmount; i++){

            if(i == dotPosition){
                float x = (float) (circleRadius * (Math.cos((angle * i) * (Math.PI / 180))));
                float y = (float) (circleRadius * (Math.sin((angle * i) * (Math.PI / 180))));

                canvas.drawCircle(x,y, bounceDotRadius, progressPaint);

            }else{
                float x = (float) (circleRadius * (Math.cos((angle * i) * (Math.PI / 180))));
                float y = (float) (circleRadius * (Math.sin((angle * i) * (Math.PI / 180))));

                canvas.drawCircle(x,y, dotRadius, progressPaint);

            }

        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = 0;
        int height = 0;

        width = 100 + (dotRadius*3);
        height = 100 + (dotRadius*3);

        setMeasuredDimension(width, height);
    }

    private void startAnimation() {
        BounceAnimation bounceAnimation = new BounceAnimation();
        bounceAnimation.setDuration(150);
        bounceAnimation.setRepeatCount(Animation.INFINITE);
        bounceAnimation.setInterpolator(new LinearInterpolator());
        bounceAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {

            }

            @Override
            public void onAnimationRepeat(Animation animation) {
                dotPosition++;
                if (dotPosition > dotAmount) {
                    dotPosition = 1;
                }


            }
        });
        startAnimation(bounceAnimation);
    }


    private class BounceAnimation extends Animation {
        @Override
        protected void applyTransformation(float interpolatedTime, Transformation t) {
            super.applyTransformation(interpolatedTime, t);
            invalidate();
        }
    }
}
