package com.sprite.base.common.ui.fragment;

import com.sprite.base.common.ui.activity.BaseActivity;
import android.os.Bundle;
import android.view.View;

public abstract class BasePresenter
{
    public BaseActivity baseActivity;
    public BaseFragment baseFragment;
    
    public BasePresenter(BaseActivity activity)
    {
        baseActivity=activity;
    }
    
    public BasePresenter(BaseFragment fragment)
    {
        baseActivity=fragment.getBaseActivity();
        baseFragment=fragment;
    }
    
    public Bundle getArguments()
    {
        if(baseFragment==null)
        {
            return baseActivity.getIntent().getExtras();
        }
        else
        {
            return baseFragment.getArguments();
        }
    }
    
    public void onCreate()
    {}
    
    public void onViewCreated(View view)
    {}
    
    public void onResume()
    {}
    
    public void onPause()
    {}
    
    public void onDestroyView()
    {}
    
    public void onDestroy()
    {}
    
    public void addAfterResume(Runnable runnable)
    {
        if(baseFragment==null)
        {
            baseActivity.addAfterResumeAction(runnable);
        }
        else
        {
            baseFragment.addAfterResumeAction(runnable);
        }
    }
}
