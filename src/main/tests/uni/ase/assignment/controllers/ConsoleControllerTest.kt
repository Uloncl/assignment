package uni.ase.assignment.controllers

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

internal class ConsoleControllerTest(val coc: ConsoleController) {

    @Test
    fun testConsole() : Boolean {
        try {
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
            return true
        } catch (e: java.lang.Exception) {
            return false
        } catch (e: Exception) {
            return false
        }
    }
}