;;; Sierra Script 1.0 - (do not remove this comment)
;
;	 MAIN.SC
;
;	 This is the main game script. It contains the main game instance and all the global variables.
;	
;	 In addition to the above, it contains the crucial default Messager
;	 and its findTalker method (used for mapping talker numbers to a Talker or Narrator instance).
;
;

(script# MAIN)
(include game.sh) (include "0.shm")
(use Plane)
(use Game)
(use Messager)
(use Grooper)
(use Talker)
(use IconBar)
(use GameEgo)
(use StopWalk)
(use Ego)
(use System)

(public
	SCI2 0
)

(local
	ego								;pointer to ego
	theGame							;ID of the Game instance
	curRoom							;ID of current room
	thePlane						;default plane
	quit							;when TRUE, quit game
	cast							;collection of actors
	regions							;set of current regions
	timers							;list of timers in the game
	sounds							;set of sounds being played
	inventory						;set of inventory items in game
	planes							;list of all active planes in the game
	curRoomNum						;current room number
	prevRoomNum						;previous room number
	newRoomNum						;number of room to change to
	debugOn							;generic debug flag -- set from debug menu
	score							;the player's current score
	possibleScore					;highest possible score
	textCode						;code that handles interactive text
	cuees							;list of who-to-cues for next cycle
	theCursor						;the number of the current cursor
	normalCursor					;number of normal cursor form
	waitCursor						;cursor number of "wait" cursor
	userFont	=	USERFONT		;font to use for Print
	smallFont	=	4 				;small font for save/restore, etc.
	lastEvent					  	;the last event (used by save/restore game)
	eventMask	=	allEvents	  	;event mask passed to GetEvent in (uEvt new:)
	bigFont	=		USERFONT	  	;large font
	version	=		0			  	;pointer to 'incver' version string
									;	WARNING!  Must be set in room 0
									;	(usually to {x.yyy    } or {x.yyy.zzz})
	autoRobot
	curSaveDir						;address of current save drive/directory string
	numCD	=	0					;number of current CD, 0 for file based
	perspective						;player's viewing angle: degrees away
									;	from vertical along y axis
	features						;locations that may respond to events
	panels	=	NULL				;list of game panels
	useSortedFeatures	=	FALSE	;enable cast & feature sorting?
	unused_6
	overlays	= -1
	doMotionCue						;a motion cue has occurred - process it
	systemPlane						;ID of standard system plane
	saveFileSelText					;text of fileSelector item that's selected.
	unused_8
	unused_2
	[sysLogPath 20]					;-used for system standard logfile path	
	endSysLogPath					;/		(uses 20 globals)
	gameControls					;pointer to instance of game controls
	ftrInitializer					;pointer to code that gets called from
													;	a feature's init
	doVerbCode						;pointer to code that gets invoked if
									;	no feature claims a user event
	approachCode					;pointer to code that translates verbs
									;	into bits
	useObstacles	=	TRUE		;will Ego use PolyPath or not?
	unused_9
	theIconBar						;points to TheIconBar or Null	
	mouseX							;-last known mouse position
	mouseY							;/
	keyDownHandler					;-our EventHandlers, get called by game
	mouseDownHandler				;/
	directionHandler				;/
	speechHandler					;a special handler for speech events
	lastVolume
	pMouse	=	NULL				;pointer to a Pseudo-Mouse, or NULL
	theDoits	=	NULL			;list of objects to get doits each cycle
	eatMice	=	60					;how many ticks before we can mouse
	user	=	NULL				;pointer to specific applications User
	syncBias						;-globals used by sync.sc
	theSync							;/		(will be removed shortly)
	extMouseHandler					;extended mouse handler
	talkers							;list of talkers on screen
	inputFont	=	SYSFONT			;font used for user type-in
	tickOffset						;used to adjust gameTime after restore
	howFast							;measurment of how fast a machine is
	gameTime						;ticks since game start
	narrator						;pointer to narrator (normally Narrator)
	msgType	=	TEXT_MSG			;type of messages used
	messager						;pointer to messager (normally Messager)
	prints							;list of Print's on screen
	walkHandler						;list of objects to get walkEvents
	textSpeed	=	2				;time text remains on screen
	altPolyList						;list of alternate obstacles
	;globals 96-99 are unused
		global96
		global97
		global98
	lastSysGlobal
	;globals > 99 are for game use
	disabledIcons
)

(instance egoObj of GameEgo
	(properties
		view vEgo
	)
)

(instance SCI2 of Game
	(method (init)
		(= systemPlane Plane)
		(super init:)
		(= ego egoObj)
		(user alterEgo: ego)
		(= handsOnCode gameHandsOnCode)
		(= handsOffCode gameHandsOffCode)
		(= approachCode gameApproachCode)
		(= doVerbCode gameDoVerbCode)
		(= messager gameMessager)
		((= narrator Narrator)
			font: userFont
			fore: 7
			back: 0
		)
		((ScriptID GAME_ICONBAR 0) init:)
		((ScriptID GAME_INV 0) init:)
		(self newRoom: TESTROOM)
	)

	(method (startRoom roomNum)	
		(super startRoom: roomNum)
		(if
			(and
				(ego cycler?)
				(not (ego looper?))
				((ego cycler?) isKindOf: StopWalk)
			)
			(ego setLooper: stopGroop)
		)
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


(instance gameHandsOnCode of Code
	(method (doit)
		(user canControl: TRUE canInput: TRUE)
		(theIconBar enable:
			ICON_WALK
			ICON_LOOK
			ICON_DO
			ICON_TALK
			ICON_CURITEM
			ICON_INVENTORY
			ICON_CONTROL
			ICON_HELP
		)
		(if (not (theIconBar curInvIcon?))
			(theIconBar disable: ICON_CURITEM)
		)
	)
)

(instance gameHandsOffCode of Code
	(method (doit)
		(user canControl: FALSE canInput: FALSE)
		(= disabledIcons NULL)
		(theIconBar disable:
			ICON_WALK
			ICON_LOOK
			ICON_DO
			ICON_TALK
			ICON_CUSTOM
			ICON_CURITEM
			ICON_INVENTORY
		)
		(theIconBar eachElementDo: #perform checkIcon)
	)
)

(instance gameMessager of Messager
	(method (findTalker who &tmp theTalker)
		(if
			(= theTalker
				(switch who
					(else  narrator)
				)
			)
			(return)
		else
			(super findTalker: who)
		)
	)
)


(instance checkIcon of Code
	(method (doit theIcon)
		(if
			(and
				(theIcon isKindOf: IconItem)
				(& (theIcon signal?) DISABLED)
			)
			(|= disabledIcons (>> $8000 (theIconBar indexOf: theIcon)))
		)
	)
)

(instance stopGroop of GradualLooper)
