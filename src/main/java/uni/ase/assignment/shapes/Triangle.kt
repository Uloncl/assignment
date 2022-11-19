package uni.ase.assignment.shapes

import javafx.scene.canvas.GraphicsContext
import javafx.scene.paint.Color
import uni.ase.assignment.controllers.LogController

// https://d138zd1ktt9iqe.cloudfront.net/media/seo_landing_files/classification-of-triangles-1621331800.png

class Triangle(
    log : LogController,
    g: GraphicsContext,
    x : Double = 0.0,
    y : Double = 0.0,
    var points : Map<Double, Double>? = mapOf(0.0 to 0.0, 0.0 to 0.0, 0.0 to 0.0),
    var randomised : Boolean = true,
    var radius : Double = 0.0,
    var triType : TriType = TriType.EQUILATERAL,
    var fill: Boolean? = false,
    var strokeCol: Color? = null,
    var fillCol: Color? = null
) : Shape(log, g, x, y) {
    override var out : String = "Triangle drawn at $x, $y"
    override fun draw() {
        val oldStrokeCol = g.stroke
        val oldFillCol = g.fill
        if (strokeCol != null) {
            g.stroke = strokeCol
        }
        if (fillCol != null) {
            g.fill = fillCol
        }
        if (fill == true) {
            out = "filled $out"
            g.fillPolygon(points!!.keys.toDoubleArray(), points!!.values.toDoubleArray(), 3)
        }
        g.strokePolygon(points!!.keys.toDoubleArray(), points!!.values.toDoubleArray(), 3)
        if (strokeCol != null) {
            out = "$out stroke colour: $strokeCol"
            g.stroke = oldStrokeCol
        }
        if (fillCol != null) {
            out = "$out fill colour: $fillCol"
            g.fill = oldFillCol
        }
        log.out(out)
    }
}

enum class TriType {
    EQUILATERAL,
    ISOCELES,
    SCALENE, // https://stackoverflow.com/questions/14829621/formula-to-find-points-on-the-circumference-of-a-circle-given-the-center-of-the
    ACUTE,
    RIGHT_ANGLE,
    OBTUSE
}