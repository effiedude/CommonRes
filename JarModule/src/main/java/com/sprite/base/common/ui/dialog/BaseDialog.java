package com.sprite.base.common.ui.dialog;

import com.sprite.base.common.ui.R;
import com.townspriter.base.foundation.utils.device.DisplayUtil;
import com.townspriter.base.foundation.utils.system.SystemInfo;
import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

/******************************************************************************
 * @path BaseDialog
 * @describe 系统弹窗基类
 * @author 张飞
 * @email
 * @date 2021年12月12日21:24:50
 * *****************************************************************************
 */
public abstract class BaseDialog extends DialogFragment
{
    public static final String TAG="BaseDialog";
    private final Rect rootRect=new Rect();
    protected @Nullable View rootView;
    private @Nullable DialogInterface.OnDismissListener mDismissListener;
    private @Nullable OutSideClickListener outsideListener;
    private @Nullable OnBackListener backListener;
    
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
    
    /**
     * 是否支持点击屏幕外部区域隐藏弹窗
     *
     * @return true:是 false:否
     */
    public abstract boolean supportOutsideTouch();
    
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NO_TITLE,R.style.DialogTheme);
    }
    
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
        if(mDismissListener!=null&&getDialog()!=null)
        {
            getDialog().setOnDismissListener(mDismissListener);
        }
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
        rootView=root;
        rootView.post(()->
        {
            rootView.getGlobalVisibleRect(rootRect);
            rootRect.bottom=SystemInfo.INSTANCE.getScreenHeight(getContext())-rootRect.bottom-DisplayUtil.getStatusBarHeight();
        });
        /** 点击屏幕不消失 */
        if(null!=getDialog())
        {
            getDialog().setCanceledOnTouchOutside(supportOutsideTouch());
            getDialog().setOnKeyListener((view,keyCode,event)->
            {
                if(keyCode==KeyEvent.KEYCODE_BACK&&event.getAction()==MotionEvent.ACTION_UP&&backListener!=null)
                {
                    backListener.onBackPressed();
                    return true;
                }
                return false;
            });
            getDialog().getWindow().getDecorView().setOnTouchListener(new View.OnTouchListener()
            {
                @SuppressLint("ClickableViewAccessibility")
                @Override
                public boolean onTouch(View view,MotionEvent motionEvent)
                {
                    if(motionEvent.getAction()==MotionEvent.ACTION_UP&&rootRect.contains((int)motionEvent.getRawX(),(int)motionEvent.getRawY()))
                    {
                        if(null!=outsideListener)
                        {
                            outsideListener.onOutSideClicked();
                        }
                    }
                    return false;
                }
            });
        }
    }
    
    public @Nullable DialogInterface.OnDismissListener getDismissListener()
    {
        return mDismissListener;
    }
    
    public void setDismissListener(@Nullable DialogInterface.OnDismissListener dismissListener)
    {
        mDismissListener=dismissListener;
        if(getDialog()!=null)
        {
            getDialog().setOnDismissListener(mDismissListener);
        }
    }
    
    public void setOutsideClickListener(@Nullable OutSideClickListener listener)
    {
        outsideListener=listener;
    }
    
    public void setBackListener(@Nullable OnBackListener listener)
    {
        backListener=listener;
    }
    
    public interface OutSideClickListener
    {
        void onOutSideClicked();
    }
    public interface OnBackListener
    {
        void onBackPressed();
    }
}
