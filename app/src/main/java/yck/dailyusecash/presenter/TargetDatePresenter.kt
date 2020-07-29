package yck.dailyusecash.presenter

import android.app.Activity
import android.content.Context
import android.util.Log
import yck.dailyusecash.base.BaseConstant
import yck.dailyusecash.data.TargetDateData
import yck.dailyusecash.interfaces.ITargetDateConstractor
import yck.dailyusecash.repository.CustomRepository
import java.lang.Exception
import java.util.*

class TargetDatePresenter : ITargetDateConstractor.presenter{

    lateinit var mContext: Context
    lateinit var mActivity: Activity
    lateinit var mCustomRepository: CustomRepository

    constructor(mContext: Context) {
        this.mContext = mContext
        this.mActivity = mContext as Activity
    }

    /*
    목표 날짜 저장
    */
    override fun saveTargetDate(targetDateData: TargetDateData) {
        //저장형식 -> 0000/00/00
        mCustomRepository = CustomRepository(mContext)
        var result =""
        try {
            result = targetDateData.year + "/" + targetDateData.month + "/" + targetDateData.dayOfMonth
        }catch (e:Exception){
            Log.e("exception","e : "+e.message)
        }
        mCustomRepository.setRepository(BaseConstant.PREF_KEY_TARGET_DATE, result)
    }

    /*
    목표 날짜를 이용한 D-day 저장
    */
    override fun saveDdayDate(targetDateData: TargetDateData) {
        mCustomRepository = CustomRepository(mContext)
        var result =""
        var dayInt = 0
        var monthInt = 0

        Log.d("mytag","month : "+targetDateData.month)
        Log.d("mytag","dayOfMonth : "+targetDateData.dayOfMonth)

        try {
            var yearInt:Int = Integer.parseInt(targetDateData.year)
            if(targetDateData.month.substring(0,1).equals("0")){
                monthInt = Integer.parseInt(targetDateData.month.substring(1,2))
            }else{
                monthInt = Integer.parseInt(targetDateData.month)
            }
            if(targetDateData.dayOfMonth.substring(0,1).equals("0")){
                dayInt = Integer.parseInt(targetDateData.dayOfMonth.substring(1,2))
            }else{
                dayInt = Integer.parseInt(targetDateData.dayOfMonth)
            }
            //D day 계산하기
            var cal: Calendar = Calendar.getInstance()
            var now_day: Long = cal.timeInMillis//현재시간
            cal.set(yearInt, monthInt - 1, dayInt)//목표시간
            var event_day: Long = cal.timeInMillis
            var d_day: Long = (event_day - now_day) / (60 * 60 * 24 * 1000)
            result = d_day.toString()
        }catch (e:Exception){
            Log.e("exception","e : "+e.message)
        }
        mCustomRepository.setRepository(BaseConstant.PREF_KEY_DDAY, result)
    }


}//class end