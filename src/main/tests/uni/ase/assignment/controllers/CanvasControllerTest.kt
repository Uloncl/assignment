package uni.ase.assignment.controllers

import javafx.scene.SnapshotParameters
import javafx.scene.canvas.Canvas
import javafx.scene.image.WritableImage
import javafx.scene.paint.Color
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import java.awt.image.RenderedImage

import javax.swing.SwingUtilities

internal class CanvasControllerTest (val cac: CanvasController) {
    val sp: SnapshotParameters = SnapshotParameters()
    val si: WritableImage = WritableImage(1, 1)

    @Test
    fun testDrawRect() : Boolean {
        try {
            cac.DrawRect(100.0, 100.0, 100.0, 100.0)
            return true
        } catch (e: java.lang.Exception) {
            return false
        } catch (e: Exception) {
            return false
        }
    }

    @Test
    fun testClear() : Boolean {
        try {
            cac.clear()
            return true
        } catch (e: java.lang.Exception) {
            return false
        } catch (e: Exception) {
            return false
        }
    }

    @Test
    fun testReset() : Boolean {
        try {
            cac.reset()
            return true
        } catch (e: java.lang.Exception) {
            return false
        } catch (e: Exception) {
            return false
        }
    }

    @Test
    fun testSetStrokeColour() : Boolean {
        try {
            cac.setStroke("red")
            cac.setStrokeHex("#696969")
            return true
        } catch (e: java.lang.Exception) {
            return false
        } catch (e: Exception) {
            return false
        }
        cac.reset()
    }

    @Test
    fun testSetFillColour() : Boolean {
        try {
            cac.setFillCol("red")
            cac.setFillColHex("#420420")
            return true
        } catch (e: java.lang.Exception) {
            return false
        } catch (e: Exception) {
            return false
        }
        cac.reset()
    }

    @Test
    fun testSetFill() : Boolean {
        try {
            cac.fill = true
            cac.DrawRect(100.0, 100.0, 100.0, 100.0)
            cac.fill = false
            cac.DrawRect(200.0, 200.0, 100.0, 100.0)
            return true
        } catch (e: java.lang.Exception) {
            return false
        } catch (e: Exception) {
            return false
        }
        cac.reset()
    }

    @Test
    fun testDrawLine() : Boolean {
        try {
            cac.DrawLine(100.0, 100.0, 200.0, 200.0)
            return true
        } catch (e: java.lang.Exception) {
            return false
        } catch (e: Exception) {
            return false
        }
        cac.reset()
    }

    @Test
    fun testDrawPoly() : Boolean {
        try {
            cac.DrawPoly(200.0, 200.0, 100.0, 3.0)
            return true
        } catch (e: java.lang.Exception) {
            return false
        } catch (e: Exception) {
            return false
        }
        cac.reset()
    }

    @Test
    fun testDrawOval() : Boolean {
        try {
            cac.DrawOval(100.0, 100.0, 100.0, 100.0)
            return true
        } catch (e: java.lang.Exception) {
            return false
        } catch (e: Exception) {
            return false
        }
        cac.reset()
    }

    @Test
    fun testDrawArc() : Boolean {
        try {
            cac.DrawArc(200.0, 200.0, 100.0, 100.0, 90.0, 90.0, "chord")
            cac.DrawArc(200.0, 200.0, 100.0, 100.0, 90.0, 90.0, "open")
            cac.DrawArc(200.0, 200.0, 100.0, 100.0, 90.0, 90.0, "round")
            return true
        } catch (e: java.lang.Exception) {
            return false
        } catch (e: Exception) {
            return false
        }
        cac.reset()
    }
}