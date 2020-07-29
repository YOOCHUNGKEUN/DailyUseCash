package yck.dailyusecash.interfaces

interface IResultRemainingAmountConstractor {

    interface view{
        fun setResultItemRemainingAmountView()
        fun setRemainingTodayAmountview()
    }

    interface presenter{
        fun getResultRemaining():String
        fun saveResultRemainingAmount()
        fun resultResultRemainingAmount():String
        fun resultResultRemainingAmountOfToday():String
    }

}//class end