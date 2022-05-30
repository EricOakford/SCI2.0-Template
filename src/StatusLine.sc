;;; Sierra Script 1.0 - (do not remove this comment)
;
;	STATUSLINE.SC
;
;	This script manages the game's status line.
;	As SCI32 did away with the DrawStatus kernel call,
;	this now has to be done entirely in the script.
;
(script# STATUS_LINE)
(include game.sh) (include "0.shm")
(use Main)
(use Plane)
(use String)
(use DText)
(use System)

(public
	statusPlane 0
	statusCode 1
)

(instance statusPlane of Plane)

(instance statusText of DText)

(instance statusCode of Code
	(method (doit roomNum &tmp statusBuf scoreBuf statCast)
		;get the text from the msg
		(= statusBuf (String new:))
		(Message MsgGet MAIN N_STATUSLINE NULL NULL 1 (statusBuf data?))
		
		;get the current and maximum scores
		(= scoreBuf (String new:))
		(scoreBuf format: statusBuf score possibleScore)
		
		;set up the plane except in rooms where it's not shown
		(if (not (OneOf roomNum TITLE WHERE_TO DEATH GAME_RESTART))
			(statusPlane
				priority: (+ (GetHighPlanePri) 1)
				addCast: (= statCast (Cast new:))
				setRect: 0 0 319 9
			)
			;Now actually display the text
			(statusText
				font: 1307
				text: (scoreBuf data?)
				fore: 23
				setPri: 500
				setSize: 700
				posn: 0 0
				init: statCast
			)
		else
			;hide the status line
			(statusPlane setInsetRect: -1 -1 -1 -1)
		)
		;clean up the strings and update the plane
		(statusBuf dispose:)
		(scoreBuf dispose:)
		(UpdatePlane statusPlane)
	)
)
