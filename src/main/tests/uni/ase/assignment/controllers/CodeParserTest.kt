package uni.ase.assignment.controllers

import javafx.scene.control.TextArea
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

internal class CodeParserTest (val cp: CodeParser, val code: TextArea) {

    @Test
    fun testCodeParser(): Boolean {
        try {
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
            cp.run()
            return true
        } catch (e: java.lang.Exception) {
            return false
        } catch (e: Exception) {
            return false
        }
    }
}