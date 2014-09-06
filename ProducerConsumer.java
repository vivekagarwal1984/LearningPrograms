import java.util.Random;
import java.util.Vector;


public class ProducerConsumer {
	public static void main(String[] args) {
		final int QUEUE_SIZE = 10;
	
		Vector<Object> sharedQueue = new Vector<Object>();
	
		Thread producer = new Thread(new Producer(sharedQueue,QUEUE_SIZE));
		Thread consumer = new Thread(new Consumer(sharedQueue,QUEUE_SIZE));
		
		producer.start();
		consumer.start();
	}
}

class Producer implements Runnable {
	Vector<Object> sharedQueue = new Vector<Object>();
	int size=0;
	
	public Producer(Vector<Object> sharedQueue, int size) {
		this.sharedQueue = sharedQueue;
		this.size = size;
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		while(true) {
			if(sharedQueue.size() >= size) {
				synchronized(sharedQueue) {
					try {
						System.out.println("Queue full!=" + size);
						sharedQueue.wait();
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			} else {
				synchronized(sharedQueue) {
					int i = (new Random()).nextInt(100);
					sharedQueue.add(i);
					System.out.println("Producing:"+i);
					sharedQueue.notifyAll();
				}
			}	
		}
	}
}

class Consumer implements Runnable {
	Vector<Object> sharedQueue = new Vector<Object>();
	int size=0;
	
	public Consumer(Vector<Object> sharedQueue, int size) {
		this.sharedQueue = sharedQueue;
		this.size = size;
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		while(true) {
			if(sharedQueue.size() == 0) {
				synchronized(sharedQueue) {
					try {
						System.out.println("Queue Empty!");
						sharedQueue.wait();
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				} 
			} else {
				synchronized(sharedQueue) {
					Object consume = sharedQueue.remove(0);
					System.out.println("Consuming: "+ consume.toString());
					sharedQueue.notifyAll();
				}
			}
		}
	}
}
