package uni.ase.assignment.shapes

import javafx.scene.canvas.GraphicsContext
import uni.ase.assignment.controllers.LogController

// https://d138zd1ktt9iqe.cloudfront.net/media/seo_landing_files/classification-of-triangles-1621331800.png

class Triangle(
    log : LogController,
    g: GraphicsContext,
    var randomised : Boolean = true,
    x : Int = 0,
    y : Int = 0,
    var points : Map<Int, Int> = mapOf(0 to 0, 0 to 0, 0 to 0),
    var radius : Int = 0,
    var triType : TriType = TriType.EQUILATERAL
) : Shape(log, g, x, y) {
    override fun draw() {
        TODO("Not yet implemented")
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