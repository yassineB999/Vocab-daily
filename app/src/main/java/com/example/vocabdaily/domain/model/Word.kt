package com.example.vocabdaily.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.vocabdaily.ui.theme.LightGreen
import com.example.vocabdaily.ui.theme.RedOrange
import com.example.vocabdaily.ui.theme.Violet
import com.example.vocabdaily.ui.theme.BabyBlue
import com.example.vocabdaily.ui.theme.RedPink

@Entity
data class Word(
    @PrimaryKey(autoGenerate = true) val id : Int? = null,
    val word :String,
    val description : String,
    val timestamp : Long,
    val color : Int,
    ){
    companion object{
        val wordColors = listOf(RedOrange, LightGreen, Violet, BabyBlue, RedPink)
    }
}
