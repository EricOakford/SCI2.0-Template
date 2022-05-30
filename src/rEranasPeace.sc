;;; Sierra Script 1.0 - (do not remove this comment)
(script# rEranasPeace)
(include game.sh) (include "110.shm")
(include "110.shp")
(use Main)
(use LoadMany)
(use Teller)
(use HandsOffScript)
(use Feature)
(use GameRoom)
(use Print)
(use PolyPath)
(use Procs)
(use Array)
(use Talker)
(use Sound)
(use Motion)
(use Game)
(use Actor)
(use System)
(use Print)
(use Polygon)

(public
	rm110 0
	hippieTalker 1
)

(local
	cycleTimer
)

(instance rm110 of Room ;HeroRoom
	(properties
		picture scriptNumber
		style SHOW_FADE_IN
		noun N_ROOM
		south 112
	)
	
	(method (init)
		(if (Btst fHippiesDead)
			;tree has fruit on it now
			(= picture 10)
		)
		;need to overhaul the Teller class for SCI32 to use array instances
;;;		(= [hippieTellTree 0] @hippieTellMainBranch)
;;;		(= [hippieTellTree 1] @hippieTell1)
;;;		(= [hippieTellTree 2] @hippieTell2)
;;;		(= [hippieTellTree 3] @hippieTell3)
;;;		(= [hippieTellTree 4] @hippieTell4)
;;;		(= [hippieTellTree 5] ENDTELL)

		(super init:)
		(self addObstacle: (&getpoly {Room}) (&getpoly {TreeBase}))
		(ego
			init:
			normalize:
			posn: 160 183
		)
		(theGame handsOn:)
		(tree
			init:
			setPolygon: (&getpoly {Tree})
		)
		(meadow
			init:
			setPolygon: (&getpoly {Flowers})
		)
		(theMusic
			number: sEranasPeace
			play:
		)
		(if (not (Btst fHippiesDead))
			(hippie init:)
			(egoTeller
				init: ego scriptNumber N_TELL V_HERO_TELL N_ASK
			)
			(hippieTeller
				init: hippie scriptNumber N_TELL V_HIPPIE_TELL N_ASK
			)
		)
	)

	(method (doit)
		(super doit:)
		(if (not (-- cycleTimer))
			(= cycleTimer 10)
			(PalCycle 232 237 1)
			(PalCycle 238 243 -1)
			(PalCycle 244 250 1)
		)
	)
	
	(verbs
		(V_DAZZLE
			(if (cast contains: hippie)
				(messager say: N_ROOM V_DAZZLE C_HIPPIES)
			else
				(messager say: N_ROOM V_DAZZLE)
			)
		)
	)
)

(instance egoTeller of Teller)

(instance hippie of Actor
	(properties
		view vHippie
		x 110
		y 160
		noun N_HIPPIES
	)
)

(instance hippieTeller of Teller
	(properties
		title 3
	)
)

(instance tree of Feature
	(verbs
		(V_LOOK
			(if (Btst fHippiesDead)
				(messager say: N_TREE V_LOOK C_FRUITFUL)
			else
				(messager say: N_TREE V_LOOK C_BARREN)
			)
		)
		(V_DO
			(if (Btst fHippiesDead)
				(if (> freeMeals 2)
					(messager say: N_TREE V_DO C_SATISFIED)
				else
					(messager say: N_TREE V_DO C_FRUITFUL)
					(= freeMeals 4)
				)
			else
				(messager say: N_TREE V_DO C_BARREN)
			)
		)
	)
)

(instance meadow of Feature
	(properties
		noun N_MEADOW
	)
)


(instance giveDragonsBreath of HandsOffScript
	(method (changeState newState)
		(switch (= state newState)
			(0
				(messager say: N_HIPPIES V_DRAGON_BREATH C_TIEDYE 0 self)
			)
			(1
				(Bset fHippiesDead)
				(hippie
					setLoop: 1
					setCycle: EndLoop
					self
				)
			)
			(2
				(messager say: N_HIPPIES V_DRAGON_BREATH C_HIPPIE_DEATH 0 self)
			)
			(3
				(curRoom drawPic: 10)	;there's now fruit on the tree
				(self dispose:)
			)
		)
	)
)

(instance hippieTalker of Talker
	(properties
		x 10
		y 10
		view tlkHippie
		talkWidth 260
		textX 15
		textY 110
	)
	
	(method (init)
		(= back myBackColor)
		(= font userFont)
		(super init: hippieBust hippieEyes hippieMouth &rest)
	)
)

(instance hippieBust of Prop
	(properties
		view tlkHippie
	)
)
	

(instance hippieEyes of Prop
	(properties
		nsTop 31
		nsLeft 39
		view tlkHippie
		loop 2
	)
)

(instance hippieMouth of Prop
	(properties
		nsTop 47
		nsLeft 42
		view tlkHippie
		loop 1
	)
)
