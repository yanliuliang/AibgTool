package com.tool.aibglibrary.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;

import com.tool.aibglibrary.R;


/**
 * Created by wangxingsheng on 2018/6/8.
 * 可以设置最大宽高的FrameLayout
 * 默认优先比例设置
 * 不支持参数小于零
 *
 */
public class MaxLayout extends FrameLayout {
    private float mMaxHeightRatio = -1f;// 优先级高
    private float mMaxHeight = -1f;// 优先级低
    private float mMaxWidthRatio = -1f;// 优先级高
    private float mMaxWidth = -1f;// 优先级低
    private int parentWidth;
    private int parentHeight;
    private boolean firstHeightRatio = true;//高默认优先比例设置
    private boolean firstWidthRatio = true;//宽默认优先比例设置

    public MaxLayout(Context context) {
        super(context);
    }

    public MaxLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        initAttrs(context, attrs);
    }

    public MaxLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initAttrs(context, attrs);
    }

    private void initAttrs(Context context, AttributeSet attrs) {
        TypedArray a = context.obtainStyledAttributes(attrs,
                R.styleable.MaxLayout);
        firstHeightRatio = a.getBoolean(R.styleable.MaxLayout_first_ratio_height, true);
        firstWidthRatio = a.getBoolean(R.styleable.MaxLayout_first_ratio_width,true);
        mMaxHeightRatio = a.getFloat(R.styleable.MaxLayout_max_height_ratio, -1f);
        mMaxHeight = a.getDimension(R.styleable.MaxLayout_max_height, -1f);
        mMaxWidthRatio = a.getFloat(R.styleable.MaxLayout_max_width_ratio, -1f);
        mMaxWidth = a.getDimension(R.styleable.MaxLayout_max_width, -1f);
        a.recycle();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        initParentWH();
        initWH();

        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        int maxHeightMeasureSpec = MeasureSpec.makeMeasureSpec(heightSize <= mMaxHeight ? heightSize : (int) mMaxHeight, heightMode);

        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int maxWidthMeasureSpec = MeasureSpec.makeMeasureSpec(widthSize <= mMaxWidth ? widthSize : (int) mMaxWidth, widthMode);

        super.onMeasure(maxWidthMeasureSpec, maxHeightMeasureSpec);
    }

    /**
     * 计算需要设置的宽高
     */
    private void initWH() {
        if((firstHeightRatio && mMaxHeightRatio > 0)
                || (!firstHeightRatio && mMaxHeight < 0)){
            mMaxHeight = mMaxHeightRatio * parentHeight;
        }
        if((firstWidthRatio && mMaxWidthRatio > 0)
                || (!firstWidthRatio && mMaxWidth < 0)){
            mMaxWidth = mMaxWidthRatio * parentWidth;
        }
    }

    /**
     * 获取父控件的宽高
     */
    private void initParentWH() {
        ViewGroup parent = (ViewGroup) getParent();
        if(null != parent){
            parentWidth = parent.getWidth();
            parentHeight = parent.getHeight();
        }else {
            WindowManager wm = (WindowManager) getContext()
                    .getSystemService(Context.WINDOW_SERVICE);
            DisplayMetrics dm = new DisplayMetrics();
            if (wm != null) {
                wm.getDefaultDisplay().getMetrics(dm);
                parentWidth = dm.widthPixels;
                parentHeight = dm.heightPixels;
            }
        }
    }
}