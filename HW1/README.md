# Run all tests
mvn clean test

# Run one test class
mvn clean test -Dtest="--TestClass--"

# Run one test method
mvn clean test -Dtest="--TestClass--#--TestMethod--"