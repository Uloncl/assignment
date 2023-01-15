package uni.ase.assignment.parser.structures

import uni.ase.assignment.parser.CodeParser
import uni.ase.assignment.parser.structures.blocks.Block
import uni.ase.assignment.parser.structures.variables.*

class Parameter (
    val param : String,
    var result : Variable?,
    val scope : Block
    ) {
    fun evaluate() {
        scope.parser.log.out("lets evaluate $param")
        if (param.contains(Regex("(?<left>[\\w\\\"\\'\\(\\),]+)\\s*(?<operator>\\+|\\-|\\*|/)\\s*(?<right>[\\w\\\"\\'\\(\\),]+)"))) {
            scope.parser.log.out("it maths")
            val operation = Regex("(?<left>[\\w\\\"\\'\\(\\),]+)\\s*(?<operator>\\+|\\-|\\*|/)\\s*(?<right>[\\w\\\"\\'\\(\\),]+)").findAll(param).firstOrNull()?.groups
            operation?.forEach { scope.parser.log.out(it?.value ?: "") }
            val operator = operation?.get(2)?.value ?: ""
            val leftreg = operation?.get(1)?.value ?: ""
            val rightreg = operation?.get(3)?.value ?: ""
//            val operator = ""
//            val leftreg = ""
//            val rightreg = ""
            scope.parser.log.out("${leftreg} - ${operator} - ${rightreg}")
            when (operator) {
                "+" -> {
                    scope.parser.log.out("${leftreg} + ${rightreg}")
                    when {
                        scope.findInScope(leftreg.replace(" ", ""))?.isNotEmpty() ?: false -> {
                            var left = scope.findInScope(leftreg.replace(" ", ""))?.first()
                            when {
                                scope.findInScope(rightreg.replace(" ", ""))?.isNotEmpty() ?: false -> {
                                    val right = scope.findInScope(rightreg.replace(" ", ""))?.first()
                                    when {
                                        left is IntegerVar -> {
                                            when {
                                                right is IntegerVar -> {
                                                    result = IntegerVar(
                                                        name = "result",
                                                        value = left.value + right.value,
                                                        mutable = false,
                                                        scope = scope
                                                    )
                                                }
                                                right is DoubleVar -> {
                                                    result = DoubleVar(
                                                        name = "result",
                                                        value = left.value + right.value,
                                                        mutable = false,
                                                        scope = scope
                                                    )
                                                }
                                            }
                                        }
                                        left is DoubleVar -> {
                                            when {
                                                right is IntegerVar -> {
                                                    result = DoubleVar(
                                                        name = "result",
                                                        value = left.value + right.value,
                                                        mutable = false,
                                                        scope = scope
                                                    )
                                                }
                                                right is DoubleVar -> {
                                                    result = DoubleVar(
                                                        name = "result",
                                                        value = left.value + right.value,
                                                        mutable = false,
                                                        scope = scope
                                                    )
                                                }
                                            }
                                        }
                                        left is StringVar -> {
                                            when {
                                                right is StringVar -> {
                                                    result = StringVar(
                                                        name = "result",
                                                        value = "${left.value}${right.value}",
                                                        mutable = false,
                                                        scope = scope
                                                    )
                                                }
                                            }
                                        }
                                    }
                                }
                                rightreg.matches(Regex("(\\\"(?<stringa>[^\\\"]+)\\\"|\\'(?<stringb>[^\\']+)\\')")) -> {
                                    if (left is StringVar) {
                                        result = StringVar(
                                            name = "result",
                                            value = "${left.value}${(rightreg.substring(1..rightreg.length - 2))}",
                                            mutable = false,
                                            scope = scope
                                        )
                                    }
                                }
                                rightreg.matches(Regex("(?<double>\\d+\\.\\d+)")) -> {
                                    if (left is DoubleVar) {
                                        result = DoubleVar(
                                            name = "result",
                                            value = left.value + (rightreg?.toDouble() ?: 0.0),
                                            mutable = false,
                                            scope = scope
                                        )
                                    } else if (left is IntegerVar) {
                                        result = DoubleVar(
                                            name = "result",
                                            value = left.value + (rightreg?.toDouble() ?: 0.0),
                                            mutable = false,
                                            scope = scope
                                        )
                                    }
                                }
                                rightreg.matches(Regex("(?<int>\\d+)")) -> {
                                    if (left is DoubleVar) {
                                        result = DoubleVar(
                                            name = "result",
                                            value = left.value + (rightreg?.toInt() ?: 0),
                                            mutable = false,
                                            scope = scope
                                        )
                                    } else if (left is IntegerVar) {
                                        scope.parser.log.out("${left.value} + ${rightreg?.toInt()}")
                                        result = IntegerVar(
                                            name = "result",
                                            value = left.value + (rightreg?.toInt() ?: 0),
                                            mutable = false,
                                            scope = scope
                                        )
                                        scope.parser.log.out("result = ${(result as IntegerVar).value}")
                                    }
                                }
                            }
                        }
                        leftreg.matches(Regex("(\\\"(?<stringa>[^\\\"]+)\\\"|\\'(?<stringb>[^\\']+)\\')")) -> {
                            when {
                                scope.findInScope(rightreg.replace(" ", ""))?.isNotEmpty() ?: false -> {
                                    val right = scope.findInScope(rightreg.replace(" ", ""))?.first()
                                    when {
                                        right is StringVar -> {
                                            result = StringVar(
                                                name = "result",
                                                value = "${(leftreg.substring(1..rightreg.length - 2))}${right.value}",
                                                mutable = false,
                                                scope = scope
                                            )
                                        }
                                    }
                                }
                                rightreg.matches(Regex("(\\\"(?<stringa>[^\\\"]+)\\\"|\\'(?<stringb>[^\\']+)\\')")) -> {
                                    result = StringVar(
                                        name = "result",
                                        value = "${(leftreg.substring(1..rightreg.length - 2))}${(rightreg.substring(1..rightreg.length - 2))}",
                                        mutable = false,
                                        scope = scope
                                    )
                                }
                            }
                        }
                        leftreg.matches(Regex("(?<double>\\d+\\.\\d+)")) -> {
                            when {
                                rightreg.matches(Regex("(?<double>\\d+\\.\\d+)")) -> {
                                    result = DoubleVar(
                                        name = "result",
                                        value = leftreg.toDouble() + rightreg.toDouble(),
                                        mutable = false,
                                        scope = scope
                                    )
                                }
                                rightreg.matches(Regex("(?<int>\\d+)")) -> {
                                    result = DoubleVar(
                                        name = "result",
                                        value = leftreg.toDouble() + rightreg.toInt(),
                                        mutable = false,
                                        scope = scope
                                    )
                                }
                            }
                        }
                        leftreg.matches(Regex("(?<int>\\d+)")) -> {
                            when {
                                rightreg.matches(Regex("(?<double>\\d+\\.\\d+)")) -> {
                                    result = DoubleVar(
                                        name = "result",
                                        value = leftreg.toInt() + rightreg.toDouble(),
                                        mutable = false,
                                        scope = scope
                                    )
                                }
                                rightreg.matches(Regex("(?<int>\\d+)")) -> {
                                    scope.parser.log.out("theyre both ints")
                                    result = IntegerVar(
                                        name = "result",
                                        value = leftreg.toInt() + rightreg.toInt(),
                                        mutable = false,
                                        scope = scope
                                    )
                                }
                            }
                        }
                    }
                }
                "-" -> {
                    when {
                        scope.findInScope(leftreg.replace(" ", ""))?.isNotEmpty() ?: false -> {
                            var left = scope.findInScope(leftreg.replace(" ", ""))?.first()
                            when {
                                scope.findInScope(rightreg.replace(" ", ""))?.isNotEmpty() ?: false -> {
                                    val right = scope.findInScope(rightreg.replace(" ", ""))?.first()
                                    when {
                                        left is IntegerVar -> {
                                            when {
                                                right is IntegerVar -> {
                                                    result = IntegerVar(
                                                        name = "result",
                                                        value = left.value - right.value,
                                                        mutable = false,
                                                        scope = scope
                                                    )
                                                }
                                                right is DoubleVar -> {
                                                    result = DoubleVar(
                                                        name = "result",
                                                        value = left.value - right.value,
                                                        mutable = false,
                                                        scope = scope
                                                    )
                                                }
                                            }
                                        }
                                        left is DoubleVar -> {
                                            when {
                                                right is IntegerVar -> {
                                                    result = DoubleVar(
                                                        name = "result",
                                                        value = left.value - right.value,
                                                        mutable = false,
                                                        scope = scope
                                                    )
                                                }
                                                right is DoubleVar -> {
                                                    result = DoubleVar(
                                                        name = "result",
                                                        value = left.value - right.value,
                                                        mutable = false,
                                                        scope = scope
                                                    )
                                                }
                                            }
                                        }
                                    }
                                }
                                rightreg.matches(Regex("(?<double>\\d+\\.\\d+)")) -> {
                                    if (left is DoubleVar) {
                                        result = DoubleVar(
                                            name = "result",
                                            value = left.value - (rightreg?.toDouble() ?: 0.0),
                                            mutable = false,
                                            scope = scope
                                        )
                                    } else if (left is IntegerVar) {
                                        result = DoubleVar(
                                            name = "result",
                                            value = left.value - (rightreg?.toDouble() ?: 0.0),
                                            mutable = false,
                                            scope = scope
                                        )
                                    }
                                }
                                rightreg.matches(Regex("(?<int>\\d+)")) -> {
                                    if (left is DoubleVar) {
                                        result = DoubleVar(
                                            name = "result",
                                            value = left.value - (rightreg?.toInt() ?: 0),
                                            mutable = false,
                                            scope = scope
                                        )
                                    } else if (left is IntegerVar) {
                                        result = IntegerVar(
                                            name = "result",
                                            value = left.value - (rightreg?.toInt() ?: 0),
                                            mutable = false,
                                            scope = scope
                                        )
                                    }
                                }
                            }
                        }
                        leftreg.matches(Regex("(?<double>\\d+\\.\\d+)")) -> {
                            when {
                                rightreg.matches(Regex("(?<double>\\d+\\.\\d+)")) -> {
                                    result = DoubleVar(
                                        name = "result",
                                        value = (leftreg?.toDouble() ?: 0.0) - (rightreg?.toDouble() ?: 0.0),
                                        mutable = false,
                                        scope = scope
                                    )
                                }
                                rightreg.matches(Regex("(?<int>\\d+)")) -> {
                                    result = DoubleVar(
                                        name = "result",
                                        value = (leftreg?.toDouble() ?: 0.0) - (rightreg?.toInt() ?: 0),
                                        mutable = false,
                                        scope = scope
                                    )
                                }
                            }
                        }
                        leftreg.matches(Regex("(?<int>\\d+)")) -> {
                            when {
                                rightreg.matches(Regex("(?<double>\\d+\\.\\d+)")) -> {
                                    result = DoubleVar(
                                        name = "result",
                                        value = (leftreg?.toInt() ?: 0) - (rightreg?.toDouble() ?: 0.0),
                                        mutable = false,
                                        scope = scope
                                    )
                                }
                                rightreg.matches(Regex("(?<int>\\d+)")) -> {
                                    result = IntegerVar(
                                        name = "result",
                                        value = (leftreg?.toInt() ?: 0) - (rightreg?.toInt() ?: 0),
                                        mutable = false,
                                        scope = scope
                                    )
                                }
                            }
                        }
                    }
                }
                "*" -> {
                    when {
                        scope.findInScope(leftreg.replace(" ", ""))?.isNotEmpty() ?: false -> {
                            var left = scope.findInScope(leftreg.replace(" ", ""))?.first()
                            when {
                                scope.findInScope(rightreg.replace(" ", ""))?.isNotEmpty() ?: false -> {
                                    val right = scope.findInScope(rightreg.replace(" ", ""))?.first()
                                    when {
                                        left is IntegerVar -> {
                                            when {
                                                right is IntegerVar -> {
                                                    result = IntegerVar(
                                                        name = "result",
                                                        value = left.value * right.value,
                                                        mutable = false,
                                                        scope = scope
                                                    )
                                                }
                                                right is DoubleVar -> {
                                                    result = DoubleVar(
                                                        name = "result",
                                                        value = left.value * right.value,
                                                        mutable = false,
                                                        scope = scope
                                                    )
                                                }
                                            }
                                        }
                                        left is DoubleVar -> {
                                            when {
                                                right is IntegerVar -> {
                                                    result = DoubleVar(
                                                        name = "result",
                                                        value = left.value * right.value,
                                                        mutable = false,
                                                        scope = scope
                                                    )
                                                }
                                                right is DoubleVar -> {
                                                    result = DoubleVar(
                                                        name = "result",
                                                        value = left.value * right.value,
                                                        mutable = false,
                                                        scope = scope
                                                    )
                                                }
                                            }
                                        }
                                    }
                                }
                                rightreg.matches(Regex("(?<double>\\d+\\.\\d+)")) -> {
                                    if (left is DoubleVar) {
                                        result = DoubleVar(
                                            name = "result",
                                            value = left.value * (rightreg?.toDouble() ?: 0.0),
                                            mutable = false,
                                            scope = scope
                                        )
                                    } else if (left is IntegerVar) {
                                        result = DoubleVar(
                                            name = "result",
                                            value = left.value * (rightreg?.toDouble() ?: 0.0),
                                            mutable = false,
                                            scope = scope
                                        )
                                    }
                                }
                                rightreg.matches(Regex("(?<int>\\d+)")) -> {
                                    if (left is DoubleVar) {
                                        result = DoubleVar(
                                            name = "result",
                                            value = left.value * (rightreg?.toInt() ?: 0),
                                            mutable = false,
                                            scope = scope
                                        )
                                    } else if (left is IntegerVar) {
                                        result = IntegerVar(
                                            name = "result",
                                            value = left.value * (rightreg?.toInt() ?: 0),
                                            mutable = false,
                                            scope = scope
                                        )
                                    }
                                }
                            }
                        }
                        leftreg.matches(Regex("(?<double>\\d+\\.\\d+)")) -> {
                            when {
                                rightreg.matches(Regex("(?<double>\\d+\\.\\d+)")) -> {
                                    result = DoubleVar(
                                        name = "result",
                                        value = (leftreg?.toDouble() ?: 0.0) * (rightreg?.toDouble() ?: 0.0),
                                        mutable = false,
                                        scope = scope
                                    )
                                }
                                rightreg.matches(Regex("(?<int>\\d+)")) -> {
                                    result = DoubleVar(
                                        name = "result",
                                        value = (leftreg?.toDouble() ?: 0.0) * (rightreg?.toInt() ?: 0),
                                        mutable = false,
                                        scope = scope
                                    )
                                }
                            }
                        }
                        leftreg.matches(Regex("(?<int>\\d+)")) -> {
                            when {
                                rightreg.matches(Regex("(?<double>\\d+\\.\\d+)")) -> {
                                    result = DoubleVar(
                                        name = "result",
                                        value = (leftreg?.toInt() ?: 0) * (rightreg?.toDouble() ?: 0.0),
                                        mutable = false,
                                        scope = scope
                                    )
                                }
                                rightreg.matches(Regex("(?<int>\\d+)")) -> {
                                    result = IntegerVar(
                                        name = "result",
                                        value = (leftreg?.toInt() ?: 0) * (rightreg?.toInt() ?: 0),
                                        mutable = false,
                                        scope = scope
                                    )
                                }
                            }
                        }
                    }
                }
                "/" -> {
                    when {
                        scope.findInScope(leftreg.replace(" ", ""))?.isNotEmpty() ?: false -> {
                            var left = scope.findInScope(leftreg.replace(" ", ""))?.first()
                            when {
                                scope.findInScope(rightreg.replace(" ", ""))?.isNotEmpty() ?: false -> {
                                    val right = scope.findInScope(rightreg.replace(" ", ""))?.first()
                                    when {
                                        left is IntegerVar -> {
                                            when {
                                                right is IntegerVar -> {
                                                    result = IntegerVar(
                                                        name = "result",
                                                        value = left.value / right.value,
                                                        mutable = false,
                                                        scope = scope
                                                    )
                                                }
                                                right is DoubleVar -> {
                                                    result = DoubleVar(
                                                        name = "result",
                                                        value = left.value / right.value,
                                                        mutable = false,
                                                        scope = scope
                                                    )
                                                }
                                            }
                                        }
                                        left is DoubleVar -> {
                                            when {
                                                right is IntegerVar -> {
                                                    result = DoubleVar(
                                                        name = "result",
                                                        value = left.value / right.value,
                                                        mutable = false,
                                                        scope = scope
                                                    )
                                                }
                                                right is DoubleVar -> {
                                                    result = DoubleVar(
                                                        name = "result",
                                                        value = left.value / right.value,
                                                        mutable = false,
                                                        scope = scope
                                                    )
                                                }
                                            }
                                        }
                                    }
                                }
                                rightreg.matches(Regex("(?<double>\\d+\\.\\d+)")) -> {
                                    if (left is DoubleVar) {
                                        result = DoubleVar(
                                            name = "result",
                                            value = left.value / (rightreg?.toDouble() ?: 0.0),
                                            mutable = false,
                                            scope = scope
                                        )
                                    } else if (left is IntegerVar) {
                                        result = DoubleVar(
                                            name = "result",
                                            value = left.value / (rightreg?.toDouble() ?: 0.0),
                                            mutable = false,
                                            scope = scope
                                        )
                                    }
                                }
                                rightreg.matches(Regex("(?<int>\\d+)")) -> {
                                    if (left is DoubleVar) {
                                        result = DoubleVar(
                                            name = "result",
                                            value = left.value / (rightreg?.toInt() ?: 0),
                                            mutable = false,
                                            scope = scope
                                        )
                                    } else if (left is IntegerVar) {
                                        result = IntegerVar(
                                            name = "result",
                                            value = left.value / (rightreg?.toInt() ?: 0),
                                            mutable = false,
                                            scope = scope
                                        )
                                    }
                                }
                            }
                        }
                        leftreg.matches(Regex("(?<double>\\d+\\.\\d+)")) -> {
                            when {
                                rightreg.matches(Regex("(?<double>\\d+\\.\\d+)")) -> {
                                    result = DoubleVar(
                                        name = "result",
                                        value = (leftreg?.toDouble() ?: 0.0) / (rightreg?.toDouble() ?: 0.0),
                                        mutable = false,
                                        scope = scope
                                    )
                                }
                                rightreg.matches(Regex("(?<int>\\d+)")) -> {
                                    result = DoubleVar(
                                        name = "result",
                                        value = (leftreg?.toDouble() ?: 0.0) / (rightreg?.toInt() ?: 0),
                                        mutable = false,
                                        scope = scope
                                    )
                                }
                            }
                        }
                        leftreg.matches(Regex("(?<int>\\d+)")) -> {
                            val left = Regex("(?<int>\\d+)").find(leftreg)?.groupValues as? MatchNamedGroupCollection
                            when {
                                rightreg.matches(Regex("(?<double>\\d+\\.\\d+)")) -> {
                                    val right = Regex("(?<double>\\d+\\.\\d+)").find(rightreg)?.groupValues as? MatchNamedGroupCollection
                                    result = DoubleVar(
                                        name = "result",
                                        value = (leftreg?.toInt() ?: 0) / (rightreg?.toDouble() ?: 0.0),
                                        mutable = false,
                                        scope = scope
                                    )
                                }
                                rightreg.matches(Regex("(?<int>\\d+)")) -> {
                                    val right = Regex("(?<int>\\d+)").find(rightreg)?.groupValues as? MatchNamedGroupCollection
                                    result = IntegerVar(
                                        name = "result",
                                        value = (leftreg?.toInt() ?: 0) / (rightreg?.toInt() ?: 0),
                                        mutable = false,
                                        scope = scope
                                    )
                                }
                            }
                        }
                    }
                }
            }
        } else {
            scope.parser.log.out("<${param}> is a single parameter a single value")
            when {
                scope.findInScope(param.filterNot { it.isWhitespace() })?.isNotEmpty() ?: false -> {
                    scope.parser.log.out("its a variable")
                    result = scope.findInScope(param.filterNot { it.isWhitespace() })?.first()
                    scope.parser.log.out("its a variable called ${result?.name ?: "UNKNOWN"}")
                }
                param?.matches(Regex("(?<bool>true|false)")) ?: false -> {
                    scope.parser.log.out("<${param}> is a boolean")
                    result = BooleanVar(
                        name = "tempparam",
                        value = param.toBoolean() ?: false,
                        mutable = false,
                        scope = scope
                    )
                    scope.parser.log.out("result = <$result>")
                }
                param?.matches(Regex("(\\\"(?<stringa>[^\\\"]+)\\\"|\\'(?<stringb>[^\\']+)\\')")) ?: false -> {
                    scope.parser.log.out("its a string")
                    result = StringVar(
                        name = "tempparam",
                        value = param.substring(1..param.length - 2) ?: "",
                        mutable = false,
                        scope = scope
                    )
                }
                param?.matches(Regex("(?<double>\\d+\\.\\d+)")) ?: false -> {
                    scope.parser.log.out("its a double")
                    result = DoubleVar(
                        name = "tempparam",
                        value = param.toDouble() ?: 0.0,
                        mutable = false,
                        scope = scope
                    )
                }
                param?.matches(Regex("(?<int>\\d+)")) ?: false -> {
                    scope.parser.log.out("<$param> is an integer")
                    result = IntegerVar(
                        name = "tempparam",
                        value = param.toInt(),
                        mutable = false,
                        scope = scope
                    )
                }
            }
        }
        scope.parser.log.out("result = <$result>")
    }
}