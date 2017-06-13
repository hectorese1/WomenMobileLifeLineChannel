package com.ali.mirachannel;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

/** class will generate custom view for prenatal screen
 */
@SuppressLint("DrawAllocation")
public class CustomView extends View {
	private String mExampleString; // TODO: use a default from R.string...
	private int mExampleColor = Color.RED; // TODO: use a default from
											// R.color...
	private float mExampleDimension = 0; // TODO: use a default from R.dimen...
	private Drawable mExampleDrawable;

	private TextPaint mTextPaint;
	private float mTextWidth;
	private float mTextHeight;
	private Context context;
	public CustomView(Context context) {
		super(context);
		this.context = context;
		init(null, 0);
	}

	public CustomView(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;
		init(attrs, 0);
	}

	public CustomView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		this.context = context;
		init(attrs, defStyle);
	}

	private void init(AttributeSet attrs, int defStyle) {
		// Load attributes
		final TypedArray a = getContext().obtainStyledAttributes(attrs,
				R.styleable.CustomView, defStyle, 0);

		mExampleString = a.getString(R.styleable.CustomView_exampleString);
		mExampleColor = a.getColor(R.styleable.CustomView_exampleColor,
				mExampleColor);
		// Use getDimensionPixelSize or getDimensionPixelOffset when dealing
		// with
		// values that should fall on pixel boundaries.
		mExampleDimension = a.getDimension(
				R.styleable.CustomView_exampleDimension, mExampleDimension);

		if (a.hasValue(R.styleable.CustomView_exampleDrawable)) {
			mExampleDrawable = a
					.getDrawable(R.styleable.CustomView_exampleDrawable);
			mExampleDrawable.setCallback(this);
		}

		a.recycle();

		// Set up a default TextPaint object
		mTextPaint = new TextPaint();
		mTextPaint.setFlags(Paint.ANTI_ALIAS_FLAG);
		mTextPaint.setTextAlign(Paint.Align.CENTER);

		// Update TextPaint and text measurements from attributes
		invalidateTextPaintAndMeasurements();
	}

	private void invalidateTextPaintAndMeasurements() {
		mTextPaint.setTextSize(mExampleDimension);
		mTextPaint.setColor(mExampleColor);
		mTextWidth = mTextPaint.measureText(mExampleString);

		Paint.FontMetrics fontMetrics = mTextPaint.getFontMetrics();
		mTextHeight = fontMetrics.bottom;
	}

	@SuppressLint("DrawAllocation")
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);

		// TODO: consider storing these as member variables to reduce
		// allocations per draw cycle.
		int paddingLeft = getPaddingLeft();
		int paddingTop = getPaddingTop();
		int paddingRight = getPaddingRight();
		int paddingBottom = getPaddingBottom();

		int contentWidth = getWidth() - paddingLeft - paddingRight;
		int contentHeight = getHeight() - paddingTop - paddingBottom;
		if (mExampleDrawable != null) {
			
			float ratioW =  (9*canvas.getWidth()/10)/(float)mExampleDrawable.getIntrinsicWidth();
			float ratioH = canvas.getHeight()/(float)mExampleDrawable.getIntrinsicHeight();
			
			
			float ratioC = canvas.getHeight()/(float)canvas.getWidth();//?ratioW:ratioH;
			
			int width = 9*canvas.getWidth()/10;//(int) (mExampleDrawable.getIntrinsicWidth()*ratioC);
			int hight = (int) (mExampleDrawable.getIntrinsicHeight()*ratioW);
								
			paddingLeft = canvas.getWidth() / 2 - width / 2;
			paddingTop = canvas.getHeight() / 2-canvas.getHeight()/8 - hight / 2;
			contentWidth = width; 
			contentHeight = hight;
			
			
			Rect rect = new Rect(paddingLeft-5, paddingTop-5, paddingLeft+width+5, (int) (paddingTop+hight+6*mExampleDimension));
			Paint paint = new Paint();
			paint.setFlags(Paint.ANTI_ALIAS_FLAG);
			paint.setColor(Color.WHITE);
			canvas.drawRect(rect, paint);

           // ScrollView scrollView=new ScrollView(context);
           // scrollView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
//            scrollView.measure(10,10);
//            scrollView.layout(0, 0, 10, 10);

//            LinearLayout parent = new LinearLayout(context);
//            parent.measure(10, 10);
//            parent.layout(0, 0, 10, 10);
//            parent.addView(scrollView);

            //ScrollView scrollView=new ScrollView(context);

            LinearLayout layout = new LinearLayout(context);
			TextView textView = new TextView(context);
			textView.setVisibility(View.VISIBLE);
			textView.setGravity(Gravity.CENTER);

			if(canvas.getHeight()<600){
				textView.setTextSize(20);
			}else
			textView.setTextSize(mExampleDimension);
			textView.setPadding(paddingLeft, paddingTop + contentHeight, paddingLeft, 10);
			textView.setWidth(canvas.getWidth()-20);
			textView.setText(mExampleString);			
			layout.addView(textView);
			int ht = textView.getHeight();
			System.out.println("Height = "+ht +" "+canvas.getHeight());
			
			layout.measure(10, 10);
			layout.layout(0, 0, 10, 10);

//            scrollView.measure(10,10);
//            scrollView.layout(0, 0, 10, 10);
             //scrollView.addView(layout);
//            scrollView.addView(layout);
//            scrollView.layout(0,0,10,10);
			System.out.println("ratioW "+ratioW+"  ratioH "+ratioH+"  width "+width+"  hight "+hight);
			mExampleDrawable.setBounds(paddingLeft, paddingTop, paddingLeft	+ contentWidth, paddingTop + contentHeight);			
			mExampleDrawable.draw(canvas);
			layout.draw(canvas);
           // scrollView.draw(canvas);
           // scrollView.draw(canvas);
		}
	}

	/**
	 * Gets the example string attribute value.
	 * 
	 * @return The example string attribute value.
	 */
	public String getExampleString() {
		return mExampleString;
	}

	/**
	 * Sets the view's example string attribute value. In the example view, this
	 * string is the text to draw.
	 * 
	 * @param exampleString
	 *            The example string attribute value to use.
	 */
	public void setExampleString(String exampleString) {
		mExampleString = exampleString;
		invalidateTextPaintAndMeasurements();
	}

	/**
	 * Gets the example color attribute value.
	 * 
	 * @return The example color attribute value.
	 */
	public int getExampleColor() {
		return mExampleColor;
	}

	/**
	 * Sets the view's example color attribute value. In the example view, this
	 * color is the font color.
	 * 
	 * @param exampleColor
	 *            The example color attribute value to use.
	 */
	public void setExampleColor(int exampleColor) {
		mExampleColor = exampleColor;
		invalidateTextPaintAndMeasurements();
	}

	/**
	 * Gets the example dimension attribute value.
	 * 
	 * @return The example dimension attribute value.
	 */
	public float getExampleDimension() {
		return mExampleDimension;
	}

	/**
	 * Sets the view's example dimension attribute value. In the example view,
	 * this dimension is the font size.
	 * 
	 * @param exampleDimension
	 *            The example dimension attribute value to use.
	 */
	public void setExampleDimension(float exampleDimension) {
		mExampleDimension = exampleDimension;
		invalidateTextPaintAndMeasurements();
	}

	/**
	 * Gets the example drawable attribute value.
	 * 
	 * @return The example drawable attribute value.
	 */
	public Drawable getExampleDrawable() {
		return mExampleDrawable;
	}

	/**
	 * Sets the view's example drawable attribute value. In the example view,
	 * this drawable is drawn above the text.
	 * 
	 * @param exampleDrawable
	 *            The example drawable attribute value to use.
	 */
	public void setExampleDrawable(Drawable exampleDrawable) {
		mExampleDrawable = exampleDrawable;
	}
	
	 private void drawTextOnCanvas(Canvas canvas, String text,Paint paint) {
         // maybe color the bacground..
         canvas.drawPaint(paint);
         
         // Setup a textview like you normally would with your activity context
         TextView tv = new TextView(context);

         // setup text
         tv.setText(text);

         // maybe set textcolor
         tv.setTextColor(Color.BLACK);

         // you have to enable setDrawingCacheEnabled, or the getDrawingCache will return null
         tv.setDrawingCacheEnabled(true);

         // we need to setup how big the view should be..which is exactly as big as the canvas
         tv.measure(MeasureSpec.makeMeasureSpec(canvas.getWidth(), MeasureSpec.EXACTLY), MeasureSpec.makeMeasureSpec(canvas.getHeight(), MeasureSpec.EXACTLY));

         // assign the layout values to the textview
         tv.layout(0, 0, tv.getMeasuredWidth(), tv.getMeasuredHeight());

         // draw the bitmap from the drawingcache to the canvas
         canvas.drawBitmap(tv.getDrawingCache(), 0, 0, paint);

         // disable drawing cache
         tv.setDrawingCacheEnabled(false);
 }
}
