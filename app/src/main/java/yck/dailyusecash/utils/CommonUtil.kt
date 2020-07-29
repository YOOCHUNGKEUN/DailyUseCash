package yck.dailyusecash.utils

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.net.Uri
import android.util.Log
import android.widget.Toast
import com.google.gson.Gson
import yck.dailyusecash.R
import yck.dailyusecash.async.GetLatestVersionAsync
import yck.dailyusecash.base.BaseConstant
import yck.dailyusecash.repository.CustomRepository
import java.io.PrintWriter
import java.io.StringWriter
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.*

class CommonUtil {

    lateinit var mContext: Context
    lateinit var mActivity: Activity
    var mBackKeyDoublePressedTime: Long = 2000
    lateinit var mCustomRepository:CustomRepository

    constructor(mContext: Context) {
        this.mContext = mContext
        this.mActivity = mContext as Activity
        mCustomRepository = CustomRepository(mContext)
    }

    /*
    오늘 날짜값 구하기(yyyy-mm-dd 형식)
    */
    fun getDateOfToday(): String {
        var long = System.currentTimeMillis();
        var date: Date = Date()
        var format_yyyy_MM_dd: SimpleDateFormat = SimpleDateFormat("yyyy-MM-dd")
        var str_yyyy__MM__dd: String = format_yyyy_MM_dd.format(date)
        return str_yyyy__MM__dd
    }

    /*
    두번 연속 클릭하면 앱 종료
    */
    fun doubleClickFinishApp() {
        if (System.currentTimeMillis() > mBackKeyDoublePressedTime + 2000) {
            mBackKeyDoublePressedTime = System.currentTimeMillis()
            Toast.makeText(
                mContext,
                mContext.getString(R.string.double_click_finish_app),
                Toast.LENGTH_SHORT
            ).show();
        } else {
            System.exit(0)
            mActivity.finish()
        }
    }

    /*
    1자리 숫자 : 01
    2자리 숫자 : 12
    형식으로 변경
    */
    fun setDoubleNumber(value: String): String? {
        var result = value
        if (value.length < 2) {
            result = "0" + value
        }
        return result
    }

    /*
    하루가 지났는지 확인(과거,미래 구분없이)
    */
    fun isADayHasPassed():Boolean{
        var customRepository: CustomRepository = CustomRepository(mContext)
        var getSavedTodaydate = customRepository.getRepository(BaseConstant.PREF_KEY_TODAYDATE)
        Log.d("mytag","isADayHasPassed/getSavedTodaydate : "+getSavedTodaydate)
        if(getSavedTodaydate==null||getSavedTodaydate.length<=0){
            customRepository.setRepository(BaseConstant.PREF_KEY_TODAYDATE,getDateOfToday())
            return true
        }
        var getTodayDate = getDateOfToday()
        Log.d("mytag","isADayHasPassed/getTodayDate : "+getTodayDate)
        if(getSavedTodaydate.equals(getTodayDate)){
            return false
        }
        return true
    }

    /*
    json - > String 변환하기
    */
    fun jsonInString(obj:Object):String{
        var strjson = ""
        if(obj==null){
            strjson
        }
        try {
            strjson = Gson().toJson(obj)
        }catch (e:Exception){
            Log.e("mytag","e : "+e.message)
        }
        return strjson
    }

    /*
    세자리 숫자 콤마
    */
    fun commaNumber(getvalue: Int): String {
        var custom_formatter: DecimalFormat = DecimalFormat("###,###")
        var formattedStringPrice: String = custom_formatter.format(getvalue)
        return formattedStringPrice
    }

    /*
    빈값체크
    */
    fun isEmptyString(source: String?): Boolean {
        var source = source
        if (source == null || source.isEmpty() || source.length <= 0) {
            return true
        }
        source = source.trim()
        if (source.isEmpty() || source.toLowerCase() == "null" || source.length <= 0) {
            return true
        }
        if(source.equals("0")){
            return true
        }
        return false
    }

    /*
    앱스토어 버전 저장
    */
    fun callAppStoreVersion(){
        var getLatestVersion = GetLatestVersionAsync()
        getLatestVersion.setConstructor(mContext)
        getLatestVersion.execute()
    }

    /*
    디바이스 버전 가져오기
    */
    fun getDeviceVersion():String{
        var result =""
        try {
            var pm: PackageManager = mContext.packageManager
            var pInfo: PackageInfo = pm.getPackageInfo(mContext.packageName,0)
            result = pInfo.versionName
        }catch (e: java.lang.Exception){
            var sw: StringWriter = StringWriter()
            e.printStackTrace(PrintWriter(sw))
            var exceptionAsString:String = sw.toString()
            Log.e("excep","e : "+exceptionAsString)
        }
        return result
    }

    /*
    앱 버전 업데이트 링크 연결
    */
    fun updateAppLinkGuide(){
        var appPackageName = mActivity.applicationContext.packageName;
        var appstore_url = BaseConstant.APPSTORE_URL_ID+appPackageName
        try {
            val intent: Intent = Intent(Intent.ACTION_VIEW, (Uri.parse(appstore_url)))
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
            mActivity.startActivity(intent)
        }catch (e: java.lang.Exception){
            Toast.makeText(mContext, mActivity.getString(R.string.alert_content_goto_appstore), Toast.LENGTH_LONG).show();
        }
        mActivity.finish()
    }



}//class end