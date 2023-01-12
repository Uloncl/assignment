package uni.ase.assignment.controllers

import javafx.scene.Scene
import javafx.scene.control.TextArea
import javafx.stage.Stage
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.platform.commons.util.StringUtils
import org.testfx.api.FxToolkit
import org.testfx.framework.junit5.ApplicationTest

/**
 * tests the [LogController] class, extends the [ApplicationTest] class from testfx from https://github.com/TestFX/TestFX
 */
@DisplayName("tests the LogController class")
class LogControllerTest : ApplicationTest() {
    lateinit var out : TextArea
    lateinit var log : LogController

    /**
     * starts the [stage] and makes a window so the [cmd] can be tested before each test
     */
    override fun start(stage: Stage) {
        out = TextArea()

        val scene = Scene(out)
        stage.scene = scene
        stage.show()

        log = LogController(out)
    }

    /**
     * closes the window after each test
     */
    override fun stop() {
        FxToolkit.hideStage()
    }

    /**
     * tests the [error] method in the [LogController] class
     */
    @Test
    @DisplayName("Tests error messages")
    fun testError() {
        log.error("this is an error")
        assert(log.log.text.contains(Regex("ERROR: this is an error")))
    }

    /**
     * tests the [out] method in the [LogController] class
     */
    @Test
    @DisplayName("Tests log messages")
    fun testOut() {
        log.out("this is a log message")
        assert(log.log.text.contains(Regex("this is a log message")))
    }

    /**
     * tests the [clear] method in the [LogController] class
     */
    @Test
    @DisplayName("Tests clearing all messages in log")
    fun testClear() {
        log.clear()
        assert(StringUtils.isBlank(log.log.text))
    }
}