package com.example.mobilliumchallenge.utils

import android.annotation.SuppressLint
import android.util.Log
import android.widget.DatePicker
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

object D {

    fun String.getYearFromDateString() :String{
        val date = stringToDate(this)
        return getYearFromDate(date)
    }
    fun String.getDateFromDateString() :String{
        val date = stringToDate(this)
        return getDateWithDots(date)
    }


    fun getWithFormat(date: Date?, format: String?): String {
        val dateFormat = SimpleDateFormat(format, Locale.US)
        return dateFormat.format(date)
        /*
        String dayOfTheWeek = (String) DateFormat.format("EEEE", date); // Thursday
        String day          = (String) DateFormat.format("dd",   date); // 20
        String monthString  = (String) DateFormat.format("MMM",  date); // Jun
        String monthNumber  = (String) DateFormat.format("MM",   date); // 06
        String year         = (String) DateFormat.format("yyyy", date); // 2013
        String time         = (String) DateFormat.format("hh:mm aa",date); // 06:30 PM */


    }

    fun stringToDate(date: String): Date? {
//        2021-12-01T06:52:24.147463Z
//        2001-07-04T12:08:56.235-0700   "yyyy-MM-dd'T'HH:mm:ss.SSSZ"

        val df = SimpleDateFormat("yyyy-MM-dd", Locale.US)
        return try {
            df.parse(date)
        } catch (e: ParseException) {
            e.printStackTrace()
            null
        }
    }

    fun dateToString(date: Date?): String? {
        val dateFormat = SimpleDateFormat("dd MMMM")
        return try {
            date?.let {
                dateFormat.format(date)
            }
        } catch (e: ParseException) {
            e.printStackTrace()
            ""
        }
    }
    fun getDateAsString(date: Date?): String? {
        return (getWithFormat(date, "dd") + "-"
                + getWithFormat(date, "MM") + "-"
                + getWithFormat(date, "yyyy") + "")
    }
    fun getYearFromDate(date: Date?): String {
        return (getWithFormat(date, "yyyy"))
    }

    fun getDateAsStringForRequest(date: Date?): String? {
        return (getWithFormat(date, "yyyy") + "-"
                + getWithFormat(date, "MM") + "-"
                + getWithFormat(date, "dd") + "")
    }

    fun getDateWithDots(date: Date?): String {
        return (getWithFormat(date, "dd") + "."
                + getWithFormat(date, "MM") + "."
                + getWithFormat(date, "yyyy"))
    }

    fun getDateForFilter(date: Date?): String? {
        return (getWithFormat(date, "dd") + "-"
                + getWithFormat(date, "MM") + "-"
                + getWithFormat(date, "yyyy") + "")
    }

    fun getDateFromDatePicker(datePicker: DatePicker): Date? {
        val day = datePicker.dayOfMonth
        val month = datePicker.month
        val year = datePicker.year
        val calendar = Calendar.getInstance()
        calendar[year, month] = day
        return calendar.time
    }


    fun to12Hour(hour: Int, minute: Int): String? {
        var hour = hour
        var timeSet = ""
        if (hour > 12) {
            hour -= 12
            timeSet = "PM"
        } else if (hour == 0) {
            hour += 12
            timeSet = "AM"
        } else if (hour == 12) {
            timeSet = "PM"
        } else {
            timeSet = "AM"
        }
        var min = ""
        min = if (minute < 10) "0$minute" else minute.toString()
        return hour.toString() + ':' +
                min + " " + timeSet
    }


    fun convertToNormalDate(dateReceived: String?): String? {
/*
        String dayOfTheWeek = (String) DateFormat.format("EEEE", date); // Thursday
        String day          = (String) DateFormat.format("dd",   date); // 20
        String monthString  = (String) DateFormat.format("MMM",  date); // Jun
        String monthNumber  = (String) DateFormat.format("MM",   date); // 06
        String year         = (String) DateFormat.format("yyyy", date); // 2013
        String time         = (String) DateFormat.format("hh:mm aa",date); // 06:30 PM */
        return if (dateReceived != null) try {
            @SuppressLint("SimpleDateFormat") val date1 =
                SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss").parse(dateReceived)
            getDateAsString(date1)
        } catch (e: ParseException) {
            e.printStackTrace()
            ""
        } else ""
    }

    fun convertToNormalDate(dateReceived: String?, format: String?): String? {
/*
        String dayOfTheWeek = (String) DateFormat.format("EEEE", date); // Thursday
        String day          = (String) DateFormat.format("dd",   date); // 20
        String monthString  = (String) DateFormat.format("MMM",  date); // Jun
        String monthNumber  = (String) DateFormat.format("MM",   date); // 06
        String year         = (String) DateFormat.format("yyyy", date); // 2013
        String time         = (String) DateFormat.format("hh:mm aa",date); // 06:30 PM */
        return try {
            @SuppressLint("SimpleDateFormat") val date1 =
                SimpleDateFormat(format).parse(dateReceived)
            getDateAsString(date1)
        } catch (e: ParseException) {
            e.printStackTrace()
            ""
        }
    }


    fun getCurrentDate(current: Calendar): String? {
        var current = current
        current = Calendar.getInstance()
        return getDateAsStringForRequest(current.time)
    }

    fun getOneMonthAgoDate(oneMonthAgo: Calendar): String? {
        var oneMonthAgo = oneMonthAgo
        oneMonthAgo = Calendar.getInstance()
        oneMonthAgo.add(Calendar.MONTH, -1)
        return getDateAsStringForRequest(oneMonthAgo.time)
    }


    fun isPassedOneDay(requestDate: Date?): Boolean? {
        val cal = Calendar.getInstance() //Requestin tarihi
        val currentCal = Calendar.getInstance()
        cal.time = requestDate
        cal.add(Calendar.DATE, 1)
        Log.d(
            "DateTools",
            "isPassedOneDay: requset date +1 :" + cal.time + "current date:" + currentCal.time
        )
        return !currentCal.time.before(cal.time)
    }
}