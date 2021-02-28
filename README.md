# CS1027 CS Fundamentals II - Assignment 2

A Java program for pathfinding using Depth First Search (DFS). Implement a stack data type for the DFS algorithm. Here is the flow of the program:

1. Read in the filename of an input file from the command line.
2. Parse the input file into a map using the ``JFrame`` library, display it on a new window.
3. Each map will have a starting cell (node). The program will start from the cell and look for the target.

The following is the output we should get from the file ``resources_test/xmap2.txt``.

<img src="./xmap2.gif" height="500px" />


## Learning Outcomes

To gain experience with

* Solving problems with the stack data type
* The design of algorithms in pseudocode and their implementations in Java
* Handling exceptions

## Get Start!

As we can see on the proejct title, this is the second assignment of the course [CS1027](https://www.csd.uwo.ca/courses/CS1027b/). If you want to know more about the implementation, here is the [original pdf](./Assignment2_Instructions.pdf) for the assignment.

If you are cloning or forking this repository, this project's entry point is at the following path: ./src/StartSearch.java. It is a console program. To run it, here are some file paths for your argument:

```text
    ./resources_test/xmap1.txt
    ./resources_test/xmap2.txt
    ./resources_test/xmap3.txt
    ./resources_test/xmap4.txt
    ./resources_test/xmap5.txt
```

Else if you are only "stealing" some files from your assignment, please make sure you delete the package name and my name at the comment (Since I do not want to get involved in your grading ...). The purpose of this project is to showcase my work. If you overused my code, please don't tell me. Thanks.

Here is a sitemap for all the file in this project:

* root
  * src
    * *.java
    * Test*.java
  * resources
    * *.(jpg | png)
  * resources_test
    * xmap[1-7].txt
  * .gitignore
  * Assignment2_instructions.pdf
  * README.md
  * LICENSE
