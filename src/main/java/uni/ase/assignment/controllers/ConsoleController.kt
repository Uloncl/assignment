package uni.ase.assignment.controllers

import javafx.scene.control.TextField
import javafx.scene.paint.Color
import java.util.*

/**
 * a controller for controlling the console, used for processing commands and accessing the command history from the
 * [Canvas]
 *
 * @param cmd the [TextField] where commands are entered
 * @param cc the [CanvasController] where the command history is stored
 * @param log the [LogController] used for outputting messages and errors to the log
 */
class ConsoleController (val cmd: TextField, val cc: CanvasController, val log: LogController) {
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
        cmdstr = cmdstr.lowercase()
        cmdstr = cmdstr.replace("  ", " ")
        cmdstr = cmdstr.replace("(", "")
        cmdstr = cmdstr.replace(")", "")
        val cmdarr: List<String> = cmdstr.split(" ")
        cc.cmdHistAppend(cmdarr.joinToString(separator = " "))
        cc.cmdIndex = cc.cmdHist.size
        when (cmdarr[0]) {
            "drawoval" -> {
                try {
                    cc.DrawOval(cmdarr[1].toDouble(), cmdarr[2].toDouble(), cmdarr[3].toDouble(), cmdarr[4].toDouble())
                } catch (e: Exception) {
                    log.error(e.toString())
                } finally {
                    cmd.clear()
                }
            }
            "drawrect" -> {
                try {
                    cc.DrawRect(cmdarr[1].toDouble(), cmdarr[2].toDouble(), cmdarr[3].toDouble(), cmdarr[4].toDouble())
                } catch (e: Exception) {
                    log.error(e.toString())
                } finally {
                    cmd.clear()
                }
            }
            "drawline" -> {
                try {
                    cc.DrawLine(cmdarr[1].toDouble(), cmdarr[2].toDouble(), cmdarr[3].toDouble(), cmdarr[4].toDouble())
                } catch (e: Exception) {
                    log.error(e.toString())
                } finally {
                    cmd.clear()
                }
            }
            "drawarc" -> {
                try {
                    cc.DrawArc(cmdarr[1].toDouble(), cmdarr[2].toDouble(), cmdarr[3].toDouble(), cmdarr[4].toDouble(), cmdarr[5].toDouble(), cmdarr[6].toDouble(), cmdarr[7])
                } catch (e: Exception) {
                    log.error(e.toString())
                } finally {
                    cmd.clear()
                }
            }
            "drawpoly" -> {
                try {
                    cc.DrawPoly(cmdarr[1].toDouble(), cmdarr[2].toDouble(), cmdarr[3].toDouble(), cmdarr[4].toDouble())
                } catch (e: Exception) {
                    log.error(e.toString())
                } finally {
                    cmd.clear()
                }
            }
            "pen" -> {
                try {
                    if(cmdarr[1].startsWith("#")){
                        cc.setStrokeHex(cmdarr[1].trimStart())
                        cc.setFillColHex(cmdarr[1].trimStart())
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
            "stroke" -> {
                try {
                    if(cmdarr[1].startsWith("#")){
                        cc.setStrokeHex(cmdarr[1].trimStart())
                    } else {
                        cc.setStroke(cmdarr[1])
                    }
                } catch (e: Exception) {
                    log.error(e.toString())
                } finally {
                    cmd.clear()
                }
            }
            "fillcol" -> {
                try {
                    if(cmdarr[1].startsWith("#")){
                        cc.setFillColHex(cmdarr[1].trimStart())
                    } else {
                        cc.setFillCol(cmdarr[1])
                    }
                } catch (e: Exception) {
                    log.error(e.toString())
                } finally {
                    cmd.clear()
                }
            }
            "fill" -> {
                cc.fill = !cc.fill
            }
            "clear" -> {
                var olfill = cc.fill
                var olcol = cc.g.fill
                cc.fill = true
                cc.g.fill = Color.web("0x333333")
                cc.DrawRect(0.0, 0.0, 10000.0, 10000.0)
                cc.g.fill = olcol
                cc.fill = olfill
                cmd.clear()
            }
            "reset" -> {
                run("clear")
                cc.g.fill = Color.web("0xcccccc")
                cc.g.stroke = Color.web("0xcccccc")
                cc.fill = false
            }
            "clearlog" -> log.clear()
             else -> {
                log.error("command not recognised")
            }
        }
    }
}