package uni.ase.assignment.parser.structures.variables

import org.json.JSONObject
import uni.ase.assignment.controllers.LogController
import uni.ase.assignment.parser.structures.blocks.Block

class ArrayVar (
    name : String,
    val type: String,
    mutable : Boolean,
    scope: Block,
    log : LogController
) : Variable(name, mutable, scope, log) {
    private var stringArray  : List<StringVar>  = listOf<StringVar>()
    private var integerArray : List<IntegerVar> = listOf<IntegerVar>()
    private var doubleArray  : List<DoubleVar>  = listOf<DoubleVar>()
    private var booleanArray : List<BooleanVar> = listOf<BooleanVar>()
    private var arrayArray : List<ArrayVar> = listOf<ArrayVar>()

    private var mutableStringArray  : MutableList<StringVar>  = mutableListOf<StringVar>()
    private var mutableIntegerArray : MutableList<IntegerVar> = mutableListOf<IntegerVar>()
    private var mutableDoubleArray  : MutableList<DoubleVar>  = mutableListOf<DoubleVar>()
    private var mutableBooleanArray : MutableList<BooleanVar> = mutableListOf<BooleanVar>()
    private var mutableArrayArray   : MutableList<ArrayVar>   = mutableListOf<ArrayVar>()

    var array : Any = ""

    init {
        when {
            mutable == true ->  {
                when {
                    type == "String" -> {
                        array = mutableStringArray
                    }
                    type == "Integer" -> {
                        array = mutableIntegerArray
                    }
                    type == "Int" -> {
                        array = mutableIntegerArray
                    }
                    type == "Double" -> {
                        array = mutableDoubleArray
                    }
                    type == "Boolean" -> {
                        array = mutableBooleanArray
                    }
                    type == "Array" -> {
                        array = mutableArrayArray
                    }
                }
            }
            !mutable!! -> {
                when {
                    type == "String" -> {
                        array = stringArray
                    }
                    type == "Integer" -> {
                        array = integerArray
                    }
                    type == "Int" -> {
                        array = integerArray
                    }
                    type == "Double" -> {
                        array = doubleArray
                    }
                    type == "Boolean" -> {
                        array = booleanArray
                    }
                    type == "Array" -> {
                        array = arrayArray
                    }
                }
            }
        }
    }

    fun parseCollection (collection : String) {
        log.out("array detected")
        var collectionArr : MutableList<Any?> = mutableListOf()
        log.out("type: $type")
        when {
            type == "String" -> {
                var strarr : MutableList<StringVar> = JSONObject("{\"stringarr\":${collection}}").getJSONArray("stringarr").mapIndexed { i, s ->
                    StringVar(
                        name = "$name$i",
                        value = s.toString(),
                        mutable = mutable,
                        scope = scope,
                        log = log
                    )
                }.toMutableList()
                strarr.forEach { log.out("${it.value}") }
                array = strarr
            }
            type == "Int" || type == "Integer" -> {
                var intarr : MutableList<IntegerVar> = JSONObject("{\"integerarr\":${collection}}").getJSONArray("integerarr").mapIndexed { i, s ->
                    IntegerVar(
                        name = "$name$i",
                        value = s.toString().toInt(),
                        mutable = mutable,
                        scope = scope,
                        log = log
                    )
                }.toMutableList()
                intarr.forEach { log.out("${it.value}") }
                array = intarr
            }
            type == "Double" -> {
                var doublearr : MutableList<DoubleVar> = JSONObject("{\"doublearr\":${collection}}").getJSONArray("doublearr").mapIndexed { i, s ->
                    DoubleVar(
                        name = "$name$i",
                        value = s.toString().toDouble(),
                        mutable = mutable,
                        scope = scope,
                        log = log
                    )
                }.toMutableList()
                doublearr.forEach { log.out("${it.value}") }
                array = doublearr
            }
            type == "Boolean" -> {
                var boolarr : MutableList<BooleanVar> = JSONObject("{\"boolarr\":${collection}}").getJSONArray("boolarr").mapIndexed { i, s ->
                    BooleanVar(
                        name = "$name$i",
                        value = s.toString().toBoolean(),
                        mutable = mutable,
                        scope = scope,
                        log = log
                    )
                }.toMutableList()
                boolarr.forEach { log.out("${it.value}") }
                array = boolarr
            }
            else -> {
                null
            }
        }
    }
}