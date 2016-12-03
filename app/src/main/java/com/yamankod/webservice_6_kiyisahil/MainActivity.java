package com.yamankod.webservice_6_kiyisahil;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.widget.TextView;

public class MainActivity extends Activity {

	private static final String METHOD_NAME = "sydbKordinatBilgileri";
	private static final String NAMESPACE = "http://kegm.gov.tr/DD/MobilWebService.asmx";
	private static final String SOAP_ACTION = "http://kegm.gov.tr/DD/MobilWebService.asmx/sydbKordinatBilgileri";
	private static final String URL = "http://servis.kiyiemniyeti.gov.tr/MobilWebService.asmx";
    
    TextView view ;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		view = (TextView) findViewById(R.id.text);
		fetchSoap soap = new fetchSoap();
		soap.execute("dbjjd");
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	public class fetchSoap extends AsyncTask<String, Integer, String> {
		@Override
		protected String doInBackground(String... params) {
			return getObject();
		}
		@Override
		protected void onPostExecute(String result) {
			view.setText("a   "+result);
			super.onPostExecute(result);
		}
	}

	public String getObject() {
		
		String s = "";
		SoapObject istek = new SoapObject(NAMESPACE, METHOD_NAME);
		istek.addProperty("KullaniciAdi", "KULL_MOBIL_ISLEM_LER");
		istek.addProperty("KullaniciSifresi", "SIFRE_MOBIL_ISLEM_LER");
		istek.addProperty("MMSI", 0);

		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER11);
		envelope.dotNet = true;
		envelope.setOutputSoapObject(istek);
		
		HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);
	      androidHttpTransport.debug = true;
	       
	      try {
	        androidHttpTransport.call(SOAP_ACTION, envelope);
	        SoapObject response = (SoapObject) envelope.getResponse();
	        
	        System.out.println("aaa "+response.toString());
	        s = ""+response.toString();
	      }catch(Exception exception){
	    	  System.out.println("ee "+exception.getMessage());
	      }
	      
	      return s;

	}
}
