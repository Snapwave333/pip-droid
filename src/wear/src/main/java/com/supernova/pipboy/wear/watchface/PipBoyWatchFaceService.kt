package com.supernova.pipboy.wear.watchface

import android.view.SurfaceHolder
import androidx.wear.watchface.CanvasComplicationFactory
import androidx.wear.watchface.CanvasWatchFaceService
import androidx.wear.watchface.ComplicationSlotsManager
import androidx.wear.watchface.WatchFace
import androidx.wear.watchface.WatchFaceService
import androidx.wear.watchface.WatchFaceType
import androidx.wear.watchface.WatchState
import androidx.wear.watchface.complications.ComplicationSlot
import androidx.wear.watchface.complications.DefaultComplicationDataSourcePolicy
import androidx.wear.watchface.complications.SystemDataSources
import androidx.wear.watchface.complications.data.ComplicationType
import androidx.wear.watchface.complications.datasource.ComplicationDataSourceUpdateRequester
import androidx.wear.watchface.style.CurrentUserStyleRepository
import androidx.wear.watchface.style.UserStyleSchema
import androidx.wear.watchface.style.UserStyleSetting
import androidx.wear.watchface.style.WatchFaceLayer
import com.supernova.pipboy.wear.complications.PipBoyComplicationProvider

/**
 * Pip-Boy Watch Face Service for Wear OS
 * Provides a retro-futuristic watch face with Pip-Boy styling
 */
class PipBoyWatchFaceService : CanvasWatchFaceService() {

    override fun createComplicationSlotsManager(
        currentUserStyleRepository: CurrentUserStyleRepository
    ): ComplicationSlotsManager {
        val defaultCanvasComplicationFactory = CanvasComplicationFactory { watchState, listener ->
            PipBoyComplicationRenderer(
                context = applicationContext,
                currentUserStyleRepository = currentUserStyleRepository,
                watchState = watchState,
                listener = listener
            )
        }

        return ComplicationSlotsManager(
            listOf(
                ComplicationSlot.createRoundRectComplicationSlotBuilder(
                    id = 100,
                    canvasComplicationFactory = defaultCanvasComplicationFactory,
                    supportedTypes = listOf(
                        ComplicationType.SHORT_TEXT,
                        ComplicationType.LONG_TEXT,
                        ComplicationType.RANGED_VALUE
                    ),
                    defaultDataSourcePolicy = DefaultComplicationDataSourcePolicy(
                        SystemDataSources.DATA_SOURCE_DAY_OF_WEEK,
                        ComplicationType.SHORT_TEXT
                    ),
                    bounds = androidx.wear.watchface.complications.rendering.ComplicationSlotBounds(
                        androidx.wear.watchface.complications.rendering.ComplicationBounds(
                            left = 0.3f,
                            top = 0.7f,
                            right = 0.7f,
                            bottom = 0.9f
                        )
                    )
                ).build(),
                ComplicationSlot.createRoundRectComplicationSlotBuilder(
                    id = 101,
                    canvasComplicationFactory = defaultCanvasComplicationFactory,
                    supportedTypes = listOf(
                        ComplicationType.SHORT_TEXT,
                        ComplicationType.LONG_TEXT
                    ),
                    defaultDataSourcePolicy = DefaultComplicationDataSourcePolicy(
                        PipBoyComplicationProvider::class.java,
                        ComplicationType.SHORT_TEXT
                    ),
                    bounds = androidx.wear.watchface.complications.rendering.ComplicationSlotBounds(
                        androidx.wear.watchface.complications.rendering.ComplicationBounds(
                            left = 0.1f,
                            top = 0.1f,
                            right = 0.9f,
                            bottom = 0.3f
                        )
                    )
                ).build()
            ),
            currentUserStyleRepository
        )
    }

    override fun createUserStyleSchema(): UserStyleSchema {
        return UserStyleSchema(
            listOf(
                UserStyleSetting.ListUserStyleSetting(
                    id = "color_style_setting",
                    displayName = "Primary Color",
                    description = "Change the primary color of the watch face",
                    icon = null,
                    options = listOf(
                        UserStyleSetting.ListUserStyleSetting.ListOption(
                            id = "green_style",
                            displayName = "Pip-Boy Green"
                        ),
                        UserStyleSetting.ListUserStyleSetting.ListOption(
                            id = "amber_style",
                            displayName = "Amber"
                        ),
                        UserStyleSetting.ListUserStyleSetting.ListOption(
                            id = "blue_style",
                            displayName = "Blue"
                        )
                    )
                ),
                UserStyleSetting.BooleanUserStyleSetting(
                    id = "show_complications_setting",
                    displayName = "Show Complications",
                    description = "Display complication data on the watch face",
                    icon = null,
                    defaultValue = true
                ),
                UserStyleSetting.BooleanUserStyleSetting(
                    id = "ambient_mode_setting",
                    displayName = "Ambient Mode",
                    description = "Optimize for always-on display",
                    icon = null,
                    defaultValue = true
                )
            )
        )
    }

    override suspend fun createWatchFace(
        surfaceHolder: SurfaceHolder,
        watchState: WatchState,
        complicationSlotsManager: ComplicationSlotsManager,
        currentUserStyleRepository: CurrentUserStyleRepository
    ): WatchFace {
        val renderer = PipBoyWatchFaceRenderer(
            context = applicationContext,
            surfaceHolder = surfaceHolder,
            watchState = watchState,
            complicationSlotsManager = complicationSlotsManager,
            currentUserStyleRepository = currentUserStyleRepository
        )

        return WatchFace(
            watchFaceType = WatchFaceType.ANALOG,
            renderer = renderer
        )
    }
}
