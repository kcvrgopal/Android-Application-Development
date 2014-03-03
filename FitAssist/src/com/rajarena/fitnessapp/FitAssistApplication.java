package com.rajarena.fitnessapp;

import android.app.Application;

public class FitAssistApplication extends Application {
	private double count;
	private double calburned;
	private float weight;
	public double getCount()
	{
		return this.count;
	}
	public void setCount(double d)
	{
		this.count=d;
		
	}
	public double getCalBurned()
	{
		return this.calburned;
	}
	public void setCalBurned(double d)
	{
		this.calburned=d;
	}
	public void addCalBurned(double d)
	{
		this.calburned+=d;
	}
	public void setWeight(float w)
	{
		this.weight=w;
	}
	public float getWeight()
	{
		return this.weight;
	}

}
