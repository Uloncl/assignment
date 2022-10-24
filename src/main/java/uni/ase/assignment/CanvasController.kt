package uni.ase.assignment

import javafx.scene.canvas.GraphicsContext
import javafx.scene.paint.Color
import javafx.scene.paint.Paint
import javafx.scene.shape.ArcType

class CanvasController (val g: GraphicsContext) {
    var cmdHist: MutableList<String> = mutableListOf<String>()
    init {
        g.fill = Color.web("0xcccccc")
        g.stroke = Color.web("0xcccccc")
    }

    fun cmdHistAppend (command: String) = cmdHist.add(cmdHist.size, command)

    fun DrawOval(x: Double, y: Double, width: Double, height: Double) = g.strokeOval(x, y, width, height)
    fun DrawRect(x: Double, y: Double, width: Double, height: Double) = g.strokeRect(x, y, width, height)
    fun DrawLine(x1: Double, y1: Double, x2: Double, y2: Double) = g.strokeLine(x1, y1, x2, y2)

    fun DrawArc (x: Double, y: Double, w: Double, h: Double, startAngle: Double, arcExtent: Double, closure: String) {
        var arcType: ArcType? = when(closure) {
            "chord" -> ArcType.CHORD
            "open"  -> ArcType.OPEN
            "round" -> ArcType.ROUND
            else -> null
        }
        g.strokeArc(x, y, w, h, startAngle, arcExtent, arcType)
    }

    fun DrawPoly(x: Double, y: Double, r: Double, n: Double) {
        val xp = mutableListOf<Double>()
        val yp = mutableListOf<Double>()
        var i = 0
        while (i < n) {
            xp.add(i, x + (r * Math.cos(2 * Math.PI * i / n)))
            yp.add(i, y + (r * Math.sin(2 * Math.PI * i / n)))
            i++
        }
        g.strokePolygon(xp.toDoubleArray(), yp.toDoubleArray(), n.toInt())
    }
}