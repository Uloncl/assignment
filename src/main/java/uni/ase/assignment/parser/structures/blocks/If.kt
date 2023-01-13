package uni.ase.assignment.parser.structures.blocks

import uni.ase.assignment.controllers.LogController
import uni.ase.assignment.parser.structures.Condition

class If (
    block : Block,
    var condition: Condition?,
    var ifGroup : IfGroup
) : Structure (block) {
}