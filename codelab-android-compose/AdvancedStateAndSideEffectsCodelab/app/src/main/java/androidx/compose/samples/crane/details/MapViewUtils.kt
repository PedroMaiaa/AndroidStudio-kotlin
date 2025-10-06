

package androidx.compose.samples.crane.details

import android.os.Bundle
import androidx.annotation.FloatRange
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.samples.crane.R
import androidx.compose.ui.platform.LocalContext
import com.google.android.libraries.maps.GoogleMap
import com.google.android.libraries.maps.MapView


@Composable
fun rememberMapViewWithLifecycle(): MapView {
    val context = LocalContext.current
    
    return remember {
        MapView(context).apply {
            id = R.id.map
            onCreate(Bundle())
        }
    }
}

fun GoogleMap.setZoom(
    @FloatRange(from = MinZoom.toDouble(), to = MaxZoom.toDouble()) zoom: Float
) {
    resetMinMaxZoomPreference()
    setMinZoomPreference(zoom)
    setMaxZoomPreference(zoom)
}
