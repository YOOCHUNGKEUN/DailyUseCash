package yck.dailyusecash.activity

import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.Window
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import yck.dailyusecash.base.BaseConstant
import yck.dailyusecash.utils.CommonUtil
import yck.dailyusecash.repository.CustomRepository
import org.greenrobot.eventbus.EventBus
import java.lang.Exception


open class BaseActivity :AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    /*
    이벤트 등록
    */
    fun eventRegister(context:Context){
        if(!EventBus.getDefault().isRegistered(context)){
            EventBus.getDefault().register(context)
        }
    }

    /*
    오늘 날짜 저장
    */
    fun saveTodayDate(context: Context){
        var mCommonUitls = CommonUtil(context)
        var mCustomRepository = CustomRepository(context)
        //오늘날짜저장
        mCustomRepository.setRepository(BaseConstant.PREF_KEY_TODAYDATE,mCommonUitls.getDateOfToday())
    }

    /*
    statusbar 색상변경
    */
    fun setStatusBarColor(color_res:Int){
        try{
            var window:Window = this.window
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(color_res);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }catch (e:Exception){

        }
    }


}//class end