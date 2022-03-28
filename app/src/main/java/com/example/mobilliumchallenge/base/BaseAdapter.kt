package com.example.mobilliumchallenge.base

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.os.Build
import android.util.Log
import android.widget.Filter
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.example.talentbase_android.utils.HasName
import com.example.talentbase_android.utils.Listener
import java.lang.Exception
import java.util.*
import kotlin.collections.ArrayList

abstract class BaseAdapter<T, VH : BaseHolder<T>>(
    context: Context?,
    list: MutableList<T?>?
) : RecyclerView.Adapter<VH>() {
    private var context: Context? = null
    private var originalList: MutableList<T?>? = null
    private var list: MutableList<T?>? = null
    private var emptyListener: Listener<Boolean>? = null
    private var itemClickListener: Listener<Any?>? = null
    private val TAG = "BaseRecyclerViewAdapter"

    fun setListener(listener: Listener<Any?>) {
        this.itemClickListener = listener
    }

    var recentlyDeletedItem: T? = null
    var recentlyDeletedItemPosition = 0


    init {
        this.context = context
        this.list = list
        originalList = list
    }

    open fun getFilter(): Filter? {
        return object : Filter() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            override fun performFiltering(s: CharSequence): FilterResults {
                var result: MutableList<T?>? = ArrayList()
                if (s.isEmpty()) {
                    result = originalList
                } else {
                    for (t in originalList!!) {
                        if (t is HasName) {
                            if ((t as HasName).getNameText()!!
                                    .toLowerCase(Locale.forLanguageTag("tr")).contains(
                                        s.toString().toLowerCase(
                                            Locale.forLanguageTag("tr")
                                        )
                                    )
                            ) {
                                result!!.add(t)
                            }
                        }
                    }
                }
                val filterResults = FilterResults()
                filterResults.values = result
                return filterResults
            }

            @SuppressLint("NotifyDataSetChanged")
            override fun publishResults(constraint: CharSequence, results: FilterResults) {
                try {
                    list = results.values as MutableList<T?>?
                    notifyDataSetChanged()
                } catch (e: Exception) {
                    (getContext() as Activity?)!!.runOnUiThread {
                        list = results.values as MutableList<T?>
                    }
                    notifyDataSetChanged()
                }
                if (emptyListener != null) {
                    emptyListener?.onDone(getList()!!.isEmpty())
                }
            }
        }
    }


    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.onBind(getItem(position), position)
        holder.itemView.setOnClickListener {
            itemClickListener?.onDone(null)
        }
    }

    open fun getList(): List<T?>? {
        return list
    }

    open fun getOriginalList(): List<T?>? {
        return originalList
    }

    open fun getItem(i: Int): T {
        return list?.get(i)!!
    }

    open fun addItems(list: List<T>) {
        this.list!!.addAll(list)
        originalList!!.addAll(list)
        notifyItemRangeInserted(this.list!!.size - list.size, list.size)
    }

    open fun addItem(t: T) {
        Log.d(TAG, "addItem: called")
        list!!.add(t)
        notifyItemInserted(list!!.size - 1)
    }

    open fun addItem(t: T, position: Int) {
        list!!.add(position, t)
        //        this.originalList.add(t);
        notifyItemInserted(position)
    }

    @SuppressLint("NotifyDataSetChanged")
    open fun addItemAndNotifyDataSetChanged(t: T, position: Int) {
        list!!.add(position, t)
        //        this.originalList.add(t);
        notifyDataSetChanged()
    }

    open fun deleteItem(position: Int) {
        recentlyDeletedItem = list!![position]
        recentlyDeletedItemPosition = position
        list!!.removeAt(position)
        notifyItemRemoved(position)
    }

    open fun swapPositions(fromPosition: Int, toPosition: Int) {
        Collections.swap(list, fromPosition, toPosition)
        notifyItemMoved(fromPosition, toPosition)
    }


    open fun replace(t: T, positon: Int) {
        Collections.replaceAll(list, list!![positon], t)
    }

    override fun getItemCount(): Int {
        return if (list != null) list!!.size else 0
    }

    open fun setList(list: MutableList<T?>?): BaseAdapter<T, VH>? {
        this.list = list
        return this
    }

    open fun getContext(): Context? {
        return context
    }

    open fun setEmptyListener(emptyListener: Listener<Boolean>): BaseAdapter<T, VH>? {
        this.emptyListener = emptyListener
        return this
    }
}