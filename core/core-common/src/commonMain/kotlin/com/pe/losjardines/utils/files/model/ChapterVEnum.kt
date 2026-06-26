package com.pe.losjardines.utils.files.model

enum class TravelReason {

    VACATION_RECREATION,
    FAMILY_VISIT,
    EDUCATION,
    HEALTH,
    RELIGION,
    SHOPPING,
    BUSINESS,
    WORK,
    OTHER,

    TOTAL_ARRIVALS;

    companion object {
        fun getTravelReason(name: String): TravelReason = when (name.trim()) {
            "Vacaciones, recreo u ocio"                      -> VACATION_RECREATION
            "Visitas a familiares y amigos"                  -> FAMILY_VISIT
            "Educación y formación"                          -> EDUCATION
            "Salud y atención médica"                        -> HEALTH
            "Religión o peregrinaciones"                     -> RELIGION
            "Compras (excepto para reventa)"                 -> SHOPPING
            "Negocios y motivos profesionales"               -> BUSINESS
            "Trabajo remunerado en lugar de alojamiento"     -> WORK
            else                                             -> OTHER
        }
    }
}

enum class GuestCategory {
    FOREIGNERS,
    PERUVIANS,
    TOTAL
}