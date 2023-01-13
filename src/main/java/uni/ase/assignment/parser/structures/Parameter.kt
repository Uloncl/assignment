package uni.ase.assignment.parser.structures

import uni.ase.assignment.parser.CodeParser
import uni.ase.assignment.parser.structures.blocks.Block
import uni.ase.assignment.parser.structures.variables.*

class Parameter (
    val param : String,
    var result : Variable?,
    val scope : Block,
    val parser : CodeParser
    ) {
    fun evaluate() {
        if (param.contains("(?<left>[\\w\\\"\\'\\(\\),]+)\\s*(?<operator>\\+|\\-|\\*|/)\\s*(?<right>[\\w\\\"\\'\\(\\),]+)")) {
            val operation = Regex("(?<left>[\\w\\\"\\'\\(\\),]+)\\s*(?<operator>\\+|\\-|\\*|/)\\s*(?<right>[\\w\\\"\\'\\(\\),]+)").find(param)?.groupValues as? MatchNamedGroupCollection
            val operator = operation?.get("operator")?.value ?: ""
            val leftreg = operation?.get("left")?.value ?: ""
            val rightreg = operation?.get("right")?.value ?: ""
            when (operator) {
                "+" -> {
                    when {
                        scope.findInScope(leftreg.replace(" ", ""))?.isNotEmpty() ?: false -> {
                            var left = scope.findInScope(leftreg.replace(" ", ""))?.first()!!
                            when {
                                scope.findInScope(rightreg.replace(" ", ""))?.isNotEmpty() ?: false -> {
                                    val right = scope.findInScope(rightreg.replace(" ", ""))?.first()!!
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
                                rightreg!!.matches(Regex("(\\\"(?<stringa>[^\\\"]+)\\\"|\\'(?<stringb>[^\\']+)\\')")) -> {
                                    if (left is StringVar) {
                                        val str = Regex("(\\\"(?<stringa>[^\\\"]+)\\\"|\\'(?<stringb>[^\\']+)\\')").find(rightreg)?.groupValues as? MatchNamedGroupCollection
                                        result = StringVar(
                                            name = "result",
                                            value = "${left.value}${(str?.get("stringa")?.value ?: str?.get("stringb")?.value ?: "")}",
                                            mutable = false,
                                            scope = scope
                                        )
                                    }
                                }
                                rightreg!!.matches(Regex("(?<double>\\d+\\.\\d+)")) -> {
                                    if (left is DoubleVar) {
                                        val double = Regex("(?<double>\\d+\\.\\d+)").find(rightreg)?.groupValues as? MatchNamedGroupCollection
                                        result = DoubleVar(
                                            name = "result",
                                            value = left.value + double?.get("double")!!.value.toDouble(),
                                            mutable = false,
                                            scope = scope
                                        )
                                    } else if (left is IntegerVar) {
                                        val double = Regex("(?<double>\\d+\\.\\d+)").find(rightreg)?.groupValues as? MatchNamedGroupCollection
                                        result = DoubleVar(
                                            name = "result",
                                            value = left.value + double?.get("double")!!.value.toDouble(),
                                            mutable = false,
                                            scope = scope
                                        )
                                    }
                                }
                                rightreg!!.matches(Regex("(?<int>\\d+)")) -> {
                                    if (left is DoubleVar) {
                                        val inte = Regex("(?<int>\\d+)").find(rightreg)?.groupValues as? MatchNamedGroupCollection
                                        result = DoubleVar(
                                            name = "result",
                                            value = left.value + inte?.get("int")!!.value.toInt(),
                                            mutable = false,
                                            scope = scope
                                        )
                                    } else if (left is IntegerVar) {
                                        val inte = Regex("(?<int>\\d+)").find(rightreg)?.groupValues as? MatchNamedGroupCollection
                                        result = IntegerVar(
                                            name = "result",
                                            value = left.value + inte?.get("int")!!.value.toInt(),
                                            mutable = false,
                                            scope = scope
                                        )
                                    }
                                }
                            }
                        }
                        leftreg!!.matches(Regex("(\\\"(?<stringa>[^\\\"]+)\\\"|\\'(?<stringb>[^\\']+)\\')")) -> {
                            val left = Regex("(\\\"(?<stringa>[^\\\"]+)\\\"|\\'(?<stringb>[^\\']+)\\')").find(rightreg)?.groupValues as? MatchNamedGroupCollection
                            val leftval = left?.get("stringa")?.value ?: left?.get("stringb")?.value ?: ""
                            when {
                                scope.findInScope(rightreg.replace(" ", ""))?.isNotEmpty() ?: false -> {
                                    val right = scope.findInScope(rightreg.replace(" ", ""))?.first()!!
                                    when {
                                        right is StringVar -> {
                                            result = StringVar(
                                                name = "result",
                                                value = "$leftval${right.value}",
                                                mutable = false,
                                                scope = scope
                                            )
                                        }
                                    }
                                }
                                rightreg!!.matches(Regex("(\\\"(?<stringa>[^\\\"]+)\\\"|\\'(?<stringb>[^\\']+)\\')")) -> {
                                    val right =
                                        Regex("(\\\"(?<stringa>[^\\\"]+)\\\"|\\'(?<stringb>[^\\']+)\\')").find(rightreg)?.groupValues as? MatchNamedGroupCollection
                                    result = StringVar(
                                        name = "result",
                                        value = "$leftval${(right?.get("stringa")?.value ?: right?.get("stringb")?.value ?: "")}",
                                        mutable = false,
                                        scope = scope
                                    )
                                }
                            }
                        }
                        leftreg!!.matches(Regex("(?<double>\\d+\\.\\d+)")) -> {
                            val left = Regex("(?<double>\\d+\\.\\d+)").find(rightreg)?.groupValues as? MatchNamedGroupCollection
                            when {
                                rightreg!!.matches(Regex("(?<double>\\d+\\.\\d+)")) -> {
                                    val right = Regex("(?<double>\\d+\\.\\d+)").find(rightreg)?.groupValues as? MatchNamedGroupCollection
                                    result = DoubleVar(
                                        name = "result",
                                        value = left?.get("double")!!.value.toDouble() + right?.get("double")!!.value.toDouble(),
                                        mutable = false,
                                        scope = scope
                                    )
                                }
                                rightreg!!.matches(Regex("(?<int>\\d+)")) -> {
                                    val right = Regex("(?<int>\\d+)").find(rightreg)?.groupValues as? MatchNamedGroupCollection
                                    result = DoubleVar(
                                        name = "result",
                                        value = left?.get("double")!!.value.toDouble() + right?.get("int")!!.value.toInt(),
                                        mutable = false,
                                        scope = scope
                                    )
                                }
                            }
                        }
                        leftreg!!.matches(Regex("(?<int>\\d+)")) -> {
                            val left = Regex("(?<int>\\d+)").find(leftreg)?.groupValues as? MatchNamedGroupCollection
                            when {
                                rightreg!!.matches(Regex("(?<double>\\d+\\.\\d+)")) -> {
                                    val right = Regex("(?<double>\\d+\\.\\d+)").find(rightreg)?.groupValues as? MatchNamedGroupCollection
                                    result = DoubleVar(
                                        name = "result",
                                        value = left?.get("int")!!.value.toInt() + right?.get("double")!!.value.toDouble(),
                                        mutable = false,
                                        scope = scope
                                    )
                                }
                                rightreg!!.matches(Regex("(?<int>\\d+)")) -> {
                                    val right = Regex("(?<int>\\d+)").find(rightreg)?.groupValues as? MatchNamedGroupCollection
                                    result = IntegerVar(
                                        name = "result",
                                        value = left?.get("int")!!.value.toInt() + right?.get("int")!!.value.toInt(),
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
                            var left = scope.findInScope(leftreg.replace(" ", ""))?.first()!!
                            when {
                                scope.findInScope(rightreg.replace(" ", ""))?.isNotEmpty() ?: false -> {
                                    val right = scope.findInScope(rightreg.replace(" ", ""))?.first()!!
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
                                rightreg!!.matches(Regex("(?<double>\\d+\\.\\d+)")) -> {
                                    if (left is DoubleVar) {
                                        val double = Regex("(?<double>\\d+\\.\\d+)").find(rightreg)?.groupValues as? MatchNamedGroupCollection
                                        result = DoubleVar(
                                            name = "result",
                                            value = left.value - double?.get("double")!!.value.toDouble(),
                                            mutable = false,
                                            scope = scope
                                        )
                                    } else if (left is IntegerVar) {
                                        val double = Regex("(?<double>\\d+\\.\\d+)").find(rightreg)?.groupValues as? MatchNamedGroupCollection
                                        result = DoubleVar(
                                            name = "result",
                                            value = left.value - double?.get("double")!!.value.toDouble(),
                                            mutable = false,
                                            scope = scope
                                        )
                                    }
                                }
                                rightreg!!.matches(Regex("(?<int>\\d+)")) -> {
                                    if (left is DoubleVar) {
                                        val inte = Regex("(?<int>\\d+)").find(rightreg)?.groupValues as? MatchNamedGroupCollection
                                        result = DoubleVar(
                                            name = "result",
                                            value = left.value - inte?.get("int")!!.value.toInt(),
                                            mutable = false,
                                            scope = scope
                                        )
                                    } else if (left is IntegerVar) {
                                        val inte = Regex("(?<int>\\d+)").find(rightreg)?.groupValues as? MatchNamedGroupCollection
                                        result = IntegerVar(
                                            name = "result",
                                            value = left.value - inte?.get("int")!!.value.toInt(),
                                            mutable = false,
                                            scope = scope
                                        )
                                    }
                                }
                            }
                        }
                        leftreg!!.matches(Regex("(?<double>\\d+\\.\\d+)")) -> {
                            val left = Regex("(?<double>\\d+\\.\\d+)").find(rightreg)?.groupValues as? MatchNamedGroupCollection
                            when {
                                rightreg!!.matches(Regex("(?<double>\\d+\\.\\d+)")) -> {
                                    val right = Regex("(?<double>\\d+\\.\\d+)").find(rightreg)?.groupValues as? MatchNamedGroupCollection
                                    result = DoubleVar(
                                        name = "result",
                                        value = left?.get("double")!!.value.toDouble() - right?.get("double")!!.value.toDouble(),
                                        mutable = false,
                                        scope = scope
                                    )
                                }
                                rightreg!!.matches(Regex("(?<int>\\d+)")) -> {
                                    val right = Regex("(?<int>\\d+)").find(rightreg)?.groupValues as? MatchNamedGroupCollection
                                    result = DoubleVar(
                                        name = "result",
                                        value = left?.get("double")!!.value.toDouble() - right?.get("int")!!.value.toInt(),
                                        mutable = false,
                                        scope = scope
                                    )
                                }
                            }
                        }
                        leftreg!!.matches(Regex("(?<int>\\d+)")) -> {
                            val left = Regex("(?<int>\\d+)").find(leftreg)?.groupValues as? MatchNamedGroupCollection
                            when {
                                rightreg!!.matches(Regex("(?<double>\\d+\\.\\d+)")) -> {
                                    val right = Regex("(?<double>\\d+\\.\\d+)").find(rightreg)?.groupValues as? MatchNamedGroupCollection
                                    result = DoubleVar(
                                        name = "result",
                                        value = left?.get("int")!!.value.toInt() - right?.get("double")!!.value.toDouble(),
                                        mutable = false,
                                        scope = scope
                                    )
                                }
                                rightreg!!.matches(Regex("(?<int>\\d+)")) -> {
                                    val right = Regex("(?<int>\\d+)").find(rightreg)?.groupValues as? MatchNamedGroupCollection
                                    result = IntegerVar(
                                        name = "result",
                                        value = left?.get("int")!!.value.toInt() - right?.get("int")!!.value.toInt(),
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
                            var left = scope.findInScope(leftreg.replace(" ", ""))?.first()!!
                            when {
                                scope.findInScope(rightreg.replace(" ", ""))?.isNotEmpty() ?: false -> {
                                    val right = scope.findInScope(rightreg.replace(" ", ""))?.first()!!
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
                                rightreg!!.matches(Regex("(?<double>\\d+\\.\\d+)")) -> {
                                    if (left is DoubleVar) {
                                        val double = Regex("(?<double>\\d+\\.\\d+)").find(rightreg)?.groupValues as? MatchNamedGroupCollection
                                        result = DoubleVar(
                                            name = "result",
                                            value = left.value * double?.get("double")!!.value.toDouble(),
                                            mutable = false,
                                            scope = scope
                                        )
                                    } else if (left is IntegerVar) {
                                        val double = Regex("(?<double>\\d+\\.\\d+)").find(rightreg)?.groupValues as? MatchNamedGroupCollection
                                        result = DoubleVar(
                                            name = "result",
                                            value = left.value * double?.get("double")!!.value.toDouble(),
                                            mutable = false,
                                            scope = scope
                                        )
                                    }
                                }
                                rightreg!!.matches(Regex("(?<int>\\d+)")) -> {
                                    if (left is DoubleVar) {
                                        val inte = Regex("(?<int>\\d+)").find(rightreg)?.groupValues as? MatchNamedGroupCollection
                                        result = DoubleVar(
                                            name = "result",
                                            value = left.value * inte?.get("int")!!.value.toInt(),
                                            mutable = false,
                                            scope = scope
                                        )
                                    } else if (left is IntegerVar) {
                                        val inte = Regex("(?<int>\\d+)").find(rightreg)?.groupValues as? MatchNamedGroupCollection
                                        result = IntegerVar(
                                            name = "result",
                                            value = left.value * inte?.get("int")!!.value.toInt(),
                                            mutable = false,
                                            scope = scope
                                        )
                                    }
                                }
                            }
                        }
                        leftreg!!.matches(Regex("(?<double>\\d+\\.\\d+)")) -> {
                            val left = Regex("(?<double>\\d+\\.\\d+)").find(rightreg)?.groupValues as? MatchNamedGroupCollection
                            when {
                                rightreg!!.matches(Regex("(?<double>\\d+\\.\\d+)")) -> {
                                    val right = Regex("(?<double>\\d+\\.\\d+)").find(rightreg)?.groupValues as? MatchNamedGroupCollection
                                    result = DoubleVar(
                                        name = "result",
                                        value = left?.get("double")!!.value.toDouble() * right?.get("double")!!.value.toDouble(),
                                        mutable = false,
                                        scope = scope
                                    )
                                }
                                rightreg!!.matches(Regex("(?<int>\\d+)")) -> {
                                    val right = Regex("(?<int>\\d+)").find(rightreg)?.groupValues as? MatchNamedGroupCollection
                                    result = DoubleVar(
                                        name = "result",
                                        value = left?.get("double")!!.value.toDouble() * right?.get("int")!!.value.toInt(),
                                        mutable = false,
                                        scope = scope
                                    )
                                }
                            }
                        }
                        leftreg!!.matches(Regex("(?<int>\\d+)")) -> {
                            val left = Regex("(?<int>\\d+)").find(leftreg)?.groupValues as? MatchNamedGroupCollection
                            when {
                                rightreg!!.matches(Regex("(?<double>\\d+\\.\\d+)")) -> {
                                    val right = Regex("(?<double>\\d+\\.\\d+)").find(rightreg)?.groupValues as? MatchNamedGroupCollection
                                    result = DoubleVar(
                                        name = "result",
                                        value = left?.get("int")!!.value.toInt() * right?.get("double")!!.value.toDouble(),
                                        mutable = false,
                                        scope = scope
                                    )
                                }
                                rightreg!!.matches(Regex("(?<int>\\d+)")) -> {
                                    val right = Regex("(?<int>\\d+)").find(rightreg)?.groupValues as? MatchNamedGroupCollection
                                    result = IntegerVar(
                                        name = "result",
                                        value = left?.get("int")!!.value.toInt() * right?.get("int")!!.value.toInt(),
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
                            var left = scope.findInScope(leftreg.replace(" ", ""))?.first()!!
                            when {
                                scope.findInScope(rightreg.replace(" ", ""))?.isNotEmpty() ?: false -> {
                                    val right = scope.findInScope(rightreg.replace(" ", ""))?.first()!!
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
                                rightreg!!.matches(Regex("(?<double>\\d+\\.\\d+)")) -> {
                                    if (left is DoubleVar) {
                                        val double = Regex("(?<double>\\d+\\.\\d+)").find(rightreg)?.groupValues as? MatchNamedGroupCollection
                                        result = DoubleVar(
                                            name = "result",
                                            value = left.value / double?.get("double")!!.value.toDouble(),
                                            mutable = false,
                                            scope = scope
                                        )
                                    } else if (left is IntegerVar) {
                                        val double = Regex("(?<double>\\d+\\.\\d+)").find(rightreg)?.groupValues as? MatchNamedGroupCollection
                                        result = DoubleVar(
                                            name = "result",
                                            value = left.value / double?.get("double")!!.value.toDouble(),
                                            mutable = false,
                                            scope = scope
                                        )
                                    }
                                }
                                rightreg!!.matches(Regex("(?<int>\\d+)")) -> {
                                    if (left is DoubleVar) {
                                        val inte = Regex("(?<int>\\d+)").find(rightreg)?.groupValues as? MatchNamedGroupCollection
                                        result = DoubleVar(
                                            name = "result",
                                            value = left.value / inte?.get("int")!!.value.toInt(),
                                            mutable = false,
                                            scope = scope
                                        )
                                    } else if (left is IntegerVar) {
                                        val inte = Regex("(?<int>\\d+)").find(rightreg)?.groupValues as? MatchNamedGroupCollection
                                        result = IntegerVar(
                                            name = "result",
                                            value = left.value / inte?.get("int")!!.value.toInt(),
                                            mutable = false,
                                            scope = scope
                                        )
                                    }
                                }
                            }
                        }
                        leftreg!!.matches(Regex("(?<double>\\d+\\.\\d+)")) -> {
                            val left = Regex("(?<double>\\d+\\.\\d+)").find(rightreg)?.groupValues as? MatchNamedGroupCollection
                            when {
                                rightreg!!.matches(Regex("(?<double>\\d+\\.\\d+)")) -> {
                                    val right = Regex("(?<double>\\d+\\.\\d+)").find(rightreg)?.groupValues as? MatchNamedGroupCollection
                                    result = DoubleVar(
                                        name = "result",
                                        value = left?.get("double")!!.value.toDouble() / right?.get("double")!!.value.toDouble(),
                                        mutable = false,
                                        scope = scope
                                    )
                                }
                                rightreg!!.matches(Regex("(?<int>\\d+)")) -> {
                                    val right = Regex("(?<int>\\d+)").find(rightreg)?.groupValues as? MatchNamedGroupCollection
                                    result = DoubleVar(
                                        name = "result",
                                        value = left?.get("double")!!.value.toDouble() / right?.get("int")!!.value.toInt(),
                                        mutable = false,
                                        scope = scope
                                    )
                                }
                            }
                        }
                        leftreg!!.matches(Regex("(?<int>\\d+)")) -> {
                            val left = Regex("(?<int>\\d+)").find(leftreg)?.groupValues as? MatchNamedGroupCollection
                            when {
                                rightreg!!.matches(Regex("(?<double>\\d+\\.\\d+)")) -> {
                                    val right = Regex("(?<double>\\d+\\.\\d+)").find(rightreg)?.groupValues as? MatchNamedGroupCollection
                                    result = DoubleVar(
                                        name = "result",
                                        value = left?.get("int")!!.value.toInt() / right?.get("double")!!.value.toDouble(),
                                        mutable = false,
                                        scope = scope
                                    )
                                }
                                rightreg!!.matches(Regex("(?<int>\\d+)")) -> {
                                    val right = Regex("(?<int>\\d+)").find(rightreg)?.groupValues as? MatchNamedGroupCollection
                                    result = IntegerVar(
                                        name = "result",
                                        value = left?.get("int")!!.value.toInt() / right?.get("int")!!.value.toInt(),
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
            when {
                scope.findInScope(param.replace(" ", ""))?.isNotEmpty() ?: false -> {
                    result = scope.findInScope(param.replace(" ", ""))?.first()!!
                }
                param!!.matches(Regex("(?<bool>true|false)")) -> {
                    val bool = Regex("(?<bool>true|false)").find(param)?.groupValues as? MatchNamedGroupCollection
                    result = BooleanVar(
                        name = "tempparam",
                        value = bool?.get("bool")?.value?.toBoolean() ?: false,
                        mutable = false,
                        scope = scope
                    )
                }
                param!!.matches(Regex("(\\\"(?<stringa>[^\\\"]+)\\\"|\\'(?<stringb>[^\\']+)\\')")) -> {
                    val str = Regex("(\\\"(?<stringa>[^\\\"]+)\\\"|\\'(?<stringb>[^\\']+)\\')").find(param)?.groupValues as? MatchNamedGroupCollection
                    result = StringVar(
                        name = "tempparam",
                        value = str?.get("stringa")?.value ?: str?.get("stringb")?.value ?: "",
                        mutable = false,
                        scope = scope
                    )
                }
                param!!.matches(Regex("(?<double>\\d+\\.\\d+)")) -> {
                    val double = Regex("(?<double>\\d+\\.\\d+)").find(param)?.groupValues as? MatchNamedGroupCollection
                    result = DoubleVar(
                        name = "tempparam",
                        value = double?.get("double")!!.value.toDouble(),
                        mutable = false,
                        scope = scope
                    )
                }
                param!!.matches(Regex("(?<int>\\d+)")) -> {
                    val inte = Regex("(?<int>\\d+)").find(param)?.groupValues as? MatchNamedGroupCollection
                    result = IntegerVar(
                        name = "tempparam",
                        value = inte?.get("int")!!.value.toInt(),
                        mutable = false,
                        scope = scope
                    )
                }
            }
        }
    }
}