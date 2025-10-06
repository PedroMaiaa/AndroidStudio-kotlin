package com.example.tipcalculator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tipcalculator.ui.theme.TipCalculatorTheme
import java.text.NumberFormat

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TipCalculatorTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    TipAppLayout()
                }
            }
        }
    }
}

@Composable
fun TipAppLayout() {
    var inputConta by remember { mutableStateOf("") }
    val valor = inputConta.toDoubleOrNull() ?: 0.0
    val gorjetaFormatada = calcularGorjeta(valor)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 32.dp)
            .padding(top = 40.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(R.string.title_calculate_tip),
            fontSize = 24.sp,
            color = Color(0xFF2E7D32),
            modifier = Modifier
                .align(Alignment.Start)
                .padding(bottom = 16.dp)
        )

        InputCampo(
            valorTexto = inputConta,
            aoMudarTexto = { novo -> inputConta = novo },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 24.dp)
        )

        Text(
            text = stringResource(R.string.tip_amount, gorjetaFormatada),
            fontSize = 22.sp,
            color = Color(0xFF0277BD)
        )

        Spacer(modifier = Modifier.height(200.dp))
    }
}

@Composable
fun InputCampo(
    valorTexto: String,
    aoMudarTexto: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    TextField(
        value = valorTexto,
        onValueChange = aoMudarTexto,
        label = { Text(stringResource(R.string.label_bill_amount)) },
        singleLine = true,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        modifier = modifier,
        colors = TextFieldDefaults.colors(
            focusedContainerColor = Color(0xFFFFF8E1),
            unfocusedContainerColor = Color(0xFFFFF8E1),
            focusedIndicatorColor = Color(0xFFFF6F00),
            unfocusedIndicatorColor = Color(0xFFFFA000)
        )
    )
}

fun calcularGorjeta(conta: Double, percentual: Double = 15.0): String {
    val gorjeta = conta * percentual / 100
    return NumberFormat.getCurrencyInstance().format(gorjeta)
}

@Preview(showBackground = true)
@Composable
fun TipAppPreview() {
    TipCalculatorTheme {
        TipAppLayout()
    }
}