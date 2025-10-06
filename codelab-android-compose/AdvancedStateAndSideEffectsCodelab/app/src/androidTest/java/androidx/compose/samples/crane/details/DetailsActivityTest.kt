

package androidx.compose.samples.crane.details

import androidx.compose.samples.crane.R
import androidx.compose.samples.crane.data.DestinationsRepository
import androidx.compose.samples.crane.data.ExploreModel
import androidx.compose.samples.crane.data.MADRID
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.AndroidComposeTestRule
import androidx.compose.ui.test.onNodeWithText
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.platform.app.InstrumentationRegistry
import com.google.android.libraries.maps.MapView
import com.google.android.libraries.maps.model.CameraPosition
import com.google.android.libraries.maps.model.LatLng
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.util.concurrent.CountDownLatch
import javax.inject.Inject
import kotlin.math.pow
import kotlin.math.round

@HiltAndroidTest
class DetailsActivityTest {

    @Inject
    lateinit var destinationsRepository: DestinationsRepository
    private lateinit var cityDetails: ExploreModel

    private val city = MADRID
    private val testExploreModel = ExploreModel(city, "description", "imageUrl")

    @get:Rule(order = 0)
    var hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeTestRule = AndroidComposeTestRule(
        activityRule = ActivityScenarioRule<DetailsActivity>(
            createDetailsActivityIntent(
                InstrumentationRegistry.getInstrumentation().targetContext,
                testExploreModel
            )
        ),
        
        activityProvider = { rule ->
            var activity: DetailsActivity? = null
            rule.scenario.onActivity { activity = it }
            if (activity == null) {
                throw IllegalStateException("Activity was not set in the ActivityScenarioRule!")
            }
            activity!!
        }
    )

    @Before
    fun setUp() {
        hiltRule.inject()
        cityDetails = destinationsRepository.getDestination(MADRID.name)!!
    }

    @Test
    fun mapView_cameraPositioned() {
        composeTestRule.onNodeWithText(cityDetails.city.nameToDisplay).assertIsDisplayed()
        composeTestRule.onNodeWithText(cityDetails.description).assertIsDisplayed()
        onView(withId(R.id.map)).check(matches(isDisplayed()))

        var cameraPosition: CameraPosition? = null
        waitForMap(onCameraPosition = { cameraPosition = it })

        val expected = LatLng(
            testExploreModel.city.latitude.toDouble(),
            testExploreModel.city.longitude.toDouble()
        )
        assert(expected.latitude == cameraPosition?.target?.latitude?.round(6))
        assert(expected.longitude == cameraPosition?.target?.longitude?.round(6))
    }

    
    private fun waitForMap(onCameraPosition: (CameraPosition) -> Unit) {
        val countDownLatch = CountDownLatch(1)
        composeTestRule.activityRule.scenario.onActivity {
            it.findViewById<MapView>(R.id.map).getMapAsync { map ->
                onCameraPosition(map.cameraPosition)
                countDownLatch.countDown()
            }
        }
        countDownLatch.await()
    }
}

private fun Double.round(decimals: Int = 2): Double =
    round(this * 10f.pow(decimals)) / 10f.pow(decimals)
