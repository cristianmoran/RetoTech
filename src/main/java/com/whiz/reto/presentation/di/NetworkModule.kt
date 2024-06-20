package com.whiz.reto.presentation.di

import android.content.Context
import android.content.pm.PackageManager
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.google.gson.GsonBuilder
import com.whiz.reto.BuildConfig
import com.whiz.reto.BuildConfig.URL_POKEMON
import com.whiz.reto.data.local.preferences.PreferenceManager
import com.whiz.reto.core.network.MyCallAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException
import java.net.URL
import java.security.KeyManagementException
import java.security.KeyStore
import java.security.KeyStoreException
import java.security.NoSuchAlgorithmException
import java.security.cert.Certificate
import java.security.cert.CertificateException
import java.security.cert.CertificateFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManager
import javax.net.ssl.TrustManagerFactory
import javax.net.ssl.X509TrustManager

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    companion object {

        const val CONNECT_TIMEOUT: Long = 60 * 1000
        const val READ_TIMEOUT: Long = 60 * 1000
        const val WRITE_TIMEOUT: Long = 60 * 1000
    }

    @Provides
    @Singleton
    internal fun provideGson(): GsonConverterFactory {
        return GsonConverterFactory.create(GsonBuilder().create())
    }

    @Provides
    @Singleton
    internal fun providesAppVersion(context: Context): String {
        var appVersion = "Desconocido (>= 1.1.8)"
        try {
            val packageInfo =
                context.packageManager.getPackageInfo(context.getPackageName(), 0)
            appVersion = packageInfo.versionName
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        }
        println("NetworkModule providesAppVersion appVersion : $appVersion")
        return appVersion
    }

    @Provides
    @Singleton
    internal fun providesToken(context: Context): PreferenceManager {
        return PreferenceManager(context)
    }

    @Provides
    @Singleton
    internal fun providesInterceptor(
        appVersion: String,
    ): Interceptor {
        return Interceptor { chain ->
            val original = chain.request()
            val requestBuilder = original.newBuilder()
                .header("X-Version-App", appVersion)
                .method(original.method, original.body)
            val request = requestBuilder.build()
            chain.proceed(request)
        }
    }

    @Provides
    @Singleton
    internal fun provideOkHttpClient(interceptor: Interceptor, context: Context): OkHttpClient {
        val okHttpClient = OkHttpClient.Builder()
        okHttpClient.connectTimeout(CONNECT_TIMEOUT, TimeUnit.MILLISECONDS)
        okHttpClient.readTimeout(READ_TIMEOUT, TimeUnit.MILLISECONDS)
        okHttpClient.writeTimeout(WRITE_TIMEOUT, TimeUnit.MILLISECONDS)
        val httpLoggingInterceptor = HttpLoggingInterceptor()

        okHttpClient.addInterceptor(interceptor)
            .addInterceptor(httpLoggingInterceptor.apply {
                httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
            })
        if (BuildConfig.DEBUG)
            okHttpClient.addInterceptor(ChuckerInterceptor(context))
        return okHttpClient.build()
    }

    @Provides
    @Singleton
    internal fun provideRetrofit(
        gsonConverterFactory: GsonConverterFactory, okHttpClient: OkHttpClient, context: Context
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(URL(URL_POKEMON) )
            .client(okHttpClient)
            .addConverterFactory(gsonConverterFactory) // Serialize Objects
            .addCallAdapterFactory(MyCallAdapterFactory(context)) //Set call to return {@link Observable}
            .build()
    }

    @Throws(
        CertificateException::class,
        IOException::class,
        KeyStoreException::class,
        NoSuchAlgorithmException::class,
        KeyManagementException::class
    )
    fun getSSLConfig(context: Context): SSLContext? {
        val cf: CertificateFactory? = CertificateFactory.getInstance("X.509")
        var ca: Certificate? = null
        try {
            val certificate: Int = 0
            context.resources.openRawResource(certificate)
                .use { cert -> ca = cf?.generateCertificate(cert) }
        } catch (e: Exception) {
            println(e.message)
        }
        val keyStoreType = KeyStore.getDefaultType()
        val keyStore = KeyStore.getInstance(keyStoreType)
        keyStore.load(null, null)
        keyStore.setCertificateEntry("ca", ca)
        val tmfAlgorithm = TrustManagerFactory.getDefaultAlgorithm()
        val tmf =
            TrustManagerFactory.getInstance(tmfAlgorithm)
        tmf.init(keyStore)
        val sslContext = SSLContext.getInstance("TLS")
        sslContext.init(null, tmf.trustManagers, null)
        return sslContext
    }

    fun getTrustManager(): X509TrustManager? {
        val trustManagerFactory: TrustManagerFactory?
        return try {
            trustManagerFactory =
                TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm())
            trustManagerFactory.init(null as KeyStore?)
            val trustManagers: Array<TrustManager> = trustManagerFactory.trustManagers
            if (trustManagers.size != 1 || trustManagers[0] !is X509TrustManager) {
                null
            } else trustManagers[0] as X509TrustManager
        } catch (e: NoSuchAlgorithmException) {
            println("NoSuchAlgorithmException ${e.message}")
            null
        } catch (e: KeyStoreException) {
            println("KeyStoreException ${e.message}")
            null
        }
    }
}