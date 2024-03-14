package com.example.sms.ui

class OutboxMessage (var contactNo : String, var body : String){

    var id = idGeneration()

    companion object {

        var id : Long = 0

        fun idGeneration(): Long {
            id += 1
            return id
        }

        fun addSMS(sms : OutboxMessage){
            outboxSmsArr.add(sms)
        }
        var outboxSmsArr: ArrayList<OutboxMessage> = ArrayList()
    }
}
