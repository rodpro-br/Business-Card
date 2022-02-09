package br.com.dio.businesscard.ui

import android.graphics.Color
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import br.com.dio.businesscard.App
import br.com.dio.businesscard.R
import br.com.dio.businesscard.data.BusinessCard
import br.com.dio.businesscard.databinding.ActivityAddBusinessCardBinding
import com.flask.colorpicker.ColorPickerView
import com.flask.colorpicker.builder.ColorPickerDialogBuilder


class AddBusinessCardActivity : AppCompatActivity() {

    private val binding by lazy { ActivityAddBusinessCardBinding.inflate(layoutInflater) }

    private val mainViewModel: MainViewModel by viewModels {
        MainViewModelFactory((application as App).repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        insertListeners()
    }

    private fun insertListeners() {
        binding.btnConfirm.setOnClickListener {
            val businessCard = BusinessCard(
                nome = binding.tilNome.editText?.text.toString(),
                empresa = binding.tilEmpresa.editText?.text.toString(),
                telefone = binding.tilTelefone.editText?.text.toString(),
                email = binding.tilEmail.editText?.text.toString(),
                fundoPersonalizado = binding.tilCor.editText?.text.toString()
            )
            mainViewModel.insert(businessCard)
            Toast.makeText(this, R.string.label_show_success, Toast.LENGTH_SHORT).show()
            finish()
        }

        binding.tieCor.setOnClickListener {
 
            ColorPickerDialogBuilder
                .with(this)
                .setTitle("Choose color")
                .initialColor(Color.parseColor(binding.tieCor.text?.toString()))
                .wheelType(ColorPickerView.WHEEL_TYPE.FLOWER)
                .density(12)
                .setOnColorSelectedListener { selectedColor ->
                    Toast.makeText(this, "onColorSelected: #" + Integer.toHexString(selectedColor), Toast.LENGTH_SHORT).show()
                }
                .setPositiveButton(
                    "ok"
                ) { dialog, selectedColor, allColors -> binding.tieCor.setText("#" + Integer.toHexString(selectedColor))}
                .setNegativeButton(
                    "cancel"
                ) { dialog, which -> }
                .build()
                .show()
        }


        binding.btnClose.setOnClickListener {
            finish()
        }
    }
}
