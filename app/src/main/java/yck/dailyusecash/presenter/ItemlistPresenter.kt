package yck.dailyusecash.presenter

import android.app.Activity
import android.content.Context
import yck.dailyusecash.base.BaseConstant
import yck.dailyusecash.utils.CustomConverterUtil
import yck.dailyusecash.popup.CustomPopup
import yck.dailyusecash.interfaces.IItemlistConstractor
import yck.dailyusecash.data.ListAdapterData
import yck.dailyusecash.repository.CustomRepository

class ItemlistPresenter : IItemlistConstractor.presenter {

    lateinit var mContext: Context
    lateinit var mActivity: Activity

    lateinit var mCustomRepository: CustomRepository
    lateinit var mCustomConverterUtil: CustomConverterUtil
    lateinit var mCustomPopup: CustomPopup

    constructor(mContext: Context) {
        this.mContext = mContext
        this.mActivity = mContext as Activity
    }

    /*
    리스트 아이템 추가
    */
    override fun addItemOfList(listdata: ListAdapterData) {
        mCustomRepository = CustomRepository(mContext)
        mCustomConverterUtil = CustomConverterUtil(mContext)
        var getArrayList: ArrayList<ListAdapterData> = ArrayList<ListAdapterData>()
        if (mCustomRepository.getRepository(BaseConstant.PREF_KEY_LIST) != null&&(mCustomRepository.getRepository(BaseConstant.PREF_KEY_LIST))!!.length>0) {
            var strList:String = mCustomRepository.getRepository(BaseConstant.PREF_KEY_LIST)!!
            var arrlist  = mCustomConverterUtil.stringToListAdapterData(strList)
            getArrayList = arrlist!!
        }
        getArrayList.add(listdata)
        var strList = mCustomConverterUtil.listAdapteDataToString(getArrayList).toString()
        mCustomRepository.setRepository(BaseConstant.PREF_KEY_LIST,strList)
    }

    /*
    리스트 아이템 삭제
    */
    override fun deleteItemOfList(position: Int, listAdaterData: ListAdapterData) {
        mCustomRepository = CustomRepository(mContext)
        mCustomConverterUtil = CustomConverterUtil(mContext)
        //저장된 string 리스트 가져오기
        var getStrlist = mCustomRepository.getRepository(BaseConstant.PREF_KEY_LIST)
        var arrlist: ArrayList<ListAdapterData>? = ArrayList<ListAdapterData>()
        if (getStrlist != null && getStrlist.length > 0) {
            //string -> arraylist 변환
            arrlist = mCustomConverterUtil.stringToListAdapterData(getStrlist.toString())
        }
        //arraylist delete
        arrlist?.removeAt(position)

        //arraylist -> string 변환
        var strList = ""
        if (arrlist != null) {
            strList = mCustomConverterUtil.listAdapteDataToString(arrlist).toString()
        }
        //string 저장하기
        mCustomRepository.setRepository(BaseConstant.PREF_KEY_LIST, strList)
    }

    /*
    리스트 아이템 총 금액
    */
    override fun resultAmountOfList(): Int {
        //현재 리스트 총 금액
        if (getItemList() == null) {
            return 0
        }
        var listAdapter: ArrayList<ListAdapterData> = getItemList()!!
        var all_amount_value = 0//사용전체금액
        var itemAmount = ""
        var amount_value: Int = 0//계산용변수
        for (i in 0..listAdapter.size - 1) {
            if (listAdapter.get(i).history_amount != null) {
                if (listAdapter.get(i).history_amount.contains(",")) {
                    itemAmount = listAdapter.get(i).history_amount.replace(",", "")
                } else {
                    itemAmount = listAdapter.get(i).history_amount
                }
                amount_value = itemAmount.toInt()
                all_amount_value = all_amount_value + amount_value
            }
        }
        return all_amount_value
    }

    /*
    저장된 String 형태의 ItemList 를 ArrayListAdapter 형태로 가져오기
    */
    override fun getItemList(): ArrayList<ListAdapterData>? {
        var customRepository: CustomRepository = CustomRepository(mContext)
        var customConverterUtil: CustomConverterUtil = CustomConverterUtil(mContext)
        //저장된 string 가져오기
        if (customRepository.getRepository(BaseConstant.PREF_KEY_LIST) == null || customRepository.getRepository(
                BaseConstant.PREF_KEY_LIST
            )!!.length <= 0
        ) {
            return null
        }
        var getArrList = customRepository.getRepository(BaseConstant.PREF_KEY_LIST)
        //가져온 string -> ArrayList<MainListAdapterData> 변환하기
        var transArrayList: ArrayList<ListAdapterData>? =
            customConverterUtil.stringToListAdapterData(getArrList.toString())
        if (transArrayList == null) {
            return null
        }
        return transArrayList
    }



}//class end