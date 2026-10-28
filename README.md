# Custom Resolver for WSO2 Micro Integrator

A sample custom Synapse **resolver** that shows how to plug your own value resolution logic into WSO2 Micro Integrator, next to the built-in `$SYSTEM:` and `$FILE:` resolvers.

`TestResolver` returns `pqr` when the input is `abc`, and returns any other input unchanged.

## What's inside

| Path | Description |
| --- | --- |
| `CustomResolverProject/src/main/java/org/example/resolvers/TestResolver.java` | The resolver. Implements `org.apache.synapse.commons.resolvers.Resolver`. |
| `CustomResolverProject/pom.xml` | Maven build. Depends on `synapse-commons` `4.0.0-wso2v268` (MI 4.x line). |

## How Synapse finds custom resolvers

Synapse's `ResolverFactory` loads custom resolvers with Java's `ServiceLoader`, and registers each one under its **simple class name in lowercase** (here: `testresolver`). Values that start with `$CUSTOM_` are sent to the matching resolver.

For `ServiceLoader` to find the class, the JAR must contain this file:

```
META-INF/services/org.apache.synapse.commons.resolvers.Resolver
```

with the fully qualified class name as its content:

```
org.example.resolvers.TestResolver
```

This project includes it in `src/main/resources/META-INF/services/`.

## Build

```bash
cd CustomResolverProject
mvn clean package
```

## Deploy

1. Copy the JAR from `CustomResolverProject/target/` to `<MI_HOME>/lib`.
2. Restart the server.

## Tested behaviour

Tested against `ResolverFactory` from `synapse-commons` `4.0.0-wso2v268`:

| Input | Resolver used | Result |
| --- | --- | --- |
| `$CUSTOM_TestResolver:abc` | `DefaultResolver` | `$CUSTOM_TestResolver:abc` (unchanged) |
| `$CUSTOM_TestResolver:x$CUSTOM_TestResolver:abc` | `TestResolver` | `pqr` |

`ResolverFactory` finds `TestResolver` through `ServiceLoader`, but for `$CUSTOM_` values it calls `matcher.find()` a second time and passes the **second** match's value to the resolver. A single `$CUSTOM_Name:value` therefore falls back to the default resolver. Check the behaviour of your MI version against [`ResolverFactory`](https://github.com/wso2/wso2-synapse/blob/master/modules/commons/src/main/java/org/apache/synapse/commons/resolvers/ResolverFactory.java) before relying on it.

> Older `synapse-commons` `2.1.7-wso2v227` splits the class name with `split(".")`, which fails with `ArrayIndexOutOfBoundsException` as soon as any external resolver is on the classpath. Use the MI 4.x line.

## License

Licensed under the [Apache License 2.0](LICENSE).
