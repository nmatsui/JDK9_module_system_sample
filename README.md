# Java Module System Sample

## Description
Sample programs of [Java Platform Module System (JSR 376)](http://openjdk.java.net/projects/jigsaw/spec/)

### case1 & case3
case1 : The problem of CLASS visibility from the outside of package in Java 8.  
case3 : The solution by exporting  packages in Java 9.

### case2 & case4
case2 : The problem of dependency tracking of JAR in java 8.  
case4 : The sosution by requiring packages in Java 9.

## Requirements

* java 9
  * "9-ea" (build 9-ea+95-2015-12-07-122822.javare.4009.nc)

## How to use
### case1
1 Create `service.jar`

```text
case1$ javac -d service/classes/ $(find service/src -name "*.java")
case1$ jar cvf jars/service.jar -C service/classes/ .
```

2 Create `client.jar`

```text
case1$ javac -d client/classes/ -cp client/src:"jars/*" $(find client/src -name "*.java")
case1$ jar cvf jars/client.jar -C client/classes/ .
```

3 Even if `Util.java` should not be used from the outside of this service, you can use it.

```text
case1$ java -cp "jars/*" net.example.client.Expected
com.example.service.Gateway start
com.example.service.util.Util start
com.example.service.util.Util end
com.example.service.Gateway end

case1$ java -cp "jars/*" net.example.client.UnExpected
com.example.service.util.Util start
com.example.service.util.Util end
```

### case2
1 Create `commons.jar`

```text
case2$ javac -d commons/classes/ $(find commons/src -name "*.java")
case2$ jar cvf jars/commons.jar -C commons/classes/ .
```

2 Create `service.jar`

```text
case2$ javac -d service/classes/ -cp service/src:"jars/*" $(find service/src -name "*.java")
case2$ jar cvf jars/service.jar -C service/classes/ .
```

3 Even if you have delete `commons.jar`, you can compile `client.jar`.

```text
case2$ rm jars/commons.jar
case2$ javac -d client/classes/ -cp client/src:"jars/*" $(find client/src -name "*.java")
case2$ jar cvf jars/client.jar -C client/classes/ .
```

4 You will encounter Runtime Error when you run this client.

```text
case2$ java -cp "jars/*" net.example.client.Client
com.example.service.Gateway start
Exception in thread "main" java.lang.NoClassDefFoundError: com/example/commons/lib/Library
	at com.example.service.Gateway.method(Gateway.java:8)
	at net.example.client.Client.main(Client.java:9)
Caused by: java.lang.ClassNotFoundException: com.example.commons.lib.Library
	at jdk.internal.misc.BuiltinClassLoader.loadClass(java.base@9-ea/BuiltinClassLoader.java:390)
	at jdk.internal.misc.ClassLoaders$AppClassLoader.loadClass(java.base@9-ea/ClassLoaders.java:177)
	at java.lang.ClassLoader.loadClass(java.base@9-ea/ClassLoader.java:373)
	... 2 more
```

### case3
1 Create `service.jar`

```text
case3$ javac -d service/mods/ $(find service/src -name "*.java")
case3$ jar cvf mlibs/service.jar -C service/mods/ .
```

2 You can't compile `UnExpected.java` because only `com.example.service` is exported in `service/src/module-info.java`.

```text
module service {
  exports com.example.service;
}
```

```text
case3$ javac -d client/mods/ -mp mlibs client/src/module-info.java
checking service/module-info

case3$ javac -d client/mods/ -mp mlibs client/src/net/example/client/Expected.java
checking service/module-info

case3$ javac -d client/mods/ -mp mlibs client/src/net/example/client/UnExpected.java
checking service/module-info
client/src/net/example/client/UnExpected.java:3: error: package com.example.service.util does not exist
import com.example.service.util.Util;
                               ^
client/src/net/example/client/UnExpected.java:7: error: cannot find symbol
    Util util = new Util();
    ^
  symbol:   class Util
  location: class UnExpected
client/src/net/example/client/UnExpected.java:7: error: cannot find symbol
    Util util = new Util();
                    ^
  symbol:   class Util
  location: class UnExpected
3 errors
```

### case4
1 Create `commons.jar`

```text
module commons {
  exports com.example.commons.lib;
}
```

```text
case4$ javac -d commons/mods/ $(find commons/src -name "*.java")
case4$ jar cvf mlib/commons.jar -C commons/mods/ .
```

2 Create `service.jar`

```text
module service {
  exports com.example.service;
  requires commons;
}
```

```text
case4$ javac -d service/mods/ -mp mlib/ $(find service/src -name "*.java")
checking commons/module-info
case4$ jar cvf mlib/service.jar -C service/mods/ .
```

3 If you delete `commons.jar`, you can't compile `client.jar` because javac goes back to ancestor JARs  and checks the existence of modules.

```text
module client {
  requires service;
}
```

```text
case4$ rm mlib/commons.jar
case4$ javac -d client/mods/ -mp mlib $(find client/src -name "*.java")
checking service/module-info
error: cannot find module: commons
1 error
```

## License

[MIT LISENSE](https://github.com/nmatsui/JDK9_module_system_sample/blob/master/LICENSE)

## Author

Nobuyuki Matsui nobuyuki.matsui@gmail.com

