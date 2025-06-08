package com.pe.losjardines.domain.utils

import com.pe.losjardines.data.network.dto.ClientDto
import com.pe.losjardines.domain.model.ClientFilter
import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

fun List<ClientDto>.filterClients(filter: ClientFilter): List<ClientDto>{
    val dateNow = getDateNow()
    return when(filter){
            is ClientFilter.None -> this.filterSorted { it.fecha.isDateValid("${dateNow.monthNumber.getMonthString()}/${dateNow.year}") }
            is ClientFilter.Month -> this.filterSorted { it.fecha.isDateValid("${filter.month}/${dateNow.year}") }
            is ClientFilter.Origin -> this.filterSorted { it.procedencia.lowercase() == filter.origin.lowercase() && it.fecha.isDateValid("${filter.month}/${dateNow.year}")}
            is ClientFilter.DNI -> this.filterSorted { it.dni.lowercase() == filter.dni.lowercase() && it.fecha.isDateValid("${filter.month}/${dateNow.year}") }
    }
}



inline fun <T> List<T>.filterSorted(predicate: (T) -> Boolean): List<T>{
    val destination = ArrayList<T>()
    val foundValid = false

    for (indices in this.indices.reversed()){
        if(predicate(this[indices])){
            destination.add(this[indices])
        } else if(foundValid){
            break
        }
    }
    return destination
}



fun String.isDateValid(dateFilter: String): Boolean {
    try {
        val dateRegisterSplit = this.split("/")
        val dateFilterSplit = dateFilter.split("/")
        if(dateFilterSplit[0].isEmpty()) return true
        return dateRegisterSplit[1] == dateFilterSplit[0] && dateRegisterSplit[2] == dateFilterSplit[1]
    }catch (e: Exception){
        return false
    }
}

fun Int.getMonthString(): String{
    return try{
        if(this < 10) "0$this"
        else this.toString()
    } catch (e: Exception){
        return "0"
    }
}

fun getDateNow(): LocalDate{
    return Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date
}