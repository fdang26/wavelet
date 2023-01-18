import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;

class Handler implements URLHandler {
    // The one bit of state on the server: a number that will be manipulated by
    // various requests.
    ArrayList<String> wordBank = new ArrayList<>();

    public String handleRequest(URI url) {
        // returns Number: # for home page
        if (url.getPath().equals("/")) {
            return "You are on the home page!";
        } else { // for any other path
            System.out.println("Path: " + url.getPath()); // returns the url path
            if (url.getPath().contains("/add")) {
                String[] parameters = url.getQuery().split("=");
                if (parameters[0].equals("s")) {
                    wordBank.add(parameters[1]);
                    return parameters[1] + " added!";
                }
            } else if (url.getPath().contains("/search")) {
                String[] parameters = url.getQuery().split("=");
                if (parameters[0].equals("s")) {
                    String searchQuery = parameters[1];
                    ArrayList<String> results = new ArrayList<>();
                    for (String word : wordBank) {
                        if (word.contains(searchQuery)) {
                            results.add(word);
                        }
                    }
                    return results.toString();
                }
            }
            return "404 Not Found!";
        }
    }
}

class SearchEngine {
    public static void main(String[] args) throws IOException {
        if (args.length == 0) {
            System.out.println("Missing port number! Try any number between 1024 to 49151");
            return;
        }

        int port = Integer.parseInt(args[0]);

        Server.start(port, new Handler());
    }
}
