package com.ylqhust.bookmarks.ui.controller;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.ImageView;
import android.widget.RelativeLayout;



/**
 * Created by apple on 15/10/28.
 */
public class TabController{

    private RelativeLayout[] relativeLayouts;
    private ImageView[] imageViews;
    private Context context;
    private Bitmap[] selects = new Bitmap[3];
    private Bitmap[] unSelects = new Bitmap[3];
    private int selectColor;
    private int unSelectColor;

    public TabController(RelativeLayout[] relativeLayouts,ImageView[] imageViews,Context context)
    {
        this.relativeLayouts = relativeLayouts;
        this.imageViews = imageViews;
        this.context = context;
    }


    public void setSelectImage(int[] ids)
    {
        setBitmap(selects,ids);
    }

    public void setUnSelectImage(int[] ids)
    {
        setBitmap(unSelects,ids);
    }

    public void setSelectColor(int color)
    {
        this.selectColor = color;
    }

    public void setUnselectColor(int color)
    {
        this.unSelectColor = color;
    }

    private void setBitmap(Bitmap[] bitmaps,int[] ids)
    {
        for(int i=0;i<ids.length;i++)
        {
            bitmaps[i] = BitmapFactory.decodeResource(context.getResources(), ids[i]);
        }
        return;
    }

    public void select(int index)
    {
        for(int i=0;i<relativeLayouts.length;i++)
        {
            if (index == i)
            {
                relativeLayouts[i].setBackgroundColor(selectColor);
                imageViews[i].setImageBitmap(selects[i]);
            }
            else
            {
                relativeLayouts[i].setBackgroundColor(unSelectColor);
                imageViews[i].setImageBitmap(unSelects[i]);
            }
        }
        return;
    }
}
