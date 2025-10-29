package com.example.vocabdaily.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Word(
    @PrimaryKey val id : Int? = null,
    val title :String,
    val description : String,
    val timestamp : Long,
    val color : Int,
    ){
    companion object{
        //val wordColors = listOf(RedOrange, LightGreen, Violet, BabyBlue, RedPink)
    }
}
