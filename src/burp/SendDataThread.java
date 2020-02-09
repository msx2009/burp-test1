package burp;

public class SendDataThread extends Thread{
	
	String url = null;
	String parmString = null;
	
	public SendDataThread(String url,String parmString) {
		this.url = url;
		this.parmString=parmString;
	}
	
	 public void run() {
		 HttpUtil.post(url, parmString); 
	 }

}
