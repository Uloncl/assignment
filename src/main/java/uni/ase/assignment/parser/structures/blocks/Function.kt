package uni.ase.assignment.parser.structures.blocks

import uni.ase.assignment.parser.structures.variables.*

class Function (
    block : Block,
    var name : String,
    var paramsStr : String?,
    var returnType : Any?
) : Structure (block) {
    var params : MutableList<Variable> = mutableListOf()
    init {
        if (paramsStr != null) {
            var parameters = MapVar(
                name = "params",
                keyType = "String",
                valType = "String",
                mutable = false,
                scope = block
            )
            parameters.parseCollection(paramsStr ?: "")
            (parameters.map as Map<String, String>).forEach { param ->
                when {
                    param.value == "String" -> {
                        var variable = StringVar(
                            name = param.key,
                            value = "",
                            mutable = true,
                            scope = block
                        )
                        block.vars.strings.add(variable)
                        params.add(variable)
                    }
                    param.value == "Int" || param.value == "Integer"  -> {
                        var variable = IntegerVar(
                            name = param.key,
                            value = 0,
                            mutable = true,
                            scope = block
                        )
                        block.vars.integers.add(variable)
                        params.add(variable)
                    }
                    param.value == "Double" -> {
                        var variable = DoubleVar(
                            name = param.key,
                            value = 0.0,
                            mutable = true,
                            scope = block
                        )
                        block.vars.doubles.add(variable)
                        params.add(variable)
                    }
                    param.value == "Boolean" -> {
                        var variable = BooleanVar(
                            name = param.key,
                            value = false,
                            mutable = true,
                            scope = block
                        )
                        block.vars.booleans.add(variable)
                        params.add(variable)
                    }
                    param.value.startsWith("Array<") -> {
                        block.parser.log.error("arrays cannot be passed as a parameter")
                    }
                    param.value.startsWith("Map<") -> {
                        block.parser.log.error("maps cannot be passed as a parameter")
                    }
                    else -> {
                        null
                    }
                }
            }
        }
    }


    fun run (vars : String) : Any? {
        block.parser.log.out("running code in function $name")
        if (vars != "") {
            var paramsUpdate = ArrayVar(
                name = "params",
                type = "String",
                mutable = false,
                scope = block
            )
            paramsUpdate.parseCollection(vars ?: "")
            params.forEachIndexed { i, p ->
                when {
                    p is StringVar -> {
                        p.value = (paramsUpdate.array as List<String>).get(i)
                        block.vars.strings.find { s -> s.name == p.name }?.value = p.value
                    }
                    p is IntegerVar -> {
                        p.value = (paramsUpdate.array as List<String>).get(i).toInt()
                        block.vars.integers.find { s -> s.name == p.name }?.value = p.value
                    }
                    p is DoubleVar -> {
                        p.value = (paramsUpdate.array as List<String>).get(i).toDouble()
                        block.vars.doubles.find { s -> s.name == p.name }?.value = p.value
                    }
                    p is BooleanVar -> {
                        p.value = (paramsUpdate.array as List<String>).get(i).toBoolean()
                        block.vars.booleans.find { s -> s.name == p.name }?.value = p.value
                    }
                }
            }
        }
        var result = block.parseLines()
        when {
            result!!.matches(Regex("(?<bool>true|false)")) -> {
                val returnVar = Regex("(?<bool>true|false)").find(result)!!.groups as? MatchNamedGroupCollection
                return BooleanVar(
                    name = "$name-return",
                    value = returnVar?.get("bool")?.value?.toBoolean() ?: false,
                    mutable = true,
                    scope = block
                )
            }
            result!!.matches(Regex("(\\\"(?<stringa>[^\\\"]+)\\\"|\\'(?<stringb>[^\\']+)\\')")) -> {
                val returnVar = Regex("(\\\"(?<stringa>[^\\\"]+)\\\"|\\'(?<stringb>[^\\']+)\\')").find(result)!!.groups as? MatchNamedGroupCollection
                return StringVar(
                    name = "$name-return",
                    value = returnVar?.get("stringa")?.value ?: returnVar?.get("stringb")?.value ?: "",
                    mutable = true,
                    scope = block
                )
            }
            result!!.matches(Regex("(?<double>\\d+\\.\\d+)")) -> {
                val returnVar = Regex("(?<double>\\d+\\.\\d+)").find(result)!!.groups as? MatchNamedGroupCollection
                return DoubleVar(
                    name = "$name-return",
                    value = returnVar?.get("double")?.value?.toDouble() ?: 0.0,
                    mutable = true,
                    scope = block
                )
            }
            result!!.matches(Regex("(?<int>\\d+)")) -> {
                val returnVar = Regex("(?<int>\\d+)").find(result)!!.groups as? MatchNamedGroupCollection
                return IntegerVar(
                    name = "$name-return",
                    value = returnVar?.get("int")?.value?.toInt() ?: 0,
                    mutable = true,
                    scope = block
                )
            }
            else -> {
                return null
            }
        }
    }
}