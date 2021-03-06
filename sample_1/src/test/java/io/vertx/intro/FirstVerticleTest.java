package io.vertx.intro;


import io.vertx.core.Vertx;
import io.vertx.ext.unit.Async;
import io.vertx.ext.unit.TestContext;
import io.vertx.ext.unit.junit.VertxUnitRunner;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

/*
the testContext let us control the asynchronously aspect of our verticles
 */
@RunWith(VertxUnitRunner.class)
public class FirstVerticleTest{

    private Vertx vertx;

    @Before
    public void setUp(TestContext context){
        vertx = Vertx.vertx();
        vertx.deployVerticle(FirstVerticle.class.getName(),
                // waits patiently until future.complete is called.
                context.asyncAssertSuccess());
    }

    @After
    public void tearDown(TestContext context){
        vertx.close(context.asyncAssertSuccess());
    }

    @Test
    public void testApplication(TestContext context){
        final Async async = context.async();
        vertx.createHttpClient().getNow(8080, "localhost", "/",
                response -> response.handler(body -> {
                    context.assertTrue(body.toString().contains("Hello"));
                    async.complete();
                }));
    }

}
