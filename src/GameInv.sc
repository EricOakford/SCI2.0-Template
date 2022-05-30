;;; Sierra Script 1.0 - (do not remove this comment)
;
;	GAMEINV.SC
;
;	GameInvItem is a game-specific subclass of InvItem.
;	Here, you can add inventory item instances, and can create custom properties.
;	
;	 An example might be::
;	
;	 	(instance Hammer of GameInvItem
;	 		(properties
;	 			view 900
;	 			loop 1
;	 			cursor 900			; Optional cursor when using this item.
;	 			message V_HAMMER	; Optional verb associated with the item.
;	 			signal IMMEDIATE
;	 			noun N_HAMMER		; noun from message resource 0
;	 		)
;	 	)
;	
;	 Then in the GameInv init, add the inventory item to the add: call.
;
;

(script# GAME_INV)
(include game.sh) (include "6.shm")
(use Main)
(use Print)
(use Plane)
(use IconBar)
(use Invent)
(use System)

(public
	GameInv 0
)

(local
	theItem
	theOwner
)

(define ITEMS_PER_PAGE 15)


(instance GameInv of Inventory
	(properties
		iconRight 160
		rowMargin 1
		numRow 3
		numCol 6
		itemWide 27
		itemHigh 24
	)
	;This is the game-specific inventory	
	(method (init)
		((= inventory self)
			add:
			;add inventory items here
				Money
				Food
				SkeletonKey
				Sword
				Shield
				LaundryNote
				Amulet
				Vase
				Candelabra
				HellCandle
				Pearls
				Evidence
				Bonehead
				HealingPotion
				VigorPotion
				ManaPotion
				UndeadElixir
				SleepingPills
				BeerKeg
				Lockpick
				MeepToy
				DragonsBreath
				LeatherArmor
				ChainmailArmor
				Dagger
				GargoyleCard
				Lasso
				Shovel
				CleoBones
				BallerinaCard
				AMTMCard
				InflatableBear
				Fingerbone
				GiantClothes
				Whistle
				Bong
				LoveLetter
				GhostMoney
		)
		(self
			add:
			;add icons here
				invLook
				invHand
				invSelect
				invHelp
				ok
			helpIconItem: invHelp
			selectIcon: invSelect
			okButton: ok
			eachElementDo: #modNum GAME_INV
			state: NOCLICKHELP
		)
		(super init:)
		(self setPlane: vInvWindow 0 0)
	)
	
	(method (setPlane v l c)
		(plane
			left: (/ (- 320 (CelWide v l c)) 2)
			top: (/ (- 153 (CelHigh v l c)) 2)
			setBitmap: v l c 0
		)
	)
)

(instance ok of IconItem
	(properties
		mainView vInvIcons
		mainLoop lInvOK
		mainCel 0
		signal (| HIDEBAR RELVERIFY IMMEDIATE)
		noun N_OK
		helpVerb V_HELP
	)
)

(instance invLook of IconItem
	(properties
		mainView vInvIcons
		mainLoop lInvLook
		mainCel 0
		message V_LOOK
		signal (| FIXED_POSN RELVERIFY)
		noun N_LOOK
		helpVerb V_HELP
		cursorView vIconCursors
		cursorLoop 0
		cursorCel 1
	)
)

(instance invHand of IconItem
	(properties
		mainView vInvIcons
		mainLoop lInvHand
		mainCel 0
		message V_DO
		noun N_HAND
		helpVerb V_HELP
		cursorView vIconCursors
		cursorLoop 0
		cursorCel 2
	)
)

(instance invHelp of IconItem
	(properties
		mainView vInvIcons
		mainLoop lInvHelp
		mainCel 0
		message V_HELP
		signal (| RELVERIFY IMMEDIATE)
		noun N_HELP
		helpVerb V_HELP
		cursorView vIconCursors
		cursorLoop 0
		cursorCel 4
	)
)

(instance invSelect of IconItem
	(properties
		x 7
		y 88
		mainView vInvIcons
		mainLoop lInvSelect
		mainCel 0
		noun N_SELECT
		helpVerb V_HELP
		cursorView ARROW_CURSOR
		cursorLoop 0
		cursorCel 0
	)
)

(class GameInvItem of InvItem
	(properties
		amount 0
		realOwner 0
		view vInvItems
		signal IMMEDIATE
	)

	(method (doVerb theVerb)
		(if (Message MsgSize modNum noun theVerb NULL 1)
			(Print
				addText: noun theVerb NULL 1 0 0 modNum
				init:
			)
		else ;no specific message
			(Print
				addText: NULL V_COMBINE NULL 1 0 0 modNum
				init:
			)
		)
	)
)


;Declare your inventory items below
(instance Money of GameInvItem
	;all amounts are measured in silvers
	(properties
		view vInvItems
		loop 0
		cel 0
		message V_MONEY
		noun N_MONEY
	)
	
	(method (doVerb theVerb &tmp [str 50] haveG haveS)
		(switch theVerb
			(V_LOOK
				(Message MsgGet modNum noun theVerb NULL 1 @str)
				(= haveG (/ amount 10))
				(= haveS (mod amount 10))
				(Print
					addTextF: @str haveG haveS
					init:
				)
			)
		)
	)
)

(instance Food of GameInvItem
	(properties
		view vInvItems
		loop 0
		cel 1
		message V_FOOD
		noun N_FOOD
	)
	
	(method (doVerb theVerb &tmp [str 50])
		(switch theVerb
			(V_LOOK
				(Message MsgGet modNum noun theVerb NULL 1 @str)
				(Print
					addTextF: @str amount
					init:
				)
			)
			(else
				(super doVerb: theVerb)
			)
		)
	)
)

(instance SkeletonKey of GameInvItem
	(properties
		loop 0
		cel 2
		message V_SKELETON_KEY
		noun N_SKELETON_KEY
	)
)

(instance Sword of GameInvItem
	(properties
		loop 0
		cel 3
		message V_SWORD
		noun N_SWORD
	)
)

(instance Shield of GameInvItem
	(properties
		loop 0
		cel 4
		message V_SHIELD
		noun N_SHIELD
	)
)

(instance LaundryNote of GameInvItem
	(properties
		loop 0
		cel 5
		message V_LAUNDRY_NOTE
		noun N_LAUNDRY_NOTE
	)
)

(instance Amulet of GameInvItem
	(properties
		loop 0
		cel 6
		message V_AMULET
		noun N_AMULET
	)
)

(instance Vase of GameInvItem
	(properties
		loop 0
		cel 7
		message V_VASE
		noun N_VASE
	)
)

(instance Candelabra of GameInvItem
	(properties
		loop 0
		cel 8
		message V_CANDELABRA
		noun N_CANDELABRA
	)
)

(instance HellCandle of GameInvItem
	(properties
		loop 0
		cel 9
		message V_HELL_CANDLE
		noun N_HELL_CANDLE
	)
)

(instance Pearls of GameInvItem
	(properties
		loop 0
		cel 10
		message V_PEARLS
		noun N_PEARLS
	)
)

(instance Evidence of GameInvItem
	(properties
		loop 0
		cel 11
		message V_EVIDENCE
		noun N_EVIDENCE
	)
)

(instance Bonehead of GameInvItem
	(properties
		loop 1
		cel 0
		message V_BONEHEAD
		noun N_BONEHEAD
	)
)

(instance HealingPotion of GameInvItem
	(properties
		loop 1
		cel 1
		message V_HEALING_POTION
		noun N_HEALING_POTION
	)
	(method (doVerb theVerb &tmp [str 50])
		(switch theVerb
			(V_LOOK
				(Message MsgGet modNum noun theVerb NULL 1 @str)
				(Print
					addTextF: @str amount
					init:
				)
			)
		)
	)
)

(instance VigorPotion of GameInvItem
	(properties
		loop 1
		cel 2
		message V_VIGOR_POTION
		noun N_VIGOR_POTION
	)
	(method (doVerb theVerb &tmp [str 50])
		(switch theVerb
			(V_LOOK
				(Message MsgGet modNum noun theVerb NULL 1 @str)
				(Print
					addTextF: @str amount
					init:
				)
			)
		)
	)
)

(instance ManaPotion of GameInvItem
	(properties
		loop 1
		cel 3
		message V_MANA_POTION
		noun N_MANA_POTION
	)
	(method (doVerb theVerb &tmp [str 50])
		(switch theVerb
			(V_LOOK
				(Message MsgGet modNum noun theVerb NULL 1 @str)
				(Print
					addTextF: @str amount
					init:
				)
			)
		)
	)
)

(instance UndeadElixir of GameInvItem
	(properties
		loop 1
		cel 4
		message V_UNDEAD_ELIXIR
		noun N_UNDEAD_ELIXIR
	)
)

(instance SleepingPills of GameInvItem
	(properties
		loop 1
		cel 5
		message V_SLEEPING_PILLS
		noun N_SLEEPING_PILLS
	)
)

(instance BeerKeg of GameInvItem
	(properties
		loop 1
		cel 6
		message V_BEER_KEG
		noun N_BEER_KEG
	)
	
	(method (doVerb theVerb &tmp c)
		(switch theVerb
			(V_LOOK
				(= c 0)
				(if state	;drugged
					(= c C_DRUGGED)
				)
				(Print
					addText: noun theVerb c 1 0 0 modNum
					init:
				)
			)
			(V_SLEEPING_PILLS
				(= state TRUE)
				;make sure the pills are actually removed from the inventory
				(ego use: iSleepingPills)
				(messager say: noun theVerb NULL 0 self modNum)
			)
			(else
				(super doVerb: theVerb)
			)
		)
	)
)

(instance Lockpick of GameInvItem
	(properties
		loop 1
		cel 7
		message V_LOCKPICK
		noun N_LOCKPICK
	)
)

(instance MeepToy of GameInvItem
	(properties
		loop 1
		cel 8
		message V_MEEP_TOY
		noun N_MEEP_TOY
	)
)

(instance DragonsBreath of GameInvItem
	(properties
		loop 1
		cel 9
		message V_DRAGON_BREATH
		noun N_DRAGONS_BREATH
	)
)

(instance LeatherArmor of GameInvItem
	(properties
		loop 1
		cel 10
		message V_LEATHER
		noun N_LEATHER
	)
)

(instance ChainmailArmor of GameInvItem
	(properties
		loop 1
		cel 11
		message V_CHAINMAIL
		noun N_CHAINMAIL
	)
)

(instance Dagger of GameInvItem
	(properties
		loop 2
		cel 0
		message V_DAGGER
		noun N_DAGGER
	)
	(method (doVerb theVerb &tmp [str 50])
		(switch theVerb
			(V_LOOK
				(Message MsgGet modNum noun theVerb NULL 1 @str)
				(Print
					addTextF: @str amount
					init:
				)
			)
		)
	)
)

(instance GargoyleCard of GameInvItem
	(properties
		loop 2
		cel 1
		message V_GARGOYLE_CARD
		noun N_GARGOYLE_CARD
	)
)

(instance Lasso of GameInvItem
	(properties
		loop 2
		cel 2
		message V_LASSO
		noun N_LASSO
	)
)

(instance Shovel of GameInvItem
	(properties
		loop 2
		cel 3
		message V_SHOVEL
		noun N_SHOVEL
	)
)

(instance CleoBones of GameInvItem
	(properties
		loop 2
		cel 4
		message V_CLEO_BONES
		noun N_CLEO_BONES
	)
)

(instance BallerinaCard of GameInvItem
	(properties
		loop 2
		cel 5
		message V_CLEO_BONES
		noun N_CLEO_BONES
	)
)

(instance AMTMCard of GameInvItem
	(properties
		loop 2
		cel 6
		message V_CLEO_BONES
		noun N_CLEO_BONES
	)
)

(instance InflatableBear of GameInvItem
	(properties
		loop 2
		cel 7
		message V_CLEO_BONES
		noun N_CLEO_BONES
	)
)

(instance Fingerbone of GameInvItem
	(properties
		loop 2
		cel 8
		message V_FINGER_BONE
		noun N_FINGER_BONE
	)
)

(instance GiantClothes of GameInvItem
	(properties
		loop 2
		cel 9
		message V_GIANT_CLOTHES
		noun N_GIANT_CLOTHES
	)
)

(instance Whistle of GameInvItem
	(properties
		loop 3
		cel 0
		message V_WHISTLE
		noun N_WHISTLE
	)
)

(instance Bong of GameInvItem
	(properties
		loop 3
		cel 1
		message V_BONG
		noun N_BONG
	)
)

(instance LoveLetter of GameInvItem
	(properties
		loop 3
		cel 2
		message V_LOVE_LETTER
		noun N_LOVE_LETTER
	)
)

(instance GhostMoney of GameInvItem
	(properties
		loop 3
		cel 3
		message V_GHOST_MONEY
		noun N_GHOST_MONEY
	)
)