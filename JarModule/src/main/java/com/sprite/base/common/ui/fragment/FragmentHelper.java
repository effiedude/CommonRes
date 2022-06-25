package com.sprite.base.common.ui.fragment;

import android.util.Log;
import androidx.annotation.IdRes;
import androidx.annotation.IntRange;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

/******************************************************************************
 * @path FragmentHelper
 * @version 1.0.0.0
 * @describe 片段管理器
 * @author 张飞
 * @email
 * @date 2021-06-24-18:05
 * CopyRight(C)2021 小镇精灵工作室版权所有
 * *****************************************************************************
 */
public class FragmentHelper
{
    private static final String TAG="FragmentHelper";
    private static volatile FragmentHelper instance;
    private @Nullable FragmentManager mFragmentManager;
    private FragmentTransaction mFragmentTransaction;
    private int mContainLayoutId;
    
    private FragmentHelper()
    {}
    
    public static FragmentHelper getInstance()
    {
        if(instance==null)
        {
            synchronized(FragmentHelper.class)
            {
                if(instance==null)
                {
                    instance=new FragmentHelper();
                }
            }
        }
        return instance;
    }
    
    public void setContainLayoutId(@IdRes @IntRange(from=1) int containLayoutId)
    {
        mContainLayoutId=containLayoutId;
    }
    
    public @Nullable FragmentManager getFragmentManager()
    {
        return mFragmentManager;
    }
    
    public void switchFragment(@NonNull AppCompatActivity activity,@NonNull String fragmentTag,@Nullable BaseFragment fragment)
    {
        mFragmentManager=activity.getSupportFragmentManager();
        mFragmentTransaction=mFragmentManager.beginTransaction();
        mFragmentTransaction.replace(mContainLayoutId,fragment).addToBackStack(fragmentTag).commit();
    }
    
    public boolean backFragment(@NonNull AppCompatActivity activity)
    {
        boolean returnToStackBottom=false;
        if(mFragmentManager==null)
        {
            Log.e(TAG,"backFragment-fragmentManager:NULL");
        }
        mFragmentManager.popBackStack();
        if(mFragmentManager.getBackStackEntryCount()==1)
        {
            /** 让当前应用退回到后台但不退出 */
            activity.moveTaskToBack(true);
            returnToStackBottom=true;
        }
        return returnToStackBottom;
    }
    
    public void release()
    {
        if(mFragmentManager!=null)
        {
            mFragmentManager=null;
        }
        if(mFragmentTransaction!=null)
        {
            mFragmentTransaction=null;
        }
        instance=null;
    }
}
