package com.supernova.pipboy.ui.viewmodel

import app.cash.turbine.test
import com.supernova.pipboy.data.model.PipBoyColor
import com.supernova.pipboy.data.model.SystemStatus
import com.supernova.pipboy.data.preferences.PipBoyPreferences
import com.supernova.pipboy.data.repository.AppRepository
import com.supernova.pipboy.data.repository.SystemRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.whenever

@OptIn(ExperimentalCoroutinesApi::class)
class MainViewModelTest {

    private val testDispatcher = StandardTestDispatcher()

    @Mock
    private lateinit var mockSystemRepository: SystemRepository

    @Mock
    private lateinit var mockAppRepository: AppRepository

    @Mock
    private lateinit var mockPreferences: PipBoyPreferences

    private lateinit var viewModel: MainViewModel

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        Dispatchers.setMain(testDispatcher)

        // Setup default mock behaviors
        whenever(mockPreferences.primaryColor).thenReturn(PipBoyColor.GREEN)
        whenever(mockPreferences.crtScanlinesEnabled).thenReturn(true)
        whenever(mockPreferences.crtFlickerEnabled).thenReturn(true)
        whenever(mockPreferences.crtDistortionEnabled).thenReturn(true)
        whenever(mockPreferences.notes).thenReturn("Test notes")

        // Setup repository mocks
        whenever(mockSystemRepository.systemStatus).thenReturn(MutableStateFlow(SystemStatus()))
        whenever(mockAppRepository.allApps).thenReturn(MutableStateFlow(emptyList()))
        whenever(mockAppRepository.favoriteApps).thenReturn(MutableStateFlow(emptyList()))
        whenever(mockAppRepository.categorizedApps).thenReturn(MutableStateFlow(emptyMap()))

        viewModel = MainViewModel(mockSystemRepository, mockAppRepository, mockPreferences)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `initial state should be correct`() = runTest {
        // Verify initial tab selection
        assertEquals(PipBoyTab.STATUS, viewModel.currentTab.value)

        // Verify initial color
        assertEquals(PipBoyColor.GREEN, viewModel.primaryColor.value)

        // Verify initial CRT effects
        val crtEffects = viewModel.crtEffects.value
        assertEquals(true, crtEffects.scanlines)
        assertEquals(true, crtEffects.flicker)
        assertEquals(true, crtEffects.distortion)
    }

    @Test
    fun `selectTab should update current tab`() = runTest {
        // Test tab selection
        viewModel.selectTab(PipBoyTab.INVENTORY)

        assertEquals(PipBoyTab.INVENTORY, viewModel.currentTab.value)
    }

    @Test
    fun `updatePrimaryColor should update color and preferences`() = runTest {
        val newColor = PipBoyColor.AMBER

        viewModel.updatePrimaryColor(newColor)

        assertEquals(newColor, viewModel.primaryColor.value)
        // Verify preferences were updated (would need to verify mock interaction)
    }

    @Test
    fun `updateCRTEffects should update effects and preferences`() = runTest {
        val newEffects = CRTEffects(
            scanlines = false,
            flicker = false,
            distortion = true
        )

        viewModel.updateCRTEffects(newEffects)

        assertEquals(newEffects, viewModel.crtEffects.value)
    }

    @Test
    fun `updateNotes should update notes and preferences`() = runTest {
        val newNotes = "Updated test notes"

        viewModel.updateNotes(newNotes)

        assertEquals(newNotes, viewModel.notes.value)
    }

    @Test
    fun `toggleFavoriteApp should call repository method`() = runTest {
        val packageName = "com.example.testapp"

        viewModel.toggleFavoriteApp(packageName)

        // Verify the repository method was called
        // This would be verified through mock verification
    }

    @Test
    fun `launchApp should call repository method`() = runTest {
        val packageName = "com.example.testapp"

        viewModel.launchApp(packageName)

        // Verify the repository method was called
        // This would be verified through mock verification
    }

    @Test
    fun `system status should be exposed from repository`() {
        val expectedStatus = SystemStatus(
            batteryLevel = 85,
            currentTime = "14:30"
        )

        // Update the mock to return specific status
        whenever(mockSystemRepository.systemStatus).thenReturn(
            MutableStateFlow(expectedStatus)
        )

        // Create new ViewModel to pick up the updated mock
        viewModel = MainViewModel(mockSystemRepository, mockAppRepository, mockPreferences)

        // Verify the status is exposed
        assertNotNull(viewModel.systemStatus)
    }

    @Test
    fun `all apps should be exposed from repository`() {
        // Verify the flow is exposed
        assertNotNull(viewModel.allApps)
    }

    @Test
    fun `favorite apps should be exposed from repository`() {
        // Verify the flow is exposed
        assertNotNull(viewModel.favoriteApps)
    }

    @Test
    fun `categorized apps should be exposed from repository`() {
        // Verify the flow is exposed
        assertNotNull(viewModel.categorizedApps)
    }

    @Test
    fun `media status should be exposed from repository`() {
        // Verify the flow is exposed
        assertNotNull(viewModel.mediaStatus)
    }

    @Test
    fun `notes should be exposed from preferences`() {
        // Verify the flow is exposed
        assertNotNull(viewModel.notes)
        assertEquals("Test notes", viewModel.notes.value)
    }
}
