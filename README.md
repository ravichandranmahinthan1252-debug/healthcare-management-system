# Hospital Management System

A Java Swing-based hospital management system for managing patients, clinicians, appointments, prescriptions, and referrals.

## Features

- **Patient Management**: Add, edit, and delete patient records
- **Clinician Management**: Manage healthcare provider information
- **Staff Management**: Handle administrative staff records
- **Facility Management**: Manage hospital and GP surgery details
- **Appointment Scheduling**: Book, edit, and cancel appointments
- **Prescription Management**: Create and track prescriptions with text export
- **Referral System**: Queue-based referral processing with email generation

## Quick Start

1. **Compile the project:**
   ```bash
   javac -cp . src/app/*.java src/controller/*.java src/data/*.java src/model/*.java src/service/*.java src/util/*.java src/view/*.java
   ```

2. **Run the application:**
   ```bash
   java -cp . app.App
   ```

## Data Files

The system uses CSV files located in the `src` folder:
- `patients.csv` - Patient records
- `clinicians.csv` - Healthcare provider data
- `staff.csv` - Administrative staff
- `facilities.csv` - Hospital and GP surgery information
- `appointments.csv` - Appointment bookings
- `prescriptions.csv` - Prescription records
- `referrals.csv` - Referral data

## Usage

- Use the menu to select different data folders if needed
- All changes are automatically saved to CSV files
- Prescription and referral text files are exported to the output directory
- The referral system uses a singleton queue manager for processing

## Requirements

- Java 8 or higher
- No external dependencies required