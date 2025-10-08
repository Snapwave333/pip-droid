package com.supernova.pipboy.wear.watchface

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.view.SurfaceHolder
import androidx.core.content.ContextCompat
import androidx.wear.watchface.CanvasType
import androidx.wear.watchface.ComplicationSlotsManager
import androidx.wear.watchface.Renderer
import androidx.wear.watchface.WatchState
import androidx.wear.watchface.style.CurrentUserStyleRepository
import androidx.wear.watchface.style.UserStyle
import com.supernova.pipboy.wear.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

/**
 * Pip-Boy Watch Face Renderer
 * Handles drawing the watch face with retro-futuristic styling
 */
class PipBoyWatchFaceRenderer(
    private val context: Context,
    surfaceHolder: SurfaceHolder,
    watchState: WatchState,
    complicationSlotsManager: ComplicationSlotsManager,
    currentUserStyleRepository: CurrentUserStyleRepository
) : Renderer.CanvasRenderer2<PipBoyWatchFaceRenderer.PipBoySharedAssets>(
    surfaceHolder,
    currentUserStyleRepository,
    watchState,
    CanvasType.HARDWARE,
    16L, // frames per second
    complicationSlotsManager,
    clearWithBackgroundTintBeforeRenderingHighlightLayer = false
) {

    private val scope: CoroutineScope = CoroutineScope(Dispatchers.Main + SupervisorJob())

    // Paint objects for drawing
    private lateinit var backgroundPaint: Paint
    private lateinit var textPaint: Paint
    private lateinit var accentPaint: Paint
    private lateinit var scanlinePaint: Paint

    // Colors based on user style
    private var primaryColor = android.graphics.Color.GREEN
    private var showComplications = true
    private var ambientMode = true

    override suspend fun createSharedAssets(): PipBoySharedAssets {
        return PipBoySharedAssets(
            backgroundPaint = Paint().apply {
                color = android.graphics.Color.BLACK
                isAntiAlias = true
            },
            textPaint = Paint().apply {
                color = primaryColor
                textSize = 48f
                isAntiAlias = true
                typeface = android.graphics.Typeface.create(
                    android.graphics.Typeface.MONOSPACE,
                    android.graphics.Typeface.BOLD
                )
            },
            accentPaint = Paint().apply {
                color = primaryColor
                strokeWidth = 4f
                style = Paint.Style.STROKE
                isAntiAlias = true
            },
            scanlinePaint = Paint().apply {
                color = android.graphics.Color.argb(50, 0, 255, 0)
                style = Paint.Style.FILL
            }
        )
    }

    override fun render(
        canvas: Canvas,
        bounds: Rect,
        zonedDateTime: ZonedDateTime,
        sharedAssets: PipBoySharedAssets
    ) {
        // Update colors based on user style
        updateColorsFromUserStyle()

        // Clear canvas
        canvas.drawColor(android.graphics.Color.BLACK)

        // Draw background elements
        drawBackground(canvas, bounds)

        // Draw time
        drawTime(canvas, bounds, zonedDateTime)

        // Draw date
        drawDate(canvas, bounds, zonedDateTime)

        // Draw scanlines effect
        if (ambientMode) {
            drawScanlines(canvas, bounds)
        }

        // Draw complications if enabled
        if (showComplications) {
            drawComplications(canvas, bounds, zonedDateTime)
        }

        // Draw border
        drawBorder(canvas, bounds)
    }

    override fun renderHighlightLayer(
        canvas: Canvas,
        bounds: Rect,
        zonedDateTime: ZonedDateTime,
        sharedAssets: PipBoySharedAssets
    ) {
        // Handle highlight layer for complications
        canvas.drawColor(android.graphics.Color.TRANSPARENT)
    }

    private fun updateColorsFromUserStyle() {
        val userStyle = currentUserStyleRepository.userStyle.value

        // Update primary color based on user selection
        primaryColor = when (userStyle["color_style_setting"]?.id) {
            "amber_style" -> android.graphics.Color.rgb(255, 193, 7) // Amber
            "blue_style" -> android.graphics.Color.BLUE
            else -> android.graphics.Color.GREEN // Default green
        }

        // Update complication visibility
        showComplications = userStyle["show_complications_setting"]?.toBooleanValue() ?: true

        // Update ambient mode setting
        ambientMode = userStyle["ambient_mode_setting"]?.toBooleanValue() ?: true
    }

    private fun drawBackground(canvas: Canvas, bounds: Rect) {
        // Draw main background
        canvas.drawRect(bounds, sharedAssets.backgroundPaint)

        // Draw subtle gradient overlay
        val gradientPaint = Paint().apply {
            shader = android.graphics.LinearGradient(
                0f, 0f, bounds.width().toFloat(), bounds.height().toFloat(),
                android.graphics.Color.argb(100, 0, 50, 0),
                android.graphics.Color.argb(20, 0, 100, 0),
                android.graphics.Shader.TileMode.CLAMP
            )
            isAntiAlias = true
        }
        canvas.drawRect(bounds, gradientPaint)
    }

    private fun drawTime(canvas: Canvas, bounds: Rect, zonedDateTime: ZonedDateTime) {
        val timeFormat = DateTimeFormatter.ofPattern("HH:mm:ss")
        val timeText = zonedDateTime.format(timeFormat)

        // Configure time paint
        sharedAssets.textPaint.apply {
            color = primaryColor
            textSize = 64f
            textAlign = Paint.Align.CENTER
        }

        // Calculate position
        val x = bounds.exactCenterX()
        val y = bounds.exactCenterY() - 40f

        // Draw time text
        canvas.drawText(timeText, x, y, sharedAssets.textPaint)

        // Draw time label
        sharedAssets.textPaint.apply {
            textSize = 24f
            color = android.graphics.Color.argb(150, primaryColor)
        }
        canvas.drawText("TIME", x, y - 80f, sharedAssets.textPaint)
    }

    private fun drawDate(canvas: Canvas, bounds: Rect, zonedDateTime: ZonedDateTime) {
        val dateFormat = DateTimeFormatter.ofPattern("EEE, MMM dd")
        val dateText = zonedDateTime.format(dateFormat).uppercase()

        // Configure date paint
        sharedAssets.textPaint.apply {
            color = android.graphics.Color.argb(180, primaryColor)
            textSize = 28f
            textAlign = Paint.Align.CENTER
        }

        // Calculate position
        val x = bounds.exactCenterX()
        val y = bounds.exactCenterY() + 60f

        // Draw date text
        canvas.drawText(dateText, x, y, sharedAssets.textPaint)
    }

    private fun drawScanlines(canvas: Canvas, bounds: Rect) {
        val scanlineHeight = 4f
        val scanlineSpacing = 8f

        var y = 0f
        while (y < bounds.height()) {
            canvas.drawRect(
                0f, y, bounds.width().toFloat(), y + scanlineHeight,
                sharedAssets.scanlinePaint
            )
            y += scanlineSpacing
        }
    }

    private fun drawComplications(
        canvas: Canvas,
        bounds: Rect,
        zonedDateTime: ZonedDateTime
    ) {
        // Draw complication slots
        // This would be handled by the complication system
        // For now, draw placeholder rectangles

        // Top complication area
        val topRect = Rect(
            (bounds.width() * 0.1f).toInt(),
            (bounds.height() * 0.1f).toInt(),
            (bounds.width() * 0.9f).toInt(),
            (bounds.height() * 0.3f).toInt()
        )

        sharedAssets.accentPaint.apply {
            color = android.graphics.Color.argb(100, primaryColor)
            style = Paint.Style.STROKE
            strokeWidth = 2f
        }
        canvas.drawRect(topRect, sharedAssets.accentPaint)

        // Bottom complication area
        val bottomRect = Rect(
            (bounds.width() * 0.3f).toInt(),
            (bounds.height() * 0.7f).toInt(),
            (bounds.width() * 0.7f).toInt(),
            (bounds.height() * 0.9f).toInt()
        )

        canvas.drawRect(bottomRect, sharedAssets.accentPaint)
    }

    private fun drawBorder(canvas: Canvas, bounds: Rect) {
        sharedAssets.accentPaint.apply {
            color = primaryColor
            style = Paint.Style.STROKE
            strokeWidth = 6f
        }

        // Draw outer border
        canvas.drawRect(
            3f, 3f, bounds.width() - 3f, bounds.height() - 3f,
            sharedAssets.accentPaint
        )

        // Draw corner decorations
        val cornerSize = 20f
        val corners = listOf(
            androidx.core.util.Pair(0f, 0f), // Top-left
            androidx.core.util.Pair(bounds.width().toFloat(), 0f), // Top-right
            androidx.core.util.Pair(0f, bounds.height().toFloat()), // Bottom-left
            androidx.core.util.Pair(bounds.width().toFloat(), bounds.height().toFloat()) // Bottom-right
        )

        corners.forEach { (x, y) ->
            canvas.drawRect(
                x, y, x + cornerSize, y + cornerSize,
                sharedAssets.accentPaint
            )
        }
    }

    override fun onDestroy() {
        scope.cancel()
        super.onDestroy()
    }

    /**
     * Shared assets for the watch face
     */
    class PipBoySharedAssets(
        val backgroundPaint: Paint,
        val textPaint: Paint,
        val accentPaint: Paint,
        val scanlinePaint: Paint
    )
}
