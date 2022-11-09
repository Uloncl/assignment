package uni.ase.assignment

import javafx.fxml.FXMLLoader
import javafx.scene.Scene
import javafx.scene.canvas.Canvas
import javafx.scene.control.TextArea
import javafx.scene.control.TextField
import javafx.scene.layout.VBox
import javafx.stage.Stage
import org.fxmisc.richtext.CodeArea
import org.junit.jupiter.api.*
import org.junit.jupiter.api.extension.ExtendWith
import org.reactfx.util.LL.Cons
import uni.ase.assignment.controllers.*
import org.testfx.framework.junit5.ApplicationExtension
import org.testfx.framework.junit5.Start

@ExtendWith(ApplicationExtension::class)
internal class AssignmentTest {
    lateinit var canvas: Canvas
    lateinit var cmd: TextField
    lateinit var code: TextArea
    lateinit var out: TextArea
    lateinit var log: LogController
    lateinit var cac: CanvasController
    lateinit var coc: ConsoleController
    lateinit var cp: CodeParser

    lateinit var scene: Scene

    @Start
    public fun start(stage: Stage) {
        canvas = Canvas()
        cmd    = TextField()
        code   = TextArea("")
        out    = TextArea("")

        log = LogController(out)
        cac = CanvasController(canvas.graphicsContext2D, log)
        coc = ConsoleController(cmd, cac, log)
        cp  =  CodeParser(code, cac, log, coc)

        scene = Scene(VBox(canvas, code, cmd, out))
        stage.scene = scene
        stage.show()
    }

    @Test
    @DisplayName("1. tests the entire LogController class")
    fun testLogController() {
        val testlogs: LogControllerTest = LogControllerTest(log)
        assert(testlogs.testError())
        assert(testlogs.testOut())
        assert(testlogs.testClear())
    }

    @Test
    @DisplayName("2. tests the entire CanvasController class")
    fun testCanvasController() {
        val testCanvas: CanvasControllerTest = CanvasControllerTest(cac)
        assert(testCanvas.testDrawRect())
        assert(testCanvas.testClear())
        assert(testCanvas.testReset())
        assert(testCanvas.testSetStrokeColour())
        assert(testCanvas.testSetFillColour())
        assert(testCanvas.testSetFill())
        assert(testCanvas.testDrawLine())
        assert(testCanvas.testDrawPoly())
        assert(testCanvas.testDrawOval())
        assert(testCanvas.testDrawArc())
    }

    @Test
    @DisplayName("3. tests the entire ConsoleController class")
    fun testConsoleController() {
        val testConsole: ConsoleControllerTest = ConsoleControllerTest(coc)
        assert(testConsole.testConsole())
    }

    @Test
    @DisplayName("4. tests the entire CodeParser class")
    fun testCodeParser() {
        val testCodeParser: CodeParserTest = CodeParserTest(cp, code)
        assert(testCodeParser.testCodeParser())
    }
}