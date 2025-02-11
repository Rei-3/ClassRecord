package com.assookkaa.ClassRecord.Seeder.DataSeeder;

import com.assookkaa.ClassRecord.Entity.Courses;
import com.assookkaa.ClassRecord.Entity.Subjects;
import com.assookkaa.ClassRecord.Repository.CoursesRepository;
import com.assookkaa.ClassRecord.Repository.SubjectsRepository;
import com.assookkaa.ClassRecord.Seeder.Interface.DataSeeder;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class SubjectsSeeder implements DataSeeder {

    private final CoursesRepository coursesRepository;
    private final SubjectsRepository subjectsRepository;

    public SubjectsSeeder(CoursesRepository coursesRepository, SubjectsRepository subjectsRepository) {
        this.coursesRepository = coursesRepository;
        this.subjectsRepository = subjectsRepository;
    }

    @Override
    public void seed() {

        Courses INT = coursesRepository.findById(1).orElseThrow(()-> new RuntimeException("Course is not found"));
        Courses CSC = coursesRepository.findById(2).orElseThrow(()-> new RuntimeException("Course is not found"));
        Courses Bossing = coursesRepository.findById(3).orElseThrow(()-> new RuntimeException("Course is not found"));

        if(subjectsRepository.count() == 0) {
            List<Subjects> subjects = Arrays.asList(
                    // First Year Subjects
                    new Subjects("GE 4", "Mathematics in the Modern World", 3, 0, Bossing), // General Education subject
                    new Subjects("GE 5", "Purposive Communication", 3, 0, Bossing), // General Education subject
                    new Subjects("GE 6", "Art Appreciation", 3, 0, Bossing), // General Education subject
                    new Subjects("FIL 1", "Akademiko sa Wikang Filipino", 3, 0, Bossing), // General Education subject
                    new Subjects("ITS 100", "Introduction to Computing", 3, 1, INT), // IT subject
                    new Subjects("ITS 101", "Computer Programming I", 3, 1, INT), // IT subject
                    new Subjects("PE 1", "Physical Education I", 2, 0, Bossing), // Physical Education
                    new Subjects("NSTP 1", "National Service Training Program I", 3, 0, Bossing), // National Service Training Program

                    // Second Semester First Year Subjects
                    new Subjects("GE 1", "Understanding the Self", 3, 0, Bossing), // General Education subject
                    new Subjects("GE 2", "Readings in Philippine History", 3, 0, Bossing), // General Education subject
                    new Subjects("GE 3", "The Contemporary World", 3, 0, Bossing), // General Education subject
                    new Subjects("LIT 1", "Philippine Literature", 3, 0, Bossing), // General Education subject
                    new Subjects("ITS 103", "Discrete Mathematics", 3, 1, INT), // IT subject
                    new Subjects("ITS 104", "Computer Programming II", 3, 1, INT), // IT subject
                    new Subjects("ITS 105", "Data Structures & Algorithms", 3, 1, INT), // IT subject
                    new Subjects("PE 2", "Recreational Games and Sports", 2, 0, Bossing), // Physical Education
                    new Subjects("NSTP 2", "National Service Training Program II", 3, 0, Bossing), // National Service Training Program

                    // Second Year First Semester Subjects
                    new Subjects("GE 7", "Science, Technology, and Society", 3, 0, Bossing), // General Education subject
                    new Subjects("GE 8", "Ethics", 3, 0, Bossing), // General Education subject
                    new Subjects("GE 9", "Life and Works of Rizal", 3, 0, Bossing), // General Education subject
                    new Subjects("Eng 127", "Business Communication I", 3, 0, Bossing), // English subject
                    new Subjects("ITS 200", "Database Management Systems I", 3, 1, INT), // IT subject
                    new Subjects("ITS 201", "Systems Integration & Architecture", 3, 1, INT), // IT subject
                    new Subjects("ITS 202", "Object-Oriented Programming I", 3, 1, INT), // IT subject
                    new Subjects("ITS 203", "Intellectual Property Basics", 3, 0, INT), // General subject
                    new Subjects("PE 3", "Rhythmic and Social Recreation", 2, 0, Bossing), // Physical Education

                    // Second Year Second Semester Subjects
                    new Subjects("GE 10", "Environmental Science", 3, 0, Bossing), // General Education subject
                    new Subjects("Eng 128", "Business Communication II", 3, 0, Bossing), // English subject
                    new Subjects("ITS 204", "Hardware and Software Installation", 3, 1, INT), // IT subject
                    new Subjects("ITS 205", "Database Management Systems II", 3, 1, INT), // IT subject
                    new Subjects("ITS 206", "Data Communications & Networking I", 3, 1, INT), // IT subject
                    new Subjects("ITS 207", "Object-Oriented Programming II", 3, 1, INT), // IT subject
                    new Subjects("ITS 208", "Graphics & Visual Computing", 3, 1, INT), // IT subject
                    new Subjects("ITS 209", "Introduction to Human Computer Interaction", 3, 0, INT), // General subject

                    // Third Year First Semester Subjects
                    new Subjects("GE 11", "Gender and Society", 3, 0, Bossing), // General Education subject
                    new Subjects("BPO 1", "Fundamentals of BPO 1", 3, 0, INT), // Business Process Outsourcing subject
                    new Subjects("SERV 100", "Service Culture", 3, 0, INT), // Service subject
                    new Subjects("SYS 100", "Principles of Systems Thinking", 3, 0, Bossing), // Systems subject
                    new Subjects("ITS 300", "Web Development 1", 3, 1, INT), // IT subject
                    new Subjects("ITS 301", "Seminars & Field Trips", 1, 1, INT), // IT subject
                    new Subjects("ITS 302", "Data Communications & Networking II", 3, 1, INT), // IT subject
                    new Subjects("ITS 303", "Animation", 3, 1, INT), // IT subject
                    new Subjects("ITS 304", "System Analysis & Design", 3, 1, INT), // IT subject

                    // Third Year Second Semester Subjects
                    new Subjects("GE 12", "Philippine Popular Culture", 3, 0, Bossing), // General Education subject
                    new Subjects("BPO 2", "Fundamentals of BPO 2", 3, 0, INT), // Business Process Outsourcing subject
                    new Subjects("ITS 305", "Application Development and Emerging Technologies", 3, 1, INT), // IT subject
                    new Subjects("ITS 306", "Web Development 2", 3, 1, INT), // IT subject
                    new Subjects("ITS 307", "Platform Technologies", 3, 1, INT), // IT subject
                    new Subjects("ITS 308", "System Administration and Maintenance", 3, 1, INT), // IT subject
                    new Subjects("ITS 309", "Information Assurance & Security", 3, 1, INT), // IT subject
                    new Subjects("ITS 310", "Management Information System", 3, 1, INT), // IT subject
                    new Subjects("ITS 311", "Quantitative Methods (Including Modeling & Simulation)", 3, 1, INT), // IT subject

                    // Fourth Year Subjects
                    new Subjects("ITS 400", "Internship 1", 0, 1, INT), // IT Internship
                    new Subjects("ITS 401", "Internship 2", 0, 1, INT), // IT Internship
                    new Subjects("ITS 402", "Capstone Project 1", 6, 1, INT), // IT Capstone
                    new Subjects("ITS 403", "Capstone Project 2", 6, 1, INT), // IT Capstone
                    new Subjects("ITS 404", "Social and Professional Issues of IT", 3, 1, INT), // IT subject
                    new Subjects("ITS 405", "Multimedia Systems", 3, 1, INT) // IT subject
            );
            subjectsRepository.saveAll(subjects);
        }

    }
}
