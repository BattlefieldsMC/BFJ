import com.google.gson.JsonObject;
import io.github.tastac.bfj.BattlefieldsApi;
import io.github.tastac.bfj.BattlefieldsApiBuilder;

import java.util.Arrays;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class ModelTest
{
    private static void makeRequests(BattlefieldsApi api) throws InterruptedException, ExecutionException
    {
        CompletableFuture<String> modelHashFuture = api.requestCosmeticModelHash("cat_ears2");
        CompletableFuture<JsonObject> modelFuture = api.requestCosmeticModel("cat_ears2");
        CompletableFuture<String> textureHashFuture = api.requestCosmeticTextureHash("cat_ears/white1");
        CompletableFuture<byte[]> textureFuture = api.requestCosmeticTexture("cat_ears/white1");

        CompletableFuture.allOf(modelHashFuture, modelFuture, textureHashFuture, textureFuture).join();

        System.out.println("Model Hash: " + modelHashFuture.get());
        System.out.println("Model: " + modelFuture.get());
        System.out.println("Texture Hash: " + textureHashFuture.get());
        System.out.println("Texture: " + Arrays.toString(textureFuture.get()));
    }

    public static void main(String[] args) throws Exception
    {
        BattlefieldsApi api = new BattlefieldsApiBuilder().create();

        long startTime = System.currentTimeMillis();
        makeRequests(api);

        System.out.println("Finished requests in " + (System.currentTimeMillis() - startTime) + "ms");
        System.out.println("Shutdown successfully? " + api.shutdown());
        System.out.println("Stopping!");
    }
}
