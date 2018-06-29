package com.tracker.lantimat.cartracker.utils;

/**
 * Created by GabdrakhmanovII on 04.08.2017.
 */

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;

import com.mikepenz.iconics.utils.Utils;
import com.tracker.lantimat.cartracker.R;


public class CustomProgressBar extends View {


    private int max = 100;

    private int progress;

    private Path path = new Path();

    int color = 0xff44C8E5;

    int textColor = ContextCompat.getColor(getContext(), R.color.md_black_1000);

    private Paint paint;

    private Paint gasStationPaint;

    private Paint mPaintProgress;

    private Paint mPaintProgress2;

    private RectF mRectF;

    private RectF mRectF2;

    private Paint textPaint;

    private String mark = "0";

    private final Rect textBounds = new Rect();

    private int centerY;

    private int centerX;

    private int swipeAndgle = 0;

    Bitmap gasStationBitmap;

    int circleRadius;

    int smallCirclRadius;

    public CustomProgressBar(Context context) {
        super(context);
        initUI();
    }

    public CustomProgressBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        initUI();
    }

    public CustomProgressBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initUI();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public CustomProgressBar(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initUI();
    }

    private void initUI() {

        mRectF = new RectF();
        mRectF2 = new RectF();

        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setStrokeWidth(UiUtils.dpToPx(getContext(), 1));
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(color);


        mPaintProgress = new Paint();  //Параметры первичной
        mPaintProgress.setAntiAlias(true);
        mPaintProgress.setStyle(Paint.Style.STROKE);
        mPaintProgress.setPathEffect(new DashPathEffect(new float[] {10,10}, 0));  //Эффект штрих пунктира
        mPaintProgress.setStrokeWidth(UiUtils.dpToPx(getContext(), 21));
        mPaintProgress.setColor(ContextCompat.getColor(getContext(), R.color.colorAccent1));

        mPaintProgress2 = new Paint();  //Параметры вторичной линии
        mPaintProgress2.setAntiAlias(true);
        mPaintProgress2.setStyle(Paint.Style.STROKE);
        //mPaintProgress2.setPathEffect(new DashPathEffect(new float[] {50,10}, 0));  //Эффект штрих пунктира
        mPaintProgress2.setStrokeWidth(UiUtils.dpToPx(getContext(), 21));
        mPaintProgress2.setColor(ContextCompat.getColor(getContext(), R.color.md_grey_700));

        textPaint = new Paint();  //Параметры текста
        textPaint.setAntiAlias(true);
        textPaint.setStyle(Paint.Style.FILL);
        textPaint.setColor(textColor);
        textPaint.setStrokeWidth(2);

        gasStationPaint = new Paint();

        Resources res = getResources();
        gasStationBitmap = BitmapFactory.decodeResource(res, R.drawable.ic_calendar_white_36dp);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int viewWidth = MeasureSpec.getSize(widthMeasureSpec);
        int viewHeight = MeasureSpec.getSize(heightMeasureSpec);

        int radius = (Math.min(viewWidth, viewHeight) - UiUtils.dpToPx(getContext(), 16)) / 2;
        circleRadius = (Math.min(viewWidth, viewHeight) - UiUtils.dpToPx(getContext(), 16)) / 2;

        path.reset();

        centerX = viewWidth / 2;
        centerY = viewHeight / 2;
        //path.addCircle(centerX, centerY, radius, Path.Direction.CW);  //Внешний круг

        smallCirclRadius = radius - UiUtils.dpToPx(getContext(), 7);  //Вычисляем радиус внутренного
        //path.addCircle(centerX, centerY, smallCirclRadius, Path.Direction.CW); //Внутренний круг
        smallCirclRadius += UiUtils.dpToPx(getContext(), 4);

        mRectF = new RectF(centerX - smallCirclRadius, centerY - smallCirclRadius, centerX + smallCirclRadius, centerY + smallCirclRadius); //Рисуем внешнюю линию

        mRectF2 = new RectF(centerX - smallCirclRadius, centerY - smallCirclRadius, centerX + smallCirclRadius, centerY + smallCirclRadius); //Рисуем внутреннею линию

        textPaint.setTextSize(radius * 0.5f);
    }


    @Override
    protected void onDraw(Canvas canvas) {


        super.onDraw(canvas);

        //canvas.drawPath(path, paint);  //Рисуем путь

        canvas.drawArc(mRectF2, 105, 330, false, mPaintProgress2); //Вторичная линия
        canvas.drawArc(mRectF, 105, swipeAndgle, false, mPaintProgress);  //Первичная линия



        drawTextCentred(canvas);

        //canvas.drawBitmap(gasStationBitmap, centerX - UiUtils.dpToPx(getContext(), 15), centerY + circleRadius - UiUtils.dpToPx(getContext(), 27), gasStationPaint);

    }

    public void drawTextCentred(Canvas canvas) {

        float yPosition = centerY - smallCirclRadius + (circleRadius * 0.10f) - textBounds.exactCenterY();  //Отступ сверху

            textPaint.setTextSize(Utils.convertDpToPx(getContext(), circleRadius * 0.20f));  //размер шрифта
            textPaint.getTextBounds(mark, 0, mark.length(), textBounds);
            canvas.drawText(mark, centerX - textBounds.exactCenterX(), centerY - textBounds.exactCenterY(), textPaint);

    }

    public void setMax(int max) {
        this.max = max;
    }

    public void setProgress(int progress) {
        this.progress = progress;

        int percentage = progress * 100 / max;

        swipeAndgle = percentage * 330 / 100;

        //text = percentage + "%";

        invalidate();
    }

    public void setColor(int color) {
        this.color = color;
    }

    public void setTextColor(int color) {
        this.textColor = color;
        textPaint.setColor(textColor);
    }

    public void setText(String mark) {
        this.mark = mark;
    }
}