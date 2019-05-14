package com.nosmurf.android.ui.adapter

import android.content.Context
import android.support.v4.view.PagerAdapter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.nosmurf.android.R
import com.nosmurf.android.extensions.load
import com.nosmurf.common.client.Movie
import kotlinx.android.synthetic.main.item_movie.view.*

class MoviesAdapter(private val context: Context, private val items: List<Movie>,
                    private val onItemClicked: (Movie) -> Unit) : PagerAdapter() {

    override fun isViewFromObject(view: View, `object`: Any): Boolean = view == `object`

    override fun getCount(): Int = items.size

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val view = LayoutInflater.from(context).inflate(R.layout.item_movie, container, false)
        val movie = items[position]
        view.image.load(movie.img)
        view.setOnClickListener { onItemClicked(movie) }
        container.addView(view)
        return view
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }

}