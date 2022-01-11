;;; Sierra Script 1.0 - (do not remove this comment)
;
;	GAMEICONBAR.SC
;
;	The game's icon bar is initialized here. It could have been in the game's Main script, but
;	doing it this way makes everything better organized. In fact, this was done more often for SCI32 games.
;
;

(script# GAME_ICONBAR)
(include game.sh) (include "11.shm")
(use Main)
(use IconBar)
(use System)
(use Actor)
(use Cursor)

(public
	mainIconBar 0
)

;starting icon coords
(define ICON_X 18)
(define ICON_Y 25)

(instance mainIconBar of IconBar
	(method (init &tmp theCast)
		((= theIconBar self)
			add:
			;These correspond to ICON_*** in game.sh
				iconWalk iconLook iconDo iconTalk iconCustom
				iconUseIt iconInventory iconControlPanel iconHelp
			curIcon: iconWalk	;gotta start somewhere
			useIconItem: iconUseIt
			helpIconItem: iconHelp
			walkIconItem: iconWalk
			eachElementDo: #y ICON_Y
			eachElementDo: #modNum GAME_ICONBAR
			state: (| OPENIFONME NOCLICKHELP)
		)
		(super init: &rest)
		(plane addCast: (= theCast (Cast new:)))
		(invItem init: theCast)
	)
)

(instance iconWalk of IconItem
	(properties
		x ICON_X
		mainView vIconBar
		mainLoop lWalkIcon
		mainCel 0
		type (| userEvent walkEvent)
		message V_WALK
		signal (| HIDEBAR RELVERIFY)
		noun N_WALK
		helpVerb V_HELP
		cursorView vIconCursors
		cursorLoop 0
		cursorCel 0
		maskView vIconBar
		maskLoop lDisabledIcon
		maskCel 0
	)
)

(instance iconLook of IconItem
	(properties
		mainView vIconBar
		mainLoop lLookIcon
		mainCel 0
		message V_LOOK
		signal (| HIDEBAR RELVERIFY)
		noun N_LOOK
		helpVerb V_HELP
		cursorView vIconCursors
		cursorLoop 0
		cursorCel 1
		maskView vIconBar
		maskLoop lDisabledIcon
		maskCel 1
	)

	(method (init)
		(= x
			(+
				(iconWalk x?)
				(CelWide
					(iconWalk mainView?)
					(iconWalk mainLoop?)
					(iconWalk mainCel?)
				)
			)
		)
		(super init: &rest)
	)
)

(instance iconDo of IconItem
	(properties
		mainView vIconBar
		mainLoop lDoIcon
		mainCel 0
		message V_DO
		signal (| HIDEBAR RELVERIFY)
		noun N_DO
		helpVerb V_HELP
		cursorView vIconCursors
		cursorLoop 0
		cursorCel 2
		maskView vIconBar
		maskLoop lDisabledIcon
		maskCel 2
	)

	(method (init)
		(= x
			(+
				(iconLook x?)
				(CelWide
					(iconLook mainView?)
					(iconLook mainLoop?)
					(iconLook mainCel?)
				)
			)
		)
		(super init: &rest)
	)
)

(instance iconTalk of IconItem
	(properties
		mainView vIconBar
		mainLoop lTalkIcon
		mainCel 0
		message V_TALK
		signal (| HIDEBAR RELVERIFY)
		noun N_TALK
		helpVerb V_HELP
		cursorView vIconCursors
		cursorLoop 0
		cursorCel 3
		maskView vIconBar
		maskLoop lDisabledIcon
		maskCel 3
	)
	
	(method (init)
		(= x
			(+
				(iconDo x?)
				(CelWide
					(iconDo mainView?)
					(iconDo mainLoop?)
					(iconDo mainCel?)
				)
			)
		)
		(super init: &rest)
	)
)

(instance iconCustom of IconItem
	(properties
		mainView vIconBar
		mainLoop lCustomIcon
		mainCel 0
		message 0
		signal IMMEDIATE
		noun N_CUSTOM
		helpVerb V_HELP
		maskView vIconBar
		maskLoop lDisabledIcon
		maskCel 9
	)
	
	(method (init)
		(= x
			(+
				(iconTalk x?)
				(CelWide
					(iconTalk mainView?)
					(iconTalk mainLoop?)
					(iconTalk mainCel?)
				)
			)
		)
		(super init: &rest)
	)
	
	(method (select)
		(return FALSE)
	)
)


(instance iconUseIt of IconItem
	(properties
		mainView vIconBar
		mainLoop lItemIcon
		mainCel 0
		message 0
		signal (| HIDEBAR RELVERIFY)
		noun N_CURITEM
		helpVerb V_HELP
		maskView vIconBar
		maskLoop lDisabledIcon
		maskCel 4
	)
	(method (init)
		(= x
			(+
				(iconCustom x?)
				(CelWide
					(iconCustom mainView?)
					(iconCustom mainLoop?)
					(iconCustom mainCel?)
				)
			)
		)
		(super init: &rest)
	)
)

(instance iconInventory of IconItem
	(properties
		mainView vIconBar
		mainLoop lInvIcon
		mainCel 0
		type nullEvt
		message 0
		signal (| HIDEBAR RELVERIFY IMMEDIATE)
		noun N_INVENTORY
		helpVerb V_HELP
		maskView vIconBar
		maskLoop lDisabledIcon
		maskCel 5
	)

	(method (init)
		(= x
			(+
				(iconUseIt x?)
				(CelWide
					(iconUseIt mainView?)
					(iconUseIt mainLoop?)
					(iconUseIt mainCel?)
				)
			)
		)
		(super init: &rest)
	)
	
	(method (select)
		(return
			(if (super select: &rest)
				(ego showInv:)
				(return TRUE)
			else
				(return FALSE)
			)
		)
	)
)

(instance iconControlPanel of IconItem
	(properties
		mainView vIconBar
		mainLoop lControlIcon
		mainCel 0
		message V_CONTROL
		signal (| HIDEBAR RELVERIFY IMMEDIATE)
		noun N_CONTROL
		helpVerb V_HELP
		maskView vIconBar
		maskLoop lDisabledIcon
		maskCel 7
	)
	
	(method (init)
		(= x
			(+
				(iconInventory x?)
				(CelWide
					(iconInventory mainView?)
					(iconInventory mainLoop?)
					(iconInventory mainCel?)
				)
			)
		)
		(super init: &rest)
	)
	
	(method (select)
		(return
			(if (super select: &rest)
				(theGame showControls:)
				(return TRUE)
			else
				(return FALSE)
			)
		)
	)
)

(instance iconHelp of IconItem
	(properties
		mainView vIconBar
		mainLoop lHelpIcon
		mainCel 0
		type helpEvent
		message V_HELP
		signal (| RELVERIFY IMMEDIATE)
		noun N_HELP
		helpVerb V_HELP
		cursorView vIconCursors
		cursorLoop 0
		cursorCel 4
		maskView vIconBar
		maskLoop lDisabledIcon
		maskCel 8
	)
	(method (init)
		(= x
			(+
				(iconControlPanel x?)
				(CelWide
					(iconControlPanel mainView?)
					(iconControlPanel mainLoop?)
					(iconControlPanel mainCel?)
				)
			)
		)
		(super init: &rest)
	)
)

(instance invItem of View
	(properties
		x 203
		y 15
		view vInvItems
	)
	
	(method (init)
		(super init: &rest)
		(self hide:)
	)
)

