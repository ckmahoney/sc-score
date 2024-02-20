(
~fx = (
    cool_delay: SynthDef(\cool_delay, {
        arg in, out, dt = 1000, fb = 0.2;
        var seconds = dt / 1000;
        var sig = In.ar(in);
        var local = fb * LocalIn.ar(1);
        var wet = AllpassL.ar(sig + local, seconds * 8, seconds, 3);
        LocalOut.ar(wet);
        Out.ar(out, wet);
    }),

    cool_mixer: SynthDef(\cool_mixer, {
        arg in=99, out=0, mix=0.5;
        Out.ar(out, mix * In.ar(in, 1));
    }),
	cool_mixer_stereo: SynthDef(\cool_mixer_stereo, {
        arg in=99, out=0, mix=0.5;
        Out.ar(out, mix * In.ar(in, 2));
    }),

    cool_chorus: SynthDef(\cool_chorus, {
		arg in, out, depth = 0.1, fb = 0.1;
        var sig = In.ar(in);
        var baseDelay = 0.02; // Base delay time in seconds
        var modFreq2 = SinOsc.kr(200).range(0.01, 0.201);  // Modulation frequency for the LFO
        var modFreq3 = SinOsc.kr(205).range(0.02, 0.204);  // Modulation frequency for the LFO
        var mod1 = SinOsc.kr(modFreq3) * depth + baseDelay;
        var mod2 = SinOsc.kr(modFreq2) * depth + baseDelay;
        var chorus1 = DelayC.ar(sig, 6, LFSaw.kr(1/21).range(0.005, 0.025));
        var chorus2 = DelayC.ar(sig, 6, SinOsc.kr(1/17).range(0.002, 0.022));
        var wet = Mix.new([chorus1, chorus2] * 0.5);
        Out.ar(out, wet.dup);
    }),

    cool_band: SynthDef(\cool_band, {
        arg in, out, min = 20, max = 21500;
        var sig = In.ar(in);
        var wet = LPF.ar(HPF.ar(sig, min), max);
        var gain = RMS.kr(sig)/RMS.kr(wet);
        Out.ar(out, sig * gain);
    }),

    cool_reverb_stereo: SynthDef(\cool_reverb_stereo, {
        arg in, out, decay = 1000;
        var sig = In.ar(in);
        var wet = FreeVerb2.ar(sig, sig, 1, SinOsc.kr(1/5).range(0.2, 0.4), LFSaw.kr(1/7).range(0, 0.1));
        Out.ar(out, wet);
    })
);

~fx.keysValuesDo({ |key, value|
    value.add;
});
)
