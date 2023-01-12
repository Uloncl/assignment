package uni.ase.assignment.parser.structures.variables

import uni.ase.assignment.controllers.LogController

class Variables (
    var strings : MutableList<StringVar>,
    var integers : MutableList<IntegerVar>,
    var doubles : MutableList<DoubleVar>,
    var booleans : MutableList<BooleanVar>,
    var arrays : MutableList<ArrayVar>,
    var maps : MutableList<MapVar>,
    val log : LogController
) {
    fun hasVar (name : String) : List<Variable>? {
        var all : List<Variable> = mutableListOf(strings, integers, doubles, booleans, arrays, maps).flatten()
        return all.filter { it.name == name }
    }
}