# How to Play 

1 Clone the repo 
2 Open `cool-score-yo.sc` in SuperCollider
3 Evaluate once to load synthdefs
4 Evaluate to begin playback
5 Edit `~score` Event to update playback settings
6 Evaluate to perform updates

# `~score` parameters

The `~score` Event provides three high level params. 
`chroma` is the chromatic offset applied to all melodies. This is analogous to a "transposition" key on a keyboard.
`cps` is the cycles per second, aka BPM if you think about it. This is a tempo setting.
`cpp` is cycles per phrase. This describes the number of beats that go into the overarching muscal phrase. Powers of 2 work great (1,2,4,8,32...), just needs to be greater than 0.

# Synthdefs 

The synthdefs are in the "lead", "chords", and "bass" files. 

