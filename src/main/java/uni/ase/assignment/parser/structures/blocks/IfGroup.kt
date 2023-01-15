package uni.ase.assignment.parser.structures.blocks

import uni.ase.assignment.controllers.LogController

class IfGroup(
    val ifs: MutableList<If>
) {
    fun run() {
        ifs.forEach { it.condition?.evaluate(); it.block.parser.log.out("${it.condition?.condition ?: ""}") }
        if (ifs.firstOrNull { it.condition?.outcome == true } != null) {
            ifs.firstOrNull { it.condition?.outcome == true }?.block?.parseLines()
        } else if (ifs.firstOrNull { it.condition == null } != null) {
            ifs.firstOrNull { it.condition == null }?.block?.parseLines()
        }
    }
}