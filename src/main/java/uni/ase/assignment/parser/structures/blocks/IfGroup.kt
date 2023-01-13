package uni.ase.assignment.parser.structures.blocks

import uni.ase.assignment.controllers.LogController

class IfGroup(
    val ifs: MutableList<If>
) {
    fun run() {
        ifs.forEach { it.condition?.evaluate() }
        if (ifs.first { it.condition?.outcome == true } != null) {
            ifs.first { it.condition?.outcome == true }.block.parseLines()
        } else if (ifs.first { it.condition == null } != null) {
            ifs.firstOrNull { it.condition == null }?.block?.parseLines()
        }
    }
}