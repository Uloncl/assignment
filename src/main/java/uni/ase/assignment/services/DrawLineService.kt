package uni.ase.assignment.services

import javafx.beans.property.SimpleStringProperty
import javafx.beans.property.StringProperty
import javafx.concurrent.Service
import javafx.concurrent.Task
import uni.ase.assignment.controllers.CanvasController

class DrawLineService(cac : CanvasController) : Service<Boolean>() {
    var params : StringProperty = SimpleStringProperty("")
    fun setParams(text : String) {
        println("newtext: $text")
        params = SimpleStringProperty(text)
    }
    fun getParams() : String {
        return params.get()
    }
    fun getParamsProperty() : StringProperty {
        return params
    }
    override fun createTask(): Task<Boolean> {
        return object : Task<Boolean>() {
            var params : String = getParams()
            @Throws(Exception::class)
            override fun call(): Boolean {
                return false
            }
        }
    }
}
