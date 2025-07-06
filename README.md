cat > README.md <<EOF
# 💪 GymByRaj – Full Project Summary

> 📅 **Updated:** July 04, 2025  
> 👨‍💻 **Developer:** [Raj Kushwaha](https://github.com/rajkushwaha7584)

---

## 🧾 Project Overview

**GymByRaj** is a full-featured Gym Member Management System built using **Spring Boot**, **Thymeleaf**, and **MySQL**. It is designed to handle:

- 👤 Member registration and profiles  
- 💰 Payment and subscription tracking  
- 📈 Fitness monitoring and BMI calculation  
- 🔐 Role-based login and authentication  
- 📬 Email notifications  
- 🧾 PDF membership card generation  
- 🎨 Beautiful and responsive UI  

---

## ✅ Modules & Features Implemented

### 👤 Member Management
- Register members with full personal and fitness details.
- Upload profile pictures.
- Search members by name or phone.
- View, edit, and soft-delete members (\`deleted_member\` table).
- Export data to Excel (.xlsx).

### 🔐 Login System
- Role-based login with \`ROLE_ADMIN\` and \`ROLE_MEMBER\`.
- Auto-creation of credentials upon registration.
- Secure routing and redirection based on roles.

### 📸 Profile Upload
- Upload via camera or file.
- Default gender-based image fallback.

### 📅 Membership Management
- Tracks membership duration and expiry date.
- (Planned) Expiry alerts and auto-reminders.

### 📈 BMI Calculator
- Dial gauge chart with colored BMI zones.
- Displays BMI status (Underweight, Fit, Obese, etc.).
- Supports dark mode.

### 🧾 PDF Membership Summary
- PDF card includes logo, QR code, photo, expiry info.
- Password masking for security.
- Option to email or download the PDF.

### 📬 Email Notifications
- Gmail SMTP integration.
- Sends HTML-styled email on registration/update.
- Branded email template with gym logo and info.

### 🧑‍🏫 Trainer Module
- Tailwind CSS animated trainer bio page.
- Fully responsive layout.

### 🏠 Public Landing Page
- Built with modular Thymeleaf templates.
- Contains header/footer fragments, gallery, and animations.
- 3D styled hover effects.

### 📊 Admin Dashboard
- Displays total members and member insights.
- (Planned) Analytics charts and progress counters.

---

## 🧱 Tech Stack

| Layer           | Technologies Used                                |
|----------------|---------------------------------------------------|
| **Backend**     | Spring Boot, Spring MVC, Spring Security          |
| **Frontend**    | Thymeleaf, Bootstrap 5, Tailwind CSS              |
| **Database**    | MySQL, JPA/Hibernate                              |
| **PDF/Excel**   | iText (PDF), Apache POI (Excel Export)            |
| **Email**       | JavaMail with Gmail SMTP                          |
| **Build Tools** | Maven, Spring Tool Suite (STS4)                   |
| **Dev Tools**   | IntelliJ, MySQL Workbench, Postman                |

---

## 🗃️ Database Design

| Table Name       | Purpose                            |
|------------------|------------------------------------|
| \`member\`         | Stores active gym member data      |
| \`app_user\`       | Stores login credentials and roles |
| \`deleted_member\` | Stores soft-deleted members        |
| *(Optional)*     | \`trainer\`, \`payments\`, \`bmi_log\`   |

---

## 🚀 Planned Features

- 📊 Admin analytics dashboard with visual reports
- 💳 Payment tracking and reminders
- 🖼️ Member transformation gallery
- 🌐 REST APIs with Swagger documentation
- 🧠 Deployment with custom domain and favicon

---

## 📂 Repository

[🔗 GitHub – GymByRaj](https://github.com/rajkushwaha7584/Gym_management)
EOF
