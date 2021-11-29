package com.example.tyapkurs

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

	private var et1: EditText? = null
	private var et2: EditText? = null
	private var et3: EditText? = null
	private var et4: EditText? = null
	private var et5: EditText? = null
	private var et6: EditText? = null

	var text: TextView? = null
	var text1: TextView? = null
	var n: Int = 0
	private var isAlph = false

	var arrAlph: Array<String> = arrayOf()

	var globalLoopString: String = ""

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_main)

		arrAlph = Array(26) { "" }

		et1 = findViewById(R.id.alph)
		et2 = findViewById(R.id.krat)
		et3 = findViewById(R.id.start)
		et4 = findViewById(R.id.end)
		et5 = findViewById(R.id.first)
		et6 = findViewById(R.id.second)

		val build: Button = findViewById(R.id.build)
		val run: Button = findViewById(R.id.run)

		text = findViewById(R.id.text)
		text1 = findViewById(R.id.text1)

		build.setOnClickListener {
			val krat: String = et2?.text.toString()
			if (!et1?.text.isNullOrEmpty() && !et2?.text.isNullOrEmpty()) {
				arrAlph = Array(26) { "" }
				isAlph = true
				getAlph()
				buildAlp()
			}
		}
		run.setOnClickListener {
			if (isAlph) generate()
			globalLoopString = ""
		}
	}

	private fun loop(int: Int, str: String) {
		if(int == 0) globalLoopString += et3?.text.toString() + str + et4?.text.toString() + '\n'
		else for(i in 0 until n){
			loop(int-1, str+arrAlph[i])
		}
	}

	private fun generate() {
		val f: String = et5?.text.toString()
		val s: String = et6?.text.toString()
		val krat: String = et2?.text.toString()
		val kr = krat.toInt()

		val first = f.toInt()
		val second = s.toInt()

		val betw = et3?.text!!.length+et4?.text!!.length
		if(betw%kr==0 && betw!=0) globalLoopString += et3?.text.toString() + et4?.text.toString() +'\n'
		for(i in first..second){
			if(i%kr==0 && i > betw && second<10){
				loop(i-betw, "")
			}
		}
		text1?.text = globalLoopString
	}

	private fun buildAlp() {
		val start = et3?.text.toString()
		val end = et4?.text.toString()
		val krat: String = et2?.text.toString()
		val kr = krat.toInt()
		var kol = kr - (start.length + end.length) % kr
		if (kol == kr) kol = 0

		var elem = "("
		for (i in 0 until n) {
			elem += arrAlph[i]
			if (i != n - 1) elem += " + "
		}
		elem += ")"

		var mid = "($elem"
		for (i in 2..kr) {
			mid += "*$elem"
		}
		mid += ")^"

		var res = "$start*"
		if (start.isEmpty()) res = ""
		for (i in 1..kol) {
			res += elem
		}
		if (end.isNotEmpty()) res += "$mid*$end"
		else res += mid
		val obs = start + end

		for(ch in obs){
			val let = ch
			if(!arrAlph.contains(let.toString())){
				res = "Использован неучтённый в алфавите символ"
				isAlph = false
			}
		}
		text?.text = res//kol.toString()
	}

	private fun getAlph() {
		val alp = et1?.text.toString()
		n = alp.length
		var i = 0
		var r = 0
		for (ch in alp) {
			val let = ch
			if (!arrAlph.contains(let.toString())) {
				arrAlph[i] = ch.toString()
				i++
			} else r++
		}
		n -= r
	}
}