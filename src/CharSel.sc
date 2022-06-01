;;; Sierra Script 1.0 - (do not remove this comment)
(script# CHARSEL)
(include game.sh) (include "21.shm")
(use Main)
(use Game)
(use Print)
(use Procs)
(use Actor)
(use Motion)
(use System)

(local
	local0
	local1
	local2
	local3
	titleX = [18 202 201 18 212 201]
	titleY = [146 156 146 153 160 153]
	namePrompt
)

(public
	selChar 0
)

(procedure (SelectedCharacter charType &tmp evt)
	(if (fightChar cel?)
		(fightChar setCycle: BegLoop fightChar)
	)
	(if (mageChar cel?)
		(mageArm cel: 5)
		(mageChar setCycle: BegLoop mageChar)
	)
	(if (thiefChar cel?)
		(thiefChar setCycle: BegLoop thiefChar)
	)
	(theTitle
		setLoop: 1
		cel: charType
		x: [titleX charType]
		y: [titleY charType]
	)
	(if
		(or
			(theTitle onMe: (= evt (Event new:)))
			(not local2)
		)
		(theGame setCursor: normalCursor TRUE
			(switch charType
				(FIGHTER 50)
				(MAGIC_USER 140)
				(else  235)
			)
			150
		)
	)
	(evt dispose:)
)

(instance selChar of Room
	(properties
		picture scriptNumber
	)
	
	(method (init)
		(super init:)
		(theGame handsOff:)
		(theIconBar disable:)
		(mouseDownHandler
			add: theTitle fightChar mageChar mageArm thiefChar self
		)
		(keyDownHandler
			add: self theTitle fightChar mageChar mageArm thiefChar
		)
		(directionHandler add: self)
		(fightChar init:)
		(mageChar init:)
		(thiefChar init:)
		(theTitle
			loop: 3
			init:
		)
		(roundRobin start: 0)
		(self setScript: roundRobin)
	)
	
	(method (handleEvent event)
		(if (or (event claimed?) local3)
			(event claimed: TRUE)
			(return)
		)
		(if (== (event type?) keyDown)
			(switch (event message?)
				(TAB
					(event type: direction)
					(event message: dirE)
				)
				(SHIFTTAB
					(event type: direction)
					(event message: dirW)
				)
			)
		)
		(if (& (event type?) direction)
			(switch (event message?)
				(dirW
					(event claimed: TRUE)
					(script state:
						(switch (- (theTitle cel?) 1)
							(0 1)
							(1 3)
							(else  6)
						)
						cue:
					)
				)
				(dirE
					(event claimed: TRUE)
					(script
						state:
						(switch (+ (theTitle cel?) 1)
							(1 3)
							(2 6)
							(else  1)
						)
						cue:
					)
				)
			)
		else
			(super handleEvent: event)
		)
	)
	
	(method (doVerb)
		(return FALSE)
	)
)

(instance fightChar of Prop
	(properties
		x 68
		y 129
		view 526
		signal skipCheck
	)
	
	(method (handleEvent event)
		(if
			(and
				(or
					(and
						(== (event type?) mouseDown)
						(self onMe: (event x?) (event y?))
					)
					(and
						(== (event type?) keyDown)
						(OneOf (event message?) ESC ENTER)
					)
				)
				(not local3)
				(!= (roundRobin state?) 2)
			)
			(event claimed: TRUE)
			(if (== (roundRobin register?) 1)
				(theTitle cue:)
			else
				(roundRobin changeState: 2)
			)
		else
			(super handleEvent: event)
		)
	)
	
	(method (doVerb)
		(return FALSE)
	)
)

(instance mageChar of Prop
	(properties
		x 158
		y 129
		view 527
		signal skipCheck
	)
	
	(method (handleEvent event &tmp roundRobinState)
		(= roundRobinState (roundRobin state?))
		(if
			(and
				(or
					(and
						(== (event type?) mouseDown)
						(self onMe: (event x?) (event y?))
					)
					(and
						(== (event type?) keyDown)
						(OneOf (event message?) ESC ENTER)
					)
				)
				(not local3)
				(!= roundRobinState 4)
				(!= roundRobinState 5)
			)
			(event claimed: TRUE)
			(if (== (roundRobin register?) 2)
				(theTitle cue:)
			else
				(roundRobin changeState: 4)
			)
		else
			(super handleEvent: event)
		)
	)
	
	(method (doVerb)
		(return FALSE)
	)
)

(instance mageArm of Prop
	(properties
		x 162
		y 73
		view 527
		loop 1
		priority 15
		signal skipCheck
	)
	
	(method (doVerb)
		(return 0)
	)
)

(instance thiefChar of Prop
	(properties
		x 248
		y 130
		view 528
		signal skipCheck
	)
	
	(method (handleEvent event &tmp roundRobinState)
		(= roundRobinState (roundRobin state?))
		(if
			(and
				(or
					(and
						(== (event type?) mouseDown)
						(self onMe: (event x?) (event y?))
					)
					(and
						(== (event type?) keyDown)
						(OneOf (event message?) ESC ENTER)
					)
				)
				(not local3)
				(!= roundRobinState 7)
			)
			(event claimed: 1)
			(if (== (roundRobin register?) 3)
				(theTitle cue:)
			else
				(roundRobin changeState: 7)
			)
		else
			(super handleEvent: event)
		)
	)
	
	(method (doVerb)
		(return FALSE)
	)
)

(instance theTitle of Prop
	(properties
		x 10
		y 138
		view 506
		loop 3
	)
	
	(method (handleEvent event)
		(if
			(and
				local2
				(or
					(and
						(== (event type?) mouseDown)
						(self onMe: (event x?) (event y?))
					)
					(and
						(== (event type?) keyDown)
						(OneOf (event message?) ESC ENTER)
					)
				)
			)
			(event claimed: TRUE)
			(self cue:)
		else
			(super handleEvent: event)
		)
	)
	
	(method (doVerb)
		(return FALSE)
	)
	
	(method (cue)
		(super cue:)
		(= heroType cel)
		(= local3 1)
		(roundRobin changeState: 9 cue:)
		(self
			setLoop: 2
			x: [titleX (+ cel 3)]
			y: [titleY (+ cel 3)]
		)
	)
)

(instance roundRobin of Script
	(method (doit)
		(cond
			((and local0 (== (fightChar cel?) 2))
				(= local0 0)
			)
			((and local1 (== (thiefChar cel?) 2))
				(= local1 0)
			)
		)
		(super doit: &rest)
	)
	
	(method (changeState newState)
		(switch (= state newState)
			(0
				(theGame setCursor: theCursor FALSE)
				(= seconds 6)
			)
			(1
				(= register 1)
				(theTitle loop: 0)
				(= seconds 3)
			)
			(2
				(= register 1)
				(= seconds 0)
				(SelectedCharacter FIGHTER)
				(= local2 1)
				(= local0 1)
				(fightChar setCycle: EndLoop self)
			)
			(3
				(= register 1)
				(= seconds 3)
			)
			(4
				(= register 2)
				(= seconds 0)
				(SelectedCharacter MAGIC_USER)
				(mageChar setCycle: EndLoop self)
			)
			(5
				(= register 2)
				(mageArm init: setCycle: EndLoop self)
			)
			(6
				(= register 2)
				(= seconds 3)
			)
			(7
				(= seconds 0)
				(= register 3)
				(SelectedCharacter THIEF)
				(= local1 1)
				(thiefChar setCycle: EndLoop self)
			)
			(8
				(= seconds (= register 3))
			)
			(9
				(self cue:)
			)
			(10
				(if
					(or
						(and (== heroType FIGHTER) (fightChar cycler?))
						(and (== heroType MAGIC_USER) (mageChar cycler?))
						(and (== heroType THIEF) (thiefChar cycler?))
					)
					0
				else
					(self cue:)
				)
			)
			(11
				(theGame setCursor: theCursor FALSE)
				(= ticks 5)
			)
			(12
				(curRoom drawPic: (curRoom picture?) SHOW_PIXEL_DISSOLVE)
				(= seconds 5)
			)
			(13
				;set the base stats for everyone
				(= [egoStats OPEN] 0)
				(= [egoStats DAZZLE] 0)
				(= [egoStats FLAMEDART] 0)
				(= [egoStats HEAL] 0)
				(= [egoStats LOVE] 0)
				(= [egoStats LEPGOLD] 0)
				
				;set up specific hero type's stats.
				;These are based on QFG5's initial stats.
				(switch heroType
					(FIGHTER
						(= [egoStats STR]		350)
						(= [egoStats INT]		300)
						(= [egoStats AGIL]		320)
						(= [egoStats VIT]		320)
						(= [egoStats LUCK]		300)
						
						(= [egoStats WEAPON]	350)
						(= [egoStats PARRY]		350)
						(= [egoStats DODGE]		350)
						(= [egoStats STEALTH]	0)
						(= [egoStats PICK]		0)
						(= [egoStats THROW]		300)
						(= [egoStats CLIMB]		300)
						(= [egoStats MAGIC]		0)
						(= [egoStats HONOR]		300)
						(ego get: iSword)
						(ego get: iShield)
					)
					(MAGIC_USER
						(= [egoStats STR]		250)
						(= [egoStats INT]		350)
						(= [egoStats AGIL]		300)
						(= [egoStats VIT]		300)
						(= [egoStats LUCK]		300)
						
						(= [egoStats WEAPON]	300)
						(= [egoStats PARRY]		0)
						(= [egoStats DODGE]		300)
						(= [egoStats STEALTH]	0)
						(= [egoStats PICK]		0)
						(= [egoStats THROW]		0)
						(= [egoStats CLIMB]		250)	;will be 0 when Levitate is put in the game
						(= [egoStats MAGIC]		350)
						(= [egoStats HONOR]		300)
		
						(= [egoStats OPEN]		10)
						(= [egoStats FLAMEDART]	10)
						(ego get: iDagger)
					)
					(THIEF
						(= [egoStats STR]		250)
						(= [egoStats INT]		300)
						(= [egoStats AGIL]		350)
						(= [egoStats VIT]		250)
						(= [egoStats LUCK]		300)
						
						(= [egoStats WEAPON]	300)
						(= [egoStats PARRY]		0)
						(= [egoStats DODGE]		320)
						(= [egoStats STEALTH]	300)
						(= [egoStats PICK]		300)
						(= [egoStats THROW]		300)
						(= [egoStats CLIMB]		350)
						(= [egoStats MAGIC]		0)
						(= [egoStats HONOR]		250)
		
						(ego get: iDagger)
						(ego get: iLockPick)
					)
				)
				(= [egoStats HEALTH] (ego maxHealth:))
				(= [egoStats STAMINA] (ego maxStamina:))
				(= [egoStats MANA] (ego maxMana:))
				((= namePrompt (Print new:))
					addText: N_NAME NULL NULL 1 0 0 scriptNumber
					addEdit: userName 25 0 10 userName
					init:
				)
				;release the event handlers!
				(mouseDownHandler release:)
				(keyDownHandler release:)
				(directionHandler release:)
				(theIconBar enable:)
				(curRoom newRoom: 168)
			)
		)
	)
)
