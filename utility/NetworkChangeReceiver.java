package testproject.com.app.testproject.utility;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;




public class NetworkChangeReceiver extends BroadcastReceiver
{
//	private Notification notification;

	public static MyNetworkChangeListner myNetworkChangeListner=new MyNetworkChangeListner() {
		@Override
		public void onNetworkStateChanges(boolean nwStatus) {

		}
	};


	@Override
	public void onReceive(final Context context, final Intent intent)
	{
		Utility.printLog("PK"+" onRecieve  "+intent.getAction());
		String status = NetworkUtil.getConnectivityStatusString(context);
		
		String[] networkStatus = status.split(",");
		
		Intent homeIntent=new Intent("com.app.pk.internetStatus");
		homeIntent.putExtra("STATUS", networkStatus[1]);
		context.sendBroadcast(homeIntent);
		Utility.printLog("PK Network Status"+status);
		if(myNetworkChangeListner!=null)
		{
			myNetworkChangeListner.onNetworkStateChanges("1".equals(networkStatus[1].trim()));
		}

		/*if(!"1".equals(networkStatus[1]))
		{
			sendNotification(context,networkStatus[1]);
		}*/
	}

	public  static void setMyNetworkChangeListner(MyNetworkChangeListner listner)
	{
		myNetworkChangeListner=listner;
	}


}
