package uni.ase.assignment.parser.structures.variables

import uni.ase.assignment.parser.structures.blocks.Block

open class Variable (
    val name : String,
    val mutable : Boolean,
    var scope: Block
) {
}