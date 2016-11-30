package jewar.abanoubm.jewar;


import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import java.io.StringReader;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class HTTPClient {
    private static OkHttpClient client = new OkHttpClient();


    public static String request(String requestURL) {

        Request request = new Request.Builder()
                .url(requestURL + "&" +
                                BuildConfig.G_R_A_T
                )
                .build();
        Response response;
        try {
            response = client.newCall(request).execute();
            return response.body().string();
        } catch (Exception e) {
            //  e.printStackTrace();
            return null;
        }

    }

    public static ArrayList<Book> searchBooks(String nameQuery) {
        ArrayList<Book> books = new ArrayList<>();


        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(new InputSource(
                    new StringReader(request("https://www.goodreads.com/search/index.xml?q="
                            + nameQuery + "&search[field]=title"))));
            doc.getDocumentElement().normalize();

            NodeList nList = doc.getElementsByTagName("work");
            for (int temp = 0; temp < nList.getLength(); temp++) {
                Node nNode = nList.item(temp);
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;

                    Element node = (Element) eElement.getElementsByTagName("best_book").item(0);

                    //	System.out.println("id: " + node.getElementsByTagName("id").item(0).getTextContent());
//                    System.out.println("title: " + node.getElementsByTagName("title").item(0).getTextContent());
//                    System.out.println("author: " + ((Element) node.getElementsByTagName("author").item(0)).getElementsByTagName("name").item(0).getTextContent());
//                    System.out.println("image_url:" + node.getElementsByTagName("image_url").item(0).getTextContent());
//                    System.out.println("rate: " + eElement.getElementsByTagName("average_rating").item(0).getTextContent());
//                    System.out.println("id: " + eElement.getElementsByTagName("id").item(0).getTextContent());

                    books.add(new Book(eElement.getElementsByTagName("id").item(0).getTextContent(),
                            node.getElementsByTagName("title").item(0).getTextContent(),
                            eElement.getElementsByTagName("average_rating").item(0).getTextContent(),
                            ((Element) node.getElementsByTagName("author").item(0)).getElementsByTagName("name").item(0).getTextContent(),
                            node.getElementsByTagName("image_url").item(0).getTextContent()));
//					System.out.println(
//							"Last Name : " + eElement.getElementsByTagName("image_url").item(0).getTextContent());
//					System.out.println(
//							"Nick Name : " + eElement.getElementsByTagName("nickname").item(0).getTextContent());
//					System.out.println("Marks : " + eElement.getElementsByTagName("marks").item(0).getTextContent());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();

        }
        return books;

    }
}