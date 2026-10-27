# Custom Resolver for WSO2 Micro Integrator

A sample custom Synapse **resolver** that shows how to plug your own value resolution logic into WSO2 Micro Integrator, next to the built-in `$SYSTEM:` and `$FILE:` resolvers.

`TestResolver` returns `pqr` when the input is `abc`, and returns any other input unchanged.

## What's inside

| Path | Description |
| --- | --- |
| `CustomResolverProject/src/main/java/org/example/resolvers/TestResolver.java` | The resolver. Implements `org.apache.synapse.commons.resolvers.Resolver`. |
| `CustomResolverProject/pom.xml` | Maven build. Depends on `synapse-commons` `2.1.7-wso2v227`. |

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

> **Note:** This project does not include that file yet, so add it under `src/main/resources/META-INF/services/` before building.

## Build

```bash
cd CustomResolverProject
mvn clean package
```

## Deploy

1. Copy the JAR from `CustomResolverProject/target/` to `<MI_HOME>/lib`.
2. Restart the server.
3. Reference the resolver in a configuration value with the `$CUSTOM_` prefix, for example `$CUSTOM_TestResolver:abc`.

Check the exact syntax supported by your MI version against `ResolverFactory` in [wso2-synapse](https://github.com/wso2/wso2-synapse/blob/master/modules/commons/src/main/java/org/apache/synapse/commons/resolvers/ResolverFactory.java).

## License

Licensed under the [Apache License 2.0](LICENSE).
