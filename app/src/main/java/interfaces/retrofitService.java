package interfaces;

import java.util.List;

import objects.Movie;
import objects.Search;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface retrofitService {
    @GET(".")
    Call<Search> groupList(@Query("s") String name, @Query("apikey") String key);
    @GET(".")
    Call<Movie> movieDetail(@Query("i") String imdbId, @Query("plot") String plot, @Query("apikey") String key);
}
