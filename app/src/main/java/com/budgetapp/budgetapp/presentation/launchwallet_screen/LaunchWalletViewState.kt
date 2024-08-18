package com.budgetapp.budgetapp.presentation.launchwallet_screen

data class LaunchWalletViewState(
    val isLoading:Boolean = false,
    val buttonText:String = "Launch Wallet",
    val isButtonEnabled: Boolean = true,
    val error: String? = null,
    val linkToken: String? = null
)
