package com.elasticfurkan.elasticfurkan.util;

import com.elasticfurkan.elasticfurkan.esmodel.EsWebsite;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

public class StringUtil {

    private static String emp = "";

    public static long secondsToMilisecond(double seconds) {
        double hours = Math.floor(seconds / 3600);
        seconds -= hours * 3600;
        double minutes = Math.floor(seconds / 60);
        seconds = Math.floor(seconds % 60);
        double miliSeconds = Math.floor(seconds % 1000);
        seconds -= miliSeconds;

        return (long) (hours * 3600000 + minutes * 60000 + seconds * 1000 + miliSeconds);
    }

    public static String getRegex(String old) {
        return old.replace("\u0092", emp).replace("\u0096", emp).replace("\u0091", emp).replace("\u0095", emp)
                .replace("\u0094", emp);
    }

    public static String getCategoryName(String[] link) {
        return link.length == 5 ? link[3] : "genel";
    }

    public static EsWebsite getContent(String url) {

        Document doc;
        try {

            doc = Jsoup.connect(url).get();
            String title = doc.head().select("title").text();
            doc.select("footer").remove();
            doc.select("head").remove();
            doc.select("title").remove();
            doc.select(".header").remove();
            doc.select("header").remove();
            doc.select("script").remove();
            doc.select(".footer").remove();
            doc.select(".bread").remove();
            doc.select(".access").remove();
            String content = Jsoup.parse(Jsoup.parse(doc.body().toString()).text()).text();
            String content2 = Jsoup.parse(doc.body().toString()).text();
            if (content == null || content.trim().equals(""))
                return null;
            String[] link = url.split("/");
            return new EsWebsite(url.replace("https://", ""), content, getCategoryName(link), title);

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }
}
