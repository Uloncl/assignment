package uni.ase.assignment.parser.structures.blocks

import uni.ase.assignment.controllers.LogController
import uni.ase.assignment.parser.CodeParser
import uni.ase.assignment.parser.structures.Condition
import uni.ase.assignment.parser.structures.Line
import uni.ase.assignment.parser.structures.LineType
import uni.ase.assignment.parser.structures.variables.*

class Block (
    var type : BlockType?,
    var structure : Any?,
    var range: IntRange,
    var code : String,
    var lineRange : IntRange,
    var lines : MutableList<Line>,
    var children : MutableList<Block>,
    var parent : Block?,
    var vars : Variables,
    var parser : CodeParser,
    val log : LogController
) {
    override fun toString() : String {
        return code
    }

    /** takes an [IntRange] representing a range of characters, this will then search this code block for that range and return a pair of the range of lines as an [IntRange] and a [MutableList] of the [Line]s */
    fun linesInRange(subRange : IntRange) : Pair<IntRange, MutableList<Line>> {
        val firstLine = lines.indexOfFirst { line -> line.range.contains(subRange.first) }
        val lastLine  = lines.indexOfFirst { line -> line.range.contains(subRange.last) }
        return Pair(firstLine..lastLine, lines.slice(firstLine..lastLine).toMutableList())
    }

    fun printChildren(index : Int = 0) {
        log.out("child $index - lines $lineRange: $code\n\n\n")
        children.forEachIndexed { i, c ->
            c.printChildren(i)
            log.out("\n\n")
        }
    }

    fun replaceChildrenInCode () {
        children.forEachIndexed { i, child ->
            code = code.replace(child.code, "\n{ child-$i }\n")
            child.replaceChildrenInCode()
        }
    }

    fun findInScope(name : String) : List<Variable>? {
        if (vars.hasVar(name) != null) {
            return vars.hasVar(name)
        } else {
            if (parent != null && parent?.type != BlockType.FUNCTION) {
                return parent?.findInScope(name)
            } else {
                return null
            }
        }
    }

    fun parseLines() {
        log.out("parsing lines")
        var cumulativeChars = 0
        var newLines = mutableListOf<Line>()
        code.replace("\t", "")
            .split("\n").toMutableList()
            .forEachIndexed { i, l ->
                log.out("$i: $l")
                val lineLen = l.length
                var type = LineType.UNKNOWN
                var variable : Any? = null
                when {
                    l.matches(parser.functionCallRegex) -> {
                        log.out("function call detected")
                        type = LineType.FUNCTION_CALL
                        var newLine = Line(
                            num = i,
                            range = cumulativeChars until cumulativeChars + lineLen,
                            line = l,
                            type = type,
                            variable = variable,
                            operation = null,
                            blockState = this@Block,
                            log         = log
                        )
                        newLines.add(newLine)
                    }
                    l.matches(parser.variableDeclarationRegex) -> {
                        log.out("variable declaration detected")
                        type = LineType.VARIABLE_DECLARATION
                        val variableDeclaration = parser.variableDeclarationRegex.find(l)!!.groups as? MatchNamedGroupCollection
                        val datatype : String = variableDeclaration?.get("type")?.value ?: ""
                        val collectiontype : String = variableDeclaration?.get("collectiontype")?.value ?: ""
                        when {
                            datatype == "String" -> {
                                variable = StringVar(
                                    name = variableDeclaration?.get("name")!!.value,
                                    value = variableDeclaration?.get("string")!!.value,
                                    mutable = variableDeclaration?.get("mutable")!!.value == "var",
                                    scope = this@Block,
                            log         = log
                                )
                                vars.strings.add(variable)
                                var newLine = Line(
                                    num = i,
                                    range = cumulativeChars until cumulativeChars + lineLen,
                                    line = l,
                                    type = type,
                                    variable = variable,
                                    operation = null,
                                    blockState = this@Block,
                            log         = log
                                )
                                newLines.add(newLine)
                            }
                            datatype == "Int" || datatype == "Integer"  -> {
                                variable = IntegerVar(
                                    name = variableDeclaration?.get("name")!!.value,
                                    value = variableDeclaration?.get("integer")!!.value.toInt(),
                                    mutable = variableDeclaration?.get("mutable")!!.value == "var",
                                    scope = this@Block,
                            log         = log
                                )
                                vars.integers.add(variable)
                                var newLine = Line(
                                    num = i,
                                    range = cumulativeChars until cumulativeChars + lineLen,
                                    line = l,
                                    type = type,
                                    variable = variable,
                                    operation = null,
                                    blockState = this@Block,
                                    log         = log
                                )
                                newLines.add(newLine)
                            }
                            datatype == "Double" -> {
                                variable = DoubleVar(
                                    name = variableDeclaration?.get("name")!!.value,
                                    value = variableDeclaration?.get("double")!!.value.toDouble(),
                                    mutable = variableDeclaration?.get("mutable")!!.value == "var",
                                    scope = this@Block,
                            log         = log
                                )
                                vars.doubles.add(variable)
                                var newLine = Line(
                                    num = i,
                                    range = cumulativeChars until cumulativeChars + lineLen,
                                    line = l,
                                    type = type,
                                    variable = variable,
                                    operation = null,
                                    blockState = this@Block,
                                    log         = log
                                )
                                newLines.add(newLine)
                            }
                            datatype == "Boolean" -> {
                                variable = BooleanVar(
                                    name = variableDeclaration?.get("name")!!.value,
                                    value = variableDeclaration?.get("boolean")!!.value.toBoolean(),
                                    mutable = variableDeclaration?.get("mutable")!!.value == "var",
                                    scope = this@Block,
                            log         = log
                                )
                                vars.booleans.add(variable)
                                var newLine = Line(
                                    num = i,
                                    range = cumulativeChars until cumulativeChars + lineLen,
                                    line = l,
                                    type = type,
                                    variable = variable,
                                    operation = null,
                                    blockState = this@Block,
                                    log         = log
                                )
                                newLines.add(newLine)
                            }
                            collectiontype.startsWith("Array<") -> {
                                variable = ArrayVar(
                                    name = variableDeclaration?.get("name")!!.value,
                                    type = collectiontype.substring(6 until collectiontype.length),
                                    mutable = variableDeclaration?.get("mutable")!!.value == "var",
                                    scope = this@Block,
                            log         = log
                                )
                                variable.parseCollection(variableDeclaration?.get("collection")!!.value)
                                vars.arrays.add(variable)
                                var newLine = Line(
                                    num = i,
                                    range = cumulativeChars until cumulativeChars + lineLen,
                                    line = l,
                                    type = type,
                                    variable = variable,
                                    operation = null,
                                    blockState = this@Block,
                                    log         = log
                                )
                                newLines.add(newLine)
                            }
                            collectiontype.startsWith("Map<") -> {
                                val maptypes = Regex("Map<(.+)\\s*,\\s*(.+)>").find(collectiontype)?.groups
                                variable = MapVar(
                                    name = variableDeclaration?.get("name")!!.value,
                                    keyType = maptypes?.get(1)?.value,
                                    valType = maptypes?.get(2)?.value,
                                    mutable = variableDeclaration?.get("mutable")!!.value == "var",
                                    scope = this@Block,
                            log         = log
                                )
                                variable.parseCollection(variableDeclaration?.get("collection")!!.value)
                                vars.maps.add(variable)
                                var newLine = Line(
                                    num = i,
                                    range = cumulativeChars until cumulativeChars + lineLen,
                                    line = l,
                                    type = type,
                                    variable = variable,
                                    operation = null,
                                    blockState = this@Block,
                                    log         = log
                                )
                                newLines.add(newLine)
                            }
                            else -> {
                                null
                            }
                        }
                    }
                    l.matches(parser.variableUpdateRegex) -> {
                        log.out("variable update detected")
                        type = LineType.VARIABLE_UPDATE
                        val variableUpdate = parser.variableUpdateRegex.find(l)!!.groups as? MatchNamedGroupCollection
                        val varToUpdate : Variable? = findInScope(variableUpdate?.get("name")?.value ?: "")?.first()
                        when {
                            varToUpdate is StringVar -> {
                                varToUpdate.scope.vars.strings.get(varToUpdate.scope.vars.strings.indexOf(varToUpdate)).value = variableUpdate?.get("string")?.value ?: varToUpdate.scope.vars.strings.get(varToUpdate.scope.vars.strings.indexOf(varToUpdate)).value
                                var newLine = Line(
                                    num = i,
                                    range = cumulativeChars until cumulativeChars + lineLen,
                                    line = l,
                                    type = type,
                                    variable = varToUpdate.scope.vars.strings.get(varToUpdate.scope.vars.strings.indexOf(varToUpdate)),
                                    operation = null,
                                    blockState = this@Block,
                            log         = log
                                )
                                newLines.add(newLine)
                            }
                            varToUpdate is IntegerVar -> {
                                varToUpdate.scope.vars.integers.get(varToUpdate.scope.vars.integers.indexOf(varToUpdate)).value = variableUpdate?.get("integer")?.value?.toInt() ?: varToUpdate.scope.vars.integers.get(varToUpdate.scope.vars.integers.indexOf(varToUpdate)).value
                                var newLine = Line(
                                    num = i,
                                    range = cumulativeChars until cumulativeChars + lineLen,
                                    line = l,
                                    type = type,
                                    variable = varToUpdate.scope.vars.integers.get(varToUpdate.scope.vars.integers.indexOf(varToUpdate)),
                                    operation = null,
                                    blockState = this@Block,
                            log         = log
                                )
                                newLines.add(newLine)
                            }
                            varToUpdate is DoubleVar -> {
                                varToUpdate.scope.vars.doubles.get(varToUpdate.scope.vars.doubles.indexOf(varToUpdate)).value = variableUpdate?.get("double")?.value?.toDouble() ?: varToUpdate.scope.vars.doubles.get(varToUpdate.scope.vars.doubles.indexOf(varToUpdate)).value
                                var newLine = Line(
                                    num = i,
                                    range = cumulativeChars until cumulativeChars + lineLen,
                                    line = l,
                                    type = type,
                                    variable = varToUpdate.scope.vars.doubles.get(varToUpdate.scope.vars.doubles.indexOf(varToUpdate)),
                                    operation = null,
                                    blockState = this@Block,
                            log         = log
                                )
                                newLines.add(newLine)
                            }
                            varToUpdate is BooleanVar -> {
                                varToUpdate.scope.vars.booleans.get(varToUpdate.scope.vars.booleans.indexOf(varToUpdate)).value = variableUpdate?.get("boolean")?.value?.toBoolean() ?: varToUpdate.scope.vars.booleans.get(varToUpdate.scope.vars.booleans.indexOf(varToUpdate)).value
                                var newLine = Line(
                                    num = i,
                                    range = cumulativeChars until cumulativeChars + lineLen,
                                    line = l,
                                    type = type,
                                    variable = varToUpdate.scope.vars.booleans.get(varToUpdate.scope.vars.booleans.indexOf(varToUpdate)),
                                    operation = null,
                                    blockState = this@Block,
                            log         = log
                                )
                                newLines.add(newLine)
                            }
                            varToUpdate is ArrayVar -> {
                                varToUpdate.scope.vars.arrays.get(varToUpdate.scope.vars.arrays.indexOf(varToUpdate)).parseCollection(variableUpdate?.get("collection")?.value ?: "")
                                var newLine = Line(
                                    num = i,
                                    range = cumulativeChars until cumulativeChars + lineLen,
                                    line = l,
                                    type = type,
                                    variable = varToUpdate.scope.vars.arrays.get(varToUpdate.scope.vars.arrays.indexOf(varToUpdate)),
                                    operation = null,
                                    blockState = this@Block,
                            log         = log
                                )
                                newLines.add(newLine)
                            }
                            varToUpdate is MapVar -> {
                                varToUpdate.scope.vars.maps.get(varToUpdate.scope.vars.maps.indexOf(varToUpdate)).parseCollection(variableUpdate?.get("collection")?.value ?: "")
                                var newLine = Line(
                                    num = i,
                                    range = cumulativeChars until cumulativeChars + lineLen,
                                    line = l,
                                    type = type,
                                    variable = varToUpdate.scope.vars.maps.get(varToUpdate.scope.vars.maps.indexOf(varToUpdate)),
                                    operation = null,
                                    blockState = this@Block,
                            log         = log
                                )
                                newLines.add(newLine)
                            }
                            else -> {
                                null
                            }
                        }
                    }
                    l.matches(Regex("\\{\\s*child-(?<childnumber>\\d+)\\s*\\}")) -> {
                        log.out("code block detected")
                        type = LineType.BLOCK
                        val blockDeclaration = Regex("\\{\\s*child-(?<childnumber>\\d+)\\s*\\}").find(l)!!.groups as? MatchNamedGroupCollection
                        if (children.get(blockDeclaration?.get("childnumber")!!.value.toInt()).type == BlockType.WHILE) {
                            (children.get(blockDeclaration?.get("childnumber")!!.value.toInt()).structure as? While)?.runBlock()
                        }
                        var newLine = Line(
                            num = i,
                            range = cumulativeChars until cumulativeChars + lineLen,
                            line = l,
                            type = type,
                            variable = variable,
                            operation = null,
                            blockState = this@Block,
                            log         = log
                        )
                        newLines.add(newLine)
                    }
                    else -> {
                        log.out("line $i <--$l--> could not be identified")
                    }
                }
            }
        lines = newLines
    }

    fun defineBlocks () {
        Regex("(?<blockdefinition>[^\\n]+)[\\s\\t]*(?<child>\\{ child-(?<childnumber>\\d) \\})").findAll(code).forEachIndexed { i, m ->
            val match = m.groups as? MatchNamedGroupCollection
            var child = children.get(match?.get("childnumber")!!.value.toInt())
            val line = match?.get("blockdefinition")!!.value
            var currIfGroup : IfGroup? = null
            when {
                line.matches(parser.functionDeclarationRegex) && parent == null -> {
                    val functionDeclaration = parser.functionDeclarationRegex.find(line)!!.groups as? MatchNamedGroupCollection
                    child.type = BlockType.FUNCTION
                    var functionParameters = mutableListOf<Any>()
                    functionDeclaration?.get("params")!!.value.let { params ->
                        functionParameters = if (functionParameters == null) mutableListOf() else functionParameters
                        val paramsSplit = params.split(Regex(",\\s*"))
                        paramsSplit.mapIndexed { i, param ->
                            val paramParsed = Regex("(?<varname>[^,:\\n]+)\\s*:\\s*(?<type>[A-Z]\\w+)|(?<funname>\\w+)\\((?<params>.*)\\)").find(param)!!.groups as? MatchNamedGroupCollection
                            if (paramParsed?.get("funname") == null) {
                                val type = paramParsed?.get("type")!!.value
                                when (type) {
                                    "String" -> {
                                        functionParameters.add(StringVar(
                                            paramParsed?.get("varname")!!.value, "", false, this@Block, log))
                                    }
                                    "Double" -> {
                                        functionParameters.add(DoubleVar(paramParsed?.get("varname")!!.value, 0.0, false, this@Block, log))
                                    }
                                    "Integer" -> {
                                        functionParameters.add(IntegerVar(paramParsed?.get("varname")!!.value, 1, false, this@Block, log))
                                    }
                                    "Boolean" -> {
                                        functionParameters.add(BooleanVar(paramParsed?.get("varname")!!.value, false, false, this@Block, log))
                                    }
                                    "Array" -> {
                                        functionParameters.add(ArrayVar(paramParsed?.get("varname")!!.value, "String", false, this@Block, log))
                                    }
                                    "Map" -> {
                                        functionParameters.add(MapVar(paramParsed?.get("varname")!!.value, null, null, false, this@Block, log))
                                    }
                                    else -> {
                                        parser.log.out("invalid datatype")
                                    }
                                }
                            }
                        }
                    }
                    child.structure = Function(child, functionDeclaration?.get("name")!!.value, functionParameters, functionDeclaration?.get("returntype")!!.value, log)
                }
                line.matches(parser.ifRegex) -> {
                    val ifDeclaration = parser.ifRegex.find(line)!!.groups as? MatchNamedGroupCollection
                    child.type = BlockType.IF
                    var childStructure = If(child, Condition(ifDeclaration?.get("condition")!!.value, null, log), null, log)
                    currIfGroup = IfGroup(mutableListOf(childStructure), log)
                    childStructure.ifGroup = currIfGroup
                    child.structure = childStructure
                }
                line.matches(parser.elifRegex) -> {
                    val elifDeclaration = parser.elifRegex.find(line)!!.groups as? MatchNamedGroupCollection
                    child.type = BlockType.ELIF
                    var childStructure = If(child, Condition(elifDeclaration?.get("condition")!!.value, null, log), null, log)
                    currIfGroup?.ifs!!.add(childStructure)
                    childStructure.ifGroup = currIfGroup
                    child.structure = currIfGroup
                }
                line.matches(parser.elseRegex) -> {
                    child.type = BlockType.ELSE
                    var childStructure = If(child, null, null, log)
                    currIfGroup?.ifs!!.add(childStructure)
                    childStructure.ifGroup = currIfGroup
                    child.structure = childStructure
                }
                line.matches(parser.forRegex) -> {
                    val forLoopDeclaration = parser.forRegex.find(line)!!.groups as? MatchNamedGroupCollection
                    if (forLoopDeclaration?.get("parama") == null) {
                        child.type = BlockType.FOR
                        var counterVar = Regex("(?<name>\\w+)\\s*\\=\\s*(?<value>\\d)").find(forLoopDeclaration?.get("param1")!!.value)!!.groups as? MatchNamedGroupCollection
                        child.structure = For(
                            block = child,
                            counter = IntegerVar(counterVar?.get("name")!!.value, counterVar?.get("value")!!.value.toInt(), true, child, log),
                            condition = Condition(forLoopDeclaration?.get("param2")!!.value, null, log),
                            increment = forLoopDeclaration?.get("param3")!!.value.toInt(), 
                            log
                        )
                    } else {
                        child.type = BlockType.FOREACH
                        child.structure = ForEach(
                            block = child,
                            collection = ArrayVar(forLoopDeclaration?.get("parama")!!.value, "String", false, child, log),
                            element = forLoopDeclaration?.get("param2")!!.value, 
                            log
                        )
                    }
                }
                line.matches(parser.whileRegex) -> {
                    val whileLoopDeclaration = parser.whileRegex.find(line)!!.groups as? MatchNamedGroupCollection
                    child.type = BlockType.WHILE
                    child.structure = While(child, Condition(whileLoopDeclaration?.get("condition")!!.value, null, log), log)
                }
                else -> {
                    parser.log.error("invalid block declaration on line ${child.lineRange.first}")
                }
            }
        }
    }
}
