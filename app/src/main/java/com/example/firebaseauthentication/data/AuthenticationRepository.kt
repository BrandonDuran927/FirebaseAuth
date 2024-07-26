package com.example.firebaseauthentication.data

import androidx.credentials.Credential
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.flow.Flow

interface AuthenticationRepository {

    fun signInWithGoogle(credential: AuthCredential): Flow<AuthResult>
    fun signOutUser()
    fun deleteUser()

    fun signInWithEmailPassword(email: String, password: String): Flow<AuthResult>
    fun signUpWithEmailPassword(email: String, password: String): Flow<AuthResult>

    fun retrieveUser(): Flow<FirebaseUser?>
    fun resetPasswordEmail(email: String): Flow<Result<Unit>>

}