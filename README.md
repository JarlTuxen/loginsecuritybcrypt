# loginsecuritybcrypt

Formålet med projektet er at vise, hvordan login kan håndteres med hashed password gemt i cookie.

Projektet består af login-form og index-form.

* ved login checkes credentials, hvorefter user id og password gemmes i cookie
* på index form checkes credentials - hvis ok så vises username på siden ellers link til login
* password hashes med bcrypt ved login - derefter bruges det ikke i klartekst
* der sammenlignes med det hashede password

[Dokumentation af bcrypt](https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/crypto/bcrypt/BCrypt.html).


BCrypt hentes fra org.springframework.security.crypto.bcrypt. Det er Spring Security, som dermed vil blive aktiveret. 

For at undgå at Spring Security aktiveres, excludes Spring Security config ved applikationsstart:
> @SpringBootApplication(exclude = { SecurityAutoConfiguration.class })

## Projektet kan forbedres yderligere
* Generer token når login lykkes og brug det i stedet for brugernavn og hashed password. Vil fjerne risikoen for at hashed password bliver opsnappet og brugt til at finde password ved brute force.
* Sæt timeout på token/cookie, så CSRF (tyveri af cookie og benyttelse til adgang på session) bliver vanskeliggjort.