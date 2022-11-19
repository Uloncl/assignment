package uni.ase.assignment.shapes

import javafx.scene.canvas.GraphicsContext
import javafx.scene.paint.Color
import uni.ase.assignment.controllers.LogController

class Polyline(
    log : LogController,
    g: GraphicsContext,
    x : Double = 0.0,
    y : Double = 0.0,
    var r : Double = 0.0,
    var n : Int = 3,
    var preset: PolylinePreset = PolylinePreset.NONE,
    var strokeCol: Color? = null
) : Shape(log, g, x, y) {
    override var out : String = "Polyline drawn at $x, $y"
    protected var xp = mutableListOf<Double>()
    protected var yp = mutableListOf<Double>()

    protected fun generatePoints() {
        var i = 0
        while (i < n) {
            xp.add(i, x + (r * Math.cos(2 * Math.PI * i / n)))
            yp.add(i, y + (r * Math.sin(2 * Math.PI * i / n)))
            i++
        }
    }

    override fun draw() {
        generatePoints()
        val oldStrokeCol = g.stroke
        if (strokeCol != null) {
            g.stroke = strokeCol
        }
        g.strokePolyline(xp.toDoubleArray(), yp.toDoubleArray(), n)
        if (strokeCol != null) {
            out = "$out stroke colour: $strokeCol"
            g.stroke = oldStrokeCol
        }
        log.out(out)
    }
}

enum class PolylinePreset {
    NONE,
    TRIANGLE,
    SQUARE,
    PENTAGON,
    HEXAGON,
    SEPTAGON,
    OCTAGON,
    NONAGON,
    DECAGON,
    HENDECAGON,
    DODECAGON,
    FIVE_POINTED_STAR,
    SIX_POINTED_STAR
}