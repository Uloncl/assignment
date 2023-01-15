package uni.ase.assignment.parser.structures.blocks

import uni.ase.assignment.controllers.LogController
import uni.ase.assignment.parser.structures.Condition

class While (
    block : Block,
    var condition : Condition
) : Structure (block) {
    fun runBlock() {
        println("running while loop")
        condition.evaluate()
        var shouldRun : Boolean = condition.outcome ?: false
        while (shouldRun) {
            println("while (${condition.outcome})")
            block.parseLines()
            condition.evaluate()
            shouldRun = condition.outcome ?: true
        }
    }
}