package uni.ase.assignment.controllers

import javafx.scene.control.TextField

class ConsoleController (val cmd: TextField, val cc: CanvasController, val log: LogController) {
    fun run() {
        val cmdarr: List<String> = cmd.getText().split(" ")
        when (cmdarr[0]) {
            "drawOval" -> {
                try {
                    cc.DrawOval(cmdarr[1].toDouble(), cmdarr[2].toDouble(), cmdarr[3].toDouble(), cmdarr[4].toDouble())
                } catch (e: Exception) {
                    log.error(e.toString())
                } finally {
                    cmd.clear()
                }
            }
            "drawRect" -> {
                try {
                    cc.DrawRect(cmdarr[1].toDouble(), cmdarr[2].toDouble(), cmdarr[3].toDouble(), cmdarr[4].toDouble())
                } catch (e: Exception) {
                    log.error(e.toString())
                } finally {
                    cmd.clear()
                }
            }
            "drawLine" -> {
                try {
                    cc.DrawLine(cmdarr[1].toDouble(), cmdarr[2].toDouble(), cmdarr[3].toDouble(), cmdarr[4].toDouble())
                } catch (e: Exception) {
                    log.error(e.toString())
                } finally {
                    cmd.clear()
                }
            }
            "drawArc" -> {
                try {
                    cc.DrawArc(cmdarr[1].toDouble(), cmdarr[2].toDouble(), cmdarr[3].toDouble(), cmdarr[4].toDouble(), cmdarr[5].toDouble(), cmdarr[6].toDouble(), cmdarr[7])
                } catch (e: Exception) {
                    log.error(e.toString())
                } finally {
                    cmd.clear()
                }
            }
            "drawPoly" -> {
                try {
                    cc.DrawPoly(cmdarr[1].toDouble(), cmdarr[2].toDouble(), cmdarr[3].toDouble(), cmdarr[4].toDouble())
                } catch (e: Exception) {
                    log.error(e.toString())
                } finally {
                    cmd.clear()
                }
            }
            else -> {
                log.error("command not recognised")
            }
        }
    }
}