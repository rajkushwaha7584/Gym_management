
# � GymByRaj – Full Project Summary



> � **Updated:** July 04, 2025  

> �‍� **Developer:** [Raj Kushwaha](https://github.com/rajkushwaha7584)



---



## � Project Overview

**GymByRaj** is a complete Gym Member Management System built using **Spring Boot**, **Thymeleaf**, and **MySQL**, designed to manage:



- � Member registration and profiles  

- � Payment and subscription tracking  

- � Fitness stats and BMI  

- � Secure role-based login system  

- � Automated email notifications  

- � PDF generation for member cards  

- �️ A beautiful and responsive UI  



---



## ✅ Modules & Features Implemented



### � Member Management

- Full registration (name, age, gender, height, weight, contact, address)

- Upload profile picture

- Search by name/phone

- View/edit/delete (soft delete to `deleted_member`)

- Export to Excel (`.xlsx`)



### � Login System

- Spring Security with roles: `ROLE_ADMIN` and `ROLE_MEMBER`

- Auto-login credential generation on registration

- Secure dashboard routing based on roles



### � Profile Upload

- File/camera upload

- Gender-based fallback profile image



### � Membership Management

- Tracks membership start & expiry dates

- Planned: Expiry alerts and reminders



### � BMI Calculator

- Dial chart styled like a gauge (colored range)

- BMI status (Underweight, Fit, Obese, etc.)

- Supports dark mode



### � PDF Membership Summary

- Member card with logo, QR code, photo, expiry info

- Optional password masking

- Download or email the PDF



### � Email Notifications

- Gmail SMTP integration

- HTML-formatted emails on registration & update

- Branding with logo and member info



### �‍� Trainer Module

- `trainerbio.html` with Tailwind CSS animations

- Fully responsive grid layout



### � Public Landing Page

- Modular Thymeleaf layout with header/footer fragments

- Gallery for trainers, members, transformations

- 3D animations and hover effects



### � Admin Dashboard

- Total members count

- Analytics-ready layout

- Planned: Graphs, progress counters, charts



---



## � Tech Stack



| Layer        | Tech Used |

|--------------|-----------|

| **Backend**  | Spring Boot, Spring MVC, Spring Security |

| **Frontend** | Thymeleaf, Bootstrap 5, Tailwind CSS |

| **Database** | MySQL, JPA/Hibernate |

| **PDF & Excel** | iText, Apache POI |

| **Email**    | JavaMail with Gmail SMTP |

| **Build Tools** | Maven, STS4 |

| **Dev Tools** | MySQL Workbench, Postman, IntelliJ |



---



## � Database Design



| Table Name       | Purpose                              |

|------------------|--------------------------------------|

| `member`         | Stores active gym member records     |

| `app_user`       | Stores login credentials & roles     |

| `deleted_member` | Stores soft-deleted member records   |

| *(Optional)*     | `trainer`, `payments`, `bmi_log`, etc.|



---



## � Features Planned



- � Admin Analytics Dashboard (charts, counters)

- � Payment tracking with due alerts

- � Transformation gallery

- � REST API with Swagger documentation

- � Deployment with custom domain, favicon



---



## � Repository



[� GitHub – GymByRaj �](https://github.com/rajkushwaha7584/Gym_management)



