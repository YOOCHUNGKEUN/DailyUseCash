package yck.dailyusecash.utils

import android.animation.ValueAnimator
import android.app.Activity
import android.content.Context
import android.widget.TextView

class CustomAnimatorUtil {

    lateinit var mContext: Context
    lateinit var mActivity: Activity
    lateinit var mCommonUtil: CommonUtil

    constructor(mContext: Context) {
        this.mContext = mContext
        this.mActivity = mContext as Activity

        mCommonUtil = CommonUtil(mContext)
    }

    /*
    숫자 카운팅 애니메이션 효과
    */
    fun setNumberAnimationOnedayUse(tv: TextView, value: Int) {
        val animator = ValueAnimator.ofInt(0, value) //0 시작숫자, value 끝숫자
        animator.duration = 600 //mills => 0.6초 애니메이션
        animator.addUpdateListener {
                animation -> tv.setText(mCommonUtil.commaNumber(animation.animatedValue as Int))
        }
        animator.start()
    }




}//class end