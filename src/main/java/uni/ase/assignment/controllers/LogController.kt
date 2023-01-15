package uni.ase.assignment.controllers

import javafx.scene.control.TextArea

/**
 * controller for controlling the log [TextArea] used for displaying outputs and errors
 *
 * @param log the [TextArea] where the error messages will be displayed
 */
class LogController (val log: TextArea) {
    /**
     * prints custom error messages in the [log]
     *
     * @param text the error message to be printed
     */
    fun error(text: String) {
        log.appendText("ERROR: $text\n")
    }

    /**
     * prints log messages to the output [log]
     *
     * @param text the log message to be printed
     */
    fun out(text: String) {
        log.appendText("$text\n")
    }

    /**
     * clears the log
     */
    fun clear() {
        log.clear()
    }
}