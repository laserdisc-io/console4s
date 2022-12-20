# console4s

Some utilities for rendering console output. 

## Installation

To use the dependency, add the following to your `build.sbt`

```sbt
libraryDependencies += "io.laserdisc" %% "console4s" % "latestVersion"
```

## Utilities

### Tree Rendering

Format a hierarchy of nodes as a tree.  See [README-TreeRendering.md](README-TreeRendering.md) 

```
─ Root
  ├─ Child A
  │  ├─ ChildA-1
  │  └─ ChildA-2
  ├─ Child B
  └─ Child C
     └─ ChildC-1
```



