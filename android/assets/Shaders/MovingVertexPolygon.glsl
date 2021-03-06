attribute vec4 a_color;
attribute vec3 a_position;
attribute vec2 a_texCoord0;

uniform mat4 u_projTrans;

varying vec4 v_color;
varying vec2 v_texCoord0;
void main() {
	v_color = vec4(a_color.r-sin(a_position.y),a_color.g-sin(a_position.y),a_color.b-sin(a_position.y),a_color.a);
	v_texCoord0 = a_texCoord0;
	gl_Position =  u_projTrans * vec4(a_position.x+sin(a_position.y),a_position.y,a_position.z, 1.);
}