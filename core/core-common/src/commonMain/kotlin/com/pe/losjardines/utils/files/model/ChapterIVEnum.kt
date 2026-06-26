package com.pe.losjardines.utils.files.model

enum class ResidenceMetric {
    ARRIVALS,
    OVERNIGHTS
}

enum class ForeignCountry {

    ARGENTINA,
    GERMANY,
    BELARUS,
    BOLIVIA,
    BRAZIL,
    CANADA,
    COLOMBIA,
    SOUTH_KOREA,
    COSTA_RICA,
    CHILE,
    CHINA,
    ECUADOR,
    USA,
    SPAIN,
    FRANCE,
    NETHERLANDS,
    INDIA,
    ISRAEL,
    ITALY,
    JAPAN,
    MEXICO,
    PANAMA,
    UNITED_KINGDOM,
    RUSSIA,
    SWITZERLAND,
    TURKEY,
    URUGUAY,
    VENEZUELA,

    AFRICA,
    OCEANIA,
    OTHER_AMERICA,
    OTHER_ASIA,
    OTHER_EUROPE,

    TOTAL;

    companion object {
        fun getCountry(name: String): ForeignCountry? = when (name.trim()) {
            "Argentina"                          -> ARGENTINA
            "Alemania"                           -> GERMANY
            "Bielorrusia"                        -> BELARUS
            "Bolivia"                            -> BOLIVIA
            "Brasil"                             -> BRAZIL
            "Canadá"                             -> CANADA
            "Colombia"                           -> COLOMBIA
            "Corea del Sur"                      -> SOUTH_KOREA
            "Costa Rica"                         -> COSTA_RICA
            "Chile"                              -> CHILE
            "China (Rep. Popular)"               -> CHINA
            "Ecuador"                            -> ECUADOR
            "Estados Unidos"                     -> USA
            "España"                             -> SPAIN
            "Francia"                            -> FRANCE
            "Países Bajos"                       -> NETHERLANDS
            "India"                              -> INDIA
            "Israel"                             -> ISRAEL
            "Italia"                             -> ITALY
            "Japón"                              -> JAPAN
            "México"                             -> MEXICO
            "Panamá"                             -> PANAMA
            "Reino Unido"                        -> UNITED_KINGDOM
            "Rusia"                              -> RUSSIA
            "Suiza"                              -> SWITZERLAND
            "Turquía"                            -> TURKEY
            "Uruguay"                            -> URUGUAY
            "Venezuela"                          -> VENEZUELA
            "África (Ghana, Marruecos, Sudáfrica…)" -> AFRICA
            "Oceanía (Australia…)"               -> OCEANIA
            "Otro país de América"               -> OTHER_AMERICA
            "Otro país de Asia"                  -> OTHER_ASIA
            "Otro país de Europa"                -> OTHER_EUROPE
            else                                 -> null
        }
    }
}

enum class PeruRegion {

    LIMA_METROPOLITANA_CALLAO,
    REGION_LIMA,
    AMAZONAS,
    ANCASH,
    APURIMAC,
    AREQUIPA,
    AYACUCHO,
    CAJAMARCA,
    CUSCO,
    HUANCAVELICA,
    HUANUCO,
    ICA,
    JUNIN,
    LA_LIBERTAD,
    LAMBAYEQUE,
    LORETO,
    MADRE_DE_DIOS,
    MOQUEGUA,
    PASCO,
    PIURA,
    PUNO,
    SAN_MARTIN,
    TACNA,
    TUMBES,
    UCAYALI,

    TOTAL_RESIDENTS,
    TOTAL_GENERAL;

    companion object {
        fun getRegion(name: String): PeruRegion? = when (name.trim()) {
            "Lima Metropolitana y Callao" -> LIMA_METROPOLITANA_CALLAO
            "Región Lima"                 -> REGION_LIMA
            "Amazonas"                    -> AMAZONAS
            "Áncash"                      -> ANCASH
            "Apurímac"                    -> APURIMAC
            "Arequipa"                    -> AREQUIPA
            "Ayacucho"                    -> AYACUCHO
            "Cajamarca"                   -> CAJAMARCA
            "Cusco"                       -> CUSCO
            "Huancavelica"                -> HUANCAVELICA
            "Huánuco"                     -> HUANUCO
            "Ica"                         -> ICA
            "Junín"                       -> JUNIN
            "La Libertad"                 -> LA_LIBERTAD
            "Lambayeque"                  -> LAMBAYEQUE
            "Loreto"                      -> LORETO
            "Madre de Dios"               -> MADRE_DE_DIOS
            "Moquegua"                    -> MOQUEGUA
            "Pasco"                       -> PASCO
            "Piura"                       -> PIURA
            "Puno"                        -> PUNO
            "San Martín"                  -> SAN_MARTIN
            "Tacna"                       -> TACNA
            "Tumbes"                      -> TUMBES
            "Ucayali"                     -> UCAYALI
            else                          -> null
        }
    }
}
