package com.nosmurf.android.ui.adapter

import android.view.View
import com.nosmurf.android.R
import kotlinx.android.synthetic.main.item_pass.view.*

class NextPassAdapter : RootAdapter<String>() {

    override val itemLayoutId: Int = R.layout.item_pass

    override fun viewHolder(view: View): RootViewHolder<String> = ViewHolder(view = view)

    class ViewHolder(view: View) : RootViewHolder<String>(itemView = view) {
        override fun bind(model: String) {
            itemView.pass.text = model
        }
    }

}