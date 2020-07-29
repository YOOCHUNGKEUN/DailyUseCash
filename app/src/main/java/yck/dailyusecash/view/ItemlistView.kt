package yck.dailyusecash.view

import android.app.Activity
import android.content.Context
import androidx.recyclerview.widget.LinearLayoutManager
import yck.dailyusecash.interfaces.IItemlistConstractor
import yck.dailyusecash.data.ListAdapterData
import yck.dailyusecash.presenter.ItemlistPresenter
import kotlinx.android.synthetic.main.activity_main.*
import yck.dailyusecash.adapter.CustomRecyclerAdapter

class ItemlistView : IItemlistConstractor.view {

    lateinit var mContext: Context
    lateinit var mActivity: Activity

    constructor(mContext: Context) {
        this.mContext = mContext
        this.mActivity = mContext as Activity
    }

    override fun setListView() {
        var arrlistAdapter: ArrayList<ListAdapterData> = ArrayList<ListAdapterData>()
        var itemlistPresenter: ItemlistPresenter = ItemlistPresenter(mContext)
        if (itemlistPresenter.getItemList() == null) {
            arrlistAdapter.clear()
        }else{
            arrlistAdapter = itemlistPresenter.getItemList()!!
        }
        //set Recycler Adapter
        val adapter = CustomRecyclerAdapter(mContext, arrlistAdapter)
        mActivity.mainlist_recyclerView.adapter = adapter
        val lm = LinearLayoutManager(mContext)
        mActivity.mainlist_recyclerView.layoutManager = lm
    }


}//class end

