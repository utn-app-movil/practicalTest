package Entity


import java.time.LocalDate
import java.time.LocalTime


class Schedule{


    var fecha: LocalDate = LocalDate.of(2025, 11, 10)
    var hora: LocalTime = LocalTime.of(8, 30)
    var profesor: String = ""
    var aula: String = ""

    constructor(fecha: LocalDate, hora: LocalTime, profesor: String, aula: String) {
        this.fecha = fecha
        this.hora = hora
        this.profesor = profesor
        this.aula = aula

}




    override fun toString(): String {
        return "$fecha $hora $profesor $aula"
    }}

