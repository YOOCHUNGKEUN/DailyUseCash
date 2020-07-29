package yck.dailyusecash.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import yck.dailyusecash.R
import yck.dailyusecash.base.BaseConstant
import yck.dailyusecash.popup.CustomPopup
import yck.dailyusecash.data.CustomPopupData
import yck.dailyusecash.data.ListAdapterData
import kotlinx.android.synthetic.main.list_item.view.*
import yck.dailyusecash.utils.CommonUtil

class CustomRecyclerAdapter(var context:Context,private val items: ArrayList<ListAdapterData>): RecyclerView.Adapter<CustomRecyclerAdapter.ViewHolder>() {

    var mContext:Context = context
    var mCustomPopupData:CustomPopupData = CustomPopupData("",0)
    lateinit var mCustomPopup: CustomPopup

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        val listener = View.OnClickListener { it ->
            //리스트 아이템 삭제 팝업
            mCustomPopupData.order = BaseConstant.DELETE_ITEM
            mCustomPopupData.position = position
            mCustomPopup = CustomPopup(mContext)
            mCustomPopup.showTextTypePopup(mCustomPopupData,item)
        }
        holder.apply {
            bind(listener,item)
            itemView.tag = item
        }

    }

    class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        private var view: View = v
        fun bind(listener: View.OnClickListener, item: ListAdapterData){
            if(item!=null){
                if(item.history_name==null||item.history_name.length<=0){
                    return
                }
                if(item.history_date==null||item.history_date.length<=0){
                    return
                }
                if(item.history_amount==null||item.history_amount.length<=0){
                    return
                }

                //날짜 20200616 -> 2020-06-16 형식으로 변환시키기
                if(item.history_date.contains("-")){
                    view.tv_date.text = item.history_date
                }else{
                    var strYear:String = item.history_date.substring(0,4)
                    var strMonth:String = item.history_date.substring(4,6)
                    var strDay:String = item.history_date.substring(6,item.history_date.length)
                    view.tv_date.text = strYear+"-"+strMonth+"-"+strDay
                }

                //금액
                var itemAmount = ""
                if(item.history_amount.contains(",")){
                    itemAmount = item.history_amount.replace(",","")
                }else{
                    itemAmount = item.history_amount
                }

                var amount_Int:Int = itemAmount.toInt()

                view.tv_history_name.text = item.history_name
                view.tv_amount.text = amount_Int.toString()
                view.setOnClickListener(listener)
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflatedView = LayoutInflater.from(parent.context).inflate(R.layout.list_item,parent,false)
        return ViewHolder(
            inflatedView
        )
    }


}//class end