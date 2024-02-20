(
// an instrument designed to be played by polyphonic part (like organ or accordion)
var main = SynthDef(\cool_chords, {
	arg out, freq, amp = 0.1, dur = 2;

	var env = EnvGen.ar(Env.new([0.4, 1, 0.5, 0.05, 0], [dur/7, dur/5, dur/3, dur/3, dur], \exp));

	var sig = amp * env * LFPulse.ar(freq);

	Out.ar(out, sig);
	DetectSilence.ar(sig, doneAction:2);
});

var synth = Bus.audio;
var chorus = Bus.audio;
var moduleMixdown = Bus.audio;

var notes = 60 + Scale.minor.degrees + ~score.chroma;

// extend the range by 1.5 octaves
notes = notes ++ (notes + 12) ++ (notes + 7);

main.add;


Synth.new(\cool_mixer, [
	\in, moduleMixdown,
	\out, ~channel.chords,
	\mix, 1
]);

Synth.new(\cool_mixer, [
	\in, synth,
	\out, moduleMixdown,
	\mix, 1/2
]);

Synth.new(\cool_mixer, [
	\in, chorus,
	\out, moduleMixdown,
	\mix, 1/2
]);


Synth.new(\cool_chorus, [
	\in, synth,
	\out, chorus.index,
	\mix, 0,
]);


Pbindef(\chords,
	\instrument, \cool_chords,
	\out, ~channel.chords,
	\amp, 0.05 / 3,
	\midinote, Pfunc({
		var n = 2 + 2.rand;
		notes.scramble.copyRange(0, n);
	}, inf),
	\dur, Pshuf(2 * [1/3, 1/2, 1], inf)
).play;

)