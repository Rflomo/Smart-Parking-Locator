package com.example.myproject;


    public class Users {

        private String name,email,contact_no, status;

        public Users() {
        }

        public Users(String name, String email, String contact_no, String status) {
            this.name = name;
            this.email = email;
            this.contact_no = contact_no;
            this.status = status;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getContact_no() {
            return contact_no;
        }

        public void setContact_no(String contact_no) {
            this.contact_no = contact_no;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }
    }

