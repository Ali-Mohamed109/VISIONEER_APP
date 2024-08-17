#version 300 es
layout(location = 0) in vec4 a_Position;
uniform mat4 u_ModelViewProjection;

void main() {
    gl_Position = u_ModelViewProjection * a_Position;
}