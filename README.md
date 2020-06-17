# Wytyczne do projektu z przedmiotu Bazy Danych 2020

| Kierunek              | Przedmiot   | Semestr | Rok akademicki |
| :-------------------: | :---------: | :-----: | :------------: |
| Informatyka Stosowana | Bazy danych | 4       | 2019/2020      |


## Temat
Tematem projektu jest aplikacja zarządzająca bazą danych przychodni lekarskiej.


## Struktura projektu
```bash
.
├── /resources/    # Pliki zasobów w projekcie (SS, rysunki, schematy)
│   └── /README.md # Opis projektu w formie pliku markdown
├── /src/          # Katalog główny projektu
│   └── /app/      # Kod źródłowy aplikacji
├── /.gitignore    # Pliki i foldery pomijane przy commitowaniu do repozytorium
└── /README.md     # Opis projektu
```
Projekt składa się z trzech części - projektu bazy danych, zaimplementowania funkcjonalności za pomocą języka SQL oraz wykorzystaniem napisanych zapytań w aplikacji.

### Projekt bazy danych
Cała baza danych przechowywana jest w plikach aplikacji. 


![image](https://user-images.githubusercontent.com/56622678/84870495-b0193a80-b07f-11ea-9357-18f4943ef5d9.png)



### Implementacja zapytań SQL
Zapytania SQL przechowywane są w ciele aplikacji, adokładnie w interfejsach danych class które one dotyczą.

Fragment zawartości takiego interfesju:

```java
final String getAllAboveEnteredRating = "SELECT * FROM doctors WHERE doctor_ID IN " +
                                            "(SELECT doctor_ID from visits HAVING AVG(rating) > #{rating} order by AVG(rating));";
@Select(getAllAboveEnteredRating)
    @Results(value = {
            @Result(property = "id", column = "doctor_ID"),
            @Result(property = "name", column = "name"),
            @Result(property = "surname", column = "surname"),
            @Result(property = "specializationId", column = "specialization_ID"),
            @Result(property = "officeId", column = "office_ID")
    })
    List<Doctor> getAllAboveEnteredRating(float rating);
```
### Aplikacja
Aplikacja jest obsugiwana poprzec konsolę

Implementcja przykładowej funkcjonalności:
```java
switch (mode) {
                case 1:
                    while(loop_2) {
                        String phoneNumber;
                        String email;
                        System.out.print("Type the name: ");
                        String name = input.next();
                        System.out.print("Type the surname: ");
                        String surname = input.next();
                        System.out.print("Type the PESEL: ");
                        pesel = input.next();
                        System.out.print("Type the phoneNumber: ");
                        phoneNumber = input.next();
                        System.out.print("Type the email: ");
                        email = input.next();

                        //Check if data matches?

                        Patient toCreate = Patient.builder().name(name).surname(surname).pesel(pesel).phoneNumber(phoneNumber).email(email).build();
                        if (patientService.createPatient(toCreate) == true)
                            System.out.println("Now you are a member of clinic's system");
                        else
                            System.err.println("Something wrong");

                        System.out.println("\n1. Register another patient");
                        System.out.println("2. Go back");
                        mode = in.nextInt();
                        if(mode == 2) loop_2 = false;
                    }
                    break;
              }

```


## Wykorzystane technologie
1.  Java SE Development Kit 8 
2.  Maven
3.  H2 Database Engine
4.  MyBatis 3
5.  Flyway
## Uchomienie aplikacji
1. Pobrać repo
2. W programie IntelliJ IDEA uruchomić tylko zawartośc pliku 
    ```bash
    ├── /src/         
    │     └── /app/    
    │           └── /clinic/     
    ```
3. Uruchomić funkcje main w folderze
    ```bash
    ├── /src/         
    │     └── /app/    
    │           └── /clinic/ 
    │                   └── /src/
    │                         └── /main/
    │                               └── /java/
    │                                     └── /App.java/
    ```
4. Aplikacja uruchomi sie w konsoli
