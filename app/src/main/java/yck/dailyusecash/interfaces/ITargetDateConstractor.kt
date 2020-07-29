package yck.dailyusecash.interfaces

import yck.dailyusecash.data.TargetDateData

interface ITargetDateConstractor {

    interface view{
        fun setTargetDateView()
        fun setDdayView()
    }

    interface presenter{
        fun saveTargetDate(targetDateData: TargetDateData)
        fun saveDdayDate(targetDateData: TargetDateData)
    }

}//class end