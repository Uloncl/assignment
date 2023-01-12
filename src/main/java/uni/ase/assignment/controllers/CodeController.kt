package uni.ase.assignment.controllers

import javafx.scene.control.TextArea
import javafx.stage.FileChooser
import javafx.stage.Stage
import uni.ase.assignment.parser.CodeParser
import java.io.File
import java.nio.file.Files

/**
 * controls the code [TextArea], currently provides saving and loading functionality
 *
 * @param code the [TextArea] where the code is written
 * @param cp the [CodeParser] that currently isnt used for anything
 * @param stage the [Stage] which is used by the [FileChooser]s for the saving and loading functionality
 */
class CodeController(val code: TextArea, val cp: CodeParser, val stage: Stage) {
    val SaveLocation : File = File(System.getProperty("user.home") + "/turtle-programs")

    /**
     * saves the text in the [code] [TextArea] to a .tup (turtle program) file specified by the [FileChooser] dialogue
     */
    fun save() {
        if (!SaveLocation.isDirectory) {
            Files.createDirectory(SaveLocation.toPath())
        }
        val fc: FileChooser = FileChooser()
        val filter: FileChooser.ExtensionFilter = FileChooser.ExtensionFilter("Turtle Program File (*.tup)", "*.tup")
        fc.extensionFilters.add(filter)
        fc.initialDirectory = SaveLocation
        fc.title = "Save"
        val loc: File = fc.showSaveDialog(stage)
        if (loc != null) {
            loc.writeText(code.text)
        }
    }

    /**
     * loads code from a .tup (turtle program) file and writes what was in it to the [code] [TextArea]
     */
    fun load() {
        val fc: FileChooser = FileChooser()
        val filter: FileChooser.ExtensionFilter = FileChooser.ExtensionFilter("Turtle Program File (*.tup)", "*.tup")
        fc.extensionFilters.add(filter)
        fc.initialDirectory = SaveLocation
        fc.title = "Load"
        val loc: File = fc.showOpenDialog(stage)
        if (loc != null) {
            val savedCode: String = loc.useLines { it.toList() }.joinToString("\n")
            code.text = savedCode
        }
    }
}