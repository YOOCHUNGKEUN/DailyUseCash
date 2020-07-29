package yck.dailyusecash.presenter

import android.app.Activity
import android.content.Context
import android.util.Log
import yck.dailyusecash.base.BaseConstant
import yck.dailyusecash.data.ListAdapterData
import yck.dailyusecash.utils.CommonUtil
import yck.dailyusecash.utils.CustomConverterUtil
import yck.dailyusecash.interfaces.IResultRemainingAmountConstractor
import yck.dailyusecash.repository.CustomRepository

class ResultRemainingAmountPresenter : IResultRemainingAmountConstractor.presenter {

    lateinit var mContext: Context
    lateinit var mActivity: Activity
    lateinit var mCustomRepository:CustomRepository
    lateinit var mItemlistPresenter: ItemlistPresenter
    lateinit var mCommonUtils:CommonUtil
    lateinit var mCustomConverterUtil: CustomConverterUtil

    constructor(mContext: Context) {
        this.mContext = mContext
        this.mActivity = mContext as Activity

        mCustomRepository = CustomRepository(mContext)
        mCommonUtils = CommonUtil(mContext)
        mCustomConverterUtil = CustomConverterUtil(mContext)
    }

    /*
    현재 총 잔여금 가져오기
    */
    override fun getResultRemaining():String{
        var result = ""
        if(mCommonUtils.isEmptyString(mCustomRepository.getRepository(BaseConstant.PREF_KEY_RESULT_REMAINING))){
            return result
        }
        mCustomRepository.getRepository(BaseConstant.PREF_KEY_RESULT_REMAINING)
        return result
    }

    /*
    현재 총 잔여금 저장하기
    */
    override fun saveResultRemainingAmount(){
        if(mCommonUtils.isEmptyString(resultResultRemainingAmount())){
            return
        }
        mCustomRepository.setRepository(BaseConstant.PREF_KEY_RESULT_REMAINING, resultResultRemainingAmount())
    }

    /*
    현재 총 잔여금 계산 하기
    */
    override fun resultResultRemainingAmount(): String {
        var result = ""
        if(mCommonUtils.isEmptyString(mCustomRepository.getRepository(BaseConstant.PREF_KEY_TARGET_AMOUNT))){
            return result
        }
        //목표금액 - 현재 리스트 총 금액 = result
        //목표금액
        var getTargetAmount:Int = Integer.parseInt(mCustomRepository.getRepository(BaseConstant.PREF_KEY_TARGET_AMOUNT)!!)
        //현재 리스트 총 금액
        mItemlistPresenter = ItemlistPresenter(mContext)
        if(mItemlistPresenter.resultAmountOfList()==0){
            result = mCustomRepository.getRepository(BaseConstant.PREF_KEY_TARGET_AMOUNT)!!
            return result
        }
        //계산
        var resultListItem:Int = mItemlistPresenter.resultAmountOfList()
        result = (getTargetAmount - resultListItem).toString()
        return result
    }

    /*
    오늘의 잔여금 계산 하기
    */
    override fun resultResultRemainingAmountOfToday(): String {
        var amount_value: Int = 0//계산용변수
        var all_today_amount_value = 0//사용전체금액
        var target_today_amount = 0//오늘의 목표금액
        var result_value: Int = 0//계산된 오늘의 잔여금액
        var itemAmout = ""

        //현재 아이템 리스트
        var arrlist: ArrayList<ListAdapterData>? = ArrayList<ListAdapterData>()
        var getStrlist = mCustomRepository.getRepository(BaseConstant.PREF_KEY_LIST)
        if (getStrlist != null && getStrlist.length > 0) {
            arrlist = mCustomConverterUtil.stringToListAdapterData(getStrlist.toString())
        }
        //오늘날짜
        var getToday = mCommonUtils.getDateOfToday().replace("-","")
        //오늘 사용 목표 금액
        if(mCustomRepository.getRepository(BaseConstant.PREF_KEY_TODAY_TARGET_AMOUNT)!=null&&(mCustomRepository.getRepository(BaseConstant.PREF_KEY_TODAY_TARGET_AMOUNT))!!.length>0){
            target_today_amount = mCustomRepository.getRepository(BaseConstant.PREF_KEY_TODAY_TARGET_AMOUNT)!!.toInt()
        }
        for (i in 0..arrlist!!.size - 1) {
            //리스트 아이템 날짜
            var item_date = (arrlist.get(i).history_date).replace("-","")
            if (arrlist.get(i).history_amount != null) {
                if(getToday.equals(item_date)){
                    if(arrlist.get(i).history_amount.contains(",")){
                        itemAmout = arrlist.get(i).history_amount.replace(",","")
                    }else{
                        itemAmout = arrlist.get(i).history_amount
                    }
                    amount_value = itemAmout.toInt()
                    all_today_amount_value = all_today_amount_value + amount_value
                }
            }
        }
        //계산 = 오늘의목표금액 - 리스트아이템합산(오늘만)
        Log.d("mytag","resultResultRemainingAmountOfToday/target_today_amount : "+target_today_amount)
        Log.d("mytag","resultResultRemainingAmountOfToday/all_today_amount_value : "+all_today_amount_value)
        result_value = target_today_amount - all_today_amount_value
        return result_value.toString()
    }




}//class end