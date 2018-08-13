## Java Chat-server

In order to run the server:
- clone the repository
- on the root of the project, go to: `cd out/production/chat-server`
- run `java com/company/Server/Main numberOfPort`, replace numberOfPort with a port number on which you want to run the server eg. 8080.

In order to run the client:
- clone the repository
- on the root of the project, go to: `cd out/production/chat-server`
- run `java com/company/Client/Main IPAddress numberOfPort`, replace IPAddress with the IPaddress of the server, replace numberOfPort with a port number on which the server is running

OR

connect to the server using TCP client, eg. netcat. Enter: `nc IPAddress numberOfPort`.

