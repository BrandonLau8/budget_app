//package com.budgetapp.budgetapp.presentation.plaidlink_screen
//
//import android.app.Activity
//import android.util.Log
//import androidx.activity.result.ActivityResultCaller
//import androidx.lifecycle.ViewModel
//import androidx.lifecycle.viewModelScope
//import arrow.core.computations.result
//import com.budgetapp.budgetapp.domain.respository.LinkTokenRepository
//import com.budgetapp.budgetapp.presentation.util.sendEvent
//import com.budgetapp.budgetapp.util.Event
//import com.plaid.link.Plaid
//import com.plaid.link.linkTokenConfiguration
//import com.plaid.link.result.LinkExit
//import com.plaid.link.result.LinkSuccess
//import dagger.hilt.android.lifecycle.HiltViewModel
//import kotlinx.coroutines.flow.MutableStateFlow
//import kotlinx.coroutines.flow.StateFlow
//import kotlinx.coroutines.flow.asStateFlow
//import kotlinx.coroutines.flow.update
//import kotlinx.coroutines.launch
//import javax.inject.Inject
//
//@HiltViewModel
//class PlaidLinkViewModel @Inject constructor(
//    private val linkTokenRepository: LinkTokenRepository
//) : ViewModel() {
//
//    private val _linkToken = MutableStateFlow<String?>(null)
//    val linkToken: StateFlow<String?> = _linkToken.asStateFlow()
//
//    init {
//        getLinkToken()
//    }
//
//    private fun getLinkToken() {
//        viewModelScope.launch {
//            val result = linkTokenRepository.getLinkToken()
//
//            result.fold(
//                { error ->
//                    sendEvent(Event.Toast(error.error.message))
//                    Log.d("test", "getLinkToken: ${error.error.message}")
//                },
//                { response ->
//                    _linkToken.value = response.link_token
//                }
//            )
//        }
//    }
//
//    fun launchPlaidLink(
//        context: Activity,
//        token:String,
//        onLinkSuccess: (LinkSuccess) -> Unit,
//        onLinkExit: (LinkExit) -> Unit
//    ) {
//        val linkConfiguration = linkTokenConfiguration {
//            this.token = token
//        }
//
//        val linkAccountToPlaid =
//            context.registerForActivity {
//                when (it) {
//                    is LinkSuccess -> onLinkSuccess(result)
//                    is LinkExit -> /* handle LinkExit */
//                }
//            }
//    }
//}