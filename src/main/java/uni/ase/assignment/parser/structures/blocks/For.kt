package uni.ase.assignment.parser.structures.blocks

import uni.ase.assignment.controllers.LogController
import uni.ase.assignment.parser.structures.Condition
import uni.ase.assignment.parser.structures.variables.IntegerVar

class For (
    val block : Block,
    var counter : IntegerVar,
    var condition : Condition,
    var increment : Int,
    val log : LogController
) {
}