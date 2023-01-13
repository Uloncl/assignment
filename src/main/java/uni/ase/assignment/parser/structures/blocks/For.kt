package uni.ase.assignment.parser.structures.blocks

import uni.ase.assignment.controllers.LogController
import uni.ase.assignment.parser.structures.Condition
import uni.ase.assignment.parser.structures.variables.IntegerVar

class For (
    block : Block,
    var counter : IntegerVar,
    var condition : Condition,
    var increment : Int
) : Structure (block) {
    fun runBlock() {
        block.parser.log.out("running for ${counter.name} = ${counter.value} ; ${condition.condition} ; ${increment}")
        block.vars.integers.add(counter)
        condition.evaluate()
        var shouldRun : Boolean = condition.outcome ?: false
        while (shouldRun) {
            block.parser.log.out("should the for loop run: $shouldRun")
            block.parseLines()
            block.vars.integers.find { it.name == counter.name }?.value?.plus(increment)
            condition.evaluate()
            shouldRun = condition.outcome ?: true
        }
    }
}