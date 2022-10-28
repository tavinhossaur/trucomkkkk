package com.example.trucomkkkk

import android.annotation.SuppressLint
import android.content.res.Configuration
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.trucomkkkk.databinding.ActivityMainBinding
import com.maxkeppeler.sheets.options.Option
import com.maxkeppeler.sheets.options.OptionsSheet

class MainActivity : AppCompatActivity() {
    
    private lateinit var binding: ActivityMainBinding

    companion object{
        var ptsNos = 0
        var ptsEles = 0
        var ptsLimite = 12
    }
    
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        binding = ActivityMainBinding.inflate(layoutInflater)
        
        setContentView(binding.root)

        if (ptsLimite == Int.MAX_VALUE){
            binding.limitePts.text = "∞ pts"
        }else{
            binding.limitePts.text = "$ptsLimite pts"
        }

        binding.btnConfig.setOnClickListener {
            binding.btnConfig.isEnabled = false
            OptionsSheet().show(this) {
                title("Selecione o valor máximo de pontos")
                with(
                    Option(R.drawable.ic_round_remove_24, "12 pontos"),
                    Option(R.drawable.ic_round_add_24, "15 pontos"),
                    Option(R.drawable.ic_round_all_inclusive_24, "Infinito")
                )
                onClose { binding.btnConfig.isEnabled = true }
                onPositive { index: Int, _: Option ->
                    when(index){
                        0 -> {
                            if(ptsNos >= 12 || ptsEles >= 12) { reiniciar("A pontuação atual de um dos times já execedeu este limite") }
                            ptsLimite = 12
                            binding.limitePts.text = "$ptsLimite pts"
                            Toast.makeText(this@MainActivity, "Limite de pontos definido para $ptsLimite", Toast.LENGTH_SHORT).show()
                        }
                        1 -> {
                            if(ptsNos >= 15 || ptsEles >= 15) { reiniciar("A pontuação atual de um dos times já execedeu este limite") }
                            ptsLimite = 15
                            binding.limitePts.text = "$ptsLimite pts"
                            Toast.makeText(this@MainActivity, "Limite de pontos definido para $ptsLimite", Toast.LENGTH_SHORT).show()
                        }
                        2 -> {
                            if(ptsNos >= Int.MAX_VALUE || ptsEles >= Int.MAX_VALUE) { reiniciar("A pontuação atual de um dos times já execedeu este limite") }
                            ptsLimite = Int.MAX_VALUE
                            binding.limitePts.text = "∞ pts"
                            Toast.makeText(this@MainActivity, "Limite de pontos definido para infinito", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
        }

        binding.btnTrucoNos.setOnClickListener { 
            ptsNos += 3
            binding.ptsNos.text = ptsNos.toString()
            checarPontos()
        }

        binding.btnTrucoEles.setOnClickListener {
            ptsEles += 3
            binding.ptsEles.text = ptsEles.toString()
            checarPontos()
        }

        binding.btnUmNos.setOnClickListener {
            ++ptsNos
            binding.ptsNos.text = ptsNos.toString()
            checarPontos()
        }

        binding.btnUmEles.setOnClickListener {
            ++ptsEles
            binding.ptsEles.text = ptsEles.toString()
            checarPontos()
        }
        
        binding.btnReiniciar.setOnClickListener { 
            reiniciar("Partida reiniciada")
        }
    }

    private fun reiniciar(toast : String){
        ptsNos = 0
        ptsEles = 0
        binding.ptsNos.text = ptsNos.toString()
        binding.ptsEles.text = ptsEles.toString()
        binding.btnTrucoNos.isEnabled = true
        binding.btnTrucoEles.isEnabled = true
        binding.btnTrucoNos.background.alpha = 255
        binding.btnTrucoEles.background.alpha = 255
        if (resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK == Configuration.UI_MODE_NIGHT_YES) {
            binding.btnTrucoNos.setCardBackgroundColor(ContextCompat.getColor(this, R.color.black2))
            binding.btnTrucoEles.setCardBackgroundColor(ContextCompat.getColor(this, R.color.black2))
        }else{
            binding.btnTrucoNos.setCardBackgroundColor(ContextCompat.getColor(this, com.google.android.material.R.color.cardview_light_background))
            binding.btnTrucoEles.setCardBackgroundColor(ContextCompat.getColor(this, com.google.android.material.R.color.cardview_light_background))
        }
        Toast.makeText(this, toast, Toast.LENGTH_SHORT).show()
    }

    private fun checarPontos(){
        when{
            ptsNos >= ptsLimite -> {
                reiniciar("Nós ganhamos 😁")
            }
            ptsEles >= ptsLimite -> {
                reiniciar("Eles ganharam 😒")
            }
        }

        if (ptsNos == ptsLimite - 1 ){
            binding.btnTrucoNos.isEnabled = false
            if (resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK == Configuration.UI_MODE_NIGHT_YES){
                binding.btnTrucoNos.background.alpha = 64
            }else{
                binding.btnTrucoNos.setCardBackgroundColor(ContextCompat.getColor(this, R.color.grey))
            }
        }

        if (ptsEles == ptsLimite - 1){
            binding.btnTrucoEles.isEnabled = false
            if (resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK == Configuration.UI_MODE_NIGHT_YES){
                binding.btnTrucoEles.background.alpha = 64
            }else{
                binding.btnTrucoEles.setCardBackgroundColor(ContextCompat.getColor(this, R.color.grey))
            }
        }
    }
}