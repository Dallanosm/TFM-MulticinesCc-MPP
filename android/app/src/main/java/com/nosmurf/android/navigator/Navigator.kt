package com.nosmurf.android.navigator

import android.content.Context
import android.content.Intent
import android.net.Uri
import com.nosmurf.android.ui.activity.MovieDetailsActivity


fun navigateToMovieDetailScreen(context: Context, id: Long) {
      context.startActivity(MovieDetailsActivity.getCallingIntent(context, id))
}

fun openVideo(context: Context, videoURL: String) {
    context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(videoURL)))
}