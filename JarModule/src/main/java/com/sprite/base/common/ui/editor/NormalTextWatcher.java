package com.sprite.base.common.ui.editor;

import java.lang.ref.WeakReference;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import androidx.annotation.NonNull;

/******************************************************************************
 * @path Talent:NormalTextWatcher
 * @version 1.0.0.0
 * @describe 输入框过滤器
 * @author 张飞
 * @email
 * @date 2021-06-24-18:49
 * CopyRight(C)2021 小镇精灵工作室版权所有
 * *****************************************************************************
 */
public class NormalTextWatcher implements TextWatcher
{
    public static final String TAG="NormalTextWatcher";
    /** 汉字/字母/数字 */
    public static final String NORMALxREGEXxALL="[^a-zA-Z0-9\u4E00-\u9FA5]";
    /** 汉字 */
    public static final String NORMALxREGEXxCHINESE="[^\u4E00-\u9FA5]";
    private final WeakReference<EditText> mEditText;
    private final String mRegex;
    
    public NormalTextWatcher(@NonNull EditText editText)
    {
        this(NORMALxREGEXxALL,editText);
    }
    
    public NormalTextWatcher(@NonNull String regex,@NonNull EditText editText)
    {
        mRegex=regex;
        mEditText=new WeakReference<>(editText);
    }
    
    @Override
    public void beforeTextChanged(CharSequence charSequence,int start,int count,int after)
    {}
    
    @Override
    public void onTextChanged(CharSequence charSequence,int start,int before,int count)
    {}
    
    @Override
    public void afterTextChanged(Editable editable)
    {
        EditText editText=mEditText.get();
        if(editable!=null)
        {
            String content=editable.toString();
            String realContent=content.replaceAll(mRegex,"");
            editText.removeTextChangedListener(this);
            editable.replace(0,editable.length(),realContent.trim());
            editText.addTextChangedListener(this);
        }
    }
}
