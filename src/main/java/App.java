import model.*;
import oracle.ucp.util.Pair;
import service.*;

import javax.print.Doc;
import java.io.IOException;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

class App {


    public static void main(String[] args) throws IOException {

        PatientService patientService = new PatientService();
        VisitService visitService = new VisitService();
        DoctorService doctorService = new DoctorService();
        SpecializationService specializationService = new SpecializationService();
        ServiceService serviceService = new ServiceService();
        LocalDateTime visitTime = LocalDateTime.now();
        String dateTime;


        Scanner input = new Scanner(System.in);
        Scanner in = new Scanner(System.in);

        String pesel;
        int choice;

        boolean loop = true;

            System.out.println("Welcome in our clinic!");
        while(loop) {
            System.out.println("What do you want to do?");
            System.out.println("1. Register");
            System.out.println("2. Make an appointment");
            System.out.println("3. Change the date of visit");
            System.out.println("4. Canceling an appointment");
            System.out.println("5. Find a doctor");
            System.out.println("6. Price list of services");
            System.out.println("7. Appointment calendar");
            System.out.println("0. Exit");

            int mode = input.nextInt();
            if(mode == 0) loop =false;

            boolean loop_2 =true;

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
                        if (patientService.createPatient(toCreate) == true) {
                            System.out.println("Now you are a member of clinic's system");
                            break;
                        }
                        else
                            System.err.println("Something wrong");

                        System.out.println("\n1. Register another patient");
                        System.out.println("2. Go back");
                        mode = in.nextInt();
                        if(mode == 2) loop_2 = false;
                    }
                    break;
                case 2:
                    while(loop_2) {
                        System.out.println("Insert your pesel:");
                        pesel = input.next();
                        System.out.println("Choose specialization");
                        specializationService.findAllSpecializations().forEach(System.out::println);
                        int specialization = input.nextInt();

                        System.out.println("Choose service");
                        serviceService.findServiceBySpecializationId(specialization).forEach(System.out::println);
                        int service = input.nextInt();

                        System.out.println("Choose doctor");
                        doctorService.getDoctorBySpecializationId(specialization).forEach(System.out::println);
                        int doctor = input.nextInt();


                        Pair<List<LocalDateTime>, List<LocalDateTime>> termsSchema = visitService.createTermsSchema(doctor);
                        List<LocalDateTime> begins = termsSchema.get1st();
                        List<LocalDateTime> ends = termsSchema.get2nd();

                        System.out.println("Below there are schedule of selected doctor");

                        for (int i = 0; i < begins.size(); i++) {
                            System.out.println(begins.get(i) + "\t" + ends.get(i));
                        }

                        System.out.println("Enter your dream visit time. Correct format: yyyy-mm-dd hh:mm");
                        dateTime = null;
                        dateTime = in.nextLine();

                        try {
                            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
                            visitTime = LocalDateTime.parse(dateTime, formatter);
                        } catch (IllegalArgumentException ex) {
                            System.err.println("Invalid date format");
                            break;
                        }

                        if (visitService.createVisit(visitTime, serviceService.findServiceById(service), doctor, pesel)) {
                            System.out.println("Visit added");
                            break;
                        }
                        System.out.println("Something wrong");

                        System.out.println("\n1. Make another appointment");
                        System.out.println("2. Go back");
                        mode = in.nextInt();
                        if(mode == 2) loop_2 = false;
                    }
                    break;
                case 3:
                    while(loop_2) {
                        System.out.println("Insert your pesel:");
                        pesel = input.next();
                        visitService.showPatientVisits(pesel).forEach(System.out::println);
                        System.out.println("Choose appointment");
                        int visit = input.nextInt();


                        System.out.println("Enter your dream visit time. Correct format: yyyy-mm-dd hh:mm");
                        dateTime = in.nextLine();
                        try {
                            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
                            visitTime = LocalDateTime.parse(dateTime, formatter);
                        } catch (IllegalArgumentException ex) {
                            System.err.println("Invalid date format");
                            break;
                        }

                        if (visitService.changeTerm(visitTime, visit) == true) {
                            System.out.println("The time of appointment was changed");
                            break;
                        } else {
                            System.err.println("Something wrong");
                        }
                        System.out.println("\n1. Change time of another appointment");
                        System.out.println("2. Go back");
                        mode = in.nextInt();
                        if (mode == 2) loop_2 = false;
                    }
                    break;
                case 4:
                    while(loop_2) {
                        System.out.println("Insert your pesel:");
                        pesel = input.next();
                        int confirmation;
                        System.out.println("Below there are your visits");
                        visitService.showPatientVisits(pesel).forEach(System.out::println);
                        System.out.println("Which visit would you like cancelling?");
                        choice = in.nextInt();
                        Visit chosenVisit = visitService.getVisitById(choice);
                        System.out.println("Are you sure that you would like cancel below visit to confirm type 1 to reject type 0");
                        System.out.println(chosenVisit);
                        confirmation = in.nextInt();


                        switch (confirmation) {
                            case 0:
                                System.err.println("Have to return to start menu");
                                break;
                            case 1:
                                if (visitService.cancelVisit(choice) == true) {
                                    System.out.println("Visit cancelled, below there are your current visits:");
                                    visitService.showPatientVisits(pesel).forEach(System.out::println);
                                } else
                                    throw new RuntimeException("Something Wrong");
                                break;
                            default:
                                throw new IllegalArgumentException("Wrong typed number");
                        }

                        System.out.println("\n1. Cancel another appointment");
                        System.out.println("2. Go back");
                        mode = in.nextInt();
                        if (mode == 2) loop_2 = false;
                    }
                    break;
                case 5:
                    while(loop_2) {
                        float rating = 0f;

                        System.out.println("Type which print method would you like use?");
                        System.out.println("1. Print all doctors");
                        System.out.println("2. Print doctors above selected rating");
                        System.out.println("3. Print doctors by specialization");
                        choice = in.nextInt();

                        switch (choice) {
                            case 1:
                                doctorService.findAll().forEach(System.out::println);
                                break;
                            case 2:
                                System.out.println("Type rating");
                                rating = in.nextFloat();

                                doctorService.findAllAboveRating(rating).forEach(System.out::println);
                                break;
                            case 3:
                                System.out.println("Select specialization");
                                specializationService.findAllSpecializations().forEach(System.out::println);
                                choice = in.nextInt();
                                doctorService.getDoctorBySpecializationId(choice).forEach(System.out::println);
                                break;
                        }

                        System.out.println("\n1. New search");
                        System.out.println("2. Go back");
                        mode = in.nextInt();
                        if (mode == 2) loop_2 = false;

                    }
                    break;
                case 6:
                    while(loop_2) {
                        System.out.println("Choose specialization");
                        specializationService.findAllSpecializations().forEach(System.out::println);
                        int specialization = input.nextInt();
                        List<Service> services = serviceService.findServiceBySpecializationId(specialization);
                        services.forEach(System.out::println);

                        System.out.println("\n1. Check price of another service");
                        System.out.println("2. Go back");
                        mode = in.nextInt();
                        if (mode == 2) loop_2 = false;
                    }
                    break;
                case 7:
                    while(loop_2) {
                        System.out.println("Insert your pesel:");
                        pesel = input.next();
                        List<Visit> patient_visits = visitService.showPatientVisits(pesel);
                        //patient_visits.forEach(System.out::println);
                        for (Visit v : patient_visits) {
                            Service v_service = serviceService.findServiceById(v.getServiceId());
                            Doctor v_doctor = doctorService.getDoctorById(v.getDoctorId());
                            //System.out.println(v.toString());
                            System.out.println(v.getId() + ". " + v.getDate() + " - '" + v_service.getTitle().toUpperCase() + "' - " + v_doctor.getSurname() + ",MD - rating: " + v.getRating());
                        }
                        System.out.println("\n1. Check another patient's calendar.");
                        System.out.println("2. Go back.");
                        mode = in.nextInt();
                        if( mode == 2 ) loop_2 = false;
                    }
                    break;
                case 8:
                    break;
//default:

            }

        }
    }

}
