;;; Sierra Script 1.0 - (do not remove this comment)
(script# rTownOutside)
(include game.sh) (include "165.shm")
(use Main)
(use Game)
(use Talker)
(use Feature)
(use HandsOffScript)
;(use Ware)
(use Print)
(use Procs)
(use Teller)
(use Motion)
(use Polygon)
(use GameRoom)
(use Actor)
(use System)

(public
	rm165 0
	mervTalker 1
)

(local
	cuedWhat
)

(instance rm165 of HeroRoom
	(properties
		picture scriptNumber
		noun N_ROOM
		horizon 50
		north rHealerOutside
		east 166
		south 174
		west rTownEntryway
	)
	
	(method (init)
		(super init:)
		(self addObstacle: (&getpoly {SouthEast}))
		(switch prevRoomNum
			(south
				(ego posn: 160 170)
			)
			(north
				(ego posn: 220 83)
			)
			(east
				(ego posn: 265 140)
			)
			(west
				(ego posn: 75 103)
			)
			(else 
				(ego posn: 265 140)
			)
		)
		(ego
			init:
			normalize:
		)
		(if Night
			(self addObstacle: (&getpoly {WallNight}))
			(gate init:)
		else
			(self addObstacle: (&getpoly {WallDay}))
			(merv
				init:
				;approachVerbs: V_LOOK V_TALK V_DO
			)
		)
		(theSign
			init:
			setPolygon: (&getpoly {Sign})
		)
		(wagon
			init:
			setPolygon: (&getpoly {Wagon})
		)
		(wall
			init:
			setPolygon: (&getpoly {Wall})
		)
	)
	
	(method (doit)
		(super doit:)
		(cond 
			((ego script?))
			((curRoom script?))
			((and (< (ego x?) 60) (> (ego y?) 90))
				(if (not (cast contains: gate))
					(curRoom newRoom: rTownEntryway)
				)
			)
		)
	)
)

(instance gate of View
	(properties
		x 20
		y 114
		noun N_GATE
		view scriptNumber
		priority 4
		signal ignrAct
	)
)

(instance theSign of Feature
	(properties
		x 280
		y 140
		noun N_SIGN
	)
)

(instance wagon of Feature
	(properties
		x 233
		y 150
		noun N_WAGON
	)
)

(instance wall of Feature
	(properties
		x 97
		y 50
		noun N_WALL
	)
)

(enum 1
	buyToy
	buyPills
	buyRations
)

(instance egoTeller of Teller)

(instance mervTeller of Teller)

(instance merv of Actor
	(properties
		x 126
		y 95
		view vMerv
		noun N_MERV
		approachX 125
		approachY 106
	)
	(verbs
		(V_SWORD
			(cond
				((Btst fRobbedMerv)
					(messager say: N_MERV V_SWORD C_ALREADY_ROBBED)
				)
				((and (Btst fBoughtMeepToy) (Btst fBoughtSleepingPills))
					(messager say: N_MERV V_SWORD)
					(= cuedWhat cueRobbery)
					(ego setScript: cueIt)
				)
				(else
					;don't want an unwinnable state
					(messager say: N_MERV V_SWORD C_CANT_ROB)
				)
			)
		)
		(V_TALK
			(SolvePuzzle 2 f165TalkToMerv)
			(cond
				((Btst fRobbedMerv)
					(messager say: N_MERV V_TALK C_ALREADY_ROBBED)
				)
				((not (Btst fMetMerv))
					(Bset fMetMerv)
					(messager say: N_MERV V_TALK C_HELLO_FIRST)
					(InitTellers)
				)
				((Btst fMetMerv)
					(messager say: N_TELL V_HERO_TELL C_HELLO_AGAIN)
					(InitTellers)
				)
				(else
					(super doVerb: theVerb)
				)
			)
			(return TRUE)
		)
		(V_LEP_GOLD
			(if (ego castSpell: LEPGOLD)
				(Bset fLeprechaunGold)
				(= cuedWhat cueLeprechaunGold)
				(messager say: N_MERV V_LEP_GOLD)
				(ego setScript: cueIt)
			)
			(return TRUE)
		)
		(V_MONEY
			(if (Btst fRobbedMerv)
				(messager say: N_MERV V_MONEY C_ALREADY_ROBBED)
			else
				(Prints {Ware class to be reimplemented})
;;;				((= wareList (List new:))
;;;					add: meepToy sleepingPills rations
;;;				)
;;;				(switch ((ScriptID WARE 0) doit:)
;;;					(notEnough
;;;						(messager say: N_MERV V_MONEY C_NOT_ENOUGH)
;;;					)
;;;					(buyNothing
;;;						(messager say: N_MERV V_MONEY C_BUY_NOTHING)
;;;					)
;;;					(buyToy
;;;						(if (Btst fBoughtMeepToy)
;;;							(messager say: N_MERV V_MONEY C_ALREADY_BOUGHT_TOY)
;;;							(= cuedWhat cueCantBuyToy)
;;;						else
;;;							(messager say: N_MERV V_MONEY C_BUY_TOY)
;;;							(= cuedWhat cueBuyToy)
;;;						)
;;;					)
;;;					(buyPills
;;;						(if (Btst fBoughtSleepingPills)
;;;							(messager say: N_MERV V_MONEY C_ALREADY_BOUGHT_PILLS)
;;;							(= cuedWhat cueCantBuyPills)
;;;						else
;;;							(messager say: N_MERV V_MONEY C_BUY_PILLS)
;;;							(= cuedWhat cueBuyPills)
;;;						)
;;;					)
;;;					(buyRations
;;;						(messager say: N_MERV V_MONEY C_BUY_RATIONS)
;;;						(= cuedWhat cueBuyRations)
;;;					)
;;;				)
;;;				(if cuedWhat
;;;					(ego setScript: cueIt)
;;;				)
				(return TRUE)
			)
		)
	)
)

(procedure (InitTellers)
	(egoTeller init: ego scriptNumber N_TELL V_HERO_TELL N_ASK)
	(mervTeller init: merv scriptNumber N_TELL V_MERV_TELL N_ASK)
)

(enum 1
	cueRobbery
	cueBuyPills
	cueBuyToy
	cueBuyRations
	cueCantBuyPills
	cueCantBuyToy
	cueLeprechaunGold
)

(instance cueIt of Script
	(method (changeState newState)
		(switch (= state newState)
			(0
				(= ticks 20)
			)
			(1
				(switch cuedWhat
					(0	;didn't set it
						(self cue:)
					)
					(cueRobbery
						(ego
							get: iMoney 70
							addHonor: -100
						)
						(Bset fRobbedMerv)
						(SolvePuzzle -5 f165RobMerv)
					)
					(cueBuyPills
						(ego get: iSleepingPills)
						(Bset fBoughtSleepingPills)
						(SolvePuzzle 2 f165BuySomething)
					)
					(cueBuyToy
						(ego get: iMeepToy)
						(Bset fBoughtMeepToy)
						(SolvePuzzle 2 f165BuySomething)
					)
					(cueBuyRations
						(ego get: iFood 1)
					)
					(cueCantBuyPills
						(if (not (Btst fLeprechaunGold))
							(ego get: iMoney 50)
						)
					)
					(cueCantBuyToy
						(if (not (Btst fLeprechaunGold))
							(ego get: iMoney 10)
						)
					)
					(cueLeprechaunGold
						(mervTeller doVerb: V_MONEY)
					)
				)
				(self cue:)
			)
			(2
				(= cuedWhat 0)
				(self dispose:)
			)
		)
	)
)

;;;(instance meepToy of Ware
;;;	(properties
;;;		noun N_MEEP_TOY
;;;		price {10}
;;;	)
;;;)
;;;
;;;(instance sleepingPills of Ware
;;;	(properties
;;;		noun N_SLEEPING_PILLS
;;;		price {50}
;;;	)
;;;)
;;;
;;;(instance rations of Ware
;;;	(properties
;;;		noun N_RATIONS
;;;		price {5}
;;;	)
;;;)

(instance mervTalker of Talker
	(properties
		x 10
		y 10
		view tlkMerv
		talkWidth 260
		textX 15
		textY 110
	)
	
	(method (init)
		(= font userFont)
		(= fore myTextColor)
		(= back myBackColor)
		(super init: mervBust mervEyes mervMouth &rest)
	)
)


(instance mervBust of Prop
	(properties
		view tlkMerv
	)
)

(instance mervMouth of Prop
	(properties
		nsTop 34
		nsLeft 18
		view tlkMerv
		loop 1
	)
)

(instance mervEyes of Prop
	(properties
		nsTop 22
		nsLeft 17
		view tlkMerv
		loop 2
	)
)