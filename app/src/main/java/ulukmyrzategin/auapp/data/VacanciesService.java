package ulukmyrzategin.auapp.data;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import ulukmyrzategin.auapp.data.model.VacanciesModel;
import ulukmyrzategin.auapp.utils.Constants;

/**
 * Created by $TheSusanin on 21.08.2018.
 */
public interface VacanciesService {
    @FormUrlEncoded
    @POST(Constants.MOBILE_API)
    Call<List<VacanciesModel>> postVacancies(@Field("login") String login,
                                            @Field("f") String f,
                                            @Field("limit") String limit,
                                            @Field("page") String page);

    @FormUrlEncoded
    @POST(Constants.MOBILE_API)
    Call<ArrayList<VacanciesModel>> postSearchingVacancies(@Field("login") String login,
                                                           @Field("f") String f,
                                                           @Field("limit") String limit,
                                                           @Field("page") String page,
                                                           @Field("salary") String salary,
                                                           @Field("term") String term);
}
