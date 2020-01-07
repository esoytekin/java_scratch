package com.monzo;

import org.jsoup.Jsoup;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.*;
import java.util.stream.Collectors;

/**
 * Hello world!
 *
 */
public class App 
{
    private static final int LIMIT = 100;

    public static void main( String[] args ) throws InterruptedException {

        String url = "https://monzo.com";

        long start = System.currentTimeMillis ();

        Set <String> result = new App ().run (url);

        System.out.printf ("fetched (%d) results in %d seconds.\n", result.size (), System.currentTimeMillis () - start);

    }

    private List<String> crawl(String url) {

        try {
            return Jsoup.connect (url).get()
                    .select ("a[href]")
                    .stream ().map (e -> e.attr ("abs:href"))
                    .collect (Collectors.toList ());
        } catch (IOException e) {
            e.printStackTrace ();
        }

        return Collections.emptyList ();

    }


    private boolean isBaseUrl(String baseUrl, String l) {
        return l.matches ("^http[s]?://(?:w{3}\\.)?"+baseUrl + ".*$") && !l.matches (".*\\.(pdf)|(xml)");
    }

    private Set <String> run(final String url) throws InterruptedException {

        final String baseUrl = url.replaceAll ("http[s]?://(?:w{3}\\.)?(.*)", "$1");

        final BlockingQueue<List<String>> workList = new LinkedBlockingQueue <> ();
        final Map<String, Boolean> visited = new HashMap <> ();
        final ExecutorService executorService = Executors.newFixedThreadPool (20);

        int n = 0;

        workList.add(Collections.singletonList (url));

        n++;


        for (; visited.size () < LIMIT && n>0; n-- ) {

            final List <String> urlList = workList.take ();

            for (String u : urlList) {

                if (isBaseUrl (baseUrl, u) && visited.get (u) == null ) {

                    visited.put (u, true);
                    n++;

                    executorService.execute (() -> {
                        final List <String> crawledList = crawl (u);
                        workList.add(crawledList);
                    });

                }
            }
        }

        executorService.shutdown();

        executorService.awaitTermination (1, TimeUnit.MILLISECONDS);

        return visited.keySet ();

    }
}
