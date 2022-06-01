;;; Sierra Script 1.0 - (do not remove this comment)
(script# 119)
(include game.sh) (include "119.shm")
(use Main)
(use Feature)
(use GameRoom)
(use Polygon)
;(use Encounter)
(use System)

(public
	rm119 0
)

;NOTE: This room contains the witch mama's treehouse.

(instance rm119 of HeroRoom ;EncRoom
	(properties
		picture scriptNumber
		south 126
		west 118
		;encChance 30
		;entrances (| reSOUTH reWEST)

	)
	
	(method (init)
		(self setRegions: rgFOREST)
		(super init:)
		(treeHouse
			init:
			setPolygon: (&getpoly {Tree})
			approachVerbs: V_DO
		)
		(ego normalize:)
		(if (not (OneOf prevRoomNum vGoblin vGuard vRogue))
			(= egoX (ego x?))
			(= egoY (ego y?))
		)
		;(self setRegions: rgENCOUNTER)
	)
	
	(method (doVerb)
		(return FALSE)
	)
	
	(method (cue)
		(self newRoom: rWitchHouse)
	)
)

(instance treeHouse of Feature
	(properties
		x 235
		y 90
		noun N_TREEHOUSE
		approachX 230
		approachY 90
	)
	
	(verbs
		(V_DO
			(if Night
				(if (== contentLevel contentEXPLICIT)
					(messager say: N_TREEHOUSE V_DO C_NIGHT_EXPLICIT)
				else
					(messager say: N_TREEHOUSE V_DO C_NIGHT_CLEAN)
				)
			else
				(messager say: N_TREEHOUSE V_DO C_DAY 0 curRoom)
			)
		)
	)
)