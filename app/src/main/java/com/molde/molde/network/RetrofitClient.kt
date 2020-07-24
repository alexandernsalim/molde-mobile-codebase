package com.molde.molde.network

import com.molde.molde.network.interceptor.LogInterceptor
import com.molde.molde.network.service.*
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitClient {

    private const val MOLDE_PATH = "http://10.0.2.2:9000/molde/api/v1/"
    private const val SHOP_USER_SERVICE = MOLDE_PATH + "shopuser/"
    private const val PRODUCT_SERVICE = MOLDE_PATH + "product/"
    private const val CART_SERVICE = MOLDE_PATH + "cart/"
    private const val ADDRESS_SERVICE = MOLDE_PATH + "address/"
    private const val BANK_ACCOUNT_SERVICE = MOLDE_PATH + "bankaccount/"
    private const val ORDER_SERVICE = MOLDE_PATH + "order/"
    private const val DISCUSSION_SERVICE = MOLDE_PATH + "discussions/"
    private const val REVIEW_SERVICE = MOLDE_PATH + "reviews/"
    private const val RAJAONGKIR_PATH = "https://api.rajaongkir.com/basic/"

    private val client = OkHttpClient.Builder()
        .addInterceptor(LogInterceptor())
        .connectTimeout(30, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .build()

    fun authenticationClient(): AuthService {
        val retrofit = clientBuilder(MOLDE_PATH, client)

        return retrofit.create(AuthService::class.java)
    }

    fun shopUserClient(): ShopUserService {
        val retrofit = clientBuilder(SHOP_USER_SERVICE, client)

        return retrofit.create(ShopUserService::class.java)
    }

    fun productClient(): ProductService {
        val retrofit = clientBuilder(PRODUCT_SERVICE, client)

        return retrofit.create(ProductService::class.java)
    }

    fun discussionClient(): DiscussionService {
        val retrofit = clientBuilder(DISCUSSION_SERVICE, client)

        return retrofit.create(DiscussionService::class.java)
    }

    fun reviewClient(): ReviewService {
        val retrofit = clientBuilder(REVIEW_SERVICE, client)

        return retrofit.create(ReviewService::class.java)
    }

    fun cartClient(): CartService {
        val retrofit = clientBuilder(CART_SERVICE, client)

        return retrofit.create(CartService::class.java)
    }

    fun addressClient(): AddressService {
        val retrofit = clientBuilder(ADDRESS_SERVICE, client)

        return retrofit.create(AddressService::class.java)
    }

    fun bankAccountClient(): BankAccountService {
        val retrofit = clientBuilder(BANK_ACCOUNT_SERVICE, client)

        return retrofit.create(BankAccountService::class.java)
    }

    fun orderClient(): OrderService {
        val retrofit = clientBuilder(ORDER_SERVICE, client)

        return retrofit.create(OrderService::class.java)
    }

    fun rajaOngkirClient(): RajaOngkirService {
        val retrofit = clientBuilder(RAJAONGKIR_PATH, client)

        return retrofit.create(RajaOngkirService::class.java)
    }

    private fun clientBuilder(url: String, client: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(url)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}