package com.sprite.base.common.ui.fragment;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Queue;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.sprite.base.common.ui.activity.BaseActivity;

/******************************************************************************
 * @path BaseFragment
 * @version 1.0.0.0
 * @describe 片段基类
 * @author 张飞
 * @email
 * @date 2021-06-24-17:58
 * CopyRight(C)2021 小镇精灵工作室版权所有
 * *****************************************************************************
 */
public abstract class BaseFragment extends Fragment implements View.OnTouchListener
{
    private final List<BasePresenter> presenterList=new ArrayList<>();
    private final Queue<Runnable> afterResumeActions=new LinkedList<>();
    private View rootView;
    private BaseActivity baseActivity;
    
    @Override
    public void onAttach(@NonNull Context context)
    {
        super.onAttach(context);
        baseActivity=(BaseActivity)getActivity();
    }
    
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        baseActivity=(BaseActivity)getActivity();
        if(baseActivity!=null&&isScreenSecure())
        {
            baseActivity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_SECURE);
        }
        onPresentCreate();
    }
    
    @Override
    @Nullable
    public View onCreateView(LayoutInflater inflater,@Nullable ViewGroup container,@Nullable Bundle savedInstanceState)
    {
        rootView=inflater.inflate(setLayoutResourceId(),container);
        return rootView;
    }
    
    @Override
    public void onViewCreated(View view,Bundle savedInstanceState)
    {
        // 监听触摸事件防止触摸事件被下层控件响应
        view.setOnTouchListener(this);
        onPresentViewCreated(view);
    }
    
    @Override
    public boolean onTouch(View view,MotionEvent event)
    {
        return true;
    }
    
    @Override
    public void onResume()
    {
        super.onResume();
        afterResumeAction();
        onPresentResume();
    }
    
    @Override
    public void onPause()
    {
        super.onPause();
        onPresentPause();
    }
    
    @Override
    public void onDestroyView()
    {
        super.onDestroyView();
        onPresentDestroyView();
    }
    
    @Override
    public void onDestroy()
    {
        super.onDestroy();
        onPresentDestroy();
    }

    public BaseActivity getBaseActivity()
    {
        return baseActivity;
    }

    public @Nullable <T extends View> T findViewById(int id)
    {
        if(rootView==null)
        {
            return null;
        }
        return rootView.findViewById(id);
    }

    public boolean onBackPressed()
    {
        return false;
    }
    
    // 是否是沉浸式状态栏
    public boolean isStatusBarTextDark()
    {
        return false;
    }
    
    // 是否允许当前页面被截屏
    public boolean isScreenSecure()
    {
        return false;
    }
    
    public void addPresenter(BasePresenter presenter)
    {
        presenterList.add(presenter);
    }
    
    public void addAfterResumeAction(Runnable runnable)
    {
        if(isResumed())
        {
            runnable.run();
        }
        else
        {
            afterResumeActions.offer(runnable);
        }
    }
    
    private void onPresentCreate()
    {
        for(BasePresenter presenter:presenterList)
        {
            presenter.onCreate();
        }
    }
    
    private void onPresentViewCreated(View view)
    {
        for(BasePresenter presenter:presenterList)
        {
            presenter.onViewCreated(view);
        }
    }
    
    private void onPresentResume()
    {
        for(BasePresenter presenter:presenterList)
        {
            presenter.onResume();
        }
    }
    
    private void onPresentPause()
    {
        for(BasePresenter presenter:presenterList)
        {
            presenter.onPause();
        }
    }
    
    private void onPresentDestroyView()
    {
        for(BasePresenter presenter:presenterList)
        {
            presenter.onDestroyView();
        }
    }
    
    private void onPresentDestroy()
    {
        for(BasePresenter presenter:presenterList)
        {
            presenter.onDestroy();
        }
    }
    
    private void afterResumeAction()
    {
        while(!afterResumeActions.isEmpty())
        {
            Objects.requireNonNull(afterResumeActions.poll()).run();
        }
    }
    
    /**
     * setLayoutResourceId
     * 设置根布局资源
     *
     * @return 根布局资源标示
     */
    protected abstract @LayoutRes int setLayoutResourceId();
}
