(
var lead = SynthDef(\cool_lead, {
	arg out, freq, amp = 1, dur = 2;

	var env = EnvGen.ar(Env.new([1, 0], [dur], \lin));
	var glideEnv = EnvGen.ar(Env.new([1.2, 1], [dur/12], \exp));

	var sig = amp * env * LFSaw.ar(freq * glideEnv);

	Out.ar(out, sig);
});

var delay = SynthDef(\cool_delay, {
	arg in, out, dt = 1000, fb = 0.2;
	var seconds = dt/1000;

	var sig = In.ar(in);
	var local = fb * LocalIn.ar(1);
	var wet = AllpassL.ar(sig + local, seconds * 8, seconds, 3);

	LocalOut.ar(wet);
	Out.ar(out, wet);
});

var mixer = SynthDef(\cool_mixer, {
	arg in=99, out=0, mix=0.5;

	Out.ar(out, mix * In.ar(in, 1));
});


var buses = Array.fill(8, { Bus.audio(Server.default) });


lead.add;
mixer.add;
delay.add;

c = Synth.new(\cool_mixer, [
	\in, buses[4],
	\out, 0,
	\mix, 1/4
]);

b=Synth.new(\cool_delay, [
	\in, buses[0],
	\out, buses[4]
]);

a= Synth.new(\cool_mixer, [
	\in, buses[0],
	\out, 0,
	\mix, 1/2
]);

Pbindef(\lead,
	\instrument, \cool_lead,
	\out, buses[0],
	\midinote, Prand(60 + Scale.minor.degrees, inf),
	\dur, Pshuf([1/3, 1/2, 1], inf)
).play;


)