package uni.ase.assignment.shapes

import javafx.scene.canvas.GraphicsContext
import uni.ase.assignment.controllers.LogController

class Polygon(
    log : LogController,
    g: GraphicsContext,
    x : Int = 0,
    y : Int = 0,
    var r : Int = 0,
    var n : Int = 3,
    var preset : PolygonPreset = PolygonPreset.NONE
) : Shape(log, g, x, y) {
    init {
        if (n < 3) {
            log.error("number of sides cannot be less than 3")
        }
    }
    override fun draw() {
        TODO("Not yet implemented")
    }
}

enum class PolygonPreset {
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