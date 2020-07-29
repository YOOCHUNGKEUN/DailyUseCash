package yck.dailyusecash.utils

import android.app.Activity
import android.content.Context
import android.util.Log
import yck.dailyusecash.interfaces.ICustomTrans
import yck.dailyusecash.data.ListAdapterData
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import java.lang.Exception

class CustomConverterUtil : ICustomTrans{

    lateinit var mContext: Context
    lateinit var mActivity: Activity

    constructor(mContext: Context) {
        this.mContext = mContext
        this.mActivity = mContext as Activity
    }

    //ArrayList -> String
    override fun listAdapteDataToString(arrlstString: ArrayList<ListAdapterData>): String? {
        lateinit var strjson: String;
        if (arrlstString == null) {
            return ""
        }
        try {
            var listType: TypeToken<MutableList<ListAdapterData>> = object :
                TypeToken<MutableList<ListAdapterData>>() {}
            var makeGson = GsonBuilder().create()
            strjson = makeGson.toJson(arrlstString, listType.type)
        } catch (e: Exception) {
            Log.e("yck", "jsonToString e : " + e.message)
        }
        return strjson
    }

    //String -> ListAdapter
    override fun stringToListAdapterData(str: String): ArrayList<ListAdapterData>? {
        var makeGson = GsonBuilder().create()
        var listType: TypeToken<MutableList<ListAdapterData>> =
            object : TypeToken<MutableList<ListAdapterData>>() {}
        var arrList: ArrayList<ListAdapterData>?
        try {
            arrList = makeGson.fromJson(str, listType.type)
        } catch (e: Exception) {
            arrList = null
            e.printStackTrace()
        }
        return arrList
    }


}//clas