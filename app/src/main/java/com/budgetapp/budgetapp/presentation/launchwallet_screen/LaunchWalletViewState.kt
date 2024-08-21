package com.budgetapp.budgetapp.presentation.launchwallet_screen

data class LinkTokenState(
    val isLoading:Boolean = false,
    val buttonText:String = "Launch Wallet",
    val isButtonEnabled: Boolean = true,
    val error: String? = null,
    val linkToken: String? = null
)
