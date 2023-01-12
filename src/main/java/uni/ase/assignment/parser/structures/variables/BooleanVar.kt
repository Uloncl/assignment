package uni.ase.assignment.parser.structures.variables

import uni.ase.assignment.controllers.LogController
import uni.ase.assignment.parser.structures.blocks.Block

class BooleanVar (
    name : String,
    var value: Boolean,
    mutable : Boolean,
    scope: Block,
    log : LogController
) : Variable(name, mutable, scope, log) {
}