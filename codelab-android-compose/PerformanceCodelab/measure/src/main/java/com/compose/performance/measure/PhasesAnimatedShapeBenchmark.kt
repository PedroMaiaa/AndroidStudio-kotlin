
package com.compose.performance.measure

import androidx.benchmark.macro.CompilationMode
import androidx.benchmark.macro.ExperimentalMetricApi
import androidx.benchmark.macro.FrameTimingMetric
import androidx.benchmark.macro.MacrobenchmarkScope
import androidx.benchmark.macro.Metric
import androidx.benchmark.macro.TraceSectionMetric
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import androidx.test.uiautomator.By
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
@OptIn(ExperimentalMetricApi::class)
class PhasesAnimatedShapeBenchmark : AbstractBenchmark() {

    @Test
    fun phasesAnimatedShapeCompilationFull() = benchmark(CompilationMode.Full())

    override val metrics: List<Metric> =
        listOf(
            FrameTimingMetric(),
            TraceSectionMetric("PhasesComposeLogo", TraceSectionMetric.Mode.Sum),
        )

    override fun MacrobenchmarkScope.setupBlock() {
        pressHome()
        startTaskActivity("phases_animatedshape")
    }

    override fun MacrobenchmarkScope.measureBlock() {
        val toggleButton = device.findObject(By.text("Toggle Size"))
        toggleButton.click()
        
        Thread.sleep(2_000)
    }
}
