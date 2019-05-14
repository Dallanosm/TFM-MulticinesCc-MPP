package com.nosmurf.android.ui.adapter

import android.view.View
import com.nosmurf.android.R
import com.nosmurf.android.extensions.toFormattedString
import com.nosmurf.common.client.Comment
import com.nosmurf.common.client.DATE_FORMAT
import kotlinx.android.synthetic.main.item_comment.view.*
import java.util.*

class CommentsAdapter : RootAdapter<Comment>() {

    override val itemLayoutId: Int = R.layout.item_comment

    override fun viewHolder(view: View): RootViewHolder<Comment> = ViewHolder(view)

    class ViewHolder(view: View) : RootViewHolder<Comment>(itemView = view) {
        override fun bind(model: Comment) {
            itemView.comment.text = model.value
            itemView.date.text = Date(model.createdDate).toFormattedString(DATE_FORMAT)
        }
    }

}