package com.example.tijori;

public class HelperClass {
        public String name, email, username, password, newsimage,pin;
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
        public String getUsername() {
            return username;
        }
        public void setUsername(String username) {
            this.username = username;
        }
        public String getPassword() {
            return password;
        }
        public void setPassword(String password) {
            this.password = password;
        }

        public String getNewsimage(){ return newsimage;}
        public void setNewsimage(String newsimage){this.newsimage = newsimage;}

    public String getPin() {
        return pin;
    }
    public void setPin(String pin) {
        this.pin = pin;
    }


        public HelperClass(String name, String email, String username, String password, String newsimage,String pin) {
            this.name = name;
            this.email = email;
            this.username = username;
            this.password = password;
            this.newsimage = newsimage;
            this.pin = pin;
        }
        public HelperClass() {
        }
        public HelperClass(String name,String email,String username,String password){
            this.name = name;
            this.email = email;
            this.username = username;
            this.password = password;
        }
    }

