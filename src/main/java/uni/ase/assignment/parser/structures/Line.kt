package uni.ase.assignment.parser.structures

import uni.ase.assignment.controllers.LogController
import uni.ase.assignment.parser.structures.blocks.Block

class Line (
    val num : Int,
    val range: IntRange,
    val line : String,
    val type : LineType? = LineType.FUNCTION_CALL,
    var variable : Any?,
    var operation : Unit?,
    val blockState : Block?
) {
}