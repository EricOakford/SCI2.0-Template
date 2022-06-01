;;; Sierra Script 1.0 - (do not remove this comment)
(script# DEBUG)
(include game.sh)
(use Main)
(use Intrface)
(use String)
(use Print)
(use Feature)
(use Sound)
(use File)
(use User)
(use System)

(public
	debugRm 0
)

(local
	local0
	numBMPs
	local2
)
(instance debugSound of Sound)

(instance debugRm of Feature
	(method (init)
		(super init:)
		(self y: -1)
		(mouseDownHandler add: self)
		(keyDownHandler add: self)
	)
	
	(method (dispose)
		(mouseDownHandler delete: self)
		(keyDownHandler delete: self)
		(super dispose:)
	)
	
	(method (handleEvent event &tmp
				eventX eventY evt node i
				bmpFile obj roomNum theAlterEgo)
		(if (event claimed?) (return))
		(switch (event type?)
			(keyDown
				(event claimed: TRUE)
				(switch (event message?)
					(`?
						(Prints
							{ALT-C - show Cast\n
							ALT-B - capture BMP\n
							ALT-E - show Ego\n
							ALT-F - (vacant)\n
							ALT-G - show/set/clear Flag\n
							ALT-I - get Inventory Item\n
							ALT-M - show Memory\n
							ALT-P - (vacant)\n
							ALT-R - show Room info\n
							ALT-S - (vacant)\n
							ALT-T - teleport\n
							ALT-U - handsOn\n
							ALT-X - exit the Game\n
							CTRL-S - test a sound_}
						)
					)
					(`@b
						(while
							(and
								(< numBMPs 999)
								(= bmpFile (String format: {%03d.BMP} numBMPs))
								(FileIO FileExists (bmpFile data?))
							)
							(++ numBMPs)
						)
						(if (< numBMPs 999)
							(Printf {Screen saved as\n___%s} (bmpFile data?))
						else
							(Prints
								{Sorry, no can do. How did you get so many files?}
							)
						)
					)
					(`@c
						(if (cast size:)
							(= obj (String newWith: 75 {}))
							(= i (cast first:))
							(while i
								(= node (NodeValue i))
								(obj
									format:
										{class: %s\n
										name: %s\n
										view: %d\n
										loop: %d\n
										cel: %d\n
										posn: %d %d %d\n
										heading: %d\n
										pri: %d\n
										signal: $%x\n}
										((node -super-?) name?)
										(node name?)
										(node view?)
										(node loop?)
										(node cel?)
										(node x?)
										(node y?)
										(node z?)
										(node heading?)
										(node priority?)
										(node signal?)
								)
								(if
									(not
										(Print
											addText: (obj data?)
											addIcon:
												(node view?)
												(node loop?)
												(node cel?)
												(+ (Print x?) 80)
												(+ (Print y?) 80)
											init:
										)
									)
									(break)
								)
								(= i (cast next: i))
							)
							(obj dispose:)
						else
							(Prints {No One Home!})
							(return)
						)
					)
					(`@e
						(= node
							(cond 
								((cast contains: (user alterEgo?)) (user alterEgo?))
								(else (Prints {no ego!}) (return))
							)
						)
						(= obj (String newWith: 75 {}))
						(obj
							format:
								{name: %s\n
								view: %d\n
								loop: %d\n
								cel: %d\n
								posn: %d %d %d\n
								heading: %d\n
								pri: %d\n
								signal: $%x\n
								script: %s\n}
								(node name?)
								(node view?)
								(node loop?)
								(node cel?)
								(node x?)
								(node y?)
								(node z?)
								(node heading?)
								(node priority?)
								(node signal?)
								(if (node script?)
									((node script?) name?)
								else
									{..none..}
								)
						)
						(Print
							addText: (obj data?)
							addIcon:
								(node view?)
								(node loop?)
								(node cel?)
								(+ (Print x?) 80)
								(+ (Print y?) 80)
							init:
						)
						(obj dispose:)
					)
					(`@f
					)
					(`@g
						(= obj (String newWith: 75 {}))
						(Print
							font: userFont
							y: 100
							addText: {Flag num?}
							addEdit: obj 5 50
							init:
						)
						(= i (obj asInteger:))
						(obj dispose:)
						(switch
							(Print
								font: userFont
								y: 50
								addText: (if (gameFlags test: i)
									{flag is SET}
								else
									{flag is CLEARED}
								)
								addButton: 1 { set_} 0 12
								addButton: 2 {clear} 0 26
								addButton: -1 {cancel} 0 40
								init:
							)
							(1 (gameFlags set: i))
							(2 (gameFlags clear: i))
						)
					)
					(`@i
					)
					(`@m
						(theGame showMem:)
					)
					(`@p
					)
					(`@r
						(Printf
							{name: %s\n
							number: %d\n
							picture: %d\n
							style: %d\n
							horizon: %d\n
							north: %d\n
							south: %d\n
							east: %d\n
							west: %d\n
							script: %s_}
							(curRoom name?)
							curRoomNum
							(curRoom picture?)
							(curRoom style?)
							(curRoom horizon?)
							(curRoom north?)
							(curRoom south?)
							(curRoom east?)
							(curRoom west?)
							(if (curRoom script?)
								((curRoom script?) name?)
							else
								{..none..}
							)
							78
							120
						)
					)
					(`@s
					)
					(`^s
						(= i (GetNumber {setLoop?}))
						(= node (GetNumber {which sound number?}))
						(debugSound setLoop: i number: node play:)
					)
					(`@t
						(= roomNum (GetNumber {Which room number?}))
						(curRoom newRoom: roomNum)
					)
					(`@u
						(theGame handsOn:)
					)
					(`@x
						(= quit TRUE)
					)
					(else
						(event claimed: FALSE)
					)
				)
			)
			(mouseDown
				(switch (event modifiers?)
					(13 0)
					(14 0)
					(12
						(event claimed: TRUE)
						(event globalize:)
						(= eventX (event x?))
						(= eventY (event y?))
						(event localize: (cast plane?))
						(Printf
							{global: %d/%d\n local: %d/%d}
							eventX
							eventY
							(event x?)
							(event y?)
							75
							160
							10
							42
							999
						)
					)
					((| ctrlDown shiftRight)
						(event type: keyDown message: 4864)
						(self handleEvent: event)
					)
					((| ctrlDown shiftLeft)
						(event type: keyDown message: 4608)
						(self handleEvent: event)
					)
					(9 0)
					(10 0)
					(shiftRight 0)
					(shiftLeft 0)
					(ctrlDown 0)
					(altDown
						(event claimed: TRUE)
						(= theAlterEgo (User alterEgo?))
						(ego setMotion: 0)
						(while (!= ((= evt (Event new:)) type?) 2)
							(evt localize: (cast plane?))
							(theAlterEgo x: (evt x?) y: (evt y?))
							(if (theAlterEgo scaler?) ((theAlterEgo scaler?) doit:))
							(UpdateScreenItem theAlterEgo)
							(FrameOut)
							(evt dispose:)
						)
						(evt dispose:)
					)
				)
			)
		)
	)
)