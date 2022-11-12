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

/**
 * tests the [ConsoleController] class, extends the [ApplicationTest] class from testfx from https://github.com/TestFX/TestFX
 */
@DisplayName("tests the ConsoleController class")
class ConsoleControllerTest : ApplicationTest() {
    lateinit var canvas : Canvas
    lateinit var cmd : TextField
    lateinit var code : TextArea
    lateinit var out : TextArea

    lateinit var log : LogController
    lateinit var cac : CanvasController
    lateinit var coc : ConsoleController
    lateinit var cp  : CodeParser

    /**
     * starts the [stage] and makes a window so the [cmd] can be tested before each test
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
     * tests the [run] method in the [ConsoleController] class
     */
    @Test
    fun testConsole() {
        assertDoesNotThrow {
            coc.run("drawrect 100 100 100 100")
            coc.run("drawline 100 100 100 100")
            coc.run("drawoval 100 100 100 100")
            coc.run("drawpoly 100 100 100 5")
            coc.run("drawarc 100 100 100 100 90 90 chord")
            coc.run("drawarc 100 100 100 100 90 90 open")
            coc.run("drawarc 100 100 100 100 90 90 round")

            coc.run("stroke red")
            coc.run("stroke #696969")
            coc.run("fillcol red")
            coc.run("fillcol #420420")
            coc.run("fill")
            coc.run("drawrect 100 100 100 100")
            coc.run("drawline 100 100 100 100")
            coc.run("drawoval 100 100 100 100")
            coc.run("drawpoly 100 100 100 5")
            coc.run("drawarc 100 100 100 100 90 90 chord")
            coc.run("drawarc 100 100 100 100 90 90 open")
            coc.run("drawarc 100 100 100 100 90 90 round")
            coc.run("fill")

            coc.run("clear")
            coc.run("reset")

            for (i in 1..50) {
                coc.cmdNext()
            }
            for (i in 1..50) {
                coc.cmdLast()
            }
        }
    }
}