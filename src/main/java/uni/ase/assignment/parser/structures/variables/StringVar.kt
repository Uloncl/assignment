package uni.ase.assignment.parser.structures.variables

import uni.ase.assignment.parser.structures.blocks.Block

class StringVar (
    name : String,
    var value: String,
    mutable : Boolean,
    scope: Block
) : Variable(name, mutable, scope) {
}