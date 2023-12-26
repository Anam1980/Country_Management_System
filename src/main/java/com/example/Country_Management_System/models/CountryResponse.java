package com.example.Country_Management_System.models;


import java.util.Map;

public class CountryResponse {

    private Name name;

    private long population;

    private double area;

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

    public static class Name {
        private String common;
        private String official;
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

