package ulukmyrzategin.auapp.data;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import ulukmyrzategin.auapp.utils.Constants;


/**
 * Created by $TheSusanin on 28.04.2018.
 */

public class NetworkBuilder {

    private static VacanciesService sService = null;

    public static VacanciesService initService() {
        if (sService == null) {
            sService = new Retrofit.Builder()
                    .baseUrl(Constants.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(getOkHttpClient())
                    .build()
                    .create(VacanciesService.class);
        }
        return sService;
    }

    private static OkHttpClient getOkHttpClient() {
        return new OkHttpClient.Builder()
                .readTimeout(20, TimeUnit.SECONDS)
                .connectTimeout(20, TimeUnit.SECONDS)
                .writeTimeout(20, TimeUnit.SECONDS)
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Request.Builder builder = chain.request()
                                .newBuilder()
                                .addHeader("Accept", "application/json;version=1");
                        return chain.proceed(builder.build());
                    }
                })
                .build();
    }
}
