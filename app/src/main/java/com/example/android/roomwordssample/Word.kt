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

import androidx.room.*

/**
 * A basic class representing an entity that is a row in a one-column database table.
 *
 * @ Entity - You must annotate the class as an entity and supply a table name if not class name.
 * @ PrimaryKey - You must identify the primary key.
 * @ ColumnInfo - You must supply the column name if it is different from the variable name.
 *
 * See the documentation for the full rich set of annotations.
 * https://developer.android.com/topic/libraries/architecture/room.html
 */
typealias ListWordTag = List<WordTags?>?

@Entity(tableName = "word_table")
data class Word(
        @PrimaryKey @ColumnInfo(name = "word") val word: String,
        val firstWord: String = word.split(" ")[0].toString(),
        val name: String = "name",
        val age: Int = 0
)

@Entity(tableName = "word_details", foreignKeys = arrayOf(ForeignKey(entity = Word::class,
        parentColumns = arrayOf("word"),
        childColumns = arrayOf("wordOwnerId"),
        onDelete = ForeignKey.CASCADE)))
data class WordDetails(
        val wordOwnerId: String,
        val tag: String? = "*",
        val info: String? = "*",
        val charFirst: String? = "*",
        val position: Int = 0
) {
    @PrimaryKey(autoGenerate = true)  var libraryId: Long = 0
}


data class WordTags(
        var wordTag: String? = "tag"

)

data class UserAndLibrary(
        @Embedded val user: Word,
        @Relation(
                parentColumn = "userId",
                entityColumn = "userOwnerId"
        )
        val library: WordTags
)

/*class WordTags {
 public constructor( wordTags: String){
  this.wordTag=wordTag
 }
var wordTag: String? = "tag"

}*/