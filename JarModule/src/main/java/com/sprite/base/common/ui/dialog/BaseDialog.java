package com.sprite.base.common.ui.dialog;

import com.sprite.base.common.ui.R;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

/******************************************************************************
 * @path BaseDialog
 * @version 1.0.0.0
 * @describe 系统弹窗基类
 * @author 张飞
 * @email
 * @date 2021-06-24-18:00
 * CopyRight(C)2021 小镇精灵工作室版权所有
 * *****************************************************************************
 */
public abstract class BaseDialog extends DialogFragment
{
    private static final String TAG="BaseDialog";
    
    /**
     * layout
     * 设置跟布局
     * 
     * @return 跟布局
     */
    protected abstract @LayoutRes int layout();
    
    /**
     * release
     * 释放资源(根据需要选择性调用)
     */
    public abstract void release();
    
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState)
    {
        /** 设置系统弹窗横向铺满 */
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        super.onActivityCreated(savedInstanceState);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(getContext().getColor(R.color.uixcolorxwhite)));
        getDialog().getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT,WindowManager.LayoutParams.WRAP_CONTENT);
    }
    
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,@Nullable ViewGroup container,@Nullable Bundle savedInstanceState)
    {
        if(layout()==0)
        {
            return super.onCreateView(inflater,container,savedInstanceState);
        }
        return inflater.inflate(layout(),container,false);
    }
    
    @Override
    public void onViewCreated(@NonNull View view,@Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view,savedInstanceState);
        initView(view);
    }
    
    @Override
    public void onDestroyView()
    {
        super.onDestroyView();
        release();
    }
    
    protected void initView(View root)
    {
        // 点击屏幕不消失
        getDialog().setCanceledOnTouchOutside(false);
    }
}
