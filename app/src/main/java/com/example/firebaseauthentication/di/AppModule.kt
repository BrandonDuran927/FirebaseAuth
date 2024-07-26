package com.example.firebaseauthentication.di

import android.app.Application
import android.content.Context
import androidx.compose.runtime.traceEventEnd
import com.example.firebaseauthentication.R
import com.example.firebaseauthentication.data.AuthenticationRepository
import com.example.firebaseauthentication.data.AuthenticationRepositoryImpl
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.firebase.auth.FirebaseAuth
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import com.google.android.gms.auth.api.identity.Identity

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun providesFirebaseAuth() = FirebaseAuth.getInstance()

    @Singleton
    @Provides
    fun provideRepository(firebaseAuth: FirebaseAuth): AuthenticationRepository =
        AuthenticationRepositoryImpl(firebaseAuth)

    @Singleton
    @Provides
    fun provideOneTapClient(@ApplicationContext context: Context) = Identity.getSignInClient(context)

    @Singleton
    @Provides
    fun provideSignInRequest(@ApplicationContext context: Context): BeginSignInRequest = BeginSignInRequest.Builder()
        .setGoogleIdTokenRequestOptions(
            BeginSignInRequest
                .GoogleIdTokenRequestOptions.builder()
                .setSupported(true)
                .setServerClientId(context.getString(R.string.web_client_id))
                .setFilterByAuthorizedAccounts(false)
                .build()
        )
        .setAutoSelectEnabled(true)
        .build()
}