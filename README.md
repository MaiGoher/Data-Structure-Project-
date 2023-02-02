# Data-Structure-Project-
XML (Extensible Markup Language) is one of the most famous formats for storing and sharing information among different devices.
Some text editors such as Sublime Text are able to parse such files and do some basic operations.
In this project, you will work on developing a GUI (Graphical User Interface) based program to parse and visualize an XML file.

In our system the XML file will represent users in a social network
Each user has id (unique), name, list of posts, list of followers.
Each post has text and list of topics.
## Features

- Checking XML consistency
- Correcting XML
- Formatting XML
- Converting XML to JSON
- Minifying XML file
- Compressing XML/JSON data
- Decompressing compressed data
- Network analysis
- Post Search
- Graph visualization
![Xml_logo svg](https://user-images.githubusercontent.com/39887130/216409010-1073bed3-4518-4085-b1a9-981f571b44a4.png)
### Checking XML consistency

Check if the XML file is consistent, that is, all opening tags have corresponding closing tags.
This check is done using Stack data structure.
If the XML file is inconsistent, number of errors, and tags left in the stack are displayed to the user.
### Formatting XML

Adjust the indentation of an XML file, to make it more readable.
If the file is not consistent, user cannot format it.
