
package com.example.android.compose.recomposehighlighter

import androidx.compose.runtime.Stable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.drawscope.ContentDrawScope
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.lerp
import androidx.compose.ui.node.DrawModifierNode
import androidx.compose.ui.node.ModifierNodeElement
import androidx.compose.ui.node.invalidateDraw
import androidx.compose.ui.platform.InspectorInfo
import androidx.compose.ui.platform.debugInspectorInfo
import androidx.compose.ui.unit.dp
import java.util.Objects
import kotlin.math.min
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


@Stable
fun Modifier.recomposeHighlighter(): Modifier = this.then(RecomposeHighlighterElement())

private class RecomposeHighlighterElement : ModifierNodeElement<RecomposeHighlighterModifier>() {

    override fun InspectorInfo.inspectableProperties() {
        debugInspectorInfo { name = "recomposeHighlighter" }
    }

    override fun create(): RecomposeHighlighterModifier = RecomposeHighlighterModifier()

    override fun update(node: RecomposeHighlighterModifier) {
        node.incrementCompositions()
    }

    
    override fun equals(other: Any?): Boolean = false

    override fun hashCode(): Int = Objects.hash(this)
}

private class RecomposeHighlighterModifier : Modifier.Node(), DrawModifierNode {

    private var timerJob: Job? = null

    
    private var totalCompositions: Long = 0
        set(value) {
            if (field == value) return
            restartTimer()
            field = value
            invalidateDraw()
        }

    fun incrementCompositions() {
        totalCompositions++
    }

    override fun onAttach() {
        super.onAttach()
        restartTimer()
    }

    override val shouldAutoInvalidate: Boolean = false

    override fun onDetach() {
        timerJob?.cancel()
    }

    
    private fun restartTimer() {
        if (!isAttached) return

        timerJob?.cancel()
        timerJob = coroutineScope.launch {
            delay(3000)
            totalCompositions = 0
            invalidateDraw()
        }
    }

    override fun ContentDrawScope.draw() {
        
        drawContent()

        

        val hasValidBorderParams = size.minDimension > 0f
        if (!hasValidBorderParams || totalCompositions <= 0) {
            return
        }

        val (color, strokeWidthPx) =
            when (totalCompositions) {
                
                
                1L -> Color.Blue to 1f
                
                2L -> Color.Green to 2.dp.toPx()
                
                
                else -> {
                    lerp(
                        Color.Yellow.copy(alpha = 0.8f),
                        Color.Red.copy(alpha = 0.5f),
                        min(1f, (totalCompositions - 1).toFloat() / 100f)
                    ) to totalCompositions.toInt().dp.toPx()
                }
            }

        val halfStroke = strokeWidthPx / 2
        val topLeft = Offset(halfStroke, halfStroke)
        val borderSize = Size(size.width - strokeWidthPx, size.height - strokeWidthPx)

        val fillArea = (strokeWidthPx * 2) > size.minDimension
        val rectTopLeft = if (fillArea) Offset.Zero else topLeft
        val size = if (fillArea) size else borderSize
        val style = if (fillArea) Fill else Stroke(strokeWidthPx)

        drawRect(
            brush = SolidColor(color),
            topLeft = rectTopLeft,
            size = size,
            style = style
        )
    }
}
