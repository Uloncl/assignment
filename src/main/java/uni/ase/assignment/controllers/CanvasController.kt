package uni.ase.assignment.controllers

import javafx.scene.canvas.GraphicsContext
import javafx.scene.paint.Color
import javafx.scene.shape.ArcType
import java.io.File

/**
 * the canvas controller that allows and controls drawing on the canvas
 * 
 * @param g the [GraphicsContext] from the canvas
 * @param log the [LogController] for writing outputs
 */
class CanvasController(val g: GraphicsContext, val log: LogController) {
    var cmdHist: MutableList<String> = mutableListOf<String>()
    var cmdIndex: Int = 0
    val cmdHistFile: File = File(System.getProperty("user.dir") +"/src/main/resources/cmdhist")

    /**
     * initializes the class with a light grey colour and reading the command history from the file where it is stored
     */
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

    /**
     * clears the canvas
     */
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

    /**
     * reset the canvas by clearing it and resetting the stroke and fill colours
     */
    fun reset() {
        clear()
        g.fill = Color.web("0xCCCCCC")
        g.stroke = Color.web("0xCCCCCC")
    }

    /**
     * for appending the [command] parameter to the command history array and file
     */
    fun cmdHistAppend(command: String) {
        cmdHist.add(cmdHist.size, command)
        cmdHistFile.appendText(command + "\n")
    }

    /**
     * draws an oval with the coordinates [x], [y] and size [h] x [w]
     */
    fun DrawOval(x: Double, y: Double, w: Double, h: Double) {
        if (fill) {
            g.fillOval(x, y, w, h)
            g.strokeOval(x, y, w, h)
        } else {
            g.strokeOval(x, y, w, h)
        }
        log.out("Oval drawn at $x, $y with size: $w x $h")
    }

    /**
     * draws a rectangle with the coordinates [x], [y] and size [h] x [w]
     */
    fun DrawRect(x: Double, y: Double, w: Double, h: Double) {
        if (fill) {
            g.fillRect(x, y, w, h)
            g.strokeRect(x, y, w, h)
        } else {
            g.strokeRect(x, y, w, h)
        }
        log.out("Rectangle drawn at $x, $y with size: $w x $h")
    }

    /**
     * draws a line from the coordinates [x1], [y2] to [x2], [y2]
     */
    fun DrawLine(x1: Double, y1: Double, x2: Double, y2: Double) {
        g.strokeLine(x1, y1, x2, y2)
        log.out("line drawn from $x1, $y1 to $x2, $y2")
    }

    /**
     * draws an arc with the coordinates [x], [y], size [h] x [w], [startAngle] which is where the arc will start,
     * [arcExtent] which is how far the arc will go, and [closure] which decides how the arc should be closed, it can
     * either be left open with only the arc being drawn, it can be a chord where the 2 ends of the arc are connected
     * directly or round where it is connected like a pizza slice where the 2 ends of the arc are connected to the center
     */
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

    /**
     * draws a polygon with the center coordinates [x], [y], radius [r] and number of sides [n]
     */
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

    /**
     * sets the colour of all lines and edges of shapes drawn with the [hex] colour passed
     */
    fun setStrokeHex(hex: String) {
        if(hex.drop(1).uppercase().matches(Regex("^[0-9A-F]+$")) && hex.length == 7) {
            g.stroke = Color.web("0x${hex.drop(1)}")
            log.out("stroke colour changed to $hex")
        } else {
            log.error("hex code invalid")
        }
    }

    /**
     * sets the [colour] of all lines and edges of shapes drawn from some preset colours
     */
    fun setStroke(color: String) {
        var color = color.lowercase()
        when(color) {
            "white" -> {
                setStrokeHex("#ffffff")
                log.out("stroke colour changed to $color")
            }
            "black" -> {
                setStrokeHex("#000000")
                log.out("stroke colour changed to $color")
            }
            "lightgray" -> {
                setStrokeHex("#bbbbbb")
                log.out("stroke colour changed to $color")
            }
            "gray" -> {
                setStrokeHex("#888888")
                log.out("stroke colour changed to $color")
            }
            "darkgray" -> {
                setStrokeHex("#444444")
                log.out("stroke colour changed to $color")
            }
            "red" -> {
                setStrokeHex("#ff0000")
                log.out("stroke colour changed to $color")
            }
            "blue" -> {
                setStrokeHex("#0000ff")
                log.out("stroke colour changed to $color")
            }
            "green" -> {
                setStrokeHex("#00ff00")
                log.out("stroke colour changed to $color")
            }
            "yellow" -> {
                setStrokeHex("#ffff00")
                log.out("stroke colour changed to $color")
            }
            "orange" -> {
                setStrokeHex("#ffa500")
                log.out("stroke colour changed to $color")
            }
            "cyan" -> {
                setStrokeHex("#00ffff")
                log.out("stroke colour changed to $color")
            }
            "purple" -> {
                setStrokeHex("#8e44ad")
                log.out("stroke colour changed to $color")
            }
            "pink" -> {
                setStrokeHex("#ff55ff")
                log.out("stroke colour changed to $color")
            }
            else -> log.error("colour preset not found")
        }
    }

    /**
     * set the colours of any shapes drawn while [fill] is true from a [hex] value
     */
    fun setFillColHex(hex: String) {
        if(hex.drop(1).uppercase().matches(Regex("^[0-9A-F]+$")) && hex.length == 7) {
            g.fill = Color.web("0x${hex.drop(1)}")
            log.out("fill colour changed to $hex")
        } else {
            log.error("hex code invalid")
        }
    }

    /**
     * sets the [colour] of any shapes drawn while [fill] is true from some preset colours
     */
    fun setFillCol(color: String) {
        var color = color.lowercase()
        when(color) {
            "white" -> {
                setFillColHex("#ffffff")
                log.out("fill colour changed to $color")
            }
            "black" -> {
                setFillColHex("#000000")
                log.out("fill colour changed to $color")
            }
            "lightgray" -> {
                setFillColHex("#bbbbbb")
                log.out("fill colour changed to $color")
            }
            "gray" -> {
                setFillColHex("#888888")
                log.out("fill colour changed to $color")
            }
            "darkgray" -> {
                setFillColHex("#444444")
                log.out("fill colour changed to $color")
            }
            "red" -> {
                setFillColHex("#ff0000")
                log.out("fill colour changed to $color")
            }
            "blue" -> {
                setFillColHex("#0000ff")
                log.out("fill colour changed to $color")
            }
            "green" -> {
                setFillColHex("#00ff00")
                log.out("fill colour changed to $color")
            }
            "yellow" -> {
                setFillColHex("#ffff00")
                log.out("fill colour changed to $color")
            }
            "orange" -> {
                setFillColHex("#ffa500")
                log.out("fill colour changed to $color")
            }
            "cyan" -> {
                setFillColHex("#00ffff")
                log.out("fill colour changed to $color")
            }
            "purple" -> {
                setFillColHex("#8e44ad")
                log.out("fill colour changed to $color")
            }
            "pink" -> {
                setFillColHex("#ff55ff")
                log.out("fill colour changed to $color")
            }
            else -> log.error("colour preset not found")
        }
    }
}