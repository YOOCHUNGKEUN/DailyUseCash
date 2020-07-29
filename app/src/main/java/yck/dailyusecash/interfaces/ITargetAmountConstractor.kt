package yck.dailyusecash.interfaces

import yck.dailyusecash.data.EventData

interface ITargetAmountConstractor {

    interface view {
        fun setTargetAmountView()
        fun setTargetAmountOfTodayView()
    }

    interface presenter {
        fun resultTargetAmountOfToday(): String
        fun saveTargetAmount(event_data: EventData)
    }

}//class end