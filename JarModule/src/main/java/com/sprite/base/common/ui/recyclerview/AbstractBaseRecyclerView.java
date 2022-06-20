package com.sprite.base.common.ui.recyclerview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/******************************************************************************
 * @path AbstractBaseRecyclerView
 * @version 1.0.0.0
 * @describe 横竖滚动控件基类.业务场景除非特殊定制化.否则应该不需要继承此类.应根据需求继承:{@link BaseHorizontalRecyclerView}或{@link BaseVerticalRecyclerView}
 * @author 张飞
 * @email
 * @date 2021-06-28-15:08
 * CopyRight(C)2021 智慧培森科技版权所有
 * *****************************************************************************
 */
public abstract class AbstractBaseRecyclerView extends RecyclerView
{
    private static final int ITEMxVIEWxCACHExSIZE=30;
    /** 最小滑动距离优化.系统认为最小的滑动距离不一定精确.需要斟酌使用 */
    private static final float SLOPxTHRESHOLDxVERTICALxPX=6;
    private static final float SLOPxTHRESHOLDxHORIZONTALxPX=6;
    private static final float SLOPxFACTOR=5;
    
    public AbstractBaseRecyclerView(@NonNull Context context)
    {
        this(context,null,0);
    }
    
    public AbstractBaseRecyclerView(@NonNull Context context,@Nullable AttributeSet attrs)
    {
        this(context,attrs,0);
    }
    
    public AbstractBaseRecyclerView(@NonNull Context context,@Nullable AttributeSet attrs,int defStyleAttr)
    {
        super(context,attrs,defStyleAttr);
        initView();
    }
    
    /*********************************
     * @function isVerticalOrientation
     * @since JDK 1.7.0-79
     * @describe 设置控件的方向
     * @param
     * @exception
     * @return
     * true:纵向(默认) false:横向
     * @date 2021-06-28-15:17
     * @version 1.0.0.0
     * ********************************
     */
    public boolean isVerticalOrientation()
    {
        return true;
    }
    
    protected void initView()
    {
        LinearLayoutManager layoutManager=new LinearLayoutManager(getContext());
        if(isVerticalOrientation())
        {
            layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        }
        else
        {
            layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        }
        setLayoutManager(layoutManager);
        // RecyclerItemDecoration itemDecoration=new RecyclerItemDecoration(0);
        // addItemDecoration(itemDecoration);
        // 如果每个子类条的高度是固定的设置此方法减少重回次数
        // setHasFixedSize(true);
        // 加大缓存
        setItemViewCacheSize(ITEMxVIEWxCACHExSIZE);
        setDrawingCacheEnabled(true);
        setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
        /** 优化父控件滑动与子控件点击冲突 */
        addOnItemTouchListener(new OnItemTouchListener()
        {
            public float downX,downY,upX,upY;
            
            @Override
            public boolean onInterceptTouchEvent(@NonNull RecyclerView recyclerView,@NonNull MotionEvent motionEvent)
            {
                switch(motionEvent.getAction())
                {
                    case MotionEvent.ACTION_DOWN:
                    case MotionEvent.ACTION_MOVE:
                        downX=motionEvent.getX();
                        downY=motionEvent.getY();
                        break;
                    case MotionEvent.ACTION_UP:
                    case MotionEvent.ACTION_CANCEL:
                        upX=motionEvent.getX();
                        upY=motionEvent.getY();
                        /** 距离较小当作点击事件来处理 */
                        if(Math.abs(upX-downX)<SLOPxTHRESHOLDxVERTICALxPX&&Math.abs(upY-downY)<SLOPxTHRESHOLDxHORIZONTALxPX)
                        {
                            return false;
                        }
                        /** 真正的滑动事件 */
                        if(Math.abs(upX-downX)>SLOPxTHRESHOLDxVERTICALxPX*SLOPxFACTOR||Math.abs(upY-downY)>SLOPxTHRESHOLDxHORIZONTALxPX*SLOPxFACTOR)
                        {
                            return true;
                        }
                        break;
                    default:
                        break;
                }
                return false;
            }
            
            @Override
            public void onTouchEvent(@NonNull RecyclerView recyclerView,@NonNull MotionEvent motionEvent)
            {}
            
            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept)
            {}
        });
    }
}
