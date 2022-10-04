package me.petrolingus.jlt.lwjgl;

import me.petrolingus.jlt.core.Bound;
import me.petrolingus.jlt.core.Particle;
import org.lwjgl.BufferUtils;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;

import java.nio.ByteBuffer;
import java.util.concurrent.ThreadLocalRandom;

public class Renderer {

    private static final int BPP = 4;

    private final long window;
    private final int width;
    private final int height;

    public ByteBuffer buffer;

    public Renderer(int width, int height, long window) {
        this.width = width;
        this.height = height;
        this.window = window;
        int whb = width * height * BPP;
        buffer = BufferUtils.createByteBuffer(whb);
    }

    public void loop() {

        GL.createCapabilities();
        GL11.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
        GL11.glPointSize(8);

        int n = 500;
        Bound bound = new Bound(-1, 1, 1, -1);

        Particle[] particles = new Particle[n];
        for (int i = 0; i < n; i++) {
            double x = ThreadLocalRandom.current().nextDouble(-0.99, 0.99);
            double y = ThreadLocalRandom.current().nextDouble(-0.99, 0.99);
            double vx = ThreadLocalRandom.current().nextDouble(-0.0005, 0.0005);
            double vy = ThreadLocalRandom.current().nextDouble(-0.0005, 0.0005);
            Particle particle = new Particle(x, y, vx, vy);
            particle.setBound(bound);
            particles[i] = particle;
        }

        while (!GLFW.glfwWindowShouldClose(window)) {
            GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT | GL11.GL_STENCIL_BUFFER_BIT);

            GL11.glBegin(GL11.GL_POINTS);
            for (Particle particle : particles) {
                double x = particle.x;
                double y = particle.y;
                GL11.glVertex2d(x, y);
            }
            GL11.glEnd();

            for (Particle particle : particles) {
                particle.move();
            }

            GL11.glReadPixels(0, 0, width, height, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, buffer);
        }

    }

}
