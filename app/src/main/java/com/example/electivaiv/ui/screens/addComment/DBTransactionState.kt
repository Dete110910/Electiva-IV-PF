package com.example.electivaiv.ui.screens.addComment

sealed class DBTransactionState {
    object Idle: DBTransactionState()
    object Loading: DBTransactionState()
    object Success: DBTransactionState()
    object Error: DBTransactionState()
}