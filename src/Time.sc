;;; Sierra Script 1.0 - (do not remove this comment)
(script# TIME)
(include game.sh) (include "17.shm") (include "22.shm")
(use Main)
(use GameRoom)
(use Procs)
(use Motion)
(use String)
(use Print)
(use System)

(public
	egoSleeps 0
	egoRests 1
	SleepChoice 2
	showTime 3
	fixTime 4
	advanceTime 5
)

(local
	isDaytime
	awakenHour
)

(enum 1
	rest10Minutes
	rest30Minutes
	rest60Minutes
	goToSleep
	stayAwake
)


(instance egoSleeps of Code
	(method (init theHour theMin &tmp sleptHours oldTime awakenCase)
		(cond
			((Btst fStarving)	;can't sleep with an empty stomach
				(messager say: N_SLEEP NULL C_STARVING 0 0 TIME)
			)
			;room where ego can sleep
			((and (curRoom isKindOf: HeroRoom) (curRoom canSleep?))
				(= lostSleep 0)
				(= oldTime Clock)
				(switch argc
					(0
						;in case someone forgets to specifically set the hour
						(fixTime init: 5)
					)
					(1
						(fixTime init: theHour)
					)
					(else
						(fixTime init: theHour theMin)
					)
				)
				(= sleptHours
					(/ (mod (- (+ Clock GAMEDAY) oldTime) GAMEDAY) GAMEHOUR)
				)
				(= [egoStats STAMINA] (MaxStamina))
				(if (== curRoomNum rEranasPeace)
					(= [egoStats HEALTH] (MaxHealth))
					(= [egoStats MANA] (MaxMana))
				else
					(TakeDamage (- (* sleptHours 2)))
					(UseMana (- (* sleptHours 2)))
				)
				(if (> oldTime Clock)
					(NextDay)
				)
				(messager say: N_SLEEP NULL C_AWAKE_MORNING 0 0 TIME)
			)
			(else	;can't sleep here
				(messager say: N_SLEEP NULL C_CANT_SLEEP 0 0 TIME)
			)
		)
	)
)

(instance egoRests of Code
	(method (init theMin mess)
		(cond
			((and (curRoom isKindOf: HeroRoom) (curRoom timePasses?))
				(= lastRestDay Day)
				(= lastRestTime Clock)
				(ego
					useStamina: (- theMin)
					useMana: (- (/ theMin 5))
					takeDamage: (- (/ (+ theMin 5) 15))
				)
				(if mess
					(messager say: N_SLEEP NULL C_RESTED 0 0 TIME)
				)
				(advanceTime init: 0 theMin)
			)
			(else
				(messager say: N_SLEEP NULL C_CANT_REST 0 0 TIME)
			)
		)
	)
)

(procedure (SleepChoice &tmp evt ret sleepCase)
	(switch
		(Print
			addText: N_SLEEP NULL C_HOW_LONG 1 0 0 TIME
			addButton: rest10Minutes N_SLEEP NULL C_REST_10 1 0 20 TIME
			addButton: rest30Minutes N_SLEEP NULL C_REST_30 1 0 40 TIME
			addButton: rest60Minutes N_SLEEP NULL C_REST_60 1 0 60 TIME
			addButton: goToSleep N_SLEEP NULL C_SLEEP_UNTIL_MORNING 1 0 80 TIME
			addButton: stayAwake N_SLEEP NULL C_STAY_AWAKE 1 0 100 TIME
			init:
		)
		(rest10Minutes
			(egoRests init: 10 TRUE)
		)
		(rest30Minutes
			(egoRests init: 30 TRUE)
		)
		(rest60Minutes
			(egoRests init: 60 TRUE)
		)
		(goToSleep
			(cond
				((not (NeedSleep))
					(messager say: N_SLEEP NULL C_TOO_EARLY 0 0 TIME)
				)
				;add rooms with unique responses 
				(
					(or
						(OneOf curRoomNum rConfedGate)	;rooms with unique responses
						(and (curRoom isKindOf: HeroRoom) (curRoom canSleep?))	;ego can sleep here
					)
					((= evt (Event new:)) type: mouseDown message: V_SLEEP)
					(if (not (mouseDownHandler handleEvent: evt))
						(regions handleEvent: evt)
					)
					(evt dispose:)
					(= ret TRUE)
				)
				(else
					(egoSleeps init: 5 0)
				)
			)
		)
		(stayAwake
			(= ret TRUE)
		)
	)
	(return ret)
)

(procedure (NeedSleep)
	(return (if (>= timeODay TIME_SUNSET) else lostSleep))
)

(instance showTime of Code
	(method (init &tmp whatDay timeBuf dayBuf)
		(= whatDay Day)
		(if (or (!= timeODay TIME_MIDNIGHT) (> Clock 500))
			(++ whatDay)
		)
		(= timeBuf (String new:))
		(= dayBuf (String new:))
		(Message MsgGet PROCS N_TIME_OF_DAY NULL (+ timeODay 1) 1 (timeBuf data?))
		(Message MsgGet PROCS N_TIME_OF_DAY NULL NULL 1 (dayBuf data?))
		(Print
			font: userFont
			addTextF: dayBuf timeBuf whatDay
			init:
		)
	)
)

(instance fixTime of Code
	(method (init theHour theMin &tmp oldTimeODay oldTime)
		(= oldTime Clock)
		(if (>= argc 1)
			(= Clock (* GAMEHOUR theHour))
			(= oldSysTime (GetTime SysTime1))
			(if (>= argc 2)
				(+= Clock (/ (* GAMEHOUR theMin) 60))
			)
		)
		(= oldTimeODay timeODay)
		(cond 
			((< (^ Clock 1) 300)
				(= timeODay TIME_MIDNIGHT)
			)	
			((< (^ Clock 1) 750)
				(= timeODay TIME_NOTYETDAWN)
			)
			((< (^ Clock 1) 1200)
				(= timeODay TIME_DAWN)
				(PalVary PalVaryReverse 30)
				(= Night FALSE)
			)
			((< (^ Clock 1) 1650)
				(= timeODay TIME_MIDMORNING)
			)
			((< (^ Clock 1) 2100)
				(= timeODay TIME_MIDDAY)
			)
			((< (^ Clock 1) 2550)
				(= timeODay TIME_MIDAFTERNOON)
			)
			((< (^ Clock 1) 3000)
				(= timeODay TIME_SUNSET)
			)
			((< (^ Clock 1) 3450)
				(= timeODay TIME_NIGHT)
				(PalVary PalVaryStart (curRoom picture?) 30)
				(= Night TRUE)
			)
			(else
				(= timeODay TIME_MIDNIGHT)
			)
		)
		(if (and (== timeODay TIME_MIDNIGHT) (!= oldTimeODay TIME_MIDNIGHT))
			(if (== (++ lostSleep) 1)
				(messager say: N_TIME NULL C_TIRED 0 0 TIME)
			else
				(messager say: N_TIME NULL C_EXHAUSTED 0 0 TIME)
			)
		)

	)
)

(instance advanceTime of Code
	(method (init addHours addMinutes &tmp newTime)
		(switch argc
			(1
				(= newTime (+ Clock (* GAMEHOUR addHours)))
			)
			(2
				(= newTime
					(+ Clock (* GAMEHOUR addHours) (/ (* GAMEHOUR addMinutes) 60))
				)
			)
		)
		(^= newTime 1)
		(if
			(or
				(and (< Clock 1100) (> newTime 1200))
				(and
					(< Clock 2500)
					(or (> newTime 2600) (< newTime Clock))
				)
			)
			(ego eatMeal:)
		)
		(fixTime
			init: (/ newTime GAMEHOUR) (/ (* (mod newTime GAMEHOUR) 60) GAMEHOUR)
		)
	)
)
