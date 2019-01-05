package fr.leroideskiwis.fl.utils;

public class ThreadFactory {

    private Runnable runnable;

    public ThreadFactory(Runnable runnable) {
        this.runnable = runnable;
    }

    public Thread startDaemonStart(String name){

        if(runnable == null) return null;

        Thread th = new Thread(runnable);
        if(name != null) th.setName(name);
        th.setDaemon(true);
        th.start();

        return th;

    }

    public void startSecureThread(String name){

        new Thread(() -> {

            Thread th = startDaemonStart(name);

            if(th == null) return;

            try {
                th.join(60000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            if(th.isAlive()){

                while(th.getState() != Thread.State.TIMED_WAITING & th.getState() !=Thread.State.WAITING) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                th.interrupt();
                if(th.isAlive()) th.stop();
                System.err.println("Le thread "+th.getName()+" a été interrompu par sécurité.");

            }

        }, "Sous-thread").start();

    }

}
