import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class Server {
    public static void main(String[] args) throws NoSuchAlgorithmException{
        final ServerSocket serverSocket ;
        final Socket clientSocket ;
        final BufferedReader in;
        final PrintWriter out;
        final Scanner sc =new Scanner(System.in);

        //CREATE THE POSTS
        Post post = new Post("bd4fd422f16f44ec6262e79f12c6269afab4ccdd48cd9ce8a75a572e2fddafe3",new Date().getTime(), "johnDoe@gmail.com", 2, "Hello\n" + "this is my first post\n");

         Post post2 = new Post("8558c13a39da5e5564d0fa33d87e0fa8f35ab4b92d2fff95f68e8706527248f6",new Date().getTime() + 34353, "sarahJames@gmail.com", 2, "Hello\n" + "this is my second post\n");

         Post post3 = new Post("cb526fdb63aa4c92cf198c56d6e8f604fededda545d59cd216db07d53843a1ca",new Date().getTime() + 12332, "BryanWilliams@gmail.com", 2, "Hello\n" + "this is my third post\n");

         Post post4 = new Post("1b26b0feb92e29b3c7ae5376c1e758fe1781d326f0df700855574d434de58f73".toString(),new Date().getTime() + 23131, "JadeMonroe@gmail.com", 2, "Hello\n" + "this is my fourth post\n");

         List<Post> posts = new ArrayList<>();
         posts.add(post);
         posts.add(post2);
         posts.add(post3);
         posts.add(post4);
         

        System.out.println("Waiting for the client request");

        try {
            serverSocket = new ServerSocket(20111);
            clientSocket = serverSocket.accept();
            out = new PrintWriter(clientSocket.getOutputStream());
            in = new BufferedReader (new InputStreamReader(clientSocket.getInputStream()));

            Thread sender= new Thread(new Runnable() {
                String msg = ""; //variable that will contains the data writter by the user
                @Override   // annotation to override the run method
                public void run() {
                    while(isValidCommand(msg)){
                        msg = sc.nextLine(); //reads data from user's keybord

                        if (msg.contains("GOODBYE!")){
                            System.out.println("you said goodbye");
                            out.println(msg);
                            out.close();
                            sc.close();
                            System.exit(0);
                            break;
                        }

                        out.println(msg);    // write data stored in msg in the clientSocket
                        out.flush();  // forces the sending of the data
                        
                        if(!isValidCommand(msg)){
                            System.out.println("UNKOWN COMMAND GOODBYE");
                            out.close();
                            sc.close();
                            break;
                            
                        }
                    }
                }
                private boolean isValidCommand(String msg2) {
                    if (msg2.equals("WHEN?")) {
                        return true;
                    } else if (msg2.contains("POSTS?")) {
                        return true;
                    }else if(msg.contains("HELLO?")){
                        return true;
                    }else if (msg2.contains("FETCH?")){
                        return true;
                    }else if (msg2.contains("GOODBYE!")){
                        return true;
                    }else if (msg2.contains("HELLO") || msg2.contains("")){
                        return true;
                    }
                    return false;
                }
            });
            sender.start();

            Thread receive= new Thread(new Runnable() {
                String msg ;
                @Override
                public void run() {
                    try {
                        msg = in.readLine();
                        while(msg!=null){
                            System.out.println("Client : "+msg);
                            if(msg.equals("WHEN?")){
                                out.println("NOW " + new Date().getTime());
                                out.flush();
                                msg = in.readLine();
                            }else if (msg.contains("POSTS?")){
                                try{
                                    long time = Long.parseLong(getNextWord(msg, "POSTS?"));
                                    int count = 0;
                                    for(Post post : posts){
                                        if(post.getCreated() >= time){
                                            count =count + 1;
                                            
                                        }
                                    }
                                    out.println("OPTIONS " + String.valueOf(count));
                                    out.flush();

                                    
                                }catch(Exception e){
                                    
                                    out.println("UNKOWN COMMAND, GOOD BYE");
                                    out.flush();
                        
                                }
                                msg = in.readLine();

                            }else if (msg.contains("FETCH?")){
                                try {
                                    String key = getNextWord(msg, "SHA-256");
                                    int count = 0;
                                    for(Post post : posts){
                                        if(post.getPost_id().equals(key)){
                                            count =+ 1;
                                            out.println("FOUND ");
                                            out.println("Post-Id: SHA-256 " + post.getPost_id() + "\n"
                                            + "Created: " + post.getCreated() + "\n"
                                            + "Author: " + post.getAuthor()
                                            + "Contents:" + post.getConents());
                                            
                                        }
                                    }
                                    if(count == 0){
                                        out.println("SORRY");
                                    }
                                    out.flush();
                                } catch (Exception e) {
                                    out.println("UNKOWN COMMAND, GOODYE");
                                    out.flush();
                                    clientSocket.close();
                                    serverSocket.close();
                                    break;

                                }
                                msg = in.readLine();
                            }else if(msg.contains("HELLO") || msg.contains("")){
                                msg = in.readLine();
                            }
                        }

                        System.out.println("Client disconnected");
                        out.close();
                        clientSocket.close();
                        serverSocket.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
            receive.start();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
    public static String getNextWord(String str, String word) {
        String[] words = str.split(" "), data = word.split(" ");
        int index = Arrays.asList(words).indexOf((data.length > 1) ? data[data.length - 1] : data[0]);
        return (index == -1) ? "Not Found" : ((index + 1) == words.length) ? "End" : words[index + 1];
    }
}