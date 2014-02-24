package com.rajarena.fitnessapp;

import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

public class CalActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.calactivity);
		calculate();
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
		float cbmr;
		if(sex==0)
		{
			cbmr = (float)(66+13.7*(wt)+5*(ht*100)-6.8*(age));
		}
		else
		{
			cbmr = (float) (655+9.6*wt+1.8*ht-4.7*age);
		}
		((TextView) findViewById(R.id.bmi)).setText(String.valueOf(cbmi));
		((TextView) findViewById(R.id.bmr)).setText(String.valueOf(cbmr));
		((TextView) findViewById(R.id.put)).setText(String.valueOf((int)((cbmr*1.4)+500)));
		((TextView) findViewById(R.id.cut)).setText(String.valueOf((int)((cbmr*1.4)-500)));
		((TextView) findViewById(R.id.maintain)).setText(String.valueOf((int)((cbmr*1.4))));

	}
}
