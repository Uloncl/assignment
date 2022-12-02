package uni.ase.assignment.controllers

import javafx.scene.canvas.GraphicsContext
import javafx.scene.paint.Color
import javafx.scene.shape.ArcType
import uni.ase.assignment.shapes.*
import java.io.File
import java.util.*

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
        g.fill = Color.web("#CCCCCC")
        g.stroke = Color.web("#CCCCCC")
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
        Rectangle(
            log,
            g,
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
    }

    /**
     * reset the canvas by clearing it and resetting the stroke and fill colours
     */
    fun reset() {
        clear()
        g.fill = Color.web("#CCCCCC")
        g.stroke = Color.web("#CCCCCC")
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
    fun DrawOval(params: List<String>) {
        try {
            when (params.size) {
                1 -> log.error("not enough parameters")
                2 -> log.error("not enough parameters")
                3 -> log.error("not enough parameters")
                4 -> log.error("not enough parameters")
                5 -> Oval(
                    log,
                    g,
                    x = params[1].toDouble(),
                    y = params[2].toDouble(),
                    w = params[3].toDouble(),
                    h = params[4].toDouble()
                ).draw()
                6 -> Oval(
                    log,
                    g,
                    x = params[1].toDouble(),
                    y = params[2].toDouble(),
                    w = params[3].toDouble(),
                    h = params[4].toDouble(),
                    fill = params[5].toBoolean()
                ).draw()
                7 -> Oval(
                    log,
                    g,
                    x = params[1].toDouble(),
                    y = params[2].toDouble(),
                    w = params[3].toDouble(),
                    h = params[4].toDouble(),
                    fill = params[5].toBoolean(),
                    strokeCol = Color.web(getHex(params[6]))
                ).draw()
                8 -> Oval(
                    log,
                    g,
                    x = params[1].toDouble(),
                    y = params[2].toDouble(),
                    w = params[3].toDouble(),
                    h = params[4].toDouble(),
                    fill = params[5].toBoolean(),
                    strokeCol = Color.web(getHex(params[6])),
                    fillCol = Color.web(getHex(params[7]))
                ).draw()
                else -> log.error("incorrect number of parameters")
            }
        } catch (e : Exception) {
            log.error(e.toString())
        } catch (e : java.lang.Exception) {
            log.error(e.toString())
        }
    }

    /**
     * draws an oval with the coordinates [x], [y] and size [h] x [w]
     */
    fun DrawCircle(params: List<String>) {
        try {
            when (params.size) {
                1 -> log.error("not enough parameters")
                2 -> log.error("not enough parameters")
                3 -> log.error("not enough parameters")
                4 -> Circle(
                    log,
                    g,
                    x = params[1].toDouble(),
                    y = params[2].toDouble(),
                    r = params[3].toDouble()
                ).draw()
                5 -> Circle(
                    log,
                    g,
                    x = params[1].toDouble(),
                    y = params[2].toDouble(),
                    r = params[3].toDouble(),
                    fill = params[4].toBoolean()
                ).draw()
                6 -> Circle(
                    log,
                    g,
                    x = params[1].toDouble(),
                    y = params[2].toDouble(),
                    r = params[3].toDouble(),
                    fill = params[4].toBoolean(),
                    strokeCol = Color.web(getHex(params[5]))
                ).draw()
                7 -> Circle(
                    log,
                    g,
                    x = params[1].toDouble(),
                    y = params[2].toDouble(),
                    r = params[3].toDouble(),
                    fill = params[4].toBoolean(),
                    strokeCol = Color.web(getHex(params[5])),
                    fillCol = Color.web(getHex(params[6]))
                ).draw()
                else -> log.error("incorrect number of parameters")
            }
        } catch (e : Exception) {
            log.error(e.toString())
        } catch (e : java.lang.Exception) {
            log.error(e.toString())
        }
    }

    /**
     * draws a rectangle with the coordinates [x], [y] and size [h] x [w]
     */
    fun DrawRect(params: List<String>) {
        try {
            when (params.size) {
                1 -> log.error("not enough parameters")
                2 -> log.error("not enough parameters")
                3 -> log.error("not enough parameters")
                4 -> log.error("not enough parameters")
                5 -> Rectangle(
                    log,
                    g,
                    x = params[1].toDouble(),
                    y = params[2].toDouble(),
                    w = params[3].toDouble(),
                    h = params[4].toDouble()
                ).draw()
                6 -> Rectangle(
                    log,
                    g,
                    x = params[1].toDouble(),
                    y = params[2].toDouble(),
                    w = params[3].toDouble(),
                    h = params[4].toDouble(),
                    fill = params[5].toBoolean()
                ).draw()
                7 -> Rectangle(
                    log,
                    g,
                    x = params[1].toDouble(),
                    y = params[2].toDouble(),
                    w = params[3].toDouble(),
                    h = params[4].toDouble(),
                    fill = params[5].toBoolean(),
                    strokeCol = Color.web(getHex(params[6]))
                ).draw()
                8 -> Rectangle(
                    log,
                    g,
                    x = params[1].toDouble(),
                    y = params[2].toDouble(),
                    w = params[3].toDouble(),
                    h = params[4].toDouble(),
                    fill = params[5].toBoolean(),
                    strokeCol = Color.web(getHex(params[6])),
                    fillCol = Color.web(getHex(params[7]))
                ).draw()
                9 -> Rectangle(
                    log,
                    g,
                    x = params[1].toDouble(),
                    y = params[2].toDouble(),
                    w = params[3].toDouble(),
                    h = params[4].toDouble(),
                    fill = params[5].toBoolean(),
                    strokeCol = Color.web(getHex(params[6])),
                    fillCol = Color.web(getHex(params[7])),
                    rounded = params[8].toBoolean()
                ).draw()
                10 -> Rectangle(
                    log,
                    g,
                    x = params[1].toDouble(),
                    y = params[2].toDouble(),
                    w = params[3].toDouble(),
                    h = params[4].toDouble(),
                    fill = params[5].toBoolean(),
                    strokeCol = Color.web(getHex(params[6])),
                    fillCol = Color.web(getHex(params[7])),
                    rounded = params[8].toBoolean(),
                    cornerSize = params[9].toDouble()
                ).draw()
                else -> log.error("incorrect number of parameters")
            }
        } catch (e : Exception) {
            log.error(e.toString())
        } catch (e : java.lang.Exception) {
            log.error(e.toString())
        }
    }

    /**
     * draws an oval with the coordinates [x], [y] and size [h] x [w]
     */
    fun DrawSquare(params: List<String>) {
        try {
            when (params.size) {
                1 -> log.error("not enough parameters")
                2 -> log.error("not enough parameters")
                3 -> log.error("not enough parameters")
                4 -> log.error("not enough parameters")
                5 -> Square(
                    log,
                    g,
                    x = params[1].toDouble(),
                    y = params[2].toDouble(),
                    size = params[3].toDouble(),
                    fill = params[4].toBoolean()
                ).draw()
                6 -> Square(
                    log,
                    g,
                    x = params[1].toDouble(),
                    y = params[2].toDouble(),
                    size = params[3].toDouble(),
                    fill = params[4].toBoolean(),
                    strokeCol = Color.web(getHex(params[5]))
                ).draw()
                7 -> Square(
                    log,
                    g,
                    x = params[1].toDouble(),
                    y = params[2].toDouble(),
                    size = params[3].toDouble(),
                    fill = params[4].toBoolean(),
                    strokeCol = Color.web(getHex(params[5])),
                    fillCol = Color.web(getHex(params[6])),
                ).draw()
                8 -> Square(
                    log,
                    g,
                    x = params[1].toDouble(),
                    y = params[2].toDouble(),
                    size = params[3].toDouble(),
                    fill = params[4].toBoolean(),
                    strokeCol = Color.web(getHex(params[5])),
                    fillCol = Color.web(getHex(params[6])),
                    rounded = params[7].toBoolean()
                ).draw()
                9 -> Square(
                    log,
                    g,
                    x = params[1].toDouble(),
                    y = params[2].toDouble(),
                    size = params[3].toDouble(),
                    fill = params[4].toBoolean(),
                    strokeCol = Color.web(getHex(params[5])),
                    fillCol = Color.web(getHex(params[6])),
                    rounded = params[7].toBoolean(),
                    cornerSize = params[8].toDouble()
                ).draw()
                else -> log.error("incorrect number of parameters")
            }
        } catch (e : Exception) {
            log.error(e.toString())
        } catch (e : java.lang.Exception) {
            log.error(e.toString())
        }
    }

    /**
     * draws a line from the coordinates [x1], [y2] to [x2], [y2]
     */
    fun DrawLine(params: List<String>) {
        try {
            when (params.size) {
                1 -> log.error("not enough parameters")
                2 -> log.error("not enough parameters")
                3 -> log.error("not enough parameters")
                4 -> log.error("not enough parameters")
                5 -> Line(
                    log,
                    g,
                    x1 = params[1].toDouble(),
                    y1 = params[2].toDouble(),
                    x2 = params[3].toDouble(),
                    y2 = params[4].toDouble()
                ).draw()
                6 -> Line(
                    log,
                    g,
                    x1 = params[1].toDouble(),
                    y1 = params[2].toDouble(),
                    x2 = params[3].toDouble(),
                    y2 = params[4].toDouble(),
                    strokeCol = Color.web(getHex(params[5]))
                ).draw()
                else -> log.error("incorrect number of parameters")
            }
        } catch (e : Exception) {
            log.error(e.toString())
        } catch (e : java.lang.Exception) {
            log.error(e.toString())
        }
    }

    /**
     * draws an arc with the coordinates [x], [y], size [h] x [w], [startAngle] which is where the arc will start,
     * [arcExtent] which is how far the arc will go, and [closure] which decides how the arc should be closed, it can
     * either be left open with only the arc being drawn, it can be a chord where the 2 ends of the arc are connected
     * directly or round where it is connected like a pizza slice where the 2 ends of the arc are connected to the center
     */
    fun DrawArc(params: List<String>) {
        try {
            when (params.size) {
                1 -> log.error("not enough parameters")
                2 -> log.error("not enough parameters")
                3 -> log.error("not enough parameters")
                4 -> log.error("not enough parameters")
                5 -> Arc(
                    log,
                    g,
                    x = params[1].toDouble(),
                    y = params[2].toDouble(),
                    w = params[3].toDouble(),
                    h = params[4].toDouble()
                ).draw()
                6 -> Arc(
                    log,
                    g,
                    x = params[1].toDouble(),
                    y = params[2].toDouble(),
                    w = params[3].toDouble(),
                    h = params[4].toDouble(),
                    startAngle = params[5].toDouble()
                ).draw()
                7 -> Arc(
                    log,
                    g,
                    x = params[1].toDouble(),
                    y = params[2].toDouble(),
                    w = params[3].toDouble(),
                    h = params[4].toDouble(),
                    startAngle = params[5].toDouble(),
                    endAngle = params[6].toDouble()
                ).draw()
                8 -> Arc(
                    log,
                    g,
                    x = params[1].toDouble(),
                    y = params[2].toDouble(),
                    w = params[3].toDouble(),
                    h = params[4].toDouble(),
                    startAngle = params[5].toDouble(),
                    endAngle = params[6].toDouble(),
                    closingMethod = ArcType.OPEN
                ).draw()
                9 -> Arc(
                    log,
                    g,
                    x = params[1].toDouble(),
                    y = params[2].toDouble(),
                    w = params[3].toDouble(),
                    h = params[4].toDouble(),
                    startAngle = params[5].toDouble(),
                    endAngle = params[6].toDouble(),
                    closingMethod = ArcType.OPEN,
                    strokeCol = Color.web(getHex(params[8]))
                ).draw()
                else -> log.error("incorrect number of parameters")
            }
        } catch (e : Exception) {
            log.error(e.toString())
        } catch (e : java.lang.Exception) {
            log.error(e.toString())
        }
    }

    /**
     * draws a polygon with the center coordinates [x], [y], radius [r] and number of sides [n]
     */
    fun DrawPolygon(params: List<String>) {
        try {
            when (params.size) {
                1 -> log.error("not enough parameters")
                2 -> log.error("not enough parameters")
                3 -> log.error("not enough parameters")
                4 -> log.error("not enough parameters")
                5 -> Polygon(
                    log,
                    g,
                    x = params[1].toDouble(),
                    y = params[2].toDouble(),
                    r = params[3].toDouble(),
                    n = params[4].toInt()
                ).draw()
                6 -> Polygon(
                    log,
                    g,
                    x = params[1].toDouble(),
                    y = params[2].toDouble(),
                    r = params[3].toDouble(),
                    n = params[4].toInt(),
                    preset = PolygonPreset.NONE
                ).draw()
                7 -> Polygon(
                    log,
                    g,
                    x = params[1].toDouble(),
                    y = params[2].toDouble(),
                    r = params[3].toDouble(),
                    n = params[4].toInt(),
                    preset = PolygonPreset.NONE,
                    fill = params[6].toBoolean()
                ).draw()
                8 -> Polygon(
                    log,
                    g,
                    x = params[1].toDouble(),
                    y = params[2].toDouble(),
                    r = params[3].toDouble(),
                    n = params[4].toInt(),
                    preset = PolygonPreset.NONE,
                    fill = params[6].toBoolean(),
                    strokeCol = Color.web(getHex(params[7]))
                ).draw()
                9 -> Polygon(
                    log,
                    g,
                    x = params[1].toDouble(),
                    y = params[2].toDouble(),
                    r = params[3].toDouble(),
                    n = params[4].toInt(),
                    preset = PolygonPreset.NONE,
                    fill = params[6].toBoolean(),
                    strokeCol = Color.web(getHex(params[7])),
                    fillCol = Color.web(getHex(params[8]))
                ).draw()
                else -> log.error("incorrect number of parameters")
            }
        } catch (e : Exception) {
            log.error(e.toString())
        } catch (e : java.lang.Exception) {
            log.error(e.toString())
        }
    }

    /**
     * draws an oval with the coordinates [x], [y] and size [h] x [w]
     */
    fun DrawPolyline(params : List<String>) {
        try {
            when (params.size) {
                1 -> log.error("not enough parameters")
                2 -> log.error("not enough parameters")
                3 -> log.error("not enough parameters")
                4 -> log.error("not enough parameters")
                5 -> Polyline(
                    log,
                    g,
                    x = params[1].toDouble(),
                    y = params[2].toDouble(),
                    r = params[3].toDouble(),
                    n = params[4].toInt()
                ).draw()
                6 -> Polyline(
                    log,
                    g,
                    x = params[1].toDouble(),
                    y = params[2].toDouble(),
                    r = params[3].toDouble(),
                    n = params[4].toInt(),
                    preset = PolylinePreset.NONE
                ).draw()
                7 -> Polyline(
                    log,
                    g,
                    x = params[1].toDouble(),
                    y = params[2].toDouble(),
                    r = params[3].toDouble(),
                    n = params[4].toInt(),
                    preset = PolylinePreset.NONE,
                    strokeCol = Color.web(getHex(params[6]))
                ).draw()
                else -> log.error("incorrect number of parameters")
            }
        } catch (e : Exception) {
            log.error(e.toString())
        } catch (e : java.lang.Exception) {
            log.error(e.toString())
        }
    }

    /**
     * draws an oval with the coordinates [x], [y] and size [h] x [w]
     */
    fun DrawTriangle(params : List<String>) {
        try {
            when (params.size) {
                1 -> log.error("not enough parameters")
                2 -> log.error("not enough parameters")
                3 -> log.error("not enough parameters")
                4 -> Triangle(
                    log,
                    g,
                    x = params[1].toDouble(),
                    y = params[2].toDouble(),
                    randomised = params[3].toBoolean()
                ).draw()
                5 -> Triangle(
                    log,
                    g,
                    x = params[1].toDouble(),
                    y = params[2].toDouble(),
                    randomised = params[3].toBoolean(),
                    points = mapOf(0.0 to 0.0, 0.0 to 0.0, 0.0 to 0.0)
                ).draw()
                6 -> Triangle(
                    log,
                    g,
                    x = params[1].toDouble(),
                    y = params[2].toDouble(),
                    randomised = params[3].toBoolean(),
                    points = mapOf(0.0 to 0.0, 0.0 to 0.0, 0.0 to 0.0),
                    radius = params[5].toDouble()
                ).draw()
                7 -> Triangle(
                    log,
                    g,
                    x = params[1].toDouble(),
                    y = params[2].toDouble(),
                    randomised = params[3].toBoolean(),
                    points = mapOf(0.0 to 0.0, 0.0 to 0.0, 0.0 to 0.0),
                    radius = params[5].toDouble(),
                    triType = TriType.EQUILATERAL
                ).draw()
                8 -> Triangle(
                    log,
                    g,
                    x = params[1].toDouble(),
                    y = params[2].toDouble(),
                    randomised = params[3].toBoolean(),
                    points = mapOf(0.0 to 0.0, 0.0 to 0.0, 0.0 to 0.0),
                    radius = params[5].toDouble(),
                    triType = TriType.EQUILATERAL,
                    fill = params[7].toBoolean()
                ).draw()
                9 -> Triangle(
                    log,
                    g,
                    x = params[1].toDouble(),
                    y = params[2].toDouble(),
                    randomised = params[3].toBoolean(),
                    points = mapOf(0.0 to 0.0, 0.0 to 0.0, 0.0 to 0.0),
                    radius = params[5].toDouble(),
                    triType = TriType.EQUILATERAL,
                    fill = params[7].toBoolean(),
                    strokeCol = Color.web(getHex(params[8]))
                ).draw()
                10 -> Triangle(
                    log,
                    g,
                    x = params[1].toDouble(),
                    y = params[2].toDouble(),
                    randomised = params[3].toBoolean(),
                    points = mapOf(0.0 to 0.0, 0.0 to 0.0, 0.0 to 0.0),
                    radius = params[5].toDouble(),
                    triType = TriType.EQUILATERAL,
                    fill = params[7].toBoolean(),
                    strokeCol = Color.web(getHex(params[8])),
                    fillCol = Color.web(getHex(params[9])),
                ).draw()
                else -> log.error("incorrect number of parameters")
            }
        } catch (e : Exception) {
            log.error(e.toString())
        } catch (e : java.lang.Exception) {
            log.error(e.toString())
        }
    }

    /**
     * draws an oval with the coordinates [x], [y] and size [h] x [w]
     */
    fun DrawText(params : List<String>) {
        try {
            when (params.size) {
                1 -> log.error("not enough parameters")
                2 -> log.error("not enough parameters")
                3 -> log.error("not enough parameters")
                4 -> Text(
                    log,
                    g,
                    x = params[1].toDouble(),
                    y = params[2].toDouble(),
                    text = params[3]
                ).draw()
                5 -> Text(
                    log,
                    g,
                    x = params[1].toDouble(),
                    y = params[2].toDouble(),
                    text = params[3],
                    strokeCol = Color.web(getHex(params[4]))
                ).draw()
                else -> log.error("incorrect number of parameters")
            }
        } catch (e : Exception) {
            log.error(e.toString())
        } catch (e : java.lang.Exception) {
            log.error(e.toString())
        }
    }

    /**
     * sets the [colour] of all lines and edges of shapes drawn from some preset colours
     */
    fun getHex(colour: String) : String {
        var colour = colour.lowercase()
        when(colour) {
            "white" -> {
                log.out("stroke colour changed to $colour")
                return "#ffffff"
            }
            "black" -> {
                log.out("stroke colour changed to $colour")
                return "#000000"
            }
            "lightgray" -> {
                log.out("stroke colour changed to $colour")
                return "#bbbbbb"
            }
            "gray" -> {
                log.out("stroke colour changed to $colour")
                return "#888888"
            }
            "darkgray" -> {
                log.out("stroke colour changed to $colour")
                return "#444444"
            }
            "red" -> {
                log.out("stroke colour changed to $colour")
                return "#ff0000"
            }
            "blue" -> {
                log.out("stroke colour changed to $colour")
                return "#0000ff"
            }
            "green" -> {
                log.out("stroke colour changed to $colour")
                return "#00ff00"
            }
            "yellow" -> {
                log.out("stroke colour changed to $colour")
                return "#ffff00"
            }
            "orange" -> {
                log.out("stroke colour changed to $colour")
                return "#ffa500"
            }
            "cyan" -> {
                log.out("stroke colour changed to $colour")
                return "#00ffff"
            }
            "purple" -> {
                log.out("stroke colour changed to $colour")
                return "#8e44ad"
            }
            "pink" -> {
                log.out("stroke colour changed to $colour")
                return "#ff55ff"
            }
            else -> {
                if (colour.startsWith("#")) {
                    return colour
                } else {
                    return ""
                    log.error("invalid colour")
                }
            }
        }
    }

    /**
     * sets the colour of all lines and edges of shapes drawn with the [hex] colour passed
     */
    fun setStroke(colour: String) {
        var colour = getHex(colour)
        g.stroke = Color.web(getHex(colour))
        log.out("stroke colour changed to $colour")
    }

    /**
     * set the colours of any shapes drawn while [fill] is true from a [hex] value
     */
    fun setFillCol(colour: String) {
        var colour = getHex(colour)
        g.fill = Color.web(getHex(colour))
        log.out("stroke colour changed to $colour")
    }
}