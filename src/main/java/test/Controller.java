package test;

import io.micronaut.http.annotation.PathVariable;
import io.micronaut.http.annotation.Post;
import io.reactivex.Single;

@io.micronaut.http.annotation.Controller
public class Controller {

    @Post("/create/{id}")
    public Single<String> lowprio(@PathVariable String id) {
        return Single.just("OK");
    }
}
