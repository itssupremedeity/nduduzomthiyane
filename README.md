# nduduzomthiyane
grad assessment

An application that turns base64 image data to actual image and stores it in a file.

Tools:
  Programming language - JAVA
  Framework - Springboot
  Database - Sprinboot in memory database H2
  
Its reads a csvfile that contains base64 image encodings and converts them into actual files stored inside a local
direcory and turns the file to a URI and stores it in a database

Images can be retrieved by a REST endpoint querying our database and displaying the image object.

URL format - http://localhost:8080/v1/api/image/Momentum/Health




