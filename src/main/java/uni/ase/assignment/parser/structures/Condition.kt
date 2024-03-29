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
    val scope : Block
    ) {
    fun evaluate() {
        scope.parser.log.out("lets evaluate this condition")
        val conditionreg = Regex("(?<left>.+?)\\s*(?<operator>==|<=|>=|<|>|!=)\\s*(?<right>.+)|(?<function>(?<name>[A-Z]\\w+)\\((?<parameters>.+)\\))|(?<param>[^\\n]+)").find(condition)?.groups as? MatchNamedGroupCollection
        if (conditionreg?.get("left")?.value != null && conditionreg?.get("right")?.value != null) {
            scope.parser.log.out("its an evaluation of 2 values")
            if (conditionreg?.get("left")?.value?.matches(Regex("(?<function>[A-Z]\\w+\\((?<parameters>.+)\\))")) == true && conditionreg?.get("right")?.value?.matches(Regex("(?<function>[A-Z]\\w+\\((?<parameters>.+)\\))")) == false) {
                scope.parser.log.out("the first part is a function")
                val leftFunc = Regex("(?<function>[A-Z]\\w+\\((?<parameters>.+)\\))").find(conditionreg?.get("left")?.value ?: "")?.groups as? MatchNamedGroupCollection
                var function = scope.parser.allCode.children.filter { child ->
                    child.type == BlockType.FUNCTION
                }.filter { child ->
                    (child.structure as uni.ase.assignment.parser.structures.blocks.Function)?.name == leftFunc?.get("name")?.value
                }.first().structure as uni.ase.assignment.parser.structures.blocks.Function
                var funcRes = function.run(leftFunc?.get("parameters")?.value ?: "")
                when {
                    funcRes is StringVar -> {
                        when {
                            conditionreg?.get("operator")?.value == "==" -> {
                                outcome = funcRes.value == conditionreg?.get("right")?.value
                            }
                            conditionreg?.get("operator")?.value == "!=" -> {
                                outcome = funcRes.value != conditionreg?.get("right")?.value
                            }
                            else -> {
                                outcome = false
                            }
                        }
                    }
                    funcRes is IntegerVar -> {
                        when {
                            conditionreg?.get("operator")?.value == "==" -> {
                                outcome = funcRes.value == (conditionreg?.get("right")?.value?.toInt() ?: funcRes.value - 1)
                            }
                            conditionreg?.get("operator")?.value == "<=" -> {
                                outcome = funcRes.value <= (conditionreg?.get("right")?.value?.toInt() ?: funcRes.value + 1)
                            }
                            conditionreg?.get("operator")?.value == ">=" -> {
                                outcome = funcRes.value >= (conditionreg?.get("right")?.value?.toInt() ?: funcRes.value - 1)
                            }
                            conditionreg?.get("operator")?.value == "<" -> {
                                outcome = funcRes.value < (conditionreg?.get("right")?.value?.toInt() ?: funcRes.value + 1)
                            }
                            conditionreg?.get("operator")?.value == ">" -> {
                                outcome = funcRes.value > (conditionreg?.get("right")?.value?.toInt() ?: funcRes.value - 1)
                            }
                            conditionreg?.get("operator")?.value == "!=" -> {
                                outcome = funcRes.value != (conditionreg?.get("right")?.value?.toInt() ?: funcRes.value)
                            }
                            else -> {
                                outcome = false
                            }
                        }
                    }
                    funcRes is DoubleVar -> {
                        when {
                            conditionreg?.get("operator")?.value == "==" -> {
                                outcome = funcRes.value == (conditionreg?.get("right")?.value?.toDouble() ?: funcRes.value - 1)
                            }
                            conditionreg?.get("operator")?.value == "<=" -> {
                                outcome = funcRes.value <= (conditionreg?.get("right")?.value?.toDouble() ?: funcRes.value + 1)
                            }
                            conditionreg?.get("operator")?.value == ">=" -> {
                                outcome = funcRes.value >= (conditionreg?.get("right")?.value?.toDouble() ?: funcRes.value - 1)
                            }
                            conditionreg?.get("operator")?.value == "<" -> {
                                outcome = funcRes.value < (conditionreg?.get("right")?.value?.toDouble() ?: funcRes.value + 1)
                            }
                            conditionreg?.get("operator")?.value == ">" -> {
                                outcome = funcRes.value > (conditionreg?.get("right")?.value?.toDouble() ?: funcRes.value - 1)
                            }
                            conditionreg?.get("operator")?.value == "!=" -> {
                                outcome = funcRes.value != (conditionreg?.get("right")?.value?.toDouble() ?: funcRes.value)
                            }
                            else -> {
                                outcome = false
                            }
                        }
                    }
                    funcRes is BooleanVar -> {
                        when {
                            conditionreg?.get("operator")?.value == "==" -> {
                                outcome = funcRes.value == conditionreg?.get("right")?.value.toBoolean()
                            }
                            conditionreg?.get("operator")?.value == "!=" -> {
                                outcome = funcRes.value != conditionreg?.get("right")?.value.toBoolean()
                            }
                            else -> {
                                outcome = false
                            }
                        }
                    }
                    else -> {
                        outcome = false
                    }
                }
            } else if (conditionreg?.get("left")?.value?.matches(Regex("(?<function>[A-Z]\\w+\\((?<parameters>.+)\\))")) == false && conditionreg?.get("right")?.value?.matches(Regex("(?<function>[A-Z]\\w+\\((?<parameters>.+)\\))")) == true) {
                scope.parser.log.out("the second part is a function")
                val rightFunc = Regex("(?<function>[A-Z]\\w+\\((?<parameters>.+)\\))").find(
                    conditionreg?.get("right")?.value ?: ""
                )!!.groups as? MatchNamedGroupCollection
                var function = scope.parser.allCode.children.filter { child ->
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
                            conditionreg?.get("operator")?.value == "==" -> {
                                outcome = funcRes.value == conditionreg?.get("right")?.value
                            }
                            conditionreg?.get("operator")?.value == "!=" -> {
                                outcome = funcRes.value != conditionreg?.get("right")?.value
                            }
                            else -> {
                                outcome = false
                            }
                        }
                    }
                    funcRes is IntegerVar -> {
                        when {
                            conditionreg?.get("operator")?.value == "==" -> {
                                outcome = funcRes.value == (conditionreg?.get("right")?.value?.toInt() ?: funcRes.value - 1)
                            }
                            conditionreg?.get("operator")?.value == "<=" -> {
                                outcome = funcRes.value <= (conditionreg?.get("right")?.value?.toInt() ?: funcRes.value + 1)
                            }
                            conditionreg?.get("operator")?.value == ">=" -> {
                                outcome = funcRes.value >= (conditionreg?.get("right")?.value?.toInt() ?: funcRes.value - 1)
                            }
                            conditionreg?.get("operator")?.value == "<" -> {
                                outcome = funcRes.value < (conditionreg?.get("right")?.value?.toInt() ?: funcRes.value + 1)
                            }
                            conditionreg?.get("operator")?.value == ">" -> {
                                outcome = funcRes.value > (conditionreg?.get("right")?.value?.toInt() ?: funcRes.value - 1)
                            }
                            conditionreg?.get("operator")?.value == "!=" -> {
                                outcome = funcRes.value != (conditionreg?.get("right")?.value?.toInt() ?: funcRes.value)
                            }
                            else -> {
                                outcome = false
                            }
                        }
                    }
                    funcRes is DoubleVar -> {
                        when {
                            conditionreg?.get("operator")?.value == "==" -> {
                                outcome = funcRes.value == (conditionreg?.get("right")?.value?.toDouble() ?: funcRes.value - 1)
                            }
                            conditionreg?.get("operator")?.value == "<=" -> {
                                outcome = funcRes.value <= (conditionreg?.get("right")?.value?.toDouble() ?: funcRes.value + 1)
                            }
                            conditionreg?.get("operator")?.value == ">=" -> {
                                outcome = funcRes.value >= (conditionreg?.get("right")?.value?.toDouble() ?: funcRes.value - 1)
                            }
                            conditionreg?.get("operator")?.value == "<" -> {
                                outcome = funcRes.value < (conditionreg?.get("right")?.value?.toDouble() ?: funcRes.value + 1)
                            }
                            conditionreg?.get("operator")?.value == ">" -> {
                                outcome = funcRes.value > (conditionreg?.get("right")?.value?.toDouble() ?: funcRes.value - 1)
                            }
                            conditionreg?.get("operator")?.value == "!=" -> {
                                outcome = funcRes.value != (conditionreg?.get("right")?.value?.toDouble() ?: funcRes.value)
                            }
                            else -> {
                                outcome = false
                            }
                        }
                    }
                    funcRes is BooleanVar -> {
                        when {
                            conditionreg?.get("operator")?.value == "==" -> {
                                outcome = funcRes.value == conditionreg?.get("right")?.value.toBoolean()
                            }
                            conditionreg?.get("operator")?.value == "!=" -> {
                                outcome = funcRes.value != conditionreg?.get("right")?.value.toBoolean()
                            }
                            else -> {
                                outcome = false
                            }
                        }
                    }
                    else -> {
                        outcome = false
                    }
                }
            } else if (conditionreg?.get("left")?.value?.matches(Regex("(?<function>[A-Z]\\w+\\((?<parameters>.+)\\))")) == true && conditionreg?.get("right")?.value?.matches(Regex("(?<function>[A-Z]\\w+\\((?<parameters>.+)\\))")) == true) {
                scope.parser.log.out("both parts are functions")
                val leftFunc = Regex("(?<function>[A-Z]\\w+\\((?<parameters>.+)\\))").find(
                    conditionreg?.get("right")?.value ?: ""
                )!!.groups as? MatchNamedGroupCollection
                var lfunction = scope.parser.allCode.children.filter { child ->
                    child.type == BlockType.FUNCTION
                }.filter { child ->
                    (child.structure as uni.ase.assignment.parser.structures.blocks.Function)?.name == leftFunc?.get(
                        "name"
                    )?.value
                }.first().structure as uni.ase.assignment.parser.structures.blocks.Function
                var lfuncRes = lfunction.run(leftFunc?.get("parameters")?.value ?: "")
                val rightFunc = Regex("(?<function>[A-Z]\\w+\\((?<parameters>.+)\\))").find(
                    conditionreg?.get("right")?.value ?: ""
                )!!.groups as? MatchNamedGroupCollection
                var rfunction = scope.parser.allCode.children.filter { child ->
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
                            conditionreg?.get("operator")?.value == "==" -> {
                                outcome = lfuncRes.value == rfuncRes.value
                            }
                            conditionreg?.get("operator")?.value == "!=" -> {
                                outcome = lfuncRes.value != rfuncRes.value
                            }
                            else -> {
                                outcome = false
                            }
                        }
                    }
                    lfuncRes is IntegerVar && rfuncRes is IntegerVar -> {
                        when {
                            conditionreg?.get("operator")?.value == "==" -> {
                                outcome = lfuncRes.value == rfuncRes.value
                            }
                            conditionreg?.get("operator")?.value == "<=" -> {
                                outcome = lfuncRes.value <= rfuncRes.value
                            }
                            conditionreg?.get("operator")?.value == ">=" -> {
                                outcome = lfuncRes.value >= rfuncRes.value
                            }
                            conditionreg?.get("operator")?.value == "<" -> {
                                outcome = lfuncRes.value < rfuncRes.value
                            }
                            conditionreg?.get("operator")?.value == ">" -> {
                                outcome = lfuncRes.value > rfuncRes.value
                            }
                            conditionreg?.get("operator")?.value == "!=" -> {
                                outcome = lfuncRes.value != rfuncRes.value
                            }
                            else -> {
                                outcome = false
                            }
                        }
                    }
                    lfuncRes is DoubleVar && rfuncRes is DoubleVar -> {
                        when {
                            conditionreg?.get("operator")?.value == "==" -> {
                                outcome = lfuncRes.value == rfuncRes.value
                            }
                            conditionreg?.get("operator")?.value == "<=" -> {
                                outcome = lfuncRes.value <= rfuncRes.value
                            }
                            conditionreg?.get("operator")?.value == ">=" -> {
                                outcome = lfuncRes.value >= rfuncRes.value
                            }
                            conditionreg?.get("operator")?.value == "<" -> {
                                outcome = lfuncRes.value < rfuncRes.value
                            }
                            conditionreg?.get("operator")?.value == ">" -> {
                                outcome = lfuncRes.value > rfuncRes.value
                            }
                            conditionreg?.get("operator")?.value == "!=" -> {
                                outcome = lfuncRes.value != rfuncRes.value
                            }
                            else -> {
                                outcome = false
                            }
                        }
                    }
                    lfuncRes is BooleanVar && rfuncRes is BooleanVar -> {
                        when {
                            conditionreg?.get("operator")?.value == "==" -> {
                                outcome = lfuncRes.value == rfuncRes.value
                            }
                            conditionreg?.get("operator")?.value == "!=" -> {
                                outcome = lfuncRes.value != rfuncRes.value
                            }
                            else -> {
                                outcome = false
                            }
                        }
                    }
                    else -> {
                        outcome = false
                    }
                }
            } else {
                scope.parser.log.out("neither parts are functions")
                var left = conditionreg?.get("left")?.value ?: ""
                var right = conditionreg?.get("right")?.value ?: ""
                scope.parser.log.out("left: <$left> right: <$right>")
                when {
                    scope.findInScope(left)?.isNotEmpty() ?: false -> {
                        scope.parser.log.out("left is a variable")
                        var leftvar = scope.findInScope(left)?.firstOrNull()
                        when {
                            scope.findInScope(right)?.isNotEmpty() ?: false -> {
                                scope.parser.log.out("right is a variable")
                                var rightvar = scope.findInScope(right)?.firstOrNull()
                                when {
                                    leftvar is StringVar && rightvar is StringVar -> {
                                        when {
                                            conditionreg?.get("operator")?.value == "==" -> {
                                                outcome = leftvar.value == rightvar.value
                                            }
                                            conditionreg?.get("operator")?.value == "!=" -> {
                                                outcome = leftvar.value != rightvar.value
                                            }
                                            else -> {
                                                outcome = false
                                            }
                                        }
                                    }
                                    leftvar is BooleanVar && rightvar is BooleanVar -> {
                                        when {
                                            conditionreg?.get("operator")?.value == "==" -> {
                                                outcome = leftvar.value == rightvar.value
                                            }
                                            conditionreg?.get("operator")?.value == "!=" -> {
                                                outcome = leftvar.value != rightvar.value
                                            }
                                            else -> {
                                                outcome = false
                                            }
                                        }
                                    }
                                    leftvar is DoubleVar -> {
                                        when {
                                            rightvar is DoubleVar -> {
                                                when {
                                                    conditionreg?.get("operator")?.value == "==" -> {
                                                        outcome = leftvar.value == rightvar.value
                                                    }
                                                    conditionreg?.get("operator")?.value == "<=" -> {
                                                        outcome = leftvar.value <= rightvar.value
                                                    }
                                                    conditionreg?.get("operator")?.value == ">=" -> {
                                                        outcome = leftvar.value >= rightvar.value
                                                    }
                                                    conditionreg?.get("operator")?.value == "<"  -> {
                                                        outcome = leftvar.value < rightvar.value
                                                    }
                                                    conditionreg?.get("operator")?.value == ">"  -> {
                                                        outcome = leftvar.value > rightvar.value
                                                    }
                                                    conditionreg?.get("operator")?.value == "!=" -> {
                                                        outcome = leftvar.value != rightvar.value
                                                    }
                                                    else -> {
                                                        outcome = false
                                                    }
                                                }
                                            }
                                            rightvar is IntegerVar -> {
                                                when {
                                                    conditionreg?.get("operator")?.value == "==" -> {
                                                        outcome = false
                                                    }
                                                    conditionreg?.get("operator")?.value == "<=" -> {
                                                        outcome = leftvar.value <= rightvar.value
                                                    }
                                                    conditionreg?.get("operator")?.value == ">=" -> {
                                                        outcome = leftvar.value >= rightvar.value
                                                    }
                                                    conditionreg?.get("operator")?.value == "<"  -> {
                                                        outcome = leftvar.value < rightvar.value
                                                    }
                                                    conditionreg?.get("operator")?.value == ">"  -> {
                                                        outcome = leftvar.value > rightvar.value
                                                    }
                                                    conditionreg?.get("operator")?.value == "!=" -> {
                                                        outcome = true
                                                    }
                                                    else -> {
                                                        outcome = false
                                                    }
                                                }
                                            }
                                            else -> {
                                                outcome = false
                                            }
                                        }
                                    }
                                    leftvar is IntegerVar -> {
                                        when {
                                            rightvar is DoubleVar -> {
                                                when {
                                                    conditionreg?.get("operator")?.value == "==" -> {
                                                        outcome = false
                                                    }
                                                    conditionreg?.get("operator")?.value == "<=" -> {
                                                        outcome = leftvar.value <= rightvar.value
                                                    }
                                                    conditionreg?.get("operator")?.value == ">=" -> {
                                                        outcome = leftvar.value >= rightvar.value
                                                    }
                                                    conditionreg?.get("operator")?.value == "<"  -> {
                                                        outcome = leftvar.value < rightvar.value
                                                    }
                                                    conditionreg?.get("operator")?.value == ">"  -> {
                                                        outcome = leftvar.value > rightvar.value
                                                    }
                                                    conditionreg?.get("operator")?.value == "!=" -> {
                                                        outcome = true
                                                    }
                                                    else -> {
                                                        outcome = false
                                                    }
                                                }
                                            }
                                            rightvar is IntegerVar -> {
                                                when {
                                                    conditionreg?.get("operator")?.value == "==" -> {
                                                        outcome = leftvar.value == rightvar.value
                                                    }
                                                    conditionreg?.get("operator")?.value == "<=" -> {
                                                        outcome = leftvar.value <= rightvar.value
                                                    }
                                                    conditionreg?.get("operator")?.value == ">=" -> {
                                                        outcome = leftvar.value >= rightvar.value
                                                    }
                                                    conditionreg?.get("operator")?.value == "<"  -> {
                                                        outcome = leftvar.value < rightvar.value
                                                    }
                                                    conditionreg?.get("operator")?.value == ">"  -> {
                                                        outcome = leftvar.value > rightvar.value
                                                    }
                                                    conditionreg?.get("operator")?.value == "!=" -> {
                                                        outcome = leftvar.value != rightvar.value
                                                    }
                                                    else -> {
                                                        outcome = false
                                                    }
                                                }
                                            }
                                            else -> {
                                                outcome = false
                                            }
                                        }
                                    }
                                }
                            }
                            right.matches(Regex("(?<bool>true|false)")) -> {
                                scope.parser.log.out("right is a boolean")
                                if (leftvar is BooleanVar) {
                                    when {
                                        conditionreg?.get("operator")?.value == "==" -> {
                                            outcome = leftvar.value == right.toBoolean()
                                        }
                                        conditionreg?.get("operator")?.value == "!=" -> {
                                            outcome = leftvar.value != right.toBoolean()
                                        }
                                        else -> {
                                            outcome = false
                                        }
                                    }
                                }
                            }
                            right.matches(Regex("(\\\"(?<stringa>[^\\\"]+)\\\"|\\'(?<stringb>[^\\']+)\\')")) -> {
                                scope.parser.log.out("right is a string")
                                if (leftvar is StringVar) {
                                    when {
                                        conditionreg?.get("operator")?.value == "==" -> {
                                            outcome = leftvar.value == right.substring(1..right.length - 2)
                                        }
                                        conditionreg?.get("operator")?.value == "!=" -> {
                                            outcome = leftvar.value != right.substring(1..right.length - 2)
                                        }
                                        else -> {
                                            outcome = false
                                        }
                                    }
                                }
                            }
                            right.matches(Regex("(?<double>\\d+\\.\\d+)")) -> {
                                scope.parser.log.out("right is a double")
                                if (leftvar is DoubleVar) {
                                    when {
                                        conditionreg?.get("operator")?.value == "==" -> {
                                            outcome = leftvar.value == right.toDouble()
                                        }
                                        conditionreg?.get("operator")?.value == "!=" -> {
                                            outcome = leftvar.value != right.toDouble()
                                        }
                                        else -> {
                                            outcome = false
                                        }
                                    }
                                }
                            }
                            right.matches(Regex("(?<int>\\d+)")) -> {
                                scope.parser.log.out("right is a integer")
                                if (leftvar is IntegerVar) {
                                    scope.parser.log.out("left <${leftvar.name}> = ${leftvar.value} ${conditionreg?.get("operator")?.value} ${right}")
                                    when {
                                        conditionreg?.get("operator")?.value == "==" -> {
                                            outcome = leftvar.value == right.toInt()
                                        }
                                        conditionreg?.get("operator")?.value == "!=" -> {
                                            outcome = leftvar.value != right.toInt()
                                        }
                                        else -> {
                                            outcome = false
                                        }
                                    }
                                }
                            }
                        }
                    }
                    left.matches(Regex("(?<bool>true|false)")) -> {
                        scope.parser.log.out("left is a boolean")
                        when {
                            scope.findInScope(right)?.isNotEmpty() ?: false -> {
                                scope.parser.log.out("right is a variable")
                                var rightvar = scope.findInScope(right)?.firstOrNull()
                                when {
                                    rightvar is BooleanVar -> {
                                        when {
                                            conditionreg?.get("operator")?.value == "==" -> {
                                                outcome = left.toBoolean() == rightvar.value
                                            }
                                            conditionreg?.get("operator")?.value == "!=" -> {
                                                outcome = left.toBoolean() != rightvar.value
                                            }
                                            else -> {
                                                outcome = false
                                            }
                                        }
                                    }
                                }
                            }
                            right.matches(Regex("(?<bool>true|false)")) -> {
                                scope.parser.log.out("right is a boolean")
                                when {
                                    conditionreg?.get("operator")?.value == "==" -> {
                                        outcome = left.toBoolean() == right.toBoolean()
                                    }
                                    conditionreg?.get("operator")?.value == "!=" -> {
                                        outcome = left.toBoolean() != right.toBoolean()
                                    }
                                    else -> {
                                        outcome = false
                                    }
                                }
                            }
                        }
                    }
                    left.matches(Regex("(\\\"(?<stringa>[^\\\"]+)\\\"|\\'(?<stringb>[^\\']+)\\')")) -> {
                        scope.parser.log.out("left is a string")
                        when {
                            scope.findInScope(right)?.isNotEmpty() ?: false -> {
                                scope.parser.log.out("right is a variable")
                                var rightvar = scope.findInScope(right)?.firstOrNull()
                                when {
                                    rightvar is StringVar -> {
                                        when {
                                            conditionreg?.get("operator")?.value == "==" -> {
                                                outcome = left.substring(1..left.length - 2) == rightvar.value
                                            }
                                            conditionreg?.get("operator")?.value == "!=" -> {
                                                outcome = left.substring(1..left.length - 2) != rightvar.value
                                            }
                                            else -> {
                                                outcome = false
                                            }
                                        }
                                    }
                                }
                            }
                            right.matches(Regex("(\\\"(?<stringa>[^\\\"]+)\\\"|\\'(?<stringb>[^\\']+)\\')")) -> {
                                scope.parser.log.out("right is a string")
                                when {
                                    conditionreg?.get("operator")?.value == "==" -> {
                                        outcome = left.substring(1..left.length - 2) == right
                                    }
                                    conditionreg?.get("operator")?.value == "!=" -> {
                                        outcome = left.substring(1..left.length - 2) != right
                                    }
                                    else -> {
                                        outcome = false
                                    }
                                }
                            }
                        }
                    }
                    left.matches(Regex("(?<double>\\d+\\.\\d+)")) -> {
                        scope.parser.log.out("left is a double")
                        when {
                            scope.findInScope(right)?.isNotEmpty() ?: false -> {
                                scope.parser.log.out("right is a variable")
                                var rightvar = scope.findInScope(right)?.firstOrNull()
                                when {
                                    rightvar is DoubleVar -> {
                                        when {
                                            conditionreg?.get("operator")?.value == "==" -> {
                                                outcome = left.toDouble() == rightvar.value
                                            }
                                            conditionreg?.get("operator")?.value == "<=" -> {
                                                outcome = left.toDouble() <= rightvar.value
                                            }
                                            conditionreg?.get("operator")?.value == ">=" -> {
                                                outcome = left.toDouble() >= rightvar.value
                                            }
                                            conditionreg?.get("operator")?.value == "<" -> {
                                                outcome = left.toDouble() < rightvar.value
                                            }
                                            conditionreg?.get("operator")?.value == ">" -> {
                                                outcome = left.toDouble() > rightvar.value
                                            }
                                            conditionreg?.get("operator")?.value == "!=" -> {
                                                outcome = left.toDouble() != rightvar.value
                                            }
                                        }
                                    }
                                }
                            }
                            right.matches(Regex("(?<double>\\d+\\.\\d+)")) -> {
                                scope.parser.log.out("right is a double")
                                when {
                                    conditionreg?.get("operator")?.value == "==" -> {
                                        outcome = left.toDouble() == right.toDouble()
                                    }
                                    conditionreg?.get("operator")?.value == "<=" -> {
                                        outcome = left.toDouble() <= right.toDouble()
                                    }
                                    conditionreg?.get("operator")?.value == ">=" -> {
                                        outcome = left.toDouble() >= right.toDouble()
                                    }
                                    conditionreg?.get("operator")?.value == "<" -> {
                                        outcome = left.toDouble() < right.toDouble()
                                    }
                                    conditionreg?.get("operator")?.value == ">" -> {
                                        outcome = left.toDouble() > right.toDouble()
                                    }
                                    conditionreg?.get("operator")?.value == "!=" -> {
                                        outcome = left.toDouble() != right.toDouble()
                                    }
                                }
                            }
                        }
                    }
                    left.matches(Regex("(?<int>\\d+)")) -> {
                        scope.parser.log.out("left is a integer")
                        when {
                            scope.findInScope(right)?.isNotEmpty() ?: false -> {
                                scope.parser.log.out("right is a variable")
                                var rightvar = scope.findInScope(right)?.firstOrNull()
                                when {
                                    rightvar is IntegerVar -> {
                                        when {
                                            conditionreg?.get("operator")?.value == "==" -> {
                                                outcome = left.toInt() == rightvar.value
                                            }
                                            conditionreg?.get("operator")?.value == "<=" -> {
                                                outcome = left.toInt() <= rightvar.value
                                            }
                                            conditionreg?.get("operator")?.value == ">=" -> {
                                                outcome = left.toInt() >= rightvar.value
                                            }
                                            conditionreg?.get("operator")?.value == "<" -> {
                                                outcome = left.toInt() < rightvar.value
                                            }
                                            conditionreg?.get("operator")?.value == ">" -> {
                                                outcome = left.toInt() > rightvar.value
                                            }
                                            conditionreg?.get("operator")?.value == "!=" -> {
                                                outcome = left.toInt() != rightvar.value
                                            }
                                        }
                                    }
                                }
                            }
                            right.matches(Regex("(?<int>\\d+)")) -> {
                                scope.parser.log.out("right is a integer")
                                when {
                                    conditionreg?.get("operator")?.value == "==" -> {
                                        outcome = left.toInt() == right.toInt()
                                    }
                                    conditionreg?.get("operator")?.value == "<=" -> {
                                        outcome = left.toInt() <= right.toInt()
                                    }
                                    conditionreg?.get("operator")?.value == ">=" -> {
                                        outcome = left.toInt() >= right.toInt()
                                    }
                                    conditionreg?.get("operator")?.value == "<" -> {
                                        outcome = left.toInt() < right.toInt()
                                    }
                                    conditionreg?.get("operator")?.value == ">" -> {
                                        outcome = left.toInt() > right.toInt()
                                    }
                                    conditionreg?.get("operator")?.value == "!=" -> {
                                        outcome = left.toInt() != right.toInt()
                                    }
                                }
                            }
                        }
                    }
                    else -> {
                        outcome = false
                    }
                }
            }
        } else if (conditionreg?.get("function") != null) {
            scope.parser.log.out("its a function call, i hope it returns something")
            var function = scope.parser.allCode.children.filter { child ->
                child.type == BlockType.FUNCTION
            }.filter { child ->
                (child.structure as uni.ase.assignment.parser.structures.blocks.Function)?.name == conditionreg?.get(
                    "name"
                )?.value
            }.first().structure as uni.ase.assignment.parser.structures.blocks.Function
            var funcRes = function.run(conditionreg?.get("parameters")?.value ?: "")
            if (funcRes is BooleanVar) {
                outcome = funcRes.value
            } else {
                scope.parser.log.error("function ${function.name} does not return a boolean")
            }
        } else if (conditionreg?.get("param") != null) {
            scope.parser.log.out("<${conditionreg?.get("param")?.value ?: ""}> is a single parameter")
            var parameter = Parameter(conditionreg?.get("param")?.value ?: "", null, scope)
            parameter.evaluate()
            outcome = (parameter.result as BooleanVar?)?.value
            scope.parser.log.out("outcome = <${outcome}>")
        } else {
            scope.parser.log.out("idk what $condition is")
            outcome = false
        }
        scope.parser.log.out("outcome = <$outcome>")
    }
}