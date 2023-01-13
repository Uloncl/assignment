package uni.ase.assignment.parser.structures.variables

import uni.ase.assignment.parser.structures.blocks.Block

class IntegerVar (
    name : String,
    var value: Int,
    mutable : Boolean,
    scope: Block
) : Variable(name, mutable, scope) {
}