package uni.ase.assignment.parser.structures.blocks

import uni.ase.assignment.controllers.LogController
import uni.ase.assignment.parser.structures.Condition

class While (
    val block : Block,
    var condition : Condition,
    val log : LogController
) {
    fun runBlock() {
        condition.evaluate()
        var shouldRun : Boolean = condition.outcome ?: false
        while (shouldRun) {
            block.parseLines()
        }
    }
}