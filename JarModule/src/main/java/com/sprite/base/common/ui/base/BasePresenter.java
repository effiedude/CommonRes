package com.sprite.base.common.ui.base;

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
    
    protected void onCreate()
    {}
    
    protected void onViewCreated(View view)
    {}
    
    protected void onResume()
    {}
    
    protected void onPause()
    {}
    
    protected void onDestroyView()
    {}
    
    protected void onDestroy()
    {}
    
    protected void addAfterResume(Runnable runnable)
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
