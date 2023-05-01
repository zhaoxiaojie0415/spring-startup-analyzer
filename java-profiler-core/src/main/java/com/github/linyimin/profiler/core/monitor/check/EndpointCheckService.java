package com.github.linyimin.profiler.core.monitor.check;

import ch.qos.logback.classic.Logger;
import com.github.linyimin.profiler.common.logger.LogFactory;
import com.github.linyimin.profiler.common.settings.ProfilerSettings;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.kohsuke.MetaInfServices;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static java.net.HttpURLConnection.HTTP_OK;

/**
 * @author linyimin
 * @date 2023/04/20 17:54
 * @description 通过endpoint检查app状态
 **/
@MetaInfServices
public class EndpointCheckService implements AppStatusCheckService {

    private final Logger logger = LogFactory.getStartupLogger();

    private List<String> healthEndpoints;

    private OkHttpClient client;

    @Override
    public void init() {
        client = new OkHttpClient().newBuilder().callTimeout(3, TimeUnit.SECONDS).build();
        String endpoints = ProfilerSettings.getProperty("java-profiler.app.status.check.endpoints", "http://127.0.0.1:7002/health");
        healthEndpoints = Arrays.asList(endpoints.split(","));
        logger.info("endpoints: {}", healthEndpoints);
    }

    @Override
    public AppStatus check() {

        for (String endpoint : healthEndpoints) {
            Request request = new Request.Builder().url(endpoint).build();
            try (Response response = client.newCall(request).execute()) {
                return response.code() == HTTP_OK ? AppStatus.running : AppStatus.initializing;
            } catch (IOException ignored) {
            }
        }
        return AppStatus.initializing;
    }
}
