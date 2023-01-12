package uni.ase.assignment.parser.structures.blocks

import uni.ase.assignment.controllers.LogController

class IfGroup(
    val ifs: MutableList<If>,
    val log : LogController
) {
}