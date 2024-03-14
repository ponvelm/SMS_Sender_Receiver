package com.example.sms.ui

class InboxMessage (var contactNo : String, var body : String){

    var id = idGeneration()

    companion object {

        var id : Long = 0

        fun idGeneration(): Long {
            id += 1
            return id
        }

        fun addSMS(sms : InboxMessage){
            inboxSmsArr.add(sms)
        }
        var inboxSmsArr: ArrayList<InboxMessage> = ArrayList()
    }
}