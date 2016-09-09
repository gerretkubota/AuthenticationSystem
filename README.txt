/********************************************************************************
*
*
*  Name: Gerret Kubota
*  Class: CS 460
*  Assignment: Final Project
*  Description: Creating a authentication system for users to log into a server.
*               The user can create/login into the server. If the user is creating
*               an account for the first time, it will ask to come up with a 
*               user name and password and entering the number of authentications
*               the person would want for each time they log in 
*               (the number they enter will be the number of times the password will
*               be hashed by using SHA-1). If the user is trying
*               to log into the server, the server will ask the user to input the
*               provided password that was stated in the previous logging in session.
*               As the passwords are used, it will be deleted.
*
* Credit: Copyright © 2015 [Gerret Kubota] All rights reserved. Copyrights
*********************************************************************************/ 


---------------------------
S/Key Authentication System
---------------------------

 * Introduction
 * Instructions (Step by step)
 * Security Analysis



------------
Introduction
------------

The purpose of this program is to ensure a secure and safe account when using our application.
This application uses the S/Key authentication system, which utilizes a one way hash function for the user’s password. The application will ask the user to create a user name, password, and the number of times the user would want the account to be authenticated. The number of authentications provided by the user will determine how many times the password will be hashed from using the SHA-1 hashing function. Using a one way hash function is great because once you hash a value it is infeasible, it is very difficult to obtain the actual value before it was hashed (in addition to the SHA-1, the application will add “salt” to the password so that it will be harder for attackers to obtain the password). Furthermore, when the user provides the number of authentications, the application will then provide the password each time you have successfully logged in. Afterwards, the application will then delete the provided password, and when the number of authentications is 0, the account will be then deleted. The user name and password will be written in a text file and it will be updated in a orderly manner. Hope you enjoy the usage of this application!

---------------------------
Instructions (Step by step)
---------------------------

i). Run the program through any IDE or terminal.
ii). As the program is running, the user will see the menu displayed.

****************
First time users
****************

i). Choose create an account.
ii). You will be instructed with the following steps displayed.
iii). Choose a user name; it is not cased sensitive, you may include any special characters.
iv). Choose a strong password (Mixing it up with special characters and capitalization is strongly recommended).
v).  Choose the number of authentications you would like for the account you have created.
vi). You will be provided for a password for the next log-in time.
CAUTION: BEFORE YOU EXIT THE PROGRAM, PLEASE MAKE SURE THAT YOU WRITE THE PROVIDED PASSWORD DOWN SO THAT YOU MAY
LOG IN THE NEXT TIME YOU VISIT THE PROGRAM.

****************
Existing users
****************

i). Choose log in.
ii). You will be prompted to enter your user name.
iii). You will be prompted to enter the password (This should have been provided after you have successfully logged in).
iv). After you have successfully logged in, you will be provided with a password. If you have logged in with the last password authentication, the account will be deleted.

****************
Credits
****************

i). Choose the credits option to see the copyright.


--------------------
Security Analysis
--------------------
The flaws of this application is that it uses a plain text file that contains the user’s information; such as their user name and passwords. The attacker may easily obtain the text file and possess all of the user’s information. In the near future, to prevent from having attacks in this application, the developers may implement an encryption to the text-file or use an alternative way so that the attacker may not obtain the text-file so easily.