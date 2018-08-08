## Java Echo Server

In order to run the server:
- clone the repository
- on the root of the project, go to: `cd out/production/echo-server`
- run `java com/company/Server/Main numberOfPort`, replace numberOfPort with a port number on which you want to run the server eg. 8080.

Connect to the server using TCP client, eg. netcat. Enter: `nc localhost numberOfPort`, if you are on different machine than the server, replace localhost with IP number; replace numberOfPort with number of port on which the server is running.

