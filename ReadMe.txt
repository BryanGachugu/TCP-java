This is a demonstration of Java sockets using TCP connection between a server and client.

The server initializes a connection in which all other clients can connect to.
After a successful connection, A request is sent to the server and from teh server to the client to inform both parties that the connection was successfully established

After, The clients or the server can start writing commands to access and manage posts which are available in both cases. (The list of posts could exist in the server alone, and then all clients would fetch them from here, but if the fetching fails for this part only, then we would need to make sure the list exists. Thus, I have eliminated the need for fetching posts and made them available in to all client instances)

Both the client and the server listen for commands from any either end and reflects what the command was. That is, if a client types in a command, the server  can see the command typed but not the results, and vice versa

-------------------------RUNNUNG------------------------
STEP 1: Open cmd
STEP 2: Navigate to the directory (TCPJava)

# The above step can be accomplished by dragging the folder to the cmd

STEP 3: Compile Server file with >> javac Server.java
STEP 4: Run the Server file with >> java Server

# You should now see "waiting for client request"

STEP 5: Open another instance of the CMD in the same working directory (TCPJava)
STEP 6: Compile Main file with >> javac Main.java
STEP 7: Run the Main file with >> java Main
