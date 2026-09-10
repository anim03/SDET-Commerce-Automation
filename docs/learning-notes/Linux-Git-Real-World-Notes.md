# Linux & Git — Real-World Notes for SDET

> Practical Linux, Terminal, Git and GitHub notes built while developing the **SDET-Commerce-Automation** project.

These notes are designed for:

- Real project work
- SDET / Automation Engineer interviews
- CI/CD understanding
- Troubleshooting
- GitHub portfolio development
- Linux-based test execution
- Docker and cloud environments

The objective is not to memorize commands.

The objective is to understand:

**What does the command do?  
Why do we need it?  
When would an SDET use it?  
What actually happened when we used it in our project?**

---

# SECTION 1 — WHY DOES AN SDET NEED LINUX?

## 1. What is Linux?

Linux is an operating system family widely used for:

- Servers
- Cloud environments
- Docker containers
- CI/CD runners
- Development environments
- Test environments
- Production systems

A large percentage of backend applications eventually execute in a Linux-based environment.

Even if an SDET develops tests on macOS or Windows, the tests may execute on:

```text
Developer Machine
        ↓
Git Repository
        ↓
CI/CD Pipeline
        ↓
Linux Runner
        ↓
Application / Tests
        ↓
Docker / Cloud
```

Therefore, Linux knowledge is highly valuable for an SDET.

### Interview Answer

> "As an SDET, I use Linux and command-line tools for test execution, environment configuration, log analysis, process troubleshooting, file management, Docker operations and CI/CD workflows. I focus on practical Linux skills that help me diagnose automation and environment failures."

---

# SECTION 2 — TERMINAL, SHELL AND COMMAND LINE

## 2. What is a Terminal?

A terminal is an interface through which we interact with the operating system using commands.

Instead of clicking folders manually, we can execute:

```bash
cd backend
```

Instead of opening a folder graphically, the shell changes our current working directory.

---

## 3. What is a Shell?

The terminal is the interface.

The **shell** interprets the commands entered into the terminal.

Examples:

```text
bash
zsh
sh
```

On our Mac, we are using:

```text
zsh
```

This is why some errors appeared as:

```text
zsh: command not found
```

The shell receives the command, interprets it and attempts to execute the requested program.

Conceptually:

```text
User
 ↓
Terminal
 ↓
Shell
 ↓
Operating System
 ↓
Program
```

---

# SECTION 3 — UNDERSTANDING THE COMMAND PROMPT

## 4. Our Project Prompt

During the project we frequently saw something similar to:

```text
SDET-Commerce-Automation git:(main)
```

This tells us important information.

```text
SDET-Commerce-Automation
```

means we are currently inside our project directory.

```text
git:(main)
```

means the directory is a Git repository and the current branch is:

```text
main
```

Therefore, before running commands, always understand:

```text
Where am I?
Which repository am I in?
Which Git branch am I on?
```

---

# SECTION 4 — pwd

## 5. Print Working Directory

Command:

```bash
pwd
```

`pwd` means:

```text
Print Working Directory
```

Example:

```text
/Users/<username>/SDET-Commerce-Automation
```

This tells us our exact current location in the filesystem.

### Why is this useful?

Suppose we accidentally run:

```bash
rm some-file
```

from the wrong directory.

We may delete the wrong file.

Before important operations, checking:

```bash
pwd
```

can prevent mistakes.

### SDET Usage

Useful when:

- running Maven tests
- executing shell scripts
- troubleshooting relative paths
- working with CI scripts
- managing reports
- navigating repositories

---

# SECTION 5 — cd

## 6. Change Directory

`cd` means:

```text
Change Directory
```

Example:

```bash
cd backend
```

Now the current directory becomes:

```text
backend
```

Return one level:

```bash
cd ..
```

Go to home directory:

```bash
cd ~
```

Go directly to our project:

```bash
cd ~/SDET-Commerce-Automation
```

Go directly to API automation:

```bash
cd ~/SDET-Commerce-Automation/api-automation
```

### Absolute vs Relative Path

Relative path:

```bash
cd backend
```

means:

> Enter the `backend` directory relative to my current location.

Absolute/home-based path:

```bash
cd ~/SDET-Commerce-Automation/backend
```

works regardless of where we currently are.

This is useful in scripts and troubleshooting.

---

# SECTION 6 — ls

## 7. Listing Files and Directories

Command:

```bash
ls
```

Displays files/directories in the current location.

Example:

```text
README.md
api-automation
backend
docker-compose.yml
docs
```

Detailed listing:

```bash
ls -l
```

Shows information such as:

- permissions
- owner
- size
- modification date
- filename

Show hidden files:

```bash
ls -a
```

This is important because files beginning with `.` are normally hidden.

Examples:

```text
.git
.gitignore
.env
.env.example
```

Detailed + hidden:

```bash
ls -la
```

This is one of the most useful combinations.

### Real Project Example

To check whether an environment file exists:

```bash
ls -la backend
```

or:

```bash
ls -la api-automation
```

---

# SECTION 7 — HIDDEN FILES

## 8. Why does `.env` start with a dot?

On Unix-like systems, filenames beginning with:

```text
.
```

are treated as hidden files.

Examples:

```text
.env
.gitignore
.git
```

They are not necessarily secret.

**Hidden does NOT mean secure.**

For example:

```text
.env
```

may contain credentials.

Its security comes from:

- correct filesystem handling
- `.gitignore`
- not committing it
- secure secret management

not merely because its filename starts with a dot.

---

# SECTION 8 — mkdir

## 9. Creating Directories

Command:

```bash
mkdir frontend
```

creates:

```text
frontend/
```

Nested directories can be created using:

```bash
mkdir -p docs/learning-notes
```

`-p` allows creation of parent directories when necessary.

### Future Project Example

When we start the frontend, we may eventually have:

```text
frontend/
```

although tools such as Vite can generate the application structure for us.

---

# SECTION 9 — touch

## 10. Creating an Empty File

Command:

```bash
touch file.txt
```

If the file does not exist, it creates it.

We used the same concept for our notes:

```bash
touch docs/learning-notes/Linux-Git-Real-World-Notes.md
```

The file could then be opened in VS Code:

```bash
code docs/learning-notes/Linux-Git-Real-World-Notes.md
```

### Important

`touch` does not normally add meaningful content.

It creates the file or updates its timestamp.

---

# SECTION 10 — cp

## 11. Copying Files

`cp` means:

```text
copy
```

Syntax:

```bash
cp source destination
```

During our project, before replacing the large learning-notes file, we created a backup:

```bash
cp docs/learning-notes/SDET-Learning-Notes.md \
docs/learning-notes/SDET-Learning-Notes-OLD-BACKUP.md
```

Meaning:

```text
Original file
     ↓ copy
Backup file
```

The original remained available while we edited the working version.

### Why was this useful?

We were replacing thousands of lines of documentation.

Creating a backup reduced the risk of accidentally losing the previous notes.

---

# SECTION 11 — mv

## 12. Moving and Renaming Files

`mv` means:

```text
move
```

Example:

```bash
mv old.txt new.txt
```

This effectively renames the file.

It can also move a file:

```bash
mv file.txt ~/Desktop/
```

### Real Project Use

We created an old notes backup inside the Git repository.

Before publishing the repository, a backup file should not accidentally become part of the professional source repository.

A safe approach is:

```bash
mv docs/learning-notes/SDET-Learning-Notes-OLD-BACKUP.md \
~/Desktop/SDET-Learning-Notes-OLD-BACKUP.md
```

Now:

```text
Repository
   ↓
backup removed from repo workspace

Desktop
   ↓
backup retained locally
```

This demonstrates an important Git principle:

> Not every local file belongs in source control.

---

# SECTION 12 — rm

## 13. Removing Files

Command:

```bash
rm file.txt
```

deletes a file.

Remove a directory recursively:

```bash
rm -r directory
```

Force recursive removal:

```bash
rm -rf directory
```

### VERY IMPORTANT

```bash
rm -rf
```

is powerful and dangerous.

It can recursively delete files without normal confirmation.

Never blindly copy an `rm -rf` command.

Before destructive operations, check:

```bash
pwd
ls
```

### SDET Rule

For cleanup scripts, understand exactly:

```text
WHAT is being deleted
WHERE it is being deleted
WHY it is safe
```

---

# SECTION 13 — cat

## 14. Reading File Content

Command:

```bash
cat filename
```

Example:

```bash
cat backend/.env.example
```

This prints the file content directly in the terminal.

Useful for small files such as:

```text
configuration
JSON
properties
scripts
small logs
```

For huge files, `cat` can produce too much terminal output.

In that situation use:

```text
head
tail
less
grep
```

---

# SECTION 14 — head

## 15. Reading the Beginning of a File

Command:

```bash
head README.md
```

shows the beginning of the file.

Specify line count:

```bash
head -25 README.md
```

### Real Project Usage

We used:

```bash
head -25 README.md
```

to verify the first part of our README before publishing the repository.

This is a practical example of validating documentation from the terminal.

---

# SECTION 15 — tail

## 16. Reading the End of a File

Command:

```bash
tail README.md
```

Show last 25 lines:

```bash
tail -25 README.md
```

### Real Project Usage

While building our learning notes, we used commands such as:

```bash
tail -25 docs/learning-notes/SDET-Learning-Notes.md
```

This allowed us to verify that the latest section had been appended correctly.

### Log Monitoring

A very important Linux usage is:

```bash
tail -f application.log
```

`-f` means:

```text
follow
```

Instead of exiting after showing the end of the file, the terminal continues displaying new lines as they are written.

This is extremely useful for:

```text
application logs
test execution logs
server troubleshooting
CI/CD debugging
```

---

# SECTION 16 — wc

## 17. Counting Lines

`wc` means:

```text
word count
```

Count lines:

```bash
wc -l README.md
```

### Real Project Example

We ran:

```bash
wc -l README.md
```

and verified that our README contained hundreds of lines.

Similarly:

```bash
wc -l docs/learning-notes/SDET-Learning-Notes.md
```

helped us understand the size of our detailed project documentation.

Useful options include:

```bash
wc -l file
wc -w file
wc -c file
```

where:

```text
-l → lines
-w → words
-c → bytes
```

---

# SECTION 17 — grep

## 18. Searching Text

`grep` is one of the most important Linux/Unix command-line tools for an SDET.

Basic example:

```bash
grep "JWT" README.md
```

This searches for:

```text
JWT
```

inside the file.

Show line numbers:

```bash
grep -n "JWT" README.md
```

Ignore case:

```bash
grep -i "jwt" README.md
```

Recursive search:

```bash
grep -R "JWT" .
```

Common combinations:

```bash
grep -RIn "JWT" .
```

Conceptually:

```text
R → recursive
I → ignore binary files
n → show line numbers
```

---

# SECTION 18 — REGULAR EXPRESSIONS WITH grep

## 19. Extended Regex Search

We used:

```bash
grep -nE
```

`-E` enables extended regular expressions.

Example from our README verification:

```bash
grep -nE \
'git (status|add|commit|push|remote|diff|log|check-ignore|grep|branch)' \
docs/learning-notes/SDET-Learning-Notes.md
```

This allows one search expression to match several possibilities:

```text
git status
git add
git commit
git push
git remote
git diff
...
```

This was useful when we wanted to determine:

> Have we already documented these Git commands?

That is a real SDET-style use of terminal search.

---

# SECTION 19 — PIPE |

## 20. What is a Pipe?

The pipe operator is:

```bash
|
```

It sends the output of one command into another command.

Example:

```bash
git status --short --untracked-files=all | grep ".env"
```

Conceptually:

```text
git status output
       ↓
       |
       ↓
grep filter
       ↓
matching lines only
```

Another example:

```bash
grep -n "git" notes.md | head -20
```

Meaning:

```text
Search for "git"
        ↓
send matches to head
        ↓
show only first 20
```

### Why is this important?

Linux commands become much more powerful when combined.

Instead of creating one huge command for every task, Unix tools are often designed to perform small operations that can be connected together.

---

# SECTION 20 — OUTPUT REDIRECTION

## 21. `>` vs `>>`

These two operators are extremely important.

### `>`

Writes output to a file and replaces existing content.

Example:

```bash
echo "hello" > file.txt
```

If `file.txt` already contained data, that content is overwritten.

### `>>`

Appends output to the end of a file.

```bash
echo "hello" >> file.txt
```

Existing content remains.

New content is added at the bottom.

### Real Project Example

We intentionally emptied the old learning-notes file using:

```bash
> docs/learning-notes/SDET-Learning-Notes.md
```

This truncates the file to zero bytes.

That is why we created a backup first.

### Important

Never casually run:

```bash
> important-file
```

because it can erase the existing contents immediately.

---

# SECTION 21 — COMMAND CHAINING

## 22. `&&`

Example:

```bash
cd backend && ./mvnw test
```

Meaning:

```text
Change directory to backend
        ↓
ONLY IF SUCCESSFUL
        ↓
run Maven tests
```

If the first command fails, the second command does not execute.

---

## 23. `||`

Example:

```bash
some-command || echo "Command failed"
```

Meaning:

```text
Run command
   ↓
if it fails
   ↓
run fallback command
```

We used this concept in security scans.

Example pattern:

```bash
grep ... || echo "PASS: No JWT tokens found"
```

If `grep` finds no matching JWT token, its non-zero exit status causes the PASS message to execute.

This introduces an important concept:

**Linux commands return exit codes.**

---

# SECTION 22 — EXIT CODES

## 24. What is an Exit Code?

Programs normally return a numeric exit status.

Common convention:

```text
0     → success
non-0 → some type of failure / non-success result
```

Check the previous command's status:

```bash
echo $?
```

Example:

```bash
ls README.md
echo $?
```

If the file exists, we normally expect:

```text
0
```

### Why does an SDET care?

CI/CD pipelines rely heavily on exit codes.

Conceptually:

```text
Tests execute
     ↓
Exit code 0
     ↓
Pipeline step passes
```

or:

```text
Tests execute
     ↓
Non-zero exit code
     ↓
Pipeline step fails
```

This is one reason command-line knowledge becomes important when moving from local automation to CI/CD.

---

# SECTION 23 — ENVIRONMENT VARIABLES

## 25. What is an Environment Variable?

An environment variable stores configuration outside the application source code.

Examples from our project:

```text
DB_URL
DB_USERNAME
DB_PASSWORD
JWT_SECRET
TEST_ENV
BASE_URL
TEST_EMAIL
TEST_PASSWORD
ADMIN_EMAIL
ADMIN_PASSWORD
```

Application/test code reads these values at runtime.

Instead of:

```java
String password = "actual-password";
```

we use configuration from the environment.

Conceptually:

```text
Environment
    ↓
Configuration
    ↓
Application / Test Framework
```

This improves:

- security
- portability
- environment switching
- CI/CD compatibility

---

# SECTION 24 — Reading Environment Variables

## 26. echo

Example:

```bash
echo $TEST_ENV
```

If:

```text
TEST_ENV=local
```

is exported, the output may be:

```text
local
```

### Security Warning

Do NOT casually execute:

```bash
echo $DB_PASSWORD
echo $JWT_SECRET
```

especially while:

- screen sharing
- recording demos
- collecting logs
- creating screenshots
- working in CI

The command itself may expose the secret.

---

# SECTION 25 — source

## 27. Loading Environment Configuration

We repeatedly used:

```bash
source .env
```

`source` executes commands from the specified file in the **current shell**.

Our project also used:

```bash
set -a
source .env
set +a
```

### What does this do?

```bash
set -a
```

automatically exports subsequently assigned variables.

Then:

```bash
source .env
```

loads the variables.

Then:

```bash
set +a
```

disables automatic exporting.

Conceptually:

```text
.env
 ↓
source
 ↓
shell environment
 ↓
Spring Boot / Maven / REST Assured
```

---

# SECTION 26 — OUR BACKEND START SCRIPT

## 28. Real Project Script

Our backend uses a helper script conceptually like:

```bash
#!/bin/bash

set -a
source "$(dirname "$0")/.env"
set +a

cd "$(dirname "$0")"

echo "Starting SDET Commerce backend..."

./mvnw spring-boot:run
```

This script teaches several Linux concepts at once.

### `#!/bin/bash`

This is called a:

```text
shebang
```

It tells the operating system which interpreter should execute the script.

### `dirname "$0"`

`$0` represents the script path/name.

`dirname` extracts its directory.

This allows the script to locate `.env` relative to itself instead of assuming where the user launched it from.

That makes the script more reliable.

---

# SECTION 27 — SHELL SCRIPT PERMISSIONS

## 29. chmod

A script may need executable permission.

Example:

```bash
chmod +x run-local.sh
```

Meaning:

```text
chmod → change mode
+x    → add executable permission
```

Then it can be executed as:

```bash
./run-local.sh
```

### Why `./`?

The shell does not automatically search the current directory for every executable.

Therefore:

```bash
./run-local.sh
```

means:

> Execute `run-local.sh` from the current directory.

---

# SECTION 28 — find

## 30. Finding Files

Basic example:

```bash
find . -name "*.backup"
```

Meaning:

```text
Start from current directory
        ↓
search recursively
        ↓
find files matching *.backup
```

### Real Project Cleanup

We used commands like:

```bash
find . -name "*.backup" -not -path "./.git/*" -delete
```

and:

```bash
find . -name "*.bak" -not -path "./.git/*" -delete
```

and:

```bash
find . -name ".DS_Store" -not -path "./.git/*" -delete
```

Breakdown:

```text
find .
```

search from current directory.

```text
-name "*.backup"
```

match backup files.

```text
-not -path "./.git/*"
```

do not operate inside Git's internal directory.

```text
-delete
```

delete matches.

### Important

Before using `-delete`, a safer verification pattern is:

```bash
find . -name "*.backup" -not -path "./.git/*"
```

Inspect results first.

Then, if correct, perform deletion.

This is a good engineering habit:

```text
Discover
   ↓
Verify
   ↓
Modify/Delete
```

---

# SECTION 29 — less

## 31. Viewing Large Output

For large files/output:

```bash
less filename
```

Unlike `cat`, `less` allows navigation.

Useful controls:

```text
q → quit
/ → search
n → next search result
```

Git also frequently opens output in a pager.

For example:

```bash
git log
```

may show:

```text
(END)
```

To exit:

```text
q
```

### Real Project Connection

We saw screens containing:

```text
(END)
```

while inspecting Git output.

That did not mean the terminal was frozen.

It meant the output was being displayed through a pager.

---

# SECTION 30 — COMMAND NOT FOUND

## 32. Understanding `command not found`

During our GitHub setup, we initially executed:

```bash
gh --version
```

and received:

```text
zsh: command not found: gh
```

This means the shell could not locate an executable named:

```text
gh
```

Possible reasons include:

```text
program not installed
PATH does not include program
incorrect command name
installation incomplete
```

In our case, GitHub CLI was not installed.

We installed it using Homebrew:

```bash
brew install gh
```

Then:

```bash
gh --version
```

worked.

This is a real troubleshooting pattern:

```text
Command fails
      ↓
Read exact error
      ↓
Identify missing dependency
      ↓
Install/configure it
      ↓
Verify installation
```

---

# SECTION 31 — which

## 33. Finding an Executable

Command:

```bash
which java
```

or:

```bash
which mvn
```

or:

```bash
which gh
```

This shows which executable will be used by the shell.

Useful when multiple versions are installed.

For example:

```bash
which java
java -version
```

together help answer:

```text
Which Java executable am I using?
Which Java version is it?
```

---

# SECTION 32 — PROCESS BASICS

## 34. What is a Process?

When a program executes, the operating system runs it as a process.

Examples:

```text
Spring Boot backend
PostgreSQL
Docker
Java
Maven
Browser
Test runner
```

Each running process receives a process identifier:

```text
PID
```

Understanding processes becomes important when:

```text
server does not stop
port is already occupied
test runner hangs
multiple application instances are running
```

---

# SECTION 33 — ps

## 35. Viewing Processes

A common command is:

```bash
ps
```

A broader view may use:

```bash
ps aux
```

To search for Java:

```bash
ps aux | grep java
```

Conceptually:

```text
all processes
      ↓
pipe
      ↓
only lines containing java
```

This can help investigate whether a Java application is still running.

---

# SECTION 34 — PORT TROUBLESHOOTING

## 36. lsof

A very useful macOS/Linux-style troubleshooting command is:

```bash
lsof -i :8080
```

This helps identify which process is using port:

```text
8080
```

Why does this matter?

Our Spring Boot backend normally runs on:

```text
localhost:8080
```

If another process already owns that port, the backend may fail to start.

Troubleshooting flow:

```text
Spring Boot says port 8080 already in use
                ↓
lsof -i :8080
                ↓
identify PID/process
                ↓
decide whether it should be stopped
```

Do not kill a process until you understand what it is.

---

# SECTION 35 — kill

## 37. Stopping a Process

After identifying the correct PID, a process can be sent a termination signal.

Example:

```bash
kill <PID>
```

A stronger signal sometimes used is:

```bash
kill -9 <PID>
```

But `kill -9` should not be the first choice.

It forcefully terminates the process and does not allow normal graceful shutdown handling.

Preferred thinking:

```text
Identify process
      ↓
Understand process
      ↓
Try graceful termination
      ↓
Force only when necessary
```

---

# SECTION 36 — DOCKER COMMANDS ARE ALSO PART OF OUR TERMINAL WORK

## 38. docker ps

We used:

```bash
docker ps
```

to display running containers.

To include stopped containers:

```bash
docker ps -a
```

Our PostgreSQL container is part of the application's local infrastructure.

Conceptually:

```text
Docker Desktop
      ↓
PostgreSQL Container
      ↓
Port 5432
      ↓
Spring Boot Backend
```

---

## 39. docker compose

Our project uses:

```bash
docker compose up -d
```

`up` starts services defined in:

```text
docker-compose.yml
```

`-d` means:

```text
detached mode
```

The containers continue running in the background.

Stop/remove Compose services:

```bash
docker compose down
```

---

## 40. docker logs

For container troubleshooting:

```bash
docker logs <container-name>
```

Follow logs:

```bash
docker logs -f <container-name>
```

This is another real-world SDET skill.

When a test fails because a dependency is unhealthy, the problem may not be the test.

We should investigate:

```text
test logs
application logs
container logs
database state
environment configuration
```

---

# SECTION 37 — DATABASE COMMAND EXECUTION THROUGH DOCKER

## 41. docker exec

We used a command similar to:

```bash
docker exec -it sdet-commerce-postgres \
psql -U sdetuser -d sdetcommerce
```

This means:

```text
docker exec
     ↓
execute command inside running container
```

`-i`:

```text
interactive input
```

`-t`:

```text
pseudo-terminal
```

Then:

```text
psql
```

opens PostgreSQL's command-line client.

This allowed us to inspect the actual database while testing the API.

That is a strong SDET debugging technique:

```text
API request
    ↓
API response
    ↓
database inspection
```

---

# SECTION 38 — REAL SDET TROUBLESHOOTING MINDSET

## 42. Don't Just Re-run Failed Tests

Suppose an automated test fails.

A weak troubleshooting approach is:

```text
Test failed
   ↓
rerun
   ↓
rerun
   ↓
add retry
```

A better SDET approach is:

```text
Test failed
     ↓
Read assertion/error
     ↓
Check request/response
     ↓
Check environment
     ↓
Check backend logs
     ↓
Check process/container
     ↓
Check database
     ↓
Identify actual failure layer
```

Linux and terminal knowledge make this investigation much faster.

---

# SECTION 39 — REAL COMMANDS USED SO FAR

## 43. Project Navigation

```bash
cd ~/SDET-Commerce-Automation
```

Backend:

```bash
cd ~/SDET-Commerce-Automation/backend
```

API automation:

```bash
cd ~/SDET-Commerce-Automation/api-automation
```

---

## 44. Backend Environment Loading

```bash
set -a
source .env
set +a
```

Then:

```bash
./mvnw clean test
```

---

## 45. Backend Startup

From project root:

```bash
./backend/run-local.sh
```

---

## 46. API Regression

```bash
cd ~/SDET-Commerce-Automation/api-automation
./run-tests-local.sh
```

Our completed milestone produced:

```text
Tests run: 48
Failures: 0
```

---

## 47. Documentation Verification

```bash
wc -l README.md
```

```bash
grep -n "^#" README.md
```

```bash
head -25 README.md
```

```bash
tail -25 README.md
```

These were not random Linux exercises.

We used them to validate the actual GitHub documentation before publishing it.

---

# SECTION 40 — LINUX INTERVIEW QUESTIONS

## 48. What Linux commands do you commonly use as an SDET?

### Interview Answer

> "I commonly use commands such as `cd`, `ls`, `pwd`, `find`, `grep`, `head`, `tail`, `wc`, `ps`, `lsof`, `kill` and environment-variable commands. I also work with shell scripts, Docker commands and command pipelines for test execution, log analysis, environment configuration and troubleshooting."

---

## 49. What is the difference between `>` and `>>`?

### Interview Answer

> "`>` redirects output and overwrites the target file, while `>>` appends output to the existing file. I use them carefully because using `>` against an existing file can replace its contents."

---

## 50. What is a pipe?

### Interview Answer

> "A pipe passes the standard output of one command as input to another command. For example, I can pipe `git status` into `grep` to filter only environment-related files."

Example:

```bash
git status --short | grep ".env"
```

---

## 51. How do you investigate logs?

### Interview Answer

> "For small logs I may use `cat`, while for large or continuously updating logs I use tools such as `tail`, `tail -f`, `grep` and `less`. I usually filter around timestamps, error messages, request identifiers or relevant service names."

---

## 52. How would you troubleshoot a port conflict?

### Interview Answer

> "I first identify the process using the port, for example with `lsof -i :8080`. I verify what the process is before terminating anything. If it is a stale application process, I stop it gracefully and then restart the required service."

---

## 53. Why are environment variables useful?

### Interview Answer

> "Environment variables separate environment-specific configuration from source code. They allow the same application or automation framework to run against different environments while avoiding hardcoded credentials and URLs."

---

# SECTION 41 — PART 1 QUICK CHEAT SHEET

```text
pwd
→ show current directory

cd
→ change directory

ls
→ list files

ls -la
→ detailed list including hidden files

mkdir
→ create directory

touch
→ create empty file/update timestamp

cp
→ copy

mv
→ move/rename

rm
→ remove

cat
→ print file

head
→ beginning of file

tail
→ end of file

tail -f
→ continuously follow file

wc -l
→ count lines

grep
→ search text

find
→ find files/directories

|
→ pipe output

>
→ redirect/overwrite

>>
→ redirect/append

&&
→ execute next command if previous succeeds

||
→ execute next command if previous fails

source
→ execute/load file in current shell

chmod +x
→ add executable permission

which
→ locate executable

ps
→ inspect processes

lsof -i :PORT
→ identify process using a port

kill
→ send signal to process

docker ps
→ running containers

docker ps -a
→ all containers

docker logs
→ container logs

docker exec
→ execute command inside container
```

---

# SECTION 42 — REMEMBER THIS

Linux knowledge for an SDET is not about memorizing hundreds of commands.

The important skill is being able to answer:

```text
Where am I?
What files are here?
What process is running?
What is using this port?
Where are the logs?
What environment variables are loaded?
What does this error mean?
What changed?
How can I filter the output?
How can I prove the actual application state?
```

That is the practical Linux mindset.

---

# END OF LINUX FOUNDATION — PART 1

Next:

**Git from zero → working tree → staging area → commits → branches → diff → history → .gitignore → remotes → GitHub → authentication → our actual 108-file milestone → first GitHub push.**

---

# GIT PART 2 — FROM ZERO TO REAL PROJECT PUSH

# SECTION 43 — WHAT IS GIT?

## 54. Git Basics

Git is a distributed version control system.

It helps us track:

```text
What changed?
Who changed it?
When did it change?
Why did it change?
Can I go back?
Can multiple developers work safely?
```

Without Git, developers may create files like:

```text
project-final
project-final-2
project-final-latest
project-final-latest-final
```

Git solves this problem through structured history.

---

# SECTION 44 — GIT VS GITHUB

## 55. Git and GitHub Are Not the Same

```text
Git
→ Version control system

GitHub
→ Online platform that hosts Git repositories
```

Git can work completely locally.

Example:

```text
Local Project
     ↓
git init
     ↓
Git Repository
```

GitHub is only needed when we want:

```text
Remote backup
Collaboration
Pull requests
Portfolio visibility
CI/CD integration
```

---

# SECTION 45 — WHAT IS A REPOSITORY?

## 56. Git Repository

A repository is a project tracked by Git.

Inside a Git repository there is a hidden directory:

```text
.git/
```

That directory stores Git's internal metadata and history.

You can usually verify hidden files using:

```bash
ls -la
```

Important:

```text
Do not manually modify .git/
```

unless you know exactly what you are doing.

---

# SECTION 46 — INITIALIZING GIT

## 57. git init

A new local repository can be created using:

```bash
git init
```

This creates:

```text
.git/
```

and turns the directory into a Git repository.

### Important

In our current project, Git was already initialized before the final GitHub push.

Therefore we did NOT need to run:

```bash
git init
```

again.

This is why we avoided blindly copying all commands shown by GitHub after creating the remote repository.

### Real Engineering Principle

Before running initialization commands:

```text
Understand current repository state first.
```

---

# SECTION 47 — GIT STATUS

## 58. git status

One of the most important Git commands:

```bash
git status
```

It answers:

```text
Which branch am I on?
Which files changed?
Which files are staged?
Which files are untracked?
Is the working tree clean?
```

Short version:

```bash
git status --short
```

Detailed untracked files:

```bash
git status --short --untracked-files=all
```

We used:

```bash
git status --short --untracked-files=all
```

before our major commit.

---

# SECTION 48 — UNDERSTANDING M AND ??

## 59. Modified Files

Example:

```text
 M README.md
```

Means:

```text
README.md is already tracked by Git
but its working copy has changed.
```

---

## 60. Untracked Files

Example:

```text
?? api-automation/pom.xml
```

Means:

```text
The file exists locally
but Git is not tracking it yet.
```

This is normal for newly created files.

---

# SECTION 49 — THE THREE IMPORTANT AREAS IN GIT

## 61. Working Directory

This is where we edit files.

Example:

```text
README.md
backend/
api-automation/
docs/
```

After modification:

```text
Working Directory contains changes.
```

---

## 62. Staging Area

The staging area contains changes selected for the next commit.

Command:

```bash
git add .
```

or a specific file:

```bash
git add README.md
```

---

## 63. Repository / Commit History

Once staged changes are committed:

```bash
git commit -m "message"
```

Git records a snapshot.

Flow:

```text
Working Directory
      ↓
git add
      ↓
Staging Area
      ↓
git commit
      ↓
Local Repository History
```

---

# SECTION 50 — WHY STAGING EXISTS

## 64. Why Not Commit Everything Automatically?

Because we may have multiple unrelated changes.

Example:

```text
README update
API framework changes
temporary debug file
unfinished frontend
```

Staging lets us decide:

```text
What belongs in this commit?
```

This helps create clean commit history.

---

# SECTION 51 — git add

## 65. Stage a Specific File

```bash
git add README.md
```

---

## 66. Stage All Current Changes

```bash
git add .
```

We intentionally delayed running:

```bash
git add .
```

until after repository cleanup and secret checks.

That is important.

### Bad Workflow

```text
Make changes
↓
git add .
↓
git commit
```

without review.

### Better Workflow

```text
Make changes
↓
git status
↓
Check .gitignore
↓
Check secrets
↓
Review files
↓
git add .
↓
Review staged content
↓
commit
```

---

# SECTION 52 — git diff

## 67. Unstaged Changes

Command:

```bash
git diff
```

Shows changes in tracked files that are not yet staged.

This helps answer:

```text
What did I modify?
```

---

# SECTION 53 — git diff --cached

## 68. Review Staged Changes

After:

```bash
git add .
```

we used:

```bash
git diff --cached
```

This shows:

```text
What exactly is going into the next commit?
```

This is extremely important before a large commit.

---

## 69. Staged Summary

We also used:

```bash
git diff --cached --stat
```

This gives a summary such as:

```text
108 files changed
26150 insertions(+)
2026 deletions(-)
```

This was our actual milestone summary.

It immediately told us:

```text
This is a large commit.
We should verify it carefully.
```

---

# SECTION 54 — LIST ONLY STAGED FILES

## 70. git diff --cached --name-only

Command:

```bash
git diff --cached --name-only
```

This prints only filenames.

We used this to verify staged environment files.

Example:

```bash
git diff --cached --name-only | grep -E '(^|/)\.env($|\.)'
```

Expected:

```text
api-automation/.env.example
backend/.env.example
```

Real `.env` files did not appear.

This was a direct security check before committing.

---

# SECTION 55 — .gitignore

## 71. What Is .gitignore?

`.gitignore` tells Git which untracked files should normally be ignored.

Examples from our project:

```text
.env
target/
allure-results/
node_modules/
.DS_Store
*.backup
```

---

## 72. Why .env Must Be Ignored

Real `.env` files may contain:

```text
Database username
Database password
JWT secret
Test credentials
Admin credentials
Environment URLs
```

These should never be committed to a public repository.

---

## 73. Why .env.example Should Be Committed

We want users to know which variables are required.

Therefore:

```text
.env
→ ignored

.env.example
→ committed
```

This gives us:

```text
Security + usability
```

---

# SECTION 56 — git check-ignore

## 74. Verify Ignore Rules

We used:

```bash
git check-ignore -v backend/.env api-automation/.env
```

This tells us:

```text
Is Git ignoring these files?
Which rule caused the ignore?
```

This is much better than assuming `.gitignore` works.

---

# SECTION 57 — IMPORTANT .gitignore BEHAVIOR

## 75. Already Tracked Files

A common misunderstanding:

```text
I added file to .gitignore
therefore Git stops tracking it.
```

Not necessarily.

If a file was already tracked before the ignore rule was added, `.gitignore` does not automatically remove it from Git history.

This is why repository hygiene should be designed early.

---

# SECTION 58 — SECRET SCANNING

## 76. Why Scan Before Commit?

`.gitignore` only protects certain files.

A secret could accidentally exist inside:

```text
Java code
README
Markdown
shell script
properties file
test code
```

Therefore we performed explicit secret scans.

---

## 77. JWT Pattern Scan

We used:

```bash
grep -RInE \
'eyJ[A-Za-z0-9_-]+\.[A-Za-z0-9_-]+\.[A-Za-z0-9_-]+' \
. \
--exclude-dir=.git \
--exclude-dir=target \
--exclude-dir=node_modules \
--exclude='.env'
```

This searches for JWT-like values.

One intentional fake test token was found:

```text
eyJhbGciOiJIUzI1NiJ9.invalid.signature
```

This was used for a negative security test and was safe.

Important lesson:

```text
Search result
≠
automatically a security leak
```

You still need to classify the result.

---

# SECTION 59 — PRIVATE KEY / SECRET SCANS

## 78. Private Key Scan

Example:

```bash
grep -RIn \
'BEGIN .*PRIVATE KEY' \
. \
--exclude-dir=.git \
--exclude-dir=target \
--exclude-dir=node_modules
```

Ideal:

```text
No matches
```

---

# SECTION 60 — SAFE STAGING CHECK

## 79. Check Sensitive Filenames

We used:

```bash
git diff --cached --name-only | \
grep -E '(^|/)\.env$|target/|allure-results/|allure-report/|\.DS_Store$|\.backup$|\.bak$'
```

If nothing unexpected appears, staging is safer.

This is a practical pre-commit quality gate.

---

# SECTION 61 — git commit

## 80. Creating a Commit

Command:

```bash
git commit -m "message"
```

Our actual milestone commit was:

```bash
git commit -m "feat: complete backend and API automation milestone"
```

Git created commit:

```text
cf23ac6
```

---

# SECTION 62 — WHAT IS A COMMIT?

## 81. Commit Concept

A commit is a snapshot of staged project state.

It contains metadata such as:

```text
Commit hash
Author
Timestamp
Message
Parent commit
Changed content
```

A commit becomes part of repository history.

---

# SECTION 63 — COMMIT HASH

## 82. cf23ac6

Our commit showed:

```text
cf23ac6
```

This is the short form of the commit hash.

The full hash is longer.

Git uses commit hashes to uniquely identify commits.

Example:

```bash
git log -1 --oneline
```

Output concept:

```text
cf23ac6 feat: complete backend and API automation milestone
```

---

# SECTION 64 — GOOD COMMIT MESSAGES

## 83. Why Commit Message Matters

Weak:

```text
changes
```

Better:

```text
fix tests
```

Stronger:

```text
feat: complete backend and API automation milestone
```

The message tells future readers:

```text
What type of change?
What milestone was completed?
```

---

# SECTION 65 — CONVENTIONAL COMMIT STYLE

## 84. Common Prefixes

Examples:

```text
feat:
fix:
docs:
test:
refactor:
chore:
ci:
```

Examples:

```text
feat: add order cancellation workflow

fix: return 403 for insufficient role

test: add payment database validation

docs: update project architecture

ci: add API regression workflow
```

This can make Git history easier to understand.

---

# SECTION 66 — git log

## 85. View History

Command:

```bash
git log
```

Compact:

```bash
git log --oneline
```

Latest commit:

```bash
git log -1 --oneline
```

We used this after committing to confirm the latest milestone.

---

# SECTION 67 — GIT PAGER

## 86. What Is `(END)`?

Commands such as:

```bash
git log
git diff
```

may open output in a pager.

At the bottom you may see:

```text
(END)
```

The terminal is not frozen.

Press:

```text
q
```

to quit.

We encountered this during our project verification.

---

# SECTION 68 — WORKING TREE CLEAN

## 87. After Commit

We ran:

```bash
git status
```

and received:

```text
nothing to commit, working tree clean
```

Meaning:

```text
All intended current changes are committed
No unstaged changes
No untracked non-ignored files
```

This was an important checkpoint before connecting GitHub.

---

# SECTION 69 — BRANCHES

## 88. What Is a Branch?

A branch is a movable reference to a line of development.

Our current branch:

```text
main
```

Prompt showed:

```text
git:(main)
```

---

## 89. List Branches

```bash
git branch
```

Current branch is normally marked with:

```text
*
```

Example:

```text
* main
```

---

# SECTION 70 — WHY BRANCHES MATTER

## 90. Real Development

Instead of making every change directly on `main`, professional teams often use:

```text
main
  ↓
feature/product-search
feature/payment
fix/order-cancellation
```

Developers work independently and merge reviewed work later.

Our first milestone was developed directly on local `main`.

Later, for portfolio improvement and CI/CD practice, feature branches can be used.

---

# SECTION 71 — REMOTE REPOSITORY

## 91. What Is a Remote?

A remote is a named reference to another Git repository.

Usually:

```text
origin
```

refers to the primary remote repository.

Example:

```text
Local Repository
      ↓
origin
      ↓
GitHub Repository
```

---

# SECTION 72 — git remote -v

## 92. Check Existing Remotes

Before connecting GitHub we ran:

```bash
git remote -v
```

Initially it returned:

```text
no output
```

Meaning:

```text
No remote repository was configured.
```

This is why `git push` could not yet know where to send the project.

---

# SECTION 73 — CREATING GITHUB REPOSITORY

## 93. Important Decision

We created a new empty GitHub repository named:

```text
SDET-Commerce-Automation
```

We intentionally did NOT ask GitHub to initialize:

```text
README
.gitignore
License
```

because these files already existed locally.

Otherwise we could have created unnecessary remote history and merge complications.

---

# SECTION 74 — CONNECTING ORIGIN

## 94. git remote add origin

We connected the local repository to GitHub using:

```bash
git remote add origin https://github.com/<username>/SDET-Commerce-Automation.git
```

Conceptually:

```text
origin
=
nickname for GitHub repository URL
```

---

## 95. Verify Remote

Then:

```bash
git remote -v
```

showed both:

```text
origin ... (fetch)
origin ... (push)
```

Meaning Git knew where to:

```text
download from
upload to
```

---

# SECTION 75 — FETCH VS PUSH URL

## 96. Fetch

Fetch direction:

```text
GitHub
  ↓
Local
```

Used for retrieving remote changes.

---

## 97. Push

Push direction:

```text
Local
  ↓
GitHub
```

Used for publishing local commits.

---

# SECTION 76 — FIRST PUSH AUTHENTICATION ISSUE

## 98. What Happened?

We attempted:

```bash
git push -u origin main
```

Git asked:

```text
Username for 'https://github.com':
```

Then requested authentication.

Modern GitHub HTTPS Git operations do not use the normal account password as Git credentials.

We avoided entering the normal GitHub password.

---

# SECTION 77 — GITHUB CLI

## 99. What Is gh?

`gh` is the GitHub Command Line Interface.

It allows terminal interaction with GitHub.

Examples:

```bash
gh auth login
gh repo view
gh pr create
```

Initially:

```bash
gh --version
```

returned:

```text
zsh: command not found: gh
```

Meaning the CLI was not installed.

---

# SECTION 78 — INSTALLING gh

## 100. Homebrew Installation

We installed GitHub CLI using:

```bash
brew install gh
```

Then verified:

```bash
gh --version
```

The CLI was successfully available.

This is an important real-life dependency troubleshooting example.

---

# SECTION 79 — gh auth login

## 101. Browser Authentication

We ran:

```bash
gh auth login
```

and selected:

```text
GitHub.com
HTTPS
Authenticate Git with GitHub credentials
Login with a web browser
```

The browser then authorized GitHub CLI.

Important:

```text
One-time codes and tokens should not be shared publicly.
```

---

# SECTION 80 — gh auth status

## 102. Verify Authentication

Command:

```bash
gh auth status
```

confirmed that GitHub CLI was authenticated.

This is better than assuming browser authentication succeeded.

Verification is a recurring engineering principle:

```text
Configure
   ↓
Verify
```

---

# SECTION 81 — FIRST SUCCESSFUL PUSH

## 103. Actual Command

After authentication:

```bash
git push -u origin main
```

completed successfully.

Important output:

```text
[new branch] main -> main
branch 'main' set up to track 'origin/main'
```

This meant:

```text
Local main
     ↓
pushed to
     ↓
Remote main
```

and upstream tracking was configured.

---

# SECTION 82 — WHAT DOES -u MEAN?

## 104. Upstream Tracking

Command:

```bash
git push -u origin main
```

contains:

```text
-u
```

which is shorthand for setting upstream tracking.

After this:

```text
local main
```

tracks:

```text
origin/main
```

Therefore future pushes can usually be:

```bash
git push
```

instead of:

```bash
git push origin main
```

---

# SECTION 83 — NORMAL FUTURE WORKFLOW

## 105. After Upstream Is Configured

Typical flow:

```bash
git status

git add .

git commit -m "message"

git push
```

But a better professional workflow is:

```text
Review
→ Stage
→ Review staged diff
→ Commit
→ Push
```

---

# SECTION 84 — REAL PROJECT GIT FLOW

## 106. Our Exact Milestone Flow

```text
Project Development
        ↓
Backend + API Automation Completed
        ↓
48/48 API Regression Passed
        ↓
README Updated
        ↓
Learning Notes Updated
        ↓
Backup Moved Outside Repository
        ↓
.env Ignore Verification
        ↓
Generated File Check
        ↓
Secret/JWT Scan
        ↓
git status Review
        ↓
git add .
        ↓
108 Files Staged
        ↓
git diff --cached --stat
        ↓
Staged Secret Check
        ↓
git commit
        ↓
cf23ac6
        ↓
Working Tree Clean
        ↓
GitHub Repository Created
        ↓
origin Configured
        ↓
GitHub CLI Authentication
        ↓
git push -u origin main
        ↓
GitHub Portfolio Project Live
```

---

# SECTION 85 — WHY 108 FILES WAS NOT AUTOMATICALLY A PROBLEM

## 107. Large Commit Context

We staged:

```text
108 files
```

A large number alone does not mean something is wrong.

The important questions were:

```text
Are these expected files?
Are secrets included?
Are build outputs included?
Are temporary files included?
Does the commit represent one meaningful milestone?
```

In our case the files represented a large accumulated milestone:

```text
Backend expansion
API automation
Schemas
Configuration
Documentation
Security
Reporting
```

Therefore we reviewed it carefully before committing.

---

# SECTION 86 — REAL PRE-COMMIT CHECKLIST

## 108. Commands We Used

Repository status:

```bash
git status --short --untracked-files=all
```

Check ignored `.env`:

```bash
git check-ignore -v backend/.env api-automation/.env
```

Check staged summary:

```bash
git diff --cached --stat
```

Check staged filenames:

```bash
git diff --cached --name-only
```

Check `.env` candidates:

```bash
git diff --cached --name-only | grep -E '(^|/)\.env($|\.)'
```

Expected only:

```text
api-automation/.env.example
backend/.env.example
```

---

# SECTION 87 — GIT SAFETY MINDSET

## 109. Never Blindly Run Git Commands

Dangerous pattern:

```text
Copy random command
↓
Run it
↓
Understand later
```

Better:

```text
Understand current state
↓
Understand command
↓
Predict expected result
↓
Run command
↓
Verify actual result
```

This is exactly how we approached the first GitHub push.

---

# SECTION 88 — REAL MISTAKE: EXECUTING OUTPUT AS COMMAND

## 110. What Happened?

During staged env verification, expected output lines such as:

```text
api-automation/.env.example
backend/.env.example
```

were accidentally pasted into the terminal as commands.

Shell returned:

```text
zsh: permission denied
```

This was not a project issue.

The shell simply tried to execute the filename.

### Lesson

Understand whether a block represents:

```text
COMMAND
```

or:

```text
EXPECTED OUTPUT
```

This is important while following technical documentation.

---

# SECTION 89 — git fetch

## 111. What Is Fetch?

Command:

```bash
git fetch
```

downloads remote Git information but does not automatically merge it into the current branch.

Concept:

```text
Remote
  ↓
fetch
  ↓
Local remote-tracking references
```

Useful when you want to inspect remote changes safely before integrating them.

---

# SECTION 90 — git pull

## 112. What Is Pull?

Conceptually:

```text
git pull
=
fetch
+
integrate remote changes
```

A common workflow:

```bash
git pull
```

before beginning work can help synchronize local work with remote.

In team environments, the exact pull/rebase strategy should follow project standards.

---

# SECTION 91 — git clone

## 113. Cloning Existing Repository

If a repository already exists on GitHub, instead of:

```bash
git init
```

we normally use:

```bash
git clone <repository-url>
```

This creates:

```text
Local project directory
+
Git history
+
origin remote
```

Important distinction:

```text
New local project
→ git init

Existing remote project
→ git clone
```

---

# SECTION 92 — BRANCH CREATION

## 114. Create and Switch to a Branch

Modern command:

```bash
git switch -c feature/frontend
```

Older common form:

```bash
git checkout -b feature/frontend
```

For our next React phase, a future professional workflow could be:

```bash
git switch -c feature/react-frontend
```

Then implement frontend work independently from `main`.

---

# SECTION 93 — SWITCH BRANCH

## 115. git switch

Example:

```bash
git switch main
```

Switch to:

```text
main
```

Then:

```bash
git switch feature/react-frontend
```

returns to the feature branch.

---

# SECTION 94 — MERGE

## 116. What Is Merge?

Suppose:

```text
main
  \
   feature/react-frontend
```

After feature completion:

```bash
git switch main
git merge feature/react-frontend
```

Git integrates the feature branch into `main`.

In professional teams, this integration is often performed through a Pull Request rather than direct local merge.

---

# SECTION 95 — MERGE CONFLICT

## 117. What Is a Conflict?

A conflict can occur when two branches modify overlapping parts of the same file and Git cannot safely decide which version should win.

Example:

```text
main changes README line 20
feature changes README line 20
```

Git may require manual resolution.

---

# SECTION 96 — CONFLICT MARKERS

## 118. What They Look Like

A conflicted file may contain:

```text
<<<<<<< HEAD
current branch content
=======
incoming branch content
>>>>>>> feature-branch
```

We should:

```text
Understand both changes
↓
Create correct final content
↓
Remove conflict markers
↓
Stage resolved file
↓
Complete merge/rebase
```

Never randomly choose one side without understanding the requirement.

---

# SECTION 97 — REBASE

## 119. What Is Rebase?

Rebase moves/replays commits onto a different base.

Conceptually:

```text
main:       A---B---C
                 \
feature:          D---E
```

After rebasing feature onto latest main:

```text
main:       A---B---C
                     \
feature:              D'---E'
```

Rebase can produce a cleaner linear history.

But because it rewrites commit history, use it carefully—especially for shared branches.

---

# SECTION 98 — MERGE VS REBASE

## 120. Simple Comparison

```text
Merge
→ combines histories
→ preserves branch history

Rebase
→ replays commits on new base
→ can create linear history
→ rewrites commit hashes
```

Follow team convention.

Do not rebase public/shared commits casually.

---

# SECTION 99 — git stash

## 121. Temporary Work Storage

Suppose you have unfinished changes but need to switch branches.

Command:

```bash
git stash
```

Later:

```bash
git stash pop
```

Concept:

```text
Uncommitted changes
     ↓
temporary stash
     ↓
clean working tree
```

Stash is temporary—not a replacement for meaningful commits.

---

# SECTION 100 — git restore

## 122. Discard Working-Tree Change

Modern Git command:

```bash
git restore file.txt
```

This can replace current unstaged changes with the tracked version.

### Warning

This may discard work.

Always inspect:

```bash
git diff
```

before restoring.

---

# SECTION 101 — UNSTAGE A FILE

## 123. Restore From Staging Area

If a file was accidentally staged:

```bash
git restore --staged file.txt
```

This removes it from staging but keeps local changes.

Concept:

```text
Staged
 ↓
restore --staged
 ↓
Working directory change remains
```

---

# SECTION 102 — git revert

## 124. Safe History Undo

Command concept:

```bash
git revert <commit>
```

Revert creates a NEW commit that reverses an earlier commit.

This is useful for already-shared history.

It preserves history rather than rewriting it.

---

# SECTION 103 — git reset

## 125. Reset Is More Powerful

`git reset` can move branch pointers and alter staging state.

Examples vary:

```text
--soft
--mixed
--hard
```

Because reset can rewrite local history or discard work, use it carefully.

Especially:

```bash
git reset --hard
```

can permanently discard local modifications.

Do not use it casually.

---

# SECTION 104 — REVERT VS RESET

## 126. Important Interview Difference

```text
git revert
→ creates new undo commit
→ safer for shared history

git reset
→ moves branch/reference backward
→ may rewrite history
→ mainly safer for local/unshared work when understood
```

---

# SECTION 105 — git show

## 127. Inspect a Commit

Command:

```bash
git show <commit>
```

Example:

```bash
git show cf23ac6
```

Can display:

```text
Commit metadata
Message
Diff
```

Useful when reviewing what changed in a specific milestone.

---

# SECTION 106 — git log --oneline

## 128. Compact History

```bash
git log --oneline
```

Example concept:

```text
cf23ac6 feat: complete backend and API automation milestone
abc1234 earlier commit
```

This is useful for quickly understanding project history.

---

# SECTION 107 — HEAD

## 129. What Is HEAD?

`HEAD` usually points to the commit currently checked out.

Conceptually:

```text
HEAD
 ↓
main
 ↓
latest commit
```

If:

```text
HEAD -> main
```

appears in Git output, it means the current checked-out branch is `main`.

---

# SECTION 108 — origin/main

## 130. Local vs Remote Tracking Branch

```text
main
```

is the local branch.

```text
origin/main
```

represents our local knowledge of the remote `main` branch.

After successful push and synchronization:

```text
main
and
origin/main
```

may point to the same commit.

---

# SECTION 109 — AHEAD / BEHIND

## 131. Ahead

If local has commits not pushed:

```text
Your branch is ahead of 'origin/main' by 1 commit
```

Meaning:

```text
local has new commit
remote does not
```

Action may be:

```bash
git push
```

---

## 132. Behind

If remote has commits missing locally:

```text
Your branch is behind 'origin/main'
```

Then we should retrieve/integrate remote work using the team's Git workflow.

---

# SECTION 110 — GITHUB PULL REQUEST

## 133. What Is a Pull Request?

A Pull Request is a GitHub collaboration workflow for proposing changes.

Typical flow:

```text
main
 ↓
feature branch
 ↓
commit
 ↓
push branch
 ↓
Pull Request
 ↓
review
 ↓
checks
 ↓
merge
```

This will become useful when we start working with professional feature branches in this project.

---

# SECTION 111 — FUTURE FRONTEND BRANCH WORKFLOW

## 134. Example

For our React milestone, a clean future workflow could be:

```bash
git switch main

git pull

git switch -c feature/react-frontend
```

Develop frontend.

Then:

```bash
git status
git add .
git diff --cached
git commit -m "feat: add React commerce frontend"
git push -u origin feature/react-frontend
```

Then create a Pull Request.

Important:

This is a recommended future workflow—not something we have already completed.

---

# SECTION 112 — GIT INTERVIEW QUESTIONS

## 135. What is the difference between Git and GitHub?

> "Git is a distributed version control system that tracks source history locally, while GitHub is a remote hosting and collaboration platform built around Git repositories."

---

## 136. What is the staging area?

> "The staging area is an intermediate area where I select the exact changes that should be part of the next commit."

---

## 137. Difference between `git diff` and `git diff --cached`?

> "`git diff` shows unstaged tracked changes, while `git diff --cached` shows changes already staged for the next commit."

---

## 138. What does `git status` tell you?

> "It shows the current branch and the state of tracked, modified, staged and untracked files, as well as synchronization information with the upstream branch."

---

## 139. Why use `.gitignore`?

> "To prevent generated files, local configuration, secrets, IDE metadata and other non-source artifacts from being accidentally tracked."

---

## 140. Difference between merge and rebase?

> "Merge combines histories and preserves branch structure, while rebase replays commits on another base to create a more linear history. Rebase rewrites commit hashes, so I avoid casually rebasing shared history."

---

## 141. Difference between revert and reset?

> "`git revert` creates a new commit that reverses an earlier change and is safer for shared history. `git reset` moves repository references and can rewrite local history or discard changes depending on the mode."

---

## 142. What does `git push -u origin main` do?

> "It pushes the local main branch to the remote named origin and sets origin/main as the upstream tracking branch, allowing future pushes and pulls to use the configured tracking relationship."

---

## 143. How do you prevent secrets from reaching GitHub?

> "I externalize credentials through environment variables, ignore real `.env` files, commit only safe `.env.example` templates, inspect Git status and staged files, and scan the working tree for credential or token patterns before publishing."

---

# SECTION 113 — REAL-LIFE GIT CHEAT SHEET

## 144. Check Repository State

```bash
git status
```

Detailed:

```bash
git status --short --untracked-files=all
```

---

## 145. View Unstaged Changes

```bash
git diff
```

---

## 146. Stage

Specific:

```bash
git add README.md
```

Everything reviewed:

```bash
git add .
```

---

## 147. Review Staged Changes

```bash
git diff --cached
```

Summary:

```bash
git diff --cached --stat
```

Files:

```bash
git diff --cached --name-only
```

---

## 148. Unstage

```bash
git restore --staged <file>
```

---

## 149. Commit

```bash
git commit -m "feat: meaningful message"
```

---

## 150. Latest Commit

```bash
git log -1 --oneline
```

---

## 151. History

```bash
git log --oneline
```

---

## 152. Remote

```bash
git remote -v
```

Add:

```bash
git remote add origin <repository-url>
```

---

## 153. First Push

```bash
git push -u origin main
```

Later:

```bash
git push
```

---

## 154. Fetch

```bash
git fetch
```

---

## 155. Pull

```bash
git pull
```

---

## 156. Branch

```bash
git branch
```

Create:

```bash
git switch -c feature/example
```

Switch:

```bash
git switch main
```

---

## 157. Stash

```bash
git stash
git stash pop
```

---

## 158. Inspect Commit

```bash
git show <commit>
```

---

# SECTION 114 — OUR REAL FIRST GITHUB MILESTONE

## 159. Real Project Facts

Project:

```text
SDET-Commerce-Automation
```

Branch:

```text
main
```

Milestone commit:

```text
cf23ac6
```

Commit message:

```text
feat: complete backend and API automation milestone
```

Files changed:

```text
108
```

Final first push:

```bash
git push -u origin main
```

Result:

```text
main -> main
```

and:

```text
local main tracks origin/main
```

---

# SECTION 115 — COMPLETE REAL FLOW TO MEMORIZE

## 160. Our Git Journey

```text
git status
    ↓
review files
    ↓
verify .gitignore
    ↓
verify .env ignored
    ↓
scan secrets
    ↓
git add .
    ↓
git diff --cached --stat
    ↓
check staged filenames
    ↓
git commit
    ↓
git log
    ↓
git status
    ↓
create empty GitHub repo
    ↓
git remote add origin
    ↓
git remote -v
    ↓
authenticate GitHub CLI
    ↓
git push -u origin main
    ↓
refresh GitHub
    ↓
verify repository
```

---

# SECTION 116 — SENIOR SDET GIT MINDSET

## 161. Git Is Part of Quality Engineering

An SDET should not think:

```text
Git is only developer responsibility.
```

An automation engineer works with:

```text
Test framework changes
Feature branches
Pull requests
CI/CD
Code review
Merge conflicts
Release branches
Hotfixes
Regression pipelines
Configuration
```

All of these depend heavily on Git.

---

# SECTION 117 — DO NOT MEMORIZE COMMANDS WITHOUT STATE

## 162. Always Ask

Before Git operation:

```text
Which branch am I on?
What is modified?
What is staged?
What is untracked?
What is ignored?
What remote is configured?
Is my local branch ahead or behind?
What exactly will this command change?
```

This mindset prevents many Git mistakes.

---

# END OF GIT FOUNDATION — PART 2

Next Part:

**Advanced real-world Git + Linux troubleshooting for SDET: branches, merge conflicts, rebase recovery, stash, reset/revert scenarios, logs, permissions, networking, curl, environment troubleshooting, CI/CD Linux commands and production-style debugging.**

---

# PART 3 — REAL-WORLD LINUX & GIT TROUBLESHOOTING FOR SDET

# SECTION 118 — TROUBLESHOOTING MINDSET

## 163. Senior SDET Does Not Start With a Fix

When something fails, do not immediately:

```text
Restart everything
Delete files
Add retry
Force push
Kill processes
Change random configuration
```

First understand the failure.

A good troubleshooting flow is:

```text
Observe
   ↓
Collect Evidence
   ↓
Identify Failure Layer
   ↓
Form Hypothesis
   ↓
Verify Hypothesis
   ↓
Apply Smallest Safe Fix
   ↓
Retest
   ↓
Document Root Cause
```

For our project, possible failure layers include:

```text
UI
API
Spring Boot
Authentication
Database
Docker
Environment Variables
Test Framework
Operating System
Git
CI/CD
```

### Interview Answer

> "I troubleshoot failures layer by layer instead of immediately assuming the automation is defective. I first collect evidence from the assertion, request/response, logs, environment, application process and database, then isolate the actual failure layer before applying a fix."

---

# SECTION 119 — FILE PERMISSIONS

## 164. Understanding Linux Permissions

Run:

```bash
ls -l
```

You may see:

```text
-rwxr-xr-x
```

Permissions are grouped conceptually as:

```text
owner | group | others
```

Permission types:

```text
r → read
w → write
x → execute
```

Example:

```text
rwx
```

means:

```text
read + write + execute
```

---

# SECTION 120 — DIRECTORY PERMISSIONS

## 165. Permissions Behave Differently for Directories

For a directory:

```text
r → list directory contents
w → create/delete entries
x → enter/traverse directory
```

This distinction is useful when troubleshooting:

```text
Permission denied
```

---

# SECTION 121 — chmod

## 166. Making a Script Executable

Suppose:

```bash
./run-tests-local.sh
```

returns:

```text
permission denied
```

Check:

```bash
ls -l run-tests-local.sh
```

If executable permission is missing:

```bash
chmod +x run-tests-local.sh
```

Then:

```bash
./run-tests-local.sh
```

### Real Project Connection

Our project uses scripts such as:

```text
backend/run-local.sh
api-automation/run-tests-local.sh
```

These scripts need executable permission when invoked directly using `./`.

---

# SECTION 122 — NUMERIC PERMISSIONS

## 167. chmod 755

You may encounter:

```bash
chmod 755 script.sh
```

Numeric permissions are based on:

```text
read    = 4
write   = 2
execute = 1
```

Therefore:

```text
7 = 4 + 2 + 1 = rwx
5 = 4 + 1     = r-x
```

So:

```text
755
```

means:

```text
Owner  → rwx
Group  → r-x
Others → r-x
```

Do not blindly use:

```bash
chmod 777
```

just to fix permission issues.

`777` gives:

```text
read + write + execute
```

to everyone.

That is usually unnecessarily permissive.

### Security Principle

Use:

```text
least privilege
```

Give only the permissions actually required.

---

# SECTION 123 — chown

## 168. File Ownership

`chown` changes file ownership.

General form:

```bash
chown user file
```

or:

```bash
chown user:group file
```

You may need elevated privileges depending on the file.

### SDET Scenario

A CI process generates reports under a different user.

Later another process cannot modify them.

Do not immediately run:

```bash
chmod 777
```

First inspect:

```bash
ls -l
```

and determine:

```text
Who owns the file?
What permissions exist?
Which user is running the failing process?
```

---

# SECTION 124 — sudo

## 169. What Is sudo?

`sudo` allows an authorized user to execute a command with elevated privileges.

Example:

```bash
sudo <command>
```

### Important

Do not use `sudo` as the default solution for:

```text
Permission denied
```

because the real problem might be:

```text
wrong ownership
wrong directory
incorrect permissions
bad installation
incorrect user
```

### Senior Engineering Principle

Understand why elevated privilege is needed before using it.

---

# SECTION 125 — PROCESS TROUBLESHOOTING

## 170. Find Java Processes

Example:

```bash
ps aux | grep java
```

This combines:

```text
ps aux
→ list processes

|
→ pipe

grep java
→ filter Java-related lines
```

### Common Detail

The output may also contain:

```text
grep java
```

because the `grep` process itself contains the word `java` in its arguments.

A useful alternative is:

```bash
pgrep -fl java
```

when available.

---

# SECTION 126 — PORT ALREADY IN USE

## 171. Scenario

Spring Boot fails with something like:

```text
Port 8080 was already in use
```

Do not immediately change the application port.

First check:

```bash
lsof -i :8080
```

Possible output concept:

```text
COMMAND   PID   USER   ...
java      1234  user   ...
```

Now we know which process owns the port.

---

# SECTION 127 — GRACEFUL PROCESS TERMINATION

## 172. kill

First try:

```bash
kill 1234
```

Then verify:

```bash
lsof -i :8080
```

If the process is gone, the port is free.

Only when appropriate and normal termination fails might stronger termination be considered:

```bash
kill -9 1234
```

### Why Not Start With `kill -9`?

Because forceful termination does not allow normal cleanup.

Applications may need to:

```text
close connections
flush data
release resources
finish shutdown hooks
```

---

# SECTION 128 — BACKGROUND PROCESSES

## 173. `&`

A command can be started in the background using:

```bash
some-command &
```

This returns control to the shell while the process continues.

However, for our current backend we intentionally use normal foreground execution during development because application logs remain visible.

This helps debugging.

---

# SECTION 129 — ENVIRONMENT VARIABLE TROUBLESHOOTING

## 174. Scenario

Suppose Spring Boot reports:

```text
DB_USERNAME not configured
```

or:

```text
JWT_SECRET missing
```

Possible causes:

```text
.env not loaded
wrong .env location
variable misspelled
shell did not export variable
script launched incorrectly
```

First inspect variable existence safely.

For a non-secret variable:

```bash
echo "$TEST_ENV"
```

For a secret, avoid printing the value.

Use:

```bash
if [ -n "$JWT_SECRET" ]; then
  echo "JWT_SECRET is set"
else
  echo "JWT_SECRET is NOT set"
fi
```

This checks presence without exposing the secret.

---

# SECTION 130 — env AND printenv

## 175. Inspect Environment

Commands include:

```bash
env
```

and:

```bash
printenv
```

For a safe non-secret variable:

```bash
printenv TEST_ENV
```

### Security Warning

Do not dump the complete environment into:

```text
CI logs
screenshots
support tickets
public chat
```

because environment variables may contain credentials.

---

# SECTION 131 — export

## 176. Exporting Variables

Example:

```bash
export TEST_ENV=local
```

Now child processes started from the shell can receive that variable.

Check:

```bash
echo "$TEST_ENV"
```

Our scripts automate this process using:

```bash
set -a
source .env
set +a
```

---

# SECTION 132 — TEMPORARY ENVIRONMENT OVERRIDE

## 177. One-Command Environment Variable

You can also run:

```bash
TEST_ENV=qa ./run-tests-local.sh
```

This sets the variable for that command invocation.

This is useful for environment switching.

However, our framework also expects the corresponding environment URL configuration to exist.

---

# SECTION 133 — PATH

## 178. What Is PATH?

When we run:

```bash
java
mvn
gh
git
```

the shell searches directories listed in:

```bash
echo "$PATH"
```

If a program exists but its location is not in `PATH`, we may receive:

```text
command not found
```

---

# SECTION 134 — which + VERSION TROUBLESHOOTING

## 179. Java Example

Check executable:

```bash
which java
```

Then:

```bash
java -version
```

For Maven:

```bash
which mvn
mvn -version
```

For Git:

```bash
which git
git --version
```

For GitHub CLI:

```bash
which gh
gh --version
```

This is useful when:

```text
works on my machine
but fails on another machine
```

because tool versions may differ.

---

# SECTION 135 — NETWORKING BASICS FOR SDET

## 180. localhost

`localhost` refers to the current machine.

Common equivalent IP:

```text
127.0.0.1
```

Our backend runs locally using:

```text
http://localhost:8080
```

Our PostgreSQL database is exposed locally on:

```text
localhost:5432
```

---

# SECTION 136 — PORTS

## 181. What Is a Port?

An IP identifies a machine/interface.

A port helps identify a network service/process endpoint.

Example:

```text
localhost:8080
```

means:

```text
Host → localhost
Port → 8080
```

In our project:

```text
Spring Boot → 8080
PostgreSQL  → 5432
```

Later the React development server may run on another local port.

---

# SECTION 137 — curl

## 182. Why SDETs Should Know curl

`curl` allows HTTP requests directly from the terminal.

This is extremely useful for:

```text
API debugging
server health checks
authentication debugging
CI/CD checks
environment verification
```

Simple example:

```bash
curl http://localhost:8080/v3/api-docs
```

This can verify whether our OpenAPI endpoint is reachable.

---

# SECTION 138 — curl -i

## 183. Include Response Headers

```bash
curl -i http://localhost:8080/v3/api-docs
```

`-i` includes response headers.

Useful for checking:

```text
HTTP status
content type
headers
response body
```

---

# SECTION 139 — curl -v

## 184. Verbose Request

```bash
curl -v http://localhost:8080/v3/api-docs
```

Verbose mode can expose:

```text
connection details
request headers
response headers
protocol information
```

Useful for network debugging.

### Security Warning

Verbose output may expose sensitive headers if authentication is involved.

Do not publish raw verbose output containing tokens.

---

# SECTION 140 — curl WITH AUTHENTICATION

## 185. Bearer Token Concept

An authenticated API request can look like:

```bash
curl \
  -H "Authorization: Bearer <TOKEN>" \
  http://localhost:8080/api/products
```

### Important

Never commit or publish:

```text
real JWT
```

For documentation use:

```text
<TOKEN>
```

or:

```text
[REDACTED]
```

This is the same security principle we implemented in Allure reporting.

---

# SECTION 141 — HTTP TROUBLESHOOTING

## 186. Status Code Thinking

Common API responses:

```text
200 → successful request
201 → resource created
400 → invalid request
401 → authentication missing/invalid
403 → authenticated but forbidden
404 → resource not found
409 → business/state conflict
500 → server-side failure
```

Our project specifically demonstrated:

```text
401 → invalid/missing authentication
403 → valid user without ADMIN role
```

This distinction is important for security testing.

---

# SECTION 142 — DNS BASICS

## 187. Domain Name Resolution

Humans use names such as:

```text
example.com
```

Networks ultimately communicate using IP addresses.

DNS resolves:

```text
Domain Name
    ↓
IP Address
```

Useful diagnostic commands vary by system, but commonly include:

```bash
nslookup example.com
```

or:

```bash
dig example.com
```

If an API hostname cannot resolve, the test may fail before it ever reaches the application.

---

# SECTION 143 — ping

## 188. Basic Connectivity Check

Example:

```bash
ping example.com
```

This can help inspect basic network reachability.

But:

```text
ping failure does NOT automatically mean the website/API is down
```

because ICMP may be blocked.

Therefore use the correct diagnostic tool for the layer being tested.

For HTTP services:

```bash
curl
```

is often more relevant.

---

# SECTION 144 — LOG TROUBLESHOOTING

## 189. Search Errors

Example:

```bash
grep -i "error" application.log
```

Show line numbers:

```bash
grep -ni "error" application.log
```

Search multiple patterns:

```bash
grep -Ei "error|exception|failed" application.log
```

---

# SECTION 145 — LIVE LOGS

## 190. tail -f

```bash
tail -f application.log
```

Useful when reproducing an issue manually.

Flow:

```text
Start tail -f
      ↓
Perform API action
      ↓
Watch logs appear
      ↓
Correlate failure
```

Exit with:

```text
Ctrl + C
```

---

# SECTION 146 — Ctrl + C

## 191. Interrupting a Foreground Process

When Spring Boot or another command runs in the foreground, we often stop it with:

```text
Ctrl + C
```

This sends an interrupt signal.

This is generally preferable to opening another terminal and force-killing the process.

---

# SECTION 147 — DISK SPACE

## 192. df

Command:

```bash
df -h
```

Shows filesystem disk usage in human-readable form.

Useful when:

```text
CI runner is full
Docker cannot create files
reports cannot be generated
build suddenly fails
```

---

# SECTION 148 — DIRECTORY SIZE

## 193. du

Example:

```bash
du -sh .
```

Shows approximate size of the current directory.

Specific directory:

```bash
du -sh target
```

Useful for investigating:

```text
large build output
large reports
Docker-related storage
workspace growth
```

---

# SECTION 149 — MEMORY

## 194. Memory Troubleshooting

Linux environments commonly provide:

```bash
free -h
```

to inspect memory.

macOS differs, so commands are not identical across every Unix-like operating system.

Important interview principle:

> Linux and macOS share many Unix command-line concepts, but they are not the same operating system and some commands/options differ.

---

# SECTION 150 — OS INFORMATION

## 195. uname

Command:

```bash
uname -a
```

Provides system/kernel information.

Architecture:

```bash
uname -m
```

Our Mac environment is Apple Silicon, so architecture awareness matters when installing binaries or Docker images.

Common architectures include:

```text
arm64 / aarch64
x86_64 / amd64
```

---

# SECTION 151 — WHY ARCHITECTURE MATTERS

## 196. SDET Scenario

Suppose a tool has builds for:

```text
amd64
arm64
```

Installing the wrong binary can cause execution failures.

This can appear in:

```text
local setup
Docker
CI runners
cloud machines
browser drivers
native libraries
```

Always understand:

```text
OS + CPU architecture + runtime version
```

when debugging installation problems.

---

# SECTION 152 — GIT TROUBLESHOOTING STARTS WITH STATUS

## 197. Golden Command

When confused in Git:

```bash
git status
```

Do this before:

```text
reset
rebase
merge
restore
commit
push
```

Git often tells you exactly what state the repository is in.

---

# SECTION 153 — VISUALIZING GIT HISTORY

## 198. Useful Command

```bash
git log --oneline --graph --decorate --all
```

This helps visualize:

```text
branches
commits
HEAD
remote branches
merge structure
```

Example concept:

```text
* abc123 (HEAD -> feature/frontend)
* def456
| * 789abc (origin/main, main)
|/
* 123def
```

This is extremely useful when branch history becomes confusing.

---

# SECTION 154 — ACCIDENTALLY MODIFIED A FILE

## 199. Scenario

You changed:

```text
README.md
```

but want to discard the unstaged modification.

First inspect:

```bash
git diff README.md
```

If you are certain the changes are not needed:

```bash
git restore README.md
```

### Warning

The local unstaged modification is discarded.

Do not run this blindly.

---

# SECTION 155 — ACCIDENTALLY STAGED A FILE

## 200. Scenario

You ran:

```bash
git add .
```

and accidentally staged:

```text
debug.txt
```

Do NOT delete the file just to unstage it.

Use:

```bash
git restore --staged debug.txt
```

Now:

```text
file remains locally
but is removed from staging
```

Then decide whether it should be:

```text
ignored
deleted
kept for later
```

---

# SECTION 156 — ACCIDENTALLY STAGED .env

## 201. Security Scenario

Suppose:

```bash
git status
```

shows:

```text
new file: .env
```

in the staged section.

Immediately:

```bash
git restore --staged .env
```

Then ensure `.gitignore` contains an appropriate rule.

Verify:

```bash
git check-ignore -v .env
```

### Important

If the secret was already committed or pushed, simply adding `.env` to `.gitignore` is NOT enough.

The credential may already exist in repository history.

In that situation:

```text
rotate/revoke the exposed secret
assess repository history
remove sensitive history using an approved process if necessary
```

Treat the credential as compromised.

---

# SECTION 157 — COMMITTED TO WRONG BRANCH

## 202. Scenario

Suppose you intended to work on:

```text
feature/react-frontend
```

but accidentally committed on:

```text
main
```

First:

```bash
git status
git log -1 --oneline
```

Do NOT immediately reset.

The correct recovery depends on:

```text
Was the commit pushed?
Is main shared?
Are there later commits?
Do we want to preserve the commit?
```

For an unpushed local commit, one possible safe strategy is:

```bash
git branch feature/react-frontend
```

This creates a branch pointing to the current commit.

Then the local `main` can be corrected using an appropriate strategy after verifying history.

### Senior Principle

Recovery depends on repository state.

Never memorize:

```text
wrong branch → reset --hard
```

as a universal solution.

---

# SECTION 158 — STASH BEFORE SWITCHING WORK

## 203. Scenario

You are halfway through frontend work.

An urgent issue arrives.

Check:

```bash
git status
```

Temporarily save changes:

```bash
git stash push -m "WIP React frontend"
```

Switch:

```bash
git switch main
```

Later:

```bash
git switch feature/react-frontend
git stash list
git stash pop
```

### Important

Always inspect the result of:

```bash
git stash pop
```

because conflicts can occur.

---

# SECTION 159 — STASH LIST

## 204. View Stashes

```bash
git stash list
```

Example:

```text
stash@{0}: On feature/react-frontend: WIP React frontend
```

Inspect a stash:

```bash
git stash show -p stash@{0}
```

This is safer than blindly popping an unknown stash.

---

# SECTION 160 — MERGE CONFLICT TROUBLESHOOTING

## 205. Scenario

Suppose:

```bash
git merge feature/react-frontend
```

reports a conflict.

First:

```bash
git status
```

Git lists conflicted files.

Open the file and inspect:

```text
<<<<<<<
=======
>>>>>>>
```

Understand both sides.

Create the correct final version.

Then:

```bash
git add <resolved-file>
```

Check:

```bash
git status
```

When all conflicts are resolved, complete the merge as Git instructs.

---

# SECTION 161 — ABORTING A MERGE

## 206. If You Should Not Continue

If a merge is in progress and you determine it should be abandoned:

```bash
git merge --abort
```

This attempts to return to the pre-merge state.

### Important

Use:

```bash
git status
```

first to verify that a merge is actually in progress.

---

# SECTION 162 — REBASE CONFLICT

## 207. Scenario

During:

```bash
git rebase main
```

Git may stop due to conflict.

Flow:

```text
Resolve conflict
↓
git add resolved-file
↓
git rebase --continue
```

If the rebase should be cancelled:

```bash
git rebase --abort
```

Again:

```bash
git status
```

is your guide.

---

# SECTION 163 — WHY REBASE CHANGES COMMIT HASHES

## 208. Important Concept

A Git commit's identity depends on metadata including its parent.

When rebase changes the parent/history, Git creates new commits.

Therefore:

```text
old commit D
```

becomes conceptually:

```text
new commit D'
```

with a different hash.

This is why rebasing already-shared branches can cause collaboration problems.

---

# SECTION 164 — REVERTING A BAD SHARED COMMIT

## 209. Scenario

A bad commit has already been pushed to a shared branch.

Instead of rewriting shared history, we may use:

```bash
git revert <commit-hash>
```

This creates a new commit that reverses the change.

History becomes:

```text
A
↓
B — bad change
↓
C — revert B
```

The original history remains visible.

---

# SECTION 165 — RESET MODES

## 210. `git reset --soft`

Conceptually:

```text
Move branch pointer
Keep changes staged
```

Useful in some local-history correction scenarios.

---

## 211. `git reset` / `--mixed`

Conceptually:

```text
Move branch pointer
Keep file changes
Remove them from staging
```

---

## 212. `git reset --hard`

Conceptually:

```text
Move branch pointer
Reset staging
Reset working tree
```

This can discard local work.

### Rule

Never use:

```bash
git reset --hard
```

just because an online answer says so.

First understand:

```text
HEAD
target commit
working tree
staging area
whether commits are pushed
```

---

# SECTION 166 — FORCE PUSH

## 213. Why Force Push Is Dangerous

Normal push:

```bash
git push
```

protects against certain history mismatches.

Force push:

```bash
git push --force
```

can overwrite remote branch history.

On a shared branch this can remove teammates' work from the branch history.

---

# SECTION 167 — `--force-with-lease`

## 214. Safer Than Blind `--force`

When history rewriting is genuinely required, teams often prefer:

```bash
git push --force-with-lease
```

over:

```bash
git push --force
```

`--force-with-lease` adds a safety check based on the remote state you expect.

Still:

```text
it is a history-rewriting operation
```

and should not be used casually.

### Interview Answer

> "I avoid force pushing shared branches. If a rewritten feature branch genuinely needs a force push, I prefer `--force-with-lease` because it provides an additional safeguard against overwriting unexpected remote changes."

---

# SECTION 168 — PUSH REJECTED

## 215. Scenario

You run:

```bash
git push
```

and Git rejects the push because the remote contains work you do not have.

Do not immediately:

```bash
git push --force
```

Instead investigate:

```bash
git status
git fetch
git log --oneline --graph --decorate --all
```

Understand:

```text
What changed remotely?
What changed locally?
Why did histories diverge?
```

Then integrate using the team's merge/rebase strategy.

---

# SECTION 169 — DETACHED HEAD

## 216. What Is Detached HEAD?

Normally:

```text
HEAD → branch → commit
```

Example:

```text
HEAD → main → abc123
```

Detached HEAD means:

```text
HEAD → commit directly
```

without being attached to a normal local branch.

This may happen after checking out a specific commit.

Example:

```bash
git switch --detach <commit>
```

---

# SECTION 170 — WHY DETACHED HEAD MATTERS

## 217. Scenario

You inspect an old commit and accidentally start making valuable changes.

If you want to preserve them, create a branch before losing track of them:

```bash
git switch -c recovery/my-work
```

The key idea:

```text
valuable work should belong to a branch
```

---

# SECTION 171 — git reflog

## 218. Recovery Tool

`git reflog` records movements of local references such as HEAD.

Command:

```bash
git reflog
```

It can help recover from situations such as:

```text
accidental reset
lost-looking local commit
branch movement
rebase confusion
```

Example concept:

```text
abc123 HEAD@{0}: reset: moving to ...
def456 HEAD@{1}: commit: important change
```

If a commit appears lost from normal history, reflog may still help locate it.

### Important

Reflog is primarily local repository metadata.

It is not a substitute for:

```text
good commits
remote backups
safe Git practices
```

---

# SECTION 172 — GIT RECOVERY MINDSET

## 219. Before Any Recovery Command

Run:

```bash
git status
```

Then:

```bash
git log --oneline --graph --decorate --all
```

If history was moved unexpectedly:

```bash
git reflog
```

Then answer:

```text
What happened?
What do I need to preserve?
Was anything pushed?
Is the branch shared?
```

Only then choose:

```text
restore
stash
revert
reset
rebase
merge
```

---

# SECTION 173 — CI/CD LINUX MINDSET

## 220. Why Tests Behave Differently in CI

A test may pass locally but fail in CI because CI may have different:

```text
OS
Java version
Node version
environment variables
filesystem paths
permissions
CPU/memory
network access
browser mode
timezone
locale
database state
```

Therefore:

```text
"Works locally"
```

is evidence—but not proof that the pipeline environment is correct.

---

# SECTION 174 — COMMON CI DIAGNOSTIC COMMANDS

## 221. Environment Information

Useful safe commands can include:

```bash
pwd
```

```bash
ls -la
```

```bash
java -version
```

```bash
mvn -version
```

```bash
node --version
```

```bash
npm --version
```

```bash
git --version
```

```bash
uname -a
```

These help answer:

```text
What environment is actually running the test?
```

---

# SECTION 175 — DO NOT PRINT CI SECRETS

## 222. Bad Debugging

Avoid:

```bash
env
```

in a public CI log if it could reveal protected variables.

Avoid:

```bash
echo "$PASSWORD"
```

Instead check presence:

```bash
if [ -n "$PASSWORD" ]; then
  echo "PASSWORD is configured"
else
  echo "PASSWORD is missing"
fi
```

---

# SECTION 176 — SHELL SCRIPT FAILURE HANDLING

## 223. `set -e`

Our API runner uses:

```bash
set -e
```

This tells Bash to exit when an unhandled command fails in many ordinary script contexts.

Example:

```bash
#!/bin/bash
set -e

command1
command2
command3
```

If a relevant command fails, the script should not blindly continue executing later steps.

This is useful for automation scripts.

### Important Nuance

Shell error handling has edge cases around constructs such as conditionals, pipelines and compound commands.

So `set -e` is useful, but it is not a complete error-handling strategy by itself.

---

# SECTION 177 — DEBUGGING SHELL SCRIPTS

## 224. `bash -x`

A useful debugging technique:

```bash
bash -x script.sh
```

This prints commands as the shell executes them.

Useful when:

```text
script behaves differently than expected
wrong variable used
wrong path calculated
condition not behaving as expected
```

### Security Warning

Do NOT use shell tracing carelessly with scripts containing secrets.

Tracing may print expanded values.

---

# SECTION 178 — SCRIPT PATH VS CURRENT DIRECTORY

## 225. Important Real Project Concept

Suppose we run:

```bash
./backend/run-local.sh
```

from project root.

The current shell directory is:

```text
SDET-Commerce-Automation/
```

but the script itself lives in:

```text
backend/
```

Our script uses:

```bash
dirname "$0"
```

to determine its own directory.

That allows it to find:

```text
backend/.env
```

reliably.

This is better than assuming the user already ran:

```bash
cd backend
```

---

# SECTION 179 — RELATIVE PATH FAILURE

## 226. Scenario

A script works when launched from one directory but fails from another.

Example symptom:

```text
.env: No such file or directory
```

Possible reason:

```text
script uses current working directory
instead of script directory
```

Debug with:

```bash
pwd
```

and inspect script path logic.

This is a very common automation issue.

---

# SECTION 180 — DATABASE TROUBLESHOOTING FLOW

## 227. API Test Fails Due to Data

Suppose:

```text
POST /api/orders
```

fails unexpectedly.

Do not only inspect the test.

Check:

```text
Request
↓
Response
↓
Backend logs
↓
Database
```

For our Docker PostgreSQL setup:

```bash
docker ps
```

Then:

```bash
docker exec -it sdet-commerce-postgres \
psql -U sdetuser -d sdetcommerce
```

Now inspect relevant tables using SQL.

This is why an SDET benefits from:

```text
Linux + Docker + SQL + API knowledge together
```

---

# SECTION 181 — CONTAINER LOG TROUBLESHOOTING

## 228. PostgreSQL Logs

Example:

```bash
docker logs sdet-commerce-postgres
```

Follow:

```bash
docker logs -f sdet-commerce-postgres
```

This can help diagnose:

```text
database startup failure
authentication failure
recovery
connection issues
```

---

# SECTION 182 — DOCKER CONTAINER STATUS

## 229. Running vs Stopped

```bash
docker ps
```

shows running containers.

```bash
docker ps -a
```

shows running + stopped containers.

If database connection fails:

```text
Do not immediately blame JDBC.
```

First ask:

```text
Is PostgreSQL container actually running?
```

---

# SECTION 183 — PORT MAPPING

## 230. Docker Port Concept

Our PostgreSQL Compose configuration maps:

```text
5432:5432
```

Conceptually:

```text
Host Port : Container Port
```

Therefore:

```text
Spring Boot on host
        ↓
localhost:5432
        ↓
Docker port mapping
        ↓
PostgreSQL container:5432
```

Understanding this is important when debugging database connectivity.

---

# SECTION 184 — HTTP VS APPLICATION FAILURE

## 231. Example

Suppose:

```bash
curl http://localhost:8080/api/products
```

returns:

```text
401
```

This means:

```text
network connection worked
server responded
endpoint exists/reached security layer
authentication is missing/invalid
```

This is very different from:

```text
Connection refused
```

which may mean:

```text
server not running
wrong port
nothing listening
```

Always distinguish:

```text
transport/connectivity failure
```

from:

```text
application-level HTTP response
```

---

# SECTION 185 — CURL CONNECTION REFUSED

## 232. Troubleshooting

If:

```bash
curl http://localhost:8080/v3/api-docs
```

returns connection refused:

Check:

```bash
lsof -i :8080
```

Then verify backend startup.

Possible root causes:

```text
backend not running
backend startup failed
wrong port
process terminated
```

---

# SECTION 186 — 401 TROUBLESHOOTING

## 233. Authentication Failure

If API returns:

```text
401 Unauthorized
```

check:

```text
Was token provided?
Is token valid?
Is token expired?
Is Authorization header formatted correctly?
Did login succeed?
```

Do not classify every 401 as an application defect.

It may be:

```text
test data
environment
token generation
authentication configuration
actual product defect
```

---

# SECTION 187 — 403 TROUBLESHOOTING

## 234. Authorization Failure

If:

```text
USER
↓
POST /api/products
↓
403
```

this is expected in our RBAC design.

But:

```text
ADMIN
↓
POST /api/products
↓
403
```

would require investigation.

Possible areas:

```text
JWT role claim
authentication filter
authority conversion
SecurityConfig
test admin credentials
environment data
```

---

# SECTION 188 — 500 TROUBLESHOOTING

## 235. Server Error

If an API returns:

```text
500
```

inspect:

```text
response
backend logs
stack trace
database state
request data
recent application changes
```

Do not change the automation assertion to accept 500.

A failing test may be correctly exposing an application problem.

---

# SECTION 189 — AUTOMATION FAILURE CLASSIFICATION

## 236. Useful Categories

When a regression test fails, classify it:

```text
Application defect
Automation defect
Test-data issue
Environment issue
Dependency issue
Configuration issue
Infrastructure issue
Expected requirement change
Flaky/nondeterministic behavior
```

This makes defect triage much more precise.

---

# SECTION 190 — DON'T HIDE FAILURES WITH RETRIES

## 237. Retry Is Not Root-Cause Analysis

Bad:

```text
Test fails
↓
retry 3 times
↓
eventually passes
↓
ignore
```

Better:

```text
Why was the first execution unstable?
```

Investigate:

```text
shared state
timing
async processing
network instability
environment
data collision
incorrect waits
dependency availability
```

Retries may be appropriate in limited infrastructure scenarios, but they should not hide genuine defects.

---

# SECTION 191 — REAL TROUBLESHOOTING SCENARIO 1

## 238. Backend Does Not Start

Symptom:

```text
Application failed to start
```

Investigation:

```bash
java -version
```

```bash
lsof -i :8080
```

Check environment variables safely.

Check PostgreSQL:

```bash
docker ps
```

Then inspect the actual Spring Boot error.

Possible causes:

```text
wrong Java
port conflict
DB unavailable
missing environment variable
invalid configuration
compile failure
```

---

# SECTION 192 — REAL TROUBLESHOOTING SCENARIO 2

## 239. API Tests Cannot Connect

Symptom:

```text
Connection refused
```

Check:

```text
1. Is backend running?
2. Which BASE_URL is configured?
3. Which TEST_ENV is active?
4. Is the port correct?
5. Can curl reach the endpoint?
```

Commands:

```bash
echo "$TEST_ENV"
```

```bash
curl -i http://localhost:8080/v3/api-docs
```

```bash
lsof -i :8080
```

---

# SECTION 193 — REAL TROUBLESHOOTING SCENARIO 3

## 240. Maven Works in One Terminal but Not Another

Possible reason:

```text
environment variables were sourced only in the first shell
```

Remember:

```bash
source .env
```

affects the current shell.

Opening another terminal creates another shell session.

Therefore variables may need to be loaded again.

This explains why:

```bash
./run-local.sh
```

is convenient.

The script loads its configuration automatically.

---

# SECTION 194 — REAL TROUBLESHOOTING SCENARIO 4

## 241. Git Shows Unexpected Files

Run:

```bash
git status --short --untracked-files=all
```

Ask:

```text
Should this file be committed?
Should it be ignored?
Is it generated?
Is it secret?
Is it temporary?
```

Do not immediately:

```bash
git add .
```

---

# SECTION 195 — REAL TROUBLESHOOTING SCENARIO 5

## 242. Push Rejected

Run:

```bash
git status
git fetch
git log --oneline --graph --decorate --all
```

Understand divergence.

Do NOT jump directly to:

```bash
git push --force
```

---

# SECTION 196 — REAL TROUBLESHOOTING SCENARIO 6

## 243. Secret Accidentally Committed

If a real credential is committed:

```text
Do not only delete the file.
Do not only add it to .gitignore.
```

Treat the credential as exposed.

Actions may include:

```text
revoke/rotate credential
remove secret from current source
investigate Git history
clean history when required
update secure configuration
verify no other secrets exist
```

Security response is more important than simply making `git status` clean.

---

# SECTION 197 — REAL TROUBLESHOOTING SCENARIO 7

## 244. Script Permission Denied

Symptom:

```text
permission denied
```

Check:

```bash
ls -l script.sh
```

If executable permission is genuinely required:

```bash
chmod +x script.sh
```

Then:

```bash
./script.sh
```

Do not automatically use:

```bash
sudo
```

or:

```bash
chmod 777
```

---

# SECTION 198 — REAL TROUBLESHOOTING SCENARIO 8

## 245. Wrong Tool Version

Check:

```bash
which java
java -version
```

```bash
which mvn
mvn -version
```

```bash
which node
node --version
```

Compare with project requirements.

This becomes especially important for:

```text
Java
Maven
Node
npm
Playwright
Docker
CI runners
```

---

# SECTION 199 — INTERVIEW: HOW DO YOU DEBUG A FAILED AUTOMATED TEST?

## 246. Strong Answer

> "I first determine whether the failure is in the test or the system under test. I inspect the assertion and test logs, then the API request and response where applicable. I verify environment configuration and test data, check application and container logs, and validate database state when necessary. For connectivity issues I verify the process, port and endpoint directly. Once I identify the failure layer, I reproduce it independently where possible and fix or report the root cause rather than hiding the failure with retries."

---

# SECTION 200 — INTERVIEW: HOW DO YOU DEBUG A CI-ONLY FAILURE?

## 247. Strong Answer

> "I compare the CI environment with the local environment, including OS, runtime versions, environment variables, filesystem paths, permissions, test data and external dependencies. I inspect pipeline logs and artifacts, verify that required services are available, and reproduce the CI configuration locally or in a similar container where possible."

---

# SECTION 201 — INTERVIEW: HOW COMFORTABLE ARE YOU WITH LINUX?

## 248. Strong SDET Answer

> "My Linux knowledge is practical and focused on quality engineering. I use command-line tools for filesystem navigation, searching logs, environment configuration, permissions, process and port troubleshooting, shell scripts, Docker operations and CI/CD execution. I am comfortable using commands such as `grep`, `find`, `tail`, `ps`, `lsof`, `chmod`, `curl` and related tools to investigate test and environment failures."

Do not claim:

```text
Linux System Administrator
```

unless you genuinely have that depth.

---

# SECTION 202 — INTERVIEW: HOW COMFORTABLE ARE YOU WITH GIT?

## 249. Strong Answer

> "I use Git for day-to-day automation development, including staging and reviewing changes, feature branches, commits, remote synchronization and pull-request workflows. I understand merge and rebase concepts, conflict resolution, stash, revert and reset, and I am careful around history-rewriting operations. I also verify sensitive files and secrets before publishing automation repositories."

---

# SECTION 203 — TROUBLESHOOTING COMMAND CHEAT SHEET

## 250. Linux

Current location:

```bash
pwd
```

Files:

```bash
ls -la
```

Processes:

```bash
ps aux
```

Java processes:

```bash
ps aux | grep java
```

Port:

```bash
lsof -i :8080
```

Graceful stop:

```bash
kill <PID>
```

Find files:

```bash
find . -name "<pattern>"
```

Search logs:

```bash
grep -Ei "error|exception|failed" application.log
```

Live logs:

```bash
tail -f application.log
```

Disk:

```bash
df -h
```

Directory size:

```bash
du -sh .
```

Architecture:

```bash
uname -m
```

HTTP:

```bash
curl -i <URL>
```

---

## 251. Git

Current state:

```bash
git status
```

Changes:

```bash
git diff
```

Staged changes:

```bash
git diff --cached
```

History:

```bash
git log --oneline --graph --decorate --all
```

Branches:

```bash
git branch
```

Remote:

```bash
git remote -v
```

Fetch:

```bash
git fetch
```

Unstage:

```bash
git restore --staged <file>
```

Discard unstaged change carefully:

```bash
git restore <file>
```

Stash:

```bash
git stash push -m "WIP"
```

Stash list:

```bash
git stash list
```

Revert shared commit:

```bash
git revert <commit>
```

Recovery history:

```bash
git reflog
```

---

# SECTION 204 — DEBUGGING DECISION TREE

## 252. Test Failure

```text
TEST FAILED
    |
    +-- Assertion failure?
    |       |
    |       +-- inspect expected vs actual
    |
    +-- API failure?
    |       |
    |       +-- inspect request
    |       +-- inspect response
    |       +-- reproduce with curl/Swagger
    |
    +-- Authentication?
    |       |
    |       +-- 401 → token/authentication
    |       +-- 403 → authorization/RBAC
    |
    +-- Application failure?
    |       |
    |       +-- backend logs
    |
    +-- Database?
    |       |
    |       +-- Docker
    |       +-- PostgreSQL
    |       +-- SQL validation
    |
    +-- Connectivity?
    |       |
    |       +-- process
    |       +-- port
    |       +-- URL
    |
    +-- Environment?
    |       |
    |       +-- TEST_ENV
    |       +-- BASE_URL
    |       +-- required variables
    |
    +-- Automation defect?
            |
            +-- locator/request/test data/assertion/cleanup
```

---

# SECTION 205 — GIT PROBLEM DECISION TREE

## 253. Something Looks Wrong in Git

```text
GIT PROBLEM
    |
    +-- First:
    |      git status
    |
    +-- Need history?
    |      git log --oneline --graph --decorate --all
    |
    +-- Unstaged unwanted change?
    |      inspect git diff
    |      then consider git restore
    |
    +-- Accidentally staged?
    |      git restore --staged
    |
    +-- Need temporary workspace?
    |      git stash
    |
    +-- Merge conflict?
    |      resolve → add → continue
    |
    +-- Rebase conflict?
    |      resolve → add → rebase --continue
    |
    +-- Bad shared commit?
    |      consider git revert
    |
    +-- Lost-looking local commit?
    |      inspect git reflog
    |
    +-- Push rejected?
           fetch + inspect history
           NEVER blindly force
```

---

# SECTION 206 — FIVE COMMANDS TO REMEMBER UNDER PRESSURE

If you forget everything during troubleshooting, start with:

```bash
pwd
```

```bash
git status
```

```bash
git diff
```

```bash
lsof -i :8080
```

```bash
curl -i http://localhost:8080/v3/api-docs
```

They answer:

```text
Where am I?
What changed?
What exactly changed?
Is the server listening?
Can I reach the server?
```

---

# SECTION 207 — SENIOR SDET PRINCIPLE

## 254. Evidence Before Assumption

Do not say:

```text
"The API is down."
```

when all you know is:

```text
"My test failed."
```

Instead gather evidence:

```text
Test failed
↓
curl also fails
↓
nothing listening on expected port
↓
backend startup log shows DB connection failure
↓
PostgreSQL container is stopped
```

Now the conclusion is supported.

That difference separates:

```text
guessing
```

from:

```text
engineering diagnosis
```

---

# END OF PART 3 — REAL-WORLD TROUBLESHOOTING

At this point we understand:

Linux filesystem
Linux permissions
Processes
Ports
Environment variables
Networking
curl
Logs
Docker troubleshooting
Git states
Branch troubleshooting
Merge conflicts
Rebase
Stash
Restore
Reset
Revert
Force-push risks
Reflog
CI/CD troubleshooting
SDET failure classification

The next goal is to turn these concepts into realistic hands-on exercises rather than only reading commands.

---

# PART 4 — HANDS-ON LINUX + GIT LAB FOR SDET

# SECTION 208 — LAB GOAL

The purpose of this lab is to practice commands safely.

We will NOT use the real project for risky Git experiments.

We will create a separate practice directory.

Goal:

```text
Learn by doing
↓
See command output
↓
Understand state changes
↓
Recover safely
```

---

# SECTION 209 — CREATE SAFE PRACTICE DIRECTORY

## 255. Create Lab Folder

Run:

```bash
cd ~
mkdir -p sdet-linux-git-lab
cd sdet-linux-git-lab
pwd
```

Expected location conceptually:

```text
/Users/<username>/sdet-linux-git-lab
```

Now verify:

```bash
ls -la
```

---

# SECTION 210 — CREATE BASIC FILES

## 256. Create Sample Files

Run:

```bash
touch app.log
touch test-results.txt
touch config.txt
```

Check:

```bash
ls -la
```

Now add content:

```bash
echo "INFO Application started" > app.log
echo "ERROR Database connection failed" >> app.log
echo "INFO Retrying connection" >> app.log
echo "INFO Application ready" >> app.log
```

Verify:

```bash
cat app.log
```

Expected:

```text
INFO Application started
ERROR Database connection failed
INFO Retrying connection
INFO Application ready
```

---

# SECTION 211 — PRACTICE head AND tail

## 257. head

Run:

```bash
head -2 app.log
```

Expected:

```text
INFO Application started
ERROR Database connection failed
```

Now:

```bash
tail -2 app.log
```

Expected:

```text
INFO Retrying connection
INFO Application ready
```

---

# SECTION 212 — PRACTICE grep

## 258. Search Errors

Run:

```bash
grep "ERROR" app.log
```

Expected:

```text
ERROR Database connection failed
```

Show line numbers:

```bash
grep -n "ERROR" app.log
```

Ignore case:

```bash
grep -ni "error" app.log
```

Multiple patterns:

```bash
grep -Ei "error|failed|retry" app.log
```

### What We Learned

```text
grep
→ filters useful information from large text/log output
```

---

# SECTION 213 — PRACTICE wc

## 259. Count Lines

Run:

```bash
wc -l app.log
```

Expected:

```text
4 app.log
```

Count words:

```bash
wc -w app.log
```

---

# SECTION 214 — PRACTICE find

## 260. Create Nested Files

Run:

```bash
mkdir -p reports/api
mkdir -p reports/ui

touch reports/api/api-report.txt
touch reports/ui/ui-report.txt
touch reports/ui/debug.log
```

Now:

```bash
find . -name "*.txt"
```

You should see `.txt` files recursively.

Find logs:

```bash
find . -name "*.log"
```

---

# SECTION 215 — FIND BEFORE DELETE

## 261. Safe Cleanup Pattern

Create temporary files:

```bash
touch old.backup
touch reports/api/api.backup
```

Find them first:

```bash
find . -name "*.backup"
```

Do NOT delete immediately.

First verify output.

Then:

```bash
find . -name "*.backup" -delete
```

Verify:

```bash
find . -name "*.backup"
```

Expected:

```text
no output
```

### Engineering Habit

```text
Find
↓
Verify
↓
Delete
```

---

# SECTION 216 — PRACTICE COPY

## 262. cp

Run:

```bash
cp config.txt config-backup.txt
```

Check:

```bash
ls -la
```

Now:

```bash
echo "environment=local" > config.txt
```

Compare:

```bash
cat config.txt
cat config-backup.txt
```

Notice:

```text
config.txt changed
backup remains separate
```

---

# SECTION 217 — PRACTICE MOVE / RENAME

## 263. mv

Rename:

```bash
mv config-backup.txt config-old.txt
```

Check:

```bash
ls
```

Move file into reports:

```bash
mv config-old.txt reports/
```

Check:

```bash
find reports -maxdepth 2 -type f
```

---

# SECTION 218 — PRACTICE REDIRECTION

## 264. `>` Overwrite

Run:

```bash
echo "first line" > test-results.txt
cat test-results.txt
```

Now:

```bash
echo "replacement line" > test-results.txt
cat test-results.txt
```

Notice:

```text
first line disappeared
```

because `>` overwrites.

---

## 265. `>>` Append

Run:

```bash
echo "second line" >> test-results.txt
cat test-results.txt
```

Now both lines remain.

---

# SECTION 219 — PRACTICE PIPES

## 266. Pipe Output

Run:

```bash
cat app.log | grep "INFO"
```

This works, although simpler form is:

```bash
grep "INFO" app.log
```

Now:

```bash
grep -n "INFO" app.log | head -2
```

This means:

```text
Find INFO
↓
show line numbers
↓
send to head
↓
show first 2 matches
```

---

# SECTION 220 — PRACTICE COMMAND CHAINING

## 267. `&&`

Run:

```bash
mkdir demo-folder && cd demo-folder && pwd
```

All commands execute only if previous ones succeed.

Return:

```bash
cd ..
```

---

## 268. `||`

Run:

```bash
ls file-that-does-not-exist || echo "File not found"
```

Expected:

```text
ls error
File not found
```

---

# SECTION 221 — PRACTICE EXIT CODES

## 269. Successful Command

Run:

```bash
ls app.log
echo $?
```

Expected:

```text
0
```

Now:

```bash
ls missing-file
echo $?
```

Expected:

```text
non-zero value
```

### Important

This is how shell scripts and CI/CD understand command success/failure.

---

# SECTION 222 — CREATE SHELL SCRIPT

## 270. Simple Test Runner

Run:

```bash
cat > run-demo-tests.sh <<'EOF'
#!/bin/bash
set -e

echo "Starting demo tests..."

if grep -q "ERROR" app.log; then
  echo "Error found in application log"
else
  echo "No error found"
fi

echo "Demo test execution completed"
EOF
```

Now inspect:

```bash
cat run-demo-tests.sh
```

---

# SECTION 223 — EXECUTABLE PERMISSION LAB

## 271. Try Running Script

Run:

```bash
./run-demo-tests.sh
```

You may receive:

```text
permission denied
```

Now inspect:

```bash
ls -l run-demo-tests.sh
```

Make executable:

```bash
chmod +x run-demo-tests.sh
```

Check again:

```bash
ls -l run-demo-tests.sh
```

Now execute:

```bash
./run-demo-tests.sh
```

---

# SECTION 224 — LIVE LOG LAB

## 272. tail -f

Open a second terminal.

Terminal 1:

```bash
cd ~/sdet-linux-git-lab
tail -f app.log
```

Leave it running.

Terminal 2:

```bash
cd ~/sdet-linux-git-lab
echo "INFO New request received" >> app.log
```

Go back to Terminal 1.

You should immediately see:

```text
INFO New request received
```

Add another:

```bash
echo "ERROR Payment failed" >> app.log
```

Terminal 1 should update live.

Stop `tail -f`:

```text
Ctrl + C
```

### Real SDET Use

```text
Follow logs
↓
Perform test/action
↓
Watch application behavior live
```

---

# SECTION 225 — PROCESS LAB

## 273. Start a Temporary Process

Run:

```bash
sleep 300 &
```

The shell should display a job/PID.

Now:

```bash
ps aux | grep sleep
```

or:

```bash
pgrep -fl sleep
```

You should see the running process.

---

# SECTION 226 — STOP PROCESS LAB

## 274. Kill Temporary Process

Find PID:

```bash
pgrep -fl sleep
```

Then:

```bash
kill <PID>
```

Verify:

```bash
pgrep -fl sleep
```

If no relevant process appears, it stopped.

Do NOT type literal `<PID>`.

Replace it with the actual number.

---

# SECTION 227 — LOCAL HTTP SERVER LAB

## 275. Start Local Server

Check Python:

```bash
python3 --version
```

Then start a simple local web server:

```bash
python3 -m http.server 9090
```

Leave terminal running.

Expected concept:

```text
Serving HTTP on ... port 9090
```

---

# SECTION 228 — CHECK PORT

## 276. lsof

Open another terminal:

```bash
lsof -i :9090
```

You should see Python listening on the port.

This is the exact concept we use for:

```text
Spring Boot port 8080
```

---

# SECTION 229 — CURL LAB

## 277. Call Local Server

Run:

```bash
curl -i http://localhost:9090
```

You should receive an HTTP response.

Try:

```bash
curl -v http://localhost:9090
```

Observe:

```text
connection
request headers
response headers
```

Stop server from original terminal:

```text
Ctrl + C
```

Now try again:

```bash
curl -i http://localhost:9090
```

Expected concept:

```text
connection failed/refused
```

Now check:

```bash
lsof -i :9090
```

Expected:

```text
no listening process
```

This proves the difference between:

```text
HTTP response
```

and:

```text
server not listening
```

---

# SECTION 230 — START GIT LAB

## 278. Create Separate Practice Repository

Stay inside:

```bash
cd ~/sdet-linux-git-lab
```

Create Git-specific folder:

```bash
mkdir git-practice
cd git-practice
```

Initialize:

```bash
git init
```

Check:

```bash
git status
```

---

# SECTION 231 — CREATE FIRST GIT FILE

## 279. README

Run:

```bash
echo "# Git Practice" > README.md
```

Check:

```bash
git status
```

Expected:

```text
README.md is untracked
```

Short form:

```bash
git status --short
```

Expected concept:

```text
?? README.md
```

---

# SECTION 232 — STAGE FILE

## 280. git add

Run:

```bash
git add README.md
```

Then:

```bash
git status
```

README should now appear under:

```text
Changes to be committed
```

---

# SECTION 233 — UNSTAGE LAB

## 281. Remove From Staging

Run:

```bash
git restore --staged README.md
```

Check:

```bash
git status
```

README becomes untracked again.

Now stage it again:

```bash
git add README.md
```

---

# SECTION 234 — FIRST COMMIT

## 282. Commit

Run:

```bash
git commit -m "docs: add initial README"
```

If Git asks you to configure user identity, check:

```bash
git config --global user.name
git config --global user.email
```

If already configured, continue.

Then:

```bash
git status
```

Expected:

```text
working tree clean
```

Check history:

```bash
git log --oneline
```

---

# SECTION 235 — PRACTICE MODIFICATION

## 283. Modify Tracked File

Run:

```bash
echo "" >> README.md
echo "Learning Git safely." >> README.md
```

Check:

```bash
git status --short
```

Expected:

```text
 M README.md
```

Inspect:

```bash
git diff
```

Now you can see exact unstaged changes.

---

# SECTION 236 — STAGED DIFF LAB

## 284. Stage Modification

Run:

```bash
git add README.md
```

Now:

```bash
git diff
```

likely shows nothing because modification is staged.

Use:

```bash
git diff --cached
```

Now the change appears.

This is the practical difference between:

```text
git diff
```

and:

```text
git diff --cached
```

---

# SECTION 237 — SECOND COMMIT

## 285. Commit Change

Run:

```bash
git commit -m "docs: expand Git practice README"
```

Check:

```bash
git log --oneline
```

Now there should be at least two commits.

---

# SECTION 238 — CREATE FEATURE BRANCH

## 286. Branch Practice

Check current branch:

```bash
git branch
```

Create:

```bash
git switch -c feature/api-tests
```

Check:

```bash
git branch
```

Expected:

```text
* feature/api-tests
  main
```

The exact default initial branch may differ depending on Git configuration.

If your default branch is `master`, rename it before continuing:

```bash
git branch -m main
```

Only do this if needed.

---

# SECTION 239 — COMMIT ON FEATURE BRANCH

## 287. Create Feature File

Run:

```bash
cat > api-tests.txt <<'EOF'
Login API Test
Product API Test
Cart API Test
EOF
```

Check:

```bash
git status --short
```

Stage and commit:

```bash
git add api-tests.txt
git commit -m "test: add API test scenarios"
```

Now:

```bash
git log --oneline --decorate -5
```

Feature branch points to the new commit.

---

# SECTION 240 — SWITCH BACK TO MAIN

## 288. Observe Branch Difference

Run:

```bash
git switch main
```

Now:

```bash
ls
```

Notice:

```text
api-tests.txt may disappear from working tree
```

Why?

Because that file belongs to the feature branch commit, not `main`.

Switch back:

```bash
git switch feature/api-tests
```

The file returns.

This is one of the best ways to understand branches.

---

# SECTION 241 — STASH LAB

## 289. Create Unfinished Work

Stay on:

```text
feature/api-tests
```

Run:

```bash
echo "Order API Test - WIP" >> api-tests.txt
```

Check:

```bash
git status
```

Now stash:

```bash
git stash push -m "WIP order API test"
```

Check:

```bash
git status
```

Expected:

```text
working tree clean
```

View stashes:

```bash
git stash list
```

---

# SECTION 242 — RESTORE STASH

## 290. Apply Work Back

Run:

```bash
git stash pop
```

Check:

```bash
git status
```

The modification should be back.

Inspect:

```bash
git diff
```

Now discard this WIP intentionally:

```bash
git restore api-tests.txt
```

Check:

```bash
git status
```

Expected:

```text
clean
```

---

# SECTION 243 — MERGE FEATURE BRANCH

## 291. Merge Clean Feature

Switch:

```bash
git switch main
```

Merge:

```bash
git merge feature/api-tests
```

Check:

```bash
git log --oneline --graph --decorate --all
```

Now main contains feature work.

---

# SECTION 244 — CREATE MERGE CONFLICT LAB

## 292. Create First Conflict Branch

From main:

```bash
git switch -c feature/login-message
```

Replace README with:

```bash
cat > README.md <<'EOF'
# Git Practice

Login feature branch version.
EOF
```

Commit:

```bash
git add README.md
git commit -m "docs: update login branch message"
```

---

# SECTION 245 — CREATE CONFLICT ON MAIN

## 293. Different Change to Same Lines

Switch:

```bash
git switch main
```

Replace README differently:

```bash
cat > README.md <<'EOF'
# Git Practice

Main branch version.
EOF
```

Commit:

```bash
git add README.md
git commit -m "docs: update main branch message"
```

Now both branches changed the same area differently.

---

# SECTION 246 — TRIGGER MERGE CONFLICT

## 294. Merge

Run:

```bash
git merge feature/login-message
```

Git should report a conflict in:

```text
README.md
```

Check:

```bash
git status
```

View:

```bash
cat README.md
```

You should see markers conceptually like:

```text
<<<<<<< HEAD
Main branch version.
=======
Login feature branch version.
>>>>>>> feature/login-message
```

---

# SECTION 247 — RESOLVE MERGE CONFLICT

## 295. Create Correct Final Version

Replace file:

```bash
cat > README.md <<'EOF'
# Git Practice

Main and login feature changes have been integrated.
EOF
```

Now stage:

```bash
git add README.md
```

Check:

```bash
git status
```

Complete merge:

```bash
git commit -m "merge: resolve README conflict"
```

Now:

```bash
git log --oneline --graph --decorate --all
```

Observe branch history.

### What We Practiced

```text
Conflict
↓
git status
↓
inspect markers
↓
understand both changes
↓
create final version
↓
git add
↓
complete merge
```

---

# SECTION 248 — PRACTICE REVERT

## 296. Create Bad Commit

On main:

```bash
echo "THIS LINE SHOULD NOT EXIST" >> README.md
```

Commit:

```bash
git add README.md
git commit -m "docs: add temporary bad line"
```

Check:

```bash
tail -5 README.md
```

Get latest commit:

```bash
git log -1 --oneline
```

---

# SECTION 249 — REVERT BAD COMMIT

## 297. git revert

Run:

```bash
git revert HEAD
```

Git may open an editor for the revert commit message.

If an editor appears and you are comfortable with the default message, save and exit.

After completion:

```bash
git log --oneline -3
```

Then:

```bash
tail -5 README.md
```

The bad line should be removed.

Important:

```text
Original bad commit still exists in history
+
new revert commit reverses it
```

---

# SECTION 250 — SAFE RESET DEMO

## 298. Create Local Commit

Create file:

```bash
echo "temporary local work" > temporary.txt
git add temporary.txt
git commit -m "chore: temporary local commit"
```

Check:

```bash
git log -2 --oneline
```

Now demonstrate a soft reset:

```bash
git reset --soft HEAD~1
```

Check:

```bash
git status
```

`temporary.txt` should remain staged.

This demonstrates:

```text
commit removed from branch tip
but changes preserved in staging
```

Now commit again:

```bash
git commit -m "chore: restore temporary local commit"
```

---

# SECTION 251 — RESET MIXED DEMO

## 299. Mixed Reset

Run:

```bash
git reset HEAD~1
```

Check:

```bash
git status
```

Now `temporary.txt` should remain in the working tree but no longer be staged.

Stage again:

```bash
git add temporary.txt
git commit -m "chore: restore temporary local commit again"
```

### Important

We are doing this ONLY in the practice repository.

Do not experiment with reset casually in the real project.

---

# SECTION 252 — REFLOG LAB

## 300. View Reference History

Run:

```bash
git reflog -10
```

Observe entries for:

```text
commits
resets
branch switches
merges
```

You may see previous commit hashes that are no longer visible in normal branch history.

This demonstrates why:

```text
git reflog
```

is useful for local recovery.

---

# SECTION 253 — GIT SHOW LAB

## 301. Inspect Latest Commit

Run:

```bash
git show HEAD
```

For shorter output:

```bash
git show --stat HEAD
```

This tells you:

```text
who
when
commit message
files changed
```

---

# SECTION 254 — HISTORY VISUALIZATION

## 302. Graph

Run:

```bash
git log --oneline --graph --decorate --all
```

Spend time reading this output.

Try to identify:

```text
main
feature branch
merge commit
HEAD
commit sequence
```

This command is one of the best tools for understanding Git history.

---

# SECTION 255 — CREATE .gitignore LAB

## 303. Create Files That Should Not Be Committed

Run:

```bash
touch .env
mkdir -p target
touch target/report.txt
```

Create `.gitignore`:

```bash
cat > .gitignore <<'EOF'
.env
target/
*.log
EOF
```

Check:

```bash
git status --short --untracked-files=all
```

You should see:

```text
.gitignore
```

but normally not:

```text
.env
target/report.txt
```

because they are ignored.

---

# SECTION 256 — VERIFY IGNORE RULES

## 304. git check-ignore

Run:

```bash
git check-ignore -v .env
```

Then:

```bash
git check-ignore -v target/report.txt
```

This proves the ignore rule is actually working.

---

# SECTION 257 — IGNORED FILE DISCOVERY

## 305. Show Ignored Files

Run:

```bash
git status --ignored --short
```

This can display ignored content as well.

Useful when debugging:

```text
Why isn't Git showing my file?
```

---

# SECTION 258 — PRACTICE SECRET SAFETY

## 306. Dummy Environment File

Do NOT use real credentials.

Run:

```bash
cat > .env <<'EOF'
DB_USERNAME=dummy-user
DB_PASSWORD=dummy-password
JWT_SECRET=dummy-secret
EOF
```

Because `.env` is ignored:

```bash
git status --short
```

should not show it.

Verify:

```bash
git check-ignore -v .env
```

### Important

Even though this lab uses dummy values, our real project uses actual secrets locally.

Therefore:

```text
real .env
→ ignored

.env.example
→ safe placeholders only
```

---

# SECTION 259 — CREATE .env.example

## 307. Safe Template

Run:

```bash
cat > .env.example <<'EOF'
DB_USERNAME=<your-db-username>
DB_PASSWORD=<your-db-password>
JWT_SECRET=<your-jwt-secret>
EOF
```

Check:

```bash
git status --short
```

`.env.example` should appear as untracked because it is safe to commit.

Stage:

```bash
git add .gitignore .env.example
```

Review:

```bash
git diff --cached
```

Commit:

```bash
git commit -m "chore: add safe environment template"
```

---

# SECTION 260 — PRE-COMMIT REVIEW LAB

## 308. Create Several Changes

Run:

```bash
echo "new documentation" >> README.md
touch notes.txt
```

Now:

```bash
git status --short --untracked-files=all
```

Before staging ask:

```text
Are these intended?
Any generated files?
Any secret files?
```

Then:

```bash
git add .
```

Review:

```bash
git diff --cached --stat
```

```bash
git diff --cached --name-only
```

```bash
git diff --cached
```

Only after review:

```bash
git commit -m "docs: add practice notes"
```

This mirrors our real portfolio repository workflow.

---

# SECTION 261 — WRONG FILE STAGED LAB

## 309. Practice Unstaging

Create:

```bash
echo "temporary debug info" > debug.txt
```

Stage:

```bash
git add debug.txt
```

Check:

```bash
git status
```

Now decide:

```text
This should NOT be committed.
```

Unstage:

```bash
git restore --staged debug.txt
```

Check:

```bash
git status
```

Delete:

```bash
rm debug.txt
```

Check again:

```bash
git status
```

---

# SECTION 262 — BRANCH DELETE LAB

## 310. Delete Merged Feature Branch

List:

```bash
git branch
```

If `feature/api-tests` has already been merged into main:

```bash
git branch -d feature/api-tests
```

`-d` is safer because Git checks whether the branch is merged.

Force deletion:

```bash
git branch -D <branch>
```

exists, but do not use it casually.

---

# SECTION 263 — FETCH/PUSH THEORY LAB WITHOUT REMOTE

## 311. Why We Stop Here

This local practice repository intentionally has no GitHub remote.

Check:

```bash
git remote -v
```

Expected:

```text
no output
```

We are NOT going to create unnecessary GitHub repositories just for this lab.

Remote commands are already practiced through our real:

```text
SDET-Commerce-Automation
```

repository.

---

# SECTION 264 — REAL PROJECT SAFE CHECK

## 312. Return to Real Project

After the lab:

```bash
cd ~/SDET-Commerce-Automation
```

Run ONLY read-only verification:

```bash
git status
```

Then:

```bash
git log -1 --oneline
```

Then:

```bash
git remote -v
```

Do not run merge/reset/revert experiments here.

The goal is to see how the concepts from the practice repository map to the real project.

---

# SECTION 265 — HANDS-ON INTERVIEW STORY

## 313. Linux Story

A strong practical explanation:

> "I use Linux and shell commands during automation development to inspect logs, verify environment variables, troubleshoot processes and ports, manage files and execute Docker-based dependencies. For example, when debugging local services I can use `lsof` to identify the process listening on a port, `curl` to verify API connectivity and `grep` or `tail -f` to investigate logs."

---

# SECTION 266 — HANDS-ON GIT STORY

## 314. Git Story

> "Before publishing my SDET automation project, I reviewed the working tree, verified that real `.env` files were ignored, scanned for sensitive data, inspected staged filenames and reviewed the staged diff before committing. I then connected the local repository to GitHub, configured the remote and pushed the main branch after authentication."

This is stronger than saying:

```text
I know git add, commit and push.
```

---

# SECTION 267 — WHAT THIS LAB TAUGHT

## 315. Linux Skills Practiced

```text
pwd
ls
touch
mkdir
cat
head
tail
grep
wc
find
cp
mv
rm
pipes
redirection
exit codes
chmod
shell scripts
tail -f
processes
kill
ports
curl
```

---

## 316. Git Skills Practiced

```text
git init
git status
git add
git diff
git diff --cached
git commit
git log
git branch
git switch
git stash
git merge
merge conflict resolution
git revert
git reset --soft
git reset mixed
git reflog
git show
.gitignore
git check-ignore
staged review
safe cleanup
```

---

# SECTION 268 — SAFE VS DANGEROUS COMMANDS

## 317. Generally Safe Inspection Commands

```bash
pwd
ls
cat
head
tail
grep
find
git status
git diff
git log
git show
git branch
git remote -v
git check-ignore
```

These primarily inspect state.

---

## 318. Commands Requiring More Care

```bash
rm
find ... -delete
kill
chmod
git restore
git reset
git rebase
git merge
git revert
git push
```

These modify state.

---

## 319. High-Risk Commands

Use only when fully understood:

```bash
rm -rf
git reset --hard
git push --force
git branch -D
kill -9
```

A senior engineer is not someone who uses powerful commands frequently.

A senior engineer knows when NOT to use them.

---

# SECTION 269 — LAB DEBUGGING PRINCIPLE

## 320. Predict Before Running

Before every command, ask:

```text
What state am I currently in?

What do I expect this command to change?

What output do I expect?

How can I verify the result?

Can I recover if I am wrong?
```

This habit is more important than memorizing syntax.

---

# SECTION 270 — FINAL HANDS-ON REVISION FLOW

## 321. Linux Mental Model

```text
Need location?
→ pwd

Need files?
→ ls

Need text?
→ cat/head/tail

Need search?
→ grep

Need file discovery?
→ find

Need live logs?
→ tail -f

Need process?
→ ps/pgrep

Need port?
→ lsof

Need HTTP verification?
→ curl

Need file permission?
→ ls -l / chmod
```

---

## 322. Git Mental Model

```text
Confused?
→ git status

Need to know change?
→ git diff

Need to know staged change?
→ git diff --cached

Need history?
→ git log

Need temporary storage?
→ git stash

Need branch?
→ git switch

Conflict?
→ status → resolve → add → continue

Bad shared commit?
→ revert

Lost local history?
→ reflog

Need to publish?
→ review → commit → push
```

---

# END OF PART 4 — HANDS-ON LINUX + GIT LAB

This lab converted theoretical Linux and Git knowledge into practical SDET experience.

Next:

**Part 5 — Advanced Git collaboration + CI/CD/Linux interview scenarios: Pull Requests, code reviews, release branches, cherry-pick, tags, GitHub Actions thinking, Jenkins/Linux troubleshooting, SSH basics, networking and production-style incident debugging.**

---

# PART 5 — ADVANCED GIT COLLABORATION, CI/CD & PRODUCTION TROUBLESHOOTING

# SECTION 271 — PROFESSIONAL TEAM GIT WORKFLOW

## 323. Git in a Real Engineering Team

A professional workflow usually looks like:

```text
Requirement / Jira Ticket
        ↓
Update Local Main
        ↓
Create Feature Branch
        ↓
Develop / Automate
        ↓
Local Testing
        ↓
Commit
        ↓
Push Feature Branch
        ↓
Create Pull Request
        ↓
CI Pipeline
        ↓
Code Review
        ↓
Fix Review Comments
        ↓
Approval
        ↓
Merge
        ↓
Main Branch
```

As an SDET, we may use this workflow for:

```text
automation framework changes
new automated tests
API tests
Playwright tests
test utilities
CI/CD configuration
test-data utilities
bug fixes
reporting improvements
```

---

# SECTION 272 — STARTING NEW WORK SAFELY

## 324. Typical Workflow

Before creating a feature branch:

```bash
git switch main
```

Then synchronize:

```bash
git pull
```

Then create branch:

```bash
git switch -c feature/react-frontend
```

Concept:

```text
latest main
    ↓
new branch
    ↓
new work
```

This reduces the chance of starting work from an outdated base.

---

# SECTION 273 — BRANCH NAMING

## 325. Useful Naming Patterns

Examples:

```text
feature/react-frontend
feature/playwright-framework
feature/payment-tests

fix/order-cancellation
fix/login-validation

test/product-rbac
test/payment-db-validation

docs/linux-git-notes

ci/api-regression
```

Some organizations include Jira IDs:

```text
feature/ABC-123-product-search
```

There is no universal naming standard.

Follow the team's convention.

---

# SECTION 274 — SMALL, LOGICAL COMMITS

## 326. Why Commit Quality Matters

Avoid a single commit containing unrelated work such as:

```text
React login
README typo
database migration
API test
random formatting
```

Prefer logical commits:

```text
feat: add login page

test: add login API validation

docs: document authentication flow
```

This improves:

```text
reviewability
history
debugging
revertability
```

---

# SECTION 275 — BEFORE PUSHING A FEATURE BRANCH

## 327. Review First

Check:

```bash
git status
```

Then:

```bash
git diff
```

Stage intended changes:

```bash
git add .
```

Review staged changes:

```bash
git diff --cached
```

Then:

```bash
git commit -m "feat: add React login flow"
```

Review history:

```bash
git log --oneline -5
```

Then push:

```bash
git push -u origin feature/react-frontend
```

---

# SECTION 276 — PULL REQUEST

## 328. What Is a Pull Request?

A Pull Request proposes:

```text
source branch
      ↓
changes
      ↓
target branch
```

Example:

```text
feature/react-frontend
        ↓
Pull Request
        ↓
main
```

A PR allows:

```text
code review
automated checks
discussion
approval
change tracking
controlled merge
```

---

# SECTION 277 — WHAT SHOULD A GOOD PR CONTAIN?

## 329. PR Description

A useful PR explains:

```text
What changed?
Why was it needed?
How was it tested?
Any risks?
Any screenshots/reports?
Any known limitations?
```

Example:

```text
Summary:
Added API automation for payment validation.

Testing:
- Successful payment
- Duplicate payment
- Cancelled-order payment
- Unauthorized request
- Database validation

Regression:
48 API tests passed locally.
```

The exact template depends on the organization.

---

# SECTION 278 — CODE REVIEW

## 330. Why Code Review Matters for SDET

Automation is production code supporting product quality.

Poor automation can create:

```text
false failures
false confidence
flaky tests
slow pipelines
maintenance problems
security risks
```

Therefore test code should also be reviewed.

---

# SECTION 279 — WHAT TO REVIEW IN AUTOMATION CODE

## 331. SDET Review Checklist

Look for:

```text
Correct test coverage
Clear assertions
No hardcoded secrets
Reusable utilities
Independent tests
Proper cleanup
Good test data
Stable selectors
Correct waits
No unnecessary sleeps
API status + body validation
DB validation where appropriate
Environment independence
Readable naming
Useful reporting
```

---

# SECTION 280 — RESPONDING TO REVIEW COMMENTS

## 332. Professional Approach

Suppose reviewer says:

```text
Please move authentication logic into a reusable helper.
```

Do not create another branch.

Modify the same feature branch.

Then:

```bash
git status
git add .
git commit -m "refactor: reuse authentication helper"
git push
```

The Pull Request automatically receives the new commit.

---

# SECTION 281 — CI CHECKS ON A PR

## 333. Typical PR Pipeline

When a PR is opened:

```text
Pull Request
    ↓
Checkout Code
    ↓
Install Dependencies
    ↓
Compile
    ↓
Unit Tests
    ↓
API Tests
    ↓
UI Tests
    ↓
Static Analysis
    ↓
Reports
    ↓
Pass / Fail
```

Not every organization runs every test layer on every PR.

For example:

```text
PR
→ smoke tests

Nightly
→ full regression
```

This balances:

```text
feedback speed
vs
coverage
```

---

# SECTION 282 — BRANCH PROTECTION

## 334. Protected Main Branch

Organizations often protect:

```text
main
```

Possible rules:

```text
No direct push
PR required
Approval required
CI must pass
Conversation must be resolved
Specific reviewers required
```

This prevents accidental or unreviewed production changes.

---

# SECTION 283 — MERGE STRATEGIES

## 335. Merge Commit

A normal merge may preserve branch history:

```text
A---B------M
    \     /
     C---D
```

`M` is the merge commit.

---

## 336. Squash Merge

Multiple feature commits become one commit on the target branch.

Feature:

```text
C
D
E
```

Target may receive:

```text
F
```

representing the squashed feature.

Useful when feature branches contain many small development commits.

---

## 337. Rebase Merge

Commits may be replayed onto the target branch to produce linear history.

The correct strategy depends on team standards.

---

# SECTION 284 — CHERRY-PICK

## 338. What Is git cherry-pick?

`cherry-pick` applies a specific existing commit onto the current branch.

Example:

```bash
git cherry-pick abc1234
```

Concept:

```text
Branch A:
A---B---C

Branch B:
D---E

Need only commit C on Branch B

git cherry-pick C

Branch B:
D---E---C'
```

The new commit has a different hash.

---

# SECTION 285 — REAL CHERRY-PICK SCENARIO

## 339. Hotfix Example

Suppose:

```text
develop
```

contains several new features plus one important production bug fix.

Production release branch should receive only the bug fix.

Instead of merging all of `develop`, the team may cherry-pick the specific fix commit.

Example:

```bash
git switch release/1.2
git cherry-pick <fix-commit>
```

### Important

Do not use cherry-pick as a substitute for a clear branching strategy.

---

# SECTION 286 — CHERRY-PICK CONFLICT

## 340. Conflict Handling

If conflict occurs:

```bash
git status
```

Resolve the files.

Then:

```bash
git add <resolved-file>
```

Continue:

```bash
git cherry-pick --continue
```

Abort:

```bash
git cherry-pick --abort
```

---

# SECTION 287 — TAGS

## 341. What Is a Git Tag?

A tag identifies an important point in Git history.

Often used for:

```text
releases
milestones
versions
```

Example:

```text
v1.0.0
```

Unlike a normal branch, a release tag is generally intended to remain fixed to a specific commit.

---

# SECTION 288 — ANNOTATED TAG

## 342. Create Tag

Example:

```bash
git tag -a v1.0.0 -m "Core commerce automation milestone"
```

Inspect:

```bash
git show v1.0.0
```

Push tag:

```bash
git push origin v1.0.0
```

### Our Project

We have NOT yet created this release tag.

A future milestone could conceptually be:

```text
v1.0.0
→ core commerce platform + automation
```

Later:

```text
v2.0.0
→ Redis integration

v3.0.0
→ Kafka integration
```

Only tag genuine milestones.

---

# SECTION 289 — SEMANTIC VERSIONING

## 343. Common Version Pattern

```text
MAJOR.MINOR.PATCH
```

Example:

```text
2.4.1
```

Conceptually:

```text
2 → major
4 → minor
1 → patch
```

A commonly used interpretation:

```text
MAJOR → incompatible/breaking change
MINOR → backward-compatible feature
PATCH → backward-compatible bug fix
```

Actual release policy may vary by organization.

---

# SECTION 290 — RELEASE BRANCHES

## 344. Release Branch Concept

Some teams use:

```text
main
develop
feature/*
release/*
hotfix/*
```

Example:

```text
feature
   ↓
develop
   ↓
release/2.0
   ↓
main
```

Other teams use trunk-based development and do not maintain long-lived `develop` or release branches.

### Interview Principle

Do not claim:

```text
Every company must use GitFlow.
```

Say:

> "Branching strategy depends on the delivery model. I have worked with feature-branch workflows and understand release, hotfix and trunk-based concepts."

---

# SECTION 291 — HOTFIX

## 345. Hotfix Concept

Production issue:

```text
main
 ↓
hotfix/payment-validation
 ↓
fix
 ↓
test
 ↓
PR
 ↓
main
 ↓
release/deployment
```

Depending on branching strategy, the fix may also need to be propagated to another active branch.

---

# SECTION 292 — SSH BASICS

## 346. What Is SSH?

SSH means:

```text
Secure Shell
```

It provides encrypted remote communication.

Common uses:

```text
remote server access
Git authentication
cloud VM access
administration
secure command execution
```

---

# SECTION 293 — HTTPS VS SSH FOR GIT

## 347. HTTPS

Git remote example:

```text
https://github.com/<user>/<repo>.git
```

Our current project uses:

```text
HTTPS
```

and we authenticated using GitHub CLI.

---

## 348. SSH

SSH remote concept:

```text
git@github.com:<user>/<repo>.git
```

Authentication uses SSH keys rather than normal web credentials.

Both approaches are valid.

Do not change our current project to SSH merely for the sake of changing it.

---

# SECTION 294 — SSH KEY CONCEPT

## 349. Public/Private Key Pair

SSH commonly uses:

```text
Private Key
+
Public Key
```

Private key:

```text
stays secret
```

Public key:

```text
can be registered with the remote service/server
```

Conceptually:

```text
Local Private Key
        ↓
cryptographic proof
        ↓
Remote Service
        ↑
registered Public Key
```

Never publish private SSH keys.

---

# SECTION 295 — COMMON SSH FILES

## 350. SSH Directory

Usually:

```text
~/.ssh/
```

may contain files such as:

```text
id_ed25519
id_ed25519.pub
known_hosts
config
```

Typical convention:

```text
id_ed25519
→ private key

id_ed25519.pub
→ public key
```

Never commit:

```text
private SSH key
```

to Git.

---

# SECTION 296 — ssh COMMAND

## 351. Remote Server Concept

Example:

```bash
ssh user@server
```

Conceptually:

```text
Local Terminal
     ↓
encrypted SSH connection
     ↓
Remote Linux Server
```

Once connected, commands execute on the remote machine.

This becomes relevant when working with cloud VMs such as AWS EC2.

---

# SECTION 297 — SCP

## 352. Secure Copy

`scp` can transfer files over SSH.

Conceptual example:

```bash
scp report.html user@server:/tmp/
```

This copies a local file to a remote server.

For modern production workflows, artifacts are often handled by:

```text
CI/CD
artifact repositories
object storage
deployment tooling
```

rather than manually copying files.

Still, understanding `scp` is useful.

---

# SECTION 298 — CI/CD BASICS

## 353. CI

CI means:

```text
Continuous Integration
```

Developers frequently integrate code and automated checks validate changes.

Concept:

```text
Commit
 ↓
Push
 ↓
Pipeline
 ↓
Build
 ↓
Tests
 ↓
Quality Feedback
```

---

## 354. CD

CD may refer to:

```text
Continuous Delivery
```

or:

```text
Continuous Deployment
```

Continuous Delivery:

```text
software remains deployable
deployment may require approval
```

Continuous Deployment:

```text
validated changes may automatically reach production
```

The exact implementation varies.

---

# SECTION 299 — WHY SDET OWNS PART OF CI QUALITY

## 355. SDET Contribution

SDETs commonly contribute:

```text
test execution commands
pipeline test stages
test selection
environment configuration
report generation
failure diagnostics
quality gates
parallel execution
flaky-test analysis
```

CI/CD is therefore directly relevant to SDET work.

---

# SECTION 300 — GITHUB ACTIONS

## 356. What Is GitHub Actions?

GitHub Actions is GitHub's workflow automation platform.

Workflow files live under:

```text
.github/workflows/
```

and are normally written in YAML.

Our roadmap includes GitHub Actions later.

---

# SECTION 301 — FUTURE PROJECT PIPELINE

## 357. Possible Architecture

Later our project may use:

```text
GitHub Push / Pull Request
          ↓
GitHub Actions
          ↓
Backend Build
          ↓
API Automation
          ↓
Frontend Build
          ↓
Playwright Tests
          ↓
Reports
```

Eventually:

```text
Performance Tests
AWS Deployment
```

can be incorporated appropriately.

This is FUTURE architecture, not current implementation.

---

# SECTION 302 — CI RUNNER

## 358. What Is a Runner?

A runner is the machine/environment executing CI jobs.

It may be:

```text
GitHub-hosted
self-hosted
Linux VM
Windows VM
macOS VM
container
```

This explains why tests may behave differently in CI.

---

# SECTION 303 — TYPICAL CI JOB

## 359. Concept

A job may perform:

```text
Checkout repository
↓
Install Java
↓
Install dependencies
↓
Start required services
↓
Build application
↓
Execute tests
↓
Generate report
↓
Upload artifacts
```

Every step is essentially automation running on a machine.

---

# SECTION 304 — CI ENVIRONMENT VARIABLES

## 360. Secrets in CI

Locally we use:

```text
.env
```

In CI, secrets should normally come from the CI platform's secure secret/configuration mechanism.

Conceptually:

```text
CI Secret Store
       ↓
Environment Variable
       ↓
Test/Application
```

Do not commit CI credentials into YAML.

---

# SECTION 305 — CI FAILURE: COMMAND NOT FOUND

## 361. Scenario

Pipeline says:

```text
mvn: command not found
```

Possible causes:

```text
Maven not installed
PATH issue
wrong runner image
setup step missing
```

Investigation:

```bash
which mvn
mvn -version
```

In our backend, Maven Wrapper can reduce dependence on globally installed Maven:

```bash
./mvnw
```

---

# SECTION 306 — WHY MAVEN WRAPPER HELPS

## 362. `mvn` vs `./mvnw`

```bash
mvn test
```

depends on system Maven installation.

```bash
./mvnw test
```

uses the project's Maven Wrapper mechanism.

Benefits include:

```text
consistent Maven version
easier CI setup
less dependency on machine configuration
```

The Java runtime is still required.

---

# SECTION 307 — CI FAILURE: PERMISSION DENIED

## 363. Scenario

Pipeline:

```text
./run-tests.sh: Permission denied
```

Investigate file permissions.

Locally:

```bash
ls -l run-tests.sh
```

Ensure executable bit is tracked appropriately:

```bash
chmod +x run-tests.sh
git add run-tests.sh
git commit -m "fix: make test runner executable"
```

Git tracks executable-bit information for files.

---

# SECTION 308 — CI FAILURE: ENVIRONMENT VARIABLE MISSING

## 364. Scenario

Pipeline says:

```text
ADMIN_PASSWORD is required
```

Do NOT hardcode it into source code.

Check:

```text
CI secret configured?
Correct variable name?
Secret exposed to this workflow/job?
Environment restrictions?
```

Debug safely:

```bash
if [ -n "$ADMIN_PASSWORD" ]; then
  echo "ADMIN_PASSWORD configured"
else
  echo "ADMIN_PASSWORD missing"
fi
```

Never print the actual password.

---

# SECTION 309 — CI FAILURE: TESTS PASS LOCALLY

## 365. Scenario

Classic issue:

```text
Local
→ PASS

CI
→ FAIL
```

Compare:

```text
OS
Java version
Maven version
Node version
Browser version
Timezone
Locale
Environment variables
Database
Network
Filesystem
Permissions
Test data
Parallelism
```

Do not immediately mark the test:

```text
flaky
```

---

# SECTION 310 — CI FAILURE: PATH

## 366. Scenario

Local script assumes:

```text
/Users/myname/project/file
```

CI does not have that path.

Bad:

```text
hardcoded absolute developer path
```

Better:

```text
relative paths
repository-root paths
environment configuration
script-relative paths
```

Our use of:

```bash
dirname "$0"
```

is an example of making shell scripts less dependent on launch location.

---

# SECTION 311 — CI FAILURE: CASE SENSITIVITY

## 367. Scenario

A file is:

```text
LoginTests.java
```

but code references:

```text
logintests.java
```

Some local filesystems may hide this mistake.

Linux CI may be case-sensitive.

Therefore a test can:

```text
work locally
fail in Linux CI
```

File/path casing should be exact.

---

# SECTION 312 — CI FAILURE: TIMEZONE

## 368. Scenario

A test assumes local date/time.

Local:

```text
IST
```

CI:

```text
UTC
```

Now date assertions fail.

Better design:

```text
explicit timezone handling
controlled clock when possible
avoid environment-dependent date assumptions
```

This is especially important for:

```text
booking
payments
orders
expiry
scheduled jobs
```

---

# SECTION 313 — CI FAILURE: PARALLEL TESTS

## 369. Scenario

Tests pass sequentially but fail in parallel.

Possible causes:

```text
shared test data
static mutable state
same user account
same cart
same product
same database records
order dependency
non-thread-safe utilities
```

Our use of unique product names and cleanup utilities helps reduce test-data collision.

---

# SECTION 314 — CI ARTIFACTS

## 370. What Is an Artifact?

A CI artifact is output preserved from a pipeline run.

Examples:

```text
Allure results
HTML reports
screenshots
videos
logs
JUnit XML
performance reports
```

Artifacts help investigate failures after the runner has finished.

---

# SECTION 315 — TEST REPORTING IN CI

## 371. Future Flow

For our API framework:

```text
REST Assured + TestNG
        ↓
Allure Results
        ↓
CI
        ↓
Report / Artifact
```

Our current Allure reporting already produces:

```text
request attachment
response attachment
environment metadata
sanitized secrets
```

This gives us a strong base for future CI integration.

---

# SECTION 316 — JENKINS

## 372. What Is Jenkins?

Jenkins is an automation server commonly used for:

```text
CI/CD
scheduled tests
build pipelines
deployments
regression execution
```

A Jenkins pipeline can execute shell commands similarly to a local terminal.

---

# SECTION 317 — JENKINS AGENT

## 373. Controller and Agent Concept

Conceptually:

```text
Jenkins Controller
       ↓
schedules work
       ↓
Jenkins Agent
       ↓
executes build/tests
```

Agents can have different:

```text
OS
Java
browser
tools
permissions
network access
```

This is why environment troubleshooting matters.

---

# SECTION 318 — JENKINS WORKSPACE

## 374. Workspace Concept

Jenkins checks project files into a workspace.

Never assume the path is:

```text
your local Mac path
```

Instead scripts should operate relative to:

```text
workspace/repository
```

This is another reason hardcoded local paths are poor automation design.

---

# SECTION 319 — PIPELINE STAGES

## 375. Example

A test pipeline might contain:

```text
Checkout
↓
Build
↓
Start Dependencies
↓
API Tests
↓
UI Tests
↓
Publish Results
↓
Archive Artifacts
```

Clear stages make failure diagnosis easier.

---

# SECTION 320 — FAIL FAST VS CONTINUE

## 376. Pipeline Design Decision

Suppose:

```text
Backend build fails
```

Should UI tests run?

Probably not if the application cannot be built/deployed.

Therefore some stages should fail fast.

But sometimes independent test/reporting stages should still run.

Pipeline behavior should reflect dependency relationships.

---

# SECTION 321 — EXIT CODE IN CI

## 377. Why It Matters

Suppose:

```bash
./run-tests.sh
```

returns:

```text
0
```

CI normally interprets success.

If it returns non-zero:

```text
pipeline step may fail
```

This is why test runners must propagate failures correctly.

A script that hides a failed test and exits `0` can create false pipeline success.

---

# SECTION 322 — BAD SHELL SCRIPT EXAMPLE

## 378. Problem

Imagine:

```bash
mvn test
echo "Tests completed"
exit 0
```

If designed carelessly, forcing:

```bash
exit 0
```

can hide test failure.

CI might become green despite failing tests.

That destroys trust in automation.

---

# SECTION 323 — QUALITY GATE

## 379. What Is a Quality Gate?

A quality gate determines whether software is allowed to proceed.

Possible checks:

```text
Build successful
Unit tests pass
API smoke tests pass
Critical UI tests pass
No blocker vulnerabilities
Code-quality threshold met
```

The exact gate depends on product risk and delivery strategy.

---

# SECTION 324 — SMOKE VS REGRESSION IN PIPELINE

## 380. Practical Strategy

Example:

```text
Pull Request
→ fast smoke suite

Merge to main
→ broader integration suite

Nightly
→ full regression

Pre-release
→ release validation
```

This keeps developer feedback fast without sacrificing broader coverage.

---

# SECTION 325 — PRODUCTION TROUBLESHOOTING MINDSET

## 381. Scenario

Monitoring reports:

```text
Checkout API failures increased
```

Do not immediately:

```text
restart production
```

First determine:

```text
When did it start?
What changed?
Which endpoint?
Which environment?
Which users?
Which status code?
Any deployment?
Any dependency issue?
```

---

# SECTION 326 — INCIDENT INVESTIGATION FLOW

## 382. Example

```text
Alert
 ↓
Check impact
 ↓
Check recent deployment/change
 ↓
Check logs
 ↓
Check application health
 ↓
Check dependencies
 ↓
Check database
 ↓
Check network
 ↓
Identify root cause
 ↓
Mitigate
 ↓
Verify recovery
 ↓
Document
```

In a real organization, follow incident-management and access procedures.

---

# SECTION 327 — LOG CORRELATION

## 383. Request ID

Modern services may attach identifiers such as:

```text
requestId
correlationId
traceId
```

Suppose failed API response contains:

```text
traceId=abc123
```

Search logs:

```bash
grep "abc123" application.log
```

This is much more precise than:

```bash
grep "ERROR"
```

across millions of lines.

---

# SECTION 328 — TIMESTAMP FILTERING

## 384. Why Time Matters

If failure occurred at:

```text
14:35
```

start investigation around that period.

Correlate:

```text
test timestamp
API logs
application logs
database logs
deployment events
monitoring
```

Good incident investigation is evidence-driven.

---

# SECTION 329 — RECENT DEPLOYMENT CHECK

## 385. Important Question

If failures suddenly start after deployment:

```text
What changed?
```

Git can help:

```bash
git log --oneline
```

For specific commit:

```bash
git show <commit>
```

For range:

```bash
git diff <old-commit>..<new-commit>
```

This connects:

```text
runtime failure
```

with:

```text
source change
```

---

# SECTION 330 — git bisect CONCEPT

## 386. Finding the Commit That Introduced a Problem

`git bisect` can perform a binary search through commit history.

Concept:

```text
Known good commit
Known bad commit
        ↓
Git selects midpoint
        ↓
test it
        ↓
mark good/bad
        ↓
repeat
        ↓
identify first bad commit
```

Commands conceptually:

```bash
git bisect start
git bisect bad
git bisect good <known-good-commit>
```

Then test selected revisions and mark:

```bash
git bisect good
```

or:

```bash
git bisect bad
```

Finish:

```bash
git bisect reset
```

This can be extremely useful when many commits occurred between known good and bad versions.

---

# SECTION 331 — AUTOMATED BISECT

## 387. Advanced Concept

Git bisect can also execute a script automatically:

```bash
git bisect run <test-script>
```

If the script reliably returns:

```text
0 → good
non-zero → bad
```

Git can automate much of the search.

This is a powerful connection between:

```text
Git
Linux exit codes
automation
```

---

# SECTION 332 — RELEASE VALIDATION

## 388. SDET Responsibilities

Before release, SDET may validate:

```text
correct build/version
deployment success
critical APIs
critical UI journeys
database migrations
configuration
integrations
security-critical flows
regression results
known issues
```

The goal is not:

```text
run every possible test blindly
```

but:

```text
provide evidence about release quality and risk
```

---

# SECTION 333 — ROLLBACK

## 389. What Is Rollback?

If a release creates severe problems, an organization may restore a previous stable version or configuration.

Conceptually:

```text
New Version
    ↓
Critical Failure
    ↓
Rollback Decision
    ↓
Previous Stable Version
```

Rollback implementation depends on deployment architecture.

Git revert and deployment rollback are related concepts but are NOT automatically the same thing.

---

# SECTION 334 — GIT REVERT VS DEPLOYMENT ROLLBACK

## 390. Important Difference

```text
git revert
→ source-control operation
→ creates a new commit reversing code

deployment rollback
→ runtime/deployment operation
→ restore previous deployed version/state
```

A team may use one, both, or another strategy depending on the incident.

---

# SECTION 335 — DATABASE MIGRATION RISK

## 391. Why Rollback Can Be Hard

Application rollback may be complicated if the new release changed:

```text
database schema
data format
events
external integrations
```

Therefore release planning should consider:

```text
backward compatibility
migration strategy
rollback strategy
```

This is important for integration and release testing.

---

# SECTION 336 — SSH PRODUCTION SAFETY

## 392. Do Not Treat Production Like Localhost

On local machine we may freely:

```text
restart app
delete test data
kill process
modify files
```

In production:

```text
access controls
change management
incident process
audit requirements
least privilege
```

matter.

Never run destructive commands simply because you have shell access.

---

# SECTION 337 — READ-ONLY FIRST

## 393. Production Troubleshooting Principle

Prefer inspection first:

```bash
pwd
ls
ps
lsof
tail
grep
df
```

before modification commands such as:

```text
rm
kill
chmod
restart
configuration change
```

Evidence before action.

---

# SECTION 338 — SENIOR SDET CI/CD INTERVIEW QUESTION

## 394. How Do You Integrate Automation Into CI/CD?

### Strong Answer

> "I organize automation by execution purpose rather than running the entire suite on every change. Fast smoke and critical integration tests can run on pull requests, while broader regression suites can run after merge, nightly or before releases. The pipeline should externalize environment configuration, fail correctly when tests fail, publish useful reports and artifacts, and make failures easy to diagnose."

---

# SECTION 339 — INTERVIEW: PR FAILS IN CI

## 395. What Do You Do?

> "I first identify which pipeline stage failed and inspect the exact error and artifacts. If it is a test failure, I classify whether it is an application, automation, data, environment or infrastructure problem. For CI-only failures I compare runtime versions, environment variables, paths, permissions, dependencies and parallel execution with my local environment before changing the test."

---

# SECTION 340 — INTERVIEW: MERGE CONFLICT

## 396. Strong Answer

> "I use `git status` to identify conflicted files, inspect both versions and understand the intended final behavior rather than blindly selecting one side. After resolving the file I stage it and continue the merge or rebase. If I realize the operation itself was incorrect, I can abort it before proceeding."

---

# SECTION 341 — INTERVIEW: CHERRY-PICK

## 397. Strong Answer

> "`git cherry-pick` applies a specific commit onto the current branch. It can be useful when a particular fix needs to be propagated without merging all changes from another branch, for example a targeted release fix. I use it carefully because the applied commit receives a new identity and conflicts may still need resolution."

---

# SECTION 342 — INTERVIEW: TAGS

## 398. Strong Answer

> "Git tags identify important commits, commonly releases or milestones. Unlike a branch, which moves as new commits are added, a release tag is normally intended to remain associated with a specific version."

---

# SECTION 343 — INTERVIEW: SSH

## 399. Strong Answer

> "SSH provides encrypted remote communication and is commonly used for remote Linux access and Git authentication. It can use public/private key authentication, where the private key must remain protected and the public key can be registered with the remote service."

---

# SECTION 344 — INTERVIEW: PIPELINE IS GREEN BUT TEST ACTUALLY FAILED

## 400. Possible Cause

One important possibility is incorrect exit-code handling.

Example:

```text
test command fails
↓
script ignores failure
↓
script exits 0
↓
pipeline becomes green
```

### Strong Answer

> "I would inspect how the test command's exit status is propagated through the shell script and pipeline. A quality pipeline must not convert genuine test failures into successful job exits merely because reporting or cleanup commands ran afterward."

---

# SECTION 345 — INTERVIEW: HOW DO YOU REDUCE PIPELINE TIME?

## 401. Strong Answer

Possible strategies:

```text
Test pyramid
Smoke suite on PR
Parallel execution
Tagging
Risk-based selection
Avoid duplicate tests
API instead of UI where appropriate
Reusable environment setup
Caching dependencies
Efficient test data
Remove unnecessary waits
```

### Interview Answer

> "I first analyze where pipeline time is being spent. I keep most validation at API or integration level, run only critical smoke tests on fast feedback paths, parallelize independent tests, eliminate duplicate coverage and unnecessary waits, and reserve full regression for appropriate triggers such as nightly or pre-release runs."

---

# SECTION 346 — INTERVIEW: FLAKY TEST IN CI

## 402. Investigation

Check:

```text
Is failure random or deterministic?
Shared data?
Parallelism?
Timing?
Network?
Environment?
Selector?
Async behavior?
Dependency?
```

Do not immediately add:

```text
sleep
retry
```

Fix root cause where possible.

---

# SECTION 347 — INTERVIEW: HOW DO YOU HANDLE SECRETS IN CI?

## 403. Strong Answer

> "I keep secrets outside source control and inject them through the CI platform's protected secret-management mechanism or an approved external secret manager. Tests read them as environment variables or runtime configuration. I avoid printing secret values in logs and sanitize sensitive request and response data in reports."

This maps directly to our Allure redaction design.

---

# SECTION 348 — OUR PROJECT: CURRENT VS FUTURE

## 404. Current

Already implemented:

```text
Spring Boot
PostgreSQL
Dockerized PostgreSQL
JWT authentication
RBAC
Products
Cart
Orders
Mock Payment
REST Assured
TestNG
JDBC validation
JSON schema validation
48 API tests
Environment switching
Allure
Sensitive-data sanitization
Swagger/OpenAPI
Git
GitHub public repository
```

---

## 405. Future

Planned:

```text
React + TypeScript
Playwright + TypeScript
UI + API hybrid testing
Full Dockerization
GitHub Actions
k6
AWS
```

Potential later evolution:

```text
Redis
Kafka
```

Interview rule:

```text
Never present planned technology as already implemented.
```

---

# SECTION 349 — OUR FUTURE CI/CD FLOW

## 406. Target Architecture

```text
Developer
   ↓
Feature Branch
   ↓
Pull Request
   ↓
GitHub Actions
   ↓
Build Backend
   ↓
Start Dependencies
   ↓
API Tests
   ↓
Build Frontend
   ↓
Playwright Tests
   ↓
Reports
   ↓
Quality Gate
   ↓
Merge
```

Later:

```text
main
 ↓
AWS deployment
 ↓
post-deployment validation
```

This is a target design, not current functionality.

---

# SECTION 350 — GIT + CI/CD CONNECTION

## 407. Complete Picture

```text
Git
→ tracks changes

GitHub
→ hosts repository

Branch
→ isolates work

Pull Request
→ review boundary

CI
→ automatically validates change

Test Automation
→ quality evidence

Report
→ failure diagnosis

Quality Gate
→ controls progression

CD
→ delivers/deploys validated software
```

These technologies are not separate interview topics.

They form one engineering workflow.

---

# SECTION 351 — SENIOR SDET RELEASE STORY

## 408. Interview Structure

When asked:

```text
How do you ensure release quality?
```

A strong structure is:

```text
Requirement Risk
     ↓
Test Strategy
     ↓
Automation Coverage
     ↓
CI Execution
     ↓
Defect Analysis
     ↓
Regression
     ↓
Release Validation
     ↓
Production Monitoring / Feedback
```

### Example Answer

> "I start by identifying high-risk business flows and integration points, then ensure the appropriate coverage exists across API, UI, integration and database layers. I integrate critical tests into CI for fast feedback and use broader regression for release validation. Before release I review failures, known defects and environment-specific risks rather than relying only on a pass percentage."

---

# SECTION 352 — ADVANCED GIT COMMAND CHEAT SHEET

## 409. Feature Branch

```bash
git switch main
git pull
git switch -c feature/example
```

---

## 410. Push Branch

```bash
git push -u origin feature/example
```

---

## 411. History

```bash
git log --oneline --graph --decorate --all
```

---

## 412. Cherry-Pick

```bash
git cherry-pick <commit>
```

Continue conflict resolution:

```bash
git cherry-pick --continue
```

Abort:

```bash
git cherry-pick --abort
```

---

## 413. Tag

```bash
git tag -a v1.0.0 -m "Release v1.0.0"
```

Push:

```bash
git push origin v1.0.0
```

---

## 414. Compare Versions

```bash
git diff <old>..<new>
```

---

## 415. Inspect Commit

```bash
git show <commit>
```

---

## 416. Bisect

```bash
git bisect start
git bisect bad
git bisect good <known-good>
```

Finish:

```bash
git bisect reset
```

---

# SECTION 353 — CI/LINUX CHEAT SHEET

## 417. Environment

```bash
pwd
ls -la
uname -a
```

---

## 418. Tool Versions

```bash
java -version
mvn -version
node --version
npm --version
git --version
```

---

## 419. Processes

```bash
ps aux
```

---

## 420. Port

```bash
lsof -i :8080
```

---

## 421. HTTP

```bash
curl -i http://localhost:8080/v3/api-docs
```

---

## 422. Logs

```bash
grep -Ei "error|exception|failed" application.log
```

```bash
tail -f application.log
```

---

## 423. Disk

```bash
df -h
```

```bash
du -sh .
```

---

# SECTION 354 — INCIDENT DEBUGGING CHEAT SHEET

## 424. Think in Layers

```text
Test
 ↓
Request
 ↓
Network
 ↓
Application
 ↓
Authentication / Authorization
 ↓
Business Logic
 ↓
Database
 ↓
External Dependency
 ↓
Infrastructure
```

Ask:

```text
At which layer did expected behavior stop?
```

---

# SECTION 355 — TEN SENIOR SDET SCENARIOS TO PRACTICE

## 425. Scenario Questions

Be able to answer these without memorized textbook language:

```text
1. Tests pass locally but fail in CI. What do you check?

2. A PR pipeline suddenly takes twice as long. How do you investigate?

3. API test receives 401. How do you debug it?

4. USER gets 200 on an ADMIN endpoint. What does that indicate?

5. A real password was pushed to GitHub. What do you do?

6. Two branches have a merge conflict. How do you resolve it?

7. A production defect started after a deployment. How do you isolate the change?

8. Tests fail only when executed in parallel. What could cause it?

9. Pipeline is green even though Maven reported test failures. Why?

10. A critical fix needs to reach a release branch without bringing unrelated features. What Git option could help?
```

---

# SECTION 356 — RAPID ANSWERS

## 426. Local Pass / CI Fail

```text
Compare environment, versions, variables, filesystem, dependencies,
data, timezone, permissions and parallelism.
```

## 427. Pipeline Slow

```text
Measure stage duration, identify bottleneck, check test growth,
parallelism, dependencies, waits and duplicate coverage.
```

## 428. 401

```text
Authentication/token issue.
```

## 429. USER Gets ADMIN Access

```text
Potential authorization/RBAC defect — security-critical.
```

## 430. Secret Pushed

```text
Rotate/revoke first, remove exposure, investigate history,
clean history when required and prevent recurrence.
```

## 431. Merge Conflict

```text
status → inspect → understand both changes → resolve → stage → continue.
```

## 432. Defect After Deployment

```text
correlate timeline → logs → deployment → Git diff/history → dependencies.
```

## 433. Parallel Failure

```text
shared state, data collision, thread safety, environment contention.
```

## 434. Green Pipeline With Failed Tests

```text
incorrect exit-code propagation or failure handling.
```

## 435. Specific Release Fix

```text
cherry-pick may be appropriate depending on branching strategy.
```

---

# SECTION 357 — FINAL PROFESSIONAL MINDSET

## 436. Tools Are Connected

A Senior SDET should not think:

```text
Selenium is one skill
API is another skill
Git is another skill
Linux is another skill
CI/CD is another skill
```

Think:

```text
Code
 ↓
Git
 ↓
Pull Request
 ↓
CI
 ↓
Application Environment
 ↓
API / UI / DB
 ↓
Automation
 ↓
Reports
 ↓
Quality Decision
```

The value comes from understanding how the system fits together.

---

# SECTION 358 — WHAT NOT TO CLAIM IN INTERVIEWS

## 437. Be Precise

Do not say:

```text
"I am an AWS expert"
```

before implementing AWS.

Do not say:

```text
"I built Kafka architecture"
```

before adding Kafka.

Do not say:

```text
"I have production Linux administration experience"
```

if your experience is mainly development/test environments.

Instead say:

> "I am comfortable with practical Linux troubleshooting for automation, CI/CD and application environments."

And:

> "I am currently extending my end-to-end SDET project toward GitHub Actions, Playwright and AWS."

This is both credible and strong.

---

# SECTION 359 — PART 5 FINAL MEMORY MAP

## 438. Git Collaboration

```text
Main
 ↓
Feature Branch
 ↓
Commit
 ↓
Push
 ↓
Pull Request
 ↓
CI
 ↓
Review
 ↓
Approval
 ↓
Merge
 ↓
Release
```

---

## 439. CI/CD

```text
Trigger
 ↓
Runner
 ↓
Checkout
 ↓
Setup
 ↓
Build
 ↓
Test
 ↓
Report
 ↓
Quality Gate
 ↓
Deploy
```

---

## 440. Production Investigation

```text
Alert
 ↓
Impact
 ↓
Timeline
 ↓
Logs
 ↓
Recent Change
 ↓
Application
 ↓
Dependencies
 ↓
Database
 ↓
Infrastructure
 ↓
Root Cause
 ↓
Mitigation
 ↓
Verification
```

---

# END OF PART 5 — ADVANCED GIT COLLABORATION, CI/CD & PRODUCTION TROUBLESHOOTING

At this stage these notes cover:

- Linux fundamentals
- Shell and terminal
- Filesystem commands
- Permissions
- Processes and ports
- Environment variables
- Networking and curl
- Logs
- Docker troubleshooting
- Git fundamentals
- Staging and commits
- Branching
- Merge and rebase
- Stash
- Restore/reset/revert
- Reflog
- GitHub remotes and authentication
- Hands-on Git/Linux exercises
- Pull Requests
- Code reviews
- Cherry-pick
- Tags and releases
- SSH concepts
- CI/CD
- GitHub Actions concepts
- Jenkins concepts
- CI troubleshooting
- Production-style troubleshooting
- Senior SDET interview scenarios

---

# PART 6 — FINAL LINUX, GIT & CI/CD INTERVIEW REVISION FOR SENIOR SDET

# SECTION 360 — HOW TO USE THIS PART

This part is designed for:

```text
Interview revision
Rapid recall
Scenario practice
Command explanation
Senior SDET discussion
```

Do not memorize every sentence.

Understand:

```text
What is the problem?
Which layer is failing?
Which command helps?
What is the safest next step?
```

---

# SECTION 361 — LINUX INTERVIEW QUESTIONS

## 441. What is Linux?

### Short Answer

> Linux is an open-source Unix-like operating system widely used for servers, containers, cloud environments and CI/CD systems.

### SDET Relevance

As an SDET, Linux is useful for:

```text
running tests
checking logs
managing files
debugging services
working with Docker
CI/CD pipelines
remote environments
```

---

## 442. What is a shell?

> A shell is a command interpreter that allows users to interact with the operating system through commands and scripts.

Examples:

```text
bash
zsh
sh
```

---

## 443. What is the difference between terminal and shell?

```text
Terminal
→ application/interface where commands are entered

Shell
→ program that interprets those commands
```

Example:

```text
Terminal app
        ↓
zsh shell
        ↓
Linux/macOS operating system
```

---

## 444. What does `pwd` do?

```bash
pwd
```

Shows:

```text
current working directory
```

Useful when:

```text
script cannot find file
wrong folder
relative path issue
```

---

## 445. What does `ls -la` do?

```bash
ls -la
```

Shows:

```text
files
directories
hidden files
permissions
ownership
```

`-l`:

```text
long format
```

`-a`:

```text
include hidden files
```

---

## 446. Why do files starting with `.` matter?

Examples:

```text
.env
.gitignore
.git
.github
```

They are hidden by default in Unix-like systems.

---

## 447. Difference between `>` and `>>`?

```text
>
→ overwrite

>>
→ append
```

Example:

```bash
echo "hello" > file.txt
```

overwrites.

```bash
echo "world" >> file.txt
```

adds to existing content.

---

## 448. What is a pipe?

```text
|
```

A pipe sends output of one command as input to another.

Example:

```bash
ps aux | grep java
```

Flow:

```text
list processes
↓
filter Java processes
```

---

## 449. What does `grep` do?

`grep` searches text using patterns.

Example:

```bash
grep -i "error" application.log
```

Useful for:

```text
logs
CI output
configuration
test reports
```

---

## 450. Difference between `grep` and `find`?

```text
grep
→ searches text/content

find
→ searches files/directories
```

Example:

```bash
find . -name "*.log"
```

vs:

```bash
grep "ERROR" app.log
```

---

## 451. What does `tail -f` do?

```bash
tail -f application.log
```

continuously follows new log entries.

Useful while reproducing an issue.

---

## 452. What is an environment variable?

An environment variable stores configuration available to processes.

Example:

```bash
export TEST_ENV=local
```

Then:

```bash
echo "$TEST_ENV"
```

In automation, environment variables help avoid hardcoded configuration.

---

## 453. Why should passwords not be hardcoded?

Because hardcoded credentials can:

```text
enter Git history
appear in logs
be shared accidentally
require code changes for rotation
```

Use:

```text
environment variables
CI secret store
approved secret manager
```

---

## 454. What does `source` do?

Example:

```bash
source .env
```

It executes the file in the current shell context.

This allows variables or shell commands inside the file to affect the current shell.

---

## 455. What does `chmod +x` do?

```bash
chmod +x run-tests.sh
```

adds executable permission.

This allows:

```bash
./run-tests.sh
```

to be executed directly.

---

## 456. What does `chmod 755` mean?

```text
Owner  → rwx
Group  → r-x
Others → r-x
```

Based on:

```text
r = 4
w = 2
x = 1
```

---

## 457. Why not use `chmod 777`?

Because it gives:

```text
read
write
execute
```

to everyone.

It violates least-privilege principles in most cases.

---

## 458. What is a process?

A process is a running instance of a program.

Examples:

```text
Java application
PostgreSQL
browser
Docker service
test runner
```

---

## 459. How do you find a process?

Examples:

```bash
ps aux
```

or:

```bash
ps aux | grep java
```

or:

```bash
pgrep -fl java
```

---

## 460. How do you find which process is using port 8080?

```bash
lsof -i :8080
```

This is useful when Spring Boot says:

```text
Port 8080 already in use
```

---

## 461. Difference between `kill` and `kill -9`?

```text
kill PID
→ requests normal termination

kill -9 PID
→ forcefully terminates process
```

Prefer graceful termination first.

---

## 462. What is localhost?

```text
localhost
```

means:

```text
current machine
```

Common loopback IP:

```text
127.0.0.1
```

---

## 463. What is a port?

A port identifies a specific network service endpoint.

Example:

```text
localhost:8080
```

In our project:

```text
Spring Boot → 8080
PostgreSQL → 5432
```

---

## 464. What does `curl` do?

`curl` sends network requests from the command line.

Example:

```bash
curl -i http://localhost:8080/v3/api-docs
```

Useful for:

```text
API debugging
connectivity validation
HTTP testing
CI health checks
```

---

## 465. Difference between connection refused and HTTP 401?

```text
Connection refused
→ server/service may not be listening

401
→ server responded but authentication failed
```

Very important troubleshooting distinction.

---

## 466. Difference between 401 and 403?

```text
401
→ authentication missing/invalid

403
→ authenticated but not authorized
```

In our project:

```text
USER POST product
→ 403

missing/invalid JWT
→ 401
```

---

## 467. What does `df -h` do?

```bash
df -h
```

shows filesystem disk usage.

Useful when:

```text
CI runner runs out of disk
Docker builds fail
reports cannot be created
```

---

## 468. What does `du -sh` do?

```bash
du -sh .
```

shows approximate size of a directory.

---

## 469. What does `uname -m` show?

```bash
uname -m
```

shows system architecture.

Examples:

```text
arm64
aarch64
x86_64
```

Useful for choosing correct binaries and Docker images.

---

# SECTION 362 — GIT INTERVIEW QUESTIONS

## 470. What is Git?

> Git is a distributed version-control system used to track source-code changes and support collaboration.

---

## 471. What is GitHub?

> GitHub is a platform that hosts Git repositories and provides collaboration features such as pull requests, code reviews and CI/CD integration.

Git and GitHub are not the same thing.

---

## 472. What is a Git repository?

A Git repository is a project whose history is tracked by Git.

It contains:

```text
working files
+
.git metadata
```

---

## 473. What is the working directory?

The working directory contains the files you are currently editing.

---

## 474. What is the staging area?

The staging area contains changes selected for the next commit.

Command:

```bash
git add <file>
```

moves intended changes toward the next commit.

---

## 475. What is a commit?

A commit is a snapshot of staged project changes with metadata and a unique hash.

Example:

```bash
git commit -m "test: add payment validation"
```

---

## 476. What does `git status` do?

```bash
git status
```

shows repository state, including:

```text
current branch
modified files
staged files
untracked files
merge/rebase state
```

This should be the first command when confused.

---

## 477. What does `git diff` show?

```bash
git diff
```

shows unstaged modifications.

---

## 478. What does `git diff --cached` show?

```bash
git diff --cached
```

shows staged changes that would enter the next commit.

---

## 479. Why review staged changes before commit?

To verify:

```text
only intended files
no secrets
no generated files
no accidental changes
```

This is especially important before publishing public repositories.

---

## 480. What is `.gitignore`?

`.gitignore` defines untracked files Git should ignore.

Examples:

```text
.env
target/
node_modules/
logs/
```

---

## 481. Does `.gitignore` remove already tracked files?

No.

If a file is already tracked, adding it to `.gitignore` does not automatically remove it from Git history/tracking.

---

## 482. What is a branch?

A branch is a movable pointer to a line of Git development.

Example:

```text
main
feature/react-frontend
```

Branches allow isolated development.

---

## 483. How do you create and switch to a branch?

```bash
git switch -c feature/react-frontend
```

---

## 484. Difference between `git fetch` and `git pull`?

```text
git fetch
→ downloads remote updates without integrating them

git pull
→ fetches and integrates remote changes
```

---

## 485. What is `origin`?

`origin` is the conventional name for the primary remote repository.

Check:

```bash
git remote -v
```

---

## 486. What does `git push -u origin branch` do?

It:

```text
pushes branch to origin
+
sets upstream tracking
```

After that, usually:

```bash
git push
```

is enough.

---

## 487. What is a Pull Request?

A Pull Request proposes changes from one branch into another and allows:

```text
review
CI validation
discussion
approval
controlled merge
```

---

## 488. What is a merge?

Merge combines histories from branches.

Example:

```bash
git merge feature/api-tests
```

---

## 489. What is a merge conflict?

A conflict occurs when Git cannot automatically determine the correct combined change.

Typical flow:

```text
git status
↓
inspect conflict
↓
resolve manually
↓
git add
↓
complete merge
```

---

## 490. What is rebase?

Rebase replays commits onto another base commit.

It can create cleaner linear history but rewrites commit identities.

---

## 491. Why should you be careful rebasing shared branches?

Because rebase rewrites commit history.

Teammates may already depend on the original commits.

---

## 492. What is stash?

`git stash` temporarily saves uncommitted work.

Example:

```bash
git stash push -m "WIP frontend"
```

Restore:

```bash
git stash pop
```

---

## 493. Difference between restore and reset?

Simplified:

```text
git restore
→ primarily works with file content/staging

git reset
→ moves branch/HEAD state and can also affect staging/working tree
```

Use `reset` more carefully.

---

## 494. What is `git revert`?

`git revert` creates a new commit that reverses an earlier commit.

Useful for shared history.

---

## 495. Difference between revert and reset?

```text
revert
→ preserves history and creates reverse commit

reset
→ moves branch pointer and may rewrite local history
```

---

## 496. What is `git reflog`?

```bash
git reflog
```

records local reference movements.

It can help locate commits after:

```text
reset
rebase
branch movement
accidental history changes
```

---

## 497. What is detached HEAD?

Detached HEAD means HEAD points directly to a commit instead of a normal branch.

If valuable work is created there, create a branch to preserve it.

---

## 498. What is cherry-pick?

```bash
git cherry-pick <commit>
```

applies a specific existing commit onto the current branch.

Useful for targeted fixes.

---

## 499. What is a Git tag?

A tag identifies a specific important commit, commonly:

```text
release
version
milestone
```

Example:

```text
v1.0.0
```

---

## 500. What is `git bisect`?

Git bisect uses binary search through commit history to identify the commit that introduced a defect.

---

# SECTION 363 — CI/CD INTERVIEW QUESTIONS

## 501. What is CI?

CI stands for:

```text
Continuous Integration
```

Code changes are integrated frequently and validated automatically.

---

## 502. What is Continuous Delivery?

Software is automatically validated and kept ready for deployment, but production deployment may still require approval.

---

## 503. What is Continuous Deployment?

Validated changes can automatically reach production without a manual deployment approval step.

---

## 504. Why is CI/CD important for SDET?

Because SDET automation provides quality feedback inside the delivery pipeline.

Typical contribution:

```text
smoke tests
API tests
UI tests
quality gates
reports
failure diagnostics
```

---

## 505. What is a CI runner?

A runner is the machine/environment executing CI jobs.

Could be:

```text
Linux VM
container
Windows agent
macOS agent
self-hosted runner
```

---

## 506. Why do tests pass locally but fail in CI?

Possible reasons:

```text
different OS
different versions
missing variables
different timezone
case-sensitive paths
permissions
different data
parallelism
network
dependency availability
```

---

## 507. How do you debug a CI-only failure?

### Strong Answer

> "I first identify the failing pipeline stage and compare the CI environment with local execution, including runtime versions, environment variables, filesystem paths, permissions, dependencies, test data, timezone and parallelism. I inspect logs and artifacts before changing the test."

---

## 508. What are CI artifacts?

Artifacts are preserved outputs from pipeline execution.

Examples:

```text
Allure reports
JUnit XML
logs
screenshots
videos
performance reports
```

---

## 509. Why are exit codes important in CI?

```text
0
→ success

non-zero
→ failure
```

If scripts incorrectly return `0`, genuine failures may appear green.

---

## 510. What is a quality gate?

A quality gate determines whether a change can progress.

Possible conditions:

```text
build success
tests pass
security threshold
code-quality threshold
critical regression pass
```

---

## 511. Should every test run on every PR?

Not necessarily.

A better strategy may be:

```text
PR
→ fast smoke

main
→ broader integration

nightly
→ full regression

release
→ release validation
```

---

## 512. How can you reduce pipeline execution time?

Possible approaches:

```text
test pyramid
parallelization
tagging
risk-based test selection
remove duplicate tests
API over UI where suitable
avoid unnecessary waits
cache dependencies
better test data
```

---

# SECTION 364 — COMMAND-BASED INTERVIEW QUESTIONS

## 513. Explain This Command

```bash
ps aux | grep java
```

Answer:

> It lists running processes and filters entries containing `java`, which is useful for finding Java applications such as Spring Boot processes.

---

## 514. Explain This Command

```bash
lsof -i :8080
```

Answer:

> It shows the process using or listening on port 8080, which is useful when an application cannot start because the port is already occupied.

---

## 515. Explain This Command

```bash
grep -Ei "error|exception|failed" application.log
```

Answer:

> It searches the log case-insensitively using an extended regular expression for common failure keywords such as error, exception or failed.

---

## 516. Explain This Command

```bash
find . -name "*.backup"
```

Answer:

> It recursively searches from the current directory for files or directories whose names match the `.backup` pattern.

---

## 517. Explain This Command

```bash
chmod +x run-tests.sh
```

Answer:

> It adds executable permission to the shell script so it can be invoked directly.

---

## 518. Explain This Command

```bash
curl -i http://localhost:8080/v3/api-docs
```

Answer:

> It sends an HTTP request to the local OpenAPI endpoint and includes response headers, useful for verifying backend connectivity and HTTP response details.

---

## 519. Explain This Command

```bash
git status --short
```

Answer:

> It shows a concise summary of modified, staged and untracked files.

---

## 520. Explain This Command

```bash
git diff --cached
```

Answer:

> It shows changes currently staged for the next commit.

---

## 521. Explain This Command

```bash
git log --oneline --graph --decorate --all
```

Answer:

> It displays a compact visual representation of commit history across branches, including branch and tag references.

---

## 522. Explain This Command

```bash
git restore --staged file.txt
```

Answer:

> It removes `file.txt` from the staging area while keeping the working-directory changes.

---

## 523. Explain This Command

```bash
git stash push -m "WIP frontend"
```

Answer:

> It temporarily stores uncommitted work with a descriptive stash message so the working tree can be cleaned for another task.

---

## 524. Explain This Command

```bash
git revert abc123
```

Answer:

> It creates a new commit that reverses the changes introduced by commit `abc123`, making it suitable for shared history.

---

## 525. Explain This Command

```bash
git reflog
```

Answer:

> It shows local reference movements and can help recover commits after operations such as reset or rebase.

---

## 526. Explain This Command

```bash
git push -u origin feature/react-frontend
```

Answer:

> It pushes the local feature branch to the remote named `origin` and configures that remote branch as the upstream tracking branch.

---

# SECTION 365 — SENIOR SDET SCENARIO QUESTIONS

## 527. Backend is not starting. What do you do?

Possible flow:

```text
Read startup error
↓
check Java version
↓
check port
↓
check environment variables
↓
check database container
↓
inspect actual stack trace
```

Commands:

```bash
java -version
lsof -i :8080
docker ps
```

Do not restart randomly.

---

## 528. API automation shows connection refused. What do you check?

```text
backend running?
correct BASE_URL?
correct TEST_ENV?
correct port?
network reachable?
```

Use:

```bash
curl -i http://localhost:8080/v3/api-docs
```

and:

```bash
lsof -i :8080
```

---

## 529. API returns 401. What do you inspect?

```text
token present?
token valid?
expired?
Authorization format?
login successful?
correct environment?
```

---

## 530. API returns 403 for USER on admin endpoint.

For our project this is expected behavior.

It validates:

```text
RBAC
authorization
```

---

## 531. ADMIN also receives 403. What do you inspect?

```text
admin credentials
JWT role claim
authority mapping
security configuration
environment data
```

---

## 532. Test returns 500. What do you do?

Inspect:

```text
request
response
backend logs
stack trace
database
recent change
```

Do not modify assertion to accept 500.

---

## 533. Tests pass individually but fail together.

Possible causes:

```text
shared data
cleanup issue
test dependency
static state
same account/cart/order
parallel collision
```

---

## 534. Tests fail only in parallel.

Check:

```text
thread safety
shared static state
same test data
database collisions
shared authentication state
resource contention
```

---

## 535. Test is flaky. Should you add retry?

Not immediately.

Investigate:

```text
timing
async behavior
environment instability
selectors
network
test data
dependencies
```

Retry should not hide a real defect.

---

## 536. CI pipeline is green even though tests failed.

Inspect:

```text
exit-code propagation
shell script error handling
pipeline conditions
ignored failures
```

---

## 537. Script works locally but not in Jenkins.

Compare:

```text
working directory
PATH
permissions
environment variables
Java/Maven version
OS
agent configuration
```

---

## 538. Pipeline says `command not found`.

Check:

```bash
which <command>
<command> --version
```

Possible cause:

```text
tool missing
PATH missing
incorrect runner image
setup stage missing
```

---

## 539. Pipeline says permission denied.

Inspect:

```bash
ls -l script.sh
```

If appropriate:

```bash
chmod +x script.sh
```

Commit executable-bit change if needed.

---

## 540. Test date fails only in CI.

Check timezone.

Example:

```text
local → IST
CI → UTC
```

Avoid environment-dependent assumptions.

---

# SECTION 366 — GIT INCIDENT SCENARIOS

## 541. Accidentally staged the wrong file.

Use:

```bash
git restore --staged <file>
```

This unstages while keeping local content.

---

## 542. Accidentally staged `.env`.

Immediately:

```bash
git restore --staged .env
```

Then verify ignore rule:

```bash
git check-ignore -v .env
```

---

## 543. Secret was already committed.

Do NOT only add `.env` to `.gitignore`.

Treat it as exposed:

```text
rotate/revoke secret
remove from source
inspect Git history
clean history when required
verify no other exposure
```

---

## 544. Secret was pushed to GitHub.

First priority:

```text
credential rotation/revocation
```

because removing the file does not make the original credential safe again.

---

## 545. You committed on the wrong branch.

Before doing anything:

```bash
git status
git log -1 --oneline
```

Then determine:

```text
pushed or unpushed?
shared branch?
need to preserve commit?
```

Do not automatically use `reset --hard`.

---

## 546. Push is rejected.

Do:

```bash
git fetch
git log --oneline --graph --decorate --all
```

Understand remote/local divergence.

Do not blindly force push.

---

## 547. Merge conflict occurs.

Flow:

```text
git status
↓
inspect conflicted files
↓
understand both versions
↓
resolve
↓
git add
↓
complete operation
```

---

## 548. Rebase conflict occurs.

After resolving:

```bash
git add <file>
git rebase --continue
```

Abort:

```bash
git rebase --abort
```

---

## 549. Bad commit already exists on shared main.

Usually consider:

```bash
git revert <commit>
```

rather than rewriting shared history.

---

## 550. You think you lost a commit after reset.

Use:

```bash
git reflog
```

Locate previous HEAD/reference state.

---

## 551. Critical fix must move to another branch without unrelated features.

Possible tool:

```bash
git cherry-pick <commit>
```

depending on team strategy.

---

## 552. You need to find which commit introduced a regression.

Possible approach:

```bash
git bisect
```

especially when there are many commits between known good and bad versions.

---

# SECTION 367 — REAL PROJECT INTERVIEW STORIES

## 553. GitHub Portfolio Publishing Story

### Situation

Our SDET commerce automation project had reached a major backend and API automation milestone.

### Actions

Before publishing, we verified:

```text
real .env files ignored
only .env.example staged
generated files excluded
sensitive values not exposed
staged files reviewed
Git status clean after commit
```

Then we:

```text
created GitHub repository
configured origin
authenticated GitHub CLI
pushed main branch
```

### Result

The project became available as a public portfolio repository with:

```text
Spring Boot backend
REST Assured automation
PostgreSQL
JWT/RBAC
Allure
Swagger
documentation
```

### Interview Answer

> "Before publishing my automation project, I treated source-control security as part of the release process. I verified that local environment files were ignored, reviewed staged files and searched for accidental sensitive data before creating the commit and pushing the repository."

---

## 554. Environment Configuration Story

### Problem

Tests need to run against different environments.

Hardcoding:

```text
localhost
QA URL
stage URL
```

inside test classes creates maintenance problems.

### Solution

Framework configuration supports:

```text
local
qa
stage
```

through environment variables.

Concept:

```text
TEST_ENV
      ↓
configuration
      ↓
correct BASE_URL
```

### Interview Answer

> "I separated environment configuration from test logic so the same test suite can target different environments without code changes. This also prepares the framework for CI/CD execution where environment variables can be injected at runtime."

---

## 555. Allure Security Story

### Problem

Attaching complete API requests to reports can expose:

```text
JWT
password
cookies
tokens
```

### Solution

A custom REST Assured filter sanitizes sensitive headers and JSON fields before attaching them to Allure.

Actual request behavior remains unchanged.

### Interview Answer

> "I wanted detailed request and response evidence in Allure without exposing credentials, so I added a sanitization layer that masks sensitive headers and JSON fields before reporting while leaving the actual API request untouched."

---

## 556. RBAC Testing Story

### Requirement

```text
USER
→ read product

ADMIN
→ create/update/delete product
```

Automation validates:

```text
USER create product
→ 403

ADMIN create product
→ 201
```

### Interview Answer

> "I don't only validate authentication. I also automate authorization boundaries, for example verifying that a normal user cannot access admin product-management operations while the admin role can."

---

## 557. Database Validation Story

For critical API workflows, response validation alone may not prove persistence.

Flow:

```text
API request
↓
HTTP validation
↓
database query
↓
persisted-state validation
```

We use JDBC/SQL validation for relevant scenarios.

### Interview Answer

> "For workflows where persistence is important, I complement API assertions with direct database validation. That helps identify whether a failure is at the API-response layer or actual data-persistence layer."

---

# SECTION 368 — HOW TO ANSWER "TELL ME YOUR GIT EXPERIENCE"

## 558. 30-Second Answer

> "I use Git regularly for automation development and portfolio projects. I work with branches, staging, commits, remotes and pull-request workflows, and I understand merge conflicts, stash, rebase, revert, reset, reflog, tags and cherry-pick. I also treat secret verification and staged-diff review as part of my Git workflow before publishing code."

---

# SECTION 369 — HOW TO ANSWER "TELL ME YOUR LINUX EXPERIENCE"

## 559. 30-Second Answer

> "My Linux knowledge is practical and SDET-focused. I use the command line for filesystem operations, shell scripts, environment configuration, log analysis, process and port troubleshooting, API checks using curl, Docker operations and CI/CD debugging. I focus on using Linux to diagnose application and automation failures rather than claiming Linux administration expertise."

---

# SECTION 370 — HOW TO ANSWER "TELL ME YOUR CI/CD EXPERIENCE"

## 560. 30-Second Answer

> "I have worked with automation in CI/CD environments using tools such as Jenkins and Azure DevOps, where automated suites are triggered as part of build and regression workflows. I understand pipeline stages, environment configuration, reports, artifacts, quality gates and CI troubleshooting. In my portfolio project I am also preparing the framework for GitHub Actions integration."

---

# SECTION 371 — HOW TO ANSWER A TROUBLESHOOTING QUESTION

## 561. Use This Framework

Never jump directly to a command.

Answer:

```text
1. Understand symptom
2. Reproduce
3. Identify failure layer
4. Collect evidence
5. Compare expected vs actual
6. Apply smallest safe fix
7. Retest
8. Document root cause
```

This sounds much more senior than:

```text
"I would restart it."
```

---

# SECTION 372 — 20 RAPID-FIRE QUESTIONS

## 562.

### Q1. First Git command when confused?

```bash
git status
```

### Q2. Find port 8080 owner?

```bash
lsof -i :8080
```

### Q3. Search error in logs?

```bash
grep -i "error" application.log
```

### Q4. Follow live log?

```bash
tail -f application.log
```

### Q5. Show hidden files?

```bash
ls -la
```

### Q6. Show current folder?

```bash
pwd
```

### Q7. Unstage file?

```bash
git restore --staged <file>
```

### Q8. Show staged diff?

```bash
git diff --cached
```

### Q9. Temporarily store changes?

```bash
git stash
```

### Q10. Reverse shared commit?

```bash
git revert <commit>
```

### Q11. Recover lost-looking local commit?

```bash
git reflog
```

### Q12. Download remote Git state without merging?

```bash
git fetch
```

### Q13. Test HTTP endpoint?

```bash
curl -i <URL>
```

### Q14. Make script executable?

```bash
chmod +x script.sh
```

### Q15. Check Java version?

```bash
java -version
```

### Q16. Check running Docker containers?

```bash
docker ps
```

### Q17. Show Git remote?

```bash
git remote -v
```

### Q18. Specific commit to another branch?

```bash
git cherry-pick <commit>
```

### Q19. Find regression-introducing commit?

```text
git bisect
```

### Q20. Authentication vs authorization status?

```text
401 → authentication
403 → authorization
```

---

# SECTION 373 — 5-MINUTE PRE-INTERVIEW REVISION

## 563. Linux

Remember:

```text
pwd
ls
grep
find
tail -f
chmod
ps
lsof
curl
env
exit codes
```

Mental flow:

```text
Application failure
→ logs + process + port + config
```

---

## 564. Git

Remember:

```text
status
diff
add
commit
branch
switch
fetch
pull
merge
rebase
stash
restore
revert
reset
reflog
cherry-pick
tag
bisect
```

Mental flow:

```text
Confused
→ status

History confusion
→ log

Lost commit
→ reflog

Shared bad commit
→ revert
```

---

## 565. CI/CD

Remember:

```text
Trigger
Runner
Checkout
Setup
Build
Test
Report
Artifact
Quality Gate
Deploy
```

Troubleshoot:

```text
stage
logs
versions
environment
permissions
data
network
parallelism
exit code
```

---

# SECTION 374 — 2-MINUTE SENIOR SDET REVISION

## 566. Complete System Thinking

```text
Requirement
    ↓
Code
    ↓
Git
    ↓
Feature Branch
    ↓
Pull Request
    ↓
CI Pipeline
    ↓
Build
    ↓
API / UI / DB Tests
    ↓
Reports
    ↓
Quality Gate
    ↓
Release
    ↓
Monitoring
```

Senior SDET thinking is:

```text
not only test execution
```

It is:

```text
quality engineering across the delivery lifecycle
```

---

# SECTION 375 — TOP 10 INTERVIEW STATEMENTS TO REMEMBER

## 567.

### 1

> "I troubleshoot layer by layer rather than assuming every test failure is an automation defect."

### 2

> "I use Git status and diff before making history-changing decisions."

### 3

> "I prefer evidence before modifying a running environment."

### 4

> "I treat secrets as runtime configuration, not source code."

### 5

> "A test passing locally does not prove the CI environment is equivalent."

### 6

> "Retries should not hide genuine instability or product defects."

### 7

> "I keep most automated validation below the UI layer where practical."

### 8

> "CI should fail correctly when critical tests fail."

### 9

> "I use API, database and logs together when debugging distributed workflows."

### 10

> "Automation should provide reliable quality evidence, not just a high test-count number."

---

# SECTION 376 — WHAT NOT TO DO IN INTERVIEWS

## 568. Avoid Saying

```text
I know all Linux commands.

I always use force push.

Whenever CI fails I rerun it.

I use sleep to handle flaky tests.

403 means login failed.

Git and GitHub are the same.

I commit .env because repository is private.

All tests should always run on every PR.

If something fails in production, restart server first.
```

These answers indicate weak engineering judgement.

---

# SECTION 377 — BETTER ENGINEERING LANGUAGE

## 569. Instead of:

```text
The API was down.
```

Say:

> "The automated request failed, so I verified the endpoint independently before concluding whether it was an application or connectivity issue."

---

## 570. Instead of:

```text
Git broke.
```

Say:

> "The local and remote branch histories had diverged, so I fetched the remote state and inspected the commit graph before deciding how to integrate the changes."

---

## 571. Instead of:

```text
CI is flaky.
```

Say:

> "The failure was CI-specific, so I compared environment configuration, runtime versions, test data and parallel execution before classifying it as nondeterministic."

---

# SECTION 378 — FINAL COMMAND MEMORY MAP

## 572. Linux

```text
Where am I?
→ pwd

What's here?
→ ls -la

Find file?
→ find

Find text?
→ grep

Watch logs?
→ tail -f

Process?
→ ps / pgrep

Port?
→ lsof

HTTP?
→ curl

Permission?
→ ls -l / chmod

Environment?
→ printenv / export / source
```

---

## 573. Git

```text
Current state?
→ git status

What changed?
→ git diff

What's staged?
→ git diff --cached

History?
→ git log

Temporary work?
→ git stash

Undo staged?
→ git restore --staged

Undo shared commit?
→ git revert

Lost commit?
→ git reflog

Specific commit needed?
→ git cherry-pick

Find first bad commit?
→ git bisect
```

---

# SECTION 379 — FINAL SDET DEBUGGING MAP

## 574. Failure Investigation

```text
TEST FAILS
   ↓
Is assertion correct?
   ↓
Inspect request/response
   ↓
Check HTTP status
   ↓
Check application logs
   ↓
Check authentication/RBAC
   ↓
Check database
   ↓
Check environment
   ↓
Check dependency/infrastructure
   ↓
Check automation implementation
   ↓
Identify root cause
```

---

# SECTION 380 — FINAL PROJECT POSITIONING

## 575. What This Knowledge Adds to Our Portfolio

Our portfolio is not just:

```text
REST Assured tests
```

It demonstrates understanding across:

```text
Java
Spring Boot
REST APIs
PostgreSQL
JWT
RBAC
REST Assured
TestNG
JDBC
Allure
Swagger
Docker
Git
GitHub
Linux/Shell
Environment configuration
Security-conscious reporting
Automation architecture
```

Planned expansion:

```text
React
TypeScript
Playwright
GitHub Actions
k6
AWS
```

This creates a stronger:

```text
Senior SDET / Automation Lead
```

story than presenting isolated testing tools.

---

# SECTION 381 — FINAL LINUX + GIT STUDY PLAN

## 576. Level 1 — Remember

Know what these commands do:

```text
pwd
ls
grep
find
tail
chmod
ps
lsof
curl
git status
git diff
git add
git commit
git branch
git switch
```

---

## 577. Level 2 — Practice

Be comfortable with:

```text
shell script execution
environment variables
process debugging
port debugging
Git feature branches
stash
merge conflict
revert
restore
```

---

## 578. Level 3 — Interview

Be able to explain:

```text
rebase
reset vs revert
reflog
cherry-pick
bisect
CI runner
exit codes
secret handling
CI-only failures
parallel failures
```

---

## 579. Level 4 — Real Project

Apply these concepts while building:

```text
React frontend
Playwright automation
GitHub Actions
Docker
AWS
```

Real usage will convert theory into long-term skill.

---

# SECTION 382 — FINAL ONE-PAGE REVISION

## Linux

```text
Filesystem      → pwd, ls, cd
Files           → cp, mv, rm
Search          → grep, find
Logs            → head, tail, tail -f
Permissions     → chmod
Processes       → ps, pgrep, kill
Ports           → lsof
Networking      → curl
Environment     → export, source, printenv
Disk            → df, du
```

## Git

```text
State           → status
Changes         → diff
Stage           → add
Snapshot        → commit
History         → log
Branch          → switch
Remote          → fetch/pull/push
Temporary       → stash
Integration     → merge/rebase
Undo            → restore/revert/reset
Recovery        → reflog
Selective Copy  → cherry-pick
Release         → tag
Regression Hunt → bisect
```

## CI/CD

```text
Trigger
↓
Runner
↓
Checkout
↓
Setup
↓
Build
↓
Test
↓
Report
↓
Artifact
↓
Quality Gate
↓
Deployment
```

## Senior SDET

```text
Don't guess.
Collect evidence.

Don't hide failures.
Find root cause.

Don't hardcode environments.
Externalize configuration.

Don't expose secrets.
Sanitize and protect them.

Don't automate everything through UI.
Use the correct test layer.

Don't just run tests.
Engineer quality.
```

---

# END OF PART 6

# LINUX + GIT NOTES COMPLETE

These notes now cover Linux and Git from:

```text
ZERO
↓
FOUNDATION
↓
REAL-WORLD COMMANDS
↓
HANDS-ON LAB
↓
ADVANCED GIT
↓
CI/CD
↓
PRODUCTION TROUBLESHOOTING
↓
SENIOR SDET INTERVIEW LEVEL
```

The next learning phase should be practical application inside the SDET Commerce Automation project rather than adding more Linux/Git theory.

Next project phase:

```text
Document latest GitHub milestone
↓
React + TypeScript frontend
↓
Playwright + TypeScript
↓
UI + API hybrid testing
↓
Full Dockerization
↓
GitHub Actions
↓
k6
↓
AWS
```