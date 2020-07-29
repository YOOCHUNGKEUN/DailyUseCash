package yck.dailyusecash.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import yck.dailyusecash.R
import yck.dailyusecash.base.BaseConstant
import yck.dailyusecash.popup.CustomPopup
import yck.dailyusecash.data.CustomPopupData
import yck.dailyusecash.data.EventData
import yck.dailyusecash.data.ListAdapterData
import yck.dailyusecash.utils.CommonUtil
import yck.dailyusecash.presenter.ItemlistPresenter
import yck.dailyusecash.presenter.ResultRemainingAmountPresenter
import yck.dailyusecash.presenter.TargetDatePresenter
import yck.dailyusecash.repository.CustomRepository
import yck.dailyusecash.view.ItemlistView
import yck.dailyusecash.view.ResultRemainingAmountView
import yck.dailyusecash.view.TargetAmountView
import yck.dailyusecash.view.TargetDateView
import kotlinx.android.synthetic.main.activity_main.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import yck.dailyusecash.presenter.TargetAmountPresenter

class MainActivity : BaseActivity(),View.OnClickListener{

    var mItemlistView = ItemlistView(this)
    var mTargetDateView = TargetDateView(this)
    var mTargetAmountView = TargetAmountView(this)
    var mResultRemainingAmountView = ResultRemainingAmountView(this)

    var mTargetDatePresenter = TargetDatePresenter(this)
    var mTargetAmountPresenter = TargetAmountPresenter(this)
    var mResultRemainingAmountPresenter = ResultRemainingAmountPresenter(this)
    var mItemlistPresenter = ItemlistPresenter(this)

    var mListAdapterData = ListAdapterData("","","")
    var mCustomPopupData = CustomPopupData("",0)
    var mCommonUtils = CommonUtil(this)

    var mCustomRepository = CustomRepository(this)
    var mCustomPopup = CustomPopup(this)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStatusBarColor(getColor(R.color.color_ffffff))
        setContentView(R.layout.activity_main)
        eventRegister(this)//this 등록했기 때문에, Subscribe에서 onCreate선언한 모든것을 지속적으로 사용 가능함


        //mCommonUtils.callAppStoreVersion()


        //오늘날짜 저장
        if(mCommonUtils.isEmptyString(mCustomRepository.getRepository(BaseConstant.PREF_KEY_TODAYDATE))){
            saveTodayDate(this)
        }

        setOnClickListener()

        //리스트뷰 셋팅
        mItemlistView.setListView()

        //목표날짜 셋팅
        mTargetDateView.setTargetDateView()
        mTargetDateView.setDdayView()//d-day셋팅

        //목표금액 셋팅
        mTargetAmountView.setTargetAmountView()
        mTargetAmountView.setTargetAmountOfTodayView()//오늘목표 금액 셋팅

        //현재 총 잔여금액
        mResultRemainingAmountView.setResultItemRemainingAmountView()
        mResultRemainingAmountView.setRemainingTodayAmountview()//오늘의잔여금 셋팅
    }

    override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().unregister(this)
    }

    /*
    클릭리스너
    */
    fun setOnClickListener(){
        mainlist_btn_add_list.setOnClickListener(this)
        mainlist_init_alldata.setOnClickListener(this)
        mainlist_tv_target_date.setOnClickListener(this)
        mainlist_tv_target_amount.setOnClickListener(this)
    }

    /*
    백버튼
    */
    override fun onBackPressed() {
        if (mCommonUtils == null) {
            mCommonUtils = CommonUtil(this)
        }
        mCommonUtils.doubleClickFinishApp()
    }

    /*
    클릭리스너
    */
    override fun onClick(v: View?) {
        when(v){
            //리스트 아이템 추가 버튼
            mainlist_btn_add_list ->{
                mCustomPopup.showEditTextTypePopup(BaseConstant.ADD_ITEM)
            }
            //리스트 초기화 버튼
            mainlist_init_alldata ->{
                mCustomPopupData.order = BaseConstant.INIT_LIST
                mCustomPopup.showTextTypePopup(mCustomPopupData,mListAdapterData)
            }
            //목표날짜 설정 버튼
            mainlist_tv_target_date ->{
                mCustomPopup.showCalendarPopup()
            }
            //목표 사용 금액 설정 버튼
            mainlist_tv_target_amount ->{
                if(mCommonUtils.isEmptyString(mCustomRepository.getRepository(BaseConstant.PREF_KEY_TARGET_DATE))){
                    Toast.makeText(this,getString(R.string.alert_content_choice_day_first),Toast.LENGTH_SHORT).show()
                    return
                }
                mCustomPopup.showEditTextTypePopup(BaseConstant.TARGET_AMOUNT)
            }
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun arrivalPoint(event_data:EventData){
        Log.d("mytag","MainActivity/arrivalPoint/event_data.order : "+event_data.order)
        //아이템추가 완료,아이템삭제완료
        if(event_data.order.equals(BaseConstant.COMPLETE_ADDITEM)||event_data.order.equals(BaseConstant.COMPLETE_DELETEITEM)) {
            if(event_data.order.equals(BaseConstant.COMPLETE_ADDITEM)){
                mItemlistPresenter.addItemOfList(event_data.listAdapterData)
            }else if(event_data.order.equals(BaseConstant.COMPLETE_DELETEITEM)){
                mItemlistPresenter.deleteItemOfList(event_data.position,event_data.listAdapterData)
            }
            mResultRemainingAmountPresenter.saveResultRemainingAmount()//전체잔여금 저장
            mResultRemainingAmountView.setResultItemRemainingAmountView()//전체잔여금 셋팅
            mResultRemainingAmountView.setRemainingTodayAmountview()//오늘의잔여금 셋팅
            mItemlistView.setListView()//리스트 셋팅
        }
        //리스트초기화 완료
        else if(event_data.order.equals(BaseConstant.COMPLETE_INITLIST)){
            mCustomRepository.initRepository()
            var intent: Intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
            overridePendingTransition(0, 0)
        }
        //목표금액 설정 완료
        else if(event_data.order.equals(BaseConstant.COMPLETE_TARGETAMOUNT)){
            mTargetAmountPresenter.saveTargetAmount(event_data)//목표금액 저장
            mTargetAmountView.setTargetAmountView()//목표금액 셋팅
            mResultRemainingAmountPresenter.saveResultRemainingAmount()//총 잔여금 저장
            mResultRemainingAmountView.setResultItemRemainingAmountView()//총 잔여금 셋팅
            mTargetAmountView.setTargetAmountOfTodayView()//오늘의 목표 금액 셋팅
        }
        //달력선택 완료
        else if(event_data.order.equals(BaseConstant.COMPLETE_SELECTED_CALENDAR)){
            mTargetDatePresenter.saveTargetDate(event_data.targetDateData)//목표금액 셋팅
            mTargetDatePresenter.saveDdayDate(event_data.targetDateData)//d-day 저장
            mTargetDateView.setTargetDateView()//목표날짜 셋팅
            mTargetDateView.setDdayView()//d-day 셋팅
        }
        //앱스토어 버전 가져오기
        else if(event_data.order.equals(BaseConstant.COMPLETE_GET_APPSTORE_VERSION)){
            var getDeviceVersion = mCommonUtils.getDeviceVersion()
            var getAppStoreVersion = event_data.appversion
            var intGetDeviceVersion = getDeviceVersion.replace(".","")
            var intGetAppStoreVersion = getAppStoreVersion.replace(".","")
            if(intGetDeviceVersion<intGetAppStoreVersion){
                var customPopupData = CustomPopupData(BaseConstant.APP_UPDATE,0)
                var listAdapterData = ListAdapterData("","","")
                mCustomPopup.showTextTypePopup(customPopupData,listAdapterData)
            }
        }else{
            Log.e("mytag","MainActivity/arrivalPoint/else")
        }
    }


}//class end