package com.sprite.base.common.ui.recyclerview;

import android.content.Context;
import android.util.AttributeSet;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/******************************************************************************
 * @path BaseVerticalRecyclerView
 * @version 1.0.0.0
 * @describe
 * @author 张飞
 * @email
 * @date 2021-06-24-18:10
 * CopyRight(C)2021 小镇精灵工作室版权所有
 * *****************************************************************************
 */
public class BaseVerticalRecyclerView extends AbstractBaseRecyclerView
{
    public BaseVerticalRecyclerView(@NonNull Context context)
    {
        super(context);
    }
    
    public BaseVerticalRecyclerView(@NonNull Context context,@Nullable AttributeSet attrs)
    {
        super(context,attrs);
    }
    
    public BaseVerticalRecyclerView(@NonNull Context context,@Nullable AttributeSet attrs,int defStyleAttr)
    {
        super(context,attrs,defStyleAttr);
    }
}
