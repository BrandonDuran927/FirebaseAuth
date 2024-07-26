package com.example.firebaseauthentication.data

import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject


class AuthenticationRepositoryImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    ) : AuthenticationRepository {

    override fun signInWithGoogle(credential: AuthCredential): Flow<AuthResult> {
        return flow {
            try {
                val result = firebaseAuth.signInWithCredential(credential).await()
                emit(result)
            } catch (e: Exception) {
                // Emit the error, or handle it as needed
                e.printStackTrace()
                throw e
            }
        }
    }

    override fun signOutUser() {
        firebaseAuth.signOut()
    }

    override fun deleteUser() {
        firebaseAuth.currentUser?.delete()
    }

    override fun signInWithEmailPassword(email: String, password: String): Flow<AuthResult> {

        return flow {
            try {
                val result = firebaseAuth.signInWithEmailAndPassword(email, password).await()
                emit(result)
            } catch (e: Exception) {
                e.printStackTrace()
                throw e
            }
        }

    }

    override fun signUpWithEmailPassword(email: String, password: String): Flow<AuthResult> {
        return flow {
            try {
                val registerUser = firebaseAuth.createUserWithEmailAndPassword(email, password).await()
                emit(registerUser)
            } catch (e: Exception) {
                e.printStackTrace()
                throw e
            }
        }
    }

    override fun retrieveUser(): Flow<FirebaseUser?> {
        return flow {
            try {
                val result = firebaseAuth.currentUser
                emit(result)
            } catch (e: Exception) {
                e.printStackTrace()
                throw e
            }
        }
    }


    override fun resetPasswordEmail(email: String): Flow<Result<Unit>> {
        return flow {
            try {
                firebaseAuth.sendPasswordResetEmail(email).await()
                emit(Result.success(Unit))
            } catch (e: Exception) {
                e.printStackTrace()
                throw e
            }
        }
    }

}