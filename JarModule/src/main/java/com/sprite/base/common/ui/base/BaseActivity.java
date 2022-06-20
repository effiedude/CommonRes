package com.sprite.base.common.ui.base;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Queue;
import com.sprite.base.common.ui.util.TranslucentStatusBar;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

/******************************************************************************
 * @path BaseActivity
 * @describe 基础类
 * @author
 * @email
 * @date
 * CopyRight(C)2021 小镇精灵工作室版权所有
 * *****************************************************************************
 */
public abstract class BaseActivity extends AppCompatActivity
{
    private final List<BasePresenter> presenterList=new ArrayList<>();
    private final Queue<Runnable> afterResumeActions=new LinkedList<>();
    private ActivityStatus activityStatus;
    
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        activityStatus=ActivityStatus.CREATE;
        super.onCreate(savedInstanceState);
        addAfterResumeAction(()->TranslucentStatusBar.setup(BaseActivity.this,isStatusBarTextDark()));
        ActivityManager.getInstance().add(this);
        onPresentCreate();
    }
    
    @Override
    protected void onStart()
    {
        activityStatus=ActivityStatus.START;
        super.onStart();
    }
    
    @Override
    protected void onResume()
    {
        activityStatus=ActivityStatus.RESUME;
        super.onResume();
        onPresentResume();
        afterResumeAction();
    }
    
    @Override
    protected void onPause()
    {
        activityStatus=ActivityStatus.PAUSE;
        super.onPause();
        onPresentPause();
    }
    
    @Override
    protected void onStop()
    {
        activityStatus=ActivityStatus.STOP;
        super.onStop();
    }
    
    @Override
    protected void onDestroy()
    {
        activityStatus=ActivityStatus.DESTROY;
        super.onDestroy();
        onPresentDestroy();
        ActivityManager.getInstance().remove(this);
    }
    
    public ActivityStatus getStatus()
    {
        return activityStatus;
    }
    
    public boolean isVisible()
    {
        return activityStatus!=ActivityStatus.PAUSE&&activityStatus!=ActivityStatus.STOP&&activityStatus!=ActivityStatus.DESTROY;
    }
    
    protected boolean isStatusBarTextDark()
    {
        List<Fragment> fragments=getSupportFragmentManager().getFragments();
        if(fragments.size()>0)
        {
            Fragment fragment=fragments.get(0);
            if(fragment instanceof BaseFragment)
            {
                return ((BaseFragment)fragment).isStatusBarTextDark();
            }
        }
        return true;
    }
    
    public void addPresenter(BasePresenter presenter)
    {
        presenterList.add(presenter);
    }
    
    public void addAfterResumeAction(Runnable runnable)
    {
        if(activityStatus==ActivityStatus.RESUME)
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
}
