package uni.ase.assignment.controllers

import javafx.scene.control.TextArea

class CodeParser (val ca: TextArea, val cac: CanvasController, val log: LogController, val coc: ConsoleController) {
    fun run() {
        log.out("running code");
        val code: List<String> = ca.getText().split("\n")
        code.forEach { cmd ->
            if (cmd != "") {
                coc.run(cmd.substringBefore('(') + " " + (cmd.subSequence(cmd.indexOf('('), cmd.indexOf(')'))).split(",").joinToString(separator = " "))
        }}
//        val funstar: MutableList<Int> = mutableListOf<Int>()
//        val funends: MutableList<Int> = mutableListOf<Int>()
//        code.toCharArray().forEachIndexed { i, e -> if (e == '{') funstar.add(i) else if (e == '}') funends.add(i) }
    }
}