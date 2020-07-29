package yck.dailyusecash.popup

import android.annotation.SuppressLint
import android.app.Activity
import android.app.DatePickerDialog
import android.content.Context
import android.util.Log
import android.view.View
import android.widget.DatePicker
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import yck.dailyusecash.R
import yck.dailyusecash.base.BaseConstant
import yck.dailyusecash.interfaces.ICustomPopupConstractor
import yck.dailyusecash.data.CustomPopupData
import yck.dailyusecash.data.EventData
import yck.dailyusecash.data.ListAdapterData
import yck.dailyusecash.data.TargetDateData
import yck.dailyusecash.presenter.ItemlistPresenter
import yck.dailyusecash.presenter.TargetDatePresenter
import yck.dailyusecash.repository.CustomRepository
import yck.dailyusecash.view.CustomPopupView
import kotlinx.android.synthetic.main.custom_dialog_edit_type.view.*
import kotlinx.android.synthetic.main.custom_dialog_text_type.view.*
import org.greenrobot.eventbus.EventBus
import yck.dailyusecash.utils.CommonUtil
import java.lang.Exception
import java.util.*

class CustomPopup : ICustomPopupConstractor {

    companion object{
        lateinit var cDialogView: View
    }
    lateinit var mContext: Context
    lateinit var mActivity: Activity
    lateinit var mBuilder: AlertDialog.Builder
    lateinit var mAlertDialog: AlertDialog
    lateinit var mCommonUtils: CommonUtil
    var mListAdapterData = ListAdapterData("","","")
    var mTargetDateData = TargetDateData("","","")

    constructor(mContext: Context) {
        this.mContext = mContext
        this.mActivity = mContext as Activity
        mCommonUtils = CommonUtil(mContext)
    }

    /*
    EditText Type Popup
    */
    override fun showEditTextTypePopup(order: String) {
        var customPopupView: CustomPopupView = CustomPopupView(mContext)
        cDialogView = customPopupView.editTextTypePopupDialogView(order)
        mBuilder = AlertDialog.Builder(mContext).setView(cDialogView)
        mAlertDialog = mBuilder.show()
        mAlertDialog.setCancelable(false)
        cDialogView.dialog_date.setOnClickListener {//달력버튼
            var customPopup = CustomPopup(mContext)
            customPopup.showCalendarPopup()
        }
        cDialogView.dialog_tv_cancel.setOnClickListener {//취소버튼
            mAlertDialog.dismiss()
        }
        cDialogView.dialog_tv_confirm.setOnClickListener {//확인버튼
            //리스트 아이템 추가
            if(order.equals(BaseConstant.ADD_ITEM)) {
                mListAdapterData.history_name = cDialogView.dialog_edt_1.text.toString()
                mListAdapterData.history_amount = cDialogView.dialog_edt_2.text.toString()
                mListAdapterData.history_date = cDialogView.dialog_date.text.toString()
                var event_data:EventData = EventData(BaseConstant.COMPLETE_ADDITEM,0,"","",mTargetDateData,mListAdapterData)
                EventBus.getDefault().post(event_data)
            }
            //목표 금액 설정
            else if(order.equals(BaseConstant.TARGET_AMOUNT)) {
                if(cDialogView.dialog_edt_1.text.toString()==null||cDialogView.dialog_edt_1.text.toString().length<=0){
                    Toast.makeText(mContext,mContext.getString(R.string.alert_content_blank), Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }
                var getAmount = cDialogView.dialog_edt_1.text.toString()
                var event_data:EventData = EventData(BaseConstant.COMPLETE_TARGETAMOUNT,0,"",getAmount,mTargetDateData,mListAdapterData)
                EventBus.getDefault().post(event_data)
            }
            mAlertDialog.dismiss()
        }
    }

    /*
    Text Type Popup
    */
    override fun showTextTypePopup(customPopupData: CustomPopupData, listAdapterData:ListAdapterData) {
        var customPopupView: CustomPopupView = CustomPopupView(mContext)
        cDialogView = customPopupView.textTypePopupDialogView(customPopupData, listAdapterData)
        mBuilder = AlertDialog.Builder(mContext).setView(cDialogView)
        mAlertDialog = mBuilder.show()
        mAlertDialog.setCancelable(false)
        cDialogView.dialog2_tv_cancel.setOnClickListener {//취소버튼
            mAlertDialog.dismiss()
        }
        cDialogView.dialog2_tv_confirm.setOnClickListener {//확인버튼
            //전체 리스트 초기화
            if(customPopupData.order.equals(BaseConstant.INIT_LIST)){
                var event_data:EventData = EventData(BaseConstant.COMPLETE_INITLIST,0,"","",mTargetDateData,listAdapterData)
                EventBus.getDefault().post(event_data)
            }
            //리스트 아이템 삭제
            else if(customPopupData.order.equals(BaseConstant.DELETE_ITEM)){
                var event_data:EventData = EventData(BaseConstant.COMPLETE_DELETEITEM,customPopupData.position,"","",mTargetDateData,listAdapterData)
                EventBus.getDefault().post(event_data)
            }
            //앱스토어 팝업
            else if(customPopupData.order.equals(BaseConstant.APP_UPDATE)){
                //앱 업데이트 안내 => 업데이트 안뜸 임시 보류
                //mCommonUtils.updateAppLinkGuide()
            }
            mAlertDialog.dismiss()
        }
    }

    /*
    Calendar Type Popup
    */
    @SuppressLint("ResourceType")
    override fun showCalendarPopup() {
        mCommonUtils = CommonUtil(mContext)
        var calendar = Calendar.getInstance()
        var year = calendar.get(Calendar.YEAR)
        var month = calendar.get(Calendar.MONTH)
        var day = calendar.get(Calendar.DAY_OF_MONTH)
        var date_listener = object : DatePickerDialog.OnDateSetListener {
            override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
                var strDoubleMonth = mCommonUtils.setDoubleNumber((month+1).toString())
                var strDoubleDay = mCommonUtils.setDoubleNumber(dayOfMonth.toString())
                //당일 선택불가 체크
                var strSelectedDate = year.toString()+strDoubleMonth+strDoubleDay
                var getDateOftoday = mCommonUtils.getDateOfToday().replace("-","")
                if(strSelectedDate.equals(getDateOftoday)) {
                    Toast.makeText(mContext,mContext.getString(R.string.alert_content_not_choiec_today), Toast.LENGTH_SHORT).show()
                    return
                }
                mTargetDateData.year = year.toString()
                if (strDoubleMonth != null) {
                    mTargetDateData.month = strDoubleMonth
                }
                if (strDoubleDay != null) {
                    mTargetDateData.dayOfMonth = strDoubleDay
                }
                //아이템 추가 할때, 선택한 달력값 셋팅하기
                try {
                    var selectedDate = mTargetDateData.year+"-"+mTargetDateData.month+"-"+mTargetDateData.dayOfMonth
                    cDialogView.dialog_date.text = selectedDate
                }catch (e:Exception){
                    Log.e("mytag","e : "+e.message)
                }
                var event_data:EventData = EventData(BaseConstant.COMPLETE_SELECTED_CALENDAR,0,"","",mTargetDateData,mListAdapterData)
                EventBus.getDefault().post(event_data)
            }
        }
        //var builder = DatePickerDialog(mContext, android.R.style.Theme_DeviceDefault_Light, date_listener,year, month, day)
        var builder = DatePickerDialog(mContext, R.style.CalendarPopup, date_listener,year, month, day)
        builder.show()
    }



}//class end