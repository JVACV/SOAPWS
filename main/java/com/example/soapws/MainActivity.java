package com.example.soapws;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.Spinner;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.ksoap2.transport.HttpsTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    Spinner sp1;
    ListView lview1;
    String resp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sp1=findViewById(R.id.sp1);
        lview1=findViewById(R.id.lview1);
    }

    public void getCities(View view) {

        MyTask mt = new MyTask();
        mt.execute();
    }

    class MyTask extends AsyncTask {

        @Override
        protected Object doInBackground(Object[] objects) {
            loadData();
            return null;
        }

        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);
        }


    }

    public void loadData(){

        String wsdl_url="https://graphical.weather.gov/xml/SOAP_server/ndfdXMLserver.php?wsdl";
        String soap_action="https://graphical.weather.gov/xml/DWMLgen/wsdl/ndfdXML.wsdl#LatLonListCityNames";
        String name_space="https://graphical.weather.gov";
        String method_name="LatLonListCityNames";

        SoapObject soapObject = new SoapObject(name_space, method_name);
        soapObject.addProperty("displayLevel", sp1.getSelectedItem().toString());

        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.setOutputSoapObject(soapObject);

        HttpsTransportSE htse = new HttpsTransportSE(wsdl_url, 443, "xml/DWMLgen/wsdl/ndfdXML.wsdl#LatLonListCityNames", 1000);
        try {
            htse.call(soap_action, envelope);
            SoapObject obj = (SoapObject) envelope.bodyIn;
            resp = obj.getProperty(0).toString();
            System.out.println(resp);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        }
    }
}