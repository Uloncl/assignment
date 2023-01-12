package uni.ase.assignment.parser.structures.blocks

import uni.ase.assignment.controllers.LogController
import uni.ase.assignment.parser.structures.Condition

class If (
    val block : Block,
    var condition: Condition?,
    var ifGroup : IfGroup?,
    val log : LogController
) {
}