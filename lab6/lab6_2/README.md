# Run sonarqube on docker
docker run -d --name sonarqube -e SONAR_ES_BOOTSTRAP_CHECKS_DISABLE=true -p 9000:9000 sonarqube:latest

# token
tqs_lab6_2: 30b40b68e33b9dba93250e313885b2b0eba21b26

# f)
All conditions of the code passed, with 1 bug, 0 vulnerabilities and 1 security hotspots.
The bug was "Save and re-use of this 'Random'" which is a Critical bug.
The Security hotspot was "Make sure that using this pseudorandom number generator is safe here." whilst we instanciate a new Random object.
There were also 26 code smells, with 1 being a blocker because of the use of "NUMBERS" variable, which can create misunderstandings with the field "numbers"

# g)
To solve the bug we can set up a global variable and rewrite the object when we need it to, instead of always allocating memory and creating a new object every time.
To solve the Security hotspot you would want to change the ranodm generator for a safer one, instead of using a PRNG (Pseudorandom generator) it would be much safer to use a CSPRNG (Cryptographic PRNG).
To solve the blocker code smell, we can change the "NUMBERS" variable name to a more suited one like "NUM_NUMBERS" which gives us a more detailed description of what the variable really is: number of normal numbers that can be in a euromillion's game.