package uni.ase.assignment.parser.structures

import uni.ase.assignment.controllers.LogController

class Condition (
    val condition : String,
    var outcome: Boolean?,
    val log : LogController
    ) {
    fun evaluate() {
        val condition = Regex("(?<function>[A-Z]\\w+\\((?<parameters>.+)\\))|(?<left>.+?)\\s*==\\s*(?<right>.+)|(?<boolean>true|false)").find(condition)!!.groups as? MatchNamedGroupCollection
        if (condition?.get("function") != null) {

        } else if (condition?.get("left") != null && condition?.get("right") != null) {
            if (condition?.get("left")?.value?.matches(Regex("(?<function>[A-Z]\\w+\\((?<parameters>.+)\\))")) == true) {

            } else if (condition?.get("right")?.value?.matches(Regex("(?<function>[A-Z]\\w+\\((?<parameters>.+)\\))")) == true) {

            } else if (condition?.get("left")?.value?.matches(Regex("(?<function>[A-Z]\\w+\\((?<parameters>.+)\\))")) == true && condition?.get("right")?.value?.matches(Regex("(?<function>[A-Z]\\w+\\((?<parameters>.+)\\))")) == true) {

            } else {
                outcome = condition?.get("left")?.value == condition?.get("right")?.value
            }
        } else if (condition?.get("boolean") != null) {

        }
    }
}