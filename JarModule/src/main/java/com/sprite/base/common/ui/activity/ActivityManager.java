package com.sprite.base.common.ui.activity;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import android.app.Activity;

/******************************************************************************
 * @path ActivityManager
 * @version 1.0.0.0
 * @describe 活动窗口管理者
 * @author 张飞
 * @email
 * @date 2022年04月17日16:31:37
 * CopyRight(C)2021 小镇精灵工作室版权所有
 * *****************************************************************************
 */
public class ActivityManager
{
    private static volatile ActivityManager instance=null;
    private final List<ActivityState> activityStateList=new ArrayList<>();
    
    private ActivityManager()
    {}
    
    public static ActivityManager getInstance()
    {
        if(null==instance)
        {
            synchronized(ActivityManager.class)
            {
                if(null==instance)
                {
                    instance=new ActivityManager();
                }
            }
        }
        return instance;
    }
    
    public void add(Activity activity)
    {
        activityStateList.add(new ActivityState(activity));
    }
    
    public void remove(Activity activity)
    {
        removeState(activity.hashCode());
    }
    
    public void removeTop()
    {
        Activity topActivity=last();
        if(null!=topActivity)
        {
            finish(topActivity);
        }
    }
    
    public void finish(Activity activity)
    {
        removeState(activity.hashCode());
        activity.finish();
    }
    
    public void clear()
    {
        if(activityStateList.size()>0)
        {
            Iterator<ActivityState> iterator=activityStateList.iterator();
            while(iterator.hasNext())
            {
                ActivityState stat=iterator.next();
                Activity activity=stat.activity.get();
                if(activity!=null)
                {
                    activity.finish();
                    iterator.remove();
                }
            }
        }
    }
    
    public Activity last()
    {
        if(activityStateList.isEmpty())
        {
            return null;
        }
        ActivityState stat=activityStateList.get(activityStateList.size()-1);
        return stat.activity.get();
    }
    
    public List<ActivityState> list()
    {
        return activityStateList;
    }
    
    private void removeState(int code)
    {
        Iterator<ActivityState> iterator=activityStateList.iterator();
        while(iterator.hasNext())
        {
            ActivityState activityState=iterator.next();
            if(activityState.code==code)
            {
                iterator.remove();
                break;
            }
        }
    }
    
    public static class ActivityState
    {
        public int code;
        public String name;
        public WeakReference<Activity> activity;
        
        ActivityState(Activity activity)
        {
            this.code=activity.hashCode();
            this.name=activity.getClass().getName();
            this.activity=new WeakReference<>(activity);
        }
    }
}
