package uni.ase.assignment.controllers

import javafx.scene.canvas.GraphicsContext
import javafx.scene.paint.Color
import javafx.scene.shape.ArcType
import java.io.File

class CanvasController(val g: GraphicsContext, val log: LogController) {
    var cmdHist: MutableList<String> = mutableListOf<String>()
    var cmdIndex: Int = 0
    val cmdHistFile: File = File(System.getProperty("user.dir") +"/src/main/resources/cmdhist")

    init {
        g.fill = Color.web("0xCCCCCC")
        g.stroke = Color.web("0xCCCCCC")
        cmdHistFile.forEachLine { cmdHist.add(it) }
        cmdHist.add("")
        cmdIndex = cmdHist.size-1
    }

    var fill: Boolean = false
        get() = field
        set(value) {
            log.out("fill = $value")
            field = value
        }

    fun clear() {
        fill = true
        var olfill = g.fill
        var olstroke = g.stroke
        g.fill = Color.web("0x333333")
        g.stroke = Color.web("0x333333")
        DrawRect(0.0, 0.0, 10000.0, 10000.0)
        g.fill = olfill
        g.stroke = olstroke
    }

    fun reset() {
        clear()
        g.fill = Color.web("0xCCCCCC")
        g.stroke = Color.web("0xCCCCCC")
    }

    fun cmdHistAppend(command: String) {
        cmdHist.add(cmdHist.size, command)
        cmdHistFile.appendText(command + "\n")
    }

    fun DrawOval(x: Double, y: Double, w: Double, h: Double) {
        if (fill) {
            g.fillOval(x, y, w, h)
            g.strokeOval(x, y, w, h)
        } else {
            g.strokeOval(x, y, w, h)
        }
        log.out("Oval drawn at $x, $y with size: $w x $h")
    }

    fun DrawRect(x: Double, y: Double, w: Double, h: Double) {
        if (fill) {
            g.fillRect(x, y, w, h)
            g.strokeRect(x, y, w, h)
        } else {
            g.strokeRect(x, y, w, h)
        }
        log.out("Rectangle drawn at $x, $y with size: $w x $h")
    }

    fun DrawLine(x1: Double, y1: Double, x2: Double, y2: Double) {
        g.strokeLine(x1, y1, x2, y2)
        log.out("line drawn from $x1, $y1 to $x2, $y2")
    }

    fun DrawArc(x: Double, y: Double, w: Double, h: Double, startAngle: Double, arcExtent: Double, closure: String) {
        var arcType: ArcType? = when (closure) {
            "chord" -> ArcType.CHORD
            "open" -> ArcType.OPEN
            "round" -> ArcType.ROUND
            else -> null
        }
        g.strokeArc(x, y, w, h, startAngle, arcExtent, arcType)
        log.out("Arc drawn at $x, $y size: $h x $w start angle: $startAngle arc extent: $arcExtent closure: $closure")
    }

    fun DrawPoly(x: Double, y: Double, r: Double, n: Double) {
        log.out("")
        val xp = mutableListOf<Double>()
        val yp = mutableListOf<Double>()
        var i = 0
        while (i < n) {
            xp.add(i, x + (r * Math.cos(2 * Math.PI * i / n)))
            yp.add(i, y + (r * Math.sin(2 * Math.PI * i / n)))
            i++
        }
        if (fill) {
            g.fillPolygon(xp.toDoubleArray(), yp.toDoubleArray(), n.toInt())
            g.strokePolygon(xp.toDoubleArray(), yp.toDoubleArray(), n.toInt())
        } else {
            g.strokePolygon(xp.toDoubleArray(), yp.toDoubleArray(), n.toInt())
        }
        log.out("polygon drawn at $x, $y with a radius to vertices of $r and $n sides")
    }

    fun setStroke(color: Color) {
        g.stroke = color
        log.out("stroke colour changed to $color")
    }
    fun setStrokeHex(hex: String) {
        if(hex.drop(1).uppercase().matches(Regex("^[0-9A-F]+$")) && hex.length == 7) {
            g.stroke = Color.web("0x${hex.drop(1)}")
            log.out("stroke colour changed to $hex")
        } else {
            log.error("hex code invalid")
        }
    }
    fun setStroke(color: String) {
        var color = color.lowercase()
        when(color) {
            "white" -> {
                g.stroke = Color.WHITE
                log.out("stroke colour changed to $color")
            }
            "black" -> {
                g.stroke = Color.BLACK
                log.out("stroke colour changed to $color")
            }
            "lightgray" -> {
                g.stroke = Color.LIGHTGRAY
                log.out("stroke colour changed to $color")
            }
            "gray" -> {
                g.stroke = Color.GRAY
                log.out("stroke colour changed to $color")
            }
            "darkgray" -> {
                g.stroke = Color.DARKGRAY
                log.out("stroke colour changed to $color")
            }
            "red" -> {
                g.stroke = Color.RED
                log.out("stroke colour changed to $color")
            }
            "blue" -> {
                g.stroke = Color.BLUE
                log.out("stroke colour changed to $color")
            }
            "green" -> {
                g.stroke = Color.GREEN
                log.out("stroke colour changed to $color")
            }
            "yellow" -> {
                g.stroke = Color.YELLOW
                log.out("stroke colour changed to $color")
            }
            "orange" -> {
                g.stroke = Color.ORANGE
                log.out("stroke colour changed to $color")
            }
            "cyan" -> {
                g.stroke = Color.CYAN
                log.out("stroke colour changed to $color")
            }
            else -> log.error("colour preset not found")
        }
    }

    fun setFill(color: Color) {
        g.fill = color
        log.out("fill colour changed to $color")
    }
    fun setFillHex(hex: String) {
        if(hex.drop(1).uppercase().matches(Regex("^[0-9A-F]+$")) && hex.length == 7) {
            g.fill = Color.web("0x${hex.drop(1)}")
            log.out("fill colour changed to $hex")
        } else {
            log.error("hex code invalid")
        }
    }
    fun setFill(color: String) {
        var color = color.lowercase()
        when(color) {
            "white" -> {
                g.fill = Color.WHITE
                log.out("fill colour changed to $color")
            }
            "black" -> {
                g.fill = Color.BLACK
                log.out("fill colour changed to $color")
            }
            "lightgray" -> {
                g.fill = Color.LIGHTGRAY
                log.out("fill colour changed to $color")
            }
            "gray" -> {
                g.fill = Color.GRAY
                log.out("fill colour changed to $color")
            }
            "darkgray" -> {
                g.fill = Color.DARKGRAY
                log.out("fill colour changed to $color")
            }
            "red" -> {
                g.fill = Color.RED
                log.out("fill colour changed to $color")
            }
            "blue" -> {
                g.fill = Color.BLUE
                log.out("fill colour changed to $color")
            }
            "green" -> {
                g.fill = Color.GREEN
                log.out("fill colour changed to $color")
            }
            "yellow" -> {
                g.fill = Color.YELLOW
                log.out("fill colour changed to $color")
            }
            "orange" -> {
                g.fill = Color.ORANGE
                log.out("fill colour changed to $color")
            }
            "cyan" -> {
                g.fill = Color.CYAN
                log.out("fill colour changed to $color")
            }
            else -> log.error("colour preset not found")
        }
    }
}