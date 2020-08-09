package yck.dailyusecash.interfaces

import yck.dailyusecash.data.CustomPopupData
import yck.dailyusecash.data.ListAdapterData

interface IItemlistConstractor {

    interface view{
        fun setListView()
    }

    interface presenter{
        fun addItemOfList(listdata:ListAdapterData)
        fun deleteItemOfList(position:Int, listAdaterData:ListAdapterData)
        fun resultAmountOfList():Int
        fun getItemList():ArrayList<ListAdapterData>?
    }

}//class end