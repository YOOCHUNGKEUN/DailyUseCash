package yck.dailyusecash.view

import android.app.Activity
import android.content.Context
import yck.dailyusecash.base.BaseConstant
import yck.dailyusecash.utils.CustomAnimatorUtil
import yck.dailyusecash.interfaces.IResultRemainingAmountConstractor
import yck.dailyusecash.presenter.ResultRemainingAmountPresenter
import yck.dailyusecash.repository.CustomRepository
import kotlinx.android.synthetic.main.activity_main.*

class ResultRemainingAmountView : IResultRemainingAmountConstractor.view{

    lateinit var mContext: Context
    lateinit var mActivity: Activity
    lateinit var mCustomRepository:CustomRepository
    lateinit var mResultRemainingAmountPresenter: ResultRemainingAmountPresenter
    lateinit var mCustomAnimatorUtil: CustomAnimatorUtil

    constructor(mContext: Context) {
        this.mContext = mContext
        this.mActivity = mContext as Activity

        mCustomRepository = CustomRepository(mContext)
        mResultRemainingAmountPresenter = ResultRemainingAmountPresenter(mContext)
        mCustomAnimatorUtil = CustomAnimatorUtil(mContext)
    }
    /*
    현재 총 잔여금액 셋팅
    */
    override fun setResultItemRemainingAmountView() {
        var getResultRemaining = mCustomRepository.getRepository(BaseConstant.PREF_KEY_RESULT_REMAINING)
        if(getResultRemaining==null || getResultRemaining.length<=0){
            mActivity.mainlist_tv_rest_amount.text = "0"
            return
        }
        mCustomAnimatorUtil.setNumberAnimationOnedayUse(mActivity.mainlist_tv_rest_amount,getResultRemaining!!.toInt())
    }

    /*
    오늘의 잔여금액 셋팅(리스트 아이템 변경시(추가/삭제)마다 저장하기 때문에 저장된 리스트를 가져와서 사용함)
    */
    override fun setRemainingTodayAmountview() {
        if(mResultRemainingAmountPresenter.resultResultRemainingAmountOfToday()==null||(mResultRemainingAmountPresenter.resultResultRemainingAmountOfToday()).length<=0){
            return
        }
        mCustomAnimatorUtil.setNumberAnimationOnedayUse(mActivity.mainlist_tv_remaining_amount_of_today,(mResultRemainingAmountPresenter.resultResultRemainingAmountOfToday())!!.toInt())
    }


}//class end