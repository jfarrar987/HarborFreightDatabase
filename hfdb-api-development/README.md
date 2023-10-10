# hfdb-api

API layer for HFDB. This program allows the front end to grab the data from the database in a RESTful fashion

## Development Setup

First and foremost, all the applications need to be setup. In order to do this, chocolatey needs to be setup if not already done so. Open powershell (as admin) and run the following command:<br/>`Set-ExecutionPolicy Bypass -Scope Process -Force; [System.Net.ServicePointManager]::SecurityProtocol = [System.Net.ServicePointManager]::SecurityProtocol -bor 3072; iex ((New-Object System.Net.WebClient).DownloadString('https://community.chocolatey.org/install.ps1'))`

**Do not click out of powershell and keep it in the foreground until it is finished**

Chocolatey is finished installing, close and reopen powershell (as admin) and run the following command:<br/>
`choco install git github-desktop maven openjdk vscode -y`

**Do not click out of powershell and keep it in the foreground until it is finished**

Some of these programs update on their own so run this command to pin them. It is highly recommended. Run the following commands to do so.<br/>
`choco pin add -n=vscode`<br/>
`choco pin add -n=github-desktop`

The final program to be installed in chocolatey is postgresql. Run the following command in powershell (as admin):<br/>`choco install postgresql -y`

**Do not click out of powershell and keep in the foreground unti it is finished**<br/>**One more thing...** after postgres has finished installing and before you close out powershell, the password to postgres has been printed to the console. Copy it store it somewhere. That is the password you will use to login to postgres.

We are now finished with powershell so it can be closed.

Next we need to define the `hfdb-database0` database and the tables within it.

First open psql and hit enter on each line until you get to the password field. Your password needs to be pasted into that field. When pasting it, it will no be echoed back so you won't see it being typed or pasted.<br/><br/>
Once logged in, type the following command to create the database: `create database "hfdb-database0";` and hit enter. Once that command is ran, type `\l` and hit enter to verify that the database was created. Once it is verified that it was created, connect to it by typing `\c hfdb-database0` and hit enter.<br/><br/>
Next, navigate over to the [tables repository](https://github.com/hfdb-info/tables) and open the tables.sql file and click the raw button. Then copy the table definitions and paste them into the psql terminal window. Verify that for each table, psql says that the table was created. It wouldn't hurt to press enter a couple of times at the end to make sure the multiline create table command was executed.<br/>

We are now finished with psql and it can be closed.

Next the repository needs to be cloned. Open github desktop and sign into it with your github account. Once signed in, clone this repository to a folder somewhere.

Once github desktop has finished cloning the repository, make sure the branch selected is `development` **and not** `main` **or** `original-template` **or any other branch if any exist**. The latter branches are reserved for special uses. Unless Justin says otherwise, only use the `development` branch.

Once visual studio code opens, it'll have loaded the directory as a project. The files structure in the explorer pane in vscode should match the directory exactly how it appears in the repo on github.

The next step to be accomplished is installing all the necessary extensions. With vscode open, hit ctrl+p and copy/paste in the following command and hit enter:<br/>
`ext install vscjava.vscode-java-pack esbenp.prettier-vscode Pivotal.vscode-boot-dev-pack`

Once the extensions are finished installing, the last thing to do is setup the environment variables for the program. In the search bar in the start menu, search for `edit the system environment variables`. When it has been found, open it, and click on `environment variables...` at the bottom. From here you will need to add the following **User** variables:

`pgIPAddr` : `127.0.0.1`<br/>
`pgPort` : `5432`<br/>
`pgUser` : `postgres`<br/>
`pgPasswd` : Set this one to your postgres password<br/>
`wsIPAddr` : `127.0.0.1`<br/>
`wsPort` : `3000`<br/>

After those variables have been set, hit OK and OK to apply the changes. If vscode is open at this point, you will need to close all instances and reopen it.

From there you should be good to start working.

## Quick Tour

Maven and Spring wrap everything in a lot of project files which can make things confusing. To simplify things, most coding will be done in the directory `src/main/java/info/hfdb/hfdbapi/`.
Here there exists the file `HfdbApiApplication.java` where the main method lives and the package `Controller` which contains some example code I (Justin) have provided, particularly the classes `HFDBAPI.java` and `Status.java`.

Opening `HFDBAPI.java` reveals a class with the annotation `@RestController` and a couple of functions with the annotation `@RequestMapping("/somePath")`.
Basically when Strings and primatives are returned, they are returned as Text, however when Objects are returned, as in the case of the function, `getStatus()`,
it returns the class that has been serialized in JSON.

## Running/Debugging

Hitting F5 starts up the project and reveal the debug menu at the top that'll allow you to pause, Step over/into/out, restart, and stop execution where applicable.
Since the project will contain functions with the annotation `@RequestMapping("/somePath")`, it'll respond to the according HTTP requests. The default IP is `localhost:8080`
so when you hit F5 and start the project and navigate to `localhost:8080/somePath`, the project will respond to the HTTP requests as defined by the RequestMapping functions.

## FAQ

Q: When I load up the project in vscode after cloning the repo, vscode is throwing errors such as `Unbound classpath container`,<br/> `The type java.lang.Object cannot be resolved`, and numerous other errors.

A: At this time, the best solution for this is to backup your data and reinstall windows from scratch. Nathan had this issue when he first cloned the repo. Justin went through and performed a clean install of Windows 10 in a VM following the instructions as described in the setup and couldn't reproduce the issue. If anyone encounters this issue, they're welcome to attempt to fix it theirself, (if they successfully do so, great! Report it to Justin so this can be updated.) but deadlines will not be adjusted to work around doing so.
