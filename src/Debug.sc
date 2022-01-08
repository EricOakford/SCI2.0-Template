;;; Sierra Script 1.0 - (do not remove this comment)
(script# DEBUG)
(include game.sh)
(use Main)
(use Intrface)
(use DButton)
(use DText)
(use Plane)
(use String)
(use Print)
(use Dialog)
(use Feature)
(use Ego)
(use File)
(use Invent)
(use User)
(use Actor)
(use System)

(public
	debugHandler 0
)

(local
	newDButton
	egoInCast
	bmpFile
)

(procedure (localproc_0028 param1 param2 &tmp str)
	(= str (String newWith: 80 {}))
	(if (> argc 1) (str format: {%d} param2))
	(return
		(if (GetInput (str data?) 10 param1)
			(str asInteger:)
		else
			-1
		)
	)
)

(instance debugHandler of Feature
	(properties
		y -1
	)
	
	(method (init)
		(super init:)
		(mouseDownHandler addToFront: self)
		(keyDownHandler addToFront: self)
	)
	
	(method (dispose)
		(mouseDownHandler delete: self)
		(keyDownHandler delete: self)
		(sysLogPath dispose:)
		(super dispose:)
		(DisposeScript DEBUG)
	)
	
	(method (handleEvent event &tmp
				str evt node obj i)
		(= sysLogPath (String new: 1000))
		(= str (String new:))
		(switch (event type?)
			(keyDown
				(event claimed: TRUE)
				(switch (event message?)
					(ESC
						(= quit TRUE)
					)
					(`@a
						(= node (cast first:))
						(while node
							(= obj (NodeValue node))
							(str
								format:
									{name: %s\n
									class: %s\n
									view: %d\n
									loop: %d\n
									cel: %d\n
									posn: %d %d %d\n
									heading: %d\n
									pri: %d\n
									signal: $%x\n
									scaleSignal: $%x\n
									scaleX: %d\n
									scaleY: %d\n
									illBits: $%x\n}
									(obj name?)
									((obj -super-?) name?)
									(obj view?)
									(obj loop?)
									(obj cel?)
									(obj x?)
									(obj y?)
									(obj z?)
									(obj heading?)
									(obj priority?)
									(obj signal?)
									(obj scaleSignal?)
									(obj scaleX?)
									(obj scaleY?)
									(if
										(or
											(== (obj -super-?) Actor)
											(== (obj -super-?) Ego)
										)
										(obj illegalBits?)
									else
										-1
									)
							)
							(if (not (obj scaleSignal?))
								(Print
									addIcon: (obj view?) (obj loop?) (obj cel?) 0 0
									font: smallFont
									addText: str (CelWide (obj view?) (obj loop?) (obj cel?)) 0
									init:
								)
							else
								(Print font: smallFont addText: str 0 0 init:)
							)
							(= node (cast next: node))
						)
					)
					(2
						(= bmpFile (String format: {%03d.BMP} curRoomNum))
						(if (FileIO FileExists (bmpFile data?))
							(Printf {%03d.BMP SUCCESSFULLY CREATED} curRoomNum)
						else
							(Printf {ERROR CREATING %03d.BMP} curRoomNum)
						)
					)
					(`@b
					)
					(`@c
						(repeat
							(if
								(or
									(== ((= event (Event new:)) type?) mouseDown)
									(== (event type?) keyDown)
								)
								(break)
							)
							(event dispose:)
						)
						(event dispose:)
					)
					(`@d
						(if (= debugOn (not debugOn))
							(Prints {On})
						else
							(Prints {Off})
						)
					)
					(`@f
						(= i 0)
						(= i (GetNumber {Flag #:}))
						(if (Btst i)
							(Prints {cleared})
							(Bclr i)
						else
							(Prints {set})
							(Bset i)
						)
					)
					(`@g
						(GetInput str 5 {Variable No.})
						(if (not (= node (str asInteger:))) (return))
						(GetInput str 5 {Value})
						(= [ego node] (str asInteger:))
					)
					(`@i
						(ego get: (GetNumber str))
					)
					(`@m
						(theGame showMem:)
					)
					(`@l
						((ScriptID LOGGER) doit:)
					)
					(`@p)
					(`@q
						(Print
							font: smallFont
							addTextF: {Cur X: %d,Y: %d} (event x?) (event y?)
							init:
						)
					)
					(`@r
						(str
							format:
								{Current Room\n
								name: %s\n
								script: %s\n
								horizon: %d\n
								vanishingX: %d\n
								vanishingY: %d\n
								picAngle: %d\n
								north: %d\n
								south: %d\n
								east: %d\n
								west: %d\n
								style: %d\n
								curPic: %d}
								(curRoom name?)
								(if (curRoom script?)
									((curRoom script?) name?)
								else
									{none}
								)
								(curRoom horizon?)
								(curRoom vanishingX?)
								(curRoom vanishingY?)
								(curRoom picAngle?)
								(curRoom north?)
								(curRoom south?)
								(curRoom east?)
								(curRoom west?)
								(curRoom style?)
								(curRoom curPic?)
						)
						(Print font: smallFont addText: str 0 0 init:)
					)
					(`@t
					)
					(`@s
						(= node (cast first:))
						(while node
							(= obj (NodeValue node))
							(str
								format:
									{Updating cast members\n
									name: %s\n
									class: %s\n
									view: %d\n
									loop: %d\n
									cel: %d\n
									posn: %d %d %d\n
									heading: %d\n
									pri: %d\n
									signal: $%x\n
									illBits: $%x\n}
									(obj name?)
									((obj -super-?) name?)
									(obj view?)
									(obj loop?)
									(obj cel?)
									(obj x?)
									(obj y?)
									(obj z?)
									(obj heading?)
									(obj priority?)
									(obj signal?)
									(if
										(or
											(== (obj -super-?) Actor)
											(== (obj -super-?) Ego)
										)
										(obj illegalBits?)
									else
										-1
									)
							)
							(if (not (obj scaleSignal?))
								(Print
									addIcon: (obj view?) (obj loop?) (obj cel?) 0 0
									font: smallFont
									addText: str (CelWide (obj view?) (obj loop?) (obj cel?)) 0
									init:
								)
							else
								(Print font: smallFont addText: str 0 0 init:)
							)
							(= node (cast next: node))
						)
					)
					(`@u
						(theGame handsOn:)
					)
					(`@v)
					(`@x
						(= quit TRUE)
					)
					(`@y
						(Print
							font: smallFont
							addTextF:
								{vanishing x: %d,y: %d}
								(curRoom vanishingX?)
								(curRoom vanishingY?)
							init:
						)
						(= node (localproc_0028 {vanishingX:}))
						(if (OneOf node -1 0)
						else
							(curRoom vanishingX: node)
						)
						(= node (localproc_0028 {vanishingY:}))
						(if (OneOf node -1 0)
						else
							(curRoom vanishingY: node)
						)
						(Print
							font: smallFont
							addTextF:
								{vanishing x: %d,y: %d}
								(curRoom vanishingX?)
								(curRoom vanishingY?)
							init:
						)
					)
					(`@z
						(= evt (Event new:))
						((user alterEgo?)
							posn: (evt x?) (- (evt y?) 10)
							setMotion: 0
						)
						(evt dispose:)
					)
					(`@h
						(Print
							font: smallFont
							addText:
								{ALT-A show Cast\n
								ALT-B (vacant)\n
								ALT-C Control map\n
								ALT-D DebugOn toggle\n
								ALT-F Flag set/clr\n
								ALT-G Global set\n
								ALT-I Inv items\n
								ALT-L Log file\n
								ALT-M Memory\n
								ALT-P Priority map\n
								ALT-Q show Cursor Coords\n
								ALT-R Room info\n
								ALT-S Updating cast elements\n
								ALT-T Teleport\n
								ALT-U return User control\n
								ALT-V Visual map\n
								ALT-W (vacant)\n
								ALT-Y Vanishing point adj\n
								ALT-Z position ego at cursor}
							init:
						)
					)
					(else
						(event claimed: FALSE)
					)
				)
			)
			(mouseDown
				(if (== (event modifiers?) altDown)
					(event claimed: TRUE)
					(while (!= mouseUp ((= evt (Event new:)) type?))
						((user alterEgo?)
							posn: (evt x?) (- (evt y?) 10)
							setMotion: 0
						)
						(evt dispose:)
					)
					(evt dispose:)
				)
			)
		)
		(str dispose:)
	)
)