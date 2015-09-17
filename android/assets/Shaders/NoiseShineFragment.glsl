varying vec4 v_color;
varying vec2 v_texCoord0;

uniform sampler2D u_sampler2D;

void main() {
	gl_FragColor = vec4(texture2D(u_sampler2D, v_texCoord0).rgb,1-sin(v_texCoord0.x)*sin(v_texCoord0.x)) * v_color;
}