(
var main = SynthDef(\cool_bass, {
	arg out, freq, amp = 0.1, dur = 2;

	var env = EnvGen.ar(Env.new([0, 1, 0], [0.005, dur], \lin));

	var hoov1 = freq + SinOsc.ar(dur/3, 0, 0.3).range(-1, 1);
	var hoov2 = freq + SinOsc.ar(dur/5, 0, 0.1).range(-2, 2);
	var hoov3 = freq + SinOsc.ar(dur/7, 0, 0.5).range(-0.75, 0.5);
	var sig = amp * env * LFTri.ar(Mix.ar([freq, hoov1, hoov2, hoov3, freq * 2, freq*3]));

	Out.ar(out, sig);
});

var notes = Scale.minor.degrees + ~score.chroma;


var synth = Bus.audio;

main.add;


Pbindef(\bass,
	\instrument, \cool_bass,
	\out, ~channel.bass,
	\amp, 0.5,
	\midinote, Prand(notes, inf),
	\dur, Pshuf(4 * [1/3, 1/2, 1], inf)
).play;


)