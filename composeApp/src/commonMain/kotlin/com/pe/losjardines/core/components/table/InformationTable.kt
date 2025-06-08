package com.pe.losjardines.core.components.table

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.pe.losjardines.core.values.AppTypography
import com.pe.losjardines.core.values.BackgroundLightColor
import com.pe.losjardines.core.values.BlackTextColor
import com.pe.losjardines.domain.model.ClientModel
import com.pe.losjardines.presentation.constance.TableConstance.headerTitleWidth
import com.pe.losjardines.presentation.enums.TitleClient
import com.pe.losjardines.presentation.enums.TitleClient.*
import com.pe.losjardines.presentation.model.DataUpdate

@Composable
fun UserInformationTable(
    modifier: Modifier = Modifier,
    scrollRow: ScrollState,
    typography: AppTypography,
    user: ClientModel,
    onClick: (dataUpdate: DataUpdate) -> Unit,
    onDelete: (client: ClientModel) -> Unit
){
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .horizontalScroll(scrollRow),
        horizontalArrangement = Arrangement.Center
    ) {

        TextInformation(
            text = user.room,
            width = headerTitleWidth[0],
            onClick = {
                onClick(DataUpdate(
                    type = ROOM,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    value = user.room,
                    userModel = user
            ))},
            onLongPressed = { onDelete(user) },
            typography = typography
        )

        TextInformation(
            text = user.name,
            width = headerTitleWidth[1],
            onClick = {
                onClick(DataUpdate(
                    type = NAME,
                    value = user.name,
                    userModel = user
            ))},
            onLongPressed = { onDelete(user) },
            typography = typography
        )

        TextInformation(
            text = user.dni,
            width = headerTitleWidth[2],
            onClick = {
                onClick(DataUpdate(
                    type = DNI,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    value = user.dni,
                    userModel = user
                ))
            },
            onLongPressed = { onDelete(user) },
            typography = typography
        )

        TextInformation(
            text = user.price,
            width = headerTitleWidth[3],
            onClick = {
                onClick(DataUpdate(
                    type = PRICE,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    value = user.price,
                    userModel = user
                ))
            },
            onLongPressed = { onDelete(user) },
            typography = typography
        )

        TextInformation(
            text = user.date,
            width = headerTitleWidth[4],
            onClick = {
                onClick(DataUpdate(
                    type = DATE,
                    value = user.date,
                    userModel = user
                ))
            },
            onLongPressed = { onDelete(user) },
            typography = typography
        )

        TextInformation(
            text = user.time,
            width = headerTitleWidth[5],
            onClick = {
                onClick(DataUpdate(
                    type = TIME,
                    value = user.time,
                    userModel = user
                ))
            },
            onLongPressed = { onDelete(user) },
            typography = typography
        )

        TextInformation(
            text = user.origin,
            width = headerTitleWidth[6],
            onClick = {
                onClick(DataUpdate(
                    type = ORIGIN,
                    value = user.origin,
                    userModel = user
                ))
            },
            onLongPressed = { onDelete(user) },
            typography = typography
        )

        TextInformation(
            text = user.observation,
            width = headerTitleWidth[7],
            onClick = {
                onClick(DataUpdate(
                    type = OBSERVATION,
                    value = user.observation,
                    userModel = user
                ))
            },
            onLongPressed = { onDelete(user) },
            typography = typography
        )

    }
}

@Composable
fun TextInformation(
    text: String,
    width: Int,
    onClick: () -> Unit,
    onLongPressed: () -> Unit = {},
    typography: AppTypography
){
    Text(
        text = text,
        modifier = Modifier
            .width(width.dp)
            .height(60.dp)
            .background(color = BackgroundLightColor)
            .wrapContentHeight(align = Alignment.CenterVertically)
            .pointerInput(Unit) {
                detectTapGestures(
                    onTap = {
                        onClick()
                    },
                    onLongPress = {
                        onLongPressed()
                    }
                )
            },
        color = BlackTextColor,
        style = typography.bodyLarge,
        textAlign = TextAlign.Center,
    )
}