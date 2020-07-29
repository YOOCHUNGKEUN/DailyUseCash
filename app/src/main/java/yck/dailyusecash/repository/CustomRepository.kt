package yck.dailyusecash.repository

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import yck.dailyusecash.base.BaseConstant
import yck.dailyusecash.interfaces.IRepositoryConstractor

class CustomRepository :IRepositoryConstractor{

    lateinit var mContext: Context
    lateinit var mActivity:Activity

    constructor(mContext: Context) {
        this.mContext = mContext
        this.mActivity = mContext as Activity
    }

    override fun getRepository(key: String):String? {
        var pref: SharedPreferences = mContext.getSharedPreferences(BaseConstant.PREF_KEY, 0)
        if (pref == null) {
            return null
        }
        return pref.getString(key, "")
    }

    override fun setRepository(key: String, value: String) {
        var pref: SharedPreferences = mContext.getSharedPreferences(BaseConstant.PREF_KEY, 0)
        if (pref == null) {
            return
        }
        var editor: SharedPreferences.Editor = pref.edit()
        editor.putString(key, value)
        editor.commit()
    }

    override fun initRepository() {
        var pref: SharedPreferences = mContext.getSharedPreferences(BaseConstant.PREF_KEY, 0)
        var editor: SharedPreferences.Editor = pref.edit()
        editor.remove(BaseConstant.PREF_KEY)
        editor.clear()
        editor.commit()
    }

}//class end