
# Ì≤™ GymByRaj ‚Äì Full Project Summary



> Ì≥Ö **Updated:** July 04, 2025  

> Ì±®‚ÄçÌ≤ª **Developer:** [Raj Kushwaha](https://github.com/rajkushwaha7584)



---



## Ì∑æ Project Overview

**GymByRaj** is a complete Gym Member Management System built using **Spring Boot**, **Thymeleaf**, and **MySQL**, designed to manage:



- Ì±§ Member registration and profiles  

- Ì≤∞ Payment and subscription tracking  

- Ì≥à Fitness stats and BMI  

- Ì¥ê Secure role-based login system  

- Ì≥¨ Automated email notifications  

- Ì≥Ñ PDF generation for member cards  

- Ì∂ºÔ∏è A beautiful and responsive UI  



---



## ‚úÖ Modules & Features Implemented



### Ì±§ Member Management

- Full registration (name, age, gender, height, weight, contact, address)

- Upload profile picture

- Search by name/phone

- View/edit/delete (soft delete to `deleted_member`)

- Export to Excel (`.xlsx`)



### Ì¥ê Login System

- Spring Security with roles: `ROLE_ADMIN` and `ROLE_MEMBER`

- Auto-login credential generation on registration

- Secure dashboard routing based on roles



### Ì≥∏ Profile Upload

- File/camera upload

- Gender-based fallback profile image



### Ì≥Ö Membership Management

- Tracks membership start & expiry dates

- Planned: Expiry alerts and reminders



### Ì≥à BMI Calculator

- Dial chart styled like a gauge (colored range)

- BMI status (Underweight, Fit, Obese, etc.)

- Supports dark mode



### Ì∑æ PDF Membership Summary

- Member card with logo, QR code, photo, expiry info

- Optional password masking

- Download or email the PDF



### Ì≥¨ Email Notifications

- Gmail SMTP integration

- HTML-formatted emails on registration & update

- Branding with logo and member info



### Ì∑ë‚ÄçÌø´ Trainer Module

- `trainerbio.html` with Tailwind CSS animations

- Fully responsive grid layout



### Ìø† Public Landing Page

- Modular Thymeleaf layout with header/footer fragments

- Gallery for trainers, members, transformations

- 3D animations and hover effects



### Ì≥ä Admin Dashboard

- Total members count

- Analytics-ready layout

- Planned: Graphs, progress counters, charts



---



## Ì∑± Tech Stack



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



## Ì≥ä Database Design



| Table Name       | Purpose                              |

|------------------|--------------------------------------|

| `member`         | Stores active gym member records     |

| `app_user`       | Stores login credentials & roles     |

| `deleted_member` | Stores soft-deleted member records   |

| *(Optional)*     | `trainer`, `payments`, `bmi_log`, etc.|



---



## Ì∑™ Features Planned



- Ì≥ä Admin Analytics Dashboard (charts, counters)

- Ì≥Ü Payment tracking with due alerts

- Ì≥∏ Transformation gallery

- Ì≥± REST API with Swagger documentation

- Ìºê Deployment with custom domain, favicon



---



## Ì≥Å Repository



[Ì¥ó GitHub ‚Äì GymByRaj Ì≤™](https://github.com/rajkushwaha7584/Gym_management)



