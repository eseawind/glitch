glitch
======

Error Handling Framework

Purpose
-----

Configuration Setup
-----

### Configuring Mappings Using the Configuration File

### Configuring Mappings Programmatically


ExceptionManager
-----


Remaining Tasks
-----
The following tasks are remaining:

- Update the README so that it is clear to the reader what the purpose is, how to use it and how to modify the code base.
- The Configuration's handler removal is unimplemented. This is needed to remove handlers that could not be instantiated. The underlying collection mechanism does not support this (or I don't know how to use it).
- Remove the printStackTrace method from the CoreException interface. The interface softly assumes that implementors will be a java.lang.Throwable. But, that doesn't need to be the case. Removing the printStackTrace contract help separate the idea of the CoreException being a true Throwable.
- More ExceptionManager test cases are needed. This task was not completed and, thus, the unit testing is not as comprehensive as it should be.