import gui.Frame;
import gui.FrameServer;

public class StartUp {
    public static void main(String[] args) throws Exception {
        StartUp launcher = new StartUp();
        launcher.run();
    }

    public void run() {
        FrameServer server = new FrameServer();
        Frame startFrame = new Frame(server);
    }
}

