package com.clarxlabs.ellion.auth.presentation

interface AppModel {
    interface State<out M : AppModel>
    interface Event<out M : AppModel>
}
