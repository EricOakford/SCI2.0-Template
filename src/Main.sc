;;; Sierra Script 1.0 - (do not remove this comment)
;
;	MAIN.SC
;
;	This is the main game script. It contains the main game instance and all the global variables.
;
;	In addition to the above, it contains the crucial default Messager
;	and its findTalker method (used for mapping talker numbers to a Talker or Narrator instance).
;

(script# MAIN)
(include game.sh) (include "0.shm")
(use GameEgo)
(use Procs)
(use Intrface)
(use Dialog)
(use Print)
(use Talker)
(use Messager)
(use PMouse)
(use Polygon)
(use PolyPath)
(use IconBar)
(use Feature)
(use Flags)
(use GameWindow)
(use Plane)
(use StopWalk)
(use Sound)
(use Game)
(use User)
(use System)

(public
	SCI2 0
)

(local
	ego								  	;pointer to ego
	theGame							  	;ID of the Game instance
	curRoom							  	;ID of current room
	thePlane							;default plane
	quit							  	;when TRUE, quit game
	cast							  	;collection of actors
	regions							  	;set of current regions
	timers							  	;list of timers in the game
	sounds							  	;set of sounds being played
	inventory						  	;set of inventory items in game
	planes								;list of all active planes in the game
	curRoomNum						  	;current room number
	prevRoomNum						  	;previous room number
	newRoomNum						  	;number of room to change to
	debugOn							  	;generic debug flag -- set from debug menu
	score							  	;the player's current score
	possibleScore					  	;highest possible score
	textCode							;code that handles interactive text
	cuees							  	;list of who-to-cues for next cycle
	theCursor						  	;the number of the current cursor
	normalCursor						;number of normal cursor form
	waitCursor							;cursor number of "wait" cursor
	userFont		=	USERFONT	  	;font to use for Print
	smallFont		=	4 			  	;small font for save/restore, etc.
	lastEvent						  	;the last event (used by save/restore game)
	eventMask	=	allEvents	  		;event mask passed to GetEvent in (uEvt new:)
	bigFont			=	USERFONT	  	;large font
	version			=	0			  	;pointer to 'incver' version string
										;	WARNING!  Must be set in room 0
										;	(usually to {x.yyy    } or {x.yyy.zzz})
	autoRobot
	curSaveDir							;address of current save drive/directory string
	numCD	=	0						;number of current CD, 0 for file based
	perspective							;player's viewing angle: degrees away
										;	from vertical along y axis
	features							;locations that may respond to events
	panels	=	NULL					;list of game panels
	useSortedFeatures	=	FALSE		;enable cast & feature sorting?
	unused_6
	overlays			=	-1
	doMotionCue							;a motion cue has occurred - process it
	systemPlane							;ID of standard system plane
	saveFileSelText						;text of fileSelector item that's selected.
	unused_8
	unused_2
	[sysLogPath	20]						;-used for system standard logfile path	
	endSysLogPath						;/		(uses 20 globals)
	gameControls						;pointer to instance of game controls
	ftrInitializer						;pointer to code that gets called from
										;	a feature's init
	doVerbCode							;pointer to code that gets invoked if
										;	no feature claims a user event
	approachCode						;pointer to code that translates verbs
										;	into bits
	useObstacles	=	TRUE			;will Ego use PolyPath or not?
	unused_9
	theIconBar							;points to TheIconBar or Null	
	mouseX								;-last known mouse position
	mouseY								;/
	keyDownHandler						;-our EventHandlers, get called by game
	mouseDownHandler					;/
	directionHandler					;/
	speechHandler						;a special handler for speech events
	lastVolume
	pMouse			=	NULL			;pointer to a Pseudo-Mouse, or NULL
	theDoits		=	NULL			;list of objects to get doits each cycle
	eatMice			=	60				;how many ticks before we can mouse
	user			=	NULL			;pointer to specific applications User
	syncBias							;-globals used by sync.sc
	theSync								;/		(will be removed shortly)
	extMouseHandler						;extended mouse handler
	talkers							;list of talkers on screen
	inputFont		=	SYSFONT			;font used for user type-in
	tickOffset							;used to adjust gameTime after restore
	howFast								;measurment of how fast a machine is
	gameTime							;ticks since game start
	narrator							;pointer to narrator (normally Narrator)
	msgType			=	TEXT_MSG		;type of messages used
	messager							;pointer to messager (normally Messager)
	prints								;list of Print's on screen
	walkHandler							;list of objects to get walkEvents
	textSpeed		=	2				;time text remains on screen
	altPolyList							;list of alternate obstacles
	;globals 96-99 are unused
		global96
		global97
		global98
	lastSysGlobal
	;globals 100 and above are for game use	
	
	;these globals are retained at restart, as they are pointers to objects
	statusLineCode			;pointer for status line code
	theMusic				;music object, current playing music
	theMusic2				;ambient sound
	gameFlags				;pointer for Flags object, which only requires one global
	iconSettings
	theCurIcon
	egoLooper
	keep107
	keep108
	keep109
	keep110			
	;end globals to retain at restart
	
	;standard globals for colors
	colBlack
	colGray1
	colGray2
	colGray3
	colGray4
	colGray5
	colWhite
	colDRed
	colLRed
	colVLRed
	colDYellow
	colYellow
	colLYellow
	colVDGreen
	colDGreen
	colLGreen
	colVLGreen
	colDBlue
	colBlue
	colLBlue
	colVLBlue
	colMagenta
	colLMagenta
	colCyan
	colLCyan
	;end standard color globals

	myTextColor				;color of text in message boxes
	myBackColor				;color of message boxes
	saveCursorX				; position of cursor when HandsOff is used
	saveCursorY				;
	numDACs					;Number of voices supported by digital audio driver
	numVoices				;Number of voices supported by sound driver
	debugging				;debug mode enabled
	isHandsOff				;ego can't be controlled
	deathReason				;message to display when calling EgoDead
)

;
; Global sound objects
(instance longSong of Sound
	(properties
		flags mNOPAUSE
	)
)

(instance longSong2 of Sound
	(properties
		flags mNOPAUSE
	)
)

;
;  Sound used only by theGame:solvePuzzle
(instance pointsSound of Sound
	(properties
		number sScore
		flags mNOPAUSE
	)
)

;
; The main game instance. It adds game-specific functionality.	
; Replace "SCI2" with the game's internal name (up to 6 characters)
(instance SCI2 of Game	
	(properties
		printLang ENGLISH	;set your game's language here. Supported languages can be found in SYSTEM.SH.
	)

	(method (init)
		;load up the standard game system
		(= systemPlane Plane)
		(= version {x.yyy})
		(super init:)

		;initialize the colors first
		((ScriptID COLOR_INIT 0) doit:)
		
		;set up the global sounds
		((= theMusic longSong)
			owner: self
			init:
		)
		((= theMusic2 longSong2)
			owner: self
			init:
		)
		
		(pointsSound
			owner: self
			init:
			setPri: 15
			setLoop: 1
		)
		
		;set up doVerb and feature initializer code
		(= doVerbCode gameDoVerbCode)
		(= ftrInitializer gameFtrInit)
		
		;assign code instances to variables
		(= pMouse PseudoMouse)
		(= messager gameMessager)
		(= approachCode gameApproachCode)
		(= handsOffCode gameHandsOff)
		(= handsOnCode gameHandsOn)
		((= gameFlags gameEventFlags)
			init:
		)
		((= altPolyList (List new:))
			name: {altPolys}
			add:
		)
		
		;set up the ego
		(= ego GameEgo)
		(= egoLooper (ScriptID GAME_EGO 1))
		(user alterEgo:  ego)

		;initialize icon bar, control panel, inventory, and status line
		((ScriptID GAME_ICONBAR 0) init:)
		((ScriptID GAME_CONTROLS 0) init:)
		((ScriptID GAME_INV 0) init:)
		((ScriptID STATUS_LINE 0) init:)
		(= statusLineCode (ScriptID STATUS_LINE 1))
		
		;initialize everything else in the retart room
		(self newRoom: GAME_RESTART)
	)

	(method (startRoom n)
		(if debugging
			((ScriptID DEBUG 0) init:)
		)
		(statusLineCode doit: n)
		(super startRoom: n)
	)

	(method (pragmaFail &tmp theVerb)
		;nobody responds to user input
		(if (user canInput:)
			(= theVerb ((user curEvent?) message?))
			(if (OneOf theVerb V_DO V_LOOK V_TALK)
				(messager say: N_PRAGFAIL theVerb NULL 1 0 MAIN)
			else ;non-handled verb
				(messager say: N_PRAGFAIL V_COMBINE NULL 1 0 MAIN)
			)
		)
	)

	(method (handleEvent event)
		(super handleEvent: event)
		(if (event claimed?) (return TRUE))
		(return
			(switch (event type?)
				(keyDown
					(switch (event message?)
						(TAB
							(if (not (& ((theIconBar at: ICON_INVENTORY) signal?) DISABLED))
								(ego showInv:)
							)
						)
						(SHIFTTAB
							(if (not (& ((theIconBar at: ICON_INVENTORY) signal?) DISABLED))
								(ego showInv:)
							)
						)
						(`^c
							(if (not (& ((theIconBar at: ICON_CONTROL) signal?) DISABLED))
								(theGame showControls:)
								(event claimed: TRUE)
							)
						)
						(`#2
							(cond
								((theGame masterVolume:)
									(theGame masterVolume: 0)
								)
								((> numVoices 1)
									(theGame masterVolume: 15)
								)
								(else
									(theGame masterVolume: 1)
								)
							)
							(event claimed: TRUE)
						)
						(`#5
							(if (not (& ((theIconBar at: ICON_CONTROL) signal?) DISABLED))
								(theGame save:)
								(event claimed: TRUE)
							)
						)
						(`#7
							(if (not (& ((theIconBar at: ICON_CONTROL) signal?) DISABLED))
								(theGame restore:)
								(event claimed: TRUE)
							)
						)
						(`#9
							(theGame restart:)
							(event claimed: TRUE)
						)
						(`^q
							(theGame quitGame:)
							(event claimed: TRUE)
						)
					)
				)
			)
		)
	)
	
	(method (solvePuzzle pValue pFlag)
		;Adds an amount to the player's current score.
		;It checks if a certain flag is set so that the points are awarded only once.
		(if (and (> argc 1) (gameFlags test: pFlag))
			(return)
		)
		(if pValue
			(theGame changeScore: pValue)
			(if (and (> argc 1) pFlag)
				(gameFlags set: pFlag)
				(statusLineCode doit: curRoomNum)
				(pointsSound play:)
			)
		)
	)

	(method (showAbout)
		((ScriptID GAME_ABOUT 0) doit:)
		(DisposeScript GAME_ABOUT)
	)
	
	(method (restart)
		;if a parameter is given, skip the dialog and restart immediately
		(if argc
			(curRoom newRoom: GAME_RESTART)
		else
			;the game's restart dialog
			(if (YesNoDialog N_RESTART)
				(curRoom newRoom: GAME_RESTART)
			)
		)
	)

	(method (quitGame)
		(if (YesNoDialog N_QUITGAME)
			(super quitGame:)
		)
	)

	(method (showControls &tmp oldCur)
		(theIconBar hide:)
		(gameControls showSelf:)
	)
)

(instance gameDoVerbCode of Code
	;if there is no corresponding message for an object and verb, bring up a default message.
	(method (doit theVerb)
		(if (OneOf theVerb V_LOOK V_DO V_TALK)
			(messager say: N_VERB_GENERIC theVerb NULL 1 0 MAIN)
		else ;non-handled verb
			(messager say: N_VERB_GENERIC V_COMBINE NULL 1 0 MAIN)
		)
	)
)

(instance gameFtrInit of Code		; sets up defaults
	(method (doit theObj)
		; angle used by facingMe
		(if (== (theObj sightAngle?) ftrDefault)
			(theObj sightAngle: 90)
		)
		; maximum distance to get an object (for example.)
		; instance of Action or EventHandler with Actions
		(if (== (theObj actions?) ftrDefault)
			(theObj actions: 0)
		)
	)
)

(instance gameMessager of Messager
	(method (findTalker who &tmp theTalker)
		(if
			(= theTalker
				(switch who
					;Add the talkers here, using the defines you set in the message editor
					;from TALKERS.SH
					(else  narrator)
				)
			)
			(return)
		else
			(super findTalker: who)
		)
	)
)

(instance gameApproachCode of Code
	(method (doit theVerb)
		(switch theVerb
			(V_LOOK $0001)
			(V_TALK $0002)
			(V_WALK $0004)
			(V_DO $0008)
			(else  $8000)
		)
	)
)

(instance gameHandsOff of Code
	;Disable ego control
	(method (doit)
		(if (not theCurIcon)	; don't want to save it twice!
			(= theCurIcon (theIconBar curIcon?))
		)
		
		(= isHandsOff TRUE)
		(user
			canControl: FALSE
			canInput: FALSE
		)
		(ego setMotion: 0)
		
		; save the state of each icon so we can put the icon bar back the way it was
		(= iconSettings 0)
		(theIconBar
			eachElementDo: #perform checkIcon
			curIcon: (theIconBar at: ICON_CONTROL)
		)
	
		; disable some icons so user doesn't screw us up
		(theIconBar disable:
			ICON_WALK
			ICON_LOOK
			ICON_DO
			ICON_TALK
			ICON_ITEM
			ICON_INVENTORY
		)

		(theGame setCursor: waitCursor TRUE)
	)
)

(instance gameHandsOn of Code
	;Ensable ego control
	(method (doit)
		(= isHandsOff FALSE)
		(User
			canControl: TRUE
			canInput: TRUE
		)
		
		; re-enable iconbar
		(theIconBar enable:
			ICON_WALK
			ICON_LOOK
			ICON_DO
			ICON_TALK
			ICON_ITEM
			ICON_INVENTORY
			ICON_CONTROL
			ICON_HELP
		)
		(if (not (theIconBar curInvIcon?))
			(theIconBar disable: ICON_ITEM)
		)
	
		(if theCurIcon
			(theIconBar curIcon: theCurIcon)
			(theGame setCursor: (theCurIcon getCursor:))
			(= theCurIcon 0)
			(if (and	(== (theIconBar curIcon?) (theIconBar at: ICON_ITEM))
						(not (theIconBar curInvIcon?))
					)
				(theIconBar advanceCurIcon:)
			)
		)
		(theGame setCursor: ((theIconBar curIcon?) getCursor:) TRUE)
	)
)

(instance checkIcon of Code
	(method (doit theIcon)
		(if (theIcon isKindOf: IconItem)		; It's an icon
			(if (& (theIcon signal?) DISABLED)
				(|= iconSettings (>> $8000 (theIconBar indexOf: theIcon)))
			)
		)
	)
)

(instance gameEventFlags of Flags
	(properties
		size NUMFLAGS
	)
)