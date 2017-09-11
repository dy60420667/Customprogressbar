package com.laomao.customprogressbarview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

/**
 * http://blog.csdn.net/mouse12138/article/details/51837650
 *
 * http://www.bubuko.com/infodetail-1148533.html
 */
public class CustomProgressBar extends View {
    private int max_num = 6;//圆的总数
    private int progress = 0;//实心圆数


    private Paint paint_background;
    private int item_width = 0;//每两个圆心之间的距离

    private int bar_background_radius = 50;//大圆的半径
    private int bar_line_width = 4;//线的高度
    private Integer bar_line_color = Color.BLACK;//线的颜色
    private Integer bar_background_color;//进度的背景颜色
    private Integer bar_color;//进度的颜色

    private Integer rect_margin_top = 20;
    private Integer space_between_round = 2;//两个圆之间的间距


    public CustomProgressBar(Context context) {
        super(context);
    }

    public CustomProgressBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public CustomProgressBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.CustomProgressBarStyle);
        bar_line_color = ta.getInteger(R.styleable.CustomProgressBarStyle_bar_line_color, Color.rgb(224,224,224));
        bar_background_color = ta.getInteger(R.styleable.CustomProgressBarStyle_bar_background_color, Color.rgb(244,244,244));
        bar_color = ta.getInteger(R.styleable.CustomProgressBarStyle_bar_color, Color.rgb( 255,87,69));
        bar_line_width = ta.getInteger(R.styleable.CustomProgressBarStyle_bar_line_width, (int) Utils.dip2px(0.5f));
        bar_background_radius = ta.getInteger(R.styleable.CustomProgressBarStyle_bar_background_radius,(int) Utils.dip2px(6.5f));
        rect_margin_top = ta.getInteger(R.styleable.CustomProgressBarStyle_rect_margin_top,(int) Utils.dip2px(2.5f));
        space_between_round = ta.getInteger(R.styleable.CustomProgressBarStyle_space_between_round,(int) Utils.dip2px(2f));

        paint_background = new Paint();
        paint_background.setStrokeWidth(4);
        paint_background.setStyle(Paint.Style.STROKE);
        paint_background.setColor(bar_background_color);
    }


    public void setMax(int max_num){
        if(max_num<1){
            setVisibility(View.INVISIBLE);
            return;
        }
        this.max_num = max_num;
        initItemWidth();
    }


    public void setProgress(int progress) {
        this.progress = progress;
        if (progress < 0) {
            this.progress = 0;
        }
        if (progress > max_num) {
            this.progress = max_num;
        }
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawBackground(canvas);
        drawBackgroundLine(canvas);
        drawProgress(canvas);
    }

    private void drawProgress(Canvas canvas) {
        paint_background.setColor(bar_color);
        int centerY = getHeight()/2;
        int centerX = bar_background_radius;


        int x_top =centerX+ bar_background_radius /2 ,y_top = getHeight()/2-bar_background_radius+rect_margin_top+space_between_round;
        int x_right = getWidth()- bar_background_radius *3/2-item_width*(max_num-progress),y_right = getHeight()/2+bar_background_radius-rect_margin_top-space_between_round;

        paint_background.setStyle(Paint.Style.FILL);
        canvas.drawRect(x_top,y_top,x_right,y_right, paint_background);

        //画空心圆的实心背景图
        for (int i = 0; i < progress ; i++) {
            canvas.drawCircle(centerX, centerY, bar_background_radius -space_between_round, paint_background);
            centerX += item_width;
        }

    }

    private void drawBackgroundLine(Canvas canvas) {
        paint_background.setColor(bar_line_color);
        int centerY = getHeight()/2;
        int centerX = bar_background_radius;


        int x_top =centerX+ bar_background_radius /2 ,y_top = getHeight()/2-bar_background_radius+rect_margin_top+bar_line_width;
        int x_right = getWidth()- bar_background_radius *3/2,y_right = getHeight()/2+bar_background_radius-rect_margin_top-bar_line_width;


        paint_background.setStyle(Paint.Style.FILL);
        canvas.drawRect(x_top,y_top,x_right,y_right, paint_background);

        //画空心圆的实心背景图
        for (int i = 0; i < max_num ; i++) {
            canvas.drawCircle(centerX, centerY, bar_background_radius - bar_line_width, paint_background);
            centerX += item_width;
        }
    }

    private void drawBackground(Canvas canvas) {

        paint_background.setColor(bar_background_color);
        int centerY = getHeight()/2;
        int centerX = bar_background_radius;

        int x_top =centerX+ bar_background_radius /2 ,y_top = getHeight()/2-bar_background_radius+rect_margin_top;
        int x_right = getWidth()- bar_background_radius *3/2,y_right = getHeight()/2+bar_background_radius-rect_margin_top;

        paint_background.setStyle(Paint.Style.FILL);
        canvas.drawRect(x_top,y_top,x_right,y_right, paint_background);

        //画空心圆的实心背景图
        for (int i = 0; i < max_num ; i++) {
            canvas.drawCircle(centerX, centerY, bar_background_radius, paint_background);
            centerX += item_width;
        }
    }

    /**
     * 重写onMeasure
     * @param widthMeasureSpec
     * @param heightMeasureSpec
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        initItemWidth();
    }

    private void initItemWidth(){
        if(getMeasuredWidth()!=0){
            if(max_num>1){
                item_width = (getMeasuredWidth() - bar_background_radius *2)/(max_num-1);
            }else{
                item_width=0;
            }
            invalidate();
        }
    }
}