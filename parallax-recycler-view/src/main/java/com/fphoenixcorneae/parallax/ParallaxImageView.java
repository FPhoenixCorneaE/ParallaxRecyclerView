package com.fphoenixcorneae.parallax;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.util.AttributeSet;
import android.widget.ImageView;

import androidx.appcompat.widget.AppCompatImageView;

/**
 * @desc 视差图像显示控件
 * @date 2019-6-15 11:32
 */
public class ParallaxImageView extends AppCompatImageView {

    private final float DEFAULT_PARALLAX_RATIO = 1.2f;
    private final boolean DEFAULT_CENTER_CROP = true;

    private float parallaxRatio = DEFAULT_PARALLAX_RATIO;
    private boolean shouldCenterCrop = DEFAULT_CENTER_CROP;

    private boolean needToTranslate = true;
    private OnParallaxImageListener onParallaxImageListener;

    private int rowYPos = -1;
    private int recyclerViewHeight = -1;
    private int recyclerViewYPos = -1;

    public ParallaxImageView(Context context) {
        this(context, null);
    }

    public ParallaxImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ParallaxImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        setScaleType(ImageView.ScaleType.MATRIX);

        if (attrs != null) {
            TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.ParallaxImageView, 0, 0);
            try {
                this.parallaxRatio = typedArray.getFloat(R.styleable.ParallaxImageView_parallax_ratio, DEFAULT_PARALLAX_RATIO);
                this.shouldCenterCrop = typedArray.getBoolean(R.styleable.ParallaxImageView_parallax_center_crop, DEFAULT_CENTER_CROP);
            } finally {
                typedArray.recycle();
            }
        }
    }

    /**
     * This trick was needed because there is no way to detect when image is displayed,
     * we need to translate image for very first time as well. This will be needed only
     * if you are using async image loading...
     * <p/>
     * # If only there was another way to get notified when image has displayed.
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        ensureTranslate();
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        ensureTranslate();
    }

    @Override
    public void setImageDrawable(Drawable drawable) {
        super.setImageDrawable(drawable);
        ensureTranslate();
    }

    @Override
    public void setImageBitmap(Bitmap bm) {
        super.setImageBitmap(bm);
        ensureTranslate();
    }

    @Override
    public void setImageResource(int resId) {
        super.setImageResource(resId);
        ensureTranslate();
    }

    @Override
    public void setImageURI(Uri uri) {
        super.setImageURI(uri);
        ensureTranslate();
    }

    /**
     * Notify this view when it is back on recyclerView, so we can reset.
     */
    public void reset() {
        this.needToTranslate = true;
    }

    public void centerCrop(boolean enable) {
        this.shouldCenterCrop = enable;
    }

    public void setParallaxRatio(float parallaxRatio) {
        this.parallaxRatio = parallaxRatio;
    }

    public OnParallaxImageListener getOnParallaxImageListener() {
        return onParallaxImageListener;
    }

    public void setOnParallaxImageListener(OnParallaxImageListener onParallaxImageListener) {
        this.onParallaxImageListener = onParallaxImageListener;
    }

    public synchronized boolean doTranslate() {
        if (getDrawable() == null) {
            return false;
        }

        if (getOnParallaxImageListener() != null && getValues()) {
            calculateAndMove();
            return true;
        } else {
            return false;
        }
    }

    private boolean ensureTranslate() {
        if (needToTranslate) {
            needToTranslate = !doTranslate();
        }
        return !needToTranslate;
    }

    private boolean getValues() {
        int[] values = getOnParallaxImageListener().requireValuesForTranslate();
        if (values == null) {
            return false;
        }

        this.rowYPos = values[0];
        this.recyclerViewHeight = values[1];
        this.recyclerViewYPos = values[2];
        return true;
    }

    private void calculateAndMove() {
        float distanceFromCenter = (float) (recyclerViewYPos + recyclerViewHeight) / 2 - rowYPos;

        int drawableHeight = getDrawable().getIntrinsicHeight();
        int imageViewHeight = getMeasuredHeight();
        float scale = 1;
        if (shouldCenterCrop) {
            scale = recomputeImageMatrix();
            drawableHeight *= scale;
        }

        float difference = drawableHeight - imageViewHeight;
        float move = (distanceFromCenter / recyclerViewHeight) * difference * parallaxRatio;

        moveTo((move / 2) - (difference / 2), scale);
    }

    private float recomputeImageMatrix() {
        float scale;
        final int viewWidth = getWidth() - getPaddingLeft() - getPaddingRight();
        final int viewHeight = getHeight() - getPaddingTop() - getPaddingBottom();
        final int drawableWidth = getDrawable().getIntrinsicWidth();
        final int drawableHeight = getDrawable().getIntrinsicHeight();

        if (drawableWidth * viewHeight > drawableHeight * viewWidth) {
            scale = (float) viewHeight / (float) drawableHeight;
        } else {
            scale = (float) viewWidth / (float) drawableWidth;
        }

        return scale;
    }

    private void moveTo(float move, float scale) {
        /* 3*3的矩阵 */
        Matrix imageMatrix = getImageMatrix();
        if (scale != 1) {
            imageMatrix.setScale(scale, scale);
        }

        float[] matrixValues = new float[9];
        imageMatrix.getValues(matrixValues);
        float current = matrixValues[Matrix.MTRANS_Y];
        imageMatrix.postTranslate(0, move - current);

        setImageMatrix(imageMatrix);
        invalidate();
    }

}