package name.bagi.levente.pedometer;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.FieldPosition;
import java.text.Format;
import java.text.ParsePosition;
import java.util.Arrays;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Color;
import android.os.Bundle;

import com.androidplot.series.XYSeries;
import com.androidplot.xy.BoundaryMode;
import com.androidplot.xy.LineAndPointFormatter;
import com.androidplot.xy.SimpleXYSeries;
import com.androidplot.xy.XYPlot;
import com.androidplot.xy.XYStepMode;

public class ChartActivity extends Activity {

	private XYPlot xyPlot;
	
	
	final String[] days = new String[] {
        	"1","2", "3","4", "5", "6",
        	"7", "8","9","10", "11","12"
        };
	 
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
 
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chart);
 
        // initialize our XYPlot reference:
        xyPlot = (XYPlot) findViewById(R.id.xyplot);
 
        Double[] steps = new Double[5];
        Double[] calories = new Double[5];
        
        try {
			ContextWrapper cw = new ContextWrapper(this);
			File directory = cw.getDir(Pedometer.BACKUP_DIR, Context.MODE_PRIVATE);
			File file = new File(directory+"/"+Pedometer.BACKUP_FILE);
			
			FileReader fileReader = new FileReader(file);
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			String line;
			int i = 0;
			while ((line = bufferedReader.readLine()) != null) {
				String arr[] = line.split(",");
				if (arr.length == 5 && i < 5) {
					steps[i] = new Double(arr[0]);
					calories[i] = new Double(arr[4]);
					i++;
				}
				else {
					//there is an  error
					System.out.println("There is error in the i/p file!!!");
				}
			}
			fileReader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

        
        // Converting the above income array into XYSeries
        XYSeries series1 = new SimpleXYSeries(
                Arrays.asList(steps),          		 // array => list
                SimpleXYSeries.ArrayFormat.Y_VALS_ONLY , // Y_VALS_ONLY means use the element index as the x value
                "Steps");                             	 // Title of this series
 
     // Converting the above expense array into XYSeries
        XYSeries series2 = new SimpleXYSeries(	
        		Arrays.asList(calories), 				// array => list
        		SimpleXYSeries.ArrayFormat.Y_VALS_ONLY, // Y_VALS_ONLY means use the element index as the x value
        		"Calories");							// Title of this series
 
        // Create a formatter to format Line and Point of income series
        LineAndPointFormatter incomeFormat = new LineAndPointFormatter(
                Color.rgb(0, 0, 255),                   // line color
                Color.rgb(200, 200, 200),               // point color
                null );                					// fill color (none)
        
        
        // Create a formatter to format Line and Point of expense series
        LineAndPointFormatter expenseFormat = new LineAndPointFormatter(
                Color.rgb(255, 0, 0),                   // line color
                Color.rgb(200, 200, 200),               // point color
                null);					                // fill color (none)
 
        
        
        xyPlot.addSeries(series1,expenseFormat);
        xyPlot.addSeries(series2, incomeFormat);
        
        // Formatting the Domain Values ( X-Axis )
        xyPlot.setDomainValueFormat(new Format() {
 
			@Override
            public StringBuffer format(Object obj, StringBuffer toAppendTo, FieldPosition pos) {
                return new StringBuffer( days[ ( (Double)obj).intValue() ]  );				
            }
 
            @Override
            public Object parseObject(String source, ParsePosition pos) {
                return null; 
            }
        });        
       
        xyPlot.setDomainLabel("");
        xyPlot.setRangeLabel("");
        
        // Increment X-Axis by 1 value
        xyPlot.setDomainStep(XYStepMode.INCREMENT_BY_VAL, 1);
        xyPlot.setRangeStep(XYStepMode.INCREMENT_BY_VAL, 1);
        xyPlot.setRangeLowerBoundary(0, BoundaryMode.FIXED);
        
        xyPlot.getGraphWidget().setRangeLabelWidth(50);               
        
        // Reduce the number of range labels
        xyPlot.setTicksPerRangeLabel(2);
        
        // Reduce the number of domain labels
        xyPlot.setTicksPerDomainLabel(2);
 
        // Remove all the developer guides from the chart
        xyPlot.disableAllMarkup();
    }
    
}