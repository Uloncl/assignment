package uni.ase.assignment.parser.structures.variables

import uni.ase.assignment.parser.CodeParser
import uni.ase.assignment.parser.structures.blocks.Block

class Variables (
    var strings : MutableList<StringVar>,
    var integers : MutableList<IntegerVar>,
    var doubles : MutableList<DoubleVar>,
    var booleans : MutableList<BooleanVar>,
    var arrays : MutableList<ArrayVar>,
    var maps : MutableList<MapVar>,
    val parser : CodeParser
) {
    fun hasVar (name : String) : List<Variable>? {
        var all : List<Variable> = mutableListOf(strings, integers, doubles, booleans, arrays, maps).flatten()
        return all.filter { it.name == name }
    }

    fun update (vars : Variables) {
        vars.strings.forEach { a -> strings.find { b -> b.name == a.name }?.value = a.value }
        vars.integers.forEach { a -> integers.find { b -> b.name == a.name }?.value = a.value }
        vars.doubles.forEach { a -> doubles.find { b -> b.name == a.name }?.value = a.value }
        vars.booleans.forEach { a -> booleans.find { b -> b.name == a.name }?.value = a.value }
        vars.arrays.forEach { a -> arrays.find { b -> b.name == a.name }?.array = a.array }
        vars.maps.forEach { a -> maps.find { b -> b.name == a.name }?.map = a.map }
    }
}