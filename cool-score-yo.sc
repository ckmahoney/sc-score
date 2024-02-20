(
var submix = Bus.audio;

~channel = (
    bass: Bus.audio,
    chords: Bus.audio,
    lead: Bus.audio
);

~mix = (
    down: Bus.audio,
    out: [0, 1]
);

~score = (
	chroma: 5, // chromatic offset (key signature)
	cps: 1.2,  // cycles per second (tempo)
	cpp: 32    // cycles per phrase (phrasing)
);

~assignFreqRange = { |channelKey|
    switch(channelKey,
        'bass',   { [20, 7000] },
        'chords', { [100, 12000] },
        'lead',   { [200, 20000] },
        // Default case for any other channels
        { [20, 20000] }
    )
};

Synth.new(\cool_mixer, [
    \in, ~mix.down,
    \out, ~mix.out
]);

~channel.keysValuesDo({|key, val|
    // Get the frequency range for the current channel
    var freqRange = ~assignFreqRange.(key);

	var dry = Bus.audio;
	var wet = Bus.audio;
	var localmix = Bus.audio;

	Synth.new(\cool_mixer, [
        \in, localmix ,
        \out, ~mix.down,
        \mix, 1
    ]);

	Synth.new(\cool_mixer, [
        \in, dry ,
        \out,localmix,
        \mix, 0.25
    ]);

	Synth.new(\cool_mixer, [
        \in, wet ,
        \out,localmix,
        \mix, 0.75
    ]);

    // Apply High Pass Filter (HPF) and Low Pass Filter (LPF)
    Synth.new(\cool_band, [
        \in, val,
        \out, wet,
        \min, freqRange[0],
        \max, freqRange[1]
    ]);


});

// dependency chaining
loadRelative("cool-synth-fx.sc", true, {
    var scripts = [
        "cool-synth-bass.sc",
        "cool-synth-lead.sc",
        "cool-synth-chords.sc"
    ];
    "loaded audio effects".postln;
    scripts.do({|name|
        postln("Loading module " + name);
        loadRelative(name);
    });
});

// make the bass a little quieter
Pbindef(\bass, \amp, 0.5);
)
