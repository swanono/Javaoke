# Javaoke INFO4

## How to make it work

In the command line go to the Javaoke directory, type :
```bash
mkdir stats bin
```

To the ip of the server, type :
```bash
ip addr ls | grep "inet"
```

Then, to compile the code, type :
```bash
javac -d ./bin -cp ./src ./src/**/*.java
```

Then, to launch the project you can do :
- First in the computer of the server :
```bash
java -cp ./bin serverpkg/StatsManager
```

- To launch the server :
```bash
java -cp ./bin serverpkg/Server (-l)
```

- To launch the client requesting the music list :
```bash
java -cp ./bin clientpkg/ClientLauncher <ip-server> --list
```

- To launch the client requesting a music file :
```bash
java -cp ./bin clientpkg/ClientLauncher <ip-server> --play <music-name> (--speed=<multi-music-speed>) (--pitch=<music-speed>)
```

Parameters in parenthesis are optionnals, replace the <...> by real values