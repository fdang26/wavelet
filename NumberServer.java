import java.io.IOException;
import java.net.URI;

class Handler implements URLHandler {
    // The one bit of state on the server: a number that will be manipulated by
    // various requests.
    int num = 0;

    public String handleRequest(URI url) {
        // returns Number: # for home page
        if (url.getPath().equals("/")) {
            return String.format("Foster's Number: %d", num);
        } else if (url.getPath().equals("/increment")) {
            // if the path is /increment, add one to num and displayed the increment message
            num += 1;
            return String.format("Number incremented!");
        } else { // for any other path
            System.out.println("Path: " + url.getPath()); // returns the url path
            if (url.getPath().contains("/add")) {
                String[] parameters = url.getQuery().split("=");
                if (parameters[0].equals("count")) {
                    num += Integer.parseInt(parameters[1]);
                    return String.format("Number increased by %s! It's now %d", parameters[1], num);
                }
            }
            return "404 Not Found!";
        }
    }
}

class NumberServer {
    public static void main(String[] args) throws IOException {
        if (args.length == 0) {
            System.out.println("Missing port number! Try any number between 1024 to 49151");
            return;
        }

        int port = Integer.parseInt(args[0]);

        Server.start(port, new Handler());
    }
}
