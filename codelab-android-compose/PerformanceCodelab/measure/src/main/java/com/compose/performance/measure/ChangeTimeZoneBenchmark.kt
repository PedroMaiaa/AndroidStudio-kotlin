
package com.compose.performance.measure

import androidx.benchmark.macro.CompilationMode
import androidx.benchmark.macro.ExperimentalMetricApi
import androidx.benchmark.macro.FrameTimingMetric
import androidx.benchmark.macro.MacrobenchmarkScope
import androidx.benchmark.macro.Metric
import androidx.benchmark.macro.StartupMode
import androidx.benchmark.macro.TraceSectionMetric
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import androidx.test.uiautomator.By
import androidx.test.uiautomator.UiDevice
import androidx.test.uiautomator.Until
import androidx.tracing.Trace
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
@OptIn(ExperimentalMetricApi::class)
class ChangeTimeZoneBenchmark : AbstractBenchmark(StartupMode.WARM) {
    @Test
    fun changeTimeZoneCompilationFull() = benchmark(CompilationMode.Full())

    override val metrics: List<Metric> =
        listOf(
            FrameTimingMetric(),
            TraceSectionMetric("PublishDate.registerReceiver", TraceSectionMetric.Mode.Sum)
        )

    override fun MacrobenchmarkScope.setupBlock() {
        device.changeTimeZone("America/Los_Angeles")
        pressHome()
        startTaskActivity("accelerate_heavy")
        device.wait(Until.hasObject(By.res("list_of_items")), 5_000)
    }

    override fun MacrobenchmarkScope.measureBlock() {
        device.changeTimeZone("Europe/Warsaw")
        device.changeTimeZone("America/Los_Angeles")
    }
}

fun UiDevice.changeTimeZone(zoneId: String) {
    
    Trace.beginAsyncSection("change timezone", 0)
    executeShellCommand("service call alarm 3 s16 $zoneId")
    Trace.endAsyncSection("change timezone", 0)
    Thread.sleep(1_000)
}
