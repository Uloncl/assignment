package uni.ase.assignment.parser.structures.blocks

import javafx.scene.paint.Color
import javafx.scene.shape.ArcType
import uni.ase.assignment.parser.CodeParser
import uni.ase.assignment.parser.structures.Condition
import uni.ase.assignment.parser.structures.Line
import uni.ase.assignment.parser.structures.LineType
import uni.ase.assignment.parser.structures.Parameter
import uni.ase.assignment.parser.structures.variables.*
import uni.ase.assignment.shapes.*

class Block(
    var type: BlockType?,
    var structure: Any?,
    var range: IntRange,
    var code: String,
    var lineRange: IntRange,
    var lines: MutableList<Line>,
    var children: MutableList<Block>,
    var parent: Block?,
    var vars: Variables,
    var parser: CodeParser
) {
    override fun toString(): String {
        return code
    }

    /** takes an [IntRange] representing a range of characters, this will then search this code block for that range and return a pair of the range of lines as an [IntRange] and a [MutableList] of the [Line]s */
    fun linesInRange(subRange: IntRange): Pair<IntRange, MutableList<Line>> {
        val firstLine = lines.indexOfFirst { line -> line.range.contains(subRange.first) }
        val lastLine = lines.indexOfFirst { line -> line.range.contains(subRange.last) }
        return Pair(firstLine..lastLine, lines.slice(firstLine..lastLine).toMutableList())
    }

    fun printChildren(index: Int = 0) {
        parser.log.out("child $index - lines $lineRange: $code\n")
        children.forEachIndexed { i, c ->
            c.printChildren(i)
            parser.log.out("\n")
        }
    }

    fun replaceChildrenInCode() {
        children.forEachIndexed { i, child ->
            code = code.replace(child.code, "\n{ child-$i }\n")
            child.replaceChildrenInCode()
        }
    }

    fun findInScope(name: String): List<Variable>? {
        parser.log.out("searching for var called <${name}>")
        if (vars.hasVar(name)?.isNotEmpty() ?: false) {
            parser.log.out("this block has the variable\n${vars.hasVar(name)}")
            vars.hasVar(name)?.forEach {
                parser.log.out("$it")
            }
            return vars.hasVar(name)
        } else {
            if (parent != null && parent?.type != BlockType.FUNCTION) {
                parser.log.out("searching in the parent for the variable")
                return parent?.findInScope(name)
            } else {
                parser.log.out("could not find the variable")
                return null
            }
        }
    }

    fun isInFunction(): Function? {
        if (parent?.type == BlockType.FUNCTION) {
            return parent?.structure as? uni.ase.assignment.parser.structures.blocks.Function
        } else if (parent?.type == BlockType.MAIN) {
            return null
        } else {
            parent?.isInFunction()
        }
        return null
    }

    fun parseLines(): String? {
        parser.log.out("parsing lines")
        var cumulativeChars = 0
        var newLines = mutableListOf<Line>()
        code.replace("\t", "")
            .split("\n").toMutableList()
            .forEachIndexed { i, l ->
                parser.log.out("parsing lines")
                parser.log.out("\n$i: $l")
                val lineLen = l.length
                var type = LineType.UNKNOWN
                var variable: Any? = null
                when {
                    l.matches(Regex("return\\s+(?<toreturn>[^\\n]+)")) -> {
                        if (isInFunction() != null) {
                            var func = isInFunction()
                            if (func?.returnType != null) {
                                type = LineType.RETURN
                                val returnStatement =
                                    Regex("return\\s+(?<toreturn>[^\\n]+)").find(l)?.groups as? MatchNamedGroupCollection
                                return returnStatement?.get("toreturn")?.value
                            } else {
                                parser.log.error("function does not have a return type")
                            }
                        } else {
                            parser.log.error("return statement is not within a function")
                        }
                    }
                    l.matches(parser.functionCallRegex) -> {
                        parser.log.out("function call detected")
                        type = LineType.FUNCTION_CALL
                        var newLine = Line(
                            num = i,
                            range = cumulativeChars until cumulativeChars + lineLen,
                            line = l,
                            type = type,
                            variable = null,
                            operation = null,
                            blockState = this@Block
                        )
                        newLines.add(newLine)
                        val functioncall = parser.functionCallRegex.find(l)?.groups as? MatchNamedGroupCollection
                        val funcname = functioncall?.get("name")?.value ?: ""
                        val funcparams = functioncall?.get("params")?.value?.split(Regex("\\,\\s*"))?.toMutableList() ?: mutableListOf()
                        var funcParameters : List<Parameter> = funcparams.map {
                            var param : Parameter = Parameter(it, null, this@Block)
                            param.evaluate()
                            parser.log.out(param.result.toString())
                            return@map param
                        }
                        when {
                            funcname == "Line" -> {
                                parser.cac.DrawLine(listOf("",
                                    (funcParameters.get(0)?.result as? IntegerVar)?.value?.toString() ?: "0.0",
                                    (funcParameters.get(1)?.result as? IntegerVar)?.value?.toString() ?: "0.0",
                                    (funcParameters.get(2)?.result as? IntegerVar)?.value?.toString() ?: "0.0",
                                    (funcParameters.get(3)?.result as? IntegerVar)?.value?.toString() ?: "0.0",
                                    (funcParameters.get(4)?.result as? StringVar)?.value ?: "FFFFFF"
                                ))
                            }
                            funcname == "Square" -> {
                                parser.cac.DrawSquare(listOf("",
                                    (funcParameters.get(0)?.result as? IntegerVar?)?.value?.toString() ?: "0.0",
                                    (funcParameters.get(1)?.result as? IntegerVar?)?.value?.toString() ?: "0.0",
                                    (funcParameters.get(2)?.result as? IntegerVar?)?.value?.toString() ?: "0.0",
                                    (funcParameters.get(3)?.result as? BooleanVar?)?.value?.toString() ?: "false",
                                    (funcParameters.get(4)?.result as? StringVar?)?.value ?: "FFFFFF",
                                    (funcParameters.get(5)?.result as? StringVar?)?.value ?: "FFFFFF",
                                    (funcParameters.get(6)?.result as? BooleanVar?)?.value?.toString() ?: "true",
                                    (funcParameters.get(7)?.result as? IntegerVar?)?.value?.toString() ?: "0.0"
                                ))
                            }
                            funcname == "Circle" -> {
                                parser.cac.DrawCircle(listOf("",
                                    (funcParameters.get(0)?.result as? IntegerVar?)?.value?.toString() ?: "0.0",
                                    (funcParameters.get(1)?.result as? IntegerVar?)?.value?.toString() ?: "0.0",
                                    (funcParameters.get(2)?.result as? IntegerVar?)?.value?.toString() ?: "0.0",
                                    (funcParameters.get(3)?.result as? BooleanVar?)?.value?.toString() ?: "false",
                                    (funcParameters.get(4)?.result as? StringVar?)?.value ?: "FFFFFF",
                                    (funcParameters.get(5)?.result as? StringVar?)?.value ?: "FFFFFF",
                                ))
                            }
                            funcname == "Rectangle" -> {
                                parser.cac.DrawRect(listOf("",
                                    (funcParameters.get(0)?.result as? IntegerVar?)?.value?.toString() ?: "0.0",
                                    (funcParameters.get(1)?.result as? IntegerVar?)?.value?.toString() ?: "0.0",
                                    (funcParameters.get(2)?.result as? IntegerVar?)?.value?.toString() ?: "0.0",
                                    (funcParameters.get(3)?.result as? IntegerVar?)?.value?.toString() ?: "0.0",
                                    (funcParameters.get(4)?.result as? BooleanVar?)?.value?.toString() ?: "false",
                                    (funcParameters.get(5)?.result as? StringVar?)?.value ?: "FFFFFF",
                                    (funcParameters.get(6)?.result as? StringVar?)?.value ?: "FFFFFF",
                                    (funcParameters.get(7)?.result as? BooleanVar?)?.value?.toString() ?: "false",
                                    (funcParameters.get(8)?.result as? IntegerVar?)?.value?.toString() ?: "0.0"
                                ))
                            }
                            funcname == "Oval" -> {
                                parser.cac.DrawOval(listOf("",
                                    (funcParameters.get(0)?.result as? IntegerVar?)?.value?.toString() ?: "0.0",
                                    (funcParameters.get(1)?.result as? IntegerVar?)?.value?.toString() ?: "0.0",
                                    (funcParameters.get(2)?.result as? IntegerVar?)?.value?.toString() ?: "0.0",
                                    (funcParameters.get(3)?.result as? IntegerVar?)?.value?.toString() ?: "0.0",
                                    (funcParameters.get(4)?.result as? BooleanVar?)?.value?.toString() ?: "false",
                                    (funcParameters.get(5)?.result as? StringVar?)?.value ?: "FFFFFF",
                                    (funcParameters.get(6)?.result as? StringVar?)?.value ?: "FFFFFF"
                                ))
                            }
                            funcname == "Polygon" -> {
                                parser.cac.DrawPolygon(listOf("",
                                    (funcParameters.get(0)?.result as? IntegerVar?)?.value?.toString() ?: "0.0",
                                    (funcParameters.get(1)?.result as? IntegerVar?)?.value?.toString() ?: "0.0",
                                    (funcParameters.get(2)?.result as? IntegerVar?)?.value?.toString() ?: "0.0",
                                    (funcParameters.get(3)?.result as? IntegerVar?)?.value?.toString() ?: "0",
                                    (funcParameters.get(4)?.result as? BooleanVar?)?.value?.toString() ?: "false",
                                    (funcParameters.get(5)?.result as? StringVar?)?.value ?: "FFFFFF",
                                    (funcParameters.get(6)?.result as? StringVar?)?.value ?: "FFFFFF"
                                ))
                            }
                            funcname == "Polyline" -> {
                                parser.cac.DrawPolyline(listOf("",
                                    (funcParameters.get(0)?.result as? IntegerVar?)?.value?.toString() ?: "0.0",
                                    (funcParameters.get(1)?.result as? IntegerVar?)?.value?.toString() ?: "0.0",
                                    (funcParameters.get(2)?.result as? IntegerVar?)?.value?.toString() ?: "0.0",
                                    (funcParameters.get(3)?.result as? IntegerVar?)?.value?.toString() ?: "0",
                                    (funcParameters.get(4)?.result as? StringVar?)?.value ?: "FFFFFF"
                                ))
                            }
                            funcname == "Arc" -> {
                                parser.cac.DrawArc(listOf("",
                                    (funcParameters.get(0)?.result as? IntegerVar?)?.value?.toString() ?: "0.0",
                                    (funcParameters.get(1)?.result as? IntegerVar?)?.value?.toString() ?: "0.0",
                                    (funcParameters.get(2)?.result as? IntegerVar?)?.value?.toString() ?: "0.0",
                                    (funcParameters.get(3)?.result as? IntegerVar?)?.value?.toString() ?: "0.0",
                                    (funcParameters.get(4)?.result as? IntegerVar?)?.value?.toString() ?: "0.0",
                                    (funcParameters.get(5)?.result as? IntegerVar?)?.value?.toString() ?: "90.0",
                                    (funcParameters.get(6)?.result as? StringVar?)?.value ?: "FFFFFF"
                                ))
                            }
                            funcname == "Text" -> {
                                parser.cac.DrawText(listOf("",
                                    (funcParameters.get(0)?.result as? IntegerVar?)?.value?.toString() ?: "0.0",
                                    (funcParameters.get(1)?.result as? IntegerVar?)?.value?.toString() ?: "0.0",
                                    (funcParameters.get(2)?.result as? StringVar?)?.value ?: "Example Text",
                                    (funcParameters.get(3)?.result as? StringVar?)?.value ?: "FFFFFF"
                                ))
                            }
//                            "Triangle" -> {
//                            }
                            funcname == "Clear" -> {
                                parser.cac.clear()
                            }
                            funcname == "Reset" -> {
                                parser.cac.reset()
                            }
                            funcname == "Wait" -> {
                                parser.log.out("attempting to sleep")
                                Thread.sleep(
                                    (findInScope(funcparams.get(0))?.firstOrNull() as IntegerVar?)?.value?.toLong()
                                        ?: funcparams.get(0).toLong()
                                )
                            }
                            funcname == "Print" -> {
                                val textParam = Parameter(funcparams.get(0), null, this@Block)
                                textParam.evaluate()
                                val text : Variable = textParam.result ?: StringVar("", "", false, this@Block)
                                when {
                                    text is StringVar -> {
                                        parser.log.out(text.value)
                                    }
                                    text is IntegerVar -> {
                                        parser.log.out(text.value.toString())
                                    }
                                    text is DoubleVar -> {
                                        parser.log.out(text.value.toString())
                                    }
                                    text is BooleanVar -> {
                                        parser.log.out(text.value.toString())
                                    }
                                }
                            }
                            parser.allCode.children.filter { it.type == BlockType.FUNCTION }.isNotEmpty() -> {
                                parser.log.out("user defined function call detected")
                                (parser.allCode.children.filter { it.structure is Function && (it as uni.ase.assignment.parser.structures.blocks.Function).name == funcname }
                                    .first().structure as Function).run(funcparams.joinToString(","))
                            }
                            else -> {
                                parser.log.error("no function definition found")
                            }
                        }
                    }
                    l.matches(parser.variableDeclarationRegex) -> {
                        parser.log.out("variable declaration detected")
                        type = LineType.VARIABLE_DECLARATION
                        val variableDeclaration =
                            parser.variableDeclarationRegex.find(l)?.groups as? MatchNamedGroupCollection
                        val datatype: String = variableDeclaration?.get("type")?.value ?: ""
                        val collectiontype: String = variableDeclaration?.get("collectiontype")?.value ?: ""
                        when {
                            datatype == "String" -> {
                                val param =
                                    Parameter(variableDeclaration?.get("value")?.value ?: "", null, this@Block)
                                param.evaluate()
                                variable = StringVar(
                                    name = variableDeclaration?.get("name")?.value ?: "",
                                    value = (param.result as StringVar?)?.value ?: "",
                                    mutable = variableDeclaration?.get("mutable")?.value == "var",
                                    scope = this@Block
                                )
                                if (findInScope(variable.name)?.isEmpty() ?: true) {
                                    vars.strings.add(variable)
                                    parser.log.out("variable added: ${variable.name} = ${variable.value}")
                                    var newLine = Line(
                                        num = i,
                                        range = cumulativeChars until cumulativeChars + lineLen,
                                        line = l,
                                        type = type,
                                        variable = variable,
                                        operation = null,
                                        blockState = this@Block
                                    )
                                    newLines.add(newLine)
                                } else {
                                    parser.log.out("variable with name <${variable.name}> already exists")
                                }
                            }
                            datatype == "Int" || datatype == "Integer" -> {
                                val param =
                                    Parameter(variableDeclaration?.get("value")?.value?.filterNot { it.isWhitespace() }
                                        ?: "", null, this@Block)
                                param.evaluate()
                                variable = IntegerVar(
                                    name = variableDeclaration?.get("name")?.value ?: "",
                                    value = (param.result as IntegerVar?)?.value ?: 0,
                                    mutable = variableDeclaration?.get("mutable")?.value == "var",
                                    scope = this@Block
                                )
                                if (findInScope(variable.name)?.isEmpty() ?: true) {
                                    vars.integers.add(variable)
                                    parser.log.out("variable added: ${variable.name} = ${variable.value}")
                                    var newLine = Line(
                                        num = i,
                                        range = cumulativeChars until cumulativeChars + lineLen,
                                        line = l,
                                        type = type,
                                        variable = variable,
                                        operation = null,
                                        blockState = this@Block
                                    )
                                    newLines.add(newLine)
                                } else {
                                    parser.log.out("variable with name <${variable.name}> already exists")
                                }
                            }
                            datatype == "Double" -> {
                                val param =
                                    Parameter(variableDeclaration?.get("value")?.value ?: "", null, this@Block)
                                param.evaluate()
                                variable = DoubleVar(
                                    name = variableDeclaration?.get("name")?.value ?: "",
                                    value = (param.result as DoubleVar?)?.value ?: 0.0,
                                    mutable = variableDeclaration?.get("mutable")?.value == "var",
                                    scope = this@Block
                                )
                                if (findInScope(variable.name)?.isEmpty() ?: true) {
                                    vars.doubles.add(variable)
                                    parser.log.out("variable added: ${variable.name} = ${variable.value}")
                                    var newLine = Line(
                                        num = i,
                                        range = cumulativeChars until cumulativeChars + lineLen,
                                        line = l,
                                        type = type,
                                        variable = variable,
                                        operation = null,
                                        blockState = this@Block
                                    )
                                    newLines.add(newLine)
                                } else {
                                    parser.log.out("variable with name <${variable.name}> already exists")
                                }
                            }
                            datatype == "Boolean" -> {
                                variable = BooleanVar(
                                    name = variableDeclaration?.get("name")?.value ?: "",
                                    value = variableDeclaration?.get("value")?.value?.toBoolean() ?: false,
                                    mutable = variableDeclaration?.get("mutable")?.value == "var",
                                    scope = this@Block
                                )
                                if (findInScope(variable.name)?.isEmpty() ?: true) {
                                    vars.booleans.add(variable)
                                    parser.log.out("variable added: ${variable.name} = ${variable.value}")
                                    var newLine = Line(
                                        num = i,
                                        range = cumulativeChars until cumulativeChars + lineLen,
                                        line = l,
                                        type = type,
                                        variable = variable,
                                        operation = null,
                                        blockState = this@Block
                                    )
                                    newLines.add(newLine)
                                } else {
                                    parser.log.out("variable with name <${variable.name}> already exists")
                                }
                            }
                            collectiontype.startsWith("Array<") -> {
                                variable = ArrayVar(
                                    name = variableDeclaration?.get("name")?.value ?: "",
                                    type = collectiontype.substring(6 until collectiontype.length),
                                    mutable = variableDeclaration?.get("mutable")?.value == "var",
                                    scope = this@Block
                                )
                                variable.parseCollection(variableDeclaration?.get("collection")?.value ?: "")
                                if (findInScope(variable.name)?.isEmpty() ?: true) {
                                    vars.arrays.add(variable)
                                    var newLine = Line(
                                        num = i,
                                        range = cumulativeChars until cumulativeChars + lineLen,
                                        line = l,
                                        type = type,
                                        variable = variable,
                                        operation = null,
                                        blockState = this@Block
                                    )
                                    newLines.add(newLine)
                                } else {
                                    parser.log.out("variable with name <${variable.name}> already exists")
                                }
                            }
                            collectiontype.startsWith("Map<") -> {
                                val maptypes = Regex("Map<(.+)\\s*,\\s*(.+)>").find(collectiontype)?.groups
                                variable = MapVar(
                                    name = variableDeclaration?.get("name")?.value ?: "",
                                    keyType = maptypes?.get(1)?.value,
                                    valType = maptypes?.get(2)?.value,
                                    mutable = variableDeclaration?.get("mutable")?.value == "var",
                                    scope = this@Block
                                )
                                variable.parseCollection(variableDeclaration?.get("collection")?.value ?: "")
                                if (findInScope(variable.name)?.isEmpty() ?: true) {
                                    vars.maps.add(variable)
                                    var newLine = Line(
                                        num = i,
                                        range = cumulativeChars until cumulativeChars + lineLen,
                                        line = l,
                                        type = type,
                                        variable = variable,
                                        operation = null,
                                        blockState = this@Block
                                    )
                                    newLines.add(newLine)
                                } else {
                                    parser.log.out("variable with name <${variable.name}> already exists")
                                }
                            }
                            else -> {
                                null
                            }
                        }
                    }
                    l.matches(parser.variableUpdateRegex) -> {
                        type = LineType.VARIABLE_UPDATE
                        val variableUpdate = parser.variableUpdateRegex.find(l)?.groups as? MatchNamedGroupCollection
                        parser.log.out("variable update detected, name = ${variableUpdate?.get("name")?.value ?: "UNKNOWN"}")
                        val varToUpdate: Variable? =
                            findInScope(variableUpdate?.get("name")?.value ?: "")?.firstOrNull()
                        when {
                            varToUpdate is StringVar -> {
                                val param =
                                    Parameter(variableUpdate?.get("value")?.value ?: "", null, this@Block)
                                param.evaluate()
                                varToUpdate.scope.vars.strings.get(varToUpdate.scope.vars.strings.indexOf(varToUpdate)).value =
                                    (param.result as StringVar?)?.value ?: varToUpdate.scope.vars.strings.get(
                                        varToUpdate.scope.vars.strings.indexOf(varToUpdate)
                                    ).value
                                var newLine = Line(
                                    num = i,
                                    range = cumulativeChars until cumulativeChars + lineLen,
                                    line = l,
                                    type = type,
                                    variable = varToUpdate.scope.vars.strings.get(
                                        varToUpdate.scope.vars.strings.indexOf(
                                            varToUpdate
                                        )
                                    ),
                                    operation = null,
                                    blockState = this@Block
                                )
                                newLines.add(newLine)
                            }
                            varToUpdate is IntegerVar -> {
                                val param =
                                    Parameter(variableUpdate?.get("value")?.value ?: "", null, this@Block)
                                param.evaluate()
                                varToUpdate.scope.vars.integers.get(varToUpdate.scope.vars.integers.indexOf(varToUpdate)).value =
                                    (param.result as IntegerVar?)?.value ?: varToUpdate.scope.vars.integers.get(
                                        varToUpdate.scope.vars.integers.indexOf(varToUpdate)
                                    ).value
                                var newLine = Line(
                                    num = i,
                                    range = cumulativeChars until cumulativeChars + lineLen,
                                    line = l,
                                    type = type,
                                    variable = varToUpdate.scope.vars.integers.get(
                                        varToUpdate.scope.vars.integers.indexOf(
                                            varToUpdate
                                        )
                                    ),
                                    operation = null,
                                    blockState = this@Block
                                )
                                newLines.add(newLine)
                            }
                            varToUpdate is DoubleVar -> {
                                val param =
                                    Parameter(variableUpdate?.get("value")?.value ?: "", null, this@Block)
                                param.evaluate()
                                varToUpdate.scope.vars.doubles.get(varToUpdate.scope.vars.doubles.indexOf(varToUpdate)).value =
                                    (param.result as DoubleVar?)?.value ?: varToUpdate.scope.vars.doubles.get(
                                        varToUpdate.scope.vars.doubles.indexOf(varToUpdate)
                                    ).value
                                var newLine = Line(
                                    num = i,
                                    range = cumulativeChars until cumulativeChars + lineLen,
                                    line = l,
                                    type = type,
                                    variable = varToUpdate.scope.vars.doubles.get(
                                        varToUpdate.scope.vars.doubles.indexOf(
                                            varToUpdate
                                        )
                                    ),
                                    operation = null,
                                    blockState = this@Block
                                )
                                newLines.add(newLine)
                            }
                            varToUpdate is BooleanVar -> {
                                val param =
                                    Parameter(variableUpdate?.get("value")?.value ?: "", null, this@Block)
                                param.evaluate()
                                varToUpdate.scope.vars.booleans.get(varToUpdate.scope.vars.booleans.indexOf(varToUpdate)).value =
                                    (param.result as BooleanVar?)?.value ?: varToUpdate.scope.vars.booleans.get(
                                        varToUpdate.scope.vars.booleans.indexOf(varToUpdate)
                                    ).value
                                var newLine = Line(
                                    num = i,
                                    range = cumulativeChars until cumulativeChars + lineLen,
                                    line = l,
                                    type = type,
                                    variable = varToUpdate.scope.vars.booleans.get(
                                        varToUpdate.scope.vars.booleans.indexOf(
                                            varToUpdate
                                        )
                                    ),
                                    operation = null,
                                    blockState = this@Block
                                )
                                newLines.add(newLine)
                            }
                            varToUpdate is ArrayVar -> {
                                varToUpdate.scope.vars.arrays.get(varToUpdate.scope.vars.arrays.indexOf(varToUpdate))
                                    .parseCollection(variableUpdate?.get("collection")?.value ?: "")
                                var newLine = Line(
                                    num = i,
                                    range = cumulativeChars until cumulativeChars + lineLen,
                                    line = l,
                                    type = type,
                                    variable = varToUpdate.scope.vars.arrays.get(
                                        varToUpdate.scope.vars.arrays.indexOf(
                                            varToUpdate
                                        )
                                    ),
                                    operation = null,
                                    blockState = this@Block
                                )
                                newLines.add(newLine)
                            }
                            varToUpdate is MapVar -> {
                                varToUpdate.scope.vars.maps.get(varToUpdate.scope.vars.maps.indexOf(varToUpdate))
                                    .parseCollection(variableUpdate?.get("collection")?.value ?: "")
                                var newLine = Line(
                                    num = i,
                                    range = cumulativeChars until cumulativeChars + lineLen,
                                    line = l,
                                    type = type,
                                    variable = varToUpdate.scope.vars.maps.get(
                                        varToUpdate.scope.vars.maps.indexOf(
                                            varToUpdate
                                        )
                                    ),
                                    operation = null,
                                    blockState = this@Block
                                )
                                newLines.add(newLine)
                            }
                            else -> {
                                null
                            }
                        }
                    }
                    l.matches(Regex("\\{\\s*child-(?<childnumber>\\d+)\\s*\\}")) -> {
                        parser.log.out("code block detected")
                        type = LineType.BLOCK
                        val blockDeclaration =
                            Regex("\\{\\s*child-(?<childnumber>\\d+)\\s*\\}").find(l)?.groups as? MatchNamedGroupCollection
                        val childNum: Int = blockDeclaration?.get("childnumber")?.value?.toInt() ?: -1
                        if (children.get(childNum).type == BlockType.IF) {
                            parser.log.out("if block detected")
                            (children.get(childNum).structure as? If)?.ifGroup?.run()
                        } else if (children.get(childNum).type == BlockType.FOR) {
                            parser.log.out("for block detected")
                            (children.get(childNum).structure as? For)?.runBlock()
                        } else if (children.get(childNum).type == BlockType.FOREACH) {
                            parser.log.out("foreach block detected")
                            (children.get(childNum).structure as? ForEach)?.runBlock()
                        } else if (children.get(childNum).type == BlockType.WHILE) {
                            parser.log.out("while block detected")
                            println("while block detected")
                            (children.get(childNum).structure as? While)?.runBlock()
                        }
                        var newLine = Line(
                            num = i,
                            range = cumulativeChars until cumulativeChars + lineLen,
                            line = l,
                            type = type,
                            variable = variable,
                            operation = null,
                            blockState = this@Block
                        )
                        newLines.add(newLine)
                    }
                    else -> {
                        parser.log.out("line $i <--$l--> could not be identified")
                    }
                }
            }
        return null
        lines = newLines
    }

    fun defineBlocks() {
        Regex("(?<blockdefinition>[^\\n]+)[\\s\\t]*(?<child>\\{ child-(?<childnumber>\\d) \\})").findAll(code)
            .forEachIndexed { i, m ->
                val match = m.groups as? MatchNamedGroupCollection
                var child = children.get(match?.get("childnumber")?.value?.toInt() ?: -1)
                val line = match?.get("blockdefinition")?.value ?: ""
                var currIfGroup: IfGroup? = null
                when {
                    line.matches(parser.functionDeclarationRegex) && parent == null -> {
                        val functionDeclaration =
                            parser.functionDeclarationRegex.find(line ?: "")?.groups as? MatchNamedGroupCollection
                        child.type = BlockType.FUNCTION
                        child.structure = Function(
                            child,
                            functionDeclaration?.get("name")?.value ?: "",
                            functionDeclaration?.get("params")?.value,
                            functionDeclaration?.get("returntype")?.value
                        )
                    }
                    line.matches(parser.ifRegex) -> {
                        val ifDeclaration = parser.ifRegex.find(line ?: "")?.groups as? MatchNamedGroupCollection
                        child.type = BlockType.IF
                        currIfGroup = IfGroup(mutableListOf())
                        var ifStruct = If(
                            child,
                            Condition(ifDeclaration?.get("condition")?.value ?: "", null, this@Block),
                            currIfGroup
                        )
                        currIfGroup.ifs.add(ifStruct)
                        ifStruct.ifGroup = currIfGroup
                        child.structure = ifStruct
                    }
                    line.matches(parser.elifRegex) -> {
                        val elifDeclaration = parser.elifRegex.find(line ?: "")?.groups as? MatchNamedGroupCollection
                        child.type = BlockType.ELIF
                        var elifStruct = If(
                            child,
                            Condition(elifDeclaration?.get("condition")?.value ?: "", null, this@Block),
                            currIfGroup ?: IfGroup(mutableListOf())
                        )
                        currIfGroup?.ifs?.add(elifStruct)
                        child.structure = currIfGroup
                    }
                    line.matches(parser.elseRegex) -> {
                        child.type = BlockType.ELSE
                        var elseStruct = If(child, null, currIfGroup ?: IfGroup(mutableListOf()))
                        currIfGroup?.ifs?.add(elseStruct)
                        child.structure = elseStruct
                    }
                    line.matches(parser.forRegex) -> {
                        val forLoopDeclaration = parser.forRegex.find(line ?: "")?.groups as? MatchNamedGroupCollection
                        if (forLoopDeclaration?.get("parama") == null) {
                            child.type = BlockType.FOR
                            var counterVar = Regex("(?<name>\\w+)\\s*\\=\\s*(?<value>\\d+)").find(
                                forLoopDeclaration?.get("param1")?.value ?: ""
                            )?.groups as? MatchNamedGroupCollection
                            var forcounter = IntegerVar(
                                counterVar?.get("name")?.value ?: "x",
                                counterVar?.get("value")?.value?.toInt() ?: 0,
                                true,
                                child
                            )
                            child.vars.integers.add(forcounter)
                            child.structure = For(
                                block = child,
                                counter = forcounter,
                                condition = Condition(
                                    forLoopDeclaration?.get("param2")?.value ?: "",
                                    null,
                                    this@Block
                                ),
                                increment = forLoopDeclaration?.get("param3")?.value?.toInt() ?: 1
                            )
                            parser.log.out("for loop made with ${(child.structure as For).counter.name} = ${(child.structure as For).counter.value} ; ${(child.structure as For).condition.condition} ; ${(child.structure as For).increment}")
                        } else {
                            child.type = BlockType.FOREACH
                            child.structure = ForEach(
                                block = child,
                                collection = ArrayVar(
                                    forLoopDeclaration?.get("parama")?.value ?: "",
                                    "String",
                                    false,
                                    child
                                ),
                                element = forLoopDeclaration?.get("param2")?.value ?: ""
                            )
                        }
                    }
                    line.matches(parser.whileRegex) -> {
                        val whileLoopDeclaration = parser.whileRegex.find(line)?.groups as? MatchNamedGroupCollection
                        child.type = BlockType.WHILE
                        child.structure = While(
                            child,
                            Condition(whileLoopDeclaration?.get("condition")?.value ?: "", null, this@Block)
                        )
                    }
                    else -> {
                        parser.log.error("invalid block declaration on line ${child.lineRange.first}")
                    }
                }
            }
    }
}
