package uni.ase.assignment.parser.structures.variables

import uni.ase.assignment.parser.structures.blocks.Block

class DoubleVar (
    name : String,
    var value: Double,
    mutable : Boolean,
    scope: Block
) : Variable(name, mutable, scope) {
}