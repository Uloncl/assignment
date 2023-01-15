package uni.ase.assignment.services

import javafx.concurrent.Service
import javafx.concurrent.Task
import javafx.scene.paint.Paint
import org.kordamp.ikonli.javafx.FontIcon
import uni.ase.assignment.parser.CodeParser

class CodeParserService (
    val codeParser: CodeParser,
    var runBtn : FontIcon,
    var stopBtn : FontIcon
) : Service<String?>() {
    init {
        this.setOnRunning {
            runBtn.iconColor = Paint.valueOf("AAAAAA")
            stopBtn.iconColor = Paint.valueOf("FF0000")
        }
        this.setOnCancelled {
            runBtn.iconColor = Paint.valueOf("00FF00")
            stopBtn.iconColor = Paint.valueOf("AAAAAA")
        }
        this.setOnFailed {
            runBtn.iconColor = Paint.valueOf("00FF00")
            stopBtn.iconColor = Paint.valueOf("AAAAAA")
        }
        this.setOnSucceeded {
            runBtn.iconColor = Paint.valueOf("00FF00")
            stopBtn.iconColor = Paint.valueOf("AAAAAA")
        }
    }
    override fun createTask(): Task<String?> {
        return object : Task<String?>() {
            @Throws(Exception::class)
            override fun call(): String? {
                return ""
            }
        }
    }
}
