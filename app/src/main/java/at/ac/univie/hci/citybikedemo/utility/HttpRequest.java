package at.ac.univie.hci.citybikedemo.utility;

import android.os.AsyncTask;
import android.widget.Toast;

import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/**
 * Utility Class that handles the sending of a HTTP Request and transforms the HTTP Response Body to String
 */
public class HttpRequest extends AsyncTask<String, Void, String> {

    final private String apiURL = "https://api.citybik.es";
    final private String allNetworksHref = "/v2/networks";
    private String href;

    public HttpRequest(){
        this.href = null;
    }

    public HttpRequest(String href){
        this.href = href;
    }

    public String getHref() {
        return href;
    }

    @Override
    protected void onPreExecute() {

    }

    @Override
    protected void onPostExecute(String result) {

    }

    @Override
    protected String doInBackground(String... params) {
        URL url;
        try {
            if (href == null) {
                url = new URL(apiURL + allNetworksHref);
            }
            else {
                url = new URL(apiURL + href);
            }
            URLConnection con = url.openConnection();
            InputStream in = con.getInputStream();
            String encoding = con.getContentEncoding();
            encoding = encoding == null ? "UTF-8" : encoding;
            return IOUtils.toString(in, encoding);
        }
        catch (MalformedURLException ex){
            ex.printStackTrace();
        }
        catch (IOException ex){
            ex.printStackTrace();
        }
        return null;
    }

}
