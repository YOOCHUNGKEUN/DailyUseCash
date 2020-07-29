package yck.dailyusecash.utils

import android.app.Activity
import android.content.Context
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.widget.EditText

class CustomTextUtil {

    lateinit var mContext: Context
    lateinit var mActivity: Activity
    lateinit var mCommonUtils:CommonUtil
    var mResult =""

    constructor(mContext: Context) {
        this.mContext = mContext
        this.mActivity = mContext as Activity

        mCommonUtils = CommonUtil(mContext)
    }

    /*
    EditText 입력 모양 셋팅
    */
    fun setTextWatcher(dialog: EditText): TextWatcher {
        var resultTextWatcher = (object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                var input:String = s.toString()

                if(!TextUtils.isEmpty(s.toString())&&!s.toString().equals(mResult)){
                    if(input.contains(",")){
                        input = input.replace(",","")
                    }
                    mResult = mCommonUtils.commaNumber(input.toInt())
                    dialog.setText(mResult)
                    dialog.setSelection(mResult.length)
                }
            }
        })
        return resultTextWatcher
    }


}//class end
