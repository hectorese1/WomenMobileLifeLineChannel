package com.ali.mirachannel;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;

/**class for generate custom view for family circle and calender for the class
 */
public class CustomViewFamily extends View {
	private String mExampleString; // TODO: use a default from R.string...
	private int mExampleColor = Color.RED; // TODO: use a default from
											// R.color...
	private float mExampleDimension = 0; // TODO: use a default from R.dimen...
	private Drawable mExampleDrawable;

	private TextPaint mTextPaint;
	private float mTextWidth;
	private float mTextHeight;

//	---------------------------------------------------
	private int pointX[];  // Class variable
    private int pointY[]; 
    
    int circleNo[] = {	
    					R.drawable.c1,R.drawable.c2,R.drawable.c3,R.drawable.c4,R.drawable.c5,
			    		R.drawable.c6,R.drawable.c7,R.drawable.c8,R.drawable.c9,R.drawable.c10,
			    		R.drawable.c11,R.drawable.c12,R.drawable.c13,R.drawable.c14,R.drawable.c15,
			    		R.drawable.c16,R.drawable.c17,R.drawable.c18,R.drawable.c19,R.drawable.c20,
			    		R.drawable.c21,R.drawable.c22,R.drawable.c23,R.drawable.c24,R.drawable.c25,
			    		R.drawable.c26,R.drawable.c27,R.drawable.c28,R.drawable.c29,R.drawable.c30,
			    		R.drawable.c31,R.drawable.c32			    		
    				};
    
    int endCircle[] = {
    		
    		R.drawable.c1_28,R.drawable.c1_29,R.drawable.c1_30,R.drawable.c1_31,R.drawable.c1_32
    				};
         
    String[] weekDays = {"Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"};
    String[] Months = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
    
    private String lmc_date ="27/11/2014";// FamilyPlanView.Lmc_Date[FamilyPlanView.index];//"10/02/2014"; 
    int lmc_days = 29;//Integer.parseInt(FamilyPlanView.Lmc_days[FamilyPlanView.index]);//29;
    int currrent_day =1;
    private String currrent_date="28/11/2014";;
	
//	---------------------------------------------------
	public CustomViewFamily(Context context) {
		super(context);
		init(null, 0);
	}

	public CustomViewFamily(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(attrs, 0);
	}

	public CustomViewFamily(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
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
		mTextPaint.setTextAlign(Paint.Align.LEFT);

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

		// Draw the text.
		canvas.drawText(mExampleString, paddingLeft
				+ (contentWidth - mTextWidth) / 2, paddingTop
				+ (contentHeight + mTextHeight) / 2, mTextPaint);

		// Draw the example drawable on top of the text.
		if (mExampleDrawable != null) {
			mExampleDrawable.setBounds(paddingLeft, paddingTop, paddingLeft
					+ contentWidth, paddingTop + contentHeight);
			mExampleDrawable.draw(canvas);
		}
		
		drawCirclesOnCircumference(getWidth()/2-15, getHeight()/2, getWidth()/2 - 30, lmc_days, canvas);
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
	
public void drawCirclesOnCircumference(int x0, int y0, int radius, int noOfCircles, Canvas canvas){
    	
    	double angle = 0;
    	currrent_day=currrent_day%noOfCircles;
    	pointX = new int[noOfCircles];
    	pointY = new int[noOfCircles];                       
        
        Paint paint = new Paint();
        paint.setStyle(Paint.Style.STROKE);
        Bitmap b = null; 
		for(int i = 0 ; i < noOfCircles ; i++){
            angle = (i) * ((double) (360) / noOfCircles);
            System.out.println("ANGle "+angle);            
            pointX[i] = (int) (x0 + radius * Math.cos(Math.toRadians(angle - 90)));
            pointY[i] = (int) (y0 + radius * Math.sin(Math.toRadians(angle - 90)));            
            paint.setStyle(Paint.Style.FILL);            
            if(i < lmc_days-1){
            b=BitmapFactory.decodeResource(getResources(), circleNo[i]);
            }
            else{            	
            	b=BitmapFactory.decodeResource(getResources(), endCircle[(i-27)]);
            }                     
            if(i == 0){
            	 canvas.drawBitmap(b, pointX[i], pointY[i], paint);            	 
              }else{    	  
             	 canvas.drawBitmap(b, pointX[i], pointY[i], paint);
	            	if(i < 7 && i > 0 || i < (lmc_days - 1) && i > 18){
	                	 canvas.drawBitmap(b, pointX[i], pointY[i], paint);	            	
	            	}else {
	                	 canvas.drawBitmap(b, pointX[i], pointY[i], paint);
					}	            	
	            	if(i == lmc_days-1){
	                	 canvas.drawBitmap(b, pointX[i], pointY[i], paint);
	            	}	            	
	            	if(currrent_day == i){
	                	 canvas.drawBitmap(b, pointX[i], pointY[i], paint);
                  	}
              }
            
        }						
		paint.setColor(Color.BLACK); 			
		canvas.drawLine(getWidth()/2, getHeight()/2, pointX[currrent_day], pointY[currrent_day], paint);
		paint.setColor(Color.TRANSPARENT);
	        canvas.drawPaint(paint);
    }

public String getLmc_date() {
	return lmc_date;
}

public void setLmc_date(String lmc_date) {
	this.lmc_date = lmc_date;
}

public int getLmc_days() {
	return lmc_days;
}

public void setLmc_days(int lmc_days) {
	this.lmc_days = lmc_days;
}

public int getCurrrent_day() {
	return currrent_day;
}

public void setCurrrent_day(int currrent_day) {
	this.currrent_day = currrent_day;
}

public String getCurrrent_date() {
	return currrent_date;
}

public void setCurrrent_date(String currrent_date) {
	this.currrent_date = currrent_date;
}
}
