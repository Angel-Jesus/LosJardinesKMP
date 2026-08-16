package com.pe.losjardines.presentation.content.reservation.contract

import com.pe.losjardines.base.ui.BaseEffect

/**
 * Efectos de una sola emisión (navegación, mensajes puntuales, etc.) de la sección Reservation.
 *
 * Se consumen desde la pantalla observando el canal `effect` del [com.pe.losjardines.base.ui.BaseViewModel].
 */
sealed interface ReservationEffect: BaseEffect {
}
