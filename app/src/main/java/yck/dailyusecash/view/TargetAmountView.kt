package yck.dailyusecash.view

import android.app.Activity
import android.content.Context
import android.util.Log
import yck.dailyusecash.base.BaseConstant
import yck.dailyusecash.utils.CustomAnimatorUtil
import yck.dailyusecash.interfaces.ITargetAmountConstractor
import yck.dailyusecash.presenter.TargetAmountPresenter
import yck.dailyusecash.repository.CustomRepository
import kotlinx.android.synthetic.main.activity_main.*
import yck.dailyusecash.utils.CommonUtil

class TargetAmountView : ITargetAmountConstractor.view{

    lateinit var mContext: Context
    lateinit var mActivity: Activity
    lateinit var mTargetAmountPresenter: TargetAmountPresenter
    lateinit var mRepository: CustomRepository
    lateinit var mCustomAnimatorUtil: CustomAnimatorUtil
    lateinit var mCommonUtil: CommonUtil

    constructor(mContext: Context) {
        this.mContext = mContext
        this.mActivity = mContext as Activity

        mRepository = CustomRepository(mContext)
        mTargetAmountPresenter = TargetAmountPresenter(mContext)
        mCustomAnimatorUtil = CustomAnimatorUtil(mContext)
        mCommonUtil = CommonUtil(mContext)
    }

    /*
    목표 사용금액 셋팅
    */
    override fun setTargetAmountView() {
        var customRepository: CustomRepository = CustomRepository(mContext)
        var getTargetAmount = customRepository.getRepository(BaseConstant.PREF_KEY_TARGET_AMOUNT)
        if(mCommonUtil.isEmptyString(getTargetAmount)){
            mActivity.mainlist_tv_target_amount.text = "0000"
            return
        }
        mCustomAnimatorUtil.setNumberAnimationOnedayUse(mActivity.mainlist_tv_target_amount,getTargetAmount!!.toInt())
    }

    /*
    오늘 목표금액 셋팅
    */
    override fun setTargetAmountOfTodayView() {
        //오늘 목표금액 최신업데이트
        var getResult =""

        if(mCommonUtil.isADayHasPassed()) {
            var targetAmountPresenter = TargetAmountPresenter(mContext)
            getResult = targetAmountPresenter.resultTargetAmountOfToday()
        }else {
            if(mCommonUtil.isEmptyString(mRepository.getRepository(BaseConstant.PREF_KEY_TODAY_TARGET_AMOUNT))){
                var targetAmountPresenter = TargetAmountPresenter(mContext)
                getResult = targetAmountPresenter.resultTargetAmountOfToday()
            }else{
                getResult = mRepository.getRepository(BaseConstant.PREF_KEY_TODAY_TARGET_AMOUNT)!!
            }
        }
        if(mCommonUtil.isEmptyString(getResult)){
            mActivity.mainlist_tv_today_use_amount.text = "0"
            return
        }
        mCustomAnimatorUtil.setNumberAnimationOnedayUse(mActivity.mainlist_tv_today_use_amount,getResult!!.toInt())
        mRepository.setRepository(BaseConstant.PREF_KEY_TODAY_TARGET_AMOUNT,getResult)
    }


}//class end