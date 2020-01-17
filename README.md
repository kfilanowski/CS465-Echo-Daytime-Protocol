# Author
Kevin Filanowski

# Version
09/30/18 - 1.0.0

# Table of Contents
* [Description](#description)
* [Contents](#contents)
* [Compiling](#compiling)
* [Usage](#usage)

# Description

This program contains two separate programs.
The first, EchoTcp, establishes a TCP connection with a specified
server on some port and writes a specified message on that connection.
It then prints the message returned by the server, which will be the same
message sent. This demonstrates the Echo protocol using TCP.

The second program, DaytimeUdp, demonstrates the daytime protocol.
The program sends datagram packets to a specified server on a
specified port and sends a message. The message returned from the
server will be printed and this should be the date and time.

# Contents
doc - A HTML document of the java docs.

src - A folder containing the source files of the program.

## Inside src

EchoTcp.java
* Demonstrates Echo protocol on specified server.

DaytimeUdp
* Demonstrates Daytime protocol on specified server.

# Compiling
To compile the program, ensure that the files described in 'CONTENTS',
specifically in the src folder, are all in the same directory.
Then run the following command to compile all java files in your current
directory:

`javac *.java`

There should be no errors or warnings. two class files should appear.

# Usage
`java EchoTcp`

`java DaytimeUdp`

or

`./java EchoTcp`

`./java DaytimeUdp`
