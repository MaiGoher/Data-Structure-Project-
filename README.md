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
## Checking XML consistency

Check if the XML file is consistent, that is, all opening tags have corresponding closing tags.
This check is done using Stack data structure.
If the XML file is inconsistent, number of errors, and tags left in the stack are displayed to the user.
## Formatting XML

Adjust the indentation of an XML file, to make it more readable.
If the file is not consistent, user cannot format it.
## Converting XML to JSON

JSON((Javascript Object Notation) is a format that is used to represent data, and it's widely used to share structures information over the web.
Conversion to XML is done on three iterations:
1. Transform the xml string into array of node objects.
2. transforms this array into a tree of nodes.
3. transforms the tree recursively into JSON string
## Compressing XML/JSON data

Huffman coding is a data compression technique that is used to represent the data in an efficient way. It is a lossless compression algorithm that works by constructing a binary tree that assigns codes to each character in the text. These codes are chosen based on the frequency of the characters, with more frequent characters getting shorter codes and less frequent characters getting longer codes. The main goal of Huffman coding is to minimize the average length of the codes assigned to the characters, resulting in more efficient storage of the text, which resulting a significantly reduce the size of the data without losing any of the original information.
## Decompressing compressed data

Return the compressed data to its original format
Simply , Decompression works by reversing the process of compression,the compressed text is read bit by bit then look up the corresponding symbol in the reverse Huffman coding dictionary. The decoded symbols are concatenated to form the original data.
## Network analysis

Analyze the data represented in the network

**Most Influencer User**
- It returns the user who has the most followers on the network
**Most Active User**
- It return the user who follows the biggest number of users
**Mutual Friends**
- The mutual followers between two users are the users who follow both of them.
**Suggested Friends**
- For each user, a list of users to follow can be suggested based on the followers of their followers.
## Post Search

- Searching is simply done by checking the post's body text and topics for the word to be found, and adds a string with the user's name and relevant information (the body or topic) to the list to be returned if found.
## Graph visualization

- Graph Visualization is done using : 
         
         Graphviz

     ![WhatsApp Image 2023-02-02 at 8 49 30 PM](https://user-images.githubusercontent.com/39887130/216422129-912d17b0-0cb5-4af8-a153-c5309a47219e.jpeg)
## Screenshots

![WhatsApp Image 2023-02-02 at 9 04 21 PM](https://user-images.githubusercontent.com/39887130/216425982-708a634f-0ad9-4b32-b235-a77ddfafdb48.jpeg)

![WhatsApp Image 2023-02-02 at 9 04 05 PM](https://user-images.githubusercontent.com/39887130/216426409-605e12bd-e495-4b56-8ab2-8eef120ed379.jpeg)
## Authors

- [@esraaAmr](https://github.com/esraaAmr)
- [@Habibaaahmed](https://github.com/Habibaaahmed)
- [@MaiGoher](https://github.com/MaiGoher)
- [@maramahmed74](https://github.com/maramahmed74)
