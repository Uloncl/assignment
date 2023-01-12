package uni.ase.assignment.parser.structures.variables

import org.json.JSONObject
import uni.ase.assignment.controllers.LogController
import uni.ase.assignment.parser.structures.blocks.Block

class MapVar (
    name : String,
    val keyType: String?,
    val valType: String?,
    mutable : Boolean,
    scope: Block,
    log : LogController
) : Variable(name, mutable, scope, log) {

    private var mapStringString  : Map<StringVar, StringVar>  = mapOf<StringVar, StringVar>()
    private var mapStringInteger : Map<StringVar, IntegerVar> = mapOf<StringVar, IntegerVar>()
    private var mapStringDouble  : Map<StringVar, DoubleVar>  = mapOf<StringVar, DoubleVar>()
    private var mapStringBoolean : Map<StringVar, BooleanVar> = mapOf<StringVar, BooleanVar>()
    private var mapStringArray   : Map<StringVar, ArrayVar>   = mapOf<StringVar, ArrayVar>()
    private var mapStringMap     : Map<StringVar, MapVar>     = mapOf<StringVar, MapVar>()

    private var mapIntegerString  : Map<IntegerVar, StringVar>  = mapOf<IntegerVar, StringVar>()
    private var mapIntegerInteger : Map<IntegerVar, IntegerVar> = mapOf<IntegerVar, IntegerVar>()
    private var mapIntegerDouble  : Map<IntegerVar, DoubleVar>  = mapOf<IntegerVar, DoubleVar>()
    private var mapIntegerBoolean : Map<IntegerVar, BooleanVar> = mapOf<IntegerVar, BooleanVar>()
    private var mapIntegerArray   : Map<IntegerVar, ArrayVar>   = mapOf<IntegerVar, ArrayVar>()
    private var mapIntegerMap     : Map<IntegerVar, MapVar>     = mapOf<IntegerVar, MapVar>()

    private var mapDoubleString  : Map<DoubleVar, StringVar>  = mapOf<DoubleVar, StringVar>()
    private var mapDoubleInteger : Map<DoubleVar, IntegerVar> = mapOf<DoubleVar, IntegerVar>()
    private var mapDoubleDouble  : Map<DoubleVar, DoubleVar>  = mapOf<DoubleVar, DoubleVar>()
    private var mapDoubleBoolean : Map<DoubleVar, BooleanVar> = mapOf<DoubleVar, BooleanVar>()
    private var mapDoubleArray   : Map<DoubleVar, ArrayVar>   = mapOf<DoubleVar, ArrayVar>()
    private var mapDoubleMap     : Map<DoubleVar, MapVar>     = mapOf<DoubleVar, MapVar>()

    private var mutableMapStringString  : Map<StringVar, StringVar>  = mutableMapOf<StringVar, StringVar>()
    private var mutableMapStringInteger : Map<StringVar, IntegerVar> = mutableMapOf<StringVar, IntegerVar>()
    private var mutableMapStringDouble  : Map<StringVar, DoubleVar>  = mutableMapOf<StringVar, DoubleVar>()
    private var mutableMapStringBoolean : Map<StringVar, BooleanVar> = mutableMapOf<StringVar, BooleanVar>()
    private var mutableMapStringArray   : Map<StringVar, ArrayVar>   = mutableMapOf<StringVar, ArrayVar>()
    private var mutableMapStringMap     : Map<StringVar, MapVar>     = mutableMapOf<StringVar, MapVar>()

    private var mutableMapIntegerString  : Map<IntegerVar, StringVar>  = mutableMapOf<IntegerVar, StringVar>()
    private var mutableMapIntegerInteger : Map<IntegerVar, IntegerVar> = mutableMapOf<IntegerVar, IntegerVar>()
    private var mutableMapIntegerDouble  : Map<IntegerVar, DoubleVar>  = mutableMapOf<IntegerVar, DoubleVar>()
    private var mutableMapIntegerBoolean : Map<IntegerVar, BooleanVar> = mutableMapOf<IntegerVar, BooleanVar>()
    private var mutableMapIntegerArray   : Map<IntegerVar, ArrayVar>   = mutableMapOf<IntegerVar, ArrayVar>()
    private var mutableMapIntegerMap     : Map<IntegerVar, MapVar>     = mutableMapOf<IntegerVar, MapVar>()

    private var mutableMapDoubleString   : Map<DoubleVar, StringVar>  = mutableMapOf<DoubleVar, StringVar>()
    private var mutableMapDoubleInteger  : Map<DoubleVar, IntegerVar> = mutableMapOf<DoubleVar, IntegerVar>()
    private var mutableMapDoubleDouble   : Map<DoubleVar, DoubleVar>  = mutableMapOf<DoubleVar, DoubleVar>()
    private var mutableMapDoubleBoolean  : Map<DoubleVar, BooleanVar> = mutableMapOf<DoubleVar, BooleanVar>()
    private var mutableMapDoubleArray    : Map<DoubleVar, ArrayVar>   = mutableMapOf<DoubleVar, ArrayVar>()
    private var mutableMapDoubleMap      : Map<DoubleVar, MapVar>     = mutableMapOf<DoubleVar, MapVar>()

    var map : Any = ""

    init {
        when {
            mutable == true ->  {
                when {
                    keyType == "String" -> {
                        when {
                            valType == "String" -> {
                                map = mutableMapStringString
                            }
                            valType == "Integer" -> {
                                map = mutableMapStringInteger
                            }
                            valType == "Int" -> {
                                map = mutableMapStringInteger
                            }
                            valType == "Double" -> {
                                map = mutableMapStringDouble
                            }
                            valType == "Boolean" -> {
                                map = mutableMapStringBoolean
                            }
                            valType == "Array" -> {
                                map = mutableMapStringArray
                            }
                            valType == "Map" -> {
                                map = mutableMapStringMap
                            }
                        }
                    }
                    keyType == "Integer" -> {
                        when {
                            valType == "String" -> {
                                map = mutableMapIntegerString
                            }
                            valType == "Integer" -> {
                                map = mutableMapIntegerInteger
                            }
                            valType == "Int" -> {
                                map = mutableMapIntegerInteger
                            }
                            valType == "Double" -> {
                                map = mutableMapIntegerDouble
                            }
                            valType == "Boolean" -> {
                                map = mutableMapIntegerBoolean
                            }
                            valType == "Array" -> {
                                map = mutableMapIntegerArray
                            }
                            valType == "Map" -> {
                                map = mutableMapIntegerMap
                            }
                        }
                    }
                    keyType == "Int" -> {
                        when {
                            valType == "String" -> {
                                map = mutableMapIntegerString
                            }
                            valType == "Integer" -> {
                                map = mutableMapIntegerInteger
                            }
                            valType == "Int" -> {
                                map = mutableMapIntegerInteger
                            }
                            valType == "Double" -> {
                                map = mutableMapIntegerDouble
                            }
                            valType == "Boolean" -> {
                                map = mutableMapIntegerBoolean
                            }
                            valType == "Array" -> {
                                map = mutableMapIntegerArray
                            }
                            valType == "Map" -> {
                                map = mutableMapIntegerMap
                            }
                        }
                    }
                    keyType == "Double" -> {
                        when {
                            valType == "String" -> {
                                map = mutableMapDoubleString
                            }
                            valType == "Integer" -> {
                                map = mutableMapDoubleInteger
                            }
                            valType == "Int" -> {
                                map = mutableMapDoubleInteger
                            }
                            valType == "Double" -> {
                                map = mutableMapDoubleDouble
                            }
                            valType == "Boolean" -> {
                                map = mutableMapDoubleBoolean
                            }
                            valType == "Array" -> {
                                map = mutableMapDoubleArray
                            }
                            valType == "Map" -> {
                                map = mutableMapDoubleMap
                            }
                        }
                    }
                }
            }
            !mutable!! -> {
                when {
                    keyType == "String" -> {
                        when {
                            valType == "String" -> {
                                map = mapStringString
                            }
                            valType == "Integer" -> {
                                map = mapStringInteger
                            }
                            valType == "Int" -> {
                                map = mapStringInteger
                            }
                            valType == "Double" -> {
                                map = mapStringDouble
                            }
                            valType == "Boolean" -> {
                                map = mapStringBoolean
                            }
                            valType == "Array" -> {
                                map = mapStringArray
                            }
                            valType == "Map" -> {
                                map = mapStringMap
                            }
                        }
                    }
                    keyType == "Integer" -> {
                        when {
                            valType == "String" -> {
                                map = mapIntegerString
                            }
                            valType == "Integer" -> {
                                map = mapIntegerInteger
                            }
                            valType == "Int" -> {
                                map = mapIntegerInteger
                            }
                            valType == "Double" -> {
                                map = mapIntegerDouble
                            }
                            valType == "Boolean" -> {
                                map = mapIntegerBoolean
                            }
                            valType == "Array" -> {
                                map = mapIntegerArray
                            }
                            valType == "Map" -> {
                                map = mapIntegerMap
                            }
                        }
                    }
                    keyType == "Int" -> {
                        when {
                            valType == "String" -> {
                                map = mapIntegerString
                            }
                            valType == "Integer" -> {
                                map = mapIntegerInteger
                            }
                            valType == "Int" -> {
                                map = mapIntegerInteger
                            }
                            valType == "Double" -> {
                                map = mapIntegerDouble
                            }
                            valType == "Boolean" -> {
                                map = mapIntegerBoolean
                            }
                            valType == "Array" -> {
                                map = mapIntegerArray
                            }
                            valType == "Map" -> {
                                map = mapIntegerMap
                            }
                        }
                    }
                    keyType == "Double" -> {
                        when {
                            valType == "String" -> {
                                map = mapDoubleString
                            }
                            valType == "Integer" -> {
                                map = mapDoubleInteger
                            }
                            valType == "Int" -> {
                                map = mapDoubleInteger
                            }
                            valType == "Double" -> {
                                map = mapDoubleDouble
                            }
                            valType == "Boolean" -> {
                                map = mapDoubleBoolean
                            }
                            valType == "Array" -> {
                                map = mapDoubleArray
                            }
                            valType == "Map" -> {
                                map = mapDoubleMap
                            }
                        }
                    }
                }
            }
        }
    }

    fun parseCollection (collection : String) {
        log.out("map<$keyType, $valType> detected")
        var collectionArr : Map<Any?, Any?> = mutableMapOf()
        when {
            keyType == "String" -> {
                when {
                    valType == "String" -> {
                        var strstrmap : MutableMap<StringVar, StringVar> = JSONObject("{${collection.substring(1..collection.length-2)}}").toMap().map { k ->
                            var subkey = StringVar(
                                name = "$name${k.key}k",
                                value = k.key.toString(),
                                mutable = mutable,
                                scope = scope,
                                log = log
                            )
                            var subval = StringVar(
                                name = "$name${k.key}v",
                                value = k.value.toString(),
                                mutable = mutable,
                                scope = scope,
                                log = log
                            )
                            return@map Pair(subkey, subval)
                        }.associate { it.first to it.second }.toMutableMap()
                        strstrmap.forEach { t, u -> log.out("${t.value}: ${u.value}") }
                        map = strstrmap
                    }
                    valType == "Int" || valType == "Integer" -> {
                        var strintmap : MutableMap<StringVar, IntegerVar> = JSONObject("{${collection.substring(1..collection.length-2)}}").toMap().map { k ->
                            var subkey = StringVar(
                                name = "$name${k.key}k",
                                value = k.key.toString(),
                                mutable = mutable,
                                scope = scope,
                                log = log
                            )
                            var subval = IntegerVar(
                                name = "$name${k.key}v",
                                value = k.value.toString().toInt(),
                                mutable = mutable,
                                scope = scope,
                                log = log
                            )
                            return@map Pair(subkey, subval)
                        }.associate { it.first to it.second }.toMutableMap()
                        strintmap.forEach { t, u -> log.out("${t.value}: ${u.value}") }
                        map = strintmap
                    }
                    valType == "Double" -> {
                        var strdoumap : MutableMap<StringVar, DoubleVar> = JSONObject("{${collection.substring(1..collection.length-2)}}").toMap().map { k ->
                            var subkey = StringVar(
                                name = "$name${k.key}k",
                                value = k.key.toString(),
                                mutable = mutable,
                                scope = scope,
                                log = log
                            )
                            var subval = DoubleVar(
                                name = "$name${k.key}v",
                                value = k.value.toString().toDouble(),
                                mutable = mutable,
                                scope = scope,
                                log = log
                            )
                            return@map Pair(subkey, subval)
                        }.associate { it.first to it.second }.toMutableMap()
                        strdoumap.forEach { t, u -> log.out("${t.value}: ${u.value}") }
                        map = strdoumap
                    }
                    valType == "Boolean" -> {
                        var strboomap : MutableMap<StringVar, BooleanVar> = JSONObject("{${collection.substring(1..collection.length-2)}}").toMap().map { k ->
                            var subkey = StringVar(
                                name = "$name${k.key}k",
                                value = k.key.toString(),
                                mutable = mutable,
                                scope = scope,
                                log = log
                            )
                            var subval = BooleanVar(
                                name = "$name${k.key}v",
                                value = k.value.toString().toBoolean(),
                                mutable = mutable,
                                scope = scope,
                                log = log
                            )
                            return@map Pair(subkey, subval)
                        }.associate { it.first to it.second }.toMutableMap()
                        strboomap.forEach { t, u -> log.out("${t.value}: ${u.value}") }
                        map = strboomap
                    }
                }
            }
            keyType == "Int" || keyType == "Integer" -> {
                when {
                    valType == "String" -> {
                        var intstrmap : MutableMap<IntegerVar, StringVar> = JSONObject("{${collection.substring(1..collection.length-2)}}").toMap().map { k ->
                            var subkey = IntegerVar(
                                name = "$name${k.key}k",
                                value = k.key.toInt(),
                                mutable = mutable,
                                scope = scope,
                                log = log
                            )
                            var subval = StringVar(
                                name = "$name${k.key}v",
                                value = k.value.toString(),
                                mutable = mutable,
                                scope = scope,
                                log = log
                            )
                            return@map Pair(subkey, subval)
                        }.associate { it.first to it.second }.toMutableMap()
                        intstrmap.forEach { t, u -> log.out("${t.value}: ${u.value}") }
                        map = intstrmap
                    }
                    valType == "Int" || valType == "Integer" -> {
                        var intintmap : MutableMap<IntegerVar, IntegerVar> = JSONObject("{${collection.substring(1..collection.length-2)}}").toMap().map { k ->
                            var subkey = IntegerVar(
                                name = "$name${k.key}k",
                                value = k.key.toInt(),
                                mutable = mutable,
                                scope = scope,
                                log = log
                            )
                            var subval = IntegerVar(
                                name = "$name${k.key}v",
                                value = k.value.toString().toInt(),
                                mutable = mutable,
                                scope = scope,
                                log = log
                            )
                            return@map Pair(subkey, subval)
                        }.associate { it.first to it.second }.toMutableMap()
                        intintmap.forEach { t, u -> log.out("${t.value}: ${u.value}") }
                        map = intintmap
                    }
                    valType == "Double" -> {
                        var intdoumap : MutableMap<IntegerVar, DoubleVar> = JSONObject("{${collection.substring(1..collection.length-2)}}").toMap().map { k ->
                            var subkey = IntegerVar(
                                name = "$name${k.key}k",
                                value = k.key.toInt(),
                                mutable = mutable,
                                scope = scope,
                                log = log
                            )
                            var subval = DoubleVar(
                                name = "$name${k.key}v",
                                value = k.value.toString().toDouble(),
                                mutable = mutable,
                                scope = scope,
                                log = log
                            )
                            return@map Pair(subkey, subval)
                        }.associate { it.first to it.second }.toMutableMap()
                        intdoumap.forEach { t, u -> log.out("${t.value}: ${u.value}") }
                        map = intdoumap
                    }
                    valType == "Boolean" -> {
                        var intboomap : MutableMap<IntegerVar, BooleanVar> = JSONObject("{${collection.substring(1..collection.length-2)}}").toMap().map { k ->
                            var subkey = IntegerVar(
                                name = "$name${k.key}k",
                                value = k.key.toInt(),
                                mutable = mutable,
                                scope = scope,
                                log = log
                            )
                            var subval = BooleanVar(
                                name = "$name${k.key}v",
                                value = k.value.toString().toBoolean(),
                                mutable = mutable,
                                scope = scope,
                                log = log
                            )
                            return@map Pair(subkey, subval)
                        }.associate { it.first to it.second }.toMutableMap()
                        intboomap.forEach { t, u -> log.out("${t.value}: ${u.value}") }
                        map = intboomap
                    }
                }
            }
            keyType == "Double" -> {
                when {
                    valType == "String" -> {
                        var doustrmap : MutableMap<DoubleVar, StringVar> = JSONObject("{${collection.substring(1..collection.length-2)}}").toMap().map { k ->
                            var subkey = DoubleVar(
                                name = "$name${k.key}k",
                                value = k.key.toDouble(),
                                mutable = mutable,
                                scope = scope,
                                log = log
                            )
                            var subval = StringVar(
                                name = "$name${k.key}v",
                                value = k.value.toString(),
                                mutable = mutable,
                                scope = scope,
                                log = log
                            )
                            return@map Pair(subkey, subval)
                        }.associate { it.first to it.second }.toMutableMap()
                        doustrmap.forEach { t, u -> log.out("${t.value}: ${u.value}") }
                        map = doustrmap
                    }
                    valType == "Int" || valType == "Integer" -> {
                        var douintmap : MutableMap<DoubleVar, IntegerVar> = JSONObject("{${collection.substring(1..collection.length-2)}}").toMap().map { k ->
                            var subkey = DoubleVar(
                                name = "$name${k.key}k",
                                value = k.key.toDouble(),
                                mutable = mutable,
                                scope = scope,
                                log = log
                            )
                            var subval = IntegerVar(
                                name = "$name${k.key}v",
                                value = k.value.toString().toInt(),
                                mutable = mutable,
                                scope = scope,
                                log = log
                            )
                            return@map Pair(subkey, subval)
                        }.associate { it.first to it.second }.toMutableMap()
                        douintmap.forEach { t, u -> log.out("${t.value}: ${u.value}") }
                        map = douintmap
                    }
                    valType == "Double" -> {
                        var doudoumap : MutableMap<DoubleVar, DoubleVar> = JSONObject("{${collection.substring(1..collection.length-2)}}").toMap().map { k ->
                            var subkey = DoubleVar(
                                name = "$name${k.key}k",
                                value = k.key.toDouble(),
                                mutable = mutable,
                                scope = scope,
                                log = log
                            )
                            var subval = DoubleVar(
                                name = "$name${k.key}v",
                                value = k.value.toString().toDouble(),
                                mutable = mutable,
                                scope = scope,
                                log = log
                            )
                            return@map Pair(subkey, subval)
                        }.associate { it.first to it.second }.toMutableMap()
                        doudoumap.forEach { t, u -> log.out("${t.value}: ${u.value}") }
                        map = doudoumap
                    }
                    valType == "Boolean" -> {
                        var douboomap : MutableMap<DoubleVar, BooleanVar> = JSONObject("{${collection.substring(1..collection.length-2)}}").toMap().map { k ->
                            var subkey = DoubleVar(
                                name = "$name${k.key}k",
                                value = k.key.toDouble(),
                                mutable = mutable,
                                scope = scope,
                                log = log
                            )
                            var subval = BooleanVar(
                                name = "$name${k.key}v",
                                value = k.value.toString().toBoolean(),
                                mutable = mutable,
                                scope = scope,
                                log = log
                            )
                            return@map Pair(subkey, subval)
                        }.associate { it.first to it.second }.toMutableMap()
                        douboomap.forEach { t, u -> log.out("${t.value}: ${u.value}") }
                        map = douboomap
                    }
                }
            }
            else -> {
                null
            }
        }
    }
}