# Playwright TestNG LambdaTest Integration

This project demonstrates how to run Playwright tests on LambdaTest automation platform using TestNG framework.

## Prerequisites Setup

### 1. Environment Variables

Set the following environment variables with your LambdaTest credentials: which you can find once you sign in to lambdatest account 

```bash


String user = "msainir"; // replace with your username
String accessKey = "LT_s123"; // replace with your access key


```


### 2. Install Hyperexecute Binary


If you are currently using `MacOS`

```

curl -O https://downloads.lambdatest.com/hyperexecute/darwin/hyperexecute
chmod u+x hyperexecute

```

or 

If you are currently using `Win`

```
curl -O https://downloads.lambdatest.com/hyperexecute/windows/hyperexecute.exe
```

## Overview

1. **Java 18** - Make sure you have Java 18 installed to test it locally using mvn, if you dont have it you can still directly trigger it on hyperexecute using runtime which is set in yaml.
2. **LambdaTest Account** - You need a LambdaTest account with valid credentials
3. **LTCapability.java** - It is the file where we are setting up the capabilities.
4. **BaseTest.java** - It is the file where we are setting up the driver and the hooks.



## Project Structure

```
src/test/java/example/
├── LTCapability.java          # LambdaTest capabilities configuration
├── base/
│   └── BaseTest.java          # Base test class with LambdaTest connection
|
home page
└── tests/
    ├── GoogleSearchTest.java  # Google search functionality test
    ├── SampleTest1.java       # Sample test 1
```

## Running Tests

### Run All Tests
```bash
mvn test
```

### Run Specific Test Class
```bash
mvn test -Dtest=GoogleSearchTest -Dexec.skip=true
mvn test -Dtest=SampleTest1 -Dexec.skip=true
```

### Offical document link

1. [Offical Docs](https://www.lambdatest.com/support/docs/deep-dive-into-hyperexecute-yaml/)

2.  [CI/CD Integration Doc](https://www.lambdatest.com/support/docs/hyperexecute-integration-with-ci-cd-tools/)

3. [Job Report Doc](https://www.lambdatest.com/support/docs/hyperexecute-job-reports/)


