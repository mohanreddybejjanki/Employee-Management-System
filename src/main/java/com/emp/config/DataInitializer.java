package com.emp.config;

import com.emp.entity.*;
import com.emp.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired private UserRepository userRepo;
    @Autowired private DepartmentRepository deptRepo;
    @Autowired private EmployeeRepository empRepo;
    @Autowired private AnnouncementRepository annRepo;
    @Autowired private HolidayRepository holidayRepo;

    @Override
    public void run(String... args) {

        if (userRepo.findByUsername("admin").isPresent()) return;

        // Admin
        User admin = new User();
        admin.setUsername("admin");
        admin.setPassword("admin123");
        admin.setRole("ADMIN");
        admin.setActive(true);
        userRepo.save(admin);

        // Departments
        Department it  = dept("Information Technology", "Software development, IT infrastructure and support", "Ramesh Kumar Reddy");
        Department hr  = dept("Human Resources", "Recruitment, payroll, employee relations and welfare", "Sunita Sharma");
        Department fin = dept("Finance & Accounts", "Budgeting, accounting, taxation and financial reporting", "Anil Verma");
        Department ops = dept("Operations", "Day-to-day operations, logistics and process management", "Priya Singh");
        Department mkt = dept("Marketing", "Digital marketing, branding and customer acquisition", "Kiran Rao");
        Department qa  = dept("Quality Assurance", "Software testing, QA processes and compliance", "Neha Gupta");

        // ---- 10 Male Employees (Hyderabad/Telangana) ----
        emp("Arjun",      "Reddy",      "arjun.reddy@emp.com",      "9876501001", "Senior Software Engineer",    "Male",   "arjun",      it,  72000, "1992-04-15", "2021-06-01", "Flat 302, Kondapur, Hyderabad - 500084");
        emp("Venkatesh",  "Naidu",      "venkatesh.naidu@emp.com",   "9876501002", "DevOps Engineer",             "Male",   "venkatesh",  it,  68000, "1990-08-22", "2020-03-15", "H.No 45, Madhapur, Hyderabad - 500081");
        emp("Suresh",     "Kumar",      "suresh.kumar@emp.com",      "9876501003", "HR Manager",                  "Male",   "suresh",     hr,  55000, "1988-12-10", "2019-07-01", "Plot 12, Gachibowli, Hyderabad - 500032");
        emp("Rajesh",     "Varma",      "rajesh.varma@emp.com",      "9876501004", "Senior Accountant",           "Male",   "rajesh",     fin, 58000, "1991-03-25", "2020-11-01", "Flat 501, Hitech City, Hyderabad - 500081");
        emp("Karthik",    "Rao",        "karthik.rao@emp.com",       "9876501005", "Operations Manager",          "Male",   "karthik",    ops, 62000, "1989-07-18", "2018-09-10", "H.No 78, Begumpet, Hyderabad - 500016");
        emp("Mahesh",     "Babu",       "mahesh.babu@emp.com",       "9876501006", "Marketing Executive",         "Male",   "mahesh",     mkt, 48000, "1994-01-30", "2022-02-15", "Flat 204, Banjara Hills, Hyderabad - 500034");
        emp("Srinivas",   "Goud",       "srinivas.goud@emp.com",     "9876501007", "QA Lead",                     "Male",   "srinivas",   qa,  60000, "1990-06-12", "2019-04-20", "Plot 89, Jubilee Hills, Hyderabad - 500033");
        emp("Prasad",     "Chowdary",   "prasad.chowdary@emp.com",   "9876501008", "Full Stack Developer",        "Male",   "prasad",     it,  70000, "1993-09-05", "2021-08-01", "H.No 23, Kukatpally, Hyderabad - 500072");
        emp("Vikram",     "Teja",       "vikram.teja@emp.com",       "9876501009", "Finance Analyst",             "Male",   "vikram",     fin, 54000, "1992-11-20", "2020-01-10", "Flat 701, Ameerpet, Hyderabad - 500016");
        emp("Naveen",     "Kumar",      "naveen.kumar@emp.com",      "9876501010", "Recruitment Specialist",      "Male",   "naveen",     hr,  46000, "1995-05-08", "2022-06-01", "H.No 56, Dilsukhnagar, Hyderabad - 500036");

        // ---- 10 Female Employees (Hyderabad/Telangana) ----
        emp("Priya",      "Lakshmi",    "priya.lakshmi@emp.com",     "9876501011", "Software Engineer",           "Female", "priya",      it,  65000, "1993-02-14", "2021-03-01", "Flat 403, Kondapur, Hyderabad - 500084");
        emp("Sneha",      "Reddy",      "sneha.reddy@emp.com",       "9876501012", "UI/UX Designer",              "Female", "sneha",      it,  60000, "1994-07-22", "2021-09-15", "H.No 34, Madhapur, Hyderabad - 500081");
        emp("Anitha",     "Sharma",     "anitha.sharma@emp.com",     "9876501013", "HR Executive",                "Female", "anitha",     hr,  44000, "1995-11-30", "2022-01-10", "Plot 67, KPHB Colony, Hyderabad - 500072");
        emp("Deepika",    "Nair",       "deepika.nair@emp.com",      "9876501014", "Accounts Executive",          "Female", "deepika",    fin, 50000, "1991-04-18", "2020-06-01", "Flat 102, Secunderabad - 500003");
        emp("Kavitha",    "Rao",        "kavitha.rao@emp.com",       "9876501015", "Operations Executive",        "Female", "kavitha",    ops, 47000, "1993-08-25", "2021-07-20", "H.No 90, LB Nagar, Hyderabad - 500035");
        emp("Pooja",      "Singh",      "pooja.singh@emp.com",       "9876501016", "Digital Marketing Analyst",   "Female", "pooja",      mkt, 52000, "1996-03-12", "2022-04-01", "Flat 305, Banjara Hills, Hyderabad - 500034");
        emp("Swathi",     "Goud",       "swathi.goud@emp.com",       "9876501017", "QA Engineer",                 "Female", "swathi",     qa,  55000, "1992-10-05", "2020-08-15", "H.No 12, Miyapur, Hyderabad - 500049");
        emp("Ramya",      "Chandra",    "ramya.chandra@emp.com",     "9876501018", "Business Analyst",            "Female", "ramya",      it,  67000, "1991-06-28", "2019-11-01", "Plot 45, Gachibowli, Hyderabad - 500032");
        emp("Lavanya",    "Devi",       "lavanya.devi@emp.com",      "9876501019", "Payroll Executive",           "Female", "lavanya",    hr,  48000, "1994-12-15", "2022-03-01", "Flat 208, Tarnaka, Hyderabad - 500017");
        emp("Mounika",    "Reddy",      "mounika.reddy@emp.com",     "9876501020", "Financial Controller",        "Female", "mounika",    fin, 75000, "1988-09-20", "2017-05-01", "H.No 78, Jubilee Hills, Hyderabad - 500033");

        // Announcements
        ann("Welcome to EMP Management System",
                "We are pleased to launch our new integrated ERP portal. All employees can now access attendance, leaves, salary slips, assets and more from one place. For any queries please contact HR.",
                "HIGH");
        ann("Ugadi Bonus Announcement",
                "The management is pleased to announce a special Ugadi bonus for all permanent employees. The bonus will be credited to your accounts by April 10th, 2026.",
                "HIGH");
        ann("Mandatory Safety Training – April 15th",
                "All employees are required to complete the mandatory workplace safety training on April 15th, 2026 at 10:00 AM in the Main Conference Room.",
                "MEDIUM");
        ann("New Leave Policy Effective May 2026",
                "Effective May 1st 2026, all leave requests must be submitted at least 48 hours in advance via the portal. Emergency leaves should be reported to your manager directly.",
                "HIGH");
        ann("Company Picnic – April 20th at Hussain Sagar",
                "Annual company picnic will be held on April 20th at Hussain Sagar Lake front. Families are welcome! Please register via the portal by April 15th.",
                "MEDIUM");
        ann("IT Infrastructure Maintenance – Sunday April 6th",
                "Our IT team will be performing scheduled maintenance on Sunday April 6th between 11 PM and 3 AM. Portal may be temporarily unavailable during this window.",
                "LOW");

        // Holidays
        holiday("Dr. Ambedkar Jayanti",  LocalDate.of(2026, 4, 14),  "NATIONAL",  "Birth anniversary of Dr. B.R. Ambedkar");
        holiday("Ram Navami",            LocalDate.of(2026, 4, 6),   "FESTIVAL",  "Hindu festival celebrating birth of Lord Ram");
        holiday("Maharashtra Day",       LocalDate.of(2026, 5, 1),   "NATIONAL",  "Labour Day / Maharashtra Day");
        holiday("Eid ul-Fitr",           LocalDate.of(2026, 3, 31),  "FESTIVAL",  "End of Ramadan – Eid celebration");
        holiday("Independence Day",      LocalDate.of(2026, 8, 15),  "NATIONAL",  "India's 80th Independence Day");
        holiday("Gandhi Jayanti",        LocalDate.of(2026, 10, 2),  "NATIONAL",  "Birth anniversary of Mahatma Gandhi");
        holiday("Dussehra",              LocalDate.of(2026, 10, 20), "FESTIVAL",  "Victory of good over evil");
        holiday("Diwali",               LocalDate.of(2026, 11, 8),  "FESTIVAL",  "Festival of Lights");
        holiday("Christmas",            LocalDate.of(2026, 12, 25), "NATIONAL",  "Christmas Day");
        holiday("New Year",             LocalDate.of(2027, 1, 1),   "NATIONAL",  "New Year's Day");
    }

    private Department dept(String name, String desc, String hod) {
        Department d = new Department();
        d.setName(name); d.setDescription(desc); d.setHodName(hod);
        return deptRepo.save(d);
    }

    private void emp(String first, String last, String email, String phone,
                     String designation, String gender, String username,
                     Department dept, double salary, String dob, String join, String address) {
        User u = new User();
        u.setUsername(username); u.setPassword("emp123");
        u.setRole("EMPLOYEE"); u.setActive(true);
        User saved = userRepo.save(u);

        long count = empRepo.count() + 1;
        Employee e = new Employee();
        e.setFirstName(first); e.setLastName(last);
        e.setEmail(email); e.setPhone(phone);
        e.setDesignation(designation); e.setGender(gender);
        e.setDepartment(dept); e.setUser(saved);
        e.setBasicSalary(salary);
        e.setDateOfBirth(LocalDate.parse(dob));
        e.setJoinDate(LocalDate.parse(join));
        e.setAddress(address);
        e.setStatus("ACTIVE");
        e.setEmployeeCode(String.format("EMP%04d", count));
        empRepo.save(e);
    }

    private void ann(String title, String content, String priority) {
        Announcement a = new Announcement();
        a.setTitle(title); a.setContent(content);
        a.setPriority(priority); a.setPostedBy("admin");
        a.setPostedOn(LocalDate.now());
        annRepo.save(a);
    }

    private void holiday(String name, LocalDate date, String type, String desc) {
        Holiday h = new Holiday();
        h.setName(name); h.setDate(date);
        h.setType(type); h.setDescription(desc);
        holidayRepo.save(h);
    }
}