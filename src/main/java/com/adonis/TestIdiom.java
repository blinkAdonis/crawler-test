package com.adonis;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * @author adonis
 *
 * @createDate Aug 12, 2015
 */
public class TestIdiom {

    public static void main(String[] args) {
        IdiomDAO dao = new IdiomDAO();
        List<Integer> firstList = IntStream.range(16, 40).boxed().collect(Collectors.toList());
        List<Integer> secondList = IntStream.range(1, 1001).boxed().collect(Collectors.toList());

        for (Integer first : firstList) {
            System.out.println(first);
            int tmp = first * 1000;
            secondList.parallelStream().forEach(second -> {
                int id = tmp + second;
                String url = "http://chengyu.t086.com/cy" + first + "/" + id + ".html";
                System.out.println(url);
                HttpGet get = new HttpGet(url);
                try (CloseableHttpClient httpClient = HttpClientBuilder.create().build()) {
                    CloseableHttpResponse response = httpClient.execute(get);
                    int statusCode = response.getStatusLine().getStatusCode();
                    if (statusCode != 200) {
                        return;
                    }
                    String content = EntityUtils.toString(response.getEntity(),
                            Charset.forName("GBK"));
                    Document doc = Jsoup.parse(content);
                    Elements elements = doc.select("tr");
                    String word = "";
                    String pronunciation = "";
                    String explanation = "";
                    String source = "";
                    for (Element element : elements) {
                        Elements td = element.select("td");
                        if (td.isEmpty()) {
                            continue;
                        }

                        if (td.get(0).text().equals("词目")) {
                            System.out.println(td.get(1).text());
                            word = td.get(1).text();
                        }
                        if (td.get(0).text().equals("发音")) {
                            System.out.println(td.get(1).text());
                            pronunciation = td.get(1).text();
                        }
                        if (td.get(0).text().equals("释义")) {
                            System.out.println(td.get(1).text());
                            explanation = td.get(1).text();
                        }
                        if (td.get(0).text().equals("出处")) {
                            System.out.println(td.get(1).text());
                            source = td.get(1).text();
                        }
                    }
                    dao.insert(id, word, pronunciation, explanation, source);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        }

    }
}
