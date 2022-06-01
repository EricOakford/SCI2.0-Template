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
(use GameEgo)
(use Print)
(use Dialog)
(use Talker)
(use String)
(use Array)
(use Messager)
(use Polygon)
(use PolyPath)
(use StopWalk)
(use IconBar)
(use GameRoom)
(use Plane)
(use Flags)
(use Grooper)
(use Procs)
(use Sound)
(use User)
(use Game)
(use System)

(public
	SYTYWH 0
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
	
	;these globals are retained at restart
	statusLineCode			;pointer for status line code
	soundFx					;sound effect being played
	theMusic				;music object, current playing music
	theMusic2				;secondary music
	globalSound				;ambient sound
	gameFlags				;pointer for Flags object, which only requires one global
	scoreFlags
	disabledIcons
	oldCurIcon
	spellCursor
	;end globals to retain at restart
	
	myTextColor				;color of text in message boxes
	myBackColor				;color of message boxes
	myHighlightColor		;color of icon highlight
	myLowlightColor			;color of icon lowlight
	debugging				;debug mode enabled
	scoreFont				;font for displaying the score in the control panel
	numDACs					;Number of voices supported by digital audio driver
	numVoices				;Number of voices supported by sound driver
	deathReason				;message to display when calling EgoDead
	spellCost		=		2	;spCostOpen
	spCostDetect	=		2
	spCostDazzle	=		3
	spCostZap		=		3
	spCostFlame		=		5
	spCostLove		=		10
	spCostHeal		=		10
	spCostLepGold	=		10
	contentLevel
	egoGait
	disabledActions
	heroType
	oldSysTime
	Clock
	Day
	Night
	timeToEat
	hungerCounter
	timeODay
	lostSleep
	lastRestDay
	lastRestTime
	timeEntered	;what time when you entered a room? (updated on every room change)
	freeMeals	;As the game progresses through the day,
				; this gets reduced first. If this is 0, then you eat a ration.
	theBuyDialog
	wareList
	projObj
	;TODO: Replace these with IntArray instances
	[egoStats NUM_ATTRIBS]
	[skillTicks NUM_ATTRIBS]
	[oldStats NUM_ATTRIBS]
	;recovery time for stamina, health, and mana
	stamCounter =	STAM_RATE
	healCounter =	HEAL_RATE
	manaCounter	=	MANA_RATE
	userName
	globalTeller
	bellaState	=		bellaInitial
	cleoState	=		cleoInitial
	meepState	=		meepInitial
	;combat globals
	monsterNum
	monsterHealth
	zapPower
	monsterDazzle
	numGoblins
	daggerRoom
	missedDaggers
	hitDaggers
	egoX
	egoY
	monsterDistX
	monsterDistY
	battleResult
	
	;money globals
	bucks
	silversInBank	
)

(instance egoObj of GameEgo
	(properties
		name {ego}
		view vEgo
	)
)

(instance SYTYWH of Game
	; The main game instance. It adds game-specific functionality.	
	(properties
		printLang ENGLISH	;set your game's language here. Supported languages can be found in SYSTEM.SH.
	)

	(method (init)
		(= systemPlane Plane)
		(super init:)

		;Assign globals to this script's objects
		((= theMusic longSong)
			owner: self
			init:
		)
		((= theMusic2 longSong2)
			owner: self
			init:
		)
		((= globalSound theGlobalSound)
			owner: self
			init:
		)
		((= soundFx soundEffects)
			owner: self
			init:
		)
		(pointsSound
			owner: self
			init:
			setPri: 15
			setLoop: 1
		)
		(= messager gameMessager)
		(= doVerbCode gameDoVerbCode)
		(= approachCode gameApproachCode)
		(= handsOffCode gameHandsOff)
		(= handsOnCode gameHandsOn)
		((= gameFlags gameEventFlags)
			init:
		)
		((= scoreFlags gameScoreFlags)
			init:
		)
;;;		((= egoStats egoStatArray)
;;;			init:
;;;		)
;;;		((= skillTicks skillTickArray)
;;;			init:
;;;		)
;;;		((= oldStats oldStatArray)
;;;			init:
;;;		)
		(= userName (String new: 40))
		((= altPolyList (List new:)) name: {altPolys} add:)

		;load up the ego, icon bar, inventory, control panel, and status line
		(= ego egoObj)
		(user alterEgo: ego canControl: FALSE canInput: FALSE)
		((ScriptID GAME_ICONBAR 0) init:)
		((ScriptID GAME_INV 0) init:)
		((ScriptID GAME_CONTROLS 0) init:)
		((ScriptID STATUS_LINE 0) init:)
		(= statusLineCode (ScriptID STATUS_LINE 1))
		
		;go to the restart room
		(self newRoom: GAME_RESTART)
	)

	(method (startRoom roomNum)
		(if debugging
			((ScriptID DEBUG 0) init:)
		)
		(if
			;all rooms where you can encounter a monster
			(OneOf roomNum
				111 112 117 118 119 123 124 125 126 127 133
				134 135 136 142 143 144 151 152 156 157 161
				162 163 169 171 172 174 175 179 180 181 185
				186 192
			)
			(ScriptID rgENCOUNTER)
		)
		(statusLineCode doit: roomNum)
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
	
	(method (save)
		(if (Btst fSaveAllowed)
			(super save:)
		else
			(messager say: N_CANT_SAVE NULL NULL 0 0 MAIN)
		)
	)
	
	(method (doit &tmp thisTime)
		(if
			;run the regular cycle stuff
			(and
				(not (Btst fTimeStopped))
				(curRoom isKindOf: HeroRoom)
				(curRoom timePasses?)
				(!= oldSysTime (= thisTime (GetTime SysTime1)))
			)
			(= oldSysTime thisTime)
			(= thisTime Clock)
			;if it's passed 3600, change to the next day.
			(if (>= (++ Clock) GAMEDAY)
				(= Clock 0)
				(NextDay)
			)
			;if we advance to a new hour, fix the time
			(if (< (mod Clock GAMEHOUR) (mod thisTime GAMEHOUR))
				(FixTime)
			)
			;if ego's sneaking, use the skill a bit
			(if (== egoGait MOVE_SNEAK)
				(SkillUsed STEALTH 1)
			)
			;see if it's time to eat
			(cond 
				(
					(and
						(or
							(and (< 1100 Clock) (< Clock 1200))
							(and (< 2500 Clock) (< Clock 2600))
						)
						(not timeToEat)
					)
					(if (> Clock 2500)
						(= timeToEat 2650)
					else
						(= timeToEat 1250)
					)
					(ego eatMeal:)
				)
				((> Clock timeToEat)
					(= timeToEat FALSE)
				)
			)
			(if (<= (-= hungerCounter 1) 0)
				(= hungerCounter 100)
				(if (Btst fStarving)
					(ego eatMeal:)
				)
			)
			;every 20 game seconds, ego gets refreshed in Stamina.
			(if (not (-- stamCounter))
				(= stamCounter STAM_RATE)
				(cond 
					;if the hero's starving, or has gone more than 1 day without sleep, reduce SP by 5
					((or (Btst fStarving) (> lostSleep 1))
						(ego useStamina: 5)
					)
					;if the Hero's running, then reduce SP by 2
					((== egoGait MOVE_RUN)
						(ego useStamina: 2)
					)
					;if the hero's not getting hungry, and hasn't gone a day without sleep, then regain by 1
					((and (not (Btst fHungry)) (not lostSleep))
						(ego useStamina: -1)
					)
				)
				;mana gets refreshed once every 5 stamina refreshes
				(if (not (-- manaCounter))
					(= manaCounter MANA_RATE)
					(ego useMana: -1)
				)
				;health gets refreshed once every 15 stamina refreshes
				(if (not (-- healCounter))
					(= healCounter HEAL_RATE)
					(ego takeDamage: -1)
				)
			)
		)	;end of timekeeping code
		(super doit:)
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
								(event claimed: TRUE)
							)
						)
						(`^q
							(theGame quitGame:)
							(event claimed: TRUE)
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
						(`#6
							(if (not (& ((theIconBar at: ICON_CONTROL) signal?) DISABLED))
								(theGame restore:)
								(event claimed: TRUE)
							)
						)
					)
				)
			)
		)
	)
	
	(method (showControls &tmp oldCur)
		(theIconBar hide:)
		(gameControls showSelf:)
	)
	
	(method (solvePuzzle pValue pFlag)
		;Adds an amount to the player's current score.
		;It checks if a certain flag is set so that the points are awarded only once.
		(if (and (> argc 1) (scoreFlags test: pFlag))
			(return)
		)
		(if pValue
			(+= score pValue)
			(statusLineCode doit: curRoomNum)
			(pointsSound play:)
			(if (and (> argc 1) pFlag)
				(scoreFlags set: pFlag)
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
			(if (YesOrNo N_RESTART)
				(curRoom newRoom: GAME_RESTART)
			)
		)
	)

	(method (quitGame)
		;if a parameter is given, skip the dialog and quit immediately		
		(if argc
			(super quitGame:)
		else
			;the game's quit dialog
			(if (YesOrNo N_QUITGAME)
				(super quitGame:)
			)
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

(instance gameMessager of Messager
	(method (findTalker who &tmp theTalker)
		(if
			(= theTalker
				(switch who
					;Add the talkers here, using the defines you set in the message editor
					;from TALKERS.SH
;;;					(BAGI		(ScriptID rStarbucks 1))
;;;					(BANKER		(ScriptID tlkBanker	0))
;;;					(BARNARD	(ScriptID tlkBarnard 0))
;;;					(BARTENDER	(ScriptID tlkBartender 0))
;;;					(BELLA		(ScriptID tlkBella 0))
;;;					(BIG_JIM	(ScriptID 135 1))
;;;					(BIG_JURG	(ScriptID tlkJurgBros 0))
;;;					(BONEHEAD	(ScriptID tlkBonehead 0))
;;;					(BUTCH		(ScriptID tlkButch 0))
;;;					(CASSIDY	(ScriptID rButcherShop 1))
;;;					(CAVEMAN	(ScriptID rCavemen 1))
;;;					(CLETUS		(ScriptID rConfedGate 1))
;;;					(GARGOYLE	(ScriptID tlkGargoyle 0))
;;;					(GHOST		(ScriptID rChurchInside 2))
;;;					(HEALER		(ScriptID rHealerInside 1))
;;;					(HILDE		(ScriptID tlkHilde 0))
;;;					(HIPPIE		(ScriptID rEranasPeace 1))
;;;					(KASPAR		(ScriptID tlkKaspar 0))
;;;					(LAWYER		narrator) ;(ScriptID rOgreArea 1))
;;;					(LIL_JURG	(ScriptID tlkJurgBros	1))
;;;					(MEEP		(ScriptID tlkMeep 0))
;;;					(MERV		(ScriptID rTownOutside 1))
;;;					(MOBSTER	(ScriptID tlkMobster 0))
;;;					(MONK		(ScriptID rChurchInside 1))
;;;					(RICHARD	(ScriptID tlkRichard 0))
;;;					(WITCH_MAMA	(ScriptID rWitchHouse 1))
;;;					(WOLFGANG	(ScriptID rGuildHall	1))
					(else  narrator)
				)
			)
			(return)
		else
			(super findTalker: who)
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
	(method (doit)
		(if (not oldCurIcon)
			(= oldCurIcon (theIconBar curIcon?))
		)
		(user canControl: FALSE canInput: FALSE)
		(ego setMotion: 0)
		(= disabledIcons NULL)
		(theIconBar
			eachElementDo: #perform checkIcon
			curIcon: (theIconBar at: ICON_CONTROL)
			disable:
				ICON_WALK
				ICON_LOOK
				ICON_DO
				ICON_TALK
				ICON_ACTIONS
				ICON_CAST
				ICON_USEIT
				ICON_INVENTORY
		)
		(theGame setCursor: waitCursor)
	)
)

(instance gameHandsOn of Code
	(method (doit)
		(user canControl: TRUE canInput: TRUE)
		(theIconBar enable:
			ICON_WALK
			ICON_LOOK
			ICON_DO
			ICON_TALK
			ICON_ACTIONS
			ICON_CAST
			ICON_USEIT
			ICON_INVENTORY
		)
		(if (not (curRoom inset:))
			(theIconBar enable: ICON_CONTROL)
		)
		(if (not (theIconBar curInvIcon?))
			(theIconBar disable: ICON_USEIT)
		)
		(if (not [egoStats MAGIC])
			(theIconBar disable: ICON_CAST)
		)
		(if oldCurIcon
			(theIconBar curIcon: oldCurIcon)
			(theGame setCursor: (oldCurIcon getCursor:))
			(if
				(and
					(== (theIconBar curIcon?) (theIconBar at: ICON_USEIT))
					(not (theIconBar curInvIcon?))
				)
				(theIconBar advanceCurIcon:)
			)
		)
		(= oldCurIcon 0)
		(theGame setCursor: ((theIconBar curIcon?) getCursor:) TRUE)
	)
)

(instance gameEventFlags of Flags
	(properties
		size NUMFLAGS
	)
)

(instance gameScoreFlags of Flags
	(properties
		size NUMFLAGS
	)
)


(instance theGlobalSound of Sound
	(properties
		flags mNOPAUSE
	)
)

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

(instance soundEffects of Sound
	(properties
		flags (| mNOPAUSE mLOAD_AUDIO)
	)
)
(instance pointsSound of Sound
	(properties
		number sPoints
		flags mNOPAUSE
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

(instance egoStatArray of IntArray)

(instance skillTickArray of IntArray)

(instance oldStatArray of IntArray)

(instance stopGroop of GradualLooper)
