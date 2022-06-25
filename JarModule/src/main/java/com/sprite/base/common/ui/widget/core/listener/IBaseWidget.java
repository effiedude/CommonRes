package com.sprite.base.common.ui.widget.core.listener;

import android.view.View;

/******************************************************************************
 * @path IBaseWidget
 * @version 1.0.0.0
 * @describe 控件定义模板
 * @author 张飞
 * @email
 * @date 2021-06-24-18:03
 * CopyRight(C)2021 小镇精灵工作室版权所有
 * *****************************************************************************
 */
public interface IBaseWidget
{
    /**
     * getView
     * 获取当前控件实例
     *
     * @return 当前控件实例
     */
    View getView();
    
    /**
     * bind
     * 绑定数据
     */
    void bind();
    
    /**
     * unbind
     * 解绑数据
     */
    void unbind();
    
    /**
     * isValid
     * 检测数据的有效性
     * 
     * @return 数据是否有效
     */
    boolean isValid();
}
