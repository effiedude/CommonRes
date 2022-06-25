package com.sprite.base.common.eventbus;

import org.greenrobot.eventbus.EventBus;

public class EventBusProxy
{
    public static void register(Object subscriber)
    {
        if(!EventBus.getDefault().isRegistered(subscriber))
        {
            EventBus.getDefault().register(subscriber);
        }
    }
    
    public static void unregister(Object subscriber)
    {
        if(EventBus.getDefault().isRegistered(subscriber))
        {
            EventBus.getDefault().unregister(subscriber);
        }
    }
    
    public static void sendEvent(Object object)
    {
        EventBus.getDefault().post(object);
    }
    
    public static void sendStickyEvent(Object object)
    {
        EventBus.getDefault().postSticky(object);
    }
}
