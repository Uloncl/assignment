package uni.ase.assignment.parser.structures

import uni.ase.assignment.parser.CodeParser
import uni.ase.assignment.parser.structures.blocks.Block
import uni.ase.assignment.parser.structures.blocks.BlockType
import uni.ase.assignment.parser.structures.variables.BooleanVar
import uni.ase.assignment.parser.structures.variables.DoubleVar
import uni.ase.assignment.parser.structures.variables.IntegerVar
import uni.ase.assignment.parser.structures.variables.StringVar

class Condition (
    val condition : String,
    var outcome: Boolean?,
    val scope : Block,
    val parser : CodeParser
    ) {
    fun evaluate() {
        if (condition.contains("&&")) {
            outcome = condition.split("&&").map {
                val condition = Regex("(?<left>.+?)\\s*(?<operator>==|<=|>=|<|>|!=)\\s*(?<right>.+)|(?<function>(?<name>[A-Z]\\w+)\\((?<parameters>.+)\\))|(?<boolean>true|false)").find(condition)!!.groups as? MatchNamedGroupCollection
                if (condition?.get("left") != null && condition?.get("right") != null) {
                    if (condition?.get("left")?.value?.matches(Regex("(?<function>[A-Z]\\w+\\((?<parameters>.+)\\))")) == true && condition?.get(
                            "right"
                        )?.value?.matches(Regex("(?<function>[A-Z]\\w+\\((?<parameters>.+)\\))")) == false
                    ) {
                        val leftFunc = Regex("(?<function>[A-Z]\\w+\\((?<parameters>.+)\\))").find(
                            condition?.get("left")?.value ?: ""
                        )!!.groups as? MatchNamedGroupCollection
                        var function = parser.allCode.children.filter { child ->
                            child.type == BlockType.FUNCTION
                        }.filter { child ->
                            (child.structure as uni.ase.assignment.parser.structures.blocks.Function)?.name == leftFunc?.get(
                                "name"
                            )?.value
                        }.first().structure as uni.ase.assignment.parser.structures.blocks.Function
                        var leftfunc = function.run(leftFunc?.get("parameters")?.value ?: "")
                        var rightvar = scope.findInScope(condition?.get("left")?.value ?: "")?.firstOrNull()
                        if (rightvar == null) {
                            when {
                                leftfunc is StringVar -> {
                                    when {
                                        condition?.get("operator")?.value == "==" -> {
                                            return@map leftfunc.value == condition?.get("right")?.value
                                        }
                                        condition?.get("operator")?.value == "!=" -> {
                                            return@map leftfunc.value != condition?.get("right")?.value
                                        }
                                        else -> {
                                            return@map null
                                        }
                                    }
                                }
                                leftfunc is IntegerVar -> {
                                    when {
                                        condition?.get("operator")?.value == "==" -> {
                                            return@map leftfunc.value == (condition?.get("right")?.value?.toInt() ?: leftfunc.value - 1)
                                        }
                                        condition?.get("operator")?.value == "<=" -> {
                                            return@map leftfunc.value <= (condition?.get("right")?.value?.toInt() ?: leftfunc.value + 1)
                                        }
                                        condition?.get("operator")?.value == ">=" -> {
                                            return@map leftfunc.value >= (condition?.get("right")?.value?.toInt() ?: leftfunc.value - 1)
                                        }
                                        condition?.get("operator")?.value == "<" -> {
                                            return@map leftfunc.value < (condition?.get("right")?.value?.toInt() ?: leftfunc.value + 1)
                                        }
                                        condition?.get("operator")?.value == ">" -> {
                                            return@map leftfunc.value > (condition?.get("right")?.value?.toInt() ?: leftfunc.value - 1)
                                        }
                                        condition?.get("operator")?.value == "!=" -> {
                                            return@map leftfunc.value != (condition?.get("right")?.value?.toInt() ?: leftfunc.value)
                                        }
                                        else -> {
                                            return@map null
                                        }
                                    }
                                }
                                leftfunc is DoubleVar -> {
                                    when {
                                        condition?.get("operator")?.value == "==" -> {
                                            return@map leftfunc.value == (condition?.get("right")?.value?.toDouble() ?: leftfunc.value - 1)
                                        }
                                        condition?.get("operator")?.value == "<=" -> {
                                            return@map leftfunc.value <= (condition?.get("right")?.value?.toDouble() ?: leftfunc.value + 1)
                                        }
                                        condition?.get("operator")?.value == ">=" -> {
                                            return@map leftfunc.value >= (condition?.get("right")?.value?.toDouble() ?: leftfunc.value - 1)
                                        }
                                        condition?.get("operator")?.value == "<" -> {
                                            return@map leftfunc.value < (condition?.get("right")?.value?.toDouble() ?: leftfunc.value + 1)
                                        }
                                        condition?.get("operator")?.value == ">" -> {
                                            return@map leftfunc.value > (condition?.get("right")?.value?.toDouble() ?: leftfunc.value - 1)
                                        }
                                        condition?.get("operator")?.value == "!=" -> {
                                            return@map leftfunc.value != (condition?.get("right")?.value?.toDouble() ?: leftfunc.value)
                                        }
                                        else -> {
                                            return@map null
                                        }
                                    }
                                }
                                leftfunc is BooleanVar -> {
                                    when {
                                        condition?.get("operator")?.value == "==" -> {
                                            return@map leftfunc.value == condition?.get("right")?.value.toBoolean()
                                        }
                                        condition?.get("operator")?.value == "!=" -> {
                                            return@map leftfunc.value != condition?.get("right")?.value.toBoolean()
                                        }
                                        else -> {
                                            return@map null
                                        }
                                    }
                                }
                                else -> {
                                    return@map null
                                }
                            }
                        } else {
                            when {
                                leftfunc is StringVar && rightvar is StringVar -> {
                                    when {
                                        condition?.get("operator")?.value == "==" -> {
                                            return@map leftfunc.value == rightvar.value
                                        }
                                        condition?.get("operator")?.value == "!=" -> {
                                            return@map leftfunc.value != rightvar.value
                                        }
                                        else -> {
                                            return@map null
                                        }
                                    }
                                }
                                leftfunc is IntegerVar -> {
                                    when {
                                        condition?.get("operator")?.value == "==" -> {
                                            return@map leftfunc.value == (rightvar as IntegerVar).value
                                        }
                                        condition?.get("operator")?.value == "<=" -> {
                                            return@map leftfunc.value <= (rightvar as IntegerVar).value
                                        }
                                        condition?.get("operator")?.value == ">=" -> {
                                            return@map leftfunc.value >= (rightvar as IntegerVar).value
                                        }
                                        condition?.get("operator")?.value == "<" -> {
                                            return@map leftfunc.value < (rightvar as IntegerVar).value
                                        }
                                        condition?.get("operator")?.value == ">" -> {
                                            return@map leftfunc.value > (rightvar as IntegerVar).value
                                        }
                                        condition?.get("operator")?.value == "!=" -> {
                                            return@map leftfunc.value != (rightvar as IntegerVar).value
                                        }
                                        else -> {
                                            return@map null
                                        }
                                    }
                                }
                                leftfunc is DoubleVar -> {
                                    when {
                                        condition?.get("operator")?.value == "==" -> {
                                            return@map leftfunc.value == (rightvar as DoubleVar).value
                                        }
                                        condition?.get("operator")?.value == "<=" -> {
                                            return@map leftfunc.value <= (rightvar as DoubleVar).value
                                        }
                                        condition?.get("operator")?.value == ">=" -> {
                                            return@map leftfunc.value >= (rightvar as DoubleVar).value
                                        }
                                        condition?.get("operator")?.value == "<" -> {
                                            return@map leftfunc.value < (rightvar as DoubleVar).value
                                        }
                                        condition?.get("operator")?.value == ">" -> {
                                            return@map leftfunc.value > (rightvar as DoubleVar).value
                                        }
                                        condition?.get("operator")?.value == "!=" -> {
                                            return@map leftfunc.value != (rightvar as DoubleVar).value
                                        }
                                        else -> {
                                            return@map null
                                        }
                                    }
                                }
                                leftfunc is BooleanVar -> {
                                    when {
                                        condition?.get("operator")?.value == "==" -> {
                                            return@map leftfunc.value == (rightvar as BooleanVar).value
                                        }
                                        condition?.get("operator")?.value == "!=" -> {
                                            return@map leftfunc.value != (rightvar as BooleanVar).value
                                        }
                                        else -> {
                                            return@map null
                                        }
                                    }
                                }
                                else -> {
                                    return@map null
                                }
                            }
                        }
                    } else if (condition?.get("left")?.value?.matches(Regex("(?<function>[A-Z]\\w+\\((?<parameters>.+)\\))")) == false && condition?.get(
                            "right"
                        )?.value?.matches(Regex("(?<function>[A-Z]\\w+\\((?<parameters>.+)\\))")) == true
                    ) {
                        val rightFunc = Regex("(?<function>[A-Z]\\w+\\((?<parameters>.+)\\))").find(
                            condition?.get("right")?.value ?: ""
                        )!!.groups as? MatchNamedGroupCollection
                        var function = parser.allCode.children.filter { child ->
                            child.type == BlockType.FUNCTION
                        }.filter { child ->
                            (child.structure as uni.ase.assignment.parser.structures.blocks.Function)?.name == rightFunc?.get(
                                "name"
                            )?.value
                        }.first().structure as uni.ase.assignment.parser.structures.blocks.Function
                        var rightfunc = function.run(rightFunc?.get("parameters")?.value ?: "")
                        var leftvar = scope.findInScope(condition?.get("left")?.value ?: "")?.firstOrNull()
                        if (leftvar == null) {
                            when {
                                rightfunc is StringVar -> {
                                    when {
                                        condition?.get("operator")?.value == "==" -> {
                                            return@map condition?.get("left")?.value == rightfunc.value
                                        }
                                        condition?.get("operator")?.value == "!=" -> {
                                            return@map condition?.get("left")?.value != rightfunc.value
                                        }
                                        else -> {
                                            return@map null
                                        }
                                    }
                                }
                                rightfunc is IntegerVar -> {
                                    when {
                                        condition?.get("operator")?.value == "==" -> {
                                            return@map (condition?.get("left")?.value?.toInt() ?: rightfunc.value - 1) == rightfunc.value
                                        }
                                        condition?.get("operator")?.value == "<=" -> {
                                            return@map (condition?.get("left")?.value?.toInt() ?: rightfunc.value + 1) <= rightfunc.value
                                        }
                                        condition?.get("operator")?.value == ">=" -> {
                                            return@map (condition?.get("left")?.value?.toInt() ?: rightfunc.value - 1) >= rightfunc.value
                                        }
                                        condition?.get("operator")?.value == "<" -> {
                                            return@map (condition?.get("left")?.value?.toInt() ?: rightfunc.value + 1) < rightfunc.value
                                        }
                                        condition?.get("operator")?.value == ">" -> {
                                            return@map (condition?.get("left")?.value?.toInt() ?: rightfunc.value - 1) > rightfunc.value
                                        }
                                        condition?.get("operator")?.value == "!=" -> {
                                            return@map (condition?.get("left")?.value?.toInt() ?: rightfunc.value) != rightfunc.value
                                        }
                                        else -> {
                                            return@map null
                                        }
                                    }
                                }
                                rightfunc is DoubleVar -> {
                                    when {
                                        condition?.get("operator")?.value == "==" -> {
                                            return@map (condition?.get("right")?.value?.toDouble() ?: rightfunc.value - 1) == rightfunc.value
                                        }
                                        condition?.get("operator")?.value == "<=" -> {
                                            return@map (condition?.get("right")?.value?.toDouble() ?: rightfunc.value + 1) <= rightfunc.value
                                        }
                                        condition?.get("operator")?.value == ">=" -> {
                                            return@map (condition?.get("right")?.value?.toDouble() ?: rightfunc.value - 1) >= rightfunc.value
                                        }
                                        condition?.get("operator")?.value == "<" -> {
                                            return@map (condition?.get("right")?.value?.toDouble() ?: rightfunc.value + 1) < rightfunc.value
                                        }
                                        condition?.get("operator")?.value == ">" -> {
                                            return@map (condition?.get("right")?.value?.toDouble() ?: rightfunc.value - 1) > rightfunc.value
                                        }
                                        condition?.get("operator")?.value == "!=" -> {
                                            return@map (condition?.get("right")?.value?.toDouble()
                                                ?: rightfunc.value) != rightfunc.value
                                        }
                                        else -> {
                                            return@map null
                                        }
                                    }
                                }
                                rightfunc is BooleanVar -> {
                                    when {
                                        condition?.get("operator")?.value == "==" -> {
                                            return@map condition?.get("left")?.value.toBoolean() == rightfunc.value
                                        }
                                        condition?.get("operator")?.value == "!=" -> {
                                            return@map condition?.get("left")?.value.toBoolean() != rightfunc.value
                                        }
                                        else -> {
                                            return@map null
                                        }
                                    }
                                }
                                else -> {
                                    return@map null
                                }
                            }
                        } else {
                            when {
                                rightfunc is StringVar && leftvar is StringVar -> {
                                    when {
                                        condition?.get("operator")?.value == "==" -> {
                                            return@map leftvar.value == rightfunc.value
                                        }
                                        condition?.get("operator")?.value == "!=" -> {
                                            return@map leftvar.value != rightfunc.value
                                        }
                                        else -> {
                                            return@map null
                                        }
                                    }
                                }
                                rightfunc is IntegerVar -> {
                                    when {
                                        condition?.get("operator")?.value == "==" -> {
                                            return@map (leftvar as IntegerVar).value == rightfunc.value
                                        }
                                        condition?.get("operator")?.value == "<=" -> {
                                            return@map (leftvar as IntegerVar).value <= rightfunc.value
                                        }
                                        condition?.get("operator")?.value == ">=" -> {
                                            return@map (leftvar as IntegerVar).value >= rightfunc.value
                                        }
                                        condition?.get("operator")?.value == "<" -> {
                                            return@map (leftvar as IntegerVar).value < rightfunc.value
                                        }
                                        condition?.get("operator")?.value == ">" -> {
                                            return@map (leftvar as IntegerVar).value > rightfunc.value
                                        }
                                        condition?.get("operator")?.value == "!=" -> {
                                            return@map (leftvar as IntegerVar).value != rightfunc.value
                                        }
                                        else -> {
                                            return@map null
                                        }
                                    }
                                }
                                rightfunc is DoubleVar -> {
                                    when {
                                        condition?.get("operator")?.value == "==" -> {
                                            return@map (leftvar as DoubleVar).value == rightfunc.value
                                        }
                                        condition?.get("operator")?.value == "<=" -> {
                                            return@map (leftvar as DoubleVar).value <= rightfunc.value
                                        }
                                        condition?.get("operator")?.value == ">=" -> {
                                            return@map (leftvar as DoubleVar).value >= rightfunc.value
                                        }
                                        condition?.get("operator")?.value == "<" -> {
                                            return@map (leftvar as DoubleVar).value < rightfunc.value
                                        }
                                        condition?.get("operator")?.value == ">" -> {
                                            return@map (leftvar as DoubleVar).value > rightfunc.value
                                        }
                                        condition?.get("operator")?.value == "!=" -> {
                                            return@map (leftvar as DoubleVar).value != rightfunc.value
                                        }
                                        else -> {
                                            return@map null
                                        }
                                    }
                                }
                                rightfunc is BooleanVar -> {
                                    when {
                                        condition?.get("operator")?.value == "==" -> {
                                            return@map (leftvar as BooleanVar).value == rightfunc.value
                                        }
                                        condition?.get("operator")?.value == "!=" -> {
                                            return@map (leftvar as BooleanVar).value != rightfunc.value
                                        }
                                        else -> {
                                            return@map null
                                        }
                                    }
                                }
                                else -> {
                                    return@map null
                                }
                            }
                        }
                    } else if (condition?.get("left")?.value?.matches(Regex("(?<function>[A-Z]\\w+\\((?<parameters>.+)\\))")) == true && condition?.get(
                            "right"
                        )?.value?.matches(Regex("(?<function>[A-Z]\\w+\\((?<parameters>.+)\\))")) == true
                    ) {
                        val leftFunc = Regex("(?<function>[A-Z]\\w+\\((?<parameters>.+)\\))").find(
                            condition?.get("right")?.value ?: ""
                        )!!.groups as? MatchNamedGroupCollection
                        var lfunction = parser.allCode.children.filter { child ->
                            child.type == BlockType.FUNCTION
                        }.filter { child ->
                            (child.structure as uni.ase.assignment.parser.structures.blocks.Function)?.name == leftFunc?.get(
                                "name"
                            )?.value
                        }.first().structure as uni.ase.assignment.parser.structures.blocks.Function
                        var lfuncRes = lfunction.run(leftFunc?.get("parameters")?.value ?: "")
                        val rightFunc = Regex("(?<function>[A-Z]\\w+\\((?<parameters>.+)\\))").find(
                            condition?.get("right")?.value ?: ""
                        )!!.groups as? MatchNamedGroupCollection
                        var rfunction = parser.allCode.children.filter { child ->
                            child.type == BlockType.FUNCTION
                        }.filter { child ->
                            (child.structure as uni.ase.assignment.parser.structures.blocks.Function)?.name == rightFunc?.get(
                                "name"
                            )?.value
                        }.first().structure as uni.ase.assignment.parser.structures.blocks.Function
                        var rfuncRes = rfunction.run(rightFunc?.get("parameters")?.value ?: "")
                        when {
                            lfuncRes is StringVar && rfuncRes is StringVar -> {
                                when {
                                    condition?.get("operator")?.value == "==" -> {
                                        return@map lfuncRes.value == rfuncRes.value
                                    }
                                    condition?.get("operator")?.value == "!=" -> {
                                        return@map lfuncRes.value != rfuncRes.value
                                    }
                                    else -> {
                                        return@map null
                                    }
                                }
                            }
                            lfuncRes is IntegerVar && rfuncRes is IntegerVar -> {
                                when {
                                    condition?.get("operator")?.value == "==" -> {
                                        return@map lfuncRes.value == rfuncRes.value
                                    }
                                    condition?.get("operator")?.value == "<=" -> {
                                        return@map lfuncRes.value <= rfuncRes.value
                                    }
                                    condition?.get("operator")?.value == ">=" -> {
                                        return@map lfuncRes.value >= rfuncRes.value
                                    }
                                    condition?.get("operator")?.value == "<" -> {
                                        return@map lfuncRes.value < rfuncRes.value
                                    }
                                    condition?.get("operator")?.value == ">" -> {
                                        return@map lfuncRes.value > rfuncRes.value
                                    }
                                    condition?.get("operator")?.value == "!=" -> {
                                        return@map lfuncRes.value != rfuncRes.value
                                    }
                                    else -> {
                                        return@map null
                                    }
                                }
                            }
                            lfuncRes is DoubleVar && rfuncRes is DoubleVar -> {
                                when {
                                    condition?.get("operator")?.value == "==" -> {
                                        return@map lfuncRes.value == rfuncRes.value
                                    }
                                    condition?.get("operator")?.value == "<=" -> {
                                        return@map lfuncRes.value <= rfuncRes.value
                                    }
                                    condition?.get("operator")?.value == ">=" -> {
                                        return@map lfuncRes.value >= rfuncRes.value
                                    }
                                    condition?.get("operator")?.value == "<" -> {
                                        return@map lfuncRes.value < rfuncRes.value
                                    }
                                    condition?.get("operator")?.value == ">" -> {
                                        return@map lfuncRes.value > rfuncRes.value
                                    }
                                    condition?.get("operator")?.value == "!=" -> {
                                        return@map lfuncRes.value != rfuncRes.value
                                    }
                                    else -> {
                                        return@map null
                                    }
                                }
                            }
                            lfuncRes is BooleanVar && rfuncRes is BooleanVar -> {
                                when {
                                    condition?.get("operator")?.value == "==" -> {
                                        return@map lfuncRes.value == rfuncRes.value
                                    }
                                    condition?.get("operator")?.value == "!=" -> {
                                        return@map lfuncRes.value != rfuncRes.value
                                    }
                                    else -> {
                                        return@map null
                                    }
                                }
                            }
                            else -> {
                                return@map null
                            }
                        }
                    } else {
                        var left = condition?.get("left")?.value
                        var right = condition?.get("right")?.value
                        var leftvar = scope.findInScope(left ?: "")?.firstOrNull()
                        var rightvar = scope.findInScope(right ?: "")?.firstOrNull()
                        when {
                            leftvar != null && rightvar != null -> {
                                when {
                                    leftvar is BooleanVar -> {
                                        when {
                                            rightvar is BooleanVar -> {
                                                when {
                                                    condition?.get("operator")?.value == "==" -> {
                                                        return@map leftvar.value == rightvar.value
                                                    }
                                                    condition?.get("operator")?.value == "!=" -> {
                                                        return@map leftvar.value != rightvar.value
                                                    }
                                                    else -> {
                                                        return@map null
                                                    }
                                                }
                                            }
                                            else -> {
                                                return@map null
                                            }
                                        }
                                    }
                                    leftvar is StringVar -> {
                                        when {
                                            rightvar is StringVar -> {
                                                when {
                                                    condition?.get("operator")?.value == "==" -> {
                                                        return@map leftvar.value == rightvar.value
                                                    }
                                                    condition?.get("operator")?.value == "!=" -> {
                                                        return@map leftvar.value != rightvar.value
                                                    }
                                                    else -> {
                                                        return@map null
                                                    }
                                                }
                                            }
                                            else -> {
                                                return@map null
                                            }
                                        }
                                    }
                                    leftvar is DoubleVar -> {
                                        when {
                                            rightvar is DoubleVar -> {
                                                when {
                                                    condition?.get("operator")?.value == "==" -> {
                                                        return@map leftvar.value == rightvar.value
                                                    }
                                                    condition?.get("operator")?.value == "<=" -> {
                                                        return@map leftvar.value <= rightvar.value
                                                    }
                                                    condition?.get("operator")?.value == ">=" -> {
                                                        return@map leftvar.value >= rightvar.value
                                                    }
                                                    condition?.get("operator")?.value == "<" -> {
                                                        return@map leftvar.value < rightvar.value
                                                    }
                                                    condition?.get("operator")?.value == ">" -> {
                                                        return@map leftvar.value > rightvar.value
                                                    }
                                                    condition?.get("operator")?.value == "!=" -> {
                                                        return@map leftvar.value != rightvar.value
                                                    }
                                                    else -> {
                                                        return@map null
                                                    }
                                                }
                                            }
                                            else -> {
                                                return@map null
                                            }
                                        }
                                    }
                                    leftvar is IntegerVar -> {
                                        when {
                                            rightvar is IntegerVar -> {
                                                when {
                                                    condition?.get("operator")?.value == "==" -> {
                                                        return@map leftvar.value == rightvar.value
                                                    }
                                                    condition?.get("operator")?.value == "<=" -> {
                                                        return@map leftvar.value <= rightvar.value
                                                    }
                                                    condition?.get("operator")?.value == ">=" -> {
                                                        return@map leftvar.value >= rightvar.value
                                                    }
                                                    condition?.get("operator")?.value == "<" -> {
                                                        return@map leftvar.value < rightvar.value
                                                    }
                                                    condition?.get("operator")?.value == ">" -> {
                                                        return@map leftvar.value > rightvar.value
                                                    }
                                                    condition?.get("operator")?.value == "!=" -> {
                                                        return@map leftvar.value != rightvar.value
                                                    }
                                                    else -> {
                                                        return@map null
                                                    }
                                                }
                                            }
                                            else -> {
                                                return@map null
                                            }
                                        }
                                    }
                                    else -> {
                                        return@map null
                                    }
                                }
                            }
                            leftvar != null && rightvar == null -> {
                                when {
                                    leftvar is BooleanVar -> {
                                        when {
                                            right!!.matches(Regex("(?<bool>true|false)")) -> {
                                                when {
                                                    condition?.get("operator")?.value == "==" -> {
                                                        return@map leftvar.value == right.toBoolean()
                                                    }
                                                    condition?.get("operator")?.value == "!=" -> {
                                                        return@map leftvar.value != right.toBoolean()
                                                    }
                                                    else -> {
                                                        return@map null
                                                    }
                                                }
                                            }
                                            else -> {
                                                return@map null
                                            }
                                        }
                                    }
                                    leftvar is StringVar -> {
                                        when {
                                            right!!.matches(Regex("(\\\"(?<stringa>[^\\\"]+)\\\"|\\'(?<stringb>[^\\']+)\\')")) -> {
                                                val right = Regex("(\\\"(?<stringa>[^\\\"]+)\\\"|\\'(?<stringb>[^\\']+)\\')").find(right)!!.groups as? MatchNamedGroupCollection
                                                when {
                                                    condition?.get("operator")?.value == "==" -> {
                                                        return@map leftvar.value == right?.get("stringa")?.value ?: right?.get("stringb")?.value
                                                    }
                                                    condition?.get("operator")?.value == "!=" -> {
                                                        return@map leftvar.value != right?.get("stringa")?.value ?: right?.get("stringb")?.value
                                                    }
                                                    else -> {
                                                        return@map null
                                                    }
                                                }
                                            }
                                            else -> {
                                                return@map null
                                            }
                                        }
                                    }
                                    leftvar is DoubleVar -> {
                                        when {
                                            right!!.matches(Regex("(?<double>\\d+\\.\\d+)")) -> {
                                                when {
                                                    condition?.get("operator")?.value == "==" -> {
                                                        return@map leftvar.value == rightvar?.toDouble() ?: leftvar.value - 1
                                                    }
                                                    condition?.get("operator")?.value == "<=" -> {
                                                        return@map leftvar.value <= rightvar?.toDouble() ?: leftvar.value - 1
                                                    }
                                                    condition?.get("operator")?.value == ">=" -> {
                                                        return@map leftvar.value >= rightvar?.toDouble() ?: leftvar.value + 1
                                                    }
                                                    condition?.get("operator")?.value == "<" -> {
                                                        return@map leftvar.value < rightvar?.toDouble() ?: leftvar.value - 1
                                                    }
                                                    condition?.get("operator")?.value == ">" -> {
                                                        return@map leftvar.value > rightvar?.toDouble() ?: leftvar.value + 1
                                                    }
                                                    condition?.get("operator")?.value == "!=" -> {
                                                        return@map leftvar.value != rightvar?.toDouble() ?: leftvar.value
                                                    }
                                                    else -> {
                                                        return@map null
                                                    }
                                                }
                                            }
                                            else -> {
                                                return@map null
                                            }
                                        }
                                    }
                                    leftvar is IntegerVar -> {
                                        when {
                                            right!!.matches(Regex("(?<int>\\d+)")) -> {
                                                when {
                                                    condition?.get("operator")?.value == "==" -> {
                                                        return@map leftvar.value == right.toInt()
                                                    }
                                                    condition?.get("operator")?.value == "<=" -> {
                                                        return@map leftvar.value <= right.toInt()
                                                    }
                                                    condition?.get("operator")?.value == ">=" -> {
                                                        return@map leftvar.value >= right.toInt()
                                                    }
                                                    condition?.get("operator")?.value == "<" -> {
                                                        return@map leftvar.value < right.toInt()
                                                    }
                                                    condition?.get("operator")?.value == ">" -> {
                                                        return@map leftvar.value > right.toInt()
                                                    }
                                                    condition?.get("operator")?.value == "!=" -> {
                                                        return@map leftvar.value != right.toInt()
                                                    }
                                                    else -> {
                                                        return@map null
                                                    }
                                                }
                                            }
                                            else -> {
                                                return@map null
                                            }
                                        }
                                    }
                                    else -> {
                                        return@map null
                                    }
                                }
                            }
                            leftvar == null && rightvar != null -> {
                                when {
                                    left!!.matches(Regex("(?<bool>true|false)")) -> {
                                        when {
                                            rightvar is BooleanVar -> {
                                                when {
                                                    condition?.get("operator")?.value == "==" -> {
                                                        return@map left.toBoolean() == (rightvar as BooleanVar).value
                                                    }
                                                    condition?.get("operator")?.value == "!=" -> {
                                                        return@map left.toBoolean() != (rightvar as BooleanVar).value
                                                    }
                                                    else -> {
                                                        return@map null
                                                    }
                                                }
                                            }
                                            else -> {
                                                return@map null
                                            }
                                        }
                                    }
                                    left!!.matches(Regex("(\\\"(?<stringa>[^\\\"]+)\\\"|\\'(?<stringb>[^\\']+)\\')")) -> {
                                        when {
                                            rightvar is StringVar -> {
                                                val left = Regex("(\\\"(?<stringa>[^\\\"]+)\\\"|\\'(?<stringb>[^\\']+)\\')").find(left)!!.groups as? MatchNamedGroupCollection
                                                when {
                                                    condition?.get("operator")?.value == "==" -> {
                                                        return@map left?.get("stringa")?.value ?: left?.get("stringb")?.value == rightvar.value
                                                    }
                                                    condition?.get("operator")?.value == "!=" -> {
                                                        return@map left?.get("stringa")?.value ?: left?.get("stringb")?.value != rightvar.value
                                                    }
                                                    else -> {
                                                        return@map null
                                                    }
                                                }
                                            }
                                            else -> {
                                                return@map null
                                            }
                                        }
                                    }
                                    left!!.matches(Regex("(?<double>\\d+\\.\\d+)")) -> {
                                        when {
                                            rightvar is DoubleVar -> {
                                                when {
                                                    condition?.get("operator")?.value == "==" -> {
                                                        return@map left.toDouble() == rightvar.value
                                                    }
                                                    condition?.get("operator")?.value == "<=" -> {
                                                        return@map left.toDouble() <= rightvar.value
                                                    }
                                                    condition?.get("operator")?.value == ">=" -> {
                                                        return@map left.toDouble() >= rightvar.value
                                                    }
                                                    condition?.get("operator")?.value == "<" -> {
                                                        return@map left.toDouble() < rightvar.value
                                                    }
                                                    condition?.get("operator")?.value == ">" -> {
                                                        return@map left.toDouble() > rightvar.value
                                                    }
                                                    condition?.get("operator")?.value == "!=" -> {
                                                        return@map left.toDouble() != rightvar.value
                                                    }
                                                    else -> {
                                                        return@map null
                                                    }
                                                }
                                            }
                                            else -> {
                                                return@map null
                                            }
                                        }
                                    }
                                    left!!.matches(Regex("(?<int>\\d+)")) -> {
                                        when {
                                            rightvar is IntegerVar -> {
                                                when {
                                                    condition?.get("operator")?.value == "==" -> {
                                                        return@map left.toInt() == rightvar.value
                                                    }
                                                    condition?.get("operator")?.value == "<=" -> {
                                                        return@map left.toInt() <= rightvar.value
                                                    }
                                                    condition?.get("operator")?.value == ">=" -> {
                                                        return@map left.toInt() >= rightvar.value
                                                    }
                                                    condition?.get("operator")?.value == "<" -> {
                                                        return@map left.toInt() < rightvar.value
                                                    }
                                                    condition?.get("operator")?.value == ">" -> {
                                                        return@map left.toInt() > rightvar.value
                                                    }
                                                    condition?.get("operator")?.value == "!=" -> {
                                                        return@map left.toInt() != rightvar.value
                                                    }
                                                    else -> {
                                                        return@map null
                                                    }
                                                }
                                            }
                                            else -> {
                                                return@map null
                                            }
                                        }
                                    }
                                    else -> {
                                        return@map null
                                    }
                                }
                            }
                            leftvar == null && rightvar == null -> {
                                when {
                                    left!!.matches(Regex("(?<bool>true|false)")) -> {
                                        when {
                                            right!!.matches(Regex("(?<bool>true|false)")) -> {
                                                when {
                                                    condition?.get("operator")?.value == "==" -> {
                                                        return@map left == right
                                                    }
                                                    condition?.get("operator")?.value == "!=" -> {
                                                        return@map left != right
                                                    }
                                                    else -> {
                                                        return@map null
                                                    }
                                                }
                                            }
                                            right!!.matches(Regex("(\\\"(?<stringa>[^\\\"]+)\\\"|\\'(?<stringb>[^\\']+)\\')")) -> {

                                            }
                                            right!!.matches(Regex("(?<double>\\d+\\.\\d+)")) -> {

                                            }
                                            right!!.matches(Regex("(?<int>\\d+)")) -> {

                                            }
                                            else -> {
                                                return@map null
                                            }
                                        }
                                    }
                                    left!!.matches(Regex("(\\\"(?<stringa>[^\\\"]+)\\\"|\\'(?<stringb>[^\\']+)\\')")) -> {
                                        when {
                                            right!!.matches(Regex("(\\\"(?<stringa>[^\\\"]+)\\\"|\\'(?<stringb>[^\\']+)\\')")) -> {
                                                val left = Regex("(\\\"(?<stringa>[^\\\"]+)\\\"|\\'(?<stringb>[^\\']+)\\')").find(left)!!.groups as? MatchNamedGroupCollection
                                                val right = Regex("(\\\"(?<stringa>[^\\\"]+)\\\"|\\'(?<stringb>[^\\']+)\\')").find(right)!!.groups as? MatchNamedGroupCollection
                                                when {
                                                    condition?.get("operator")?.value == "==" -> {
                                                        return@map left?.get("stringa")?.value ?: left?.get("stringb")?.value == right?.get("stringa")?.value ?: right?.get("stringb")?.value
                                                    }
                                                    condition?.get("operator")?.value == "!=" -> {
                                                        return@map left?.get("stringa")?.value ?: left?.get("stringb")?.value != right?.get("stringa")?.value ?: right?.get("stringb")?.value
                                                    }
                                                    else -> {
                                                        return@map null
                                                    }
                                                }
                                            }
                                            else -> {
                                                return@map null
                                            }
                                        }
                                    }
                                    left!!.matches(Regex("(?<double>\\d+\\.\\d+)")) -> {
                                        when {
                                            right!!.matches(Regex("(?<double>\\d+\\.\\d+)")) -> {
                                                when {
                                                    condition?.get("operator")?.value == "==" -> {
                                                        return@map left == right
                                                    }
                                                    condition?.get("operator")?.value == "<=" -> {
                                                        return@map left <= right
                                                    }
                                                    condition?.get("operator")?.value == ">=" -> {
                                                        return@map left >= right
                                                    }
                                                    condition?.get("operator")?.value == "<" -> {
                                                        return@map left < right
                                                    }
                                                    condition?.get("operator")?.value == ">" -> {
                                                        return@map left > right
                                                    }
                                                    condition?.get("operator")?.value == "!=" -> {
                                                        return@map left != right
                                                    }
                                                    else -> {
                                                        return@map null
                                                    }
                                                }
                                            }
                                            else -> {
                                                return@map null
                                            }
                                        }
                                    }
                                    left!!.matches(Regex("(?<int>\\d+)")) -> {
                                        when {
                                            right!!.matches(Regex("(?<int>\\d+)")) -> {
                                                when {
                                                    condition?.get("operator")?.value == "==" -> {
                                                        return@map left == right
                                                    }
                                                    condition?.get("operator")?.value == "<=" -> {
                                                        return@map left <= right
                                                    }
                                                    condition?.get("operator")?.value == ">=" -> {
                                                        return@map left >= right
                                                    }
                                                    condition?.get("operator")?.value == "<" -> {
                                                        return@map left < right
                                                    }
                                                    condition?.get("operator")?.value == ">" -> {
                                                        return@map left > right
                                                    }
                                                    condition?.get("operator")?.value == "!=" -> {
                                                        return@map left != right
                                                    }
                                                    else -> {
                                                        return@map null
                                                    }
                                                }
                                            }
                                            else -> {
                                                return@map null
                                            }
                                        }
                                    }
                                    else -> {
                                        return@map null
                                    }
                                }
                            }
                            else -> {
                                return@map null
                            }
                        }
                    }
                } else if (condition?.get("function") != null) {
                    var function = parser.allCode.children.filter { child ->
                        child.type == BlockType.FUNCTION
                    }.filter { child ->
                        (child.structure as uni.ase.assignment.parser.structures.blocks.Function)?.name == condition?.get(
                            "name"
                        )?.value
                    }.first().structure as uni.ase.assignment.parser.structures.blocks.Function
                    var funcRes = function.run(condition?.get("parameters")?.value ?: "")
                    if (funcRes is BooleanVar) {
                        return@map funcRes.value
                    } else {
                        parser.log.error("function ${function.name} does not return a boolean")
                    }
                } else if (condition?.get("boolean") != null) {
                    return@map condition?.get("boolean")?.value == "true"
                } else {
                    return@map null
                }
            }.filter { it != null }.all { it == true }
        } else if (condition.contains("||")) {
            outcome = condition.split("||").map {
                val condition =
                    Regex("(?<left>.+?)\\s*(?<operator>==|<=|>=|<|>|!=)\\s*(?<right>.+)|(?<function>(?<name>[A-Z]\\w+)\\((?<parameters>.+)\\))|(?<boolean>true|false)").find(
                        condition
                    )!!.groups as? MatchNamedGroupCollection
                if (condition?.get("left") != null && condition?.get("right") != null) {
                    if (condition?.get("left")?.value?.matches(Regex("(?<function>[A-Z]\\w+\\((?<parameters>.+)\\))")) == true && condition?.get(
                            "right"
                        )?.value?.matches(Regex("(?<function>[A-Z]\\w+\\((?<parameters>.+)\\))")) == false
                    ) {
                        val leftFunc = Regex("(?<function>[A-Z]\\w+\\((?<parameters>.+)\\))").find(
                            condition?.get("left")?.value ?: ""
                        )!!.groups as? MatchNamedGroupCollection
                        var function = parser.allCode.children.filter { child ->
                            child.type == BlockType.FUNCTION
                        }.filter { child ->
                            (child.structure as uni.ase.assignment.parser.structures.blocks.Function)?.name == leftFunc?.get(
                                "name"
                            )?.value
                        }.first().structure as uni.ase.assignment.parser.structures.blocks.Function
                        var funcRes = function.run(leftFunc?.get("parameters")?.value ?: "")
                        when {
                            funcRes is StringVar -> {
                                when {
                                    condition?.get("operator")?.value == "==" -> {
                                        return@map funcRes.value == condition?.get("right")?.value
                                    }
                                    condition?.get("operator")?.value == "!=" -> {
                                        return@map funcRes.value != condition?.get("right")?.value
                                    }
                                    else -> {
                                        return@map null
                                    }
                                }
                            }
                            funcRes is IntegerVar -> {
                                when {
                                    condition?.get("operator")?.value == "==" -> {
                                        return@map funcRes.value == (condition?.get("right")?.value?.toInt() ?: funcRes.value - 1)
                                    }
                                    condition?.get("operator")?.value == "<=" -> {
                                        return@map funcRes.value <= (condition?.get("right")?.value?.toInt() ?: funcRes.value + 1)
                                    }
                                    condition?.get("operator")?.value == ">=" -> {
                                        return@map funcRes.value >= (condition?.get("right")?.value?.toInt() ?: funcRes.value - 1)
                                    }
                                    condition?.get("operator")?.value == "<" -> {
                                        return@map funcRes.value < (condition?.get("right")?.value?.toInt() ?: funcRes.value + 1)
                                    }
                                    condition?.get("operator")?.value == ">" -> {
                                        return@map funcRes.value > (condition?.get("right")?.value?.toInt() ?: funcRes.value - 1)
                                    }
                                    condition?.get("operator")?.value == "!=" -> {
                                        return@map funcRes.value != (condition?.get("right")?.value?.toInt() ?: funcRes.value)
                                    }
                                    else -> {
                                        return@map null
                                    }
                                }
                            }
                            funcRes is DoubleVar -> {
                                when {
                                    condition?.get("operator")?.value == "==" -> {
                                        return@map funcRes.value == (condition?.get("right")?.value?.toDouble() ?: funcRes.value - 1)
                                    }
                                    condition?.get("operator")?.value == "<=" -> {
                                        return@map funcRes.value <= (condition?.get("right")?.value?.toDouble() ?: funcRes.value + 1)
                                    }
                                    condition?.get("operator")?.value == ">=" -> {
                                        return@map funcRes.value >= (condition?.get("right")?.value?.toDouble() ?: funcRes.value - 1)
                                    }
                                    condition?.get("operator")?.value == "<" -> {
                                        return@map funcRes.value < (condition?.get("right")?.value?.toDouble() ?: funcRes.value + 1)
                                    }
                                    condition?.get("operator")?.value == ">" -> {
                                        return@map funcRes.value > (condition?.get("right")?.value?.toDouble() ?: funcRes.value - 1)
                                    }
                                    condition?.get("operator")?.value == "!=" -> {
                                        return@map funcRes.value != (condition?.get("right")?.value?.toDouble() ?: funcRes.value)
                                    }
                                    else -> {
                                        return@map null
                                    }
                                }
                            }
                            funcRes is BooleanVar -> {
                                when {
                                    condition?.get("operator")?.value == "==" -> {
                                        return@map funcRes.value == condition?.get("right")?.value.toBoolean()
                                    }
                                    condition?.get("operator")?.value == "!=" -> {
                                        return@map funcRes.value != condition?.get("right")?.value.toBoolean()
                                    }
                                    else -> {
                                        return@map null
                                    }
                                }
                            }
                            else -> {
                                return@map null
                            }
                        }
                    } else if (condition?.get("left")?.value?.matches(Regex("(?<function>[A-Z]\\w+\\((?<parameters>.+)\\))")) == false && condition?.get(
                            "right"
                        )?.value?.matches(Regex("(?<function>[A-Z]\\w+\\((?<parameters>.+)\\))")) == true
                    ) {
                        val rightFunc = Regex("(?<function>[A-Z]\\w+\\((?<parameters>.+)\\))").find(
                            condition?.get("right")?.value ?: ""
                        )!!.groups as? MatchNamedGroupCollection
                        var function = parser.allCode.children.filter { child ->
                            child.type == BlockType.FUNCTION
                        }.filter { child ->
                            (child.structure as uni.ase.assignment.parser.structures.blocks.Function)?.name == rightFunc?.get(
                                "name"
                            )?.value
                        }.first().structure as uni.ase.assignment.parser.structures.blocks.Function
                        var funcRes = function.run(rightFunc?.get("parameters")?.value ?: "")
                        when {
                            funcRes is StringVar -> {
                                when {
                                    condition?.get("operator")?.value == "==" -> {
                                        return@map funcRes.value == condition?.get("right")?.value
                                    }
                                    condition?.get("operator")?.value == "!=" -> {
                                        return@map funcRes.value != condition?.get("right")?.value
                                    }
                                    else -> {
                                        return@map null
                                    }
                                }
                            }
                            funcRes is IntegerVar -> {
                                when {
                                    condition?.get("operator")?.value == "==" -> {
                                        return@map funcRes.value == (condition?.get("right")?.value?.toInt() ?: funcRes.value - 1)
                                    }
                                    condition?.get("operator")?.value == "<=" -> {
                                        return@map funcRes.value <= (condition?.get("right")?.value?.toInt() ?: funcRes.value + 1)
                                    }
                                    condition?.get("operator")?.value == ">=" -> {
                                        return@map funcRes.value >= (condition?.get("right")?.value?.toInt() ?: funcRes.value - 1)
                                    }
                                    condition?.get("operator")?.value == "<" -> {
                                        return@map funcRes.value < (condition?.get("right")?.value?.toInt() ?: funcRes.value + 1)
                                    }
                                    condition?.get("operator")?.value == ">" -> {
                                        return@map funcRes.value > (condition?.get("right")?.value?.toInt() ?: funcRes.value - 1)
                                    }
                                    condition?.get("operator")?.value == "!=" -> {
                                        return@map funcRes.value != (condition?.get("right")?.value?.toInt() ?: funcRes.value)
                                    }
                                    else -> {
                                        return@map null
                                    }
                                }
                            }
                            funcRes is DoubleVar -> {
                                when {
                                    condition?.get("operator")?.value == "==" -> {
                                        return@map funcRes.value == (condition?.get("right")?.value?.toDouble() ?: funcRes.value - 1)
                                    }
                                    condition?.get("operator")?.value == "<=" -> {
                                        return@map funcRes.value <= (condition?.get("right")?.value?.toDouble() ?: funcRes.value + 1)
                                    }
                                    condition?.get("operator")?.value == ">=" -> {
                                        return@map funcRes.value >= (condition?.get("right")?.value?.toDouble() ?: funcRes.value - 1)
                                    }
                                    condition?.get("operator")?.value == "<" -> {
                                        return@map funcRes.value < (condition?.get("right")?.value?.toDouble() ?: funcRes.value + 1)
                                    }
                                    condition?.get("operator")?.value == ">" -> {
                                        return@map funcRes.value > (condition?.get("right")?.value?.toDouble() ?: funcRes.value - 1)
                                    }
                                    condition?.get("operator")?.value == "!=" -> {
                                        return@map funcRes.value != (condition?.get("right")?.value?.toDouble() ?: funcRes.value)
                                    }
                                    else -> {
                                        return@map null
                                    }
                                }
                            }
                            funcRes is BooleanVar -> {
                                when {
                                    condition?.get("operator")?.value == "==" -> {
                                        return@map funcRes.value == condition?.get("right")?.value.toBoolean()
                                    }
                                    condition?.get("operator")?.value == "!=" -> {
                                        return@map funcRes.value != condition?.get("right")?.value.toBoolean()
                                    }
                                    else -> {
                                        return@map null
                                    }
                                }
                            }
                            else -> {
                                return@map null
                            }
                        }
                    } else if (condition?.get("left")?.value?.matches(Regex("(?<function>[A-Z]\\w+\\((?<parameters>.+)\\))")) == true && condition?.get(
                            "right"
                        )?.value?.matches(Regex("(?<function>[A-Z]\\w+\\((?<parameters>.+)\\))")) == true
                    ) {
                        val leftFunc = Regex("(?<function>[A-Z]\\w+\\((?<parameters>.+)\\))").find(
                            condition?.get("right")?.value ?: ""
                        )!!.groups as? MatchNamedGroupCollection
                        var lfunction = parser.allCode.children.filter { child ->
                            child.type == BlockType.FUNCTION
                        }.filter { child ->
                            (child.structure as uni.ase.assignment.parser.structures.blocks.Function)?.name == leftFunc?.get(
                                "name"
                            )?.value
                        }.first().structure as uni.ase.assignment.parser.structures.blocks.Function
                        var lfuncRes = lfunction.run(leftFunc?.get("parameters")?.value ?: "")
                        val rightFunc = Regex("(?<function>[A-Z]\\w+\\((?<parameters>.+)\\))").find(
                            condition?.get("right")?.value ?: ""
                        )!!.groups as? MatchNamedGroupCollection
                        var rfunction = parser.allCode.children.filter { child ->
                            child.type == BlockType.FUNCTION
                        }.filter { child ->
                            (child.structure as uni.ase.assignment.parser.structures.blocks.Function)?.name == rightFunc?.get(
                                "name"
                            )?.value
                        }.first().structure as uni.ase.assignment.parser.structures.blocks.Function
                        var rfuncRes = rfunction.run(rightFunc?.get("parameters")?.value ?: "")
                        when {
                            lfuncRes is StringVar && rfuncRes is StringVar -> {
                                when {
                                    condition?.get("operator")?.value == "==" -> {
                                        return@map lfuncRes.value == rfuncRes.value
                                    }
                                    condition?.get("operator")?.value == "!=" -> {
                                        return@map lfuncRes.value != rfuncRes.value
                                    }
                                    else -> {
                                        return@map null
                                    }
                                }
                            }
                            lfuncRes is IntegerVar && rfuncRes is IntegerVar -> {
                                when {
                                    condition?.get("operator")?.value == "==" -> {
                                        return@map lfuncRes.value == rfuncRes.value
                                    }
                                    condition?.get("operator")?.value == "<=" -> {
                                        return@map lfuncRes.value <= rfuncRes.value
                                    }
                                    condition?.get("operator")?.value == ">=" -> {
                                        return@map lfuncRes.value >= rfuncRes.value
                                    }
                                    condition?.get("operator")?.value == "<" -> {
                                        return@map lfuncRes.value < rfuncRes.value
                                    }
                                    condition?.get("operator")?.value == ">" -> {
                                        return@map lfuncRes.value > rfuncRes.value
                                    }
                                    condition?.get("operator")?.value == "!=" -> {
                                        return@map lfuncRes.value != rfuncRes.value
                                    }
                                    else -> {
                                        return@map null
                                    }
                                }
                            }
                            lfuncRes is DoubleVar && rfuncRes is DoubleVar -> {
                                when {
                                    condition?.get("operator")?.value == "==" -> {
                                        return@map lfuncRes.value == rfuncRes.value
                                    }
                                    condition?.get("operator")?.value == "<=" -> {
                                        return@map lfuncRes.value <= rfuncRes.value
                                    }
                                    condition?.get("operator")?.value == ">=" -> {
                                        return@map lfuncRes.value >= rfuncRes.value
                                    }
                                    condition?.get("operator")?.value == "<" -> {
                                        return@map lfuncRes.value < rfuncRes.value
                                    }
                                    condition?.get("operator")?.value == ">" -> {
                                        return@map lfuncRes.value > rfuncRes.value
                                    }
                                    condition?.get("operator")?.value == "!=" -> {
                                        return@map lfuncRes.value != rfuncRes.value
                                    }
                                    else -> {
                                        return@map null
                                    }
                                }
                            }
                            lfuncRes is BooleanVar && rfuncRes is BooleanVar -> {
                                when {
                                    condition?.get("operator")?.value == "==" -> {
                                        return@map lfuncRes.value == rfuncRes.value
                                    }
                                    condition?.get("operator")?.value == "!=" -> {
                                        return@map lfuncRes.value != rfuncRes.value
                                    }
                                    else -> {
                                        return@map null
                                    }
                                }
                            }
                            else -> {
                                return@map null
                            }
                        }
                    } else {
                        var left = condition?.get("left")?.value
                        var right = condition?.get("right")?.value
                        when {
                            left!!.matches(Regex("(?<bool>true|false)")) -> {
                                when {
                                    right!!.matches(Regex("(?<bool>true|false)")) -> {
                                        when {
                                            condition?.get("operator")?.value == "==" -> {
                                                return@map left == right
                                            }
                                            condition?.get("operator")?.value == "!=" -> {
                                                return@map left != right
                                            }
                                            else -> {
                                                return@map null
                                            }
                                        }
                                    }
                                    right!!.matches(Regex("(\\\"(?<stringa>[^\\\"]+)\\\"|\\'(?<stringb>[^\\']+)\\')")) -> {

                                    }
                                    right!!.matches(Regex("(?<double>\\d+\\.\\d+)")) -> {

                                    }
                                    right!!.matches(Regex("(?<int>\\d+)")) -> {

                                    }
                                    else -> {
                                        return@map null
                                    }
                                }
                            }
                            left!!.matches(Regex("(\\\"(?<stringa>[^\\\"]+)\\\"|\\'(?<stringb>[^\\']+)\\')")) -> {
                                when {
                                    right!!.matches(Regex("(\\\"(?<stringa>[^\\\"]+)\\\"|\\'(?<stringb>[^\\']+)\\')")) -> {
                                        val left = Regex("(\\\"(?<stringa>[^\\\"]+)\\\"|\\'(?<stringb>[^\\']+)\\')").find(left)!!.groups as? MatchNamedGroupCollection
                                        val right = Regex("(\\\"(?<stringa>[^\\\"]+)\\\"|\\'(?<stringb>[^\\']+)\\')").find(right)!!.groups as? MatchNamedGroupCollection
                                        when {
                                            condition?.get("operator")?.value == "==" -> {
                                                return@map left?.get("stringa")?.value ?: left?.get("stringb")?.value == right?.get("stringa")?.value ?: right?.get("stringb")?.value
                                            }
                                            condition?.get("operator")?.value == "!=" -> {
                                                return@map left?.get("stringa")?.value ?: left?.get("stringb")?.value != right?.get("stringa")?.value ?: right?.get("stringb")?.value
                                            }
                                            else -> {
                                                return@map null
                                            }
                                        }
                                    }
                                    else -> {
                                        return@map null
                                    }
                                }
                            }
                            left!!.matches(Regex("(?<double>\\d+\\.\\d+)")) -> {
                                when {
                                    right!!.matches(Regex("(?<double>\\d+\\.\\d+)")) -> {
                                        when {
                                            condition?.get("operator")?.value == "==" -> {
                                                return@map left == right
                                            }
                                            condition?.get("operator")?.value == "<=" -> {
                                                return@map left <= right
                                            }
                                            condition?.get("operator")?.value == ">=" -> {
                                                return@map left >= right
                                            }
                                            condition?.get("operator")?.value == "<" -> {
                                                return@map left < right
                                            }
                                            condition?.get("operator")?.value == ">" -> {
                                                return@map left > right
                                            }
                                            condition?.get("operator")?.value == "!=" -> {
                                                return@map left != right
                                            }
                                            else -> {
                                                return@map null
                                            }
                                        }
                                    }
                                    else -> {
                                        return@map null
                                    }
                                }
                            }
                            left!!.matches(Regex("(?<int>\\d+)")) -> {
                                when {
                                    right!!.matches(Regex("(?<int>\\d+)")) -> {
                                        when {
                                            condition?.get("operator")?.value == "==" -> {
                                                return@map left == right
                                            }
                                            condition?.get("operator")?.value == "<=" -> {
                                                return@map left <= right
                                            }
                                            condition?.get("operator")?.value == ">=" -> {
                                                return@map left >= right
                                            }
                                            condition?.get("operator")?.value == "<" -> {
                                                return@map left < right
                                            }
                                            condition?.get("operator")?.value == ">" -> {
                                                return@map left > right
                                            }
                                            condition?.get("operator")?.value == "!=" -> {
                                                return@map left != right
                                            }
                                            else -> {
                                                return@map null
                                            }
                                        }
                                    }
                                    else -> {
                                        return@map null
                                    }
                                }
                            }
                            else -> {
                                return@map null
                            }
                        }
                    }
                } else if (condition?.get("function") != null) {
                    var function = parser.allCode.children.filter { child ->
                        child.type == BlockType.FUNCTION
                    }.filter { child ->
                        (child.structure as uni.ase.assignment.parser.structures.blocks.Function)?.name == condition?.get(
                            "name"
                        )?.value
                    }.first().structure as uni.ase.assignment.parser.structures.blocks.Function
                    var funcRes = function.run(condition?.get("parameters")?.value ?: "")
                    if (funcRes is BooleanVar) {
                        return@map funcRes.value
                    } else {
                        parser.log.error("function ${function.name} does not return a boolean")
                    }
                } else if (condition?.get("boolean") != null) {
                    return@map condition?.get("boolean")?.value == "true"
                } else {
                    return@map null
                }
            }.filter { it != null }.any { it == true }
        } else {
            val condition = Regex("(?<left>.+?)\\s*(?<operator>==|<=|>=|<|>|!=)\\s*(?<right>.+)|(?<function>(?<name>[A-Z]\\w+)\\((?<parameters>.+)\\))|(?<boolean>true|false)").find(condition)?.groups as? MatchNamedGroupCollection
            when {
                condition?.get("left") != null || condition?.get("right") != null -> {

                }
                condition?.get("function") != null -> {

                }
                condition?.get("boolean") != null -> {

                }
            }
            if (condition?.get("left") != null && condition?.get("right") != null) {
                if (condition?.get("left")?.value?.matches(Regex("(?<function>[A-Z]\\w+\\((?<parameters>.+)\\))")) == true && condition?.get("right")?.value?.matches(Regex("(?<function>[A-Z]\\w+\\((?<parameters>.+)\\))")) == false) {
                    val leftFunc = Regex("(?<function>[A-Z]\\w+\\((?<parameters>.+)\\))").find(
                        condition?.get("left")?.value ?: ""
                    )!!.groups as? MatchNamedGroupCollection
                    var function = parser.allCode.children.filter { child ->
                        child.type == BlockType.FUNCTION
                    }.filter { child ->
                        (child.structure as uni.ase.assignment.parser.structures.blocks.Function)?.name == leftFunc?.get(
                            "name"
                        )?.value
                    }.first().structure as uni.ase.assignment.parser.structures.blocks.Function
                    var funcRes = function.run(leftFunc?.get("parameters")?.value ?: "")
                    when {
                        funcRes is StringVar -> {
                            when {
                                condition?.get("operator")?.value == "==" -> {
                                    outcome = funcRes.value == condition?.get("right")?.value
                                }
                                condition?.get("operator")?.value == "!=" -> {
                                    outcome = funcRes.value != condition?.get("right")?.value
                                }
                                else -> {
                                    outcome = null
                                }
                            }
                        }
                        funcRes is IntegerVar -> {
                            when {
                                condition?.get("operator")?.value == "==" -> {
                                    outcome = funcRes.value == (condition?.get("right")?.value?.toInt() ?: funcRes.value - 1)
                                }
                                condition?.get("operator")?.value == "<=" -> {
                                    outcome = funcRes.value <= (condition?.get("right")?.value?.toInt() ?: funcRes.value + 1)
                                }
                                condition?.get("operator")?.value == ">=" -> {
                                    outcome = funcRes.value >= (condition?.get("right")?.value?.toInt() ?: funcRes.value - 1)
                                }
                                condition?.get("operator")?.value == "<" -> {
                                    outcome = funcRes.value < (condition?.get("right")?.value?.toInt() ?: funcRes.value + 1)
                                }
                                condition?.get("operator")?.value == ">" -> {
                                    outcome = funcRes.value > (condition?.get("right")?.value?.toInt() ?: funcRes.value - 1)
                                }
                                condition?.get("operator")?.value == "!=" -> {
                                    outcome = funcRes.value != (condition?.get("right")?.value?.toInt() ?: funcRes.value)
                                }
                                else -> {
                                    outcome = null
                                }
                            }
                        }
                        funcRes is DoubleVar -> {
                            when {
                                condition?.get("operator")?.value == "==" -> {
                                    outcome = funcRes.value == (condition?.get("right")?.value?.toDouble() ?: funcRes.value - 1)
                                }
                                condition?.get("operator")?.value == "<=" -> {
                                    outcome = funcRes.value <= (condition?.get("right")?.value?.toDouble() ?: funcRes.value + 1)
                                }
                                condition?.get("operator")?.value == ">=" -> {
                                    outcome = funcRes.value >= (condition?.get("right")?.value?.toDouble() ?: funcRes.value - 1)
                                }
                                condition?.get("operator")?.value == "<" -> {
                                    outcome = funcRes.value < (condition?.get("right")?.value?.toDouble() ?: funcRes.value + 1)
                                }
                                condition?.get("operator")?.value == ">" -> {
                                    outcome = funcRes.value > (condition?.get("right")?.value?.toDouble() ?: funcRes.value - 1)
                                }
                                condition?.get("operator")?.value == "!=" -> {
                                    outcome = funcRes.value != (condition?.get("right")?.value?.toDouble() ?: funcRes.value)
                                }
                                else -> {
                                    outcome = null
                                }
                            }
                        }
                        funcRes is BooleanVar -> {
                            when {
                                condition?.get("operator")?.value == "==" -> {
                                    outcome = funcRes.value == condition?.get("right")?.value.toBoolean()
                                }
                                condition?.get("operator")?.value == "!=" -> {
                                    outcome = funcRes.value != condition?.get("right")?.value.toBoolean()
                                }
                                else -> {
                                    outcome = null
                                }
                            }
                        }
                        else -> {
                            outcome = null
                        }
                    }
                } else if (condition?.get("left")?.value?.matches(Regex("(?<function>[A-Z]\\w+\\((?<parameters>.+)\\))")) == false && condition?.get(
                        "right"
                    )?.value?.matches(Regex("(?<function>[A-Z]\\w+\\((?<parameters>.+)\\))")) == true
                ) {
                    val rightFunc = Regex("(?<function>[A-Z]\\w+\\((?<parameters>.+)\\))").find(
                        condition?.get("right")?.value ?: ""
                    )!!.groups as? MatchNamedGroupCollection
                    var function = parser.allCode.children.filter { child ->
                        child.type == BlockType.FUNCTION
                    }.filter { child ->
                        (child.structure as uni.ase.assignment.parser.structures.blocks.Function)?.name == rightFunc?.get(
                            "name"
                        )?.value
                    }.first().structure as uni.ase.assignment.parser.structures.blocks.Function
                    var funcRes = function.run(rightFunc?.get("parameters")?.value ?: "")
                    when {
                        funcRes is StringVar -> {
                            when {
                                condition?.get("operator")?.value == "==" -> {
                                    outcome = funcRes.value == condition?.get("right")?.value
                                }
                                condition?.get("operator")?.value == "!=" -> {
                                    outcome = funcRes.value != condition?.get("right")?.value
                                }
                                else -> {
                                    outcome = null
                                }
                            }
                        }
                        funcRes is IntegerVar -> {
                            when {
                                condition?.get("operator")?.value == "==" -> {
                                    outcome = funcRes.value == (condition?.get("right")?.value?.toInt() ?: funcRes.value - 1)
                                }
                                condition?.get("operator")?.value == "<=" -> {
                                    outcome = funcRes.value <= (condition?.get("right")?.value?.toInt() ?: funcRes.value + 1)
                                }
                                condition?.get("operator")?.value == ">=" -> {
                                    outcome = funcRes.value >= (condition?.get("right")?.value?.toInt() ?: funcRes.value - 1)
                                }
                                condition?.get("operator")?.value == "<" -> {
                                    outcome = funcRes.value < (condition?.get("right")?.value?.toInt() ?: funcRes.value + 1)
                                }
                                condition?.get("operator")?.value == ">" -> {
                                    outcome = funcRes.value > (condition?.get("right")?.value?.toInt() ?: funcRes.value - 1)
                                }
                                condition?.get("operator")?.value == "!=" -> {
                                    outcome = funcRes.value != (condition?.get("right")?.value?.toInt() ?: funcRes.value)
                                }
                                else -> {
                                    outcome = null
                                }
                            }
                        }
                        funcRes is DoubleVar -> {
                            when {
                                condition?.get("operator")?.value == "==" -> {
                                    outcome = funcRes.value == (condition?.get("right")?.value?.toDouble() ?: funcRes.value - 1)
                                }
                                condition?.get("operator")?.value == "<=" -> {
                                    outcome = funcRes.value <= (condition?.get("right")?.value?.toDouble() ?: funcRes.value + 1)
                                }
                                condition?.get("operator")?.value == ">=" -> {
                                    outcome = funcRes.value >= (condition?.get("right")?.value?.toDouble() ?: funcRes.value - 1)
                                }
                                condition?.get("operator")?.value == "<" -> {
                                    outcome = funcRes.value < (condition?.get("right")?.value?.toDouble() ?: funcRes.value + 1)
                                }
                                condition?.get("operator")?.value == ">" -> {
                                    outcome = funcRes.value > (condition?.get("right")?.value?.toDouble() ?: funcRes.value - 1)
                                }
                                condition?.get("operator")?.value == "!=" -> {
                                    outcome = funcRes.value != (condition?.get("right")?.value?.toDouble() ?: funcRes.value)
                                }
                                else -> {
                                    outcome = null
                                }
                            }
                        }
                        funcRes is BooleanVar -> {
                            when {
                                condition?.get("operator")?.value == "==" -> {
                                    outcome = funcRes.value == condition?.get("right")?.value.toBoolean()
                                }
                                condition?.get("operator")?.value == "!=" -> {
                                    outcome = funcRes.value != condition?.get("right")?.value.toBoolean()
                                }
                                else -> {
                                    outcome = null
                                }
                            }
                        }
                        else -> {
                            outcome = null
                        }
                    }
                } else if (condition?.get("left")?.value?.matches(Regex("(?<function>[A-Z]\\w+\\((?<parameters>.+)\\))")) == true && condition?.get(
                        "right"
                    )?.value?.matches(Regex("(?<function>[A-Z]\\w+\\((?<parameters>.+)\\))")) == true
                ) {
                    val leftFunc = Regex("(?<function>[A-Z]\\w+\\((?<parameters>.+)\\))").find(
                        condition?.get("right")?.value ?: ""
                    )!!.groups as? MatchNamedGroupCollection
                    var lfunction = parser.allCode.children.filter { child ->
                        child.type == BlockType.FUNCTION
                    }.filter { child ->
                        (child.structure as uni.ase.assignment.parser.structures.blocks.Function)?.name == leftFunc?.get(
                            "name"
                        )?.value
                    }.first().structure as uni.ase.assignment.parser.structures.blocks.Function
                    var lfuncRes = lfunction.run(leftFunc?.get("parameters")?.value ?: "")
                    val rightFunc = Regex("(?<function>[A-Z]\\w+\\((?<parameters>.+)\\))").find(
                        condition?.get("right")?.value ?: ""
                    )!!.groups as? MatchNamedGroupCollection
                    var rfunction = parser.allCode.children.filter { child ->
                        child.type == BlockType.FUNCTION
                    }.filter { child ->
                        (child.structure as uni.ase.assignment.parser.structures.blocks.Function)?.name == rightFunc?.get(
                            "name"
                        )?.value
                    }.first().structure as uni.ase.assignment.parser.structures.blocks.Function
                    var rfuncRes = rfunction.run(rightFunc?.get("parameters")?.value ?: "")
                    when {
                        lfuncRes is StringVar && rfuncRes is StringVar -> {
                            when {
                                condition?.get("operator")?.value == "==" -> {
                                    outcome = lfuncRes.value == rfuncRes.value
                                }
                                condition?.get("operator")?.value == "!=" -> {
                                    outcome = lfuncRes.value != rfuncRes.value
                                }
                                else -> {
                                    outcome = null
                                }
                            }
                        }
                        lfuncRes is IntegerVar && rfuncRes is IntegerVar -> {
                            when {
                                condition?.get("operator")?.value == "==" -> {
                                    outcome = lfuncRes.value == rfuncRes.value
                                }
                                condition?.get("operator")?.value == "<=" -> {
                                    outcome = lfuncRes.value <= rfuncRes.value
                                }
                                condition?.get("operator")?.value == ">=" -> {
                                    outcome = lfuncRes.value >= rfuncRes.value
                                }
                                condition?.get("operator")?.value == "<" -> {
                                    outcome = lfuncRes.value < rfuncRes.value
                                }
                                condition?.get("operator")?.value == ">" -> {
                                    outcome = lfuncRes.value > rfuncRes.value
                                }
                                condition?.get("operator")?.value == "!=" -> {
                                    outcome = lfuncRes.value != rfuncRes.value
                                }
                                else -> {
                                    outcome = null
                                }
                            }
                        }
                        lfuncRes is DoubleVar && rfuncRes is DoubleVar -> {
                            when {
                                condition?.get("operator")?.value == "==" -> {
                                    outcome = lfuncRes.value == rfuncRes.value
                                }
                                condition?.get("operator")?.value == "<=" -> {
                                    outcome = lfuncRes.value <= rfuncRes.value
                                }
                                condition?.get("operator")?.value == ">=" -> {
                                    outcome = lfuncRes.value >= rfuncRes.value
                                }
                                condition?.get("operator")?.value == "<" -> {
                                    outcome = lfuncRes.value < rfuncRes.value
                                }
                                condition?.get("operator")?.value == ">" -> {
                                    outcome = lfuncRes.value > rfuncRes.value
                                }
                                condition?.get("operator")?.value == "!=" -> {
                                    outcome = lfuncRes.value != rfuncRes.value
                                }
                                else -> {
                                    outcome = null
                                }
                            }
                        }
                        lfuncRes is BooleanVar && rfuncRes is BooleanVar -> {
                            when {
                                condition?.get("operator")?.value == "==" -> {
                                    outcome = lfuncRes.value == rfuncRes.value
                                }
                                condition?.get("operator")?.value == "!=" -> {
                                    outcome = lfuncRes.value != rfuncRes.value
                                }
                                else -> {
                                    outcome = null
                                }
                            }
                        }
                        else -> {
                            outcome = null
                        }
                    }
                } else {
                    var left = condition?.get("left")?.value
                    var right = condition?.get("right")?.value
                    when {
                        left!!.matches(Regex("(?<bool>true|false)")) -> {
                            when {
                                right!!.matches(Regex("(?<bool>true|false)")) -> {
                                    when {
                                        condition?.get("operator")?.value == "==" -> {
                                            outcome = left == right
                                        }
                                        condition?.get("operator")?.value == "!=" -> {
                                            outcome = left != right
                                        }
                                        else -> {
                                            outcome = null
                                        }
                                    }
                                }
                                right!!.matches(Regex("(\\\"(?<stringa>[^\\\"]+)\\\"|\\'(?<stringb>[^\\']+)\\')")) -> {

                                }
                                right!!.matches(Regex("(?<double>\\d+\\.\\d+)")) -> {

                                }
                                right!!.matches(Regex("(?<int>\\d+)")) -> {

                                }
                                else -> {
                                    outcome = null
                                }
                            }
                        }
                        left!!.matches(Regex("(\\\"(?<stringa>[^\\\"]+)\\\"|\\'(?<stringb>[^\\']+)\\')")) -> {
                            when {
                                right!!.matches(Regex("(\\\"(?<stringa>[^\\\"]+)\\\"|\\'(?<stringb>[^\\']+)\\')")) -> {
                                    val left = Regex("(\\\"(?<stringa>[^\\\"]+)\\\"|\\'(?<stringb>[^\\']+)\\')").find(left)!!.groups as? MatchNamedGroupCollection
                                    val right = Regex("(\\\"(?<stringa>[^\\\"]+)\\\"|\\'(?<stringb>[^\\']+)\\')").find(right)!!.groups as? MatchNamedGroupCollection
                                    when {
                                        condition?.get("operator")?.value == "==" -> {
                                            outcome = left?.get("stringa")?.value ?: left?.get("stringb")?.value == right?.get("stringa")?.value ?: right?.get("stringb")?.value
                                        }
                                        condition?.get("operator")?.value == "!=" -> {
                                            outcome = left?.get("stringa")?.value ?: left?.get("stringb")?.value != right?.get("stringa")?.value ?: right?.get("stringb")?.value
                                        }
                                        else -> {
                                            outcome = null
                                        }
                                    }
                                }
                                else -> {
                                    outcome = null
                                }
                            }
                        }
                        left!!.matches(Regex("(?<double>\\d+\\.\\d+)")) -> {
                            when {
                                right!!.matches(Regex("(?<double>\\d+\\.\\d+)")) -> {
                                    when {
                                        condition?.get("operator")?.value == "==" -> {
                                            outcome = left == right
                                        }
                                        condition?.get("operator")?.value == "<=" -> {
                                            outcome = left <= right
                                        }
                                        condition?.get("operator")?.value == ">=" -> {
                                            outcome = left >= right
                                        }
                                        condition?.get("operator")?.value == "<" -> {
                                            outcome = left < right
                                        }
                                        condition?.get("operator")?.value == ">" -> {
                                            outcome = left > right
                                        }
                                        condition?.get("operator")?.value == "!=" -> {
                                            outcome = left != right
                                        }
                                        else -> {
                                            outcome = null
                                        }
                                    }
                                }
                                else -> {
                                    outcome = null
                                }
                            }
                        }
                        left!!.matches(Regex("(?<int>\\d+)")) -> {
                            when {
                                right!!.matches(Regex("(?<int>\\d+)")) -> {
                                    when {
                                        condition?.get("operator")?.value == "==" -> {
                                            outcome = left == right
                                        }
                                        condition?.get("operator")?.value == "<=" -> {
                                            outcome = left <= right
                                        }
                                        condition?.get("operator")?.value == ">=" -> {
                                            outcome = left >= right
                                        }
                                        condition?.get("operator")?.value == "<" -> {
                                            outcome = left < right
                                        }
                                        condition?.get("operator")?.value == ">" -> {
                                            outcome = left > right
                                        }
                                        condition?.get("operator")?.value == "!=" -> {
                                            outcome = left != right
                                        }
                                        else -> {
                                            outcome = null
                                        }
                                    }
                                }
                                else -> {
                                    outcome = null
                                }
                            }
                        }
                        else -> {
                            outcome = null
                        }
                    }
                }
            } else if (condition?.get("function") != null) {
                var function = parser.allCode.children.filter { child ->
                    child.type == BlockType.FUNCTION
                }.filter { child ->
                    (child.structure as uni.ase.assignment.parser.structures.blocks.Function)?.name == condition?.get(
                        "name"
                    )?.value
                }.first().structure as uni.ase.assignment.parser.structures.blocks.Function
                var funcRes = function.run(condition?.get("parameters")?.value ?: "")
                if (funcRes is BooleanVar) {
                    outcome = funcRes.value
                } else {
                    parser.log.error("function ${function.name} does not return a boolean")
                }
            } else if (condition?.get("boolean") != null) {
                outcome = condition?.get("boolean")?.value == "true"
            } else {
                outcome = null
            }
        }
    }
}