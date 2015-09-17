varying vec4 v_color;
varying vec2 v_texCoord0;

uniform sampler2D u_sampler2D;
void main() {
        float BlurAmount=10.0;
	float pixelSize=1.0/512.0;
	vec4 sum;
	    sum += texture2D(u_sampler2D, vec2(v_texCoord0.x-pixelSize*4.0*BlurAmount,v_texCoord0.y)) * 0.0162162162;
        sum += texture2D(u_sampler2D, vec2(v_texCoord0.x-pixelSize*3.0*BlurAmount,v_texCoord0.y)) * 0.0540540541;
        sum += texture2D(u_sampler2D, vec2(v_texCoord0.x-pixelSize*2.0*BlurAmount,v_texCoord0.y)) * 0.1216216216;
        sum += texture2D(u_sampler2D, vec2(v_texCoord0.x-pixelSize*1.0*BlurAmount,v_texCoord0.y)) * 0.1945945946;

        sum += texture2D(u_sampler2D, vec2(v_texCoord0.x, v_texCoord0.y)) * 0.2270270270;

        sum += texture2D(u_sampler2D, vec2(v_texCoord0.x-pixelSize*1.0*BlurAmount,v_texCoord0.y)) * 0.1945945946;
        sum += texture2D(u_sampler2D, vec2(v_texCoord0.x-pixelSize*2.0*BlurAmount,v_texCoord0.y)) * 0.1216216216;
        sum += texture2D(u_sampler2D, vec2(v_texCoord0.x-pixelSize*3.0*BlurAmount,v_texCoord0.y)) * 0.0540540541;
        sum += texture2D(u_sampler2D, vec2(v_texCoord0.x-pixelSize*4.0*BlurAmount,v_texCoord0.y)) * 0.0162162162;
	gl_FragColor=sum*v_color;

}