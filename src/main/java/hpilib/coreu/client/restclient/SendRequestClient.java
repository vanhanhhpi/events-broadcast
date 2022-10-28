package hpilib.coreu.client.restclient;

import okhttp3.*;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class SendRequestClient {
  public SendRequestClient() {
  }


  public Response sendToGHN(String fullUrl, String strBody, String token) throws IOException, SocketTimeoutException {

    OkHttpClient client = new OkHttpClient.Builder().connectTimeout(30, TimeUnit.SECONDS)
            .build();

    MediaType mediaType = MediaType.parse("application/json");
    RequestBody body = RequestBody.create(mediaType, strBody);
    Request request = new Request.Builder().url(fullUrl).method("POST", body)
            .addHeader("Token", token).addHeader("Content-Type", "application/json")
            .build();
    Response response = client.newCall(request).execute();
    return response;

  }

  public Response POST(String fullUrl, String strBody, Map<String, String> header)
          throws IOException, SocketTimeoutException {

    OkHttpClient client = new OkHttpClient.Builder().connectTimeout(30, TimeUnit.SECONDS)
            .build();

    MediaType mediaType = MediaType.parse("application/json");
    RequestBody body = RequestBody.create(mediaType, strBody);
    var builder = new Request.Builder().url(fullUrl).method("POST", body);
    if (header != null) {
      for (var map : header.entrySet()) {
        builder.addHeader(map.getKey(), map.getValue());
      }
    }

    Request request = builder.build();
    Response response = client.newCall(request).execute();
    return response;
  }

  public Response POST(String Url, String str) throws IOException {
    OkHttpClient client = new OkHttpClient.Builder().connectTimeout(30, TimeUnit.SECONDS)
            .build();
    MediaType mediaType = MediaType.parse("application/json");
    RequestBody body = RequestBody.create(mediaType, str);
    Request request = new Request.Builder().url(Url).method("POST", body)
            .addHeader("accept", "*/*").addHeader("Content-Type", "application/json").build();

    Response response = client.newCall(request).execute();
    return response;
  }

  public Response GET(String fullUrl, Map<String, String> header) throws IOException, SocketTimeoutException {
    OkHttpClient client = new OkHttpClient.Builder().connectTimeout(30, TimeUnit.SECONDS)
            .build();
    var builder = new Request.Builder().url(fullUrl).method("POST", null);
    if (header != null) {
      for (var map : header.entrySet()) {
        builder.addHeader(map.getKey(), map.getValue());
      }
    }
    Request request = builder.build();
    Response response = client.newCall(request).execute();

    return response;
  }

  public void POSTAsync(String fullUrl, String strBody, Map<String, String> header, String keyData)
          throws IOException, SocketTimeoutException {
    mapCallBack.put(keyData, "HPI_running");
    OkHttpClient client = new OkHttpClient.Builder().connectTimeout(30, TimeUnit.SECONDS)
            .build();

    MediaType mediaType = MediaType.parse("application/json");
    RequestBody body = RequestBody.create(mediaType, strBody);
    var builder = new Request.Builder().url(fullUrl).method("POST", body);
    if (header != null) {
      for (var map : header.entrySet()) {
        builder.addHeader(map.getKey(), map.getValue());
      }
    }
    Request request = builder.build();
    Call call = client.newCall(request);
    call.enqueue(new Callback() {

      @Override
      public void onFailure(Call call, IOException e) {
        mapCallBack.put(keyData, "HPI_fail");
      }

      @Override
      public void onResponse(Call call, Response response) throws IOException {
        mapCallBack.put(keyData, response.body().string());
        response.body().close();

      }
    });
  }

  private static Map<String, String> mapCallBack = new HashMap<String, String>();

  public Boolean procesDone(String keyData, Boolean isStream) throws IOException {
    if (!isStream) {
      String data = mapCallBack.get(keyData);
      if (data == null || data.equals("HPI_running") || data.equals("HPI_fail")) {
        return false;
      }
      return true;
    }
    String data = mapCallBack.get(keyData);
    while (data == null || data.equals("HPI_running") || data.equals("HPI_fail")) {
      try {
        Thread.sleep(100);
      } catch (InterruptedException e) {
      }
      data = mapCallBack.get(keyData);

    }
    return true;
  }

  /**
   * hàm này chỉ call được 1 lần, sau khi call data, key đó sẽ bị remove ra khỏi
   * bộ nhớ
   *
   * @param key
   * @return
   */
  public String getDataCallBack(String key) {
    String tmp = mapCallBack.get(key);
    mapCallBack.remove(key);
    return tmp;
  }


  public void PushNotifyDora( String text) {
    OkHttpClient client = new OkHttpClient.Builder().connectTimeout(30, TimeUnit.SECONDS)
            .build();

    MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
    RequestBody body = RequestBody.create(mediaType, "chat_id=-696526517&text=" + text);

    var builder = new Request.Builder()
            .url("https://api.telegram.org/bot5338387396:AAEVmHgI4LvzaE-V5IBEvvgDi4k7fpI3Dfg/sendMessage")
            .method("POST", body)
            .addHeader("Content-Type", "application/x-www-form-urlencoded");

    Request request = builder.build();
    Call call = client.newCall(request);
    call.enqueue(new Callback() {
      @Override
      public void onFailure(Call call, IOException e) {
        e.printStackTrace();
      }
      @Override
      public void onResponse(Call call, Response response) throws IOException {
        response.body().close();
      }
    });
  }
}
