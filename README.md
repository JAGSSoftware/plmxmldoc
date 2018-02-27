# plmxmldoc

[![Build Status](https://travis-ci.org/JAGSSoftware/plmxmldoc.svg?branch=develop)](https://travis-ci.org/JAGSSoftware/plmxmldoc)
[![CodeFactor](https://www.codefactor.io/repository/github/jagssoftware/plmxmldoc/badge/develop)](https://www.codefactor.io/repository/github/jagssoftware/plmxmldoc/overview/develop)

The utility reads a xml [Teamcenter][1] preference file, extracts the information and renders a
html file with the content.

In order to have a right visualization of the generated html it is recommended to have access
to internet, because te html file uses bootstrap css styles and jquery.

## Compilation

First clone the project in your local system. To create the executable package you need [maven][2]
installed in your computer. Clone it in the directory of your choice. Go to the directory.

```bash
$> mvn clean package
```

This will compile the project and create the package in target/ directory.

## Use

Once the package has been created, you can make use of it directly from target/ directory,
or copy it in any other directory of your choice and use from there.

It is an executable jar package, therefore to run it:

```sh
$> java -jar plmxmldoc-1.0.0.jar -project <name of the project>
-version <version of the project> -inputFilenames <preference input file name>[,<preference input file name>,...]
[-outputFilename <preference documentation file name>]
```

The argument ```outputFilename``` is optional. If it is not specified:

* and there is only one inputFilename, the output file will have the same name as inputFilename but replacing ```.xml``` by ```.html```
* and there is a comma separated list of inputFilenames, the output file will have the name ```output.html```

and it will be created in the same directory the tool was executed.

[1]: https://www.plm.automation.siemens.com/de_de/products/teamcenter/
[2]: https://maven.apache.org/
