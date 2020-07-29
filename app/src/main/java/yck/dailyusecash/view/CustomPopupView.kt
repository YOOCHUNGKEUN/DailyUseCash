package yck.dailyusecash.view

import android.app.Activity
import android.content.Context
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import yck.dailyusecash.R
import yck.dailyusecash.base.BaseConstant
import yck.dailyusecash.utils.CommonUtil
import yck.dailyusecash.data.CustomPopupData
import yck.dailyusecash.data.ListAdapterData
import kotlinx.android.synthetic.main.custom_dialog_edit_type.view.*
import kotlinx.android.synthetic.main.custom_dialog_text_type.view.*
import yck.dailyusecash.popup.CustomPopup
import yck.dailyusecash.utils.CustomTextUtil

class CustomPopupView {

    lateinit var mContext: Context
    lateinit var mActivity: Activity
    lateinit var mDialogView: View
    lateinit var mCommonUtil: CommonUtil
    lateinit var mCustomTextUtil: CustomTextUtil


    constructor(mContext: Context) {
        this.mContext = mContext
        this.mActivity = mContext as Activity

        mCustomTextUtil = CustomTextUtil(mContext)
        mCommonUtil = CommonUtil(mContext)
    }

    fun editTextTypePopupDialogView(order: String): View {
        mDialogView = LayoutInflater.from(mContext).inflate(R.layout.custom_dialog_edit_type, null)
        if (order.equals(BaseConstant.ADD_ITEM)) {
            setAddItemPopupView()
        } else if (order.equals(BaseConstant.TARGET_AMOUNT)) {
            setTargetAmountView()
        }
        return mDialogView
    }

    fun textTypePopupDialogView(customPopupData: CustomPopupData, listAdapterData: ListAdapterData): View {
        mDialogView = LayoutInflater.from(mContext).inflate(R.layout.custom_dialog_text_type, null)
        if (customPopupData.order.equals(BaseConstant.DELETE_ITEM)) {
            setDeleteItemView(listAdapterData)
        } else if (customPopupData.order.equals(BaseConstant.INIT_LIST)) {
            setInitListItemView()
        } else if (customPopupData.order.equals(BaseConstant.APP_UPDATE)) {
            setUpdateAppView()
        }
        return mDialogView
    }

    //=================================================================================================================
    //리스트 아이템 추가 Popup View
    fun setAddItemPopupView() {
        mDialogView.dialog_tv_title.visibility = View.VISIBLE
        mDialogView.dialog_edt_1.visibility = View.VISIBLE
        mDialogView.dialog_edt_2.visibility = View.VISIBLE
        mDialogView.dialog_date.visibility = View.VISIBLE
        mDialogView.dialog_tv_cancel.visibility = View.VISIBLE
        mDialogView.dialog_tv_confirm.visibility = View.VISIBLE
        mDialogView.dialog_tv_title.setText(mActivity.resources.getString(R.string.set_add_item))
        mDialogView.dialog_edt_1.setHint(mActivity.resources.getString(R.string.hint_add_history_name))
        mDialogView.dialog_edt_2.setHint(mActivity.resources.getString(R.string.hint_add_amount))
        mDialogView.dialog_edt_2.inputType = InputType.TYPE_CLASS_NUMBER//입력타입 -> 숫자
        mDialogView.dialog_edt_2.addTextChangedListener(mCustomTextUtil.setTextWatcher(mDialogView.dialog_edt_2))//입력할때 세자리 , 표시
        mDialogView.dialog_date.setText(mCommonUtil.getDateOfToday())
        mDialogView.dialog_tv_cancel.setText(mActivity.resources.getString(R.string.co_cancel))
        mDialogView.dialog_tv_confirm.setText(mActivity.resources.getString(R.string.co_confirm))
    }

    //목표금액 설정 Popup View
    fun setTargetAmountView() {
        mDialogView.dialog_tv_title.visibility = View.VISIBLE
        mDialogView.dialog_edt_1.visibility = View.VISIBLE
        mDialogView.dialog_tv_cancel.visibility = View.VISIBLE
        mDialogView.dialog_tv_confirm.visibility = View.VISIBLE
        mDialogView.dialog_tv_title.setText(mActivity.resources.getString(R.string.set_target_amount_comment))
        mDialogView.dialog_edt_1.setHint(mActivity.resources.getString(R.string.hint_target_amount))
        mDialogView.dialog_edt_1.inputType = InputType.TYPE_CLASS_NUMBER//입력타입 -> 숫자
        mDialogView.dialog_edt_1.addTextChangedListener(mCustomTextUtil.setTextWatcher(mDialogView.dialog_edt_1))//입력할때 세자리 , 표시
        mDialogView.dialog_tv_cancel.setText(mActivity.resources.getString(R.string.co_cancel))
        mDialogView.dialog_tv_confirm.setText(mActivity.resources.getString(R.string.co_confirm))
    }

    //리스트 아이템 삭제 Popup View
    fun setDeleteItemView(listAdapterData: ListAdapterData) {
        mDialogView.dialog2_tv_title.visibility = View.VISIBLE
        mDialogView.dialog2_tv_contents.visibility = View.VISIBLE
        mDialogView.dialog2_tv_cancel.visibility = View.VISIBLE
        mDialogView.dialog2_tv_confirm.visibility = View.VISIBLE
        mDialogView.dialog2_tv_title.setText(mActivity.resources.getString(R.string.set_remove_item_comment))
        val history_name = listAdapterData?.history_name ?: "(항목없음)"
        val history_amount = listAdapterData?.history_amount ?: "(금액없음)"
        mDialogView.dialog2_tv_contents.setText(
            mContext.getString(R.string.set_add_history_name)
                    + history_name
                    + "\n"
                    + mContext.getString(R.string.set_add_amount)
                    + history_amount)
        mDialogView.dialog2_tv_cancel.setText(mActivity.resources.getString(R.string.co_cancel))
        mDialogView.dialog2_tv_confirm.setText(mActivity.resources.getString(R.string.co_confirm))
    }

    //리스트 초기화 Popup View
    fun setInitListItemView() {
        mDialogView.dialog2_tv_title.visibility = View.VISIBLE
        mDialogView.dialog2_tv_contents.visibility = View.VISIBLE
        mDialogView.dialog2_tv_cancel.visibility = View.VISIBLE
        mDialogView.dialog2_tv_confirm.visibility = View.VISIBLE
        mDialogView.dialog2_tv_title.setText(mActivity.resources.getString(R.string.init_data_title))
        mDialogView.dialog2_tv_contents.setText(mActivity.resources.getString(R.string.init_data_comment))
        mDialogView.dialog2_tv_cancel.setText(mActivity.resources.getString(R.string.co_cancel))
        mDialogView.dialog2_tv_confirm.setText(mActivity.resources.getString(R.string.co_confirm))
    }

    // 앱 업데이트 Popup View
    fun setUpdateAppView() {
        mDialogView.dialog2_tv_title.visibility = View.VISIBLE
        mDialogView.dialog2_tv_contents.visibility = View.VISIBLE
        mDialogView.dialog2_tv_cancel.visibility = View.VISIBLE
        mDialogView.dialog2_tv_confirm.visibility = View.VISIBLE
        mDialogView.dialog2_tv_title.setText(mActivity.resources.getString(R.string.update_title))
        mDialogView.dialog2_tv_contents.setText(mActivity.resources.getString(R.string.update_content))
        mDialogView.dialog2_tv_cancel.setText(mActivity.resources.getString(R.string.co_cancel))
        mDialogView.dialog2_tv_confirm.setText(mActivity.resources.getString(R.string.co_confirm))
    }


}//cnass end