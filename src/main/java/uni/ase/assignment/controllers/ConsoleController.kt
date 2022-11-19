package uni.ase.assignment.controllers

import javafx.scene.control.TextField
import javafx.scene.paint.Color
import uni.ase.assignment.shapes.Rectangle
import java.util.*

/**
 * a controller for controlling the console, used for processing commands and accessing the command history from the
 * [Canvas]
 *
 * @param cmd the [TextField] where commands are entered
 * @param cc the [CanvasController] where the command history is stored
 * @param log the [LogController] used for outputting messages and errors to the log
 */
class ConsoleController (val cmd: TextField, val cc: CanvasController, val log: LogController, val cp: CodeParser) {
    /**
     * if there is a command stored ahead of the current [cmdIndex] in the [cmdHist] array and puts it in the [cmd] [TextField]
     */
    fun cmdLast() {
        if (cc.cmdIndex > 0 && cc.cmdHist.isNotEmpty()) {
            cmd.text = cc.cmdHist[cc.cmdIndex - 1]
            cc.cmdIndex -= 1
        } else if (cc.cmdIndex == 0 && cc.cmdHist.isNotEmpty()) {
            cmd.text = cc.cmdHist[0]
        }
    }

    /**
     * if there is a command stored behind of the current [cmdIndex] in the [cmdHist] array and puts it in the [cmd] [TextField]
     */
    fun cmdNext() {
        if(cc.cmdIndex < cc.cmdHist.size-1 && cc.cmdHist.isNotEmpty()) {
            cmd.text = cc.cmdHist[cc.cmdIndex + 1]
            cc.cmdIndex += 1
        }
    }

    /**
     * runs a command either from the [custcmd] parameter or from the [cmd] [TextField] if [custcmd] is empty, splits the
     * command by spaces and takes the first value from the array and uses it as the name of the command to find which
     * function to run and uses the rest of the values in the array as the parameters for the function
     */
    fun run(custcmd: String) {
        var cmdstr: String = if(custcmd == "") cmd.getText() else custcmd
        System.out.println("$cmdstr")
        val cmdarr: List<String> = cmdstr.split(Regex("[^a-zA-Z0-9]")).filter { !it.isBlank() }
        System.out.println("${cmdarr.size}")
        cmdarr.forEach { System.out.println("$it") }
        cc.cmdHistAppend(cmdstr)
        cc.cmdIndex = cc.cmdHist.size
        when {
            cmdstr.startsWith("oval")      -> cc.DrawOval(cmdarr)
            cmdstr.startsWith("circle")    -> cc.DrawCircle(cmdarr)
            cmdstr.startsWith("rect")      -> cc.DrawRect(cmdarr)
            cmdstr.startsWith("square")    -> cc.DrawSquare(cmdarr)
            cmdstr.startsWith("line")      -> cc.DrawLine(cmdarr)
            cmdstr.startsWith("arc")       -> cc.DrawArc(cmdarr)
            cmdstr.startsWith("polygon")   -> cc.DrawPolygon(cmdarr)
            cmdstr.startsWith("polyline")  -> cc.DrawPolyline(cmdarr)
            cmdstr.startsWith("triangle")  -> cc.DrawTriangle(cmdarr)
            cmdstr.startsWith("text")      -> cc.DrawText(cmdarr)
            cmdstr.startsWith("pen")       -> {
                try {
                    if(cmdarr[1].startsWith("#")){
                        cc.setStroke(cmdarr[1].trimStart())
                        cc.setFillCol(cmdarr[1].trimStart())
                    } else {
                        cc.setStroke(cmdarr[1])
                        cc.setFillCol(cmdarr[1])
                    }
                } catch (e: Exception) {
                    log.error(e.toString())
                } finally {
                    cmd.clear()
                }
            }
            cmdstr.startsWith("stroke") -> {
                try {
                    if(cmdarr[1].startsWith("#")){
                        cc.setStroke(cmdarr[1].trimStart())
                    } else {
                        cc.setStroke(cmdarr[1])
                    }
                } catch (e: Exception) {
                    log.error(e.toString())
                } finally {
                    cmd.clear()
                }
            }
            cmdstr.startsWith("fillcol") -> {
                try {
                    if(cmdarr[1].startsWith("#")){
                        cc.setFillCol(cmdarr[1].trimStart())
                    } else {
                        cc.setFillCol(cmdarr[1])
                    }
                } catch (e: Exception) {
                    log.error(e.toString())
                } finally {
                    cmd.clear()
                }
            }
            cmdstr.startsWith("fill") -> {
                cc.fill = !cc.fill
            }
            cmdstr.startsWith("clear") -> {
                Rectangle(
                    log,
                    cc.g,
                    0.0,
                    0.0,
                    10000.0,
                    10000.0,
                    false,
                    0.0,
                    true,
                    Color.web("#333333"),
                    Color.web("#333333")
                ).draw()
                cmd.clear()
            }
            cmdstr.startsWith("reset") -> {
                run("clear")
                cc.g.fill = Color.web("0xcccccc")
                cc.g.stroke = Color.web("0xcccccc")
                cc.fill = false
            }
            cmdstr.startsWith("clearlog") -> log.clear()
            cmdstr.startsWith("run") -> cp.run()
             else -> {
                log.error("command not recognised")
            }
        }
    }
}