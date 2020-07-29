package yck.dailyusecash.interfaces

import yck.dailyusecash.data.CustomPopupData
import yck.dailyusecash.data.ListAdapterData

interface ICustomPopupConstractor {
    fun showEditTextTypePopup(order:String)
    fun showTextTypePopup(customPopupData: CustomPopupData, listAdapterData:ListAdapterData)
    fun showCalendarPopup()
}