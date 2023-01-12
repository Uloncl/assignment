package uni.ase.assignment.controllers

import javafx.scene.Scene
import javafx.scene.canvas.Canvas
import javafx.scene.control.TextArea
import javafx.scene.control.TextField
import javafx.scene.layout.VBox
import javafx.stage.Stage
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.testfx.api.FxToolkit
import org.testfx.framework.junit5.ApplicationTest
import uni.ase.assignment.parser.CodeParser

/**
 * tests the [CodeParser] class, extends the [ApplicationTest] class from testfx from https://github.com/TestFX/TestFX
 */
@DisplayName("tests the CodeParser class")
class CodeParserTest : ApplicationTest() {
    lateinit var canvas : Canvas
    lateinit var cmd : TextField
    lateinit var code : TextArea
    lateinit var out : TextArea

    lateinit var log : LogController
    lateinit var cac : CanvasController
    lateinit var coc : ConsoleController
    lateinit var cp  : CodeParser

    /**
     * starts the [stage] and makes a window so the [code] can be tested before each test
     */
    override fun start(stage: Stage) {
        canvas = Canvas()
        cmd = TextField()
        code = TextArea()
        out = TextArea()

        val scene = Scene(VBox(canvas, code, cmd, out))
        stage.scene = scene
        stage.show()

        log = LogController(out)
        cac = CanvasController(canvas.graphicsContext2D, log)
        cp  = CodeParser(code, cac, log, cmd)
        coc = ConsoleController(cmd, cac, log, cp)
    }

    /**
     * closes the window after each test
     */
    override fun stop() {
        FxToolkit.hideStage()
    }

    /**
     * tests the [run] method in the [CodeParser]
     */
    @Test
    fun testCodeParser() {
        code.text = """
                drawrect(100, 100, 100, 100)
                drawline(100, 100, 100, 100)
                drawoval(100, 100, 100, 100)
                drawpoly(100, 100, 100, 5)
                drawarc(100, 100, 100, 100, 90, 90, chord)
                drawarc(100, 100, 100, 100, 90, 90, open)
                drawarc(100, 100, 100, 100, 90, 90, round)
                stroke(red)
                stroke(#696969)
                fillcol(blue)
                fillcol(#420420)
                fill()
                drawrect(100, 100, 100, 100)
                drawline(100, 100, 100, 100)
                drawoval(100, 100, 100, 100)
                drawpoly(100, 100, 100, 5)
                drawarc(100, 100, 100, 100, 90, 90, chord)
                drawarc(100, 100, 100, 100, 90, 90, open)
                drawarc(100, 100, 100, 100, 90, 90, round)
                fill()
                clear()
                reset()
            """.trimIndent().trimMargin()

        assertDoesNotThrow { cp.run() }
    }
}