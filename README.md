# JVM Magics

[![Build Status](https://travis-ci.org/jupyter/jvm-magics.svg?branch=master)](https://travis-ci.org/jupyter/jvm-magics)
[![JVM magics on jitpack](https://jitpack.io/v/jupyter/jvm-magics.svg)](https://jitpack.io/#jupyter/jvm-magics)

[**Configuration**](#configuration) | [**Usage for Library Authors**](#usage---library-authors) | [**Usage for Kernel Authors**](#usage---kernel-authors)

This project is a plugin system for magics implementations across JVM kernels (Scala, Clojure, Groovy, ...).

## Purpose

This API has two main uses:

* For library authors to provide a way to expose Jupyter magic functions to JVM kernels
* For kernel authors to call magic functions that are made available by libraries

For example, Apache Spark runs on the JVM. Instead of implementing a `sql` magic function separately in every kernel to run a SparkSQL statement, a library can implement a `sql` magic function once to make it available to any kernel.

This separates the dependencies of the kernel from the dependencies of magic functions that are available. Kernels and magics can be written in different languages and magics can be loaded at runtime.

## Configuration

See [instructions on JitPack](https://jitpack.io/#jupyter/jvm-magics) for gradle, maven, sbt, or leiningen.

## Usage

### Usage - Library authors

Library authors can register a magic function by calling `Magics.register`.

For example, the following will register a magic function for SparkSQL:

```scala
Magics.registerLineCellMagic("sql", (line, cell, interp) -> {
  String sqlText = cell != null ? cell : line;
  SparkSession spark = SparkSession.builder.getOrCreate();
  DataFrame df = spark.sql(sqlText);
  interp.setVariable("sqlResult", df);
  return df;
});
```

Any kernel implementation can use this `sql` magic to run SQL queries using Spark.

This library is intended to be used with jvm-repr. Objects returned by magic functions are displayed by kernel implementations, and the recommended implementation is to use `jvm-repr` displayers.

Magics implementations can provide jvm-repr displayers for classes returned by magics. By using displayers, magics do not need to spend time in expensive display conversions unless the representations will be sent to the user session.

### Usage - Kernel authors

Kernel authors can use this API to call registered magic functions:

```java
Object result = Magics.callLineMagic("sql", lineText);
Map<String, String> resultByMIME = Displayers.display(result);
Kernel.this.display(resultByMIME);
```

It is up to the kernel to display the object returned by a magic function, ideally using `jvm-repr` displayers.

