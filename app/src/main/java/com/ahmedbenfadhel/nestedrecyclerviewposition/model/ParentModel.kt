package com.ahmedbenfadhel.nestedrvsavescrollposition.model

data class ParentModel (
    val title : String = "",
    val children : List<ChildModel>
)