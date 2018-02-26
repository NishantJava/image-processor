# image-processor

**Technical stack:**
1.	Java 8
2.	Logging Framework: Slf4j-Logback
3.	Build framework – Gradle
4.	IDE - IntelliJ IDEA
5.	File handling – Java NIO
6.	Other features - Streams API

**Application flow:**
1.	Check for files in “_images_” resource folder. This path can be changed to any  physical folder. It can also be modified to filter specific files extensions or names.
2.	For each file, open input stream and read first 1000 bytes in byte array.
3.	If bytes read are less than 1000 (small or empty files), log warning message. However still continue the processing.
4.	If there is any error in reading file (IO errors), log the error with exception stack, and return byte total as 0.
5.	Total the byte array elements (using Java 8 Stream reducing). As there is no in-built Byte stream, the elements are moved to Integer stream instead (with implicit casting). This helps to make code more readable. However, this can be replaced with simple for loop.
6.	Put the file name (keys) and byte total (Values) in Hash Map. This is important as file names are unique and byte total can be duplicate. As we have to sort over Byte total, we need one more step. If that had been File name, we could have used TreeMap.
7.	Next step is to sort the result map in ascending order of its values. This is achieved using Stream sorting using in-built value comparator and collecting results in LinkedHashMap which maintain the order of entries.
8.	Lastly we just print the results.

**Testing:**
The code is written to work with any file types. So I have used text files for testing. Each test file contains single “Char” repeated 1000 times. So we know the byte total beforehand (ASCII value of char * 1000). Rest is just routine _JUnit_.

