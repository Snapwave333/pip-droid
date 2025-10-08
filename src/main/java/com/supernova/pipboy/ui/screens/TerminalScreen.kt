package com.supernova.pipboy.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.supernova.pipboy.data.model.PipBoyColor
import com.supernova.pipboy.data.terminal.TerminalCommands
import com.supernova.pipboy.ui.theme.PipBoyTypography
import kotlinx.coroutines.launch

/**
 * Terminal Screen - Text-based command interface
 */
@Composable
fun TerminalScreen(
    primaryColor: PipBoyColor,
    onExit: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val terminalCommands = remember { TerminalCommands(context) }
    val scope = rememberCoroutineScope()
    val focusRequester = remember { FocusRequester() }
    val listState = rememberLazyListState()
    
    var inputText by remember { mutableStateOf("") }
    var history by remember { mutableStateOf(listOf<TerminalLine>()) }
    var commandHistory by remember { mutableStateOf(listOf<String>()) }
    var historyIndex by remember { mutableStateOf(-1) }
    
    val color = Color(primaryColor.red, primaryColor.green, primaryColor.blue)
    
    // Show welcome message
    LaunchedEffect(Unit) {
        history = listOf(
            TerminalLine("ROBCO INDUSTRIES (TM) TERMLINK PROTOCOL", false),
            TerminalLine("COPYRIGHT 2075-2077 ROBCO INDUSTRIES", false),
            TerminalLine("", false),
            TerminalLine("Type 'help' for available commands.", false),
            TerminalLine("", false)
        )
    }
    
    // Auto-scroll to bottom when history changes
    LaunchedEffect(history.size) {
        if (history.isNotEmpty()) {
            listState.animateScrollToItem(history.size - 1)
        }
    }
    
    // Focus input on start
    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }
    
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color.Black)
            .padding(16.dp)
    ) {
        // Header
        Text(
            text = "☢ TERMINAL",
            style = PipBoyTypography.displayLarge,
            color = color,
            fontSize = 18.sp,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        
        // Terminal output
        LazyColumn(
            state = listState,
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .border(1.dp, color)
                .padding(8.dp),
            verticalArrangement = Arrangement.spacedBy(2.dp)
        ) {
            items(history) { line ->
                Text(
                    text = line.text,
                    style = TextStyle(
                        fontFamily = FontFamily.Monospace,
                        fontSize = 12.sp,
                        color = if (line.isError) Color.Red else color
                    )
                )
            }
        }
        
        Spacer(modifier = Modifier.height(8.dp))
        
        // Input line
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .border(1.dp, color)
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "> ",
                style = TextStyle(
                    fontFamily = FontFamily.Monospace,
                    fontSize = 14.sp,
                    color = color
                )
            )
            
            BasicTextField(
                value = inputText,
                onValueChange = { newValue ->
                    // Handle Enter key to execute command
                    if (newValue.contains("\n")) {
                        val command = newValue.replace("\n", "").trim()
                        if (command.isNotEmpty()) {
                            // Add command to history
                            history = history + TerminalLine("> $command", false)
                            commandHistory = commandHistory + command
                            historyIndex = -1
                            
                            // Execute command
                            scope.launch {
                                val output = terminalCommands.executeCommand(command)
                                
                                // Check for special commands
                                when {
                                    output.lines.contains("CLEAR_SCREEN") -> {
                                        history = emptyList()
                                    }
                                    output.lines.contains("EXIT_TERMINAL") -> {
                                        history = history + output.lines.filter { it != "EXIT_TERMINAL" }.map { TerminalLine(it, output.isError) }
                                        kotlinx.coroutines.delay(1000)
                                        onExit()
                                    }
                                    else -> {
                                        history = history + output.lines.map { TerminalLine(it, output.isError) }
                                    }
                                }
                            }
                        }
                        inputText = ""
                    } else {
                        inputText = newValue
                    }
                },
                modifier = Modifier
                    .weight(1f)
                    .focusRequester(focusRequester),
                textStyle = TextStyle(
                    fontFamily = FontFamily.Monospace,
                    fontSize = 14.sp,
                    color = color
                ),
                cursorBrush = SolidColor(color),
                singleLine = true,
                decorationBox = { innerTextField ->
                    if (inputText.isEmpty()) {
                        Text(
                            text = "Type command...",
                            style = TextStyle(
                                fontFamily = FontFamily.Monospace,
                                fontSize = 14.sp,
                                color = color.copy(alpha = 0.3f)
                            )
                        )
                    }
                    innerTextField()
                }
            )
        }
        
        // Helper text
        Text(
            text = "Press Enter to execute • Type 'help' for commands • Type 'exit' to quit",
            style = TextStyle(
                fontFamily = FontFamily.Monospace,
                fontSize = 9.sp,
                color = color.copy(alpha = 0.5f)
            ),
            modifier = Modifier.padding(top = 4.dp)
        )
    }
}

/**
 * Terminal line data class
 */
data class TerminalLine(
    val text: String,
    val isError: Boolean = false
)

