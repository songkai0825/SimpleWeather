package com.sk.simpleweather;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.animation.AccelerateInterpolator;

import java.util.ArrayList;


public class MyCurveView extends View {

    private boolean isAnimation = false;

    //已转化为0至100范围的当前进度，随动画时间改变而改变
    private int mHighPercent = 0;

    //已转化为0至100范围的当前进度，随动画时间改变而改变
    private int mLowPercent = 0;

    //每日温度宽度
    private float mTempWidth;

    //顶部日期、底部天气字体大小
    private int text_size;

    //温度字体大小
    private int temp_text_size;

    //曲线颜色
    private int curve_color;

    //顶部日期、底部天气字体颜色
    private int text_color;

    //温度字体颜色
    private int temp_text_color;

    //温度圆圈颜色
    private int circle_color;

    //温度Paint
    private Paint mTempTextPaint;

    //顶部日期、底部天气Paint
    private Paint mTextPaint;

    //温度圆圈Paint
    private Paint circlePaint;

    //曲线Paint
    private Paint mCurvePaint;

    //温度曲线幅度
    private float curve_ratio;

    private float measureHeight;
    private float lastX = 0;
    private float measureWidth = 0;

    //这是最初的的位置
    private float mStartX = 0;

    private ScrollRunnable mScrollRunnable;

    private boolean isFling = false;
    private float dispatchTouchX = 0;
    private float dispatchTouchY = 0;

    final float density;

    final float scaledDensity;

    private VelocityTracker mVelocityTracker = null;

    public MyCurveView(Context context) {
        this(context, null);
    }

    public MyCurveView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyCurveView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        density = getContext().getResources().getDisplayMetrics().density;
        scaledDensity = getContext().getResources().getDisplayMetrics().scaledDensity;

        @SuppressLint("CustomViewStyleable") TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.barchar_style);
        text_size = (int) typedArray.getDimension(R.styleable.barchar_style_text_size, sp2Px(16));
        text_color = typedArray.getColor(R.styleable.barchar_style_text_color, Color.parseColor("#ffffff"));
        temp_text_size = (int) typedArray.getDimension(R.styleable.barchar_style_temp_text_size, sp2Px(15));
        temp_text_color = typedArray.getColor(R.styleable.barchar_style_temp_text_color, Color.parseColor("#ffffff"));
        curve_color = typedArray.getColor(R.styleable.barchar_style_curve_color, Color.parseColor("#ffffff"));
        circle_color = typedArray.getColor(R.styleable.barchar_style_circle_color, Color.parseColor("#ffffff"));
        curve_ratio = typedArray.getFloat(R.styleable.barchar_style_curve_ratio, 2);
        typedArray.recycle();
        initPaint();
    }

    private float sp2Px(float spValue) {
        return spValue * scaledDensity;
    }

    private void initPaint() {
        mTextPaint = new Paint();
        mTextPaint.setTextSize(text_size);
        mTextPaint.setColor(text_color);
        mTextPaint.setStyle(Paint.Style.FILL);
        mTextPaint.setDither(true);
        mTextPaint.setAntiAlias(true);
        mTextPaint.setStrokeWidth(1f);

        mTempTextPaint = new Paint();
        mTempTextPaint.setTextSize(temp_text_size);
        mTempTextPaint.setColor(temp_text_color);
        mTempTextPaint.setAntiAlias(true);
        mTempTextPaint.setStyle(Paint.Style.FILL);
        mTempTextPaint.setDither(true);
        mTempTextPaint.setStrokeWidth(1f);

        mCurvePaint = new Paint();
        mCurvePaint.setAntiAlias(true);
        mCurvePaint.setColor(curve_color);
        mCurvePaint.setStrokeWidth(1.5f);
        mCurvePaint.setStyle(Paint.Style.STROKE);

        circlePaint = new Paint();
        circlePaint.setAntiAlias(true);
        circlePaint.setStyle(Paint.Style.FILL);
        circlePaint.setColor(circle_color);
        circlePaint.setDither(true);

        highPath = new Path();
        lowPath = new Path();
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        int dispatchCurrX = (int) ev.getX();
        int dispatchCurrY = (int) ev.getY();
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                //父容器不拦截点击事件，子控件拦截点击事件。如果不设置为true,外层会直接拦截，从而导致motionEvent为cancle
                getParent().requestDisallowInterceptTouchEvent(true);
                dispatchTouchX = getX();
                dispatchTouchY = getY();
                break;

            case MotionEvent.ACTION_MOVE:
                float deltaX = dispatchCurrX - dispatchTouchX;
                float deltaY = dispatchCurrY - dispatchTouchY;
                if (Math.abs(deltaY) - Math.abs(deltaX) > 0) {//竖直滑动的父容器拦截事件
                    getParent().requestDisallowInterceptTouchEvent(false);
                }
                //向右滑动，滑动到左边边界，父容器进行拦截
                if ((dispatchCurrX - dispatchTouchX) > 0 && mStartX == 0) {
                    getParent().requestDisallowInterceptTouchEvent(false);
                } else if ((dispatchCurrX - dispatchTouchX) < 0 && mStartX == -getMoveLength()) {
                    //向左滑动，滑动到右边边界，父容器进行拦截
                    getParent().requestDisallowInterceptTouchEvent(false);
                }
                break;

            case MotionEvent.ACTION_UP:
                break;

            default:
                break;
        }
        dispatchTouchX = dispatchCurrX;
        dispatchTouchY = dispatchCurrY;
        return super.dispatchTouchEvent(ev);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (isAnimation) {
            return true;
        }
        if (mVelocityTracker == null) {
            mVelocityTracker = VelocityTracker.obtain();
        }
        mVelocityTracker.addMovement(event);
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                lastX = event.getX();
                //当点击的时候，判断如果是在fling的效果的时候，就停止快速滑动
                if (isFling) {
                    removeCallbacks(mScrollRunnable);
                    isFling = false;
                }
                break;

            case MotionEvent.ACTION_MOVE:
                float currX = event.getX();
                mStartX += currX - lastX;

                //这是向右滑动
                if ((currX - lastX) > 0) {
                    if (mStartX > 0) {
                        mStartX = 0;
                    }
                } else {//这是向左滑动
                    if (-mStartX > getMoveLength()) {
                        mStartX = -getMoveLength();
                    }
                }
                //如果数据量少，根本没有充满横屏，就没必要重新绘制，
                if (measureWidth < dataArray.size() * mTempWidth) {
                    invalidate();
                }

                lastX = currX;
                break;

            case MotionEvent.ACTION_UP:
                mVelocityTracker.computeCurrentVelocity(1000);
                //计算猛滑动的速度，如果是大于某个值，并且数据的长度大于整个屏幕的长度，那么就允许有flIng后逐渐停止的效果
                if (Math.abs(mVelocityTracker.getXVelocity()) > 100
                        && !isFling && measureWidth < dataArray.size() * mTempWidth) {
                    mScrollRunnable = new ScrollRunnable(mVelocityTracker.getXVelocity() / 5);
                    this.post(mScrollRunnable);
                }
                break;

            case MotionEvent.ACTION_CANCEL:
                break;
        }
        return true;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        measureWidth = widthSize;
        measureHeight = heightSize;
        mTempWidth = measureWidth / 6;
        setMeasuredDimension(widthSize, heightSize);
    }

    float highControlPt1X;
    float highControlPt1Y;
    float highControlPt2X;
    float highControlPt2Y;

    float lowControlPt1X;
    float lowControlPt1Y;
    float lowControlPt2X;
    float lowControlPt2Y;

    Path highPath;
    Path lowPath;

    @SuppressLint("DrawAllocation")
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (dataArray.size() <= 0) {
            drawNoDataText(canvas);
        } else {
            float startX = mStartX + (mTempWidth / 2);
            for (int i = 0; i < dataArray.size(); i++) {
                //绘制天气图标
                RectF rectF = new RectF(startX - 30, 300, startX + 30, 360);
                canvas.drawBitmap(dataArray.get(i).getTypeBitmap(), null, rectF, mCurvePaint);

                //绘制最高温度
                float highTextWidth = mTempTextPaint.measureText((int)(high + mHighPercent * dataArray.get(i).getHighTemp()) + "");
                float highTextStartX = startX - highTextWidth / 2;
                drawTempText(canvas, (int)(high + getHighTempByPercent(i)) + "", highTextStartX, (140 - curve_ratio * getHighTempByPercent(i)));
                canvas.drawCircle(startX, (160 - curve_ratio * getHighTempByPercent(i)), 5, circlePaint);
                if (i == 0) {
                    highPath.moveTo(startX, (160 - curve_ratio * getHighTempByPercent(i)));
                    highControlPt1X = startX + mTempWidth / 4;
                    highControlPt1Y = 160 - curve_ratio * getHighTempByPercent(i);
                    highControlPt2X = startX + (mTempWidth / 4) * 3;
                    highControlPt2Y = ((160 - curve_ratio  * getHighTempByPercent(i + 1))) - (((160 - curve_ratio * getHighTempByPercent(i + 2))) - ((160 - curve_ratio * getHighTempByPercent(i)))) / 4;
                    highPath.cubicTo(
                            highControlPt1X, highControlPt1Y,
                            highControlPt2X, highControlPt2Y,
                            startX + mTempWidth, (160 - curve_ratio * getHighTempByPercent(i + 1)));
                    canvas.drawPath(highPath, mCurvePaint);
                    highPath.reset();
                }
                if (i != 0 && i < dataArray.size() - 2) {
                    highPath.moveTo(startX, (160 - curve_ratio * getHighTempByPercent(i)));
                    highControlPt1X = startX + mTempWidth / 4;
                    highControlPt1Y = ((160 - curve_ratio * getHighTempByPercent(i))) + (((160 - curve_ratio * getHighTempByPercent(i + 1))) - ((160 - curve_ratio * getHighTempByPercent(i - 1)))) / 4;
                    highControlPt2X = startX + (mTempWidth / 4) * 3;
                    highControlPt2Y = ((160 - curve_ratio  * getHighTempByPercent(i + 1))) - (((160 - curve_ratio * getHighTempByPercent(i + 2))) - ((160 - curve_ratio * getHighTempByPercent(i)))) / 4;
                    highPath.cubicTo(
                            highControlPt1X, highControlPt1Y,
                            highControlPt2X, highControlPt2Y,
                            startX + mTempWidth, (160 - curve_ratio * getHighTempByPercent(i + 1)));
                    canvas.drawPath(highPath, mCurvePaint);
                    highPath.reset();
                }
                if (i == dataArray.size() - 2) {
                    highPath.moveTo(startX, (160 - curve_ratio * getHighTempByPercent(i)));
                    highControlPt1X = startX + mTempWidth / 4;
                    highControlPt1Y = ((160 - curve_ratio * getHighTempByPercent(i))) + (((160 - curve_ratio * getHighTempByPercent(i + 1))) - ((160 - curve_ratio * getHighTempByPercent(i - 1)))) / 4;
                    highControlPt2X = startX + (mTempWidth / 4) * 3;
                    highControlPt2Y = 160 - curve_ratio  * getHighTempByPercent(i + 1);
                    highPath.cubicTo(
                            highControlPt1X, highControlPt1Y,
                            highControlPt2X, highControlPt2Y,
                            startX + mTempWidth, (160 - curve_ratio * getHighTempByPercent(i + 1)));
                    canvas.drawPath(highPath, mCurvePaint);
                    highPath.reset();
                }

                //绘制最低温度
                float lowTextWidth = mTempTextPaint.measureText((int)(low + mLowPercent * dataArray.get(i).getLowTemp()) + "");
                float lowTextStartX = startX - lowTextWidth / 2;
                drawTempText(canvas, (int)(low - getLowTempByPercent(i)) + "", lowTextStartX, 250 + getLowTempByPercent(i));
                canvas.drawCircle(startX, (210 + curve_ratio * getLowTempByPercent(i)), 5, circlePaint);
                if (i == 0) {
                    lowPath.moveTo(startX, (210 + curve_ratio * getLowTempByPercent(i)));
                    lowControlPt1X = startX + mTempWidth / 4;
                    lowControlPt1Y = 210 + curve_ratio * getLowTempByPercent(i);
                    lowControlPt2X = startX + (mTempWidth / 4) * 3;
                    lowControlPt2Y = ((210 + curve_ratio * getLowTempByPercent(i + 1))) - (((210 + curve_ratio * getLowTempByPercent(i + 2))) - ((210 + curve_ratio * getLowTempByPercent(i)))) / 4;
                    lowPath.cubicTo(
                            lowControlPt1X, lowControlPt1Y,
                            lowControlPt2X, lowControlPt2Y,
                            startX + mTempWidth, (210 + curve_ratio    * getLowTempByPercent(i + 1)));
                    canvas.drawPath(lowPath, mCurvePaint);
                    lowPath.reset();
                }
                if (i != 0 && i < dataArray.size() - 2) {
                    lowPath.moveTo(startX, (210 + curve_ratio * getLowTempByPercent(i)));
                    lowControlPt1X = startX + mTempWidth / 4;
                    lowControlPt1Y = ((210 + curve_ratio * getLowTempByPercent(i))) + (((210 + curve_ratio * getLowTempByPercent(i + 1))) - ((210 + curve_ratio * getLowTempByPercent(i - 1)))) / 4;
                    lowControlPt2X = startX + (mTempWidth / 4) * 3;
                    lowControlPt2Y = ((210 + curve_ratio * getLowTempByPercent(i + 1))) - (((210 + curve_ratio * getLowTempByPercent(i + 2))) - ((210 + curve_ratio * getLowTempByPercent(i)))) / 4;
                    lowPath.cubicTo(
                            lowControlPt1X, lowControlPt1Y,
                            lowControlPt2X, lowControlPt2Y,
                            startX + mTempWidth, (210 + curve_ratio    * getLowTempByPercent(i + 1)));
                    canvas.drawPath(lowPath, mCurvePaint);
                    lowPath.reset();
                }
                if (i == dataArray.size() - 2) {
                    lowPath.moveTo(startX, (210 + curve_ratio * getLowTempByPercent(i)));
                    lowControlPt1X = startX + mTempWidth / 4;
                    lowControlPt1Y = ((210 + curve_ratio * getLowTempByPercent(i))) + (((210 + curve_ratio * getLowTempByPercent(i + 1))) - ((210 + curve_ratio * getLowTempByPercent(i - 1)))) / 4;
                    lowControlPt2X = startX + (mTempWidth / 4) * 3;
                    lowControlPt2Y = 210 + curve_ratio * getLowTempByPercent(i + 1);
                    lowPath.cubicTo(
                            lowControlPt1X, lowControlPt1Y,
                            lowControlPt2X, lowControlPt2Y,
                            startX + mTempWidth, (210 + curve_ratio    * getLowTempByPercent(i + 1)));
                    canvas.drawPath(lowPath, mCurvePaint);
                    lowPath.reset();
                }

                //绘制日期
                float dayTextWidth = mTextPaint.measureText(dataArray.get(i).getDate() + "日");
                float dayStartX = startX - dayTextWidth / 2;
                float dayTextStartY = 40 + getFontAscentHeight(mTextPaint);
                drawDayText(canvas, dataArray.get(i).getDate() + "日", dayStartX, dayTextStartY);

                //绘制天气
                float typeTextWidth = mTextPaint.measureText(dataArray.get(i).getType());
                float typeTextStartX = startX - typeTextWidth / 2;
                float typeTextStartY = measureHeight - 40 - getFontDescentHeight(mTextPaint);
                canvas.drawText(dataArray.get(i).getType(), typeTextStartX, typeTextStartY, mTextPaint);

                startX = startX + mTempWidth;
            }
        }
    }

    private void drawNoDataText(Canvas canvas) {
        String text = "获取网络数据中...";
        float textWidth = mTextPaint.measureText(text);
        canvas.drawText(text, measureWidth / 2 - textWidth / 2, measureHeight / 2 - 10, mTextPaint);
    }

    //文字基准线的上部距离
    private float getFontAscentHeight(Paint paint) {
        Paint.FontMetrics fm = paint.getFontMetrics();
        return -fm.ascent;
    }

    //文字基准线的下部距离
    private float getFontDescentHeight(Paint paint) {
        Paint.FontMetrics fm = paint.getFontMetrics();
        return fm.descent;
    }

    //绘制温度
    private void drawTempText(Canvas canvas, String text, float textStartX, float textStartY) {
        canvas.drawText(text, textStartX, textStartY, mTempTextPaint);
    }

    private void drawDayText(Canvas canvas, String text, float bottomStartX, float bottomStartY) {
        canvas.drawText(text, bottomStartX, bottomStartY, mTextPaint);
    }

    private float getHighTempByPercent(int index) {
        return mHighPercent * dataArray.get(index).getHighTemp();
    }

    private float getLowTempByPercent(int index) {
        return mLowPercent * dataArray.get(index).getLowTemp();
    }

    private float getMoveLength() {
        return mTempWidth * dataArray.size() - measureWidth;
    }

    static class WeatherData {
        private float lowTemp;
        private float highTemp;
        private int date;
        private String type;
        private Bitmap typeBitmap;

        WeatherData(float lowTemp, float highTemp, int date, String type, Bitmap typeBitmap) {
            this.lowTemp = lowTemp;
            this.highTemp = highTemp;
            this.date = date;
            this.type = type;
            this.typeBitmap = typeBitmap;
        }

        float getLowTemp() {
            return lowTemp;
        }

        void setLowTemp(float lowTemp) {
            this.lowTemp = lowTemp;
        }

        float getHighTemp() {
            return highTemp;
        }

        void setHighTemp(float highTemp) {
            this.highTemp = highTemp;
        }

        int getDate() {
            return date;
        }

        public void setDate(int date) {
            this.date = date;
        }

        String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        Bitmap getTypeBitmap() {
            return typeBitmap;
        }

        public void setTypeBitmap(Bitmap typeBitmap) {
            this.typeBitmap = typeBitmap;
        }
    }

    private class ScrollRunnable implements Runnable {

        private float speed;

        ScrollRunnable(float speed) {
            this.speed = speed;
        }

        @Override
        public void run() {
            if (Math.abs(speed) < 60) {
                isFling = false;
                return;
            }
            isFling = true;
            mStartX += speed / 15;
            speed = speed / 1.1f;
            //向右滑动
            if ((speed) > 0) {
                if (mStartX > 0) {
                    mStartX = 0;
                }
            } else {
                //向右滑动
                if (-mStartX > getMoveLength()) {
                    mStartX = -getMoveLength();
                }
            }
            postDelayed(this, 5);
            invalidate();
        }
    }

    public void setProgress(int averageHigh, int averageLow, final int low, int top, ArrayList<WeatherData> innerData) {
        arrayList(innerData, top, low, averageHigh, averageLow);
        ValueAnimator animatorHigh = ValueAnimator.ofInt(0, top);
        animatorHigh.setDuration(1000);
        animatorHigh.setInterpolator(new AccelerateInterpolator());
        animatorHigh.addUpdateListener(valueAnimator -> {
            mHighPercent = (int)valueAnimator.getAnimatedValue();
            invalidate();
        });

        ValueAnimator animatorLow = ValueAnimator.ofInt(0, low);
        animatorLow.setDuration(1000);
        animatorLow.setInterpolator(new AccelerateInterpolator());
        animatorLow.addUpdateListener(valueAnimator -> {
            mLowPercent = (int)valueAnimator.getAnimatedValue();
        });

        AnimatorSet set = new AnimatorSet();
        set.playTogether(animatorHigh, animatorLow);
        set.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                isAnimation = true;
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                isAnimation = false;
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        set.start();
    }

    private ArrayList<WeatherData> dataArray = new ArrayList<>();

    private int high;
    private int low;

    @SuppressWarnings("PointlessArithmeticExpression")
    private void arrayList(ArrayList<WeatherData> innerData, int max, int min, int averageHigh, int averageLow) {
        high = averageHigh;
        low = averageLow;

        dataArray.clear();
        dataArray.addAll(innerData);

        for (int i = 0; i < innerData.size(); i++) {
            //在0至最高值的变化时间内，将值从平均值变为当前值
            dataArray.get(i).setHighTemp((innerData.get(i).getHighTemp() - averageHigh) / (max - 0));
            //在0至最低值的变化时间内，将值从平均值变为当前值
            dataArray.get(i).setLowTemp((averageLow - innerData.get(i).getLowTemp()) / min);
        }
    }

}
