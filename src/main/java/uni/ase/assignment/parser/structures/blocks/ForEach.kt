package uni.ase.assignment.parser.structures.blocks

import uni.ase.assignment.controllers.LogController

class ForEach (
    val block : Block?,
    var collection : Any?,
    var element : Any,
    val log : LogController
) {
}