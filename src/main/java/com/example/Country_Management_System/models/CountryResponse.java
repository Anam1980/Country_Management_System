package com.example.Country_Management_System.models;


import java.util.Map;

/**
 * Represents a response containing information about a country.
 */
public class CountryResponse {

    //Name of the Country
    private Name name;

    //Population of country
    private long population;

    //Area of country
    private double area;

    //Getter Setters
    public long getPopulation() {
        return population;
    }

    public void setPopulation(long population) {
        this.population = population;
    }

    public double getArea() {
        return area;
    }

    public void setArea(float area) {
        this.area = area;
    }

    public Name getName() {
        return name;
    }

    public void setName(Name name) {
        this.name = name;
    }

    /**
     *  class Name->Represents the name information of the country.
     */
    public static class Name {

        //Common Name of country
        private String common;

        //Official name of country
        private String official;

        //nativeName of the country
        private Map<String, NativeName> nativeName;

        // getters and setters
        public String getCommon() {
            return common;
        }

        public void setCommon(String common) {
            this.common = common;
        }

        public String getOfficial() {
            return official;
        }

        public void setOfficial(String official) {
            this.official = official;
        }

        public Map<String, NativeName> getNativeName() {
            return nativeName;
        }

        public void setNativeName(Map<String, NativeName> nativeName) {
            this.nativeName = nativeName;
        }

        /**
         * class NativeNames ->  Represents the native names information of the country.
         */
        public static class NativeName {
            private String official;
            private String common;

            // getters and setters
            public String getOfficial() {
                return official;
            }

            public void setOfficial(String official) {
                this.official = official;
            }

            public String getCommon() {
                return common;
            }

            public void setCommon(String common) {
                this.common = common;
            }
        }
    }


}

