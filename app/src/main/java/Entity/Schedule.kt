package Entity

import java.sql.Time
import java.time.LocalTime

class Schedule {
    private var ScheduleId:         String  =   ""
    private var ScheduleName:       String  =   ""
    private var Daylist:            String  =   ""
    private lateinit var HourStart: LocalTime
    private lateinit var HourEnd:   LocalTime


        constructor(ScheduleId: String, ScheduleName: String, Daylist: String, HourStart: LocalTime, HourEnd: LocalTime)
    {
        this.ScheduleId     = ScheduleId
        this.ScheduleName   = ScheduleName
        this.Daylist        = Daylist
        this.HourStart      = HourStart
        this.HourEnd        =   HourEnd
    }

    var idSchedule: String
        get() = this.idSchedule
        set(value) {this.idSchedule=value}

    var Schedule_Name: String
        get() = this.Schedule_Name
        set(value) {this.Schedule_Name=value}

    var Day_list: String
        get() = this.Day_list
        set(value) {this.Day_list=value}

    var Hour_Start: LocalTime
        get() = this.Hour_Start
        set(value) {this.Hour_Start=value}

    var Hour_End: LocalTime
        get() = this.Hour_End
        set(value) {this.Hour_End=value}
}