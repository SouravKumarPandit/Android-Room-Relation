/*
 * Copyright (C) 2017 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.android.roomwordssample

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {

    private val newWordActivityRequestCode = 1
    private val wordViewModel: WordViewModel by viewModels {
        WordViewModelFactory((application as WordsApplication).repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerview)
        val adapter = WordListAdapter()
         adapter.wordViewModel=wordViewModel
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

//        val invoke = wordViewModel.wordDetailsList.invoke("55")
//        wordViewModel.wordDetailsList.invoke("gc").observe(owner = this){
//            Toast.makeText(this,it.toString(),Toast.LENGTH_LONG).show()
//
//        }

        // Add an observer on the LiveData returned by getAlphabetizedWords.
        // The onChanged() method fires when the observed data changes and the activity is
        // in the foreground.
        wordViewModel.allWords.observe(owner = this) { words ->
            // Update the cached copy of the words in the adapter.
            words.let { adapter.submitList(it) }
        }

        val fab = findViewById<FloatingActionButton>(R.id.fab)
        fab.setOnClickListener {
            val intent = Intent(this@MainActivity, NewWordActivity::class.java)
            startActivityForResult(intent, newWordActivityRequestCode)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, intentData: Intent?) {
        super.onActivityResult(requestCode, resultCode, intentData)

        if (requestCode == newWordActivityRequestCode && resultCode == Activity.RESULT_OK) {
            intentData?.getCharSequenceArrayListExtra(NewWordActivity.EXTRA_REPLY)?.let { reply ->
                val word =reply[0].toString()
                val tag =reply[1].toString()
                val info =reply[2].toString()
                val firstChar =reply[3].toString()
                val postion =reply[4].toString()
                val indexpostion=postion.toInt()

                val wordtagList = arrayListOf<WordTags>()
                for (indexTag in 0 until 5){
                    wordtagList.add(WordTags( "$word $indexTag"))
                }
                val wordDetails = WordDetails(tag=tag,info=info,charFirst= firstChar,position = indexpostion,wordOwnerId = word)

//                val wordData = Word(word,firstChar, wordDetails,wordtagList)
                val wordData = Word(word)
                wordViewModel.insert(wordData)
                wordViewModel.insertDetails(wordDetails)
            }
        } else {
            Toast.makeText(
                applicationContext,
                R.string.empty_not_saved,
                Toast.LENGTH_LONG
            ).show()
        }
    }
}
