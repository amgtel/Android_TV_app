package com.amgsoftsol18.tvonlinefree;

public class Channel {

    private String Name, Urldirection, Language;

    public Channel(){
    }

    public Channel(String name, String urldirection, String lang){
        Name = name;
        Urldirection = urldirection;
        Language = lang;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        this.Name = name;
    }

    public String getUrldirection() {
        return Urldirection;
    }

    public void setUrldirection(String urldirection) {
        this.Urldirection = urldirection;
    }

    public String getLanguage() {
        return Language;
    }

    public void setLanguage(String language) {
        Language = language;
    }
}
