package me.petrolingus.jlt.lwjgl;

import org.lwjgl.glfw.Callbacks;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.system.MemoryUtil;

import java.nio.ByteBuffer;
import java.util.Objects;

public class Window {

    private final int width;

    private final int height;

    private long window;

    private Renderer renderer;

    private boolean isInit;

    public Window(int width, int height) {
        this.width = width;
        this.height = height;
        this.isInit = false;
    }

    public static void main(String[] args) {
        new Window(700, 700).run();
    }

    public void run() {
        init();
        this.renderer = new Renderer(width, height, window);
        this.isInit = true;
        loop();

        // Free the window callbacks and destroy the window
        Callbacks.glfwFreeCallbacks(window);
        GLFW.glfwDestroyWindow(window);

        // Terminate GLFW and free the error callback
        GLFW.glfwTerminate();
        Objects.requireNonNull(GLFW.glfwSetErrorCallback(null)).free();
    }

    private void init() {
        GLFWErrorCallback.createPrint(System.err).set();

        if (!GLFW.glfwInit()) {
            throw new IllegalStateException("Unable to initialize GLFW");
        }

        // Configure GLFW
        GLFW.glfwDefaultWindowHints();
        GLFW.glfwWindowHint(GLFW.GLFW_VISIBLE, GLFW.GLFW_TRUE);
        GLFW.glfwWindowHint(GLFW.GLFW_RESIZABLE, GLFW.GLFW_FALSE);
        GLFW.glfwWindowHint(GLFW.GLFW_STENCIL_BITS, 4);
        GLFW.glfwWindowHint(GLFW.GLFW_SAMPLES, 4);

        // Create the window
        window = GLFW.glfwCreateWindow(width, height, "LWJGL Window", MemoryUtil.NULL, MemoryUtil.NULL);
        if (window == MemoryUtil.NULL) {
            throw new RuntimeException("Failed to create the GLFW window");
        }

        //noinspection resource
        GLFW.glfwSetKeyCallback(window, (window, key, scancode, action, mods) -> {
            if (key == GLFW.GLFW_KEY_ESCAPE && action == GLFW.GLFW_RELEASE) {
                GLFW.glfwSetWindowShouldClose(window, true);
            }
        });

        GLFW.glfwMakeContextCurrent(window);
        GLFW.glfwSwapInterval(1);
//        GLFW.glfwHideWindow(window);
    }

    private void loop() {
        renderer.loop();
    }

    public boolean isInit() {
        return isInit;
    }

    public ByteBuffer getScreen() {
        if (!isInit) return null;
        return renderer.buffer;
    }
}
