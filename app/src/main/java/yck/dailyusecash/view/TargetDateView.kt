package yck.dailyusecash.view

import android.app.Activity
import android.content.Context
import yck.dailyusecash.base.BaseConstant
import yck.dailyusecash.utils.CustomAnimatorUtil
import yck.dailyusecash.interfaces.ITargetDateConstractor
import yck.dailyusecash.repository.CustomRepository
import kotlinx.android.synthetic.main.activity_main.*

class TargetDateView : ITargetDateConstractor.view{

    lateinit var mContext: Context
    lateinit var mActivity: Activity
    lateinit var mCustomAnimatorUtil: CustomAnimatorUtil

    constructor(mContext: Context) {
        this.mContext = mContext
        this.mActivity = mContext as Activity

        mCustomAnimatorUtil = CustomAnimatorUtil(mContext)
    }

    override fun setTargetDateView() {
        var customRepository:CustomRepository = CustomRepository(mContext)
        var getTargetDate = customRepository.getRepository(BaseConstant.PREF_KEY_TARGET_DATE)
        if(getTargetDate==null || getTargetDate.length<=0){
            mActivity.mainlist_tv_target_date.text = "0000/00/00"
            return
        }
        mActivity.mainlist_tv_target_date.text = getTargetDate
    }

    override fun setDdayView() {
        var customRepository:CustomRepository = CustomRepository(mContext)
        var getDday = customRepository.getRepository(BaseConstant.PREF_KEY_DDAY)
        if(getDday==null || getDday.length<=0){
            mActivity.mainlist_tv_dday.text = "day"
            return
        }
        mCustomAnimatorUtil.setNumberAnimationOnedayUse(mActivity.mainlist_tv_dday,getDday!!.toInt())
    }


}//class end