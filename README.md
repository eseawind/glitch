glitch
======

Error Handling Framework

Overview
-----
The glitch framework provides a way to handle an error by sending it to a manager that will perform actions that are desired when that error appears.

To use the framework, the following three interfaces and classes will be in use:
- <code>CoreException</code>
- <code>CoreExceptionHandler</code>
- <code>ExceptionManager</code>

All error classes must implement the <code>CoreException</code> interface. This interface identifies the implementing class as an error to the framework.

The <code>CoreExceptionHandler</code> interface identifies a class that is responsible for handling an error in a specific manner. For example, your project may want certain errors to be saved to a database. A handler class (i.e., a class that implements CoreExceptionHandler) is created with logic to do this specific task. Thus, errors (i.e., a class that implements CoreException) that should be saved to the database are then mapped to the handler in the configuration file.

The <code>ExceptionManager</code> class is used to execute the handling of actions associated with error using the <code>handleException(CoreException)</code> method. The <code>ExceptionManager</code> class is a singleton that holds the configuration mapping between the errors and handlers. Whenever the <code>handleException</code> method is called for the error, the class finds all handlers mapped to that error and executes the handlers. If no explicit handler mappings are found for the error then all listed default handlers are executed.

Configuration
-----

### Mapping Handlers using the Configuration File

### Mapping Handlers Programmatically


ExceptionManager
-----


Remaining Tasks
-----
The following tasks are remaining:

- Update the README so that it is clear to the reader what the purpose is, how to use it and how to modify the code base.
- The Configuration's handler removal is unimplemented. This is needed to remove handlers that could not be instantiated. The underlying collection mechanism does not support this (or I don't know how to use it).
- Remove the printStackTrace method from the CoreException interface. The interface softly assumes that implementors will be a java.lang.Throwable. But, that doesn't need to be the case. Removing the printStackTrace contract help separate the idea of the CoreException being a true Throwable.
- More ExceptionManager test cases are needed. This task was not completed and, thus, the unit testing is not as comprehensive as it should be.
