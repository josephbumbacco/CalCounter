package com.example.calcounter.Javabean;

import android.os.Parcel;
import android.os.Parcelable;


/**
 * @author Drew Brooks
 *
 * Food Class - A class designed for our food object
 */
public class Food implements Parcelable {


    /*
     * Food properties
     */
    private int id;
    private String name;
    private String brand;
    private Double calories;


    /**
     * Constructor method for food
     *
     * @param name
     * @param brand
     * @param calories
     */
    public Food(String name, String brand, Double calories) {
        this.name = name;
        this.brand = brand;
        this.calories = calories;
    }

    public Food() {

    }

    public Food(int id, String name, String brand, Double calories) {
        this.id = id;
        this.name = name;
        this.brand = brand;
        this.calories = calories;
    }

    /**
     * Getters and Setters for all our food properties
     *
     * @return values of properties
     */

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public Double getCalories() {
        return calories;
    }

    public void setCalories(Double calories) {
        this.calories = calories;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    /**
     * Code below here conforms class to parcelable
     *
     * @param dest
     * @param flags
     */
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.name);
        dest.writeString(this.brand);
        dest.writeDouble(this.calories);
    }

    protected Food(Parcel in) {
        this.id = in.readInt();
        this.name = in.readString();
        this.brand = in.readString();
        this.calories = in.readDouble();
    }

    public static final Parcelable.Creator<Food> CREATOR = new Parcelable.Creator<Food>() {
        @Override
        public Food createFromParcel(Parcel source) {
            return new Food(source);
        }

        @Override
        public Food[] newArray(int size) {
            return new Food[size];
        }
    };
}
