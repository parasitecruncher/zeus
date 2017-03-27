package info.company.zeus.johnlam.soundcloud;

import android.app.Activity;
import android.util.Log;

import info.company.zeus.Models.Scloud;
import info.company.zeus.Music_frag;
import info.company.zeus.johnlam.soundcloud.object.CommentObject;
import info.company.zeus.johnlam.soundcloud.object.TrackObject;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.tasks.Task;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ypyproductions.utils.DBLog;
import com.ypyproductions.utils.StringUtils;
import com.ypyproductions.webservice.DownloadUtils;

import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * 
 *
Created by Mithun
 *
 */
public class SoundCloudAPI implements ISoundCloudConstants {
	
	private static final String TAG = SoundCloudAPI.class.getSimpleName();
	
	private String clientId;
	private String clientSecret;
	private String mPrefixClientId;
	private Activity activity;

	public SoundCloudAPI(String clientId, String clientSecret,Activity activity) {
		this.clientId = clientId;
		this.clientSecret = clientSecret;
		this.activity=activity;
		this.mPrefixClientId = String.format(FORMAT_CLIENT_ID, clientId);
	}
	
	public ArrayList<TrackObject> getListTrackObjectsByGenre(String genre,int offset, int limit){
		StringBuilder mStringBuilder = new StringBuilder();
		mStringBuilder.append(URL_API);
		mStringBuilder.append(METHOD_TRACKS);
		mStringBuilder.append(JSON_PREFIX);
		mStringBuilder.append(mPrefixClientId);
		mStringBuilder.append(String.format(FILTER_GENRE, genre));
		mStringBuilder.append(String.format(OFFSET, String.valueOf(offset), String.valueOf(limit)));
		
		String url = mStringBuilder.toString();
		mStringBuilder=null;

		DBLog.d(TAG, "==============>getListTrackObjectsByGenre="+url);
		InputStream data = DownloadUtils.download(url);
		if(data!=null){
			return SoundCloudJsonParsingUtils.parsingListTrackObject(data);
		}
		return null;
	}
	
	public String getListTrackObjectsByQuery(String query, int offset, int limit, final Class cls, final Object o){

		StringBuilder mStringBuilder = new StringBuilder();
		mStringBuilder.append(URL_API);
		mStringBuilder.append(METHOD_TRACKS);
		mStringBuilder.append(JSON_PREFIX);
		mStringBuilder.append(mPrefixClientId);
		mStringBuilder.append(String.format(FILTER_QUERY, query));
		mStringBuilder.append(String.format(OFFSET, String.valueOf(offset), String.valueOf(limit)));
		
		String url = mStringBuilder.toString();
		mStringBuilder=null;
		
		DBLog.d(TAG, "==============>getListTrackObjectsByQuery="+url);
		InputStream data = DownloadUtils.download(url);
		RequestQueue queue = Volley.newRequestQueue(activity);

// Request a string response from the provided URL.
		final String[] Res = {new String()};
		StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
				new Response.Listener<String>() {
					@Override
					public void onResponse(String response) {
						// Display the first 500 characters of the response string.
						DBLog.d("Response--------------------->>>>>>>>>>>>>>>>>>>>>>>\n",response.length()+"");
						gotResponse(response,cls,o);
						Res[0] =response;


					}
				}, new Response.ErrorListener() {
			@Override
			public void onErrorResponse(VolleyError error) {
				Log.d("Response", "Didn't work!");
			}
		});


// Add the request to the RequestQueue.
		queue.add(stringRequest);
		if(data!=null){
			DBLog.d("Ressponse", "==============>Response="+data);
			return Res[0];
		}
		return null;
	}

	private void gotResponse(String response, Class cls, Object o) {
		Gson gson = new Gson();
		Type type = new TypeToken<List<Scloud>>() {}.getType();
		ArrayList<Scloud> Scloudobj = gson.fromJson(response, type);
		//(cls.cast(o)).responseRecieved(Scloudobj);
		Music_frag music_frag=(Music_frag)o;
		music_frag.responseReceived(Scloudobj);
		Log.d("Response", Scloudobj.size()+"");
	}

	public ArrayList<TrackObject> getListTrackObjectsByTypes(String types,int offset, int limit,final Class<?> cls,final Object o){
		StringBuilder mStringBuilder = new StringBuilder();
		mStringBuilder.append(URL_API);
		mStringBuilder.append(METHOD_TRACKS);
		mStringBuilder.append(JSON_PREFIX);
		mStringBuilder.append(mPrefixClientId);
		mStringBuilder.append(String.format(FILTER_TYPES, types));
		mStringBuilder.append(String.format(OFFSET, String.valueOf(offset), String.valueOf(limit)));
		
		String url = mStringBuilder.toString();
		mStringBuilder=null;
		
		DBLog.d(TAG, "==============>getListTrackObjectsByQuery="+url);
		InputStream data = DownloadUtils.download(url);
		RequestQueue queue = Volley.newRequestQueue(activity);

// Request a string response from the provided URL.
		final String[] Res = {new String()};
		StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
				new Response.Listener<String>() {
					@Override
					public void onResponse(String response) {
						// Display the first 500 characters of the response string.
						DBLog.d("Response--------------------->>>>>>>>>>>>>>>>>>>>>>>\n",response.length()+"");
						gotResponse(response,cls,o);
						Res[0] =response;


					}
				}, new Response.ErrorListener() {
			@Override
			public void onErrorResponse(VolleyError error) {
				Log.d("Response", "Didn't work!");
			}
		});


// Add the request to the RequestQueue.
		queue.add(stringRequest);
		return null;
	}
	
	public ArrayList<TrackObject> getListTrackObjectsOfUser(long userId){
		StringBuilder mStringBuilder = new StringBuilder();
		mStringBuilder.append(URL_API);
		mStringBuilder.append(METHOD_USER+"/");
		mStringBuilder.append(String.valueOf(userId)+"/");
		mStringBuilder.append(METHOD_TRACKS);
		mStringBuilder.append(JSON_PREFIX);
		mStringBuilder.append(mPrefixClientId);
		String url = mStringBuilder.toString();
		mStringBuilder=null;
		
		DBLog.d(TAG, "==============>getListTrackObjectsOfUser="+url);
		InputStream data = DownloadUtils.download(url);
		if(data!=null){
			return SoundCloudJsonParsingUtils.parsingListTrackObject(data);
		}
		return null;
	}
	
	public ArrayList<CommentObject> getListCommentObject(long trackId){
		StringBuilder mStringBuilder = new StringBuilder();
		mStringBuilder.append(URL_API);
		mStringBuilder.append(METHOD_TRACKS+"/");
		mStringBuilder.append(String.valueOf(trackId)+"/");
		mStringBuilder.append(METHOD_COMMENTS);
		mStringBuilder.append(JSON_PREFIX);
		mStringBuilder.append(mPrefixClientId);
		String url = mStringBuilder.toString();
		mStringBuilder=null;
		
		DBLog.d(TAG, "==============>getListCommentObject="+url);
		InputStream data = DownloadUtils.download(url);
		if(data!=null){
			return SoundCloudJsonParsingUtils.parsingListCommentObject(data);
		}
		return null;
	}
	
	public TrackObject getTrackObject(long id){
		StringBuilder mStringBuilder = new StringBuilder();
		mStringBuilder.append(URL_API);
		mStringBuilder.append(METHOD_TRACKS);
		mStringBuilder.append("/");
		mStringBuilder.append(String.valueOf(id));
		mStringBuilder.append(JSON_PREFIX);
		mStringBuilder.append(mPrefixClientId);
		String url = mStringBuilder.toString();
		mStringBuilder=null;
		
		DBLog.d(TAG, "==============>getTrackObject="+url);
		String data = DownloadUtils.downloadString(url);
		if(!StringUtils.isEmptyString(data)){
			return SoundCloudJsonParsingUtils.parsingTrackObject(data);
		}
		return null;
	}

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public String getClientSecret() {
		return clientSecret;
	}

	public void setClientSecret(String clientSecret) {
		this.clientSecret = clientSecret;
	}
	
	
	
	
	
}
