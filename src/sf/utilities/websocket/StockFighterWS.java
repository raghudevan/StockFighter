package sf.utilities.websocket;

import java.net.URI;
import java.net.URISyntaxException;

import sf.utilities.websocket.WebsocketClientEndpoint.MessageHandler;

public class StockFighterWS implements Runnable {

	/**
	 * We listen for websocket activity on a parallel thread and
	 * can respond to incoming messages in the handleMessage method
	 * asynchronously
	 * **/
	private Thread thread;
	private WebsocketClientEndpoint clientEndpoint; 
	private String url;

	public StockFighterWS(String url) {
		this.url = url;
	}

	public void startListening(String threadName,
			Boolean isDaemon) {

		if(thread == null || !thread.isAlive()) {
			startConnection(this.url);

			thread = new Thread(this, threadName);
			thread.setDaemon(isDaemon);
			thread.start();
		}
	}

	private void startConnection(String url) {
		try {
			System.out.println("Listening to socket activity!");
			clientEndpoint = new WebsocketClientEndpoint(new URI(url));
			clientEndpoint.addMessageHandler(new StockFighterMessageHandler());
		} catch (URISyntaxException e) {
			System.out.println(e.getMessage());
		}
	}

	/**
	 * Stockfighter does not respond to any messages at the moment.
	 * Adding this anyways, might need it later
	 * **/
	@SuppressWarnings("unused")
	private void sendMessage(String messageToServer) {
		clientEndpoint.sendMessage(messageToServer);
	}

	@Override
	public void run() {
		while(true) {
			synchronized (thread) {
				try {

					//wait for notifications
					System.out.println(thread.getName() + ": Will wait for notifications!");
					thread.wait();

					/* 
					 * If thread.notify() is called, the control comes to this line
					 * But to do so, thread must be put in a synchronized block
					 * */
				} catch (InterruptedException e) {
					System.out.println(thread.getName() + ": Interupted Exception: " + e.getMessage());
				} catch (IllegalMonitorStateException e) {
					System.out.println(thread.getName() + ": Illegal Monitor State Exception: " + e.getMessage());
				}
			}
		}
	}

	private class StockFighterMessageHandler implements MessageHandler {
		public void handleMessage(String message) {
			if(message != null) {
				/*
				 * This is where you would deparse the message
				 * */
				System.out.println("Socket message: " + message);
			}
		}
	}
}
