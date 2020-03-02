package com.example.entrytest.Model

import java.io.Serializable

class User(id : Int, email : String, first_name : String, last_name : String, url : String) : Serializable{
    var id: Int = id
        get() = field
        set(value) {
            field = value
        }
    var email: String = email
        get() = field
        set(value) {
            field = value
        }
    var first_name: String =first_name
        get() = field
        set(value) {
            field = value
        }
    var last_name: String = last_name
        get() = field
        set(value) {
            field = value
        }
    var url: String = url
        get() = field
        set(value){
            field = value
    }
}