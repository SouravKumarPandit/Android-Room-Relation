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
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity

/**
 * Activity for entering a word.
 */

class NewWordActivity : AppCompatActivity() {

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_word)
        val editWordView = findViewById<EditText>(R.id.edit_word)
        val editTagView = findViewById<EditText>(R.id.word_tag)
        val editInfoView = findViewById<EditText>(R.id.word_info)
        val editFirstCharView = findViewById<EditText>(R.id.word_char_first)
        val editPositionView = findViewById<EditText>(R.id.word_position)

        val button = findViewById<Button>(R.id.button_save)
        button.setOnClickListener {
            val replyIntent = Intent()
            if (TextUtils.isEmpty(editWordView.text)) {
                setResult(Activity.RESULT_CANCELED, replyIntent)
            } else {
                val word = editWordView.text.toString()
                val tag = editTagView.text.toString()
                val info = editInfoView.text.toString()
                val firstChar = editFirstCharView.text.toString()
                val positionString = editPositionView.text.toString()

                val position = if (positionString.isBlank()) "0";
                else
                    positionString.toString()

                replyIntent.putCharSequenceArrayListExtra(EXTRA_REPLY, arrayListOf(
                        word,
                        tag,
                        info,
                        firstChar,
                        position,
                ))
                setResult(Activity.RESULT_OK, replyIntent)
            }
            finish()
        }
    }

    companion object {
        const val EXTRA_REPLY = "com.example.android.wordlistsql.REPLY"
    }
}
