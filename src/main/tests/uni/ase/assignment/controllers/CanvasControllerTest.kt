package uni.ase.assignment.controllers

import javafx.scene.Scene
import javafx.scene.SnapshotParameters
import javafx.scene.canvas.Canvas
import javafx.scene.control.TextArea
import javafx.scene.control.TextField
import javafx.scene.image.WritableImage
import javafx.scene.layout.VBox
import javafx.scene.paint.Color
import javafx.stage.Stage
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.testfx.api.FxToolkit
import org.testfx.framework.junit5.ApplicationTest
import java.awt.image.RenderedImage

import javax.swing.SwingUtilities

/**
 * tests the [CanvasController] class, extends the [ApplicationTest] class from testfx from https://github.com/TestFX/TestFX
 */
@DisplayName("tests the CanvasController class")
class CanvasControllerTest : ApplicationTest(){
    lateinit var canvas : Canvas
    lateinit var out : TextArea

    lateinit var log : LogController
    lateinit var cac : CanvasController

    /**
     * starts the [stage] and makes a window so the [canvas] can be tested before each test
     */
    override fun start(stage: Stage){
        canvas = Canvas()
        out = TextArea()

        val scene = Scene(VBox(canvas, out))
        stage.scene = scene
        stage.show()

        log = LogController(out)
        cac = CanvasController(canvas.graphicsContext2D, log)
    }

    /**
     * closes the window after each test
     */
    override fun stop(){
        FxToolkit.hideStage()
    }

    /**
     * tests drawing a rectangle
     */
    @Test
    @DisplayName("tests DrawRect function")
    fun testDrawRect(){
        assertDoesNotThrow { cac.DrawRect(100.0, 100.0, 100.0, 100.0) }
    }

    /**
     *  tests clearing the canvas
     */
    @Test
    @DisplayName("tests Clear function")
    fun testClear(){
        assertDoesNotThrow { cac.clear() }
    }

    /**
     * tests resetting the canvas
     */
    @Test
    @DisplayName("tests Reset function")
    fun testReset(){
        assertDoesNotThrow { cac.reset() }
    }

    /**
     * tests setting the stroke colour
     */
    @Test
    @DisplayName("tests SetStrokeColour function")
    fun testSetStrokeColour(){
        assertDoesNotThrow {
            cac.setStroke("red")
            cac.setStrokeHex("#696969")
        }
        cac.reset()
    }

    /**
     * tests setting the fill colour
     */
    @Test
    @DisplayName("test SetFillColour function")
    fun testSetFillColour(){
        assertDoesNotThrow {
            cac.setFillCol("red")
            cac.setFillColHex("#420420")
        }
        cac.reset()
    }

    /**
     * tests using fill to draw filled shapes
     */
    @Test
    @DisplayName("tests SetFill function")
    fun testSetFill(){
        assertDoesNotThrow {
            cac.fill = true
            cac.DrawRect(100.0, 100.0, 100.0, 100.0)
            cac.fill = false
            cac.DrawRect(200.0, 200.0, 100.0, 100.0)
        }
        cac.reset()
    }

    /**
     * tests drawing a line
     */
    @Test
    @DisplayName("tests DrawLine function")
    fun testDrawLine(){
        assertDoesNotThrow { cac.DrawLine(100.0, 100.0, 200.0, 200.0) }
        cac.reset()
    }

    /**
     * tests drawing a polygon
     */
    @Test
    @DisplayName("tests DrawPoly function")
    fun testDrawPoly(){
        assertDoesNotThrow { cac.DrawPoly(200.0, 200.0, 100.0, 3.0) }
        cac.reset()
    }

    /**
     * tests drawing an oval
     */
    @Test
    @DisplayName("tests DrawOval function")
    fun testDrawOval(){
        assertDoesNotThrow { cac.DrawOval(100.0, 100.0, 100.0, 100.0) }
        cac.reset()
    }

    /**
     * tests drawing an arc
     */
    @Test
    @DisplayName("tests DrawArc function")
    fun testDrawArc(){
        assertDoesNotThrow {
            cac.DrawArc(200.0, 200.0, 100.0, 100.0, 90.0, 90.0, "chord")
            cac.DrawArc(200.0, 200.0, 100.0, 100.0, 90.0, 90.0, "open")
            cac.DrawArc(200.0, 200.0, 100.0, 100.0, 90.0, 90.0, "round")
        }
        cac.reset()
    }
}