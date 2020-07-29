package yck.dailyusecash.interfaces

import yck.dailyusecash.data.ListAdapterData

interface ICustomTrans {

    fun listAdapteDataToString(arrlstString: ArrayList<ListAdapterData>): String?
    fun stringToListAdapterData(str:String):ArrayList<ListAdapterData>?

}