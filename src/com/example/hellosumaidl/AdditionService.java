package com.example.hellosumaidl;


import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;

public class AdditionService extends Service{

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
	}
	
	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return new IAdditionService.Stub() {
			
			@Override
			public int add(int valuel, int valuel2) throws RemoteException {
				// TODO Auto-generated method stub
				return valuel+valuel2;
			}

			@Override
			public String iAdd(String str1, String str2) throws RemoteException {
				// TODO Auto-generated method stub
				return str1 + str2 ;
			}
		};
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}
}
