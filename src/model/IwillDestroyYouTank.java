package model;

public class IwillDestroyYouTank extends FireableObject {
	private boolean connected = false;



	public IwillDestroyYouTank(boolean connected){
		this.connected = connected;
	}
	public boolean isConnected() {
		return connected;
	}

	public void setConnected(boolean connected) {
		this.connected = connected;
	}
}
