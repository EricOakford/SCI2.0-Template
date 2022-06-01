;;; Sierra Script 1.0 - (do not remove this comment)
(script# CHARSHEET)
(include game.sh) (include "15.shm")
(use Main)
(use DText)
(use Plane)
(use Procs)
(use String)
(use Array)
(use IconBar)
(use Actor)
(use System)

(define TEXT_COLOR 84)
(define POISONED_COLOR 106)
(define DECREASE_STAT_COLOR 42)
(define INCREASE_STAT_COLOR	46)

(public
	charSheet 0
)

(local
	userNameSize
	theScore
	heroX
	heroY
	newCast
	[local5 4]
	newStr
)
(instance charSheet of Code
	(method (doit)
		;(cast eachElementDo: #perform (ScriptID 90 0) 1)
		(= theScore score)
		(Bset fTimeStopped)
		(= newStr (String new:))
		(charInitScreen init: show: dispose:)
		(newStr dispose:)
		(Bclr fTimeStopped)
	)
)

(class SheetIcon of IconItem
	(properties
		text1 0
		text2 0
		nameBack 0
		nameLow 0
		nameHigh 0
		valueBack 0
		valueLow 0
		valueHigh 0
		case 0
		stat 0
		nsLeft 0
		nsTop 0
		nsRight 0
		nsBottom 0
		signal $0000
		state $0000
		maskView 0
		maskLoop 0
	)
	
	(method (init &tmp temp0)
		(PalVary PalVaryKill)
		(= text1 (String new:))
		(= text2 (String new:))
		(= nameBack (myDText new:))
		(= nameLow (myDText new:))
		(= valueBack (myDText new:))
		(= valueLow (myDText new:))
		(= maskView [egoStats stat])
		(= maskLoop 0)
		(Message MsgGet scriptNumber 1 0 case 1 (text1 data?))
		(nameBack
			posn: (- nsLeft 83) nsTop
			text: (text1 data?)
			font: userFont
			fore: 174
			back: 254
			skip: 254
			setSize:
			setPri: 242
			init: newCast
		)
		(nameLow
			posn: (- nsLeft 84) nsTop
			text: (text1 data?)
			font: userFont
			fore: TEXT_COLOR
			back: 254
			skip: 254
			setSize:
			setPri: 243
			init: newCast
		)
		(cond 
			((== stat -1)
				(text2 format: {%d} theScore)
				(valueBack posn: nsLeft nsTop)
				(valueLow posn: (- nsLeft 1) nsTop)
			)
			((== stat HEALTH)
				(text2 format: {%d/%d} [egoStats HEALTH] (ego maxHealth:))
				(valueBack posn: (- nsLeft 27) nsTop)
				(valueLow posn: (- nsLeft 28) nsTop)
			)
			((== stat 18)
				(text2 format: {%d/%d} [egoStats 18] (ego maxStamina:))
				(valueBack posn: (- nsLeft 27) nsTop)
				(valueLow posn: (- nsLeft 28) nsTop)
			)
			((== stat 19)
				(text2 format: {%d/%d} [egoStats 19] (ego maxMana:))
				(valueBack posn: (- nsLeft 27) nsTop)
				(valueLow posn: (- nsLeft 28) nsTop)
			)
			(else
				(text2 format: {%d} [egoStats stat])
				(valueBack posn: nsLeft nsTop)
				(valueLow posn: (- nsLeft 1) nsTop)
			)
		)
		(valueBack
			text: (text2 data?)
			font: userFont
			fore: 174
			back: 254
			skip: 254
			mode: -1
			setSize:
			setPri: 242
			init: newCast
		)
		(cond 
			((> [egoStats stat] [oldStats stat]) (= temp0 INCREASE_STAT_COLOR))
			((< [egoStats stat] [oldStats stat]) (= temp0 DECREASE_STAT_COLOR))
			(else (= temp0 TEXT_COLOR))
		)
		(valueLow
			text: (text2 data?)
			font: userFont
			fore: temp0
			back: 254
			skip: 254
			mode: -1
			setSize:
			setPri: 243
			init: newCast
		)
	)
	
	(method (dispose)
		(text1 dispose:)
		(text2 dispose:)
		(super dispose:)
	)
	
	(method (show)
		(= state (| state $0020))
		(= nsBottom (+ nsTop 8))
	)
)

(instance strengthIcon of SheetIcon
	(properties
		case 6
		nsLeft 93
		nsTop 34
		nsRight 113
	)
)

(instance intgenIcon of SheetIcon
	(properties
		case 7
		stat 1
		nsLeft 93
		nsTop 45
		nsRight 113
	)
)

(instance agilityIcon of SheetIcon
	(properties
		case 8
		stat 2
		nsLeft 93
		nsTop 56
		nsRight 113
	)
)

(instance vitalIcon of SheetIcon
	(properties
		case 9
		stat 3
		nsLeft 93
		nsTop 67
		nsRight 113
	)
)

(instance luckIcon of SheetIcon
	(properties
		case 10
		stat 4
		nsLeft 93
		nsTop 78
		nsRight 113
	)
)

(instance magicIcon of SheetIcon
	(properties
		case 11
		stat 12
		nsLeft 93
		nsTop 89
		nsRight 113
	)
)

(instance commIcon of SheetIcon
	(properties
		case 13
		stat 13
		nsLeft 93
		nsTop 100
		nsRight 113
	)
)

(instance weaponIcon of SheetIcon
	(properties
		case 14
		stat 5
		nsLeft 300
		nsTop 35
		nsRight 313
	)
)

(instance parryIcon of SheetIcon
	(properties
		case 15
		stat 6
		nsLeft 300
		nsTop 44
		nsRight 313
	)
)

(instance dodgeIcon of SheetIcon
	(properties
		case 16
		stat 7
		nsLeft 300
		nsTop 54
		nsRight 313
	)
)

(instance stealthIcon of SheetIcon
	(properties
		case 17
		stat 8
		nsLeft 300
		nsTop 64
		nsRight 313
	)
)

(instance pickIcon of SheetIcon
	(properties
		case 18
		stat 9
		nsLeft 300
		nsTop 74
		nsRight 313
	)
)

(instance throwIcon of SheetIcon
	(properties
		case 19
		stat 10
		nsLeft 300
		nsTop 84
		nsRight 313
	)
)

(instance climbIcon of SheetIcon
	(properties
		case 20
		stat 11
		nsLeft 300
		nsTop 94
		nsRight 313
	)
)

(instance acrobIcon of SheetIcon
	(properties
		case 21
		stat 15
		nsLeft 300
		nsTop 104
		nsRight 313
	)
)

(instance honorIcon of SheetIcon
	(properties
		case 22
		stat 14
		nsLeft 300
		nsTop 114
		nsRight 313
	)
)

(instance pointIcon of SheetIcon
	(properties
		case 30
		stat -1
		nsLeft 93
		nsTop 150
		nsRight 113
	)
)

(instance healthIcon of SheetIcon
	(properties
		case 29
		stat 17
		nsLeft 93
		nsTop 159
		nsRight 113
	)
)

(instance staminaIcon of SheetIcon
	(properties
		case 25
		stat 18
		nsLeft 300
		nsTop 150
		nsRight 313
	)
)

(instance manaIcon of SheetIcon
	(properties
		case 26
		stat 19
		nsLeft 300
		nsTop 159
		nsRight 313
	)
)

(instance namePlate of SheetIcon
	(properties
		case 28
		nsLeft 81
		nsTop 172
		nsRight 235
	)
	
	(method (init)
		(= text1 (String new:))
		(= nameBack (myDText new:))
		(= nameLow (myDText new:))
		(= valueHigh (myDText new:))
		(= valueLow (myDText new:))
		(Message MsgGet scriptNumber 1 0 case 1 (text1 data?))
		(nameBack
			posn: nsLeft nsTop
			text: (text1 data?)
			font: userFont
			fore: 174
			back: 254
			skip: 254
			setSize:
			setPri: 242
			init: newCast
		)
		(nameLow
			posn: (- nsLeft 1) nsTop
			text: (text1 data?)
			font: userFont
			fore: TEXT_COLOR
			back: 254
			skip: 254
			setSize:
			setPri: 243
			init: newCast
		)
		(valueHigh
			text: (userName data?)
			posn: (+ nsLeft 47) (+ nsTop 2)
			fore: POISONED_COLOR
			mode: 0
			font: smallFont
			setPri: 0
			setSize:
			init: newCast
		)
		(valueLow
			text: (userName data?)
			posn: (+ nsLeft 47) (+ nsTop 2)
			fore: TEXT_COLOR
			mode: 0
			font: smallFont
			setPri: 243
			setSize:
			init: newCast
		)
	)
	
	(method (dispose)
		(text1 dispose:)
		(DisposeClone self)
	)
	
	(method (show)
		(= nsBottom (+ nsTop 8))
	)
)

(instance charInitScreen of IconBar
	(properties
		state $0000
	)
	
	(method (init &tmp temp0 temp1 temp2)
		(= newCast (Cast new:))
		(= plane (Plane new:))
		(plane
			priority: (+ (GetHighPlanePri) 1)
			setRect: 0 10 319 199
			init:
			drawPic: scriptNumber 0
			addCast: newCast
		)
		(= temp1 (IntArray new: 4))
		(= heroX (IntArray with: 169 169 169))
		(= heroY (IntArray with: 150 150 150))
		(= userNameSize (userName size:))
		(temp1 dispose:)
		(self
			add:
				strengthIcon
				intgenIcon
				agilityIcon
				vitalIcon
				luckIcon
				magicIcon
				commIcon
				weaponIcon
				parryIcon
				dodgeIcon
				stealthIcon
				pickIcon
				throwIcon
				climbIcon
				acrobIcon
				honorIcon
				pointIcon
				healthIcon
				staminaIcon
				manaIcon
				namePlate
		)
		(self eachElementDo: #init self)
	)	
	
	(method (show &tmp temp0 temp1 theNextNode temp3)
		(|= state $0020)
		(Message MsgGet scriptNumber
			(switch heroType
				(0 2)
				(1 3)
				(2 4)
				(3 5)
			)
			0
			0
			1
			(newStr data?)
		)
		((myDText new:)
			posn: 150 155
			text: (newStr data?)
			font: 8
			fore: 174
			back: 254
			skip: 254
			setSize:
			setPri: 242
			init: newCast
		)
		((myDText new:)
			posn: 149 155
			text: (newStr data?)
			font: 8
			fore: TEXT_COLOR
			back: 254
			skip: 254
			setSize:
			setPri: 243
			init: newCast
		)
		(if (== heroType PALADIN)
			(aHero
				loop: 0
				cel: 0
				posn: (heroX at: 0) (heroY at: 0)
				init: newCast
			)
		else
			(aHero
				loop: heroType
				cel: 0
				posn: (heroX at: heroType) (heroY at: heroType)
				init: newCast
			)
		)
		(= temp0 30)
		(= temp1 30)
		(= theNextNode (FirstNode elements))
		(while theNextNode
			(= nextNode (NextNode theNextNode))
			(if (not (= temp3 (NodeValue theNextNode))) (return))
			(if
				(and
					(not (& (temp3 signal?) $0080))
					(<= (temp3 nsRight?) 0)
				)
				(temp3 show: temp0 temp1)
				(= temp0 (+ 20 (temp3 nsRight?)))
			else
				(temp3 show:)
			)
			(= theNextNode nextNode)
		)
		(UpdatePlane plane)
		(self doit: hide:)
	)
	
	(method (swapCurIcon)
	)
	
	(method (advanceCurIcon)
	)	
)

(instance aHero of View
	(properties
		view 802
	)
)

(instance myDText of DText	
	(method (dispose &tmp planeCasts theBitmap)
		(= theBitmap 0)
		(if bitmap (= theBitmap bitmap) (= bitmap 0))
		(cast delete: self)
		(if (self isNotHidden:) (DeleteScreenItem self))
		((= planeCasts (plane casts?))
			eachElementDo: #delete self
		)
		(= plane 0)
		(DisposeClone self)
		(if theBitmap (Bitmap 1 theBitmap))
	)
)
