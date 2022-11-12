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
    /**
     * the run method that takes the code from the main [TextArea] and splits it line by line to be processed by the [coc] [CommandController]
     */
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