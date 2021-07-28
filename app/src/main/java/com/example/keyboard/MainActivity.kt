package com.example.keyboard

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.keyboard.databinding.ActivityMainBinding
import java.text.NumberFormat
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    var lastNumeric: Boolean = false
    var lastDot: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val actionBar = supportActionBar
        actionBar!!.hide()
    }

    fun onDigit(view: View) {
        setAED(view)

        if (lastDot) {
            setNumberAfterDot(view)
        } else {
            setNumberBeforeDot(view)
        }
    }

    fun onDecimalPoint(view: View) {
        if (lastNumeric && !lastDot) {
            binding.numberScreen.dot.text = "."
            lastNumeric = false
            lastDot = true
        }
    }

    fun onClear(view: View) {
        if (lastDot && binding.numberScreen.lastZero.text.length > 0) {
         clearAfterDot()
        } else {
         clearBeforDot()
        }
    }

    fun setAED(view: View) {
        if (!lastNumeric && !lastDot && view != binding.btnZero) {
            binding.numberScreen.AED.text = "AED"
        }
    }

    fun setNumberAfterDot(view: View) {
        if (binding.numberScreen.lastZero.text.length < 2) {
            if (lastNumeric) {
                if (view != binding.btnZero) {
                    binding.numberScreen.lastZero.append((view as TextView).text)
                    lastNumeric = true
                }
            } else {
                binding.numberScreen.lastZero.append((view as TextView).text)
                lastNumeric = true
            }
        }
    }

    fun setNumberBeforeDot(view: View) {
        if (binding.numberScreen.firstZero.text.length < 9) {
            if (lastNumeric) {
                val currentNumber = (binding.numberScreen.firstZero.text.toString())
                val number = (view as TextView).text.toString()
                val sum = Integer.parseInt(currentNumber.replace(",", "") + number)
                val show = NumberFormat.getNumberInstance(Locale.US).format(sum)
                binding.numberScreen.firstZero.text = ""
                binding.numberScreen.firstZero.text = show
                lastNumeric = true
            } else {
                if (view != binding.btnZero) {
                    if (binding.numberScreen.firstZero.text.toString() == "") {
                        val number = Integer.parseInt((view as TextView).text.toString())
                        val sum = number
                        var show = NumberFormat.getNumberInstance(Locale.US).format(sum)
                        binding.numberScreen.firstZero.setText("")
                        binding.numberScreen.firstZero.setText(show)
                        lastNumeric = true
                    }
                }
            }
        }
    }

    fun clearAfterDot(){
        val currentNum = (binding.numberScreen.lastZero.text.toString()).dropLast(1)
        binding.numberScreen.lastZero.setText("")
        binding.numberScreen.lastZero.setText(currentNum)

        if (binding.numberScreen.lastZero.text.isEmpty()) {
            binding.numberScreen.dot.text = ""
            lastDot = false
        }
    }

    fun clearBeforDot(){
        if (lastNumeric && binding.numberScreen.firstZero.text.length > 0) {
            Log.d("num1", binding.numberScreen.firstZero.text.toString())
            val currentNum =
                (binding.numberScreen.firstZero.text.toString()).replace(",", "").dropLast(1)
            if (currentNum != "") {
                val sum = Integer.parseInt(currentNum)
                var show = NumberFormat.getNumberInstance(Locale.US).format(sum)
                Log.d("num2", show)
                binding.numberScreen.firstZero.text = ""
                binding.numberScreen.firstZero.text = show
            } else {
                binding.numberScreen.firstZero.text = ""
                binding.numberScreen.AED.text = ""
                lastNumeric = false

            }
        }
    }
}

