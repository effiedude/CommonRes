package com.sprite.base.common.ui.widget.core;

import com.sprite.base.common.ui.widget.core.listener.IBaseWidget;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;
import androidx.annotation.LayoutRes;

/******************************************************************************
 * @path BaseWidget
 * @version 1.0.0.0
 * @describe 默认无内容页面
 * @author 张飞
 * @email
 * @date 2021-06-24-18:02
 * CopyRight(C)2021 小镇精灵工作室版权所有
 * *****************************************************************************
 */
public abstract class BaseWidget extends RelativeLayout implements IBaseWidget
{
    public BaseWidget(Context context)
    {
        this(context,null,0,0);
    }
    
    public BaseWidget(Context context,AttributeSet attrs)
    {
        this(context,attrs,0,0);
    }
    
    public BaseWidget(Context context,AttributeSet attrs,int defStyleAttr)
    {
        this(context,attrs,defStyleAttr,0);
    }
    
    public BaseWidget(Context context,AttributeSet attrs,int defStyleAttr,int defStyleRes)
    {
        super(context,attrs,defStyleAttr,defStyleRes);
        initView();
        initData();
    }
    
    /**
     * layout
     * 设置根布局
     *
     * @return 跟布局
     */
    protected abstract @LayoutRes int layout();
    
    @Override
    public View getView()
    {
        return this;
    }
    
    @Override
    public void bind()
    {
        if(isValid())
        {}
    }
    
    @Override
    public void unbind()
    {}
    
    @Override
    public boolean isValid()
    {
        return true;
    }
    
    protected void initData()
    {}
    
    protected void initView()
    {
        inflate(getContext(),layout(),this);
    }
}
