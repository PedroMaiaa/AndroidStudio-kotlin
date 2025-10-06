
package com.compose.performance.measure

import androidx.benchmark.macro.CompilationMode
import androidx.benchmark.macro.ExperimentalMetricApi
import androidx.benchmark.macro.FrameTimingMetric
import androidx.benchmark.macro.MacrobenchmarkScope
import androidx.benchmark.macro.Metric
import androidx.benchmark.macro.TraceSectionMetric
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
@OptIn(ExperimentalMetricApi::class)
class PhasesComposeLogoBenchmark : AbstractBenchmark() {

    @Test
    fun phasesComposeLogoCompilationFull() = benchmark(CompilationMode.Full())

    override val metrics: List<Metric> =
        listOf(
            FrameTimingMetric(),
            TraceSectionMetric("PhasesComposeLogo", TraceSectionMetric.Mode.Sum),
        )

    override fun MacrobenchmarkScope.setupBlock() {
        pressHome()
        startTaskActivity("phases_logo")
    }

    override fun MacrobenchmarkScope.measureBlock() {
        
        Thread.sleep(1_000)
    }
}
