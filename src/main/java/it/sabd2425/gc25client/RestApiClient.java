package it.sabd2425.gc25client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import it.sabd2425.gc25client.data.BenchConfig;
import it.sabd2425.gc25client.data.QueryResult;
import it.sabd2425.gc25client.data.TimestampResult;
import it.sabd2425.gc25client.errors.BenchmarkCreationException;
import it.sabd2425.gc25client.errors.DefaultApiException;
import it.sabd2425.gc25client.errors.HttpRequestException;
import it.sabd2425.gc25client.errors.InternalApiException;
import it.sabd2425.gc25client.mappers.JsonMapper;
import it.sabd2425.gc25client.mappers.MessagePackMapper;

import java.io.IOException;
import java.io.Serializable;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.Optional;
import java.util.logging.Logger;

public class RestApiClient implements Serializable {
    private static final Logger logger = Logger.getLogger(RestApiClient.class.getName());
    private static final String CONTENT_TYPE = "Content-Type";
    private static final String APPLICATION_JSON = "application/json";
    private static final String APPLICATION_MSGPACK = "application/msgpack";
    private static final HttpClient client = HttpClient
            .newBuilder()
            .build();
    private final String apiEndpoint;

    public RestApiClient(String endpoint) {
        this.apiEndpoint = endpoint;
    }

    public String create(BenchConfig config) throws DefaultApiException {
        try {
            var endpoint = createEndpoint();
            var request = getCreateRequest(endpoint, config);
            var response = client
                    .sendAsync(request, HttpResponse.BodyHandlers.ofString())
                    .join();
            if (response.statusCode() != 200) {
                warning(createEndpoint(), responseCode(response.statusCode()));
                throw new BenchmarkCreationException(response.statusCode(), config);
            }
            var mapper = new ObjectMapper();
            return mapper.readValue(response.body(), String.class);
        } catch (IOException e) {
            throw new InternalApiException("create", e);
        }
    }

    private HttpRequest getCreateRequest(String endpoint, BenchConfig config) {
        return HttpRequest.newBuilder()
                .header(CONTENT_TYPE, APPLICATION_JSON)
                .uri(URI.create(endpoint))
                .POST(HttpRequest.BodyPublishers.ofString(toJson(config)))
                .build();
    }

    private String createEndpoint() {
        return apiEndpoint + "/create";
    }

    private String toJson(BenchConfig config) {
        return "{"
                + "\"apitoken\":\"" + config.getApiToken() + "\","
                + "\"name\":\"" + config.getName() + "\","
                + "\"max_batches\":" + config.getMaxBatches() + ","
                + "\"test\":" + config.isTest()
                + "}";
    }

    public void start(String benchId) throws DefaultApiException {
        var response = client
                .sendAsync(getStartRequest(benchId), HttpResponse.BodyHandlers.ofString())
                .join();
        if (response.statusCode() != 200) {
            throw new HttpRequestException(startEndpoint(benchId), response.statusCode(), response.body());
        }
    }

    private HttpRequest getStartRequest(String benchId) {
        return HttpRequest.newBuilder()
                .uri(URI.create(startEndpoint(benchId)))
                .POST(HttpRequest.BodyPublishers.noBody())
                .build();
    }

    private String startEndpoint(String benchId) {
        return apiEndpoint + "/start/" + benchId;
    }

    public void end(String benchId) throws DefaultApiException {
        try {
            var endpoint = endEndpoint(benchId);
            var request = getEndRequest(endpoint);
            var response = client.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() != 200) {
                throw new HttpRequestException(endpoint, response.statusCode(), response.body());
            }
        } catch (IOException | InterruptedException e) {
            throw new InternalApiException("end", e);
        }
    }

    private HttpRequest getEndRequest(String endpoint) {
        return HttpRequest.newBuilder()
                .timeout(Duration.ofSeconds(15))
                .header(CONTENT_TYPE, APPLICATION_JSON)
                .uri(URI.create(endpoint))
                .POST(HttpRequest.BodyPublishers.noBody())
                .build();
    }

    private String endEndpoint(String benchId) {
        return apiEndpoint + "/end/" + benchId;
    }

    public Optional<byte[]> nextBatch(String benchId) throws DefaultApiException {
        var endpoint = nextBatchEndpoint(benchId);
        var request = HttpRequest.newBuilder()
                .uri(URI.create(endpoint))
                .header(CONTENT_TYPE, APPLICATION_MSGPACK)
                .GET()
                .build();
        var response = client
                .sendAsync(request, HttpResponse.BodyHandlers.ofByteArray())
                .join();
        if (response.statusCode() == 404) {
            return Optional.empty();
        } else if (response.statusCode() == 200) {
            return Optional.of(response.body());
        } else {
            throw new HttpRequestException(endpoint, response.statusCode(), new String(response.body()));
        }
    }

    private String nextBatchEndpoint(String benchId) {
        return apiEndpoint + "/next_batch/" + benchId;
    }

    private static void warning(String endpoint, String message) {
        logger.warning(() -> String.format("%s: %s", endpoint, message));
    }

    private static String responseCode(int responseCode) {
        return String.format("response code %d", responseCode);
    }

    public TimestampResult postResult(String benchId, QueryResult result) throws DefaultApiException {
        try {
            var endpoint = postResultEndpoint(benchId, result);
            var request = getPostResultRequest(endpoint, result);
            var response = client.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() != 200) {
                throw new HttpRequestException(endpoint, response.statusCode(), response.body());
            }
            return JsonMapper.fromString(response.body(), TimestampResult.class);
        } catch (IOException | InterruptedException e) {
            throw new InternalApiException("post", e);
        }
    }

    private HttpRequest getPostResultRequest(String endpoint, QueryResult result) throws IOException {
        return HttpRequest.newBuilder()
                .uri(URI.create(endpoint))
                .header(CONTENT_TYPE, APPLICATION_MSGPACK)
                .POST(HttpRequest.BodyPublishers.ofByteArray(MessagePackMapper.toBytes(result)))
                .build();
    }

    private String postResultEndpoint(String benchId, QueryResult result) {
        return apiEndpoint
                + "/result/" + result.getQuery()
                + "/" + benchId
                + "/" + result.getBatchId();
    }
}