package uni.ase.assignment.parser.structures.blocks

import uni.ase.assignment.controllers.LogController

class Function (
    val block : Block,
    var name : String,
    var parameters : List<Any>?,
    var returnType : Any?,
    val log : LogController
) {
}