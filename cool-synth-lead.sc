(

var lead = SynthDef(\cool_lead, {
	arg out, freq, amp = 0.1, dur = 2;

	var env = EnvGen.ar(Env.new([1, 0], [dur], \lin));
	var glideEnv = EnvGen.ar(Env.new([1.2, 1], [dur/12], \exp));

	var sig = amp * env * LFSaw.ar(freq * glideEnv);

	Out.ar(out, sig);
	DetectSilence.ar(sig, doneAction:2);
});

var buses = Array.fill(2, { Bus.audio(Server.default) });
var notes = 60 + Scale.minor.degrees +  ~score.chroma;

var moduleMixdown = Bus.audio;

var synth = Bus.audio;

var delay = Bus.audio;

Synth.new(\cool_mixer, [
	\in, moduleMixdown,
	\out, ~channel.lead,
	\mix, 1
]);

Synth.new(\cool_mixer, [
	\in, synth,
	\out, moduleMixdown,
	\mix, 1/2
]);

Synth.new(\cool_mixer, [
	\in, delay,
	\out, moduleMixdown,
	\mix, 1/2
]);
Synth.new(\cool_delay, [
	\in, ~channel.lead,
	\out, delay
]);


lead.add;


Pbindef(\lead,
	\instrument, \cool_lead,
	\out, synth,
	\midinote, Prand(notes, inf),
	\dur, Pshuf([1/3, 1/2, 1], inf)
).play;

)
