package com.example.demo.services;

public class CheckUserInput {

    public boolean checkMessageSize(String message){
        if(message == null){
            return false;
        }
        else if(message.length() > 140){
            return false;
        }
        else if(message.length() == 0){
            return false;
        }
        return true;
    }

    public String checkDate(String year, String month, String day){
        //Checks if "valid" date
        //Returns formatted date or null
        try{
            int x = Integer.parseInt(year);
            x = Integer.parseInt(month);
            x = Integer.parseInt(day);
        }
        catch (NumberFormatException e){
            return null;
        }

        if (year.length() != 4){
            return null;
        }
        else if(month.length() > 2){
            return null;
        }
        else if(day.length() > 2){
            return null;
        }

        if(month.length() == 1){
            month = "0" + month;
        }
        if(day.length() == 1){
            day = "0" + day;
        }

        return year + "-" + month + "-" + day;
    }

    public String checkDate(String date){
        String[] arr = date.split("-");
        if(arr.length != 3){
            return null;
        }
        return checkDate(arr[0], arr[1], arr[2]);
    }

    public boolean checkRegistration(){
        //Take all parameters
        return false;
    }

    public boolean checkKeywords(String keyword1, String keyword2, String keyword3){
        if (keyword1 == null || keyword1.length() > 45){
            return false;
        }
        else if (keyword2 == null || keyword2.length() > 45){
            return false;
        }
        else if (keyword3 == null || keyword3.length() > 45){
            return false;
        }
        return true;
    }

    public boolean checkChangesToProfile(){
        return false;
    }
}