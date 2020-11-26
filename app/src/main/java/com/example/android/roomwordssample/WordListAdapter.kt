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

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.android.roomwordssample.WordListAdapter.WordViewHolder

class WordListAdapter : ListAdapter<WordAndWordDetails, WordViewHolder>(WORDS_COMPARATOR) {

//    var wordViewModel: WordViewModel? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WordViewHolder {
        val view: View = LayoutInflater.from(parent.context)
                .inflate(R.layout.recyclerview_item, parent, false)
        return WordViewHolder(view)
    }

    override fun onBindViewHolder(holder: WordViewHolder, position: Int) {
        val current = getItem(position)

        holder.bind(current)
    }

  inner  class WordViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val wordItemView: TextView = itemView.findViewById(R.id.textView)
        private val wordListLayout: LinearLayout = itemView.findViewById(R.id.wordListLayout)
//        private val infoView: TextView = itemView.findViewById(R.id.tvInfo)
        private val tagView: TextView = itemView.findViewById(R.id.tvtag)
        private val positionView: TextView = itemView.findViewById(R.id.tvPosition)
        private val firstCharView: TextView = itemView.findViewById(R.id.tvFirstChar)
        /*private var wordDetails:LiveData<List<WordDetails?>?>?=null
                set(value) {
                    field=value
                    if (value!=null){
                        bindDetails(value.value)
                    }

                }*/
        fun bind(wordData: WordAndWordDetails) {
            wordItemView.hideEmptyText(wordData.word.word)
            bindDetails(wordData.wordDetailsList)
          /* val wordDetails = wordViewModel?.wordDetailsList?.invoke(wordData.word.word)
            wordDetails?.observeForever{
                if (it!=null){
                    bindDetails(it)
                }
            }*/

//            infoView.hideEmptyText(wordData.wordDetails?.info ?: "")
//            tagView.hideEmptyText(wordData.wordDetails?.tag ?: "")
//            positionView.hideEmptyText(wordData.wordDetails?.position.toString() ?: "0")
//            firstCharView.hideEmptyText(wordData.wordDetails?.charFirst ?: "None")
        }

        fun bindDetails(wordDetails: List<WordDetails?>?) {
            wordListLayout.removeAllViews()
            if (!wordDetails.isNullOrEmpty()) {
                wordDetails.forEachIndexed { i, wordData ->
                    if (wordData != null)
                        wordListLayout.addView(getTextData(wordListLayout.context, i, wordData))
                }
            }

        }

        private fun getTextData(context: Context, i: Int, wordData: WordDetails): View {
            val textView = TextView(context)
            textView.layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)
            textView.text = "$i. ${wordData.info} ---- ${wordData.tag} ---- ${wordData.position}"

            return textView
        }
/*
        companion object {

            fun create(parent: ViewGroup): WordViewHolder {
                val view: View = LayoutInflater.from(parent.context)
                        .inflate(R.layout.recyclerview_item, parent, false)
                return WordViewHolder(view)
            }
        }*/
    }

    companion object {
        private val WORDS_COMPARATOR = object : DiffUtil.ItemCallback<WordAndWordDetails>() {
            override fun areItemsTheSame(oldItem: WordAndWordDetails, newItem: WordAndWordDetails): Boolean {
                return oldItem === newItem
            }

            override fun areContentsTheSame(oldItem: WordAndWordDetails, newItem: WordAndWordDetails): Boolean {
                return oldItem.word == newItem.word
            }
        }

    }
}

fun TextView.hideEmptyText(text: CharSequence?) {
    if (text.isNullOrEmpty()) {
        this.visibility = View.GONE
        return
    }
    this.text = text
}