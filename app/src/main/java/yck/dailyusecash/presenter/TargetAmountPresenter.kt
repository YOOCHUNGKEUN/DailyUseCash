package yck.dailyusecash.presenter

import android.app.Activity
import android.content.Context
import yck.dailyusecash.base.BaseConstant
import yck.dailyusecash.utils.CommonUtil
import yck.dailyusecash.interfaces.ITargetAmountConstractor
import yck.dailyusecash.repository.CustomRepository
import kotlinx.android.synthetic.main.activity_main.*
import yck.dailyusecash.data.EventData

class TargetAmountPresenter: ITargetAmountConstractor.presenter{

    lateinit var mContext: Context
    lateinit var mActivity: Activity
    lateinit var mRepository: CustomRepository
    lateinit var mCommonUtils: CommonUtil

    constructor(mContext: Context) {
        this.mContext = mContext
        this.mActivity = mContext as Activity
        mCommonUtils = CommonUtil(mContext)
        mRepository = CustomRepository(mContext)
    }

    //오늘의 목표금액
    //현재 총잔여금/dday
    override fun resultTargetAmountOfToday():String{
        var currenty_remaining_amount:Int = 0
        var dday:Int = 0
        var result:Int = 0
        mRepository = CustomRepository(mContext)
        //현재 총 잔여금
        if(mRepository.getRepository(BaseConstant.PREF_KEY_RESULT_REMAINING)==null||(mRepository.getRepository(BaseConstant.PREF_KEY_RESULT_REMAINING))!!.length<=0){
            return ""
        }
        currenty_remaining_amount = Integer.parseInt(mRepository.getRepository(BaseConstant.PREF_KEY_RESULT_REMAINING)!!)
        //D-day Count
        if(mRepository.getRepository(BaseConstant.PREF_KEY_DDAY)==null){
            return ""
        }
        dday = Integer.parseInt(mRepository.getRepository(BaseConstant.PREF_KEY_DDAY)!!)
        //계산
        result = currenty_remaining_amount/dday
        return result.toString()
    }

    //오늘 목표 금액 저장하기
    override fun saveTargetAmount(event_data:EventData) {
        if(mCommonUtils.isEmptyString(event_data.target_amount)){
            return
        }
        if(event_data.target_amount.contains(",")){
            event_data.target_amount = event_data.target_amount.replace(",","")
        }
        mRepository.setRepository(BaseConstant.PREF_KEY_TARGET_AMOUNT,event_data.target_amount)
    }


}//class end