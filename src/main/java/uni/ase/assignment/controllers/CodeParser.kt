package uni.ase.assignment.controllers

import javafx.scene.control.TextArea
import javafx.scene.control.TextField

/**
 * parses "code" from the main [TextArea] line by line as individual commands
 *
 * @param ca the [TextArea] where the code is written
 * @param cac the [CanvasController] that isnt currently used
 * @param log the [LogController] for writing outputs
 * @param coc the [ConsoleController] where the commands are processed after the "code" has been split
 */
class CodeParser (val ca: TextArea, val cac: CanvasController, val log: LogController, val cmd: TextField) {
    var coc : ConsoleController = ConsoleController(cmd, cac, log, this)

    //                              name    mutable   value
    var strings   : MutableMap<Pair<String, Boolean>, String>  = mutableMapOf()
    var integers  : MutableMap<Pair<String, Boolean>, Int>     = mutableMapOf()
    var doubles   : MutableMap<Pair<String, Boolean>, Double>  = mutableMapOf()
    var booleans  : MutableMap<Pair<String, Boolean>, Boolean> = mutableMapOf()
    //                              name    return type             list of code strings
    var functions : MutableMap<Pair<String, Pair<String?, Boolean>>, List<String>> = mutableMapOf()

    var stringArrays   : MutableMap<Pair<String, Boolean>, MutableList<String?>> = mutableMapOf()
    var integersArrays : MutableMap<Pair<String, Boolean>, MutableList<Int?>> = mutableMapOf()
    var doublesArrays  : MutableMap<Pair<String, Boolean>, MutableList<Double?>> = mutableMapOf()
    var booleansArrays : MutableMap<Pair<String, Boolean>, MutableList<Boolean?>> = mutableMapOf()

    var stringToStringMaps   : MutableMap<Pair<String, Boolean>, MutableMap<String?, String?>> = mutableMapOf()
    var stringToIntegersMaps : MutableMap<Pair<String, Boolean>, MutableMap<String?, Int?>> = mutableMapOf()
    var stringToDoublesMaps  : MutableMap<Pair<String, Boolean>, MutableMap<String?, Double?>> = mutableMapOf()
    var stringToBooleansMaps : MutableMap<Pair<String, Boolean>, MutableMap<String?, Boolean?>> = mutableMapOf()

    var integerToStringMaps   : MutableMap<Pair<String, Boolean>, MutableMap<Int?, String?>> = mutableMapOf()
    var integerToIntegersMaps : MutableMap<Pair<String, Boolean>, MutableMap<Int?, Int?>> = mutableMapOf()
    var integerToDoublesMaps  : MutableMap<Pair<String, Boolean>, MutableMap<Int?, Double?>> = mutableMapOf()
    var integerToBooleansMaps : MutableMap<Pair<String, Boolean>, MutableMap<Int?, Boolean?>> = mutableMapOf()
SS
    var doubleToStringMaps   : MutableMap<Pair<String, Boolean>, MutableMap<Double?, String?>> = mutableMapOf()
    var doubleToIntegersMaps : MutableMap<Pair<String, Boolean>, MutableMap<Double?, Int?>> = mutableMapOf()
    var doubleToDoublesMaps  : MutableMap<Pair<String, Boolean>, MutableMap<Double?, Double?>> = mutableMapOf()
    var doubleToBooleansMaps : MutableMap<Pair<String, Boolean>, MutableMap<Double?, Boolean?>> = mutableMapOf()

    var booleanToStringMaps   : MutableMap<Pair<String, Boolean>, MutableMap<Boolean?, String?>> = mutableMapOf()
    var booleanToIntegersMaps : MutableMap<Pair<String, Boolean>, MutableMap<Boolean?, Int?>> = mutableMapOf()
    var booleanToDoublesMaps  : MutableMap<Pair<String, Boolean>, MutableMap<Boolean?, Double?>> = mutableMapOf()
    var booleanToBooleansMaps : MutableMap<Pair<String, Boolean>, MutableMap<Boolean?, Boolean?>> = mutableMapOf()

    // start bracket line number and position in line, end bracket line number and position in line
    var bracketPositions : MutableMap<Pair<Int, Int>, Pair<Int, Int>> = mutableMapOf()

    val stringRegex : Regex = Regex("\\\"(.*)\\\"|\\'(.*)\\'")
    val doubleRegex : Regex = Regex("\\d+\\.\\d+")
    val integerRegex : Regex = Regex("\\d+")
    val booleanRegex : Regex = Regex("(True|False)")
    val paramOrFunRegex : Regex = Regex("\\\".*\\\"|\\'.*\\'|\\d+\\.\\d+|\\d+|[a-zA-Z0-9_\\\"'\\.\\(\\)\\,\\ ]+")
    val ternaryRegex : Regex = Regex("($paramOrFunRegex)\\s*\\={2}\\s*($paramOrFunRegex)\\s*\\?\\s*($paramOrFunRegex)\\s*\\:\\s*($paramOrFunRegex)\\s*")
    val variableDeclarationRegex : Regex = Regex("(var|val)\\s*([A-Z]\\w+|[A-Z]\\w+<([A-Z]\\w+)>)\\s*(\\w+)\\s*\\={1}\\s*(\\\".+\\\"|'.+'|\\d+.{1}\\d+|(\\w+)\\((.*)\\)|[^\\s\\W]+|\\[[\\w,\\s]+\\])")
    val variableUpdateRegex : Regex = Regex("(\\w+)\\s*\\={1}\\s*(\\\".+\\\"|'.+'|\\d+.{1}\\d+|(\\w+)\\((.*)\\)|[^\\s\\W]+|\\[[\\w,\\s]+\\]|$ternaryRegex)")

    val functionDeclarationRegex : Regex = Regex("function\\s*(\\w*)\\((.*)\\)\\s*:\\s*([A-Z]\\w*)\\s*\\{")
    val functionCallRegex : Regex = Regex("(\\w+)\\((.*)\\)")

    val ifRegex : Regex = Regex("if\\s*\\((.*)\\)\\s*\\{")
    val elifRegex : Regex = Regex("elif\\s*\\((.*)\\)\\s*\\{")
    val elseRegex : Regex = Regex("else\\s*\\{")

    val forRegex : Regex = Regex("for\\s*\\((.*);\\s*(.*);\\s*(.*)\\)\\s*\\{")
    val foreachRegex : Regex = Regex("foreach\\s*\\((.*)\\)\\s*\\{")
    val whileRegex : Regex = Regex("while\\s*\\((.*)\\)\\s*\\{")

    val switchRegex : Regex = Regex("switch\\s*\\((.*)\\)\\s*\\{")
    val switchConditionRegex : Regex = Regex("(\\w*)\\s*->\\s*\\{")
    val switchElseRegex : Regex = Regex("else\\s*->\\s*\\{")

    val allBetweenBraces : Regex = Regex("\\{([^{}]+)\\}")

    var initialLines : MutableMap<Pair<Int, IntRange>, String> = mutableMapOf()
    // blocks of code what line those block start on, what range of characters the block covers of the original code, and then the actual list of lines of code
    var codeBlocks : MutableMap<Pair<Int, IntRange>, List<String>> = mutableMapOf()

    fun emptyVarArrays() {
        strings  = mutableMapOf()
        integers = mutableMapOf()
        doubles  = mutableMapOf()
        booleans = mutableMapOf()
        functions = mutableMapOf()

        stringArrays   = mutableMapOf()
        integersArrays = mutableMapOf()
        doublesArrays  = mutableMapOf()
        booleansArrays = mutableMapOf()

        stringToStringMaps   = mutableMapOf()
        stringToIntegersMaps = mutableMapOf()
        stringToDoublesMaps  = mutableMapOf()
        stringToBooleansMaps = mutableMapOf()

        integerToStringMaps   = mutableMapOf()
        integerToIntegersMaps = mutableMapOf()
        integerToDoublesMaps  = mutableMapOf()
        integerToBooleansMaps = mutableMapOf()

        doubleToStringMaps   = mutableMapOf()
        doubleToIntegersMaps = mutableMapOf()
        doubleToDoublesMaps  = mutableMapOf()
        doubleToBooleansMaps = mutableMapOf()

        booleanToStringMaps   = mutableMapOf()
        booleanToIntegersMaps = mutableMapOf()
        booleanToDoublesMaps  = mutableMapOf()
        booleanToBooleansMaps = mutableMapOf()
    }

    /**
     * the run method that takes the code from the main [TextArea] and splits it line by line to be processed by the [coc] [CommandController]
     */
    fun run() {
        emptyVarArrays()
        log.out("running code");
        val code: String = ca.getText()
        var tempAllLines : List<String> = code.split("\n")
        var lineStartIndex : Int = 0
        var currLine : Int = 0
        for (i in 0..code.length) {
            if (code.get(i) == '\n' && currLine < tempAllLines.size) {
                initialLines.put(Pair(currLine, IntRange(lineStartIndex, i)), tempAllLines[currLine])
                currLine++
            }
        }
        initialLines.forEach { line -> log.out("line: ${line.key.first} range: ${line.key.second} line: ${line.value}") }
//        var matches = allBetweenBraces.findAll(code).toMutableList()
//        val matcht = Regex("\\{\\n\\t\\thfgceuilnbrhuenfga\\(6347219, 654, 675, 345\\)\\n\\t\\}").find(code)
//        if (matcht != null) {
//            log.out("${matcht.value} was found at ${matcht.range}")
//        }
//        matches.forEach { match ->
//            codeBlocks.put
//            log.out("${match.value} found at ${match.range}")
//            Regex("\\{([^{}]*${
//                match.value
//                    .replace("\n", "\\n")
//                    .replace("{", "\\{")
//                    .replace("}", "\\}")
//                    .replace("(", "\\(")
//                    .replace(")", "\\)")
//                    .replace("[", "\\[")
//                    .replace("]", "\\]")
//            }[^{}]*)\\}").find(code)?.let { newMatch ->
//                matches.add(newMatch)
//                matches = recursiveFindMatch(code)
//            }
//        }
//        parseLines(code.split("\n"))
    }

//    private fun recursiveFindMatch(code: String) {
//        matches.forEach { match ->
//            log.out("${match.value} found at ${match.range}")
//            Regex("\\{([^{}]*${
//                match.value
//                    .replace("\n", "\\n")
//                    .replace("{", "\\{")
//                    .replace("}", "\\}")
//                    .replace("(", "\\(")
//                    .replace(")", "\\)")
//                    .replace("[", "\\[")
//                    .replace("]", "\\]")
//            }[^{}]*)\\}").find(code)?.let { newMatch ->
//                matches.add(newMatch)
//                recursiveFindMatch(code)
//            }
//        }
//    }

    fun parseLine(line : String) {
        when {
            line.matches(functionCallRegex) -> {
                val functionCall = functionCallRegex.find(line)!!.groupValues
                runFunction(functionCall[1], "0, ${functionCall[2]}".split(",") as MutableList<String>)
            }
            line.matches(variableDeclarationRegex) -> {
                val varDeclaration = variableDeclarationRegex.find(line)!!.groupValues
                when {
                    varDeclaration[3].matches(stringRegex)  -> {
                        strings.put(Pair(varDeclaration[2], if (varDeclaration[0] == "var") true else false), varDeclaration[3])
                        log.out("${varDeclaration[2]} = ${varDeclaration[3]}")
                    }
                    varDeclaration[3].matches(doubleRegex)  -> {
                        doubles.put(Pair(varDeclaration[2], if (varDeclaration[0] == "var") true else false), varDeclaration[3].toDouble())
                        log.out("${varDeclaration[2]} = ${varDeclaration[3]}")
                    }
                    varDeclaration[3].matches(integerRegex) -> {
                        integers.put(Pair(varDeclaration[2], if (varDeclaration[0] == "var") true else false), varDeclaration[3].toInt())
                        log.out("${varDeclaration[2]} = ${varDeclaration[3]}")
                    }
                    varDeclaration[3].matches(booleanRegex) -> {
                        booleans.put(Pair(varDeclaration[2], if (varDeclaration[0] == "var") true else false), varDeclaration[3].toBoolean())
                        log.out("${varDeclaration[2]} = ${varDeclaration[3]}")
                    }
                    varDeclaration[3].matches(functionCallRegex) -> {
                        log.out("found ${varDeclaration[3]}")
                    }
                    else -> {
                        log.error("invalid parameter $line")
                    }
                }
            }
            line.matches(variableUpdateRegex) -> {
                val varDeclaration = variableUpdateRegex.find(line)!!.groupValues
                when {
                    varDeclaration[3].matches(stringRegex)  -> {
                        strings.put(Pair(varDeclaration[0], true), varDeclaration[1])
                        log.out("${varDeclaration[0]} = ${varDeclaration[1]}")
                    }
                    varDeclaration[3].matches(doubleRegex)  -> {
                        doubles.put(Pair(varDeclaration[0], true), varDeclaration[1].toDouble())
                        log.out("${varDeclaration[0]} = ${varDeclaration[1]}")
                    }
                    varDeclaration[3].matches(integerRegex) -> {
                        integers.put(Pair(varDeclaration[0], true), varDeclaration[1].toInt())
                        log.out("${varDeclaration[0]} = ${varDeclaration[1]}")
                    }
                    varDeclaration[3].matches(booleanRegex) -> {
                        booleans.put(Pair(varDeclaration[0], true), varDeclaration[1].toBoolean())
                        log.out("${varDeclaration[0]} = ${varDeclaration[1]}")
                    }
                    varDeclaration[3].matches(functionCallRegex) -> {
                        log.out("found ${varDeclaration[1]}")
                    }
                    else -> {
                        log.error("invalid parameter $line")
                    }
                }
            }
            line.matches(functionDeclarationRegex) -> {
                val functionDeclaration = functionDeclarationRegex.find(line)!!.groupValues
                functions.put(Pair(functionDeclaration[0], if (functionDeclaration[2].isNotEmpty()) Pair(functionDeclaration[3], true) else Pair(null, false)), listOf(""))
            }
            line.matches(ifRegex) -> {
                val ifDeclaration = ifRegex.find(line)!!.groupValues
                //evaluate condition ifDeclaration[0]
            }
            line.matches(elifRegex) -> {
                val elifDeclaration = elifRegex.find(line)!!.groupValues
                //evaluate condition elifDeclaration[0]
            }
            line.matches(elseRegex) -> {
                val elseDeclaration = elseRegex.find(line)!!.groupValues
                //evaluate condition elseDeclaration[0]
            }
            line.matches(forRegex) -> {
                val forLoopDeclaration = forRegex.find(line)!!.groupValues
                //create list of lines from loop properties
            }
            line.matches(foreachRegex) -> {
                val foreachLoopDeclaration = foreachRegex.find(line)!!.groupValues
                //create list of lines from loop properties
            }
            line.matches(whileRegex) -> {
                val whileLoopDeclaration = whileRegex.find(line)!!.groupValues
                //create list of lines from loop properties
            }
            line.matches(switchRegex) -> {
                val switchDeclaration = switchRegex.find(line)!!.groupValues
                //create list of lines from loop properties
            }
        }
    }


    fun runFunction(name : String, params : MutableList<String>) {
        params.forEachIndexed { i, v ->
            params[i] = v.trim()
            if (params[i].matches(stringRegex)) {
                val strings = stringRegex.find(params[i])!!.groupValues
                params[i] = if (strings[1].isEmpty()) strings[2] else strings[1]
            }
        }
        when {
            name.startsWith("oval") -> cac.DrawOval(params)
            name.startsWith("circle") -> cac.DrawCircle(params)
            name.startsWith("rect") -> cac.DrawRect(params)
            name.startsWith("square") -> cac.DrawSquare(params)
            name.startsWith("line") -> cac.DrawLine(params)
            name.startsWith("arc") -> cac.DrawArc(params)
            name.startsWith("polygon") -> cac.DrawPolygon(params)
            name.startsWith("polyline") -> cac.DrawPolyline(params)
            name.startsWith("triangle") -> cac.DrawTriangle(params)
            name.startsWith("text") -> cac.DrawText(params)
            name.startsWith("print") -> log.out(params[0])
        }
    }
}