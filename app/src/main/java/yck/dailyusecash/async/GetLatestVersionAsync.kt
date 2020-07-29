package yck.dailyusecash.async

import android.content.Context
import android.os.AsyncTask
import android.util.Log
import org.greenrobot.eventbus.EventBus
import org.json.JSONObject
import org.jsoup.Jsoup
import yck.dailyusecash.base.BaseConstant
import yck.dailyusecash.data.EventData
import yck.dailyusecash.data.ListAdapterData
import yck.dailyusecash.data.TargetDateData
import yck.dailyusecash.repository.CustomRepository
import java.io.PrintWriter
import java.io.StringWriter
import java.lang.Exception

class GetLatestVersionAsync : AsyncTask<String,String,JSONObject>() {

    lateinit var mcontext:Context;
    lateinit var mRepository: CustomRepository
    var mstrStoreLatestVesion:String = ""


    fun setConstructor(context:Context){
        this.mcontext = context;
        mRepository = CustomRepository(context)
    }

    override fun onPreExecute() {
        super.onPreExecute()
    }

    override fun doInBackground(vararg params: String?): JSONObject {
        var strgetPackName = mcontext.packageName
        Log.d("mytag","doInBackground/strgetPackName : "+strgetPackName)

        try {
            var doc: org.jsoup.nodes.Document? = Jsoup.connect(BaseConstant.APPSTORE_URL_ID+strgetPackName).get()
            mstrStoreLatestVesion = doc!!.getElementsByClass("htlgb").get(6).text()
        }catch (e:Exception){
            var sw = StringWriter()
            e.printStackTrace(PrintWriter(sw))
            var exceptionAsString = sw.toString()
            Log.e("excep","e : "+exceptionAsString)
        }
        return JSONObject()
    }


    override fun onPostExecute(result: JSONObject?) {
        super.onPostExecute(result)
        Log.d("mytag","latestVertsion : "+mstrStoreLatestVesion)
        var targetDateData = TargetDateData("","","")
        var listAdapterData = ListAdapterData("","","")
        var event_data:EventData = EventData(BaseConstant.COMPLETE_GET_APPSTORE_VERSION,0,mstrStoreLatestVesion,"",targetDateData,listAdapterData)
        EventBus.getDefault().post(event_data)
    }





}//class end