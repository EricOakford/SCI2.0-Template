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
		(= inventory self)
		(self
			add:
			;add inventory items here
				Money
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
		(ego get: iMoney)
	)
	
	(method (setPlane v l c)
		(plane
			left: (/ (- 320 (CelWide v l c)) 2)
			top: (/ (- 153 (CelHigh v l c)) 2)
			setBitmap: v l c 0
		)
	)
	
	(method (carryingNothing)
		(messager say: N_EMPTY NULL NULL 0 0 GAME_INV)
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

;Declare your inventory items below
(instance Money of InvItem
	(properties
		mainView vInvItems
		mainLoop 0
		mainCel iMoney	;cel and item number are the same by default
		signal IMMEDIATE
		message V_MONEY
		noun N_MONEY
		name "Money"
	)
)