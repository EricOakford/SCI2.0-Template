;;; Sierra Script 1.0 - (do not remove this comment)
;
;	GAMEABOUT.SC
;
;	Displays a message in response to a call to "theGame showAbout:".
;
;

(script# GAME_ABOUT)
(include game.sh) (include "9.shm")
(use Main)
(use System)

(public
	aboutCode 0
)

(instance aboutCode of Code
	(method (doit)
		(messager say: N_ABOUT NULL NULL NULL NULL GAME_ABOUT)
		(self dispose:)
	)
)


