package uni.ase.assignment.parser.structures.blocks

import uni.ase.assignment.controllers.LogController
import uni.ase.assignment.parser.structures.variables.*

class ForEach (
    block : Block,
    var collection : Variable,
    var element : String
) : Structure (block) {
    fun runBlock() {
        when {
            collection is ArrayVar && (collection as ArrayVar).array is MutableList<*> -> {
                ((collection as ArrayVar).array as MutableList<String>).forEach { e ->
                    when {
                        e.matches(Regex("(?<bool>true|false)")) -> {
                            if (block.vars.booleans.find { it.name == element } != null) block.vars.booleans.find { it.name == element }!!.value = e.toBoolean() else block.vars.booleans.add(BooleanVar(element, e.toBoolean(), true, block))
                        }
                        e.matches(Regex("(\\\"(?<stringa>[^\\\"]+)\\\"|\\'(?<stringb>[^\\']+)\\')")) -> {
                            if (block.vars.strings.find { it.name == element } != null) block.vars.strings.find { it.name == element }!!.value = e else block.vars.strings.add(StringVar(element, e, true, block))
                        }
                        e.matches(Regex("(?<double>\\d+\\.\\d+)")) -> {
                            if (block.vars.doubles.find { it.name == element } != null) block.vars.doubles.find { it.name == element }!!.value = e.toDouble() else block.vars.doubles.add(DoubleVar(element, e.toDouble(), true, block))
                        }
                        e.matches(Regex("(?<int>\\d+)")) -> {
                            if (block.vars.integers.find { it.name == element } != null) block.vars.integers.find { it.name == element }!!.value = e.toInt() else block.vars.integers.add(
                                IntegerVar(element, e.toInt(), true, block)
                            )
                        }
                        else -> {
                        }
                    }
                }
            }
            else -> {
                block.parser.log.error("invalid collection")
            }
        }
    }
}