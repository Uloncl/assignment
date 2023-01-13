package uni.ase.assignment.parser.structures.blocks

import uni.ase.assignment.controllers.LogController
import uni.ase.assignment.parser.structures.Condition

class While (
    block : Block,
    var condition : Condition
) : Structure (block) {
    fun runBlock() {
        block.parser.log.out("running while loop")
        condition.evaluate()
        block.parser.log.out("while condition = ${condition.outcome}")
        var shouldRun : Boolean = condition.outcome ?: false
        while (shouldRun) {
            block.parseLines()
            condition.evaluate()
            shouldRun = condition.outcome ?: true
        }
    }
}