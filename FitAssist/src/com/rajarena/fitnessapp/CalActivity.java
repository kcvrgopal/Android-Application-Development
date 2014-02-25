package com.rajarena.fitnessapp;

import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class CalActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.calactivity);
		calculate();
		String msg="You took "+Math.round(MainActivity.getCount());
		Toast.makeText(this, msg , Toast.LENGTH_SHORT).show();
	}
	public void calculate()
	{
		DBConnection dbc=new DBConnection(this);
		List<Integer> val=dbc.getInfo();
		float ht=(float)val.get(0)/100;
		float wt=(float) ((float)val.get(1)/2.20462);
		float age=(float)val.get(2);
		float bf=(float)val.get(3);
		int sex=val.get(4);
		System.out.println(ht+" "+wt+" "+age+" "+bf);
		float cbmi = wt/(ht*ht);
		float leanmass=wt-bf*wt/100;
		float cbmr;
		float x=(float) 0.0;
		if(sex==0)
		{
			cbmr = (float)(66+13.7*(leanmass)+5*(ht*100)-6.8*(age));
		}
		else
		{
			cbmr = (float) (655+9.6*leanmass+1.8*ht-4.7*age);
		}
		double steps=MainActivity.getCount();
		if(steps<1500)
		{
			x=(float) 1.2;
		}
		else if(steps>=1500&&steps<3000)
		{
			x=(float) 1.3;
		}
		else if(steps >= 3000 && steps <5700)
		{
			x=(float) 1.4;
		}
		else if(steps>=5700&&steps <7900)
		{
			x=(float) 1.5;
		}
		else if(steps>=7900)
		{
			x=(float) 1.6;	
		}
		float calburned=(float) (((float)val.get(1))*MainActivity.getCount()/3500);
		((TextView) findViewById(R.id.bmi)).setText(String.valueOf(cbmi));
		((TextView) findViewById(R.id.bmr)).setText(String.valueOf(Math.round(cbmr)));
		((TextView) findViewById(R.id.put)).setText(String.valueOf(Math.round((cbmr*x)+500)));
		((TextView) findViewById(R.id.cut)).setText(String.valueOf(Math.round((cbmr*x)-500)));
		((TextView) findViewById(R.id.maintain)).setText(String.valueOf(Math.round((cbmr*x))));
		((TextView) findViewById(R.id.leanmass)).setText(String.valueOf(Math.round(leanmass*2.2)));
		((TextView) findViewById(R.id.calburned)).setText(String.valueOf(Math.round(calburned)));

	}
}
