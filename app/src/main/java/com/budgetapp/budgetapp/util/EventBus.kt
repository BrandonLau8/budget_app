package com.budgetapp.budgetapp.util

import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow

//a singleton object that acts as a central hub for sending and receiving events within your application using Kotlin's coroutines and flows.
object EventBus {
    private val _events = Channel<Any>() //a channel is a coroutine based construct that allows for communication between coroutines
    val events = _events.receiveAsFlow() //converts channel into a flow

    //Flow is like a stream of data that you can collect over time. Like a conveyor belt that sends items one by one you can pick up as they come.

    suspend fun sendEvent(event: Any) {
        _events.send(event)
    }
}

//Sealed interfaces are useful when you want to model a fixed set of related types, like different states, actions, or events in your application.
sealed interface Event {
    data class Toast(val message:String):Event
}